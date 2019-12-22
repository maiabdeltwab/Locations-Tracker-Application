package com.fym.lta.BAO;

import com.fym.lta.DTO.UserDto;

/**
 * class to create implemented class from each interface by methods
 * return object of implemented class
 */
public class BaoFactory {

    /**
     * method to create object from BuildingBao implement
     * @param user object
     * @return buildingbaoimpl object
     */
    public BuildingBao createBuildingBao(UserDto user) {
        return new BuildingBaoImpl(user);
    }

    /**
     * method to create object from StageScheduleBao implement
     * @param user object
     * @return stageschedulebaoimpl object
     */
    public StageScheduleBao createStageScheduleBao(UserDto user) {
        return new StageScheduleBaoImpl(user);
    }

    /**
     * method to create object from ScheduleSlotBao implement
     * @param user object
     * @return ScheduleSlotBaoimpl object
     */
    public ScheduleSlotBao createScheduleSlotBao(UserDto user) {
        return new ScheduleSlotBaoImpl(user);
    }

    /**
     * method to create object from FloorBao implement
     * @param user object
     * @return floorbaoimpl object
     */
    public FloorBao createFloorBao(UserDto user) {
        return new FloorBaoImpl(user);
    }

    /**
     * method to create object from RoleScreenBao implement
     * @param user object
     * @return rolescreenbaoimpl object
     */
    public RoleScreenBao createRoleScreenBao(UserDto user) {
        return new RoleScreenBaoImpl(user);
    }

    /**
     * method to create object from locationBao implement
     * @param user object
     * @return locationbaoimpl object
     */
    public LocationBao createLocationBao(UserDto user) {
        return new LocationBaoImpl(user);
    }

    /**
     * method to create object from loactionTypeBao implement
     * @param user object
     * @return loactiontypebaoimpl object
     */
    public LocationTypeBao createLocationTypeBao(UserDto user) {
        return new LocationTypeBaoImpl(user);
    }

    /**
     * method to create object from equipmentBao implement
     * @param user object
     * @return equipmentbaoimpl object
     */
    public EquipmentBao createEquipmnetBao(UserDto user) {
        return new EquipmentBaoImpl(user);
    }

    /**
     * method to create object from equipmenttypeBao implement
     * @param user object
     * @return equipmenttypebaoimpl object
     */
    public EquipmentTypeBao createEquipmentTypeBao(UserDto user) {
        return new EquipmentTypeBaoImpl(user);
    }

    /**
     * method to create object from equipmentspecificationBao implement
     * @param user object
     * @return euipmentSpecificationbaoimpl object
     */
    public EquipSpecificationBao createEquipmentSpecificationBao(UserDto user) {
        return new EquipSpecificationBaoImpl(user);
    }

    /**
     * method to create object from departmentBao implement
     * @param user object
     * @return departmentbaoimpl object
     */
    public DepartmentBao createDepartmentBao(UserDto user) {
        return new DepartmentBaoImpl(user);

    }

    /**
     * method to create object from courseBao implement
     * @param user object
     * @return coursebaoimpl object
     */
    public CourseBao createCourseBao(UserDto user) {
        return new CourseBaoImpl(user);
    }

    /**
     * method to create object from scheduleBao implement
     * @param user object
     * @return schedulebaoimpl object
     */
    public ScheduleBao createScheduleBao(UserDto user) {
        return new ScheduleBaoImpl(user);
    }

    /**
     * method to create object from stageBao implement
     * @param user object
     * @return stagebaoimpl object
     */
    public StageBao createStageBao(UserDto user) {
        return new StageBaoImpl(user);
    }

    /**
     * method to create object from staffBao implement
     * @param user object
     * @return staffbaoimpl object
     */
    public StaffBao createStaffBao(UserDto user) {
        return new StaffBaoImpl(user);
    }

    /**
     * method to create object from userBao implement
     * @param user object
     * @return userbaoimpl object
     */
    public UserBao createUserBao(UserDto user) {
        return new UserBaoImpl(user);
    }

    /**
     * method to create object from roleBao implement
     * @param user object
     * @return rolebaoimpl object
     */
    public RoleBao createRoleBao(UserDto user) {
        return new RoleBaoImpl(user);
    }

    /**
     * method to create object from screenBao implement
     * @param user object
     * @return screenbaoimpl object
     */
    public ScreenBao createScreenBao(UserDto user) {
        return new ScreenBaoImpl(user);
    }

    /**
     * method to create object from facultyBao implement
     * @param user object
     * @return facultybaoimpl object
     */
    public FacultyBao createFacultyBao(UserDto user) {
        return new FacultyBaoImpl(user);
    }

    /**
     * method to create object from employeeBao implement
     * @param user object
     * @return employeebaoimpl object
     */
    public EmployeeBao createEmployeeBao(UserDto user) {
        return new EmployeeBaoImpl(user);
    }

    /**
     * method to create object from equipmenttyoeBao implement
     * @param user object
     * @return equipmenttypebaoimpl object
     */
    public EqTypeSpecBao createEqTypeSpecBao(UserDto user) {
        return new EqTypeSpecBaoImpl(user);
    }

    /**
     * method to create object from staffuserBao implement
     * @param user object
     * @return staffuserbaoimpl object
     */
    public StaffUserBao createStaffUserBao(UserDto user) {
        return new StaffUserBaoImpl(user);
    }

    /**
     * method to create object from employeeuserBao implement
     * @param user object
     * @return employeeuserbaoimpl object
     */
    public EmpUserBao createEmpUserBao(UserDto user) {
        return new EmpUserBaoImpl(user);
    }

    /**
     * method to create object from staffcourseBao implement
     * @param user object
     * @return staffcoursebaoimpl object
     */
    public StaffCourseBao createStaffCourseBao(UserDto user) {
        return new StaffCourseBaoImpl(user);
    }

    /**
     * method to create object from slotBao implement
     * @param user object
     * @return slotbaoimpl object
     */
    public SlotBao createSlotBao(UserDto user) {
        return new SlotBaoImpl(user);
    }
}
