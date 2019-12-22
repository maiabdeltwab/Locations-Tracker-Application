package com.fym.lta.DAO;

import com.fym.lta.DTO.EmployeeDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface EmployeeDao
{
  public abstract Boolean createNew(EmployeeDto emp, UserDto user);

  public abstract Boolean delete(EmployeeDto emp);

  public abstract Boolean update(EmployeeDto emp, UserDto user);

  public abstract List<EmployeeDto> searchFor(EmployeeDto emp);

  public abstract List<EmployeeDto> viewAll();

  public abstract Boolean isExist(EmployeeDto emp);

  public abstract UserDto viewUserOfStaff(EmployeeDto emp);

}
