package com.fym.lta.DAO;


import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.FloorDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.UserDto;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;

public class FloorDaoImpl implements FloorDao
{
  private UserDto user = null;

  public FloorDaoImpl(UserDto user)
  {
    this.user = user;
  }

  /**
   * @param - dto floor object which would be inserted
   * @return true if success - false if not
   */

  public Boolean createNew(FloorDto f, UserDto user)
  {
    try
      {
        //Start database connection
        JdbcRowSet jdbc = user.getConnect();


        //get building id
        jdbc.setCommand("select ID_BUILD FROM BUILDING WHERE CODE_BUILD = ? ");
        jdbc.setString(1, f.getBuild().getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            f.getBuild().setId(jdbc.getInt("ID_BUILD"));
          }


        //insert the new floor
        jdbc.setCommand("insert into FLOOR (ID_FLOOR,CODE_FLOOR,DESCRIPTION_FLOOR,ID_BUILD,INSERTED_BY,INSERTED_AT) values(?,?,?,?,?,sysdate)");
        jdbc.setInt(1, f.getId());
        jdbc.setString(2, f.getCode());
        jdbc.setString(3, f.getDescription());
        jdbc.setInt(4, f.getBuild().getId());
        jdbc.setInt(5, user.getId());
        jdbc.execute();

        return true;
      }
    catch(SQLIntegrityConstraintViolationException e)
      {
        System.out.println("Error!");
        return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }


  /**
   * @param dto floor object which would be deleted
   * @return true if success - false if not
   */
  public Boolean delete(FloorDto f)
  {
    try
      {

        //Start database connection
        JdbcRowSet jdbc = user.getConnect();


        //get building id
        jdbc.setCommand("select ID_BUILD FROM BUILDING WHERE CODE_BUILD = ? ");
        jdbc.setString(1, f.getBuild().getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            f.getBuild().setId(jdbc.getInt("ID_BUILD"));

          }


        //delete locations in this floor

        //get location in floor's building
        LocationDao location_dao =
          new DaoFactory().createLocationDao(user); //location dao object
        List<LocationDto> locations =
          location_dao.searchBuilding(f.getBuild().getId()); //search about location in this building

        //delete location in this floor
        if(locations!=null&&!locations.isEmpty())
          {
            for(int i = 0; i<locations.size(); i++)
              {
                if(locations.get(i).getFloor().getCode().equalsIgnoreCase(f.getCode()))
                  {
                    if(location_dao.delete(locations.get(i)))
                      System.out.println("true");
                  }

              }
          }


        //delete floor
        jdbc.setCommand("delete from floor where CODE_FLOOR=?");
        jdbc.setString(1, f.getCode());
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


  //not used
  public Boolean update(FloorDto f, UserDto user)
  {
    try
      {
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("UPDATE FLOOR "+
          "SET ID_FLOOR = ? , DESCRIPTION_FLOOR = ? , ID_BUILD = ? ,"+
          " UPDATED_BY=? , UPDATED_AT =SYSDATE"+"WHERE CODE_FLOOR = ?");

        jdbc.setInt(1, f.getId());
        jdbc.setString(2, f.getDescription());
        jdbc.setInt(3, f.getBuild().getId());
        jdbc.setInt(4, user.getId());
        jdbc.setString(5, f.getCode());
        jdbc.execute();

        return true;
      }
    catch(java.sql.SQLException e)
      {
        System.out.println("a");
        return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        System.out.println("a");
        return false;
      }
  }


  public List<FloorDto> viewAll()
  {
    List<FloorDto> floors = null;
    try
      {

        //Start database connection
        JdbcRowSet jdbc = user.getConnect();

        //get all floors in data base
        jdbc.setCommand("SELECT FLOOR.ID_FLOOR ,FLOOR.CODE_FLOOR, FLOOR.DESCRIPTION_FLOOR, "+
          " BUILDING.CODE_BUILD FROM "+
          "(FLOOR JOIN BUILDING ON (FLOOR.ID_BUILD = BUILDING.ID_BUILD)) ORDER BY FLOOR.CODE_FLOOR");
        jdbc.execute();

        //start get result
        FloorDto f = null;
        while(jdbc.next())
          {
            if(floors==null)
              {
                floors = new ArrayList<>();
              }
            f = new FloorDto();

            f.setId(jdbc.getInt("ID_floor"));
            f.setCode(jdbc.getString("CODE_floor"));
            f.setDescription(jdbc.getString("DESCRIPTION_floor"));
            f.setBuild(new BuildingDto());
            f.getBuild().setCode(jdbc.getString("CODE_BUILD"));

            floors.add(f);
            f = null;
          }

        //get location number for each floor

        if(floors!=null&&!floors.isEmpty())
          {

            for(int i = 0; i<floors.size(); i++)
              {
                int location_no = 0;
                jdbc.setCommand("SELECT CODE_FLOOR FROM LOCATION WHERE CODE_FLOOR=? ");
                jdbc.setString(1, floors.get(i).getCode());
                jdbc.execute();

                while(jdbc.next())
                  {
                    location_no++;
                  }
                floors.get(i).setLocation_number(location_no);
              }
          }


      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return floors;
  }


  /**search for is floor exist or not
   * @param floor
   * @return boolean value
   */
  public Boolean isExist(FloorDto f)
  {
    boolean flag = false;

    try
      {
        //start db connection
        JdbcRowSet jdbc = user.getConnect();


        //search for floor
        jdbc.setCommand("SELECT CODE_FLOOR FROM FLOOR WHERE CODE_FLOOR = ?");
        jdbc.setString(1, f.getCode());
        jdbc.execute();

        while(jdbc.next())
          {
            flag = true; //return true if exist
            break;
          }
        return flag;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  /**
   * @return floor have attribute value inserted in search
   */
  @Override
  public List<FloorDto> searchFor(FloorDto floor)
  {
    // TODO Implement this method
    List<FloorDto> floors = null;
    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("SELECT FLOOR.ID_FLOOR ,FLOOR.CODE_FLOOR, FLOOR.DESCRIPTION_FLOOR, "+
          "BUILDING.CODE_BUILD FROM (FLOOR JOIN BUILDING ON (FLOOR.ID_BUILD = BUILDING.ID_BUILD)) "+
          "WHERE FLOOR.ID_FLOOR=? OR FLOOR.CODE_FLOOR =? OR FLOOR.DESCRIPTION_FLOOR=? OR BUILDING.CODE_BUILD=? ORDER BY FLOOR.CODE_FLOOR ");

        try
          {
            jdbc.setInt(1, Integer.parseInt(floor.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, floor.getSearch());
        jdbc.setString(3, floor.getSearch());
        jdbc.setString(4, floor.getSearch());
        jdbc.execute();

        //start get result
        FloorDto f = null;
        while(jdbc.next())
          {
            if(floors==null)
              {
                floors = new ArrayList<>();
              }
            f = new FloorDto();

            f.setId(jdbc.getInt("ID_floor"));
            f.setCode(jdbc.getString("CODE_floor"));
            f.setDescription(jdbc.getString("DESCRIPTION_floor"));
            f.setBuild(new BuildingDto());
            f.getBuild().setCode(jdbc.getString("CODE_BUILD"));

            floors.add(f);
            f = null;
          }

        //get location number for each floor

        if(floors!=null&&!floors.isEmpty())
          {

            for(int i = 0; i<floors.size(); i++)
              {
                int location_no = 0;
                jdbc.setCommand("SELECT CODE_FLOOR FROM LOCATION WHERE CODE_FLOOR=? ");
                jdbc.setString(1, floors.get(i).getCode());
                jdbc.execute();

                while(jdbc.next())
                  {
                    location_no++;
                  }
                floors.get(i).setLocation_number(location_no);
              }

          }

      }
    catch(SQLException e)
      {
        e.printStackTrace();
        System.out.println("invalid input");
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return floors;

  }

  /**Search for floors for a building  (used in building)
   * @param floor that has building id
   * @return floors in this building
   * */
  @Override
  public List<FloorDto> viewBuildingFloors(FloorDto f)
  {
    List<FloorDto> floors = null;
    try
      {

        //Start data base connection
        JdbcRowSet jdbc = user.getConnect();


        //select floors for specific  building depend on building code
        jdbc.setCommand("SELECT FLOOR.ID_FLOOR,FLOOR.CODE_FLOOR,BUILDING.ID_BUILD,BUILDING.CODE_BUILD "+
          "FROM FLOOR JOIN BUILDING ON (BUILDING.ID_BUILD=FLOOR.ID_BUILD) "+
          "WHERE BUILDING.CODE_BUILD=? ORDER BY ID_FLOOR");

        jdbc.setString(1, f.getBuild().getCode()); //get code building


        jdbc.execute();
        FloorDto floor = null;
        while(jdbc.next())
          {
            if(floor==null)
              {
                floors = new ArrayList<>();
              }
            floor = new FloorDto();
            floor.setId(jdbc.getInt("id_FLOOR"));
            floor.setCode(jdbc.getString("code_FLOOR"));
            floors.add(floor);

          }

      }
    catch(SQLException e)
      {
        e.printStackTrace();
        System.out.println("invalid input");
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return floors;

  }

}
