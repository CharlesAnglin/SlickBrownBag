import models.People
import tables.{PeopleTable, TeamTable}
import slick.dbio.DBIO

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class TestSpec extends IntegrationTests with PeopleTable {

  //What does this import allow us to do?
  import dbConfig.profile.api._

  val createTables = (team.schema ++ people.schema).create
    db.run(createTables)

  "abc" must {
    "123" in {
      val DBIOAction = createPersonReturningIDQuery("Jeff")
      val future = db.run(DBIOAction)
      await(future)

      val people = awaitDatabase(findAllPeopleQuery)
      println(people)
    }
    "456" in {
      awaitDatabase(createTeamReturningIDQuery("EMAC", false))
      awaitDatabase(createTeamReturningIDQuery("RATE", false))
    }
    "789" in {
      val teamId = awaitDatabase(findTeamId("EMAC"))
      awaitDatabase(createPersonReturningIDQuery("Yen", Some(teamId)))

      val error = intercept[Exception] {
        awaitDatabase(createPersonReturningIDQuery("Bobby", Some(1001)))
      }
      println(Console.YELLOW + error.getMessage + Console.RESET)
    }
    "101112" in {
      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)

      val teamID = awaitDatabase(findTeamId("RATE"))
      awaitDatabase(updateCapacity(teamID, true))

      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)
    }
    "131415" in {

    }
  }

}
