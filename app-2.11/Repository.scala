package repository

import javax.inject.{Inject, Singleton}

import slick.dbio.DBIO
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Repository @Inject()(val teamstore: TeamStore) {

  import teamstore._

  lazy val dbConfig = teamstore.dbConfigProvider.get[JdbcProfile]

  def insertEnrolment(string: String)(implicit ec: ExecutionContext): Future[Unit] = {
    import dbConfig._
    import dbConfig.profile.api._

    db.run(findAllPeopleQuery).map(_ => Unit)
  }

  //Highly convoluted exmaple...
  //person name is not uniquie...
  def switchTeams(name: String, newTeam: String, fullCapacity: Boolean) = {
    import dbConfig._
    import dbConfig.profile.api._

    db.run {
      (for {
        teamID <- findTeamId(newTeam)
        personID <- findPersonID(name)
        updatePersonsTeam <- updateTeam(personID, Some(teamID))
        updateTeamCapactiy <- updateCapacity(teamID, fullCapacity)
      } yield ()).transactionally
    }
  }


}
