package com.fym.lta.DAO;

import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;


public class StageDaoImpl implements StageDao
{

  private UserDto user = null;

  public StageDaoImpl(UserDto user)
  {
    this.user = user;
  }

  public Boolean createNew(StageDto st, UserDto user)
  {
    try
      {
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("insert into STAGE (NO_STAGE , CODE_STAGE , NO_OF_STD , ID_DEPARTMENT , INSERTED_BY , INSERTED_AT ) values(?,?,?,?,?, SYSDATE )");
        jdbc.setString(1, st.getNumber());
        jdbc.setString(2, st.getCode());
        jdbc.setInt(3, st.getNum_of_std());
        jdbc.setInt(4, st.getDepartment().getId());
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

  public Boolean isExist(StageDto st)
  {
    boolean flag = false;
    try
      {
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("select CODE_STAGE from STAGE where CODE_STAGE = ? ");
        jdbc.setString(1, st.getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            flag = true;
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

  public List<StageDto> searchFor(StageDto st)
  {
    List<StageDto> stages = null;
    try
      {

        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("select NO_STAGE, CODE_STAGE , NO_OF_STD , ID_DEPARTMENT "+
          "from STAGE where NO_STAGE=? OR CODE_STAGE= ? OR NO_OF_STD=? OR ID_DEPARTMENT=? ");

        try
          {
            jdbc.setInt(1, Integer.parseInt(st.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, st.getSearch());
        try
          {
            jdbc.setInt(3, Integer.parseInt(st.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(3, -1);
          }
        try
          {
            jdbc.setInt(4, Integer.parseInt(st.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(4, -1);
          }
        jdbc.execute();

        StageDto s = null;
        while(jdbc.next())
          {
            if(stages==null)
              {
                stages = new ArrayList<>();
              }
            s = new StageDto();

            s.setNumber(jdbc.getString("NO_STAGE"));
            s.setCode(jdbc.getString("CODE_STAGE"));
            s.setNum_of_std(jdbc.getInt("NO_OF_STD"));
            s.setDepartment(new DepartmentDto(jdbc.getInt("ID_DEPARTMENT")));
            stages.add(s);
            s = null;
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return stages;
  }

  public Boolean delete(StageDto st)
  {

    try
      {

        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("delete from STAGE where CODE_STAGE = ?");
        jdbc.setString(1, st.getCode());
        jdbc.execute();
        return true;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  public List<StageDto> viewAll()
  {
    List<StageDto> stages = null;
    try
      {

        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("select DEPARTMENT.ID_DEPARTMENT, STAGE.NO_STAGE , STAGE.CODE_STAGE , STAGE.NO_OF_STD , DEPARTMENT.NAME_DEPARTMENT  "+
          "from STAGE , DEPARTMENT "+
          "WHERE STAGE.ID_DEPARTMENT = DEPARTMENT.ID_DEPARTMENT "+
          "order by STAGE.NO_STAGE ");
        jdbc.execute();

        StageDto st = null;
        while(jdbc.next())
          {
            if(stages==null)
              {
                stages = new ArrayList<>();
              }
            st = new StageDto();
            st.setNumber(jdbc.getString("NO_STAGE"));
            st.setCode(jdbc.getString("CODE_STAGE"));
            st.setNum_of_std(jdbc.getInt("NO_OF_STD"));
            st.setDepartment(new DepartmentDto(jdbc.getInt("ID_DEPARTMENT")));
            stages.add(st);
            st = null;
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return stages;
  }

  public Boolean update(StageDto st, UserDto user)
  {
    try
      {

        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("UPDATE STAGE "+
          "set NO_STAGE=? , NO_OF_STD=? , ID_DEPARTMENT=? ,UPDATED_BY=? , UPDATED_AT = SYSDATE "+
          "where CODE_STAGE = ?");
        jdbc.setString(1, st.getNumber());
        jdbc.setInt(2, st.getNum_of_std());
        jdbc.setInt(3, st.getDepartment().getId());
        jdbc.setInt(4, user.getId());
        jdbc.setString(5, st.getCode());
        jdbc.execute();
        return true;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

}
