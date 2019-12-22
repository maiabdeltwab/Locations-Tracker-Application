
package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.EqTypeSpecBao;
import com.fym.lta.BAO.EquipSpecificationBao;
import com.fym.lta.BAO.EquipmentTypeBao;
import com.fym.lta.BAO.RoleScreenBao;
import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.RoleScreenDao;
import com.fym.lta.DTO.EquipSpecificationDto;
import com.fym.lta.DTO.EquipTypeSpecDto;
import com.fym.lta.DTO.EquipmentTypeDto;
import com.fym.lta.DTO.UserDto;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Nada Elgammal
 */

public class EqTypeSpecPanel extends javax.swing.JPanel
{
  @SuppressWarnings("compatibility:-6164628297961372426")
  private static final long serialVersionUID = 1L;

  /**Definitions*/
  //define user dto as "user"
  private static UserDto user;
  // identify object of EquipmentTypeBao
  private static EqTypeSpecBao business;
  private static EquipTypeSpecDto ets;

  /** Creates new form EqTypeSpecPanel */
  public EqTypeSpecPanel(UserDto user_data)
  {

    try
      {
        //set panel components
        initComponents();
      
        //set taken user object to the private one
        user = user_data;
      
        //set new business object
        business = new BaoFactory().createEqTypeSpecBao(user_data);

        //call setTableModel method and pass viewAll method to it as a parameter
        setTableModel(business.viewAll());

        //use user access method to check user validity on this screen
        userAccess();

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }


  /**set equipment type table model
   * @param equipment type dto list */
  private void setTableModel(List<EquipTypeSpecDto> equip_types)
  {
    int size = 0; //for table row size  
    
   //if given list not empty or null
   if(equip_types!=null&&!equip_types.isEmpty())
      size = equip_types.size(); //set table size to this list size

    /*this (2D) array of objects for table model
       *the first D for rows, second for columns */
    Object[][] equipsArr = new Object[size][4];
    
    
    for(int i = 0; i<size; i++)//for each row
      {
        /**set equip type attributes to this array element*/
        equipsArr[i][0] = equip_types.get(i).getId();
        equipsArr[i][1] = equip_types.get(i).getType_id();
        equipsArr[i][2] = equip_types.get(i).getSpecification_id();
      }
    
    //set table model using this array
    typeSpecTable.setModel(new javax.swing.table.DefaultTableModel(equipsArr,
        new String[] { "Id", "Type ID", "Specification ID" }));


    //change table header color
    typeSpecTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
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


    //label under tabel show types count viewed in table
    no_of_rows.setText("Table result: "+
      Integer.toString(typeSpecTable.getRowCount())+"  specifications");
  }


  /**This method use to check user validity on this screen
   * if user has full access or viewonly access*/
  private void userAccess()
  {

    //set user screen name to "Equipment type" to make check on it
    user.setScreen_name("Equipment type specification frame");


    //define role screen bao
    RoleScreenBao roleScreenBao =
      new BaoFactory().createRoleScreenBao(user);

    //if user has viewonly access to this screen
    if(roleScreenBao.viewonly(user))
      {
        //hide all edits components
        EditPanel.setVisible(false);
      }
  }


  /** method to set the table and allocate its columns and content
   * with specifications of one type */
  private void equip_spe(List<EquipSpecificationDto> equip_specs)
  {
    int size = 0; //for table row size

    //if given list not empty or null
    if(equip_specs!=null&&!equip_specs.isEmpty())
      size = equip_specs.size(); //set table size to this list size
 
    /*this (2D) array of objects for table model
       *the first D for rows, second for columns */
    Object[][] equipsArr = new Object[size][3];
    
    for(int i = 0; i<size; i++)
      {
        /**set equipment spec attributes to this array element*/
        equipsArr[i][0] = equip_specs.get(i).getId();
        equipsArr[i][1] = equip_specs.get(i).getName();
        equipsArr[i][2] = equip_specs.get(i).getValue();
      }
    
    //set table model using this array
    typeSpecTable.setModel(new javax.swing.table.DefaultTableModel(equipsArr,
        new String[] { "Id", "Name", "Value" }));

    //change table header color
    typeSpecTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
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

    //label under tabel show specs count viewed in table
    no_of_rows.setText("Table result: "+
      Integer.toString(typeSpecTable.getRowCount())+"  specifications");

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
    typeSpecTable = new javax.swing.JTable();
    tableLabel = new javax.swing.JLabel();
    no_of_rows = new java.awt.Label();
    EditPanel = new javax.swing.JPanel();
    jSeparator1 = new javax.swing.JSeparator();
    idLabel = new javax.swing.JLabel();
    idText = new javax.swing.JTextField();
    typeIDLabel = new javax.swing.JLabel();
    typeComboBox = new javax.swing.JComboBox<>();
    specifComboBox = new javax.swing.JComboBox<>();
    SpecIdLabel = new javax.swing.JLabel();
    jLabel10 = new javax.swing.JLabel();
    savePanel = new javax.swing.JPanel();
    insertButton = new javax.swing.JButton();
    deletePanel = new javax.swing.JPanel();
    deleteButton = new javax.swing.JButton();
    specsPanel = new javax.swing.JPanel();
    viewSpecButton = new javax.swing.JButton();
    jLabel8 = new javax.swing.JLabel();
    jSeparator2 = new javax.swing.JSeparator();
    refreshPanel = new javax.swing.JPanel();
    refreshButton = new javax.swing.JButton();
    BackButton = new javax.swing.JButton();
    BackIcon = new javax.swing.JLabel();

    setBackground(new java.awt.Color(245, 245, 245));
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    typeSpecTable.setAutoCreateRowSorter(true);
    typeSpecTable.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    typeSpecTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {
        {null, null, null},
        {null, null, null},
        {null, null, null},
        {null, null, null}
      },
      new String []
      {
        "ID", "Type ID", "Specification ID"
      }
    )
    {
      Class[] types = new Class []
      {
        java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
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
    typeSpecTable.setFillsViewportHeight(true);
    typeSpecTable.setRowHeight(25);
    typeSpecTable.setSelectionBackground(new java.awt.Color(0, 153, 204));
    typeSpecTable.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        typeSpecTableMouseClicked(evt);
      }
    });
    typeSpecTable.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyPressed(java.awt.event.KeyEvent evt)
      {
        typeSpecTableKeyPressed(evt);
      }
    });
    jScrollPane1.setViewportView(typeSpecTable);

    add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 79, 730, 790));

    tableLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    tableLabel.setForeground(new java.awt.Color(231, 78, 123));
    tableLabel.setText("Equipment Types Specifications Relation Table:");
    add(tableLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 450, 30));

    no_of_rows.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
    no_of_rows.setForeground(new java.awt.Color(135, 135, 135));
    no_of_rows.setText("no of rows");
    add(no_of_rows, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 870, 170, 30));

    EditPanel.setBackground(new java.awt.Color(245, 245, 245));
    EditPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    EditPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 534, 550, 30));

    idLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    idLabel.setForeground(new java.awt.Color(0, 51, 204));
    idLabel.setText("ID");
    EditPanel.add(idLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

    idText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    idText.setText("Enter ID");
    idText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    idText.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        idTextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        idTextFocusLost(evt);
      }
    });
    EditPanel.add(idText, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 174, 550, 60));

    typeIDLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    typeIDLabel.setForeground(new java.awt.Color(0, 51, 204));
    typeIDLabel.setText("Type ID");
    EditPanel.add(typeIDLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, 40));

    typeComboBox.setBackground(new java.awt.Color(255, 255, 255));
    typeComboBox.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    typeComboBox.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        typeComboBoxFocusGained(evt);
      }
    });
    typeComboBox.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        typeComboBoxActionPerformed(evt);
      }
    });
    EditPanel.add(typeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 550, 50));

    specifComboBox.setBackground(new java.awt.Color(255, 255, 255));
    specifComboBox.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    specifComboBox.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        specifComboBoxFocusGained(evt);
      }
    });
    specifComboBox.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        specifComboBoxMouseClicked(evt);
      }
    });
    specifComboBox.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        specifComboBoxActionPerformed(evt);
      }
    });
    EditPanel.add(specifComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 380, 550, 50));

    SpecIdLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    SpecIdLabel.setForeground(new java.awt.Color(0, 51, 204));
    SpecIdLabel.setText("Specification ID");
    EditPanel.add(SpecIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, -1, 50));

    jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_maintenance_128px.png"))); // NOI18N
    EditPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, -1, -1));

    savePanel.setBackground(new java.awt.Color(0, 129, 211));

    insertButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    insertButton.setForeground(new java.awt.Color(255, 255, 255));
    insertButton.setText("Insert");
    insertButton.setBorderPainted(false);
    insertButton.setContentAreaFilled(false);
    insertButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        insertButtonMouseMoved(evt);
      }
    });
    insertButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        insertButtonMouseExited(evt);
      }
    });
    insertButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        insertButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout savePanelLayout = new javax.swing.GroupLayout(savePanel);
    savePanel.setLayout(savePanelLayout);
    savePanelLayout.setHorizontalGroup(
      savePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(insertButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
    );
    savePanelLayout.setVerticalGroup(
      savePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(insertButton, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(savePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, -1, 50));

    deletePanel.setBackground(new java.awt.Color(0, 129, 211));

    deleteButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    deleteButton.setForeground(new java.awt.Color(255, 255, 255));
    deleteButton.setText("Delete");
    deleteButton.setBorderPainted(false);
    deleteButton.setContentAreaFilled(false);
    deleteButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        deleteButtonMouseMoved(evt);
      }
    });
    deleteButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        deleteButtonMouseExited(evt);
      }
    });
    deleteButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        deleteButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout deletePanelLayout = new javax.swing.GroupLayout(deletePanel);
    deletePanel.setLayout(deletePanelLayout);
    deletePanelLayout.setHorizontalGroup(
      deletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
    );
    deletePanelLayout.setVerticalGroup(
      deletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(deletePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 460, 110, 50));

    specsPanel.setBackground(new java.awt.Color(0, 129, 211));

    viewSpecButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    viewSpecButton.setForeground(new java.awt.Color(255, 255, 255));
    viewSpecButton.setText("View All Specifications ");
    viewSpecButton.setBorderPainted(false);
    viewSpecButton.setContentAreaFilled(false);
    viewSpecButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        viewSpecButtonMouseMoved(evt);
      }
    });
    viewSpecButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        viewSpecButtonMouseExited(evt);
      }
    });
    viewSpecButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        viewSpecButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout specsPanelLayout = new javax.swing.GroupLayout(specsPanel);
    specsPanel.setLayout(specsPanelLayout);
    specsPanelLayout.setHorizontalGroup(
      specsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(viewSpecButton, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
    );
    specsPanelLayout.setVerticalGroup(
      specsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(viewSpecButton, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
    );

    EditPanel.add(specsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 550, 220, -1));

    add(EditPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 170, 630, 700));

    jLabel8.setBackground(new java.awt.Color(231, 78, 123));
    jLabel8.setFont(new java.awt.Font("VIP Rawy Regular", 0, 36)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(231, 78, 123));
    jLabel8.setText("Equipment type specification");
    add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 70, 560, 90));
    add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 140, 620, 20));

    refreshPanel.setBackground(new java.awt.Color(0, 129, 211));

    refreshButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    refreshButton.setForeground(new java.awt.Color(255, 255, 255));
    refreshButton.setText("Refresh ");
    refreshButton.setBorderPainted(false);
    refreshButton.setContentAreaFilled(false);
    refreshButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        refreshButtonMouseMoved(evt);
      }
    });
    refreshButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        refreshButtonMouseExited(evt);
      }
    });
    refreshButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        refreshButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout refreshPanelLayout = new javax.swing.GroupLayout(refreshPanel);
    refreshPanel.setLayout(refreshPanelLayout);
    refreshPanelLayout.setHorizontalGroup(
      refreshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(refreshButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
    );
    refreshPanelLayout.setVerticalGroup(
      refreshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, refreshPanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    add(refreshPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 880, 120, -1));

    BackButton.setBackground(new java.awt.Color(255, 255, 255));
    BackButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 24)); // NOI18N
    BackButton.setText("Back");
    BackButton.setToolTipText("");
    BackButton.setBorderPainted(false);
    BackButton.setContentAreaFilled(false);
    BackButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    BackButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        BackButtonMouseMoved(evt);
      }
    });
    BackButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mousePressed(java.awt.event.MouseEvent evt)
      {
        BackButtonMousePressed(evt);
      }
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        BackButtonMouseExited(evt);
      }
    });
    BackButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        BackButtonActionPerformed(evt);
      }
    });
    add(BackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1460, 880, 80, 50));

    BackIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_forward_filled_50px.png"))); // NOI18N
    BackIcon.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        BackIconMouseClicked(evt);
      }
    });
    add(BackIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(1530, 880, 50, 50));
  }//GEN-END:initComponents

  private void typeSpecTableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_typeSpecTableMouseClicked
  {//GEN-HEADEREND:event_typeSpecTableMouseClicked

    int row = typeSpecTable.getSelectedRow(); //get the selected row
    idText.setText(typeSpecTable.getValueAt(row, 0).toString());
    
  }//GEN-LAST:event_typeSpecTableMouseClicked

  private void typeSpecTableKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_typeSpecTableKeyPressed
  {//GEN-HEADEREND:event_typeSpecTableKeyPressed

    // set text of textfields by the data of the keypressed row in table
    if(evt.getExtendedKeyCode()==KeyEvent.VK_UP||
      evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
      {
        int row = typeSpecTable.getSelectedRow();
        idText.setText(typeSpecTable.getValueAt(row, 0).toString());
      }
  }//GEN-LAST:event_typeSpecTableKeyPressed

  private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_refreshButtonActionPerformed
  {//GEN-HEADEREND:event_refreshButtonActionPerformed
    //implementation of refresh button

    try
      {

        // reset and repaint the table then set text of textfields to its default when panel is opened
        setTableModel(business.viewAll());
        typeSpecTable.repaint();
        idText.setText("Enter ID");
        tableLabel.setText("Equipment Types Specifications Relation Table:");

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

  }//GEN-LAST:event_refreshButtonActionPerformed

  private void viewSpecButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_viewSpecButtonActionPerformed
  {//GEN-HEADEREND:event_viewSpecButtonActionPerformed
    //implementation of view all specifacyions button
    //view all specifications in database to user
    try
      {

        /* create object of EquipspecificationbaoImpl through BaoFactory().createEquipmentSpecificationBao(user) method
      then call listAll method from bao layer
      */
        EquipSpecificationBao b =
          new BaoFactory().createEquipmentSpecificationBao(user);

        //reset table with the specifications list and repaint
        equip_spe(b.listAll());
        typeSpecTable.repaint();

        // reset label text
        tableLabel.setText("All Specifications :");
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

  }//GEN-LAST:event_viewSpecButtonActionPerformed

  private void idTextFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_idTextFocusGained
  {//GEN-HEADEREND:event_idTextFocusGained

    // delete text from textfield when textfield is selected
    if(idText.getText().equalsIgnoreCase("Enter ID"))
      idText.setText("");

    idText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

  }//GEN-LAST:event_idTextFocusGained

  private void idTextFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_idTextFocusLost
  {//GEN-HEADEREND:event_idTextFocusLost

    // reset text of textfield to its default when its focus is lost
    if(idText.getText().trim().equalsIgnoreCase(""))
      idText.setText("Enter ID");

    idText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

  }//GEN-LAST:event_idTextFocusLost

  private void typeComboBoxFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_typeComboBoxFocusGained
  {//GEN-HEADEREND:event_typeComboBoxFocusGained
    //set the comboBox items by equipment types names of equipments
    try
      {
        EquipmentTypeBao eq_type_bao =
          new BaoFactory().createEquipmentTypeBao(user);
        List<EquipmentTypeDto> eq_type_list = eq_type_bao.viewAll();
        typeComboBox.removeAllItems();
        if(eq_type_list!=null&&!eq_type_list.isEmpty())
          for(int i = 0; i<eq_type_list.size(); i++)
            {
              typeComboBox.addItem(Integer.toString(eq_type_list.get(i).getId()));
            }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }//GEN-LAST:event_typeComboBoxFocusGained

  private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_typeComboBoxActionPerformed
  {//GEN-HEADEREND:event_typeComboBoxActionPerformed

  }//GEN-LAST:event_typeComboBoxActionPerformed

  private void specifComboBoxFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_specifComboBoxFocusGained
  {//GEN-HEADEREND:event_specifComboBoxFocusGained
    //set the comboBox items by equipment specifications IDs
    try
      {
        EquipSpecificationBao eq_spec_bao =
          new BaoFactory().createEquipmentSpecificationBao(user);
        List<EquipSpecificationDto> eq_spec_list = eq_spec_bao.listAll();
        specifComboBox.removeAllItems();
        if(eq_spec_list!=null&&!eq_spec_list.isEmpty())
          for(int i = 0; i<eq_spec_list.size(); i++)
            {
              specifComboBox.addItem(Integer.toString(eq_spec_list.get(i).getId()));
            }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }//GEN-LAST:event_specifComboBoxFocusGained

  private void specifComboBoxMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_specifComboBoxMouseClicked
  {//GEN-HEADEREND:event_specifComboBoxMouseClicked

  }//GEN-LAST:event_specifComboBoxMouseClicked

  private void specifComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_specifComboBoxActionPerformed
  {//GEN-HEADEREND:event_specifComboBoxActionPerformed

  }//GEN-LAST:event_specifComboBoxActionPerformed


  private void insertButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_insertButtonActionPerformed
  {//GEN-HEADEREND:event_insertButtonActionPerformed
    try
      {

        // check the data validity enterd by user
        if(checkValidity())
          {
            ets = new EquipTypeSpecDto();
            // store data entered by user in dto object attributes
            ets.setId(Integer.parseInt(idText.getText()));
            ets.setType_id(Integer.parseInt(typeComboBox.getSelectedItem().toString()));
            ets.setSpecification_id(Integer.parseInt(specifComboBox.getSelectedItem().toString()));

            // call update method from bao then show message if the process terminated successfully
            if(business.add(ets))
              {

                //show message when process terminated successfully and reset table
                JOptionPane.showMessageDialog(null,
                  "Record Inserted Successfully");
                setTableModel(business.viewAll());
              }
            else
              {

                JOptionPane.showMessageDialog(null,
                  "This Record Refers to non Existent Type or Specification");
              }
          }

        //repaint the table with the lateset updates
        // set text fields text to the default at the end of the process
        typeSpecTable.repaint();
        idText.setText("Enter ID");
        tableLabel.setText("Equipment Types Specifications Relation Table:");
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

  }//GEN-LAST:event_insertButtonActionPerformed

  private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deleteButtonActionPerformed
  {//GEN-HEADEREND:event_deleteButtonActionPerformed
    // implementation of delete button
    try
      {

        // get the select row count from table
        int row = typeSpecTable.getSelectedRow();
        ets = new EquipTypeSpecDto();
        // get data from the selected row and store it to the dto object fields
        ets.setId(Integer.parseInt(typeSpecTable.getModel().getValueAt(row,
          0).toString()));
        ets.setType_id(Integer.parseInt(typeSpecTable.getModel().getValueAt(row,
          1).toString()));
        ets.setSpecification_id(Integer.parseInt(typeSpecTable.getModel().getValueAt(row,
          2).toString()));

        // call delete method from bao and pass the dto object to it as parameter to delete it
        if(business.delete(ets))
          {

            //show message when process terminated successfully and reset table with the latest data
            JOptionPane.showMessageDialog(null,
              "Record Deleted Successfully");
            setTableModel(business.viewAll());
          }
        //set the table with the newest list in database and repaint it using listAll method in bao
        typeSpecTable.repaint();
        idText.setText("Enter ID");
        tableLabel.setText("Equipment Types Specifications Relation Table:");
      }
    catch(java.lang.ArrayIndexOutOfBoundsException e)
      {

        // show message if no row is selected from table
        JOptionPane.showMessageDialog(null,
          "Please select row from table to delete");
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }//GEN-LAST:event_deleteButtonActionPerformed

  private void insertButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_insertButtonMouseExited
  {//GEN-HEADEREND:event_insertButtonMouseExited
    // TODO add your handling code here:
    savePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_insertButtonMouseExited

  private void deleteButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_deleteButtonMouseExited
  {//GEN-HEADEREND:event_deleteButtonMouseExited
    // TODO add your handling code here:
    deletePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_deleteButtonMouseExited

  private void viewSpecButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_viewSpecButtonMouseExited
  {//GEN-HEADEREND:event_viewSpecButtonMouseExited
    // TODO add your handling code here:
    specsPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_viewSpecButtonMouseExited

  private void refreshButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_refreshButtonMouseExited
  {//GEN-HEADEREND:event_refreshButtonMouseExited
    // TODO add your handling code here:
    refreshPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_refreshButtonMouseExited

  private void insertButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_insertButtonMouseMoved
  {//GEN-HEADEREND:event_insertButtonMouseMoved
    // TODO add your handling code here:
    savePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_insertButtonMouseMoved

  private void deleteButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_deleteButtonMouseMoved
  {//GEN-HEADEREND:event_deleteButtonMouseMoved
    // TODO add your handling code here:
    deletePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_deleteButtonMouseMoved

  private void viewSpecButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_viewSpecButtonMouseMoved
  {//GEN-HEADEREND:event_viewSpecButtonMouseMoved
    // TODO add your handling code here:
    specsPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_viewSpecButtonMouseMoved

  private void refreshButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_refreshButtonMouseMoved
  {//GEN-HEADEREND:event_refreshButtonMouseMoved

    refreshPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_refreshButtonMouseMoved

  private void BackButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_BackButtonMouseMoved
  {//GEN-HEADEREND:event_BackButtonMouseMoved

    /* change font color to blue
    * when user move mouse to this button if it enable */
    if(BackButton.isEnabled())
     BackButton.setForeground(Color.blue);
  }//GEN-LAST:event_BackButtonMouseMoved

  private void BackButtonMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_BackButtonMousePressed
  {//GEN-HEADEREND:event_BackButtonMousePressed

    //change font color to red when user press this button if it enable
    if(BackButton.isEnabled())
    BackButton.setForeground(Color.red);
  }//GEN-LAST:event_BackButtonMousePressed

  private void BackButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_BackButtonMouseExited
  {//GEN-HEADEREND:event_BackButtonMouseExited

    /* change font color to black
    * when user exit mouse from this button if it enable */
    if(BackButton.isEnabled())
    BackButton.setForeground(Color.black);
  }//GEN-LAST:event_BackButtonMouseExited

  private void BackButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_BackButtonActionPerformed
  {//GEN-HEADEREND:event_BackButtonActionPerformed
    try
      {
        //create equipment type panel
        EquipmentTypePanel equip_type_panel = new EquipmentTypePanel(user);
        //set panel bounds as this panel
        equip_type_panel.setBounds(this.getBounds());
        this.removeAll(); //remove all component in this panel
        this.revalidate(); //revalidate it
        this.repaint(); //repaint it
        this.add(equip_type_panel); //add equipment type panel to this panel
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }//GEN-LAST:event_BackButtonActionPerformed

  private void BackIconMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_BackIconMouseClicked
  {//GEN-HEADEREND:event_BackIconMouseClicked
    try
      {
        //create equipment type panel
        EquipmentTypePanel equip_type_panel = new EquipmentTypePanel(user);
        //set panel bounds as this panel
        equip_type_panel.setBounds(this.getBounds());
        this.removeAll(); //remove all component in this panel
        this.revalidate(); //revalidate it
        this.repaint(); //repaint it
        this.add(equip_type_panel); //add equipment type panel to this panel
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }//GEN-LAST:event_BackIconMouseClicked



  // method to check the validity of user input in each text field
  private boolean checkValidity()
  {
    try
      {
        if(idText.getText().equalsIgnoreCase("Enter ID"))
          {
            JOptionPane.showMessageDialog(null, "Please, enter id number",
              "", JOptionPane.ERROR_MESSAGE);
            return false;
          }
        Integer.parseInt(idText.getText());
        return true;
      }
    catch(NumberFormatException e)
      {
        JOptionPane.showMessageDialog(null,
          "ID is invalid! \n (please enter a number)");
        return false;
      }
  }


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton BackButton;
  private javax.swing.JLabel BackIcon;
  private javax.swing.JPanel EditPanel;
  private javax.swing.JLabel SpecIdLabel;
  private javax.swing.JButton deleteButton;
  private javax.swing.JPanel deletePanel;
  private javax.swing.JLabel idLabel;
  private javax.swing.JTextField idText;
  private javax.swing.JButton insertButton;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private java.awt.Label no_of_rows;
  private javax.swing.JButton refreshButton;
  private javax.swing.JPanel refreshPanel;
  private javax.swing.JPanel savePanel;
  private javax.swing.JComboBox<String> specifComboBox;
  private javax.swing.JPanel specsPanel;
  private javax.swing.JLabel tableLabel;
  private javax.swing.JComboBox<String> typeComboBox;
  private javax.swing.JLabel typeIDLabel;
  private javax.swing.JTable typeSpecTable;
  private javax.swing.JButton viewSpecButton;
  // End of variables declaration//GEN-END:variables

}
