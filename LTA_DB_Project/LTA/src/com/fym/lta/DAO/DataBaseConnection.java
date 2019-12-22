package com.fym.lta.DAO;

import java.sql.SQLException;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class DataBaseConnection
{
  public DataBaseConnection()
  {
    super();
  }

  public JdbcRowSet openConnection()
  {
    JdbcRowSet connect = null;

    try
      {
        connect = RowSetProvider.newFactory().createJdbcRowSet();
        connect.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
        connect.setUsername("lta");
        connect.setPassword("lta");
      }
    catch(SQLException e)
      {
        e.printStackTrace();
      }
    return connect;
  }

  public void closeConnection(JdbcRowSet connect)

  {
    try
      {
        connect.close();
      }

    catch(SQLException e)
      {
        e.printStackTrace();
      }
  }


}
