package com.fym.lta.DAO;

import com.fym.lta.DTO.ScreenDto;
import com.fym.lta.DTO.UserDto;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;

public class ScreenDaoImpl implements ScreenDao
{
  private UserDto user = null;

  public ScreenDaoImpl(UserDto user)
  {
    this.user = user;
  }

  public Boolean delete(ScreenDto sc)
  {
    return null;
  }

  public ScreenDto searchFor(int id)
  {
    return null;
  }

  public Boolean createNew(ScreenDto sc)
  {
    return null;
  }

  public Boolean update(ScreenDto sc)
  {
    return null;
  }

  public Boolean isExist(ScreenDto sc)
  {
    return null;
  }

  public List<ScreenDto> viewAll()
  {
    List<ScreenDto> screen = new ArrayList<>();
    try
      {

        JdbcRowSet jdbc = user.getConnect();


        jdbc.setCommand("select id_screen, name_screen, DESCRIPTION_SCREEN "+
          "from screen order by id_screen ");
        jdbc.execute();
        ScreenDto s = null;
        while(jdbc.next())
          {
            s = new ScreenDto();
            s.setId(jdbc.getInt("ID_screen"));
            s.setName(jdbc.getString("name_screen"));
            s.setDescripltion(jdbc.getString("DESCRIPTION_SCREEN"));
            screen.add(s);
            s = null;
          }


      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    return screen;
  }
}
