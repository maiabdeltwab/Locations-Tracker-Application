package com.fym.lta.DTO;

import java.util.List;

/**class to define slot attributes with setters, getters and constructors
 */
public class SlotDto {

    /* slot code */
    private String code;

    /* slot start time */
    private String start_time;

    /* slot end time */
    private String end_time;

    /* slot number */
    private int num;

    /* slot day */
    private String day;

    /* schedule of slot */
    private ScheduleSlotDto schedule;

    /* slot course */
    private CourseDto course;

    /* slot list of staff */
    private List<StaffDto> staff;

    /* slot prefered location type */
    private LocationTypeDto plt;

    /* number of students of slot */
    private int student_number;

    /* slot type lecture or section */
    private String slot_type;

    /* slot location */
    private LocationDto location;

    /* search key for slot */
    private String search;

    /* list of user accounts for staff members of slots */
    private List<UserDto> users;

    /* slot term */
    private int term;


    /**
     * term attribute setter
     * @param term int
     */
    public void setTerm(int term) {
        this.term = term;
    }

    /**
     * term attribute getter
     * @return int
     */
    public int getTerm() {
        return term;
    }

    /**
     * user accounts attribute setter
     * @param user list
     */
    public void setUser(List<UserDto> user) {
        this.users = user;
    }

    /**
     * user acounts attribute getter
     * @return list
     */
    public List<UserDto> getUser() {
        return users;
    }

    /**
     * search attribute setter
     * @param search string
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * search attribute getter
     * @return string
     */
    public String getSearch() {
        return search;
    }

    /**
     * schedule attribtute setter
     * @param schedule object
     */
    public void setSchedule(ScheduleSlotDto schedule) {
        this.schedule = schedule;
    }

    /**
     * schedule attribute getter
     * @return object
     */
    public ScheduleSlotDto getSchedule() {
        return schedule;
    }

    /**
     * slot staff attribute setter
     * @param staff list
     */
    public void setStaff(List<StaffDto> staff) {
        this.staff = staff;
    }

    /**
     * slot staff attribute getter
     * @return list
     */
    public List<StaffDto> getStaff() {
        return staff;
    }

    /**
     * prefered location type attribute setter
     * @param plt object
     */
    public void setPlt(LocationTypeDto plt) {
        this.plt = plt;
    }

    /**
     * prefered location type attribute getter
     * @return object
     */
    public LocationTypeDto getPlt() {
        return plt;
    }

    /**
     * slot number of students setter
     * @param student_number int
     */
    public void setStudent_number(int student_number) {
        this.student_number = student_number;
    }

    /**
     * slot number of students getter
     * @return int
     */
    public int getStudent_number() {
        return student_number;
    }

    /**
     * slot type attribute setter
     * @param slot_type string
     */
    public void setSlot_type(String slot_type) {
        this.slot_type = slot_type;
    }

    /**
     * slot type attribute getter
     * @return string
     */
    public String getSlot_type() {
        return slot_type;
    }

    /**
     * slot course attribute setter
     * @param course object
     */
    public void setCourse(CourseDto course) {
        this.course = course;
    }

    /**
     * slot course attribute getter
     * @return object
     */
    public CourseDto getCourse() {
        return course;
    }

    /**
     * slot location attribute setter
     * @param location object
     */
    public void setLocation(LocationDto location) {
        this.location = location;
    }

    /**
     * slot location attribute getter
     * @return object
     */
    public LocationDto getLocation() {
        return location;
    }

    /**
     * slot code attribute setter
     * @param code string
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *slot code attribute getter
     * @return string
     */
    public String getCode() {
        return code;
    }

    /**
     * end time attribute setter
     * @param end_time string
     */
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    /**
     * end time attribute getter
     * @return string
     */
    public String getEnd_time() {
        return end_time;
    }

    /**
     * start time attribute setter
     * @param start_time string
     */
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    /**
     * start time attribute getter
     * @return string
     */
    public String getStart_time() {
        return start_time;
    }

    /**
     * slot number attribute setter
     * @param num int
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     *slot number attribute getter
     * @return int
     */
    public int getNum() {
        return num;
    }

    /**
     * slot day ttribute setter
     * @param day string
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * slot day attribute getter
     * @return string
     */
    public String getDay() {
        return day;
    }

    /**
     * class main constructor
     */
    public SlotDto() {
        super();
    }
}
