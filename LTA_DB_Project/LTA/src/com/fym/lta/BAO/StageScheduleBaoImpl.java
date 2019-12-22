package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.StageScheduleDao;
import com.fym.lta.DTO.StageScheduleDto;
import com.fym.lta.DTO.UserDto;


public class StageScheduleBaoImpl implements StageScheduleBao {
    private UserDto user = null;

    private StageScheduleDao dao = null; ;

    public StageScheduleBaoImpl(UserDto user) {
        this.user = user;
        dao = new DaoFactory().createStageScheduleDao(user);
    }

    /**
     * check if assignment exists in database
     * call isExist method of dao layer
     * @param stageSchedule dto object
     * @return true if exists - flase if not
     */
    public boolean isExist(StageScheduleDto stageSchedule) {
        boolean flag = false;
        try {
            flag = dao.isExist(stageSchedule);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * add new stage-schedule object in database
     * check if not exists
     * call add new method of dao layer
     * @param stageSchedule dto object
     * @return true if added - false if not
     */
    public boolean add(StageScheduleDto stageSchedule) {
        boolean addFlag = false;
        try {
            if (!dao.isExist(stageSchedule)) {
                addFlag = dao.createNew(stageSchedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addFlag;
    }

    /**
     * delete stage-schedule assignment from DB
     * check if exists
     * call delete method of dao layer
     * @param stageSchedule
     * @return true if deleted - false if not
     */
    public boolean delete(StageScheduleDto stageSchedule) {
        boolean deleteFlag = false;
        try {
            if (dao.isExist(stageSchedule)) {
                deleteFlag = dao.delete(stageSchedule);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteFlag;
    }
}
