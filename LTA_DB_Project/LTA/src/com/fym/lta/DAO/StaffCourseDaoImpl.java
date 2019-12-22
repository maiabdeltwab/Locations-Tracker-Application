package com.fym.lta.DAO;

import com.fym.lta.DTO.UserDto;
import com.fym.lta.DTO.WorkOnDto;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;

public class StaffCourseDaoImpl implements StaffCourseDao
{
  private UserDto user = null;


  public StaffCourseDaoImpl(UserDto user)
  {
    this.user = user;
  }

  public boolean insert(WorkOnDto staff_course)
  {
    try
      {

        //open connection with dataase using resources
        JdbcRowSet jdbc = user.getConnect();


        //query to insert staff id and his/her course id in database
        jdbc.setCommand("insert into STAFF_COURSE ( ID_COURSE , ID_STAFF , WORK_HOURS_STAFF  ) VALUES ( ? , ? ,? ) ");

        //pass objects from dto object to query as input to be inserted
        try
          {
            jdbc.setInt(1, staff_course.getCourse_id());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        try
          {
            jdbc.setInt(2, staff_course.getStaff_id());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(2, -1);
          }

        try
          {
            jdbc.setInt(3, staff_course.getWork_hrs());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(3, -1);
          }

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

  public boolean delete(WorkOnDto staff_course)
  {
    try
      {

        // open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        // query to delete record using its id
        jdbc.setCommand("delete from STAFF_COURSE where ID_COURSE = ? OR ID_STAFF=? ");

        //pass id from dto object parameter to the query
        try
          {
            jdbc.setInt(1, staff_course.getCourse_id());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        try
          {
            jdbc.setInt(2, staff_course.getStaff_id());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(2, -1);
          }
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

  public boolean isExist(WorkOnDto staff_course)
  {

    boolean existFlag = false;
    try
      {

        //open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        //query to select the record using its id
        jdbc.setCommand("select ID_COURSE , ID_STAFF from STAFF_COURSE where ID_COURSE = ? AND ID_STAFF = ? ");

        //pass id to query
        try
          {
            jdbc.setInt(1, staff_course.getCourse_id());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        try
          {
            jdbc.setInt(2, staff_course.getStaff_id());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(2, -1);
          }
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

        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return existFlag;

  }

  public List<WorkOnDto> viewAll()
  {
    //identify dto list
    List<WorkOnDto> staff_course = null;
    try
      {

        //open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        //query to get data from database
        jdbc.setCommand("select ID_COURSE , ID_STAFF , WORK_HOURS_STAFF from STAFF_COURSE order by ID_STAFF ");
        jdbc.execute();

        // identify dto object then pass database resultset to its fields (if exist)
        WorkOnDto dto = null;
        while(jdbc.next())
          {
            if(staff_course==null)
              staff_course = new ArrayList<>();
            dto = new WorkOnDto();
            dto.setCourse_id(jdbc.getInt("ID_COURSE"));
            dto.setStaff_id(jdbc.getInt("ID_STAFF"));
            dto.setWork_hrs(jdbc.getInt("WORK_HOURS_STAFF"));

            //add object to the list
            staff_course.add(dto);
            dto = null;
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
    return staff_course;
  }

}
