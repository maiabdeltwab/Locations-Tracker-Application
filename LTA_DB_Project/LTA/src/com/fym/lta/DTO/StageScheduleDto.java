package com.fym.lta.DTO;

/**class to define stage-schedule relation attributes
 * with setters, getters and constructors
 */
public class StageScheduleDto {

    /* schedule id */
    private int schedule_id;

    /* stage code */
    private String stage_code;


    /**
     * class main constructor
     */
    public StageScheduleDto() {
        super();
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

    /**
     * stage code attribute setter
     * @param stage_code string
     */
    public void setStage_code(String stage_code) {
        this.stage_code = stage_code;
    }

    /**
     * stage code attribute getter
     * @return string
     */
    public String getStage_code() {
        return stage_code;
    }

}
