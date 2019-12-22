package com.fym.lta.DAO;

import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface ScheduleDao
{

  /**add new schedule
   * @param schedule,user
   * @return true if it inserted successfuly, false if not
   */
  public abstract Boolean createNew(ScheduleDto sch, UserDto user);

  /**search for is schedule exist or not
   * @param location
   * @return boolean value
   */
  public abstract Boolean isExist(ScheduleDto sch);

  /**delete schedule from database
   * @param schedule
   * @return true if it deleted successfuly, false if no
   */
  public abstract Boolean delete(StageDto stage, DepartmentDto department);

  /**Update information for an existing schedule
   * @param schedule object, user object
   * @return true if it success false if not */
  public abstract Boolean update(ScheduleDto sch, UserDto user);

  /** Find number of slots for a specific schedule
   * @param take schedule object
   * @return return int value of number of slots */
  public abstract boolean numOfSlots(ScheduleDto sch);

  /** get the max schedule id to generate a new schedule
   * @return the max id
   * */
  public abstract int getMaxId();

  /** get the schedule id
   * @param schedule
   * @return the same schedule after assign its id */
  public abstract ScheduleDto getID(ScheduleDto schedule);

  /** get the schedule id
   * @param schedule path
   * @return  id */
  public abstract int getScheduleID(String path);

  public abstract List<ScheduleDto> viewAll();

  public abstract List<ScheduleDto> searchFor(ScheduleDto sch);


}
