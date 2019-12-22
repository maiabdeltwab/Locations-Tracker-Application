package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.EqTypeSpecDao;
import com.fym.lta.DTO.EquipSpecificationDto;
import com.fym.lta.DTO.EquipTypeSpecDto;
import com.fym.lta.DTO.EquipmentTypeDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public class EqTypeSpecBaoImpl implements EqTypeSpecBao
{

  /* identify userdto and equipment tyoe spec dao objects
    *initialize to null */

  private UserDto user = null;
  private EqTypeSpecDao dao = null;


  public EqTypeSpecBaoImpl(UserDto user)
  {
    /* set user dto object */
    this.user = user;
    /* create object of EqTypeSpecdao impl class and set to dao object */
    dao = new DaoFactory().createEqTypeSpecDao(user);
  }


  /** method to insert new object passed from ui,
   * check the record then pass it to dao layer to
     insert it in database tables */
  public boolean add(EquipTypeSpecDto type_spec)
  {
    boolean addFlag = false;
    try
      {
        /* check if the object already exists in databas
             * by using isExist method implemented in dao */
        if(!dao.isExist(type_spec))
          {
            // call insert method in dao and pass the object parameter to it then return
            addFlag = dao.insert(type_spec);
          }

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return addFlag;
  }

  /**delete object passed from ui,
   * check if record exists then call delete method from dao to delete it
   * from database */
  public boolean delete(EquipTypeSpecDto type_spec)
  {
    boolean deleteFlag = false;
    try
      {
        if(dao.isExist(type_spec))
          deleteFlag = dao.delete(type_spec);
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return deleteFlag;
  }

  /**method to view all objects stored in database
   * call viewAll method in dao to get objects
   * return list of EquipTypeSpecDetailsDto objects*/
  public List<EquipTypeSpecDto> viewAll()
  {
    List<EquipTypeSpecDto> type_sp = null;
    try
      {
        type_sp = dao.listAll();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return type_sp;
  }

  /** method to view all specifications of given type stored in database
   * call viewAll method in dao layer
   * return list of specification dto objects*/
  public List<EquipSpecificationDto> viewAllSpecif(EquipmentTypeDto type)
  {
    List<EquipSpecificationDto> eq_sp = null;
    try
      {
        eq_sp = dao.loadAllSpecif(type);
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return eq_sp;
  }
}
