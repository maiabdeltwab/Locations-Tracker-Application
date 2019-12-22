package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.BuildingBao;
import com.fym.lta.BAO.DepartmentBao;
import com.fym.lta.BAO.LocationBao;
import com.fym.lta.BAO.LocationTypeBao;
import com.fym.lta.BAO.SlotBao;
import com.fym.lta.BAO.StaffBao;
import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StaffDto;
import com.fym.lta.DTO.UserDto;

import java.awt.Color;
import java.awt.Component;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Mai-AbdEltwab
 */
public class StaffLoadPanel extends javax.swing.JPanel
{
  @SuppressWarnings("compatibility:8257125201016826125")
  private static final long serialVersionUID = 1L;

  private static UserDto user;

  /** Creates new form StaffLoadPanel */
  public StaffLoadPanel(UserDto u)
  {
    user = u;
    initComponents();
    defaultdata();
    
    setTableModel(null);
    defaultRender();

  }


  private void setTableModel(List<SlotDto> slots)
  {

    Object[][] tableArr = new Object[25][5];

    //set days
    tableArr[2][0] = "                  Sunday";
    tableArr[7][0] = "                  Monday";
    tableArr[12][0] = "                  Tuesday";
    tableArr[17][0] = "                Wednesday";
    tableArr[22][0] = "                  Thursday";


    for(int i = 0; slots!=null&&i<slots.size(); i++)
      {

        int day = -1;

        if(slots.get(i).getDay().equalsIgnoreCase("Sunday"))
          day = 0;
        else if(slots.get(i).getDay().equalsIgnoreCase("Monday"))
          day = 5;
        else if(slots.get(i).getDay().equalsIgnoreCase("Tuesday"))
          day = 10;
        else if(slots.get(i).getDay().equalsIgnoreCase("Wednesday"))
          day = 15;
        else if(slots.get(i).getDay().equalsIgnoreCase("Thursday"))
          day = 20;


        if(slots.get(i).getStaff().size()==2)

          {
            tableArr[day][slots.get(i).getNum()] =
              "Course:  "+slots.get(i).getCourse().getCode();

            tableArr[day+1][slots.get(i).getNum()] =
              "Staff: "+slots.get(i).getStaff().get(0).getPosition()+". "+
              slots.get(i).getStaff().get(0).getName();

            tableArr[day+2][slots.get(i).getNum()] =
              "       "+slots.get(i).getStaff().get(1).getPosition()+". "+
              slots.get(i).getStaff().get(1).getName();

            tableArr[day+3][slots.get(i).getNum()] =
              slots.get(i).getSlot_type();

            tableArr[day+4][slots.get(i).getNum()] =
              "Student number: "+slots.get(i).getStudent_number();
          }

        else if(slots.get(i).getStaff().size()==1)
          {

            tableArr[day][slots.get(i).getNum()] =
              "Course:  "+slots.get(i).getCourse().getCode();

            tableArr[day+1][slots.get(i).getNum()] =
              "Staff: "+slots.get(i).getStaff().get(0).getPosition()+". "+
              slots.get(i).getStaff().get(0).getName();

            tableArr[day+2][slots.get(i).getNum()] =
              slots.get(i).getSlot_type();

            tableArr[day+3][slots.get(i).getNum()] =
              "Student number: "+slots.get(i).getStudent_number();

          }

        else if(slots.get(i).getStaff().size()==3)
          {
            tableArr[day][slots.get(i).getNum()] =
              "Course:  "+slots.get(i).getCourse().getCode();

            tableArr[day+1][slots.get(i).getNum()] =
              "Staff: "+slots.get(i).getStaff().get(0).getPosition()+". "+
              slots.get(i).getStaff().get(0).getName();

            tableArr[day+2][slots.get(i).getNum()] =
              slots.get(i).getStaff().get(1).getPosition()+". "+
              slots.get(i).getStaff().get(1).getName()+", "+
              slots.get(i).getStaff().get(2).getPosition()+". "+
              slots.get(i).getStaff().get(2).getName();

            tableArr[day+3][slots.get(i).getNum()] =
              slots.get(i).getSlot_type();

            tableArr[day+4][slots.get(i).getNum()] =
              "Student number: "+slots.get(i).getStudent_number();
          }

      }

    locationOccupancyTable.setModel(new javax.swing.table.DefaultTableModel(tableArr,
        new String[] { "                    Time Slot",
        "                    1st slot", "                    2nd slot",
        "                   3rd slot", "                    4th slot" }));

    //change header color
    locationOccupancyTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
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
    if(slots!=null)
      no_of_rows.setText("Table result: "+Integer.toString(slots.size())+
        "  slots");

  }

  /**set default table cell renderer*/
  private void defaultRender()
  {
    //set slot borders
    for(int col = 0; col<5; col++)
      {
        locationOccupancyTable.getColumnModel().getColumn(col).setCellRenderer(new TableCellRenderer()
        {
          @Override
          public Component getTableCellRendererComponent(JTable jTable,
            Object object, boolean b, boolean b2, int row, int column)
          {

            DefaultTableCellRenderer renderer =
              new DefaultTableCellRenderer();

            DefaultTableCellRenderer comp =
              (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
              object, b, b2, row, column);

            comp.setBackground(Color.decode("#FDFDFD"));

            if(row==4||row==9||row==14||row==19)
              comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));


            return comp;
          }
        });
      }

    locationOccupancyTable.setShowVerticalLines(true);
    locationOccupancyTable.setGridColor(Color.lightGray);


    //Change color,font,order of day column
    locationOccupancyTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer()
    {
      @Override
      public Component getTableCellRendererComponent(JTable jTable,
        Object object, boolean b, boolean b2, int row, int column)
      {

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

        DefaultTableCellRenderer comp =
          (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
          object, b, b2, row, column);

        comp.setBackground(Color.decode("#FDFDFD"));
        comp.setForeground(Color.decode("#0033CC")); //set cell color
        comp.setFont(new java.awt.Font("VIP Rawy Regular", 0, 22));

        if(row==4||row==9||row==14||row==19)
          comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
            Color.lightGray));

        return comp;
      }
    });

  }

  /**set default data in combos*/
  private void defaultdata()
  {

    //Set all existing location type to location type combobox
    try
      {
        DepartmentBao depart_Bao =
          new BaoFactory().createDepartmentBao(user); //create department bao object
        List<DepartmentDto> depart_list =
          depart_Bao.viewAll(); //get all departments

        department_combo.removeAllItems(); //remove all existing data in department combobox
        department_combo.addItem(" ");

        //set department names to the department combobox
        if(depart_list!=null&&!depart_list.isEmpty())
          {
            for(int i = 0; i<depart_list.size(); i++)
              {
                department_combo.addItem(depart_list.get(i).getName());
              }
            department_combo.setSelectedIndex(-1); //select nothing in this combo

          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

   
    //set all existing staff members in db to staff combobox
    try
      {
        //remove all items in staff combobox
        staff_combo.removeAllItems();

        //create new staff bao object
        StaffBao staff_bao = new BaoFactory().createStaffBao(user);
      
        //get all existing staff members
        List<StaffDto> staff_members = staff_bao.viewAll();

        //set staff members to staff combo
        if(staff_members!=null&&!staff_members.isEmpty())
          {
            for(int i = 0; i<staff_members.size(); i++)
              {
                staff_combo.addItem(staff_members.get(i).getName());
              }

            staff_combo.setSelectedIndex(-1); //select nothing in this combo
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

    departmentText = new javax.swing.JLabel();
    CodeLabel = new javax.swing.JLabel();
    StaffLabel = new javax.swing.JLabel();
    department_combo = new javax.swing.JComboBox<>();
    staff_combo = new javax.swing.JComboBox<>();
    jScrollPane1 = new javax.swing.JScrollPane();
    locationOccupancyTable = new javax.swing.JTable();
    jLabel8 = new javax.swing.JLabel();
    no_of_rows = new java.awt.Label();
    position_combo = new javax.swing.JComboBox<>();
    PositionLabel = new javax.swing.JLabel();

    setBackground(new java.awt.Color(245, 245, 245));
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    departmentText.setBackground(new java.awt.Color(0, 51, 204));
    departmentText.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    departmentText.setForeground(new java.awt.Color(0, 51, 204));
    departmentText.setText("Department");
    add(departmentText, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 130, 50));

    CodeLabel.setBackground(new java.awt.Color(0, 51, 204));
    CodeLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 30)); // NOI18N
    CodeLabel.setForeground(new java.awt.Color(231, 78, 123));
    CodeLabel.setText("Space Occupancy");
    add(CodeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 240, 50));

    StaffLabel.setBackground(new java.awt.Color(0, 51, 204));
    StaffLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    StaffLabel.setForeground(new java.awt.Color(0, 51, 204));
    StaffLabel.setText("Staff");
    add(StaffLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 150, -1, 50));

    department_combo.setBackground(new java.awt.Color(255, 255, 255));
    department_combo.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    department_combo.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        department_comboActionPerformed(evt);
      }
    });
    add(department_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 300, 50));

    staff_combo.setBackground(new java.awt.Color(255, 255, 255));
    staff_combo.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    staff_combo.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mousePressed(java.awt.event.MouseEvent evt)
      {
        staff_comboMousePressed(evt);
      }
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        staff_comboMouseClicked(evt);
      }
    });
    staff_combo.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        staff_comboActionPerformed(evt);
      }
    });
    add(staff_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 150, 360, 50));

    locationOccupancyTable.setBackground(new java.awt.Color(253, 253, 253));
    locationOccupancyTable.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    locationOccupancyTable.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    locationOccupancyTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {
        {null, null, null, null, null},
        {null, null, null, null, null},
        {null, null, null, null, null},
        {null, null, null, null, null},
        {null, null, null, null, null}
      },
      new String []
      {
        "Time Slot", "1st slot", "2nd slot", "3rd slot", "4th slot"
      }
    )
    {
      boolean[] canEdit = new boolean []
      {
        false, false, false, false, false
      };

      public boolean isCellEditable(int rowIndex, int columnIndex)
      {
        return canEdit [columnIndex];
      }
    });
    locationOccupancyTable.setCellSelectionEnabled(true);
    locationOccupancyTable.setFillsViewportHeight(true);
    locationOccupancyTable.setFocusable(false);
    locationOccupancyTable.setGridColor(new java.awt.Color(102, 102, 102));
    locationOccupancyTable.setName(""); // NOI18N
    locationOccupancyTable.setRowHeight(24);
    locationOccupancyTable.setRowMargin(0);
    locationOccupancyTable.setSelectionBackground(new java.awt.Color(0, 153, 204));
    locationOccupancyTable.setShowHorizontalLines(false);
    locationOccupancyTable.setShowVerticalLines(false);
    locationOccupancyTable.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mousePressed(java.awt.event.MouseEvent evt)
      {
        locationOccupancyTableMousePressed(evt);
      }
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        locationOccupancyTableMouseClicked(evt);
      }
    });
    jScrollPane1.setViewportView(locationOccupancyTable);
    locationOccupancyTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    if (locationOccupancyTable.getColumnModel().getColumnCount() > 0)
    {
      locationOccupancyTable.getColumnModel().getColumn(0).setHeaderValue("Time Slot");
      locationOccupancyTable.getColumnModel().getColumn(1).setHeaderValue("1st slot");
      locationOccupancyTable.getColumnModel().getColumn(2).setHeaderValue("2nd slot");
      locationOccupancyTable.getColumnModel().getColumn(3).setHeaderValue("3rd slot");
      locationOccupancyTable.getColumnModel().getColumn(4).setHeaderValue("4th slot");
    }

    add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 1490, 640));

    jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_geo_fence_64px.png"))); // NOI18N
    add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 70, 80));

    no_of_rows.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
    no_of_rows.setForeground(new java.awt.Color(135, 135, 135));
    no_of_rows.setText("No reult ");
    add(no_of_rows, new org.netbeans.lib.awtextra.AbsoluteConstraints(1410, 870, 150, 30));

    position_combo.setBackground(new java.awt.Color(255, 255, 255));
    position_combo.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    position_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Professor ", "Associate Professor", "Assistant Professor", "Assistant lecturer ", "Demonstrator" }));
    position_combo.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        position_comboActionPerformed(evt);
      }
    });
    add(position_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 150, 310, 50));

    PositionLabel.setBackground(new java.awt.Color(0, 51, 204));
    PositionLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    PositionLabel.setForeground(new java.awt.Color(0, 51, 204));
    PositionLabel.setText("Position");
    add(PositionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 150, 100, 50));
  }//GEN-END:initComponents

  private void department_comboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_department_comboActionPerformed
  {//GEN-HEADEREND:event_department_comboActionPerformed

    try
      {
        //check if department combo is not empty and the selected index is not the empty index
        if(department_combo.getSelectedIndex()!=-1)
          {
            List<StaffDto> staff = null; //for result staff set
            DepartmentDto department = null; //for selected department
            String position = null; //for selected position
            
            //create staff bao object
            StaffBao staff_bao =
              new BaoFactory().createStaffBao(user);

            if(department_combo.getSelectedIndex()!=0) //not the empty item
              { 
                //get selected department
                department = new DepartmentDto();
                department.setName(department_combo.getSelectedItem().toString());
              }

            //check if building combo is not empty and the selected index is not the empty index
            if(position_combo.getSelectedIndex()!=-1&&
              position_combo.getSelectedIndex()!=0)
              {
                //get selected building
                position = position_combo.getSelectedItem().toString();
              }
            
            
            //remove all items in staff combobox
            staff_combo.removeAllItems();

            staff =
              staff_bao.search_department_position(department,
              position); //get staff

            //set staff names to staff combo
            if(staff!=null&&!staff.isEmpty())
              {
                for(int i = 0; i<staff.size(); i++)
                  {
                    staff_combo.addItem(staff.get(i).getName());
                  }

                staff_combo.setSelectedIndex(-1); //select nothing in this combo
              }
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    
  }//GEN-LAST:event_department_comboActionPerformed

  private void staff_comboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_staff_comboActionPerformed
  {//GEN-HEADEREND:event_staff_comboActionPerformed


    if(staff_combo.getSelectedIndex()!=-1)
      {
        //get the selected location code
        StaffDto staff_member = new StaffDto();
        staff_member.setName(staff_combo.getSelectedItem().toString());

        SlotBao slot_bao = new BaoFactory().createSlotBao(user);
        List<SlotDto> slots = slot_bao.staff_load(staff_member);

        if(slots!=null)
          {

            setTableModel(slots);
            defaultRender();
            locationOccupancyTable.repaint();

          }
        else
          JOptionPane.showMessageDialog(null,
            "There is no slot assign to this staff.", "Not Found", 1);


      }
  }//GEN-LAST:event_staff_comboActionPerformed

  private void locationOccupancyTableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_locationOccupancyTableMouseClicked
  {//GEN-HEADEREND:event_locationOccupancyTableMouseClicked
    // TODO add your handling code here:

    //non select for day column
    if(locationOccupancyTable.getSelectedColumn()==0)
      locationOccupancyTable.clearSelection();

    //slect slot in day 1
    if(locationOccupancyTable.getSelectedRow()>=0&&
      locationOccupancyTable.getSelectedRow()<=4)
      {
        locationOccupancyTable.addRowSelectionInterval(0, 4);


        defaultRender();

        locationOccupancyTable.getColumnModel().getColumn(locationOccupancyTable.getSelectedColumn()).setCellRenderer(new TableCellRenderer()
        {
          @Override
          public Component getTableCellRendererComponent(JTable jTable,
            Object object, boolean b, boolean b2, int row, int column)
          {

            DefaultTableCellRenderer renderer =
              new DefaultTableCellRenderer();

            DefaultTableCellRenderer comp =
              (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
              object, b, b2, row, column);

            if(row>=0&&row<=4)
              comp.setBackground(Color.decode("#0099CC"));

            if(row==4||row==9||row==14||row==19)
              comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));


            return comp;
          }
        });

      }

    //slect slot in day 2
    if(locationOccupancyTable.getSelectedRow()>=5&&
      locationOccupancyTable.getSelectedRow()<=9)
      {
        locationOccupancyTable.addRowSelectionInterval(5, 9);

        defaultRender();

        locationOccupancyTable.getColumnModel().getColumn(locationOccupancyTable.getSelectedColumn()).setCellRenderer(new TableCellRenderer()
        {
          @Override
          public Component getTableCellRendererComponent(JTable jTable,
            Object object, boolean b, boolean b2, int row, int column)
          {

            DefaultTableCellRenderer renderer =
              new DefaultTableCellRenderer();

            DefaultTableCellRenderer comp =
              (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
              object, b, b2, row, column);

            if(row>=5&&row<=9)
              comp.setBackground(Color.decode("#0099CC"));

            if(row==4||row==9||row==14||row==19)
              comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));


            return comp;
          }
        });

      }

    //slect slot in day 3
    if(locationOccupancyTable.getSelectedRow()>=10&&
      locationOccupancyTable.getSelectedRow()<=14)
      {
        locationOccupancyTable.addRowSelectionInterval(10, 14);

        defaultRender();

        locationOccupancyTable.getColumnModel().getColumn(locationOccupancyTable.getSelectedColumn()).setCellRenderer(new TableCellRenderer()
        {
          @Override
          public Component getTableCellRendererComponent(JTable jTable,
            Object object, boolean b, boolean b2, int row, int column)
          {

            DefaultTableCellRenderer renderer =
              new DefaultTableCellRenderer();

            DefaultTableCellRenderer comp =
              (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
              object, b, b2, row, column);

            if(row>=10&&row<=14)
              comp.setBackground(Color.decode("#0099CC"));

            if(row==4||row==9||row==14||row==19)
              comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));


            return comp;
          }
        });
      }

    //slect slot in day 4
    if(locationOccupancyTable.getSelectedRow()>=15&&
      locationOccupancyTable.getSelectedRow()<=19)
      {
        locationOccupancyTable.addRowSelectionInterval(15, 19);

        defaultRender();

        locationOccupancyTable.getColumnModel().getColumn(locationOccupancyTable.getSelectedColumn()).setCellRenderer(new TableCellRenderer()
        {
          @Override
          public Component getTableCellRendererComponent(JTable jTable,
            Object object, boolean b, boolean b2, int row, int column)
          {

            DefaultTableCellRenderer renderer =
              new DefaultTableCellRenderer();

            DefaultTableCellRenderer comp =
              (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
              object, b, b2, row, column);

            if(row>=15&&row<=19)
              comp.setBackground(Color.decode("#0099CC"));

            if(row==4||row==9||row==14||row==19)
              comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));


            return comp;
          }
        });
      }

    //slect slot in day 5
    if(locationOccupancyTable.getSelectedRow()>=20&&
      locationOccupancyTable.getSelectedRow()<=24)
      {
        locationOccupancyTable.addRowSelectionInterval(20, 24);

        defaultRender();

        locationOccupancyTable.getColumnModel().getColumn(locationOccupancyTable.getSelectedColumn()).setCellRenderer(new TableCellRenderer()
        {
          @Override
          public Component getTableCellRendererComponent(JTable jTable,
            Object object, boolean b, boolean b2, int row, int column)
          {

            DefaultTableCellRenderer renderer =
              new DefaultTableCellRenderer();

            DefaultTableCellRenderer comp =
              (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
              object, b, b2, row, column);
            if(row>=20&&row<=24)
              comp.setBackground(Color.decode("#0099CC"));

            if(row==4||row==9||row==14||row==19)
              comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));


            return comp;
          }
        });
      }


      
  }//GEN-LAST:event_locationOccupancyTableMouseClicked

  private void locationOccupancyTableMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_locationOccupancyTableMousePressed
  {//GEN-HEADEREND:event_locationOccupancyTableMousePressed
 

    //non select for day column
    if(locationOccupancyTable.getSelectedColumn()==0)
      locationOccupancyTable.clearSelection();

    //slect slot in day 1
    if(locationOccupancyTable.getSelectedRow()>=0&&
      locationOccupancyTable.getSelectedRow()<=4)
      {
        locationOccupancyTable.addRowSelectionInterval(0, 4);


        defaultRender();

        locationOccupancyTable.getColumnModel().getColumn(locationOccupancyTable.getSelectedColumn()).setCellRenderer(new TableCellRenderer()
        {
          @Override
          public Component getTableCellRendererComponent(JTable jTable,
            Object object, boolean b, boolean b2, int row, int column)
          {

            DefaultTableCellRenderer renderer =
              new DefaultTableCellRenderer();

            DefaultTableCellRenderer comp =
              (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
              object, b, b2, row, column);

            if(row>=0&&row<=4)
              comp.setBackground(Color.decode("#0099CC"));

            if(row==4||row==9||row==14||row==19)
              comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));


            return comp;
          }
        });

      }

    //slect slot in day 2
    if(locationOccupancyTable.getSelectedRow()>=5&&
      locationOccupancyTable.getSelectedRow()<=9)
      {
        locationOccupancyTable.addRowSelectionInterval(5, 9);

        defaultRender();

        locationOccupancyTable.getColumnModel().getColumn(locationOccupancyTable.getSelectedColumn()).setCellRenderer(new TableCellRenderer()
        {
          @Override
          public Component getTableCellRendererComponent(JTable jTable,
            Object object, boolean b, boolean b2, int row, int column)
          {

            DefaultTableCellRenderer renderer =
              new DefaultTableCellRenderer();

            DefaultTableCellRenderer comp =
              (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
              object, b, b2, row, column);

            if(row>=5&&row<=9)
              comp.setBackground(Color.decode("#0099CC"));

            if(row==4||row==9||row==14||row==19)
              comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));


            return comp;
          }
        });

      }

    //slect slot in day 3
    if(locationOccupancyTable.getSelectedRow()>=10&&
      locationOccupancyTable.getSelectedRow()<=14)
      {
        locationOccupancyTable.addRowSelectionInterval(10, 14);

        defaultRender();

        locationOccupancyTable.getColumnModel().getColumn(locationOccupancyTable.getSelectedColumn()).setCellRenderer(new TableCellRenderer()
        {
          @Override
          public Component getTableCellRendererComponent(JTable jTable,
            Object object, boolean b, boolean b2, int row, int column)
          {

            DefaultTableCellRenderer renderer =
              new DefaultTableCellRenderer();

            DefaultTableCellRenderer comp =
              (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
              object, b, b2, row, column);

            if(row>=10&&row<=14)
              comp.setBackground(Color.decode("#0099CC"));

            if(row==4||row==9||row==14||row==19)
              comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));


            return comp;
          }
        });
      }

    //slect slot in day 4
    if(locationOccupancyTable.getSelectedRow()>=15&&
      locationOccupancyTable.getSelectedRow()<=19)
      {
        locationOccupancyTable.addRowSelectionInterval(15, 19);

        defaultRender();

        locationOccupancyTable.getColumnModel().getColumn(locationOccupancyTable.getSelectedColumn()).setCellRenderer(new TableCellRenderer()
        {
          @Override
          public Component getTableCellRendererComponent(JTable jTable,
            Object object, boolean b, boolean b2, int row, int column)
          {

            DefaultTableCellRenderer renderer =
              new DefaultTableCellRenderer();

            DefaultTableCellRenderer comp =
              (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
              object, b, b2, row, column);

            if(row>=15&&row<=19)
              comp.setBackground(Color.decode("#0099CC"));

            if(row==4||row==9||row==14||row==19)
              comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));


            return comp;
          }
        });
      }

    //slect slot in day 5
    if(locationOccupancyTable.getSelectedRow()>=20&&
      locationOccupancyTable.getSelectedRow()<=24)
      {
        locationOccupancyTable.addRowSelectionInterval(20, 24);

        defaultRender();

        locationOccupancyTable.getColumnModel().getColumn(locationOccupancyTable.getSelectedColumn()).setCellRenderer(new TableCellRenderer()
        {
          @Override
          public Component getTableCellRendererComponent(JTable jTable,
            Object object, boolean b, boolean b2, int row, int column)
          {

            DefaultTableCellRenderer renderer =
              new DefaultTableCellRenderer();

            DefaultTableCellRenderer comp =
              (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
              object, b, b2, row, column);
            if(row>=20&&row<=24)
              comp.setBackground(Color.decode("#0099CC"));

            if(row==4||row==9||row==14||row==19)
              comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));


            return comp;
          }
        });
      }


      
  }//GEN-LAST:event_locationOccupancyTableMousePressed

  private void staff_comboMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_staff_comboMouseClicked
  {//GEN-HEADEREND:event_staff_comboMouseClicked
    // TODO add your handling code here:
  }//GEN-LAST:event_staff_comboMouseClicked

  private void staff_comboMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_staff_comboMousePressed
  {//GEN-HEADEREND:event_staff_comboMousePressed
    // TODO add your handling code here:
    
  }//GEN-LAST:event_staff_comboMousePressed

  private void position_comboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_position_comboActionPerformed
  {//GEN-HEADEREND:event_position_comboActionPerformed
    
    try
      {

        //check if department combo is not empty and the selected index is not the empty index
        if(position_combo.getSelectedIndex()!=-1)
          {
            List<StaffDto> staff = null; //for result staff set
            DepartmentDto department = null; //for selected department
            String position = null; //for selected position

            //create staff bao object
            StaffBao staff_bao = new BaoFactory().createStaffBao(user);

            if(position_combo.getSelectedIndex()!=0) //not the empty item
              {
                //get selected building
                position = position_combo.getSelectedItem().toString();
              }

            //check if building combo is not empty and the selected index is not the empty index
            if(department_combo.getSelectedIndex()!=-1&&
              department_combo.getSelectedIndex()!=0)
              {
                //get selected department
                department = new DepartmentDto();
                department.setName(department_combo.getSelectedItem().toString());
              }


            //remove all items in staff combobox
            staff_combo.removeAllItems();

            staff =
              staff_bao.search_department_position(department,
              position); //get staff

            //set staff names to staff combo
            if(staff!=null&&!staff.isEmpty())
              {
                for(int i = 0; i<staff.size(); i++)
                  {
                    staff_combo.addItem(staff.get(i).getName());
                  }

                staff_combo.setSelectedIndex(-1); //select nothing in this combo
              }

          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  
  }//GEN-LAST:event_position_comboActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel CodeLabel;
  private javax.swing.JLabel PositionLabel;
  private javax.swing.JLabel StaffLabel;
  private javax.swing.JLabel departmentText;
  private javax.swing.JComboBox<String> department_combo;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTable locationOccupancyTable;
  private java.awt.Label no_of_rows;
  private javax.swing.JComboBox<String> position_combo;
  private javax.swing.JComboBox<String> staff_combo;
  // End of variables declaration//GEN-END:variables

}


