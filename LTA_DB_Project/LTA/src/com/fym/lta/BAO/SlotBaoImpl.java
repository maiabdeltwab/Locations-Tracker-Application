package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.SlotDao;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.StaffSlotDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;


public class SlotBaoImpl implements SlotBao {
    
    private UserDto user = null;
    private SlotDao dao = null;


    public SlotBaoImpl(UserDto user) {
        this.user = user;
        dao = new DaoFactory().createSlotDao(user);
    }

    /**Delete a slot from DB
     * @param this slot
     * @return ture if success or false if not */
    public boolean delete(SlotDto s) {
        boolean deleteFlage = true;
        try {
            if (dao.isExist(s))
                deleteFlage = dao.delete(s);
            else
                deleteFlage = false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return deleteFlage;
    }

    /**insert new slot in DB
     * @param slot,user
     * @return boolean value for success or not
     * */
    public boolean add(SlotDto s, UserDto user) {
        boolean saveFlage = true;
        try {
            //data is valid
            if (dao.isExist(s)) {
                saveFlage = false;
            } else {
                saveFlage = dao.createNew(s, user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**Search for existing slot/s in database
     * @param slot
     * @return found slots  */
    public List<SlotDto> searchFor(SlotDto s) {
        List<SlotDto> slots = null;
        try {
            slots = dao.searchFor(s);
            return slots;
        } catch (Exception e) {
            e.printStackTrace();
            return slots;
        }
    }

    /**Update a slot
     * @param this slot, user
     * @return ture or false */
    public boolean update(SlotDto s, UserDto user) {
        boolean saveFlage = true;
        try {
            //data is valid
            if (dao.isExist(s)) {
                saveFlage = dao.update(s, user);
            } else {
                saveFlage = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**method to get location assigned to slot
     * @param slot
     * @return slot after assign location to it **/
    public SlotDto getSlot(SlotDto s) {
        SlotDto slot = null;
        try {
            slot = dao.getSlot(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slot;
    }

    /** assign staff to a specific slot
     * @param staff slot dto object
     * @return true or false
     * */
    public boolean assignStaff(StaffSlotDto ss) {
        boolean flag = false;
        try {
            flag = dao.assignStaff(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**This method for getting all slot for a schedule
     * @param shcedule
     * @return slots
     * */
    public List<SlotDto> ScheduleSlot(ScheduleDto schedule) {

        List<SlotDto> slots = null;
        try {
            slots = dao.ScheduleSlot(schedule);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slots;

    }


    /**check if a specific slot is exist in database or not
     *@param slot object
     *@return ture if it exist, false if not */
    public boolean isExist(SlotDto slot) {
        boolean existFlag = false;
        try {
            existFlag = dao.isExist(slot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existFlag;
    }

    /**get slots assigned to location
     * used in location accupancy
     * @param take location
     * @return its slot list
     * */
    public List<SlotDto> location_accupancy(LocationDto location) {
        List<SlotDto> slots = null;
        try {
            slots = dao.location_accupancy(location);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slots;

    }

  /**get slots assigned to staff
   * used in staff load
   * @param take staff member
   * @return assigned slot list
   * */
  public List<SlotDto> staff_load(StaffDto staff_member){
    
    List<SlotDto> slots = null;
    try
      {
        slots = dao.staff_load(staff_member);
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return slots;

  }

    /**Check if this staff assigned to this slot
     * @param staff slot dto object
     * @return true, or false */
    public boolean isAssigned(StaffSlotDto ss) {
        boolean assignedFlag = false;
        try {
            assignedFlag = dao.isAssigned(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assignedFlag;
    }


    /** get all slots and its info. for a specific schedule
     * to view it in UI
     * @param take selected stage, department
     * @return slot list
     * */
    public List<SlotDto> viewSlotsOfSchedule(StageDto stage, DepartmentDto department) {
        List<SlotDto> slots = null;
        try {
            slots = dao.viewSlotsOfSchedule(stage, department);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slots;
    }


    /** get all slots and its info. for a specific schedule
     * to view it in UI
     * @param take selected stage, department
     * @return slot list
     * */
    public List<SlotDto> viewSlotsOfSchedule(ScheduleDto schedule) {
        List<SlotDto> slots = null;
        try {
            slots = dao.viewSlotsOfSchedule(schedule);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slots;
    }

    /**
     * get slots of selected schedule
     * call dao layer method to get slots from database
     * @param slot dto object
     * @param schedule dto object
     * @return list of slots dto
     */
    public List<SlotDto> getSlots(SlotDto slot, ScheduleDto schedule) {
        List<SlotDto> slots = null;
        try {
            slots = dao.getSlots(slot, schedule);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slots;
    }

    /**
     * update location assigned to slots manually by user
     * @param slot dto object
     * @param user dto object
     * @return true if updated seccessfully - false if not
     */
    public boolean updateManual(SlotDto slot, UserDto user) {
        boolean saveFlage = true;
        try {
            saveFlage = dao.updateManual(slot, user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

}
