package forms

import java.util.Date

import models.SchoolClass
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

//case class SchoolClassForm(
//                      id: Int,
//                      grade: Int,
//                      name: String,
//                      workbookLanguage: String,
//                      isOperational: Boolean,
//                      forSchool: Int,
//                      ruid: String,
//                      lastUpdateTime: Date,
//                      isDeleted: Boolean
//                      )
case class AdditionalInfo(name: String, age: Int)

object SchoolClassForm {
  val schoolClassForm = Form(
    tuple(
      "data" -> list(
        mapping(
        "id" -> number,
        "grade"  -> number,
        "name"  -> text,
        "workbookLanguage"  -> text,
        "isOperational"  -> boolean,
        "forSchool"  -> number,
        "ruid"  -> text,
        "lastUpdateTime"  -> date,
        "isDeleted"  -> boolean,
        )(SchoolClass.apply)(SchoolClass.unapply)
      ),
      "action" -> text
    )
  )

  val uploadAdditionalInfoForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "age" -> number
    )(AdditionalInfo.apply)(AdditionalInfo.unapply)
  )
}
