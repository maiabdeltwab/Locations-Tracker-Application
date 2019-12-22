package com.fym.lta.DAO;

import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

/**
 *
 * @author Mai-AbdEltwab
 */

public abstract interface LocationTypeDao
{

  /**
   * @param  location type
   * @param  user
   * @return true for success false for not
   */
  public abstract Boolean update(LocationTypeDto lt, UserDto user);

  /**
   * @return List of all existing location type in db
   */
  public abstract List<LocationTypeDto> viewAll();

  /**check if location type exist or not
   * @param location type object
   * @return true if it found in db, false if not
   */
  public abstract Boolean isExist(LocationTypeDto lt);

  /**search for location type/s
   * @param type
   * @return types that their id/code/des/color match with search field
   */
  public abstract List<LocationTypeDto> searchFor(LocationTypeDto type);

  /**delete location type
   * @param  location type object
   * @return true if it deleted successfuly, false if not
   */
  public abstract Boolean delete(LocationTypeDto lt);

  /**add new location type
   * @param location type
   * @param user
   * @return true if it inserted successfuly, false if not
   */
  public abstract Boolean createNew(LocationTypeDto lt, UserDto user);
}
