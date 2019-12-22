package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.DepartmentDao;
import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;


public class DepartmentBaoImpl implements DepartmentBao {
    /* identify user dto object and initialize to null */
    private UserDto user = null;

    /* identify department dao object and initialize to null */
    private DepartmentDao dao = null;

    /**
     * class constructor
     * set user connection object
     * create object of department dao implement and set to dao object
     * @param user dto connection object
     */
    public DepartmentBaoImpl(UserDto user) {
        this.user = user;
        dao = new DaoFactory().createDepartmentDao(user);
    }

    /** Delete a row selected by user in GUI.
     * Takes the object and return true if it has deleted and false if any exception occur*/
    public boolean delete(DepartmentDto department) {

        /* identify delete flag */
        boolean deleteFlage = true;
        try {
            /* chech if selected department exists
     * if yes call dao layer delete method
     * set method return to flag */
            if (dao.isExist(department))
                deleteFlage = dao.delete(department);
            else {
                /* set flag to flase if not exist */
                deleteFlage = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        /* return flag */
        return deleteFlage;
    }

    /** Method to view all exist department
     * no parameters
     * return list of departmentdto objects */
    public List<DepartmentDto> viewAll() {

        /* identify list of department dto */
        List<DepartmentDto> departs = new ArrayList<DepartmentDto>();

        try {
            /* call view all layer from dao layer
      * set method return to list */
            departs = dao.viewAll();
        }

        catch (Exception e) {
            e.printStackTrace();

        }
        /* return list */
        return departs;
    }

    /** Create a new department
     * takes department object inserted by user
     * Return true for if it success, False if not */
    public boolean create(DepartmentDto department, UserDto user) {
        /* identify save flag
   * initialize to true */
        boolean saveFlage = true;
        try {

            /* check if passed department exists in database
     * set flah to false */
            if (dao.isExist(department)) {
                saveFlage = false;
            } else
                /* if does not exist
           * call createNew method of dao layer
           * set method return to flag */
                saveFlage = dao.createNew(department, user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        /* return flag */
        return saveFlage;
    }


    /** update an existed one
     * takes department object inserted by user
     * Return true for if it success, False if not */
    public boolean update(DepartmentDto department, UserDto user) {
        /* identify save flag
       * initialize to false */
        boolean saveFlage = false;
        try {
            /* check if selected object exists in database
   * if true call update method of dao layer
   * set method return to flag */
            if (dao.isExist(department))
                saveFlage = dao.update(department, user);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        /* return flag */
        return saveFlage;
    }

    /** Search For Drparmtment with any attributes of it(id,name,code,...)
     * This method takes department object and return list of department if exist.
     */
    public List<DepartmentDto> searchFor(DepartmentDto department) {

        List<DepartmentDto> departs = null;

        try {
            /* call search for method from dao layer
     * set method return to department dto list */
            departs = dao.searchFor(department);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* return identified list */
        return departs;
    }


    /**get the max id in departments
     * @return id*/
    public int calcMaxId() {
        int id = 0;
        try {

            /* call get max id method from dao layer
      * set method reurn to id variable */
            id = dao.getMaxId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**get buildings for a specific department
     * @param department object
     * @return building list*/
    public List<BuildingDto> viewBuilding(DepartmentDto department) {
        List<BuildingDto> builds = null;
        try {
            /* call dao layer method to view buildings
       * set method return to builds list*/
            builds = dao.viewBuilding(department);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builds;
    }

    /**Get the department id
     * @param department
     * @return ID */
    public int getDepartmentId(DepartmentDto department) {
        int id = 0;
        try {
            /* call getid method of dao layer
     * set method retuen to id identified variable */
            id = dao.getDepartmentId(department);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * view department of stage
     * @param department dto object
     * @return list of departments
     */
    public List<DepartmentDto> viewStageDepartment(DepartmentDto department) {

        List<DepartmentDto> deps = null;
        try {
            /* call view method from dao layer
      * set method return to list */
            deps = dao.viewStageDepartment(department);
            return deps;
        } catch (Exception e) {
            e.printStackTrace();
            return deps;
        }
    }

}
