package com.fym.lta.DAO;


import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface DepartmentDao
{

  /** Search For Drparmtment with any attributes of it(id,name,code,...)
   * This method takes department object and return list of department if exist.
   */
  public abstract List<DepartmentDto> searchFor(DepartmentDto d);


  /** Delete a row selected by user in GUI.
   * Takes the object and return true if it has deleted and false if any exception occur*/
  public abstract boolean delete(DepartmentDto d);


  /** Create a new department
   * takes department object inserted by user
   * Returm true for if it success, False if not */

  public abstract boolean createNew(DepartmentDto d, UserDto user);


  /** Method to pick up all tuples from department table
   * no parameters
   * return list of departmentdto objects
   */
  public abstract List<DepartmentDto> viewAll();

  /** update object selected by user
   * take department object from bao
   * Returm true for if it success, False if not */
  public abstract boolean update(DepartmentDto d, UserDto user);


  /** Check if object is exist in database
   * take this object and return boolean value show if it is exist or not
   * */
  public abstract boolean isExist(DepartmentDto d);

  /**get buildings for a specific department
   * @param department object
   * @return building list*/
  public abstract List<BuildingDto> viewBuilding(DepartmentDto d);


  /**Get the department id
   * @param department
   * @return ID */
  public abstract int getDepartmentId(DepartmentDto d);


  /**get the max id in departments
   * @return id*/
  public abstract int getMaxId();


  /**Get stage and view its departments*/
  public List<DepartmentDto> viewStageDepartment(DepartmentDto d);


}
