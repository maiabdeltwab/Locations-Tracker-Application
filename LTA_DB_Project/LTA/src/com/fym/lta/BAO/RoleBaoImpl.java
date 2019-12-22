package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.RoleDao;
import com.fym.lta.DTO.RoleDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;


public class RoleBaoImpl implements RoleBao {

    /* identify userdto and role dao objects and initialize to null */
    private UserDto user = null;
    private RoleDao dao = null;


    public RoleBaoImpl(UserDto user) {
        this.user = user;

        /* create object of roledao impl class and set to dao object */
        dao = new DaoFactory().createRoleDao(user);
    }

    /**
     * delete role from database
     * call delete method of dao layer to delete
     * @param role
     * @return
     */
    public boolean delete(RoleDto role) {

        boolean deleteFlage = true;
        try {
            if (dao.isExist(role))
                deleteFlage = dao.delete(role);
            else
                deleteFlage = false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return deleteFlage;
    }

    /**
     * search for role
     * call searchFor method of dao layer to search
     * @param role
     * @return list of role objects
     */
    public List<RoleDto> searchFor(RoleDto role) {

        List<RoleDto> roles = null;
        try {
            roles = dao.searchFor(role);
            return roles;
        } catch (Exception e) {
            e.printStackTrace();
            return roles;
        }
    }

    /**
     * view all objects in database
     * call dao layer vie method to list all objects
     * @return list of ojects
     */
    public List<RoleDto> listAll() {
        List<RoleDto> role = null;
        try {
            role = dao.viewAll();
            return role;
        } catch (Exception e) {
            e.printStackTrace();
            return role;
        }
    }

    /**
     * add new role to database
     * call add method of dao layer to add object data
     * @param role object
     * @param user object
     * @return true if sucess
     */
    public boolean add(RoleDto role, UserDto user) {
        boolean saveFlage = false;
        try {
            //data is valid
            if (dao.isExist(role)) {
                return saveFlage;
            } else {
                saveFlage = dao.createNew(role, user);
                return saveFlage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    /**
     * update role in database
     * call update method of dao layer tp update
     * @param role dto object
     * @param user dto object
     * @return true if success
     */
    public boolean update(RoleDto role, UserDto user) {

        boolean updateFlag = true;
        try {

            if (dao.isExist(role)) {
                updateFlag = dao.update(role, user);
            }

        }

        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return updateFlag;
    }

}
