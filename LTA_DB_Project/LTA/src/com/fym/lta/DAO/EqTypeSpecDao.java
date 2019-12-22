package com.fym.lta.DAO;

/**
 *
 * @author Nada El-Gammal
 */

import com.fym.lta.DTO.EquipSpecificationDto;
import com.fym.lta.DTO.EquipTypeSpecDto;
import com.fym.lta.DTO.EquipmentTypeDto;

import java.util.List;

public interface EqTypeSpecDao
{

  public abstract boolean insert(EquipTypeSpecDto type_spec);

  public abstract boolean delete(EquipTypeSpecDto type_spec);

  public abstract boolean isExist(EquipTypeSpecDto type_spec);

  public abstract List<EquipTypeSpecDto> listAll();

  public abstract List<EquipSpecificationDto> loadAllSpecif(EquipmentTypeDto type);

}
