package com.fym.lta.DAO;


import java.util.List;

import com.fym.lta.DTO.CourseDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.UserDto;

public abstract interface CourseDao
{


  /**check if course exist or not
   * @param course object
   * @return true if it found in db, false if not
   */
  public abstract Boolean isExist(CourseDto c);


  /** Search For course with any attributes of it(id,name,code,...)
   * This method takes course object and return list of courses if exist.
   */
  public abstract List<CourseDto> searchFor(CourseDto c);

  /** Search For course with id
   * This method takes course object and return list of courses if exist.
   */
  public abstract CourseDto getCourse(CourseDto c);


  /** Delete a row selected by user in GUI.
   * Takes the object and return true if it has deleted and false if any exception occur*/
  public abstract Boolean delete(CourseDto c);


  /** Method to view all exist course
   * @param no parameters
   * @return list of coursedto objects */
  public abstract List<CourseDto> viewAll();


  /** update an existed one
   * @param takes course object inserted by user
   * @Return true for if it success, False if not */
  public abstract Boolean update(CourseDto c, UserDto user);


  /** Create a new course
   * @param takes course object inserted by user
   * @Return true for if it success, False if not */
  public abstract Boolean createNew(CourseDto c, UserDto user);

  public abstract boolean isAssigned(CourseDto c, DepartmentDto d);

  public abstract boolean assignDepCourse(CourseDto c, DepartmentDto d);

  public abstract int getMaxId();

  public abstract int getId(CourseDto c);

  public abstract List<DepartmentDto> viewDepsOfCourse(CourseDto c);

}
