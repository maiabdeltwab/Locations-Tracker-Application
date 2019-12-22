
package com.fym.lta.Excel;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.CourseBao;
import com.fym.lta.DTO.CourseDto;
import com.fym.lta.DTO.CourseDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.UserDto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CourseExcel {

    /* identify user-dto, course-dto, course-bao objects
    * and initialize to null */
    private UserDto user_login = null;
    private CourseDto course = null;
    private CourseBao course_bao = null;

    /* identify list of course-dto objects and initialize to null */
    private List<CourseDto> courses = null;


    public CourseExcel(UserDto user) {
        super();

        /* create objects from user-dto and set to user object */
        user = user_login;

        /* create object from course-bao and set to course bao object */
        course_bao = new BaoFactory().createCourseBao(user);
    }


    /**Read coursess data from excel file*/
    public List<CourseDto> read_data(String name) throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(name));
        XSSFSheet sheet = workbook.getSheetAt(0);

        /* get number of rows contain data */
        int noOfColumns = sheet.getPhysicalNumberOfRows();

        /* loop to get data of each course */
        for (int i = 1; i < noOfColumns; i++) {
            //get each course row
            XSSFRow row = sheet.getRow(i);

            /* create object of course-dto */
            course = new CourseDto();

            /* read course id and set to object */
            course.setId((int) row.getCell(0).getNumericCellValue());

            /* read course code and set to object */
            course.setCode(row.getCell(1).getStringCellValue());

            /* read course name and set to object */
            course.setName(row.getCell(2).getStringCellValue());
          
            /* read course term and set to object */
            course.setTerm(row.getCell(3).getStringCellValue());

            /* read course number of hours and set to object */
            course.setLec_time((float) row.getCell(4).getNumericCellValue());
            course.setSec_time((float) row.getCell(5).getNumericCellValue());
            
            course.setPlt_lecture(new LocationTypeDto());
            /* read course prefered location type for lecture and set to object */
            course.getPlt_lecture().setCode(row.getCell(6).getStringCellValue());
            course.setPlt_section(new LocationTypeDto());

            /* read course prefered location type for section and set to object */
            course.getPlt_section().setCode(row.getCell(7).getStringCellValue());

            /* read departments of course*/
            String[] departs = row.getCell(8)
                                  .getStringCellValue()
                                  .split(",");

            /* identify list of department-dto objects and set to null*/
            List<DepartmentDto> departments = null;

            /* idenify department-dto object and set to null*/
            DepartmentDto depart = null;

            /* loop to assign departments to course */
            for (int j = 0; j < departs.length; j++) {
                if (departments == null)
                    departments = new ArrayList<>();

                /* create object of department-dto */
                depart = new DepartmentDto();

                /* read department code and set to department object */
                depart.setCode(departs[j].trim());

                /* add department-dto object to department list */
                departments.add(depart);
                depart = null;
            }

            /* set departments list to course object */
            course.setDeparts(departments);

            if (courses == null)
                courses = new ArrayList<>();

            /* add course object to list */
            courses.add(course);

        }
        /* return courses list */
        return courses;

    }

    /**
     * write course objects stored in database to excel sheet
     * @param filename
     * @param filepath
     * @param courses
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void write(String filename, String filepath, List<CourseDto> courses) throws FileNotFoundException,
                                                                                        IOException {
        try {

            //create workbook to generate .xls file
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(filename);

            // Create a Font for styling header cells
            Font headerFont = workbook.createFont();
            headerFont.setBold(false);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLACK.getIndex());

            // Create a CellStyle with the font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.LEFT);

            // Create a CellStyle with the font for day
            CellStyle headerCellStyle1 = workbook.createCellStyle();
            headerCellStyle1.setFont(headerFont);

            //create first row
            //create first cell and set its string to "ID"
            sheet.createRow(0)
                 .createCell(0)
                 .setCellValue("ID");

            //create second cell and set its string to "Code"
            sheet.getRow(0)
                 .createCell(1)
                 .setCellValue("Code");

            //create third cell and set its content to "Name"
            sheet.getRow(0)
                 .createCell(2)
                 .setCellValue("Name");

            //create fourth cell and set its content to "Lec time"
            sheet.getRow(0)
                 .createCell(3)
                 .setCellValue("Lec time");

            //create fifth cell and set its content to "PLT Lecture"
            sheet.getRow(0)
                 .createCell(4)
                 .setCellValue("PLT Lecture");

            //create sixth cell and set its content to "PLT section"
            sheet.getRow(0)
                 .createCell(5)
                 .setCellValue("PLT Section");

            //create seventh cell and set its content to "Department"
            sheet.getRow(0)
                 .createCell(6)
                 .setCellValue("Department");

            //set cells style
            sheet.getRow(0)
                 .getCell(0)
                 .setCellStyle(headerCellStyle);
            sheet.getRow(0)
                 .getCell(1)
                 .setCellStyle(headerCellStyle);
            sheet.getRow(0)
                 .getCell(2)
                 .setCellStyle(headerCellStyle);
            sheet.getRow(0)
                 .getCell(3)
                 .setCellStyle(headerCellStyle);
            sheet.getRow(0)
                 .getCell(4)
                 .setCellStyle(headerCellStyle);
            sheet.getRow(0)
                 .getCell(5)
                 .setCellStyle(headerCellStyle);
            sheet.getRow(0)
                 .getCell(6)
                 .setCellStyle(headerCellStyle);

            //loop to insert course data
            for (int i = 0; i < courses.size() && courses != null; i++) {

                //create first row
                // create first cell and set its content to course id
                sheet.createRow(i + 1)
                     .createCell(0)
                     .setCellValue(courses.get(i).getId());

                // create second cell and set its content to course code
                sheet.getRow(i + 1)
                     .createCell(1)
                     .setCellValue(courses.get(i).getCode());

                // create third cell and set its content to course name
                sheet.getRow(i + 1)
                     .createCell(2)
                     .setCellValue(courses.get(i).getName());
              
                // create 4th cell and set its content to course number of hours
                 sheet.getRow(i+
                 1).createCell(5).setCellValue(courses.get(i).getTerm());
              
                // create 5th cell and set its content to course number of hours
                sheet.getRow(i + 1)
                     .createCell(4)
                     .setCellValue(courses.get(i).getLec_time());

                // create 6th cell and set its content to course number of hours
                sheet.getRow(i+
                1).createCell(5).setCellValue(courses.get(i).getSec_time());

                // create 7th cell and set its content to course plt lec
                sheet.getRow(i + 1)
                     .createCell(6)
                     .setCellValue(courses.get(i)
                                          .getPlt_lecture()
                                          .getCode());

                // create 8th cell and set its content to course plt sec
                sheet.getRow(i + 1)
                     .createCell(7)
                     .setCellValue(courses.get(i)
                                          .getPlt_section()
                                          .getCode());

                // create 9th cell and set its content to course departments
                String departs = courses.get(i)
                                        .getDeparts()
                                        .get(0)
                                        .getCode();
                for (int j = 1; j < courses.get(i)
                                           .getDeparts()
                                           .size(); j++) {
                    departs += ", " + courses.get(i)
                                             .getDeparts()
                                             .get(j)
                                             .getCode();

                }

                sheet.getRow(i + 1)
                     .createCell(8)
                     .setCellValue(departs);

                // set cell style
                sheet.getRow(i + 1)
                     .getCell(0)
                     .setCellStyle(headerCellStyle1);
                sheet.getRow(i + 1)
                     .getCell(1)
                     .setCellStyle(headerCellStyle1);
                sheet.getRow(i + 1)
                     .getCell(2)
                     .setCellStyle(headerCellStyle1);
                sheet.getRow(i + 1)
                     .getCell(3)
                     .setCellStyle(headerCellStyle1);
                sheet.getRow(i + 1)
                     .getCell(4)
                     .setCellStyle(headerCellStyle1);
                sheet.getRow(i + 1)
                     .getCell(5)
                     .setCellStyle(headerCellStyle1);
                sheet.getRow(i+1)
                     .getCell(6)
                     .setCellStyle(headerCellStyle1);
                sheet.getRow(i+1)
                     .getCell(7)
                     .setCellStyle(headerCellStyle1);
                sheet.getRow(i+1)
                     .getCell(8)
                     .setCellStyle(headerCellStyle1);

            }

            // Resize all columns to fit the content size
            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn(i);
            }

            // write data to the file

            FileOutputStream fileOut = new FileOutputStream(filepath + "\\" + filename);
            workbook.write(fileOut);
            fileOut.close();


            // Closing the workbook
            workbook.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
