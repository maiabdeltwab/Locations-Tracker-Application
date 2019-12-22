package com.fym.lta.DAO;


import com.fym.lta.DTO.EmployeeDto;
import com.fym.lta.DTO.RoleDto;
import com.fym.lta.DTO.UserDto;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;


public class EmployeeDaoImpl implements EmployeeDao
{

  private UserDaoImpl endUserDaoImpl;
  private UserDto user_dto = null;

  public EmployeeDaoImpl(UserDto user)
  {
    this.user_dto = user;
  }

  public Boolean createNew(EmployeeDto emp, UserDto user)
  {
    try
      {
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("insert into EMPLOYEE (ID_EMPLOYEE,NAME_EMPLOYEE,JOP_EMPLOYEE,INSERTED_BY,INSERTED_AT) values(?,?,?,?,SYSDATE)");
        jdbc.setInt(1, emp.getId());
        jdbc.setString(2, emp.getName());
        jdbc.setString(3, emp.getJob());
        jdbc.setInt(4, user.getId());
        jdbc.execute();
        return true;
      }

    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }

  }

  public Boolean delete(EmployeeDto emp)
  {
    try
      {

        JdbcRowSet jdbc = user_dto.getConnect();

        jdbc.setCommand("delete from EMPLOYEE where ID_EMPLOYEE=?");
        jdbc.setInt(1, emp.getId());

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

  public Boolean update(EmployeeDto emp, UserDto user)
  {
    try
      {
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("UPDATE EMPLOYEE "+
          "SET NAME_EMPLOYEE = ? ,JOP_EMPLOYEE = ? , UPDATED_BY = ? , UPDATED_AT = SYSDATE "+
          "WHERE ID_EMPLOYEE = ?");

        jdbc.setString(1, emp.getName());
        jdbc.setString(2, emp.getJob());
        jdbc.setInt(3, user.getId());
        jdbc.setInt(4, emp.getId());
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

  public List<EmployeeDto> searchFor(EmployeeDto emp)
  {
    List<EmployeeDto> emps = null;
    try
      {
        JdbcRowSet jdbc = user_dto.getConnect();


        jdbc.setCommand("select ID_EMPLOYEE , NAME_EMPLOYEE , JOP_EMPLOYEE from EMPLOYEE where EMPLOYEE.ID_EMPLOYEE=? OR EMPLOYEE.NAME_EMPLOYEE=? OR EMPLOYEE.JOP_EMPLOYEE=? ");
        try
          {
            jdbc.setInt(1, Integer.parseInt(emp.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, emp.getSearch());
        jdbc.setString(3, emp.getSearch());
        jdbc.execute();

        EmployeeDto employee = null;
        while(jdbc.next())
          {
            if(employee==null)
              {
                emps = new ArrayList<>();
              }
            employee = new EmployeeDto();
            employee.setId(jdbc.getInt("ID_EMPLOYEE"));
            employee.setName(jdbc.getString("NAME_EMPLOYEE"));
            employee.setJob(jdbc.getString("JOP_EMPLOYEE"));

            emps.add(employee);
            employee = null;
          }


      }
    catch(SQLException e)
      {
        e.printStackTrace();
        System.out.println("Error Searching for Employee!");
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

    return emps;
  }

  public List<EmployeeDto> viewAll()
  {
    List<EmployeeDto> employees = null;
    try
      {

        JdbcRowSet jdbc = user_dto.getConnect();

        jdbc.setCommand("select ID_EMPLOYEE,NAME_EMPLOYEE,JOP_EMPLOYEE from EMPLOYEE order by ID_EMPLOYEE ");
        jdbc.execute();

        EmployeeDto temp = null;
        while(jdbc.next())
          {
            if(employees==null)
              {
                employees = new ArrayList<>();
              }
            temp = new EmployeeDto();
            temp.setId(jdbc.getInt("ID_EMPLOYEE"));
            temp.setName(jdbc.getString("NAME_EMPLOYEE"));
            temp.setJob(jdbc.getString("JOP_EMPLOYEE"));
            employees.add(temp);
            temp = null;

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
    return employees;
  }

  public Boolean isExist(EmployeeDto emp)
  {
    boolean flag = false;
    try
      {
        JdbcRowSet jdbc = user_dto.getConnect();


        jdbc.setCommand("SELECT ID_EMPLOYEE FROM EMPLOYEE WHERE ID_EMPLOYEE = ?");
        jdbc.setInt(1, emp.getId());
        jdbc.execute();

        while(jdbc.next())
          {
            flag = true;
            break;
          }
        return flag;
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

  public UserDto viewUserOfStaff(EmployeeDto emp)
  {

    UserDto user = null;
    try
      {
        JdbcRowSet jdbc = user_dto.getConnect();

        jdbc.setCommand("select USER_TABLE.ID_USER , USER_TABLE.NAME_USER_ , USER_TABLE.EMAIL_USER , USER_TABLE.ID_ROLE "+
          "FROM EMPLOYEE JOIN EMP_ACC ON (EMPLOYEE.ID_EMPLOYEE = EMP_ACC.ID_EMPLOYEE) "+
          "JOIN USER_TABLE ON (USER_TABLE.ID_USER=EMP_ACC.ID_USER) "+
          "WHERE EMPLOYEE.ID_EMPLOYEE=?");
        jdbc.setInt(1, emp.getId());
        jdbc.execute();

        while(jdbc.next())
          {
            user = new UserDto();
            user.setId(jdbc.getInt("ID_USER"));
            user.setEmail(jdbc.getString("EMAIL_USER"));
            user.setUsername(jdbc.getString("NAME_USER_"));
            user.setRole(new RoleDto());
            user.getRole().setId(jdbc.getInt("ID_ROLE"));
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
    return user;


  }
}
