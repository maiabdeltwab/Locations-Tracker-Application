package com.fym.lta.Excel;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.StaffBao;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.RoleDto;
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

public class UserExcel {
    /* identify user-dto, user-bao objects
    * and initialize to null */
    private UserDto user_login = null;
    private UserDto user = null;
    private StaffBao user_bao = null;

    /* identify list of user-dto list and initialize to null */
    private List<UserDto> users = null;


    public UserExcel(UserDto user) {
        super();
        user = user_login;

        /* create object from user-bao and set to user bao object */
        user_bao = new BaoFactory().createStaffBao(user);

    }

    /**Read users data from excel file*/
    public List<UserDto> read_data(String name) throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(name));
        XSSFSheet sheet = workbook.getSheetAt(0);

        /* get number of rows contain data */
        int noOfColumns = sheet.getPhysicalNumberOfRows();

        /* loop to get data of each user */
        for (int i = 1; i < noOfColumns; i++) {
            //get each building row
            XSSFRow row = sheet.getRow(i);

            /* create object from user-dto */
            user = new UserDto();

            /* read user id and set to object */
            user.setId((int) row.getCell(0).getNumericCellValue());

            /* read username and set to object */
            user.setUsername(row.getCell(1).getStringCellValue());

            /* read user email and set to object */
            user.setEmail(row.getCell(2).getStringCellValue());
            user.setRole(new RoleDto());

            /* read role name of user and set to object */
            user.getRole().setName(row.getCell(3).getStringCellValue());

            /* set user password */
            user.setPassword("123456789");

            if (users == null)
                users = new ArrayList<>();

            /* add user object to users list */
            users.add(user);

        }

        return users;

    }

    /**
     * write user objects stored in DB in excel sheet
     * @param filename
     * @param filepath
     * @param users_
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void write(String filename, String filepath, List<UserDto> users_) throws FileNotFoundException,
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

            //create second cell and set its string to "Username"
            sheet.getRow(0)
                 .createCell(1)
                 .setCellValue("Username");

            //create third cell and set its string to "Email"
            sheet.getRow(0)
                 .createCell(2)
                 .setCellValue("Email");

            //create fourth cell and set its string to "Role"
            sheet.getRow(0)
                 .createCell(3)
                 .setCellValue("Role");

            //set cells' style
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

            //insert users data
            for (int i = 0; i < users_.size() && users_ != null; i++) {

                //create row
                //create first cell and set its content to user id
                sheet.createRow(i + 1)
                     .createCell(0)
                     .setCellValue(users_.get(i).getId());

                //create second cell and set its content to username
                sheet.getRow(i + 1)
                     .createCell(1)
                     .setCellValue(users_.get(i).getUsername());

                //create third cell and set its content to user email
                sheet.getRow(i + 1)
                     .createCell(2)
                     .setCellValue(users_.get(i).getEmail());

                //create fourth cell and set its content to role name of user
                sheet.getRow(i + 1)
                     .createCell(3)
                     .setCellValue(users_.get(i)
                                         .getRole()
                                         .getName());

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
