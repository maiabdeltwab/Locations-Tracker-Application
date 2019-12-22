package com.fym.lta.DTO;

import java.util.List;

/**class to define course attributes with setters, getters and constructors
 */
public class CourseDto {

    /* course description */
    private String description;
    /* course name */
    private String name;
    /* course code */
    private String code;
    /* course id */
    private int id;
    /* number of hours for lecture */
    private Float lec_time;
    /* number of hours for section*/
    private Float sec_time;
    /* prefered location type for course lecture and section */
    private LocationTypeDto plt_lecture, plt_section;
    /* list of departments of course */
    private List<DepartmentDto> departs;
    /* search key for course */
    private String search;
    /* course term */
    private String term;
   
    /**
     * id attribute constructor
     * @param id
     */
    public CourseDto(int id) {
        this.id = id;
    }

    /**
     * list of departments setter
     * @param departs
     */
    public void setDeparts(List<DepartmentDto> departs) {
        this.departs = departs;
    }

    /**
     * list of departments getter
     * @return list of deparmentDto
     */
    public List<DepartmentDto> getDeparts() {
        return departs;
    }

    /**
     * prefered lecture locayion type attribute setter
     * @param plt_lecture
     */
    public void setPlt_lecture(LocationTypeDto plt_lecture) {
        this.plt_lecture = plt_lecture;
    }

    /**
     * prefered lecture location type getter
     * @return plt lecture
     */
    public LocationTypeDto getPlt_lecture() {
        return plt_lecture;
    }

    /**
     * prefered section location type attribute setter
     * @param plt_section
     */
    public void setPlt_section(LocationTypeDto plt_section) {
        this.plt_section = plt_section;
    }

    /**
     * prefered section location type attribute getter
     * @return plt section
     */
    public LocationTypeDto getPlt_section() {
        return plt_section;
    }

    /**
     * search attribute setter
     * @param search
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * search attribute getter
     * @return search string
     */
    public String getSearch() {
        return search;
    }

    /**
     * class main consrtuctor
     */
    public CourseDto() {
        super();
    }

    /**
     * description attribute setter
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * description attriute getter
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * name attribute setter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * name attribute getter
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * id attribute setter
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * id attribute getter
     * @return id int
     */
    public int getId() {
        return id;
    }

    /**
     * code attribute setter
     * @param code string
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * code attribute getter
     * @return code string
     */
    public String getCode() {
        return code;
    }

    /**
     * number of hours for lecture attribute setter
     * @param no_of_hrs float
     */
    public void setLec_time(Float no_of_hrs) {
        this.lec_time = no_of_hrs;
    }

    /**
   * number of hours for lecture attribut getter
   * @return float number of hours
   */
    public Float getLec_time() {
        return lec_time;
    }

  
  /**
   * number of hours for section attribute setter
   * @param no_of_hrs float
   */
  public void setSec_time(Float sec_time)
  {
    this.sec_time = sec_time;
  }

  /**
   * number of hours for section attribut getter
   * @return float number of hours
   */
  public Float getSec_time()
  {
    return sec_time;
  }

  /**
   * term attribute setter
   * @param term as string 
   */
  public void setTerm(String term)
  {
    this.term = term;
  }

  /**
   * term attribut getter
   * @return term as string 
   */
  public String getTerm()
  {
    return term;
  }


  /**
   * search attribute constructor
   * @param search
   */
  public CourseDto(String search)
  {
    this.search = search;
  }

}
