package com.fym.lta.BAO;

/**
 *
 * @author Islam
 */

import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

/**
 * interface to set methods to perform action on building 
 * by calling DAO layer
 */
public abstract interface BuildingBao
{


  /**
   * @return all buildings in data base
   */
  public abstract List<BuildingDto> ListAll();


  /**
   * @param - dto building object which would be inserted
   * @return true if success - false if not
   */
  public abstract Boolean insert(BuildingDto build, UserDto user);

  /**
   * @param dto object for selected building which would be updated
   * @return true if success - false if not
   */
  public abstract Boolean update(BuildingDto build, UserDto user);

  /**
   * @param dto building object which would be deleted
   * @return true if success - false if not
   */
  public abstract Boolean delete(BuildingDto build);

  /**
   * @return building have attribute value inserted in search
   */
  public abstract List<BuildingDto> searchFor(BuildingDto build);
}
