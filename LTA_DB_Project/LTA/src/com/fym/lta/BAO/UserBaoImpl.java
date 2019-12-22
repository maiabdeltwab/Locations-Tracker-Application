package com.fym.lta.BAO;

import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.DataBaseConnection;
import com.fym.lta.DAO.UserDao;
import com.fym.lta.DTO.EmployeeDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.UserDto;

import java.util.List;

import javax.sql.rowset.JdbcRowSet;


public class UserBaoImpl implements UserBao {

    private UserDto user = null;
    private UserDao dao = null;

    public UserBaoImpl(UserDto user) {
        this.user = user;
        dao = new DaoFactory().createUserDao(user);
    }

    /**use to check if the last user logged in program saved his login or not
     * @return the last logged in user
     * */
    public void savelogin(UserDto user) {
        try {
            dao.savelogin(user);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**use to check if the last user logged in program saved his login or not
     * @return the last logged in user
     * */
    public UserDto Keeplogin() {

        List<UserDto> list = null;
        UserDto user = null;
        try {
            // create DataBaseConnection to open a new connection
            DataBaseConnection db = new DataBaseConnection();

            list = dao.Keeplogin();

            if (!list.isEmpty() && list != null) {
                for (int i = 0; i < list.size(); i++) {
                    user = list.get(i);
                    JdbcRowSet connect = db.openConnection();
                    user.setConnect(connect);
                }
            }
        } catch (Exception e) {
            user = new UserDto();
            e.printStackTrace();
        }
        return user;
    }

    /** Method to view all exist users
     * @param no parameters
     * @return list of users objects */
    public List<UserDto> viewAll() {
        List<UserDto> list = null;
        try {

            list = dao.viewAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /** Create a new user account
     * @param takes user object inserted by anthor user
     * @Return true for if it success, False if not */
    public boolean add(UserDto u, UserDto user) {
        boolean saveFlage = true;
        try {
            if (dao.isExist(u)) {

                saveFlage = false;

            } else {
                saveFlage = dao.createNew(u, user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return saveFlage;
    }

    /** Delete user account.
     * Takes the object and return true if it has deleted and false if any exception occur*/
    public boolean delete(UserDto user) {

        boolean deleteFlage = true;
        try {
            if (dao.isExist(user))
                deleteFlage = dao.delete(user);
            else
                deleteFlage = false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return deleteFlage;
    }


    /** Search For user account with any attributes of it(id,name,code,...)
     * This method takes user object and return list of users if exist.
     */
    public List<UserDto> searchFor(UserDto user) {
        List<UserDto> users = null;
        try {
            users = dao.searchFor(user);
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return users;
        }
    }


    /** update an existed one
     * @param takes user object inserted by user
     * @Return true for if it success, False if not */
    public boolean update(UserDto u, UserDto user) {

        boolean updateFlag = true;
        try {

            if (dao.isExist(user)) {
                updateFlag = dao.update(u, user);
            } else {

                updateFlag = false;
            }
        }

        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return updateFlag;
    }


    /** used for login
     * open database connection
     * @param take user object with entered username/email and password
     * @return it with other needed data (if user found in database)
     */
    public UserDto login(UserDto user) {
        UserDto result = null;
        result = dao.check(user);

        // create DataBaseConnection to open a new connection
        DataBaseConnection db = new DataBaseConnection();

        if (result != null) {
            JdbcRowSet connect = db.openConnection();
            result.setConnect(connect);
            return result;
        }

        else
            return null;
    }


    /** used for loginout
     *  close database connection
     * @param take user object with entered username/email and password
     * @return it with other needed data (if user found in database)
     */
    public void closeConnection(UserDto user) {

        try {
            // create DataBaseConnection to close the connection to db
            DataBaseConnection db = new DataBaseConnection();
            db.closeConnection(user.getConnect());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**Method to get user staff
     * @param take user dto
     * @return user's staff (if exist)
     * */
    public StaffDto getStaff(UserDto user) {
        StaffDto staff = null;
        staff = dao.getStaff(user);

        if (staff != null)
            return staff;

        else
            return null;
    }

    /**Method to get user employee
     * @param take user dto
     * @return user's employee (if exist)
     * */
    public EmployeeDto getEmployee(UserDto user)

    {
        EmployeeDto employee = null;
        employee = dao.getEmployee(user);

        if (employee != null)
            return employee;

        else
            return null;

    }

    /**get the max user ID
     * @retrun Id
     * **/
    public int calcMaxId() {
        int id = 0;
        try {
            id = dao.getMaxId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }


    /**Check if user existing in database or not
     * @param take user
     * @return true if exist, false if not*/
    public boolean isExist(UserDto user) {
        boolean existFlag = false;
        try {
            existFlag = dao.isExist(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existFlag;
    }


    /**get the max user ID
     * @retrun Id
     * **/
    public int getId(UserDto user) {
        int id = 0;
        try {
            id = dao.getId(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**Use to user setting panel
     * update user info.
     * @param take user dto
     * @return it after update
     * */
    public UserDto userSetting(UserDto user) {
        try {
            user = dao.userSetting(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

}
