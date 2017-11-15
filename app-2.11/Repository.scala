package repository

import javax.inject.Inject

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import tables.PeopleTable

import scala.concurrent.ExecutionContext.Implicits.global

//* The overall idea for the layout is to inject the database config into this repository class (this is handeled by Slick, all we do is name the database to match the application config).
//* Once we have the DB connection we also inherit the tables we've defined. When the tables are dependent they will inherit from each other so in this case although we will define two tables, because they're connected by a foreign key we only need to inherit one.
class Repository @Inject()(@NamedDatabase("charlie") val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] with PeopleTable {

  lazy val dbConf = dbConfigProvider.get[JdbcProfile]

  //* Now that we have two tables we can have a look at defining a more complicated function. In this highly convoluted example we'll define a function which switchs a person from one team AND updates the new team to have fullCapacity.
  //* Yes it's not the best example, especially as the person name is not unique...

  //* To stop someone else moving on to the team we want to update transitionally, e.g. either the person moves and we set the team to fullCapacity or we do neither. Here all we have to to is append ".transactionally" to our DBIOAction to make this happen.
  def switchTeams(name: String, newTeam: String, fullCapacity: Boolean) = {
    import dbConf._
    import dbConf.profile.api._

    dbConf.db.run {
      //* As mentioned we can play with DBIOActions just like we would with futures so here we use a for yield to combine several DBIOActions together.
      (for {
        teamID <- findTeamId(newTeam)
        personID <- findPersonID(name)
        updatePersonsTeam <- updateTeam(personID, Some(teamID))
        updateTeamCapacity <- updateCapacity(teamID, fullCapacity)
      } yield ()).transactionally
    }
  }


}
