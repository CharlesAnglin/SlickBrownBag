import repository.Repository

class RepositorySpec extends IntegrationTests {

  val repository = new Repository(dbConfigProvider)

  import repository._

  "switchTeams" must {
    "put Yen in EMAC" in {
      //* First of all we need to insert some data to play with.
      awaitDatabase(createTeamReturningIDQuery("EMAC", false))
      val rateId = awaitDatabase(createTeamReturningIDQuery("RATE", false))
      awaitDatabase(createPersonReturningIDQuery("Yen", Some(rateId)))

      //* print lines before and after to see what's changed.
      println(Console.MAGENTA + awaitDatabase(findAllTeamsQuery) + Console.RESET)
      println(Console.MAGENTA + awaitDatabase(findAllPeopleQuery) + Console.RESET)

      await(switchTeams("Yen", "EMAC", true))

      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)
      println(Console.YELLOW + awaitDatabase(findAllPeopleQuery) + Console.RESET)
    }
  }
}
