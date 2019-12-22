
package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.BuildingBao;
import com.fym.lta.BAO.FloorBao;
import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.FloorDao;
import com.fym.lta.DAO.RoleScreenDao;
import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.FloorDto;
import com.fym.lta.DTO.UserDto;
import com.fym.lta.Excel.FloorExcel;

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
public class FloorPanel extends javax.swing.JPanel
{
  @SuppressWarnings("compatibility:-6981567536372983589")
  private static final long serialVersionUID = -5499471167947301211L;
  private static UserDto user = null;


  /** Creates new form FloorJPanel */
  public FloorPanel(UserDto u)
  {
    try
      {
        user = u;
        initComponents();

        //set default edit panel data
        defaultdata();

        //view all floors exit in database
        FloorBao bao;
        bao = new BaoFactory().createFloorBao(user);
        setTableModel(bao.ListAll());
        viewonly(user);

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }

  private void setTableModel(List<FloorDto> floors)
  {
    int size = 0;
    if(floors!=null&&!floors.isEmpty())
      {
        size = floors.size();
      }
    Object[][] buildArr = new Object[size][5];
    for(int i = 0; i<size; i++)
      {
        buildArr[i][0] = floors.get(i).getId();
        buildArr[i][1] = floors.get(i).getCode();
        buildArr[i][2] = floors.get(i).getDescription();
        buildArr[i][3] = floors.get(i).getBuild().getCode();
        buildArr[i][4] = floors.get(i).getLocation_number();

      }
    FloorTable.setModel(new javax.swing.table.DefaultTableModel(buildArr,
        new String[] { "Id", "Code", "Description", "Building",
        "N.of Locations" }));


    //change header color
    FloorTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
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
      Integer.toString(FloorTable.getRowCount())+"  Floors");
  }


  /**For view only user access */
  private void viewonly(UserDto u)
  {

    u.setScreen_name("Floor");

    RoleScreenDao dao = new DaoFactory().createRoleScreenDao(user);
    if(dao.viewonly(u))
      {
        EditPanel.setVisible(false);
        System.out.println(" \n done panel");
      }
  }


  /** to set default data for edit space panel
   */
  private void defaultdata()
  {

    //unenable text boxes
    IdText.setEnabled(true);
    CodeText.setEnabled(false);
    DesText.setEnabled(false);

    //set default text for text boxes
    IdText.setText("Floor ID");
    CodeText.setText("Floor Code");
    DesText.setText("Floor Description");

    //set all existing builings in db to building combobox1&2
    try
      {
        BuildingBao build_bao =
          new BaoFactory().createBuildingBao(user); //create building bao object
        List<BuildingDto> build_list =
          build_bao.ListAll(); //get all building fron DB


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


        building_combo2.removeAllItems(); //remove all item from building combobox
        building_combo2.addItem("Select Building");

        //set buildings code to building combo
        if(build_list!=null&&!build_list.isEmpty())
          {
            for(int i = 0; i<build_list.size(); i++)
              {
                building_combo2.addItem(build_list.get(i).getCode());
              }

            building_combo2.setSelectedIndex(0); //select nothing in this combo
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
    FloorTable = new javax.swing.JTable();
    EditPanel = new javax.swing.JPanel();
    IdText = new javax.swing.JTextField();
    IdLabel = new javax.swing.JLabel();
    NameLabel = new javax.swing.JLabel();
    CodeLabel = new javax.swing.JLabel();
    CodeText = new javax.swing.JTextField();
    DesText = new javax.swing.JTextField();
    jLabel1 = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    DeletePanel = new javax.swing.JPanel();
    Delete = new javax.swing.JButton();
    InsertPanel = new javax.swing.JPanel();
    Insert = new javax.swing.JButton();
    ClearPanel = new javax.swing.JPanel();
    clear = new javax.swing.JButton();
    building_combo = new javax.swing.JComboBox<>();
    no_of_rows = new java.awt.Label();
    jLabel9 = new javax.swing.JLabel();
    jSeparator2 = new javax.swing.JSeparator();
    RefreshPanel = new javax.swing.JPanel();
    Refresh = new javax.swing.JButton();
    SearchPanel = new javax.swing.JPanel();
    search = new javax.swing.JButton();
    building_combo2 = new javax.swing.JComboBox<>();
    ImportButton = new javax.swing.JButton();
    ExportButton = new javax.swing.JButton();

    setBackground(new java.awt.Color(245, 245, 245));
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    FloorTable.setAutoCreateRowSorter(true);
    FloorTable.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    FloorTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {

      },
      new String []
      {
        "Id", "Code", "Description", "Building", "locations number"
      }
    )
    {
      boolean[] canEdit = new boolean []
      {
        false, false, false, true, true
      };

      public boolean isCellEditable(int rowIndex, int columnIndex)
      {
        return canEdit [columnIndex];
      }
    });
    FloorTable.setFillsViewportHeight(true);
    FloorTable.setRowHeight(25);
    FloorTable.setSelectionBackground(new java.awt.Color(0, 153, 204));
    FloorTable.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        FloorTableMouseClicked(evt);
      }
    });
    FloorTable.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyPressed(java.awt.event.KeyEvent evt)
      {
        FloorTableKeyPressed(evt);
      }
    });
    jScrollPane1.setViewportView(FloorTable);
    if (FloorTable.getColumnModel().getColumnCount() > 0)
    {
      FloorTable.getColumnModel().getColumn(0).setHeaderValue("Id");
      FloorTable.getColumnModel().getColumn(1).setHeaderValue("Code");
      FloorTable.getColumnModel().getColumn(2).setHeaderValue("Description");
      FloorTable.getColumnModel().getColumn(3).setHeaderValue("Building");
      FloorTable.getColumnModel().getColumn(4).setHeaderValue("locations number");
    }

    add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 714, 760));

    EditPanel.setBackground(new java.awt.Color(245, 245, 245));
    EditPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    IdText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    IdText.setText("Floor ID");
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
    EditPanel.add(IdText, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 580, 60));

    IdLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    IdLabel.setForeground(new java.awt.Color(0, 51, 204));
    IdLabel.setText("Id");
    EditPanel.add(IdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 20, 50));

    NameLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    NameLabel.setForeground(new java.awt.Color(0, 51, 204));
    NameLabel.setText("Description");
    EditPanel.add(NameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, -1, 50));

    CodeLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    CodeLabel.setForeground(new java.awt.Color(0, 51, 204));
    CodeLabel.setText("Code");
    EditPanel.add(CodeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, 50));

    CodeText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    CodeText.setText("Floor Code");
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
    EditPanel.add(CodeText, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 580, 60));

    DesText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    DesText.setText("Floor Description");
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
    EditPanel.add(DesText, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, 580, 60));

    jLabel1.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(0, 51, 204));
    jLabel1.setText("Building");
    EditPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 70, 30));

    jLabel8.setBackground(new java.awt.Color(231, 78, 123));
    jLabel8.setFont(new java.awt.Font("VIP Rawy Regular", 0, 36)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(231, 78, 123));
    jLabel8.setText("Location ");
    EditPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 50, 240, 60));

    jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_geo_fence_128px.png"))); // NOI18N
    EditPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 140, 130));

    DeletePanel.setBackground(new java.awt.Color(0, 129, 211));
    DeletePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

    EditPanel.add(DeletePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 550, 100, 50));

    InsertPanel.setBackground(new java.awt.Color(0, 129, 211));
    InsertPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    Insert.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    Insert.setForeground(new java.awt.Color(255, 255, 255));
    Insert.setText("Insert");
    Insert.setBorderPainted(false);
    Insert.setContentAreaFilled(false);
    Insert.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        InsertMouseMoved(evt);
      }
    });
    Insert.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        InsertMouseExited(evt);
      }
    });
    Insert.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        InsertActionPerformed(evt);
      }
    });
    InsertPanel.add(Insert, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 50));

    EditPanel.add(InsertPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, 100, 50));

    ClearPanel.setBackground(new java.awt.Color(0, 129, 211));
    ClearPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        clearMouseClicked(evt);
      }
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

    EditPanel.add(ClearPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 550, 100, 50));

    building_combo.setBackground(new java.awt.Color(255, 255, 255));
    building_combo.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
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
    EditPanel.add(building_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 580, 50));

    add(EditPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 150, 658, 720));

    no_of_rows.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
    no_of_rows.setForeground(new java.awt.Color(135, 135, 135));
    no_of_rows.setText("no of rows");
    add(no_of_rows, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 880, 210, 20));

    jLabel9.setBackground(new java.awt.Color(231, 78, 123));
    jLabel9.setFont(new java.awt.Font("VIP Rawy Regular", 0, 36)); // NOI18N
    jLabel9.setForeground(new java.awt.Color(231, 78, 123));
    jLabel9.setText("Floor");
    add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 70, 240, 80));
    add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 130, 620, 30));

    RefreshPanel.setBackground(new java.awt.Color(0, 129, 211));
    RefreshPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
    RefreshPanel.add(Refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 50));

    add(RefreshPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 890, -1, 50));

    SearchPanel.setBackground(new java.awt.Color(0, 129, 211));
    SearchPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    search.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
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
    SearchPanel.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

    add(SearchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 50, 70, 50));

    building_combo2.setBackground(new java.awt.Color(255, 255, 255));
    building_combo2.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    building_combo2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Building" }));
    building_combo2.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        building_combo2FocusGained(evt);
      }
    });
    building_combo2.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mousePressed(java.awt.event.MouseEvent evt)
      {
        building_combo2MousePressed(evt);
      }
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        building_combo2MouseClicked(evt);
      }
    });
    building_combo2.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        building_combo2ActionPerformed(evt);
      }
    });
    add(building_combo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 630, 50));

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
    add(ImportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 880, 80, 70));

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
    add(ExportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 880, 80, 70));
  }//GEN-END:initComponents

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
    // TODO add your handling code here:
    try
      {
        FloorDao build;
        build = new DaoFactory().createFloorDao(user);
        setTableModel(build.viewAll());
        FloorTable.repaint();
        defaultdata(); //refresh edit panel
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    }//GEN-LAST:event_RefreshActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
    // TODO add your handling code here:
    try
      {


        // Check if the Search field is empty
        if(building_combo2.getSelectedItem().toString().equalsIgnoreCase("Select Building"))
          {
            //Show message tell user that Search Field is empty
            JOptionPane.showMessageDialog(null, "Please, Select building.",
              "", JOptionPane.ERROR_MESSAGE);
            //throw exception
            throw new IllegalArgumentException("no building selected");
          }


        FloorDto floor = new FloorDto(); //create floor object
        List<FloorDto> floors = null; //floors list for searsh result set
        FloorBao bao =
          new BaoFactory().createFloorBao(user); //create floor bao object

        //get search text
        floor.setSearch(building_combo2.getSelectedItem().toString());

        floors =
          bao.searchFor(floor); //search for this text using search bao method

        if(floors!=null)
          { //if their is result show it in table
            setTableModel(floors);
            FloorTable.repaint();
          }
        else
          { //if no show message to tell user that
            JOptionPane.showMessageDialog(null,
              "There is no floors for this building.", "Invalid search", 1);
          }

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    }//GEN-LAST:event_searchActionPerformed

    private void IdTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusGained
    // TODO add your handling code here:
    if(IdText.getText().equalsIgnoreCase("Floor ID"))
      {
        IdText.setText("");
      }

    IdText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_IdTextFocusGained

    private void IdTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusLost
    // TODO add your handling code here:
    if(IdText.getText().trim().equalsIgnoreCase(""))
      {
        IdText.setText("Floor ID");
      }

    IdText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_IdTextFocusLost

    private void CodeTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodeTextFocusGained
    // TODO add your handling code here:
        
    }//GEN-LAST:event_CodeTextFocusGained

    private void CodeTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodeTextFocusLost
    // TODO add your handling code here:
       
    }//GEN-LAST:event_CodeTextFocusLost

    private void DesTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_DesTextFocusGained
    // TODO add your handling code here:
       
    }//GEN-LAST:event_DesTextFocusGained

    private void DesTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_DesTextFocusLost
    // TODO add your handling code here:
        
    }//GEN-LAST:event_DesTextFocusLost

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
    // TODO add your handling code here:
    try
      {

        //Show confirm message
        int reply =
          JOptionPane.showConfirmDialog(null,
            "Are you sure to delete this Floor?\n"+
            "All things depend on it will be deleted!", "Warning",
            JOptionPane.YES_NO_OPTION);

        //delete object if user choose yes
        if(reply==JOptionPane.YES_OPTION)
          {
            FloorDto floor = new FloorDto(); //create dto floor object
            FloorBao bao =
              new BaoFactory().createFloorBao(user); //create object Floor bao

            //get code
            floor.setCode(CodeText.getText());

            floor.setBuild(new BuildingDto()); //get building code
            floor.getBuild().setCode(building_combo2.getSelectedItem().toString());


            //delete it using bao delete method
            if(bao.delete(floor)) //if it deleted sucessflly show message to tell user that
              {
                JOptionPane.showMessageDialog(null,
                  "Floor Deleted Successfully");
                setTableModel(bao.ListAll());
                FloorTable.repaint();
                IdText.setEnabled(true);
              }

            else
              //if bao method return false
              JOptionPane.showMessageDialog(null, "Floor doesn't exist!",
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

    private void building_combo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_building_combo2FocusGained
    // TODO add your handling code here:
    }//GEN-LAST:event_building_combo2FocusGained

  private void clearActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_clearActionPerformed
  {//GEN-HEADEREND:event_clearActionPerformed
    defaultdata(); //call setdefault method to rest edit panel
          
  }//GEN-LAST:event_clearActionPerformed

  private void building_combo2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_building_combo2ActionPerformed
  {//GEN-HEADEREND:event_building_combo2ActionPerformed
    
   
  }//GEN-LAST:event_building_combo2ActionPerformed

  private void FloorTableKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_FloorTableKeyPressed
  {//GEN-HEADEREND:event_FloorTableKeyPressed
    
    
    int i = FloorTable.getSelectedRow();

    //Because "FloorTable.getSelectedRow()" doesn't give the correct selected row
    if(evt.getExtendedKeyCode()==KeyEvent.VK_UP)
      i--; //for up key decrement
    else if(evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
      i++; // for down key increment
    try
      {

        //move up and down in table to get data into edit space
        if(evt.getExtendedKeyCode()==KeyEvent.VK_UP||
          evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
          {
            IdText.setEnabled(false);
            // set the attributes of selected row to text boxes
            IdText.setText(FloorTable.getValueAt(i, 0).toString());
            CodeText.setText(FloorTable.getValueAt(i, 1).toString());
            DesText.setText(FloorTable.getValueAt(i, 2).toString());

            //select building in building combobox
            String building_code = FloorTable.getValueAt(i, 3).toString();
            for(int j = 0; j<building_combo.getItemCount(); j++)
              {
                if(building_combo.getItemAt(j).equalsIgnoreCase(building_code))
                  {
                    building_combo.setSelectedIndex(j);
                  }
              }
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
            if(FloorTable.getSelectedRowCount()==1)
              {
                //Show confirm message
                int reply =
                  JOptionPane.showConfirmDialog(null,
                    "Are you sure to delete this floor?\n"+
                    "All things depend on it will be deleted!", "Warning",
                    JOptionPane.YES_NO_OPTION);


                //delete object if user choose yes
                if(reply==JOptionPane.YES_OPTION)
                  {
                    FloorDto floor =
                      new FloorDto(); //create dto floor object
                    FloorBao bao =
                      new BaoFactory().createFloorBao(user); //create object floor bao

                    //get selected floor id from table
                    String s =
                      FloorTable.getValueAt(FloorTable.getSelectedRow(),
                        1).toString();
                    floor.setCode(s); //set it to floor object

                    floor.setBuild(new BuildingDto()); //get building code
                    floor.getBuild().setCode(FloorTable.getValueAt(FloorTable.getSelectedRow(),
                      3).toString());

                    //delete it using bao delete method
                    if(bao.delete(floor)) //if it deleted sucessfilly show message to tell user that
                      {
                        JOptionPane.showMessageDialog(null,
                          "Floor Deleted Successfully");
                        setTableModel(bao.ListAll());
                        FloorTable.repaint();
                        IdText.setEnabled(true);

                      }

                    else
                      //if bao method return false (location type not be deleted)
                      JOptionPane.showMessageDialog(null,
                        "Can't delete object", "Error",
                        JOptionPane.ERROR_MESSAGE);
                  }

              }
            else if(FloorTable.getSelectedRowCount()==0)
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
  }//GEN-LAST:event_FloorTableKeyPressed

  private void FloorTableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_FloorTableMouseClicked
  {//GEN-HEADEREND:event_FloorTableMouseClicked
   
    int i = FloorTable.getSelectedRow();

    IdText.setEnabled(false);

    // set the attributes of selected row to text boxes
    IdText.setText(FloorTable.getValueAt(i, 0).toString());
    CodeText.setText(FloorTable.getValueAt(i, 1).toString());
    DesText.setText(FloorTable.getValueAt(i, 2).toString());

    //select building in building combobox
    String building_code = FloorTable.getValueAt(i, 3).toString();
    for(int j = 0; j<building_combo.getItemCount(); j++)
      {
        if(building_combo.getItemAt(j).equalsIgnoreCase(building_code))
          {
            building_combo.setSelectedIndex(j);
          }
      }

  }//GEN-LAST:event_FloorTableMouseClicked

  private void IdTextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_IdTextActionPerformed
  {//GEN-HEADEREND:event_IdTextActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_IdTextActionPerformed

  private void InsertActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_InsertActionPerformed
  {//GEN-HEADEREND:event_InsertActionPerformed
    // TODO add your handling code here:

    if(checkValidity())
      { //if id is valid
        try
          {

            //if building not be selected
            if(building_combo.getSelectedIndex()==-1)
              {
                JOptionPane.showMessageDialog(null,
                  "Please, Select building", "Error",
                  JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("there is no selected building ");
              }

            FloorDto floor = new FloorDto(); //create floor object
            FloorBao bao =
              new BaoFactory().createFloorBao(user); // create floor bao object

            //create building object and set selected building code in it
            floor.setBuild(new BuildingDto());
            floor.getBuild().setCode(building_combo.getSelectedItem().toString());


            //set id to the new floor
            int id = Integer.parseInt(IdText.getText());
            floor.setId(id);

            //setcode to the new floor
            floor.setCode(floor.getBuild().getCode()+"-"+
              Integer.toString(id));

            //set description to the new floor
            if(id==1)
              floor.setDescription(Integer.toString(id)+"st Floor");
            else if(id==2)
              floor.setDescription(Integer.toString(id)+"nd Floor");
            else
              floor.setDescription(Integer.toString(id)+"th Floor");


            //set generated id/code/des for the new floor to their textboxes
            IdText.setText(Integer.toString(floor.getId()));
            CodeText.setText(floor.getCode());
            DesText.setText(floor.getDescription());

            //insert floor
            if(bao.insert(floor, user))
              { //show message to tell user that
                JOptionPane.showMessageDialog(null,
                  "Floor inserted Successfully", "Done", 1);
                //refresh table
                setTableModel(bao.ListAll());
                FloorTable.repaint();
                IdText.setEnabled(false);
              }

            else
              {
                //if any error found
                JOptionPane.showMessageDialog(null,
                  "This floor of this building is already exist!", "Error",
                  JOptionPane.ERROR_MESSAGE);


              }
          }
        catch(Exception e)
          {

            e.printStackTrace();
          }
      }
  }//GEN-LAST:event_InsertActionPerformed

  private void clearMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseMoved
  {//GEN-HEADEREND:event_clearMouseMoved
    // TODO add your handling code here:
    ClearPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_clearMouseMoved

  private void clearMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseClicked
  {//GEN-HEADEREND:event_clearMouseClicked
    // TODO add your handling code here:

  }//GEN-LAST:event_clearMouseClicked

  private void DeleteMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteMouseMoved
  {//GEN-HEADEREND:event_DeleteMouseMoved
    // TODO add your handling code here:
    DeletePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_DeleteMouseMoved

  private void InsertMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_InsertMouseMoved
  {//GEN-HEADEREND:event_InsertMouseMoved
    // TODO add your handling code here:
    InsertPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_InsertMouseMoved

  private void RefreshMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseMoved
  {//GEN-HEADEREND:event_RefreshMouseMoved
    // TODO add your handling code here:
    RefreshPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_RefreshMouseMoved

  private void clearMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseExited
  {//GEN-HEADEREND:event_clearMouseExited
    // TODO add your handling code here:
    ClearPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_clearMouseExited

  private void DeleteMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteMouseExited
  {//GEN-HEADEREND:event_DeleteMouseExited
    // TODO add your handling code here:
    DeletePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_DeleteMouseExited

  private void InsertMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_InsertMouseExited
  {//GEN-HEADEREND:event_InsertMouseExited
    // TODO add your handling code here:
    InsertPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_InsertMouseExited

  private void RefreshMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseExited
  {//GEN-HEADEREND:event_RefreshMouseExited
    // TODO add your handling code here:
    RefreshPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_RefreshMouseExited

  private void searchMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseMoved
  {//GEN-HEADEREND:event_searchMouseMoved
    // TODO add your handling code here:
    SearchPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_searchMouseMoved

  private void searchMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseExited
  {//GEN-HEADEREND:event_searchMouseExited
    // TODO add your handling code here:
    SearchPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_searchMouseExited

  private void building_comboFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_building_comboFocusGained
  {//GEN-HEADEREND:event_building_comboFocusGained
    // TODO add your handling code here:
  }//GEN-LAST:event_building_comboFocusGained

  private void building_comboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_building_comboActionPerformed
  {//GEN-HEADEREND:event_building_comboActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_building_comboActionPerformed

  private void building_combo2MousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_building_combo2MousePressed
  {//GEN-HEADEREND:event_building_combo2MousePressed
    // TODO add your handling code here:
  
  }//GEN-LAST:event_building_combo2MousePressed

  private void building_combo2MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_building_combo2MouseClicked
  {//GEN-HEADEREND:event_building_combo2MouseClicked
    // TODO add your handling code here:
    try
      {

        // Check if the Search field is empty
        if(building_combo2.getSelectedItem().toString().equalsIgnoreCase("Select Building"))
          {
            
            //throw exception
            throw new IllegalArgumentException("no building selected");
          }


        FloorDto floor = new FloorDto(); //create floor object
        List<FloorDto> floors = null; //floors list for searsh result set
        FloorBao bao =
          new BaoFactory().createFloorBao(user); //create floor bao object

        //get search text
        floor.setSearch(building_combo2.getSelectedItem().toString());

        floors =
          bao.searchFor(floor); //search for this text using search bao method

        if(floors!=null)
          { //if their is result show it in table
            setTableModel(floors);
            FloorTable.repaint();
          }
        else
          { //if no show message to tell user that
            /*  JOptionPane.showMessageDialog(null,
              "There is no floors for this building.", "Invalid search", 1); */
          }

      }
    catch(Exception e)
      {
        e.printStackTrace();
      } //Set all existing floors for selected building to floor combobox
  }//GEN-LAST:event_building_combo2MouseClicked

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

    FloorExcel read = new FloorExcel(user);
    FloorBao bao = new BaoFactory().createFloorBao(user);
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

        List<FloorDto> import_floors = read.read_data(filePath);

        if(import_floors!=null)
        {

          //insert reading building to database
          for (int i=0;  i<import_floors.size() ;i++)
          {
            bao.insert(import_floors.get(i), user);
          }

          // show result set in table
          setTableModel(import_floors);
          FloorTable.repaint();

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
    FloorExcel excel = new FloorExcel(user);
    List<FloorDto> floors = null;
    FloorDto floor = null;

    try
    {
      //get all buildings in table

      for(int i=0; i<FloorTable.getRowCount() ; i++)
      {
        if(floors==null)
        floors = new ArrayList<>();

        floor = new FloorDto();
        floor.setId(Integer.parseInt(FloorTable.getValueAt(i, 0).toString()));
        floor.setCode(FloorTable.getValueAt(i, 1).toString());
        floor.setDescription(FloorTable.getValueAt(i, 2).toString());
        floor.setBuild(new BuildingDto());
        floor.getBuild().setCode(FloorTable.getValueAt(i,
              3).toString());

        floors.add(floor);

        floor=null;

      }

      //create new file chooser
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      fileChooser.setAcceptAllFileFilterUsed(false);

      //set file name
      String filename = "floors.xlsx";

      if(!building_combo2.getSelectedItem().toString().equalsIgnoreCase("Select Building"))
      filename = building_combo2.getSelectedItem().toString()+ "-floors.xlsx";
      //show file dialog
      int result = fileChooser.showSaveDialog(null);

      //user selected file
      if(result==JFileChooser.APPROVE_OPTION)
      {
        //get path
        String filePath =
        fileChooser.getSelectedFile().getAbsolutePath();

        excel.write(filename, filePath, floors);

        //write excel file
        try
        {

          JOptionPane.showMessageDialog(null,
            "Floors data export ended successfully!", "Done", 1);
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

  //not used
  private boolean checkValidity()
  {

    // check input 1
    try
      {
        if(IdText.getText().equalsIgnoreCase("Floor ID"))
          {
            JOptionPane.showMessageDialog(null, "Please, enter id number",
              "", JOptionPane.ERROR_MESSAGE);
            return false;
          }

        int id = Integer.parseInt(IdText.getText());

        if(id<1)
          {
            JOptionPane.showMessageDialog(null,
              "Invalid Id \n\n(ID is only Positive Numbers)",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("ID is only Positive Numbers");
          }

        return true;
      }
    catch(NumberFormatException e)
      {
        JOptionPane.showMessageDialog(null,
          "ID is invalid! \n"+" (please enter a number)", "",
          JOptionPane.ERROR_MESSAGE);
        // own implemented dialog
        return false;
      }

  }


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel ClearPanel;
  private javax.swing.JLabel CodeLabel;
  private javax.swing.JTextField CodeText;
  private javax.swing.JButton Delete;
  private javax.swing.JPanel DeletePanel;
  private javax.swing.JTextField DesText;
  private javax.swing.JPanel EditPanel;
  private javax.swing.JButton ExportButton;
  private javax.swing.JTable FloorTable;
  private javax.swing.JLabel IdLabel;
  private javax.swing.JTextField IdText;
  private javax.swing.JButton ImportButton;
  private javax.swing.JButton Insert;
  private javax.swing.JPanel InsertPanel;
  private javax.swing.JLabel NameLabel;
  private javax.swing.JButton Refresh;
  private javax.swing.JPanel RefreshPanel;
  private javax.swing.JPanel SearchPanel;
  private javax.swing.JComboBox<String> building_combo;
  private javax.swing.JComboBox<String> building_combo2;
  private javax.swing.JButton clear;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSeparator jSeparator2;
  private java.awt.Label no_of_rows;
  private javax.swing.JButton search;
  // End of variables declaration//GEN-END:variables

}
