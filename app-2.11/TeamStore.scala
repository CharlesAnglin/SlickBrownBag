package repository

import javax.inject.Inject

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import tables.PeopleTable

import scala.concurrent.{ExecutionContext, Future}

class TeamStore @Inject()(@NamedDatabase("charlie") val dbConfigProvider: DatabaseConfigProvider)
  extends PeopleTable with HasDatabaseConfigProvider[JdbcProfile]


//Could just define the functions in here? See questions in text file
//{
//
//  lazy val dbConf = dbConfigProvider.get[JdbcProfile]
//
//  def insertEnrolment(string: String)(implicit ec: ExecutionContext): Future[Unit] = {
//    import dbConf._
//    import dbConf.profile.api._
//
//    dbConf.db.run(findAllPeopleQuery).map(_ => Unit)
//  }
//
//}
