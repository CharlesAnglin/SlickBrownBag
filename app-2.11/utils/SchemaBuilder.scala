package utils

import javax.inject.{Inject, Singleton}

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import tables.PeopleTable

@Singleton
class SchemaBuilder @Inject()(@NamedDatabase("charlie") val dbConfigProvider: DatabaseConfigProvider)
  extends PeopleTable with HasDatabaseConfigProvider[JdbcProfile] {

  def printSchemas(): Unit = {
    import dbConfig.profile.api._

    val tables = Seq(team, people)
      .map(_.asInstanceOf[TableQuery[Table[Any]]])

    println("# --- !Ups")
    tables.foreach { t => t.schema.createStatements.foreach(s => println(s + ";")); println() }
    println()
    println("""insert into "team" ("teamName", "fullCapacity")  values ('EMAC', 'false');""")
    println("""insert into "team" ("teamName", "fullCapacity")  values ('RATE', 'false');""")
    println()
    println("""insert into "people" ("name", "team")  values ('Charlie', '1');""")
    println("""insert into "people" ("name", "team")  values ('Yen', '2');""")
    println("""insert into "people" ("name", "team")  values ('Dan', null);""")
    println()
    println("# --- !Downs")
    tables.reverse.foreach { t => t.schema.dropStatements.foreach(s => println(s + ";")); println() }
  }
}
