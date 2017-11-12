import repository.{Repository, TeamStore}

class RepositorySpec extends IntegrationTests {

  val teamStore = app.injector.instanceOf[TeamStore]
  val repository = new Repository(teamStore)

  import teamStore._
  import repository._

  "abc" must {
    "123" in {
      //Requires other IT tests to be run to create tables and populate data... which leads on to Evolutions...
      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)
      println(Console.YELLOW + awaitDatabase(findAllPeopleQuery) + Console.RESET)

      await(switchTeams("Yen", "EMAC", true))

      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)
      println(Console.YELLOW + awaitDatabase(findAllPeopleQuery) + Console.RESET)
    }
    "456" in {
      //checks to see if evolutions works
      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)
      println(Console.YELLOW + awaitDatabase(findAllPeopleQuery) + Console.RESET)
    }
  }

}
