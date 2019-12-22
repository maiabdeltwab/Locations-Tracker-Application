package com.fym.lta.BAO;

import com.fym.lta.DTO.ScheduleSlotDto;


public interface ScheduleSlotBao {

    /**
     * insert schedule-slot assignment in database
     * @param scheduleSlot dto object
     * @return true if sucess - false if not
     */
    public abstract boolean insert(ScheduleSlotDto scheduleSlot);

    /**
     * delete schedule-slot assinment from database
     * @param scheduleSlot dto object
     * @return true if deleted successfuly - false if not
     */
    public abstract boolean delete(ScheduleSlotDto scheduleSlot);

    /**
     * update schedule-slot assignment data in database
     * @param scheduleSlot dto object
     * @return true if success - false if not
     */
    public abstract boolean update(ScheduleSlotDto scheduleSlot);
}
