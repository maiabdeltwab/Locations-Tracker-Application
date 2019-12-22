package com.fym.lta.DAO;

import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.FloorDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;

public class LocationDaoImpl implements LocationDao
{

  private UserDto user = null;

  public LocationDaoImpl(UserDto user_data)
  {
    user = user_data;
  }

  /**delete location from database
   * @param location
   * @return true if it deleted successfuly, false if not
   */
  public Boolean delete(LocationDto location)
  {

    try
      {
        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        // Delete the row with id of the selected location and its dependence in database
        jdbc.setCommand("UPDATE EQUIPMENT SET ID_LOC=(NULL) WHERE ID_LOC=?"); //SET LOCATION TO NULL FOR EQUIPMENT
        jdbc.setInt(1, location.getId());
        jdbc.execute();
        jdbc.setCommand("UPDATE SLOT SET ID_LOC=(NULL) WHERE ID_LOC=?"); //SET LOCATION TO NULL FOR SLOTS
        jdbc.setInt(1, location.getId());
        jdbc.execute();
        jdbc.setCommand("DELETE FROM LOCATION WHERE ID_LOC=?"); //delete loction
        jdbc.setInt(1, location.getId());
        jdbc.execute();

        return true; //return true if sucess
      }

    catch(java.sql.SQLException e)
      {
        System.out.println("78");
        return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  /** add new location
   * @param location, user
   * @return true if it inserted successfuly, false if not
   */
  public Boolean createNew(LocationDto location, UserDto user)
  {
    try
      {
        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //get building id
        jdbc.setCommand("select ID_BUILD FROM BUILDING WHERE CODE_BUILD = ? ");
        jdbc.setString(1, location.getBuild().getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            location.getBuild().setId(jdbc.getInt("ID_BUILD"));
          }

        //get location type id
        jdbc.setCommand("SELECT ID_LOC_TYPE FROM LOCATION_TYPE WHERE CODE_LOC_TYPE = ? ");
        jdbc.setString(1, location.getType().getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            location.getType().setId(jdbc.getInt("ID_LOC_TYPE"));
          }


        //insert object to DB //and user&date
        jdbc.setCommand("insert into location (ID_LOC,CAP_LOC, CODE_LOC, NAME_LOC, ID_LOC_TYPE, "+
          "CODE_FLOOR, ID_BUILD, INSERTED_BY, INSERTED_AT) "+
          "values(?,?,?,?,?,?,?,?,SYSDATE)");

        jdbc.setInt(1, location.getId());
        jdbc.setInt(2, location.getCapacity());
        jdbc.setString(3, location.getCode());
        jdbc.setString(4, location.getName());
        jdbc.setInt(5, location.getType().getId());
        jdbc.setString(6, location.getFloor().getCode());
        jdbc.setInt(7, location.getBuild().getId());
        jdbc.setInt(8, user.getId());
        jdbc.execute();

        return true;
      }
    catch(SQLIntegrityConstraintViolationException e)
      {
        return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  /**Search about location
   * @param location dto object
   * @return list of result location set
   */
  public List<LocationDto> searchFor(LocationDto location)
  {
    List<LocationDto> locs = null;
    try
      {
        //start connection
        JdbcRowSet jdbc = user.getConnect();


        //sreach in database
        jdbc.setCommand("SELECT LOCATION.ID_LOC, LOCATION.CAP_LOC , LOCATION.CODE_LOC, "+
          " LOCATION.NAME_LOC, LOCATION_TYPE.CODE_LOC_TYPE, LOCATION_TYPE.COLOR, "+
          " LOCATION.CODE_FLOOR, BUILDING.CODE_BUILD "+
          "FROM (LOCATION JOIN LOCATION_TYPE ON(LOCATION.ID_LOC_TYPE = LOCATION_TYPE.ID_LOC_TYPE) "+
          "JOIN BUILDING ON (LOCATION.ID_BUILD = BUILDING.ID_BUILD)) "+
          "WHERE LOCATION.ID_LOC=? OR LOCATION.CODE_LOC =? OR LOCATION.NAME_LOC=? OR "+
          "BUILDING.CODE_BUILD=? OR LOCATION_TYPE.CODE_LOC_TYPE =? OR LOCATION.CODE_FLOOR=? "+
          "OR LOCATION.CAP_LOC=? ORDER BY LOCATION.ID_LOC  ");


        // for location id
        try
          {
            jdbc.setInt(1, Integer.parseInt(location.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }

        jdbc.setString(2, location.getSearch()); //for location code
        jdbc.setString(3, location.getSearch()); //for location name
        jdbc.setString(4, location.getSearch()); //for building code
        jdbc.setString(5, location.getSearch()); // for location type code
        jdbc.setString(6, location.getSearch()); //for floor code

        //For location capacity
        try
          {
            jdbc.setInt(7, Integer.parseInt(location.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(7, -1);
          }

        jdbc.execute();

        //get result
        LocationDto loc = null;
        while(jdbc.next())
          {
            if(loc==null)
              {
                locs = new ArrayList<>();
              }

            loc = new LocationDto();
            loc.setId(jdbc.getInt("id_loc")); //get id
            loc.setCapacity(jdbc.getInt("cap_loc")); //get capacity
            loc.setCode(jdbc.getString("code_loc")); //get code
            loc.setName(jdbc.getString("name_loc")); //get name

            FloorDto f =
              new FloorDto(jdbc.getString("code_floor")); //get floor code
            loc.setFloor(f);

            //get location Type
            loc.setType(new LocationTypeDto());
            loc.getType().setCode(jdbc.getString("code_loc_type"));
            loc.getType().setColor(jdbc.getString("COLOR"));

            //getbuiding code
            loc.setBuild(new BuildingDto());
            loc.getBuild().setCode(jdbc.getString("code_build"));

            locs.add(loc);

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
    return locs;

  }

  /**update location information
   * @param location
   * @param user
   * @return
   */
  public Boolean update(LocationDto location, UserDto user)
  {
    // TODO Implement this method
    try
      {

        //Start database connection
        JdbcRowSet jdbc = user.getConnect();


        //get building id
        jdbc.setCommand("select ID_BUILD FROM BUILDING WHERE CODE_BUILD = ? ");
        jdbc.setString(1, location.getBuild().getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            location.getBuild().setId(jdbc.getInt("ID_BUILD"));
          }

        //get location type id
        jdbc.setCommand("SELECT ID_LOC_TYPE FROM LOCATION_TYPE WHERE CODE_LOC_TYPE = ? ");
        jdbc.setString(1, location.getType().getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            location.getType().setId(jdbc.getInt("ID_LOC_TYPE"));
          }

        //update location data
        jdbc.setCommand("UPDATE location "+
          "SET cap_loc=? , code_loc = ? , name_loc = ? ,id_loc_type=?  ,code_floor=? ,ID_BUILD = ?"+
          " ,UPDATED_BY = ? , UPDATED_AT=SYSDATE "+"WHERE id_loc = ?");


        jdbc.setInt(1, location.getCapacity());
        jdbc.setString(2, location.getCode());
        jdbc.setString(3, location.getName());
        jdbc.setInt(4, location.getType().getId());
        jdbc.setString(5, location.getFloor().getCode());
        jdbc.setInt(6, location.getBuild().getId());
        jdbc.setInt(7, user.getId());
        jdbc.setInt(8, location.getId());
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


  /**search for is location exist or not
   * @param location
   * @return boolean value
   */
  public Boolean isExist(LocationDto location)
  {
    // TODO Implement this method
    boolean flag = false;
    try
      {
        //start data =base connection
        JdbcRowSet jdbc = user.getConnect();

        //search in db
        jdbc.setCommand("SELECT id_loc FROM location WHERE id_loc = ?");
        jdbc.setInt(1, location.getId());
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

  /**get all location in database
   * @return list of existing location
   */
  public List<LocationDto> viewAll()
  {
    // TODO Implement this method
    List<LocationDto> loc = null;
    try
      {

        //START DATABASE CONNECTION
        JdbcRowSet jdbc = user.getConnect();

        //get all locations in database
        jdbc.setCommand("SELECT LOCATION.ID_LOC ,LOCATION.CAP_LOC , LOCATION.CODE_LOC, LOCATION.NAME_LOC "+
          ",LOCATION_TYPE.CODE_LOC_TYPE ,LOCATION_TYPE.COLOR, LOCATION.CODE_FLOOR , BUILDING.CODE_BUILD "+
          "FROM (LOCATION_TYPE JOIN LOCATION  ON (LOCATION_TYPE.ID_LOC_TYPE=LOCATION.ID_LOC_TYPE)) "+
          "JOIN BUILDING ON (LOCATION.ID_BUILD = BUILDING.ID_BUILD) ORDER BY LOCATION.ID_LOC");
        jdbc.execute();

        LocationDto location = null;
        while(jdbc.next())
          {
            if(loc==null)
              {
                loc = new ArrayList<>();
              }
            location = new LocationDto();

            //get building
            BuildingDto building =
              new BuildingDto(); //create building dto object
            building.setCode(jdbc.getString("CODE_BUILD")); //set result building code in it
            location.setBuild(building); //set building to location

            //get floor
            FloorDto floor =
              new FloorDto(jdbc.getString("CODE_FLOOR")); //create floor dto set result floor code in it
            location.setFloor(floor); //set floor to location

            //get location type
            //create a location type dto object
            LocationTypeDto location_type = new LocationTypeDto();
            location_type.setCode(jdbc.getString("CODE_LOC_TYPE")); //set result location type code to it
            location_type.setColor(jdbc.getString("COLOR"));
            location.setType(location_type); //set type to location

            location.setId(jdbc.getInt("ID_LOC")); //get location id
            location.setCapacity(jdbc.getInt("CAP_LOC")); //get location capacity
            location.setCode(jdbc.getString("CODE_LOC")); //get location code
            location.setName(jdbc.getString("NAME_LOC")); //get location name
            loc.add(location);
            location = null;
          }


      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return loc;
  }

  /**use it in location type dao to search for location created by specific type
   * @param: take location type id
   * @return: list of locations
   * */
  public List<LocationDto> searchLocationType(int loc_type_id)
  {

    List<LocationDto> locs = null;

    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //search on db
        jdbc.setCommand("SELECT ID_LOC FROM LOCATION  WHERE ID_LOC_TYPE=? ");
        jdbc.setInt(1, loc_type_id);
        jdbc.execute();

        LocationDto loc = null;

        //start get result
        while(jdbc.next())
          {
            if(locs==null)
              {
                locs = new ArrayList<>();
              }
            loc = new LocationDto();

            loc.setId(jdbc.getInt("ID_LOC")); //get location id

            locs.add(loc);
            loc = null;
          }

      }
    catch(SQLException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return locs;
  }

  /**use it in building dao to search for location in specific building
   * @param: take location type id
   * @return: list of locations
   * */
  public List<LocationDto> searchBuilding(int building_id)
  {

    List<LocationDto> locs = null;

    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //search on db
        jdbc.setCommand("SELECT ID_LOC,CODE_FLOOR FROM LOCATION  WHERE ID_Build=? ");
        jdbc.setInt(1, building_id);
        jdbc.execute();

        LocationDto loc = null;

        //start get result
        while(jdbc.next())
          {
            if(locs==null)
              {
                locs = new ArrayList<>();
              }

            loc = new LocationDto();

            loc.setId(jdbc.getInt("ID_LOC")); //get location id
            loc.setFloor(new FloorDto(jdbc.getString("CODE_FLOOR"))); //get floor code

            locs.add(loc);
            loc = null;

          }
        return locs;
      }
    catch(SQLException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return locs;
  }

  /**Search for default location for schedule
   * used in location auto assignment
   * @param schedule
   * @return location
   * */
  public LocationDto defaultLocation(ScheduleDto schedule)
  {

    List<BuildingDto> buildings = new ArrayList<BuildingDto>();
    StageDto stage = null;

    LocationDto loc = null;
    BuildingDto build = null;
    LocationDto default_location = null;
    List<LocationDto> locations = null;


    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //get the default location type of this schedule
        jdbc.setCommand("SELECT DEFAULT_LOC_TYPE  "+"FROM SCHEDULE  "+
          "WHERE ID_SCHEDULE=? ");
        jdbc.setInt(1, schedule.getId());
        jdbc.execute();

        //starting get result
        while(jdbc.next())
          {
            schedule.setDefault_location_type(new LocationTypeDto());
            schedule.getDefault_location_type().setId(jdbc.getInt("DEFAULT_LOC_TYPE"));
          }


        //get stage of this schedule
        jdbc.setCommand("SELECT STAGE.CODE_STAGE,STAGE.ID_DEPARTMENT , STAGE.NO_OF_STD  "+
          "FROM STAGE JOIN SCHEDULE_STAGE ON (STAGE.CODE_STAGE =SCHEDULE_STAGE.CODE_STAGE) "+
          "WHERE SCHEDULE_STAGE.ID_SCHEDULE=? ");
        jdbc.setInt(1, schedule.getId());
        jdbc.execute();

        //starting get result
        while(jdbc.next())
          {

            stage = new StageDto();

            stage.setCode(jdbc.getString("CODE_STAGE")); //get stage code

            //get number of student of this stage
            stage.setNum_of_std(jdbc.getInt("NO_OF_STD"));

            //get department of this stage
            stage.setDepartment(new DepartmentDto());
            stage.getDepartment().setId(jdbc.getInt("ID_DEPARTMENT"));


          }


        //get building/s of schedule department
        jdbc.setCommand("SELECT BUILDING.ID_BUILD FROM BUILDING  "+
          "JOIN DEPARTMENT_BUILDING ON (BUILDING.ID_BUILD = DEPARTMENT_BUILDING.ID_BUILD) "+
          "WHERE DEPARTMENT_BUILDING.ID_DEPARTMENT=? ");
        jdbc.setInt(1, stage.getDepartment().getId());
        jdbc.execute();

        //starting get result
        while(jdbc.next())
          {
            if(buildings==null)
              {
                buildings = new ArrayList<BuildingDto>();
              }

            build = new BuildingDto();

            build.setId(jdbc.getInt("ID_BUILD")); //get building id


            buildings.add(build); //add it to the array list

            build = null;

          }


        //get the default location
        for(int i = 0; i<buildings.size(); i++) //for every building
          {

            //get all location of this building
            jdbc.setCommand("SELECT ID_LOC,RESERVED_NUMBER,CODE_LOC,CODE_FLOOR,CAP_LOC,ID_LOC_TYPE FROM LOCATION WHERE ID_BUILD = ? ");
            jdbc.setInt(1, buildings.get(i).getId());
            jdbc.execute();

            //start get result
            while(jdbc.next())
              {
                if(locations==null)
                  {
                    locations = new ArrayList<>();
                  }

                loc = new LocationDto();

                loc.setId(jdbc.getInt("ID_LOC")); //get location id
                loc.setFloor(new FloorDto(jdbc.getString("CODE_FLOOR"))); //get floor code
                loc.setCode(jdbc.getString("CODE_LOC")); //get location code
                loc.setCapacity(jdbc.getInt("CAP_LOC")); //get location capacity
                //get locatoin type id
                loc.setType(new LocationTypeDto());
                loc.getType().setId(jdbc.getInt("ID_LOC_TYPE"));
                //get the reserved number
                loc.setReserved_number(jdbc.getInt("RESERVED_NUMBER"));

                locations.add(loc);

                loc = null;

              }


            /*get the location which has the less number of reserved slots
          /and its capacity >= number of students of stage
          and its type match the default location type of this schedule*/
            for(int j = 0; locations!=null&&j<locations.size(); j++)
              {

                if(locations.get(j).getCapacity()>=stage.getNum_of_std()&&
                  locations.get(j).getType().getId()==
                  schedule.getDefault_location_type().getId())
                  {
                    if(default_location==null||
                      locations.get(j).getReserved_number()<
                      default_location.getReserved_number())
                      //assign it as default location for this schedule
                      default_location = locations.get(j);

                    else if(locations.get(j).getReserved_number()==
                      default_location.getReserved_number())
                      {
                        //get floor IDs for the assigned location and for it in the list
                        String[] code1 =
                          locations.get(j).getFloor().getCode().split("-");
                        String[] code2 =
                          default_location.getFloor().getCode().split("-");

                        /*if there is location in list
                     * which located on floor below than the assigned location*/
                        if(Integer.parseInt(code1[1])<
                          Integer.parseInt(code2[1]))
                          //assign it as default location for this schedule
                          default_location = locations.get(j);

                      }
                  }

              }

            locations = null; //clear location list
          }
      }
    catch(SQLException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return default_location;
  }

  /**Find free locations for a specific time (slot)
   * @param take slot dto object
   * @return free locations
   * */
  public List<LocationDto> freelocations(SlotDto slot)
  {

    List<LocationDto> locations = null;

    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //search on db
        jdbc.setCommand("SELECT ID_LOC, CODE_LOC, CODE_FLOOR, CAP_LOC, ID_LOC_TYPE "+
          "FROM LOCATION WHERE CAP_LOC >= ?  AND ID_LOC NOT IN ( "+
          " SELECT ID_LOC FROM SLOT WHERE DAY=? AND SLOT_NUMBER=? AND ID_LOC IS NOT NULL) ");
        jdbc.setInt(1, slot.getStudent_number());
        jdbc.setString(2, slot.getDay());
        jdbc.setInt(3, slot.getNum());
        jdbc.execute();

        LocationDto location = null;

        //start get result
        while(jdbc.next())
          {
            if(locations==null)
              {
                locations = new ArrayList<>();
              }

            location = new LocationDto();

            location.setId(jdbc.getInt("ID_LOC")); //get location id
            location.setFloor(new FloorDto(jdbc.getString("CODE_FLOOR"))); //get floor code
            location.setCode(jdbc.getString("CODE_LOC")); //get location code
            location.setCapacity(jdbc.getInt("CAP_LOC")); //get location capacity
            //get locatoin type id
            location.setType(new LocationTypeDto());
            location.getType().setId(jdbc.getInt("ID_LOC_TYPE"));
            locations.add(location);

            location = null;

          }
        return locations;
      }
    catch(SQLException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return locations;

  }


  /**Search for free locations for slot in schedule
   *  which match the PLT for this slot and buildings of this department stage
   * used in location auto assignment
   * @param schedule,slot
   * @return location list
   * */
  public List<LocationDto> match_building_and_type(ScheduleDto schedule,
    SlotDto slot)
  {

    List<BuildingDto> buildings = new ArrayList<BuildingDto>();
    StageDto stage = null;

    LocationDto loc = null;
    BuildingDto build = null;
    List<LocationDto> locations = null;


    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //get stage of this schedule
        jdbc.setCommand("SELECT STAGE.CODE_STAGE,STAGE.ID_DEPARTMENT , STAGE.NO_OF_STD  "+
          "FROM STAGE JOIN SCHEDULE_STAGE ON (STAGE.CODE_STAGE =SCHEDULE_STAGE.CODE_STAGE) "+
          "WHERE SCHEDULE_STAGE.ID_SCHEDULE=? ");
        jdbc.setInt(1, schedule.getId());
        jdbc.execute();

        //starting get result
        while(jdbc.next())
          {

            stage = new StageDto();

            stage.setCode(jdbc.getString("CODE_STAGE")); //get stage code

            //get number of student of this stage
            stage.setNum_of_std(jdbc.getInt("NO_OF_STD"));

            //get department of this stage
            stage.setDepartment(new DepartmentDto());
            stage.getDepartment().setId(jdbc.getInt("ID_DEPARTMENT"));


          }

        //get building/s of schedule department
        jdbc.setCommand("SELECT BUILDING.ID_BUILD FROM BUILDING  "+
          "JOIN DEPARTMENT_BUILDING ON (BUILDING.ID_BUILD = DEPARTMENT_BUILDING.ID_BUILD) "+
          "WHERE DEPARTMENT_BUILDING.ID_DEPARTMENT=? ");
        jdbc.setInt(1, stage.getDepartment().getId());
        jdbc.execute();

        //starting get result
        while(jdbc.next())
          {
            if(buildings==null)
              {
                buildings = new ArrayList<BuildingDto>();
              }

            build = new BuildingDto();

            build.setId(jdbc.getInt("ID_BUILD")); //get building id


            buildings.add(build); //add it to the array list

            build = null;

          }


        //get all locations in this building/s
        for(int i = 0; i<buildings.size(); i++) //for every building
          {

            /*get free locations in this building
             * which it type match the slot PLT and location capacity >= student number of slot
             * and this locations are free for this slot time */
            jdbc.setCommand("SELECT ID_LOC, CODE_LOC, CODE_FLOOR, CAP_LOC, ID_LOC_TYPE "+
              "FROM LOCATION WHERE ID_BUILD = ? AND ID_LOC_TYPE=? AND CAP_LOC >= ?  AND ID_LOC NOT IN ( "+
              " SELECT ID_LOC FROM SLOT WHERE DAY=? AND SLOT_NUMBER=? AND ID_LOC IS NOT NULL) ");
            jdbc.setInt(1, buildings.get(i).getId());
            jdbc.setInt(2, slot.getPlt().getId());
            jdbc.setInt(3, slot.getStudent_number());
            jdbc.setString(4, slot.getDay());
            jdbc.setInt(5, slot.getNum());
            jdbc.execute();

            //start get result
            while(jdbc.next())
              {
                if(locations==null)
                  {
                    locations = new ArrayList<>();
                  }

                loc = new LocationDto();

                loc.setId(jdbc.getInt("ID_LOC")); //get location id
                loc.setFloor(new FloorDto(jdbc.getString("CODE_FLOOR"))); //get floor code
                loc.setCode(jdbc.getString("CODE_LOC")); //get location code
                loc.setCapacity(jdbc.getInt("CAP_LOC")); //get location capacity
                //get locatoin type id
                loc.setType(new LocationTypeDto());
                loc.getType().setId(jdbc.getInt("ID_LOC_TYPE"));
                locations.add(loc);

                loc = null;

              }

          }


        return locations;

      }
    catch(SQLException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return locations;
  }

  /**Search for locations  for slot in schedule
   * which match the PLT for this slot
   * used in location auto assignment
   * @param slot
   * @return location list
   * */
  public List<LocationDto> match_type(SlotDto slot)
  {


    LocationDto loc = null;
    List<LocationDto> locations = null;


    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        /*get free locations
               * which it type match the slot PLT and location capacity >= student number of slot
               * and this locations are free for this slot time */
        jdbc.setCommand("SELECT ID_LOC, CODE_LOC, CODE_FLOOR, CAP_LOC, ID_LOC_TYPE "+
          "FROM LOCATION WHERE ID_LOC_TYPE=? AND CAP_LOC >= ?  AND ID_LOC NOT IN ( "+
          " SELECT ID_LOC FROM SLOT WHERE DAY=? AND SLOT_NUMBER=? AND ID_LOC IS NOT NULL) ");
        jdbc.setInt(1, slot.getPlt().getId());
        jdbc.setInt(2, slot.getStudent_number());
        jdbc.setString(3, slot.getDay());
        jdbc.setInt(4, slot.getNum());
        jdbc.execute();

        //start get result
        while(jdbc.next())
          {
            if(locations==null)
              {
                locations = new ArrayList<>();
              }

            loc = new LocationDto();

            loc.setId(jdbc.getInt("ID_LOC")); //get location id
            loc.setFloor(new FloorDto(jdbc.getString("CODE_FLOOR"))); //get floor code
            loc.setCode(jdbc.getString("CODE_LOC")); //get location code
            loc.setCapacity(jdbc.getInt("CAP_LOC")); //get location capacity
            //get locatoin type id
            loc.setType(new LocationTypeDto());
            loc.getType().setId(jdbc.getInt("ID_LOC_TYPE"));

            locations.add(loc);

            loc = null;

          }


        return locations;

      }
    catch(SQLException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return locations;

  }


  /**Search for locations  for slot in schedule
   * which match the buildings of this department stage
   * used in location auto assignment
   * @param schedule,slot
   * @return location list
   * */
  public List<LocationDto> match_building(ScheduleDto schedule,
    SlotDto slot)
  {

    List<BuildingDto> buildings = new ArrayList<BuildingDto>();
    StageDto stage = null;

    LocationDto loc = null;
    BuildingDto build = null;
    List<LocationDto> locations = null;


    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //get stage of this schedule
        jdbc.setCommand("SELECT STAGE.CODE_STAGE,STAGE.ID_DEPARTMENT , STAGE.NO_OF_STD  "+
          "FROM STAGE JOIN SCHEDULE_STAGE ON (STAGE.CODE_STAGE =SCHEDULE_STAGE.CODE_STAGE) "+
          "WHERE SCHEDULE_STAGE.ID_SCHEDULE=? ");
        jdbc.setInt(1, schedule.getId());
        jdbc.execute();

        //starting get result
        while(jdbc.next())
          {

            stage = new StageDto();

            stage.setCode(jdbc.getString("CODE_STAGE")); //get stage code

            //get number of student of this stage
            stage.setNum_of_std(jdbc.getInt("NO_OF_STD"));

            //get department of this stage
            stage.setDepartment(new DepartmentDto());
            stage.getDepartment().setId(jdbc.getInt("ID_DEPARTMENT"));


          }

        //get building/s of schedule department
        jdbc.setCommand("SELECT BUILDING.ID_BUILD FROM BUILDING  "+
          "JOIN DEPARTMENT_BUILDING ON (BUILDING.ID_BUILD = DEPARTMENT_BUILDING.ID_BUILD) "+
          "WHERE DEPARTMENT_BUILDING.ID_DEPARTMENT=? ");
        jdbc.setInt(1, stage.getDepartment().getId());
        jdbc.execute();

        //starting get result
        while(jdbc.next())
          {
            if(buildings==null)
              {
                buildings = new ArrayList<BuildingDto>();
              }

            build = new BuildingDto();

            build.setId(jdbc.getInt("ID_BUILD")); //get building id


            buildings.add(build); //add it to the array list

            build = null;

          }


        //get all locations in this building/s
        for(int i = 0; i<buildings.size(); i++) //for every building
          {

            /*get free locations in this building
               * which it capacity >= student number of slot
               * and this locations are free for this slot time */
            jdbc.setCommand("SELECT ID_LOC, CODE_LOC, CODE_FLOOR, CAP_LOC, ID_LOC_TYPE "+
              "FROM LOCATION WHERE ID_BUILD = ?  AND CAP_LOC >= ?  AND ID_LOC NOT IN ( "+
              " SELECT ID_LOC FROM SLOT WHERE DAY=? AND SLOT_NUMBER=? AND ID_LOC IS NOT NULL) ");
            jdbc.setInt(1, buildings.get(i).getId());
            jdbc.setInt(2, slot.getStudent_number());
            jdbc.setString(3, slot.getDay());
            jdbc.setInt(4, slot.getNum());
            jdbc.execute();

            //start get result
            while(jdbc.next())
              {
                if(locations==null)
                  {
                    locations = new ArrayList<>();
                  }

                loc = new LocationDto();

                loc.setId(jdbc.getInt("ID_LOC")); //get location id
                loc.setFloor(new FloorDto(jdbc.getString("CODE_FLOOR"))); //get floor code
                loc.setCode(jdbc.getString("CODE_LOC")); //get location code
                loc.setCapacity(jdbc.getInt("CAP_LOC")); //get location capacity
                //get locatoin type id
                loc.setType(new LocationTypeDto());
                loc.getType().setId(jdbc.getInt("ID_LOC_TYPE"));

                locations.add(loc);

                loc = null;

              }

          }


        return locations;

      }
    catch(SQLException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return locations;

  }

  /**Check if a specific location is free in a specific time/slot
   * @param location,slot object
   * @return true if location free, false if not  */
  public boolean freeSlot(LocationDto location, SlotDto slot)
  {


    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //search in slot table if this location assigned to slot in the same time
        jdbc.setCommand("SELECT ID_LOC FROM SLOT WHERE DAY = ? AND SLOT_NUMBER=? AND ID_LOC IS NOT NULL");
        jdbc.setString(1, slot.getDay());
        jdbc.setInt(2, slot.getNum());
        jdbc.execute();


        while(jdbc.next())
          {

            return false; //if any result found, then location is not free => return false
          }

        return true; // if nothing found, location is free => return true

      }
    catch(SQLException e)
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


  /**Get location filterd by building or location type or both
   * used for location occupancy panel
   * @param building and location type
   * @return location list */
  public List<LocationDto> search_type_building(BuildingDto building,
    LocationTypeDto type)
  {

    List<LocationDto> locations = null;

    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        if(type!=null)
          { 
            //get location type id
            jdbc.setCommand("SELECT ID_LOC_TYPE FROM LOCATION_TYPE  WHERE CODE_LOC_TYPE=? ");
            jdbc.setString(1, type.getCode());
            jdbc.execute();

            while(jdbc.next())
              {
                type.setId(jdbc.getInt("ID_LOC_TYPE"));
              }
          }

        if(building!=null)
          { //get building id
            jdbc.setCommand("SELECT ID_BUILD FROM BUILDING  WHERE CODE_BUILD=? ");
            jdbc.setString(1, building.getCode());
            jdbc.execute();

            while(jdbc.next())
              {
                building.setId(jdbc.getInt("ID_BUILD"));
              }
          }


        if(building==null&&
          type==null) //if both is null select all locations
          {

            //search on db
            jdbc.setCommand("SELECT ID_LOC, CODE_LOC FROM LOCATION");
            jdbc.execute();

            LocationDto location = null;

            //start get result
            while(jdbc.next())
              {
                if(locations==null)
                  {
                    locations = new ArrayList<>();
                  }
                location = new LocationDto();

                location.setId(jdbc.getInt("ID_LOC")); //get location id
                location.setCode(jdbc.getString("Code_LOC")); //get location code

                locations.add(location);
                location = null;
              }
          }
        
        
        else if(type==null) //filter only by given building
          {
            //search on db
            jdbc.setCommand("SELECT ID_LOC, CODE_LOC FROM LOCATION  WHERE ID_BUILD=? ");
            jdbc.setInt(1, building.getId());
            jdbc.execute();

            LocationDto location = null;

            //start get result
            while(jdbc.next())
              {
                if(locations==null)
                  {
                    locations = new ArrayList<>();
                  }
                location = new LocationDto();

                location.setId(jdbc.getInt("ID_LOC")); //get location id
                location.setCode(jdbc.getString("Code_LOC")); //get location code

                locations.add(location);
                location = null;
              }
          }

        else if(building==null) //filter only by given type
          {
            //search on db
            jdbc.setCommand("SELECT ID_LOC, CODE_LOC FROM LOCATION  WHERE ID_LOC_TYPE=? ");
            jdbc.setInt(1, type.getId());
            jdbc.execute();

            LocationDto location = null;

            //start get result
            while(jdbc.next())
              {
                if(locations==null)
                  {
                    locations = new ArrayList<>();
                  }
                location = new LocationDto();

                location.setId(jdbc.getInt("ID_LOC")); //get location id
                location.setCode(jdbc.getString("Code_LOC")); //get location code

                locations.add(location);
                location = null;
              }

          }

        else //else both are given
          {
            //search on db
            jdbc.setCommand("SELECT ID_LOC, CODE_LOC FROM LOCATION  WHERE ID_LOC_TYPE=? AND ID_BUILD=? ");
            jdbc.setInt(1, type.getId());
            jdbc.setInt(2, building.getId());
            jdbc.execute();

            LocationDto location = null;

            //start get result
            while(jdbc.next())
              {
                if(locations==null)
                  {
                    locations = new ArrayList<>();
                  }
                location = new LocationDto();

                location.setId(jdbc.getInt("ID_LOC")); //get location id
                location.setCode(jdbc.getString("Code_LOC")); //get location code
                locations.add(location);
                location = null;
              }

          }


      }
    catch(SQLException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return locations;

  }

  /**get free locaton filtered by building and type
   * @param slot,location type,building
   * @return location list */
  public List<LocationDto> freelocationsfiltered(SlotDto slot,
    BuildingDto b, LocationTypeDto l)
  {

    List<LocationDto> locs = null;

    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("select ID_BUILD FROM BUILDING WHERE CODE_BUILD = ? ");
        jdbc.setString(1, b.getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            b.setId(jdbc.getInt("ID_BUILD"));
          }

        jdbc.setCommand("select ID_LOC_TYPE FROM LOCATION_TYPE WHERE CODE_LOC_TYPE = ? ");
        jdbc.setString(1, l.getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            l.setId(jdbc.getInt("ID_LOC_TYPE"));

          }
        //search on db
        jdbc.setCommand(" SELECT ID_LOC ,NAME_LOC ,CODE_FLOOR FROM LOCATION "+
          "WHERE ID_BUILD=? AND ID_LOC_TYPE=? AND ID_LOC NOT IN  "+
          "(SELECT L.ID_LOC FROM LOCATION L JOIN SLOT S ON (S.ID_LOC = L.ID_LOC) "+
          "WHERE S.DAY=? AND S.CODE_SCHEDULE_SLOT=? ) ORDER BY ID_LOC ");
        jdbc.setInt(1, b.getId());
        jdbc.setInt(2, l.getId());
        jdbc.setString(3, slot.getDay());
        jdbc.setString(4, slot.getCode());
        jdbc.execute();
        LocationDto loc = null;

        //start get result
        while(jdbc.next())
          {
            if(locs==null)
              {
                locs = new ArrayList<>();
              }

            loc = new LocationDto();

            loc.setId(jdbc.getInt("ID_LOC")); //get location id
            loc.setFloor(new FloorDto());
            loc.getFloor().setCode(jdbc.getString("CODE_FLOOR")); //get floor code
            loc.setName(jdbc.getString("NAME_LOC"));
            locs.add(loc);
            loc = null;
          }
        return locs;
      }
    catch(SQLException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return locs;

  }

}


