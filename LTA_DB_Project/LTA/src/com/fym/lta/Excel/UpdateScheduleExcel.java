package com.fym.lta.Excel;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.ScheduleBao;
import com.fym.lta.BAO.SlotBao;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.UserDto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class UpdateScheduleExcel {

    /* identify user-dto, slot-bao objects
    * and initialize to null */
    private UserDto user;
    private String filename;
    private SlotBao slot_bao = null;

    /* create object os slot-dto */
    private SlotDto slot = new SlotDto();

    /* identify schedule-bao object and initialize to null */
    private ScheduleBao schedule_bao = null;


    public UpdateScheduleExcel(String filename, UserDto user_acc) {

        this.filename = filename;
        user = user_acc;
        
        /* create object from slot-bao and schedule-bao */
        slot_bao = new BaoFactory().createSlotBao(user);
        schedule_bao = new BaoFactory().createScheduleBao(user);
    }

    /**
     * update content of cell in excel sheet
     * @param rowIndex
     * @param cellIndex
     * @param value
     * @throws IOException
     */
    public void updateCell(int rowIndex, int cellIndex, String value) throws IOException {
        FileInputStream ExcelFileToRead = new FileInputStream(filename);
        HSSFWorkbook workbook = new HSSFWorkbook(ExcelFileToRead);
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFRow row;
        HSSFCell cell;

        // get row of sheet
        row = sheet.getRow(rowIndex);

        // get cell of sheet
        cell = row.getCell(cellIndex);

        // set the cell content
        cell.setCellValue(value);

        // close the file
        ExcelFileToRead.close();

        FileOutputStream fileOut = new FileOutputStream(filename);
        workbook.write(fileOut);
        fileOut.close();
    }

    /**
     * update the uploaded schedule file with the loaction assigned
     * to each slot
     * @param name
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void updateSheet(String name) throws FileNotFoundException, IOException {

        // get id of schedule by its path
        int scheduleId = schedule_bao.getScheduleID(name);

        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 4; c++) {

                FileInputStream fileIn = new FileInputStream(name);
                HSSFWorkbook workbook = new HSSFWorkbook(fileIn);
                HSSFSheet sheet = workbook.getSheetAt(0);
                HSSFRow row5 = sheet.getRow((r * 5) + 5);
                HSSFRow row8 = sheet.getRow((r * 5) + 8);

                // check course name cell is not empty
                if (!((row5.getCell((2 * c) + 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() ==
                       CellType.BLANK) || (row5.getCell((2 * c) + 1) == null) ||
                      (row5.getCell((2 * c) + 1).getCellType() == CellType.STRING && row5.getCell((2 * c) + 1)
                                                                                         .getStringCellValue()
                                                                                         .trim()
                                                                                         .isEmpty()))) {

                    if (row8.getCell(2 * c + 1).getCellType() == CellType.STRING) {
                        // form and set slot code
                        String[] day = row5.getCell(0)
                                           .getStringCellValue()
                                           .split("d", 2);
                        String code = "Sch" + scheduleId + "-" + day[0] + "-S" + ((c) + 1);
                        slot.setCode(code);

                        // get location name assigned to slot and write in excel if not null
                        String locName = slot_bao.getSlot(slot)
                                                 .getLocation()
                                                 .getName();
                        if (locName == null) {
                            row8.getCell((2 * c) + 1).setCellValue("PrefSpace");
                        } else {
                            fileIn.close();
                            updateCell((r * 5) + 8, (c * 2) + 1, locName);
                        }
                    }
                }
            }
        }
    }
}
