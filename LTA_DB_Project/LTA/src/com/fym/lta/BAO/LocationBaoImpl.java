package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.LocationDao;
import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public class LocationBaoImpl implements LocationBao {
    private UserDto user = null;

    private LocationDao dao = new DaoFactory().createLocationDao(user);

    public LocationBaoImpl(UserDto user) {
        this.user = user;
        dao = new DaoFactory().createLocationDao(user);

    }

    /**get all location in database
     * @return list of existing location
     */
    public List<LocationDto> ListAll() {
        List<LocationDto> locations = null;
        try {

            locations = dao.viewAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }


    /**
     * add new location
     * @param location type
     * @param user
     * @return true if it inserted successfuly, false if not
     */
    public boolean insert(LocationDto location, UserDto user) {
        boolean saveFlage = true;
        try {
            //data is valid
            if (dao.isExist(location)) {
                saveFlage = false;
                System.out.println("x");
            } else {
                saveFlage = dao.createNew(location, user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**update location information
     * @param location
     * @param user
     * @return
     */
    public boolean update(LocationDto location, UserDto user) {
        boolean saveFlage = true;
        try {
            //data is valid
            if (dao.isExist(location)) {
                saveFlage = dao.update(location, user);
            } else {
                saveFlage = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**delete location from database
     * @param location
     * @return
     */
    public boolean delete(LocationDto location) {
        boolean deleteFlage = true;
        try {

            if (dao.isExist(location))
                deleteFlage = dao.delete(location);
            else {
                deleteFlage = false;
            }
        }

        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return deleteFlage;
    }

    /**Search about location
     * @param location dto object
     * @return list of result location set
     */
    public List<LocationDto> searchFor(LocationDto location) {
        List<LocationDto> locs = null;
        try {
            locs = dao.searchFor(location);
            return locs;
        } catch (Exception e) {
            e.printStackTrace();
            return locs;
        }
    }


    /**Find free locations for a specific time (slot)
     * @param take slot dto object
     * @return free locations
     * */
    public List<LocationDto> freelocations(SlotDto slot) {

        List<LocationDto> locs = null;
        try {
            locs = dao.freelocations(slot);
            return locs;
        } catch (Exception e) {
            e.printStackTrace();
            return locs;
        }

    }

    /**Get location filterd by building and location type
     * @param building and location type
     * @return location list */
    public List<LocationDto> search_type_building(BuildingDto building, LocationTypeDto type) {

        List<LocationDto> locs = null;

        try {
            locs = dao.search_type_building(building, type);
            return locs;
        }

        catch (Exception e) {
            e.printStackTrace();
            return locs;
        }

    }

    /**
     * get free location of selected building during selected slot
     * @param slot
     * @param building
     * @param location
     * @return
     */
    public List<LocationDto> freelocationsfiltered(SlotDto slot, BuildingDto building, LocationTypeDto location) {

        List<LocationDto> locs = null;
        try {
            locs = dao.freelocationsfiltered(slot, building, location);
            return locs;
        } catch (Exception e) {
            e.printStackTrace();
            return locs;
        }

    }
}

