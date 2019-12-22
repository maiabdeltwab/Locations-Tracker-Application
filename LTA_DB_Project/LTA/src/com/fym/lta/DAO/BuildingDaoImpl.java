package com.fym.lta.DAO;
/**
 *
 * @author Islam
 */

import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.UserDto;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;


public class BuildingDaoImpl implements BuildingDao
{
  private UserDto user = null;

  public BuildingDaoImpl(UserDto user)
  {
    this.user = user;
  }

  /**search for building/s
   * @param type
   * @return types that their attribute value match with search field
   */
  public List<BuildingDto> SearchFor(BuildingDto building)
  {
    List<BuildingDto> build = null;
    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //search in database
        jdbc.setCommand("select ID_BUILD,CODE_BUILD,DESCRIPTION_BUILD from BUILDING where ID_BUILD=? OR CODE_BUILD =? OR DESCRIPTION_BUILD=? order by id_build");
        try
          {
            jdbc.setInt(1, Integer.parseInt(building.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, building.getSearch());
        jdbc.setString(3, building.getSearch());
        jdbc.execute();


        //start get result
        BuildingDto b = null;
        while(jdbc.next())
          {
            if(build==null)
              {
                build = new ArrayList<>();
              }
            b = new BuildingDto();
            b.setId(jdbc.getInt("ID_build"));
            b.setCode(jdbc.getString("CODE_BUILD"));
            b.setDescription(jdbc.getString("DESCRIPTION_build"));
            build.add(b);
            b = null;
          }


        //get floors no for each building

        if(build!=null&&!build.isEmpty())
          {
            for(int i = 0; i<build.size(); i++)
              {

                jdbc.setCommand("SELECT ID_FLOOR,CODE_FLOOR,DESCRIPTION_FLOOR FROM FLOOR WHERE ID_BUILD=?");
                jdbc.setInt(1, build.get(i).getId());
                jdbc.execute();

                int floot_no = 0;

                while(jdbc.next())
                  {
                    floot_no++;
                  }

                build.get(i).setFloorsNo(floot_no);


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
    return build;

  }

  /**
   * @return List of all existing buildings in db
   */
  public List<BuildingDto> viewAll()
  {
    List<BuildingDto> build = null;
    try
      {

        //srart db connection

        JdbcRowSet jdbc = user.getConnect();


        //get all building found in db
        jdbc.setCommand("select id_build, code_build, description_build "+
          "from building order by id_build ");
        jdbc.execute();


        BuildingDto b = null;
        while(jdbc.next())
          {
            if(build==null)
              {
                build = new ArrayList<>();
              }
            b = new BuildingDto();
            b.setId(jdbc.getInt("ID_build"));
            b.setCode(jdbc.getString("CODE_BUILD"));
            b.setDescription(jdbc.getString("DESCRIPTION_build"));
            build.add(b);
            b = null;
          }


        //get floors no for each building

        if(build!=null&&!build.isEmpty())
          {
            for(int i = 0; i<build.size(); i++)
              {

                jdbc.setCommand("SELECT ID_FLOOR,CODE_FLOOR,DESCRIPTION_FLOOR FROM FLOOR WHERE ID_BUILD=?");
                jdbc.setInt(1, build.get(i).getId());
                jdbc.execute();

                int floot_no = 0;

                while(jdbc.next())
                  {
                    floot_no++;
                  }

                build.get(i).setFloorsNo(floot_no);


              }
          }
      }

    catch(Exception e)
      {
        e.printStackTrace();
      }
    return build;
  }

  /**add new building
   * @param building
   * @param user
   * @return true if it inserted successfuly, false if not
   */
  public Boolean createNew(BuildingDto b, UserDto user)
  {
    try
      {
        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //insert new buiding
        jdbc.setCommand("insert into BUILDING (ID_BUILD,CODE_BUILD,DESCRIPTION_BUILD,INSERTED_BY,INSERTED_AT) values(?,?,?,?,sysdate)");
        jdbc.setInt(1, b.getId());
        jdbc.setString(2, b.getCode());
        jdbc.setString(3, b.getDescription());
        jdbc.setInt(4, user.getId());
        jdbc.execute();
        return true;
      }
    catch(SQLIntegrityConstraintViolationException e)
      {
        System.out.println("Error !");
        return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  /**delete building
   * @param  building object
   * @return true if it deleted successfuly, false if not
   */
  public Boolean delete(BuildingDto b)
  {
    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //delete assigned building_department
        jdbc.setCommand("DELETE FROM DEPARTMENT_BUILDING  WHERE ID_BUILD=?");
        jdbc.setInt(1, b.getId());
        jdbc.execute();


        //delete locations in this builing
        LocationDao location_dao =
          new DaoFactory().createLocationDao(user); //location dao object
        List<LocationDto> locations =
          location_dao.searchBuilding(b.getId()); //search about location in this building

        //delete the result locations
        if(locations!=null&&!locations.isEmpty())
          {
            for(int i = 0; i<locations.size(); i++)
              {
                if(location_dao.delete(locations.get(i)))
                  System.out.println("true");
              }
          }

        //delete floors in this  building
        jdbc.setCommand("delete from floor where ID_BUILD=?");
        jdbc.setInt(1, b.getId());
        jdbc.execute();

        //delete this building
        jdbc.setCommand("delete from building where ID_BUILD=?");
        jdbc.setInt(1, b.getId());
        jdbc.execute();

        System.out.println("a");
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

  /**check if building exist or not
   * @param building object
   * @return true if it found in db, false if not
   */
  public Boolean isExist(BuildingDto b)
  {
    boolean flag = false;
    try
      {
        //start database
        JdbcRowSet jdbc = user.getConnect();

        //search for buiding
        jdbc.setCommand("SELECT ID_BUILD FROM BUILDING WHERE ID_BUILD = ?");
        jdbc.setInt(1, b.getId());
        jdbc.execute();
        while(jdbc.next())
          {
            flag = true; //return true if it found
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
   * @param  Building
   * @param  user
   * @return true for success false for not
   */

  public Boolean update(BuildingDto b, UserDto user)
  {
    try
      {

        //Start database connection
        JdbcRowSet jdbc = user.getConnect();


        //update building info
        jdbc.setCommand("UPDATE BUILDING "+
          " SET CODE_BUILD = ? , DESCRIPTION_BUILD = ?, UPDATED_BY=? ,"+
          " UPDATED_AT =SYSDATE WHERE ID_BUILD =? ");

        jdbc.setString(1, b.getCode());
        jdbc.setString(2, b.getDescription());
        jdbc.setInt(3, user.getId());
        jdbc.setInt(4, b.getId());

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
