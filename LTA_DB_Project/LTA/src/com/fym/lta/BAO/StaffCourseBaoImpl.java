package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.StaffCourseDao;
import com.fym.lta.DTO.UserDto;
import com.fym.lta.DTO.WorkOnDto;

import java.util.List;


public class StaffCourseBaoImpl implements StaffCourseBao {
    private UserDto user = null;
    private StaffCourseDao dao = null;


    public StaffCourseBaoImpl(UserDto user) {
        this.user = user;
        dao = new DaoFactory().createStaffCourseDao(user);
    }


    /** method to insert new assignment,
     * check the record then pass it to dao layer to
     insert it in database tables */
    public boolean add(WorkOnDto staff_course) {
        boolean addFlag = false;
        try {
            /* check if the object already exists in databas by using
         * isExist method implemented in dao */
            if (!dao.isExist(staff_course)) {
                // call insert method in dao and pass the object parameter to it then return
                addFlag = dao.insert(staff_course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addFlag;
    }

    /**delete object  from database
     *check if record exists then call delete method from dao
     * to delete it from database */
    public boolean delete(WorkOnDto staff_course) {
        boolean deleteFlag = false;
        try {
            if (dao.isExist(staff_course))
                deleteFlag = dao.delete(staff_course);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteFlag;
    }

    /**method to view all objects stored in database
     * calling viewAll method in dao */
    public List<WorkOnDto> viewAll() {
        List<WorkOnDto> staff_course = null;
        try {
            staff_course = dao.viewAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff_course;
    }

    /**
     *
     * @param staff_course dto object
     * @return true if success
     */
    public boolean isExist(WorkOnDto staff_course) {
        boolean flag = false;
        try {
            flag = dao.isExist(staff_course);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
