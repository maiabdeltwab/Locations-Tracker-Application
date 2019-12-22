package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.EmployeeDao;
import com.fym.lta.DTO.EmployeeDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public class EmployeeBaoImpl implements EmployeeBao {
    UserBaoImpl endUserBaoImpl;

    /* identify objects from uder dto and employee dao layers
   * initialize objects to null */
    private UserDto user = null;
    private EmployeeDao db = null;


    public EmployeeBaoImpl(UserDto user) {
        /* set dto object */
        this.user = user;

        /* create object from employee dao impl class and set to dao object */
        db = new DaoFactory().createEmployeeDao(user);
    }

    /**
     * view all employees stored in database by call viewAll method of dao layer
     * @return list of emolyee dto objects
     */
    public List<EmployeeDto> listAll() {

        return db.viewAll();

    }

    /**
     * check that emp object exists in database
     * if exists then update data in database of selected employee from GUI
     * @param emp dto object to be updated
     * @param user dto object
     * @return true if success - false if failed
     */
    public boolean update(EmployeeDto emp, UserDto user) {
        boolean saveFlage = true;
        try {
            if (db.isExist(emp)) {
                /* call dao layer method to update and set its return to flag */
                saveFlage = db.update(emp, user);
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
     * check that employee object exists in database
     * if exists- delete the selected object from database
     * call dao layer method to delete the object data from database
     * @param emp dto object to be deleted
     * @return true if success - false if failed
     */
    public boolean delete(EmployeeDto emp) {

        boolean deleteFlage = true;
        try {
            if (db.isExist(emp))
                deleteFlage = db.delete(emp);
            else
                deleteFlage = false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return deleteFlage;
    }

    /**
     * search for employee in database using inserted word by user in GUI
     * call search for method of dao layer to search database for object
     * @param emp dto object of inserted word from user
     * @return list of employee dto objects that match the search word
     */
    public List<EmployeeDto> searchFor(EmployeeDto emp) {
        List<EmployeeDto> emps = null;
        try {
            emps = db.searchFor(emp);
            return emps;
        } catch (Exception e) {
            e.printStackTrace();
            return emps;
        }

    }

    /**
     * creat new employee object in database inserted from GUI
     * call dao layer method to add the object to database
     * @param emp dto object to be inserted
     * @param user dto object
     * @return true if success - false if failed
     */
    public boolean add(EmployeeDto emp, UserDto user) {

        boolean saveFlage = true;
        try {
            //data is valid
            if (db.isExist(emp)) {
                saveFlage = false;
            } else {
                saveFlage = db.createNew(emp, user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**
     * view user accout data of selected employee in GUI
     * call dao layer method to get data data
     * @param emp to object to get its account
     * @return user dto object
     */
    public UserDto viewUser(EmployeeDto emp) {
        UserDto user = null;
        try {
            if (db.isExist(emp)) {
                user = db.viewUserOfStaff(emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
