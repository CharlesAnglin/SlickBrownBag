import repository.Repository

class EvolutionSpec extends IntegrationTests {

  val repository = new Repository(dbConfigProvider)

  import repository._

  //* After dropping the database and running these tests we see that not only don't we need to create the tables anymore as Evolutions is doing it for us.
  "Evolutions" must {
    "set up the database" in {
      println(Console.YELLOW + awaitDatabase(findAllTeamsQuery) + Console.RESET)
      println(Console.YELLOW + awaitDatabase(findAllPeopleQuery) + Console.RESET)
    }
  }
  //* As mentioned this is just database base set up, Evolutions can also be used for schema organisation. We can make multiple separate set up scripts. The convention is to name them 1.sql, 2.sql etc... On start up Evolutions will:
  //    Run the UPs from the first script, then the UPs from the second
  //    Let your app do its thing
  //    Run the DOWNs from the second script, then the DOWNs from the first
  //* To see an example of this in action we can create a 2.sql script with some extra test data inside and re run the above evolutions test.
}
