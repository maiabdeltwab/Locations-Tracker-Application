package com.fym.lta.DAO;

import com.fym.lta.DTO.StageScheduleDto;

public interface StageScheduleDao
{
  public abstract Boolean update(StageScheduleDto ss);

  public abstract Boolean isExist(StageScheduleDto ss);

  public abstract Boolean createNew(StageScheduleDto ss);

  public abstract Boolean delete(StageScheduleDto ss);
}
