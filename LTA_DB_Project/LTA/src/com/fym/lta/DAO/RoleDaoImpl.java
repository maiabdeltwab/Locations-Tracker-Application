package com.fym.lta.DAO;


import com.fym.lta.DTO.RoleDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;


public class RoleDaoImpl implements RoleDao
{

  private UserDto user = null;

  public RoleDaoImpl(UserDto user)
  {
    this.user = user;
  }

  public Boolean update(RoleDto role, UserDto user)
  {
    try
      {

        JdbcRowSet jdbc = user.getConnect();

        jdbc.setCommand("update ROLE "+
          "set NAME_ROLE=?, DESCRIPTION_ROLE=? , UPDATED_BY=? , UPDATED_AT=SYSDATE "+
          "where ID_ROLE = ? ");
        jdbc.setString(1, role.getName());
        jdbc.setString(2, role.getDescription());
        jdbc.setInt(3, user.getId());
        try
          {
            jdbc.setInt(4, role.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(4, -1);
          }
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

  public Boolean createNew(RoleDto r, UserDto user)
  {
    try
      {

        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand(" insert into ROLE ( ID_ROLE, NAME_ROLE, DESCRIPTION_ROLE ,INSERTED_BY, INSERTED_AT ) values(?, ?, ? , ? , SYSDATE ) ");
        try
          {
            jdbc.setInt(1, r.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, r.getName());
        jdbc.setString(3, r.getDescription());
        jdbc.setInt(4, user.getId());
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

  public Boolean isExist(RoleDto r)
  {
    boolean flag = false;
    try
      {
        JdbcRowSet jdbc = user.getConnect();

        jdbc.setCommand("SELECT ROLE.ID_ROLE FROM ROLE where ROLE.ID_ROLE=?");
        jdbc.setInt(1, r.getId());
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

  public List<RoleDto> viewAll()
  {
    List<RoleDto> roles = null;
    try
      {

        JdbcRowSet jdbc = user.getConnect();

        jdbc.setCommand("select ID_ROLE , NAME_ROLE , DESCRIPTION_ROLE "+
          "from ROLE ");
        jdbc.execute();

        RoleDto role = null;
        while(jdbc.next())
          {
            if(roles==null)
              {
                roles = new ArrayList<>();
              }
            role = new RoleDto();
            role.setId(jdbc.getInt("ID_ROLE"));
            role.setName(jdbc.getString("NAME_ROLE"));
            role.setDescription(jdbc.getString("DESCRIPTION_ROLE"));
            roles.add(role);
            role = null;
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
    return roles;
  }

  public List<RoleDto> searchFor(RoleDto role)
  {
    List<RoleDto> roles = null;

    try
      {
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("select ID_ROLE, NAME_ROLE, DESCRIPTION_ROLE "+
          "from ROLE where ID_ROLE = ? OR NAME_ROLE = ? OR DESCRIPTION_ROLE=?");
        try
          {
            jdbc.setInt(1, Integer.parseInt(role.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, role.getSearch());
        jdbc.setString(3, role.getSearch());
        jdbc.execute();
        RoleDto R = null;
        while(jdbc.next())
          {
            if(roles==null)
              {
                roles = new ArrayList<>();
              }
            R = new RoleDto();

            R.setId(jdbc.getInt("ID_ROLE"));
            R.setName(jdbc.getString("NAME_ROLE"));
            R.setDescription(jdbc.getString("DESCRIPTION_ROLE"));
            roles.add(R);
            R = null;
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
    return roles;
  }

  public Boolean delete(RoleDto r)
  {
    try
      {
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("delete from ROLE where ID_ROLE = ?  ");
        try
          {
            jdbc.setInt(1, r.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
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
