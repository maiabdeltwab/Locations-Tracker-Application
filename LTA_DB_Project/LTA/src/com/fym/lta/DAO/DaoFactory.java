package com.fym.lta.DAO;

import com.fym.lta.DTO.UserDto;

public class DaoFactory
{
  public EmployeeDao createEmployeeDao(UserDto user)
  {
    return new EmployeeDaoImpl(user);
  }

  public UserDao createUserDao(UserDto user)
  {
    return new UserDaoImpl(user);
  }

  public StageScheduleDao createStageScheduleDao(UserDto user)
  {
    return new StageScheduleDaoImpl(user);
  }

  public ScheduleSlotDao createScheduleSlotDao(UserDto user)
  {
    return new ScheduleSlotDaoImpl(user);

  }


  public LocationDao createLocationDao(UserDto user)
  {
    return new LocationDaoImpl(user);
  }

  public RoleScreenDao createRoleScreenDao(UserDto user)
  {
    return new RoleScreenDaoImpl(user);
  }

  public LocationTypeDao createLocationTypeDao(UserDto user)
  {
    return new LocationTypeDaoImpl(user);
  }

  public StaffDao createStaffDao(UserDto user)
  {
    return new StaffDaoImpl(user);
  }

  public RoleDao createRoleDao(UserDto user)
  {
    return new RoleDaoImpl(user);
  }

  public ScreenDao createScreenDao(UserDto user)
  {
    return new ScreenDaoImpl(user);
  }

  public EquipmentDao createEquipmentDao(UserDto user)
  {
    return new EquipmentDaoImpl(user);
  }

  public EquipmentTypeDao createEquipmentTypeDao(UserDto user)
  {
    return new EquipmentTypeDaoImpl(user);
  }

  public BuildingDao createBuildingDao(UserDto user)
  {
    return new BuildingDaoImpl(user);
  }

  public FloorDao createFloorDao(UserDto user)
  {
    return new FloorDaoImpl(user);
  }

  public DepartmentDao createDepartmentDao(UserDto user)
  {
    return new DepartmentDaoImpl(user);
  }

  public FacultyDao createFacultyDao(UserDto user)
  {
    return new FacultyDaoImpl(user);
  }

  public StageDao createStageDao(UserDto user)
  {
    return new StageDaoImpl(user);
  }

  public SlotDao createSlotDao(UserDto user)
  {
    return new SlotDaoImpl(user);
  }

  public ScheduleDao createScheduleDao(UserDto user)
  {
    return new ScheduleDaoImpl(user);
  }

  public EquipmentSpecificationDao createEquipmentSpecificationDao(UserDto user)
  {
    return new EquipmentSpecificationDaoImpl(user);
  }

  public EqTypeSpecDao createEqTypeSpecDao(UserDto user)
  {
    return new EqTypeSpecDaoImpl(user);
  }

  public CourseDao createCourseDao(UserDto user)
  {
    return new CourseDaoImpl(user);
  }

  public StaffUSerDao createStaffUserDao(UserDto user)
  {
    return new StaffUserDaoImpl(user);
  }

  public EmpUserDao createEmpUserDao(UserDto user)
  {
    return new EmpUserDaoImpl(user);
  }

  public StaffCourseDao createStaffCourseDao(UserDto user)
  {
    return new StaffCourseDaoImpl(user);
  }
}
