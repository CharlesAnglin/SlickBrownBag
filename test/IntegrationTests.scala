import org.scalatest.{MustMatchers, WordSpec}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.db.DBApi
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.basic.{BasicProfile, DatabaseConfig}
import slick.dbio.{DBIOAction, NoStream}
import slick.jdbc.JdbcProfile

import scala.concurrent.{Await, Awaitable}
import scala.concurrent.duration.Duration

trait IntegrationTests extends WordSpec with MustMatchers with GuiceOneAppPerSuite with HasDatabaseConfigProvider[JdbcProfile] {

  private lazy val databaseApi = app.injector.instanceOf[DBApi]

  //What exactly is this doing?
  override protected lazy val dbConfigProvider: DatabaseConfigProvider =
    new DatabaseConfigProvider {
      override def get[P <: BasicProfile]: DatabaseConfig[P] = DatabaseConfigProvider.get[P]("charlie")
    }

  def await[T](awaitable: Awaitable[T], duration: Duration = Duration.Inf): T = Await.result(awaitable, duration)

  def awaitDatabase[R](a: DBIOAction[R, NoStream, Nothing]): R = await(dbConfig.db.run(a))





}
