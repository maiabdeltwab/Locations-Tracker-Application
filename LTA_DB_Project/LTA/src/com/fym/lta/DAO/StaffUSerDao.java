package com.fym.lta.DAO;


import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.StaffUserDto;

public interface StaffUSerDao
{

  public abstract boolean insert(StaffUserDto staff_acc);

  public abstract boolean delete(StaffUserDto staff_acc);

  public abstract boolean isExist(StaffUserDto staff_acc);

  public abstract boolean update(StaffUserDto staff_acc);

  /**Check if staff user account existing in database or not
   * @param take StaffUserDto object
   * @return true if exist, false if not*/
  public abstract Boolean staffIsExist(StaffDto staff);

  ////////////////////Nada El-Gammal
  public abstract int getStaffId(StaffUserDto staff_acc);


}
