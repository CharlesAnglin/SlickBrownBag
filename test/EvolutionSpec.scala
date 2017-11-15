import repository.Repository

class EvolutionSpec extends IntegrationTests {

  val repository = new Repository(dbConfigProvider)

  import repository._

  //    "456" in {
  //      //checks to see if evolutions works
  //      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)
  //      println(Console.YELLOW + awaitDatabase(findAllPeopleQuery) + Console.RESET)
  //    }

}
