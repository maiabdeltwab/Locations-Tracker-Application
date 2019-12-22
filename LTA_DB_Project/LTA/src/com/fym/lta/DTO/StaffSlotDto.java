package com.fym.lta.DTO;

/**class to define staff-slot relation attributes
 * with setters, getters and constructors
 */
public class StaffSlotDto {

    /* staff object */
    private StaffDto staff;

    /* slot object */
    private SlotDto slot;


    /**
     * class main constructor
     */
    public StaffSlotDto() {
        super();
    }

    /**
     * staff attribute setter
     * @param staff object
     */
    public void setStaff(StaffDto staff) {
        this.staff = staff;
    }

    /**
     * staff attribute getter
     * @return object
     */
    public StaffDto getStaff() {
        return staff;
    }

    /**
     * slot attribute setter
     * @param slot object
     */
    public void setSlot(SlotDto slot) {
        this.slot = slot;
    }

    /**
     * slot attribute getter
     * @return object
     */
    public SlotDto getSlot() {
        return slot;
    }

}
