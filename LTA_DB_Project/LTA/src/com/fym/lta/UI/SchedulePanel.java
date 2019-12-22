
package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.DepartmentBao;
import com.fym.lta.BAO.ScheduleBao;
import com.fym.lta.BAO.SlotBao;
import com.fym.lta.BAO.StageBao;
import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.RoleScreenDao;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;
import com.fym.lta.Excel.ReadScheduleExcel;
import com.fym.lta.Excel.UpdateScheduleExcel;
import com.fym.lta.Excel.WriteScheduleExcel;

import java.awt.Color;
import java.awt.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
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
 * @author Mai-AbdEltwab
 */

public class SchedulePanel extends javax.swing.JPanel
{
  @SuppressWarnings("compatibility:-1041221552983012859")
  private static final long serialVersionUID = 1L;

  private static UserDto user = null;
  private static SlotBao slotbao = null;
  private static ScheduleBao schedulebao = null;


  /** Creates new form AutoAssignPanel */
  public SchedulePanel(UserDto u)
  {
    user = u;
    slotbao = new BaoFactory().createSlotBao(user);
    schedulebao = new BaoFactory().createScheduleBao(user);

    initComponents();
    defaultdata();
    setTableModel(null);
    defaultRender();
    viewonly();
  }


  /**
   * method to set the table model by data of slots
   * */
  private void setTableModel(List<SlotDto> slots)
  {

    int size = 0;
    if(slots!=null&&!slots.isEmpty())
      size = slots.size();

    System.out.println(size);

    Object[][] slotsArr = new Object[25][9];

    // set days to table cells
    slotsArr[2][0] = "      Sunday";
    slotsArr[7][0] = "      Monday";
    slotsArr[12][0] = "     Tuesday";
    slotsArr[17][0] = "   Wednesday";
    slotsArr[22][0] = "     Thursday";

    // loop for days' rows of schedule
  

            // loop to get slot of indexed day and time slot
            for(int i = 0; i<size; i++)
              {

                    int r = -1;

                    if(slots.get(i).getDay().equalsIgnoreCase("Sunday"))
                      r = 0;
                    else if(slots.get(i).getDay().equalsIgnoreCase("Monday"))
                      r = 5;
                    else if(slots.get(i).getDay().equalsIgnoreCase("Tuesday"))
                      r = 10;
                    else if(slots.get(i).getDay().equalsIgnoreCase("Wednesday"))
                      r = 15;
                    else if(slots.get(i).getDay().equalsIgnoreCase("Thursday"))
                      r = 20;
                    
                    int c = slots.get(i).getNum()*2-1;
                    
                    
                    // set course name and code od slot
                    slotsArr[r][c] =
                      slots.get(i).getCourse().getName();
                    slotsArr[r][c+1] =
                      slots.get(i).getCourse().getCode();

                    // set type of slot
                    slotsArr[r+2][c] = slots.get(i).getSlot_type();

                    // set location of slot
                    if(slots.get(i).getLocation()!=null)
                      slotsArr[r+3][c] =
                        slots.get(i).getLocation().getName();
                    else
                      slotsArr[r+3][c] = "Not Assigned";

                    // check slot type then set plt of slot
                    if(slots.get(i).getSlot_type().equals("LECTURE"))
                      {
                        slotsArr[r+3][c+1] =
                          slots.get(i).getCourse().getPlt_lecture().getCode();
                        System.out.println("plt"+
                          slots.get(i).getCourse().getPlt_lecture().getCode());
                      }
                    if(slots.get(i).getSlot_type().equals("SECTION"))
                      {
                        slotsArr[r+3][c+1] =
                          slots.get(i).getCourse().getPlt_section().getCode();
                        System.out.println("plt"+
                          slots.get(i).getCourse().getPlt_section().getCode());
                      }

                    // set student number of slot
                    slotsArr[r+4][c] = "Student number:";
                    slotsArr[r+4][c+1] =
                      slots.get(i).getStudent_number();

                    /*
             * set staff of slot then check if members > 1 then concatenate all members' names
             * and set to cell */
                    
                    /*
                    * set user email of slot then check if users > 1 then concatenate all users' email
                    * and set to cell */
                    
                    String staff =
                      slots.get(i).getStaff().get(0).getPosition()+"/"+
                      slots.get(i).getStaff().get(0).getName();

                    System.out.println("staff size "+
                      slots.get(i).getStaff().size());
                    
                    
                    String[] email =
                           slots.get(i).getStaff().get(0).getUser().getEmail().split("@",
                             2);
                   String user = email[0];

                    if(slots.get(i).getStaff().size()>1)
                      {
                        for(int j = 1; j<slots.get(i).getStaff().size();
                          j++)
                          {
                             staff =
                              staff+" # "+
                              slots.get(i).getStaff().get(j).getPosition()+
                              "/"+slots.get(i).getStaff().get(j).getName();
                            
                             String[] _email =
                                 slots.get(i).getStaff().get(j).getUser().getEmail().split("@",
                                   2);                            
                             user = user+" # "+_email[0];

                           }
                      }
                    
                    slotsArr[r+1][c] = staff;
                    slotsArr[r+1][c+1] = user;

        
                  
              }
          
      

    scheduleTable.setModel(new javax.swing.table.DefaultTableModel(slotsArr,
        new String[] { "       Time Slot", "                1st slot", " ",
        "                2nd slot", " ", "                 3rd slot", " ",
        "                 4th slot", " " }));


    //change header color
    scheduleTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
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

  }

  private void viewonly() 
  {
    user.setScreen_name("Schedule");

    RoleScreenDao dao = new DaoFactory().createRoleScreenDao(user);
    if(dao.viewonly(user))
      {
        optionPanel.setVisible(false);
        uploadPanel.setVisible(false);
      }
    
  }
  private void defaultdata()
  {
    //set all existing Departments in db to department combobox
    try
      {

        StageBao stage_bao =
          new BaoFactory().createStageBao(user); //create building bao object
        List<StageDto> stage_list =
          stage_bao.viewAll(); //get all building from DB

        stageComboBox.removeAllItems(); //remove all item from building combobox

        if(stage_list!=null&&!stage_list.isEmpty())
          {
            int flage=0;
            stageComboBox.addItem(stage_list.get(0).getNumber());
            for(int i = 1; i<stage_list.size(); i++)
              {
                for(int j = 0; j<stageComboBox.getItemCount(); j++)

                  {
                    if(stage_list.get(i).getNumber().equals(stageComboBox.getItemAt(j)))
                       flage=-1;

                  }
              if(flage!=-1)
                stageComboBox.addItem(stage_list.get(i).getNumber());
               
                flage=0;
              }

            stageComboBox.setSelectedIndex(-1); //select nothing in this combo
          }


        DepartmentBao depart_bao =
          new BaoFactory().createDepartmentBao(user);
        List<DepartmentDto> depart_list = depart_bao.viewAll();
        DepartComboBox.removeAllItems();

        if(depart_list!=null&&!depart_list.isEmpty())
          {
            for(int i = 0; i<depart_list.size(); i++)
              {
                DepartComboBox.addItem(depart_list.get(i).getName());
              }
            DepartComboBox.setSelectedIndex(-1);
          }

        //unvisiable table options
        ReassignButton.setVisible(false);
        DeleteButton.setVisible(false);
        DownLoadButton.setVisible(false);

      }

    catch(Exception e)
      {
        e.printStackTrace();
      }

  }


  /**set default table cell renderer*/
  private void defaultRender()
  {
    //set slot borders
    for(int col = 0; col<9; col++)
      {
        scheduleTable.getColumnModel().getColumn(col).setCellRenderer(new TableCellRenderer()
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

            if(column==1||column==3||column==5||column==7)
              comp.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0,
                Color.lightGray));


            if(row==4||row==9||row==14||row==19)
              comp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));

            
            if((row==4&&column==1)||(row==4&&column==3)||
              (row==4&&column==5)||(row==4&&column==7))
              comp.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0,
                Color.lightGray));

            if((row==9&&column==1)||(row==9&&column==3)||
              (row==9&&column==5)||(row==9&&column==7))
              comp.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0,
                Color.lightGray));

            if((row==14&&column==1)||(row==14&&column==3)||
              (row==14&&column==5)||(row==14&&column==7))
              comp.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0,
                Color.lightGray));

            if((row==19&&column==1)||(row==19&&column==3)||
              (row==19&&column==5)||(row==19&&column==7))
              comp.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0,
                Color.lightGray)); 


            return comp;
          }
        });
      }


    //Change color,font,order of day column
    scheduleTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer()
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


  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  private void initComponents()//GEN-BEGIN:initComponents
  {

    DepartComboBox = new javax.swing.JComboBox<>();
    stageComboBox = new javax.swing.JComboBox<>();
    jScrollPane1 = new javax.swing.JScrollPane();
    scheduleTable = new javax.swing.JTable();
    IdLabel = new javax.swing.JLabel();
    IdLabel1 = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    CodeLabel = new javax.swing.JLabel();
    viewScheduleButton = new javax.swing.JButton();
    optionPanel = new javax.swing.JPanel();
    DeleteButton = new javax.swing.JButton();
    ReassignButton = new javax.swing.JButton();
    DownLoadButton = new javax.swing.JButton();
    uploadPanel = new javax.swing.JPanel();
    UploadFileButton = new javax.swing.JButton();
    filePathTextField = new javax.swing.JTextField();
    SaveFileButton = new javax.swing.JButton();

    setBackground(new java.awt.Color(245, 245, 245));
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    DepartComboBox.setBackground(new java.awt.Color(255, 255, 255));
    DepartComboBox.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    DepartComboBox.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        DepartComboBoxActionPerformed(evt);
      }
    });
    add(DepartComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 240, 50));

    stageComboBox.setBackground(new java.awt.Color(255, 255, 255));
    stageComboBox.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    stageComboBox.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        stageComboBoxActionPerformed(evt);
      }
    });
    add(stageComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, 270, 50));

    scheduleTable.setBackground(new java.awt.Color(253, 253, 253));
    scheduleTable.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    scheduleTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null}
      },
      new String []
      {
        "Time Slot", "First", "", "Second", "", "Third", "", "Fourth", ""
      }
    )
    {
      Class[] types = new Class []
      {
        java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
      };
      boolean[] canEdit = new boolean []
      {
        false, false, false, false, false, false, true, true, true
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
    scheduleTable.setCellSelectionEnabled(true);
    scheduleTable.setFillsViewportHeight(true);
    scheduleTable.setGridColor(new java.awt.Color(102, 102, 102));
    scheduleTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
    scheduleTable.setRowHeight(24);
    scheduleTable.setSelectionBackground(new java.awt.Color(0, 153, 204));
    scheduleTable.setShowHorizontalLines(false);
    scheduleTable.setShowVerticalLines(false);
    scheduleTable.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mousePressed(java.awt.event.MouseEvent evt)
      {
        scheduleTableMousePressed(evt);
      }
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        scheduleTableMouseClicked(evt);
      }
    });
    jScrollPane1.setViewportView(scheduleTable);
    scheduleTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    if (scheduleTable.getColumnModel().getColumnCount() > 0)
    {
      scheduleTable.getColumnModel().getColumn(0).setHeaderValue("Time Slot");
      scheduleTable.getColumnModel().getColumn(1).setHeaderValue("First");
      scheduleTable.getColumnModel().getColumn(2).setHeaderValue("");
      scheduleTable.getColumnModel().getColumn(3).setHeaderValue("Second");
      scheduleTable.getColumnModel().getColumn(4).setHeaderValue("");
      scheduleTable.getColumnModel().getColumn(5).setHeaderValue("Third");
      scheduleTable.getColumnModel().getColumn(6).setHeaderValue("");
      scheduleTable.getColumnModel().getColumn(7).setHeaderValue("Fourth");
      scheduleTable.getColumnModel().getColumn(8).setHeaderValue("");
    }

    add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 1410, 640));

    IdLabel.setBackground(new java.awt.Color(0, 51, 204));
    IdLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    IdLabel.setForeground(new java.awt.Color(0, 51, 204));
    IdLabel.setText("Stage");
    add(IdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 110, 80, 50));

    IdLabel1.setBackground(new java.awt.Color(0, 51, 204));
    IdLabel1.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    IdLabel1.setForeground(new java.awt.Color(0, 51, 204));
    IdLabel1.setText("Department");
    add(IdLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 130, 50));

    jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_calendar_64px.png"))); // NOI18N
    add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 70, 80));

    CodeLabel.setBackground(new java.awt.Color(0, 51, 204));
    CodeLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 30)); // NOI18N
    CodeLabel.setForeground(new java.awt.Color(231, 78, 123));
    CodeLabel.setText("Schedule manager");
    add(CodeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 280, 50));

    viewScheduleButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    viewScheduleButton.setForeground(new java.awt.Color(255, 255, 255));
    viewScheduleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_view_file_48px.png"))); // NOI18N
    viewScheduleButton.setToolTipText("View Schedule");
    viewScheduleButton.setBorderPainted(false);
    viewScheduleButton.setContentAreaFilled(false);
    viewScheduleButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        viewScheduleButtonMouseMoved(evt);
      }
    });
    viewScheduleButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        viewScheduleButtonMouseExited(evt);
      }
    });
    viewScheduleButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        viewScheduleButtonActionPerformed(evt);
      }
    });
    add(viewScheduleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 100, 80, 70));

    optionPanel.setBackground(new java.awt.Color(245, 245, 245));
    optionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    DeleteButton.setBackground(new java.awt.Color(255, 255, 255));
    DeleteButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    DeleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_delete_file_48px_1.png"))); // NOI18N
    DeleteButton.setText("Delete");
    DeleteButton.setToolTipText("Delete this schedule");
    DeleteButton.setBorderPainted(false);
    DeleteButton.setContentAreaFilled(false);
    DeleteButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    DeleteButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        DeleteButtonMouseMoved(evt);
      }
    });
    DeleteButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mousePressed(java.awt.event.MouseEvent evt)
      {
        DeleteButtonMousePressed(evt);
      }
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
    optionPanel.add(DeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, -1, 60));

    ReassignButton.setBackground(new java.awt.Color(255, 255, 255));
    ReassignButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    ReassignButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_update_file_45px.png"))); // NOI18N
    ReassignButton.setText("Reassign Locations");
    ReassignButton.setToolTipText("");
    ReassignButton.setBorderPainted(false);
    ReassignButton.setContentAreaFilled(false);
    ReassignButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    ReassignButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        ReassignButtonMouseMoved(evt);
      }
    });
    ReassignButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mousePressed(java.awt.event.MouseEvent evt)
      {
        ReassignButtonMousePressed(evt);
      }
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        ReassignButtonMouseExited(evt);
      }
    });
    ReassignButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        ReassignButtonActionPerformed(evt);
      }
    });
    optionPanel.add(ReassignButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 240, 60));

    DownLoadButton.setBackground(new java.awt.Color(255, 255, 255));
    DownLoadButton.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    DownLoadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_xls_55px.png"))); // NOI18N
    DownLoadButton.setText("Export");
    DownLoadButton.setToolTipText("Export this schedule in excel file");
    DownLoadButton.setBorderPainted(false);
    DownLoadButton.setContentAreaFilled(false);
    DownLoadButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    DownLoadButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        DownLoadButtonMouseMoved(evt);
      }
    });
    DownLoadButton.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mousePressed(java.awt.event.MouseEvent evt)
      {
        DownLoadButtonMousePressed(evt);
      }
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        DownLoadButtonMouseExited(evt);
      }
    });
    DownLoadButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        DownLoadButtonActionPerformed(evt);
      }
    });
    optionPanel.add(DownLoadButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 150, 60));

    add(optionPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 820, 640, 80));

    uploadPanel.setBackground(new java.awt.Color(245, 245, 245));
    uploadPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    UploadFileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_downloads_folder_48px.png"))); // NOI18N
    UploadFileButton.setToolTipText("Upload new schedule");
    UploadFileButton.setBorderPainted(false);
    UploadFileButton.setContentAreaFilled(false);
    UploadFileButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        UploadFileButtonActionPerformed(evt);
      }
    });
    uploadPanel.add(UploadFileButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, -1, 60));

    filePathTextField.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    filePathTextField.setText("Choose file");
    filePathTextField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    filePathTextField.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        filePathTextFieldFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        filePathTextFieldFocusLost(evt);
      }
    });
    filePathTextField.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        filePathTextFieldActionPerformed(evt);
      }
    });
    uploadPanel.add(filePathTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 430, 50));

    SaveFileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_save_all_45px.png"))); // NOI18N
    SaveFileButton.setToolTipText("Save schedule in database,\nApply Location Automatic Assignment to it.");
    SaveFileButton.setBorderPainted(false);
    SaveFileButton.setContentAreaFilled(false);
    SaveFileButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        SaveFileButtonActionPerformed(evt);
      }
    });
    uploadPanel.add(SaveFileButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 60, 50));

    add(uploadPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 100, 560, 70));
  }//GEN-END:initComponents

  private void UploadFileButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_UploadFileButtonActionPerformed
  {//GEN-HEADEREND:event_UploadFileButtonActionPerformed
    // TODO add your handling code here:
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
            FileReader reader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(reader);
            filePathTextField.setText(filePath);
            br.close();
            filePathTextField.requestFocus();
          }
        catch(Exception e)
          {
            JOptionPane.showMessageDialog(null, e);
          }

      }
    
  }//GEN-LAST:event_UploadFileButtonActionPerformed

  private void DepartComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_DepartComboBoxActionPerformed
  {//GEN-HEADEREND:event_DepartComboBoxActionPerformed
    // TODO add your handling code here:

  }//GEN-LAST:event_DepartComboBoxActionPerformed

  private void stageComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stageComboBoxActionPerformed
  {//GEN-HEADEREND:event_stageComboBoxActionPerformed
    // TODO add your handling code here:

  }//GEN-LAST:event_stageComboBoxActionPerformed

  private void scheduleTableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_scheduleTableMouseClicked
  {//GEN-HEADEREND:event_scheduleTableMouseClicked
    // TODO add your handling code here:
    try
      {
        int i = scheduleTable.getSelectedColumn(); //selected column

        if(i==0)
          scheduleTable.clearSelection();

        //slect slot in day 1
        if(scheduleTable.getSelectedRow()>=0&&
          scheduleTable.getSelectedRow()<=4)
          {
            scheduleTable.addRowSelectionInterval(0, 4);

            defaultRender();

            if(i%2==0)
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=0&&row<=4)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));


                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i-
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=0&&row<=4)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));


                      return comp;
                    }
                  });
              }
            else
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=0&&row<=4)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i+
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=0&&row<=4)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      return comp;
                    }
                  });

              }
          }

        //slect slot in day 2
        if(scheduleTable.getSelectedRow()>=5&&
          scheduleTable.getSelectedRow()<=9)
          {
            scheduleTable.addRowSelectionInterval(5, 9);

            defaultRender();

            if(i%2==0)
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=5&&row<=9)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i-
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=5&&row<=9)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      return comp;
                    }
                  });
              }
            else
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=5&&row<=9)
                      comp.setBackground(Color.decode("#0099CC"));
                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));


                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i+
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=5&&row<=9)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      return comp;
                    }
                  });

              }

          }

        //slect slot in day 3
        if(scheduleTable.getSelectedRow()>=10&&
          scheduleTable.getSelectedRow()<=14)
          {
            scheduleTable.addRowSelectionInterval(10, 14);

            defaultRender();

            if(i%2==0)
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=10&&row<=14)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i-
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=10&&row<=14)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));


                      return comp;
                    }
                  });
              }
            else
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=10&&row<=14)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));


                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i+
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=10&&row<=14)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));


                      return comp;
                    }
                  });

              }
          }

        //slect slot in day 4
        if(scheduleTable.getSelectedRow()>=15&&
          scheduleTable.getSelectedRow()<=19)
          {
            scheduleTable.addRowSelectionInterval(15, 19);

            defaultRender();

            if(i%2==0)
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=15&&row<=19)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i-
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=15&&row<=19)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));


                      return comp;
                    }
                  });
              }
            else
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=15&&row<=19)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));


                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i+
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=15&&row<=19)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));


                      return comp;
                    }
                  });

              }
          }

        //slect slot in day 5
        if(scheduleTable.getSelectedRow()>=20&&
          scheduleTable.getSelectedRow()<=24)
          {
            scheduleTable.addRowSelectionInterval(20, 24);

            defaultRender();

            if(i%2==0)
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=20&&row<=24)
                      comp.setBackground(Color.decode("#0099CC"));


                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i-
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=20&&row<=24)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      return comp;
                    }
                  });
              }
            else
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=20&&row<=24)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));


                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i+
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=20&&row<=24)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));


                      return comp;
                    }
                  });

              }
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }//GEN-LAST:event_scheduleTableMouseClicked

  private void scheduleTableMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_scheduleTableMousePressed
  {//GEN-HEADEREND:event_scheduleTableMousePressed
    // TODO add your handling code here:
    try
      {
        int i = scheduleTable.getSelectedColumn(); //selected column

        if(i==0)
          scheduleTable.clearSelection();

        //slect slot in day 1
        if(scheduleTable.getSelectedRow()>=0&&
          scheduleTable.getSelectedRow()<=4)
          {
            scheduleTable.addRowSelectionInterval(0, 4);

            defaultRender();

            if(i%2==0)
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=0&&row<=4)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));


                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i-
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=0&&row<=4)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));


                      return comp;
                    }
                  });
              }
            else
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=0&&row<=4)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i+
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=0&&row<=4)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      return comp;
                    }
                  });

              }
          }

        //slect slot in day 2
        if(scheduleTable.getSelectedRow()>=5&&
          scheduleTable.getSelectedRow()<=9)
          {
            scheduleTable.addRowSelectionInterval(5, 9);

            defaultRender();

            if(i%2==0)
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=5&&row<=9)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i-
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=5&&row<=9)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      return comp;
                    }
                  });
              }
            else
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=5&&row<=9)
                      comp.setBackground(Color.decode("#0099CC"));
                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));


                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i+
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=5&&row<=9)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      return comp;
                    }
                  });

              }

          }

        //slect slot in day 3
        if(scheduleTable.getSelectedRow()>=10&&
          scheduleTable.getSelectedRow()<=14)
          {
            scheduleTable.addRowSelectionInterval(10, 14);

            defaultRender();

            if(i%2==0)
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=10&&row<=14)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i-
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=10&&row<=14)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));


                      return comp;
                    }
                  });
              }
            else
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=10&&row<=14)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));


                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i+
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=10&&row<=14)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));


                      return comp;
                    }
                  });

              }
          }

        //slect slot in day 4
        if(scheduleTable.getSelectedRow()>=15&&
          scheduleTable.getSelectedRow()<=19)
          {
            scheduleTable.addRowSelectionInterval(15, 19);

            defaultRender();

            if(i%2==0)
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=15&&row<=19)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i-
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=15&&row<=19)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));


                      return comp;
                    }
                  });
              }
            else
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=15&&row<=19)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));


                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i+
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=15&&row<=19)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));


                      return comp;
                    }
                  });

              }
          }

        //slect slot in day 5
        if(scheduleTable.getSelectedRow()>=20&&
          scheduleTable.getSelectedRow()<=24)
          {
            scheduleTable.addRowSelectionInterval(20, 24);

            defaultRender();

            if(i%2==0)
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=20&&row<=24)
                      comp.setBackground(Color.decode("#0099CC"));


                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i-
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=20&&row<=24)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      return comp;
                    }
                  });
              }
            else
              {
                scheduleTable.getColumnModel().getColumn(i).setCellRenderer(new TableCellRenderer()
                {
                  @Override
                  public Component getTableCellRendererComponent(JTable jTable,
                    Object object, boolean b, boolean b2, int row,
                    int column)
                  {

                    DefaultTableCellRenderer renderer =
                      new DefaultTableCellRenderer();

                    DefaultTableCellRenderer comp =
                      (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                      object, b, b2, row, column);

                    if(row>=20&&row<=24)
                      comp.setBackground(Color.decode("#0099CC"));

                    if(column==1||column==3||column==5||column==7)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        0, 0, Color.lightGray));


                    if(row==4||row==9||row==14||row==19)
                      comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                        1, 0, Color.lightGray));

                    if((row==4&&column==1)||(row==4&&column==3)||
                      (row==4&&column==5)||(row==4&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==9&&column==1)||(row==9&&column==3)||
                      (row==9&&column==5)||(row==9&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==14&&column==1)||(row==14&&column==3)||
                      (row==14&&column==5)||(row==14&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));

                    if((row==19&&column==1)||(row==19&&column==3)||
                      (row==19&&column==5)||(row==19&&column==7))
                      comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                        1, 0, Color.lightGray));


                    return comp;
                  }
                });

                scheduleTable.getColumnModel().getColumn(i+
                  1).setCellRenderer(new TableCellRenderer()
                  {
                    @Override
                    public Component getTableCellRendererComponent(JTable jTable,
                      Object object, boolean b, boolean b2, int row,
                      int column)
                    {

                      DefaultTableCellRenderer renderer =
                        new DefaultTableCellRenderer();

                      DefaultTableCellRenderer comp =
                        (DefaultTableCellRenderer) renderer.getTableCellRendererComponent(jTable,
                        object, b, b2, row, column);

                      if(row>=20&&row<=24)
                        comp.setBackground(Color.decode("#0099CC"));

                      if(column==1||column==3||column==5||column==7)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          0, 0, Color.lightGray));


                      if(row==4||row==9||row==14||row==19)
                        comp.setBorder(BorderFactory.createMatteBorder(0, 0,
                          1, 0, Color.lightGray));

                      if((row==4&&column==1)||(row==4&&column==3)||
                        (row==4&&column==5)||(row==4&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==9&&column==1)||(row==9&&column==3)||
                        (row==9&&column==5)||(row==9&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==14&&column==1)||(row==14&&column==3)||
                        (row==14&&column==5)||(row==14&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));

                      if((row==19&&column==1)||(row==19&&column==3)||
                        (row==19&&column==5)||(row==19&&column==7))
                        comp.setBorder(BorderFactory.createMatteBorder(0, 1,
                          1, 0, Color.lightGray));


                      return comp;
                    }
                  });

              }
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }//GEN-LAST:event_scheduleTableMousePressed

  private void viewScheduleButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_viewScheduleButtonMouseMoved
  {//GEN-HEADEREND:event_viewScheduleButtonMouseMoved
    // TODO add your handling code here:
    //RefreshPanel.setBackground(Color.decode("#0051D2"));
  }//GEN-LAST:event_viewScheduleButtonMouseMoved

  private void viewScheduleButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_viewScheduleButtonMouseExited
  {//GEN-HEADEREND:event_viewScheduleButtonMouseExited
    // TODO add your handling code here:
    //RefreshPanel.setBackground(Color.decode("#0081D3"));
  }//GEN-LAST:event_viewScheduleButtonMouseExited

  private void viewScheduleButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_viewScheduleButtonActionPerformed
  {//GEN-HEADEREND:event_viewScheduleButtonActionPerformed
    // TODO add your handling code here:
    try
      {

        // identify department dto object
        DepartmentDto depart = new DepartmentDto();

        // set department name by the selected from comboBox by user
        depart.setName(DepartComboBox.getSelectedItem().toString());

        // identify stage dto object
        StageDto stage = new StageDto();

        // set stage number by the selected from combo box by user
        stage.setNumber(stageComboBox.getSelectedItem().toString());

        /* identify list of slots dto then set to it slots of schedule
      * by calling viewSlotsOfSchedule bao method */
        List<SlotDto> slots = new ArrayList<>();
        slots = slotbao.viewSlotsOfSchedule(stage, depart);

        // set table by slots data then repaint
        setTableModel(slots);
        defaultRender();
        scheduleTable.repaint();

        //if there is some thing to show, appear schedule options
        if(slots!=null)
          {
            ReassignButton.setVisible(true);
            DeleteButton.setVisible(true);
            DownLoadButton.setVisible(true);

          }

        else
          {

            //unvisible them
            ReassignButton.setVisible(false);
            DeleteButton.setVisible(false);
            DownLoadButton.setVisible(false);


            JOptionPane.showMessageDialog(null,
              "No schedule assigned to this stage yet.", "Not Found", 1);
          }

      }
    catch(Exception e)
      {
        e.printStackTrace();
        System.out.println("error in viewSchedules panel");
      }

  }//GEN-LAST:event_viewScheduleButtonActionPerformed

  private void filePathTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_filePathTextFieldActionPerformed
  {//GEN-HEADEREND:event_filePathTextFieldActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_filePathTextFieldActionPerformed

  private void filePathTextFieldFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_filePathTextFieldFocusGained
  {//GEN-HEADEREND:event_filePathTextFieldFocusGained
    // TODO add your handling code here:

    if(filePathTextField.getText().equalsIgnoreCase("Choose file"))
      {
        filePathTextField.setText("");
      }

    filePathTextField.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

  }//GEN-LAST:event_filePathTextFieldFocusGained

  private void filePathTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_filePathTextFieldFocusLost
  {//GEN-HEADEREND:event_filePathTextFieldFocusLost
    // TODO add your handling code here:

    if(filePathTextField.getText().trim().equalsIgnoreCase(""))
      {
        filePathTextField.setText("Choose file");
      }

    filePathTextField.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));
  }//GEN-LAST:event_filePathTextFieldFocusLost

  private void SaveFileButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SaveFileButtonActionPerformed
  {//GEN-HEADEREND:event_SaveFileButtonActionPerformed
    // TODO add your handling code here:

    try
      {
        ScheduleBao sch_bao = new BaoFactory().createScheduleBao(user);
        SlotBao slot_bao = new BaoFactory().createSlotBao(user);
        ScheduleDto schedule = null;

        //read schedule from excel file
        ReadScheduleExcel read = new ReadScheduleExcel(user);
       
        //if path text field is empty
        if(filePathTextField.getText().equalsIgnoreCase("Choose file"))
          JOptionPane.showMessageDialog(null, "Please, Choose file");

        File file = new File(filePathTextField.getText());
        String filePath = file.getAbsolutePath();
      
        //if user choose file
        if(read.checkSchedule(filePath))
          {

            if(read.getScheduleData(filePath, user))
              {

                //read all slots in schedule
                read.getSlots(filePath, user);

                //get stage student number
                read.setStageStudentNumber(user);
                read.setStageDefaultSpace(user);

                //get schedule id
                schedule = new ScheduleDto();
                schedule.setFile_path(filePath);
                schedule.setId(sch_bao.getScheduleID(filePath));
                System.out.println(filePath);

                defaultdata(); //refresh combo


                //apply location automatic assignment
                List<SlotDto> Failed_slots =
                  sch_bao.autoAssignment(schedule, user);

                int slot_no = slot_bao.ScheduleSlot(schedule).size();

                //assignment success
                if(Failed_slots==null)
                  JOptionPane.showMessageDialog(null,
                    "Schedule saved successfully.\n\n\n"+
                    "And, locations Automatic Assignment finished successfully. ",
                    "Done", JOptionPane.INFORMATION_MESSAGE);

                //Failed
                else if(Failed_slots.size()==slot_no)
                  JOptionPane.showMessageDialog(null,
                    "Schedule saved successfully.\n\n\n"+
                    "And, locations Automatic Assignment Failed! ", "Failed",
                    JOptionPane.ERROR_MESSAGE);

                //finish with warning
                else
                  JOptionPane.showMessageDialog(null,
                    "Schedule saved successfully.\n\n\n"+
                    "And, locations Automatic Assignment finish with warning!\n"+
                    "Some slots can't be assigned", "Done with warning",
                    JOptionPane.WARNING_MESSAGE);


                // set table by slots data then repaint
                setTableModel(slot_bao.viewSlotsOfSchedule(schedule));
                defaultRender();
                defaultdata();
                scheduleTable.repaint();
               

                /*   //set table options buttons true
                ReassignButton.setVisible(true);
                DeleteButton.setVisible(true);
                DownLoadButton.setVisible(true); */


                try
                  {

                    UpdateScheduleExcel update =
                      new UpdateScheduleExcel(filePathTextField.getText(),
                        user);

                    update.updateSheet(filePathTextField.getText());

                    System.out.println("done update");
                  }
                catch(Exception e)
                  {
                    e.printStackTrace();
                    System.out.println("error update excel");
                  }

              }
            else
              {
                JOptionPane.showMessageDialog(null,
                  "This schedule Already Exist!");
              }
          }

      }
    catch(Exception e)
      {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,
          "Error reading file, Please check your file and try again!",
          "Error", JOptionPane.ERROR_MESSAGE);
      }
    
  }//GEN-LAST:event_SaveFileButtonActionPerformed

  private void ReassignButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ReassignButtonMouseMoved
  {//GEN-HEADEREND:event_ReassignButtonMouseMoved
    // TODO add your handling code here:
    if(ReassignButton.isEnabled())
      ReassignButton.setForeground(Color.blue);
  }//GEN-LAST:event_ReassignButtonMouseMoved

  private void ReassignButtonMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ReassignButtonMousePressed
  {//GEN-HEADEREND:event_ReassignButtonMousePressed
    // TODO add your handling code here:
    if(ReassignButton.isEnabled())
      ReassignButton.setForeground(Color.red);
  }//GEN-LAST:event_ReassignButtonMousePressed

  private void ReassignButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ReassignButtonMouseExited
  {//GEN-HEADEREND:event_ReassignButtonMouseExited
    // TODO add your handling code here:
    if(ReassignButton.isEnabled())
      ReassignButton.setForeground(Color.black);
  }//GEN-LAST:event_ReassignButtonMouseExited

  private void ReassignButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ReassignButtonActionPerformed
  {//GEN-HEADEREND:event_ReassignButtonActionPerformed
    // TODO add your handling code here:
    try
      {

        SlotBao slot_bao = new BaoFactory().createSlotBao(user);

        if(stageComboBox.getSelectedIndex()==-1)
          JOptionPane.showMessageDialog(null, "Please, select stage",
            "Error", JOptionPane.ERROR_MESSAGE);
        else
          {

            if(DepartComboBox.getSelectedIndex()==-1)
              JOptionPane.showMessageDialog(null,
                "Please, select department", "Error",
                JOptionPane.ERROR_MESSAGE);

            else
              {

                ScheduleDto schedule = new ScheduleDto();
                schedule.setDepartment(new DepartmentDto());
                schedule.getDepartment().setName(DepartComboBox.getSelectedItem().toString());
                schedule.setStage(new StageDto());
                schedule.getStage().setNumber(stageComboBox.getSelectedItem().toString());

                ScheduleBao sch_bao =
                  new BaoFactory().createScheduleBao(user);
                schedule = sch_bao.getID(schedule); //get schedule ID
                System.out.println("sch id"+schedule.getId());

                //automatic reassign location
                List<SlotDto> Failed_slots =
                  sch_bao.autoAssignment(schedule, user);

                int slot_no = slot_bao.ScheduleSlot(schedule).size();

                //assignment success
                if(Failed_slots==null)
                  JOptionPane.showMessageDialog(null,
                    "locations Automatic reassignment finished successfully. ",
                    "Done", JOptionPane.INFORMATION_MESSAGE);
                //Failed
                else if(Failed_slots.size()==slot_no)
                  JOptionPane.showMessageDialog(null,
                    "locations Automatic reassignment Failed! ", "Failed",
                    JOptionPane.ERROR_MESSAGE);

                //finish with warning
                else
                  JOptionPane.showMessageDialog(null,
                    "locations Automatic reassignment finish with warning!\n"+
                    "Some slots can't be assigned", "Done with warning",
                    JOptionPane.WARNING_MESSAGE);


                // identify department dto object
                DepartmentDto depart = new DepartmentDto();

                // set department name by the selected from comboBox by user
                depart.setName(DepartComboBox.getSelectedItem().toString());

                // identify stage dto object
                StageDto stage = new StageDto();

                // set stage number by the selected from combo box by user
                stage.setNumber(stageComboBox.getSelectedItem().toString());

                /* identify list of slots dto then set to it slots of schedule
                    * by calling viewSlotsOfSchedule bao method */
                List<SlotDto> slots = new ArrayList<>();
                slots = slotbao.viewSlotsOfSchedule(stage, depart);

                // set table by slots data then repaint
                setTableModel(slots);
                defaultRender();
                scheduleTable.repaint();

              }


          }

      }
    catch(Exception e)
      {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Some this went wrong!",
          "Error", JOptionPane.ERROR_MESSAGE);
      }
  }//GEN-LAST:event_ReassignButtonActionPerformed

  private void DeleteButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteButtonMouseMoved
  {//GEN-HEADEREND:event_DeleteButtonMouseMoved
    // TODO add your handling code here:
    if(DeleteButton.isEnabled())
      DeleteButton.setForeground(Color.blue);
  }//GEN-LAST:event_DeleteButtonMouseMoved

  private void DeleteButtonMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteButtonMousePressed
  {//GEN-HEADEREND:event_DeleteButtonMousePressed
    // TODO add your handling code here:
    if(DeleteButton.isEnabled())
      DeleteButton.setForeground(Color.red);
  }//GEN-LAST:event_DeleteButtonMousePressed

  private void DeleteButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteButtonMouseExited
  {//GEN-HEADEREND:event_DeleteButtonMouseExited
    // TODO add your handling code here:
    if(DeleteButton.isEnabled())
      DeleteButton.setForeground(Color.black);
  }//GEN-LAST:event_DeleteButtonMouseExited

  private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_DeleteButtonActionPerformed
  {//GEN-HEADEREND:event_DeleteButtonActionPerformed
    // TODO add your handling code here:
    try
      {

        // identify department dto object
        DepartmentDto depart = new DepartmentDto();

        // set department name by the selected from comboBox by user
        depart.setName(DepartComboBox.getSelectedItem().toString());

        // identify stage dto object
        StageDto stage = new StageDto();

        // set stage number by the selected from combo box by user
        stage.setNumber(stageComboBox.getSelectedItem().toString());

        /* try delete this schedule using department and stage */
        if(schedulebao.delete(stage, depart))

          {
            // set table empty
            setTableModel(null);
            defaultRender();
            scheduleTable.repaint();

            //unvisible them schedule option
            ReassignButton.setVisible(false);
            DeleteButton.setVisible(false);
            DownLoadButton.setVisible(false);


            JOptionPane.showMessageDialog(null,
              "Schedule has beenn deleted successfully.", "Done", 1);

          }
        else
          {
            JOptionPane.showMessageDialog(null, "Schedulel detetion Failed!",
              "Error", 0);
          }


      }
    catch(Exception e)
      {
        e.printStackTrace();
        System.out.println("error in viewSchedules panel");
      }
  }//GEN-LAST:event_DeleteButtonActionPerformed

  private void DownLoadButtonMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DownLoadButtonMouseMoved
  {//GEN-HEADEREND:event_DownLoadButtonMouseMoved
    // TODO add your handling code here:
    if(DownLoadButton.isEnabled())
      DownLoadButton.setForeground(Color.blue);
  }//GEN-LAST:event_DownLoadButtonMouseMoved

  private void DownLoadButtonMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DownLoadButtonMousePressed
  {//GEN-HEADEREND:event_DownLoadButtonMousePressed
    // TODO add your handling code here:
    if(DownLoadButton.isEnabled())
      DownLoadButton.setForeground(Color.red);
  }//GEN-LAST:event_DownLoadButtonMousePressed

  private void DownLoadButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DownLoadButtonMouseExited
  {//GEN-HEADEREND:event_DownLoadButtonMouseExited
    // TODO add your handling code here:
    if(DownLoadButton.isEnabled())
      DownLoadButton.setForeground(Color.black);
  }//GEN-LAST:event_DownLoadButtonMouseExited

  private void DownLoadButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_DownLoadButtonActionPerformed
  {//GEN-HEADEREND:event_DownLoadButtonActionPerformed
    // TODO add your handling code here:
    try
      {
        //get selected department
        DepartmentDto department = new DepartmentDto();
        department.setName(DepartComboBox.getSelectedItem().toString());

        //get selected stage
        StageDto stage = new StageDto();
        stage.setNumber(stageComboBox.getSelectedItem().toString());

        //create new file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        fileChooser.setAcceptAllFileFilterUsed(false);


        //set file name
        String filename =
          DepartComboBox.getSelectedItem().toString()+"-"+
            stageComboBox.getSelectedItem().toString()+".xlsx";


        //show file dialog
        int result = fileChooser.showSaveDialog(null);

        //user selected file
        if(result==JFileChooser.APPROVE_OPTION)
          {
             //get path
             String filePath = fileChooser.getSelectedFile().getAbsolutePath();
             
             System.out.println(filePath); //check
             System.out.println(filename); //check

                //write excel file
                try
                  {
                    WriteScheduleExcel write = new WriteScheduleExcel(user);
                    write.writeExcel(filename, filePath, department, stage);

                    JOptionPane.showMessageDialog(null,
                      "Schedule export ended successfully!", "Done", 1);
                  }
                catch(Exception e)
                  {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                      "Failed to write file!", "Error", 0);
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
  }//GEN-LAST:event_DownLoadButtonActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel CodeLabel;
  public javax.swing.JButton DeleteButton;
  private javax.swing.JComboBox<String> DepartComboBox;
  public javax.swing.JButton DownLoadButton;
  private javax.swing.JLabel IdLabel;
  private javax.swing.JLabel IdLabel1;
  public javax.swing.JButton ReassignButton;
  private javax.swing.JButton SaveFileButton;
  private javax.swing.JButton UploadFileButton;
  private javax.swing.JTextField filePathTextField;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JPanel optionPanel;
  private javax.swing.JTable scheduleTable;
  private javax.swing.JComboBox<String> stageComboBox;
  private javax.swing.JPanel uploadPanel;
  private javax.swing.JButton viewScheduleButton;
  // End of variables declaration//GEN-END:variables

}
