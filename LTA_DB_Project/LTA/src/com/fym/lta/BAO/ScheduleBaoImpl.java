package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.LocationDao;
import com.fym.lta.DAO.ScheduleDao;
import com.fym.lta.DAO.SlotDao;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

public class ScheduleBaoImpl implements ScheduleBao {
    private UserDto user = null;

    private ScheduleDao dao = null;

    public ScheduleBaoImpl(UserDto user) {
        this.user = user;
        dao = new DaoFactory().createScheduleDao(user);
    }

    /**Update an schedule
     * @param take schedule
     * return true if sucess, false if not*/
    public boolean update(ScheduleDto s, UserDto user) {
        boolean saveFlage = true;
        try {

            if (dao.isExist(s)) {
                saveFlage = dao.update(s, user);
            } else {
                saveFlage = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**insert a new schedule
     * @param take schedule
     * return true if sucess, false if not*/
    public boolean add(ScheduleDto s, UserDto user) {
        boolean saveFlage = true;
        try {
            //data is valid
            if (!dao.isExist(s)) {
                saveFlage = dao.createNew(s, user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**delete schedule from database
     * @param schedule
     * @return true if it deleted successfuly, false if no
     */
    public Boolean delete(StageDto stage, DepartmentDto department) {
        boolean saveFlage = true;
        try {
            saveFlage = dao.delete(stage, department);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /**search for an existing schedule
     * @param take schedule
     * return schedule list if found*/
    public List<ScheduleDto> searchFor(ScheduleDto s) {
        List<ScheduleDto> sch = null;
        try {
            //  sch  = dao.searchFor(s);
            return sch;
        } catch (Exception e) {
            e.printStackTrace();
            return sch;
        }
    }

    /**Calculate the max id for all sechedules in database
     * @return integer value of this id **/
    public int calcMaxId() {
        int id = 0;
        try {
            id = dao.getMaxId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**Location Automatic Assignment for schedule
     *@param take schedule,user
     *@return slots which the auto assignment faild in it  */
    public List<SlotDto> autoAssignment(ScheduleDto schedule, UserDto user) {

        //create slot dto list to add slots which the auto assignment faild in it
        List<SlotDto> return_slots = null;
        List<SlotDto> slots = null; //for schedule slots

        try {
            //create slot dao object
            SlotDao slot_dao = new DaoFactory().createSlotDao(user);

            //create location dao object
            LocationDao location_dao = new DaoFactory().createLocationDao(user);

            LocationTypeDto PLT = null; //create location type object for PLT

            //get the schedule id use its department and stage
            // schedule = dao.getID(schedule);

            //set a default location for schedule using location dao object
            LocationDto default_location = location_dao.defaultLocation(schedule);
            System.out.println("Default location:  " + default_location.getId()); //check

            //get all slots assigned to this location using slot dao object
            slots = slot_dao.ScheduleSlot(schedule);


            //start assignment for each slot in this schedule
            for (int i = 0; i < slots.size(); i++) {

                /**get prefered location type of slot i**/

                //check if this slot type is lecture
                if (slots.get(i)
                         .getSlot_type()
                         .equalsIgnoreCase("LEC") || slots.get(i)
                                                          .getSlot_type()
                                                          .equalsIgnoreCase("LECTURE") || slots.get(i)
                                                                                               .getSlot_type()
                                                                                               .equalsIgnoreCase("L") || slots.get(i)
                                                                                                                              .getSlot_type()
                                                                                                                              .equalsIgnoreCase("LECT")) { // if yes get lecture PLT of course of this slot
                    PLT = new LocationTypeDto(slot_dao.lec_loc_type(slots.get(i)).getId());
                } else //it should be section
                    PLT = new LocationTypeDto(slot_dao.sec_loc_type(slots.get(i)).getId());


                slots.get(i).setPlt(PLT); //set plt to slot plt

                System.out.println("PLT Slot:  " + slots.get(i)
                                                        .getPlt()
                                                        .getId() + "  " + slots.get(i).getCode()); //check

                try {
                    /** Location assignment **/

                    /** case1 **/
                    //mutual assignment

                    //give mutual slots whith this slot
                    List<SlotDto> mutual_slots = slot_dao.mutual_slots(slots.get(i));
                    int n = 0; /*for mutual slots loop
                * and parameter to check if mutual assignment is done or not*/

                    /* assign location slot to the same location assign to its mutuals
      * if there is no assigned location to it (first schedule assign)
      * if not it must be "update assignment", so new location will assign to this slot
      * and its mutual slots */
                    if (slots.get(i).getLocation() == null) {
                        //check if this slot assigned to another schedule
                        if (mutual_slots != null && !mutual_slots.isEmpty()) {
                            for (n = 0; n < mutual_slots.size(); n++) {
                                if (mutual_slots.get(n).getLocation() != null) {
                                    slots.get(i).setLocation(mutual_slots.get(n).getLocation());
                                    System.out.println(slots.get(i).getCode() + ": case1"); //check
                                    n = -1;
                                    break;
                                }
                            }
                        }
                    }

                    if (n != -1) {
                        /** case2 **/
                        /*try assign the default location to this slot
        if this location capacity and type are suitable
        And if the default location is free in this slot*/


                        if (default_location != null &&
                            (slots.get(i)
                                                              .getPlt()
                                                              .getId() == default_location.getType().getId() ||
                             slots.get(i).getPlt() == null) &&
slots.get(i).getStudent_number() <= default_location.getCapacity() &&
location_dao.freeSlot(default_location, slots.get(i))) {

                            slots.get(i).setLocation(default_location);
                            System.out.println(slots.get(i).getCode() + ": case2"); //check

                        }

                        else {

                            /*find locations have the same type of PLT of the slot
         *has suitable capacity and found in building/s of stage department*/
                            List<LocationDto> match_type_building_locations =
                                location_dao.match_building_and_type(schedule, slots.get(i));

                            /** case3 **/
                            /*try assign slot to location which has the same type of its PLT
          and in department building/s */
                            if (match_type_building_locations != null && !match_type_building_locations.isEmpty()) {

                                //set the first location in list to this slot
                                slots.get(i).setLocation(match_type_building_locations.get(0));

                                for (int j = 1; j < match_type_building_locations.size(); j++) {
                                    //get floor IDs for the assigned location and for it in the list
                                    String[] code1 = slots.get(i)
                                                          .getLocation()
                                                          .getFloor()
                                                          .getCode()
                                                          .split("-");
                                    String[] code2 = match_type_building_locations.get(j)
                                                                                  .getFloor()
                                                                                  .getCode()
                                                                                  .split("-");

                                    /*if there is location in list
             * which located on floor below than the assigned location*/
                                    if (Integer.parseInt(code2[1]) < Integer.parseInt(code1[1])) {
                                        slots.get(i).setLocation(match_type_building_locations.get(j));
                                        System.out.println(slots.get(i).getCode() + ": case3"); //check
                                    }

                                }
                            }

                            else {

                                /*find locations have the same type of PLT of the slot
             *has suitable capacity and found in building/s of stage department*/
                                List<LocationDto> match_type_locations = location_dao.match_type(slots.get(i));


                                /** case4 **/
                                /*try assign slot to location which has the same type of its PLT
              ignore which building is located in */
                                if (match_type_locations != null && !match_type_locations.isEmpty()) {

                                    //set the first location in list to this slot
                                    slots.get(i).setLocation(match_type_locations.get(0));

                                    for (int j = 1; j < match_type_locations.size(); j++) {
                                        //get floor IDs for the assigned location and for it in the list
                                        String[] code1 = slots.get(i)
                                                              .getLocation()
                                                              .getFloor()
                                                              .getCode()
                                                              .split("-");
                                        String[] code2 = match_type_locations.get(j)
                                                                             .getFloor()
                                                                             .getCode()
                                                                             .split("-");

                                        /*if there is location in list
                 * which located on floor below than the assigned location*/
                                        if (Integer.parseInt(code2[1]) < Integer.parseInt(code1[1])) {
                                            slots.get(i).setLocation(match_type_locations.get(j));
                                            System.out.println(slots.get(i).getCode() + ": case4"); //check
                                        }
                                    }
                                }

                                else {

                                    /*find locations have suitable capacity and
               * found in building/s of stage department*/
                                    List<LocationDto> match_building_locations =
                                        location_dao.match_building(schedule, slots.get(i));


                                    /** case5 **/
                                    /*try assign slot to any free location which
                located in stage's department building/s */
                                    if (match_building_locations != null && !match_building_locations.isEmpty()) {
                                        //set the first location in list to this slot
                                        slots.get(i).setLocation(match_building_locations.get(0));

                                        //assign the below floor location
                                        if (slots.get(i).getLocation() != null) {
                                            for (int j = 1; j < match_building_locations.size(); j++) {
                                                //get floor IDs for the assigned location and for it in the list
                                                String[] code1 = slots.get(i)
                                                                      .getLocation()
                                                                      .getFloor()
                                                                      .getCode()
                                                                      .split("-");
                                                String[] code2 = match_building_locations.get(j)
                                                                                         .getFloor()
                                                                                         .getCode()
                                                                                         .split("-");

                                                /*if there is location in list
                   * which located on floor below than the assigned location*/
                                                if (Integer.parseInt(code2[1]) < Integer.parseInt(code1[1])) {
                                                    slots.get(i).setLocation(match_building_locations.get(j));
                                                    System.out.println(slots.get(i).getCode() + ": case5"); //check
                                                }
                                            }
                                        }
                                    }

                                    else {
                                        /*find locations have suitable capacity and
                 *ignore which building is located in*/
                                        List<LocationDto> free_locations = location_dao.freelocations(slots.get(i));


                                        /** case6 **/
                                        /*try assign slot to any free location which
                  located in stage's department building/s */
                                        if (free_locations != null && !free_locations.isEmpty()) {

                                            //set the first location in list to this slot
                                            slots.get(i).setLocation(free_locations.get(0));

                                            for (int j = 1; j < free_locations.size(); j++) {
                                                //get floor IDs for the assigned location and for it in the list
                                                String[] code1 = slots.get(i)
                                                                      .getLocation()
                                                                      .getFloor()
                                                                      .getCode()
                                                                      .split("-");
                                                String[] code2 = free_locations.get(j)
                                                                               .getFloor()
                                                                               .getCode()
                                                                               .split("-");

                                                /*if there is location in list
                     * which located on floor below than the assigned location*/
                                                if (Integer.parseInt(code2[1]) < Integer.parseInt(code1[1])) {
                                                    slots.get(i).setLocation(free_locations.get(j));
                                                    System.out.println(slots.get(i).getCode() + ": case6"); //check
                                                }
                                            }
                                        }

                                        else //the assigned faild
                                        {
                                            if (return_slots == null)
                                                return_slots = new ArrayList<SlotDto>();

                                            return_slots.add(slots.get(i)); //add it to the returned list

                                        }
                                    }
                                }
                            }
                        }
                    }
                    System.out.println("Assigned Location: " + slots.get(i)
                                                                    .getLocation()
                                                                    .getId());
                    slot_dao.update(slots.get(i), user); //after finish assignment update the slot

                    //if it has mutual slots update them
                    if (mutual_slots != null && !mutual_slots.isEmpty()) {
                        for (int l = 0; l < mutual_slots.size(); l++) {
                            //first, assign the same location to it
                            mutual_slots.get(l).setLocation(slots.get(i).getLocation());
                            //second, update it
                            slot_dao.update(mutual_slots.get(l), user);
                        }
                    }

                } catch (Exception e) {
                    if (return_slots == null)
                        return_slots = new ArrayList<SlotDto>();

                    return_slots.add(slots.get(i));
                    e.printStackTrace();
                }

            }

            return return_slots;
        } catch (Exception e) {
            e.printStackTrace();
            return slots;
        }

    }


    /** get the schedule id
     * @param schedule path
     * @return  id */
    public int getScheduleID(String path) {
        int id = 0;
        try {
            id = dao.getScheduleID(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**check if schedule exist or not*/
    public boolean isExist(ScheduleDto schedule) {
        boolean existFlag = false;
        try {
            existFlag = dao.isExist(schedule);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existFlag;
    }

    /** get the schedule id
     * @param schedule
     * @return the same schedule after assign its id */
    public ScheduleDto getID(ScheduleDto schedule) {

        try {
            schedule = dao.getID(schedule);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedule;
    }

}
