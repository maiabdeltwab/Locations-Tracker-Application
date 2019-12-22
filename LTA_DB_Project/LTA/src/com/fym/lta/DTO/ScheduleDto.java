package com.fym.lta.DTO;


/**class to define schedule attributes with setters, getters and constructors
 */
public class ScheduleDto {

    /* schedule id */
    private int id;

    /* number of slots in schedule */
    private int slot_num;

    /* slots of schedule */
    private ScheduleSlotDto slots;

    /* stage of schedule */
    private StageDto stage;

    /* schedule term */
    private int term;

    /* search key of schedule */
    private String search;

    /* schedule path on device */
    private String file_path;

    /* schedule department */
    private DepartmentDto department;

    /* default location type of schedule */
    private LocationTypeDto default_location_type;


    /**
     * file path attribute setter
     * @param file_path string
     */
    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    /**
     * file path attribute getter
     * @return string
     */
    public String getFile_path() {
        return file_path;
    }

    /**
     * default location type attribute setter
     * @param default_location_type object
     */
    public void setDefault_location_type(LocationTypeDto default_location_type) {
        this.default_location_type = default_location_type;
    }

    /**
     * default location type attribute getter
     * @return object
     */
    public LocationTypeDto getDefault_location_type() {
        return default_location_type;
    }

    /**
     * slots attribute setter
     * @param slots object
     */
    public void setSlots(ScheduleSlotDto slots) {
        this.slots = slots;
    }

    /**
     * slots attribute getter
     * @return object
     */
    public ScheduleSlotDto getSlots() {
        return slots;
    }

    /**
     * stage attribute setter
     * @param stage object
     */
    public void setStage(StageDto stage) {
        this.stage = stage;
    }

    /**
     * stage attribute getter
     * @return object
     */
    public StageDto getStage() {
        return stage;
    }

    /**
     * department attribute setter
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
     * term attribute setter
     * @param term int
     */
    public void setTerm(int term) {
        this.term = term;
    }

    /**
     *term attribute getter
     * @return int
     */
    public int getTerm() {
        return term;
    }

    /**
     * id attribute setter
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * id attribute getter
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     *number of slots attribute setter
     * @param slot_num int
     */
    public void setSlot_num(int slot_num) {
        this.slot_num = slot_num;
    }

    /**
     * number of slots attribute getter
     * @return
     */
    public int getSlot_num() {
        return slot_num;
    }

    /**
     * class main constructor
     */
    public ScheduleDto() {
        super();
    }
}
