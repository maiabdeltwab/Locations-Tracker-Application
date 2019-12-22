package com.fym.lta.DAO;

import com.fym.lta.DTO.RoleScreenDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

public abstract interface RoleScreenDao
{
  public abstract boolean isExist(RoleScreenDto rs);

  public abstract boolean createNew(RoleScreenDto rs);

  public abstract boolean delete(RoleScreenDto rs);

  public abstract List<RoleScreenDto> searchFor(RoleScreenDto rs);

  public abstract boolean update(RoleScreenDto rs);

  public abstract List<RoleScreenDto> viewAll();

  public abstract ArrayList<String> checkPanel(UserDto user);

  public abstract boolean viewonly(UserDto user);

}
