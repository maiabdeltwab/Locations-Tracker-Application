package com.fym.lta.DAO;


import com.fym.lta.DTO.FacultyDto;

import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface FacultyDao
{
  /**
     * update faculty object existing in DB
     * @param faculty-dto object
     * @param user
     * @return true if updated successfully - false if not
     */
  public abstract Boolean update(FacultyDto faculty , UserDto user);

  /**
     * search for faculty in DB
     * @param faculty-dto object
     * @return list of objects that match search key inserted by user
     */
  public abstract List<FacultyDto> searchFor(FacultyDto faculty);

  /**
     * check if faculty object exist in DB 
     * @param faculty-dto object to check its existance
     * @return true if exist - false if not
     */
  public abstract Boolean isExist(FacultyDto faculty);

  /**
     * get all faculty objects stored in DB 
     * @return list of dto objects
     */
  public abstract List<FacultyDto> viewAll();

  /**
     * delete faulty from database selected by user
     * @param faculty dto object to be deleted
     * @return true if deleted successfully - false if not
     */
  public abstract Boolean delete(FacultyDto faculty);

  /**
     * create new faculty in DB inserted by user
     * @param faculty dto object to be created
     * @param user dto object
     * @return true if created successfully - false if not
     */
  public abstract Boolean createNew(FacultyDto faculty, UserDto userDto);
  
  /**
     * set term by user of schedule to be created by system 
     * @param faculty dto object 
     * @param userDto
     * @return true if set successfully - false if not
     */
  public abstract Boolean setScheduleTerm(FacultyDto faculty, UserDto userDto);
}
