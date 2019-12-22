package com.fym.lta.DAO;

import com.fym.lta.DTO.ScheduleSlotDto;
import com.fym.lta.DTO.UserDto;

import javax.sql.rowset.JdbcRowSet;

public class ScheduleSlotDaoImpl implements ScheduleSlotDao
{
  private UserDto user = null;

  public ScheduleSlotDaoImpl(UserDto user)
  {
    this.user = user;
  }

  public Boolean update(ScheduleSlotDto ss)
  {
    try
      {

        // start connection with database using resourses
        JdbcRowSet jdbc = user.getConnect();


        // database query to perform update action
        jdbc.setCommand("update SCHEDULE_SLOTS "+
          "set CODE_SCHEDULE_SLOT=? where ID_SCHEDULE = ? ");

        // passing object attributes as inputs to the query
        jdbc.setString(1, ss.getSlot_code());
        jdbc.setInt(2, ss.getSchedule_id());
        // query execution
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

  public Boolean isExist(ScheduleSlotDto ss)
  {
    boolean existFlag = false;
    try
      {

        //open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        //query to select the record using its id
        jdbc.setCommand("select ID_SCHEDULE , CODE_SCHEDULE_SLOT from SCHEDULE_SLOTS where ID_SCHEDULE=? AND CODE_SCHEDULE_SLOT=? ");

        //pass id to query
        try
          {
            jdbc.setInt(1, ss.getSchedule_id());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, ss.getSlot_code());

        jdbc.execute();
        while(jdbc.next())
          {

            // set flag to true if record is found then return falg
            existFlag = true;
            break;
          }
      }
    catch(java.sql.SQLException e)
      {

        //show message if any sql error occured
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return existFlag;
  }

  public Boolean createNew(ScheduleSlotDto ss)
  {
    try
      {

        //open connection with dataase using resources
        JdbcRowSet jdbc = user.getConnect();


        //query to insert staff id and its user id in database
        jdbc.setCommand("insert into SCHEDULE_SLOTS ( ID_SCHEDULE , CODE_SCHEDULE_SLOT  ) VALUES ( ? , ? ) ");

        //pass objects from dto object to query as input to be inserted
        try
          {
            jdbc.setInt(1, ss.getSchedule_id());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, ss.getSlot_code());

        //query execution
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

  public Boolean delete(ScheduleSlotDto ss)
  {
    try
      {

        // open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        // query to delete record using its id
        jdbc.setCommand("delete from SCHEDULE_SLOTS where CODE_SCHEDULE_SLOT = ? ");

        //pass id from dto object parameter to the query
        jdbc.setString(1, ss.getSlot_code());
        // query execution
        jdbc.execute();
        return true;
      }
    catch(java.sql.SQLException e)
      {

        //show message when any sql error occurs
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
