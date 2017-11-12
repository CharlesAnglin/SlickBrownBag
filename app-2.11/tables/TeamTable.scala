package tables

import models.{People, Team}
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait TeamTable {
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import self.dbConfig.profile.api._

  class TeamTable(tag: Tag) extends Table[Team](tag, "team") {

    def teamID = column[Int]("teamID", O.PrimaryKey, O.AutoInc)

    def teamName = column[String]("teamName", O.Unique)

    def fullCapacity = column[Boolean]("fullCapacity")

    override def * = (teamName, fullCapacity, teamID) <>
      ((Team.apply _).tupled, Team.unapply)
  }

  protected val team = TableQuery[TeamTable]

  def createTeamReturningIDQuery(teamName: String, fullCapactity: Boolean) =
    team returning team.map(_.teamID) += Team(teamName, fullCapactity)

  def findAllTeamsQuery =
    team.result

  //".result" transforms any query into a DBIOAction
  def findTeamId(teamName: String) =
    team.filter(_.teamName===teamName).map(_.teamID).result.head

  def updateCapacity(teamID: Int, fullCapacity: Boolean) =
    team.filter(_.teamID===teamID).map(_.fullCapacity).update(fullCapacity)
}
