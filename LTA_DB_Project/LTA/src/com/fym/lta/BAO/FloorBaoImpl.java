package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.FloorDao;
import com.fym.lta.DTO.FloorDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public class FloorBaoImpl implements FloorBao {
    private UserDto user = null;

    private FloorDao dao = null;


    public FloorBaoImpl(UserDto user) {
        this.user = user;
        dao = new DaoFactory().createFloorDao(user);
    }

    /**
     * @return all floors in data base
     */
    public List<FloorDto> ListAll() {
        List<FloorDto> floors = null;
        try {

            floors = dao.viewAll();
            return floors;
        } catch (Exception e) {
            e.printStackTrace();
            return floors;
        }
    }


    /**
     * @param dto object for selected floor which would be updated
     * @return true if success - false if not
     */
    public boolean update(FloorDto floor, UserDto user) {
        boolean saveFlage = true;
        try {
            if (dao.isExist(floor)) {
                saveFlage = dao.update(floor, user);
            } else {
                saveFlage = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**
     * @param dto floor object which would be deleted
     * @return true if success - false if not
     */
    public Boolean delete(FloorDto floor) {
        boolean deleteFlage = true;
        try {
            if (dao.isExist(floor))
                deleteFlage = dao.delete(floor);
            else
                deleteFlage = false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return deleteFlage;
    }


    /**
     * @return floor have attribute value inserted in search
     */
    public List<FloorDto> searchFor(FloorDto floor) {
        List<FloorDto> floors = null;
        try {
            floors = dao.searchFor(floor);
            return floors;
        } catch (Exception e) {
            e.printStackTrace();
            return floors;
        }
    }


    /**
     * @param - dto floor object which would be inserted
     * @return true if success - false if not
     */
    public Boolean insert(FloorDto floor, UserDto user) {
        boolean saveFlage = true;
        try {
            if (dao.isExist(floor)) {
                saveFlage = false;
            } else {
                saveFlage = dao.createNew(floor, user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**Search for floors for a building
     * @param floor that has building id
     * @return floors in this building
     * */
    public List<FloorDto> viewBuildingFloors(FloorDto floor) {
        List<FloorDto> floors = null;
        try {
            floors = dao.viewBuildingFloors(floor);
            return floors;
        } catch (Exception e) {
            e.printStackTrace();
            return floors;
        }
    }
}
