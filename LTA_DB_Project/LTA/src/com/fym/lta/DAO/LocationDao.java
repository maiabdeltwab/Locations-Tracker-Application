package com.fym.lta.DAO;

import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface LocationDao
{

  /**delete location from database
   * @param location
   * @return
   */
  public abstract Boolean delete(LocationDto location);

  /**
   * add new location
   * @param location type
   * @param user
   * @return true if it inserted successfuly, false if not
   */
  public abstract Boolean createNew(LocationDto location, UserDto user);

  /**Search about location
   * @param location dto object
   * @return list of result location set
   */
  public abstract List<LocationDto> searchFor(LocationDto location);

  /**use it in location type dao to search for location created by specific type
   * @param: take location type id
   * @return: list of locations
   * */
  public abstract List<LocationDto> searchLocationType(int loc_type_id);

  /**use it in location type dao to search for location created by specific type
   * @param: take location type id
   * @return: list of locations
   * */
  public abstract List<LocationDto> searchBuilding(int Building_id);

  /**update location information
   * @param location
   * @param user
   * @return
   */
  public abstract Boolean update(LocationDto location, UserDto user);

  /**search for is location exist or not
   * @param location
   * @return boolean value
   */
  public abstract Boolean isExist(LocationDto location);

  /**get all location in database
   * @return list of existing location
   */
  public abstract List<LocationDto> viewAll();

  /**Search for default location for schedule
   * used in location auto assignment
   * @param schedule
   * @return location
   * */
  public abstract LocationDto defaultLocation(ScheduleDto schedule);

  /**Find free locations for a specific time (slot)
   * @param take slot dto object
   * @return free locations
   * */
  public abstract List<LocationDto> freelocations(SlotDto slot);

  /**Search for locations for slot
   * which match the PLT for this slot and buildings of this department stage
   * used in location auto assignment
   * @param schedule,slot
   * @return location list
   * */
  public abstract List<LocationDto> match_building_and_type(ScheduleDto schedule,
    SlotDto slot);

  /**Search for locations for slot in schedule
   * which match the PLT for this slot
   * used in location auto assignment
   * @param slot
   * @return location list
   * */
  public abstract List<LocationDto> match_type(SlotDto slot);

  /**Search for locations  for slot in schedule
   * which match the buildings of this department stage
   * used in location auto assignment
   * @param schedule,slot
   * @return location list
   * */
  public abstract List<LocationDto> match_building(ScheduleDto schedule,
    SlotDto slot);

  /**Check if a specific location is free in a specific time/slot
   * @param location,slot object
   * @return true if location free, false if not  */
  public abstract boolean freeSlot(LocationDto location, SlotDto slot);


  /**Get location filterd by building and location type
   * @param building and location type
   * @return location list */
  public abstract List<LocationDto> search_type_building(BuildingDto building,
    LocationTypeDto type);

  /**get free locaton filtered by building and type
   * @param slot,location type,building
   * @return location list */
  public abstract List<LocationDto> freelocationsfiltered(SlotDto slot,
    BuildingDto b, LocationTypeDto l);

}
