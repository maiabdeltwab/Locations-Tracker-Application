package com.fym.lta.DAO;

/**
 *
 * @author Nada El-Gammal
 */

import com.fym.lta.DTO.EquipmentDto;
import com.fym.lta.DTO.EquipmentTypeDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;


public class EquipmentTypeDaoImpl implements EquipmentTypeDao
{
  private UserDto user = null;

  public EquipmentTypeDaoImpl(UserDto user)
  {
    this.user = user;
  }

  // search for equipemnt type using text inserted by user in UI and try to match this text with each column in table
  public List<EquipmentTypeDto> searchFor(EquipmentTypeDto et)
  {

    //identify list of equipment objects to store result set in it
    List<EquipmentTypeDto> equipt = null;
    try
      {

        //open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        //database query to select equipments that search text matches any of their columns
        jdbc.setCommand("select ID_EQ_TYPE , CODE_EQ_TYPE , NAME_EQ_TYPE , NO_OF_EQ from EQ_TYPE "+
          "where ID_EQ_TYPE=? OR CODE_EQ_TYPE =? OR NAME_EQ_TYPE=? OR NO_OF_EQ=? ");

        // pass search text from object parameter to query as inputs
        try
          {
            jdbc.setInt(1, Integer.parseInt(et.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, et.getSearch());
        jdbc.setString(3, et.getSearch());
        try
          {
            jdbc.setInt(4, Integer.parseInt(et.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(4, -1);
          }
        //query execution
        jdbc.execute();

        /*identify object to pass the resultset to its attributes then add it to the list
                to be viewd to user if objects found */
        EquipmentTypeDto e = null;
        while(jdbc.next())
          {
            if(equipt==null)
              {
                equipt = new ArrayList<>();
              }
            e = new EquipmentTypeDto();
            e.setId(jdbc.getInt("ID_EQ_TYPE"));
            e.setCode(jdbc.getString("CODE_EQ_TYPE"));
            e.setName(jdbc.getString("NAME_EQ_TYPE"));
            e.setNo_of_equip(jdbc.getInt("NO_OF_EQ"));
            equipt.add(e);
            e = null;
          }
      }
    catch(java.sql.SQLException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return equipt;
  }

  // view all equipment types stored in database
  public List<EquipmentTypeDto> viewAll()
  {

    //identify list to store equipemnt types objects
    List<EquipmentTypeDto> equipt = null;
    try
      {

        //start connection with database with resources
        JdbcRowSet jdbc = user.getConnect();

        //database query to view types ordered ascendingly by id
        jdbc.setCommand("select ID_EQ_TYPE , CODE_EQ_TYPE , NAME_EQ_TYPE , NO_OF_EQ "+
          "from EQ_TYPE  order by ID_EQ_TYPE");

        //query execution
        jdbc.execute();

        //identify eqyipmentTypeDto
        //pass data obtained from databas (when exist) to object attributes then add object to the list
        EquipmentTypeDto et = null;
        while(jdbc.next())
          {
            if(equipt==null)
              {
                equipt = new ArrayList<>();
              }
            et = new EquipmentTypeDto();
            et.setId(jdbc.getInt("ID_EQ_TYPE"));
            et.setCode(jdbc.getString("CODE_EQ_TYPE"));
            et.setName(jdbc.getString("NAME_EQ_TYPE"));
            et.setNo_of_equip(jdbc.getInt("NO_OF_EQ"));
            equipt.add(et);
            et = null;
          }
      }
    catch(java.sql.SQLException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return equipt;

  }

  // add new equipment type to database
  public Boolean createNew(EquipmentTypeDto et, UserDto user)
  {
    try
      {
        // open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        // database query to insert equipment type object in table
        jdbc.setCommand("insert into EQ_TYPE( ID_EQ_TYPE , CODE_EQ_TYPE , NAME_EQ_TYPE , INSERTED_BY ,INSERTED_AT ) values(?,?,?,?,?) ");

        //pass data from dto object to table
        try
          {
            jdbc.setInt(1, et.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, et.getCode());
        jdbc.setString(3, et.getName());
        jdbc.setInt(4, user.getId());
        jdbc.setDate(5,
          new java.sql.Date(Calendar.getInstance().getTime().getTime()));


        //query execution
        jdbc.execute();
        return true;
      }
    catch(java.sql.SQLException e)
      {
        e.printStackTrace();
        return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  // check if equipment object exists in database or not
  public Boolean isExist(EquipmentTypeDto et)
  {
    boolean flag = false;
    try
      {
        //open connection with database using resourses
        JdbcRowSet jdbc = user.getConnect();


        // sql query to look for the equipment type using its id and obtain it
        jdbc.setCommand("SELECT EQ_TYPE.ID_EQ_TYPE FROM EQ_TYPE where EQ_TYPE.ID_EQ_TYPE=?  ");

        // pass the equipment id to the query as input from the object parameter
        try
          {
            jdbc.setInt(1, et.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }

        //command execution
        jdbc.execute();
        while(jdbc.next())
          {
            // set the flag to true if the command returns a resultset ( equipment is found)
            flag = true;
            break;
          }
        return flag;
      }
    catch(java.sql.SQLException e)
      {
        e.printStackTrace();
        return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  // update data of equipment type
  public Boolean update(EquipmentTypeDto et, UserDto user)
  {
    try
      {

        // start connection with database using resourses
        JdbcRowSet jdbc = user.getConnect();


        // database query to perform update action
        jdbc.setCommand("update EQ_TYPE "+
          "set CODE_EQ_TYPE=? , NAME_EQ_TYPE=? , UPDATED_BY=? , UPDATED_AT=? "+
          "where ID_EQ_TYPE = ? ");

        // passing object attributes as inputs to the query
        jdbc.setString(1, et.getCode());
        jdbc.setString(2, et.getName());
        jdbc.setInt(3, user.getId());
        jdbc.setDate(4,
          new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        // check that input to query is integer and set it to -1 if it is not ineger
        try
          {
            jdbc.setInt(5, et.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(5, -1);
          }
        // query execution
        jdbc.execute();
        return true;
      }
    catch(java.sql.SQLException e)
      {
        e.printStackTrace();
        return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  //delete equipment type
  public Boolean delete(EquipmentTypeDto et)
  {
    try
      {

        // start connection with database using resourses
        JdbcRowSet jdbc = user.getConnect();


        //database query to delete equipment by checking its code
        jdbc.setCommand("delete from EQ_TYPE where EQ_TYPE.CODE_EQ_TYPE = ?  ");
        jdbc.setString(1, et.getCode());

        //query execution
        jdbc.execute();
        return true;
      }
    catch(java.sql.SQLException e)
      {
        e.printStackTrace();
        return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  //method to load all equipments of specific type
  public List<EquipmentDto> loadAllEquipments(EquipmentTypeDto et)
  {

    //identify list of equipment type dto to store result in
    List<EquipmentDto> eq = null;
    try
      {

        //open connection with data base using resources
        JdbcRowSet jdbc = user.getConnect();


        //query to select equipment data whose type id is the same as the parameter object
        jdbc.setCommand("select ID_EQ , CODE_EQ , COUNTRY_EQ from EQUIPMENT where EQUIPMENT.ID_EQ_TYPE=? order by ID_EQ ");
        try
          {
            jdbc.setInt(1, et.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.execute();

        //identify eqyipmentDto
        //pass data obtained from databas (when exist) to object attributes then add object to the list
        EquipmentDto e = null;
        while(jdbc.next())
          {
            if(eq==null)
              eq = new ArrayList<>();
            e = new EquipmentDto();
            e.setId(jdbc.getInt("ID_EQ"));
            e.setCode(jdbc.getString("CODE_EQ"));
            e.setCountry(jdbc.getString("COUNTRY_EQ"));
            e.setType(new EquipmentTypeDto(et.getCode(),
                et.getNo_of_equip(), et.getName(), et.getId()));
            eq.add(e);
            e = null;
          }
      }
    catch(java.sql.SQLException e)
      {

        e.printStackTrace();

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return eq;

  }


}
