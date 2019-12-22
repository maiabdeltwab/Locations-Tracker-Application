package com.fym.lta.Excel;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.EmployeeBao;
import com.fym.lta.DTO.EmployeeDto;
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

public class EmployeeExcel {

    /* identify user-dto, employee-dto, employee-bao objects
    * and initialize to null */
    private UserDto user_login = null;
    private EmployeeDto employee = null;
    private EmployeeBao employee_bao = null;

    /* identify list of employee-dto list and initialize to null */
    private List<EmployeeDto> employees = null;

    public EmployeeExcel(UserDto user) {
        super();
        user = user_login;

        /* create object from employee-bao and set to employee bao object */

        employee_bao = new BaoFactory().createEmployeeBao(user);
    }

    /**
     * read employees data from excel sheet
     * @param name
     * @return list of employee-dto objects
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<EmployeeDto> read_data(String name) throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(name));
        XSSFSheet sheet = workbook.getSheetAt(0);

        /* get number of rows contain data */
        int noOfColumns = sheet.getPhysicalNumberOfRows();

        /* loop to get data of each employee */
        for (int i = 1; i < noOfColumns; i++) {
            //get each employee row
            XSSFRow row = sheet.getRow(i);

            /* create object from employee-dto */
            employee = new EmployeeDto();

            /* read employee id and set to object */
            employee.setId((int) row.getCell(0).getNumericCellValue());

            /* read employee name and set to object */
            employee.setName(row.getCell(1).getStringCellValue());

            /* read employee job and set to object */
            employee.setJob(row.getCell(2).getStringCellValue());

            if (employees == null)
                employees = new ArrayList<>();

            /* add employee object to list */
            employees.add(employee);

        }

        return employees;

    }

    /**
     * write employees objects stored in DB in excel sheet
     * @param filename
     * @param filepath
     * @param employees
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void write(String filename, String filepath, List<EmployeeDto> employees) throws FileNotFoundException,
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

            //create second cell and set its string to "Description"
            sheet.getRow(0)
                 .createCell(2)
                 .setCellValue("Description");
            sheet.getRow(0)
                 .getCell(0)
                 .setCellStyle(headerCellStyle);
            sheet.getRow(0)
                 .getCell(1)
                 .setCellStyle(headerCellStyle);
            sheet.getRow(0)
                 .getCell(2)
                 .setCellStyle(headerCellStyle);

            //insert employee data
            for (int i = 0; i < employees.size() && employees != null; i++) {

                // create first cell and set its content to employee id
                sheet.createRow(i + 1)
                     .createCell(0)
                     .setCellValue(employees.get(i).getId());

                // create second cell and set its content to employee name
                sheet.getRow(i + 1)
                     .createCell(1)
                     .setCellValue(employees.get(i).getName());

                // create third cell and set its content to employee job
                sheet.getRow(i + 1)
                     .createCell(2)
                     .setCellValue(employees.get(i).getJob());

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
