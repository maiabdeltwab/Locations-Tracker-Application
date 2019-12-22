package com.fym.lta.Excel;


import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.CourseBao;
import com.fym.lta.BAO.DepartmentBao;
import com.fym.lta.BAO.ScheduleBao;
import com.fym.lta.BAO.ScheduleSlotBao;
import com.fym.lta.BAO.SlotBao;
import com.fym.lta.BAO.StaffBao;
import com.fym.lta.BAO.StaffCourseBao;
import com.fym.lta.BAO.StaffUserBao;
import com.fym.lta.BAO.StageBao;
import com.fym.lta.BAO.StageScheduleBao;
import com.fym.lta.BAO.UserBao;
import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.CourseDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.RoleDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.ScheduleSlotDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.StaffSlotDto;
import com.fym.lta.DTO.StaffUserDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.StageScheduleDto;
import com.fym.lta.DTO.UserDto;
import com.fym.lta.DTO.WorkOnDto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadScheduleExcel {

    private UserDto user_login = null;
    private DepartmentBao departmentBao = null;
    private DepartmentDto department = new DepartmentDto();
    private CourseDto course = new CourseDto();
    private CourseBao coursebao = null;
    private StaffDto staff1 = new StaffDto();
    private StaffDto staff2 = null;
    private StaffDto staff3 = null;
    private StaffBao staffbao = null;
    private UserDto user1 = null;
    private UserDto user2 = null;
    private UserDto user3 = null;
    private UserBao userbao = null;
    private StaffUserBao staffAccountBao = null;
    private StaffUserDto staffAccount1 = new StaffUserDto();
    private StaffUserDto staffAccount2 = null;
    private StaffUserDto staffAccount3 = null;
    private StaffCourseBao staffCourseBao = null;
    private WorkOnDto staffCours1 = new WorkOnDto();
    private WorkOnDto staffCours2 = null;
    private WorkOnDto staffCours3 = null;
    private StaffSlotDto staffSlot = new StaffSlotDto();
    private SlotBao slotBao = null;
    private SlotDto slot = new SlotDto();
    private StageBao stageBao = null;
    private StageDto stage = new StageDto();
    private ScheduleBao scheduleBao = null;
    private ScheduleDto schedule = new ScheduleDto();
    private ScheduleSlotDto scheduleSlot = new ScheduleSlotDto();
    private ScheduleSlotBao scheduleSlotBao = null;
    private List<BuildingDto> buildings = new ArrayList<>();
    private StageScheduleDto stageSchedule = new StageScheduleDto();
    private StageScheduleBao stageScheduleBao = null;

    private List<SlotDto> slots = new ArrayList<>();

    private int[] studentNumbers = new int[20];
    private int i = 0;
    private String[] prefSpace = new String[20];
    private String defaultSapce;


    public ReadScheduleExcel(UserDto user) {
        super();
        user_login = user;

        departmentBao = new BaoFactory().createDepartmentBao(user_login);
        coursebao = new BaoFactory().createCourseBao(user_login);
        staffbao = new BaoFactory().createStaffBao(user_login);
        userbao = new BaoFactory().createUserBao(user_login);
        staffAccountBao = new BaoFactory().createStaffUserBao(user_login);
        staffCourseBao = new BaoFactory().createStaffCourseBao(user_login);
        slotBao = new BaoFactory().createSlotBao(user_login);
        stageBao = new BaoFactory().createStageBao(user_login);
        scheduleBao = new BaoFactory().createScheduleBao(user_login);
        scheduleSlotBao = new BaoFactory().createScheduleSlotBao(user_login);
        stageScheduleBao = new BaoFactory().createStageScheduleBao(user_login);

    }


    public String getDefaultSapce() {
        return defaultSapce;
    }

    public List<SlotDto> getSlots() {
        return slots;
    }


    ////* check if cell is empty *////
    public static boolean isCellEmpty(final XSSFCell cell) {
        try {
            if (cell == null) { // use row.getCell(x, Row.CREATE_NULL_AS_BLANK) to avoid null cells
                return true;
            }

            if (cell.getCellType() == CellType.BLANK) {
                return true;
            }

            if (cell.getCellType() == CellType.STRING && cell.getStringCellValue()
                                                             .trim()
                                                             .isEmpty()) {
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    ////* check if schedule data exists *////
    ////* data to be checked is department name , department code, academic year and term *////
    ///* return true if data exists *////
    public boolean checkSchedule(String name) throws FileNotFoundException, IOException {
        try {

            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(name));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row0 = sheet.getRow(0);
            for (int c = 0; c < 4; c++) {
                if (isCellEmpty(row0.getCell(c)))
                    return false;
            }
            XSSFRow row1 = sheet.getRow(1);
            if (isCellEmpty(row1.getCell(0)) || isCellEmpty(row1.getCell(1)))
                return false;
            XSSFRow row2 = sheet.getRow(2);
            if (isCellEmpty(row2.getCell(0)) || isCellEmpty(row2.getCell(1)))
                return false;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    ////* read scedule data and save in database *////
    public boolean getScheduleData(String name, UserDto user) throws FileNotFoundException, IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(name));
        XSSFSheet sheet = workbook.getSheetAt(0);

        // read department code and name then save in database
        XSSFRow row = sheet.getRow(0);
        if (row.getCell(0).getCellType() == CellType.STRING) {
            department.setName(row.getCell(0).getStringCellValue());
        }
        if (row.getCell(1).getCellType() == CellType.STRING) {
            department.setCode(row.getCell(1).getStringCellValue());
            int dep_id = departmentBao.getDepartmentId(department);
            department.setId(dep_id);

            // get buildings of the department
            buildings = departmentBao.viewBuilding(department);
        }


        // read schedule term an save in database
        XSSFRow row2 = sheet.getRow(2);
        if (row2.getCell(1).getCellType() == CellType.NUMERIC) {
            schedule.setTerm((int) row2.getCell(1).getNumericCellValue());
        }

        // save the schedule file path on users device
        schedule.setFile_path(name);

        // if schedule exists in database then get its id
        if (scheduleBao.isExist(schedule)) {
            int scheduleId = scheduleBao.getScheduleID(name);
            schedule.setId(scheduleId);
        }

        // if nit exist then generate id then add to database
        else {
            // generate id to schedule
            schedule.setId((scheduleBao.calcMaxId()) + 1);
            scheduleBao.add(schedule, user);
        }

        //stage
        XSSFRow row1 = sheet.getRow(1);

        // read satge number
        if (row1.getCell(1).getCellType() == CellType.STRING) {
            stage.setNumber(row1.getCell(1).getStringCellValue());
        }
        // set stage code then save to database if it does not exist
        String stageCode = department.getCode() + "-" + stage.getNumber();
        stage.setCode(stageCode);
        stage.setDepartment(department);
        stage.setNum_of_std(50);
        if (!(stageBao.isExist(stage))) {
            stageBao.add(stage, user);
        }

        // set the assignment of schedule to stage if it is nit assigned before
        stageSchedule.setStage_code(stageCode);
        stageSchedule.setSchedule_id(schedule.getId());

        if (stageScheduleBao.isExist(stageSchedule)) {
            return false;
        }

        //if(!(stageScheduleBao.isExist(stageSchedule))){

        else {
            stageScheduleBao.add(stageSchedule);
        }
        return true;
    }


    //* method to read all slots in schedule *//

    public void getSlots(String name, UserDto user) throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(name));
        XSSFSheet sheet = workbook.getSheetAt(0);

        // for loop to read slots
        for (int r = 0; r < 5; r++) {

            XSSFRow row5 = sheet.getRow((r * 5) + 5);

            // read and set day of each slot
            if (!((row5.getCell(0).getCellType() == CellType.BLANK))) {
                if (row5.getCell(0).getCellType() == CellType.STRING) {
                    slot.setDay(row5.getCell(0).getStringCellValue());
                }
            }

            for (int c = 0; c < 4; c++) {

                //XSSFRow row3 = sheet.getRow(3);
                XSSFRow row4 = sheet.getRow(4);
                XSSFRow row6 = sheet.getRow((r * 5) + 6);
                XSSFRow row7 = sheet.getRow((r * 5) + 7);
                XSSFRow row8 = sheet.getRow((r * 5) + 8);
                XSSFRow row9 = sheet.getRow((r * 5) + 9);


                int slotNumber = 1;
                if (((2 * c) + 1) == 1) {
                    slotNumber = 1;
                    String time = row4.getCell(1).getStringCellValue();
                    String[] start_end_time = time.split("[ -]+");
                    slot.setStart_time(start_end_time[1]);
                    slot.setEnd_time(start_end_time[3]);
                }
                if (((2 * c) + 1) == 3) {
                    slotNumber = 2;
                    String time = row4.getCell(3).getStringCellValue();
                    String[] start_end_time = time.split("[ -]+");
                    slot.setStart_time(start_end_time[1]);
                    slot.setEnd_time(start_end_time[3]);
                }
                if (((2 * c) + 1) == 5) {
                    slotNumber = 3;
                    String time = row4.getCell(5).getStringCellValue();
                    String[] start_end_time = time.split("[ -]+");
                    slot.setStart_time(start_end_time[1]);
                    slot.setEnd_time(start_end_time[3]);
                }
                if (((2 * c) + 1) == 7) {
                    slotNumber = 4;
                    String time = row4.getCell(7).getStringCellValue();
                    String[] start_end_time = time.split("[ -]+");
                    slot.setStart_time(start_end_time[1]);
                    slot.setEnd_time(start_end_time[3]);
                }
                slot.setNum(slotNumber);
                String[] day = slot.getDay().split("d", 2);
                String code = ("Sch" + schedule.getId() + "-" + day[0] + "-S" + slot.getNum());
                slot.setCode(code);


                if (!((row5.getCell((2 * c) + 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() ==
                       CellType.BLANK) || (row5.getCell((2 * c) + 1) == null) ||
                      (row5.getCell((2 * c) + 1).getCellType() == CellType.STRING && row5.getCell((2 * c) + 1)
                                                                                         .getStringCellValue()
                                                                                         .trim()
                                                                                         .isEmpty()))) {
                    // read course of slot
                    if (row5.getCell((2 * c) + 1).getCellType() == CellType.STRING) {

                        // read and set course name
                        course.setName(row5.getCell(2 * c + 1).getStringCellValue());

                        if (row5.getCell((2 * c) + 2).getCellType() == CellType.STRING) {

                            //read and set course code
                            course.setCode(row5.getCell((2 * c) + 2).getStringCellValue());

                            // get course id from database if it exists
                            if (coursebao.isExist(course)) {

                                course.setId(coursebao.getId(course));

                                //get existing course from db
                                course = coursebao.getCourse(course);

                            }


                            // read and set the type of slot lecture/section
                            // set course prefered location type for lecture and section

                            // in case of lecture slot
                            if (row7.getCell((2 * c) + 1).getCellType() == CellType.STRING) {
                                if (row7.getCell((2 * c) + 1)
                                        .getStringCellValue()
                                        .equals("LECTURE")) {
                                    slot.setSlot_type("LECTURE");

                                    if (row8.getCell((2 * c) + 2).getCellType() == CellType.STRING) {
                                        LocationTypeDto location = new LocationTypeDto();
                                        location.setCode(row8.getCell((2 * c) + 2).getStringCellValue());
                                        prefSpace[i] = row8.getCell((2 * c) + 2).getStringCellValue();
                                        course.setPlt_lecture(location);
                                        if (course.getPlt_section() == null) {
                                            course.setPlt_section(location);
                                        }
                                    }
                                }

                                // in case of section slot
                                if (row7.getCell((2 * c) + 1)
                                        .getStringCellValue()
                                        .equals("SECTION")) {
                                    slot.setSlot_type("SECTION");

                                    if (row8.getCell((2 * c) + 2).getCellType() == CellType.STRING) {
                                        LocationTypeDto location = new LocationTypeDto();
                                        location.setCode(row8.getCell((2 * c) + 2).getStringCellValue());
                                        prefSpace[i] = row8.getCell((2 * c) + 2).getStringCellValue();

                                        course.setPlt_section(location);
                                        if (course.getPlt_lecture() == null) {
                                            course.setPlt_lecture(location);
                                        }
                                    }
                                }

                                // get course id from database if it exists
                                if (coursebao.isExist(course)) {
                                    coursebao.update(course, user);
                                }

                                // generate id to course if it does not exist then add to database
                                if (!coursebao.isExist(course)) {
                                    int course_id = coursebao.calcMaxId() + 1;
                                    course.setId(course_id);
                                    // set course duration
                                    float duration = (float) 1.5;
                                course.setLec_time(duration);
                                course.setSec_time(duration);
                                coursebao.add(course, user);
                                }

                                // set course of the slot
                                slot.setCourse(course);
                            }
                        }

                        // read data of first staff in slot ( there may exist many staff members for one slot )
                        if (row6.getCell((2 * c) + 1).getCellType() == CellType.STRING) {

                            // get all staff members names and split to get name of each
                            String cell_value = row6.getCell((2 * c) + 1).getStringCellValue();
                            String[] members = cell_value.split("#", 3);

                            String[] name_pos1 = members[0].split("/", 2);

                            // set name of fisr staff member
                            staff1.setName(name_pos1[1]);

                            // check the staff position then set it
                            if (name_pos1[0].equals("DR")) {
                                staff1.setPosition("Demonstrator");
                                staffCours1.setWork_hrs(3);
                            }

                            else if (name_pos1[0].equals("ASSOC.PROF")) {
                                staff1.setPosition("Associate Professor");
                                staffCours1.setWork_hrs(3);
                            }

                            else if (name_pos1[0].equals("PROF")) {
                                staff1.setPosition("Professor");
                                staffCours1.setWork_hrs(3);
                            }

                            else if (name_pos1[0].equals("ENG")) {
                                staff1.setPosition("Assistant lecturer");
                                staffCours1.setWork_hrs(2);
                            }

                            else {
                                staff1.setPosition("Assistant Professor");
                                staffCours1.setWork_hrs(3);
                            }

                            // in case of two members then set name and position for the second as the fisrt one
                            if (members.length > 1) {
                                String[] name_pos2 = members[1].split("/", 2);

                                staff2 = new StaffDto();
                                staff2.setName(name_pos2[1]);

                                staffCours2 = new WorkOnDto();

                                if (name_pos2[0].equals("DR")) {
                                    staff2.setPosition("Demonstrator");
                                    staffCours2.setWork_hrs(3);
                                }

                                else if (name_pos2[0].equals("ASSOC.PROF")) {
                                    staff2.setPosition("Associate Professor");
                                    staffCours2.setWork_hrs(3);
                                }

                                else if (name_pos2[0].equals("PROF")) {
                                    staff2.setPosition("Professor");
                                    staffCours2.setWork_hrs(3);
                                }

                                else if (name_pos2[0].equals("ENG")) {
                                    staff2.setPosition("Assistant lecturer");
                                    staffCours2.setWork_hrs(2);
                                }

                                else {
                                    staff2.setPosition("Assistant Professor");
                                    staffCours2.setWork_hrs(3);
                                }
                            }

                            // in case of three members then set name and position for the third as well as the first
                            if (members.length > 2) {
                                String[] name_pos3 = members[2].split("/", 2);

                                staff3 = new StaffDto();
                                staff3.setName(name_pos3[1]);

                                staffCours3 = new WorkOnDto();

                                if (name_pos3[0].equals("DR")) {
                                    staff3.setPosition("Demonstrator");
                                    staffCours3.setWork_hrs(3);
                                }

                                else if (name_pos3[0].equals("ASSOC.PROF")) {
                                    staff3.setPosition("Associate Professor");
                                    staffCours3.setWork_hrs(3);
                                }

                                else if (name_pos3[0].equals("PROF")) {
                                    staff3.setPosition("Professor");
                                    staffCours3.setWork_hrs(3);
                                }

                                else if (name_pos3[0].equals("ENG")) {
                                    staff3.setPosition("Assistant lecturer");
                                    staffCours3.setWork_hrs(2);
                                }

                                else {
                                    staff3.setPosition("Assistant Professor");
                                    staffCours3.setWork_hrs(3);
                                }

                            }


                            // assign course to department if it is not assigned before
                            // assign staff members to department
                            if (!coursebao.isAssigned(course, department)) {
                                coursebao.assignDepCourse(course, department);
                                staff1.setDepartment(department);

                                if (staff2 != null)
                                    staff2.setDepartment(department);

                                if (staff3 != null)
                                    staff3.setDepartment(department);
                            }

                            // if course is assigned
                            // get departments assigned to course and assign staff to department
                            else {
                                List<DepartmentDto> departs = coursebao.viewDepsOfCourse(course);
                                for (int i = 0; i < departs.size(); i++) {
                                    if (departs.get(i).getId() == department.getId()) {
                                        staff1.setDepartment(department);

                                        if (staff2 != null)
                                            staff2.setDepartment(department);

                                        if (staff3 != null)
                                            staff3.setDepartment(department);
                                        break;
                                    }

                                    else {
                                        staff1.setDepartment(departs.get(0));

                                        if (staff2 != null)
                                            staff2.setDepartment(departs.get(0));

                                        if (staff3 != null)
                                            staff3.setDepartment(departs.get(0));
                                    }
                                }
                            }
                        }

                        // read user eamils for staff members
                        if (row6.getCell((2 * c) + 2).getCellType() == CellType.STRING) {

                            String cell_content = row6.getCell((2 * c) + 2).getStringCellValue();

                            // get email of each user
                            String[] users = cell_content.split("#", 3);
                            String email1 = users[0].trim() + "@fayoum.edu.eg";

                            // set email for fisrt user
                            user1 = new UserDto();
                            user1.setEmail(email1);

                            // get user id if it exists in data base
                            if (userbao.isExist(user1)) {
                                user1.setId(userbao.getId(user1));
                            }

                            // if user does not exist in database then set id , role , username and password
                            // then add user to database
                            else {
                                user1.setId((userbao.calcMaxId()) + 1);
                                RoleDto role1 = new RoleDto();
                                role1.setId(2);
                                role1.setName("staff_member");
                                user1.setRole(role1);

                                String[] staffName1 = staff1.getName().split(" ", 3);
                                String username1 = staffName1[0].toLowerCase() + users[0].trim();
                                user1.setUsername(username1);

                                String password1 = username1 + "123";
                                user1.setPassword(password1);

                                userbao.add(user1, user);
                            }

                            // if there exist more than one user
                            if (users.length > 1) {
                                user2 = new UserDto();

                                // set email to user
                                String email2 = users[1].trim() + "@fayoum.edu.eg";
                                user2.setEmail(email2);

                                // get its id if it is saved in database
                                if (userbao.isExist(user2)) {
                                    user2.setId(userbao.getId(user2));
                                }

                                // set id , role , username and password then add to database if it does not exist
                                else {
                                    user2.setId((userbao.calcMaxId()) + 1);
                                    RoleDto role2 = new RoleDto();
                                    role2.setId(2);
                                    role2.setName("staff_member");

                                    String[] staffName2 = staff2.getName().split(" ", 3);
                                    String username2 = staffName2[0].toLowerCase() + users[1].trim();
                                    user2.setUsername(username2);

                                    String password2 = username2 + "123";
                                    user2.setPassword(password2);

                                    user2.setRole(role2);
                                    userbao.add(user2, user);
                                }
                            }

                            // set third user ( if exists ) as the first one
                            if (users.length > 2) {
                                user3 = new UserDto();
                                String email3 = users[2].trim() + "@fayoum.edu.eg";
                                user3.setEmail(email3);

                                if (userbao.isExist(user3)) {
                                    user3.setId(userbao.getId(user3));
                                } else {
                                    user3.setId((userbao.calcMaxId()) + 1);
                                    RoleDto role3 = new RoleDto();
                                    role3.setId(2);
                                    role3.setName("staff_member");
                                    user3.setRole(role3);

                                    String[] staffName3 = staff3.getName().split(" ", 3);
                                    String username3 = staffName3[0].toLowerCase() + users[2].trim();
                                    user3.setUsername(username3);

                                    String password3 = username3 + "123";
                                    user3.setPassword(password3);

                                    userbao.add(user3, user);
                                }
                            }

                            //assignment of first user to first staff

                            staffAccount1.setUser_id(user1.getId());

                            // if user is previosly assigned then get the staff id
                            if (staffAccountBao.isExist(staffAccount1)) {
                                int staffId = staffAccountBao.getStaffId(staffAccount1);
                                staff1.setId(staffId);

                                // assignment of staff to course if not assigned
                                staffCours1.setCourse_id(course.getId());
                                staffCours1.setStaff_id(staff1.getId());

                                if (!(staffCourseBao.isExist(staffCours1))) {
                                    staffCourseBao.add(staffCours1);
                                }
                            }

                            //in case user is not assigned before to staff
                            else {

                                // generate id to satff then save to database
                                staff1.setId((staffbao.calcMaxId()) + 1);
                                // staff1.setDepartment(department);
                                staffbao.add(staff1, user);

                                // assign staff to the user acount and add to database
                                staffAccount1.setStaff(staff1);
                                staffAccountBao.insert(staffAccount1);

                                // assign staff to course and add to database
                                staffCours1.setCourse_id(course.getId());
                                staffCours1.setStaff_id(staff1.getId());
                                staffCourseBao.add(staffCours1);

                            }


                            //assignment of second user to second staff
                            if (user2 != null && staff2 != null) {
                                staffAccount2 = new StaffUserDto();
                                staffAccount2.setUser_id(user2.getId());

                                if (staffAccountBao.isExist(staffAccount2)) {
                                    staff2.setId(staffAccountBao.getStaffId(staffAccount2));

                                    staffCours2.setCourse_id(course.getId());
                                    staffCours2.setStaff_id(staff2.getId());

                                    if (!(staffCourseBao.isExist(staffCours2))) {
                                        staffCourseBao.add(staffCours2);
                                    }
                                } else {

                                    staff2.setId((staffbao.calcMaxId()) + 1);
                                    // staff2.setDepartment(department);
                                    staffbao.add(staff2, user);

                                    staffAccount2.setStaff(staff2);
                                    staffAccountBao.insert(staffAccount2);

                                    staffCours2.setCourse_id(course.getId());
                                    staffCours2.setStaff_id(staff2.getId());
                                    staffCourseBao.add(staffCours2);

                                }

                            }


                            ////assignment of third user to third staff
                            if (user3 != null && staff3 != null) {
                                staffAccount3 = new StaffUserDto();
                                staffAccount3.setUser_id(user3.getId());
                                if (staffAccountBao.isExist(staffAccount3)) {
                                    staff3.setId(staffAccountBao.getStaffId(staffAccount3));
                                    staffCours3.setCourse_id(course.getId());
                                    staffCours3.setStaff_id(staff3.getId());
                                    if (!(staffCourseBao.isExist(staffCours3))) {
                                        staffCourseBao.add(staffCours3);
                                    }
                                } else {
                                    staff3.setId((staffbao.calcMaxId()) + 1);
                                    staffbao.add(staff3, user);

                                    staffAccount3.setStaff(staff3);

                                    staffAccountBao.insert(staffAccount3);

                                    staffCours3.setCourse_id(course.getId());
                                    staffCours3.setStaff_id(staff3.getId());
                                    staffCourseBao.add(staffCours3);
                                }

                            }
                        }


                        //read and set the student number of slot
                        if (row9.getCell((2 * c) + 2).getCellType() == CellType.NUMERIC) {

                            slot.setStudent_number((int) row9.getCell((2 * c) + 2).getNumericCellValue());
                            studentNumbers[i] = (int) row9.getCell((2 * c) + 2).getNumericCellValue();
                        }

                        i++;

                        // add slot to database
                        slot.setLocation(null);
                        if (!slotBao.isExist(slot))
                            slotBao.add(slot, user);

                        // add slot to the list of slots
                        slots.add(slot);

                        // assign staff members to slot
                        staffSlot.setSlot(slot);

                        /* first staff member */
                        staffSlot.setStaff(staff1);
                        if (!slotBao.isAssigned(staffSlot))
                            slotBao.assignStaff(staffSlot);

                        /* second staff member if exists */
                        if (staff2 != null) {
                            staffSlot.setStaff(staff2);
                            if (!slotBao.isAssigned(staffSlot))
                                slotBao.assignStaff(staffSlot);
                        }

                        /* third staff member if exsits */
                        if (staff3 != null) {
                            staffSlot.setStaff(staff3);
                            if (!slotBao.isAssigned(staffSlot))
                                slotBao.assignStaff(staffSlot);
                        }

                        // assign slot to schedule and add to database
                        scheduleSlot.setSchedule_id(schedule.getId());
                        scheduleSlot.setSlot_code(slot.getCode());
                        scheduleSlotBao.insert(scheduleSlot);

                    }

                }


                staff2 = null;
                staff3 = null;
                user2 = null;
                user3 = null;
                staffAccount2 = null;
                staffAccount3 = null;
                staffCours2 = null;
                staffCours3 = null;


            }

        }

    }

    public void setStageStudentNumber(UserDto user) {
        /*
    Map<Integer,Integer> hashMap = new HashMap<Integer,Integer>();
    for(int i =0 ; i < studentNumbers.length ; i++){
        int key = studentNumbers[i];
        if(hashMap.containsKey(key)){
            int frequent = hashMap.get(key);
            frequent++;
            hashMap.put(key,frequent);
            }
        else{
            hashMap.put(key,1);
            }
        }
    int max_count=0 , res = -1 ;
    for( Entry<Integer,Integer> value : hashMap.entrySet() ){

        if(max_count<value.getValue()){
            res=value.getKey();
            max_count=value.getValue();
        }
    }
*/
        int element = 0;
        int count = 0;

        for (int j = 0; j < studentNumbers.length; j++) {
            if (studentNumbers[j] == 0)
                continue;
            else {
                int tempElement = studentNumbers[j];
                int tempCount = 0;
                for (int k = 0; k < studentNumbers.length; k++)
                    if (studentNumbers[k] == 0)
                        continue;
                    else {
                        if (studentNumbers[k] == tempElement)
                            tempCount++;
                        if (tempCount > count) {
                            element = tempElement;
                            count = tempCount;
                        }
                    }
            }
        }

        // System.out.println("res= "+res);
        /* set the most frequent number as the stage number */
        stage.setNum_of_std(element);
        stageBao.update(stage, user);

    }


    public void setStageDefaultSpace(UserDto user) {

        String element = null;
        int count = 0;

        for (int j = 0; prefSpace[j] != null && j < prefSpace.length; j++) {
            String tempElement = prefSpace[j];
            int tempCount = 0;
            for (int k = 0; prefSpace[k] != null && k < prefSpace.length; k++)
                if (prefSpace[k].equals(tempElement))
                    tempCount++;
            if (tempCount > count) {
                element = tempElement;
                count = tempCount;
            }
        }

        defaultSapce = element;
        LocationTypeDto defLoc = new LocationTypeDto();
        defLoc.setCode(defaultSapce);
        schedule.setDefault_location_type(defLoc);
        scheduleBao.update(schedule, user);

    }

}


