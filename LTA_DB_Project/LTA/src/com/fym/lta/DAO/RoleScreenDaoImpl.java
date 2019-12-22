package com.fym.lta.DAO;


import com.fym.lta.DTO.RoleScreenDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class RoleScreenDaoImpl implements RoleScreenDao
{
  private UserDto user = null;

  public RoleScreenDaoImpl(UserDto user)
  {
    this.user = user;
  }

  public boolean isExist(RoleScreenDto rs)
  {
    boolean flag = false;
    try
      {

        JdbcRowSet jdbc = user.getConnect();

        jdbc.setCommand("SELECT ID_ROLE FROM ROLE WHERE NAME_ROLE = ?");
        jdbc.setString(1, rs.getRole_name());
        jdbc.execute();

        while(jdbc.next())
          {
            rs.setRole_id(jdbc.getInt("ID_ROLE"));
          }

        jdbc.setCommand("SELECT ID_SCREEN FROM SCREEN WHERE NAME_SCREEN = ?");
        jdbc.setString(1, rs.getScreen_name());
        jdbc.execute();

        while(jdbc.next())
          {
            rs.setScreen_id(jdbc.getInt("ID_SCREEN"));
          }

        jdbc.setCommand("SELECT * "+"FROM ROLE_SCREEN "+
          "WHERE ID_ROLE=? AND ID_SCREEN=? ");
        jdbc.setInt(1, rs.getRole_id());
        jdbc.setInt(2, rs.getScreen_id());
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

  public boolean createNew(RoleScreenDto rs)
  {
    try
      {

        System.out.println("xsasas \n");
        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("SELECT ID_ROLE FROM ROLE WHERE NAME_ROLE = ?");
        jdbc.setString(1, rs.getRole_name());
        jdbc.execute();
        while(jdbc.next())
          {
            rs.setRole_id(jdbc.getInt("ID_ROLE"));
          }
        jdbc.setCommand("SELECT ID_SCREEN FROM SCREEN WHERE NAME_SCREEN = ?");
        jdbc.setString(1, rs.getScreen_name());
        jdbc.execute();
        while(jdbc.next())
          {
            rs.setScreen_id(jdbc.getInt("ID_SCREEN"));
          }

        // insert into database
        jdbc.setCommand("insert into ROLE_SCREEN (ID_ROLE , ID_SCREEN , FULL_ACCESS , VIEW_ONLY) "+
          "values(?,?,?,?)");

        jdbc.setInt(1, rs.getRole_id());
        jdbc.setInt(2, rs.getScreen_id());
        if(rs.isFull_access())
          jdbc.setString(3, "Y");
        else
          jdbc.setString(3, "N");
        if(rs.isView_only())
          jdbc.setString(4, "Y");
        else
          jdbc.setString(4, "N");

        System.out.println(rs.getRole_id()+" "+rs.getScreen_id()+" ");
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

  public boolean delete(RoleScreenDto rs)
  {
    try
      {

        System.out.println("dao");
        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        //get role id
        jdbc.setCommand("SELECT ID_ROLE FROM ROLE WHERE NAME_ROLE = ?");
        jdbc.setString(1, rs.getRole_name());
        jdbc.execute();
        while(jdbc.next())
          {
            rs.setRole_id(jdbc.getInt("ID_ROLE"));
          }

        //get screen id
        jdbc.setCommand("SELECT ID_SCREEN FROM SCREEN WHERE NAME_SCREEN = ?");
        jdbc.setString(1, rs.getScreen_name());
        jdbc.execute();
        while(jdbc.next())
          {
            rs.setScreen_id(jdbc.getInt("ID_SCREEN"));
          }

        //delete location type
        jdbc.setCommand("delete from ROLE_SCREEN where ID_ROLE= ? AND ID_SCREEN = ?");
        jdbc.setInt(1, rs.getRole_id());
        jdbc.setInt(2, rs.getScreen_id());
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

  public List<RoleScreenDto> searchFor(RoleScreenDto rs)
  {


    List<RoleScreenDto> types = null;

    try
      {
        // Start DataBase connection

        JdbcRowSet jdbc = user.getConnect();


        // Select all columns from Lication type table
        //   jdbc.setCommand("select ID_ROLE, ID_SCREEN ,FULL_ACCESS , VIEW_ONLY   " +
        //     "FROM ROLE_SCREEN ORDER BY ID_ROLE , ID_SCREEN ");
        jdbc.setCommand("SELECT ID_ROLE , ID_SCREEN ,  NAME_ROLE , NAME_SCREEN , FULL_ACCESS , VIEW_ONLY  "+
          "FROM ROLE_SCREEN JOIN ROLE  "+"USING (ID_ROLE) "+"JOIN SCREEN "+
          " USING (ID_SCREEN)   "+"WHERE ROLE.NAME_ROLE=? "+
          "ORDER BY ID_ROLE , ID_SCREEN");
        jdbc.setString(1, rs.getSearch());
        jdbc.execute();


        //start pick up results from table into objects
        RoleScreenDto t = null;
        while(jdbc.next())
          {

            if(types==null)
              {
                types = new ArrayList<>();
              }
            t = new RoleScreenDto();
            t.setRole_name(jdbc.getString("NAME_ROLE"));
            t.setScreen_name(jdbc.getString("NAME_SCREEN"));
            t.setRole_id(jdbc.getInt("ID_ROLE"));
            t.setScreen_id(jdbc.getInt("ID_SCREEN"));
            if(jdbc.getString("FULL_ACCESS").matches("Y"))
              t.setFull_access(true);
            else
              t.setFull_access(false);
            if(jdbc.getString("VIEW_ONLY").matches("Y"))
              t.setView_only(true);
            else
              t.setView_only(false);
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

  public boolean update(RoleScreenDto rs)
  {
    try
      {
        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        // update name & code & color for location type which its id can't be updated
        jdbc.setCommand("UPDATE ROLE_SCREEN "+
          "SET FULL_ACCESS=? ,VIEW_ONLY = ? "+
          "WHERE ID_ROLE = ? AND ID_SCREEN = ?");
        if(rs.isFull_access())
          jdbc.setString(1, "Y");
        else
          jdbc.setString(1, "N");
        if(rs.isView_only())
          jdbc.setString(2, "Y");
        else
          jdbc.setString(2, "N");
        jdbc.setInt(3, rs.getRole_id());
        jdbc.setInt(4, rs.getScreen_id());
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

  public List<RoleScreenDto> viewAll()
  {
    List<RoleScreenDto> types = null;

    try
      {
        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();

        // Select all columns from Lication type table
        //   jdbc.setCommand("select ID_ROLE, ID_SCREEN ,FULL_ACCESS , VIEW_ONLY   " +
        //     "FROM ROLE_SCREEN ORDER BY ID_ROLE , ID_SCREEN ");
        jdbc.setCommand("SELECT ID_ROLE , ID_SCREEN ,  NAME_ROLE , NAME_SCREEN , FULL_ACCESS , VIEW_ONLY\n"+
          "FROM ROLE_SCREEN JOIN ROLE\n"+"USING (ID_ROLE)\n"+
          "JOIN SCREEN\n"+"USING (ID_SCREEN)\n"+
          "ORDER BY ID_ROLE , ID_SCREEN");
        jdbc.execute();


        //start pick up results from table into objects
        RoleScreenDto t = null;
        while(jdbc.next())
          {

            if(types==null)
              {
                types = new ArrayList<>();
              }
            t = new RoleScreenDto();
            t.setRole_name(jdbc.getString("NAME_ROLE"));
            t.setScreen_name(jdbc.getString("NAME_SCREEN"));
            t.setRole_id(jdbc.getInt("ID_ROLE"));
            t.setScreen_id(jdbc.getInt("ID_SCREEN"));
            if(jdbc.getString("FULL_ACCESS").matches("Y"))
              t.setFull_access(true);
            else
              t.setFull_access(false);
            if(jdbc.getString("VIEW_ONLY").matches("Y"))
              t.setView_only(true);
            else
              t.setView_only(false);
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

  @Override
  public ArrayList<String> checkPanel(UserDto user)
  {
    List<String> panels = new ArrayList<String>();

    try(JdbcRowSet jdbc = RowSetProvider.newFactory().createJdbcRowSet())
      {

        //start database connection
        jdbc.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
        jdbc.setUsername("lta");
        jdbc.setPassword("lta");

        jdbc.setCommand("SELECT S.NAME_SCREEN \n"+
          "FROM ROLE_SCREEN RS NATURAL JOIN SCREEN S\n"+
          "WHERE ID_ROLE=? AND (FULL_ACCESS='Y' OR VIEW_ONLY='Y')"+
          " ORDER BY ID_SCREEN");
        jdbc.setInt(1, user.getRole().getId());
        jdbc.execute();
        String s;
        while(jdbc.next())
          {
            s = (jdbc.getString("NAME_SCREEN"));
            panels.add(s);
            s = null;
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    System.out.println((ArrayList<String>) panels);
    return (ArrayList<String>) panels;
  }

  @Override
  public boolean viewonly(UserDto user)
  {
    // TODO Implement this method
    Boolean flag = false;
    try
      {
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("SELECT ID_SCREEN FROM SCREEN WHERE NAME_SCREEN = ?");
        jdbc.setString(1, user.getScreen_name());
        jdbc.execute();
        while(jdbc.next())
          {
            user.setScreen_id(jdbc.getInt("ID_SCREEN"));
          }

        jdbc.setCommand("SELECT * FROM ROLE_SCREEN "+
          "WHERE ID_ROLE =? AND ID_SCREEN = ? AND VIEW_ONLY='Y' ");
        jdbc.setInt(1, user.getRole().getId());
        jdbc.setInt(2, user.getScreen_id());
        jdbc.execute();
        while(jdbc.next())
          {
            flag = true;
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return flag;
  }


}
