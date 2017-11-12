import repository.{Repository, TeamStore}

class RepositorySpec extends IntegrationTests {

  val teamStore = app.injector.instanceOf[TeamStore]
  val repository = new Repository(teamStore)

  import teamStore._
  import repository._

  "abc" must {
    "123" in {
      //Requires other IT tests to be run to populate data... which elads on to Evolutions...
      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)
      println(Console.YELLOW + awaitDatabase(findAllPeopleQuery) + Console.RESET)

      await(switchTeams("Jeff", "EMAC", true))

      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)
      println(Console.YELLOW + awaitDatabase(findAllPeopleQuery) + Console.RESET)
    }
  }

}
