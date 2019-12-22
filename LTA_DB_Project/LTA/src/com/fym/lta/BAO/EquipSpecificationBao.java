package com.fym.lta.BAO;

/**
 *
 * @author Nada El-Gammal
 */

import com.fym.lta.DTO.EquipSpecificationDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;


public abstract interface EquipSpecificationBao {

    /**
     * add new specification to database inserted by user in gui
     * @param eq_spec dto object
     * @param user dto object
     * @return true if success- false if failed
     */
    public abstract boolean insert(EquipSpecificationDto eq_spec, UserDto user);

    /**
     * update specification in database selectee by user in GUI
     * @param eq_spec dto object to be updated
     * @param user dto object
     * @return true if success- false if failed
     */
    public abstract boolean update(EquipSpecificationDto eq_spec, UserDto user);

    /**
     * delete specification object selected by user in GUI from database
     * @param eq_spec object to be deleteed
     * @return true if sucess- false if not
     */
    public abstract boolean delete(EquipSpecificationDto eq_spec);

    /**
     *search for specification in database by search word inserted in GUI
     * @param eq_spec dto obj of search word
     * @return list of dto objects that match
     */
    public abstract List<EquipSpecificationDto> searchFor(EquipSpecificationDto eq_spec);

    /**
     * view all specifications in database
     * @return list of dto objects
     */
    public abstract List<EquipSpecificationDto> listAll();
}
