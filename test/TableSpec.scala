import tables.{PeopleTable, TeamTable}

class TableSpec extends IntegrationTests with PeopleTable {

  import dbConfig.profile.api._

//  * We first have to create our tables in the DB, "db.run" executes things against the DB, more on that in a second.
  val createTables = (team.schema ++ people.schema).create
  db.run(createTables)

  "team table" must {
    "insert a team into the DB" in {
//      * The functions we've defined return DBIOActions (specifically SQLActions which is a subset). DBIOActions can be treated just like futures.
      val DBIOAction = createTeamReturningIDQuery("TAVC", false)
//      * At this point nothing has actually been run against the database, we've just specified what we want to do. Using "db.run" executes out DBIOAction against the DB and "transforms" it into a future.
      val future = db.run(DBIOAction)
      await(future)

//      * Now we can use our other function to check what's in the DB, note the use of the helpful function defined in the IntegrationTest trait.
      val teams = awaitDatabase(findAllTeamsQuery)
      println(teams)
    }
  }

}
