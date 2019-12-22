package com.fym.lta.BAO;

import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;


public abstract interface StageBao {

    /**
     * updtage stage data in database
     * @param stage dto object
     * @param user dto object
     * @return true if updated - false if not
     */
    public abstract boolean update(StageDto stage, UserDto user);

    /**
     * get all stages in database
     * @return list of stage dto objects
     */
    public abstract List<StageDto> viewAll();

    /**
     * add stage object to database
     * @param stage dto object
     * @param user dto object
     * @return true if added - false if not
     */
    public abstract boolean add(StageDto stage, UserDto user);

    /**
     * search for stage in database
     * @param stage dto object
     * @return list of stage dto objects
     */
    public abstract List<StageDto> searchFor(StageDto stage);

    /**
     * delete stage from database
     * @param stage dto object
     * @return true if deleted - flase if not
     */
    public abstract boolean delete(StageDto stage);

    /**
     * check if stage exists in database or not
     * @param stage  dto object
     * @return true if exists - false if not
     */
    public abstract boolean isExist(StageDto stage);


}
