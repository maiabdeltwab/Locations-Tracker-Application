package com.fym.lta.Excel;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.LocationTypeBao;
import com.fym.lta.DTO.BuildingDto;
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

public class LocationTypeExcel {
    /* identify user-dto, location_type-dto, location_type-bao objects
    * and initialize to null */
    private UserDto user_login = null;
    private LocationTypeDto location_type = null;
    private LocationTypeBao location_type_bao = null;

    /* identify list of location_type-dto list and initialize to null */
    private List<LocationTypeDto> location_types = null;

    public LocationTypeExcel(UserDto user) {
        super();
        user = user_login;

        /* create object from location_type-bao and set to location type bao object */
        location_type_bao = new BaoFactory().createLocationTypeBao(user);
    }

    /**Read location types data from excel file*/
    public List<LocationTypeDto> read_data(String name) throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(name));
        XSSFSheet sheet = workbook.getSheetAt(0);

        /* get number of rows contain data */
        int noOfColumns = sheet.getPhysicalNumberOfRows();

        /* loop to get data of each location type */
        for (int i = 1; i < noOfColumns; i++) {
            //get each building row
            XSSFRow row = sheet.getRow(i);

            /* create object from location_type-dto */
            location_type = new LocationTypeDto();

            /* read location type id and set to object */
            location_type.setId((int) row.getCell(0).getNumericCellValue());

            /* read location type code and set to object */
            location_type.setCode(row.getCell(1).getStringCellValue());

            /* read location type description and set to object */
            location_type.setDescription(row.getCell(2).getStringCellValue());

            /* read location type color and set to object */
            location_type.setColor(row.getCell(3).getStringCellValue());

            if (location_types == null)
                location_types = new ArrayList<>();

            /* add location type object to location types list */
            location_types.add(location_type);

        }

        return location_types;

    }

    /**
     * write location type objects stored in DB in excel sheet
     * @param filename
     * @param filepath
     * @param locationTypes
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void write(String filename, String filepath,
                      List<LocationTypeDto> locationTypes) throws FileNotFoundException, IOException {
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

            //create third cell and set its string to "Description"
            sheet.getRow(0)
                 .createCell(2)
                 .setCellValue("Description");

            //create fourth cell and set its string to "Color"
            sheet.getRow(0)
                 .createCell(3)
                 .setCellValue("Color");

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

            //insert location type data
            for (int i = 0; i < locationTypes.size() && locationTypes != null; i++) {

                //create row
                //create first cell and set its content to location type id
                sheet.createRow(i + 1)
                     .createCell(0)
                     .setCellValue(locationTypes.get(i).getId());

                //create second cell and set its content to location type code
                sheet.getRow(i + 1)
                     .createCell(1)
                     .setCellValue(locationTypes.get(i).getCode());

                //create third cell and set its content to location type description
                sheet.getRow(i + 1)
                     .createCell(2)
                     .setCellValue(locationTypes.get(i).getDescription());

                //create fourth cell and set its content to location type color
                sheet.getRow(i + 1)
                     .createCell(3)
                     .setCellValue(locationTypes.get(i).getColor());

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
