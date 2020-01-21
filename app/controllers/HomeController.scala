package controllers

import java.nio.file.Paths
import java.util.UUID

import forms.SchoolClassForm
import javax.inject._
import play.api.mvc._
import services.SchoolClassService

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents,
                               schoolClassService: SchoolClassService,
                              ) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getExcelImportForm = Action { implicit request =>
    Ok(views.html.upload("Your new application is ready.", SchoolClassForm.uploadAdditionalInfoForm))
  }

  def handleExcelImportForm = Action(parse.multipartFormData) { implicit request =>
    request.body.file("file").map { excelFile =>
        val filename = Paths.get(excelFile.filename).getFileName
        SchoolClassForm.uploadAdditionalInfoForm.bindFromRequest().fold(
          formWithErrors => BadRequest(views.html.upload("File uploaded", formWithErrors)),
          form => {
            System.out.println(form)
            val uuid = UUID.randomUUID().toString
            val tempDir = System.getProperty("java.io.tmpdir")
            val filePath = s"${tempDir}/${uuid}-${filename}"
            excelFile.ref.copyTo(Paths.get(filePath), replace = true)
            schoolClassService.importExcelFile(filePath)
            Ok("File uploaded")
          }
        )
      }
      .getOrElse {
        Redirect(routes.HomeController.index).flashing("error" -> "Missing file")
      }
  }

}
