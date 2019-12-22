package com.fym.lta.BAO;

import com.fym.lta.DTO.RoleDto;
import com.fym.lta.DTO.ScreenDto;
import com.fym.lta.DTO.UserDto;

public class ScreenBaoImpl implements ScreenBao
{
  private UserDto user = null;

  public ScreenBaoImpl(UserDto user)
  {
    this.user = user;
  }

  public void viewAll() {}

  public boolean delete(ScreenDto s)
  {
    return false;
  }

  public boolean update(int id)
  {
    return false;
  }

  public boolean add(ScreenDto s)
  {
    return false;
  }

  public RoleDto searchFor(int id)
  {
    return null;
  }
}
