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
//* At this point we note that in the above test we've had to insert set up data. Moreover unlike in TableSpec we haven't created the Team and People tables. This is because we ran TableSpec first so they're already made. If we dropped the database and ran RepositorySpec alone it would fail. This brings us nicley to Evolutions.
//* Evolutions gives you a way to tack and organise your database schemas as they grow over time and allows you to "set up" your database with the right tables and data each time. For example everytime our app starts evolutions can create all your tables for you and populate any tables which require enums.
//* To make Evolutions work all we need to do is provide a set up script. This needs to be written in SQL. In here we tell Evolutions what tables to create and what data to insert, we also tell it how to tear down the database once we're finished.
}
