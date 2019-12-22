package com.fym.lta.DAO;


import com.fym.lta.DTO.DepartBuildingDto;
import com.fym.lta.DTO.UserDto;

import java.util.Collections;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;

public class DepartBuildingDaoImpl implements DepartBuildingDao
{

  private UserDto user = null;

  public DepartBuildingDaoImpl(UserDto user)
  {
    this.user = user;
  }

  public Boolean delete(DepartBuildingDto bd)
  {
    try
      {

        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        // Delete the row with id of the selected department and its dependence
        jdbc.setCommand("delete from DEPARTMENT_BUILDING where ID_DEPARTMENT=?");
        jdbc.setInt(1, bd.getDepartment_id());
        jdbc.execute();
        return true;
      }
    catch(java.sql.SQLException e)
      {
        return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  public DepartBuildingDto searchFor(String c)
  {
    return null;
  }

  public List<DepartBuildingDto> viewAll()
  {
    return Collections.emptyList();
  }

  public Boolean update(DepartBuildingDto bd)
  {
    return null;
  }

  public Boolean createNew(DepartBuildingDto bd)
  {


    try
      {


        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        //Insert department in buildings through thier relationship
        jdbc.setCommand("insert into DEPARTMENT_BUILDING"+
          "(ID_DEPARTMENT,ID_BUILD) values(?,?)");
        jdbc.setInt(1, bd.getDepartment_id());
        jdbc.setInt(2, bd.getBuilding_id());


        return true;

      }
    catch(java.sql.SQLException e)
      {
        return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  public Boolean isExist(DepartBuildingDto bd)
  {
    return null;
  }
}
