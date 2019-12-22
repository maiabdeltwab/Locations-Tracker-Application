package com.fym.lta.DAO;


public class DeCoder
{

  public String encode(String value)
  {
    if(value!=null)
      {
        value = value.trim();
        int level = 0;
        String keyStr = "12tabsa12";
        byte key[] = keyStr.getBytes();
        IceKey ik = new IceKey(level);
        ik.clear();
        ik.set(key);
        value = ik.encode(value);

      }

    return value;
  }

  public String decode(String value)
  {

    String newValue = null;
    if(value!=null)
      {
        value = value.trim();

        String keyStr = "12tabsa12";
        byte key[] = keyStr.getBytes();

        int level = 0;
        IceKey ik = new IceKey(level);
        ik.clear();
        ik.set(key);
        value = ik.decode(value);


      }

    return value;
  }

  public static void main(String[] args)
  {
    System.out.println(new DeCoder().encode("1234"));

  }
}
