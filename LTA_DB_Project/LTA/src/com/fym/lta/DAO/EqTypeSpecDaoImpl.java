package com.fym.lta.DAO;

/**
 *
 * @author Nada El-Gammal
 */

import com.fym.lta.DTO.EquipSpecificationDto;
import com.fym.lta.DTO.EquipTypeSpecDto;
import com.fym.lta.DTO.EquipmentTypeDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;

// class to select specifications for each equipment type (handle relation between the two tables)

public class EqTypeSpecDaoImpl implements EqTypeSpecDao
{
  private UserDto user = null;


  public EqTypeSpecDaoImpl(UserDto user)
  {
    this.user = user;
  }
  //method to insert new record relating a type with existing specification
  public boolean insert(EquipTypeSpecDto type_spec)
  {
    try
      {

        //open connection with dataase using resources
        JdbcRowSet jdbc = user.getConnect();


        //query to insert type id and its specification id in database
        jdbc.setCommand("insert into SPECIFICATION_EQ_TYPE ( ID , ID_EQ_TYPE , NO ) VALUES ( ? , ? , ? ) ");

        //pass objects from dto object to query as input to be inserted
        try
          {
            jdbc.setInt(1, type_spec.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        try
          {
            jdbc.setInt(2, type_spec.getType_id());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(2, -1);
          }
        try
          {
            jdbc.setInt(3, type_spec.getSpecification_id());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
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

  // mehod to delete relation between type and specification
  public boolean delete(EquipTypeSpecDto type_spec)
  {
    try
      {

        // open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        // query to delete record using its id
        jdbc.setCommand("delete from SPECIFICATION_EQ_TYPE where ID = ? ");

        //pass id from dto object parameter to the query
        try
          {
            jdbc.setInt(1, type_spec.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }

        // query execution
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

  // method to list all relations between types and specifications
  public List<EquipTypeSpecDto> listAll()
  {

    //identify dto list
    List<EquipTypeSpecDto> eq = null;
    try
      {

        //open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        //query to get data from database
        jdbc.setCommand("select ID , ID_EQ_TYPE , NO from SPECIFICATION_EQ_TYPE order by ID ");
        jdbc.execute();

        // identify dto object then pass database resultset to its fields (if exist)
        EquipTypeSpecDto eq_t_sp = null;
        while(jdbc.next())
          {
            if(eq==null)
              eq = new ArrayList<>();
            eq_t_sp = new EquipTypeSpecDto();
            eq_t_sp.setId(jdbc.getInt("ID"));
            eq_t_sp.setType_id(jdbc.getInt("ID_EQ_TYPE"));
            eq_t_sp.setSpecification_id(jdbc.getInt("NO"));

            //add object to the list
            eq.add(eq_t_sp);
            eq_t_sp = null;
          }
      }
    catch(java.sql.SQLException e)
      {
        e.printStackTrace();
        //show message if any sql error occured
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return eq;
  }

  //method to list all specifications of given equipment type
  public List<EquipSpecificationDto> loadAllSpecif(EquipmentTypeDto type)
  {

    //identify list of specifications dto
    List<EquipSpecificationDto> eq_sp = null;
    try
      {

        //open connection with database usig resources
        JdbcRowSet jdbc = user.getConnect();


        // query to obtain specifications of type using the type id and specification id
        jdbc.setCommand("select EQ_SPEECIFICATION.NO , EQ_SPEECIFICATION.NAME , EQ_SPEECIFICATION.VALUE "+
          "from EQ_SPEECIFICATION , SPECIFICATION_EQ_TYPE "+
          "where (SPECIFICATION_EQ_TYPE.NO = EQ_SPEECIFICATION.NO) AND (SPECIFICATION_EQ_TYPE.ID_EQ_TYPE = ? ) ");

        //pass type id to the query from dto object
        jdbc.setInt(1, type.getId());
        jdbc.execute();

        // identify specification dto object then pass the resultset data (if exists) to its fields
        //add object to list
        EquipSpecificationDto e = null;
        while(jdbc.next())
          {
            if(eq_sp==null)
              eq_sp = new ArrayList<>();
            e = new EquipSpecificationDto();
            e.setId(jdbc.getInt("NO"));
            e.setName(jdbc.getString("NAME"));
            e.setValue(jdbc.getString("VALUE"));
            eq_sp.add(e);
            e = null;
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
    return eq_sp;
  }

  //method to ched if record exists in database or not
  public boolean isExist(EquipTypeSpecDto type_spec)
  {
    boolean existFlag = false;
    try
      {

        //open connection with database using resources
        JdbcRowSet jdbc = user.getConnect();


        //query to select the record using its id
        jdbc.setCommand("select ID from SPECIFICATION_EQ_TYPE where ID = ? ");

        //pass id to query
        try
          {
            jdbc.setInt(1, type_spec.getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
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
}
