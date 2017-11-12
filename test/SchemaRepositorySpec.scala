import utils.SchemaBuilder

class SchemaRepositorySpec extends IntegrationTests {

  lazy val schemaBuilder = app.injector.instanceOf[SchemaBuilder]

  "getSchema" must {
    "generate the enrolments collection schema" in {
      schemaBuilder.printSchemas()
    }
  }

}
