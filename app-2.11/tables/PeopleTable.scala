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

    //* Looks very similar to the TeamTable but this time we add a foreign key linking this table to the TeamTable. Note that you can add extra arguments in here on what to do when updating or deleting this column.
    def teamFk = foreignKey("team_FK", teamID, team)(_.teamID)

    override def * = (name, teamID, personID) <>
      ((People.apply _).tupled, People.unapply)
  }

  protected val people = TableQuery[PeopleTable]

  def findAllPeopleQuery =
    people.result

  def findPersonID(name: String) =
    people.filter(_.name === name).map(_.personID).result.head

  def updateTeam(personID: Int, teamID: Option[Int]) =
    people.filter(_.personID === personID).map(_.teamID).update(teamID)

  def createPersonReturningIDQuery(name: String, team: Option[Int] = None) =
    people returning people.map(_.personID) += People(name, team)
}
