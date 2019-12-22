
package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.BuildingBao;
import com.fym.lta.BAO.FloorBao;
import com.fym.lta.BAO.LocationBao;
import com.fym.lta.BAO.LocationTypeBao;
import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.RoleScreenDao;
import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.FloorDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.UserDto;
import com.fym.lta.Excel.LocationExcel;

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
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Islam
 */
public class LocationPanel extends javax.swing.JPanel
{
  @SuppressWarnings({ "compatibility:478108509781372152",
      "oracle.jdeveloper.java.serialversionuid-stale" })
  private static final long serialVersionUID = 1L;
  private static UserDto user = null;

  /** Creates new form LocationJPanel */
  public LocationPanel(UserDto u)
  {
    try
      {

        user = u; //user has logged in
        initComponents();
        LocationBao location_bao =
          new BaoFactory().createLocationBao(user); //Create location bao object

        //set default data to edit space components
        defaultdata();

        viewonly(user); //for if user have view only access

        //view all existing location in database in table
        setTableModel(location_bao.ListAll());


      }
    catch(Exception e)
      {
        // TODO: Add catch code
        e.printStackTrace();
      }
  }

  /** to set default data for edit space panel
   */
  private void defaultdata()
  {

    //enable text boxe again
    IdText.setEnabled(true);
    Save.setText("Save");

    //set default text for text boxes
    IdText.setText("Enter Location ID");
    CodeText.setText("Enter Location Code");
    NameText.setText("Enter Location Name");
    CapText.setText("Enter Location Capacity");


    //Set all existing location type to location type combobox
    try
      {
        LocationTypeBao loc_Bao =
          new BaoFactory().createLocationTypeBao(user); //create location type bao object
        List<LocationTypeDto> loc_list =
          loc_Bao.viewAll(); //get all location types

        location_type_combo.removeAllItems(); //remove all existing data in location combobox

        //set location type codes to the location type combobox
        if(loc_list!=null&&!loc_list.isEmpty())
          {
            for(int i = 0; i<loc_list.size(); i++)
              {
                location_type_combo.addItem(loc_list.get(i).getCode());
              }
            location_type_combo.setSelectedIndex(-1); //select nothing in this combo

          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

    //set all existing builings in db to building combobox
    try
      {
        BuildingBao build_bao =
          new BaoFactory().createBuildingBao(user); //create building bao object
        List<BuildingDto> build_list =
          build_bao.ListAll(); //get all building from DB

        building_combo.removeAllItems(); //remove all item from building combobox

        //set buildings code to building combo
        if(build_list!=null&&!build_list.isEmpty())
          {
            for(int i = 0; i<build_list.size(); i++)
              {
                building_combo.addItem(build_list.get(i).getCode());
              }

            building_combo.setSelectedIndex(-1); //select nothing in this combo
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

    //remove all items in floor combobox
    floor_combo.removeAllItems();
  }


  /**For view only user access */
  private void viewonly(UserDto u)
  {

    u.setScreen_name("Location");

    RoleScreenDao dao = new DaoFactory().createRoleScreenDao(user);
    if(dao.viewonly(u))
      {
        EditPanel.setVisible(false);
        System.out.println(" \n done panel");
      }
  }


  /**Set location table model*/
  private void setTableModel(List<LocationDto> locs)
  {
    int size = 0; //for table size with default size

    //if list parameter is not null
    if(locs!=null&&!locs.isEmpty())
      {
        size = locs.size();
      }

    //view data in table
    Object[][] buildArr = new Object[size][8];
    for(int i = 0; i<size; i++)
      {
        buildArr[i][0] = locs.get(i).getId();
        buildArr[i][1] = locs.get(i).getCode();
        buildArr[i][2] = locs.get(i).getName();
        buildArr[i][3] = locs.get(i).getCapacity();
        buildArr[i][6] = locs.get(i).getType().getCode();
        buildArr[i][4] = locs.get(i).getFloor().getCode();
        buildArr[i][5] = locs.get(i).getBuild().getCode();
      }
    LocationTable.setModel(new javax.swing.table.DefaultTableModel(buildArr,
        new String[] { "Id", "Code", "Name", "Capacity", "Floor",
        "Building", "Type" }));


    //change header color
    LocationTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
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


    //color each location according its type

    LocationTable.getColumnModel().getColumn(6).setCellRenderer(new TableCellRenderer()
    {
      @Override
      public Component getTableCellRendererComponent(JTable jTable,
        Object object, boolean b, boolean b2, int row, int column)
      {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        Component comp =
          renderer.getTableCellRendererComponent(jTable, object, b, b2, row,
          column);
        String colorStr =
          locs.get(row).getType().getColor(); //get location type color
        colorStr =
          "#".concat(colorStr); //convert it to color code "#ffffff"

        System.out.println(colorStr);
        comp.setBackground(Color.decode(colorStr.trim())); //set cell color
        return comp;
      }
    });

    //label under tabel show types count viewed in table
    no_of_rows.setText("Table result: "+
      Integer.toString(LocationTable.getRowCount())+"  locations");
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
    LocationTable = new javax.swing.JTable();
    SearchText = new javax.swing.JTextField();
    EditPanel = new javax.swing.JPanel();
    NameLabel = new javax.swing.JLabel();
    floor_combo = new javax.swing.JComboBox<>();
    CodeText = new javax.swing.JTextField();
    IdLabel = new javax.swing.JLabel();
    CodeLabel = new javax.swing.JLabel();
    IdText = new javax.swing.JTextField();
    NameText = new javax.swing.JTextField();
    IdLabel1 = new javax.swing.JLabel();
    IdLabel2 = new javax.swing.JLabel();
    IdLabel3 = new javax.swing.JLabel();
    IdLabel4 = new javax.swing.JLabel();
    building_combo = new javax.swing.JComboBox<>();
    location_type_combo = new javax.swing.JComboBox<>();
    jLabel7 = new javax.swing.JLabel();
    CapText = new javax.swing.JTextField();
    SavePanel = new javax.swing.JPanel();
    Save = new javax.swing.JButton();
    DeletePanel = new javax.swing.JPanel();
    Delete = new javax.swing.JButton();
    ClearPanel = new javax.swing.JPanel();
    clear = new javax.swing.JButton();
    jSeparator1 = new javax.swing.JSeparator();
    no_of_rows = new java.awt.Label();
    searchPanel = new javax.swing.JPanel();
    Search = new javax.swing.JButton();
    RefreshPanel = new javax.swing.JPanel();
    Refresh = new javax.swing.JButton();
    jLabel8 = new javax.swing.JLabel();
    ImportButton = new javax.swing.JButton();
    ExportButton = new javax.swing.JButton();

    setBackground(new java.awt.Color(245, 245, 245));
    setForeground(new java.awt.Color(0, 51, 204));
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    LocationTable.setAutoCreateRowSorter(true);
    LocationTable.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    LocationTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {

      },
      new String []
      {
        "Id", "Capacitiy", "Code", "Name", "ID Type", "Code Floor", "ID Build", "Type Color"
      }
    )
    {
      boolean[] canEdit = new boolean []
      {
        false, false, false, false, false, false, false, false
      };

      public boolean isCellEditable(int rowIndex, int columnIndex)
      {
        return canEdit [columnIndex];
      }
    });
    LocationTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    LocationTable.setFillsViewportHeight(true);
    LocationTable.setRowHeight(25);
    LocationTable.setSelectionBackground(new java.awt.Color(0, 153, 204));
    LocationTable.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        LocationTableMouseClicked(evt);
      }
    });
    LocationTable.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyPressed(java.awt.event.KeyEvent evt)
      {
        LocationTableKeyPressed(evt);
      }
    });
    jScrollPane1.setViewportView(LocationTable);
    if (LocationTable.getColumnModel().getColumnCount() > 0)
    {
      LocationTable.getColumnModel().getColumn(0).setHeaderValue("Id");
      LocationTable.getColumnModel().getColumn(1).setHeaderValue("Capacitiy");
      LocationTable.getColumnModel().getColumn(2).setHeaderValue("Code");
      LocationTable.getColumnModel().getColumn(3).setHeaderValue("Name");
      LocationTable.getColumnModel().getColumn(4).setHeaderValue("ID Type");
      LocationTable.getColumnModel().getColumn(5).setHeaderValue("Code Floor");
      LocationTable.getColumnModel().getColumn(6).setHeaderValue("ID Build");
      LocationTable.getColumnModel().getColumn(7).setHeaderValue("Type Color");
    }

    add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 750, 750));

    SearchText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    SearchText.setText("What do you want to search for?");
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
    add(SearchText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 660, 50));

    EditPanel.setBackground(new java.awt.Color(245, 245, 245));
    EditPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    NameLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    NameLabel.setForeground(new java.awt.Color(0, 51, 204));
    NameLabel.setText("Name");
    EditPanel.add(NameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, 40));

    floor_combo.setBackground(new java.awt.Color(255, 255, 255));
    floor_combo.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    floor_combo.setMaximumRowCount(100);
    floor_combo.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        floor_comboFocusGained(evt);
      }
    });
    floor_combo.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        floor_comboActionPerformed(evt);
      }
    });
    EditPanel.add(floor_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 510, 490, 50));

    CodeText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    CodeText.setText("Enter Location Code");
    CodeText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
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
    EditPanel.add(CodeText, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 490, 50));

    IdLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    IdLabel.setForeground(new java.awt.Color(0, 51, 204));
    IdLabel.setText("ID");
    EditPanel.add(IdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 35, 50));

    CodeLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    CodeLabel.setForeground(new java.awt.Color(0, 51, 204));
    CodeLabel.setText("Code");
    EditPanel.add(CodeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, 50));

    IdText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    IdText.setText("Enter Location ID");
    IdText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
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
    EditPanel.add(IdText, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 490, 50));

    NameText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    NameText.setText("Enter Location Name");
    NameText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
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
    EditPanel.add(NameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, 490, 50));

    IdLabel1.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    IdLabel1.setForeground(new java.awt.Color(0, 51, 204));
    IdLabel1.setText("Capacity");
    EditPanel.add(IdLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 580, 90, 50));

    IdLabel2.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    IdLabel2.setForeground(new java.awt.Color(0, 51, 204));
    IdLabel2.setText("Type");
    EditPanel.add(IdLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, -1, 50));

    IdLabel3.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    IdLabel3.setForeground(new java.awt.Color(0, 51, 204));
    IdLabel3.setText("Floor");
    EditPanel.add(IdLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, -1, 40));

    IdLabel4.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    IdLabel4.setForeground(new java.awt.Color(0, 51, 204));
    IdLabel4.setText("Building");
    EditPanel.add(IdLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 70, 50));

    building_combo.setBackground(new java.awt.Color(255, 255, 255));
    building_combo.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    building_combo.setMaximumRowCount(100);
    building_combo.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        building_comboFocusGained(evt);
      }
    });
    building_combo.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        building_comboActionPerformed(evt);
      }
    });
    EditPanel.add(building_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 440, 490, 50));

    location_type_combo.setBackground(new java.awt.Color(255, 255, 255));
    location_type_combo.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    location_type_combo.setMaximumRowCount(100);
    location_type_combo.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        location_type_comboFocusGained(evt);
      }
    });
    location_type_combo.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        location_type_comboActionPerformed(evt);
      }
    });
    EditPanel.add(location_type_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 490, 50));

    jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_geo_fence_128px.png"))); // NOI18N
    EditPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 140, 130));

    CapText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    CapText.setText("Enter Location Capacity");
    CapText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
    CapText.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        CapTextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        CapTextFocusLost(evt);
      }
    });
    EditPanel.add(CapText, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 580, 490, 50));

    SavePanel.setBackground(new java.awt.Color(0, 129, 211));
    SavePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    Save.setBackground(new java.awt.Color(0, 90, 148));
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
    SavePanel.add(Save, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 50));

    EditPanel.add(SavePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 660, 100, -1));

    DeletePanel.setBackground(new java.awt.Color(0, 129, 211));
    DeletePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    Delete.setBackground(new java.awt.Color(0, 90, 148));
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
    DeletePanel.add(Delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 50));

    EditPanel.add(DeletePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 660, -1, -1));

    ClearPanel.setBackground(new java.awt.Color(0, 129, 211));
    ClearPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    clear.setBackground(new java.awt.Color(0, 90, 148));
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
    ClearPanel.add(clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 50));

    EditPanel.add(ClearPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 660, 100, -1));
    EditPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 620, 20));

    add(EditPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 100, 670, 730));

    no_of_rows.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
    no_of_rows.setForeground(new java.awt.Color(135, 135, 135));
    no_of_rows.setText("no of rows");
    add(no_of_rows, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 850, 170, 40));

    searchPanel.setBackground(new java.awt.Color(0, 129, 211));
    searchPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 129, 211), 1, true));
    searchPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        searchPanelMouseMoved(evt);
      }
    });
    searchPanel.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        searchPanelMouseExited(evt);
      }
    });
    searchPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    Search.setBackground(new java.awt.Color(0, 90, 148));
    Search.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    Search.setForeground(new java.awt.Color(255, 255, 255));
    Search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_search_26px.png"))); // NOI18N
    Search.setBorderPainted(false);
    Search.setContentAreaFilled(false);
    Search.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        SearchMouseMoved(evt);
      }
    });
    Search.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        SearchMouseExited(evt);
      }
    });
    Search.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        SearchActionPerformed(evt);
      }
    });
    searchPanel.add(Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 50));

    add(searchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 40, 80, 50));

    RefreshPanel.setBackground(new java.awt.Color(0, 129, 211));
    RefreshPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    Refresh.setBackground(new java.awt.Color(0, 90, 148));
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
    RefreshPanel.add(Refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

    add(RefreshPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 870, 120, 50));

    jLabel8.setBackground(new java.awt.Color(231, 78, 123));
    jLabel8.setFont(new java.awt.Font("VIP Rawy Regular", 0, 36)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(231, 78, 123));
    jLabel8.setText("Location ");
    add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 50, 240, 60));

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

    private void CodeTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodeTextFocusGained
    // TODO add your handling code here:
    if(CodeText.getText().equalsIgnoreCase("Enter Location Code"))
      {
        CodeText.setText("");
      }

    CodeText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_CodeTextFocusGained

    private void CodeTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodeTextFocusLost
    // TODO add your handling code here:
    if(CodeText.getText().trim().equalsIgnoreCase("Enter Location Code"))
      {
        CodeText.setText("Enter The Code");
      }

    CodeText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_CodeTextFocusLost

    private void NameTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameTextFocusGained
    // TODO add your handling code here:
    if(NameText.getText().equalsIgnoreCase("Enter Location Name"))
      {
        NameText.setText("");
      }

    NameText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_NameTextFocusGained

    private void NameTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameTextFocusLost
    // TODO add your handling code here:
    if(NameText.getText().trim().equalsIgnoreCase(""))
      {
        NameText.setText("Enter Location Name");
      }

    NameText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_NameTextFocusLost

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
    // TODO add your handling code here:

    if(Save.getText().equalsIgnoreCase("Update"))
      update();

    else
      {
        try
          {
            //if data is valid
            if(checkValidity())
              {

                LocationDto location =
                  new LocationDto(); //create new location dto
                LocationBao bao =
                  new BaoFactory().createLocationBao(user); //creat bao object

                location.setCode(CodeText.getText()); //get enterd code
                location.setId(Integer.parseInt(IdText.getText())); //get enterd id
                location.setName(NameText.getText()); //get enterd name
                location.setCapacity(Integer.parseInt(CapText.getText())); //get enterd capacity
                //get selected building
                location.setBuild(new BuildingDto());
                location.getBuild().setCode(building_combo.getItemAt(building_combo.getSelectedIndex()));
                //get selected location type
                location.setType(new LocationTypeDto());
                location.getType().setCode(location_type_combo.getSelectedItem().toString());
                //get selected floor of selected building
                location.setFloor(new FloorDto());
                location.getFloor().setCode(floor_combo.getSelectedItem().toString());

                //if location inserted successfully
                if(bao.insert(location, user))
                  {
                    //show message to tell user that
                    JOptionPane.showMessageDialog(null,
                      "Location inserted Successfully", "Done", 1);
                    //refresh table
                    setTableModel(bao.ListAll());
                    LocationTable.repaint();

                    IdText.setEnabled(false); // make id uneditable
                    Save.setText("Update");

                  }
                else
                  {

                    int reply =
                      JOptionPane.showConfirmDialog(null,
                        "This location is already exist!\n\n"+
                        "Do you want to update it?", "Warning",
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
            e.printStackTrace();
          }
      }
        
        
    }//GEN-LAST:event_SaveActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
    // TODO add your handling code here:
    try
      {

        //Show confirm message
        int reply =
          JOptionPane.showConfirmDialog(null,
            "Are you sure to delete this Location?\n"+
            "All things depend on it will be deleted!", "Warning",
            JOptionPane.YES_NO_OPTION);

        //delete object if user choose yes
        if(reply==JOptionPane.YES_OPTION)
          {
            LocationDto location =
              new LocationDto(); //create dto location object
            LocationBao bao =
              new BaoFactory().createLocationBao(user); //create object location bao

            //get id
            int s = Integer.parseInt(IdText.getText());
            location.setId(s); //set it to location object

            //delete it using bao delete method
            if(bao.delete(location)) //if it deleted sucessflly show message to tell user that
              {
                JOptionPane.showMessageDialog(null,
                  "Location Deleted Successfully");
                setTableModel(bao.ListAll());
                LocationTable.repaint();
                IdText.setEnabled(true); //enable id text field
                Save.setText("Save");

              }

            else
              //if bao method return false (location does'nt exist)
              JOptionPane.showMessageDialog(null, "Location doesn't exist!",
                "Not Found", JOptionPane.ERROR_MESSAGE);
          }


      }
    catch(Exception e)
      {
        e.printStackTrace();
        //for non expected error
        JOptionPane.showMessageDialog(null,
          "Some Thing went wrong!\nPlease check your entered data. ",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
      }
    }//GEN-LAST:event_DeleteActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
    // TODO add your handling code here:
    try
      {

        LocationBao bao =
          new BaoFactory().createLocationBao(user); //create locatin bao object

        //refresh table data
        setTableModel(bao.ListAll());
        LocationTable.repaint();

        defaultdata(); //refresh edit panel

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    }//GEN-LAST:event_RefreshActionPerformed

    private void SearchTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusGained
    // TODO add your handling code here:
    if(SearchText.getText().equalsIgnoreCase("What do you want to search for?"))
      {
        SearchText.setText("");
      }

    SearchText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));
    }//GEN-LAST:event_SearchTextFocusGained

    private void SearchTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusLost
    // TODO add your handling code here:
    if(SearchText.getText().trim().equalsIgnoreCase(""))
      {
        SearchText.setText("What do you want to search for?");
      }

    SearchText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_SearchTextFocusLost

    private void IdTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusGained
    // TODO add your handling code here:
    if(IdText.getText().equalsIgnoreCase("Enter Location ID"))
      {
        IdText.setText("");
      }

    IdText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_IdTextFocusGained

    private void IdTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusLost
    // TODO add your handling code here:
    if(IdText.getText().trim().equalsIgnoreCase(""))
      {
        IdText.setText("Enter Location ID");
      }

    IdText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_IdTextFocusLost

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
    // TODO add your handling code here:
    try
      {

        // Check if the Search field is empty
        if(SearchText.getText().equalsIgnoreCase("What do you want to search for?"))
          {
            //Show message tell user that Search Field is empty
            JOptionPane.showMessageDialog(null, "Search field is empty",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            //throw exception
            throw new IllegalArgumentException("Search field is empty");
          }
        LocationDto loc = new LocationDto(); //create new location object
        List<LocationDto> locations =
          null; //create list of locatio to put result set on it
        LocationBao bao =
          new BaoFactory().createLocationBao(user); //create bao object

        //get entered sezrch text
        loc.setSearch(SearchText.getText());
        locations = bao.searchFor(loc); //get result from search bao methd

        //if search result found
        if(locations!=null)
          {
            //refresh table to show result search on it
            setTableModel(locations);
            LocationTable.repaint();
          }
        else
          //if there is no result show message tell user that their is no search for this text
          JOptionPane.showMessageDialog(null,
            "There is no search result for: "+SearchText.getText(),
            "Invalid search", 1);

      }
    catch(IllegalArgumentException e1)
      {
        e1.printStackTrace();
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    }//GEN-LAST:event_SearchActionPerformed

    private void CapTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CapTextFocusGained
    // TODO add your handling code here:
    if(CapText.getText().equalsIgnoreCase("Enter Location Capacity"))
      {
        CapText.setText("");
      }

    CapText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_CapTextFocusGained

    private void CapTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CapTextFocusLost
    // TODO add your handling code here:
    if(CapText.getText().trim().equalsIgnoreCase(""))
      {
        CapText.setText("Enter Location Capacity");
      }

    CapText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_CapTextFocusLost

    private void building_comboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_building_comboFocusGained
    // TODO add your handling code here:
        
    }//GEN-LAST:event_building_comboFocusGained

    private void floor_comboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_floor_comboFocusGained
    // TODO add your handling code here:
        
    }//GEN-LAST:event_floor_comboFocusGained

    private void location_type_comboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_location_type_comboFocusGained
    // TODO add your handling code here:
        
        
    }//GEN-LAST:event_location_type_comboFocusGained

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
    // TODO add your handling code here:

    defaultdata(); //call setdefault method to rest edit panel

    }//GEN-LAST:event_clearActionPerformed

    private void building_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_building_comboActionPerformed
        
    //Set all existing floors for selected building to floor combobox
    try
      {

        FloorDto floor_search =
          new FloorDto(); //create floor dto object to use it in search
        FloorBao floor_bao =
          new BaoFactory().createFloorBao(user); //create floor bao object

        //check for selected building
        BuildingDto selected_building = new BuildingDto();

        if(building_combo.getSelectedIndex()!=-1)
          {
            selected_building.setCode(building_combo.getSelectedItem().toString()); //get selected building code

            floor_search.setBuild(selected_building); //set building to the floor search object

            //get floors from bao viewBuildinFloors method
            List<FloorDto> floor_list =
              floor_bao.viewBuildingFloors(floor_search);

            floor_combo.removeAllItems(); //remove all previous data in floor combobox

            //set floors code in it
            if(floor_list!=null&&!floor_list.isEmpty())
              {
                for(int i = 0; i<floor_list.size(); i++)
                  {
                    floor_combo.addItem(floor_list.get(i).getCode());
                  }

                floor_combo.setSelectedItem(-1);

              }
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    }//GEN-LAST:event_building_comboActionPerformed

  private void floor_comboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_floor_comboActionPerformed
  {//GEN-HEADEREND:event_floor_comboActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_floor_comboActionPerformed

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
            LocationDto loc =
              new LocationDto(); //create new location object
            List<LocationDto> locations =
              null; //create list of locatio to put result set on it
            LocationBao bao =
              new BaoFactory().createLocationBao(user); //create bao object

            //get entered sezrch text
            loc.setSearch(SearchText.getText());
            locations =
              bao.searchFor(loc); //get result from search bao methd

            //if search result found
            if(locations!=null)
              {
                //refresh table to show result search on it
                setTableModel(locations);
                LocationTable.repaint();
              }
            else
              //if there is no result show message tell user that their is no search for this text
              JOptionPane.showMessageDialog(null,
                "There is no search result for: "+SearchText.getText(),
                "Invalid search", 1);

          }
        catch(IllegalArgumentException e1)
          {
            e1.printStackTrace();
          }
        catch(Exception e)
          {
            e.printStackTrace();
          }
      }
  }//GEN-LAST:event_SearchTextKeyPressed

  private void SearchTextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SearchTextActionPerformed
  {//GEN-HEADEREND:event_SearchTextActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_SearchTextActionPerformed

  private void searchPanelMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchPanelMouseMoved
  {//GEN-HEADEREND:event_searchPanelMouseMoved
    // TODO add your handling code here:

  }//GEN-LAST:event_searchPanelMouseMoved

  private void searchPanelMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchPanelMouseExited
  {//GEN-HEADEREND:event_searchPanelMouseExited
    // TODO add your handling code here:

  }//GEN-LAST:event_searchPanelMouseExited

  private void SearchMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SearchMouseMoved
  {//GEN-HEADEREND:event_SearchMouseMoved
    // TODO add your handling code here:
    searchPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_SearchMouseMoved

  private void SearchMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SearchMouseExited
  {//GEN-HEADEREND:event_SearchMouseExited
    // TODO add your handling code here:
    searchPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_SearchMouseExited

  private void LocationTableKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_LocationTableKeyPressed
  {//GEN-HEADEREND:event_LocationTableKeyPressed

    int i = LocationTable.getSelectedRow();

    //Because "LocationTable.getSelectedRow()" doesn't give the correct selected row
    if(evt.getExtendedKeyCode()==KeyEvent.VK_UP)
      i--; //for up key decrement
    else if(evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
      i++; // for down key increment
    try
      {
        //unable to edit id for selected item
        IdText.setEnabled(false);
        Save.setText("Update");

        //get values from table to edit space

        //get loction id
        IdText.setText(LocationTable.getValueAt(i, 0).toString());
        //get loction code
        CodeText.setText(LocationTable.getValueAt(i, 1).toString());
        //get loction name
        NameText.setText(LocationTable.getValueAt(i, 2).toString());
        //get loction capacity
        CapText.setText(LocationTable.getValueAt(i, 3).toString());

        //select location type in location type combobox
        String location_type = LocationTable.getValueAt(i, 6).toString();
        for(int j = 0; j<location_type_combo.getItemCount(); j++)
          {
            if(location_type_combo.getItemAt(j).equalsIgnoreCase(location_type))
              location_type_combo.setSelectedIndex(j);
          }

        //select builing in building combobox
        String building = LocationTable.getValueAt(i, 5).toString();
        for(int j = 0; j<building_combo.getItemCount(); j++)
          {
            if(building_combo.getItemAt(j).equalsIgnoreCase(building))
              building_combo.setSelectedIndex(j);
          }

        //get floor code
        String floor = LocationTable.getValueAt(i, 4).toString();
        for(int j = 0; j<floor_combo.getItemCount(); j++)
          {
            if(floor_combo.getItemAt(j).equalsIgnoreCase(floor))
              floor_combo.setSelectedIndex(j);
          }

      }
    catch(java.lang.ArrayIndexOutOfBoundsException e)
      {
        e.printStackTrace();
      }

    //delete selected object when press delete
    if(evt.getExtendedKeyCode()==KeyEvent.VK_DELETE)
      {

        try
          {

            //For one selected row in table

            if(LocationTable.getSelectedRowCount()==1)
              { //Show confirm message
                int reply =
                  JOptionPane.showConfirmDialog(null,
                    "Are you sure to delete this Location?\n"+
                    "All things depend on it will be deleted!", "Warning",
                    JOptionPane.YES_NO_OPTION);

                //delete object if user choose yes
                if(reply==JOptionPane.YES_OPTION)
                  {
                    LocationDto location =
                      new LocationDto(); //create dto location object
                    LocationBao bao =
                      new BaoFactory().createLocationBao(user); //create object location bao

                    //get selected location id from table
                    int s =
                      Integer.parseInt(LocationTable.getValueAt(LocationTable.getSelectedRow(),
                          0).toString());
                    location.setId(s); //set it to location object

                    //delete it using bao delete method
                    if(bao.delete(location)) //if it deleted sucessfilly show message to tell user that
                      {
                        JOptionPane.showMessageDialog(null,
                          "Location Deleted Successfully");
                        setTableModel(bao.ListAll());
                        LocationTable.repaint();
                        Save.setText("Save");

                      }

                    else
                      //if bao method return false (location not be deleted)
                      JOptionPane.showMessageDialog(null,
                        "Can't delete object", "Error",
                        JOptionPane.ERROR_MESSAGE);
                  }

              }
            else if(LocationTable.getSelectedRowCount()==0)
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
  }//GEN-LAST:event_LocationTableKeyPressed

  private void LocationTableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_LocationTableMouseClicked
  {//GEN-HEADEREND:event_LocationTableMouseClicked

    int i = LocationTable.getSelectedRow();

    //unable to edit id for selected item
    IdText.setEnabled(false);
    Save.setText("Update");

    //get values from table to edit space

    //get loction id
    IdText.setText(LocationTable.getValueAt(i, 0).toString());
    //get loction code
    CodeText.setText(LocationTable.getValueAt(i, 1).toString());
    //get loction name
    NameText.setText(LocationTable.getValueAt(i, 2).toString());
    //get loction capacity
    CapText.setText(LocationTable.getValueAt(i, 3).toString());

    //select location type in location type combobox
    String location_type = LocationTable.getValueAt(i, 6).toString();
    for(int j = 0; j<location_type_combo.getItemCount(); j++)
      {
        if(location_type_combo.getItemAt(j).equalsIgnoreCase(location_type))
          location_type_combo.setSelectedIndex(j);
      }

    //select builing in building combobox
    String building = LocationTable.getValueAt(i, 5).toString();
    for(int j = 0; j<building_combo.getItemCount(); j++)
      {
        if(building_combo.getItemAt(j).equalsIgnoreCase(building))
          building_combo.setSelectedIndex(j);
      }

    //get floor code
    String floor = LocationTable.getValueAt(i, 4).toString();
    for(int j = 0; j<floor_combo.getItemCount(); j++)
      {
        if(floor_combo.getItemAt(j).equalsIgnoreCase(floor))
          floor_combo.setSelectedIndex(j);
      }

  }//GEN-LAST:event_LocationTableMouseClicked

  private void SaveMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseMoved
  {//GEN-HEADEREND:event_SaveMouseMoved
    // TODO add your handling code here:
    SavePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_SaveMouseMoved

  private void DeleteMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteMouseMoved
  {//GEN-HEADEREND:event_DeleteMouseMoved
    // TODO add your handling code here:
    DeletePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_DeleteMouseMoved

  private void clearMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseMoved
  {//GEN-HEADEREND:event_clearMouseMoved
    // TODO add your handling code here:
    ClearPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_clearMouseMoved

  private void RefreshMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseMoved
  {//GEN-HEADEREND:event_RefreshMouseMoved
    // TODO add your handling code here:
    RefreshPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_RefreshMouseMoved

  private void RefreshMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseExited
  {//GEN-HEADEREND:event_RefreshMouseExited
    // TODO add your handling code here:
    RefreshPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_RefreshMouseExited

  private void SaveMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseExited
  {//GEN-HEADEREND:event_SaveMouseExited
    // TODO add your handling code here:
    SavePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_SaveMouseExited

  private void DeleteMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteMouseExited
  {//GEN-HEADEREND:event_DeleteMouseExited
    // TODO add your handling code here:
    DeletePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_DeleteMouseExited

  private void clearMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseExited
  {//GEN-HEADEREND:event_clearMouseExited
    // TODO add your handling code here:
    ClearPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_clearMouseExited

  private void location_type_comboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_location_type_comboActionPerformed
  {//GEN-HEADEREND:event_location_type_comboActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_location_type_comboActionPerformed

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

    LocationExcel read = new LocationExcel(user);
    LocationBao bao = new BaoFactory().createLocationBao(user);
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

        List<LocationDto> import_locations = read.read_data(filePath);

        if(import_locations!=null)
        {

          //insert reading building to database
          for (int i=0;  i<import_locations.size() ;i++)
          {
            bao.insert(import_locations.get(i), user);
          }

          // show result set in table
          setTableModel(import_locations);
          LocationTable.repaint();

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
    LocationExcel excel = new LocationExcel(user);
    List<LocationDto> locations = null;
    LocationDto location = null;

    try
    {
      //get all buildings in table

      for(int i=0; i<LocationTable.getRowCount() ; i++)
      {
        if(locations==null)
        locations = new ArrayList<>();

        location = new LocationDto();
        location.setId(Integer.parseInt(LocationTable.getValueAt(i, 0).toString()));
        location.setCode(LocationTable.getValueAt(i, 1).toString());
        location.setName(LocationTable.getValueAt(i, 2).toString());
        location.setCapacity(Integer.parseInt(LocationTable.getValueAt(i,
              3).toString()));
        location.setBuild(new BuildingDto());
        location.getBuild().setCode(LocationTable.getValueAt(i,
              5).toString());
        location.setFloor(new FloorDto());
        location.getFloor().setCode(LocationTable.getValueAt(i,
              4).toString());
        location.setType(new LocationTypeDto());
        location.getType().setCode(LocationTable.getValueAt(i,
              6).toString());

        locations.add(location);

        location=null;

      }

      //create new file chooser
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      fileChooser.setAcceptAllFileFilterUsed(false);

      //set file name
      String filename = "Locations.xlsx";

      if(!SearchText.getText().equalsIgnoreCase("What do you want to search for?"))
      filename = SearchText.getText()+ "-Locations.xlsx";
      //show file dialog
      int result = fileChooser.showSaveDialog(null);

      //user selected file
      if(result==JFileChooser.APPROVE_OPTION)
      {
        //get path
        String filePath =
        fileChooser.getSelectedFile().getAbsolutePath();

        excel.write(filename, filePath, locations);

        //write excel file
        try
        {

          JOptionPane.showMessageDialog(null,
            "Locations data export ended successfully!", "Done", 1);
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
 
 

  /**check Validity of entered data
   * @return: True if data is entered correctly (as expected)
   * False when one or more of them entered uncorrectly
   * */
  private boolean checkValidity()
  {
    try
      {


        //check for if id is empty
        if(IdText.getText().equalsIgnoreCase("Enter Location ID"))
          {
            JOptionPane.showMessageDialog(null, "Please, enter location id",
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
              "Please enter number for location ID", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            return false;
          }


        //check for if code is empty
        if(CodeText.getText().equalsIgnoreCase("Enter Location Code"))

          {
            JOptionPane.showMessageDialog(null,
              "Please, enter location code", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("code text is empty");
          }

        //check validity of code

        //code can't start with number or symbol
        if(!Character.isAlphabetic(CodeText.getText().charAt(0)))
          {
            JOptionPane.showMessageDialog(null,
              "Invalid code format\n\n(code can't start with number or symbol)",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("code can't start with number or symbol");

          }
        //check for all chars
        for(int i = 1; i<CodeText.getText().length(); i++)
          {

            //code contain only letters/numbers ane '_'
            if(!Character.isLetterOrDigit(CodeText.getText().charAt(i))&&
              CodeText.getText().charAt(i)!='_')
              {
                JOptionPane.showMessageDialog(null,
                  "Invalid code format\n\n(code can only be a sequence of Unicode letters and digits separated by underscore)",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("code can't contain stranger symbols");

              }
          }


        //check for if name is empty
        if(NameText.getText().equalsIgnoreCase("Enter Location Name"))
          {
            JOptionPane.showMessageDialog(null,
              "Please, enter location Name", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("name text is empty");
          }
        //check validity of name

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

            //code contain only letters/numbers ane '_'
            if(!Character.isLetterOrDigit(NameText.getText().charAt(i))&&
              NameText.getText().charAt(i)!='_'&&
              NameText.getText().charAt(i)!=' ')
              {
                JOptionPane.showMessageDialog(null,
                  "Invalid Name format\n\n(Name can only be a sequence of Unicode letters and digits separated by underscore)",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Name can't contain stranger symbols");

              }
          }


        //check for if capacity is empty
        if(CapText.getText().equalsIgnoreCase("Enter Location Capacity"))
          {
            JOptionPane.showMessageDialog(null,
              "Please, enter location capacity", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Capacity text is empty");
          }


        //Check for the entered capacity is a positive number
        try
          {

            int cap = Integer.parseInt(CapText.getText());
            if(cap<1)
              {
                JOptionPane.showMessageDialog(null,
                  "Invalid capacity \n\n(Capacity is only Positive Numbers)",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Capacity is only Positive Numbers");
              }
          }
        catch(java.lang.NumberFormatException e2)
          {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(null,
              "Please enter number for location capacity", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            return false;
          }


        //check for location type is not selected
        if(location_type_combo.getSelectedIndex()==-1) //location type
          {
            JOptionPane.showMessageDialog(null,
              "Please, Choose location type", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("location_type text is empty");
          }


        //check for building is not selected
        if(building_combo.getSelectedIndex()==-1) //Building
          {
            JOptionPane.showMessageDialog(null, "Please, Choose Building",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("buiding is empty");
          }


        //check for floor is not selected
        if(floor_combo.getSelectedIndex()==-1) //floor
          {
            JOptionPane.showMessageDialog(null, "Please, Choose floor",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("floor text is empty");
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


  //update function
  private void update()
  {
    try
      {

        //if data is valid
        if(checkValidity())
          {

            LocationDto location =
              new LocationDto(); //create location object
            LocationBao bao =
              new BaoFactory().createLocationBao(user); //create bao object

            location.setCode(CodeText.getText()); //get enterd code
            location.setId(Integer.parseInt(IdText.getText())); //get enterd id
            location.setName(NameText.getText()); //get enterd name
            location.setCapacity(Integer.parseInt(CapText.getText())); //get enterd capacity
            //get selected building
            location.setBuild(new BuildingDto());
            location.getBuild().setCode(building_combo.getItemAt(building_combo.getSelectedIndex()));
            //get selected location type
            location.setType(new LocationTypeDto());
            location.getType().setCode(location_type_combo.getSelectedItem().toString());
            //get selected floor of selected building
            location.setFloor(new FloorDto());
            location.getFloor().setCode(floor_combo.getSelectedItem().toString());

            //if update ended successfully
            if(bao.update(location, user))
              {
                //shoe message to tell user that
                JOptionPane.showMessageDialog(null,
                  "Location updated Successfully", "Done", 1);
                //refresh table
                setTableModel(bao.ListAll());
                LocationTable.repaint();
              }
            //if not
            else
              //show message to tell user this location doesn't exist
              JOptionPane.showMessageDialog(null,
                "The Location doesn't exist!", "Not Found",
                JOptionPane.ERROR_MESSAGE);
          }

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }
    
    
    
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextField CapText;
  private javax.swing.JPanel ClearPanel;
  private javax.swing.JLabel CodeLabel;
  private javax.swing.JTextField CodeText;
  private javax.swing.JButton Delete;
  private javax.swing.JPanel DeletePanel;
  private javax.swing.JPanel EditPanel;
  private javax.swing.JButton ExportButton;
  private javax.swing.JLabel IdLabel;
  private javax.swing.JLabel IdLabel1;
  private javax.swing.JLabel IdLabel2;
  private javax.swing.JLabel IdLabel3;
  private javax.swing.JLabel IdLabel4;
  private javax.swing.JTextField IdText;
  private javax.swing.JButton ImportButton;
  private javax.swing.JTable LocationTable;
  private javax.swing.JLabel NameLabel;
  private javax.swing.JTextField NameText;
  private javax.swing.JButton Refresh;
  private javax.swing.JPanel RefreshPanel;
  private javax.swing.JButton Save;
  private javax.swing.JPanel SavePanel;
  private javax.swing.JButton Search;
  private javax.swing.JTextField SearchText;
  private javax.swing.JComboBox<String> building_combo;
  private javax.swing.JButton clear;
  private javax.swing.JComboBox<String> floor_combo;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JComboBox<String> location_type_combo;
  private java.awt.Label no_of_rows;
  private javax.swing.JPanel searchPanel;
  // End of variables declaration//GEN-END:variables

}

