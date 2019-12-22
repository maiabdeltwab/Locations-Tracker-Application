
package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.EmpUserBao;
import com.fym.lta.BAO.EmployeeBao;
import com.fym.lta.BAO.RoleBao;
import com.fym.lta.BAO.StaffBao;
import com.fym.lta.BAO.StaffUserBao;
import com.fym.lta.BAO.UserBao;
import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.RoleScreenDao;
import com.fym.lta.DTO.EmployeeDto;
import com.fym.lta.DTO.EmployeeUserDto;
import com.fym.lta.DTO.RoleDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.StaffUserDto;
import com.fym.lta.DTO.UserDto;
import com.fym.lta.Excel.UserExcel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Hala Nagm Eldin
 */
public class UserPanel extends javax.swing.JPanel
{
  @SuppressWarnings({ "compatibility:9070628136493241060",
      "oracle.jdeveloper.java.serialversionuid-stale" })
  private static final long serialVersionUID = 1L;

  private static UserBao business;
  private static UserDto user_data;
  private static UserDto user = null;
  private static StaffUserBao staff_acc_bao;
  private static StaffUserDto staff_acc;
  private static EmpUserBao emp_acc_bao;
  private static EmployeeUserDto emp_acc;
  private static RoleBao role_bao = null;

  /** Creates new form UserPanel */
  public UserPanel(UserDto u)
  {
    try
      {

        user_data = u;
        initComponents();

        user = new UserDto();
        staff_acc = new StaffUserDto();
        emp_acc = new EmployeeUserDto();
        role_bao = new BaoFactory().createRoleBao(user_data);
        staff_acc_bao = new BaoFactory().createStaffUserBao(user_data);
        emp_acc_bao = new BaoFactory().createEmpUserBao(user_data);
        business = new BaoFactory().createUserBao(user_data);


        viewonly(u);
        defaultdata();
        setTableModel(business.viewAll());

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

  }

  private void setTableModel(List<UserDto> users)
  {

    int size = 0;
    if(users!=null&&!users.isEmpty())
      size = users.size();
    Object[][] UserTableArr = new Object[size][5];
    for(int i = 0; i<size; i++)
      {
        UserTableArr[i][0] = users.get(i).getId();
        UserTableArr[i][1] = users.get(i).getUsername();
        UserTableArr[i][2] = users.get(i).getEmail();
        UserTableArr[i][3] = users.get(i).getRole().getName();
      }
    UserTable.setModel(new javax.swing.table.DefaultTableModel(UserTableArr,
        new String[] { "Id", "User Name", "Email", "Role" }));

    //change header color
    UserTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
    {

      @Override
      public Component getTableCellRendererComponent(JTable table,
        Object value, boolean isSelected, boolean hasFocus, int row,
        int column)
      {

        JLabel l =
          (JLabel) super.getTableCellRendererComponent(table, value,
          isSelected, hasFocus, row, column);
        l.setBackground(Color.decode("#0081D3"));
        // l.setBorder(new EtchedBorder());

        l.setForeground(Color.white);

        return l;
      }
    });


    //label under tabel show types count viewed in table
    no_of_rows.setText("Table result: "+
      Integer.toString(UserTable.getRowCount())+"  Users");


  }

  /**For view only user access */
  private void viewonly(UserDto u)
  {

    u.setScreen_name("User");

    RoleScreenDao dao = new DaoFactory().createRoleScreenDao(user_data);
    if(dao.viewonly(u))
      {
        EditPanel.setVisible(false);
        System.out.println(" \n done panel");
      }
  }

  /** to
   * set default data for edit space panel
   */
  private void defaultdata()
  {

    //enable text boxe again
    IdText.setEnabled(true);
    UserTypeCombo.setEnabled(true);
    staffEmpCombo.setEnabled(true);

    Save.setText("Save");

    //set default text for text boxes
    IdText.setText("Enter User ID");
    UsernameText.setText("Enter Username");
    EmailText.setText("Enter User Email");
    PasswordText.setText("Enter user password");


    //Set all existing roles to role combobox
    try
      {
        //create role bao object
        List<RoleDto> role_list = role_bao.listAll(); //get all roles

        RoleCombo.removeAllItems(); //remove all existing data in role combobox

        //set roles name to the role combobox
        if(role_list!=null&&!role_list.isEmpty())
          {
            for(int i = 0; i<role_list.size(); i++)
              {
                RoleCombo.addItem(role_list.get(i).getName());
              }
            RoleCombo.setSelectedIndex(-1); //select nothing in this combo

          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

    UserTypeCombo.setSelectedIndex(-1); //select nothing in this combo
    staffEmpCombo.setSelectedIndex(-1); //select nothing in this combo
  }


  ///not used

  /**this method used in staff panel to view staff user and or delete it edit it
   * @param take staff's user account info
   * */

  public void staff_user(List<UserDto> staff_user)
  {

    setTableModel(staff_user);
    UserTable.repaint();

    //set enable to make user panel vaild only for this user account
    RefreshButton.setEnabled(false);
    SearchButton.setEnabled(false);
    search.setEnabled(false);
    UserTypeCombo.setEnabled(false);
    staffEmpCombo.setEnabled(false);
    clear.setText("Back");


    //set new buttons
    //Declare new button named "new" and set its bounds
    JButton newUser = new JButton("New");
    newUser.setBounds(Save.getBounds());
    Save.setVisible(false);

    //Declare new button named "update" and set its bounds
    JButton updateUser = new JButton("Update");
    //updateUser.setBounds(updateButton.getBounds());
    //updateButton.setVisible(false);

    //Declare new button named "update" and set its bounds
    JButton back = new JButton("Back");
    back.setBounds(clear.getBounds());
    clear.setVisible(false);

    //Declare new button named "update" and set its bounds
    JButton delete = new JButton("Delete");
    delete.setBounds(DeleteButton.getBounds());
    DeleteButton.setVisible(false);
    delete.setLocation(DeleteButton.getLocation());


    //set some changes in update option
    updateUser.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e1)
      {

        try
          {
            if(checkValidity())
              {

                staff_user.get(0).setId(Integer.parseInt(IdText.getText())); //get entered id
                staff_user.get(0).setUsername(UsernameText.getText()); //get entered username
                staff_user.get(0).setEmail(EmailText.getText()); //get entered email
                staff_user.get(0).setPassword(PasswordText.getPassword().toString()); //get entered password


                //get role name
                user.setRole(new RoleDto());
                String role = RoleCombo.getSelectedItem().toString();
                staff_user.get(0).getRole().setName(role);

                if(business.update(staff_user.get(0), user_data))
                  {

                    JOptionPane.showMessageDialog(null,
                      "User info. Updated Successfully ", "Done", 1);

                    staff_user(staff_user);

                  }

                else
                  {
                    JOptionPane.showMessageDialog(null,
                      "Something went wrong!", "Error",
                      JOptionPane.ERROR_MESSAGE);
                  }
              }
          }

        catch(Exception e)
          {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong",
              "Error", JOptionPane.ERROR_MESSAGE);
          }
      }
    });


    //set some changes in delete button option
    delete.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e1)
      {

        try
          {

            //Show confirm message
            int reply =
              JOptionPane.showConfirmDialog(null,
                "Are you sure to delete this user?\n"+
                "All things depend on it will be deleted!", "Warning",
                JOptionPane.YES_NO_OPTION);

            //delete object if user choose yes
            if(reply==JOptionPane.YES_OPTION)
              {

                staff_user.get(0).setId(Integer.parseInt(IdText.getText()));
                staff_acc.setUser_id(Integer.parseInt(IdText.getText()));


                staff_acc_bao.delete(staff_acc);

                if(business.delete(staff_user.get(0)))
                  {
                    JOptionPane.showMessageDialog(null,
                      "User Deleted Successfully.", "Done", 1);

                    staff_user.remove(0);
                    staff_user(staff_user);

                    Save.setEnabled(true);
                    IdText.setEnabled(true);
                  }
                else
                  //if bao method return false (user does'nt exist)
                  JOptionPane.showMessageDialog(null,
                    "Can't delete object!", "Not Found",
                    JOptionPane.ERROR_MESSAGE);
              }
          }

        catch(Exception e)
          {
            //for non expected error
            JOptionPane.showMessageDialog(null,
              "Some Thing went wrong!\nPlease check your entered data. ",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
          }
      }
    });


    //For new button to add user for this staff if it there
    //is no one or if it has been deleted
    newUser.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e1)
      {


        try
          {
            if(checkValidity())
              { //if data is valid

                user.setId(Integer.parseInt(IdText.getText())); //get entered id
                user.setUsername(UsernameText.getText()); //get entered username
                user.setEmail(EmailText.getText()); //get entered email
                user.setPassword(PasswordText.getPassword().toString()); //get entered password

                //get role name
                user.setRole(new RoleDto());
                String role = RoleCombo.getSelectedItem().toString();
                user.getRole().setName(role);


                if(business.add(user, user_data)) //try insert user
                  {

                    staff_user.add(user);
                    JOptionPane.showMessageDialog(null,
                      "User Inserted successfully\n", "Done", 1);
                    //refresh table
                    Save.setEnabled(false);
                    staff_user(staff_user);
                    IdText.setEnabled(false); //disable id text
                  }


              }
            else
              {
                JOptionPane.showMessageDialog(null,
                  "This user already exist.", "Error",
                  JOptionPane.ERROR_MESSAGE);

              }

          }
        catch(Exception e)
          {
            e.printStackTrace();
          }
      }
    });

    // user clear button to back to user panel
    back.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e1)
      {

        UserPanel.this.removeAll();
        StaffPanel staff_panel = new StaffPanel(user_data);

        UserPanel.this.add(staff_panel);
        staff_panel.setBounds(UserPanel.this.getBounds());
        UserPanel.this.add(staff_panel);
        UserPanel.this.repaint();

      }


    });

    //replace buttons

    EditPanel.add(back);
    EditPanel.add(updateUser);
    EditPanel.add(newUser);


  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  private void initComponents()//GEN-BEGIN:initComponents
  {

    search = new javax.swing.JTextField();
    jScrollPane1 = new javax.swing.JScrollPane();
    UserTable = new javax.swing.JTable();
    EditPanel = new javax.swing.JPanel();
    staffEmpCombo = new javax.swing.JComboBox<>();
    UserTypeCombo = new javax.swing.JComboBox<>();
    userTypeLabel = new javax.swing.JLabel();
    userTypeLabel1 = new javax.swing.JLabel();
    IdText = new javax.swing.JTextField();
    UsernameText = new javax.swing.JTextField();
    EmailText = new javax.swing.JTextField();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    PasswordText = new javax.swing.JPasswordField();
    RoleCombo = new javax.swing.JComboBox<>();
    jSeparator1 = new javax.swing.JSeparator();
    jLabel15 = new javax.swing.JLabel();
    savePanel = new javax.swing.JPanel();
    Save = new javax.swing.JButton();
    deletePanel = new javax.swing.JPanel();
    DeleteButton = new javax.swing.JButton();
    clearPanel = new javax.swing.JPanel();
    clear = new javax.swing.JButton();
    deleteAssign = new javax.swing.JButton();
    no_of_rows = new java.awt.Label();
    jSeparator2 = new javax.swing.JSeparator();
    jLabel8 = new javax.swing.JLabel();
    searchPanel = new javax.swing.JPanel();
    SearchButton = new javax.swing.JButton();
    RefreshPanel = new javax.swing.JPanel();
    RefreshButton = new javax.swing.JButton();
    ExportButton = new javax.swing.JButton();
    ImportButton = new javax.swing.JButton();

    setBackground(new java.awt.Color(245, 245, 245));
    setForeground(new java.awt.Color(0, 51, 204));
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    search.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    search.setText("What do you want to search for?");
    search.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    search.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        searchFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        searchFocusLost(evt);
      }
    });
    search.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        searchActionPerformed(evt);
      }
    });
    add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 650, 50));

    UserTable.setAutoCreateRowSorter(true);
    UserTable.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    UserTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String []
      {
        "ID", "User name", "Email", "Role"
      }
    )
    {
      Class[] types = new Class []
      {
        java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
      };
      boolean[] canEdit = new boolean []
      {
        false, false, false, false
      };

      public Class getColumnClass(int columnIndex)
      {
        return types [columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex)
      {
        return canEdit [columnIndex];
      }
    });
    UserTable.setFillsViewportHeight(true);
    UserTable.setRowHeight(25);
    UserTable.setSelectionBackground(new java.awt.Color(0, 153, 204));
    UserTable.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        UserTableMouseClicked(evt);
      }
    });
    UserTable.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyPressed(java.awt.event.KeyEvent evt)
      {
        UserTableKeyPressed(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt)
      {
        UserTableKeyReleased(evt);
      }
    });
    jScrollPane1.setViewportView(UserTable);
    if (UserTable.getColumnModel().getColumnCount() > 0)
    {
      UserTable.getColumnModel().getColumn(0).setHeaderValue("ID");
      UserTable.getColumnModel().getColumn(1).setHeaderValue("User name");
      UserTable.getColumnModel().getColumn(2).setHeaderValue("Email");
      UserTable.getColumnModel().getColumn(3).setHeaderValue("Role");
    }

    add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 750, 780));

    EditPanel.setBackground(new java.awt.Color(245, 245, 245));
    EditPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    staffEmpCombo.setBackground(new java.awt.Color(255, 255, 255));
    staffEmpCombo.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    staffEmpCombo.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        staffEmpComboFocusGained(evt);
      }
    });
    staffEmpCombo.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        staffEmpComboActionPerformed(evt);
      }
    });
    EditPanel.add(staffEmpCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 620, 330, 50));

    UserTypeCombo.setBackground(new java.awt.Color(255, 255, 255));
    UserTypeCombo.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    UserTypeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Staff member", "Employee" }));
    UserTypeCombo.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        UserTypeComboActionPerformed(evt);
      }
    });
    EditPanel.add(UserTypeCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 560, 330, 50));

    userTypeLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    userTypeLabel.setForeground(new java.awt.Color(0, 51, 204));
    userTypeLabel.setText("Assign user to");
    EditPanel.add(userTypeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 120, -1));

    userTypeLabel1.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    userTypeLabel1.setForeground(new java.awt.Color(0, 51, 204));
    userTypeLabel1.setText("Staff/ Employee ");
    EditPanel.add(userTypeLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, 140, -1));

    IdText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    IdText.setText("Enter User ID");
    IdText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    IdText.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        IdTextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        IdTextFocusLost(evt);
      }
    });
    IdText.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        IdTextActionPerformed(evt);
      }
    });
    EditPanel.add(IdText, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 590, 50));

    UsernameText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    UsernameText.setText("Enter Username");
    UsernameText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    UsernameText.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        UsernameTextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        UsernameTextFocusLost(evt);
      }
    });
    EditPanel.add(UsernameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 590, 50));

    EmailText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    EmailText.setText("Enter User Email");
    EmailText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    EmailText.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        EmailTextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        EmailTextFocusLost(evt);
      }
    });
    EditPanel.add(EmailText, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 590, 50));

    jLabel1.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(0, 51, 204));
    jLabel1.setText("ID");
    EditPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, 40));

    jLabel2.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(0, 51, 204));
    jLabel2.setText("User Name");
    EditPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, 40));

    jLabel3.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(0, 51, 204));
    jLabel3.setText("Email");
    EditPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 52, 23));

    jLabel4.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    jLabel4.setForeground(new java.awt.Color(0, 51, 204));
    jLabel4.setText("Password");
    EditPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 366, 120, 30));

    jLabel5.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(0, 51, 204));
    jLabel5.setText("Role");
    EditPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 59, 23));

    PasswordText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    PasswordText.setText("Enter user password");
    PasswordText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    PasswordText.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        PasswordTextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        PasswordTextFocusLost(evt);
      }
    });
    PasswordText.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        PasswordTextActionPerformed(evt);
      }
    });
    EditPanel.add(PasswordText, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 590, 50));

    RoleCombo.setBackground(new java.awt.Color(255, 255, 255));
    RoleCombo.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    RoleCombo.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        RoleComboFocusGained(evt);
      }
    });
    RoleCombo.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        RoleComboActionPerformed(evt);
      }
    });
    EditPanel.add(RoleCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 590, 50));
    EditPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 590, 26));

    jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_user_group_man_woman_128px.png"))); // NOI18N
    EditPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, -1, -1));

    savePanel.setBackground(new java.awt.Color(0, 129, 211));

    Save.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    Save.setForeground(new java.awt.Color(255, 255, 255));
    Save.setText("Save");
    Save.setBorderPainted(false);
    Save.setContentAreaFilled(false);
    Save.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        SaveMouseMoved(evt);
      }
    });
    Save.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        SaveMouseExited(evt);
      }
    });
    Save.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        SaveActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout savePanelLayout = new javax.swing.GroupLayout(savePanel);
    savePanel.setLayout(savePanelLayout);
    savePanelLayout.setHorizontalGroup(
      savePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Save, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
    );
    savePanelLayout.setVerticalGroup(
      savePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, savePanelLayout.createSequentialGroup()
        .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    EditPanel.add(savePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 690, 100, 50));

    deletePanel.setBackground(new java.awt.Color(0, 129, 211));

    DeleteButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    DeleteButton.setForeground(new java.awt.Color(255, 255, 255));
    DeleteButton.setText("Delete");
    DeleteButton.setBorderPainted(false);
    DeleteButton.setContentAreaFilled(false);
    DeleteButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        DeleteButtonMouseMoved(evt);
      }
    });
    DeleteButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        DeleteButtonMouseExited(evt);
      }
    });
    DeleteButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        DeleteButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout deletePanelLayout = new javax.swing.GroupLayout(deletePanel);
    deletePanel.setLayout(deletePanelLayout);
    deletePanelLayout.setHorizontalGroup(
      deletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(DeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
    );
    deletePanelLayout.setVerticalGroup(
      deletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deletePanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(DeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    EditPanel.add(deletePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 690, 100, -1));

    clearPanel.setBackground(new java.awt.Color(0, 129, 211));

    clear.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    clear.setForeground(new java.awt.Color(255, 255, 255));
    clear.setText("Clear");
    clear.setBorderPainted(false);
    clear.setContentAreaFilled(false);
    clear.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        clearMouseMoved(evt);
      }
    });
    clear.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        clearMouseExited(evt);
      }
    });
    clear.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        clearActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout clearPanelLayout = new javax.swing.GroupLayout(clearPanel);
    clearPanel.setLayout(clearPanelLayout);
    clearPanelLayout.setHorizontalGroup(
      clearPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(clear, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
    );
    clearPanelLayout.setVerticalGroup(
      clearPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clearPanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    EditPanel.add(clearPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 690, 100, -1));

    deleteAssign.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_delete_48px_1.png"))); // NOI18N
    deleteAssign.setToolTipText("Delete staff/employee assignment to this user");
    deleteAssign.setBorderPainted(false);
    deleteAssign.setContentAreaFilled(false);
    deleteAssign.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        deleteAssignMouseMoved(evt);
      }
    });
    deleteAssign.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        deleteAssignMouseExited(evt);
      }
    });
    deleteAssign.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        deleteAssignActionPerformed(evt);
      }
    });
    EditPanel.add(deleteAssign, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 590, 80, 50));

    add(EditPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 120, 620, 760));

    no_of_rows.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
    no_of_rows.setForeground(new java.awt.Color(135, 135, 135));
    no_of_rows.setText("no of rows");
    add(no_of_rows, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 880, 150, 20));
    add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 100, 620, 20));

    jLabel8.setBackground(new java.awt.Color(231, 78, 123));
    jLabel8.setFont(new java.awt.Font("VIP Rawy Regular", 0, 36)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(231, 78, 123));
    jLabel8.setText("User");
    add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 40, 240, 80));

    searchPanel.setBackground(new java.awt.Color(0, 129, 211));

    SearchButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    SearchButton.setForeground(new java.awt.Color(255, 255, 255));
    SearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_search_26px.png"))); // NOI18N
    SearchButton.setBorderPainted(false);
    SearchButton.setContentAreaFilled(false);
    SearchButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        SearchButtonMouseMoved(evt);
      }
    });
    SearchButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        SearchButtonMouseExited(evt);
      }
    });
    SearchButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        SearchButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
    searchPanel.setLayout(searchPanelLayout);
    searchPanelLayout.setHorizontalGroup(
      searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(SearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
    );
    searchPanelLayout.setVerticalGroup(
      searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(SearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    add(searchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 40, 90, 50));

    RefreshPanel.setBackground(new java.awt.Color(0, 129, 211));

    RefreshButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    RefreshButton.setForeground(new java.awt.Color(255, 255, 255));
    RefreshButton.setText("Refresh ");
    RefreshButton.setBorderPainted(false);
    RefreshButton.setContentAreaFilled(false);
    RefreshButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        RefreshButtonMouseMoved(evt);
      }
    });
    RefreshButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        RefreshButtonMouseExited(evt);
      }
    });
    RefreshButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        RefreshButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout RefreshPanelLayout = new javax.swing.GroupLayout(RefreshPanel);
    RefreshPanel.setLayout(RefreshPanelLayout);
    RefreshPanelLayout.setHorizontalGroup(
      RefreshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(RefreshPanelLayout.createSequentialGroup()
        .addComponent(RefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 1, Short.MAX_VALUE))
    );
    RefreshPanelLayout.setVerticalGroup(
      RefreshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RefreshPanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(RefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    add(RefreshPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 890, 120, 50));

    ExportButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    ExportButton.setForeground(new java.awt.Color(255, 255, 255));
    ExportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_xls_export_48px.png"))); // NOI18N
    ExportButton.setToolTipText("Export data to excel file");
    ExportButton.setBorderPainted(false);
    ExportButton.setContentAreaFilled(false);
    ExportButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        ExportButtonMouseMoved(evt);
      }
      public void mouseDragged(java.awt.event.MouseEvent evt)
      {
        ExportButtonMouseDragged(evt);
      }
    });
    ExportButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        ExportButtonMouseExited(evt);
      }
    });
    ExportButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        ExportButtonActionPerformed(evt);
      }
    });
    add(ExportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 880, 80, 70));

    ImportButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    ImportButton.setForeground(new java.awt.Color(255, 255, 255));
    ImportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_xls_import_48px.png"))); // NOI18N
    ImportButton.setToolTipText("Import data from excel file");
    ImportButton.setBorderPainted(false);
    ImportButton.setContentAreaFilled(false);
    ImportButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        ImportButtonMouseMoved(evt);
      }
      public void mouseDragged(java.awt.event.MouseEvent evt)
      {
        ImportButtonMouseDragged(evt);
      }
    });
    ImportButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        ImportButtonMouseExited(evt);
      }
    });
    ImportButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        ImportButtonActionPerformed(evt);
      }
    });
    add(ImportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 880, 80, 70));
  }//GEN-END:initComponents


    private void IdTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdTextActionPerformed
    }//GEN-LAST:event_IdTextActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
   
    try
      {
        user = new UserDto();
        List<UserDto> search_list = null;
        user.setSearch(search.getText());
        search_list = business.searchFor(user);

        if(search_list!=null)
          {
            setTableModel(search_list);
            UserTable.repaint();
          }
        else
          {
            //if there is no result show message tell user that their is no search for this text
            JOptionPane.showMessageDialog(null,
              "There is no search result for: "+search.getText(),
              "Invalid search", 1);
          }

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    

    }//GEN-LAST:event_searchActionPerformed

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
        
    try
      {
        user = new UserDto();
        List<UserDto> search_list = null;
        user.setSearch(search.getText());
        search_list = business.searchFor(user);

        if(search_list!=null)
          {
            setTableModel(search_list);
            UserTable.repaint();
          }
        else
          {
            //if there is no result show message tell user that their is no search for this text
            JOptionPane.showMessageDialog(null,
              "There is no search result for: "+search.getText(),
              "Invalid search", 1);
          }

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
        
        
        
    }//GEN-LAST:event_SearchButtonActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
    // TODO add your handling code here:
    if(Save.getText().equalsIgnoreCase("Update"))
      update();

    else
      {

        try
          {
            if(checkValidity())
              { //if data is valid


                user.setId(Integer.parseInt(IdText.getText())); //get entered id
                user.setUsername(UsernameText.getText()); //get entered username
                user.setEmail(EmailText.getText()); //get entered email
                user.setPassword(PasswordText.getText()); //get entered password


                //get role name
                user.setRole(new RoleDto());
                String role = RoleCombo.getSelectedItem().toString();
                user.getRole().setName(role);


                if(business.add(user, user_data))
                  { //try insert user
                    if(UserTypeCombo.getSelectedIndex()==0)
                      { // staff

                        staff_acc.setUser_id(Integer.parseInt(IdText.getText()));
                        staff_acc.setStaff(new StaffDto());
                        staff_acc.getStaff().setName(staffEmpCombo.getSelectedItem().toString());

                        if(staff_acc_bao.insert(staff_acc))
                          {
                            JOptionPane.showMessageDialog(null,
                              "User Inserted Successfully And Assigned To Staff",
                              "Done", 1);
                          }
                      }

                    else if(UserTypeCombo.getSelectedIndex()==1)
                      { //employee
                        emp_acc.setUser_id(Integer.parseInt(IdText.getText()));
                        emp_acc.setEmployee(new EmployeeDto());
                        emp_acc.getEmployee().setName(staffEmpCombo.getSelectedItem().toString());
                        if(emp_acc_bao.insert(emp_acc))
                          {
                            JOptionPane.showMessageDialog(null,
                              "User Inserted Successfully And Assigned To Employee",
                              "Done", 1);
                          }

                      }
                    else
                      {
                        JOptionPane.showMessageDialog(null,
                          "User Inserted successfully\n", "Done", 1);
                      }

                    //refresh table
                    setTableModel(business.viewAll());
                    UserTable.repaint();
                    IdText.setEnabled(false); //disable id text
                    UserTypeCombo.setEnabled(false);
                    staffEmpCombo.setEnabled(false);
                    Save.setText("Update");
                  }
                else
                  {
                    int reply =
                      JOptionPane.showConfirmDialog(null,
                        "This user is already exist!\n\n"+
                        "Do you want update it?", "Warning",
                        JOptionPane.YES_NO_OPTION);

                    //update object if user choose yes
                    if(reply==JOptionPane.YES_OPTION)
                      {
                        update();
                        Save.setText("Update");
                        IdText.setEnabled(false);
                        UserTypeCombo.setEnabled(false);
                        staffEmpCombo.setEnabled(false);
                      }
                  }
              }

          }
        catch(Exception e)
          {
            e.printStackTrace();
          }
      }
        
        
    }//GEN-LAST:event_SaveActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
    // TODO add your handling code here:

    try
      {

        //Show confirm message
        int reply =
          JOptionPane.showConfirmDialog(null,
            "Are you sure to delete this user?\n"+
            "All things depend on it will be deleted!", "Warning",
            JOptionPane.YES_NO_OPTION);

        //delete object if user choose yes
        if(reply==JOptionPane.YES_OPTION)
          {

            user.setId(Integer.parseInt(IdText.getText()));
            user.setEmail(EmailText.getText());
            staff_acc.setUser_id(Integer.parseInt(IdText.getText()));
            emp_acc.setUser_id(Integer.parseInt(IdText.getText()));


            if(business.delete(user))
              {
                JOptionPane.showMessageDialog(null,
                  "User Deleted Successfully.", "Done", 1);
                setTableModel(business.viewAll());
                UserTable.repaint();
                IdText.setEnabled(true);
                UserTypeCombo.setEnabled(true);
                staffEmpCombo.setEnabled(true);
                Save.setText("Save");
              }
            else
              //if bao method return false (user does'nt exist)
              JOptionPane.showMessageDialog(null,
                "Can't this user, many things depend on it!", "Not Found",
                JOptionPane.ERROR_MESSAGE);
          }
      }

    catch(Exception e)
      {
        //for non expected error
        JOptionPane.showMessageDialog(null,
          "Some Thing went wrong!\nPlease check your entered data. ",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
      }
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void RefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButtonActionPerformed
    // TODO add your handling code here:

    this.setTableModel(business.viewAll());
    UserTable.repaint();
    defaultdata(); //call setdefault method to rest edit panel
                 
            
    }//GEN-LAST:event_RefreshButtonActionPerformed

    private void searchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusGained
    if(search.getText().equalsIgnoreCase("What do you want to search for?"))
      search.setText("");

    search.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_searchFocusGained

    private void IdTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusGained
    if(IdText.getText().equalsIgnoreCase("Enter User ID"))
      IdText.setText("");

    IdText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_IdTextFocusGained

    private void UsernameTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_UsernameTextFocusGained
    if(UsernameText.getText().equalsIgnoreCase("Enter Username"))
      UsernameText.setText("");

    UsernameText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_UsernameTextFocusGained

    private void EmailTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EmailTextFocusGained
    if(EmailText.getText().equalsIgnoreCase("Enter User Email"))
      EmailText.setText("");

    EmailText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_EmailTextFocusGained

    private void PasswordTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PasswordTextFocusGained
    if(PasswordText.getText().equalsIgnoreCase("Enter user password"))
      PasswordText.setText("");

    PasswordText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_PasswordTextFocusGained

    private void searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusLost
    if(search.getText().trim().equalsIgnoreCase(""))
      search.setText("What do you want to search for?");

    search.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_searchFocusLost

    private void IdTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusLost
    if(IdText.getText().trim().equalsIgnoreCase(""))
      IdText.setText("Enter User ID");

    IdText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_IdTextFocusLost

    private void UsernameTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_UsernameTextFocusLost
    if(UsernameText.getText().trim().equalsIgnoreCase(""))
      UsernameText.setText("Enter Username");

    UsernameText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_UsernameTextFocusLost

    private void EmailTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EmailTextFocusLost
    if(EmailText.getText().trim().equalsIgnoreCase(""))
      EmailText.setText("Enter User Email");

    EmailText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_EmailTextFocusLost

    private void UserTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserTableMouseClicked
    
    //user can't edit them
    UserTypeCombo.setEnabled(false);
    staffEmpCombo.setEnabled(false);
    IdText.setEnabled(false);
    Save.setText("Update");

    int row = UserTable.getSelectedRow();
    IdText.setText(UserTable.getValueAt(row,
      0).toString()); //get id from table
    UsernameText.setText(UserTable.getValueAt(row,
      1).toString()); //get user name
    EmailText.setText(UserTable.getValueAt(row,
      2).toString()); //get user emaul
   

    //get role name
    for(int i = 0; i<RoleCombo.getItemCount(); i++)
      {
        if(RoleCombo.getItemAt(i).toString().equalsIgnoreCase(UserTable.getValueAt(row,
              3).toString()))
          RoleCombo.setSelectedIndex(i);
      }


    //get staff or employee for this user
    user.setId(Integer.parseInt(IdText.getText()));
    StaffDto staff = business.getStaff(user);

    if(staff!=null) //if staff found show it in edit space
      {

        UserTypeCombo.setSelectedIndex(0); //select staff
        staffEmpCombo.removeAllItems();
        staffEmpCombo.addItem(staff.getName()); //show staff name

      }

    else
      {
        EmployeeDto employee = business.getEmployee(user);

        if(employee!=null) //if employee found show it in edit space
          {

            UserTypeCombo.setSelectedIndex(1); //select employee
            staffEmpCombo.removeAllItems();
            staffEmpCombo.addItem(employee.getName()); //show employee name

          }


        else
          {

            //set them enable
            UserTypeCombo.setEnabled(true);
            staffEmpCombo.setEnabled(true);
            UserTypeCombo.setSelectedIndex(-1);
            staffEmpCombo.setSelectedIndex(-1);
          }


      }
    
    
    }//GEN-LAST:event_UserTableMouseClicked

    private void UserTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UserTableKeyPressed


    if(evt.getExtendedKeyCode()==KeyEvent.VK_UP||
      evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
      {
        int row = UserTable.getSelectedRow();
        if(evt.getExtendedKeyCode()==KeyEvent.VK_UP)
          row--; //for up key decrement
        else if(evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
          row++; // down key increment


        try
          {
            //user can't edit them
            UserTypeCombo.setEnabled(false);
            staffEmpCombo.setEnabled(false);
            IdText.setEnabled(false);
            Save.setText("Update");


            IdText.setText(UserTable.getValueAt(row, 0).toString());
            UsernameText.setText(UserTable.getValueAt(row, 1).toString());
            EmailText.setText(UserTable.getValueAt(row, 2).toString());
            PasswordText.setText(UserTable.getValueAt(row, 4).toString());

            //get role from table to role combo
            for(int i = 0; i<RoleCombo.getItemCount(); i++)
              {
                if(RoleCombo.getSelectedItem().toString().equalsIgnoreCase(UserTable.getValueAt(row,
                      3).toString()))
                  RoleCombo.setSelectedIndex(i);
              }


            //get staff or employee for this user
            user.setId(Integer.parseInt(IdText.getText()));
            StaffDto staff = business.getStaff(user);

            if(staff!=null) //if staff found show it in edit space
              {


                UserTypeCombo.setSelectedIndex(0); //select staff
                staffEmpCombo.removeAllItems();
                staffEmpCombo.addItem(staff.getName()); //show staff name

              }

            else
              {
                EmployeeDto employee = business.getEmployee(user);

                if(employee!=null) //if employee found show it in edit space
                  {

                    UserTypeCombo.setSelectedIndex(1); //select employee
                    staffEmpCombo.removeAllItems();
                    staffEmpCombo.addItem(employee.getName()); //show employee name

                  }

                else
                  {

                    //set them enable
                    UserTypeCombo.setEnabled(true);
                    staffEmpCombo.setEnabled(true);
                    UserTypeCombo.setSelectedIndex(-1);
                    staffEmpCombo.setSelectedIndex(-1);
                  }

              }

          }
        catch(java.lang.ArrayIndexOutOfBoundsException e)
          {
            e.printStackTrace();
          }

      }

    //delete selected object when press delete
    if(evt.getExtendedKeyCode()==KeyEvent.VK_DELETE)
      {

        try
          {

            //For one selected row in table
            if(UserTable.getSelectedRowCount()==1)
              {
                //Show confirm message
                int reply =
                  JOptionPane.showConfirmDialog(null,
                    "Are you sure to delete this user?\n"+
                    "All things depend on it will be deleted!", "Warning",
                    JOptionPane.YES_NO_OPTION);

                //delete object if user choose yes
                if(reply==JOptionPane.YES_OPTION)
                  {

                    //get selected user id from table
                    int selection =
                      Integer.parseInt(UserTable.getValueAt(UserTable.getSelectedRow(),
                          0).toString());
                    user.setId(selection); //set it to user object

                    //delete it using bao delete method
                    if(business.delete(user)) //if it deleted sucessfilly show message to tell user that
                      {
                        JOptionPane.showMessageDialog(null,
                          "User Deleted Successfully", "Done", 1);
                        setTableModel(business.viewAll());
                        UserTable.repaint();
                        IdText.setEnabled(true);
                        UserTypeCombo.setEnabled(true);
                        staffEmpCombo.setEnabled(true);
                        Save.setText("Save");
                      }

                    else
                      //if bao method return false (user not be deleted)
                      JOptionPane.showMessageDialog(null,
                        "Can't delete this account.\n\n"+
                        "There are things depend on it.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                  }

              }
            else if(UserTable.getSelectedRowCount()==0)
              {
                // if there is no selected row show message to ask user to select a row
                JOptionPane.showMessageDialog(null,
                  "There is no selected row in the table\n\n", "Error",
                  JOptionPane.WARNING_MESSAGE);
              }
            else
              {
                // if there are more than one row selected show message to ask user to select one row
                JOptionPane.showMessageDialog(null,
                  "Please, Select only one row\n\n", "Error",
                  JOptionPane.WARNING_MESSAGE);
              }
          }

        catch(Exception e)
          {
            e.printStackTrace();
          }
      }
      
      
    }//GEN-LAST:event_UserTableKeyPressed

    private void staffEmpComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_staffEmpComboActionPerformed
  
    }//GEN-LAST:event_staffEmpComboActionPerformed

    private void staffEmpComboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_staffEmpComboFocusGained
       
    }//GEN-LAST:event_staffEmpComboFocusGained

  private void clearActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_clearActionPerformed
  {//GEN-HEADEREND:event_clearActionPerformed
    // TODO add your handling code here:

    defaultdata(); //call setdefault method to rest edit panel
  }//GEN-LAST:event_clearActionPerformed

  private void RoleComboFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_RoleComboFocusGained
  {//GEN-HEADEREND:event_RoleComboFocusGained
    // TODO add your handling code here:
  }//GEN-LAST:event_RoleComboFocusGained

  private void RoleComboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_RoleComboActionPerformed
  {//GEN-HEADEREND:event_RoleComboActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_RoleComboActionPerformed

  private void UserTypeComboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_UserTypeComboActionPerformed
  {//GEN-HEADEREND:event_UserTypeComboActionPerformed
    

    //set the comboBox items by name of staff/employees
    try
      {
        if(UserTypeCombo.getSelectedIndex()==0)
          {
            StaffBao staff_bao = new BaoFactory().createStaffBao(user_data);
            List<StaffDto> staff_list = staff_bao.viewAll();
            staffEmpCombo.removeAllItems();
            if(staff_list!=null&&!staff_list.isEmpty())
              for(int i = 0; i<staff_list.size(); i++)
                {
                  if(!staff_acc_bao.staffIsExist(staff_list.get(i)))
                    staffEmpCombo.addItem(staff_list.get(i).getName());
                }
          }
        else if(UserTypeCombo.getSelectedIndex()==1)
          {
            EmployeeBao emp_bao =
              new BaoFactory().createEmployeeBao(user_data);
            List<EmployeeDto> emp_list = emp_bao.listAll();
            staffEmpCombo.removeAllItems();
            if(emp_list!=null&&!emp_list.isEmpty())
              for(int i = 0; i<emp_list.size(); i++)
                {
                  if(!emp_acc_bao.employeeIsExist(emp_list.get(i)))
                    staffEmpCombo.addItem(emp_list.get(i).getName());
                }
          }

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }//GEN-LAST:event_UserTypeComboActionPerformed

  private void PasswordTextFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_PasswordTextFocusLost
  {//GEN-HEADEREND:event_PasswordTextFocusLost
    // TODO add your handling code here:
  }//GEN-LAST:event_PasswordTextFocusLost

  private void PasswordTextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_PasswordTextActionPerformed
  {//GEN-HEADEREND:event_PasswordTextActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_PasswordTextActionPerformed

  private void UserTableKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_UserTableKeyReleased
  {//GEN-HEADEREND:event_UserTableKeyReleased
    // TODO add your handling code here:
  }//GEN-LAST:event_UserTableKeyReleased

  private void SearchButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SearchButtonMouseExited
  {//GEN-HEADEREND:event_SearchButtonMouseExited
    // TODO add your handling code here:
    searchPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_SearchButtonMouseExited

  private void RefreshButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshButtonMouseExited
  {//GEN-HEADEREND:event_RefreshButtonMouseExited
    // TODO add your handling code here:
    RefreshPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_RefreshButtonMouseExited

  private void SaveMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseExited
  {//GEN-HEADEREND:event_SaveMouseExited
    // TODO add your handling code here:
    savePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_SaveMouseExited

  private void DeleteButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteButtonMouseExited
  {//GEN-HEADEREND:event_DeleteButtonMouseExited
    // TODO add your handling code here:
    deletePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_DeleteButtonMouseExited

  private void clearMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseExited
  {//GEN-HEADEREND:event_clearMouseExited
    // TODO add your handling code here:
    clearPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_clearMouseExited

  private void SearchButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SearchButtonMouseMoved
  {//GEN-HEADEREND:event_SearchButtonMouseMoved
    // TODO add your handling code here:
    searchPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_SearchButtonMouseMoved

  private void RefreshButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshButtonMouseMoved
  {//GEN-HEADEREND:event_RefreshButtonMouseMoved
    // TODO add your handling code here:
    RefreshPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_RefreshButtonMouseMoved

  private void SaveMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseMoved
  {//GEN-HEADEREND:event_SaveMouseMoved
    // TODO add your handling code here:

    savePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_SaveMouseMoved

  private void DeleteButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteButtonMouseMoved
  {//GEN-HEADEREND:event_DeleteButtonMouseMoved
    // TODO add your handling code here:
    deletePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_DeleteButtonMouseMoved

  private void clearMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseMoved
  {//GEN-HEADEREND:event_clearMouseMoved
    // TODO add your handling code here:
    clearPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_clearMouseMoved

  private void deleteAssignActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deleteAssignActionPerformed
  {//GEN-HEADEREND:event_deleteAssignActionPerformed
    // TODO add your handling code here:

    if(UserTypeCombo.getSelectedIndex()==0)
      {
        StaffUserBao staff_bao =
          new BaoFactory().createStaffUserBao(user_data);
        StaffUserDto staff = new StaffUserDto();
        staff.setUser_id(Integer.parseInt(IdText.getText()));

        if(staff_bao.delete(staff))
          {
            JOptionPane.showMessageDialog(null,
              "Assignment Deleted Successfully.", "Done", 1);
            UserTypeCombo.setSelectedIndex(-1);
            UserTypeCombo.setEnabled(true);
            staffEmpCombo.setSelectedIndex(-1);
            staffEmpCombo.setEnabled(true);
          }

      }


    else if(UserTypeCombo.getSelectedIndex()==1)
      {
        EmpUserBao emp_bao = new BaoFactory().createEmpUserBao(user_data);
        EmployeeUserDto emp = new EmployeeUserDto();
        emp.setUser_id(Integer.parseInt(IdText.getText()));

        if(emp_bao.delete(emp))
          {
            JOptionPane.showMessageDialog(null,
              "Assignment Deleted Successfully.", "Done", 1);
            UserTypeCombo.setSelectedIndex(-1);
            UserTypeCombo.setEnabled(true);
            staffEmpCombo.setSelectedIndex(-1);
            staffEmpCombo.setEnabled(true);
          }


      }

    else
      JOptionPane.showMessageDialog(null,
        "There is no assignment to this user.", "Done", 1);
  
  }//GEN-LAST:event_deleteAssignActionPerformed

  private void deleteAssignMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_deleteAssignMouseExited
  {//GEN-HEADEREND:event_deleteAssignMouseExited
    // TODO add your handling code here:
    deleteAssign.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_delete_48px_1.png"))); // NOI18N

  }//GEN-LAST:event_deleteAssignMouseExited

  private void deleteAssignMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_deleteAssignMouseMoved
  {//GEN-HEADEREND:event_deleteAssignMouseMoved
    // TODO add your handling code here:
    deleteAssign.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_delete_48px_2.png"))); // NOI18N

  }//GEN-LAST:event_deleteAssignMouseMoved

  private void ExportButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ExportButtonMouseMoved
  {//GEN-HEADEREND:event_ExportButtonMouseMoved
    // TODO add your handling code here:
    ExportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_xls_export_48px_1.png"))); // NOI18N
  }//GEN-LAST:event_ExportButtonMouseMoved

  private void ExportButtonMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ExportButtonMouseDragged
  {//GEN-HEADEREND:event_ExportButtonMouseDragged
    // TODO add your handling code here:
  }//GEN-LAST:event_ExportButtonMouseDragged

  private void ExportButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ExportButtonMouseExited
  {//GEN-HEADEREND:event_ExportButtonMouseExited
    // TODO add your handling code here:
    ExportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_xls_export_48px.png"))); // NOI18N
  }//GEN-LAST:event_ExportButtonMouseExited

  private void ExportButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ExportButtonActionPerformed
  {//GEN-HEADEREND:event_ExportButtonActionPerformed
    // TODO add your handling code here:
    UserExcel excel = new UserExcel(user);
    List<UserDto> users = null;
    UserDto user_ = null;

    try
    {
      //get all buildings in table

      for(int i=0; i<UserTable.getRowCount() ; i++)
      {
        if(users==null)
        users = new ArrayList<>();

        user_ = new UserDto();
        
        user_.setId(Integer.parseInt(UserTable.getValueAt(i, 0).toString()));
        user_.setUsername(UserTable.getValueAt(i, 1).toString());
        user_.setEmail(UserTable.getValueAt(i, 2).toString());
        user_.setRole(new RoleDto());
        user_.getRole().setName(UserTable.getValueAt(i,
          3).toString());

      users.add(user_);

      user_=null;

    }

    //create new file chooser
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    fileChooser.setAcceptAllFileFilterUsed(false);

    //set file name
    String filename = "Users.xlsx";

    if(!search.getText().equalsIgnoreCase("What do you want to search for?"))
    filename = search.getText()+ "-Users.xlsx";
    //show file dialog
    int result = fileChooser.showSaveDialog(null);

    //user selected file
    if(result==JFileChooser.APPROVE_OPTION)
    {
      //get path
      String filePath =
      fileChooser.getSelectedFile().getAbsolutePath();

      excel.write(filename, filePath, users);

      //write excel file
      try
      {

        JOptionPane.showMessageDialog(null,
          "Users data export ended successfully!", "Done", 1);
      }
      catch(Exception e)
      {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to write file!",
          "Error", 0);
      }

    }

    else
    {
      System.out.println("saving file action cancelled");
    }
    }
    catch(Exception e)
    {
      e.printStackTrace();
      System.out.println("error writing excel");
    }
  }//GEN-LAST:event_ExportButtonActionPerformed

  private void ImportButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ImportButtonMouseMoved
  {//GEN-HEADEREND:event_ImportButtonMouseMoved
    // TODO add your handling code here:
    ImportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_xls_import_48px_1.png"))); // NOI18N
  }//GEN-LAST:event_ImportButtonMouseMoved

  private void ImportButtonMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ImportButtonMouseDragged
  {//GEN-HEADEREND:event_ImportButtonMouseDragged
    // TODO add your handling code here:
  }//GEN-LAST:event_ImportButtonMouseDragged

  private void ImportButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ImportButtonMouseExited
  {//GEN-HEADEREND:event_ImportButtonMouseExited
    // TODO add your handling code here:
    ImportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_xls_import_48px.png"))); // NOI18N
  }//GEN-LAST:event_ImportButtonMouseExited

  private void ImportButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ImportButtonActionPerformed
  {//GEN-HEADEREND:event_ImportButtonActionPerformed

    UserExcel read = new UserExcel(user_data);
    UserBao bao = new BaoFactory().createUserBao(user_data);
    JFileChooser fileChooser = new JFileChooser();

    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    FileFilter filter =
    new FileNameExtensionFilter("Excel File", "xls", "xlsx", "XLS",
      "XLSX");

    fileChooser.addChoosableFileFilter(filter);

    fileChooser.setAcceptAllFileFilterUsed(false);

    int result = fileChooser.showOpenDialog(null);

    if(result==JFileChooser.APPROVE_OPTION)
    {
      File file = fileChooser.getSelectedFile();
      String filePath = file.getAbsolutePath();
      try
      {

        List<UserDto> import_users = read.read_data(filePath);

        if(import_users!=null)
        {

          //insert reading building to database
          for (int i=0;  i<import_users.size() ;i++)
          {
            bao.add(import_users.get(i), user_data);
          }

          // show result set in table
          setTableModel(import_users);
          UserTable.repaint();

        }

      }

      catch(Exception e)
      {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,
          "Error in reading data from excel \nPlease check your file and try again",
          "Error",JOptionPane.ERROR_MESSAGE);
      }

    }
  }//GEN-LAST:event_ImportButtonActionPerformed


  private boolean checkValidity()
  {

    try
      { //check for if id is empty
        if(IdText.getText().equalsIgnoreCase("Enter User ID"))
          {
            JOptionPane.showMessageDialog(null, "Please, enter user id",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("id text is empry");
          }

        //Check validity of id
        try
          {
            int id = Integer.parseInt(IdText.getText());
            //Check for the entered id is a positive number
            if(id<1)
              {
                JOptionPane.showMessageDialog(null,
                  "Invalid Id \n\n(ID is only Positive Numbers)",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("ID is only Positive Numbers");
              }
          }
        catch(java.lang.NumberFormatException e2)
          {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(null,
              "Please enter number for user ID", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            return false;
          }


        //check for if username is empty
        if(UsernameText.getText().equalsIgnoreCase("Enter Username"))

          {
            JOptionPane.showMessageDialog(null, "Please, enter username.",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("code text is empty");
          }

        //check validity of username

        //Username can't start with number or symbol
        if(!Character.isAlphabetic(UsernameText.getText().charAt(0)))
          {
            JOptionPane.showMessageDialog(null,
              "Invalid Username format\n\n(Username can't start with number or symbol)",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Username can't start with number or symbol");

          }
        //check for all chars
        for(int i = 1; i<UsernameText.getText().length(); i++)
          {

            //code contain only letters/numbers ane '_'
            if(!Character.isLetterOrDigit(UsernameText.getText().charAt(i))&&
              UsernameText.getText().charAt(i)!='_')
              {
                JOptionPane.showMessageDialog(null,
                  "Invalid Username format\n\n(Username can only be a sequence of Unicode letters and digits separated by underscore)",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Username can't contain stranger symbols");

              }
          }


        //check for if Email is empty
        if(EmailText.getText().equalsIgnoreCase("Enter User Email"))

          {
            JOptionPane.showMessageDialog(null, "Please, enter user email.",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("user email text is empty");
          }


        //check validity of email

        //Email can't start with number or symbol
        if(!Character.isAlphabetic(EmailText.getText().charAt(0)))
          {
            JOptionPane.showMessageDialog(null,
              "Invalid Email format\n\n(Email can't start with number or symbol)",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Email can't start with number or symbol");

          }

        if(!(EmailText.getText().contains("@")))
          {
            JOptionPane.showMessageDialog(null,
              "invalid Email format\n\n It should be in form of\n example@domain.com",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
          }


        String[] email = EmailText.getText().split("@");

        if(email.length!=2)
          {
            JOptionPane.showMessageDialog(null,
              "Invalid Email format\n\n"+
              " (It should be in form of \"example@domain.com\"",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);

            return false;

          }


        //check for email two sides
        for(int i = 1; i<email[0].length(); i++) //side one
          {


            if(!Character.isLetterOrDigit(email[0].charAt(i))&&
              (email[0].charAt(i)!='_'&&
              (email[0].charAt(i)!='.'||i==email[0].length()-1)))
              {
                JOptionPane.showMessageDialog(null,
                  "Invalid Email format\n\n ", "Invalid Input",
                  JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Invalid Email format1");

              }
          }


        for(int i = 0; i<email[1].length(); i++) //side two
          {


            if(!Character.isLetter(email[1].charAt(i))&&
              (email[1].charAt(i)!='.'||(i==email[1].length()-1||i==0)))
              {
                JOptionPane.showMessageDialog(null,
                  "Invalid Email format\n\n ", "Invalid Input",
                  JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Invalid Email format2");

              }
          }

        //if password empty
        if(PasswordText.getText().isEmpty())
          {
            JOptionPane.showMessageDialog(null, "Please, Enter password",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Password is empty");

          }
        //check validity of password
        if(PasswordText.getText().length()<8)
          {
            JOptionPane.showMessageDialog(null,
              "Invalid Password\n\n(Password length must at least 8 character)",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Password length must at least 8 character");

          }

        if(RoleCombo.getSelectedIndex()==-1)
          {
            JOptionPane.showMessageDialog(null, "Please, Select user role.",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("user role isn't selected.");
          }
        return true; //all data correct

      }

    catch(IllegalArgumentException e1)
      {
        e1.printStackTrace();
        return false;
      }

    catch(Exception e)
      {
        // TODO: Add catch code
        e.printStackTrace();

        //if there is any other non expecting error
        JOptionPane.showMessageDialog(null,
          "Some Thing went wrong!\n\nPlease check your entered data. ",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
        return false;
      }

  }


  /**Update function*/
  private void update()
  {

    try
      {
        if(checkValidity())
          {

            user.setId(Integer.parseInt(IdText.getText())); //get entered id
            user.setUsername(UsernameText.getText()); //get entered username
            user.setEmail(EmailText.getText()); //get entered email
            user.setPassword(PasswordText.getText()); //get entered password


            //get role name
            user.setRole(new RoleDto());
            String role = RoleCombo.getSelectedItem().toString();
            user.getRole().setName(role);

            if(business.update(user, user_data))
              {


                if(UserTypeCombo.getSelectedIndex()==0)
                  { //staff

                    staff_acc.setUser_id(Integer.parseInt(IdText.getText())); //get user id
                    //get staff name
                    staff_acc.setStaff(new StaffDto());
                    staff_acc.getStaff().setName(staffEmpCombo.getSelectedItem().toString());

                    if(staff_acc_bao.update(staff_acc)) //update user's staff
                      JOptionPane.showMessageDialog(null,
                        "User Updated Successfully, And Re-assigned To Staff",
                        "Done", 1);
                    UserTypeCombo.setEnabled(false);
                    staffEmpCombo.setEnabled(false);
                  }

                else if(UserTypeCombo.getSelectedIndex()==1)
                  {
                    //get employee name
                    emp_acc.setUser_id(Integer.parseInt(IdText.getText()));
                    emp_acc.setEmployee(new EmployeeDto());
                    emp_acc.getEmployee().setName(staffEmpCombo.getSelectedItem().toString());
                    if(emp_acc_bao.update(emp_acc))
                      {
                        JOptionPane.showMessageDialog(null,
                          "User Updated Successfully, Re-Assigned To Employee",
                          "Done", 1);

                        UserTypeCombo.setEnabled(false);
                        staffEmpCombo.setEnabled(false);
                      }
                  }

                else if(!staffEmpCombo.isEnabled()||
                  staffEmpCombo.getSelectedIndex()==-1)
                  {
                    JOptionPane.showMessageDialog(null,
                      "User info. Updated Successfully ", "Done", 1);
                  }

                //refresh table
                setTableModel(business.viewAll());
                UserTable.repaint();

              }
          }
        else
          {
            JOptionPane.showMessageDialog(null, "Something went wrong!",
              "Error", JOptionPane.ERROR_MESSAGE);
          }

      }


    catch(Exception e)
      {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Something went wrong", "Error",
          JOptionPane.ERROR_MESSAGE);
      }


  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton DeleteButton;
  private javax.swing.JPanel EditPanel;
  private javax.swing.JTextField EmailText;
  private javax.swing.JButton ExportButton;
  private javax.swing.JTextField IdText;
  private javax.swing.JButton ImportButton;
  private javax.swing.JPasswordField PasswordText;
  private javax.swing.JButton RefreshButton;
  private javax.swing.JPanel RefreshPanel;
  private javax.swing.JComboBox<String> RoleCombo;
  private javax.swing.JButton Save;
  private javax.swing.JButton SearchButton;
  private javax.swing.JTable UserTable;
  private javax.swing.JComboBox<String> UserTypeCombo;
  private javax.swing.JTextField UsernameText;
  private javax.swing.JButton clear;
  private javax.swing.JPanel clearPanel;
  private javax.swing.JButton deleteAssign;
  private javax.swing.JPanel deletePanel;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel15;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private java.awt.Label no_of_rows;
  private javax.swing.JPanel savePanel;
  private javax.swing.JTextField search;
  private javax.swing.JPanel searchPanel;
  private javax.swing.JComboBox<String> staffEmpCombo;
  private javax.swing.JLabel userTypeLabel;
  private javax.swing.JLabel userTypeLabel1;
  // End of variables declaration//GEN-END:variables

}
