package com.fym.lta.BAO;
/**
 *
 * @author Nada El-Gammal
 */
import com.fym.lta.DTO.EquipSpecificationDto;
import com.fym.lta.DTO.EquipTypeSpecDto;
import com.fym.lta.DTO.EquipmentTypeDto;

import java.util.List;

public interface EqTypeSpecBao {
    /**
     * add new object of equipment type specification assinment to database
     * @param type_spec dto object
     * @return true if success - false if failed
     */
    public abstract boolean add(EquipTypeSpecDto type_spec);

    /**
     * delete object of equipment type specification assinment from database
     * @param type_spec dto object
     * @return true if success - false if failed
     */
    public abstract boolean delete(EquipTypeSpecDto type_spec);

    /**
     *view all objects of equipment type specification assinments in database
     * @return list of EquipTypeSpecDetailsDto objects
     */
    public abstract List<EquipTypeSpecDto> viewAll();

    /**
     *view all objects of equipment specifications in database
     * of selected type from GUI
     * @return list of equipment specifications objects
     */
    public abstract List<EquipSpecificationDto> viewAllSpecif(EquipmentTypeDto type);

}
