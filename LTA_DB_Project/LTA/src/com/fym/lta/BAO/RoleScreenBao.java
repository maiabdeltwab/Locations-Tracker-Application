package com.fym.lta.BAO;


import com.fym.lta.DTO.RoleScreenDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

public interface RoleScreenBao {

    /**
     * update role screen assignment in database
     * @param roleScreen dto object
     * @return true if success - false if not
     */
    public abstract boolean update(RoleScreenDto roleScreen);

    /**
     * add new role screen assignment in database
     * @param roleScreen dto object
     * @return true if success - false if not
     */
    public abstract boolean add(RoleScreenDto roleScreen);

    /**
     * view all role screen assignments in database
     * @return list of objects dto
     */
    public abstract List<RoleScreenDto> viewAll();

    /**
     * serach for assignment object in database
     * @param roleScreen dto object
     * @return list of dto object
     */
    public abstract List<RoleScreenDto> searchFor(RoleScreenDto roleScreen);

    /**
     * check if user has access to selected panel or not
     * @param user dto object
     * @return list of panel user has access on
     */
    public abstract ArrayList<String> checkPanel(UserDto user);

    /**
     * delete role screen assignment from database
     * @param roleScreen dto object
     * @return true if deleted successfult - false if not
     */
    public abstract boolean delete(RoleScreenDto roleScreen);

    /**
     * check that user has access to panel to view only data
     * @param user dto object
     * @return true if viewonly access - flase if not
     */
    public abstract boolean viewonly(UserDto user);

}
