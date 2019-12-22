package com.fym.lta.DAO;


import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;

/**
 *
 * @author Mai-AbdEltwab
 */


public class LocationTypeDaoImpl implements LocationTypeDao
{
  private UserDto user = null;


  public LocationTypeDaoImpl(UserDto user)
  {
    this.user = user;
  }

  /**
   * @param  location type
   * @param  user
   * @return true for success false for not
   */
  public Boolean update(LocationTypeDto lt, UserDto user)
  {
    try
      {
        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        // update name & code & color for location type which its id can't be updated
        jdbc.setCommand("UPDATE LOCATION_TYPE "+
          "SET CODE_LOC_TYPE=? ,COLOR = ?,DESCRIBTION_LOC_TYPE = ? , UPDATED_BY = ? , UPDATED_AT = SYSDATE "+
          "WHERE ID_LOC_TYPE = ?");
        jdbc.setString(1, lt.getCode());
        jdbc.setString(2, lt.getColor());
        jdbc.setString(3, lt.getDescription());
        jdbc.setInt(4, user.getId());
        jdbc.setInt(5, lt.getId());
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


  /**
   * @return List of all existing location type in db
   */
  public List<LocationTypeDto> viewAll()
  {

    List<LocationTypeDto> types = null;

    try
      {

        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();

        // Select all columns from Lication type table
        jdbc.setCommand("select ID_LOC_TYPE,CODE_LOC_TYPE, COLOR,DESCRIBTION_LOC_TYPE  from LOCATION_TYPE ORDER BY ID_LOC_TYPE");
        jdbc.execute();


        //start pick up results from table into objects
        LocationTypeDto t = null;
        while(jdbc.next())
          {

            if(types==null)
              {
                types = new ArrayList<>();
              }
            t = new LocationTypeDto();
            t.setId(jdbc.getInt("ID_LOC_TYPE"));
            t.setCode(jdbc.getString("CODE_LOC_TYPE"));
            t.setColor(jdbc.getString("COLOR"));
            t.setDescription(jdbc.getString("DESCRIBTION_LOC_TYPE"));

            types.add(t);
            t = null;
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

    return types;
  }


  /**check if location type exist or not
   * @param location type object
   * @return true if it found in db, false if not
   */
  public Boolean isExist(LocationTypeDto lt)
  {

    try
      {

        // start connection
        JdbcRowSet jdbc = user.getConnect();

        // select from table LOCATION TYPE which its id = object id
        jdbc.setCommand("SELECT ID_LOC_TYPE "+"FROM LOCATION_TYPE "+
          "WHERE ID_LOC_TYPE=?");
        jdbc.setInt(1, lt.getId());
        jdbc.execute();

        boolean flage = false;

        while(jdbc.next())
          {
            flage = true; //if there is a result set return true
            break;
          }
        return flage;

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


  /**search for location type/s
   * @param type
   * @return types that their id/code/des/color match with search field
   */
  public List<LocationTypeDto> searchFor(LocationTypeDto type)
  {

    List<LocationTypeDto> types = null;

    try
      {

        // Start DataBase connection

        JdbcRowSet jdbc = user.getConnect();

        // Select all columns from location type table which its id/name/code Compatible with search text
        jdbc.setCommand("select ID_LOC_TYPE,CODE_LOC_TYPE,COLOR,DESCRIBTION_LOC_TYPE "+
          "from LOCATION_TYPE "+
          " where ID_LOC_TYPE = ? or COLOR= ? or CODE_LOC_TYPE= ? or DESCRIBTION_LOC_TYPE= ? ORDER BY ID_LOC_TYPE");

        try
          {
            jdbc.setInt(1, Integer.parseInt(type.getSearch()));
          }
        catch(Exception e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, type.getSearch());
        jdbc.setString(3, type.getSearch());
        jdbc.setString(4, type.getSearch());
        jdbc.execute();


        //start pick up results from table into objects
        LocationTypeDto t = null;
        while(jdbc.next())
          {
            if(types==null)
              {
                types = new ArrayList<>();
              }
            t = new LocationTypeDto();
            t.setId(jdbc.getInt("ID_LOC_TYPE"));
            t.setCode(jdbc.getString("CODE_LOC_TYPE"));
            t.setColor(jdbc.getString("COLOR"));
            t.setDescription(jdbc.getString("DESCRIBTION_LOC_TYPE"));

            types.add(t);
            t = null;
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

    return types;
  }


  /**delete location type
   * @param  location type object
   * @return true if it deleted successfuly, false if not
   */
  public Boolean delete(LocationTypeDto lt)
  {
    try
      {

        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();

        // Delete the row with id of the selected location type and its dependence
        //delete location of this type
        LocationDao location_dao =
          new DaoFactory().createLocationDao(user); //location dao object
        List<LocationDto> locations =
          location_dao.searchLocationType(lt.getId()); //search about location for this type

        //delete the result locations
        if(locations!=null&&!locations.isEmpty())
          {
            for(int i = 0; i<locations.size(); i++)
              {
                if(location_dao.delete(locations.get(i)))
                  System.out.println("true");
              }
          }


        //set assigned plt of this type for courses to null
        jdbc.setCommand("UPDATE COURSE SET PLT_SEC=(NULL)  WHERE PLT_SEC=?");
        jdbc.setInt(1, lt.getId());
        jdbc.execute();
        jdbc.setCommand("UPDATE COURSE SET PLT_LEC=(NULL)  WHERE PLT_LEC=?");
        jdbc.setInt(1, lt.getId());
        jdbc.execute();

        //delete location type
        jdbc.setCommand("delete from LOCATION_TYPE where ID_LOC_TYPE=?");
        jdbc.setInt(1, lt.getId());

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


  /**add new location type
   * @param location type
   * @param user
   * @return true if it inserted successfuly, false if not
   */
  public Boolean createNew(LocationTypeDto lt, UserDto user)
  {
    try
      {

        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        // insert into database
        jdbc.setCommand("insert into LOCATION_TYPE (ID_LOC_TYPE,CODE_LOC_TYPE,COLOR,DESCRIBTION_LOC_TYPE,INSERTED_BY,INSERTED_AT) values(?,?,?,?,?,SYSDATE)");

        jdbc.setInt(1, lt.getId());
        jdbc.setString(2, lt.getCode());
        jdbc.setString(3, lt.getColor());
        jdbc.setString(4, lt.getDescription());
        jdbc.setInt(5, user.getId());
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

}
