package com.fym.lta.DAO;

import com.fym.lta.DTO.WorkOnDto;

import java.util.List;

public interface StaffCourseDao
{

  public abstract boolean insert(WorkOnDto staff_course);

  public abstract boolean delete(WorkOnDto staff_course);

  public abstract boolean isExist(WorkOnDto staff_course);

  public abstract List<WorkOnDto> viewAll();
}
