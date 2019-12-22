package com.fym.lta.Excel;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.BuildingBao;
import com.fym.lta.DTO.BuildingDto;
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

public class BuildingExcel {

    /* identify user-dto, building-dto, building-bao objects
  * and initialize to null */
    private UserDto user_login = null;
    private BuildingDto building = null;
    private BuildingBao building_bao = null;

    /* identify list of building-dto objects and initialize to null */
    private List<BuildingDto> buildings = null;

    public BuildingExcel(UserDto user) {

        super();

        /* create objects from user-dto and set to user object */
        user = user_login;

        /* create object from building-bao and set to buiding bao object */
        building_bao = new BaoFactory().createBuildingBao(user);

    }

    /**Read buildings data from excel file*/
    public List<BuildingDto> read_data(String name) throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(name));
        XSSFSheet sheet = workbook.getSheetAt(0);

        /* get number of rows contain data */
        int noOfColumns = sheet.getPhysicalNumberOfRows();

        /* loop to get data of each building */
        for (int i = 1; i < noOfColumns; i++) {
            //get each building row
            XSSFRow row = sheet.getRow(i);

            /* create object of building-dto */
            building = new BuildingDto();

            /* read buiding id and set to object */
            building.setId((int) row.getCell(0).getNumericCellValue());

            /* read buiding code and set to object */
            building.setCode(row.getCell(1).getStringCellValue());

            /* read buiding description and set to object */
            building.setDescription(row.getCell(2).getStringCellValue());

            /* add building-dto object to the list */
            if (buildings == null)
                buildings = new ArrayList<>();

            buildings.add(building);

        }

        /* return list */
        return buildings;

    }

    /**
     * write building objects stored in database in excel sheet
     * @param filename
     * @param filepath
     * @param buildings
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void write(String filename, String filepath, List<BuildingDto> buildings) throws FileNotFoundException,
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
            //create first cell and set string to "ID"
            sheet.createRow(0)
                 .createCell(0)
                 .setCellValue("ID");

            //create second cell and set string to "Code"
            sheet.getRow(0)
                 .createCell(1)
                 .setCellValue("Code");

            //create third cell and set string to "Description"
            sheet.getRow(0)
                 .createCell(2)
                 .setCellValue("Description");

            //set cell styles
            sheet.getRow(0)
                 .getCell(0)
                 .setCellStyle(headerCellStyle);
            sheet.getRow(0)
                 .getCell(1)
                 .setCellStyle(headerCellStyle);
            sheet.getRow(0)
                 .getCell(2)
                 .setCellStyle(headerCellStyle);

            //loop to insert building data
            for (int i = 0; i < buildings.size() && buildings != null; i++) {

                // write building id
                sheet.createRow(i + 1)
                     .createCell(0)
                     .setCellValue(buildings.get(i).getId());

                // write building code
                sheet.getRow(i + 1)
                     .createCell(1)
                     .setCellValue(buildings.get(i).getCode());

                // write building description
                sheet.getRow(i + 1)
                     .createCell(2)
                     .setCellValue(buildings.get(i).getDescription());

                // set cells style
                sheet.getRow(i + 1)
                     .getCell(0)
                     .setCellStyle(headerCellStyle1);
                sheet.getRow(i + 1)
                     .getCell(1)
                     .setCellStyle(headerCellStyle1);
                sheet.getRow(i + 1)
                     .getCell(2)
                     .setCellStyle(headerCellStyle1);

            }

            // Resize all columns to fit the content size
            for (int i = 0; i < 3; i++) {
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
