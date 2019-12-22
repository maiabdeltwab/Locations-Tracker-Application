package com.fym.lta.BAO;

import com.fym.lta.DTO.FloorDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface FloorBao
{


  /**
   * @return all floors in data base
   */
  public abstract List<FloorDto> ListAll();

  /**
   * @param - dto floor object which would be inserted
   * @return true if success - false if not
   */
  public abstract Boolean insert(FloorDto floor, UserDto user);

  /**
   * @param dto object for selected floor which would be updated
   * @return true if success - false if not
   */
  public abstract boolean update(FloorDto floor, UserDto user);

  /**
   * @param dto floor object which would be deleted
   * @return true if success - false if not
   */
  public abstract Boolean delete(FloorDto floor);

  /**
   * @return floor have attribute value inserted in search
   */
  public abstract List<FloorDto> searchFor(FloorDto floor);

  /**Search for floors for a building
   * @param floor that has building id
   * @return floors in this building
   * */
  public abstract List<FloorDto> viewBuildingFloors(FloorDto floor);


}
