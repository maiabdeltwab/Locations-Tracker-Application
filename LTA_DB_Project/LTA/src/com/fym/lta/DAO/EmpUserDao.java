package com.fym.lta.DAO;

import com.fym.lta.DTO.EmployeeDto;
import com.fym.lta.DTO.EmployeeUserDto;

public interface EmpUserDao
{

  public abstract boolean insert(EmployeeUserDto emp_acc);

  public abstract boolean delete(EmployeeUserDto emp_acc);

  public abstract boolean isExist(EmployeeUserDto emp_acc);

  public abstract boolean update(EmployeeUserDto emp_acc);

  /**Check if staff user account existing in database or not
   * @param take EmployeeUserDto object
   * @return true if exist, false if not*/
  public abstract Boolean employeeIsExist(EmployeeDto u);

}
