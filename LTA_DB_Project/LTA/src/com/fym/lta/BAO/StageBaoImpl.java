package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.StageDao;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public class StageBaoImpl implements StageBao {

    private UserDto user = null;
    private StageDao dao = null;

    public StageBaoImpl(UserDto user) {
        this.user = user;
        dao = new DaoFactory().createStageDao(user);
    }

    /**
     * update stage object
     * check if exists
     * call update method of dao layer
     * @param stage dto
     * @param user dto
     * @return true if updated , false if not
     */
    public boolean update(StageDto stage, UserDto user) {
        boolean saveFlage = true;
        try {
            if (dao.isExist(stage)) {
                saveFlage = dao.update(stage, user);
            } else {
                saveFlage = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**
     * get all stages in database
     * call viewall method of dao layer
     * @return list of stages dto objects
     */
    public List<StageDto> viewAll() {
        List<StageDto> stages = null;
        try {
            stages = dao.viewAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stages;
    }

    /**
     * add new stage to database
     * check if no exist
     * call add new method of dao layer
     * @param stage
     * @param user
     * @return
     */
    public boolean add(StageDto stage, UserDto user) {
        boolean saveFlage = true;
        try {
            if (!dao.isExist(stage)) {
                saveFlage = dao.createNew(stage, user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**
     * search for stage object in database
     * call search for method in dao layer
     * @param stage dto object
     * @return list of stage dto objects
     */
    public List<StageDto> searchFor(StageDto stage) {
        List<StageDto> stages = null;
        try {
            stages = dao.searchFor(stage);
            return stages;
        } catch (Exception e) {
            e.printStackTrace();
            return stages;
        }
    }

    /**
     * delete stage from databes
     * check it exists
     * call delete method of dao layer
     * @param stage dto object
     * @return true if deleted - false if not
     */
    public boolean delete(StageDto stage) {
        boolean deleteFlage = true;
        try {
            if (dao.isExist(stage))
                deleteFlage = dao.delete(stage);
            else
                deleteFlage = false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return deleteFlage;
    }

    /**
     * check if stage exists in database
     * call is exist method of dao layer
     * @param stage dto object
     * @return true if exists - false if not
     */
    public boolean isExist(StageDto stage) {
        boolean flag = false;
        try {
            flag = dao.isExist(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}
