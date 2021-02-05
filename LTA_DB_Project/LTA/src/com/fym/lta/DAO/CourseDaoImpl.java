package com.fym.lta.DAO;

///,,,,
import com.fym.lta.DTO.CourseDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class CourseDaoImpl implements CourseDao
{

  private UserDto user = null;

  public CourseDaoImpl(UserDto user)
  {
    this.user = user;
  }

  /**check if course exist or not
   * @param course object
   * @return true if it found in db, false if not
   */
  public Boolean isExist(CourseDto c)
  {
    try
      {

        // start connection
        JdbcRowSet jdbc = user.getConnect();

        // select from table course which its id = object id
        jdbc.setCommand("SELECT ID_COURSE "+"FROM COURSE "+
          "WHERE CODE_COURSE=?");
        jdbc.setString(1, c.getCode());
        jdbc.execute();

        boolean flage = false;

        while(jdbc.next())
          {
            flage = true;
            break;
          }
        return flage;

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


  /** Search For course with any attributes of it(id,name,code,...)
   * This method takes course object and return list of courses if exist.
   */
  public List<CourseDto> searchFor(CourseDto c)
  {


    List<CourseDto> courses = null;

    try
      {

        // Start DataBase connection

        JdbcRowSet jdbc = user.getConnect();


        //search in database
        jdbc.setCommand("SELECT DISTINCT C.ID_COURSE, C.NAME_COURSE, C.CODE_COURSE,C.PLT_LEC,PLT_SEC, C.LEC_TIME,C.SEC_TIME,C.TERM "+
          "            FROM COURSE C JOIN DEPARTMENT_COURSE DC ON (C.ID_COURSE=DC.ID_COURSE)  "+
          "            JOIN DEPARTMENT D ON (D.ID_DEPARTMENT=DC.ID_DEPARTMENT)  "+
          "            where C.ID_COURSE=? or C.NAME_COURSE=? or C.CODE_COURSE=?  or C.TIME_COURSE=? OR D.CODE_DEPARTMENT=?  ");

        try
          {
            jdbc.setInt(1, Integer.parseInt(c.getSearch()));
          }
        catch(Exception e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, c.getSearch());
        jdbc.setString(3, c.getSearch());

        try
          {
            jdbc.setInt(4, Integer.parseInt(c.getSearch()));
          }
        catch(Exception e)
          {
            jdbc.setInt(4, -1);
          }
        jdbc.setString(5, c.getSearch());


        jdbc.execute();


        //start pick up results from table into course list
        CourseDto cr = null;

        while(jdbc.next())
          {

            if(courses==null)
              {
                courses = new ArrayList<>();
              }

            cr = new CourseDto();
            cr.setId(jdbc.getInt("ID_COURSE"));
            cr.setName(jdbc.getString("NAME_COURSE"));
            cr.setCode(jdbc.getString("CODE_COURSE"));

            cr.setPlt_lecture(new LocationTypeDto());
            cr.getPlt_lecture().setId(jdbc.getInt("PLT_LEC"));

            cr.setPlt_section(new LocationTypeDto());
            cr.getPlt_section().setId(jdbc.getInt("PLT_SEC"));

            cr.setLec_time(Float.parseFloat(jdbc.getString("LEC_TIME")));
            cr.setSec_time(Float.parseFloat(jdbc.getString("SEC_TIME")));
            cr.setTerm(jdbc.getString("TERM"));
          
          
            courses.add(cr);
            cr = null;
          }

        //get plt_lec && plt_sec code from location type tabel
        if(courses!=null&&!courses.isEmpty())
          {
            for(int i = 0; i<courses.size(); i++)

              {
                //for plt_lec
                jdbc.setCommand("select CODE_LOC_TYPE FROM LOCATION_TYPE WHERE ID_LOC_TYPE = ? ");
                jdbc.setInt(1, courses.get(i).getPlt_lecture().getId());
                jdbc.execute();

                while(jdbc.next())
                  {
                    courses.get(i).getPlt_lecture().setCode(jdbc.getString("CODE_LOC_TYPE"));
                  }


                //for plt_sec
                jdbc.setCommand("select CODE_LOC_TYPE FROM LOCATION_TYPE WHERE ID_LOC_TYPE = ? ");
                jdbc.setInt(1, courses.get(i).getPlt_section().getId());
                jdbc.execute();

                while(jdbc.next())
                  {
                    courses.get(i).getPlt_section().setCode(jdbc.getString("CODE_LOC_TYPE"));
                  }


                //get department/s for each course

                List<DepartmentDto> departs =
                  new ArrayList<DepartmentDto>();
                DepartmentDto depart = null;

                jdbc.setCommand("select ID_DEPARTMENT "+
                  "FROM DEPARTMENT_COURSE "+"WHERE ID_COURSE = ? ");
                jdbc.setInt(1, courses.get(i).getId());
                jdbc.execute();

                while(jdbc.next())
                  {
                    depart = new DepartmentDto();
                    depart.setId(jdbc.getInt("ID_DEPARTMENT"));
                    departs.add(depart);
                    depart = null;
                  }

                if(!departs.isEmpty()&&departs!=null)
                  //get department code for each of them
                  {
                    for(int j = 0; j<departs.size(); j++)
                      {
                        jdbc.setCommand("select CODE_DEPARTMENT "+
                          "from DEPARTMENT "+"where ID_DEPARTMENT = ? ");
                        jdbc.setInt(1, departs.get(j).getId());
                        jdbc.execute();

                        while(jdbc.next())
                          departs.get(j).setCode(jdbc.getString("CODE_DEPARTMENT"));
                      }
                  }

                courses.get(i).setDeparts(departs);
              }
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

    return courses;

  }


  /** Search For course with id
   * This method takes course object and return list of courses if exist.
   */
  public CourseDto getCourse(CourseDto c)
  {


    CourseDto course = null;

    try
      {

        // Start DataBase connection

        JdbcRowSet jdbc = user.getConnect();


        //search in database
        jdbc.setCommand("SELECT ID_COURSE, NAME_COURSE, CODE_COURSE,PLT_LEC,PLT_SEC, LEC_TIME,SEC_TIME,TERM FROM COURSE "+
          "where CODE_COURSE=? ");
        jdbc.setString(1, c.getCode());
        jdbc.execute();


        //start pick up results from table into course list

        while(jdbc.next())
          {

            course = new CourseDto();
            course.setId(jdbc.getInt("ID_COURSE"));
            course.setName(jdbc.getString("NAME_COURSE"));
            course.setCode(jdbc.getString("CODE_COURSE"));

            course.setPlt_lecture(new LocationTypeDto());
            course.getPlt_lecture().setId(jdbc.getInt("PLT_LEC"));

            course.setPlt_section(new LocationTypeDto());
            course.getPlt_section().setId(jdbc.getInt("PLT_SEC"));

            course.setLec_time(Float.parseFloat(jdbc.getString("LEC_TIME")));
            course.setSec_time(Float.parseFloat(jdbc.getString("SEC_TIME")));
            course.setTerm(jdbc.getString("TERM"));

          }

        //get plt_lec && plt_sec code from location type tabel
        if(course!=null)
          {


            //for plt_lec
            jdbc.setCommand("select CODE_LOC_TYPE FROM LOCATION_TYPE WHERE ID_LOC_TYPE = ? ");
            jdbc.setInt(1, course.getPlt_lecture().getId());
            jdbc.execute();

            while(jdbc.next())
              {
                course.getPlt_lecture().setCode(jdbc.getString("CODE_LOC_TYPE"));
              }


            //for plt_sec
            jdbc.setCommand("select CODE_LOC_TYPE FROM LOCATION_TYPE WHERE ID_LOC_TYPE = ? ");
            jdbc.setInt(1, course.getPlt_section().getId());
            jdbc.execute();

            while(jdbc.next())
              {
                course.getPlt_section().setCode(jdbc.getString("CODE_LOC_TYPE"));
              }

            //get department/s for each course

            List<DepartmentDto> departs = new ArrayList<DepartmentDto>();
            DepartmentDto depart = null;

            jdbc.setCommand("select ID_DEPARTMENT "+
              "FROM DEPARTMENT_COURSE "+"WHERE ID_COURSE = ? ");
            jdbc.setInt(1, course.getId());
            jdbc.execute();

            while(jdbc.next())
              {
                depart = new DepartmentDto();
                depart.setId(jdbc.getInt("ID_DEPARTMENT"));
                departs.add(depart);
                depart = null;
              }

            if(!departs.isEmpty()&&departs!=null)
              //get department code for each of them
              {
                for(int j = 0; j<departs.size(); j++)
                  {
                    jdbc.setCommand("select CODE_DEPARTMENT "+
                      "from DEPARTMENT "+"where ID_DEPARTMENT = ? ");
                    jdbc.setInt(1, departs.get(j).getId());
                    jdbc.execute();

                    while(jdbc.next())
                      departs.get(j).setCode(jdbc.getString("CODE_DEPARTMENT"));
                  }
              }

            course.setDeparts(departs);

          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

    return course;

  }

  /** Delete a course.
   * Takes the course object and return true if it has deleted and false if any exception occur*/
  public Boolean delete(CourseDto c)
  {


    try
      {

        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();

        // Delete relation between this course and department
        jdbc.setCommand("delete from DEPARTMENT_COURSE where ID_COURSE=?");
        jdbc.setInt(1, c.getId());
        jdbc.execute();

        /*  // Delete SLOTS of this course
            jdbc.setCommand("delete from SLOT where ID_COURSE=?");
            jdbc.setInt(1, c.getId());
            jdbc.execute(); */

        // Delete the row with id of the selected location type and its dependence
        jdbc.setCommand("delete from COURSE where ID_COURSE=?");
        jdbc.setInt(1, c.getId());
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


  /** Method to view all exist course
   * @param no parameters
   * @return list of coursedto objects */
  public List<CourseDto> viewAll()
  {

    List<CourseDto> courses = null;

    try
      {

        // get DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        // Select all courses
        jdbc.setCommand("SELECT ID_COURSE, NAME_COURSE, CODE_COURSE,PLT_LEC,PLT_SEC, LEC_TIME,SEC_TIME,TERM FROM COURSE order by ID_COURSE ");
        jdbc.execute();

        //start pick up results from table into course list
        CourseDto cr = null;

        while(jdbc.next())
          {

            if(courses==null)
              {
                courses = new ArrayList<>();
              }

            cr = new CourseDto();
            cr.setId(jdbc.getInt("ID_COURSE"));
            cr.setName(jdbc.getString("NAME_COURSE"));
            cr.setCode(jdbc.getString("CODE_COURSE"));

            cr.setPlt_lecture(new LocationTypeDto());
            cr.getPlt_lecture().setId(jdbc.getInt("PLT_LEC"));

            cr.setPlt_section(new LocationTypeDto());
            cr.getPlt_section().setId(jdbc.getInt("PLT_SEC"));

            cr.setLec_time(Float.parseFloat(jdbc.getString("LEC_TIME")));
            cr.setSec_time(Float.parseFloat(jdbc.getString("SEC_TIME")));
            cr.setTerm(jdbc.getString("TERM"));
          
            courses.add(cr);
            cr = null;
          }

        //get plt_lec && plt_sec code from location type tabel
        for(int i = 0; i<courses.size(); i++)

          {
            //for plt_lec
            jdbc.setCommand("select CODE_LOC_TYPE FROM LOCATION_TYPE WHERE ID_LOC_TYPE = ? ");
            jdbc.setInt(1, courses.get(i).getPlt_lecture().getId());
            jdbc.execute();

            while(jdbc.next())
              {
                courses.get(i).getPlt_lecture().setCode(jdbc.getString("CODE_LOC_TYPE"));
              }


            //for plt_sec
            jdbc.setCommand("select CODE_LOC_TYPE FROM LOCATION_TYPE WHERE ID_LOC_TYPE = ? ");
            jdbc.setInt(1, courses.get(i).getPlt_section().getId());
            jdbc.execute();

            while(jdbc.next())
              {
                courses.get(i).getPlt_section().setCode(jdbc.getString("CODE_LOC_TYPE"));
              }


            //get department/s for each course

            List<DepartmentDto> departs = new ArrayList<DepartmentDto>();
            DepartmentDto depart = null;

            jdbc.setCommand("select ID_DEPARTMENT "+
              "FROM DEPARTMENT_COURSE "+"WHERE ID_COURSE = ? ");
            jdbc.setInt(1, courses.get(i).getId());
            jdbc.execute();

            while(jdbc.next())
              {
                depart = new DepartmentDto();
                depart.setId(jdbc.getInt("ID_DEPARTMENT"));
                departs.add(depart);
                depart = null;
              }

            if(!departs.isEmpty()&&departs!=null)
              //get department code for each of them
              {
                for(int j = 0; j<departs.size(); j++)
                  {
                    jdbc.setCommand("select CODE_DEPARTMENT "+
                      "from DEPARTMENT "+"where ID_DEPARTMENT = ? ");
                    jdbc.setInt(1, departs.get(j).getId());
                    jdbc.execute();

                    while(jdbc.next())
                      departs.get(j).setCode(jdbc.getString("CODE_DEPARTMENT"));
                  }
              }
            courses.get(i).setDeparts(departs);


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

    return courses;
  }


  /** update an existed one
   * @param takes course object inserted by user
   * @Return true for if it success, False if not */
  public Boolean update(CourseDto course, UserDto user)
  {

    try
      {
        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        //get id plt_lecture
        jdbc.setCommand("select ID_LOC_TYPE FROM LOCATION_TYPE WHERE CODE_LOC_TYPE = ? ");
        jdbc.setString(1, course.getPlt_lecture().getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            course.getPlt_lecture().setId(jdbc.getInt("ID_LOC_TYPE"));
          }

        //get id plt_section
        jdbc.setCommand("select ID_LOC_TYPE FROM LOCATION_TYPE WHERE CODE_LOC_TYPE = ? ");
        jdbc.setString(1, course.getPlt_section().getCode());
        jdbc.execute();

        while(jdbc.next())
          {
            course.getPlt_section().setId(jdbc.getInt("ID_LOC_TYPE"));
          }

        // update INFO
        jdbc.setCommand("UPDATE COURSE SET   NAME_COURSE=? , CODE_COURSE=? , PLT_LEC = ? , "+
          " PLT_SEC=? ,LEC_TIME=?,SEC_TIME=? , TERM=? , UPDATED_BY = ? , UPDATED_AT = SYSDATE  "+
          " WHERE ID_COURSE=? ");


        jdbc.setString(1, course.getName());
        jdbc.setString(2, course.getCode());
        jdbc.setInt(3, course.getPlt_lecture().getId());
        jdbc.setInt(4, course.getPlt_section().getId());
        jdbc.setFloat(5, course.getLec_time());
        jdbc.setFloat(6, course.getSec_time());
        jdbc.setString(7, course.getTerm());
        jdbc.setInt(8, user.getId());
        jdbc.setInt(9, course.getId());
        jdbc.execute();

        //get departments IDs for each department
        for(int j = 0; j<course.getDeparts().size(); j++)
          {
            jdbc.setCommand("select ID_DEPARTMENT "+"from DEPARTMENT "+
              "where CODE_DEPARTMENT = ? ");
            jdbc.setString(1, course.getDeparts().get(j).getCode());
            jdbc.execute();

            while(jdbc.next())
              course.getDeparts().get(j).setId(jdbc.getInt("ID_DEPARTMENT"));
            System.out.println("depart");
          }


        //update departments for this cousre

        //delete existing departments for this course
        jdbc.setCommand("delete from DEPARTMENT_COURSE where ID_COURSE = ? ");
        jdbc.setInt(1, course.getId());
        jdbc.execute();


        //insert department/s for this course
        for(int i = 0; i<course.getDeparts().size(); i++)
          {
            // insert into database department course table
            jdbc.setCommand("insert into DEPARTMENT_COURSE (ID_DEPARTMENT,ID_COURSE) values(?,?) ");

            jdbc.setInt(1, course.getDeparts().get(i).getId());
            jdbc.setInt(2, course.getId());

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


  /** Create a new course
   * @param takes course object inserted by user
   * @Return true for if it success, False if not */
  public Boolean createNew(CourseDto course, UserDto user)
  {

    try
      {

        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();

        //get id plt_lecture
        jdbc.setCommand("select ID_LOC_TYPE FROM LOCATION_TYPE WHERE CODE_LOC_TYPE = ? ");
        jdbc.setString(1, course.getPlt_lecture().getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            course.getPlt_lecture().setId(jdbc.getInt("ID_LOC_TYPE"));
          }


        //get id plt_section
        jdbc.setCommand("select ID_LOC_TYPE FROM LOCATION_TYPE WHERE CODE_LOC_TYPE = ? ");
        jdbc.setString(1, course.getPlt_section().getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            course.getPlt_section().setId(jdbc.getInt("ID_LOC_TYPE"));
          }


        // insert into database
        jdbc.setCommand("insert into Course (ID_COURSE, NAME_COURSE, CODE_COURSE,PLT_LEC,PLT_SEC, "+
          " LEC_TIME, SEC_TIME, TERM ,INSERTED_BY,INSERTED_AT ) values(?,?,?,?,?,?,?,SYSDATE) ");

        jdbc.setInt(1, course.getId());
        jdbc.setString(2, course.getName());
        jdbc.setString(3, course.getCode());
        jdbc.setInt(4, course.getPlt_lecture().getId());
        jdbc.setInt(5, course.getPlt_section().getId());
        jdbc.setFloat(6, course.getLec_time());
        jdbc.setFloat(7, course.getSec_time());
        jdbc.setString(8, course.getTerm());
        jdbc.setInt(9, user.getId());
        jdbc.execute();


        if(course.getDeparts()!=null)
          {
            //get department IDs for each department
            for(int j = 0; j<course.getDeparts().size(); j++)
              {
                jdbc.setCommand("select ID_DEPARTMENT "+" from DEPARTMENT "+
                  "where CODE_DEPARTMENT= ? ");
                jdbc.setString(1, course.getDeparts().get(j).getCode());
                jdbc.execute();

                while(jdbc.next())
                  course.getDeparts().get(j).setId(jdbc.getInt("ID_DEPARTMENT"));
              }


            //insert department/s for this course
            for(int i = 0; i<course.getDeparts().size(); i++)
              {
                // insert into database department course table
                jdbc.setCommand("insert into DEPARTMENT_COURSE (ID_DEPARTMENT,ID_COURSE) values(?,?)");

                jdbc.setInt(1, course.getDeparts().get(i).getId());
                jdbc.setInt(2, course.getId());

                jdbc.execute();
              }

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


  public boolean isAssigned(CourseDto c, DepartmentDto d)
  {
    boolean flag = false;
    try(JdbcRowSet jdbc = RowSetProvider.newFactory().createJdbcRowSet())
      {

        // Start DataBase connection
        jdbc.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
        jdbc.setUsername("lta");
        jdbc.setPassword("lta");
        jdbc.setCommand("select ID_DEPARTMENT , ID_COURSE from DEPARTMENT_COURSE "+
          "where ID_DEPARTMENT=? AND ID_COURSE=? ");
        try
          {
            jdbc.setInt(1, d.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        try
          {
            jdbc.setInt(2, c.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(2, -1);
          }
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

  public boolean assignDepCourse(CourseDto c, DepartmentDto d)
  {
    try
      {

        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("insert into DEPARTMENT_COURSE (ID_DEPARTMENT,ID_COURSE) values(?,?) ");
        jdbc.setInt(1, d.getId());
        jdbc.setInt(2, c.getId());

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


  public int getMaxId()
  {
    int max_id = 0;
    try(JdbcRowSet jdbc = RowSetProvider.newFactory().createJdbcRowSet())
      {

        // start connection

        jdbc.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
        jdbc.setUsername("lta");
        jdbc.setPassword("lta");
        jdbc.setCommand("select ID_COURSE FROM COURSE ");
        jdbc.execute();
        while(jdbc.next())
          {
            if(jdbc.getInt("ID_COURSE")>max_id)
              max_id = jdbc.getInt("ID_COURSE");
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return max_id;
  }

  public int getId(CourseDto c)
  {
    int id = 0;
    try(JdbcRowSet jdbc = RowSetProvider.newFactory().createJdbcRowSet())
      {

        // start connection

        jdbc.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
        jdbc.setUsername("lta");
        jdbc.setPassword("lta");
        jdbc.setCommand("select ID_COURSE FROM COURSE WHERE CODE_COURSE=?");
        jdbc.setString(1, c.getCode());
        jdbc.execute();
        while(jdbc.next())
          {
            id = jdbc.getInt("ID_COURSE");
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return id;
  }


  public List<DepartmentDto> viewDepsOfCourse(CourseDto c)
  {
    List<DepartmentDto> departs = null;
    try
      {
        // start connection

        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand(" select DEPARTMENT_COURSE.ID_DEPARTMENT "+
          "FROM DEPARTMENT_COURSE JOIN COURSE ON (COURSE.ID_COURSE = DEPARTMENT_COURSE.ID_COURSE) "+
          "WHERE COURSE.ID_COURSE=? ");
        jdbc.setInt(1, c.getId());
        jdbc.execute();
        DepartmentDto dep = null;
        while(jdbc.next())
          {
            if(departs==null)
              {
                departs = new ArrayList<>();
              }
            dep = new DepartmentDto(jdbc.getInt("ID_DEPARTMENT"));
            departs.add(dep);
            dep = null;
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return departs;

  }


}
