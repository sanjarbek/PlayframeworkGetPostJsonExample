package services

import java.io.{File, FileInputStream, FileNotFoundException, IOException}
import java.util.concurrent.atomic.AtomicInteger

import dal.SchoolClassRep
import javax.inject._
import models.SchoolClass
import org.apache.poi.ss.usermodel.{Cell, CellType, Row, Sheet, Workbook}
import org.apache.poi.xssf.usermodel.XSSFWorkbook

@Singleton
class SchoolClassService @Inject() (
                                     schoolClassRep: SchoolClassRep,
                                   ) {

  def importExcelFile(fileName: String) = {
    try {
      val excelFile = new FileInputStream(new File(fileName));
      val workbook = new XSSFWorkbook(excelFile);
      val datatypeSheet= workbook.getSheetAt(0);
      val rowIterator = datatypeSheet.iterator
      rowIterator.next // this is a header row

      while (rowIterator.hasNext) {

        val currentRow: Row = rowIterator.next
        val schoolClass = SchoolClass(
          currentRow.getCell(0).getNumericCellValue.toInt,
          currentRow.getCell(1).getNumericCellValue.toInt,
          currentRow.getCell(2).getStringCellValue,
          currentRow.getCell(3).getStringCellValue,
          currentRow.getCell(4).getBooleanCellValue,
          currentRow.getCell(5).getNumericCellValue.toInt,
          currentRow.getCell(6).getStringCellValue,
          currentRow.getCell(7).getDateCellValue,
          currentRow.getCell(8).getBooleanCellValue
        )
        schoolClassRep.create(schoolClass)
      }
    } catch {
      case e: FileNotFoundException => e.printStackTrace()
      case e: IOException => e.printStackTrace()
    }
  }

}
