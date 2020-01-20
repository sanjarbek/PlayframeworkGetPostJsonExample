package controllers

import java.nio.file.Paths
import java.util.UUID

import javax.inject._
import models.SchoolClass
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import services.SchoolClassService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents,
                              schoolClassService: SchoolClassService,
                              ) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getExcelImportForm = Action {
    Ok(views.html.upload("Your new application is ready."))
  }

  def handleExcelImportForm = Action(parse.multipartFormData) { implicit request =>
    request.body
      .file("file")
      .map { excelFile =>
        val filename    = Paths.get(excelFile.filename).getFileName
        val uuid = UUID.randomUUID().toString
        val tempDir = System.getProperty("java.io.tmpdir")
        val filePath = s"${tempDir}/${uuid}-${filename}"
        excelFile.ref.copyTo(Paths.get(filePath), replace = true)
        schoolClassService.importExcelFile(filePath)
        Ok("File uploaded")
      }
      .getOrElse {
        Redirect(routes.HomeController.index).flashing("error" -> "Missing file")
      }
  }

}
