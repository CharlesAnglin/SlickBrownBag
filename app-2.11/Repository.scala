package repository

import javax.inject.{Inject, Singleton}

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.dbio.DBIO
import slick.jdbc.JdbcProfile
import tables.PeopleTable

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class Repository @Inject()(@NamedDatabase("charlie") val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] with PeopleTable {

  lazy val dbConf = dbConfigProvider.get[JdbcProfile]

  //Highly convoluted example...
  //person name is not uniquie...
  //Can treat DBIO actions just like futures... and TRANSACTIONS!
  def switchTeams(name: String, newTeam: String, fullCapacity: Boolean) = {
    import dbConf._
    import dbConf.profile.api._

    dbConf.db.run {
      (for {
        teamID <- findTeamId(newTeam)
        personID <- findPersonID(name)
        updatePersonsTeam <- updateTeam(personID, Some(teamID))
        updateTeamCapacity <- updateCapacity(teamID, fullCapacity)
      } yield ()).transactionally
    }
  }


}
