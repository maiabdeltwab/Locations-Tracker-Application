package com.fym.lta.DAO;

import com.fym.lta.DTO.StageScheduleDto;
import com.fym.lta.DTO.UserDto;

import javax.sql.rowset.JdbcRowSet;

public class StageScheduleDaoImpl implements StageScheduleDao
{
  private UserDto user = null;


  public StageScheduleDaoImpl(UserDto user)
  {
    this.user = user;
  }

  public Boolean update(StageScheduleDto ss)
  {
    try
      {

        // start connection with database using resourses
        JdbcRowSet jdbc = user.getConnect();


        // database query to perform update action
        jdbc.setCommand("update SCHEDULE_STAGE "+
          "set CODE_STAGE=? where ID_SCHEDULE = ? ");

        // passing object attributes as inputs to the query
        jdbc.setString(1, ss.getStage_code());
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

  public Boolean isExist(StageScheduleDto ss)
  {
    boolean existFlag = false;
    try
      {

        //open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        //query to select the record using its id
        jdbc.setCommand("select ID_SCHEDULE , CODE_STAGE from SCHEDULE_STAGE where ID_SCHEDULE=? AND CODE_STAGE=? ");

        //pass id to query
        try
          {
            jdbc.setInt(1, ss.getSchedule_id());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, ss.getStage_code());

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

  public Boolean createNew(StageScheduleDto ss)
  {
    try
      {

        //open connection with dataase using resources
        JdbcRowSet jdbc = user.getConnect();


        //query to insert staff id and its user id in database
        jdbc.setCommand("insert into SCHEDULE_STAGE ( ID_SCHEDULE , CODE_STAGE  ) VALUES ( ? , ? ) ");

        //pass objects from dto object to query as input to be inserted
        try
          {
            jdbc.setInt(1, ss.getSchedule_id());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, ss.getStage_code());

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

  public Boolean delete(StageScheduleDto ss)
  {
    try
      {

        // open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        // query to delete record using its id
        jdbc.setCommand("delete from SCHEDULE_STAGE where CODE_STAGE = ? ");

        //pass id from dto object parameter to the query
        jdbc.setString(1, ss.getStage_code());
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
