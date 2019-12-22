package com.fym.lta.BAO;

import com.fym.lta.DAO.LocationTypeDaoImpl;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Mai-AbdEltwab
 */


public class LocationTypeBaoImpl implements LocationTypeBao {

    private UserDto user = null;

    public LocationTypeBaoImpl(UserDto user) {
        this.user = user;
    }

    /**
     * @param dto object for selected location type which would be updated
     * @return true if success - false if not
     */
    public boolean update(LocationTypeDto locType, UserDto user) {

        LocationTypeDaoImpl dao = new LocationTypeDaoImpl(user);
        boolean saveFlage = false;
        try {
            if (dao.isExist(locType))
                saveFlage = dao.update(locType, user);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**
     * @param - dto location type object which would be inserted
     * @return true if success - false if not
     */
    public boolean add(LocationTypeDto loctype, UserDto user) {

        LocationTypeDaoImpl dao = null;
        dao = new LocationTypeDaoImpl(user);

        boolean saveFlage = false;
        try {

            if (!dao.isExist(loctype))
                saveFlage = dao.createNew(loctype, user);
            return saveFlage;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return all location types in data base
     */
    public List<LocationTypeDto> viewAll() {

        LocationTypeDaoImpl dao = new LocationTypeDaoImpl(user);

        List<LocationTypeDto> types = new ArrayList<LocationTypeDto>();

        try {
            types = dao.viewAll();
        }

        catch (Exception e) {
            e.printStackTrace();

        }
        return types;
    }

    /**
     * @return location types have id/code/des/color inserted in search
     */
    public List<LocationTypeDto> searchFor(LocationTypeDto locType) {

        LocationTypeDaoImpl dao = new LocationTypeDaoImpl(user);
        List<LocationTypeDto> types = new ArrayList<LocationTypeDto>();

        try {
            types = dao.searchFor(locType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return types;
    }

    /**
     * @param dto location type object which would be deleted
     * @return true if success - false if not
     */
    public boolean delete(LocationTypeDto locType) {

        LocationTypeDaoImpl dao = new LocationTypeDaoImpl(user);
        boolean deleteFlage = false;
        try {
            if (dao.isExist(locType))
                deleteFlage = dao.delete(locType);
            else {
                deleteFlage = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return deleteFlage;
    }
}
