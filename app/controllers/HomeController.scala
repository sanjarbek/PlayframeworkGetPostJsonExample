package controllers

import javax.inject._
import models.SchoolClass
import play.api.libs.json.{JsError, Json}
import play.api.mvc._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(Json.toJson("Your new application is ready."))
  }

  def update = Action.async(BodyParsers.parse.json) { implicit request =>
    import SchoolClass.customSchoolClassReads
    val jsonValidation = request.body.validate[SchoolClass]
    jsonValidation.fold(
      errors => {
        Future { BadRequest(Json.obj("status" -> "ParseError", "message" -> JsError.toJson(errors)))}
      },
      schoolClass => {
        Future { Ok(Json.obj("status" -> "Ok", "message" -> Json.toJson(schoolClass)))}
      }
    )
  }

}
