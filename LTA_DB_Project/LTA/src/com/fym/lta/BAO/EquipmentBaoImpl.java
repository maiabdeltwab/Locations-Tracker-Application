package com.fym.lta.BAO;

/**
 *
 * @author Nada El-Gammal
 */

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.EquipmentDao;
import com.fym.lta.DAO.EquipmentTypeDao;
import com.fym.lta.DTO.EquipmentDto;
import com.fym.lta.DTO.EquipmentTypeDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public class EquipmentBaoImpl implements EquipmentBao {
    /* identify userdto and equip and euip type dao objects and initialize to null */
    private UserDto user = null;
    private EquipmentDao db = null;
    private EquipmentTypeDao typedao = null;


    public EquipmentBaoImpl(UserDto user) {
        /* set user dto object */
        this.user = user;

        /* create object of equipmentdao impl class and set to db object */
        db = new DaoFactory().createEquipmentDao(user);

        /* create object of equipTypedao impl class and set to typedao object */
        typedao = new DaoFactory().createEquipmentTypeDao(user);
    }

    /** method to insert new equipment passed from ui,
     * check the record then pass it to dao layer to
     insert it in database tables */
    public boolean insert(EquipmentDto equip, UserDto user) {

        boolean insertFlag = false;
        try {
            /* check if the object already exists in databas by using isExist method implemented in dao
             then show message if it exists and return */
            if (!db.isExist(equip)) { // call insert method in dao and pass the object parameter to it then return
                insertFlag = db.createNew(equip, user);
            }

            return insertFlag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**delete record passed from ui,
     * check if record exists then call delete method from dao
     * to delete from database */
    public boolean delete(EquipmentDto equip) {
        boolean deleteFlag = true;
        try {
            if (db.isExist(equip))
                deleteFlag = db.delete(equip);
            else
                deleteFlag = false;
            return deleteFlag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**method to search for equipments by calling searchFor
     * method implemented in dao */
    public List<EquipmentDto> searchFor(EquipmentDto equip) {
        List<EquipmentDto> eq = null;
        try {
            eq = db.searchFor(equip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eq;
    }

    /**method to view all equipments stored in database
     * calling viewAll method in dao*/
    public List<EquipmentDto> listAll() {
        List<EquipmentDto> eq = null;
        try {
            eq = db.viewAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eq;
    }

    /**method to update equipment passed form ui,
     * check if it already exists then call update method in dao*/
    public boolean update(EquipmentDto equip, UserDto user) {
        boolean updateFlag = false;
        try {
            if (db.isExist(equip)) {
                updateFlag = db.update(equip, user);
            } else {
                updateFlag = false;
            }
            return updateFlag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**method to count equipments of specific equipment type passed from ui,
     * check if it exists then call count method in dao*/
    public boolean countEquips(EquipmentTypeDto equipType) {
        boolean flag = false;
        try {
            if (typedao.isExist(equipType))
                flag = db.countEquipments(equipType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
