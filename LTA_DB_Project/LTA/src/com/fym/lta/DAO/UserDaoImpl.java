package com.fym.lta.DAO;


import com.fym.lta.DTO.EmployeeDto;
import com.fym.lta.DTO.RoleDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;


public class UserDaoImpl implements UserDao
{
  private UserDto user = null;

  public UserDaoImpl(UserDto user)
  {
    this.user = user;
  }

  /**use to check if the last user logged in program saved his login or not
   * @return the last logged in user
   * */
  public List<UserDto> Keeplogin()
  {
    List<UserDto> users = null;
    try(JdbcRowSet jdbc = RowSetProvider.newFactory().createJdbcRowSet())
      {

        //start database connection
        jdbc.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
        jdbc.setUsername("lta");
        jdbc.setPassword("lta");

        //get the last user logged in from databas "KEEP_USER_LOGIN" table
        jdbc.setCommand("SELECT * FROM KEEP_USER_LOGIN JOIN USER_TABLE ON (KEEP_USER_LOGIN.ID_USER = USER_TABLE.ID_USER) ");
        jdbc.execute();

        //get user
        UserDto u = new UserDto();
        while(jdbc.next())
          {
            if(users==null)
              {
                users = new ArrayList<>();
              }
            u = new UserDto();
            u.setId(jdbc.getInt("ID_USER"));
            u.setEmail(jdbc.getString("EMAIL_USER"));
            u.setUsername(jdbc.getString("NAME_USER_"));
            String dec =
              new DeCoder().decode(jdbc.getString("PASSWORD_USER"));
            u.setPassword(dec);
            u.setKeep_login(jdbc.getInt("KEEP_LOGIN"));
            u.setRole(new RoleDto());
            u.getRole().setId(jdbc.getInt("ID_ROLE"));
            users.add(u);
            u = null;
          }

        return users;
      }
    catch(java.sql.SQLException e)
      {
        return users;
      }
    catch(Exception e)
      {
        e.printStackTrace();

        return users;
      }
  }

  /** used to save the last user logged in
   * save user id and his choice to keep his login or not
   * @param logged in user
   * */
  public void savelogin(UserDto u)
  {
    try(JdbcRowSet jdbc = RowSetProvider.newFactory().createJdbcRowSet())
      {
        //start database connection
        jdbc.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
        jdbc.setUsername("lta");
        jdbc.setPassword("lta");


        //delete any data in table (so table will be never has more than one row)
        jdbc.setCommand("DELETE FROM KEEP_USER_LOGIN");
        jdbc.execute();

        //insert user that has just logged in (id,choice to keep lohin or not)
        jdbc.setCommand("INSERT INTO KEEP_USER_LOGIN (ID_USER ,KEEP_LOGIN) VALUES(?,?)");
        jdbc.setInt(1, u.getId());
        jdbc.setInt(2, u.getKeep_login());

        jdbc.execute();

      }
    catch(java.sql.SQLException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();


      }
  }

  /** Create a new user account
   * @param takes user object inserted by anthor user
   * @Return true for if it success, False if not */
  public Boolean createNew(UserDto u, UserDto user)
  {
    try
      {
        //start connection
        JdbcRowSet jdbc = user.getConnect();


        //get role id
        jdbc.setCommand("SELECT ID_ROLE FROM ROLE WHERE NAME_ROLE=?");
        jdbc.setString(1, u.getRole().getName());
        jdbc.execute();


        while(jdbc.next())
          u.getRole().setId(jdbc.getInt("ID_ROLE"));

        //insert user into database
        jdbc.setCommand("insert into USER_TABLE ( ID_USER, NAME_USER_, EMAIL_USER, PASSWORD_USER, ID_ROLE, INSERTED_BY , INSERTED_AT ) values(?,?,?,?,?,?,SYSDATE) ");
        jdbc.setInt(1, u.getId());
        jdbc.setString(2, u.getUsername());
        jdbc.setString(3, u.getEmail());
        String enc = new DeCoder().encode(u.getPassword());
        jdbc.setString(4, enc);
        jdbc.setInt(5, u.getRole().getId());
        jdbc.setInt(6, user.getId());
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


  /** Delete user account.
   * Takes the object and return true if it has deleted and false if any exception occur*/
  public Boolean delete(UserDto u)
  {

    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();

        /* //delete user form relation between user an staff
        jdbc.setCommand("delete from STAFF_ACC where ID_USER = ?");
        jdbc.setInt(1, u.getId());
        jdbc.execute(); */

        /* //delete user form relation between user an EMPLOYEE
        jdbc.setCommand("delete from EMP_ACC where ID_USER = ?");
        jdbc.setInt(1, u.getId());
        jdbc.execute(); */

        //delete user
        jdbc.setCommand("delete from USER_TABLE where ID_USER = ?");
        jdbc.setInt(1, u.getId());
        jdbc.execute();


        return true;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }


  /**Check if user existing in database or not
   * @param take user
   * @return true if exist, false if not*/
  public Boolean isExist(UserDto u)
  {
    boolean flag = false;
    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();

        //search for this user in database
        jdbc.setCommand("select ID_USER from USER_TABLE where USER_TABLE.EMAIL_USER = ?");
        jdbc.setString(1, u.getEmail());
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


  /** Search For user account with any attributes of it(id,name,code,...)
   * This method takes user object and return list of users if exist.
   */
  public List<UserDto> searchFor(UserDto user_)
  {
    List<UserDto> users = null;
    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //search in database
        jdbc.setCommand("select  USER_TABLE.ID_USER, USER_TABLE.NAME_USER_ "+
          " ,USER_TABLE.EMAIL_USER ,USER_TABLE.PASSWORD_USER, "+
          " ROLE.NAME_ROLE from (USER_TABLE JOIN ROLE "+
          " ON (USER_TABLE.ID_ROLE = ROLE.ID_ROLE)) "+
          " WHERE USER_TABLE.ID_USER=? OR USER_TABLE.NAME_USER_=? "+
          " OR USER_TABLE.EMAIL_USER=? OR USER_TABLE.PASSWORD_USER=? "+
          "OR ROLE.NAME_ROLE=? ORDER BY USER_TABLE.ID_USER");
        try
          {
            jdbc.setInt(1, Integer.parseInt(user_.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, user_.getSearch());
        jdbc.setString(3, user_.getSearch());
        jdbc.setString(4, user_.getSearch());
        jdbc.setString(5, user_.getSearch());
        jdbc.execute();

        //start get result set
        while(jdbc.next())
          {
            if(users==null)
              {
                users = new ArrayList<>();
              }
            user_ = new UserDto();
            user_.setId(jdbc.getInt("ID_USER"));
            user_.setUsername(jdbc.getString("NAME_USER_"));
            user_.setEmail(jdbc.getString("EMAIL_USER"));
            String dec =
              new DeCoder().decode(jdbc.getString("PASSWORD_USER"));
            user_.setPassword(dec);

            user_.setRole(new RoleDto());
            user_.getRole().setName(jdbc.getString("NAME_ROLE"));
            users.add(user_);
            user_ = null;
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return users;
  }


  /** Method to view all exist users
   * @param no parameters
   * @return list of users objects */
  public List<UserDto> viewAll()
  {
    List<UserDto> users = null;
    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();

        //get all ecisting users frim database
        jdbc.setCommand("select  USER_TABLE.ID_USER, USER_TABLE.NAME_USER_ ,USER_TABLE.EMAIL_USER "+
          " ,USER_TABLE.PASSWORD_USER, "+
          " ROLE.NAME_ROLE from (USER_TABLE JOIN ROLE ON (USER_TABLE.ID_ROLE = ROLE.ID_ROLE)) ORDER BY  USER_TABLE.ID_USER");
        jdbc.execute();
        UserDto user = null;


        //start get result set
        while(jdbc.next())
          {
            if(users==null)
              {
                users = new ArrayList<>();
              }
            user = new UserDto();
            user.setId(jdbc.getInt("ID_USER"));
            user.setUsername(jdbc.getString("NAME_USER_"));
            user.setEmail(jdbc.getString("EMAIL_USER"));
            String dec =
              new DeCoder().decode(jdbc.getString("PASSWORD_USER"));
            user.setPassword(dec);
            user.setRole(new RoleDto());
            user.getRole().setName(jdbc.getString("NAME_ROLE"));
            users.add(user);
            user = null;
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return users;
  }


  /** update an existed one
   * @param takes user object inserted by user
   * @Return true for if it success, False if not */
  public Boolean update(UserDto u, UserDto user)
  {
    try
      {
        //start connection
        JdbcRowSet jdbc = user.getConnect();

        //get role id
        jdbc.setCommand("SELECT ID_ROLE FROM ROLE WHERE NAME_ROLE  = ?");
        jdbc.setString(1, u.getRole().getName());
        jdbc.execute();

        while(jdbc.next())
          u.getRole().setId(jdbc.getInt("ID_ROLE"));


        //update user's info.
        jdbc.setCommand("UPDATE USER_TABLE "+
          "set NAME_USER_=? , EMAIL_USER=? , PASSWORD_USER=? , ID_ROLE=? ,UPDATED_BY=? , UPDATED_AT = SYSDATE "+
          "where ID_USER = ?");
        jdbc.setString(1, u.getUsername());
        jdbc.setString(2, u.getEmail());
        String enc = new DeCoder().encode(u.getPassword());
        jdbc.setString(3, enc);
        jdbc.setInt(4, u.getRole().getId());
        jdbc.setInt(5, user.getId());
        jdbc.setInt(6, u.getId());
        jdbc.execute();
        return true;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  /** used for login
   * @param take user object with entered username/email and password
   * @return it with other needed data (if user found in database)
   */
  public UserDto check(UserDto u)
  {
    UserDto user = null;
    try(JdbcRowSet jdbc = RowSetProvider.newFactory().createJdbcRowSet())
      {

        //start database connection
        jdbc.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
        jdbc.setUsername("lta");
        jdbc.setPassword("lta");

        //check if logged in user existing in database or not
        // and get all info. of this user
        jdbc.setCommand("select ID_USER , ID_ROLE ,NAME_USER_ ,EMAIL_USER  from USER_TABLE where (Email_USER = ? or NAME_USER_ = ? ) and password_user=? ");
        jdbc.setString(1, u.getEmail());
        jdbc.setString(2, u.getUsername());
        String enc = new DeCoder().encode(u.getPassword());
        jdbc.setString(3, enc);
        System.out.println(enc);
        jdbc.execute();


        while(jdbc.next())
          {
            user = new UserDto();
            user.setId(jdbc.getInt("ID_USER"));
            user.setRole(new RoleDto());
            user.getRole().setId(jdbc.getInt("ID_ROLE"));
            user.setEmail(jdbc.getString("EMAIL_USER"));
            user.setUsername(jdbc.getString("NAME_USER_"));
            user.setPassword(u.getPassword());
            break;
          }
        return user;
      }
    catch(java.sql.SQLException e)
      {
        return null;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return null;
      }
  }


  /**Method to get user staff
   * @param take user dto
   * @return user's staff (if exist)
   * */
  public StaffDto getStaff(UserDto u)
  {
    StaffDto staff = null;
    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //get staff id from staff account table
        jdbc.setCommand("SELECT ID_STAFF FROM STAFF_ACC WHERE ID_USER=? ");
        jdbc.setInt(1, u.getId());
        jdbc.execute();


        while(jdbc.next())
          {
            staff = new StaffDto();
            staff.setId(jdbc.getInt("ID_STAFF"));
            break;
          }

        //get staff name
        jdbc.setCommand("SELECT NAME_STAFF FROM STAFF WHERE ID_STAFF = ? ");
        jdbc.setInt(1, staff.getId());
        jdbc.execute();

        while(jdbc.next())
          {
            staff.setName(jdbc.getString("NAME_STAFF"));
            break;
          }

        return staff;


      }
    catch(java.sql.SQLException e)
      {
        return null;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return null;
      }
  }


  /**Method to get user employee
   * @param take user dto
   * @return user's employee (if exist)
   * */
  public EmployeeDto getEmployee(UserDto u)
  {

    EmployeeDto employee = null;
    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //get employee id from staff account table
        jdbc.setCommand("SELECT ID_EMPLOYEE FROM EMP_ACC WHERE ID_USER=? ");
        jdbc.setInt(1, u.getId());
        jdbc.execute();

        while(jdbc.next())
          {
            employee = new EmployeeDto();
            employee.setId(jdbc.getInt("ID_EMPLOYEE"));
          }

        //get EMPLOYEE name
        jdbc.setCommand("SELECT NAME_EMPLOYEE FROM EMPLOYEE WHERE ID_EMPLOYEE=? ");
        jdbc.setInt(1, employee.getId());
        jdbc.execute();

        while(jdbc.next())
          {

            employee.setName(jdbc.getString("NAME_EMPLOYEE"));

          }

        return employee;
      }
    catch(java.sql.SQLException e)
      {
        return null;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return null;
      }
  }


  //nada
  public int getMaxId()
  {
    int max_id = 0;
    try
      {

        // start connection

        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("select MAX(ID_USER) as max FROM USER_TABLE ");
        jdbc.execute();
        while(jdbc.next())
          {
            max_id = jdbc.getInt("max");
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return max_id;
  }


  //nada
  public int getId(UserDto u)
  {
    int id = 0;
    try
      {

        // start connection

        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("select ID_USER FROM USER_TABLE where EMAIL_USER=? ");
        jdbc.setString(1, u.getEmail());
        jdbc.execute();
        while(jdbc.next())
          {
            id = jdbc.getInt("ID_USER");
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return id;
  }


  /**Use to user setting panel
   * update user info.
   * @param take user dto
   * @return it after update
   * */
  public UserDto userSetting(UserDto user)
  {

    try
      {
        //start connection
        JdbcRowSet jdbc = user.getConnect();


        //update user's info.
        jdbc.setCommand("UPDATE USER_TABLE "+
          "set NAME_USER_=? , EMAIL_USER=? , PASSWORD_USER=?  ,UPDATED_BY=? , UPDATED_AT = SYSDATE "+
          "where ID_USER = ?");
        jdbc.setString(1, user.getUsername());
        jdbc.setString(2, user.getEmail());
        String enc = new DeCoder().encode(user.getPassword());
        jdbc.setString(3, enc);
        jdbc.setInt(4, user.getId());
        jdbc.setInt(5, user.getId());
        jdbc.execute();

        return user;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return user;
      }

  }

}
