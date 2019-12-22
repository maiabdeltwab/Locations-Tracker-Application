package com.fym.lta.DAO;

/**
 *
 * @author Nada El-Gammal
 */

import com.fym.lta.DTO.EquipmentDto;
import com.fym.lta.DTO.EquipmentTypeDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;


public class EquipmentDaoImpl implements EquipmentDao
{
  private UserDto user = null;

  public EquipmentDaoImpl(UserDto user)
  {
    this.user = user;
  }
  // update data of equipment
  public Boolean update(EquipmentDto eq, UserDto user)
  {
    try
      {

        // start connection with database using resourses
        JdbcRowSet jdbc = user.getConnect();


        // obtain location id and equipment type id of inserted equipemnt by checking their names passed from UI
        jdbc.setCommand("select LOCATION.ID_LOC , EQ_TYPE.ID_EQ_TYPE "+
          "FROM LOCATION , EQ_TYPE WHERE (LOCATION.NAME_LOC=?) AND (EQ_TYPE.NAME_EQ_TYPE=?) ");
        jdbc.setString(1, eq.getLocation().getName());
        jdbc.setString(2, eq.getType().getName());
        jdbc.execute();
        while(jdbc.next())
          {

            // set location id and equipment type id obtained from database to equipment object
            eq.setType(new EquipmentTypeDto(jdbc.getInt("ID_EQ_TYPE")));
            eq.setLocation(new LocationDto(jdbc.getInt("ID_LOC")));
          }

        // database query to perform update action
        jdbc.setCommand("update EQUIPMENT "+
          "set CODE_EQ = ?  , COUNTRY_EQ = ? , ID_EQ_TYPE = ? , ID_LOC = ? , UPDATED_BY = ? , UPDATED_AT = SYSDATE "+
          "where ID_EQ = ? ");

        // passing object attributes as inputs to the query
        jdbc.setString(1, eq.getCode());
        jdbc.setString(2, eq.getCountry());

        // check that input to query is integer and set it to -1 if it is not ineger
        try
          {
            jdbc.setInt(3, eq.getType().getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(3, -1);
          }
        try
          {
            jdbc.setInt(4, eq.getLocation().getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(4, -1);
          }
        try
          {
            jdbc.setInt(5, user.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(5, -1);
          }
        try
          {
            jdbc.setInt(6, eq.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(6, -1);
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

  //delete equipment from databas
  public Boolean delete(EquipmentDto eq)
  {
    try
      {
        // start connection with database using resourses
        JdbcRowSet jdbc = user.getConnect();


        //database query to delete equipment by checking its code
        jdbc.setCommand("delete from EQUIPMENT where EQUIPMENT.CODE_EQ = ?  ");

        // pass equipment code from the object parameter as input to the query
        jdbc.setString(1, eq.getCode());
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

  // view all equipment stored in database
  public List<EquipmentDto> viewAll()
  {

    //identify list to store equipemnts objects
    List<EquipmentDto> equip = null;
    try
      {

        //start connection with database with resources
        JdbcRowSet jdbc = user.getConnect();


        //database query to view equipemnts ordered ascendingly by equipments id
        jdbc.setCommand("select EQUIPMENT.ID_EQ , EQUIPMENT.CODE_EQ, EQ_TYPE.NAME_EQ_TYPE , EQUIPMENT.COUNTRY_EQ , LOCATION.NAME_LOC "+
          "from EQUIPMENT , EQ_TYPE , LOCATION "+
          "WHERE (EQUIPMENT.ID_EQ_TYPE=EQ_TYPE.ID_EQ_TYPE) AND (EQUIPMENT.ID_LOC=LOCATION.ID_LOC) "+
          "order by EQUIPMENT.ID_EQ ");

        //query excecution
        jdbc.execute();

        //identify object of EquipmentDto , EquipmentTypeDto , LocationDto
        //pass data obtained from databas (when exist) to object attributes then add object to the list
        EquipmentDto e = null;
        EquipmentTypeDto et = null;
        LocationDto loc = null;
        while(jdbc.next())
          {
            if(equip==null)
              {
                equip = new ArrayList<>();
              }
            et = new EquipmentTypeDto(jdbc.getString("NAME_EQ_TYPE"));
            loc = new LocationDto();
            loc.setName(jdbc.getString("NAME_LOC"));
            e = new EquipmentDto();
            e.setId(jdbc.getInt("ID_EQ"));
            e.setCode(jdbc.getString("CODE_EQ"));
            e.setType(et);
            e.setCountry(jdbc.getString("COUNTRY_EQ"));
            e.setLocation(loc);
            equip.add(e);
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
    return equip;
  }

  // add new equipment to database
  public Boolean createNew(EquipmentDto eq, UserDto user)
  {
    try
      {
        // open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        // obtain location id
        jdbc.setCommand("select ID_LOC "+
          "FROM LOCATION WHERE LOCATION.NAME_LOC=? ");
        jdbc.setString(1, eq.getLocation().getName());
        jdbc.execute();
        while(jdbc.next())
          {

            // set location id
            eq.setLocation(new LocationDto(jdbc.getInt("ID_LOC")));
          }

        // obtain  equipment type id of inserted equipemnt by checking their names passed from UI

        jdbc.setCommand("select  ID_EQ_TYPE "+
          "FROM  EQ_TYPE WHERE EQ_TYPE.NAME_EQ_TYPE=? ");
        jdbc.setString(1, eq.getType().getName());
        jdbc.execute();
        while(jdbc.next())
          {

            // set equipment type id obtained from database to equipment object
            eq.setType(new EquipmentTypeDto(jdbc.getInt("ID_EQ_TYPE")));
          }

        // database query to insert equipment object in table
        jdbc.setCommand(" insert into EQUIPMENT  ( ID_EQ , CODE_EQ , ID_EQ_TYPE , COUNTRY_EQ , ID_LOC , INSERTED_BY , INSERTED_AT ) values( ? , ? , ? , ? , ?  , ? ,SYSDATE ) ");

        //insert data from dto object to table
        jdbc.setInt(1, eq.getId());
        jdbc.setString(2, eq.getCode());
        jdbc.setInt(3, eq.getType().getId());
        jdbc.setString(4, eq.getCountry());
        jdbc.setInt(5, eq.getLocation().getId());
        jdbc.setInt(6, user.getId());

        //query execution
        jdbc.execute();

        return true;

      }
    catch(java.sql.SQLException e)
      {
        // show message if any sql error occured
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
  public Boolean isExist(EquipmentDto eq)
  {
    boolean flag = false;
    try
      {
        //open connection with database using resourses
        JdbcRowSet jdbc = user.getConnect();


        // sql query to look for the equipment using its id and obtain it
        jdbc.setCommand("SELECT EQUIPMENT.ID_EQ FROM EQUIPMENT where EQUIPMENT.ID_EQ=?  ");

        // pass the equipment id to the query as input from the object parameter
        jdbc.setInt(1, eq.getId());

        //command execution
        jdbc.execute();

        // set the flag to true if the command returns a resultset ( equipment is found)
        while(jdbc.next())
          {
            flag = true;
            break;
          }
        return flag;
      }
    catch(java.sql.SQLException e)
      {
        // show message if any sql error occured
        e.printStackTrace();
        return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  // search for equipemnt using text inserted by user in UI and try to match this text with each column in table
  public List<EquipmentDto> searchFor(EquipmentDto eq)
  {

    //identify list of equipment objects to store result set in it
    List<EquipmentDto> equip = null;
    try
      {

        //open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        //database query to select equipments that search text matches any of their columns
        jdbc.setCommand("select EQUIPMENT.ID_EQ, EQUIPMENT.CODE_EQ, EQ_TYPE.NAME_EQ_TYPE, EQUIPMENT.COUNTRY_EQ, LOCATION.NAME_LOC "+
          "from EQ_TYPE JOIN EQUIPMENT ON (EQ_TYPE.ID_EQ_TYPE=EQUIPMENT.ID_EQ_TYPE) "+
          "JOIN LOCATION ON (EQUIPMENT.ID_LOC = LOCATION.ID_LOC) "+
          "where EQUIPMENT.ID_EQ=? OR EQUIPMENT.CODE_EQ=? OR EQ_TYPE.NAME_EQ_TYPE=? OR EQUIPMENT.COUNTRY_EQ=? OR LOCATION.NAME_LOC=? ");

        // pass search text from object parameter to query as inputs
        try
          {
            jdbc.setInt(1, Integer.parseInt(eq.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, eq.getSearch());
        jdbc.setString(3, eq.getSearch());
        jdbc.setString(4, eq.getSearch());
        jdbc.setString(5, eq.getSearch());
        jdbc.execute();

        /*identify object to pass the resultset to its attributes then add it to the list
            to be viewd to user if objects found */
        EquipmentDto e = null;
        while(jdbc.next())
          {
            if(equip==null)
              {
                equip = new ArrayList<>();
              }
            e = new EquipmentDto();
            e.setId(jdbc.getInt("ID_EQ"));
            e.setCode(jdbc.getString("CODE_EQ"));
            e.setType(new EquipmentTypeDto(jdbc.getString("NAME_EQ_TYPE")));
            e.setCountry(jdbc.getString("COUNTRY_EQ"));
            e.setLocation(new LocationDto(jdbc.getString("NAME_LOC")));
            equip.add(e);
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
    return equip;
  }

  /* method to count equipents that exist of specific equipment type
    and view the number in equipment type panel by passing each type id to the method as a parameter */
  public boolean countEquipments(EquipmentTypeDto et)
  {
    try
      {

        //open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        //query to select equipment id for eqch equipment that has equipment type id as the object paramenter
        jdbc.setCommand("select ID_EQ from EQUIPMENT where EQUIPMENT.ID_EQ_TYPE = ? ");
        jdbc.setInt(1, et.getId());

        //query execution
        jdbc.execute();

        // identify number of equipments and initialize it by 0
        int num = 0;

        // increase number by one for each record in the resultset of the query
        while(jdbc.next())
          {
            num++;
          }

        //query to set number of equipments column in equipment type table by the calculated number
        jdbc.setCommand("update EQ_TYPE set NO_OF_EQ =? where ID_EQ_TYPE = ? ");
        jdbc.setInt(1, num);
        jdbc.setInt(2, et.getId());
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
}
