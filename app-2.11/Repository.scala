package repository

import javax.inject.{Inject, Singleton}

import slick.dbio.DBIO
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class Repository @Inject()(val teamstore: TeamStore) {

  import teamstore._

  lazy val dbConfig = teamstore.dbConfigProvider.get[JdbcProfile]

  //Highly convoluted example...
  //person name is not uniquie...
  //Can treat DBIO actions just like futures... and TRANSACTIONS!
  def switchTeams(name: String, newTeam: String, fullCapacity: Boolean) = {
    import dbConfig._
    import dbConfig.profile.api._

    db.run {
      (for {
        teamID <- findTeamId(newTeam)
        personID <- findPersonID(name)
        updatePersonsTeam <- updateTeam(personID, Some(teamID))
        updateTeamCapacity <- updateCapacity(teamID, fullCapacity)
      } yield ()).transactionally
    }
  }


}
