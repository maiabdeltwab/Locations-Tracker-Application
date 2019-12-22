package com.fym.lta.BAO;

import com.fym.lta.DTO.CourseDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface StaffBao {

    /**
     * view all staff members saved in database
     * no param
     * @return list of staff dto object
     */
    public abstract List<StaffDto> viewAll();

    /**
     * delete staff member from database selected by user in GUI
     * @param staff dto object
     * @return true if success - false if failed
     */
    public abstract boolean delete(StaffDto staff);

    /**
     * search for staff member in database by search word inserted by user in GUI
     * @param staff dto object
     * @return list of staff object that match
     */
    public abstract List<StaffDto> SearchFor(StaffDto staff);

    /**
     * insert new staff member in database from GUI
     * @param staff dto object
     * @param user dto object
     * @return true if done successfully - false if not
     */
    public abstract boolean add(StaffDto staff, UserDto user);

    /**
     * update staff object in database by data inserted from GUI
     * @param staff dto object
     * @param user dto object
     * @return true if updated successfully - false if not
     */
    public abstract boolean update(StaffDto staff, UserDto user);

    /**
     * view courses that staff work on
     * @param staff dto object
     * @return list of course dto objects
     */
    public abstract List<CourseDto> viewCourses(StaffDto staff);

    /**
     * view user account data of selected staff in gui
     * @param staff fto object
     * @return user account dto object
     */
    public abstract UserDto viewUser(StaffDto staff);

    /**
     * calculate max id of staff members in database
     * @return max id int
     */
    public abstract int calcMaxId();

  /**Get staff filterd by department or position or both
   * used for staff load panel
   * @param positiion and department
   * @return staff list */
  public abstract List<StaffDto> search_department_position(DepartmentDto department,
    String position);

}
