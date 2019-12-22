package com.fym.lta.DTO;

/**class to define staff-course relation attributes
 * with setters, getters and constructors
 */
public class WorkOnDto {

    /* course id */
    private int course_id;

    /* number of work hours for staff on course */
    private int work_hrs;

    /* staff id */
    private int staff_id;


    /**
     * class main constructor
     */
    public WorkOnDto() {
        super();
    }

    /**
     * course id attribute setter
     * @param course_id int
     */
    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    /**
     * course id attribute getter
     * @return int
     */
    public int getCourse_id() {
        return course_id;
    }

    /**
     * work hours attribute setter
     * @param work_hrs int
     */
    public void setWork_hrs(int work_hrs) {
        this.work_hrs = work_hrs;
    }

    /**
     * work hours attribute getter
     * @return int
     */
    public int getWork_hrs() {
        return work_hrs;
    }

    /**
     * staff id attribute setter
     * @param staff_id int
     */
    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    /**
     * staff id attribute getter
     * @return int
     */
    public int getStaff_id() {
        return staff_id;
    }
}
