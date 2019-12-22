package com.fym.lta.DAO;


import com.fym.lta.DTO.RoleDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

public abstract interface RoleDao
{

  public abstract Boolean update(RoleDto r, UserDto user);

  public abstract Boolean createNew(RoleDto r, UserDto user);

  public abstract Boolean isExist(RoleDto r);

  public abstract List<RoleDto> viewAll();

  public abstract List<RoleDto> searchFor(RoleDto role);

  public abstract Boolean delete(RoleDto r);
}
