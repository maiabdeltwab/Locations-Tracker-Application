package com.fym.lta.BAO;

import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.StaffSlotDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface SlotBao {

    /**Delete a slot from DB
     * @param this slot
     * @return ture if success or false if not */
    public abstract boolean delete(SlotDto s);

    /**insert new slot in DB
     * @param slot,user
     * @return boolean value for success or not
     * */
    public abstract boolean add(SlotDto s, UserDto user);

    /**Search for existing slot/s in database
     * @param slot
     * @return found slots  */
    public abstract List<SlotDto> searchFor(SlotDto s);

    /**Update a slot
     * @param this slot, user
     * @return ture or false */
    public abstract boolean update(SlotDto s, UserDto user);

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

    /**check if a specific slot is exist in database or not
     *@param slot object
     *@return ture if it exist, false if not */
    public abstract boolean isExist(SlotDto slot);

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
    public abstract List<SlotDto> viewSlotsOfSchedule(StageDto stage, DepartmentDto department);

    /** get all slots and its info. for a specific schedule
     * to view it in UI
     * @param take selected stage, department
     * @return slot list
     * */
    public abstract List<SlotDto> viewSlotsOfSchedule(ScheduleDto schedule);

    /**
     * get slots from database in selected schedule
     * @param slot dto object
     * @param schedule dto objevt selected
     * @return list of slots dto object
     */
    public abstract List<SlotDto> getSlots(SlotDto slot, ScheduleDto schedule);

    /**
     * update allocation to selected slot by user manually
     * @param slot dto object
     * @param user dto object
     * @return true if successfully done- false if not
     */
    public abstract boolean updateManual(SlotDto slot, UserDto user);

}
