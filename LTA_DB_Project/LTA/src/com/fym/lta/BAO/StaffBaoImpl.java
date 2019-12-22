package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.StaffDao;
import com.fym.lta.DTO.CourseDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;


public class StaffBaoImpl implements StaffBao {

    private UserDto user_dto = null;
    private StaffDao dao = null;

    public StaffBaoImpl(UserDto user) {
        this.user_dto = user;
        dao = new DaoFactory().createStaffDao(user_dto);
    }

    /**
     * view all staff members in database
     * call view all method of dao layer to view from database
     * @return list of staff dto objects
     */
    public List<StaffDto> viewAll() {
        List<StaffDto> staff = null;
        try {
            staff = dao.viewAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }

    /**
     * delete selected staff from database
     * call delete method of dao layer
     * @param staff dto object
     * @return true if success - false if not
     */
    public boolean delete(StaffDto staff) {
        boolean deleteFlage = true;
        try {
            if (dao.isExist(staff))
                deleteFlage = dao.delete(staff);
            else
                deleteFlage = false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return deleteFlage;
    }

    /**
     * search for staff member in database
     * call search for method of dao layer
     * @param staff object dto
     * @return list of staff dto
     */
    public List<StaffDto> SearchFor(StaffDto staff) {
        List<StaffDto> staffs = null;
        try {
            staffs = dao.searchFor(staff);
            return staffs;
        } catch (Exception e) {
            e.printStackTrace();
            return staffs;
        }
    }

    /**
     * add new staff to database
     * check if not exists in database
     * call add method of dao layer
     * @param staff dto object
     * @param user dto object
     * @return true if success
     */
    public boolean add(StaffDto staff, UserDto user) {
        boolean saveFlage = true;
        try {
            if (dao.isExist(staff)) {
                saveFlage = false;
            } else {
                saveFlage = dao.createNew(staff, user_dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**
     * update staff in database
     * check if exists in database
     * call update method of dao layer
     * @param satff dto object
     * @param user dto object
     * @return true if successfully updated
     */
    public boolean update(StaffDto satff, UserDto user) {
        boolean saveFlage = true;
        try {
            if (dao.isExist(satff)) {
                saveFlage = dao.update(satff, user_dto);
            } else {
                saveFlage = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**
     * view course in database assigned to staff
     * call viewCourses method of dao layer
     * @param staff dto object
     * @return list of courses
     */
    public List<CourseDto> viewCourses(StaffDto staff) {
        List<CourseDto> courses = null;
        try {
            courses = dao.viewCourses(staff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * view user account data of staff
     * check if staff exists
     * call viewUser method of dao layer
     * @param staff dto object
     * @return user dto object
     */
    public UserDto viewUser(StaffDto staff) {
        UserDto user = null;
        try {
            if (dao.isExist(staff)) {
                user = dao.viewUserOfStaff(staff);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * claculate max id of staff members in database
     * call calc method of dao layer
     * @return max id int
     */
    public int calcMaxId() {
        int id = 0;
        try {
            id = dao.getMaxId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }


  /**Get staff filterd by department or position or both
   * used for staff load panel
   * @param positiion and department
   * @return staff list */
  public List<StaffDto> search_department_position(DepartmentDto department,
    String position)
  {
    List<StaffDto> staff = null;
    try
      {
        staff = dao.search_department_position(department, position);
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return staff;
  }
}


