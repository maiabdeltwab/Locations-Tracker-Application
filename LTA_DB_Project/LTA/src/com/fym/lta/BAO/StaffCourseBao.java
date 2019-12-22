package com.fym.lta.BAO;

import com.fym.lta.DTO.WorkOnDto;

import java.util.List;

public interface StaffCourseBao {

    /**
     * add staff-course assignment to database
     * @param staff_course object dto
     * @return true if success - false if not
     */
    public abstract boolean add(WorkOnDto staff_course);

    /**
     * delete staff-course assignment in database
     * @param staff_course dto object
     * @return true if success - false if not
     */
    public abstract boolean delete(WorkOnDto staff_course);

    /**
     * view all staff-course assignments in database
     * @return list of dto objects
     */
    public abstract List<WorkOnDto> viewAll();

    /**
     * check if assignment exists
     * @param staff_course object dto
     * @return true if exists - false if not
     */
    public abstract boolean isExist(WorkOnDto staff_course);

}
