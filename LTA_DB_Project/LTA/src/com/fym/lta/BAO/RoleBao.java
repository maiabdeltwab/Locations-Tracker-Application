package com.fym.lta.BAO;

import com.fym.lta.DTO.RoleDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface RoleBao {
    /**
     * delete role from database selected in GUI
     * @param role dto obj
     * @return true if success - false if not
     */
    public abstract boolean delete(RoleDto role);

    /**
     * search for role in database by search word inserted by user in GUI
     * @param role dto obj
     * @return list of dto objects that matches
     */
    public abstract List<RoleDto> searchFor(RoleDto role);

    /**
     * view all role objects staored in database
     * @return list of role dto objects
     */
    public abstract List<RoleDto> listAll();

    /**
     * add new role to database inserted by user in gui
     * @param role dto object
     * @param user dto object
     * @return true if success- false if not
     */
    public abstract boolean add(RoleDto role, UserDto user);

    /**
     * update role object data in database selected by user in gui
     * @param role to object to be updated
     * @param user dto object
     * @return true if success - false if not
     */
    public abstract boolean update(RoleDto role, UserDto user);

}
