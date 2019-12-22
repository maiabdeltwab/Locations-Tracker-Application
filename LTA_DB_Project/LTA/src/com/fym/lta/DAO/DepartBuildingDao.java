package com.fym.lta.DAO;


import com.fym.lta.DTO.DepartBuildingDto;

import java.util.List;


public interface DepartBuildingDao
{
  public abstract Boolean delete(DepartBuildingDto bd);

  public abstract DepartBuildingDto searchFor(String c);

  public abstract List<DepartBuildingDto> viewAll();

  public abstract Boolean update(DepartBuildingDto bd);

  public abstract Boolean createNew(DepartBuildingDto bd);

  public abstract Boolean isExist(DepartBuildingDto bd);
}
