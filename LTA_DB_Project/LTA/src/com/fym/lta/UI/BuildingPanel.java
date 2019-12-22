
package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.BuildingBao;
import com.fym.lta.BAO.RoleScreenBao;
import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.UserDto;
import com.fym.lta.Excel.BuildingExcel;

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
 * @author Islam
 */
public class BuildingPanel extends javax.swing.JPanel
{
  @SuppressWarnings({ "compatibility:5549122475143851583",
      "oracle.jdeveloper.java.serialversionuid-stale" })

  private static final long serialVersionUID = 1L;

  /**Definitions*/
  //define user dto as "user"
  private static UserDto user;
  //define building bao as "building_bao"
  private static BuildingBao building_bao;
  //define building dto as "building"
  private static BuildingDto building; 
  
  /**constructor to create new BuildingPanel
   * @param user object */
  public BuildingPanel(UserDto login_user)
  {
    try
      {
        //set taken user object to the private one
        user = login_user;
      
        //set panel components
        initComponents();
      
        //use user access method to check user validity on this screen 
        userAccess();

        //initialization "bao" 
        building_bao = new BaoFactory().createBuildingBao(user);
      
        //view all existing buildings
        setTableModel(building_bao.ListAll());

      }
    catch(Exception e)
      {
        // print this exception
        e.printStackTrace();
      }

  }

  /** set building table model 
   *@param building dto list */
  private void setTableModel(List<BuildingDto> buildings)
  {
    
    int size = 0; //for table row size
    
    //if given list not empty or null
    if(buildings!=null&&!buildings.isEmpty())
      {
        //set table size to this list size 
        size = buildings.size();
      }
    
    /*this (2D) array of objects for table model 
     *the first D for rows, second for columns */
    Object[][] buildArr = new Object[size][4];
    
    for(int i = 0; i<size; i++) //for each row
      {
        buildArr[i][0] = buildings.get(i).getId(); //set id
        buildArr[i][1] = buildings.get(i).getCode(); //set code 
        buildArr[i][2] = buildings.get(i).getDescription(); //set description
        buildArr[i][3] = buildings.get(i).getFloorsNo(); //set no. of floors
      }
    
    //set table model using this array
    BuildingTable.setModel(new javax.swing.table.DefaultTableModel(buildArr,
        new String[] { "Id", "Code", "Description", "Floors number" }));


    //change table header rendering
     BuildingTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
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

    //label under tabel show buildings count viewed in table
    no_of_rows.setText("Table result: "+
      Integer.toString(BuildingTable.getRowCount())+"  Buildings");

  }


/**This method use to check user validity on this screen
 * if user has full access or viewonly access*/
  private void userAccess()
  {
    //set user screen name to "Building" to make check on it
    user.setScreen_name("Building");

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


  /**For update an existing building*/
  private void update()
  {
    try
      {
        if(checkValidity())
          {
           
            building = new BuildingDto(); //set new builing dto to "building"
            building.setCode(CodeText.getText()); //get code
            building.setId(Integer.parseInt(IdText.getText())); //get id
            building.setDescription(DesText.getText()); //get description

            //try update this building
            if(building_bao.update(building, user))
              { 
                //if update done show message to inform user that
                JOptionPane.showMessageDialog(null,
                  "Building updated Successfully", "Done", 1);
                
                //refresh table
                setTableModel(building_bao.ListAll());
                BuildingTable.repaint();
                
              }
            else  //if update failed
              {
                //show message to tell user this buiding does'nt exist
                JOptionPane.showMessageDialog(null,
                  "This Building does'nt exist!", "Not Found",
                  JOptionPane.ERROR_MESSAGE);
                BuildingTable.repaint();
              }
          }

      }
    catch(Exception e)
      {
        e.printStackTrace();
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
    BuildingTable = new javax.swing.JTable();
    SearchText = new javax.swing.JTextField();
    EditPanel = new javax.swing.JPanel();
    IdText = new javax.swing.JTextField();
    IdLabel = new javax.swing.JLabel();
    CodeText = new javax.swing.JTextField();
    CodeLabel = new javax.swing.JLabel();
    DesText = new javax.swing.JTextField();
    NameLabel = new javax.swing.JLabel();
    LocationSectionImage = new javax.swing.JLabel();
    SavePanel = new javax.swing.JPanel();
    Save = new javax.swing.JButton();
    DeletePanel = new javax.swing.JPanel();
    Delete = new javax.swing.JButton();
    ClearPanel = new javax.swing.JPanel();
    clear = new javax.swing.JButton();
    no_of_rows = new java.awt.Label();
    title = new javax.swing.JLabel();
    jSeparator1 = new javax.swing.JSeparator();
    searchPanel = new javax.swing.JPanel();
    search = new javax.swing.JButton();
    RefreshPanel = new javax.swing.JPanel();
    Refresh = new javax.swing.JButton();
    ImportButton = new javax.swing.JButton();
    ExportButton = new javax.swing.JButton();

    setBackground(new java.awt.Color(245, 245, 245));
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    BuildingTable.setAutoCreateRowSorter(true);
    BuildingTable.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    BuildingTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {

      },
      new String []
      {
        "Id", "Code", "Description"
      }
    )
    {
      boolean[] canEdit = new boolean []
      {
        false, false, false
      };

      public boolean isCellEditable(int rowIndex, int columnIndex)
      {
        return canEdit [columnIndex];
      }
    });
    BuildingTable.setFillsViewportHeight(true);
    BuildingTable.setRowHeight(25);
    BuildingTable.setSelectionBackground(new java.awt.Color(0, 153, 204));
    BuildingTable.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        BuildingTableMouseClicked(evt);
      }
    });
    BuildingTable.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyPressed(java.awt.event.KeyEvent evt)
      {
        BuildingTableKeyPressed(evt);
      }
    });
    jScrollPane1.setViewportView(BuildingTable);
    if (BuildingTable.getColumnModel().getColumnCount() > 0)
    {
      BuildingTable.getColumnModel().getColumn(0).setHeaderValue("Id");
      BuildingTable.getColumnModel().getColumn(1).setHeaderValue("Code");
      BuildingTable.getColumnModel().getColumn(2).setHeaderValue("Description");
    }

    add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 700, 750));

    SearchText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    SearchText.setText("What do you want to search ?");
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
    add(SearchText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 620, 50));

    EditPanel.setBackground(new java.awt.Color(245, 245, 245));
    EditPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    IdText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    IdText.setText("Enter Building ID");
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
    EditPanel.add(IdText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 570, 60));

    IdLabel.setBackground(new java.awt.Color(0, 51, 204));
    IdLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    IdLabel.setForeground(new java.awt.Color(0, 51, 204));
    IdLabel.setText("ID");
    EditPanel.add(IdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 166, 20, 40));

    CodeText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    CodeText.setText("Enter Building code");
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
    CodeText.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        CodeTextActionPerformed(evt);
      }
    });
    EditPanel.add(CodeText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 570, 60));

    CodeLabel.setBackground(new java.awt.Color(0, 51, 204));
    CodeLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    CodeLabel.setForeground(new java.awt.Color(0, 51, 204));
    CodeLabel.setText("Code");
    EditPanel.add(CodeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 70, 60));

    DesText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    DesText.setText("Enter Building Description");
    DesText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    DesText.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        DesTextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        DesTextFocusLost(evt);
      }
    });
    EditPanel.add(DesText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 410, 570, 60));

    NameLabel.setBackground(new java.awt.Color(0, 51, 204));
    NameLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    NameLabel.setForeground(new java.awt.Color(0, 51, 204));
    NameLabel.setText("Description");
    EditPanel.add(NameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, -1, 50));

    LocationSectionImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_geo_fence_128px.png"))); // NOI18N
    EditPanel.add(LocationSectionImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 140, 130));

    SavePanel.setBackground(new java.awt.Color(0, 129, 211));
    SavePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
    SavePanel.add(Save, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 50));

    EditPanel.add(SavePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 530, 100, 50));

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
      .addComponent(Delete, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
    );
    DeletePanelLayout.setVerticalGroup(
      DeletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeletePanelLayout.createSequentialGroup()
        .addGap(0, 3, Short.MAX_VALUE)
        .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    EditPanel.add(DeletePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 530, -1, 50));

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
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ClearPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(clear, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
    );
    ClearPanelLayout.setVerticalGroup(
      ClearPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(clear, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(ClearPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 530, -1, 50));

    add(EditPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 140, 650, 770));

    no_of_rows.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
    no_of_rows.setForeground(new java.awt.Color(135, 135, 135));
    no_of_rows.setText("no of rows");
    add(no_of_rows, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 860, 220, 20));

    title.setBackground(new java.awt.Color(231, 78, 123));
    title.setFont(new java.awt.Font("VIP Rawy Regular", 0, 36)); // NOI18N
    title.setForeground(new java.awt.Color(231, 78, 123));
    title.setText("Building");
    add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 70, 240, 70));
    add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 130, 600, 20));

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
      .addComponent(search, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
    );
    searchPanelLayout.setVerticalGroup(
      searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    add(searchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 70, 50));

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
      .addComponent(Refresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
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
    add(ImportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 860, 80, 70));

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
    add(ExportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 860, 80, 70));
  }//GEN-END:initComponents

    private void IdTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusGained
    // TODO add your handling code here:
    if(IdText.getText().equalsIgnoreCase("Enter Building ID"))
      {
        IdText.setText("");
      }

    IdText.setBorder(new LineBorder(Color.decode("#0081D3"), 2, true));

    }//GEN-LAST:event_IdTextFocusGained

    private void IdTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusLost
    // TODO add your handling code here:
    if(IdText.getText().trim().equalsIgnoreCase(""))
      {
        IdText.setText("Enter Building ID");
      }

    IdText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2, true));

    }//GEN-LAST:event_IdTextFocusLost

    private void CodeTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodeTextFocusGained
    // TODO add your handling code here:
    if(CodeText.getText().equalsIgnoreCase("Enter Building code"))
      {
        CodeText.setText("");
      }

    CodeText.setBorder(new LineBorder(Color.decode("#0081D3"), 2, true));

    }//GEN-LAST:event_CodeTextFocusGained

    private void CodeTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodeTextFocusLost
    // TODO add your handling code here:
    if(CodeText.getText().trim().equalsIgnoreCase(""))
      {
        CodeText.setText("Enter Building code");
      }

    CodeText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2, true));

    }//GEN-LAST:event_CodeTextFocusLost

    private void DesTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_DesTextFocusGained
    // TODO add your handling code here:
      
    if(DesText.getText().equalsIgnoreCase("Enter Building Description"))
      {
        DesText.setText("");
      }

    DesText.setBorder(new LineBorder(Color.decode("#0081D3"), 2, true));

    }//GEN-LAST:event_DesTextFocusGained

    private void DesTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_DesTextFocusLost
    // TODO add your handling code here:
    if(DesText.getText().trim().equalsIgnoreCase(""))
      {
        DesText.setText("Enter Building Description");
      }

    DesText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2, true));

    }//GEN-LAST:event_DesTextFocusLost

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed

    //if the set text update
    if(Save.getText().equalsIgnoreCase("Update"))
      update(); //use update method

    else  //else try insert this building
      {
        try
          {

            if(checkValidity())  //check if entered data is valid
              {

                building =
                  new BuildingDto(); //set new builing dto to "building"
                building.setCode(CodeText.getText()); //get code
                building.setId(Integer.parseInt(IdText.getText())); //get id
                building.setDescription(DesText.getText()); //get description

                if(building_bao.insert(building, user)) //try insert it
                  {
                  
                    //if it success show message to tell user that
                    JOptionPane.showMessageDialog(null,
                      "Building inserted Successfully", "Done", 1);
                    
                    //refresh table
                    setTableModel(building_bao.ListAll()); 
                    BuildingTable.repaint();
                    
                    //set id text disable (id can't be edited after insert)
                    IdText.setEnabled(false);
                    
                    //set save button text to "Update"
                    Save.setText("Update");
                  }
                else // if it failed it mean this building is already exit 
                  {
                    /*so show message to ask user if he want 
                    * to update this building or not*/
                    int reply =
                      JOptionPane.showConfirmDialog(null,
                        "This Building is already exist!\n"+
                        "Do you want to update it?", "Warning",
                        JOptionPane.YES_NO_OPTION);

                    //update object if user choose yes
                    if(reply==JOptionPane.YES_OPTION)
                      {
                        update(); //use update method
                        
                        //set save button text to "Update"
                        Save.setText("Update");
                        
                        //set id text disable
                        IdText.setEnabled(false);
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

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed

    //Show confirm message
    int reply =
      JOptionPane.showConfirmDialog(null,
      "Are you sure to delete this building?\n"+
      "All things depend on it will be deleted!", "Warning",
      JOptionPane.YES_NO_OPTION);

    //delete object if user choose yes
    if(reply==JOptionPane.YES_OPTION)
      {
      
        try
          {
            building = new BuildingDto();//set new builing dto to "building"
            building.setId(Integer.parseInt(IdText.getText())); //get id

            //go to business layer
            boolean flag = building_bao.delete(building);

            /* if object has deleted successfully */
            if(flag==true)
              {
                // show message to tell user this
                JOptionPane.showMessageDialog(null,
                  "Building deleted successfully!", "Done", 1);

                // repaint the table
                setTableModel(building_bao.ListAll());
                BuildingTable.repaint();
                
                IdText.setEnabled(true); //set id text enabled again
                
                Save.setText("Insert"); //set save button text to insert

              }

            //else show message to tell user that this object does't exist in database
            else
              JOptionPane.showMessageDialog(null, "Building doesn't exist!",
                "Not Found", JOptionPane.ERROR_MESSAGE);
          }
        catch(Exception e)
          {
            // print exception
            e.printStackTrace();
            //for non expected error
            JOptionPane.showMessageDialog(null,
              "Some Thing went wrong!\nPlease check your entered data. ",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);

          }
      }
    }//GEN-LAST:event_DeleteActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed

    try
      {
        //repaint table
        setTableModel(building_bao.ListAll());
        BuildingTable.repaint();
      
        IdText.setEnabled(true); //enable text id box
        Save.setText("Insert");  //set save button text to insert

        //set default texts for text boxes
        IdText.setText("Enter Building ID");
        CodeText.setText("Enter Building code");
        DesText.setText("Enter Building Description");

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    }//GEN-LAST:event_RefreshActionPerformed

    private void SearchTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusGained
    // TODO when user get focus on search text field 
      
    //if found text is the default text
    if(SearchText.getText().equalsIgnoreCase("What do you want to search ?"))
      {
        SearchText.setText(""); //remove it
      }

    //change border color 
    SearchText.setBorder(new LineBorder(Color.decode("#0081D3"), 2,true));

    }//GEN-LAST:event_SearchTextFocusGained

    private void SearchTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusLost
    // TODO when user focus out from search text field
    
    //if there is no text
    if(SearchText.getText().trim().equalsIgnoreCase(""))
      {
        //set the default one
        SearchText.setText("What do you want to search ?");
      }

    //reset border color
    SearchText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2, true));

    }//GEN-LAST:event_SearchTextFocusLost
  // Search Button
    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed

    try
      {
      
        // Check if the Search field is empty
        if(SearchText.getText().equalsIgnoreCase("What do you want to search ?"))
          {
           
            //Show message tell user that Search Field is empty
            JOptionPane.showMessageDialog(null, "Search field is empty",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            
            //throw exception
            throw new IllegalArgumentException("Search field is empty");
          }

        building = new BuildingDto(); //set new builing dto to "building"
       
        List<BuildingDto> buildings =
          null; //create building dto list to put search result in it

      
        building.setSearch(SearchText.getText()); //get search text

        buildings = building_bao.searchFor(building); //get search result

        if(buildings!=null) //if there is result
          { 
            // show result set in table
            setTableModel(buildings);
            BuildingTable.repaint();
          }
        else
          { 
            //if there is no search result show message to tell user that
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

  private void CodeTextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_CodeTextActionPerformed
  {//GEN-HEADEREND:event_CodeTextActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_CodeTextActionPerformed

  private void clearActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_clearActionPerformed
  {//GEN-HEADEREND:event_clearActionPerformed
    // TODO add your handling code here:

    //enable id text box again
    IdText.setEnabled(true);
    
    Save.setText("Insert"); //set save button text to insert

    //set default texts for text boxes
    IdText.setText("Enter Building ID");
    CodeText.setText("Enter Building code");
    DesText.setText("Enter Building Description");
    
  }//GEN-LAST:event_clearActionPerformed

  private void BuildingTableKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_BuildingTableKeyPressed
  {//GEN-HEADEREND:event_BuildingTableKeyPressed
   
    int i = BuildingTable.getSelectedRow(); //get selected row 

    //Because "BuildingTable.getSelectedRow()" give the previous selected row
    if(evt.getExtendedKeyCode()==KeyEvent.VK_UP)
      i--; //for up key decrement
    else if(evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
      i++; // for down key increment
    
    /**move up and down in table to get data into edit space*/
    try
      {
        if(evt.getExtendedKeyCode()==KeyEvent.VK_UP||
          evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
          {
          
            IdText.setEnabled(false); //set id text disable
            Save.setText("Update");   //set save button text "Update"
            
            // set the attributes of selected row to text boxes
            IdText.setText(BuildingTable.getValueAt(i, 0).toString());
            CodeText.setText(BuildingTable.getValueAt(i, 1).toString());
            DesText.setText(BuildingTable.getValueAt(i, 2).toString());

          }
      }

    catch(java.lang.ArrayIndexOutOfBoundsException e)
      {
        e.printStackTrace();
      }


    /**delete selected object when press delete*/
    if(evt.getExtendedKeyCode()==KeyEvent.VK_DELETE)
      {

        try
          {

            //For one selected row in table
            if(BuildingTable.getSelectedRowCount()==1)
              {


                //Show confirm message
                int reply =
                  JOptionPane.showConfirmDialog(null,
                    "Are you sure to delete this building?\n"+
                    "All things depend on it will be deleted!", "Warning",
                    JOptionPane.YES_NO_OPTION);


                //delete object if user choose yes
                if(reply==JOptionPane.YES_OPTION)
                  {
                    building = new BuildingDto(); //set new builing dto to "building"
                  
                    //get selected building id from table
                    int id =
                      Integer.parseInt(BuildingTable.getValueAt(BuildingTable.getSelectedRow(),
                          0).toString());
                    
                    building.setId(id); //set it to building object

                    //delete it using bao delete method
                    if(building_bao.delete(building)) 
                      {
                        //if it deleted sucessfilly show message to tell user that
                        JOptionPane.showMessageDialog(null,
                          "Building Deleted Successfully");
                        
                        //refresh table
                        setTableModel(building_bao.ListAll());
                        BuildingTable.repaint();
                        
                        IdText.setEnabled(true); //enable id text field
                        Save.setText("Insert");  //set save button text to insert
                        
                      }

                    else
                      //if bao method return false (building not be deleted)
                      JOptionPane.showMessageDialog(null,
                        "Can't delete object", "Error",
                        JOptionPane.ERROR_MESSAGE);
                  }

              }
            //if there is no selected row 
            else if(BuildingTable.getSelectedRowCount()==0)
              {
                //show message to ask user to select a row
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
  }//GEN-LAST:event_BuildingTableKeyPressed

  private void SearchTextKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_SearchTextKeyPressed
  {//GEN-HEADEREND:event_SearchTextKeyPressed
   
    if(evt.getExtendedKeyCode()==KeyEvent.VK_ENTER)
      {

        try
          {
            // Check if the Search field is empty
            if(SearchText.getText().equalsIgnoreCase(""))
              {
                //Show message tell user that Search Field is empty
                JOptionPane.showMessageDialog(null, "Search field is empty",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                //throw exception
                throw new IllegalArgumentException("Search field is empty");
              }

           building = new BuildingDto(); //set new building dto
           List<BuildingDto> buildings =
              null; //create building dto list to put search result in it

            building.setSearch(SearchText.getText()); //get search text

            buildings = building_bao.searchFor(building); //get search result

            if(buildings!=null)
              { //if there is result
                // show result set in table
                setTableModel(buildings);
                BuildingTable.repaint();
              }
            else
              { //if there is no search result
                JOptionPane.showMessageDialog(null,
                  "There is no search result for: "+SearchText.getText(),
                  "Invalid search", 1);
              }

          }
        catch(Exception e)
          {
            e.printStackTrace();
          }
      }
  }//GEN-LAST:event_SearchTextKeyPressed

  private void BuildingTableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_BuildingTableMouseClicked
  {//GEN-HEADEREND:event_BuildingTableMouseClicked
    
    IdText.setEnabled(false);  //set id text field disable
    Save.setText("Update");   //set save button text to "update"
    
    int i = BuildingTable.getSelectedRow(); //get selected row 

    // set attributes of selected row to text boxes
    IdText.setText(BuildingTable.getValueAt(i, 0).toString());
    CodeText.setText(BuildingTable.getValueAt(i, 1).toString());
    DesText.setText(BuildingTable.getValueAt(i, 2).toString());
    
  }//GEN-LAST:event_BuildingTableMouseClicked

  private void searchMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseMoved
  {//GEN-HEADEREND:event_searchMouseMoved
    
    // change search panel color when user move mouse on search button
    searchPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_searchMouseMoved

  private void clearMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseMoved
  {//GEN-HEADEREND:event_clearMouseMoved
   
    // change clear panel color when user move mouse on clear button
    ClearPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_clearMouseMoved

  private void DeleteMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteMouseMoved
  {//GEN-HEADEREND:event_DeleteMouseMoved
   
    // change delete panel color when user move mouse on delete button
    DeletePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_DeleteMouseMoved

  private void SaveMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseMoved
  {//GEN-HEADEREND:event_SaveMouseMoved
    
    // change save panel color when user move mouse on save button
    SavePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_SaveMouseMoved

  private void RefreshMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseMoved
  {//GEN-HEADEREND:event_RefreshMouseMoved
   
    // change refresh panel color when user move mouse on refresh button
    RefreshPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_RefreshMouseMoved

  private void RefreshMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseExited
  {//GEN-HEADEREND:event_RefreshMouseExited

    // reset refresh panel color when user exit mouse from refresh button
    RefreshPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_RefreshMouseExited

  private void searchMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseExited
  {//GEN-HEADEREND:event_searchMouseExited
    
    // reset search panel color when user exit mouse from search button
    searchPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_searchMouseExited

  private void clearMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseExited
  {//GEN-HEADEREND:event_clearMouseExited

    // reset clear panel color when user exit mouse from clear button
    ClearPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_clearMouseExited

  private void DeleteMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteMouseExited
  {//GEN-HEADEREND:event_DeleteMouseExited

    // reset delete panel color when user exit mouse from delete button
    DeletePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_DeleteMouseExited

  private void SaveMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseExited
  {//GEN-HEADEREND:event_SaveMouseExited

    // reset save panel color when user exit mouse from save button
    SavePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_SaveMouseExited

  private void SearchTextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SearchTextActionPerformed
  {//GEN-HEADEREND:event_SearchTextActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_SearchTextActionPerformed

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
    
    //define BuildingExcel object
    BuildingExcel excel = new BuildingExcel(user);
    
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

    if(result==JFileChooser.APPROVE_OPTION)  //if user choose APPROVE option
      {
        //create new file to get file selected by user
        File file = fileChooser.getSelectedFile();
        //get file path
        String filePath = file.getAbsolutePath();

        try
          {
            //get import builidings from read_data method of excel
            List<BuildingDto> import_buildings = excel.read_data(filePath);
            
            //if there is any result
            if(import_buildings!=null)
            {
              
              //insert reading buildings to database 
              for (int i=0;  i<import_buildings.size() ;i++)
              {
                  building_bao.insert(import_buildings.get(i), user);
              }

                // show result set in table
                setTableModel(import_buildings);
                BuildingTable.repaint();
              
            }

          }
        
        catch(Exception e)
          {
          /*if any exception occur 
          show message to ask user check the input file*/
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
    
    // create building excel object
    BuildingExcel excel = new BuildingExcel(user);
    //to set export buildings on it
    List<BuildingDto> buildings = null;  

    try
      {
        //get all buildings in table
        for(int i=0; i<BuildingTable.getRowCount() ; i++)
        {
          if(buildings==null)
             buildings = new ArrayList<BuildingDto>();
          
          //get each building attributes
          building = new BuildingDto();
          building.setId(Integer.parseInt(BuildingTable.getValueAt(i, 0).toString()));
          building.setCode(BuildingTable.getValueAt(i, 1).toString());
          building.setDescription(BuildingTable.getValueAt(i, 2).toString());
          
          buildings.add(building);
          
          building=null;
          
          }
        
        //create new file chooser
        JFileChooser fileChooser = new JFileChooser();
        //set current directory
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //set selection mode to directory only 
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //set file name to "buildings" as a default 
        String filename = "buildings.xlsx";

        //if this data result from search
        if(!SearchText.getText().equalsIgnoreCase("What do you want to search ?"))
           {
            //get the search text and change filename to inculde it
            filename = SearchText.getText()+"-Buildings.xlsx";
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
                excel.write(filename, filePath, buildings);
               
                //if it success show this message
                JOptionPane.showMessageDialog(null,
                  "Buildings data export ended successfully!", "Done", 1);
              }
            catch(Exception e)
              {
                e.printStackTrace();
                //if any exception occur 
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
    
  /**This method to check validity of user entered data
   * @return true if all data entered as expected, false if not  */
  private boolean checkValidity()
  {
    try
      {
        /** check for empty entered data */
        //for default id text
        if(IdText.getText().equalsIgnoreCase("Enter Building ID"))
          {
            //ask user enter id
            JOptionPane.showMessageDialog(null, "Please, enter building id",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            
            //throw this exception
            throw new IllegalArgumentException("id text is empry");
          }
        //for code default text
        else if(CodeText.getText().equalsIgnoreCase("Enter Building code"))

          {
            //ask user enter code 
            JOptionPane.showMessageDialog(null,
              "Please, enter building code", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            
            //throw this exception
            throw new IllegalArgumentException("code text is empty");
          }
        //for description default text
        else if(DesText.getText().equalsIgnoreCase("Enter Building Description"))
          {
            //ask user enter description
            JOptionPane.showMessageDialog(null,
              "Please, enter building description", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            
            //throw this exception
            throw new IllegalArgumentException("description text is empty");
          }

        /**check validity of id*/
        //Check for the entered id is a positive number
        int id = Integer.parseInt(IdText.getText());
        if(id<1)
          {
            //ask user to enter positive number
            JOptionPane.showMessageDialog(null,
              "Invalid Id \n\n(ID is only Positive Numbers)",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            
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
              CodeText.getText().charAt(i)!='_'&&
              CodeText.getText().charAt(i)!='-'&&
              CodeText.getText().charAt(i)!='.'&&
              CodeText.getText().charAt(i)!=' ')
              {
                //show message to tell user  that
                JOptionPane.showMessageDialog(null,
                  "Invalid code format\n\n(code can only be a sequence of Unicode letters and digits separated by (-,_,.))",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
              
                //throw this exception
                throw new IllegalArgumentException("code can't contain stranger symbols");

              }
          }


        return true; //all data correct

      }
    catch(java.lang.NumberFormatException e) //occur when id isn't a number
      {
        e.printStackTrace();
        
        //ask user to enter number
        JOptionPane.showMessageDialog(null, "Please enter number for ID",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
        
        return false; // data is invalid
      }

    // catch the illagal thrown exceptions
    catch(IllegalArgumentException e)
      {
        //print this exception
        e.printStackTrace();
        return false; // data is invalid
      }

    //if there is any other non expecting error
    catch(Exception e)
      { 
        //print this exception
        e.printStackTrace();

        //ask user to check data
        JOptionPane.showMessageDialog(null,
          "Some Thing went wrong!\n\nPlease check your entered data. ",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
        
        return false; // data is invalid
        
      }

  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTable BuildingTable;
  private javax.swing.JPanel ClearPanel;
  private javax.swing.JLabel CodeLabel;
  private javax.swing.JTextField CodeText;
  private javax.swing.JButton Delete;
  private javax.swing.JPanel DeletePanel;
  private javax.swing.JTextField DesText;
  private javax.swing.JPanel EditPanel;
  private javax.swing.JButton ExportButton;
  private javax.swing.JLabel IdLabel;
  private javax.swing.JTextField IdText;
  private javax.swing.JButton ImportButton;
  private javax.swing.JLabel LocationSectionImage;
  private javax.swing.JLabel NameLabel;
  private javax.swing.JButton Refresh;
  private javax.swing.JPanel RefreshPanel;
  private javax.swing.JButton Save;
  private javax.swing.JPanel SavePanel;
  private javax.swing.JTextField SearchText;
  private javax.swing.JButton clear;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSeparator jSeparator1;
  private java.awt.Label no_of_rows;
  private javax.swing.JButton search;
  private javax.swing.JPanel searchPanel;
  private javax.swing.JLabel title;
  // End of variables declaration//GEN-END:variables

}
