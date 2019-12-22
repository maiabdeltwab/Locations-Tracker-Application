package com.fym.lta.BAO;

import com.fym.lta.DTO.StageScheduleDto;

public interface StageScheduleBao {

    /**
     * check if assignment exists
     * @param stageSchedule
     * @return true if exists - false if not
     */
    public abstract boolean isExist(StageScheduleDto stageSchedule);

    /**
     * add new stage-schedule assignment object
     * @param stageSchedule dto object
     * @return true if added-false if not
     */
    public abstract boolean add(StageScheduleDto stageSchedule);

    /**
     * delete assignment from database
     * @param stageSchedule dto object
     * @return true if deleted - false if not
     */
    public abstract boolean delete(StageScheduleDto stageSchedule);

}
