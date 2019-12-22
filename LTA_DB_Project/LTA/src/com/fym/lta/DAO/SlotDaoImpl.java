package com.fym.lta.DAO;

import com.fym.lta.DTO.CourseDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.StaffSlotDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;

public class SlotDaoImpl implements SlotDao
{

  private UserDto user = null;

  public SlotDaoImpl(UserDto user)
  {
    this.user = user;
  }

  /**insert new slot in DB
   * @param slot,user
   * @return boolean value for success or not
   * */
  public Boolean createNew(SlotDto slot, UserDto user)
  {
    try
      {
        //start new database connection
        JdbcRowSet jdbc = user.getConnect();

        //insert slot in database
        jdbc.setCommand("insert into SLOT (CODE_SCHEDULE_SLOT , DAY , ID_COURSE , SLOT_NUMBER , START_TIME ,END_TIME , NO_OF_STD , SLOT_TYPE , INSERTED_BY , INSERTED_AT ) values(?,?,?,?,?,?,?,?,?,SYSDATE )");
        jdbc.setString(1, slot.getCode());
        jdbc.setString(2, slot.getDay());
        jdbc.setInt(3, slot.getCourse().getId());
        jdbc.setInt(4, slot.getNum());
        jdbc.setString(5, slot.getStart_time());
        jdbc.setString(6, slot.getEnd_time());
        jdbc.setInt(7, slot.getStudent_number());
        jdbc.setString(8, slot.getSlot_type());
        jdbc.setInt(9, user.getId());
        jdbc.execute();

        if(slot.getLocation()!=null)
          {

            //find the mutual slots for this slot
            List<SlotDto> mutual_slots = mutual_slots(slot);

            //check if there is same location assignment
            boolean flage = false;
            if(mutual_slots!=null)
              {
                for(int i = 0; i<mutual_slots.size(); i++)
                  {
                    if(slot.getLocation().getId()==
                      mutual_slots.get(i).getLocation().getId())
                      flage = true;
                  }

              }


            if(flage==false)
              {
                //get location reserved number id from location table
                int i = 0;
                jdbc.setCommand("SELECT reserved_number FROM LOCATION  WHERE ID_LOC=?");
                jdbc.setInt(1, slot.getLocation().getId());
                jdbc.execute();


                while(jdbc.next())
                  {
                    i = jdbc.getInt("reserved_number");
                    i += 1;

                  }

                //increment the reserved number of this location
                jdbc.setCommand("UPDATE LOCATION SET reserved_number=? WHERE ID_LOC=?");
                jdbc.setInt(1, i);
                jdbc.setInt(1, slot.getLocation().getId());
                jdbc.execute();
              }
          }

        return true;
      }
    catch(java.sql.SQLException e)
      {
        return false;
      }
  }

  /**Check if slot is exist in database or not
   * @param slot
   * @return ture if exit, false if not
   * */
  public Boolean isExist(SlotDto slot)
  {

    boolean flag = false;

    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //search for this slot in database using slot code
        jdbc.setCommand("select CODE_SCHEDULE_SLOT , DAY  from SLOT where CODE_SCHEDULE_SLOT = ? ");
        jdbc.setString(1, slot.getCode());
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

  /**Delete a slot from DB
   * @param this slot
   * @return ture if success or false if not */
  public Boolean delete(SlotDto slot)
  {
    try
      {

        //start new database connection
        JdbcRowSet jdbc = user.getConnect();


        //get location id from slot table
        jdbc.setCommand("SELECT ID_LOC FROM SLOT  WHERE CODE_SCHEDULE_SLOT=?");
        jdbc.setString(1, slot.getCode());
        jdbc.execute();

        while(jdbc.next())
          {
            slot.setLocation(new LocationDto());
            slot.getLocation().setId(jdbc.getInt("ID_LOC"));
          }


        if(slot.getLocation()!=null)
          {
            //find the mutual slots for this slot
            List<SlotDto> mutual_slots = mutual_slots(slot);

            //check if there is same location assignment
            boolean flage = false;
            if(mutual_slots!=null)
              {
                for(int i = 0; i<mutual_slots.size(); i++)
                  {
                    if(slot.getLocation().getId()==
                      mutual_slots.get(i).getLocation().getId())
                      flage = true;
                  }

              }


            if(flage==false)
              {
                //get the reserved number of this location from location table
                int i = 0;
                jdbc.setCommand("SELECT reserved_number FROM LOCATION  WHERE ID_LOC=?");
                jdbc.setInt(1, slot.getLocation().getId());
                jdbc.execute();


                while(jdbc.next())
                  {
                    i = jdbc.getInt("reserved_number");
                    i -= 1;
                  }


                //decrement the reserved number of this location
                jdbc.setCommand("UPDATE LOCATION SET reserved_number=? WHERE ID_LOC=?");
                jdbc.setInt(1, i);
                jdbc.setInt(1, slot.getLocation().getId());
                jdbc.execute();

              }
          }


        //delete assignment of staff members to this slot
        jdbc.setCommand("delete from STAFF_SLOT WHERE SLOT_CODE =? ");
        jdbc.setString(1, slot.getCode());
        jdbc.execute();


        //delete this slot
        jdbc.setCommand("delete from SLOT where CODE_SCHEDULE_SLOT = ?");
        jdbc.setString(1, slot.getCode());
        jdbc.execute();


        return true;
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
  }

  /**Update a slot
   * @param this slot, user
   * @return ture or false */
  public Boolean update(SlotDto slot, UserDto user)
  {
    try
      {


        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //find the mutual slots for this slot
        List<SlotDto> mutual_slots = mutual_slots(slot);

        //check if there is same location assignment
        boolean flage = false;
        if(mutual_slots!=null)
          {
            for(int i = 0; i<mutual_slots.size(); i++)
              {
                if(slot.getLocation().getId()==
                  mutual_slots.get(i).getLocation().getId())
                  flage = true;
              }

          }


        if(flage==false)
          {
            int old_loc_id = -1; //id for the old locaion of this slot

            //get old location id from slot table
            jdbc.setCommand("SELECT ID_LOC FROM SLOT  WHERE CODE_SCHEDULE_SLOT=?");
            jdbc.setString(1, slot.getCode());
            jdbc.execute();

            while(jdbc.next())
              {
                old_loc_id = jdbc.getInt("ID_LOC");
              }


            //check if the location would be changed
            if(old_loc_id!=slot.getLocation().getId())
              {


                //check if there is an old location
                if(old_loc_id!=-1)

                  //get the reserved number of this old location from location table
                  {
                    int i = 0;
                    jdbc.setCommand("SELECT reserved_number FROM LOCATION  WHERE ID_LOC=?");
                    jdbc.setInt(1, old_loc_id);
                    jdbc.execute();


                    while(jdbc.next())
                      {
                        i = jdbc.getInt("reserved_number");
                        i -= 1;
                      }

                    //decrement the reserved number of this location
                    jdbc.setCommand("UPDATE LOCATION SET reserved_number=? WHERE ID_LOC=?");
                    jdbc.setInt(1, i);
                    jdbc.setInt(2, old_loc_id);
                    jdbc.execute();
                  }

                int i = 0;
                //get location reserved number id for the new location
                jdbc.setCommand("SELECT reserved_number FROM LOCATION  WHERE ID_LOC=?");
                jdbc.setInt(1, slot.getLocation().getId());
                jdbc.execute();


                while(jdbc.next())
                  {
                    i = jdbc.getInt("reserved_number");
                    i += 1;
                  }

                //increment the reserved number of this location
                jdbc.setCommand("UPDATE LOCATION SET reserved_number=? WHERE ID_LOC=?");
                jdbc.setInt(1, i);
                jdbc.setInt(2, slot.getLocation().getId());
                jdbc.execute();

              }
          }

        //then, update slot information
        jdbc.setCommand("UPDATE SLOT "+
          "set DAY=? , ID_COURSE=? , ID_LOC=? , SLOT_NUMBER=? , START_TIME=? , END_TIME=? , NO_OF_STD=? , SLOT_TYPE=? ,UPDATED_BY=? , UPDATED_AT = SYSDATE "+
          "where CODE_SCHEDULE_SLOT = ?");
        jdbc.setString(1, slot.getDay());
        jdbc.setInt(2, slot.getCourse().getId());
        jdbc.setInt(3, slot.getLocation().getId());
        jdbc.setInt(4, slot.getNum());
        jdbc.setString(5, slot.getStart_time());
        jdbc.setString(6, slot.getEnd_time());
        jdbc.setInt(7, slot.getStudent_number());
        jdbc.setString(8, slot.getSlot_type());
        jdbc.setInt(9, user.getId());
        jdbc.setString(10, slot.getCode());
        jdbc.execute();


        return true; //return ture if success
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;

      }
  }

  /**Search for existing slot/s in database
   * @param slot
   * @return found slots  */
  public List<SlotDto> searchFor(SlotDto slot_obj)
  {

    List<SlotDto> slots = null;

    try
      {

        //start new database connection
        JdbcRowSet jdbc = user.getConnect();


        //search in database
        jdbc.setCommand("select CODE_SCHEDULE_SLOT , DAY , ID_COURSE , ID_LOC , SLOT_NUMBER , START_TIME , END_TIME , NO_OF_STD , SLOT_TYPE "+
          "from SLOT where CODE_SCHEDULE_SLOT=? OR DAY= ? OR ID_COURSE=? OR ID_LOC=? OR SLOT_NUMBER=? OR START_TIME=? OR END_TIME=? OR NO_OF_STD=? OR SLOT_TYPE=?");
        jdbc.setString(1, slot_obj.getSearch());
        jdbc.setString(2, slot_obj.getSearch());
        try
          {
            jdbc.setInt(3, Integer.parseInt(slot_obj.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(3, -1);
          }
        try
          {
            jdbc.setInt(4, Integer.parseInt(slot_obj.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(4, -1);
          }
        try
          {
            jdbc.setInt(5, Integer.parseInt(slot_obj.getSearch()));
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(5, -1);
          }
        jdbc.setString(6, slot_obj.getSearch());
        jdbc.setString(7, slot_obj.getSearch());
        jdbc.setString(8, slot_obj.getSearch());
        jdbc.setString(9, slot_obj.getSearch());
        jdbc.execute();

        SlotDto slot = null;
        LocationDto loc = null;
        //start get result
        while(jdbc.next())
          {
            if(slots==null)
              {
                slots = new ArrayList<>();
              }
            slot = new SlotDto();
            loc = new LocationDto();

            loc.setId(jdbc.getInt("ID_LOC"));
            slot.setCode(jdbc.getString("CODE_SCHEDULE_SLOT"));
            slot.setDay(jdbc.getString("DAY"));
            slot.setCourse(new CourseDto(jdbc.getInt("ID_COURSE")));
            slot.setLocation(loc);
            slot.setNum(jdbc.getInt("SLOT_NUMBER"));
            slot.setStart_time(jdbc.getString("START_TIME"));
            slot.setEnd_time(jdbc.getString("END_TIME"));
            slot.setStudent_number(jdbc.getInt("NO_OF_STD"));
            slot.setSlot_type(jdbc.getString("SLOT_TYPE"));

            slots.add(slot);
            slot = null;
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return slots;

  }

  /**method to get location assigned to slot
   * @param slot
   * @return slot after assign location to it **/
  public SlotDto getSlot(SlotDto slot_obj)
  {
    SlotDto slot = null;
    try
      {


        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //get the location in database
        jdbc.setCommand("select LOCATION.ID_LOC , LOCATION.NAME_LOC "+
          "FROM SLOT JOIN LOCATION ON (SLOT.ID_LOC=LOCATION.ID_LOC) "+
          "WHERE CODE_SCHEDULE_SLOT=? ");
        jdbc.setString(1, slot_obj.getCode());
        jdbc.execute();


        while(jdbc.next())
          {
            slot = new SlotDto();
            LocationDto loc = new LocationDto();
            loc.setId(jdbc.getInt("ID_LOC"));
            loc.setName(jdbc.getString("NAME_LOC"));
            slot.setLocation(loc);
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return slot;
  }

  /** assign staff to a specific slot
   * @param staff slot dto object
   * @return true or false
   * */
  public boolean assignStaff(StaffSlotDto ss)
  {
    try
      {

        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("insert into STAFF_SLOT (ID_STAFF , SLOT_CODE) VALUES(?,?) ");
        try
          {
            jdbc.setInt(1, ss.getStaff().getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, ss.getSlot().getCode());
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

  /**This method for getting all slot for a schedule
   * @param shcedule
   * @return slots
   * */
  public List<SlotDto> ScheduleSlot(ScheduleDto schedule)
  {
    List<SlotDto> slots = null;
    try
      {

        //start new database connection
        JdbcRowSet jdbc = user.getConnect();


        //get all slots of this schedule from data base
        jdbc.setCommand("select S.CODE_SCHEDULE_SLOT , S.DAY , S.ID_COURSE , S.ID_LOC ,  "+
          "S.SLOT_NUMBER , S.START_TIME , S.END_TIME , S.NO_OF_STD ,S.SLOT_TYPE  "+
          "from SLOT S JOIN SCHEDULE_SLOTS SCH ON (S.CODE_SCHEDULE_SLOT = SCH.CODE_SCHEDULE_SLOT) "+
          "where SCH.ID_SCHEDULE=? ");
        jdbc.setInt(1, schedule.getId());
        jdbc.execute();

        SlotDto slot = null;
        LocationDto loc = null;

        //start  get result
        while(jdbc.next())
          {
            if(slots==null)
              {
                slots = new ArrayList<>();
              }
            slot = new SlotDto();
            loc = new LocationDto();

            try
              {
                loc.setId(jdbc.getInt("ID_LOC"));
                slot.setLocation(loc);
              }
            catch(Exception e)
              {
                slot.setLocation(null);
              }

            slot.setCode(jdbc.getString("CODE_SCHEDULE_SLOT"));
            slot.setDay(jdbc.getString("DAY"));
            slot.setCourse(new CourseDto(jdbc.getInt("ID_COURSE")));
            slot.setNum(jdbc.getInt("SLOT_NUMBER"));
            slot.setStart_time(jdbc.getString("START_TIME"));
            slot.setEnd_time(jdbc.getString("END_TIME"));
            slot.setStudent_number(jdbc.getInt("NO_OF_STD"));
            slot.setSlot_type(jdbc.getString("SLOT_TYPE"));


            slots.add(slot);
            slot = null;
            loc = null;

          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return slots;


  }

  /**get mutual slot
   * @param take slot
   * @return its mutual slots
   * */
  public List<SlotDto> mutual_slots(SlotDto slot)
  {


    List<SlotDto> slots = null;
    List<SlotDto> result = null;

    try
      {

        //start new database connection
        JdbcRowSet jdbc = user.getConnect();


        //get all mutual slots of this slot
        jdbc.setCommand("select CODE_SCHEDULE_SLOT , DAY ,ID_COURSE , ID_LOC ,   "+
          "  SLOT_NUMBER , START_TIME , END_TIME , NO_OF_STD ,SLOT_TYPE   "+
          "  from SLOT where DAY=? AND SLOT_NUMBER=?  "+
          " AND NO_OF_STD=? AND CODE_SCHEDULE_SLOT!= ? ");
        jdbc.setString(1, slot.getDay());
        jdbc.setInt(2, slot.getNum());
        jdbc.setInt(3, slot.getStudent_number());
        jdbc.setString(4, slot.getCode());
        jdbc.execute();

        SlotDto slot1 = null;
        LocationDto loc = null;

        //start  get result
        while(jdbc.next())
          {
            if(slots==null)
              {
                slots = new ArrayList<>();
              }
            slot1 = new SlotDto();
            loc = new LocationDto();

            try
              {
                loc.setId(jdbc.getInt("ID_LOC"));
                slot1.setLocation(loc);
              }
            catch(Exception e)
              {
                slot1.setLocation(null);
              }

            slot1.setCode(jdbc.getString("CODE_SCHEDULE_SLOT"));
            slot1.setDay(jdbc.getString("DAY"));
            slot1.setCourse(new CourseDto(jdbc.getInt("ID_COURSE")));
            slot1.setNum(jdbc.getInt("SLOT_NUMBER"));
            slot1.setStart_time(jdbc.getString("START_TIME"));
            slot1.setEnd_time(jdbc.getString("END_TIME"));
            slot1.setStudent_number(jdbc.getInt("NO_OF_STD"));
            slot1.setSlot_type(jdbc.getString("SLOT_TYPE"));


            slots.add(slot1);
            slot1 = null;
            loc = null;

          }

        List<StaffDto> staff_slot = null;
        List<StaffDto> staff_slot1 = null;
        StaffDto staff = null;

        //get staff to the given slot
        jdbc.setCommand("select ID_STAFF from Staff_SLOT where SLOT_CODE = ? ");
        jdbc.setString(1, slot.getCode());
        jdbc.execute();

        while(jdbc.next())
          {
            if(staff_slot==null)
              {
                staff_slot = new ArrayList<>();
              }

            staff = new StaffDto();
            staff.setId(jdbc.getInt("ID_STAFF"));

            staff_slot.add(staff);
            staff = null;

          }

        //check if there are assigned to the same staff
        if(slots!=null&&!slots.isEmpty())
          {
            for(int i = 0; i<slots.size(); i++)
              {
                //get staff to the slot i
                jdbc.setCommand("select ID_STAFF from Staff_SLOT where SLOT_CODE = ? ");
                jdbc.setString(1, slots.get(i).getCode());
                jdbc.execute();

                while(jdbc.next())
                  {
                    if(staff_slot1==null)
                      {
                        staff_slot1 = new ArrayList<>();
                      }

                    staff = new StaffDto();
                    staff.setId(jdbc.getInt("ID_STAFF"));

                    staff_slot1.add(staff);
                    staff = null;

                  }

                //check if there are the same
                int k = 0, j = 0;
                for(j = 0; j<staff_slot.size(); j++)
                  {
                    for(k = 0; k<staff_slot1.size(); k++)
                      {
                        //if there is any match break loop two
                        if(staff_slot.get(j).getId()==
                          staff_slot1.get(k).getId())
                          break;

                      }
                    //if the second loop end/break and there is no match for this staff break the first loop
                    if(k==staff_slot1.size()-1&&
                      staff_slot.get(j).getId()!=staff_slot1.get(k).getId())
                      {
                        j = -1;
                        break;
                      }
                  }
                if(j!=-1)
                  {
                    if(result==null)
                      result = new ArrayList<SlotDto>();

                    result.add(slots.get(i));
                  }
              }
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();

      }

    return slots; //return mutual slots

  }


  /** get perefered location type of a slot
   * @param take a slot
   * @return location type
   * */
  public LocationTypeDto lec_loc_type(SlotDto slot)
  {

    LocationTypeDto location_type = null;

    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //get the location type from course table in database
        jdbc.setCommand("SELECT PLT_LEC FROM COURSE WHERE ID_COURSE=? ");
        jdbc.setInt(1, slot.getCourse().getId());
        jdbc.execute();


        while(jdbc.next())
          {
            location_type = new LocationTypeDto();
            location_type.setId(jdbc.getInt("PLT_LEC"));
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return location_type;


  }

  /** get perefered location type of a section slot
   * @param take a slot
   * @return location type
   * */
  public LocationTypeDto sec_loc_type(SlotDto slot)
  {

    LocationTypeDto location_type = null;

    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //get the location type from course table in database
        jdbc.setCommand("SELECT PLT_SEC FROM COURSE WHERE ID_COURSE=? ");
        jdbc.setInt(1, slot.getCourse().getId());
        jdbc.execute();


        while(jdbc.next())
          {
            location_type = new LocationTypeDto();
            location_type.setId(jdbc.getInt("PLT_SEC"));
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return location_type;


  }


  /**get slots assigned to location
   * used in location accupancy
   * @param take location
   * @return its slot list
   * */
  public List<SlotDto> location_accupancy(LocationDto location)
  {

    List<SlotDto> slots = null;
    List<SlotDto> result = null;

    try
      {

        //start new database connection
        JdbcRowSet jdbc = user.getConnect();


        //get location id
        jdbc.setCommand("SELECT ID_LOC FROM LOCATION WHERE CODE_LOC=?");
        jdbc.setString(1, location.getCode());
        jdbc.execute();

        while(jdbc.next())
          {
            location.setId(jdbc.getInt("ID_LOC"));
          }


        //get all slots assigned to this location from data base
        jdbc.setCommand(" select C.NAME_COURSE , C.CODE_COURSE ,S.CODE_SCHEDULE_SLOT, FS.SLOT_DAY , S.ID_COURSE , S.ID_LOC , FS.SLOT_NUMBER, S.NO_OF_STD ,S.SLOT_TYPE  "+
          "          from SLOT S JOIN FACULTY_SLOTS FS ON (FS.SLOT_CODE=S.SLOT_CODE) "+
          "          JOIN COURSE C ON (S.ID_COURSE = C.ID_COURSE) "+
          "          WHERE S.ID_LOC =? ");
        jdbc.setInt(1, location.getId());
        jdbc.execute();

        SlotDto slot = null;
        LocationDto loc = null;

        //start  get result
        while(jdbc.next())
          {
            if(slots==null)
              {
                slots = new ArrayList<>();
              }
            slot = new SlotDto();
            loc = new LocationDto();


            loc.setId(jdbc.getInt("ID_LOC"));
            slot.setCode(jdbc.getString("CODE_SCHEDULE_SLOT"));
            slot.setDay(jdbc.getString("SLOT_DAY"));
            slot.setCourse(new CourseDto(jdbc.getInt("ID_COURSE")));
            slot.getCourse().setCode(jdbc.getString("CODE_COURSE"));
            slot.getCourse().setName(jdbc.getString("NAME_COURSE"));
            slot.setNum(jdbc.getInt("SLOT_NUMBER"));
            slot.setStudent_number(jdbc.getInt("NO_OF_STD"));
            slot.setSlot_type(jdbc.getString("SLOT_TYPE"));


            slots.add(slot);
            slot = null;
            loc = null;
          }

        result = slots; //assign found slots to result

        //remove mutual slots

        for(int i = 0; slots!=null&&i<slots.size(); i++)
          {
            List<SlotDto> mutuals = this.mutual_slots(slots.get(i));
            if(mutuals!=null&&!mutuals.isEmpty())
              {
                for(int j = 0; j<mutuals.size(); j++)
                  {
                    result.remove(mutuals.get(j));
                  }
              }
          }


        //get staff assigned to this slots
        for(int i = 0; result!=null&&i<result.size(); i++)
          {

            List<StaffDto> staffs = null;

            //get all staff for each slot from data base
            jdbc.setCommand("select S.ID_STAFF, S.NAME_STAFF, S.POSTION_STAFF  "+
              "FROM STAFF S JOIN STAFF_SLOT SS ON (S.ID_STAFF = SS.ID_STAFF) "+
              "WHERE SS.SLOT_CODE=? ");
            jdbc.setString(1, result.get(i).getCode());
            jdbc.execute();

            StaffDto staff = null;

            while(jdbc.next())
              {
                if(staffs==null)
                  staffs = new ArrayList<StaffDto>();

                staff = new StaffDto();

                staff.setId(jdbc.getInt("ID_STAFF"));
                staff.setName(jdbc.getString("NAME_STAFF"));

                String position = jdbc.getString("POSTION_STAFF");

                if(position.equalsIgnoreCase("Demonstrator"))
                  {
                    staff.setPosition("DR");
                  }
                else if(position.equalsIgnoreCase("Associate Professor"))
                  {
                    staff.setPosition("ASSOC.PROF");
                  }
                else if(position.equalsIgnoreCase("Professor"))
                  {
                    staff.setPosition("PROF");
                  }
                else if(position.equalsIgnoreCase("Assistant lecturer"))
                  {
                    staff.setPosition("ENG");
                  }
                else
                  {
                    staff.setPosition("ASST.PROF");
                  }

                staffs.add(staff);
                staff = null;
              }

            if(staffs!=null)
              result.get(i).setStaff(staffs);

            staffs = null;
          }


      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return result;

  }

  /**get slots assigned to staff
   * used in staff load 
   * @param take staff member 
   * @return assigned slot list
   * */
  public List<SlotDto> staff_load(StaffDto staff_member)
  {

    List<SlotDto> slots = null; //for result search set
    List<SlotDto> result = null; //final result 

    try
      {

        //start new database connection
        JdbcRowSet jdbc = user.getConnect();


        //get staff id
        jdbc.setCommand("SELECT ID_STAFF FROM STAFF WHERE NAME_STAFF=?");
        jdbc.setString(1, staff_member.getName());
        jdbc.execute();

        while(jdbc.next())
          {
            staff_member.setId(jdbc.getInt("ID_STAFF"));
          }


        //get all slots assigned to this location from data base
        jdbc.setCommand("select C.NAME_COURSE , C.CODE_COURSE ,S.CODE_SCHEDULE_SLOT, FS.SLOT_DAY , S.ID_COURSE , S.ID_LOC , FS.SLOT_NUMBER, S.NO_OF_STD ,S.SLOT_TYPE   "+
          "          from SLOT S JOIN FACULTY_SLOTS FS ON (FS.SLOT_CODE=S.SLOT_CODE) "+
          "          JOIN COURSE C ON (S.ID_COURSE = C.ID_COURSE)  "+
          "          JOIN STAFF_SLOT SS ON (S.CODE_SCHEDULE_SLOT = SS.SLOT_CODE) "+
          "          WHERE SS.ID_STAFF=? ");
      
        jdbc.setInt(1, staff_member.getId());
        jdbc.execute();

        SlotDto slot = null;
        LocationDto loc = null;

        //start  get result
        while(jdbc.next())
          {
            if(slots==null)
              {
                slots = new ArrayList<>();
              }
            slot = new SlotDto();
            loc = new LocationDto();


            loc.setId(jdbc.getInt("ID_LOC"));
            slot.setCode(jdbc.getString("CODE_SCHEDULE_SLOT"));
            slot.setDay(jdbc.getString("SLOT_DAY"));
            slot.setCourse(new CourseDto(jdbc.getInt("ID_COURSE")));
            slot.getCourse().setCode(jdbc.getString("CODE_COURSE"));
            slot.getCourse().setName(jdbc.getString("NAME_COURSE"));
            slot.setNum(jdbc.getInt("SLOT_NUMBER"));
            slot.setStudent_number(jdbc.getInt("NO_OF_STD"));
            slot.setSlot_type(jdbc.getString("SLOT_TYPE"));


            slots.add(slot);
            slot = null;
            loc = null;
          }

        result = slots; //assign found slots to result

        //remove mutual slots
        for(int i = 0; slots!=null&&i<slots.size(); i++)
          {
            List<SlotDto> mutuals = this.mutual_slots(slots.get(i));
            if(mutuals!=null&&!mutuals.isEmpty())
              {
                for(int j = 0; j<mutuals.size(); j++)
                  {
                    result.remove(mutuals.get(j));
                  }
              }
          }


        //get staff assigned to this slots
        for(int i = 0; result!=null&&i<result.size(); i++)
          {

            List<StaffDto> staffs = null;

            //get all staff for each slot from data base
            jdbc.setCommand("select S.ID_STAFF, S.NAME_STAFF, S.POSTION_STAFF  "+
              "FROM STAFF S JOIN STAFF_SLOT SS ON (S.ID_STAFF = SS.ID_STAFF) "+
              "WHERE SS.SLOT_CODE=? ");
            jdbc.setString(1, result.get(i).getCode());
            jdbc.execute();

            StaffDto staff = null;

            while(jdbc.next())
              {
                if(staffs==null)
                  staffs = new ArrayList<StaffDto>();

                staff = new StaffDto();

                staff.setId(jdbc.getInt("ID_STAFF"));
                staff.setName(jdbc.getString("NAME_STAFF"));

                String position = jdbc.getString("POSTION_STAFF");

                if(position.equalsIgnoreCase("Demonstrator"))
                  {
                    staff.setPosition("DR");
                  }
                else if(position.equalsIgnoreCase("Associate Professor"))
                  {
                    staff.setPosition("ASSOC.PROF");
                  }
                else if(position.equalsIgnoreCase("Professor"))
                  {
                    staff.setPosition("PROF");
                  }
                else if(position.equalsIgnoreCase("Assistant lecturer"))
                  {
                    staff.setPosition("ENG");
                  }
                else
                  {
                    staff.setPosition("ASST.PROF");
                  }

                staffs.add(staff);
                staff = null;
              }

            if(staffs!=null)
              result.get(i).setStaff(staffs);

            staffs = null;
          }


      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return result;

  }
  
  
  
  /** check assignment of staff to a specific slot
   * @param staff slot dto object
   * @return true or false
   * */
  public boolean isAssigned(StaffSlotDto ss)
  {

    boolean assignedFlag = false;
    try
      {
        // start connection with database
        JdbcRowSet jdbc = user.getConnect();


        // SQL query to get the data of assignment if exists
        jdbc.setCommand("select ID_STAFF , SLOT_CODE from STAFF_SLOT where ID_STAFF=? and SLOT_CODE=?");

        // pass parameters to querythen execute
        try
          {
            jdbc.setInt(1, ss.getStaff().getId());
          }
        catch(NumberFormatException e)
          {
            jdbc.setInt(1, -1);
          }
        jdbc.setString(2, ss.getSlot().getCode());
        jdbc.execute();

        // set flag to true if there exist resultset
        while(jdbc.next())
          {
            assignedFlag = true;
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return assignedFlag;
  }

  /** get all slots and its info. for a specific schedule
   * to view it in UI
   * @param take selected stage, department
   * @return slot list
   * */
  public List<SlotDto> viewSlotsOfSchedule(StageDto stage,
    DepartmentDto department)
  {

    // identify a list of slots for the schedule
    List<SlotDto> slots = null;

    try
      {

        // start connection with database
        JdbcRowSet jdbc = user.getConnect();


        // SQL query to get department code from its name
        jdbc.setCommand("SELECT CODE_DEPARTMENT FROM DEPARTMENT WHERE NAME_DEPARTMENT=? ");

        // pass the department name to query and execute
        jdbc.setString(1, department.getName());
        jdbc.execute();

        // set the department code from the resultset
        while(jdbc.next())
          {
            department.setCode(jdbc.getString("CODE_DEPARTMENT"));
          }


        // set stage code
        String stageCode = department.getCode()+"-"+stage.getNumber();
        stage.setCode(stageCode);

        /** get schedule id of the stage **/

        // SQL query to get schedule id of the stage
        jdbc.setCommand("select ID_SCHEDULE FROM SCHEDULE_STAGE WHERE CODE_STAGE=?");

        // pass stage code to the query and execute
        jdbc.setString(1, stage.getCode());
        jdbc.execute();

        // identify schedule id and set it from the resultset
        int scheduleId = 0;
        while(jdbc.next())
          {
            scheduleId = jdbc.getInt("ID_SCHEDULE");
          }

        /** get slots' code of schedule **/

        // SQL query to get slots codes from schedule id
        jdbc.setCommand("select CODE_SCHEDULE_SLOT from SCHEDULE_SLOTS where ID_SCHEDULE=?");

        // pass schedule id to query and execute
        jdbc.setInt(1, scheduleId);
        jdbc.execute();

        SlotDto slot = null;
        // set each slot code from query resultset then add to the list of slots
        while(jdbc.next())
          {

            if(slots==null)
              slots = new ArrayList<>();

            slot = new SlotDto();
            slot.setCode(jdbc.getString("CODE_SCHEDULE_SLOT"));
            slots.add(slot);
            slot = null;
          }


        /** get term of schedule and set to each slot **/

        // SQL query to get term from schedule table using schedule id
        jdbc.setCommand("select TERM from SCHEDULE where ID_SCHEDULE=?");

        // pass schedule id to query then execute
        jdbc.setInt(1, scheduleId);
        jdbc.execute();

        // get term from result set and set to all slots in schedule
        while(jdbc.next())
          {
            for(int i = 0; i<slots.size(); i++)
              {
                slots.get(i).setTerm(jdbc.getInt("TERM"));
              }
          }


        /** get slots' data of schedule **/
        // get data of each slot in list
        for(int i = 0; slots!=null&&i<slots.size(); i++)
          {

            // SQL query to get slot data using its code
            jdbc.setCommand("select DAY , ID_COURSE , ID_LOC , SLOT_NUMBER , START_TIME, END_TIME, "+
              "NO_OF_STD, SLOT_TYPE "+
              " from SLOT where CODE_SCHEDULE_SLOT =?");

            // pass slot code to query then execute
            jdbc.setString(1, slots.get(i).getCode());
            jdbc.execute();

            // save data in list from the query result set
            CourseDto course = null;
            LocationDto location = null;
            while(jdbc.next())
              {

                slots.get(i).setDay(jdbc.getString("DAY"));
                slots.get(i).setEnd_time(jdbc.getString("END_TIME"));
                slots.get(i).setStart_time(jdbc.getString("START_TIME"));
                slots.get(i).setStudent_number(jdbc.getInt("NO_OF_STD"));
                slots.get(i).setNum(jdbc.getInt("SLOT_NUMBER"));
                slots.get(i).setSlot_type(jdbc.getString("SLOT_TYPE"));

                location = new LocationDto();
                location.setId(jdbc.getInt("ID_LOC"));

                course = new CourseDto();
                course.setId(jdbc.getInt("ID_COURSE"));
                slots.get(i).setCourse(course);

                // SQL query to get location name and code using its id from location table
                jdbc.setCommand("select NAME_LOC , CODE_LOC FROM LOCATION where ID_LOC=? ");

                // pass id to query then execute
                jdbc.setInt(1, location.getId());
                jdbc.execute();

                // set location code and name from resultset then add to slots list
                while(jdbc.next())
                  {
                    
                    location.setName(jdbc.getString("NAME_LOC"));
                    location.setCode(jdbc.getString("CODE_LOC"));
                    slots.get(i).setLocation(location);
                    
                  }

                location = null;
                course = null;
              }
          }


        /** get course code and name and plt ids of each slot **/

        // get course code and name, plt_lecture id and plt_section id of each slot in list
        for(int i = 0; slots!=null&&i<slots.size(); i++)
          {

            //SQL query to get course name and code from its id assigned to slot
            jdbc.setCommand("select CODE_COURSE , NAME_COURSE , PLT_LEC , PLT_SEC "+
              "from COURSE where ID_COURSE =? ");

            // Pass id to query then execute
            jdbc.setInt(1, slots.get(i).getCourse().getId());
            jdbc.execute();

            // get code and name from result set and save to course attribute of slot
            LocationTypeDto plt_lec = null;
            LocationTypeDto plt_sec = null;
            while(jdbc.next())
              {

                plt_lec = new LocationTypeDto();
                plt_lec.setId(jdbc.getInt("PLT_LEC"));
                slots.get(i).getCourse().setPlt_lecture(plt_lec);

                plt_sec = new LocationTypeDto();
                plt_sec.setId(jdbc.getInt("PLT_SEC"));
                slots.get(i).getCourse().setPlt_section(plt_sec);

                slots.get(i).getCourse().setCode(jdbc.getString("CODE_COURSE"));
                slots.get(i).getCourse().setName(jdbc.getString("NAME_COURSE"));
                plt_lec = null;
                plt_sec = null;
              }
          }


        /** get the plt_lecture and plt_section code of course of each slot **/
        for(int i = 0; i<slots.size(); i++)
          {

            // SQL query to get location code using its id for plt_lecture
            jdbc.setCommand("select CODE_LOC_TYPE from LOCATION_TYPE where ID_LOC_TYPE=? ");

            // passing id to query then execute
            jdbc.setInt(1,
              slots.get(i).getCourse().getPlt_lecture().getId());
            jdbc.execute();

            // set the plt code of course of slot from the resultset
            while(jdbc.next())
              {
                slots.get(i).getCourse().getPlt_lecture().setCode(jdbc.getString("CODE_LOC_TYPE"));
              }


            // SQL query to get location code using its id for plt_section
            jdbc.setCommand("select CODE_LOC_TYPE from LOCATION_TYPE where ID_LOC_TYPE=? ");

            // passing id to query then execute
            jdbc.setInt(1,
              slots.get(i).getCourse().getPlt_section().getId());
            jdbc.execute();

            // set the plt code of course of slot from the resultset
            while(jdbc.next())
              {
                slots.get(i).getCourse().getPlt_section().setCode(jdbc.getString("CODE_LOC_TYPE"));
              }
          }

        /** get staff name and his user email of each slot **/

            //get staff assigned to this slots
                    for(int i = 0; slots!=null&&i<slots.size(); i++)
                      {

                        List<StaffDto> staffs = null;

                        //get all slots assigned to this location from data base
                        jdbc.setCommand("select U.email_user, S.ID_STAFF, S.NAME_STAFF, S.POSTION_STAFF  " + 
                        "                          FROM STAFF S JOIN STAFF_SLOT SS ON (S.ID_STAFF = SS.ID_STAFF) " + 
                        "                          JOIN STAFF_ACC SA ON (S.ID_STAFF=SA.ID_STAFF) " + 
                        "                          JOIN USER_TABLE U ON (U.ID_USER=SA.ID_USER) " + 
                        "                          WHERE SS.SLOT_CODE=? ");
                        jdbc.setString(1, slots.get(i).getCode());
                        jdbc.execute();

                        StaffDto staff = null;
                        UserDto user =null;
                        while(jdbc.next())
                          {
                            if(staffs==null)
                              staffs = new ArrayList<StaffDto>();

                            staff = new StaffDto();
                            user = new UserDto();
                            
                            staff.setId(jdbc.getInt("ID_STAFF"));
                            staff.setName(jdbc.getString("NAME_STAFF"));
                            String position = jdbc.getString("POSTION_STAFF");
                            
                            user.setEmail(jdbc.getString("email_user"));
                            if(position.equalsIgnoreCase("Demonstrator"))
                              {
                                staff.setPosition("DR");
                              }
                            else if(position.equalsIgnoreCase("Associate Professor"))
                              {
                                staff.setPosition("ASSOC.PROF");
                              }
                            else if(position.equalsIgnoreCase("Professor"))
                              {
                                staff.setPosition("PROF");
                              }
                            else if(position.equalsIgnoreCase("Assistant lecturer"))
                              {
                                staff.setPosition("ENG");
                              }
                            else
                              {
                                staff.setPosition("ASST.PROF");
                              }
                            
                            staff.setUser(user);
                            staffs.add(staff);
                            
                            staff = null;
                            user =null;
                            
                          }

                        if(staffs!=null)
                          slots.get(i).setStaff(staffs);

                        staffs = null;
                      }
      }

    catch(Exception e)
      {
        e.printStackTrace();
      }
    return slots;
  }


  /** get all slots and its info. for a specific schedule
   * to view it in UI
   * @param take selected stage, department
   * @return slot list
   * */
  public List<SlotDto> viewSlotsOfSchedule(ScheduleDto schedule)
  {

    // identify a list of slots for the schedule
    List<SlotDto> slots = null;
    try
      {

        // start connection with database
        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("select ID_SCHEDULE from SCHEDULE where path_sch =?");
        jdbc.setString(1, schedule.getFile_path());
        jdbc.execute();
        while(jdbc.next())
          {
            schedule.setId(jdbc.getInt("ID_SCHEDULE"));
          }

        /** get slots' code of schedule **/

        // SQL query to get slots codes from schedule id
        jdbc.setCommand("select CODE_SCHEDULE_SLOT from SCHEDULE_SLOTS where ID_SCHEDULE=?");

        //pass schedule id to query and execute
        jdbc.setInt(1, schedule.getId());
        jdbc.execute();

        SlotDto slot = null;
        // set each slot code from query resultset then add to the list of slots
        while(jdbc.next())
          {

            if(slots==null)
              slots = new ArrayList<>();

            slot = new SlotDto();
            slot.setCode(jdbc.getString("CODE_SCHEDULE_SLOT"));
            slots.add(slot);
            slot = null;
          }


        /** get term of schedule and set to each slot **/

        // SQL query to get term from schedule table using schedule id
        jdbc.setCommand("select TERM from SCHEDULE where ID_SCHEDULE=?");

        // pass schedule id to query then execute
        jdbc.setInt(1, schedule.getId());
        jdbc.execute();

        // get term from result set and set to all slots in schedule
        while(jdbc.next())
          {
            for(int i = 0; i<slots.size(); i++)
              {
                slots.get(i).setTerm(jdbc.getInt("TERM"));
              }
          }


        /** get slots' data of schedule **/
        // get data of each slot in list
        for(int i = 0; slots!=null&&i<slots.size(); i++)
          {

            // SQL query to get slot data using its code
            jdbc.setCommand("select DAY , ID_COURSE , ID_LOC , SLOT_NUMBER , START_TIME, END_TIME, "+
              "NO_OF_STD, SLOT_TYPE "+
              " from SLOT where CODE_SCHEDULE_SLOT =?");

            // pass slot code to query then execute
            jdbc.setString(1, slots.get(i).getCode());
            jdbc.execute();

            // save data in list from the query result set
            CourseDto course = null;
            LocationDto location = null;
            while(jdbc.next())
              {

                slots.get(i).setDay(jdbc.getString("DAY"));
                slots.get(i).setEnd_time(jdbc.getString("END_TIME"));
                slots.get(i).setStart_time(jdbc.getString("START_TIME"));
                slots.get(i).setStudent_number(jdbc.getInt("NO_OF_STD"));
                slots.get(i).setNum(jdbc.getInt("SLOT_NUMBER"));
                slots.get(i).setSlot_type(jdbc.getString("SLOT_TYPE"));

                location = new LocationDto();
                location.setId(jdbc.getInt("ID_LOC"));

                course = new CourseDto();
                course.setId(jdbc.getInt("ID_COURSE"));
                slots.get(i).setCourse(course);

                // SQL query to get location name and code using its id from location table
                jdbc.setCommand("select NAME_LOC , CODE_LOC FROM LOCATION where ID_LOC=? ");

                // pass id to query then execute
                jdbc.setInt(1, location.getId());
                jdbc.execute();

                // set location code and name from resultset then add to slots list
                while(jdbc.next())
                  {
                    location.setName(jdbc.getString("NAME_LOC"));
                    location.setCode(jdbc.getString("CODE_LOC"));

                    slots.get(i).setLocation(location);
                  }

                location = null;
                course = null;
              }
          }


        /** get course code and name and plt ids of each slot **/

        // get course code and name, plt_lecture id and plt_section id of each slot in list
        for(int i = 0; slots!=null&&i<slots.size(); i++)
          {

            //SQL query to get course name and code from its id assigned to slot
            jdbc.setCommand("select CODE_COURSE , NAME_COURSE , PLT_LEC , PLT_SEC "+
              "from COURSE where ID_COURSE =? ");

            // Pass id to query then execute
            jdbc.setInt(1, slots.get(i).getCourse().getId());
            jdbc.execute();

            // get code and name from result set and save to course attribute of slot
            LocationTypeDto plt_lec = null;
            LocationTypeDto plt_sec = null;
            while(jdbc.next())
              {

                plt_lec = new LocationTypeDto();
                plt_lec.setId(jdbc.getInt("PLT_LEC"));
                slots.get(i).getCourse().setPlt_lecture(plt_lec);

                plt_sec = new LocationTypeDto();
                plt_sec.setId(jdbc.getInt("PLT_SEC"));
                slots.get(i).getCourse().setPlt_section(plt_sec);

                slots.get(i).getCourse().setCode(jdbc.getString("CODE_COURSE"));
                slots.get(i).getCourse().setName(jdbc.getString("NAME_COURSE"));
                plt_lec = null;
                plt_sec = null;
              }
          }


        /** get the plt_lecture and plt_section code of course of each slot **/

        for(int i = 0; i<slots.size(); i++)
          {

            // SQL query to get location code using its id for plt_lecture
            jdbc.setCommand("select CODE_LOC_TYPE from LOCATION_TYPE where ID_LOC_TYPE=? ");

            // passing id to query then execute
            jdbc.setInt(1,
              slots.get(i).getCourse().getPlt_lecture().getId());
            jdbc.execute();

            // set the plt code of course of slot from the resultset
            while(jdbc.next())
              {
                slots.get(i).getCourse().getPlt_lecture().setCode(jdbc.getString("CODE_LOC_TYPE"));
              }


            // SQL query to get location code using its id for plt_section
            jdbc.setCommand("select CODE_LOC_TYPE from LOCATION_TYPE where ID_LOC_TYPE=? ");

            // passing id to query then execute
            jdbc.setInt(1,
              slots.get(i).getCourse().getPlt_section().getId());
            jdbc.execute();

            // set the plt code of course of slot from the resultset
            while(jdbc.next())
              {
                slots.get(i).getCourse().getPlt_section().setCode(jdbc.getString("CODE_LOC_TYPE"));
              }
          }

        /** get staff name and his user email of each slot **/

            //get staff assigned to this slots
                    for(int i = 0; slots!=null&&i<slots.size(); i++)
                      {

                        List<StaffDto> staffs = null;

                        //get all slots assigned to this location from data base
                        jdbc.setCommand("select U.email_user, S.ID_STAFF, S.NAME_STAFF, S.POSTION_STAFF  " + 
                        "                          FROM STAFF S JOIN STAFF_SLOT SS ON (S.ID_STAFF = SS.ID_STAFF) " + 
                        "                          JOIN STAFF_ACC SA ON (S.ID_STAFF=SA.ID_STAFF) " + 
                        "                          JOIN USER_TABLE U ON (U.ID_USER=SA.ID_USER) " + 
                        "                          WHERE SS.SLOT_CODE=? ");
                        jdbc.setString(1, slots.get(i).getCode());
                        jdbc.execute();

                        StaffDto staff = null;
                        UserDto user =null;
                        while(jdbc.next())
                          {
                            if(staffs==null)
                              staffs = new ArrayList<StaffDto>();

                            staff = new StaffDto();
                            user = new UserDto();
                            
                            staff.setId(jdbc.getInt("ID_STAFF"));
                            staff.setName(jdbc.getString("NAME_STAFF"));
                            String position = jdbc.getString("POSTION_STAFF");
                            
                            user.setEmail(jdbc.getString("email_user"));
                            if(position.equalsIgnoreCase("Demonstrator"))
                              {
                                staff.setPosition("DR");
                              }
                            else if(position.equalsIgnoreCase("Associate Professor"))
                              {
                                staff.setPosition("ASSOC.PROF");
                              }
                            else if(position.equalsIgnoreCase("Professor"))
                              {
                                staff.setPosition("PROF");
                              }
                            else if(position.equalsIgnoreCase("Assistant lecturer"))
                              {
                                staff.setPosition("ENG");
                              }
                            else
                              {
                                staff.setPosition("ASST.PROF");
                              }
                            
                            staff.setUser(user);
                            staffs.add(staff);
                            
                            staff = null;
                            user =null;
                            
                          }

                        if(staffs!=null)
                          slots.get(i).setStaff(staffs);

                        staffs = null;
                      }
      }

    catch(Exception e)
      {
        e.printStackTrace();
      }
    return slots;
  }


  public Boolean updateManual(SlotDto slot, UserDto user)
  {
    try
      {

        //start database connection
        JdbcRowSet jdbc = user.getConnect();


        //find id location
        jdbc.setCommand("SELECT ID_LOC FROM LOCATION  WHERE NAME_LOC=?");
        jdbc.setString(1, slot.getLocation().getName());
        jdbc.execute();

        while(jdbc.next())
          {
            slot.getLocation().setId(jdbc.getInt("ID_LOC"));
          }

        //find the mutual slots for this slot
        List<SlotDto> mutual_slots = mutual_slots(slot);

        //check if there is same location assignment
        boolean flage = false;
        if(mutual_slots!=null)
          {
            for(int i = 0; i<mutual_slots.size(); i++)
              {
                if(slot.getLocation().getId()==
                  mutual_slots.get(i).getLocation().getId())
                  flage = true;
              }

          }


        if(flage==false)
          {
            int old_loc_id = -1; //id for the old locaion of this slot

            //get old location id from slot table
            jdbc.setCommand("SELECT ID_LOC FROM SLOT  WHERE CODE_SCHEDULE_SLOT=?");
            jdbc.setString(1, slot.getCode());
            jdbc.execute();

            while(jdbc.next())
              {
                old_loc_id = jdbc.getInt("ID_LOC");
              }


            //check if the location would be changed
            if(old_loc_id!=slot.getLocation().getId())
              {


                //check if there is an old location
                if(old_loc_id!=-1)

                  //get the reserved number of this old location from location table
                  {
                    int i = 0;
                    jdbc.setCommand("SELECT reserved_number FROM LOCATION  WHERE ID_LOC=?");
                    jdbc.setInt(1, old_loc_id);
                    jdbc.execute();


                    while(jdbc.next())
                      {
                        i = jdbc.getInt("reserved_number");
                        i -= 1;
                      }

                    //decrement the reserved number of this location
                    jdbc.setCommand("UPDATE LOCATION SET reserved_number=? WHERE ID_LOC=?");
                    jdbc.setInt(1, i);
                    jdbc.setInt(2, old_loc_id);
                    jdbc.execute();
                  }

                int i = 0;
                //get location reserved number id for the new location
                jdbc.setCommand("SELECT reserved_number FROM LOCATION  WHERE ID_LOC=?");
                jdbc.setInt(1, slot.getLocation().getId());
                jdbc.execute();


                while(jdbc.next())
                  {
                    i = jdbc.getInt("reserved_number");
                    i += 1;
                  }

                //increment the reserved number of this location
                jdbc.setCommand("UPDATE LOCATION SET reserved_number=? WHERE ID_LOC=?");
                jdbc.setInt(1, i);
                jdbc.setInt(2, slot.getLocation().getId());
                jdbc.execute();

              }
          }


        //then, update slot information
        jdbc.setCommand("UPDATE SLOT SET ID_LOC=? ,UPDATED_BY=? , UPDATED_AT = SYSDATE "+
          "where CODE_SCHEDULE_SLOT = ?");
        jdbc.setInt(1, slot.getLocation().getId());
        jdbc.setInt(2, user.getId());
        jdbc.setString(3, slot.getCode());
        jdbc.execute();


        return true; //return ture if success
      }
    catch(Exception e)
      {
        e.printStackTrace();
        return false;

      }
  }

  public List<SlotDto> getSlots(SlotDto s, ScheduleDto sch)
  {
    // TODO Implement this method
    List<SlotDto> slots = null;

    try
      {

        //start new database connection
        JdbcRowSet jdbc = user.getConnect();


        //search in database
        jdbc.setCommand("SELECT S.CODE_SCHEDULE_SLOT\n"+
          "FROM SLOT S JOIN SCHEDULE_SLOTS SS ON (SS.CODE_SCHEDULE_SLOT=S.CODE_SCHEDULE_SLOT)\n"+
          "WHERE S.DAY=? AND SS.ID_SCHEDULE=?");
        jdbc.setString(1, s.getDay());
        jdbc.setInt(2, sch.getId());

        jdbc.execute();

        SlotDto slot = null;
        //start get result
        while(jdbc.next())
          {
            if(slots==null)
              {
                slots = new ArrayList<>();
              }
            slot = new SlotDto();
            slot.setCode(jdbc.getString("CODE_SCHEDULE_SLOT"));
            slots.add(slot);
            slot = null;
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return slots;
  }

  @Override
  public List<SlotDto> viewAll()
  {
    // TODO Implement this method
    return Collections.emptyList();
  }
}
