package com.fym.lta.BAO;

import com.fym.lta.DAO.CourseDao;
import com.fym.lta.DAO.CourseDaoImpl;
import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DTO.CourseDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * class to implment course interface methods
 */
public class CourseBaoImpl implements CourseBao {
    /* identify user dto object and initialize to null */
    private UserDto user = null;

    /* identify coursedao object and initialize to null */
    private CourseDao dao = null;

    UserBaoImpl endUserBaoImpl;


    public CourseBaoImpl(UserDto user) {
        /* set user dto object to start connect with database */
        this.user = user;

        /*initialize dao layer object and pass user as parameter */
        dao = new DaoFactory().createCourseDao(user); //create dao course object
    }


    /** Search For course with any attributes of it(id,name,code,...)
     * This method takes course object and return list of courses if exist.
     */
    public List<CourseDto> searchFor(CourseDto course) {

        /* identify list of course dto */
        List<CourseDto> courses = new ArrayList<CourseDto>();

        try {
            /* call search method of dao layer and set to courses list */
            courses = dao.searchFor(course);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* return list */
        return courses;
    }

    /** Search For course with id
     * This method takes course object and return list of courses if exist.
     */
    public CourseDto getCourse(CourseDto course) {

        /* identify course dto object and initialize to null */
        CourseDto courseDto = null;

        try {
            /* call dao layer metho to get course and set to object */
            courseDto = dao.getCourse(course);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* return course object */
        return courseDto;
    }


    /** Create a new course
     * takes course object inserted by user
     * Returm true for if it success, False if not */
    public boolean add(CourseDto course, UserDto user) {

        /* identify save flag and initialize to false */
        boolean saveFlage = false;
        try {
            /* check if course exists in database */
            if (!dao.isExist(course))
                /* call save method from dao layer-set cousre dto object as parameter
             * set to save flag*/
                saveFlage = dao.createNew(course, user);

            /* return flag */
            return saveFlage;

        } catch (Exception e) {
            /* print error and return false */
            e.printStackTrace();
            return false;
        }
    }


    /** update an existed one
     * @param takes course object inserted by user
     * @Return true for if it success, False if not */
    public boolean update(CourseDto course, UserDto user) {

        /* identify save flag and initialize to false */
        boolean saveFlage = false;
        try {
            /* check if flag exists in database
       * if true then call update method of dao layer
       * pass course object ass parameter
       * pass user object as parameter */
            if (dao.isExist(course))
                saveFlage = dao.update(course, user);

        } catch (Exception e) {
            /* return false if error */
            e.printStackTrace();
            return false;
        }
        /* return save flag */
        return saveFlage;
    }


    /** Method to view all exist department
     * @param no parameters
     * @return list of coursedto objects */
    public List<CourseDto> listAll() {
        
        /* identify list of courses dto */
        List<CourseDto> courses = new ArrayList<CourseDto>();

        try {
            /* call view all method from dao layer and set return to courses list */
            courses = dao.viewAll();
        }

        catch (Exception e) {
            e.printStackTrace();

        }
        /* return courses list */
        return courses;
    }


    /** Delete a row selected by user in GUI.
     * Takes the object and return true if it has deleted and false if any exception occur*/
    public boolean delete(CourseDto course) {
        /* identify delete flag and initialize to false */
        boolean deleteFlag = false;
        try {
            /* check if course object exists in database
         * if true then call delete method of dao layer
         * pass course object as parameter */
            if (dao.isExist(course))
                deleteFlag = dao.delete(course);

            /* return delete flag */
            return deleteFlag;
        } catch (Exception e) {
            /* return false if error */
            e.printStackTrace();
            return false;
        }
    }

    /** calculate max id of courses in database
     * reurn the max id */
    public int calcMaxId() {

        /* identify id int and initialize to zero */
        int id = 0;
        try {
            /* call dao layer method to get the maxId and set to id variable identified*/
            id = dao.getMaxId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* return id */
        return id;
    }

    /**
     * check if selected course exists in database
     * @param course dto object selected
     * @return true if exist - false if not exist
     */
    public boolean isExist(CourseDto course) {
        /* identify flag and initialize to false */
        boolean existFlag = false;
        try {
            /* call isExist method from dao layer to chech existance
       * pass course dto object as param */
            existFlag = dao.isExist(course);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* return */
        return existFlag;
    }

    /**
     * get id from databes of selected course
     * @param course dto object
     * @return id int
     */
    public int getId(CourseDto course) {
        /* identify id integer and initialize to zero */
        int id = 0;
        try {
            /* call dao layer method to get id
      * set the reurn to id variable identified */
            id = dao.getId(course);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* return id */
        return id;
    }

    /**
     * check if selected course object is assigned to department object selected
     * @param course
     * @param department
     * @return true if assigned - flase if not assigned
     */
    public boolean isAssigned(CourseDto course, DepartmentDto department) {
        /* identify flag and initialize to false */
        boolean flag = false;
        try {
            /* call dao layer method to check assignment
     * set return of method to flag */
            flag = dao.isAssigned(course, department);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* return flag */
        return flag;
    }

    /**
     * assign selected course dto object to selected department dto object
     * @param course dto object
     * @param department dto object
     * @return true if assigned successfully - flase if failed
     */
    public boolean assignDepCourse(CourseDto course, DepartmentDto department) {
        /* identify flag and initialize to false */
        boolean flag = false;
        try {
            /* call dao layer method to assign course to department
      * set method frturn to flag */
            flag = dao.assignDepCourse(course, department);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* return flag */
        return flag;
    }


    /**
     * view list of departments assigned to selected course dto object
     * @param course dto object
     * @return list of departments dto
     */
    public List<DepartmentDto> viewDepsOfCourse(CourseDto course) {

        /* identify list of departments dto */
        List<DepartmentDto> departs = null;
        try {
            /* call dao layer to view departments
      * set method return to departments list */
            departs = dao.viewDepsOfCourse(course);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* return list */
        return departs;
    }


}
