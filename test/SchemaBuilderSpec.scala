import utils.SchemaBuilder

class SchemaBuilderSpec extends IntegrationTests {

  lazy val schemaBuilder = app.injector.instanceOf[SchemaBuilder]

  "schemaBuilder" must {
    "generate schema" in {
      schemaBuilder.printSchemas()
    }
  }

}
