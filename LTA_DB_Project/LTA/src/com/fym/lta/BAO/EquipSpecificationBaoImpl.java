package com.fym.lta.BAO;

/**
 *
 * @author Nada El-Gammal
 */

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.EquipmentSpecificationDao;
import com.fym.lta.DTO.EquipSpecificationDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;


public class EquipSpecificationBaoImpl implements EquipSpecificationBao {
    /* identify userdto and specification dao objects and initialize to null */
    private UserDto user = null;
    private EquipmentSpecificationDao db = null;

    public EquipSpecificationBaoImpl(UserDto user) {
        /* set user dto object */
        this.user = user;

        /* create object of specificationdao impl class and set to dao object */
        db = new DaoFactory().createEquipmentSpecificationDao(user);
    }

    /** method to insert new specification passed from ui,
     * check the record then pass it to dao layer to
     insert it in database tables */
    public boolean insert(EquipSpecificationDto eq_spec, UserDto user) {
        boolean insertFlag = false;
        try {
            /* check if the object already exists in databas by using isExist method implemented in dao
             then show message if it exists and return */
            if (!db.isExist(eq_spec))

            { // call insert method in dao and pass the object parameter to it then return
                insertFlag = db.createNew(eq_spec, user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertFlag;

    }

    /** method to update specification passed form ui,
     * check if it already exists then call update method in dao */
    public boolean update(EquipSpecificationDto eq_spec, UserDto user) {
        boolean updateFlag = false;
        try {
            if (db.isExist(eq_spec))
                updateFlag = db.update(eq_spec, user);

            return updateFlag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**delete record passed from ui, check if record exists
     * then call delete method from dao to delete it from database */
    public boolean delete(EquipSpecificationDto eq_spec) {
        boolean deleteFlag = false;
        try {
            if (db.isExist(eq_spec))
                deleteFlag = db.delete(eq_spec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteFlag;

    }

    /**method to search for specifications
     * call searchFor method implemented in dao */
    public List<EquipSpecificationDto> searchFor(EquipSpecificationDto eq_spec) {
        List<EquipSpecificationDto> eq_spc = null;
        try {
            eq_spc = db.searchFor(eq_spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eq_spc;

    }

    /** method to view all specifications stored in database
     * calling viewAll method in dao */
    public List<EquipSpecificationDto> listAll() {
        List<EquipSpecificationDto> eq_spec = null;
        try {
            eq_spec = db.viewAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eq_spec;
    }
}
