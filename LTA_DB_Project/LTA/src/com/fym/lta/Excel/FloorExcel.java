package com.fym.lta.Excel;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.FloorBao;
import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.FloorDto;
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

public class FloorExcel {
    /* identify user-dto, floor-dto, floor-bao objects
    * and initialize to null */
    private UserDto user_login = null;
    private FloorDto floor = null;
    private FloorBao floor_bao = null;

    /* identify list of floor-dto list and initialize to null */
    private List<FloorDto> floors = null;

    public FloorExcel(UserDto user) {
        super();
        user = user_login;

        /* create object from floor-bao and set to floor bao object */
        floor_bao = new BaoFactory().createFloorBao(user);
    }


    /**Read floors data from excel file*/
    public List<FloorDto> read_data(String name) throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(name));
        XSSFSheet sheet = workbook.getSheetAt(0);

        /* get number of rows contain data */
        int noOfColumns = sheet.getPhysicalNumberOfRows();

        /* loop to get data of each floor */
        for (int i = 1; i < noOfColumns; i++) {
            //get each floor row
            XSSFRow row = sheet.getRow(i);

            /* create object from floor-dto */
            floor = new FloorDto();

            /* read floor id and set to object */
            floor.setId((int) row.getCell(0).getNumericCellValue());

            floor.setBuild(new BuildingDto());

            /* read buiding code of floor and set to object */
            floor.getBuild().setCode(row.getCell(1).getStringCellValue());

            //setcode to the new floor
            floor.setCode(floor.getBuild().getCode() + "-" + Integer.toString(floor.getId()));

            //set description to the new floor
            if (floor.getId() == 1)
                floor.setDescription(Integer.toString(floor.getId()) + "st Floor");
            else if (floor.getId() == 2)
                floor.setDescription(Integer.toString(floor.getId()) + "nd Floor");
            else
                floor.setDescription(Integer.toString(floor.getId()) + "th Floor");


            if (floors == null)
                floors = new ArrayList<>();

            /* add florr object to list */
            floors.add(floor);

        }

        return floors;

    }

    /**
     * write floor objects stored in DB in excel sheet
     * @param filename
     * @param filepath
     * @param floors
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void write(String filename, String filepath, List<FloorDto> floors) throws FileNotFoundException,
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
                 .createCell(2)
                 .setCellValue("Code");

            //create third cell and set its content to "Description"
            sheet.getRow(0)
                 .createCell(3)
                 .setCellValue("Description");

            //create fourth cell and set its content to "Building"
            sheet.getRow(0)
                 .createCell(1)
                 .setCellValue("Building");

            // set cells' style
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

            //insert floor data
            for (int i = 0; i < floors.size() && floors != null; i++) {
                //create row
                //create first cell and set its content to floor id
                sheet.createRow(i + 1)
                     .createCell(0)
                     .setCellValue(floors.get(i).getId());

                //create second cell and set its content to building code of floor
                sheet.getRow(i + 1)
                     .createCell(1)
                     .setCellValue(floors.get(i)
                                         .getBuild()
                                         .getCode());

                //create third cell and set its content to floor code
                sheet.getRow(i + 1)
                     .createCell(2)
                     .setCellValue(floors.get(i).getCode());

                // create fourth cell and set its content to floor description
                sheet.getRow(i + 1)
                     .createCell(3)
                     .setCellValue(floors.get(i).getDescription());

                //set cells' style
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
