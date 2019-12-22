package com.fym.lta.BAO;
/**
 *
 * @author Islam
 */
import com.fym.lta.DAO.BuildingDao;
import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;


public class BuildingBaoImpl implements BuildingBao {

    /* userdto object for database connection */
    private UserDto user = null;

    /* object from buildingdao interface */
    private BuildingDao dao = null;

    /**
     * class main constructor
     * create object from buildingBaoImpl class using DaoFactory
     * @param user object for database connection
     */
    public BuildingBaoImpl(UserDto user) {
        this.user = user;
        dao = new DaoFactory().createBuildingDao(user);
    }

    /**
     * @return all buildings in data base
     */
    public List<BuildingDto> ListAll() {
        /* identify list of buildingDto and initialize to null */
        List<BuildingDto> building = null;
        try {
            /* call dao method to view all buildings in databes and set
         * to identified list
         */
            building = dao.viewAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* return building list */
        return building;
    }


    /**
     * @param - dto building object which would be inserted
     * @return true if success - false if not
     */
    public Boolean insert(BuildingDto build, UserDto user) {
        /* identify flag to check if saved successfully and initialize to true*/
        boolean saveFlage = true;
        try {

            /* set saveFlag to false if object already exists in database */
            if (dao.isExist(build)) {
                saveFlage = false;
            } else {

                /* save dto-object in database biy calling creatNew method of dao layer
    * set the method return to save flag*/
                saveFlage = dao.createNew(build, user);
            }
        } catch (Exception e) {
            /* print any error occurs and retuen false */
            e.printStackTrace();
            return false;
        }
        /* return saveFlag */
        return saveFlage;
    }


    /**
     * @param dto object for selected building which would be deleted
     * @return true if success - false if not
     */
    public Boolean delete(BuildingDto build) {

        /* identify boolean falg and initialize to true */
        boolean deleteFlage = true;
        try {

            /* check if object exists in database then delete by calling dao method*/
            if (dao.isExist(build))
                /* set method return to delet flag */
                deleteFlage = dao.delete(build);
            else
                /* set flag to false if object does not exist in database */
                deleteFlage = false;
        } catch (Exception e) {
            /* print any error occurs and return false */
            e.printStackTrace();
            return false;
        }
        /* return delete flag */
        return deleteFlage;
    }


    /**
     * @param dto building object which would be searched for
     * @return list of buildings dto
     */
    public List<BuildingDto> searchFor(BuildingDto build) {
        /* identify list of buildings dto and initialize to null */
        List<BuildingDto> buildss = null;
        try {
            /* call dao search method and set its return to buildss list */
            buildss = dao.SearchFor(build);

            /* return buildss list method */
            return buildss;
        } catch (Exception e) {
            e.printStackTrace();
            /* return null in case of error */
            return buildss;
        }
    }


    /**
     * @param dto object for selected building which would be updated
     * @return true if success - false if not
     */
    public Boolean update(BuildingDto build, UserDto user) {
        /* identify flag for update process and initialize to true */
        boolean saveFlage = true;
        try {
            /* chech if object exists in database */
            if (dao.isExist(build)) {
                /* call update method of dao layer and set its return to flag */
                saveFlage = dao.update(build, user);
            } else {
                /* set flag to false if it does not exist */
                saveFlage = false;
            }
        } catch (Exception e) {
            /* print error and return false in case of error */
            e.printStackTrace();
            return false;
        }

        /* return flag*/
        return saveFlage;
    }

}
