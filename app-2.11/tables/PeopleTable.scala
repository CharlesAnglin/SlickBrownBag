package tables

import models.People
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait PeopleTable extends TeamTable {
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import self.dbConfig.profile.api._

  class PeopleTable(tag: Tag) extends Table[People](tag, "people") {

    def personID = column[Int]("personID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def teamID = column[Option[Int]]("team")

    //Can add extra arguments about what to do when updating or deleting...
    def teamFk = foreignKey("team_FK", teamID, team)(_.teamID)

    override def * = (name, teamID, personID) <>
      ((People.apply _).tupled, People.unapply)
  }

  protected val people = TableQuery[PeopleTable]

  def createPersonReturningIDQuery(name: String, team: Option[Int] = None) =
    people returning people.map(_.personID) += People(name, team)

  def findAllPeopleQuery =
    people.result

  //Assumes names are unique
  def findPersonID(name: String) =
    people.filter(_.name===name).map(_.personID).result.head

  def updateTeam(personID: Int, teamID: Option[Int]) =
    people.filter(_.personID===personID).map(_.teamID).update(teamID)
}
