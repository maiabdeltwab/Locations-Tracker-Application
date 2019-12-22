package com.fym.lta.BAO;

import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

/**
 *
 * @author Mai-AbdEltwab
 */

public abstract interface LocationTypeBao {

    /**
     * @param dto object for selected location type which would be updated
     * @return true if success - false if not
     */
    public abstract boolean update(LocationTypeDto locationType, UserDto user);

    /**
     * @param - dto location type object which would be inserted
     * @return true if success - false if not
     */
    public abstract boolean add(LocationTypeDto locType, UserDto user);

    /**
     * @return all location types in data base
     */
    public abstract List<LocationTypeDto> viewAll();

    /**
     * @return location types have id/code/des/color inserted in search
     */
    public abstract List<LocationTypeDto> searchFor(LocationTypeDto locType);

    /**
     * @param dto location type object which would be deleted
     * @return true if success - false if not
     */
    public abstract boolean delete(LocationTypeDto locType);

}
