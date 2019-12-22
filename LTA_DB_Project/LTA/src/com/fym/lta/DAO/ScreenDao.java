package com.fym.lta.DAO;

import com.fym.lta.DTO.ScreenDto;

import java.util.List;

public abstract interface ScreenDao
{
  public abstract Boolean delete(ScreenDto sc);

  public abstract ScreenDto searchFor(int id);

  public abstract Boolean createNew(ScreenDto sc);

  public abstract Boolean update(ScreenDto sc);

  public abstract Boolean isExist(ScreenDto sc);

  public abstract List<ScreenDto> viewAll();
}
