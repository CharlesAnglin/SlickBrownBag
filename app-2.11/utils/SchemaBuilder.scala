package utils

import javax.inject.Inject

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import tables.PeopleTable

//* We "cheat" by creating a class which has access to the tables and prints the SQL statements out so we can copy and paste them into the evolutions file
class SchemaBuilder @Inject()(@NamedDatabase("charlie") val dbConfigProvider: DatabaseConfigProvider)
  extends PeopleTable with HasDatabaseConfigProvider[JdbcProfile] {

  def printSchemas(): Unit = {
    import dbConfig.profile.api._

    val tables = Seq(team, people)
      .map(_.asInstanceOf[TableQuery[Table[Any]]])

    //* The "ups" tells evolutions how to set up the tables and insert any required data, e.g. enums.
    println("# --- !Ups")
    tables.foreach { t => t.schema.createStatements.foreach(s => println(s + ";")); println() }
    println()
    println("""insert into "team" ("teamName", "fullCapacity")  values ('EMAC', 'false');""")
    println("""insert into "team" ("teamName", "fullCapacity")  values ('RATE', 'false');""")
    println()
    println("""insert into "people" ("name", "team")  values ('Charlie', '1');""")
    println("""insert into "people" ("name", "team")  values ('Yen', '2');""")
    println()
    //* The downs tells evolutions how to tear down the tables once we're finished.
    println("# --- !Downs")
    tables.reverse.foreach { t => t.schema.dropStatements.foreach(s => println(s + ";")); println() }
  }
  //* The printed lines need to be copy and pasted into a file called "1.sql" under "conf/evolutions/{databasename}".
}
