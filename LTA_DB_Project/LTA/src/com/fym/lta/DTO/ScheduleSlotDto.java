package com.fym.lta.DTO;

/**class to define schedules-slots relation attributes
 * with setters, getters and constructors
 */
public class ScheduleSlotDto {

    /* slot code */
    private String slot_code;

    /* schedule id */
    private int schedule_id;


    /**
     * class main constructor
     */
    public ScheduleSlotDto() {
        super();
    }

    /**
     * slot code attribute setter
     * @param slot_code string
     */
    public void setSlot_code(String slot_code) {
        this.slot_code = slot_code;
    }

    /**
     * slot code attribute getter
     * @return string
     */
    public String getSlot_code() {
        return slot_code;
    }

    /**
     * schedule id attribute setter
     * @param schedule_id int
     */
    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    /**
     * schedule id attribute getter
     * @return int
     */
    public int getSchedule_id() {
        return schedule_id;
    }

}
