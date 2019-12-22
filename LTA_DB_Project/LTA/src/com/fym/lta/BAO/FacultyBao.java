package com.fym.lta.BAO;

import com.fym.lta.DTO.FacultyDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;


public abstract interface FacultyBao
{
  
  /**
     * update faculty in DB
     * @param faculty
     * @param user
     * @return true if updated-falseif not
     */
  public abstract boolean update(FacultyDto faculty, UserDto user);

  /**
     * view all faculties in db
     * @return list of faculty objects
     */
  public abstract List<FacultyDto> viewAll();

  /**
     * insert new faculty object in db
     * @param faculty dto object
     * @param user dto object
     * @return true if inserted successfully - false if not
     */
  public abstract boolean insert(FacultyDto faculty , UserDto user);
  
  /**
     * delete faculty from db 
     * @param faculty
     * @return true if deleted successfully - false if not
     */
  public abstract boolean delete(FacultyDto faculty);

  /**
     * search for faculty in DB
     * @param faculty dto object
     * @return
     */
  public abstract List<FacultyDto> searchFor(FacultyDto faculty);
  
  /**
     * set term to schedule
     * @param faculty
     * @param user
     * @return true if set successfully - false if not
     */
  public abstract boolean setScheduleTerm(FacultyDto faculty , UserDto user);
  
}
