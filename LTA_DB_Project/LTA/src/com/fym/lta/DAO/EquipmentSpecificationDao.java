package com.fym.lta.DAO;

/**
 *
 * @author Nada El-Gammal
 */

import com.fym.lta.DTO.EquipSpecificationDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface EquipmentSpecificationDao
{
  public abstract Boolean update(EquipSpecificationDto es, UserDto user);

  public abstract Boolean createNew(EquipSpecificationDto es, UserDto user);

  public abstract Boolean delete(EquipSpecificationDto es);

  public abstract List<EquipSpecificationDto> searchFor(EquipSpecificationDto es);

  public abstract List<EquipSpecificationDto> viewAll();

  public abstract Boolean isExist(EquipSpecificationDto es);
}
