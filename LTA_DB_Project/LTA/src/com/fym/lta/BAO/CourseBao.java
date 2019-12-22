package com.fym.lta.BAO;

import com.fym.lta.DTO.CourseDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;


public abstract interface CourseBao
{


  /** Create a new course
   * @param takes course object inserted by user
   * @Return true for if it success, False if not */
  public abstract boolean add(CourseDto course, UserDto user);

  /** update an existed one
   * @param takes course object inserted by user
   * @Return true for if it success, False if not */
  public abstract boolean update(CourseDto course, UserDto user);

  /** Search For course with any attributes of it(id,name,code,...)
   * This method takes course object and return list of courses if exist.
   */
  public abstract List<CourseDto> searchFor(CourseDto course);


  /** Search For course with id
   * This method takes course object and return list of courses if exist.
   */
  public abstract CourseDto getCourse(CourseDto course);

  /** Method to view all exist course
   * @param no parameters
   * @return list of coursedto objects */
  public abstract List<CourseDto> listAll();

  /** Delete a row selected by user in GUI.
   * Takes the object and return true if it has deleted and false if any exception occur*/
  public abstract boolean delete(CourseDto course);

  /**
     * check if course exists in database
     * @param course dto object
     * @return true if exists and false if doesnot exist
     */
  public abstract boolean isExist(CourseDto course);

  /**
     * get id of selected course
     * @param course dto object
     * @return id integer
     */
  public abstract int getId(CourseDto course);

  /**
     * calculate th e max. id of courses in database
     * @return id integer
     */
  public abstract int calcMaxId();

  /**
     * check if course is assigned to department or not
     * @param course dto object
     * @param department dto object
     * @return true if assigned - flase if not assigned
     */
  public abstract boolean isAssigned(CourseDto course, DepartmentDto department);

  /**
     * assign selected course to selected department
     * @param course dto object
     * @param department dto object
     * @return true if successfullt assigend - flase if not 
     */
  public abstract boolean assignDepCourse(CourseDto course, DepartmentDto department);

  /**
     * view list of departments of selected course
     * @param course dto object
     * @return list of department dto object
     */
  public abstract List<DepartmentDto> viewDepsOfCourse(CourseDto course);

}
