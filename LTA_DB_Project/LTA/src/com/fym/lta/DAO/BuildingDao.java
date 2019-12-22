package com.fym.lta.DAO;
/**
 *
 * @author Islam
 */

import java.util.List;

import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.UserDto;

public abstract interface BuildingDao
{

  /**search for building/s
   * @param type
   * @return types that their attribute value match with search field
   */
  public abstract List<BuildingDto> SearchFor(BuildingDto b);

  /**
   * @return List of all existing buildings in db
   */
  public abstract List<BuildingDto> viewAll();

  /**add new building
   * @param building
   * @param user
   * @return true if it inserted successfuly, false if not
   */
  public abstract Boolean createNew(BuildingDto b, UserDto user);

  /**delete building
   * @param  building object
   * @return true if it deleted successfuly, false if not
   */
  public abstract Boolean delete(BuildingDto b);

  /**check if building exist or not
   * @param building object
   * @return true if it found in db, false if not
   */
  public abstract Boolean isExist(BuildingDto b);

  /**
   * @param  Building
   * @param  user
   * @return true for success false for not
   */
  public abstract Boolean update(BuildingDto b, UserDto user);

}
