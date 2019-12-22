package com.fym.lta.DAO;


import com.fym.lta.DTO.FloorDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface FloorDao
{

  /**
   * @param - dto floor object which would be inserted
   * @return true if success - false if not
   */
  public abstract Boolean createNew(FloorDto f1, UserDto user);

  /**
   * @param dto floor object which would be deleted
   * @return true if success - false if not
   */
  public abstract Boolean delete(FloorDto f2);

  /**
   * @param dto object for selected floor which would be updated
   * @return true if success - false if not
   */
  public abstract Boolean update(FloorDto f, UserDto user);

  /**
   * @return all floors in data base
   */
  public abstract List<FloorDto> viewAll();

  /**
   * @return floor have attribute value inserted in search
   */
  public abstract List<FloorDto> searchFor(FloorDto f);

  /**search for is floor exist or not
   * @param floor
   * @return boolean value
   */
  public abstract Boolean isExist(FloorDto f);

  /**Search for floors for a building
   * @param floor that has building id
   * @return floors in this building
   * */
  public abstract List<FloorDto> viewBuildingFloors(FloorDto f);
}
