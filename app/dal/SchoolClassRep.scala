package dal

import java.util.Date

import models.SchoolClass

object SchoolClassRep {

  private var incId = 5

  private var schoolClasses = List(
    SchoolClass(1, 1, "A", "EN", true, 1, "hello 1", new Date(), false),
    SchoolClass(2, 1, "B", "EN", true, 1, "hello 2", new Date(), true),
    SchoolClass(3, 1, "C", "EN", true, 1, "hello 3", new Date(), false),
    SchoolClass(4, 1, "D", "EN", true, 1, "hello 4", new Date(), false),
  )

  def list = schoolClasses

  def create(schoolClass: SchoolClass) = {
    val newSchoolClass = schoolClass.copy(id = incId)
    incId += 1
    schoolClasses = newSchoolClass :: schoolClasses
    newSchoolClass
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
