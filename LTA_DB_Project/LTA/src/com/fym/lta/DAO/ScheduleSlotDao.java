package com.fym.lta.DAO;

import com.fym.lta.DTO.ScheduleSlotDto;

public abstract interface ScheduleSlotDao
{

  public abstract Boolean update(ScheduleSlotDto ss);

  public abstract Boolean isExist(ScheduleSlotDto ss);

  public abstract Boolean createNew(ScheduleSlotDto ss);

  public abstract Boolean delete(ScheduleSlotDto ss);

}
