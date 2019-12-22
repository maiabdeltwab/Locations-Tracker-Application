package com.fym.lta.BAO;

import com.fym.lta.DTO.EmployeeDto;
import com.fym.lta.DTO.EmployeeUserDto;

public interface EmpUserBao {

    /**
     * insert new assignment of user account to employee in database
     * @param emp_acc dto object
     * @return true if success - false if failed
     */
    public abstract boolean insert(EmployeeUserDto emp_acc);

    /**
     * delete assignment of user to employee from database
     * @param emp_acc dto object to be deleted
     * @return true if sucess - false if failed
     */
    public abstract boolean delete(EmployeeUserDto emp_acc);

    /**
     * update assignment of user to employee in database
     * @param emp_acc dto object to be updated
     * @return true if success - false if failed
     */
    public abstract boolean update(EmployeeUserDto emp_acc);

    /**Check if employee user account existing in database or not
     * @param take EmployeeUserDto object
     * @return true if exist, false if not*/
    public abstract Boolean employeeIsExist(EmployeeDto u);


}
