package com.fym.lta.BAO;

import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface ScheduleBao
{

  /**Update an schedule
   * @param take schedule
   * return true if sucess, false if not*/
  public abstract boolean update(ScheduleDto s, UserDto user);

  /**insert a new schedule
   * @param take schedule
   * return true if sucess, false if not*/
  public abstract boolean add(ScheduleDto s, UserDto user);

  /**delete schedule from database
   * @param schedule
   * @return true if it deleted successfuly, false if no
   */
  public abstract Boolean delete(StageDto stage, DepartmentDto department);

  /**search for an existing schedule
   * @param take schedule
   * return schedule list if found*/
  public abstract List<ScheduleDto> searchFor(ScheduleDto s);

  /**Calculate the max id for all sechedules in database
   * @return integer value of this id **/
  public abstract int calcMaxId();

  /**Location Automatic Assignment for schedule
   *@param take schedule, user
   *@return slots which the auto assignment faild in it  */
  public abstract List<SlotDto> autoAssignment(ScheduleDto schedule,
    UserDto user);

  /** get the schedule id
   * @param schedule path
   * @return  id */
  public abstract int getScheduleID(String path);

  /**check if schedule exist or not*/
  public abstract boolean isExist(ScheduleDto schedule);

  /** get the schedule id
   * @param schedule
   * @return the same schedule after assign its id */
  public abstract ScheduleDto getID(ScheduleDto schedule);

}
