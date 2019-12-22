
package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.BuildingBao;
import com.fym.lta.BAO.DepartmentBao;
import com.fym.lta.BAO.RoleScreenBao;
import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.UserDto;
import com.fym.lta.Excel.DepartmentExcel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Mai-AbdEltwab
 */


public class DepartmentPanel extends javax.swing.JPanel
{
  @SuppressWarnings({ "compatibility:-5743342370269248875",
      "oracle.jdeveloper.java.serialversionuid-stale" })
  private static final long serialVersionUID = 1L;

  /**Definitions*/
  //define user dto as "user"
  private static UserDto user;
  //define building bao as "bao"
  private static DepartmentBao bao;
  //define department dto as "department"
  private static DepartmentDto department;
  //define building dto as "building"
  private static BuildingDto building;


  /** Creates new form DepartmentPanel */
  public DepartmentPanel(UserDto user)
  {

    try
      {
        //set taken user object to the private one
        DepartmentPanel.user = user;

        bao = new BaoFactory().createDepartmentBao(user); //init bao
        
        //set panel components
        initComponents();

        /* get department data using view all method in bao
         * paint department table for the result data*/
        setTableModel(bao.viewAll());
      
        //use user access method to check user validity on this screen
        userAccess();

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

  }


  /** set department table model 
   * @param department list */
  private void setTableModel(List<DepartmentDto> departs)
  {
    int size = 0; //for table row size

    //if given list not empty or null
    if(departs!=null&&!departs.isEmpty())
      size = departs.size(); //set table size to this list size
    
    /*this (2D) array of objects for table model
     *the first D for rows, second for columns */
    Object[][] departArr = new Object[departs.size()][4];

    for(int i = 0; i<size ; i++) //for each row
      {

        departArr[i][0] = departs.get(i).getId(); //set id
        departArr[i][1] = departs.get(i).getCode(); //set code
        departArr[i][2] = departs.get(i).getName(); //set name 

        //for buildings
        String buildings = ""; //buffer string

        //view buildings in table as format "b1, b2,..."
        if(departs.get(i).getBuildings()!=null&&
          !departs.get(i).getBuildings().isEmpty())
          {
            for(int j = 0; j<departs.get(i).getBuildings().size(); j++)
              {

                if(j==departs.get(i).getBuildings().size()-1) //for last one
                  buildings += departs.get(i).getBuildings().get(j).getCode();
                else
                  buildings += departs.get(i).getBuildings().get(j).getCode()+", ";
              }
          }

        departArr[i][3] = buildings; //show them in table

      }

    //set table model using this array
    departTable.setModel(new javax.swing.table.DefaultTableModel(departArr,
        new String[] { "Id", "Code", "Name", "Building" }));

    //change table header color
    departTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
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


    //label under tabel show departments count viewed in table
    no_of_rows.setText("Table result: "+
      Integer.toString(departTable.getRowCount())+"  departments");


  }


  /**This method use to check user validity on this screen
   * if user has full access or viewonly access*/
  private void userAccess()
  {
    //set user screen name to "Department" to make check on it
    user.setScreen_name("Department");

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

  /**check Validity of entered data
   * @return: True if data is entered correctly (as expected)
   * False when one or more of them entered uncorrectly
   * */

  private boolean checkValidity()
  {

    try
      {

        /** check for empty entered data */
        //for default id text
        if(IdText.getText().contentEquals("Enter department ID")) 
          {
            //ask user enter id
            JOptionPane.showMessageDialog(null,
              "Please, enter department id", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            
            //throw empty id exception
            throw new IllegalArgumentException("id text is empry");
          }
        //for code default text
        else if(CodeText.getText().equalsIgnoreCase("Enter department code")) //for code

          {
            //ask user enter code
            JOptionPane.showMessageDialog(null,
              "Please, enter department code", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            
            //throw empty code exception
            throw new IllegalArgumentException("code text is empty");
          }
        //for name default text
        else if(NameText.getText().contentEquals("Enter department name ")) //for name
          {
            //ask user enter name
            JOptionPane.showMessageDialog(null,
              "Please, enter department name", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            
            //throw empty name exception
            throw new IllegalArgumentException("name text is empty");
          }
        // for non selected building
        else if(buildingBox.getItemCount()==0||
          buildingBox.getSelectedIndex()==-1) //for building
          {
            //ask user select at least one
            JOptionPane.showMessageDialog(null,
              "Please, select at least one building\n\n", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            
            //throw non selected building exception
            throw new IllegalArgumentException("thetre is no selected building.");
          }

        /**  Check validity of id */
        //Check for the entered id is a positive number
        int id = Integer.parseInt(IdText.getText());
        if(id<1)
          {
            //ask user to enter positive number
            JOptionPane.showMessageDialog(null,
              "ID is only Positive Numbers", "Invalid Input", 1);

            //throw this exception
            throw new IllegalArgumentException("ID is only Positive Numbers");
          }


        /**check validity of code*/

        //code can't start with number or symbol
        if(!Character.isAlphabetic(CodeText.getText().charAt(0)))
          {
            //show message to tell user  that
            JOptionPane.showMessageDialog(null,
              "Invalid code format\n\n(code can't start with number or symbol)",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            //throw this exception
            throw new IllegalArgumentException("code can't start with number or symbol");

          }
        //check for all chars
        for(int i = 1; i<CodeText.getText().length(); i++)
          {

            //code contain only letters/numbers and "_","."," ","-"
            if(!Character.isLetterOrDigit(CodeText.getText().charAt(i))&&
              CodeText.getText().charAt(i)!='_' &&
              CodeText.getText().charAt(i)!='.' &&
              CodeText.getText().charAt(i)!=' ' &&
              CodeText.getText().charAt(i)!='-')
              {
                //show message to tell user  that
                JOptionPane.showMessageDialog(null,
                  "code can't contain stranger symbols",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                
                //throw invalid code exception
                throw new IllegalArgumentException("code can't contain stranger symbols");

              }
          }


        /** check validity of name */

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
              NameText.getText().charAt(i)!=' ' &&
              NameText.getText().charAt(i)!='-' &&
              NameText.getText().charAt(i)!='.')
              {
                //show message to tell user  that
                JOptionPane.showMessageDialog(null,
                  "Invalid Name format\nName can't contain stranger symbols",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                //throw this exception
                throw new IllegalArgumentException("Name can't contain stranger symbols");
              }
          }


        return true; //data is valid

      }
    //occur when id is not numerical
    catch(java.lang.NumberFormatException e2)
      {
        
        e2.printStackTrace(); //print exception
        
        //as user enter number
        JOptionPane.showMessageDialog(null, "Please enter number for ID",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
        
        return false;  //data is invalid
      }
    
    // catch the illagal thrown exceptions
    catch(IllegalArgumentException e)
      {

        e.printStackTrace();
        return false; //data is invaild
      }

    catch(Exception e)
      {
        e.printStackTrace();
        //if there is any other non expecting error
        JOptionPane.showMessageDialog(null,
          "Some Thing went wrong!\nPlease check your entered data. ",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
        
        return false;  //data is invaild
      }


  }


  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  private void initComponents()//GEN-BEGIN:initComponents
  {

    c = new javax.swing.JComboBox<>();
    jComboBox1 = new javax.swing.JComboBox<>();
    buildingtext = new javax.swing.JTextField();
    SearchText = new javax.swing.JTextField();
    jScrollPane1 = new javax.swing.JScrollPane();
    departTable = new javax.swing.JTable();
    no_of_rows = new java.awt.Label();
    EditPanel = new javax.swing.JPanel();
    NameLabel1 = new javax.swing.JLabel();
    IdLabel1 = new javax.swing.JLabel();
    CodeLabel1 = new javax.swing.JLabel();
    IdLabel5 = new javax.swing.JLabel();
    IdText = new javax.swing.JTextField();
    CodeText = new javax.swing.JTextField();
    NameText = new javax.swing.JTextField();
    buildingBox = new javax.swing.JComboBox<>();
    jLabel6 = new javax.swing.JLabel();
    SavePanel = new javax.swing.JPanel();
    Save = new javax.swing.JButton();
    DeletePanel = new java.awt.Panel();
    Delete = new javax.swing.JButton();
    ClearPanel = new javax.swing.JPanel();
    clear = new javax.swing.JButton();
    ChangePanel = new javax.swing.JPanel();
    Change = new javax.swing.JButton();
    jLabel8 = new javax.swing.JLabel();
    jSeparator1 = new javax.swing.JSeparator();
    searchPanel = new javax.swing.JPanel();
    search = new javax.swing.JButton();
    RefreshPanel = new javax.swing.JPanel();
    Refresh = new javax.swing.JButton();
    ImportButton = new javax.swing.JButton();
    ExportButton = new javax.swing.JButton();

    c.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    buildingtext.setText("department located building/s");
    buildingtext.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        buildingtextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        buildingtextFocusLost(evt);
      }
    });
    buildingtext.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        buildingtextActionPerformed(evt);
      }
    });

    setBackground(new java.awt.Color(245, 245, 245));
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    SearchText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    SearchText.setText("Enter what do you want to search for");
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
    SearchText.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyPressed(java.awt.event.KeyEvent evt)
      {
        SearchTextKeyPressed(evt);
      }
    });
    add(SearchText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 640, 50));

    departTable.setAutoCreateRowSorter(true);
    departTable.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    departTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String []
      {
        "Id", "Code", "Name", "Building"
      }
    )
    {
      Class[] types = new Class []
      {
        java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
    departTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    departTable.setFillsViewportHeight(true);
    departTable.setRowHeight(21);
    departTable.setSelectionBackground(new java.awt.Color(0, 153, 204));
    departTable.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        departTableMouseClicked(evt);
      }
      public void mouseEntered(java.awt.event.MouseEvent evt)
      {
        departTableMouseEntered(evt);
      }
    });
    departTable.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyPressed(java.awt.event.KeyEvent evt)
      {
        departTableKeyPressed(evt);
      }
    });
    jScrollPane1.setViewportView(departTable);
    if (departTable.getColumnModel().getColumnCount() > 0)
    {
      departTable.getColumnModel().getColumn(0).setHeaderValue("Id");
      departTable.getColumnModel().getColumn(1).setHeaderValue("Code");
      departTable.getColumnModel().getColumn(2).setHeaderValue("Name");
      departTable.getColumnModel().getColumn(3).setHeaderValue("Building");
      departTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(c));
    }

    add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 730, 750));

    no_of_rows.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
    no_of_rows.setForeground(new java.awt.Color(135, 135, 135));
    no_of_rows.setText("no of rows");
    add(no_of_rows, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 860, 200, 20));

    EditPanel.setBackground(new java.awt.Color(245, 245, 245));
    EditPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    NameLabel1.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    NameLabel1.setForeground(new java.awt.Color(0, 51, 204));
    NameLabel1.setText("Name");
    EditPanel.add(NameLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 80, 40));

    IdLabel1.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    IdLabel1.setForeground(new java.awt.Color(0, 51, 204));
    IdLabel1.setText("Id");
    EditPanel.add(IdLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 35, 40));

    CodeLabel1.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    CodeLabel1.setForeground(new java.awt.Color(0, 51, 204));
    CodeLabel1.setText("Code");
    EditPanel.add(CodeLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, 40));

    IdLabel5.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    IdLabel5.setForeground(new java.awt.Color(0, 51, 204));
    IdLabel5.setText("Building");
    EditPanel.add(IdLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 76, 50));

    IdText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    IdText.setText("Enter department ID");
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
    EditPanel.add(IdText, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 580, 60));

    CodeText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    CodeText.setText("Enter department code");
    CodeText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    CodeText.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        CodeTextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        CodeTextFocusLost(evt);
      }
    });
    EditPanel.add(CodeText, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 580, 60));

    NameText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    NameText.setText("Enter department name ");
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
    EditPanel.add(NameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 580, 60));

    buildingBox.setBackground(new java.awt.Color(255, 255, 255));
    buildingBox.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    EditPanel.add(buildingBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 450, 60));

    jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_org_unit_128px.png"))); // NOI18N
    EditPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, -1, -1));

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
      .addComponent(Save, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
    );
    SavePanelLayout.setVerticalGroup(
      SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SavePanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    EditPanel.add(SavePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 560, 100, 50));

    DeletePanel.setBackground(new java.awt.Color(0, 129, 211));

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

    javax.swing.GroupLayout DeletePanelLayout = new javax.swing.GroupLayout(DeletePanel);
    DeletePanel.setLayout(DeletePanelLayout);
    DeletePanelLayout.setHorizontalGroup(
      DeletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Delete, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
    );
    DeletePanelLayout.setVerticalGroup(
      DeletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeletePanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    EditPanel.add(DeletePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 560, 100, 50));

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
      .addComponent(clear, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
    );
    ClearPanelLayout.setVerticalGroup(
      ClearPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ClearPanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    EditPanel.add(ClearPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 560, 100, 50));

    ChangePanel.setBackground(new java.awt.Color(0, 129, 211));

    Change.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    Change.setForeground(new java.awt.Color(255, 255, 255));
    Change.setText("Change");
    Change.setBorderPainted(false);
    Change.setContentAreaFilled(false);
    Change.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        ChangeMouseMoved(evt);
      }
    });
    Change.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        ChangeMouseExited(evt);
      }
    });
    Change.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        ChangeActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout ChangePanelLayout = new javax.swing.GroupLayout(ChangePanel);
    ChangePanel.setLayout(ChangePanelLayout);
    ChangePanelLayout.setHorizontalGroup(
      ChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Change, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
    );
    ChangePanelLayout.setVerticalGroup(
      ChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Change, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(ChangePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 440, 100, 50));

    add(EditPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, 650, 750));

    jLabel8.setBackground(new java.awt.Color(231, 78, 123));
    jLabel8.setFont(new java.awt.Font("VIP Rawy Regular", 0, 36)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(231, 78, 123));
    jLabel8.setText("Department ");
    add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 60, 240, 70));
    add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 120, 610, 20));

    searchPanel.setBackground(new java.awt.Color(0, 129, 211));

    search.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
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
      .addComponent(search, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
    );
    searchPanelLayout.setVerticalGroup(
      searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(searchPanelLayout.createSequentialGroup()
        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    add(searchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 40, 80, 50));

    RefreshPanel.setBackground(new java.awt.Color(0, 129, 211));

    Refresh.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    Refresh.setForeground(new java.awt.Color(255, 255, 255));
    Refresh.setText("Refresh");
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
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RefreshPanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    add(RefreshPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 870, 120, 50));

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
    add(ImportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 860, 90, 70));

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
    add(ExportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 860, 100, 70));
  }//GEN-END:initComponents

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
 
    //if the set text update
    if(Save.getText().equalsIgnoreCase("Update"))
      update(); //use update method
      
    else //try insert this course
      {
        if(checkValidity()) //check if entered data is valid
          {
            try
              {
                
                department = new DepartmentDto(); //set new department 

                // check and pick up which buildings have be selected
                List<BuildingDto> buildings = new ArrayList<BuildingDto>();

                for(int i = 0; i<buildingBox.getItemCount(); i++)
                  {
                    building = new BuildingDto(); //set new building
                    building.setCode(buildingBox.getItemAt(i)); //set building code
                    buildings.add(building); //add it to the list
                    building = null; //clear this building
                  }

                //set entered data to department object
                department.setId(Integer.parseInt(IdText.getText()));
                department.setCode(CodeText.getText());
                department.setName(NameText.getText());
                department.setBuildings(buildings);

                //go to business layer and add it
                boolean flag = bao.create(department, user);

                if(flag==true) //if it success
                  {
                    // show message to tell user that
                    JOptionPane.showMessageDialog(null,
                      "Department has inserted successfully!", "Done", 1);
                    
                    //refresh table
                    setTableModel(bao.viewAll());
                    departTable.repaint();
                    
                    //set save button text to "Update"
                    Save.setText("Update");
                    
                    //set id text disable (id can't be edited after insert)
                    IdText.setEnabled(false);

                  }
                else // if it failed it mean this department is already exit
                  {

                    /*so show message to ask user if he want
                    * to update this department or not*/
                    int reply =
                      JOptionPane.showConfirmDialog(null,
                        "This Department is already exist!\n\n"+
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


            catch(Exception e)
              {
                e.printStackTrace();
                //if there is any unexpected exception
                JOptionPane.showMessageDialog(null,
                  "Some Thing went wrong!\nPlease check your entered data. ",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);

              }
          }
      }
    }//GEN-LAST:event_SaveActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
       
       
    //Show confirm message
    int reply =
      JOptionPane.showConfirmDialog(null,
      "Are you sure to delete this department?\n"+
      "All things depend on it will be deleted!", "Warning",
      JOptionPane.YES_NO_OPTION);

    //delete object if user choose yes
    if(reply==JOptionPane.YES_OPTION)
      {

        try
          {
            department = new DepartmentDto(); //set new department

            //get department id from text box and set it to the dto object
            department.setId(Integer.parseInt(IdText.getText()));

            //go to business layer and delete it
            boolean flag = bao.delete(department);


            /*if object has deleted successfully */
            if(flag==true)
              {
                //show message to tell user this
                JOptionPane.showMessageDialog(null,
                  "Department has deleted successfully!", "Done",
                  JOptionPane.INFORMATION_MESSAGE);
                
                //refresh table
                setTableModel(bao.viewAll());
                departTable.repaint();

                IdText.setEnabled(true); //set id text enable
                Save.setText("Insert"); //set save button text to "Insert"
              }
            //else show message to tell user that this object does't exist in database
            else
              JOptionPane.showMessageDialog(null,
                "Department doesn't exist!", "Not Found",
                JOptionPane.ERROR_MESSAGE);
          }
        catch(Exception e)
          {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
              "Some Thing went wrong!\nPlease check your entered data. ",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
          }
      }
    }//GEN-LAST:event_DeleteActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed

    IdText.setEnabled(true); //enable id text box
    Save.setText("Insert"); //set save button text to "insert"

    //set default text for text boxes
    IdText.setText("Enter department ID");
    CodeText.setText("Enter department code");
    NameText.setText("Enter department name ");

    //clear selection Buildings in building combobox
    buildingBox.removeAllItems();

    // view all data again in table
    setTableModel(bao.viewAll());
    
    }//GEN-LAST:event_RefreshActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        
    try
      {

        department = new DepartmentDto(); //set new department

        // Check if the Search field is empty
        if(SearchText.getText().equalsIgnoreCase("Enter what do you want to search for"))
          {
            //Show message tell user that Search Field is empty
            JOptionPane.showMessageDialog(null, "Search field is empty",
              "Invalid Input!", JOptionPane.ERROR_MESSAGE);
            //throw exception
            throw new IllegalArgumentException("Search field is empty");
          }

        //take search text into dto object
        department.setSearch(SearchText.getText());

        //Pick up result set from database through search bao method
        List<DepartmentDto> result = bao.searchFor(department);

        //if search results are found, Show result set in table
        if(result!=null&&!result.isEmpty())
          setTableModel(result);

        //if there is no result show message tell user that their is no search for this text
        else
          JOptionPane.showMessageDialog(null,
            "There is no search result for: "+SearchText.getText(),
            "Invalid search", 1);

      }
    catch(IllegalArgumentException e)
      {
        e.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
        
    }//GEN-LAST:event_searchActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
       
    IdText.setEnabled(true); //enable id text box
    Save.setText("Insert"); //set save button text to "insert"
    
    //set default text for text boxes
    IdText.setText("Enter department ID");
    CodeText.setText("Enter department code");
    NameText.setText("Enter department name ");

    //clear selection Buildings in building combobox
    buildingBox.removeAllItems();
   
        
    }//GEN-LAST:event_clearActionPerformed

    private void NameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameTextActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_NameTextActionPerformed

    private void departTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_departTableMouseClicked
        
    IdText.setEnabled(false); //set id text disable
    Save.setText("Update"); //set save button text "Update"

    //get number of selected row in table
    int i = departTable.getSelectedRow();

    //get buildings from table
    String builds = departTable.getValueAt(i, 3).toString();
    String[] buildsCombo = null;

    //remove all existing items in buildings combobox
    buildingBox.removeAllItems();

    if(builds.contains(",")) //if there are more than one building
      {

        //split builds text to get each building code
        buildsCombo = builds.split(", ");

        if(buildsCombo!=null&&buildsCombo.length!=0) //for non empty buildings

          //add existing buildings into building combobox
          {
            for(int j = 0; j<buildsCombo.length; j++)
              {
                buildingBox.addItem(buildsCombo[j]);
              }
          }
      }

    // for one building
    else
      {
        buildingBox.addItem(builds); //add it to building combo
      }

    //set the others attributes of selected row to text boxes
    IdText.setText(departTable.getValueAt(i, 0).toString());
    CodeText.setText(departTable.getValueAt(i, 1).toString());
    NameText.setText(departTable.getValueAt(i, 2).toString());
        
 
    }//GEN-LAST:event_departTableMouseClicked

    private void departTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_departTableKeyPressed
        
        
    //get number of selected row in table
    int i = departTable.getSelectedRow();

    //Because "departTable.getSelectedRow()" give the previous selected row
    if(evt.getExtendedKeyCode()==KeyEvent.VK_UP)
      i--; //for up key decrement
    else if(evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
      i++; // for down key increment

    /**move up and down in table to get data into edit space*/
    try
      {
        //unable id text box,as user cant't update it(PK)
        IdText.setEnabled(false);
        Save.setText("Update"); //set save button text "Update"


        if(evt.getExtendedKeyCode()==KeyEvent.VK_UP||
          evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
          {

            //get buildings from table
            String builds = departTable.getValueAt(i, 3).toString();
            String[] buildsCombo = null;

            //remove all existing items in buildings combobox
            buildingBox.removeAllItems();
            
            if(builds.contains(",")) //for more than one building
              {

                //split builds text to get each building code
                buildsCombo = builds.split(", ");              

                if(buildsCombo!=null&&buildsCombo.length!=0) //for non empty buildings

                  //add existing buildings into building combobox
                  {
                    for(int j = 0; j<buildsCombo.length; j++)
                      {
                        buildingBox.addItem(buildsCombo[j]);
                      }
                  }
              }

           else // for one building
              {
                buildingBox.addItem(builds); //add it to building combo
              }

            //set the others attributes of selected row to text boxes
            IdText.setText(departTable.getValueAt(i, 0).toString());
            CodeText.setText(departTable.getValueAt(i, 1).toString());
            NameText.setText(departTable.getValueAt(i, 2).toString());


          }
      }

    catch(java.lang.ArrayIndexOutOfBoundsException e)
      {
        e.printStackTrace();
      }

    catch(Exception e)
      {
        e.printStackTrace();
      }

    /** delete selected object when press delete */
    if(evt.getExtendedKeyCode()==KeyEvent.VK_DELETE)
      {
        try
          {
            //For one selected row in table
            if(departTable.getSelectedRowCount()==1)
              { //Show confirm message
                int reply =
                  JOptionPane.showConfirmDialog(null,
                    "Are you sure to delete this department?\n"+
                    "All things depend on it will be deleted!", "Warning",
                    JOptionPane.YES_NO_OPTION);

                //delete object if user choose yes
                if(reply==JOptionPane.YES_OPTION)
                  {
                    department =
                      new DepartmentDto(); //set new department
                    
                    //get selected Department id from table
                    int id =
                      Integer.parseInt(departTable.getValueAt(departTable.getSelectedRow(),
                          0).toString());
                    department.setId(id); //set it to Department object

                 
                    if(bao.delete(department)) //if it deleted sucessfilly show message to tell user that
                      {

                        //show message to tell user this
                        JOptionPane.showMessageDialog(null,
                          "Department has deleted successfully!", "Done",
                          JOptionPane.INFORMATION_MESSAGE);

                        //refresh table
                        setTableModel(bao.viewAll());
                        departTable.repaint();

                        IdText.setEnabled(true); //set id text enable
                        Save.setText("Insert"); //set save button text to "Insert"

                      }

                    else
                      //if bao method return false (department not be deleted)
                      JOptionPane.showMessageDialog(null,
                        "Can't delete object", "Error",
                        JOptionPane.ERROR_MESSAGE);
                  }

              }
            else if(departTable.getSelectedRowCount()==0)
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
        
    }//GEN-LAST:event_departTableKeyPressed

    private void IdTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusGained
   
    //if found text is the default text
    if(IdText.getText().equalsIgnoreCase("Enter department ID"))
      IdText.setText(""); //remove it
    
    //set new border to change border color
    IdText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_IdTextFocusGained

    private void IdTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusLost
    //if there is no text
    if(IdText.getText().trim().equalsIgnoreCase(""))
      IdText.setText("Enter department ID"); //set the default one

    //set new border to reset border color
    IdText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_IdTextFocusLost

    private void CodeTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodeTextFocusGained
    //if found text is the default text
    if(CodeText.getText().equalsIgnoreCase("Enter department code"))
      CodeText.setText(""); //remove it

    //set new border to change border color
    CodeText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_CodeTextFocusGained

    private void CodeTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodeTextFocusLost
    //if there is no text
    if(CodeText.getText().trim().equalsIgnoreCase(""))
      CodeText.setText("Enter department code"); //set the default one

    //set new border to reset border color
    CodeText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_CodeTextFocusLost

    private void NameTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameTextFocusGained
    //if found text is the default text
    if(NameText.getText().equalsIgnoreCase("Enter department name "))
      NameText.setText(""); //remove it
    
    //set new border to change border color
    NameText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_NameTextFocusGained

    private void NameTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameTextFocusLost
    //if there is no text
    if(NameText.getText().trim().equalsIgnoreCase(""))
      NameText.setText("Enter department name "); //set the default one

    //set new border to reset border color
    NameText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_NameTextFocusLost

    private void SearchTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusGained
    //if found text is the default text
    if(SearchText.getText().equalsIgnoreCase("Enter what do you want to search for"))
      SearchText.setText(""); //remove it

    //set new border to change border color
    SearchText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_SearchTextFocusGained

    private void SearchTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusLost
    //if there is no text
    if(SearchText.getText().equalsIgnoreCase(""))
      SearchText.setText("Enter what do you want to search for"); //set the default one
    
    //set new border to reset border color
    SearchText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_SearchTextFocusLost

    private void SearchTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchTextKeyPressed
    
    if(evt.getExtendedKeyCode()==KeyEvent.VK_ENTER)
      {
        try
          {
            department = new DepartmentDto(); //set new department

            // Check if the Search field is empty
            if(SearchText.getText().equalsIgnoreCase(""))
              {
                //Show message tell user that Search Field is empty
                JOptionPane.showMessageDialog(null, "Search field is empty",
                  "Invalid Input!", JOptionPane.ERROR_MESSAGE);
                //throw exception
                throw new IllegalArgumentException("Search field is empty");
              }
          
            //get the input search text into search attribute in departemt dto object
            department.setSearch(SearchText.getText());

            // get the result set from bao
            List<DepartmentDto> result = bao.searchFor(department);

            //if there is a result set show them in the department table
            if(result!=null&&!result.isEmpty())
              {
                setTableModel(result);
                departTable.repaint();
              }
            else
              //if there is no result set show message to tell user that
              JOptionPane.showMessageDialog(null,
                "There is no search result for: "+SearchText.getText(),
                "Invalid search", 1);
          }

        catch(Exception e)
          {
            e.printStackTrace();

          }
      }
    }//GEN-LAST:event_SearchTextKeyPressed

    private void ChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangeActionPerformed
       
    JFrame frame = new JFrame(); // Create new frame
    frame.setBounds(100, 100, 450, 300); //set frame bounds
    frame.setResizable(false); //set frame resizable false
    frame.setAlwaysOnTop(true); //set it in top
    frame.getContentPane().setLayout(null); //set its layout null

    JPanel panel = new JPanel(); //create new panel
    panel.setBackground(Color.decode("#F5F5F5")); //set its background
    panel.setLayout(null); //set its layout null

    //Declare new button named "add"
    JButton add = new JButton("add");
    add.setBackground(Color.decode("#0081D3")); //set background color
    add.setForeground(Color.white); //set font color
    add.setBounds(95, 270, 85, 40); // set its bounds
    add.setFont(new java.awt.Font("VIP Rawy Regular", 0, 16)); // set font

    //Declare new buttom named "save"
    JButton save = new JButton("save");
    save.setBackground(Color.decode("#0081D3")); //set background color
    save.setForeground(Color.white); //set font color
    save.setBounds(200, 270, 85, 40); // set its bounds
    save.setFont(new java.awt.Font("VIP Rawy Regular", 0, 16)); // set font

    //Declare new buttom named "remove"
    JButton remove = new JButton("remove");
    remove.setBackground(Color.decode("#0081D3")); //set background color
    remove.setForeground(Color.white); //set font color
    remove.setBounds(310, 270, 85, 40); // set its bounds
    remove.setFont(new java.awt.Font("VIP Rawy Regular", 0,
        16)); // set font


    // declare two lists and their models
    DefaultListModel<Object> model_list1 = new DefaultListModel<Object>();
    DefaultListModel<Object> model_list2 = new DefaultListModel<Object>();
    JList<Object> list1 = new JList<Object>();
    JList<Object> list2 = new JList<Object>();

    // set models to lists
    list1.setModel(model_list1);
    list2.setModel(model_list2);

    // set lists bounds
    list1.setBounds(17, 41, 174, 194);
    list2.setBounds(262, 41, 174, 194);

    /* create scrollpane for list1 set its location,bounds,layout,font */
    JScrollPane listScroller = new JScrollPane();
    listScroller.setLocation(45, 43);
    listScroller.setSize(174, 194);
    listScroller.setViewportView(list1);
    list1.setLayoutOrientation(JList.VERTICAL);
    list1.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 16));
    panel.add(listScroller); //add it to the panel

    // create scrollpane for list2 set its location,bounds,layout,font
    JScrollPane listScroller1 = new JScrollPane();
    listScroller1.setLocation(262, 43);
    listScroller1.setSize(174, 194);
    listScroller1.setViewportView(list2);
    list2.setLayoutOrientation(JList.VERTICAL);
    list2.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 16));
    panel.add(listScroller1); //add it to the panel

    //set labels for list1, set location,size,text,font,font color
    JLabel labelList1 = new JLabel();
    labelList1.setText("Available buildings:");
    labelList1.setSize(170, 19);
    labelList1.setLocation(48, 20);
    labelList1.setFont(new java.awt.Font("VIP Rawy Regular", 0, 16));
    labelList1.setForeground(Color.decode("#0081D3"));
    panel.add(labelList1); //add it to the panel

    //set labels for list2, set location,size,text,font,font color
    JLabel labelList2 = new JLabel();
    labelList2.setText("Selected buildings");
    labelList2.setSize(170, 19);
    labelList2.setLocation(265, 20);
    labelList2.setFont(new java.awt.Font("VIP Rawy Regular", 0, 16));
    labelList2.setForeground(Color.decode("#0081D3"));
    panel.add(labelList2); //add it to the panel


    //get all existing buildings from data base
    BuildingBao building_bao = new BaoFactory().createBuildingBao(user);
    List<BuildingDto> builds = new ArrayList<BuildingDto>();
    builds = building_bao.ListAll();

    // set all existing buildings' code to the first list
    for(int i = 0; i<builds.size(); i++)
      {
        model_list1.addElement(builds.get(i).getCode());
      }


    /*get existing buildings' code from combobox (if exist)
         * add them to second list
         * remove them from first list
         * */
    for(int i = 0; i<buildingBox.getItemCount(); i++)
      {

        model_list2.addElement(buildingBox.getItemAt(i));
        model_list1.removeElement(buildingBox.getItemAt(i));

      }


    // move all selected element to the secode list and remove it from first list
    // when user click on add button
    add.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        list1.getSelectedValuesList().stream().forEach((data) ->
        {
          model_list2.addElement(data);
          model_list1.removeElement(data);
        });
      }
    });


    // remove all selected element from the secode list and add it to first list
    // when user click on remove button
    remove.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        list2.getSelectedValuesList().stream().forEach((data) ->
        {
          model_list1.addElement(data);
          model_list2.removeElement(data);
        });
      }
    });


    // Action when user click on save button
    save.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {

        //clear building combobox
        buildingBox.removeAllItems();

        //Save selected building into building combobox
        for(int i = 0; i<model_list2.size(); i++)
          {
            buildingBox.addItem(model_list2.get(i).toString());
          }


        //close frame
        frame.dispose();
      }
    });


    //add the panel into the frame and set the frame size/title

    //add buttons into the panel and set the frame size/title
    panel.add(add);
    panel.add(save);
    panel.add(remove);
    frame.setContentPane(panel); //add this panel to the frame
    frame.setLocationRelativeTo(null); //to make frame in screen center
    frame.setTitle("Choose department's Building");
    frame.setSize(490, 400);


    //veiw the frame when user click on the change button
    frame.setVisible(true);
    
    }//GEN-LAST:event_ChangeActionPerformed

    private void buildingtextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_buildingtextFocusGained
    // TODO add your handling code here:
    }//GEN-LAST:event_buildingtextFocusGained

    private void buildingtextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_buildingtextFocusLost
    // TODO add your handling code here:
    }//GEN-LAST:event_buildingtextFocusLost

    private void buildingtextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildingtextActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_buildingtextActionPerformed

  private void SearchTextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SearchTextActionPerformed
  {//GEN-HEADEREND:event_SearchTextActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_SearchTextActionPerformed

  private void departTableMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_departTableMouseEntered
  {//GEN-HEADEREND:event_departTableMouseEntered
    // TODO add your handling code here:

  }//GEN-LAST:event_departTableMouseEntered

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
    DeletePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_DeleteMouseMoved

  private void clearMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseMoved
  {//GEN-HEADEREND:event_clearMouseMoved

    // change clear panel color
    ClearPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_clearMouseMoved

  private void ChangeMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ChangeMouseMoved
  {//GEN-HEADEREND:event_ChangeMouseMoved
   
    // change change panel color
    ChangePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_ChangeMouseMoved

  private void RefreshMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseMoved
  {//GEN-HEADEREND:event_RefreshMouseMoved

    // change refresh panel color
    RefreshPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_RefreshMouseMoved

  private void clearMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseExited
  {//GEN-HEADEREND:event_clearMouseExited

    // reset clear panel color
    ClearPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_clearMouseExited

  private void DeleteMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteMouseExited
  {//GEN-HEADEREND:event_DeleteMouseExited

    // reset delete panel color
    DeletePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_DeleteMouseExited

  private void SaveMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseExited
  {//GEN-HEADEREND:event_SaveMouseExited

    // reset clear panel color
    SavePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_SaveMouseExited

  private void ChangeMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ChangeMouseExited
  {//GEN-HEADEREND:event_ChangeMouseExited

    // reset change panel color
    ChangePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_ChangeMouseExited

  private void RefreshMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseExited
  {//GEN-HEADEREND:event_RefreshMouseExited

    // reset refresh panel color
    RefreshPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_RefreshMouseExited

  private void searchMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseExited
  {//GEN-HEADEREND:event_searchMouseExited

    // reset search panel color
    searchPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_searchMouseExited

  private void ImportButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ImportButtonMouseMoved
  {//GEN-HEADEREND:event_ImportButtonMouseMoved

    // change color icon by change image to one has diff. color
    ImportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_xls_import_48px_1.png"))); 
    
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

    //Define department Excel object
    DepartmentExcel read = new DepartmentExcel(user);
    
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
        //get import courses from read_data method of excel
        List<DepartmentDto> import_departments = read.read_data(filePath);

      //if there is any result
      if(import_departments!=null)
        {

          //insert reading building to database
          for (int i=0;  i<import_departments.size() ;i++)
          {
            bao.create(import_departments.get(i), user);
          }

          // show result set in table
          setTableModel(import_departments);
          departTable.repaint();

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
          "Error",JOptionPane.ERROR_MESSAGE);
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

    //define department excel object
    DepartmentExcel excel = new DepartmentExcel(user);
    List<DepartmentDto> departments = null; //to set export departs on it

    try
      {
        //get all departments in table
        for(int i = 0; i<departTable.getRowCount(); i++)
          {
            if(departments==null)
              departments = new ArrayList<>(); //set new list if it's null

            department = new DepartmentDto(); //set new department
          
            department.setId(Integer.parseInt(departTable.getValueAt(i,
              0).toString())); //get id 
            department.setCode(departTable.getValueAt(i, 1).toString()); //get code
            department.setName(departTable.getValueAt(i, 2).toString()); //get name 

            //get building/s from table
            String[] buildings =
              departTable.getValueAt(i, 3).toString().trim().split(",");

            List<BuildingDto> buildings_list = null; //to add buildings in it
          
            for(int j = 0; j<buildings.length; j++)
              {
                //if list not created, create one
                if(buildings_list==null)
                  buildings_list = new ArrayList<>();

                building = new BuildingDto(); //set new building
                building.setCode(buildings[j]); //set its code

                buildings_list.add(building); //add it in the list
                building = null; //clear building object
              }
            //set buidings to department object
            department.setBuildings(buildings_list); 

            departments.add(department); //add this department to list

            department = null; //clear it

          }

        //create new file chooser
        JFileChooser fileChooser = new JFileChooser();
        //set current directory
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //set selection mode to directory only
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //set filename to "Departments" as a default name
        String filename = "Departments.xlsx";

        //if this data result from search
        if(!SearchText.getText().equalsIgnoreCase("Enter what do you want to search for"))
          {
            //get the search text and change filename to inculde it
            filename = SearchText.getText()+"-departments.xlsx";
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
                excel.write(filename, filePath, departments);
              
                //if it success show this message
                JOptionPane.showMessageDialog(null,
                  "Departments data export ended successfully!", "Done", 1);
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
        JOptionPane.showMessageDialog(null, "Failed to write file!",
          "Error", 0);
      }
  }//GEN-LAST:event_ExportButtonActionPerformed


  /**This method for update an existing department*/
  private void update()
  {
    if(checkValidity())
      {
        try
          {

            department = new DepartmentDto(); //set new department

            // check and pick up which buildings have be selected
            List<BuildingDto> buildings = new ArrayList<BuildingDto>();

            for(int i = 0; i<buildingBox.getItemCount(); i++)
              {

                building = new BuildingDto(); //set new buiding
                building.setCode(buildingBox.getItemAt(i)); //set its code
                buildings.add(building); //add it to the list
                building = null; //clear it 
              }

            //set department dto object attributes
            department.setId(Integer.parseInt(IdText.getText()));
            department.setCode(CodeText.getText());
            department.setName(NameText.getText());
            department.setBuildings(buildings);


            //go to business layer ad update it
            boolean flag = bao.update(department, user);

            /*if object has updated successfully */
            if(flag==true)
              {
               //show message to tell user this
                JOptionPane.showMessageDialog(null,
                  "Department has updated successfully!", "Done", 1);
                //refresh table
                setTableModel(bao.viewAll());
                departTable.repaint();
              }

            //else show message to tell user that this object does't exist in database
            else
              JOptionPane.showMessageDialog(null,
                "Department doesn't exist!", "Not Found",
                JOptionPane.ERROR_MESSAGE);
          }

        catch(Exception e)
          {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
              "Some Thing went wrong!/nPlease check your entered data. ",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);

          }
      }
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton Change;
  private javax.swing.JPanel ChangePanel;
  private javax.swing.JPanel ClearPanel;
  private javax.swing.JLabel CodeLabel1;
  private javax.swing.JTextField CodeText;
  private javax.swing.JButton Delete;
  private java.awt.Panel DeletePanel;
  private javax.swing.JPanel EditPanel;
  private javax.swing.JButton ExportButton;
  private javax.swing.JLabel IdLabel1;
  private javax.swing.JLabel IdLabel5;
  private javax.swing.JTextField IdText;
  private javax.swing.JButton ImportButton;
  private javax.swing.JLabel NameLabel1;
  private javax.swing.JTextField NameText;
  private javax.swing.JButton Refresh;
  private javax.swing.JPanel RefreshPanel;
  private javax.swing.JButton Save;
  private javax.swing.JPanel SavePanel;
  private javax.swing.JTextField SearchText;
  private javax.swing.JComboBox<String> buildingBox;
  private javax.swing.JTextField buildingtext;
  private javax.swing.JComboBox<String> c;
  private javax.swing.JButton clear;
  private javax.swing.JTable departTable;
  private javax.swing.JComboBox<String> jComboBox1;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSeparator jSeparator1;
  private java.awt.Label no_of_rows;
  private javax.swing.JButton search;
  private javax.swing.JPanel searchPanel;
  // End of variables declaration//GEN-END:variables

}
