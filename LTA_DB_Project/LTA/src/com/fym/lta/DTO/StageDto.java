package com.fym.lta.DTO;

/**class to define stage attributes with setters, getters and constructors
 */
public class StageDto {

    /* stage code */
    private String code;

    /* stage number of students */
    private int num_of_std;

    /* stage number */
    private String number;

    /* schedule of stage */
    private StageScheduleDto schedule;

    /* stage department */
    private DepartmentDto department;

    /* search key for stage */
    private String search;


    /**
     * class main constructor
     */
    public StageDto() {
        super();
    }

    /**
     * search attribute setter
     * @param search string
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * search attribute getter
     * @return string
     */
    public String getSearch() {
        return search;
    }

    /**
     * deaprtment attribute setter
     * @param department object
     */
    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    /**
     * department attribute getter
     * @return object
     */
    public DepartmentDto getDepartment() {
        return department;
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
     * @return string
     */
    public String getCode() {
        return code;
    }

    /**
     * number of students attribute setter
     * @param num_of_std int
     */
    public void setNum_of_std(int num_of_std) {
        this.num_of_std = num_of_std;
    }

    /**
     * number of students attribute getter
     * @return int
     */
    public int getNum_of_std() {
        return num_of_std;
    }

    /**
     * stage number attribute setter
     * @param number string
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * stage number attribute getter
     * @return string
     */
    public String getNumber() {
        return number;
    }
}
