package tables

import models.Team
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait TeamTable {
  //* This line ensures that whatever inherits this table trait has to also inherit the database config allowing us to define tables
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import self.dbConfig.profile.api._

  //* Tables are pretty boiler plate (so much so there exist things to define it for you). The main things we need are a case class of the data to go in, a column definition for each variable of the case class, boiler plate class, * and TableQuery definitions.
  class TeamTable(tag: Tag) extends Table[Team](tag, "team") {

    def teamID = column[Int]("teamID", O.PrimaryKey, O.AutoInc)

    def teamName = column[String]("teamName", O.Unique)

    def fullCapacity = column[Boolean]("fullCapacity")

    override def * = (teamName, fullCapacity, teamID) <>
      ((Team.apply _).tupled, Team.unapply)
  }

  //* We treat the TableQuery definition as our "scala collection", e.g. think of it as just a list.
  protected val team = TableQuery[TeamTable]

  //* Adding result turns our collection into a DBIOAction(More on these later). E.g. the rule is manipulate your collection to what you want and then add ".result" on the end
  def findAllTeamsQuery =
  team.result

  //* Here we only want the first result so we use ".head" - but after the ".result", not before.
  def findTeamId(teamName: String) =
  team.filter(_.teamName === teamName).map(_.teamID).result.head

  //* Changing data is the same idea, we manipulate the collection to find the values we want and then just call a different function on it, here we use ".update"
  def updateCapacity(teamID: Int, fullCapacity: Boolean) =
  team.filter(_.teamID === teamID).map(_.fullCapacity).update(fullCapacity)

  //* Here we're adding a new record. We could simply do "team += Team(a,b)" however as the teamID column is being made for us we would like it to be returned after inserting - this is what the "returning" syntax does
  def createTeamReturningIDQuery(teamName: String, fullCapactity: Boolean) =
  team returning team.map(_.teamID) += Team(teamName, fullCapactity)
}
