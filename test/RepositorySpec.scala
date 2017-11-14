import repository.Repository

class RepositorySpec extends IntegrationTests {

  val repository = new Repository(dbConfigProvider)

  import repository._

  "switchTeams" must {
    "put Yen in EMAC" in {
      //Requires other IT tests to be run to create tables and populate data... which leads on to Evolutions...
      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)
      println(Console.YELLOW + awaitDatabase(findAllPeopleQuery) + Console.RESET)

      await(switchTeams("Yen", "EMAC", true))

      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)
      println(Console.YELLOW + awaitDatabase(findAllPeopleQuery) + Console.RESET)
    }
//    "456" in {
//      //checks to see if evolutions works
//      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)
//      println(Console.YELLOW + awaitDatabase(findAllPeopleQuery) + Console.RESET)
//    }
  }

}
