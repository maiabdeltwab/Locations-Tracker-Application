package com.fym.lta.DAO;

import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.UserDto;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class DepartmentDaoImpl implements DepartmentDao
{

  private UserDto user = null;

  public DepartmentDaoImpl(UserDto user)
  {
    this.user = user;
  }

  /** Search For Drparmtment with any attributes of it(id,name,code,...)
   * This method takes department object and return list of department if exist.
   */
  public List<DepartmentDto> searchFor(DepartmentDto d)
  {

    List<DepartmentDto> departs = null;

    try
      {

        // Start DataBase connection

        JdbcRowSet jdbc = user.getConnect();


        // Select all columns from department table which its id/name/code Compatible with search text
        jdbc.setCommand("select ID_DEPARTMENT,CODE_DEPARTMENT,NAME_DEPARTMENT "+
          "from DEPARTMENT  "+
          "where ID_DEPARTMENT=? or CODE_DEPARTMENT=? or NAME_DEPARTMENT=? order by ID_DEPARTMENT");


        try
          {
            jdbc.setInt(1, Integer.parseInt(d.getSearch()));
          }
        catch(Exception e)
          {
            e.printStackTrace();
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, d.getSearch());
        jdbc.setString(3, d.getSearch());
        jdbc.execute();


        //start pick up results from table into objects
        DepartmentDto depart = null;
        while(jdbc.next())
          {
            if(departs==null)
              {
                departs = new ArrayList<>();
              }
            depart = new DepartmentDto();
            depart.setId(jdbc.getInt("ID_DEPARTMENT"));
            depart.setCode(jdbc.getString("CODE_DEPARTMENT"));
            depart.setName(jdbc.getString("NAME_DEPARTMENT"));

            departs.add(depart);
            depart = null;
          }

        //get Buildings for each department
        for(int i = 0; i<departs.size(); i++)
          {

            List<BuildingDto> builds = new ArrayList<BuildingDto>();
            BuildingDto b = null;

            jdbc.setCommand("select ID_BUILD "+"FROM DEPARTMENT_BUILDING "+
              "WHERE ID_DEPARTMENT = ? ");
            jdbc.setInt(1, departs.get(i).getId());
            jdbc.execute();

            while(jdbc.next())
              {
                b = new BuildingDto();
                b.setId(jdbc.getInt("ID_BUILD"));
                builds.add(b);
                b = null;
              }

            if(!builds.isEmpty()&&builds!=null)
              //get building code for each of them
              {
                for(int j = 0; j<builds.size(); j++)
                  {
                    jdbc.setCommand("select CODE_BUILD "+"from BUILDING "+
                      "where ID_BUILD = ? ");
                    jdbc.setInt(1, builds.get(j).getId());
                    jdbc.execute();

                    while(jdbc.next())
                      builds.get(j).setCode(jdbc.getString("CODE_BUILD"));
                  }
              }
            departs.get(i).setBuildings(builds);
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

    return departs;
  }


  /** Delete a row selected by user in GUI.
   * Takes the object and return true if it has deleted and false if any exception occur*/

  public boolean delete(DepartmentDto d)
  {


    try
      {

        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        // Delete the row with id of the selected department and its dependence

        //Delete the set of this department to buildings
        jdbc.setCommand("delete from DEPARTMENT_BUILDING where ID_DEPARTMENT=? ");
        jdbc.setInt(1, d.getId());
        jdbc.execute();

        //set staff id of this department to null
        jdbc.setCommand("UPDATE STAFF SET ID_DEPARTMENT=(NULL)  WHERE ID_DEPARTMENT=?");
        jdbc.setInt(1, d.getId());
        jdbc.execute();

        //Delete the relation between any course with this department
        jdbc.setCommand("delete from DEPARTMENT_COURSE where ID_DEPARTMENT=?");
        jdbc.setInt(1, d.getId());
        jdbc.execute();

        //Delete department stages
        jdbc.setCommand("delete from STAGE where ID_DEPARTMENT=?");
        jdbc.setInt(1, d.getId());
        jdbc.execute();

        //delete department
        jdbc.setCommand("delete from DEPARTMENT where DEPARTMENT.ID_DEPARTMENT=?");
        jdbc.setInt(1, d.getId());
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


  /** Create a new department
   * takes department object inserted by user
   * Returm true for if it success, False if not */

  public boolean createNew(DepartmentDto depart, UserDto user)
  {

    try
      {

        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        // insert into database department table
        jdbc.setCommand("insert into DEPARTMENT (ID_DEPARTMENT,CODE_DEPARTMENT,NAME_DEPARTMENT,INSERTED_BY,INSERTED_AT) values(?,?,?,?,SYSDATE)");

        jdbc.setInt(1, depart.getId());
        jdbc.setString(2, depart.getCode());
        jdbc.setString(3, depart.getName());
        jdbc.setInt(4, user.getId());
        jdbc.execute();


        //get building IDs for each building
        {
          for(int j = 0; j<depart.getBuildings().size(); j++)
            {
              jdbc.setCommand("select ID_BUILD "+"from BUILDING "+
                "where CODE_BUILD = ? ");
              jdbc.setString(1, depart.getBuildings().get(j).getCode());
              jdbc.execute();

              while(jdbc.next())
                depart.getBuildings().get(j).setId(jdbc.getInt("ID_BUILD"));
            }
        }


        //insert buildings for this department
        for(int i = 0; i<depart.getBuildings().size(); i++)
          {
            // insert into database department building table
            jdbc.setCommand("insert into DEPARTMENT_BUILDING (ID_DEPARTMENT,ID_BUILD) values(?,?)");
            jdbc.setInt(1, depart.getId());
            jdbc.setInt(2, depart.getBuildings().get(i).getId());
            jdbc.execute();
          }


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


  /** Method to pick up all tuples from department table
   * no parameters
   * return list of departmentdto objects
   */
  public List<DepartmentDto> viewAll()
  {


    List<DepartmentDto> departs = new ArrayList<DepartmentDto>();

    try
      {
        // Start DataBase connection

        JdbcRowSet jdbc = user.getConnect();


        //select department id,code,name, from table
        jdbc.setCommand("SELECT ID_DEPARTMENT,CODE_DEPARTMENT,NAME_DEPARTMENT "+
          "FROM DEPARTMENT order by ID_DEPARTMENT");
        jdbc.execute();

        //insert them into department objects
        DepartmentDto depart = new DepartmentDto();
        while(jdbc.next())
          {
            if(departs==null)
              {
                departs = new ArrayList<>();
              }
            depart = new DepartmentDto();
            depart.setId(jdbc.getInt("ID_DEPARTMENT"));
            depart.setCode(jdbc.getString("CODE_DEPARTMENT"));
            depart.setName(jdbc.getString("NAME_DEPARTMENT"));

            departs.add(depart);
            depart = null;
          }

        //get Buildings for each department
        for(int i = 0; i<departs.size(); i++)
          {

            List<BuildingDto> builds = new ArrayList<BuildingDto>();
            BuildingDto b = null;

            jdbc.setCommand("select ID_BUILD "+"FROM DEPARTMENT_BUILDING "+
              "WHERE ID_DEPARTMENT = ? ");
            jdbc.setInt(1, departs.get(i).getId());
            jdbc.execute();

            while(jdbc.next())
              {
                b = new BuildingDto();
                b.setId(jdbc.getInt("ID_BUILD"));
                builds.add(b);
                b = null;
              }

            if(!builds.isEmpty()&&builds!=null)
              //get building code for each of them
              {
                for(int j = 0; j<builds.size(); j++)
                  {
                    jdbc.setCommand("select CODE_BUILD "+"from BUILDING "+
                      "where ID_BUILD = ? ");
                    jdbc.setInt(1, builds.get(j).getId());
                    jdbc.execute();

                    while(jdbc.next())
                      builds.get(j).setCode(jdbc.getString("CODE_BUILD"));
                  }
              }
            departs.get(i).setBuildings(builds);
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
    return departs;
  }


  /** update object selected by user
   * take department object from bao
   * Returm true for if it success, False if not */

  public boolean update(DepartmentDto d, UserDto user)
  {


    try
      {
        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        // update name & code for deparment which its id can't be updated
        jdbc.setCommand("UPDATE DEPARTMENT "+
          "SET CODE_DEPARTMENT = ?,NAME_DEPARTMENT = ? , UPDATED_BY=? , UPDATED_AT=SYSDATE "+
          "WHERE ID_DEPARTMENT = ? ");

        jdbc.setString(1, d.getCode());
        jdbc.setString(2, d.getName());
        jdbc.setInt(3, user.getId());
        jdbc.setInt(4, d.getId());
        jdbc.execute();


        //get building IDs for each building
        {
          for(int j = 0; j<d.getBuildings().size(); j++)
            {
              jdbc.setCommand("select ID_BUILD "+"from BUILDING "+
                "where CODE_BUILD = ? ");
              jdbc.setString(1, d.getBuildings().get(j).getCode());
              jdbc.execute();

              while(jdbc.next())
                d.getBuildings().get(j).setId(jdbc.getInt("ID_BUILD"));
            }
        }


        //update buildings for this department

        //delete existing buildings
        jdbc.setCommand("delete from DEPARTMENT_BUILDING where ID_DEPARTMENT=?");
        jdbc.setInt(1, d.getId());
        jdbc.execute();

        //Add new buildings
        for(int i = 0; i<d.getBuildings().size(); i++)
          {

            // insert into database department building table
            jdbc.setCommand("insert into DEPARTMENT_BUILDING (ID_DEPARTMENT,ID_BUILD) values(?,?)");
            jdbc.setInt(1, d.getId());
            jdbc.setInt(2, d.getBuildings().get(i).getId());
            jdbc.execute();
          }


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


  /** Check if object is exist in database
   * take this object and return boolean value show if it is exist or not
   * */
  public boolean isExist(DepartmentDto d)
  {

    try
      {

        // start connection

        JdbcRowSet jdbc = user.getConnect();


        // select from table department which its id = object id
        jdbc.setCommand("SELECT * "+"FROM DEPARTMENT "+
          "WHERE ID_DEPARTMENT = ? ");
        jdbc.setInt(1, d.getId());
        jdbc.execute();

        boolean flage = false;

        //if it found any result return true "Department is exist"
        while(jdbc.next())
          {
            flage = true;
            break;
          }
        return flage;

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


  /**Get the department id
   * @param department
   * @return ID */
  public int getDepartmentId(DepartmentDto d)
  {
    int id = 0;
    try
      {

        // start connection

        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("select ID_DEPARTMENT from DEPARTMENT where CODE_DEPARTMENT=? ");
        jdbc.setString(1, d.getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            id = jdbc.getInt("ID_DEPARTMENT");
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return id;
  }


  /**get the max id in departments
   * @return id*/
  public int getMaxId()
  {
    int max_id = 0;
    try
      {

        // start connection

        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("select MAX(ID_DEPARTMENT) as max FROM DEPARTMENT ");
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


  /**get buildings for a specific department
   * @param department object
   * @return building list*/
  public List<BuildingDto> viewBuilding(DepartmentDto d)
  {
    List<BuildingDto> builds = null;
    try(JdbcRowSet jdbc = RowSetProvider.newFactory().createJdbcRowSet())
      {

        // start connection

        jdbc.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
        jdbc.setUsername("lta");
        jdbc.setPassword("lta");
        jdbc.setCommand("select BUILDING.ID_BUILD , BUILDING.CODE_BUILD , BUILDING.DESCRIPTION_BUILD "+
          "FROM BUILDING JOIN DEPARTMENT_BUILDING ON (BUILDING.ID_BUILD=DEPARTMENT_BUILDING.ID_BUILD) "+
          "JOIN DEPARTMENT ON (DEPARTMENT.ID_DEPARTMENT=DEPARTMENT_BUILDING.ID_DEPARTMENT) "+
          "WHERE DEPARTMENT.CODE_DEPARTMENT=?");
        jdbc.setString(1, d.getCode());
        jdbc.execute();
        BuildingDto b = null;
        while(jdbc.next())
          {
            if(builds==null)
              {
                builds = new ArrayList<>();
              }
            b = new BuildingDto();
            b.setCode(jdbc.getString("CODE_BUILD"));
            b.setId(jdbc.getInt("ID_BUILD"));
            b.setDescription(jdbc.getString("DESCRIPTION_BUILD"));
            builds.add(b);
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return builds;
  }


  /**Get stage and view its departments*/
  public List<DepartmentDto> viewStageDepartment(DepartmentDto department)
  {
    // TODO Implement this method
    List<DepartmentDto> deps = null;
    try
      {

        //Start data base connection
        JdbcRowSet jdbc = user.getConnect();


        //select floors for specific  building depend on building code
        jdbc.setCommand("SELECT D.ID_DEPARTMENT,D.CODE_DEPARTMENT,D.NAME_DEPARTMENT \n"+
          "FROM STAGE S JOIN DEPARTMENT D ON (D.ID_DEPARTMENT=S.ID_DEPARTMENT)\n"+
          "WHERE S.NO_STAGE=? ORDER BY ID_DEPARTMENT");

        jdbc.setString(1,
          department.getStage().getNumber()); //get code building


        jdbc.execute();
        DepartmentDto dep = null;
        while(jdbc.next())
          {
            if(dep==null)
              {
                deps = new ArrayList<>();
              }
            dep = new DepartmentDto();
            dep.setId(jdbc.getInt("ID_DEPARTMENT"));
            dep.setCode(jdbc.getString("CODE_DEPARTMENT"));
            dep.setName(jdbc.getString("NAME_DEPARTMENT"));
            deps.add(dep);

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
    return deps;

  }

}
