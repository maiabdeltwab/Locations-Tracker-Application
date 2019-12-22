package com.fym.lta.BAO;

/**
 *
 * @author Nada El-Gammal
 */

import com.fym.lta.DTO.EquipmentDto;
import com.fym.lta.DTO.EquipmentTypeDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface EquipmentTypeBao {

    /**
     * add new equipment type object to database inserted from GUI
     * @param equip_type dto object to be added
     * @param user dto object
     * @return true if success- false if failed
     */
    public abstract boolean add(EquipmentTypeDto equip_type, UserDto user);

    /**
     * update data of equipment type dto object in database
     * @param equip_type dto object passed from gui
     * @param user dto object
     * @return true if success - false if failed
     */
    public abstract boolean update(EquipmentTypeDto equip_type, UserDto user);

    /**
     * view all type objects in database
     * @return list of type dto objects
     */
    public abstract List<EquipmentTypeDto> viewAll();

    /**
     * delete type selected bu user in GUI from database
     * @param equip_type dto object to be deleted
     * @return true if succeess - false if failed
     */
    public abstract boolean delete(EquipmentTypeDto equip_type);

    /**
     * search for type in database by search word inserted by user in gui
     * @param equip_type dto object of serach word
     * @return list of type dto object that match search word
     */
    public abstract List<EquipmentTypeDto> searchFor(EquipmentTypeDto equip_type);

    /**
     * view all equipments in database of selected type by user in GUI
     * @param equip_type dto object selected by user
     * @return list of equipment dto objects
     */
    public abstract List<EquipmentDto> loadAllEquips(EquipmentTypeDto equip_type);

}
