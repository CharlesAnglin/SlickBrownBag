package repository

import javax.inject.{Inject, Singleton}

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.dbio.DBIO
import slick.jdbc.JdbcProfile
import tables.PeopleTable

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

//* Now that we have two tables we can have a look at defining a more compilcated function. In this highly convoluted example we'll define a function which switchs a person from one team AND updates the new team to have fullCapacity.
//* Yes it's not the best example, ecspecially as the person name is not unique...
@Singleton
class Repository @Inject()(@NamedDatabase("charlie") val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] with PeopleTable {

  lazy val dbConf = dbConfigProvider.get[JdbcProfile]

//  * To stop someone else moving on to the team we want to update transactionally, e.g. either the person moves and we set the team to fullCapacity or we do neither. Here all we have to to is append ".transactionally" to our DBIOAction to make this happen.
  def switchTeams(name: String, newTeam: String, fullCapacity: Boolean) = {
    import dbConf._
    import dbConf.profile.api._

    dbConf.db.run {
//      * As mentioned we can play with DBIOActions just like we would with futures so here we use a for yield to combine several DBIOActions together.
      (for {
        teamID <- findTeamId(newTeam)
        personID <- findPersonID(name)
        updatePersonsTeam <- updateTeam(personID, Some(teamID))
        updateTeamCapacity <- updateCapacity(teamID, fullCapacity)
      } yield ()).transactionally
    }
  }


}
