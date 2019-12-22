package com.fym.lta.DTO;

/**class to define course-department relation attributes
 * with setters, getters and constructors
 */
public class CourseDepartDto {

    /* course id */
    private int course_id;

    /* department id */
    private int department_id;

    /* number of courses for each department */
    private int no_of_courses;

    /**
     * number of courses attribute setter
     * @param no_of_courses
     */
    public void setNo_of_courses(int no_of_courses) {
        this.no_of_courses = no_of_courses;
    }

    /**
     * number of courses attribute getter
     * @return number of courses
     */
    public int getNo_of_courses() {
        return no_of_courses;
    }

    /**
     * department id attribute setter
     * @param department_id
     */
    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    /**
     * department id attribute getter
     * @return
     */
    public int getDepartment_id() {
        return department_id;
    }

    /**
     * course id attribute getter
     * @return course id
     */
    public int getCourse_id() {
        return course_id;
    }

    /**
     * course id attribute setter
     * @param course_id
     */
    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    /**
     * class main constructor
     */
    public CourseDepartDto() {
        super();
    }
}
