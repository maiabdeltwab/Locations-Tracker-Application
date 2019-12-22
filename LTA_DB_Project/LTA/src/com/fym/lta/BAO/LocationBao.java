package com.fym.lta.BAO;

import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface LocationBao {

    /**get all location in database
     * @return list of existing location
     */
    public abstract List<LocationDto> ListAll();


    /**
     * add new location
     * @param location type
     * @param user
     * @return true if it inserted successfuly, false if not
     */
    public abstract boolean insert(LocationDto location, UserDto user);

    /**update location information
     * @param location
     * @param user
     * @return
     */
    public abstract boolean update(LocationDto location, UserDto user);

    /**delete location from database
     * @param location
     * @return
     */
    public abstract boolean delete(LocationDto location);

    /**Search about location
     * @param location dto object
     * @return list of result location set
     */
    public abstract List<LocationDto> searchFor(LocationDto location);

    /**Find free locations for a specific time (slot)
     * @param take slot dto object
     * @return free locations
     * */
    public abstract List<LocationDto> freelocations(SlotDto slot);

    /**Get location filterd by building and location type
     * @param building and location type
     * @return location list */
    public abstract List<LocationDto> search_type_building(BuildingDto building, LocationTypeDto type);


    /**
     * @param slot
     * @param b
     * @param l
     * @return
     */
    public abstract List<LocationDto> freelocationsfiltered(SlotDto slot, BuildingDto building,
                                                            LocationTypeDto location);
}
