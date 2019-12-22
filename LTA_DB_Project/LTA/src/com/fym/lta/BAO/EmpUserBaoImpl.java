package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.EmpUserDao;
import com.fym.lta.DTO.EmployeeDto;
import com.fym.lta.DTO.EmployeeUserDto;
import com.fym.lta.DTO.UserDto;

public class EmpUserBaoImpl implements EmpUserBao {

    /* identify userdto and employee user dao objects and initialize to null */
    private UserDto user = null;
    private EmpUserDao dao = null;

    public EmpUserBaoImpl(UserDto user) {
        /* set user dto object */
        this.user = user;

        /* create object of empuserdao impl class and set to dao object */
        dao = new DaoFactory().createEmpUserDao(user);
    }

    /* method to insert new record passed from ui, check the record then pass it to dao layer to
     insert it in database tables */
    public boolean insert(EmployeeUserDto emp_acc) {
        boolean addFlag = false;
        try {


            // call insert method in dao and pass the object parameter to it then return
            addFlag = dao.insert(emp_acc);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return addFlag;
    }

    //delete record passed from ui, check if record exists then call delete method from dao to delete it from database
    public boolean delete(EmployeeUserDto emp_acc) {
        boolean deleteFlag = false;
        try {
            if (dao.isExist(emp_acc))
                deleteFlag = dao.delete(emp_acc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteFlag;
    }

    /**
     * update object data in database
     * call dao layer update method to update data
     * @param emp_acc dto object to be deleted
     * @return true if succeess - false if failed
     */
    public boolean update(EmployeeUserDto emp_acc) {
        boolean updateFlag = true;
        try {
            if (dao.isExist(emp_acc)) {
                updateFlag = dao.update(emp_acc);
            } else {
                updateFlag = dao.insert(emp_acc);
            }
            return updateFlag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**Check if employee user account existing in database or not
     * @param take EmployeeUserDto object
     * @return true if exist, false if not*/
    public Boolean employeeIsExist(EmployeeDto emp)

    {

        if (dao.employeeIsExist(emp))
            return true;

        else
            return false;
    }


}
