package com.fym.lta.Excel;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.DepartmentBao;
import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.DepartmentDto;
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

public class DepartmentExcel {
    /* identify user-dto, department-dto, department-bao objects
    * and initialize to null */
    private UserDto user_login = null;
    private DepartmentDto department = null;
    private DepartmentBao department_bao = null;

    /* identify list of department-dto list and initialize to null */
    private List<DepartmentDto> departs = null;

    public DepartmentExcel(UserDto user) {
        super();
        user = user_login;

        /* create object from department-bao and set to department bao object */
        department_bao = new BaoFactory().createDepartmentBao(user);
    }

    /**
     * read data from department excel sheet
     * @param name of excel sheet
     * @return list of department objects that have been read from sheet
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<DepartmentDto> read_data(String name) throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(name));
        XSSFSheet sheet = workbook.getSheetAt(0);

        /* get number of rows contain data */
        int noOfColumns = sheet.getPhysicalNumberOfRows();

        /* loop to get data of each department */
        for (int i = 1; i < noOfColumns; i++) {
            //get each department row
            XSSFRow row = sheet.getRow(i);

            /* create object from department-dto */
            department = new DepartmentDto();

            /* read department id and set to object */
            department.setId((int) row.getCell(0).getNumericCellValue());

            /* read department code and set to object */
            department.setCode(row.getCell(1).getStringCellValue());

            /* read department name and set to object */
            department.setName(row.getCell(2).getStringCellValue());

            /* read buildings of department */
            String[] builds = row.getCell(3)
                                 .getStringCellValue()
                                 .split(",");

            /* identify list of building-dto objects and initialize to null */
            List<BuildingDto> buildings = null;

            /* identify list of building-dto object and initialize to null */
            BuildingDto building = null;

            /* loop to set buildings to department object */
            for (int j = 0; j < builds.length; j++) {
                if (buildings == null)
                    buildings = new ArrayList<>();

                /* create object of building-dto */
                building = new BuildingDto();

                /* set code to building object */
                building.setCode(builds[j].trim());

                /* add building object to buildings list */
                buildings.add(building);
                building = null;
            }

            /* set buildings to department object */
            department.setBuildings(buildings);

            if (departs == null)
                departs = new ArrayList<>();

            /* add department object to departments list */
            departs.add(department);

        }

        return departs;

    }


    /**
     * write departments stored in data base in excel sheet
     * @param filename
     * @param filepath
     * @param departments
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void write(String filename, String filepath, List<DepartmentDto> departments) throws FileNotFoundException,
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

            //create fourth cell and set its content to "Buildings"
            sheet.getRow(0)
                 .createCell(3)
                 .setCellValue("Buildings");

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

            //insert department data
            for (int i = 0; i < departments.size() && departments != null; i++) {


                //create first row
                // create first cell and set its content to department id
                sheet.createRow(i + 1)
                     .createCell(0)
                     .setCellValue(departments.get(i).getId());

                // create second cell and set its content to department code
                sheet.getRow(i + 1)
                     .createCell(1)
                     .setCellValue(departments.get(i).getCode());

                // create third cell and set its content to department name
                sheet.getRow(i + 1)
                     .createCell(2)
                     .setCellValue(departments.get(i).getName());

                /* get buildings of department */
                String buildings = departments.get(i)
                                              .getBuildings()
                                              .get(0)
                                              .getCode();
                for (int j = 1; j < departments.get(i)
                                               .getBuildings()
                                               .size(); j++) {
                    buildings += ", " + departments.get(i)
                                                   .getBuildings()
                                                   .get(j)
                                                   .getCode();

                }

                /* create cell and set its content to department buildings */
                sheet.getRow(i + 1)
                     .createCell(3)
                     .setCellValue(buildings);

                /* set cells style */
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

            }

            // Resize all columns to fit the content size
            for (int i = 0; i < 4; i++) {
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
