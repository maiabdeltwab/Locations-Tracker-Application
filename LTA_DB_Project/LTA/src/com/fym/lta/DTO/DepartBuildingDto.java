package com.fym.lta.DTO;

/**class to define building-department relation attributes
 * with setters, getters and constructors
 */
public class DepartBuildingDto
{

/* building id */
  private int building_id;

/* department id */ 
  private int department_id;

  /**
   * class main constructor
   */
  public DepartBuildingDto()
  {
    super();
  }

  /**
    * building id attribute setter
    * @param building_id
    */
  public void setBuilding_id(int building_id)
  {
    this.building_id = building_id;
  }

  /**
    * building id attribute getter
    * @return building id int
    */
  public int getBuilding_id()
  {
    return building_id;
  }

  /**
    * department id attribute setter
    * @param department_id
    */
  public void setDepartment_id(int department_id)
  {
    this.department_id = department_id;
  }

  /**
     *department id attribute getter
     * @return department id
     */
  public int getDepartment_id()
  {
    return department_id;
  }
}
