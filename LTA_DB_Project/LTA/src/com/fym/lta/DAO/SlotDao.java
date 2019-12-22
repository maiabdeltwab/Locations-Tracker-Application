package com.fym.lta.DAO;

import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.StaffSlotDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface SlotDao
{

  /**insert new slot in DB
   * @param slot,user
   * @return boolean value for success or not
   * */
  public abstract Boolean createNew(SlotDto s, UserDto user);

  /**Check if slot is exist in database or not
   * @param slot
   * @return ture if exit, false if not
   * */
  public abstract Boolean isExist(SlotDto s);

  /**Delete a slot from DB
   * @param this slot
   * @return ture if success or false if not */
  public abstract Boolean delete(SlotDto s);

  /**Update a slot
   * @param this slot, user
   * @return ture or false */
  public abstract Boolean update(SlotDto s, UserDto user);

  /**Search for existing slot/s in database
   * @param slot
   * @return found slots  */
  public abstract List<SlotDto> searchFor(SlotDto s);

  /**method to get location assigned to slot
   * @param slot
   * @return slot after assign location to it **/
  public abstract SlotDto getSlot(SlotDto s);

  /** assign staff to a specific slot
   * @param staff slot dto object
   * @return true or false
   * */
  public abstract boolean assignStaff(StaffSlotDto ss);

  /**This method for getting all slot for a schedule
   * @param shcedule
   * @return slots
   * */
  public abstract List<SlotDto> ScheduleSlot(ScheduleDto schedule);

  /** get perefered location type of a lecture slot
   * @param take a slot
   * @return location type
   * */
  public abstract LocationTypeDto lec_loc_type(SlotDto slot);

  /** get perefered location type of a section slot
   * @param take a slot
   * @return location type
   * */
  public abstract LocationTypeDto sec_loc_type(SlotDto slot);

  /**get mutual slots
   * @param take slot
   * @return its mutual slots
   * */
  public abstract List<SlotDto> mutual_slots(SlotDto slot);

  /**get slots assigned to location
   * used in location accupancy
   * @param take location
   * @return its slot list
   * */
  public abstract List<SlotDto> location_accupancy(LocationDto location);

  /**get slots assigned to staff
   * used in staff load 
   * @param take staff member 
   * @return assigned slot list
   * */
  public abstract List<SlotDto> staff_load(StaffDto staff_member);
  
  
  /**Check if this staff assigned to this slot
   * @param staff slot dto object
   * @return true, or false */
  public abstract boolean isAssigned(StaffSlotDto ss);


  /** get all slots and its info. for a specific schedule
   * to view it in UI
   * @param take selected stage, department
   * @return slot list
   * */
  public abstract List<SlotDto> viewSlotsOfSchedule(StageDto stage,
    DepartmentDto department);

  /** get all slots and its info. for a specific schedule
   * to view it in UI
   * @param take selected stage, department
   * @return slot list
   * */
  public abstract List<SlotDto> viewSlotsOfSchedule(ScheduleDto schedule);


  /**
   * @param s
   * @param user
   * @return
   */
  public abstract Boolean updateManual(SlotDto s, UserDto user);

  public abstract List<SlotDto> viewAll();

  public abstract List<SlotDto> getSlots(SlotDto s, ScheduleDto sch);


}
