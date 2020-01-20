package dal

import java.util.Date

import anorm._
import javax.inject.{Inject, Singleton}
import models.SchoolClass
import play.api.db._

@Singleton
class SchoolClassRep @Inject() (db: Database) {

  private var incId = 5

  private var schoolClasses = List(
    SchoolClass(1, 1, "A", "EN", true, 1, "hello 1", new Date(), false),
    SchoolClass(2, 1, "B", "EN", true, 1, "hello 2", new Date(), true),
    SchoolClass(3, 1, "C", "EN", true, 1, "hello 3", new Date(), false),
    SchoolClass(4, 1, "D", "EN", true, 1, "hello 4", new Date(), false),
  )

  val schoolClassParser = Macro.namedParser[SchoolClass]

  def list = db.withConnection { implicit c =>
    SQL("select * from school_classes").as(schoolClassParser *)
  }

  def create(schoolClass: SchoolClass) = db.withConnection { implicit c =>
    val id: Option[Long] = SQL("insert into school_classes " +
      "(grade, name, workbook_language, is_operational, for_school, ruid, last_update_time, is_deleted) values " +
      "({grade}, {name}, {workbookLanguage}, {isOperational}, {forSchool}, {ruid}, {lastUpdateTime}, {isDeleted})")
      .on("grade" ->schoolClass.grade, "name" -> schoolClass.name,
        "workbookLanguage"->schoolClass.workbookLanguage, "isOperational"->schoolClass.isOperational,
        "forSchool"->schoolClass.forSchool, "ruid" -> schoolClass.ruid,
        "lastUpdateTime"->schoolClass.lastUpdateTime, "isDeleted"->schoolClass.isDeleted).executeInsert()
    schoolClass.copy(id=id.get.toInt)
  }

  def update(schoolClass: SchoolClass) = {
    schoolClasses = schoolClasses.map { item =>
      if (item.id == schoolClass.id) schoolClass
      else item
    }
  }

  def deleteById(id: Int) = {
    schoolClasses = schoolClasses.filterNot(item => item.id == id)
  }
}
