package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.ScheduleSlotDao;
import com.fym.lta.DTO.ScheduleSlotDto;
import com.fym.lta.DTO.UserDto;


public class ScheduleSlotBaoImpl implements ScheduleSlotBao {
    private UserDto user = null;

    private ScheduleSlotDao dao = null;


    public ScheduleSlotBaoImpl(UserDto user) {
        this.user = user;
        dao = new DaoFactory().createScheduleSlotDao(user);
    }

    /**
     * insert object in database
     * call insert method of dao layer
     * @param scheduleSlot dto object
     * @return true if done successfully
     */
    public boolean insert(ScheduleSlotDto scheduleSlot) {
        boolean addFlag = false;
        try {
            /* check if the object already exists in databas
         * by using isExist method implemented in dao*/
            if (!(dao.isExist(scheduleSlot))) {
                // call insert method in dao and pass the object parameter to it then return
                addFlag = dao.createNew(scheduleSlot);
            } else {
                // call insert method in dao and pass the object parameter to it then return
                addFlag = dao.createNew(scheduleSlot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addFlag;
    }

    /**
     * delete assinment object from database
     * call dao layer delete method to perom delete
     * @param scheduleSlot dto object
     * @return true if deleted successfully
     */
    public boolean delete(ScheduleSlotDto scheduleSlot) {
        boolean deleteFlag = false;
        try {
            if (dao.isExist(scheduleSlot))
                deleteFlag = dao.delete(scheduleSlot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteFlag;
    }

    /**
     * update assignment object in database
     * call dao layer update method to update
     * @param scheduleSlot dto object
     * @return true if done successfuly
     */
    public boolean update(ScheduleSlotDto scheduleSlot) {
        boolean updateFlag = true;
        try {
            /* update if object exists in database */
            if (dao.isExist(scheduleSlot)) {
                updateFlag = dao.update(scheduleSlot);
            }

            return updateFlag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
