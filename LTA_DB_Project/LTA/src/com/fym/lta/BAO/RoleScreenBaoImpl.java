package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.RoleScreenDao;
import com.fym.lta.DTO.RoleScreenDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;


public class RoleScreenBaoImpl implements RoleScreenBao {

    /* identify userdto and role screen dao objects and initialize to null */
    private UserDto user = null;
    private RoleScreenDao dao = null;

    public RoleScreenBaoImpl(UserDto user) {
        this.user = user;

        /* create object of rolescreendao impl class and set to dao object */
        dao = new DaoFactory().createRoleScreenDao(user);
    }

    /**
     * update role screen assignmetnt data
     * check if assignment exists
     * call update method of dao layer to update database
     * @param roleScreen object to be updated
     * @return true if sucess
     */
    public boolean update(RoleScreenDto roleScreen) {
        boolean saveFlage = false;
        try {
            if (dao.isExist(roleScreen))
                saveFlage = dao.update(roleScreen);

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
        return saveFlage;
    }

    /**
     * add role-screen object to database
     * check that it does not exist
     * call add method of dao layer to insert in database
     * @param roleScreen dto object
     * @return true if success - false if not
     */
    public boolean add(RoleScreenDto roleScreen) {
        boolean saveFlage = false;
        try {

            if (!dao.isExist(roleScreen))
                saveFlage = dao.createNew(roleScreen);
            return saveFlage;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * view all role-screen objects in database
     * call list all method of dao layer
     * @return list of role-screen dto objects
     */
    public List<RoleScreenDto> viewAll() {
        List<RoleScreenDto> types = new ArrayList<RoleScreenDto>();

        try {
            types = dao.viewAll();
        }

        catch (Exception e) {
            e.printStackTrace();

        }
        return types;
    }

    /**
     * search for role-screen object in database
     * call searchFor method of dao layer
     * @param roleScreen dto object
     * @return list of objects that match
     */
    public List<RoleScreenDto> searchFor(RoleScreenDto roleScreen) {
        List<RoleScreenDto> types = new ArrayList<RoleScreenDto>();
        try {


            types = dao.searchFor(roleScreen);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return types;
    }

    /**
     * delete role-sreen object from database
     * call delete method of dao layer
     * @param roleScreen dto object
     * @return true if success - false if failed
     */
    public boolean delete(RoleScreenDto roleScreen) {
        boolean deleteFlage = false;
        try {
            if (dao.isExist(roleScreen))
                deleteFlage = dao.delete(roleScreen);

            else
                deleteFlage = false;


            return deleteFlage;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * check user access to panel
     * call check panel method of dao layer
     * @param user dto object
     * @return list of panel names
     */
    public ArrayList<String> checkPanel(UserDto user) {

        ArrayList<String> panels = new ArrayList<String>();
        try {
            panels = dao.checkPanel(user);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return panels;
    }

    /**
     * check view only access to user
     * call check method of dao layer
     * @param user dto object
     * @return true if has access to only view
     */
    public boolean viewonly(UserDto user) {

        boolean viewOnlyFlage = false;

        try {

            viewOnlyFlage = dao.viewonly(user);
            return viewOnlyFlage;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
