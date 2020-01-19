package controllers

import java.util.Date

import dal.SchoolClassRep
import javax.inject._
import models.SchoolClass
import play.api.libs.json.{JsError, Json}
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class SchoolClassController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    import SchoolClass.datatablesWritesFormat
    val ls = SchoolClassRep.list
    Ok(Json.obj(
      "data" -> Json.toJson(ls)
    ))
  }

  def create = Action.async(BodyParsers.parse.json) { implicit request =>
    import SchoolClass.customSchoolClassReads
    val jsonValidation = request.body.validate[SchoolClass]
    jsonValidation.fold(
      errors => {
        Future { BadRequest(Json.obj("status" -> "ParseError", "message" -> JsError.toJson(errors)))}
      },
      schoolClass => {
        Future {
          val newSchoolClass = SchoolClassRep.create(schoolClass)
          Ok(Json.obj("status" -> "Ok", "message" -> Json.toJson(newSchoolClass)))
        }
      }
    )
  }

  def update = Action.async(BodyParsers.parse.json) { implicit request =>
    import SchoolClass.customSchoolClassReads
    val jsonValidation = request.body.validate[List[SchoolClass]]
    jsonValidation.fold(
      errors => {
        Future { BadRequest(Json.obj("status" -> "ParseError", "message" -> JsError.toJson(errors)))}
      },
      schoolClassesList => {
        Future {
          schoolClassesList.foreach(SchoolClassRep.update(_))
          Ok(Json.obj("status" -> "Ok", "message" -> Json.toJson("Hello")))
        }
      }
    )
  }

}
