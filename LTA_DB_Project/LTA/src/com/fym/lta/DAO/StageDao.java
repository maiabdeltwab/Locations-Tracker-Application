package com.fym.lta.DAO;

import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface StageDao
{
  public abstract Boolean createNew(StageDto st, UserDto user);

  public abstract Boolean isExist(StageDto st);

  public abstract List<StageDto> searchFor(StageDto st);

  public abstract Boolean delete(StageDto st);

  public abstract List<StageDto> viewAll();

  public abstract Boolean update(StageDto st, UserDto user);

}
