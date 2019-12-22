package com.fym.lta.Excel;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.BuildingBao;
import com.fym.lta.BAO.LocationBao;
import com.fym.lta.DTO.BuildingDto;

import com.fym.lta.DTO.FloorDto;
import com.fym.lta.DTO.LocationDto;
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

public class LocationExcel {
    /* identify user-dto, location-dto, location-bao objects
    * and initialize to null */
    private UserDto user_login = null;
    private LocationDto location = null;
    private LocationBao location_bao = null;

    /* identify list of location-dto list and initialize to null */
    private List<LocationDto> locations = null;


    public LocationExcel(UserDto user) {
        super();
        user = user_login;

        /* create object from location-bao and set to location bao object */
        location_bao = new BaoFactory().createLocationBao(user);

    }

    /**Read locations data from excel file*/
    public List<LocationDto> read_data(String name) throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(name));
        XSSFSheet sheet = workbook.getSheetAt(0);

        /* get number of rows contain data */
        int noOfColumns = sheet.getPhysicalNumberOfRows();

        /* loop to get data of each location */
        for (int i = 1; i < noOfColumns; i++) {
            //get each location row
            XSSFRow row = sheet.getRow(i);

            /* create object from location-dto */
            location = new LocationDto();

            /* read location id and set to object */
            location.setId((int) row.getCell(0).getNumericCellValue());

            /* read location code and set to object */
            location.setCode(row.getCell(1).getStringCellValue());

            /* read location name and set to object */
            location.setName(row.getCell(2).getStringCellValue());

            /* read location capacity and set to object */
            location.setCapacity((int) row.getCell(3).getNumericCellValue());
            location.setBuild(new BuildingDto());

            /* read building code of location and set to object */
            location.getBuild().setCode(row.getCell(5).getStringCellValue());
            location.setFloor(new FloorDto());

            /* read floor code of location and set to object */
            location.getFloor().setCode(row.getCell(4).getStringCellValue());
            location.setType(new LocationTypeDto());

            /* read location type code of location and set to object */
            location.getType().setCode(row.getCell(6).getStringCellValue());

            if (locations == null)
                locations = new ArrayList<>();

            /* add location object to locations list */
            locations.add(location);

        }

        return locations;

    }

    /**
     * write location objects dtored in DB in excel sheet
     * @param filename
     * @param filepath
     * @param locations
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void write(String filename, String filepath, List<LocationDto> locations) throws FileNotFoundException,
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

            //create third cell and set its string to "Name"
            sheet.getRow(0)
                 .createCell(2)
                 .setCellValue("Name");

            //create fourth cell and set its string to "Capacity"
            sheet.getRow(0)
                 .createCell(3)
                 .setCellValue("Capacity");

            //create fifth cell and set its string to "Floor"
            sheet.getRow(0)
                 .createCell(4)
                 .setCellValue("Floor");

            //create sixth cell and set its string to "Building"
            sheet.getRow(0)
                 .createCell(5)
                 .setCellValue("Building");

            //create seventh cell and set its string to "Type"
            sheet.getRow(0)
                 .createCell(6)
                 .setCellValue("Type");

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
            sheet.getRow(0)
                 .getCell(4)
                 .setCellStyle(headerCellStyle);
            sheet.getRow(0)
                 .getCell(6)
                 .setCellStyle(headerCellStyle);
            sheet.getRow(0)
                 .getCell(5)
                 .setCellStyle(headerCellStyle);

            //insert locations data
            for (int i = 0; i < locations.size() && locations != null; i++) {

                //create row
                //create first cell and set its content to location id
                sheet.createRow(i + 1)
                     .createCell(0)
                     .setCellValue(locations.get(i).getId());

                //create second cell and set its content to location code
                sheet.getRow(i + 1)
                     .createCell(1)
                     .setCellValue(locations.get(i).getCode());

                //create third cell and set its content to location name
                sheet.getRow(i + 1)
                     .createCell(2)
                     .setCellValue(locations.get(i).getName());

                //create fourth cell and set its content to location capacity
                sheet.getRow(i + 1)
                     .createCell(3)
                     .setCellValue(locations.get(i).getCapacity());

                //create fifth cell and set its content to floor code of location
                sheet.getRow(i + 1)
                     .createCell(4)
                     .setCellValue(locations.get(i)
                                            .getFloor()
                                            .getCode());

                //create sixth cell and set its content to building code of location
                sheet.getRow(i + 1)
                     .createCell(5)
                     .setCellValue(locations.get(i)
                                            .getBuild()
                                            .getCode());

                //create seventh cell and set its content to location type code of location
                sheet.getRow(i + 1)
                     .createCell(6)
                     .setCellValue(locations.get(i)
                                            .getType()
                                            .getCode());

                // set cells' style
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
                sheet.getRow(i + 1)
                     .getCell(6)
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
