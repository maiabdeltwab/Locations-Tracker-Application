package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.StaffUSerDao;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.StaffUserDto;
import com.fym.lta.DTO.UserDto;


public class StaffUserBaoImpl implements StaffUserBao {

    private UserDto user = null;
    private StaffUSerDao dao = null;


    public StaffUserBaoImpl(UserDto user) {
        this.user = user;
        dao = new DaoFactory().createStaffUserDao(user);
    }

    /** method to insert new account,
     * check if not exists then pass it to dao layer to
     insert it in database tables */
    public boolean insert(StaffUserDto staff_acc) {
        boolean addFlag = false;
        try {


            // call insert method in dao and pass the object parameter to it then return
            addFlag = dao.insert(staff_acc);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return addFlag;
    }

    /** delete account from database
     * check if acoount exists
     * call delete method from dao to delete it from database
     */
    public boolean delete(StaffUserDto staff_acc) {
        boolean deleteFlag = false;
        try {
            if (dao.isExist(staff_acc))
                deleteFlag = dao.delete(staff_acc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteFlag;
    }

    /**
     * update account in database
     * check if it exists in database
     * call update method from dao layer to update database tables
     * @param staff_acc dto objects
     * @return true if successfully updated- false if not
     */
    public boolean update(StaffUserDto staff_acc) {
        boolean updateFlag = false;
        try {
            if (dao.isExist(staff_acc)) {
                updateFlag = dao.update(staff_acc);
            } else {
                updateFlag = dao.insert(staff_acc);
            }

            return updateFlag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**Check if staff user account existing in database or not
     * @param take StaffUserDto object
     * @return true if exist, false if not*/
    public Boolean staffIsExist(StaffDto staff)

    {
        if (dao.staffIsExist(staff))
            return true;

        else
            return false;

    }

    /**
     *
     * @param staff_acc dto object
     * @return true if exists - flase if not
     */
    public boolean isExist(StaffUserDto staff_acc) {
        boolean flag = false;
        try {
            flag = dao.isExist(staff_acc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * call dao layer method to get id of staff of account
     * @param staff_acc dto object
     * @return integer id
     */
    public int getStaffId(StaffUserDto staff_acc) {
        int id = 0;
        try {
            id = dao.getStaffId(staff_acc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
