package com.fym.lta.BAO;

/**
 *
 * @author Nada El-Gammal
 */

import com.fym.lta.DTO.EquipmentDto;
import com.fym.lta.DTO.EquipmentTypeDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface EquipmentBao {
    /**
     * add new equipment in database inserted by user from GUI
     * @param equip dto object
     * @param user dto object
     * @return true if success - false if failed
     */
    public abstract boolean insert(EquipmentDto equip, UserDto user);

    /**
     * update equipment data in database from GUI
     * @param equip dto object
     * @param user dto object
     * @return true if success - false if failed
     */
    public abstract boolean update(EquipmentDto equip, UserDto user);

    /**
     * deleted equipment from database selected by user from GUI
     * @param equip dto object
     * @param user dto object
     * @return true if success - false if failed
     */
    public abstract boolean delete(EquipmentDto equip);

    /**
     * search for equipment in database by search word inserted from GUI
     * @param equip dto object of search word
     * @return list of euipment dto objects
     */
    public abstract List<EquipmentDto> searchFor(EquipmentDto equip);

    /**
     * view all equipments stored in database
     * @return list of equipment dto objects
     */
    public abstract List<EquipmentDto> listAll();

    /**
     * count number of equipments in database of selected type from GUI
     * @param equipType dto object
     * @return
     */
    public abstract boolean countEquips(EquipmentTypeDto equipType);

}
