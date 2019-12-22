package com.fym.lta.DAO;

import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class ScheduleDaoImpl implements ScheduleDao {
  private UserDto user = null;

  public ScheduleDaoImpl(UserDto user)
  {
    this.user = user;
  }

  /**add new schedule
   * @param schedule,user
   * @return true if it inserted successfuly, false if not
   */
    public Boolean createNew(ScheduleDto sch, UserDto user) {
        try
        {
          
           //start new database connection
            JdbcRowSet jdbc = user.getConnect();
            
            
            
            //insert schedule information into database
            jdbc.setCommand("insert into SCHEDULE (ID_SCHEDULE , NO_OF_SLOTS , TERM , INSERTED_BY , INSERTED_AT  , PATH_SCH  ) values(?,?,?,?, SYSDATE ,?)");
            jdbc.setInt(1,sch.getId());
            jdbc.setInt(2,sch.getSlot_num());
            jdbc.setInt(3,sch.getTerm());
            jdbc.setInt(4,user.getId());
            jdbc.setString(5, sch.getFile_path());
            jdbc.execute();
          
            return true;
        }
        catch(java.sql.SQLException e){
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

  
  /**search for is schedule exist or not
     * @param location
     * @return boolean value
     */
      public Boolean isExist(ScheduleDto sch) {
          boolean flag = false;
                         try(JdbcRowSet jdbc = RowSetProvider.newFactory().createJdbcRowSet();) 
                         {
                           
                             //start database connection
                             jdbc.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
                             jdbc.setUsername("lta");
                             jdbc.setPassword("lta"); 
                           
                             //search for this schedule in database
                             jdbc.setCommand("SELECT ID_SCHEDULE FROM SCHEDULE WHERE PATH_SCH = ?");
                             jdbc.setString(1, sch.getFile_path());
                             jdbc.execute();
                            
                           while(jdbc.next()){
                                  flag = true;
                                  break;
                                  }
                              return flag;
                          } 
                             catch(java.sql.SQLException e){
                                 return false;
                                 }
                         
          catch(Exception e){
                         e.printStackTrace();
                         return false;}
      }

  /**delete schedule from database
   * @param schedule
   * @return true if it deleted successfuly, false if no
   */
  public Boolean delete(StageDto stage, DepartmentDto department)
  {
    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();

        // SQL query to get department code from its name
        jdbc.setCommand("SELECT CODE_DEPARTMENT FROM DEPARTMENT WHERE NAME_DEPARTMENT=? ");

        // pass the department name to query and execute
        jdbc.setString(1, department.getName());
        jdbc.execute();

        // set the department code from the resultset
        while(jdbc.next())
          {
            department.setCode(jdbc.getString("CODE_DEPARTMENT"));
          }


        // set stage code
        String stageCode = department.getCode()+"-"+stage.getNumber();
        stage.setCode(stageCode);

        /** get schedule id of the stage **/

        // SQL query to get schedule id of the stage
        jdbc.setCommand("select ID_SCHEDULE FROM SCHEDULE_STAGE WHERE CODE_STAGE=?");

        // pass stage code to the query and execute
        jdbc.setString(1, stage.getCode());
        jdbc.execute();

        // identify schedule id and set it from the resultset
        int scheduleId = 0;
        while(jdbc.next())
          {
            scheduleId = jdbc.getInt("ID_SCHEDULE");
          }

        /* delete schedule from schedule stage table */
        jdbc.setCommand("delete from SCHEDULE_STAGE WHERE ID_SCHEDULE=? ");
        jdbc.setInt(1, scheduleId);
        jdbc.execute();

        /* get slots of the schedule to be deleted */
        List<SlotDto> slots = null;
        SlotDto slot = null;

        jdbc.setCommand("select CODE_SCHEDULE_SLOT from SCHEDULE_SLOTS where ID_SCHEDULE=? ");
        jdbc.setInt(1, scheduleId);
        jdbc.execute();

        /* store slots in list */
        while(jdbc.next())
          {
            if(slots==null)
              slots = new ArrayList<>();

            slot = new SlotDto();

            slot.setCode(jdbc.getString("CODE_SCHEDULE_SLOT"));
            slots.add(slot);

            slot = null;
          }


        /** delete slots of list from Slot table in database **/
        for(int i = 0; slots!=null&&i<slots.size(); i++)
          {
            jdbc.setCommand("delete from STAFF_SLOT where SLOT_CODE = ? ");
            jdbc.setString(1, slots.get(i).getCode());
            jdbc.execute();


            jdbc.setCommand("delete from SCHEDULE_SLOTS where CODE_SCHEDULE_SLOT=? ");
            jdbc.setString(1, slots.get(i).getCode());
            jdbc.execute();

            jdbc.setCommand("delete from SLOT where CODE_SCHEDULE_SLOT=? ");
            jdbc.setString(1, slots.get(i).getCode());
            jdbc.execute();
          }

        /* delete schedule from schedule table in database */
        jdbc.setCommand("delete from SCHEDULE where ID_SCHEDULE=? ");
        jdbc.setInt(1, scheduleId);
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

  /**Update information for an existing schedule
   * @param schedule object, user object
   * @return true if it success false if not */
  public Boolean update(ScheduleDto sch, UserDto user)
  {
    try
      {
        JdbcRowSet jdbc = user.getConnect();

        jdbc.setCommand("select ID_LOC_TYPE from LOCATION_TYPE where CODE_LOC_TYPE=? ");
        jdbc.setString(1, sch.getDefault_location_type().getCode());
        jdbc.execute();
      
        while(jdbc.next())
          {
            sch.getDefault_location_type().setId(jdbc.getInt("ID_LOC_TYPE"));
          }

        jdbc.setCommand("UPDATE SCHEDULE "+
          "SET NO_OF_SLOTS=? , TERM=? , UPDATED_BY=?, DEFAULT_LOC_TYPE=? , UPDATED_AT=SYSDATE "+
          "WHERE ID_SCHEDULE =? ");

        try
          {
            jdbc.setInt(1, sch.getSlot_num());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }

        try
          {
            jdbc.setInt(2, sch.getTerm());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(2, -1);
          }
        jdbc.setInt(3, user.getId());

        try
          {
            jdbc.setInt(4, sch.getDefault_location_type().getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(4, -1);
          }
        try
          {
            jdbc.setInt(5, sch.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(5, -1);
          }
        jdbc.execute();
        return true;
        //}catch(java.sql.SQLException e){
        //  return false;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  /** get the max schedule id to generate a new schedule
   * @return the max id
   * */
  public int getMaxId(){
    int max_id = 0;
    try
      {

        // start connection

        JdbcRowSet jdbc = user.getConnect();
        
        
        jdbc.setCommand("select ID_SCHEDULE FROM SCHEDULE ");
        jdbc.execute();
        while(jdbc.next())
          {
            if(jdbc.getInt("ID_SCHEDULE")>max_id)
              max_id = jdbc.getInt("ID_SCHEDULE");
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return max_id;
        }

 /** Find number of slots for a specific schedule
   * @param take schedule object
   * @return return int value of number of slots */
  public boolean numOfSlots(ScheduleDto sch){
        try
         {
            
            // start connection 

             JdbcRowSet jdbc = user.getConnect();
             
             
             jdbc.setCommand("select SCHEDULE_SLOTS.CODE_SCHEDULE_SLOT FROM SCHEDULE_SLOTS , SCHEDULE " +
                 "WHERE SCHEDULE.ID_SCHEDULE = SCHEDULE_SLOTS.ID_SCHEDULE ");
            jdbc.setInt(1,sch.getId());
            jdbc.execute();
            
             int num = 0;
            while(jdbc.next()){
                num ++ ;
            }
            
            jdbc.setCommand("update SCHEDULE set NO_OF_SLOTS =? WHERE ID_SCHEDULE=? ");
             jdbc.setInt(1,num);
             jdbc.setInt(2,sch.getId());
            
             jdbc.execute();
                 return true;

         }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

  /** get the schedule id
   * @param schedule
   * @return the same schedule after assign its id */
  public ScheduleDto getID(ScheduleDto schedule){


    try
      {
        // start connection
        JdbcRowSet jdbc = user.getConnect();
        
        

        //get the id from schedule table
        jdbc.setCommand("SELECT S.ID_SCHEDULE FROM SCHEDULE S JOIN SCHEDULE_STAGE SS ON (S.ID_SCHEDULE = SS.ID_SCHEDULE) "+
          "JOIN STAGE ON (STAGE.CODE_STAGE = SS.CODE_STAGE) JOIN DEPARTMENT D ON (D.ID_DEPARTMENT = STAGE.ID_DEPARTMENT) "+
          "WHERE STAGE.NO_STAGE=? AND D.NAME_DEPARTMENT=? ");
        jdbc.setString(2, schedule.getDepartment().getName());
        jdbc.setString(1, schedule.getStage().getNumber());
        jdbc.execute();

        while(jdbc.next())
          {
            schedule.setId(jdbc.getInt("ID_SCHEDULE"));
            System.out.println(schedule.getId());
          }
      
      }

    catch(Exception e)
      {
        e.printStackTrace();
      }
    
    return schedule;
    
  }

  /** get the schedule id
   * @param schedule path
   * @return  id */
  public int getScheduleID(String path)
  {
    int id = 0;
    try
      {

        JdbcRowSet jdbc = user.getConnect();
        
        
        jdbc.setCommand("select ID_SCHEDULE from SCHEDULE where PATH_SCH = ? ");
        jdbc.setString(1, path);
        jdbc.execute();
        while(jdbc.next())
          {
            id = jdbc.getInt("ID_SCHEDULE");
          }
      }

    catch(Exception e)
      {
        e.printStackTrace();
      }
    return id;
  }

  @Override
  public List<ScheduleDto> viewAll()
  {
    // TODO Implement this method
    List<ScheduleDto> schedules = null;
    try
      {
        JdbcRowSet jdbc = user.getConnect();
        
        
        jdbc.setCommand("select ID_SCHEDULE , NO_OF_SLOTS , TERM "+
          "from SCHEDULE order by ID_SCHEDULE ");
        jdbc.execute();

        ScheduleDto s = null;
        while(jdbc.next())
          {
            if(schedules==null)
              {
                schedules = new ArrayList<>();
              }
            s = new ScheduleDto();
            s.setId(jdbc.getInt("ID_SCHEDULE"));
            s.setSlot_num(jdbc.getInt("NO_OF_SLOTS"));
            s.setTerm(jdbc.getInt("TERM"));
            schedules.add(s);
            s = null;

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
    return schedules;
  }

  @Override
  public List<ScheduleDto> searchFor(ScheduleDto sch)
  {
    // TODO Implement this method
    List<ScheduleDto> schedules = null;
    try
      {
        JdbcRowSet jdbc = user.getConnect();
        
        

        jdbc.setCommand("select ID_SCHEDULE, NO_OF_SLOTS , TERM "+
          "from SCHEDULE "+
          "where ID_SCHEDULE=? OR NO_OF_SLOTS=? OR TERM=? ");
        try
          {
            jdbc.setInt(1, Integer.parseInt(sch.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        try
          {
            jdbc.setInt(2, Integer.parseInt(sch.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(2, -1);
          }
        try
          {
            jdbc.setInt(3, Integer.parseInt(sch.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(3, -1);
          }
        jdbc.execute();

        ScheduleDto s = null;
        while(jdbc.next())
          {
            if(schedules==null)
              {
                schedules = new ArrayList<>();
              }
            s = new ScheduleDto();
            s.setId(jdbc.getInt("ID_SCHEDULE"));
            s.setSlot_num(jdbc.getInt("NO_OF_SLOTS"));
            s.setTerm(jdbc.getInt("TERM"));
            schedules.add(s);
            s = null;
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

    return schedules;
  }

}
