
package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.DepartmentBao;
import com.fym.lta.BAO.StaffBao;
import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.RoleScreenDao;
import com.fym.lta.DTO.CourseDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.UserDto;
import com.fym.lta.Excel.StaffExcel;

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
public class StaffPanel extends javax.swing.JPanel
{
  @SuppressWarnings("compatibility:1571523892438060585")
  private static final long serialVersionUID = 1L;

  private static StaffBao business;
  private static UserDto user_login = null;

  public StaffPanel(UserDto user_account)
  {
    try
      {


        user_login = user_account;
        business = new BaoFactory().createStaffBao(user_login);

        initComponents();
        viewonly(user_login);
      
        setTableModel(business.viewAll());
        defaultdata();

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }

  /**For view only user access */
  private void viewonly(UserDto u)
  {

    u.setScreen_name("Staff member");

    RoleScreenDao dao = new DaoFactory().createRoleScreenDao(user_login);
    if(dao.viewonly(u))
      {
        EditPanel.setVisible(false);
        System.out.println(" \n done panel");
      }
  }


  private void setTableModel(List<StaffDto> staff)
  {
    int size = 0;
    if(staff!=null&&!staff.isEmpty())
      size = staff.size();

    Object[][] staffArr = new Object[size][4];
    for(int i = 0; i<size; i++)
      {
        staffArr[i][0] = staff.get(i).getId();
        staffArr[i][1] = staff.get(i).getName();
        staffArr[i][2] = staff.get(i).getPosition();
        staffArr[i][3] = staff.get(i).getDepartment().getName();
      }
    staffTable.setModel(new javax.swing.table.DefaultTableModel(staffArr,
        new String[] { "ID", "Name", "position", "Department Name" }));

    //change header color
    staffTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
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
      Integer.toString(staffTable.getRowCount())+"  Staff member");


  }

  /**set default data to edit space*/
  private void defaultdata()
  {

    IdText.setEnabled(true); //make id text enable
    IdText.setText("Enter staff ID");
    nametext.setText("Enter staff Name");
    Save.setText("Save");

    positionComboBox.setSelectedIndex(-1); //select nothing

    //set the comboBox items by names of departments
    try
      {
        DepartmentBao depart_bao =
          new BaoFactory().createDepartmentBao(user_login);
        List<DepartmentDto> dept_list = depart_bao.viewAll();
        depComboBox.removeAllItems();
        if(dept_list!=null&&!dept_list.isEmpty())
          for(int i = 0; i<dept_list.size(); i++)
            {
              depComboBox.addItem(dept_list.get(i).getName());
            }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

    depComboBox.setSelectedIndex(-1); //select nothing
  }

  private void setNewTable(UserDto user)
  {
    //int size = 0;
    //if(user != null /* && !user.isEmpty()*/ )
    //  size = user.size();

    Object[][] userArr = new Object[1][5];
    //for(int i =0;i<size;i++){
    userArr[0][0] = user.getId();
    userArr[0][1] = user.getUsername();
    userArr[0][2] = user.getEmail();
    userArr[0][3] = user.getRole().getName();
    userArr[0][4] = user.getPassword();
    //}
    staffTable.setModel(new javax.swing.table.DefaultTableModel(userArr,
        new String[] { "ID", "User Name", "Email", "Role", "Password" }));
  }

  private void setCourseTable(List<CourseDto> c)
  {
    int size = 0;
    if(c!=null&&!c.isEmpty())
      size = c.size();

    Object[][] courseArr = new Object[size][3];
    for(int i = 0; i<size; i++)
      {
        courseArr[i][0] = c.get(i).getId();
        courseArr[i][1] = c.get(i).getCode();
        courseArr[i][2] = c.get(i).getName();
      }
    staffTable.setModel(new javax.swing.table.DefaultTableModel(courseArr,
        new String[] { "Corse ID", "Course Code", "Course Name" }));
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
    staffTable = new javax.swing.JTable();
    SearchText = new javax.swing.JTextField();
    no_of_rows = new java.awt.Label();
    EditPanel = new javax.swing.JPanel();
    IdText = new javax.swing.JTextField();
    code = new javax.swing.JLabel();
    name_ = new javax.swing.JLabel();
    nametext = new javax.swing.JTextField();
    no_of_hours = new javax.swing.JLabel();
    positionComboBox = new javax.swing.JComboBox<>();
    id = new javax.swing.JLabel();
    depComboBox = new javax.swing.JComboBox<>();
    savePanel = new javax.swing.JPanel();
    Save = new javax.swing.JButton();
    deletePanel = new javax.swing.JPanel();
    Delete = new javax.swing.JButton();
    ClearPanel = new javax.swing.JPanel();
    clear = new javax.swing.JButton();
    jLabel6 = new javax.swing.JLabel();
    RefreshPanel = new javax.swing.JPanel();
    Refresh = new javax.swing.JButton();
    searchPanel = new javax.swing.JPanel();
    search = new javax.swing.JButton();
    jLabel8 = new javax.swing.JLabel();
    jSeparator2 = new javax.swing.JSeparator();
    jPanel1 = new javax.swing.JPanel();
    viewCourseButton = new javax.swing.JButton();
    viewUserButton = new javax.swing.JButton();
    ExportButton = new javax.swing.JButton();
    ImportButton = new javax.swing.JButton();

    setBackground(new java.awt.Color(245, 245, 245));
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    staffTable.setAutoCreateRowSorter(true);
    staffTable.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    staffTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {

      },
      new String []
      {
        "ID", "Name", "Position", "Department Name"
      }
    )
    {
      Class[] types = new Class []
      {
        java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
    staffTable.setFillsViewportHeight(true);
    staffTable.setRowHeight(25);
    staffTable.setSelectionBackground(new java.awt.Color(0, 153, 204));
    staffTable.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        staffTableMouseClicked(evt);
      }
      public void mouseEntered(java.awt.event.MouseEvent evt)
      {
        staffTableMouseEntered(evt);
      }
    });
    staffTable.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyPressed(java.awt.event.KeyEvent evt)
      {
        staffTableKeyPressed(evt);
      }
    });
    jScrollPane1.setViewportView(staffTable);
    if (staffTable.getColumnModel().getColumnCount() > 0)
    {
      staffTable.getColumnModel().getColumn(0).setHeaderValue("ID");
      staffTable.getColumnModel().getColumn(1).setHeaderValue("Name");
      staffTable.getColumnModel().getColumn(2).setHeaderValue("Position");
      staffTable.getColumnModel().getColumn(3).setHeaderValue("Department Name");
    }

    add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 98, 740, 780));

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
    add(SearchText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 640, 50));

    no_of_rows.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
    no_of_rows.setForeground(new java.awt.Color(135, 135, 135));
    no_of_rows.setText("no of rows");
    add(no_of_rows, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 880, 230, 20));

    EditPanel.setBackground(new java.awt.Color(245, 245, 245));
    EditPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    IdText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    IdText.setText("Enter staff ID");
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
    EditPanel.add(IdText, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 580, 60));

    code.setBackground(new java.awt.Color(0, 51, 204));
    code.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    code.setForeground(new java.awt.Color(0, 51, 204));
    code.setText("ID");
    EditPanel.add(code, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 53, 40));

    name_.setBackground(new java.awt.Color(0, 51, 204));
    name_.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    name_.setForeground(new java.awt.Color(0, 51, 204));
    name_.setText("Name");
    EditPanel.add(name_, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, 40));

    nametext.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    nametext.setText("Enter staff Name");
    nametext.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    nametext.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        nametextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        nametextFocusLost(evt);
      }
    });
    nametext.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        nametextActionPerformed(evt);
      }
    });
    EditPanel.add(nametext, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 580, 60));

    no_of_hours.setBackground(new java.awt.Color(0, 51, 204));
    no_of_hours.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    no_of_hours.setForeground(new java.awt.Color(0, 51, 204));
    no_of_hours.setText("Posittion");
    EditPanel.add(no_of_hours, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, 40));

    positionComboBox.setBackground(new java.awt.Color(255, 255, 255));
    positionComboBox.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    positionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Professor ", "Associate Professor", "Assistant Professor", "Assistant lecturer ", "Demonstrator" }));
    EditPanel.add(positionComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 580, 50));

    id.setBackground(new java.awt.Color(0, 51, 204));
    id.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    id.setForeground(new java.awt.Color(0, 51, 204));
    id.setText("Department ");
    EditPanel.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 130, 50));

    depComboBox.setBackground(new java.awt.Color(255, 255, 255));
    depComboBox.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    depComboBox.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        depComboBoxFocusGained(evt);
      }
    });
    depComboBox.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        depComboBoxActionPerformed(evt);
      }
    });
    EditPanel.add(depComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 580, 50));

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
      .addComponent(Save, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
    );
    savePanelLayout.setVerticalGroup(
      savePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Save, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(savePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 90, 50));

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
      .addComponent(Delete, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
    );
    deletePanelLayout.setVerticalGroup(
      deletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Delete, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(deletePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 530, 90, 50));

    ClearPanel.setBackground(new java.awt.Color(0, 129, 211));

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

    javax.swing.GroupLayout ClearPanelLayout = new javax.swing.GroupLayout(ClearPanel);
    ClearPanel.setLayout(ClearPanelLayout);
    ClearPanelLayout.setHorizontalGroup(
      ClearPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(clear, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
    );
    ClearPanelLayout.setVerticalGroup(
      ClearPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(clear, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(ClearPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 530, 90, 50));

    jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_org_unit_128px.png"))); // NOI18N
    EditPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, -1, -1));

    add(EditPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 140, 627, 760));

    RefreshPanel.setBackground(new java.awt.Color(0, 129, 211));

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

    javax.swing.GroupLayout RefreshPanelLayout = new javax.swing.GroupLayout(RefreshPanel);
    RefreshPanel.setLayout(RefreshPanelLayout);
    RefreshPanelLayout.setHorizontalGroup(
      RefreshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Refresh, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
    );
    RefreshPanelLayout.setVerticalGroup(
      RefreshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Refresh, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    add(RefreshPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 890, 120, 50));

    searchPanel.setBackground(new java.awt.Color(0, 129, 211));

    search.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
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

    add(searchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 30, 90, 50));

    jLabel8.setBackground(new java.awt.Color(231, 78, 123));
    jLabel8.setFont(new java.awt.Font("VIP Rawy Regular", 0, 36)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(231, 78, 123));
    jLabel8.setText("Staff");
    add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 50, 240, 80));
    add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 110, 620, 20));

    jPanel1.setBackground(new java.awt.Color(245, 245, 245));
    jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    viewCourseButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    viewCourseButton.setForeground(new java.awt.Color(255, 255, 255));
    viewCourseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_courses_60px_1.png"))); // NOI18N
    viewCourseButton.setToolTipText("Staff member's courses");
    viewCourseButton.setBorderPainted(false);
    viewCourseButton.setContentAreaFilled(false);
    viewCourseButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        viewCourseButtonMouseMoved(evt);
      }
    });
    viewCourseButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        viewCourseButtonMouseExited(evt);
      }
    });
    viewCourseButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        viewCourseButtonActionPerformed(evt);
      }
    });
    jPanel1.add(viewCourseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 90, 70));

    viewUserButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    viewUserButton.setForeground(new java.awt.Color(255, 255, 255));
    viewUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_user_male_circle_60px.png"))); // NOI18N
    viewUserButton.setToolTipText("Staff member's account");
    viewUserButton.setBorderPainted(false);
    viewUserButton.setContentAreaFilled(false);
    viewUserButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        viewUserButtonMouseMoved(evt);
      }
      public void mouseDragged(java.awt.event.MouseEvent evt)
      {
        viewUserButtonMouseDragged(evt);
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
    jPanel1.add(viewUserButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 70));

    add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 880, -1, 90));

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
    add(ExportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 880, 80, 70));

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
    add(ImportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 880, 80, 70));
  }//GEN-END:initComponents

  StaffDto s = new StaffDto();


    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
    // TODO add your handling code here:
    try
      {
        List<StaffDto> staff = null;
        s.setSearch(SearchText.getText());
        staff = business.SearchFor(s);

        if(staff!=null)
          {
            setTableModel(staff);
            staffTable.repaint();
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

    private void SearchTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusGained
    if(SearchText.getText().equalsIgnoreCase("Enter text to search"))
      {
        SearchText.setText("");
      }

    SearchText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_SearchTextFocusGained

    private void SearchTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusLost
    if(SearchText.getText().trim().equalsIgnoreCase(""))
      {
        SearchText.setText("Enter text to search");
      }

    SearchText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_SearchTextFocusLost

    private void SearchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchTextActionPerformed
    // TODO add your handling code here:

    try
      {
        List<StaffDto> staff = null;
        s.setSearch(SearchText.getText());
        staff = business.SearchFor(s);

        if(staff!=null)
          {
            setTableModel(staff);
            staffTable.repaint();
          }
        else
          {
            //if there is no result show message tell user that their is no search for this text
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

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
    // TODO add your handling code here:

    if(Save.getText().equalsIgnoreCase("Update"))
      update();

    else
      {
        try
          {
            if(checkValidity())
              {
                s.setId(Integer.parseInt(IdText.getText()));
                s.setName((nametext.getText()));
                s.setPosition(positionComboBox.getSelectedItem().toString());
                s.setDepartment(new DepartmentDto(depComboBox.getSelectedItem().toString()));

                if(business.add(s, user_login))
                  {
                    JOptionPane.showMessageDialog(null,
                      "Staff Inserted Successfully", "Done", 1);
                    //refresh table
                    setTableModel(business.viewAll());
                    staffTable.repaint();
                    Save.setText("Update");
                    IdText.setEnabled(false); //disable id text
                  }
                else
                  {

                    int reply =
                      JOptionPane.showConfirmDialog(null,
                        "This staff is already exist!\n\n"+
                        "Do you want update it?", "Warning",
                        JOptionPane.YES_NO_OPTION);

                    //update object if user choose yes
                    if(reply==JOptionPane.YES_OPTION)
                      {
                        update();
                        Save.setText("Update");
                        IdText.setEnabled(false);
                      }
                  }
              }


          }
        catch(Exception e)
          {
            JOptionPane.showMessageDialog(null, "Something went wrong!",
              "Error", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
          }
      }
    }//GEN-LAST:event_SaveActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
    try
      {
        //Show confirm message
        int reply =
          JOptionPane.showConfirmDialog(null,
            "Are you sure to delete this staff member?\n"+
            "All things depend on it will be deleted!", "Warning",
            JOptionPane.YES_NO_OPTION);

        //delete object if user choose yes
        if(reply==JOptionPane.YES_OPTION)
          {

            s.setId(Integer.parseInt(IdText.getText()));
            if(business.delete(s))
              {
                JOptionPane.showMessageDialog(null,
                  "Staff Deleted Successfully.", "Done", 1);
                setTableModel(business.viewAll());
                staffTable.repaint();
                IdText.setEnabled(true);
                Save.setText("Save");

              }

          }
        else
          {
            JOptionPane.showMessageDialog(null, "Staff does'nt exist! ",
              "Error", 0);
          }


      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    }//GEN-LAST:event_DeleteActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
    // TODO add your handling code here:
    try
      {
        setTableModel(business.viewAll());
        staffTable.repaint();
        defaultdata();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

    }//GEN-LAST:event_RefreshActionPerformed

    private void IdTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusGained
    if(IdText.getText().equalsIgnoreCase("Enter staff ID"))
      {
        IdText.setText("");
      }

    IdText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_IdTextFocusGained

    private void IdTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusLost
    if(IdText.getText().trim().equalsIgnoreCase(""))
      {
        IdText.setText("Enter staff ID");
      }

    IdText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_IdTextFocusLost

    private void IdTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdTextActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_IdTextActionPerformed

    private void nametextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nametextFocusGained
    // TODO add your handling code here:
    if(nametext.getText().equalsIgnoreCase("Enter staff Name"))
      {
        nametext.setText("");
      }

    nametext.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_nametextFocusGained

    private void nametextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nametextFocusLost
    if(nametext.getText().trim().equalsIgnoreCase(""))
      {
        nametext.setText("Enter staff Name");
      }

    nametext.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_nametextFocusLost

    private void nametextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nametextActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_nametextActionPerformed

    private void depComboBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_depComboBoxFocusGained
    
    }//GEN-LAST:event_depComboBoxFocusGained

    private void staffTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_staffTableKeyPressed
    // set text of textfields by the data of the keypressed row in table
    if(evt.getExtendedKeyCode()==KeyEvent.VK_UP||
      evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
      {
        int row = staffTable.getSelectedRow();
        if(evt.getExtendedKeyCode()==KeyEvent.VK_UP)
          row--; //for up key decrement
        else if(evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
          row++; // down key increment
        IdText.setEnabled(false);
        Save.setText("Update");


        IdText.setText(staffTable.getValueAt(row, 0).toString());
        nametext.setText(staffTable.getValueAt(row, 1).toString());

        //get position
        for(int j = 0; j<positionComboBox.getItemCount(); j++)
          {
            if(staffTable.getValueAt(row,
                2).toString().equalsIgnoreCase(positionComboBox.getItemAt(j)))
              positionComboBox.setSelectedIndex(j);
          }

        //get department
        for(int j = 0; j<depComboBox.getItemCount(); j++)
          {
            if(staffTable.getValueAt(row,
                3).toString().equalsIgnoreCase(depComboBox.getItemAt(j)))
              depComboBox.setSelectedIndex(j);
          }
      }

    //delete selected object when press delete
    if(evt.getExtendedKeyCode()==KeyEvent.VK_DELETE)
      {

        try
          {

            //For one selected row in table

            if(staffTable.getSelectedRowCount()==1)
              { //Show confirm message
                int reply =
                  JOptionPane.showConfirmDialog(null,
                    "Are you sure to delete this staff member?\n"+
                    "All things depend on it will be deleted!", "Warning",
                    JOptionPane.YES_NO_OPTION);

                //delete object if user choose yes
                if(reply==JOptionPane.YES_OPTION)
                  {

                    //get selected staff id from table
                    int s =
                      Integer.parseInt(staffTable.getValueAt(staffTable.getSelectedRow(),
                          0).toString());
                    StaffDto staff = new StaffDto();
                    staff.setId(s); //set it to user object

                    //delete it using bao delete method
                    if(business.delete(staff)) //if it deleted sucessfilly show message to tell user that
                      {
                        JOptionPane.showMessageDialog(null,
                          "Staff Deleted Successfully", "Done", 1);
                        setTableModel(business.viewAll());
                        staffTable.repaint();
                        IdText.setEnabled(true);
                        Save.setText("Save");

                      }

                    else
                      //if bao method return false (staff not be deleted)
                      JOptionPane.showMessageDialog(null,
                        "Can't delete object", "Error",
                        JOptionPane.ERROR_MESSAGE);
                  }

              }
            else if(staffTable.getSelectedRowCount()==0)
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
    }//GEN-LAST:event_staffTableKeyPressed

    private void staffTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffTableMouseClicked
    // set text of textfields by the data of the mouse clicked row in table

    IdText.setEnabled(false);
    Save.setText("Update");

    int row = staffTable.getSelectedRow();
    IdText.setText(staffTable.getValueAt(row, 0).toString());
    nametext.setText(staffTable.getValueAt(row, 1).toString());

    //get position
    for(int j = 0; j<positionComboBox.getItemCount(); j++)
      {
        if(staffTable.getValueAt(row,
            2).toString().equalsIgnoreCase(positionComboBox.getItemAt(j)))
          positionComboBox.setSelectedIndex(j);
      }

    //get department
    for(int j = 0; j<depComboBox.getItemCount(); j++)
      {
        if(staffTable.getValueAt(row,
            3).toString().equalsIgnoreCase(depComboBox.getItemAt(j)))
          depComboBox.setSelectedIndex(j);
      }
    
    
    
    
    }//GEN-LAST:event_staffTableMouseClicked

    private void viewUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewUserButtonActionPerformed

    try
      {


        //identify object of user dto
        //   List <UserDto> u = new ArrayList<UserDto>();
        int row = staffTable.getSelectedRow();
        // get data of staff from the selected row and pass it to the dto object
        s.setId(Integer.parseInt(staffTable.getModel().getValueAt(row,
          0).toString()));

        // call view user method from bao
        UserDto u = null;
      
        u = business.viewUser(s);

        //  u.add(business.viewUser(s));

        if(u!=null)
          {

            /*    // Create new frame and panel
            JFrame frame = new JFrame();
            frame.setBounds(100, 100, 450, 300);

            frame.setResizable(false);
            frame.setAlwaysOnTop(true);
            frame.getContentPane().setLayout(null);

            // declare list and its model
            DefaultListModel<Object> model_list1 =
              new DefaultListModel<Object>();
            JList<Object> list = new JList<Object>();


            // set models to lists
            list.setModel(model_list1);


            // set lists bounds
            list.setBounds(17, 41, 174, 194);

            JPanel panel = new JPanel();
            panel.setBounds(100, 100, 450, 300);

            // create scrollpane for list set its location,bounds,layout and add it to the frame
            JScrollPane listScroller = new JScrollPane();
            listScroller.setLocation(45, 43);
            listScroller.setSize(450, 250);

            listScroller.setViewportView(list);

            panel.setBackground(Color.decode("#F5F5F5"));

            list.setLayoutOrientation(JList.VERTICAL);
            panel.add(listScroller);

            frame.getContentPane().add(panel);


            model_list1.addElement("ID:  " + u.getId());
            model_list1.addElement("UserName:  "+u.getUsername());
            model_list1.addElement("Email:  "+u.getEmail());
            model_list1.addElement("Role:  "+u.getRole());

            //add the panel into the frame and set the frame size/title

            frame.setLocationRelativeTo(null); //to make frame in screen center
            frame.setTitle("Staff member - account");
            frame.setSize(490, 400);
            frame.setResizable(false);


            //veiw the frame when user click on the change button
            frame.setVisible(true); */

            // reset and repaint the table with equipments details (if found)
            setNewTable(u);
            staffTable.repaint();
            //tableLabel.setText("User of Selected Staff");
          }

        else
          JOptionPane.showMessageDialog(null,
            "There is no account assigned to this staff.");


      }

    catch(java.lang.ArrayIndexOutOfBoundsException e)
      {

        //show message if now type is selected
        JOptionPane.showMessageDialog(null,
          "Please select staff from table to show his/her user");
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

    }//GEN-LAST:event_viewUserButtonActionPerformed

  private void clearActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_clearActionPerformed
  {//GEN-HEADEREND:event_clearActionPerformed
    // TODO add your handling code here:

    defaultdata(); //call setdefault method to rest edit panel
  }//GEN-LAST:event_clearActionPerformed

  private void staffTableMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_staffTableMouseEntered
  {//GEN-HEADEREND:event_staffTableMouseEntered
    // TODO add your handling code here:
  }//GEN-LAST:event_staffTableMouseEntered

  private void viewUserButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_viewUserButtonMouseMoved
  {//GEN-HEADEREND:event_viewUserButtonMouseMoved
    // TODO add your handling code here:
    viewUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_user_male_circle_60px_1.png")));
  }//GEN-LAST:event_viewUserButtonMouseMoved

  private void viewUserButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_viewUserButtonMouseExited
  {//GEN-HEADEREND:event_viewUserButtonMouseExited
    // TODO add your handling code here:
    viewUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_user_male_circle_60px.png")));

  }//GEN-LAST:event_viewUserButtonMouseExited

  private void viewCourseButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_viewCourseButtonActionPerformed
  {//GEN-HEADEREND:event_viewCourseButtonActionPerformed
    try
      {
        //identify object of user dto
        List<CourseDto> c = null;
        int row = staffTable.getSelectedRow();
        // get data of equipment type from the selected row and pass it to the dto object
        s.setId(Integer.parseInt(staffTable.getModel().getValueAt(row,
          0).toString()));

        // call loadAllEquips method from bao
        c = business.viewCourses(s);
        if(c!=null)
          {

            // reset and repaint the table with equipments details (if found)
            setCourseTable(c);
            staffTable.repaint();
            //tableLabel.setText("Courses of Selected Staff");
          }
        else
          {
            JOptionPane.showMessageDialog(null,
              "No Courses Assigned to this Staff");
          }
      }

    catch(java.lang.ArrayIndexOutOfBoundsException e)
      {

        //show message if now type is selected
        JOptionPane.showMessageDialog(null,
          "Please select staff from table to show his/her courses");
      }

    catch(Exception e)
      {

        e.printStackTrace();
      }
  }//GEN-LAST:event_viewCourseButtonActionPerformed

  private void viewCourseButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_viewCourseButtonMouseExited
  {//GEN-HEADEREND:event_viewCourseButtonMouseExited
    // TODO add your handling code here:
    viewCourseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_courses_60px_1.png"))); // NOI18N

  }//GEN-LAST:event_viewCourseButtonMouseExited

  private void viewCourseButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_viewCourseButtonMouseMoved
  {//GEN-HEADEREND:event_viewCourseButtonMouseMoved
    // TODO add your handling code here:
    viewCourseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_courses_60px.png"))); // NOI18N

  }//GEN-LAST:event_viewCourseButtonMouseMoved

  private void viewUserButtonMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_viewUserButtonMouseDragged
  {//GEN-HEADEREND:event_viewUserButtonMouseDragged
    // TODO add your handling code here:
  }//GEN-LAST:event_viewUserButtonMouseDragged

  private void depComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_depComboBoxActionPerformed
  {//GEN-HEADEREND:event_depComboBoxActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_depComboBoxActionPerformed

  private void SaveMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseExited
  {//GEN-HEADEREND:event_SaveMouseExited
    // TODO add your handling code here:..

    savePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_SaveMouseExited

  private void DeleteMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteMouseExited
  {//GEN-HEADEREND:event_DeleteMouseExited
    // TODO add your handling code here:

    deletePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_DeleteMouseExited

  private void clearMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseExited
  {//GEN-HEADEREND:event_clearMouseExited
    // TODO add your handling code here:

    ClearPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_clearMouseExited

  private void RefreshMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseExited
  {//GEN-HEADEREND:event_RefreshMouseExited
    // TODO add your handling code here:

    RefreshPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_RefreshMouseExited

  private void searchMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseExited
  {//GEN-HEADEREND:event_searchMouseExited
    // TODO add your handling code here:

    searchPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_searchMouseExited

  private void searchMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseMoved
  {//GEN-HEADEREND:event_searchMouseMoved
    // TODO add your handling code here:

    searchPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_searchMouseMoved

  private void RefreshMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseMoved
  {//GEN-HEADEREND:event_RefreshMouseMoved
    // TODO add your handling code here:
    RefreshPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_RefreshMouseMoved

  private void SaveMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseMoved
  {//GEN-HEADEREND:event_SaveMouseMoved
    // TODO add your handling code here:

    savePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_SaveMouseMoved

  private void DeleteMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteMouseMoved
  {//GEN-HEADEREND:event_DeleteMouseMoved
    // TODO add your handling code here:

    deletePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_DeleteMouseMoved

  private void clearMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseMoved
  {//GEN-HEADEREND:event_clearMouseMoved
    // TODO add your handling code here:
    ClearPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_clearMouseMoved

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
    StaffExcel excel = new StaffExcel(user_login);
    List<StaffDto> staffs = null;
    StaffDto staff = null;

    try
    {
      //get all buildings in table

      for(int i=0; i<staffTable.getRowCount() ; i++)
      {
        if(staffs==null)
        staffs = new ArrayList<>();

        staff = new StaffDto();
        staff.setId(Integer.parseInt(staffTable.getValueAt(i, 0).toString()));
        staff.setName(staffTable.getValueAt(i, 1).toString());
        staff.setPosition(staffTable.getValueAt(i, 2).toString());
        staff.setDepartment(new DepartmentDto());
        staff.getDepartment().setName(staffTable.getValueAt(i,
              3).toString());

        staffs.add(staff);

        staff=null;

      }

      //create new file chooser
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      fileChooser.setAcceptAllFileFilterUsed(false);

      //set file name
      String filename = "Staff.xlsx";

      if(!SearchText.getText().equalsIgnoreCase("Enter text to search"))
      filename = SearchText.getText()+ "-Staff.xlsx";
      //show file dialog
      int result = fileChooser.showSaveDialog(null);

      //user selected file
      if(result==JFileChooser.APPROVE_OPTION)
      {
        //get path
        String filePath =
        fileChooser.getSelectedFile().getAbsolutePath();

        excel.write(filename, filePath, staffs);

        //write excel file
        try
        {

          JOptionPane.showMessageDialog(null,
            "Staff data export ended successfully!", "Done", 1);
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

    StaffExcel read = new StaffExcel(user_login);
    StaffBao bao = new BaoFactory().createStaffBao(user_login);
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

        List<StaffDto> import_staff = read.read_data(filePath);

        if(import_staff!=null)
        {

          //insert reading building to database
          for (int i=0;  i<import_staff.size() ;i++)
          {
            bao.add(import_staff.get(i), user_login);
          }

          // show result set in table
          setTableModel(import_staff);
          staffTable.repaint();

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
      {

        //check for if id is empty
        if(IdText.getText().equalsIgnoreCase("Enter staff ID"))
          {
            JOptionPane.showMessageDialog(null, "Please, enter staff id",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("id text is empry");
          }

        //Check validity of id
        try
          {
            int staff_id = Integer.parseInt(IdText.getText());
            //Check for the entered id is a positive number
            if(staff_id<1)
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


        //check for if name is empty
        if(nametext.getText().equalsIgnoreCase("Enter staff Name"))

          {
            JOptionPane.showMessageDialog(null, "Please, Enter staff Name.",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("name text is empty");
          }

        //check validity of username

        //Username can't start with number or symbol
        if(!Character.isAlphabetic(nametext.getText().charAt(0)))
          {
            JOptionPane.showMessageDialog(null,
              "Invalid name format\n\n(staff name can't start with number or symbol)",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Name can't start with number or symbol");

          }
        //check for all chars
        for(int i = 1; i<nametext.getText().length(); i++)
          {

            //code contain only letters/numbers ane '_'
            if(!Character.isLetterOrDigit(nametext.getText().charAt(i))&&
              nametext.getText().charAt(i)!='_'&&
              nametext.getText().charAt(i)!=' ')
              {
                JOptionPane.showMessageDialog(null,
                  "Invalid staff name format\n\n(name can only be a sequence of Unicode letters and digits separated by underscore)",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("name can't contain stranger symbols");

              }
          }

        if(positionComboBox.getSelectedIndex()==-1)
          {
            JOptionPane.showMessageDialog(null,
              "Please, select staff position.", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("staff position is'nt selected");

          }


        if(depComboBox.getSelectedIndex()==-1)
          {
            JOptionPane.showMessageDialog(null,
              "Please, select staff department.", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("staff department is'nt selected");

          }
        return true;
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


  //update function
  private void update()
  {
    try
      {
        if(checkValidity())
          {

            s.setId(Integer.parseInt(IdText.getText()));
            s.setName(nametext.getText());
            s.setPosition(positionComboBox.getSelectedItem().toString());
            s.setDepartment(new DepartmentDto(depComboBox.getSelectedItem().toString()));

            if(business.update(s, user_login))
              {
                JOptionPane.showMessageDialog(null,
                  "Staff updated Successfully", "Done", 1);
                setTableModel(business.viewAll());
                staffTable.repaint();
              }
            else
              {
                JOptionPane.showMessageDialog(null,
                  "The Staff does not exist!", "Error",
                  JOptionPane.ERROR_MESSAGE);
                staffTable.repaint();
              }
          }


      }
    catch(Exception e)
      {
        JOptionPane.showMessageDialog(null, "Something went wrong!",
          "Error", JOptionPane.ERROR_MESSAGE);

        e.printStackTrace();
      }

  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel ClearPanel;
  private javax.swing.JButton Delete;
  private javax.swing.JPanel EditPanel;
  private javax.swing.JButton ExportButton;
  private javax.swing.JTextField IdText;
  private javax.swing.JButton ImportButton;
  private javax.swing.JButton Refresh;
  private javax.swing.JPanel RefreshPanel;
  private javax.swing.JButton Save;
  private javax.swing.JTextField SearchText;
  private javax.swing.JButton clear;
  private javax.swing.JLabel code;
  private javax.swing.JPanel deletePanel;
  private javax.swing.JComboBox<String> depComboBox;
  private javax.swing.JLabel id;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JLabel name_;
  private javax.swing.JTextField nametext;
  private javax.swing.JLabel no_of_hours;
  private java.awt.Label no_of_rows;
  private javax.swing.JComboBox<String> positionComboBox;
  private javax.swing.JPanel savePanel;
  private javax.swing.JButton search;
  private javax.swing.JPanel searchPanel;
  private javax.swing.JTable staffTable;
  private javax.swing.JButton viewCourseButton;
  private javax.swing.JButton viewUserButton;
  // End of variables declaration//GEN-END:variables

}
