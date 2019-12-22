package com.fym.lta.Excel;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.BuildingBao;
import com.fym.lta.BAO.StaffBao;
import com.fym.lta.DTO.BuildingDto;

import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.StaffDto;
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

public class StaffExcel {

    /* identify user-dto, staff-dto, staff-bao objects
    * and initialize to null */
    private UserDto user_login = null;
    private StaffDto staff = null;
    private StaffBao staff_bao = null;

    /* identify list of staff-dto list and initialize to null */
    private List<StaffDto> staffs = null;


    public StaffExcel(UserDto user) {
        super();
        user = user_login;

        /* create object from staff-bao and set to staff bao object */
        staff_bao = new BaoFactory().createStaffBao(user);
    }

    /**Read staff data from excel file*/
    public List<StaffDto> read_data(String name) throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(name));
        XSSFSheet sheet = workbook.getSheetAt(0);

        /* get number of rows contain data */
        int noOfColumns = sheet.getPhysicalNumberOfRows();

        /* loop to get data of each staff */
        for (int i = 1; i < noOfColumns; i++) {
            //get each staff row
            XSSFRow row = sheet.getRow(i);

            /* create object from staff-dto */
            staff = new StaffDto();

            /* read staff id and set to object */
            staff.setId((int) row.getCell(0).getNumericCellValue());

            /* read staff name and set to object */
            staff.setName(row.getCell(1).getStringCellValue());

            /* read staff position and set to object */
            staff.setPosition(row.getCell(2).getStringCellValue());
            staff.setDepartment(new DepartmentDto());

            /* read department name of staff and set to object */
            staff.getDepartment().setName(row.getCell(3).getStringCellValue());

            if (staffs == null)
                staffs = new ArrayList<>();

            /* add staff object to staff list */
            staffs.add(staff);

        }

        return staffs;

    }

    /**
     * write staff member objects stored in DB in excel sheet
     * @param filename
     * @param filepath
     * @param staffs_
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void write(String filename, String filepath, List<StaffDto> staffs_) throws FileNotFoundException,
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

            //create second cell and set its string to "Name"
            sheet.getRow(0)
                 .createCell(1)
                 .setCellValue("Name");

            //create third cell and set its string to "Position"
            sheet.getRow(0)
                 .createCell(2)
                 .setCellValue("Position");

            //create fourth cell and set its string to "Department"
            sheet.getRow(0)
                 .createCell(3)
                 .setCellValue("Department");

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

            //insert staff data
            for (int i = 0; i < staffs_.size() && staffs_ != null; i++) {

                //create row
                //create first cell and set its content to staff id
                sheet.createRow(i + 1)
                     .createCell(0)
                     .setCellValue(staffs_.get(i).getId());

                //create second cell and set its content to staff name
                sheet.getRow(i + 1)
                     .createCell(1)
                     .setCellValue(staffs_.get(i).getName());

                //create third cell and set its content to staff position
                sheet.getRow(i + 1)
                     .createCell(2)
                     .setCellValue(staffs_.get(i).getPosition());

                //create fourth cell and set its content to department name of staff
                sheet.getRow(i + 1)
                     .createCell(3)
                     .setCellValue(staffs_.get(i)
                                          .getDepartment()
                                          .getName());

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
