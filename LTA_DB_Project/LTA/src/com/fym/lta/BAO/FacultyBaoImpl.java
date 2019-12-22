package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.EmployeeDao;
import com.fym.lta.DAO.FacultyDao;
import com.fym.lta.DTO.FacultyDto;
import com.fym.lta.DTO.UserDto;

import java.util.Collections;
import java.util.List;

public class FacultyBaoImpl implements FacultyBao {
    UserBaoImpl endUserBaoImpl;

    /* identify objects from uder dto and faculty dao layers
    * initialize objects to null */
    private UserDto user = null;
    private FacultyDao db = null;

    public FacultyBaoImpl(UserDto user) {
        super();

        /* set dto object */
        this.user = user;

        /* create object from faculty dao impl class and set to dao object */
        db = new DaoFactory().createFacultyDao(user);
    }

    /**
     * update faculty in db
     * check if it exists in db then call dao update method
     * @param faculty
     * @param user
     * @return true in case of success - false if not
     */
    public boolean update(FacultyDto faculty, UserDto user) {
        boolean flag = false;
        try {
            if (db.isExist(faculty))
                flag = db.update(faculty, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * view all faculties in db
     * @return list of objects
     */
    public List<FacultyDto> viewAll() {
        List<FacultyDto> faculties = null;
        try {
            faculties = db.viewAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return faculties;
    }

    /**
     * insert new faculty in db
     * check if it does not exist in db
     * call dao layer inert method to save the object
     * @param faculty
     * @param user
     * @return true if inserted successfully - false if not
     */
    public boolean insert(FacultyDto faculty, UserDto user) {
        boolean flag = false;
        try {
            if (!db.isExist(faculty))
                flag = db.createNew(faculty, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * delete faculty from db
     * check if it exists in db
     * call dao layer delete method to delete
     * @param faculty
     * @return true if deleted - false if not
     */
    public boolean delete(FacultyDto faculty) {
        boolean flag = false;
        try {
            if (db.isExist(faculty))
                flag = db.delete(faculty);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * serach for faculty in db
     * @param faculty
     * @return list of faulties that match search key- or null if nothing matches
     */
    public List<FacultyDto> searchFor(FacultyDto faculty) {
        List<FacultyDto> faculties = null;
        try {
            faculties = db.searchFor(faculty);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return faculties;
    }

    /**
     * set schedule term for faculty in db
     * check if faculty exists in db
     * set term for faculty using dao layer method
     * @param faculty
     * @param user
     * @return
     */
    public boolean setScheduleTerm(FacultyDto faculty, UserDto user) {
        boolean flag = false;
        try {
            if (db.isExist(faculty))
                flag = db.setScheduleTerm(faculty, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
