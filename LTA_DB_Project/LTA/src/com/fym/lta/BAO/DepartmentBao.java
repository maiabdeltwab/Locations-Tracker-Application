package com.fym.lta.BAO;

import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface DepartmentBao {


    /** Delete a row selected by user in GUI.
     * Takes the object and return true if it has deleted and false if any exception occur*/
    public abstract boolean delete(DepartmentDto d);


    /** Method to view all exist department
     * no parameters
     * return list of departmentdto objects */
    public abstract List<DepartmentDto> viewAll();


    /** Create a new department
     * takes department object inserted by user
     * Returm true for if it success, False if not */
    public abstract boolean update(DepartmentDto d, UserDto user);


    /** update an existed one
     * takes department object inserted by user
     * Return true for if it success, False if not */
    public abstract boolean create(DepartmentDto d, UserDto user);

    /** Search For Department with any attributes of it(id,name,code,...)
     * This method takes department object and return list of department if exist.
     */
    public abstract List<DepartmentDto> searchFor(DepartmentDto d);


    /**get buildings for a specific department
     * @param department object
     * @return building list*/
    public abstract List<BuildingDto> viewBuilding(DepartmentDto d);

    /**Get the department id
     * @param department
     * @return ID */
    public abstract int getDepartmentId(DepartmentDto d);

    /**get the max id in departments
     * @return id*/
    public abstract int calcMaxId();

    /**
     * view department of selected stage
     * @param depart_search dto object with stage attribute set to the selected
     * @return list of department dto
     */
    public List<DepartmentDto> viewStageDepartment(DepartmentDto depart_search);

}
