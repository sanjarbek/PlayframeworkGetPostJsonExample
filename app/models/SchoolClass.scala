package models

import java.time.LocalDateTime
import java.util.Date

import play.api.libs.json.{JsPath, Json, Reads}
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class SchoolClass(
                      id: Int,
                      grade: Int,
                      name: String,
                      workbookLanguage: String,
                      isOperational: Boolean,
                      forSchool: Int,
                      ruid: String,
                      lastUpdateTime: Date,
                      isDeleted: Boolean
                      )


object SchoolClass {
  implicit val format = Json.format[SchoolClass]

  implicit val datatablesWritesFormat = new Writes[SchoolClass] {
    def writes(schoolClass: SchoolClass) = {
      val jsObject = Json.toJson(schoolClass).asInstanceOf[JsObject]
      jsObject ++ Json.obj(
        "DT_RowId" -> schoolClass.id
      )
    }
  }

  implicit val customSchoolClassReads: Reads[SchoolClass] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "grade").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "workbookLanguage").read[String] and
    (JsPath \ "isOperational").read[Boolean] and
    (JsPath \ "forSchool").read[Int] and
    (JsPath \ "ruid").read[String] and
    (JsPath \ "lastUpdateTime").read[Date] and
    (JsPath \ "isDeleted").read[Boolean]
  )(SchoolClass.apply _ )

}
