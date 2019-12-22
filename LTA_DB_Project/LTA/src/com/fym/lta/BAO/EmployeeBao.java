package com.fym.lta.BAO;

import com.fym.lta.DTO.EmployeeDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface EmployeeBao {
    /**
     * update selected employee in GUI
     * @param emp dto object selected by user
     * @param user dto object
     * @return true if successfully updated - flase if failed
     */
    public abstract boolean update(EmployeeDto emp, UserDto user);

    /**
     * delete selected employee in GUI from database
     * @param emp dto object
     * @return true if successfully deleted - flase if failed
     */
    public abstract boolean delete(EmployeeDto emp);

    /**
     * search for employee in database by word inserted by user in GUI
     * @param emp dto object
     * @return list of employees that match the search word
     */
    public abstract List<EmployeeDto> searchFor(EmployeeDto emp);

    /**
     * add new employee to database
     * @param emp dto object
     * @param user dto object
     * @return true if success - flase if failed
     */
    public abstract boolean add(EmployeeDto emp, UserDto user);

    /**
     * view all employees saved in database
     * no param
     * @return list of employee dto objects stored
     */
    public abstract List<EmployeeDto> listAll();

    /**
     * view user account data of selected employee from GUI in database
     * @param emp dto object
     * @return user dto object
     */
    public abstract UserDto viewUser(EmployeeDto emp);
}
