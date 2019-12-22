package com.fym.lta.DAO;

import com.fym.lta.DTO.FacultyDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;

public class FacultyDaoImpl implements FacultyDao
{

  /* identify user-dto object and initialize to null */
  private UserDto user = null;

  public FacultyDaoImpl(UserDto user)
  {
      /* set the parameter to user-dto object */ 
    this.user = user;
  }

   /**
     * update an existing faculty
     * @param faculty object passed by user
     * @param user
     * @return true if updated successfully , false if not
     */
    public Boolean update(FacultyDto faculty , UserDto user)
  {
      try
      {
          // Start DataBase connection
          JdbcRowSet jdbc = user.getConnect();
          
          // sql command to update faculty 
          jdbc.setCommand("update FACULTY set STUDY_YEARS=? , DESCRIPTION_FACULTY=? " +
            "UPDATED_BY=? , UPDATED_AT=SYSDATE where NAME_FACULTY=?"); 
          
          // pass new parameters to command to update then return true
          jdbc.setInt(1,faculty.getStages());
          jdbc.setString(2, faculty.getDescription());
          jdbc.setInt(3,user.getId());
          jdbc.setString(4,faculty.getName());
          jdbc.execute();
          return true;
      }
      catch(java.sql.SQLException e)
      {
       System.out.println("sql error update faculty");
       return false;
      }
      catch(Exception e)
      {
          System.out.println("error update faculty");
          e.printStackTrace();
          return false;
      }
  }

    /**
     * search for faculty in DB by search key inserted by user 
     * @param faculty dto object
     * @return list of faculty-dto objects that any of attribiutes match
     * search key
     */
   public List<FacultyDto> searchFor(FacultyDto faculty)
  {
        List<FacultyDto> faculties = null;
        try {
            // Start DataBase connection
            JdbcRowSet jdbc = user.getConnect();
            
            //sql command to search for faculty
            jdbc.setCommand("select NAME_FACULTY , STUDY_YEARS , DESCRIPTION_FACULTY " +
                "from FACULTY where NAME_FACULTY=? OR STUDY_YEARS=? OR DESCRIPTION_FACULTY=? ");
            
            //pass parameters to command to search with
            jdbc.setString(1,faculty.getSearch());
            jdbc.setInt(2,Integer.parseInt(faculty.getSearch()));
            jdbc.setString(3,faculty.getSearch());
            jdbc.execute();
            
            //identify faculty-dto object  
            FacultyDto facul=null;
            while (jdbc.next()) {
                if (faculties == null)
                    faculties = new ArrayList<>();
                //create object of faculty-dto
                facul = new FacultyDto();
                
                //set query results to object then add to list
                facul.setDescription(jdbc.getString("DESCRIPTION_FACULTY"));
                facul.setName(jdbc.getString("NAME_FACULTY"));
                facul.setStages(jdbc.getInt("STUDY_YEARS"));
                faculties.add(facul);
                
                facul=null;
            }     
        }
        catch(java.sql.SQLException e){
            System.out.println("sql error search for faculty");
        }
      catch(Exception e)
      {
          System.out.println("error search for faculty");
          e.printStackTrace();
      }
        return faculties;
  }

  /**
     * check faculty existance
     * @param faculty dto object
     * @return true if exist - false if not
     */
  public Boolean isExist(FacultyDto faculty)
  {
      boolean existFlag = false;
      try{
          // Start DataBase connection
          JdbcRowSet jdbc = user.getConnect();
          
          //sql command to check existance
          jdbc.setCommand("select NAME_FACULTY , DESCRIPTION_FACULTY " +
              "from FACULTY where NAME_FACULTY=? ");
          
          //pass name parameter command then execute
          jdbc.setString(1,faculty.getName());
          jdbc.execute();
          
          //set flag to true in case of not null result set
          while(jdbc.next()){
              existFlag = true;
              break;
          }
      }
      catch(java.sql.SQLException e)
      {
       System.out.println("sql error in isexist faculty");
      }
      catch(Exception e)
      {
          System.out.println("error in isexist faculty");
          e.printStackTrace();
      }
        return existFlag;      
  }

  /**
     * get all faculties stored in DB
     * @return list of faculty-dto object
     */
  public List<FacultyDto> viewAll()
  {
    List<FacultyDto> faculties=null;
    
    try{
        // Start DataBase connection
        JdbcRowSet jdbc = user.getConnect(); 
        
        //sql command to view all faculties
        jdbc.setCommand("select NAME_FACULTY , STUDY_YEARS , DESCRIPTION_FACULTY" +
            " from FACULTY order by NAME_FACULTY ");
        jdbc.execute();
        
        FacultyDto faculty=null;
        //get result set from command and set to dto object then add to list
        while(jdbc.next()){
            if(faculties==null)
                faculties = new ArrayList<>();
            faculty=new FacultyDto();
            faculty.setName(jdbc.getString("NAME_FACULTY"));
            faculty.setStages(jdbc.getInt("STUDY_YEARS"));
            faculty.setDescription(jdbc.getString("DESCRIPTION_FACULTY"));
            faculties.add(faculty);
            faculty=null;
        }
    }
      catch(java.sql.SQLException e)
      {
       System.out.println("sql error view all faculties");
      }
      catch(Exception e)
      {
          System.out.println("error view all faculties");
          e.printStackTrace();
      }
    
    return faculties;
  }

  /**
     * delete faculty object from DB selected by user 
     * @param faculty dto object
     * @return true if deleted - false if not
     */
  public Boolean delete(FacultyDto faculty)
  {
      try{
          // Start DataBase connection
          JdbcRowSet jdbc = user.getConnect();
          
          //sql command to delete faculty 
          jdbc.setCommand("delete from FACULTY where NAME_FACULTY=?");
          
          //pass faculty name to command to delete
          jdbc.setString(1,faculty.getName());
          jdbc.execute();
          return true;
      }
      catch(java.sql.SQLException e)
      {
       System.out.println("sql error delete faculty");
       return false;
      }
      catch(Exception e)
      {
          System.out.println("error delete faculty");
          e.printStackTrace();
          return false;
      }
  }

  /**
     * save new faculty in DB
     * @param faculty dto object to be saved
     * @return true if inserted successfully - false if not
     */
  public Boolean createNew(FacultyDto faculty, UserDto userDto)
  {
      try{
          // Start DataBase connection
          JdbcRowSet jdbc = user.getConnect();
          
          //sql command to insert faculty
          jdbc.setCommand("insert into Faculty " +
              "(NAME_FACULTY, STUDY_YEARS, DESCRIPTION_FACULTY, INSERTED_BY, INSERTED_AT ) " +
              "values(? , ? , ? , ? , SYSDATE)");
          
          //pass parameters to command then return true
          jdbc.setString(1,faculty.getName());
          jdbc.setInt(2,faculty.getStages());
          jdbc.setString(3,faculty.getDescription());
          jdbc.setInt(4,userDto.getId());
          
          jdbc.execute();
          return true;
      }
      catch(java.sql.SQLException e)
      {
       System.out.println("sql error create faculty");
       return false;
      }
      catch(Exception e)
      {
          System.out.println("error create faculty");
          e.printStackTrace();
          return false;
      }
  }
  
  /**
     * set term of schedule
     * @param faculty
     * @param userDto
     * @return
     */
  public Boolean setScheduleTerm(FacultyDto faculty, UserDto userDto)
  {
      try{
          // Start DataBase connection
          JdbcRowSet jdbc = user.getConnect();
          
          //sql command to set term
          jdbc.setCommand("update FACULTY set TERM=? ,UPDATED_BY=? , UPDATED_AT=SYSDATE  " +
              "where NAME_FACULTY=?");
          
          //pass parameters to command then return true
          jdbc.setString(1,faculty.getTerm());
          jdbc.setInt(2,userDto.getId());
          jdbc.setString(3,faculty.getName());
          jdbc.execute();
          return true;
      }
      catch(java.sql.SQLException e)
      {
       System.out.println("sql error set term faculty");
       return false;
      }
      catch(Exception e)
      {
          System.out.println("error set term faculty");
          e.printStackTrace();
          return false;
      }
  }

}
