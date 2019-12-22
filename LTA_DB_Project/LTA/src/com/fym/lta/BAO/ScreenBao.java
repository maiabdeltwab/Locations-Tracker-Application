package com.fym.lta.BAO;

import com.fym.lta.DTO.RoleDto;
import com.fym.lta.DTO.ScreenDto;

public abstract interface ScreenBao {

    /**
     * view all screen in database
     * no param
     * no return
     */
    public abstract void viewAll();

    /**
     * delete screen from database
     * @param screen dto object
     * @return true if success-false if not
     */
    public abstract boolean delete(ScreenDto screen);

    /**
     * update screen in database using its id
     * @param id int
     * @return true if done successfuly
     */
    public abstract boolean update(int id);

    /**
     * add screen dto object in database
     * @param screen dto object
     * @return true if added successfully
     */
    public abstract boolean add(ScreenDto screen);

    /**
     * serach for screen in database by id
     * @param id int
     * @return dto object
     */
    public abstract RoleDto searchFor(int id);
}
