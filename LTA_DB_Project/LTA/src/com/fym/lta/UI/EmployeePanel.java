
package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.EmployeeBao;
import com.fym.lta.BAO.RoleScreenBao;
import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.EmployeeDao;
import com.fym.lta.DTO.EmployeeDto;
import com.fym.lta.DTO.UserDto;
import com.fym.lta.Excel.EmployeeExcel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

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
 * @author Mustafa Zalat
 */
public class EmployeePanel extends javax.swing.JPanel
{
  @SuppressWarnings("compatibility:1071024738053911202")
  private static final long serialVersionUID = -513603827684253176L;

  /**Definitions*/
  //define user dto as "user"
  private static UserDto user;
  //define employee bao as "bao"
  private static EmployeeBao bao;
  //define employee dto as "employee"
  private static EmployeeDto employee;

  /** Creates new form EmployeeJPanel */
  public EmployeePanel(UserDto user)
  {
    try
      {
        //set panel components
        initComponents();
      
        //set taken user object to the private one
        EmployeePanel.user = user;
        
        bao = new BaoFactory().createEmployeeBao(user); //init "bao"
      
        //use user access method to check user validity on this screen
        userAccess();
      
        /* get department data using view all method in bao
         * paint department table for the result data*/
        setTableModel(bao.listAll());
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }

  /** set employee table model
   * @param employee list */
  private void setTableModel(List<EmployeeDto> employees)
  {
    
    int size = 0; //for table row size
    
    //if given list not empty or null
    if(employees!=null&&!employees.isEmpty())
      {
        size = employees.size(); //set table size to this list size
      }

    /*this (2D) array of objects for table model
     *the first D for rows, second for columns */
    Object[][] empArr = new Object[size][3];
    
    for(int i = 0; i<size; i++) //for each row
      {
        empArr[i][0] = employees.get(i).getId(); //set id
        empArr[i][1] = employees.get(i).getName(); //set name
        empArr[i][2] = employees.get(i).getJob(); //set job title
      }

    //set table model using this array
    employeestable.setModel(new javax.swing.table.DefaultTableModel(empArr,
        new String[] { "ID", "Name", "Job" }));


    //change table header color
    employeestable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
    {

      @Override
      public Component getTableCellRendererComponent(JTable table,
        Object value, boolean isSelected, boolean hasFocus, int row,
        int column)
      {

        JLabel label =
          (JLabel) super.getTableCellRendererComponent(table, value,
          isSelected, hasFocus, row, column);
        //chage header background
        label.setBackground(Color.decode("#0081D3"));
        //change font color
        label.setForeground(Color.white);

        return label;
        
      }
    });

    //label under tabel show employees count viewed in table
    no_of_rows.setText("Table result: "+
      Integer.toString(employeestable.getRowCount())+"  employees");
  }

  /** set user table model (used to show employee user account)
   * @param user object */
  private void setUserTable(UserDto user)
  {
    
    /*this (2D) array of objects for table model
     *the first D for rows, second for columns */
    Object[][] userArr = new Object[1][4];
    
    //set account info. in table
    userArr[0][0] = user.getId(); //set id
    userArr[0][1] = user.getUsername(); //set username
    userArr[0][2] = user.getEmail(); //set email
    userArr[0][3] = user.getRole().getId(); //set role

    //set table model using this array
    employeestable.setModel(new javax.swing.table.DefaultTableModel(userArr,
        new String[] { "ID", "User Name", "Email", "Role ID" }));

    //change table header color
    employeestable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
    {

      @Override
      public Component getTableCellRendererComponent(JTable table,
        Object value, boolean isSelected, boolean hasFocus, int row,
        int column)
      {

        JLabel label =
          (JLabel) super.getTableCellRendererComponent(table, value,
          isSelected, hasFocus, row, column);
        //chage header background
        label.setBackground(Color.decode("#0081D3"));
        //change font color
        label.setForeground(Color.white);

        return label;

      }
    });

    //label under tabel show users count viewed in table
    no_of_rows.setText("Table result: "+
      Integer.toString(employeestable.getRowCount())+"  user");

  }


  /**This method use to check user validity on this screen
   * if user has full access or viewonly access*/
  private void userAccess()
  {
    //set user screen name to "Employee" to make check on it
    user.setScreen_name("Employee");

    //define role screen bao
    RoleScreenBao roleScreenBao =
      new BaoFactory().createRoleScreenBao(user);

    //if user has viewonly access to this screen
    if(roleScreenBao.viewonly(user))
      {
        //hide all edits/export/import components
        EditPanel.setVisible(false);
        ImportButton.setVisible(false);
        ExportButton.setVisible(false);
      }
  }




  /**Update an existing employee*/
  private void update()
  {
    if(checkValidity()) //check if entered data is valid
      {
        try
          {
            {
              employee = new EmployeeDto(); //set new employee
              
              //set entered data to employee object
              employee.setId(Integer.parseInt(IdText.getText()));
              employee.setName(NameText.getText());
              employee.setJob(JobText.getText());
              
              if(EmployeePanel.bao.update(employee, user)) //try update it
                {

                  //if it success show message to tell user that
                  JOptionPane.showMessageDialog(null,
                    "Employee updated Successfully");
                  
                  //refresh table
                  setTableModel(bao.listAll());
                  employeestable.repaint();
                }
              //else show message to tell user that this object does't exist in database
              else
                {
                  JOptionPane.showMessageDialog(null,
                    "The Employee is not exist");
                }
            }

          }
        catch(Exception e)
          {
            e.printStackTrace();
          }
      }
  }


  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  private void initComponents()//GEN-BEGIN:initComponents
  {

    jScrollPane1 = new javax.swing.JScrollPane();
    employeestable = new javax.swing.JTable();
    SearchText = new javax.swing.JTextField();
    EditPanel = new javax.swing.JPanel();
    IdLabel = new javax.swing.JLabel();
    IdText = new javax.swing.JTextField();
    NameText = new javax.swing.JTextField();
    CodeLabel = new javax.swing.JLabel();
    NameLabel = new javax.swing.JLabel();
    JobText = new javax.swing.JTextField();
    jLabel15 = new javax.swing.JLabel();
    SavePanel = new javax.swing.JPanel();
    Save = new javax.swing.JButton();
    deletePanel = new javax.swing.JPanel();
    Delete = new javax.swing.JButton();
    clearPanel = new javax.swing.JPanel();
    clear = new javax.swing.JButton();
    no_of_rows = new java.awt.Label();
    jLabel8 = new javax.swing.JLabel();
    jSeparator2 = new javax.swing.JSeparator();
    viewUserButton = new javax.swing.JButton();
    searchPanel = new javax.swing.JPanel();
    search = new javax.swing.JButton();
    refreshPanel = new javax.swing.JPanel();
    Refresh = new javax.swing.JButton();
    ImportButton = new javax.swing.JButton();
    ExportButton = new javax.swing.JButton();

    setBackground(new java.awt.Color(245, 245, 245));
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    employeestable.setAutoCreateRowSorter(true);
    employeestable.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    employeestable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {

      },
      new String []
      {
        "ID", "Name", "Job"
      }
    )
    {
      Class[] types = new Class []
      {
        java.lang.Integer.class, java.lang.String.class, java.lang.String.class
      };
      boolean[] canEdit = new boolean []
      {
        false, false, false
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
    employeestable.setFillsViewportHeight(true);
    employeestable.setRowHeight(25);
    employeestable.setSelectionBackground(new java.awt.Color(0, 153, 204));
    employeestable.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        employeestableMouseClicked(evt);
      }
    });
    employeestable.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyPressed(java.awt.event.KeyEvent evt)
      {
        employeestableKeyPressed(evt);
      }
    });
    jScrollPane1.setViewportView(employeestable);
    if (employeestable.getColumnModel().getColumnCount() > 0)
    {
      employeestable.getColumnModel().getColumn(0).setHeaderValue("ID");
      employeestable.getColumnModel().getColumn(1).setHeaderValue("Name");
      employeestable.getColumnModel().getColumn(2).setHeaderValue("Job");
    }

    add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 720, 780));

    SearchText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    SearchText.setText("Enter text to search");
    SearchText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    SearchText.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        SearchTextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        SearchTextFocusLost(evt);
      }
    });
    SearchText.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        SearchTextActionPerformed(evt);
      }
    });
    add(SearchText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 620, 50));

    EditPanel.setBackground(new java.awt.Color(245, 245, 245));
    EditPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    IdLabel.setBackground(new java.awt.Color(0, 51, 204));
    IdLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    IdLabel.setForeground(new java.awt.Color(0, 51, 204));
    IdLabel.setText("ID");
    EditPanel.add(IdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 186, -1, 30));

    IdText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    IdText.setText("Enter Employee ID");
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
    EditPanel.add(IdText, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 210, 600, 60));

    NameText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    NameText.setText("Enter Employee Name");
    NameText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    NameText.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        NameTextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        NameTextFocusLost(evt);
      }
    });
    NameText.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        NameTextActionPerformed(evt);
      }
    });
    EditPanel.add(NameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 600, 60));

    CodeLabel.setBackground(new java.awt.Color(0, 51, 204));
    CodeLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    CodeLabel.setForeground(new java.awt.Color(0, 51, 204));
    CodeLabel.setText("Name");
    EditPanel.add(CodeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, 40));

    NameLabel.setBackground(new java.awt.Color(0, 51, 204));
    NameLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    NameLabel.setForeground(new java.awt.Color(0, 51, 204));
    NameLabel.setText("Job");
    EditPanel.add(NameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, 40));

    JobText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    JobText.setText("Enter Employee Job");
    JobText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    JobText.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        JobTextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        JobTextFocusLost(evt);
      }
    });
    EditPanel.add(JobText, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 600, 60));

    jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_user_group_man_woman_128px.png"))); // NOI18N
    EditPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, -1));

    SavePanel.setBackground(new java.awt.Color(0, 129, 211));

    Save.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    Save.setForeground(new java.awt.Color(255, 255, 255));
    Save.setText("Insert");
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

    javax.swing.GroupLayout SavePanelLayout = new javax.swing.GroupLayout(SavePanel);
    SavePanel.setLayout(SavePanelLayout);
    SavePanelLayout.setHorizontalGroup(
      SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Save, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
    );
    SavePanelLayout.setVerticalGroup(
      SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Save, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(SavePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, -1, 50));

    deletePanel.setBackground(new java.awt.Color(0, 129, 211));

    Delete.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    Delete.setForeground(new java.awt.Color(255, 255, 255));
    Delete.setText("Delete ");
    Delete.setBorderPainted(false);
    Delete.setContentAreaFilled(false);
    Delete.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        DeleteMouseMoved(evt);
      }
    });
    Delete.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        DeleteMouseExited(evt);
      }
    });
    Delete.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        DeleteActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout deletePanelLayout = new javax.swing.GroupLayout(deletePanel);
    deletePanel.setLayout(deletePanelLayout);
    deletePanelLayout.setHorizontalGroup(
      deletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Delete, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
    );
    deletePanelLayout.setVerticalGroup(
      deletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Delete, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(deletePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, -1, 50));

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
      .addComponent(clear, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
    );
    clearPanelLayout.setVerticalGroup(
      clearPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(clear, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(clearPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 550, -1, 50));

    add(EditPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 120, 660, 770));

    no_of_rows.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
    no_of_rows.setForeground(new java.awt.Color(135, 135, 135));
    no_of_rows.setText("no of rows");
    add(no_of_rows, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 880, 180, 30));

    jLabel8.setBackground(new java.awt.Color(231, 78, 123));
    jLabel8.setFont(new java.awt.Font("VIP Rawy Regular", 0, 36)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(231, 78, 123));
    jLabel8.setText("Employee");
    add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 50, 240, 80));
    add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 110, 620, 20));

    viewUserButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    viewUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_user_male_circle_60px.png"))); // NOI18N
    viewUserButton.setToolTipText("Employee's account");
    viewUserButton.setBorderPainted(false);
    viewUserButton.setContentAreaFilled(false);
    viewUserButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        viewUserButtonMouseMoved(evt);
      }
    });
    viewUserButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        viewUserButtonMouseExited(evt);
      }
    });
    viewUserButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        viewUserButtonActionPerformed(evt);
      }
    });
    add(viewUserButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 890, 80, 50));

    searchPanel.setBackground(new java.awt.Color(0, 129, 211));

    search.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    search.setForeground(new java.awt.Color(255, 255, 255));
    search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_search_26px.png"))); // NOI18N
    search.setBorderPainted(false);
    search.setContentAreaFilled(false);
    search.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        searchMouseMoved(evt);
      }
    });
    search.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        searchMouseExited(evt);
      }
    });
    search.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        searchActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
    searchPanel.setLayout(searchPanelLayout);
    searchPanelLayout.setHorizontalGroup(
      searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(search, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
    );
    searchPanelLayout.setVerticalGroup(
      searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    add(searchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, 90, 50));

    refreshPanel.setBackground(new java.awt.Color(0, 129, 211));

    Refresh.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    Refresh.setForeground(new java.awt.Color(255, 255, 255));
    Refresh.setText("Refresh ");
    Refresh.setBorderPainted(false);
    Refresh.setContentAreaFilled(false);
    Refresh.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        RefreshMouseMoved(evt);
      }
    });
    Refresh.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        RefreshMouseExited(evt);
      }
    });
    Refresh.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        RefreshActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout refreshPanelLayout = new javax.swing.GroupLayout(refreshPanel);
    refreshPanel.setLayout(refreshPanelLayout);
    refreshPanelLayout.setHorizontalGroup(
      refreshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Refresh, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
    );
    refreshPanelLayout.setVerticalGroup(
      refreshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, refreshPanelLayout.createSequentialGroup()
        .addComponent(Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    add(refreshPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 890, 130, 50));

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
    add(ImportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 880, 70, 70));

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
    add(ExportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 880, 80, 70));
  }//GEN-END:initComponents

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed

    try
      {
        //Show confirm message
        int reply =
          JOptionPane.showConfirmDialog(null,
            "Are you sure to delete this employee?\n"+
            "All things depend on it will be deleted!", "Warning",
            JOptionPane.YES_NO_OPTION);

        //delete object if user choose yes
        if(reply==JOptionPane.YES_OPTION)
          {

            employee = new EmployeeDto(); //set new employee

            //get employee id from text box and set it to the dto object
            employee.setId(Integer.parseInt(IdText.getText()));
            
            
            if(bao.delete(employee)) //if deletion ended successfully
              {
                //show message to tell user this
                JOptionPane.showMessageDialog(null,
                  "Employee Deleted Successfully.", "Done", 1);
                
                //refresh table
                setTableModel(bao.listAll());
                employeestable.repaint();

                IdText.setEnabled(true); //set id text enable
                Save.setText("Insert"); //set save button text to "Insert"

              }

          }
        //else show message to tell user that this object does't exist in database
        else
          {
            JOptionPane.showMessageDialog(null, "Employee does'nt exist! ",
              "Error", 0);
          }


      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    }//GEN-LAST:event_DeleteActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed

    try
      {
        List<EmployeeDto> employees = null; //for search result
      
        // Check if the Search field is empty
        if(SearchText.getText().equalsIgnoreCase("Enter text to search"))
          {
            //Show message tell user that Search Field is empty
            JOptionPane.showMessageDialog(null, "Search field is empty",
              "Invalid Input!", JOptionPane.ERROR_MESSAGE);
            //throw exception
            throw new IllegalArgumentException("Search field is empty");
          }
      
        //get the input search text into search attribute in employee dto object
        employee.setSearch(SearchText.getText());

        // get the result set from bao
        employees = EmployeePanel.bao.searchFor(employee);

        //if there is a result set show them in the department table
        if(employees!=null)
          {

            setTableModel(employees);
            employeestable.repaint();
          }
        else
          {
            //if there is no result set show message to tell user that
            JOptionPane.showMessageDialog(null,
              "There is no search result for: "+SearchText.getText(),
              "Invalid search", 1);
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    }//GEN-LAST:event_searchActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed

    //if the set text update
    if(Save.getText().equalsIgnoreCase("Update"))
      update(); //use update method

    else //try insert this course
      {
        try
          {
            if(checkValidity()) //check if entered data is valid
              {
        
                employee = new EmployeeDto(); //set new employee
                
                //set entred data to employee object
                employee.setId(Integer.parseInt(IdText.getText())); 
                employee.setName((NameText.getText()));
                employee.setJob(JobText.getText());

                if(bao.add(employee, user)) //if insert this employee success
                  {
                    // show message to tell user that
                    JOptionPane.showMessageDialog(null,
                      "Employee Inserted Successfully", "Done", 1);

                    //refresh table
                    setTableModel(bao.listAll());
                    employeestable.repaint();

                    //set save button text to "Update"
                    Save.setText("Update");

                    //set id text disable (id can't be edited after insert)
                    IdText.setEnabled(false);
                    
                  }
                else // if it failed it mean this employee is already exit
                  {
                    /*so show message to ask user if he want
                    * to update this employee or not*/
                    int reply =
                      JOptionPane.showConfirmDialog(null,
                        "This Employee is already exist!\n\n"+
                        "Do you want update it?", "Warning",
                        JOptionPane.YES_NO_OPTION);

                    //update object if user choose yes
                    if(reply==JOptionPane.YES_OPTION)
                      {
                        update(); //use update method
                        Save.setText("Update"); //set save button text to "Update"
                        IdText.setEnabled(false); //set id text disable
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
    
    private void IdTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdTextActionPerformed
    // TODO add your handling code here:                                                                    
    }//GEN-LAST:event_IdTextActionPerformed

    private void NameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameTextActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_NameTextActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
    // TODO add your handling code here:
    try
      {
        //refresh table
        setTableModel(bao.listAll());
        employeestable.repaint();

        IdText.setEnabled(true); //enable id text box
        Save.setText("Insert"); //set save button text to "insert"

        //set default text for text boxes
        IdText.setText("Enter Employee ID");
        NameText.setText("Enter Employee Name");
        JobText.setText("Enter Employee Job");
      
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    }//GEN-LAST:event_RefreshActionPerformed

    private void SearchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchTextActionPerformed
    try
      {
        List<EmployeeDto> employees = null; //for search result

        // Check if the Search field is empty
        if(SearchText.getText().equalsIgnoreCase(""))
          {
            //Show message tell user that Search Field is empty
            JOptionPane.showMessageDialog(null, "Search field is empty",
              "Invalid Input!", JOptionPane.ERROR_MESSAGE);
            //throw exception
            throw new IllegalArgumentException("Search field is empty");
          }

        //get the input search text into search attribute in employee dto object
        employee.setSearch(SearchText.getText());

        // get the result set from bao
        employees = EmployeePanel.bao.searchFor(employee);

        //if there is a result set show them in the department table
        if(employees!=null)
          {

            setTableModel(employees);
            employeestable.repaint();
          }
        else
          {
            //if there is no result set show message to tell user that
            JOptionPane.showMessageDialog(null,
              "There is no search result for: "+SearchText.getText(),
              "Invalid search", 1);
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }                                                      
    }//GEN-LAST:event_SearchTextActionPerformed

    private void IdTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusGained
   
    //if found text is the default text
    if(IdText.getText().equalsIgnoreCase("Enter Employee ID"))
      {
        IdText.setText(""); //remove it
      }
    
    //set new border to change border color
    IdText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_IdTextFocusGained

    private void IdTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusLost
   
    //if there is no text
    if(IdText.getText().trim().equalsIgnoreCase(""))
      {
        IdText.setText("Enter Employee ID"); //set the default one
      }

    //set new border to reset border color
    IdText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_IdTextFocusLost

    private void NameTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameTextFocusGained
   
    //if found text is the default text
    if(NameText.getText().equalsIgnoreCase("Enter Employee Name"))
      { 
        NameText.setText(""); //remove it
      }
    
    //set new border to change border color
    NameText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_NameTextFocusGained

    private void NameTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameTextFocusLost
    
    //if there is no text
    if(NameText.getText().trim().equalsIgnoreCase(""))
      {
        NameText.setText("Enter Employee Name"); //set the default one
      }

    //set new border to reset border color
    NameText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_NameTextFocusLost

    private void JobTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_JobTextFocusGained
    
    //if found text is the default text
    if(JobText.getText().equalsIgnoreCase("Enter Employee Job"))
      {
        JobText.setText(""); //remove it
      }

    //set new border to change border color
    JobText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_JobTextFocusGained

    private void JobTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_JobTextFocusLost
    
    //if there is no text
    if(JobText.getText().trim().equalsIgnoreCase(""))
      {
        JobText.setText("Enter Employee Job"); //set the default one
      }

    //set new border to reset border color
    JobText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_JobTextFocusLost

    private void SearchTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusGained
    
    //if found text is the default text
    if(SearchText.getText().equalsIgnoreCase("Enter text to search"))
      {
        SearchText.setText(""); //remove it
      }

    //set new border to change border color
    SearchText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_SearchTextFocusGained

    private void SearchTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusLost
    
    //if there is no text
    if(SearchText.getText().trim().equalsIgnoreCase(""))
      {
        SearchText.setText("Enter text to search"); //set the default one
      }

    //set new border to reset border color
    SearchText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_SearchTextFocusLost

    private void viewUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewUserButtonActionPerformed

    try
      {
        //identify object of user dto
        UserDto user = null;
      
        int row = employeestable.getSelectedRow(); //get selected employee
      
        // get its id from table
        employee.setId(Integer.parseInt(employeestable.getModel().getValueAt(row,
          0).toString()));

        // get employee account 
        user = bao.viewUser(employee);
      
        if(user!=null)  //if there is an account 
          {
            //show it in the table 
            setUserTable(user);
            employeestable.repaint();
          }
        else
          JOptionPane.showMessageDialog(null,
            "There is no account for this empolyee");       
      }

    catch(java.lang.ArrayIndexOutOfBoundsException e)
      {

        //show message if no employee selected
        JOptionPane.showMessageDialog(null,
          "Please select employee from table to show his/her account");
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    }//GEN-LAST:event_viewUserButtonActionPerformed

    private void employeestableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeestableMouseClicked

    IdText.setEnabled(false); //set id text disable
    Save.setText("Update"); //set save button text "Update"

    int row = employeestable.getSelectedRow(); //get selected row

    //set employee's attributes of selected row to text boxes
    IdText.setText(employeestable.getValueAt(row, 0).toString());
    NameText.setText(employeestable.getValueAt(row, 1).toString());
    JobText.setText(employeestable.getValueAt(row, 2).toString());

    }//GEN-LAST:event_employeestableMouseClicked

    private void employeestableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employeestableKeyPressed
    
    //get number of selected row in table
    int row = employeestable.getSelectedRow();

    //Because "employeestable.getSelectedRow()" give the previous selected row
    if(evt.getExtendedKeyCode()==KeyEvent.VK_UP)
      row--; //for up key decrement
    else if(evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
      row++; // for down key increment

    if(evt.getExtendedKeyCode()==KeyEvent.VK_UP||
      evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
      {

        IdText.setEnabled(false); //set id text disable
        Save.setText("Update"); //set save button text "Update"

        //set employee's attributes of selected row to text boxes
        IdText.setText(employeestable.getValueAt(row, 0).toString());
        NameText.setText(employeestable.getValueAt(row, 1).toString());
        JobText.setText(employeestable.getValueAt(row, 2).toString());
      }

    //delete selected object when press delete
    if(evt.getExtendedKeyCode()==KeyEvent.VK_DELETE)
      {

        try
          {
            //For one selected row in table
            if(employeestable.getSelectedRowCount()==1)
              { 
              //Show confirm message
                int reply =
                  JOptionPane.showConfirmDialog(null,
                    "Are you sure to delete this employee?\n"+
                    "All things depend on it will be deleted!", "Warning",
                    JOptionPane.YES_NO_OPTION);

                //delete object if user choose yes
                if(reply==JOptionPane.YES_OPTION)
                  {
                    //get selected employee id from table
                    int id =
                      Integer.parseInt(employeestable.getValueAt(employeestable.getSelectedRow(),
                          0).toString());
                    EmployeeDto employee = new EmployeeDto();
                    employee.setId(id); //set it to employee object

                    //delete it using bao delete method
                    if(bao.delete(employee)) 
                      {
                        //if it deleted sucessfilly show message to tell user that
                        JOptionPane.showMessageDialog(null,
                          "Staff Deleted Successfully", "Done", 1);
                        
                        //refresh table
                        setTableModel(bao.listAll());
                        employeestable.repaint();

                        IdText.setEnabled(true); //set id text enable
                        Save.setText("Insert"); //set save button text to "Insert"
                      }

                    else
                      //if bao method return false (employee not be deleted)
                      JOptionPane.showMessageDialog(null,
                        "Can't delete object", "Error",
                        JOptionPane.ERROR_MESSAGE);
                  }

              }
            else if(employeestable.getSelectedRowCount()==0)
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
    }//GEN-LAST:event_employeestableKeyPressed

  private void clearActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_clearActionPerformed
  {//GEN-HEADEREND:event_clearActionPerformed
    // TODO add your handling code here:

    IdText.setEnabled(true); //enable id text box
    Save.setText("Insert"); //set save button text to "insert"

    //set default text for text boxes
    IdText.setText("Enter Employee ID");
    NameText.setText("Enter Employee Name");
    JobText.setText("Enter Employee Job");
    
  }//GEN-LAST:event_clearActionPerformed

  private void viewUserButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_viewUserButtonMouseExited
  {//GEN-HEADEREND:event_viewUserButtonMouseExited
    
    //reset button color (image) icon
    viewUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_user_male_circle_60px.png"))); // NOI18N

  }//GEN-LAST:event_viewUserButtonMouseExited

  private void viewUserButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_viewUserButtonMouseMoved
  {//GEN-HEADEREND:event_viewUserButtonMouseMoved

    // change color icon by change image to one has diff. color
    viewUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_user_male_circle_60px_1.png"))); // NOI18N

  }//GEN-LAST:event_viewUserButtonMouseMoved

  private void RefreshMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseExited
  {//GEN-HEADEREND:event_RefreshMouseExited

    // reset refresh panel color
    refreshPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_RefreshMouseExited

  private void clearMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseExited
  {//GEN-HEADEREND:event_clearMouseExited

    // reset clear panel color
    clearPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_clearMouseExited

  private void DeleteMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteMouseExited
  {//GEN-HEADEREND:event_DeleteMouseExited

    // reset delete panel color
    deletePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_DeleteMouseExited

  private void SaveMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseExited
  {//GEN-HEADEREND:event_SaveMouseExited

    // reset save panel color
    SavePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_SaveMouseExited

  private void searchMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseExited
  {//GEN-HEADEREND:event_searchMouseExited

    // reset search panel color
    searchPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_searchMouseExited

  private void searchMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseMoved
  {//GEN-HEADEREND:event_searchMouseMoved

    // change search panel color
    searchPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_searchMouseMoved

  private void SaveMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseMoved
  {//GEN-HEADEREND:event_SaveMouseMoved

    // change save panel color
    SavePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_SaveMouseMoved

  private void DeleteMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteMouseMoved
  {//GEN-HEADEREND:event_DeleteMouseMoved

    // change delete panel color
    deletePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_DeleteMouseMoved

  private void clearMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseMoved
  {//GEN-HEADEREND:event_clearMouseMoved

    // change clear panel color
    clearPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_clearMouseMoved

  private void RefreshMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseMoved
  {//GEN-HEADEREND:event_RefreshMouseMoved

    // change refresh panel color
    refreshPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_RefreshMouseMoved

  private void ImportButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ImportButtonMouseMoved
  {//GEN-HEADEREND:event_ImportButtonMouseMoved

    // change color icon by change image to one has diff. color
    ImportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_xls_import_48px_1.png"))); // NOI18N
  }//GEN-LAST:event_ImportButtonMouseMoved

  private void ImportButtonMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ImportButtonMouseDragged
  {//GEN-HEADEREND:event_ImportButtonMouseDragged
    // TODO add your handling code here:
  }//GEN-LAST:event_ImportButtonMouseDragged

  private void ImportButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ImportButtonMouseExited
  {//GEN-HEADEREND:event_ImportButtonMouseExited

    //reset button color (image) icon
    ImportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_xls_import_48px.png"))); // NOI18N
  }//GEN-LAST:event_ImportButtonMouseExited

  private void ImportButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ImportButtonActionPerformed
  {//GEN-HEADEREND:event_ImportButtonActionPerformed

    //Define employee Excel object
    EmployeeExcel read = new EmployeeExcel(user);

    //define new file chooser
    JFileChooser fileChooser = new JFileChooser();
    //set its directory
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    //set file selection mode
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    //filter files to show only excel files
    FileFilter filter =
      new FileNameExtensionFilter("Excel File", "xls", "xlsx", "XLS",
        "XLSX");
    //add this filter to file chooser
    fileChooser.addChoosableFileFilter(filter);
    //disable show all files option
    fileChooser.setAcceptAllFileFilterUsed(false);
    //show this file chooser to user and get user option
    int result = fileChooser.showOpenDialog(null);

    if(result==JFileChooser.APPROVE_OPTION) //if user choose APPROVE option
      {

        //create new file to get file selected by user
        File file = fileChooser.getSelectedFile();
        String filePath = file.getAbsolutePath(); //get file path
        try
          {
            //get import employees from read_data method of excel
            List<EmployeeDto> import_employees = read.read_data(filePath);

            if(import_employees!=null)
              {

                //insert reading employee to database
                for(int i = 0; i<import_employees.size(); i++)
                  {
                    bao.add(import_employees.get(i), user);
                  }

                // show result set in table
                setTableModel(import_employees);
                employeestable.repaint();

              }

          }

        catch(Exception e)
          {
            /*if any exception occur
             * print this exception
             * show message to ask user check the input file*/
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
              "Error in reading data from excel \nPlease check your file and try again",
              "Error", JOptionPane.ERROR_MESSAGE);
          }

      }

  }//GEN-LAST:event_ImportButtonActionPerformed

  private void ExportButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ExportButtonMouseMoved
  {//GEN-HEADEREND:event_ExportButtonMouseMoved

    // change color icon by change image to one has diff. color
    ExportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_xls_export_48px_1.png"))); // NOI18N
  }//GEN-LAST:event_ExportButtonMouseMoved

  private void ExportButtonMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ExportButtonMouseDragged
  {//GEN-HEADEREND:event_ExportButtonMouseDragged
    // TODO add your handling code here:
  }//GEN-LAST:event_ExportButtonMouseDragged

  private void ExportButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ExportButtonMouseExited
  {//GEN-HEADEREND:event_ExportButtonMouseExited
    //reset button color (image) icon
    ExportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_xls_export_48px.png"))); // NOI18N
  }//GEN-LAST:event_ExportButtonMouseExited

  private void ExportButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ExportButtonActionPerformed
  {//GEN-HEADEREND:event_ExportButtonActionPerformed

    //define employee excel object
    EmployeeExcel excel = new EmployeeExcel(user);
    List<EmployeeDto> employees = null; //to set export data in it

    try
    {
      //get all buildings in table
      for(int i=0; i<employeestable.getRowCount() ; i++)
      {
        if(employees==null)
        employees = new ArrayList<>();
        
        //get each employee info. from table
        employee = new EmployeeDto();
        employee.setId(Integer.parseInt(employeestable.getValueAt(i, 0).toString()));
        employee.setName(employeestable.getValueAt(i, 1).toString());
        employee.setJob(employeestable.getValueAt(i, 2).toString());

        employees.add(employee); //add it to the list

        employee=null;

      }

        //create new file chooser
        JFileChooser fileChooser = new JFileChooser();
        //set current directory
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //set selection mode to directory only
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //set filename to "Employees" as a default name
        String filename = "Employees.xlsx";

        //if this data result from search
        if(!SearchText.getText().equalsIgnoreCase("Enter text to search"))
          {
            //get the search text and change filename to inculde it
            filename = SearchText.getText()+"-employees.xlsx";
          }
      
        //show file dialog
        int result = fileChooser.showSaveDialog(null);

        //user selected file
        if(result==JFileChooser.APPROVE_OPTION)
          {
            //get path
            String filePath =
              fileChooser.getSelectedFile().getAbsolutePath();

            try
              {
                //write excel file
                excel.write(filename, filePath, employees);
              
                //if it success show this message
                JOptionPane.showMessageDialog(null,
                  "Employees data export ended successfully!", "Done", 1);
              }
            catch(Exception e)
              {
                e.printStackTrace();
                //if any exception occur (failed to write file)
                JOptionPane.showMessageDialog(null, "Failed to write file!",
                  "Error", 0);
              }

          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

  }//GEN-LAST:event_ExportButtonActionPerformed


  /**check Validity of entered data
   * @return: True if data is entered correctly (as expected)
   * False when one or more of them entered uncorrectly
   * */
  private boolean checkValidity()
  {

    try
      {
        /** check for empty entered data **/
        //for default id text
        if(IdText.getText().equalsIgnoreCase("Enter Employee ID"))
          {
            //ask user enter id
            JOptionPane.showMessageDialog(null, "Please, enter employee id",
              "Invaid Input", JOptionPane.ERROR_MESSAGE);
            
            //throw empty id exception
            throw new IllegalArgumentException("id text is empry");
          }
        //for default job text
        else if(JobText.getText().equalsIgnoreCase("Enter Employee Job"))

          {
            //ask user enter job title
            JOptionPane.showMessageDialog(null,
              "Please, Enter Job title for this employee", "Invaid Input",
              JOptionPane.ERROR_MESSAGE);
            //throw empty job exception
            throw new IllegalArgumentException("job text is empty");
          }
        //for default name text
        else if(NameText.getText().equalsIgnoreCase("Enter Employee Name"))
          {
          //ask user enter name
            JOptionPane.showMessageDialog(null,
              "Please, Enter Employee Name", "Invaid Input",
              JOptionPane.ERROR_MESSAGE);

            //throw empty name exception
            throw new IllegalArgumentException("name text is empty");

          }


        /** Check validity of id **/
        try
          {
            int id = Integer.parseInt(IdText.getText());
            //Check for the entered id is a positive number
            if(id<1)
              {
                //ask user to enter positive number
                JOptionPane.showMessageDialog(null,
                  "Invalid Id \n\n(ID is only Positive Numbers)",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);

                //throw this exception
                throw new IllegalArgumentException("ID is only Positive Numbers");
              }
          }
        catch(java.lang.NumberFormatException e)
          {
            e.printStackTrace();
            //if id is not numerical ask user to enter number
            JOptionPane.showMessageDialog(null,
              "Please enter number for employee ID", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            
            return false; //data is invaid
          }

        /** check validity of name **/

        //name can't start with number or symbol
        if(!Character.isAlphabetic(NameText.getText().charAt(0)))
          {
            
            JOptionPane.showMessageDialog(null,
              "Invalid Name format\n\n(Name can't start with number or symbol)",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            
            throw new IllegalArgumentException("Name can't start with number or symbol");
          }

        //check for all chars
        for(int i = 1; i<NameText.getText().length(); i++)
          {

            //code contain only letters/numbers and "_","."," ","-"
            if(!Character.isLetterOrDigit(NameText.getText().charAt(i))&&
              NameText.getText().charAt(i)!='_'&&
              NameText.getText().charAt(i)!='-'&&
              NameText.getText().charAt(i)!=' '&&
              NameText.getText().charAt(i)!='.')
              {
                JOptionPane.showMessageDialog(null,
                  "Invalid Name format\nName can't contain stranger symbols",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                
                throw new IllegalArgumentException("Name can't contain stranger symbols");

              }
          }


        /** check validity of job title **/

        //name can't start with number or symbol
        if(!Character.isAlphabetic(JobText.getText().charAt(0)))
          {
            JOptionPane.showMessageDialog(null,
              "Invalid Job name format\n\n(Job name can't start with number or symbol)",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Job name can't start with number or symbol");

          }

        //check for all chars
        for(int i = 1; i<JobText.getText().length(); i++)
          {

            //code contain only letters/numbers and "_","."," ","-"
            if(!Character.isLetterOrDigit(JobText.getText().charAt(i))&&
              JobText.getText().charAt(i)!='_'&&
              JobText.getText().charAt(i)!=' '&&
              JobText.getText().charAt(i)!='-'&&
              JobText.getText().charAt(i)!='.')
              {
                JOptionPane.showMessageDialog(null,
                  "Invalid Job name format\n\n(Job name can only be a sequence of Unicode letters and digits separated by underscore)",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                
                throw new IllegalArgumentException("Job name can't contain stranger symbols");

              }
          }


        return true; //all data correct


      }
    // catch the illagal thrown exceptions
    catch(IllegalArgumentException e1)
      {
        e1.printStackTrace();
        return false; //data is invalid
      }

    catch(Exception e)
      {
        e.printStackTrace();
        //if there is any other non expecting error
        JOptionPane.showMessageDialog(null,
          "Some Thing went wrong!\n\nPlease check your entered data. ",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
        return false;
      }


  }

    
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel CodeLabel;
  private javax.swing.JButton Delete;
  private javax.swing.JPanel EditPanel;
  private javax.swing.JButton ExportButton;
  private javax.swing.JLabel IdLabel;
  private javax.swing.JTextField IdText;
  private javax.swing.JButton ImportButton;
  private javax.swing.JTextField JobText;
  private javax.swing.JLabel NameLabel;
  private javax.swing.JTextField NameText;
  private javax.swing.JButton Refresh;
  private javax.swing.JButton Save;
  private javax.swing.JPanel SavePanel;
  private javax.swing.JTextField SearchText;
  private javax.swing.JButton clear;
  private javax.swing.JPanel clearPanel;
  private javax.swing.JPanel deletePanel;
  private javax.swing.JTable employeestable;
  private javax.swing.JLabel jLabel15;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSeparator jSeparator2;
  private java.awt.Label no_of_rows;
  private javax.swing.JPanel refreshPanel;
  private javax.swing.JButton search;
  private javax.swing.JPanel searchPanel;
  private javax.swing.JButton viewUserButton;
  // End of variables declaration//GEN-END:variables
    
}

