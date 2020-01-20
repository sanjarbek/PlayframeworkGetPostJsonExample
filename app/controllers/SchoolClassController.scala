package controllers

import java.util.Date

import dal.SchoolClassRep
import javax.inject._
import models.SchoolClass
import play.api.Logger
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

  def create = Action.async { implicit request =>
    forms.SchoolClassForm.schoolClassForm.bindFromRequest.fold(
      errors => {
        Future { BadRequest(Json.toJson("errors"))}
      },
      tuple => {
        Future {
          val (schoolClasses, action) = tuple
          val newSchoolClass = SchoolClassRep.create(schoolClasses.head)
          Ok(Json.obj("status" -> "Ok", "message" -> Json.toJson(newSchoolClass)))
        }
      }
    )
  }

  def update = Action.async { implicit request =>
    forms.SchoolClassForm.schoolClassForm.bindFromRequest.fold(
      errors => {
        Future {
          Logger.logger.info(errors.toString)
          BadRequest(Json.toJson("errors"))
        }
      },
      tuple => {
        Future {
          val (schoolClasses, action) = tuple
          schoolClasses.foreach(SchoolClassRep.update(_))
          Ok(Json.obj("status" -> "Ok", "message" -> Json.toJson("Hello")))
        }
      }
    )
  }

}
