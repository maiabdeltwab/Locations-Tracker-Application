package com.fym.lta.BAO;

import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.StaffUserDto;

public interface StaffUserBao {

    /**
     * add new staff account to database
     * @param staff_acc dto object
     * @return true if success
     */
    public abstract boolean insert(StaffUserDto staff_acc);

    /**
     * delete staff account record from database
     * @param staff_acc dto object
     * @return true if success
     */
    public abstract boolean delete(StaffUserDto staff_acc);

    /**
     * update staff account record in database
     * @param staff_acc dto object
     * @return true if success
     */
    public abstract boolean update(StaffUserDto staff_acc);

    /**Check if staff user account existing in database or not
     * @param take StaffUserDto object
     * @return true if exist, false if not*/
    public abstract Boolean staffIsExist(StaffDto staff);

    /**
     * check if staff account exists in database
     * @param staff_acc dto object
     * @return true if exixts , false if not
     */
    public abstract boolean isExist(StaffUserDto staff_acc);

    /**
     * get id of staff memeber assigned to user acount
     * @param staff_acc dto object
     * @return id int
     */
    public abstract int getStaffId(StaffUserDto staff_acc);


}
