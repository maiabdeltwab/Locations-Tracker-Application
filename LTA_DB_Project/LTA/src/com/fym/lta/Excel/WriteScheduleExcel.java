package com.fym.lta.Excel;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.SlotBao;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.awt.Color;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteScheduleExcel {

    /* identify user-dto, slot-bao objects
    * and initialize to null */
    private UserDto user_login = null;
    private SlotBao slotBao = null;

    public WriteScheduleExcel(UserDto user) {

        super();
        user_login = user;

        /* create object from slot-bao and set to slot bao object */
        slotBao = new BaoFactory().createSlotBao(user_login);

    }

    /**
     * write schedule data in excel sheet
     * @param file_name
     * @param path
     * @param department
     * @param stage
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void writeExcel(String file_name, String path, DepartmentDto department,
                           StageDto stage) throws FileNotFoundException, IOException {

        List<SlotDto> slots = null;

        try {

            //create workbook to generate .xls file
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(file_name);


            // Create a Font for styling header cells
            Font headerFont = workbook.createFont();
            headerFont.setBold(false);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLACK.getIndex());


            Font headerFont2 = workbook.createFont();
            headerFont2.setBold(true);
            headerFont2.setFontHeightInPoints((short) 14);
            headerFont2.setColor(IndexedColors.DARK_BLUE.getIndex());

            Font headerFont1 = workbook.createFont();
            headerFont1.setBold(true);
            headerFont1.setFontHeightInPoints((short) 14);
            headerFont1.setColor(IndexedColors.DARK_BLUE.getIndex());

            CellStyle headerCellStyle3 = workbook.createCellStyle();
            headerCellStyle3.setFont(headerFont1);
            headerCellStyle3.setAlignment(HorizontalAlignment.LEFT);

            // Create a CellStyle with the font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont2);
            headerCellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            // Create a CellStyle with the font
            CellStyle headerCellStyle2 = workbook.createCellStyle();
            headerCellStyle2.setFont(headerFont);
            headerCellStyle2.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            headerCellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle2.setAlignment(HorizontalAlignment.LEFT);


            // Create a CellStyle with the font for day
            CellStyle headerCellStyle1 = workbook.createCellStyle();
            headerCellStyle1.setFont(headerFont);
            headerCellStyle1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            headerCellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle1.setAlignment(HorizontalAlignment.LEFT);


            // get arraylist of slots of schedule
            slots = new ArrayList<>();
            slots = slotBao.viewSlotsOfSchedule(stage, department);


            //create all rows
            for (int r = 0; r < 30; r++) {
                // Create a Row
                XSSFRow headerRow = sheet.createRow(r);

                // Create all cells in row and set their style
                for (int i = 0; i < 9; i++) {
                    Cell cell = headerRow.createCell(i);

                    if ((i == 0 && r > 2) || r == 3 || r == 4)
                        cell.setCellStyle(headerCellStyle);
                    else if (r > 4)
                        cell.setCellStyle(headerCellStyle2);
                    else if (r < 3)
                        cell.setCellStyle(headerCellStyle3);


                }

            }

            // create row and cell to set the schedule department
            sheet.getRow(0)
                 .getCell(0)
                 .setCellValue(department.getName());
            sheet.getRow(0)
                 .getCell(1)
                 .setCellValue(department.getCode());

            // create row and cell to set the academic year
            sheet.getRow(1)
                 .getCell(0)
                 .setCellValue("Academic year");
            sheet.getRow(1)
                 .getCell(1)
                 .setCellValue(stage.getNumber());


            // create row and cells to set the term of schedule
            sheet.getRow(2)
                 .getCell(0)
                 .setCellValue("Term");
            sheet.getRow(2)
                 .getCell(1)
                 .setCellValue(slots.get(0).getTerm());

            // create rows and cells to set time slots number and day
            sheet.getRow(3)
                 .getCell(0)
                 .setCellValue("Time slot");
            sheet.getRow(3)
                 .getCell(1)
                 .setCellValue("Slot 1");
            sheet.getRow(3)
                 .getCell(3)
                 .setCellValue("Slot 2");
            sheet.getRow(3)
                 .getCell(5)
                 .setCellValue("Slot 3");
            sheet.getRow(3)
                 .getCell(7)
                 .setCellValue("Slot 4");


            // ceate row and cells to set start and end time of slots
            sheet.getRow(4)
                 .getCell(1)
                 .setCellValue("F 09:00-T 10:20");
            sheet.getRow(4)
                 .getCell(3)
                 .setCellValue("F 10:30-T 12:00");
            sheet.getRow(4)
                 .getCell(5)
                 .setCellValue("F 12:20-T 01:50");
            sheet.getRow(4)
                 .getCell(7)
                 .setCellValue("F 2:00-T 03:30");


            //set days
            sheet.getRow(5)
                 .getCell(0)
                 .setCellValue("Sunday");
            sheet.getRow(10)
                 .getCell(0)
                 .setCellValue("Monday");
            sheet.getRow(15)
                 .getCell(0)
                 .setCellValue("Tuesday");
            sheet.getRow(20)
                 .getCell(0)
                 .setCellValue("Wednesday");
            sheet.getRow(25)
                 .getCell(0)
                 .setCellValue("Thursday");


            // Resize all columns to fit the content size
            for (int i = 0; i < 9; i++) {
                sheet.autoSizeColumn(i);
            }


            // loop to get slot of indexed day and time slot
            for (int i = 0; i < slots.size(); i++) {

                //define slot day
                int r = -1;

                if (slots.get(i)
                         .getDay()
                         .equalsIgnoreCase("Sunday"))
                    r = 5;
                else if (slots.get(i)
                              .getDay()
                              .equalsIgnoreCase("Monday"))
                    r = 10;
                else if (slots.get(i)
                              .getDay()
                              .equalsIgnoreCase("Tuesday"))
                    r = 15;
                else if (slots.get(i)
                              .getDay()
                              .equalsIgnoreCase("Wednesday"))
                    r = 20;
                else if (slots.get(i)
                              .getDay()
                              .equalsIgnoreCase("Thursday"))
                    r = 25;


                int cell = slots.get(i).getNum() * 2 - 1;

                // set style for cells
                if ((r % 2 == 0 && (cell == 1 || cell == 2 || cell == 5 || cell == 6)) ||
                    (r % 2 != 0 && (cell == 3 || cell == 4 || cell == 7 || cell == 8))) {

                    sheet.getRow(r)
                         .getCell(cell)
                         .setCellStyle(headerCellStyle1);
                    sheet.getRow(r)
                         .getCell(cell + 1)
                         .setCellStyle(headerCellStyle1);

                    sheet.getRow(r + 1)
                         .getCell(cell)
                         .setCellStyle(headerCellStyle1);
                    sheet.getRow(r + 1)
                         .getCell(cell + 1)
                         .setCellStyle(headerCellStyle1);

                    sheet.getRow(r + 2)
                         .getCell(cell)
                         .setCellStyle(headerCellStyle1);
                    sheet.getRow(r + 2)
                         .getCell(cell + 1)
                         .setCellStyle(headerCellStyle1);

                    sheet.getRow(r + 3)
                         .getCell(cell)
                         .setCellStyle(headerCellStyle1);
                    sheet.getRow(r + 3)
                         .getCell(cell + 1)
                         .setCellStyle(headerCellStyle1);

                    sheet.getRow(r + 4)
                         .getCell(cell)
                         .setCellStyle(headerCellStyle1);
                    sheet.getRow(r + 4)
                         .getCell(cell + 1)
                         .setCellStyle(headerCellStyle1);
                }

                // set course name and code of slot
                sheet.getRow(r)
                     .getCell(cell)
                     .setCellValue(slots.get(i)
                                        .getCourse()
                                        .getName());
                sheet.getRow(r)
                     .getCell(cell + 1)
                     .setCellValue(slots.get(i)
                                        .getCourse()
                                        .getCode());

                // set type of slot
                sheet.getRow(r + 2)
                     .getCell(cell)
                     .setCellValue(slots.get(i).getSlot_type());

                // set location of slot
                sheet.getRow(r + 3)
                     .getCell(cell)
                     .setCellValue(slots.get(i)
                                        .getLocation()
                                        .getName());


                // check slot type then set plt of slot
                if (slots.get(i)
                         .getSlot_type()
                         .equals("LECTURE")) {
                    sheet.getRow(r + 3)
                         .getCell(cell + 1)
                         .setCellValue(slots.get(i)
                                            .getCourse()
                                            .getPlt_lecture()
                                            .getCode());
                }
                if (slots.get(i)
                         .getSlot_type()
                         .equals("SECTION")) {
                    sheet.getRow(r + 3)
                         .getCell(cell + 1)
                         .setCellValue(slots.get(i)
                                            .getCourse()
                                            .getPlt_section()
                                            .getCode());
                }


                // set student number of slot
                sheet.getRow(r + 4)
                     .getCell(cell)
                     .setCellValue("Student number");
                sheet.getRow(r + 4)
                     .getCell(cell + 1)
                     .setCellValue(slots.get(i).getStudent_number());

                /*
               * set staff of slot then check if members > 1
               * then concatenate all members' names
                     * and set to cell */
                String staff = slots.get(i)
                                    .getStaff()
                                    .get(0)
                                    .getPosition() + "/" + slots.get(i)
                                                                .getStaff()
                                                                .get(0)
                                                                .getName();

                /*
              * set staff user email of slot then check if members > 1
              * then concatenate all members' emails
                   * and set to cell */
                String[] email = slots.get(i)
                                      .getStaff()
                                      .get(0)
                                      .getUser()
                                      .getEmail()
                                      .split("@", 2);
                String user = email[0];


                for (int j = 1; j < slots.get(i)
                                         .getStaff()
                                         .size(); j++) {
                    staff = staff + " # " + slots.get(i)
                                                 .getStaff()
                                                 .get(j)
                                                 .getPosition() + "/" + slots.get(i)
                                                                             .getStaff()
                                                                             .get(j)
                                                                             .getName();

                    String[] _email = slots.get(i)
                                           .getStaff()
                                           .get(j)
                                           .getUser()
                                           .getEmail()
                                           .split("@", 2);
                    user = user + " # " + _email[0];
                }

                sheet.getRow(r + 1)
                     .getCell(cell)
                     .setCellValue(staff);
                sheet.getRow(r + 1)
                     .getCell(cell + 1)
                     .setCellValue(user);

            }


            // write data to the file

            FileOutputStream fileOut = new FileOutputStream(path + "\\" + file_name);
            workbook.write(fileOut);
            fileOut.close();


            // Closing the workbook
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
