import org.scalatest.{MustMatchers, WordSpec}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.db.DBApi
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.basic.{BasicProfile, DatabaseConfig}
import slick.dbio.{DBIOAction, NoStream}
import slick.jdbc.JdbcProfile

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Awaitable}

//* As an example of this in action we can run our new functions in some tests, here we make a trait to include the DB profile (recall that TeamTable specified it needed that) as well as some helpful functions we'll meet in a second.
trait IntegrationTests extends WordSpec with MustMatchers with GuiceOneAppPerSuite with HasDatabaseConfigProvider[JdbcProfile] {

//  private lazy val databaseApi = app.injector.instanceOf[DBApi]

  // ~~~  Each table expects the ConfigProvider to be inherited, play-slick will inject this for you automatically however we're doing tests so we insert our own.
  override protected lazy val dbConfigProvider: DatabaseConfigProvider =
  new DatabaseConfigProvider {
    override def get[P <: BasicProfile]: DatabaseConfig[P] = DatabaseConfigProvider.get[P]("charlie")
  }

  def await[T](awaitable: Awaitable[T], duration: Duration = Duration.Inf): T = Await.result(awaitable, duration)

  def awaitDatabase[R](a: DBIOAction[R, NoStream, Nothing]): R = await(dbConfig.db.run(a))

}
