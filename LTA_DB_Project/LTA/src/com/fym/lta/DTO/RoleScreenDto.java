package com.fym.lta.DTO;

/**class to define role-screen relation attributes 
 * with setters, getters and constructors
 */
public class RoleScreenDto {

    /* view only permission */
    private boolean view_only;

    /* full access permission */
    private boolean full_access;

    /* deny permission */
    private boolean deny_access;

    /* role id */
    private int role_id;

    /* screen id */
    private int screen_id;

    /* role name */
    private String role_name;

    /* screen name */
    private String screen_name;

    /* search key */
    private String search;


    /**
     * class main constructor
     */
    public RoleScreenDto() {
        super();
    }

    /**
     * role name attribute setter
     * @param role_name string
     */
    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    /**
     * role name attribute getter
     * @return string
     */
    public String getRole_name() {
        return role_name;
    }

    /**
     * screen name attribute setter
     * @param screen_name string
     */
    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    /**
     * screen name attribute getter
     * @return string
     */
    public String getScreen_name() {
        return screen_name;
    }

    /**
     * role id attribute setter
     * @param role_id int
     */
    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    /**
     * role id attribute getter
     * @return int
     */
    public int getRole_id() {
        return role_id;
    }

    /**
     * screen id attribute setter
     * @param screen_id int
     */
    public void setScreen_id(int screen_id) {
        this.screen_id = screen_id;
    }

    /**
     * view only permission attribute setter
     * @param view_only boolean
     */
    public void setView_only(boolean view_only) {
        this.view_only = view_only;
    }

    /**
     * check view only permission state
     * @return boolean
     */
    public boolean isView_only() {
        return view_only;
    }

    /**
     * full access permission setter
     * @param full_access boolean
     */
    public void setFull_access(boolean full_access) {
        this.full_access = full_access;
    }

    /**
     * full access permission attribute getter
     * @return boolean
     */
    public boolean isFull_access() {
        return full_access;
    }

    /**
     * deny permission setter
     * @param deny_access boolean
     */
    public void setDeny_access(boolean deny_access) {
        this.deny_access = deny_access;
    }

    /**
     * deny permission getter
     * @return boolean
     */
    public boolean isDeny_access() {
        return deny_access;
    }

    /**
     * screen id getter
     * @return int
     */
    public int getScreen_id() {
        return screen_id;
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
}
