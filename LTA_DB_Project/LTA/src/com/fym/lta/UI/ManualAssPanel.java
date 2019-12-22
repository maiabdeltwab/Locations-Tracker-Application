
package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.BuildingBao;
import com.fym.lta.BAO.DepartmentBao;
import com.fym.lta.BAO.LocationBao;
import com.fym.lta.BAO.LocationTypeBao;
import com.fym.lta.BAO.SlotBao;
import com.fym.lta.BAO.StageBao;
import com.fym.lta.BAO.StageScheduleBao;
import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.ScheduleDao;
import com.fym.lta.DTO.BuildingDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.LocationDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.ScheduleDto;
import com.fym.lta.DTO.SlotDto;
import com.fym.lta.DTO.StageDto;
import com.fym.lta.DTO.UserDto;

import java.awt.Color;

import java.util.List;

import javax.swing.JOptionPane;

/**
 *
 * @author Islam
 */
public class ManualAssPanel extends javax.swing.JPanel
{

  private static UserDto user = null;


  /** Creates new form ManualAssPanel */
  public ManualAssPanel(UserDto u)
  {

    user = u; //user has logged in
    initComponents();
    //set default data to edit space components
    defaultdata();

  }

  private void defaultdata()
  {

    Freepanel.setVisible(false);

    try
      {
        LocationTypeBao loc_Bao =
          new BaoFactory().createLocationTypeBao(user); //create location type bao object
        List<LocationTypeDto> loc_list =
          loc_Bao.viewAll(); //get all location types

        LocationType.removeAllItems(); //remove all existing data in location combobox

        //set location type codes to the location type combobox
        if(loc_list!=null&&!loc_list.isEmpty())
          {
            for(int i = 0; i<loc_list.size(); i++)
              {
                LocationType.addItem(loc_list.get(i).getCode());
              }
            LocationType.setSelectedIndex(-1); //select nothing in this combo

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

        Building.removeAllItems(); //remove all item from building combobox

        //set buildings code to building combo
        if(build_list!=null&&!build_list.isEmpty())
          {
            for(int i = 0; i<build_list.size(); i++)
              {
                Building.addItem(build_list.get(i).getCode());
              }

            Building.setSelectedIndex(-1); //select nothing in this combo
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

    //set all existing Departments in db to department combobox
    try
      {
        StageBao stage_bao =
          new BaoFactory().createStageBao(user); //create building bao object
        List<StageDto> stage_list =
          stage_bao.viewAll(); //get all building from DB
        Stage.removeAllItems(); //remove all item from building combobox

        //set buildings code to department combo
        if(stage_list!=null&&!stage_list.isEmpty())
          {

            Stage.addItem(stage_list.get(0).getNumber());

            for(int i = 1; i<stage_list.size(); i++)
              {

                for(int j = 0; j<Stage.getItemCount(); j++)
                  {
                    if(!stage_list.get(i).getNumber().equals(Stage.getItemAt(j)))
                      Stage.addItem(stage_list.get(i).getNumber());
                  }
              }

            Stage.setSelectedIndex(-1); //select nothing in this combo
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

    jLabel7 = new javax.swing.JLabel();
    CodeLabel = new javax.swing.JLabel();
    jPanel1 = new javax.swing.JPanel();
    jLabel4 = new javax.swing.JLabel();
    Slot = new javax.swing.JComboBox<>();
    jLabel5 = new javax.swing.JLabel();
    jLabel1 = new javax.swing.JLabel();
    Day = new javax.swing.JComboBox<>();
    jLabel6 = new javax.swing.JLabel();
    LocationType = new javax.swing.JComboBox<>();
    Stage = new javax.swing.JComboBox<>();
    Department = new javax.swing.JComboBox<>();
    jLabel3 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    Building = new javax.swing.JComboBox<>();
    searchPanel = new javax.swing.JPanel();
    search = new javax.swing.JButton();
    Freepanel = new javax.swing.JPanel();
    ClearPanel = new javax.swing.JPanel();
    SetLoc = new javax.swing.JButton();
    FreeSpace = new javax.swing.JComboBox<>();

    setBackground(new java.awt.Color(245, 245, 245));
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_calendar_128px.png"))); // NOI18N
    add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

    CodeLabel.setBackground(new java.awt.Color(0, 51, 204));
    CodeLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 36)); // NOI18N
    CodeLabel.setForeground(new java.awt.Color(231, 78, 123));
    CodeLabel.setText("Location Manual Assignment");
    add(CodeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 470, 50));

    jPanel1.setBackground(new java.awt.Color(245, 245, 245));
    jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel4.setFont(new java.awt.Font("VIP Rawy Regular", 0, 24)); // NOI18N
    jLabel4.setForeground(new java.awt.Color(0, 51, 204));
    jLabel4.setText("Slot");
    jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 130, 50));

    Slot.setBackground(new java.awt.Color(255, 255, 255));
    Slot.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    Slot.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        SlotActionPerformed(evt);
      }
    });
    jPanel1.add(Slot, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 590, 60));

    jLabel5.setFont(new java.awt.Font("VIP Rawy Regular", 0, 24)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(0, 51, 204));
    jLabel5.setText("Building");
    jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 460, 130, 40));

    jLabel1.setFont(new java.awt.Font("VIP Rawy Regular", 0, 24)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(0, 51, 204));
    jLabel1.setText("Stage");
    jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 130, 60));

    Day.setBackground(new java.awt.Color(255, 255, 255));
    Day.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    Day.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday" }));
    Day.setSelectedIndex(-1);
    Day.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        DayActionPerformed(evt);
      }
    });
    jPanel1.add(Day, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 590, 60));

    jLabel6.setFont(new java.awt.Font("VIP Rawy Regular", 0, 24)); // NOI18N
    jLabel6.setForeground(new java.awt.Color(0, 51, 204));
    jLabel6.setText("Location Type");
    jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 570, 180, 30));

    LocationType.setBackground(new java.awt.Color(255, 255, 255));
    LocationType.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    LocationType.setMaximumRowCount(20);
    LocationType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    LocationType.setSelectedIndex(-1);
    jPanel1.add(LocationType, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 600, 590, 60));

    Stage.setBackground(new java.awt.Color(255, 255, 255));
    Stage.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    Stage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    Stage.setSelectedIndex(-1);
    Stage.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        StageActionPerformed(evt);
      }
    });
    jPanel1.add(Stage, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 590, 60));

    Department.setBackground(new java.awt.Color(255, 255, 255));
    Department.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    Department.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        DepartmentActionPerformed(evt);
      }
    });
    jPanel1.add(Department, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 590, 60));

    jLabel3.setFont(new java.awt.Font("VIP Rawy Regular", 0, 24)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(0, 51, 204));
    jLabel3.setText("Department");
    jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 140, 60));

    jLabel2.setFont(new java.awt.Font("VIP Rawy Regular", 0, 24)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(0, 51, 204));
    jLabel2.setText("Day");
    jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 150, 60));

    Building.setBackground(new java.awt.Color(255, 255, 255));
    Building.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    Building.setMaximumRowCount(12);
    Building.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    Building.setSelectedIndex(-1);
    jPanel1.add(Building, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 500, 590, 60));

    searchPanel.setBackground(new java.awt.Color(0, 129, 211));

    search.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    search.setForeground(new java.awt.Color(255, 255, 255));
    search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_search_26px.png"))); // NOI18N
    search.setText("Search location");
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
      .addComponent(search, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
    );
    searchPanelLayout.setVerticalGroup(
      searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    jPanel1.add(searchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 680, 180, -1));

    Freepanel.setBackground(new java.awt.Color(245, 245, 245));
    Freepanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 4, true), "Free Spaces", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("VIP Rawy Regular", 0, 24), new java.awt.Color(0, 51, 204))); // NOI18N
    Freepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    ClearPanel.setBackground(new java.awt.Color(0, 129, 211));

    SetLoc.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    SetLoc.setForeground(new java.awt.Color(255, 255, 255));
    SetLoc.setText("Set Location");
    SetLoc.setBorderPainted(false);
    SetLoc.setContentAreaFilled(false);
    SetLoc.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        SetLocMouseMoved(evt);
      }
    });
    SetLoc.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        SetLocMouseExited(evt);
      }
    });
    SetLoc.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        SetLocActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout ClearPanelLayout = new javax.swing.GroupLayout(ClearPanel);
    ClearPanel.setLayout(ClearPanelLayout);
    ClearPanelLayout.setHorizontalGroup(
      ClearPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(SetLoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
    );
    ClearPanelLayout.setVerticalGroup(
      ClearPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ClearPanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(SetLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    Freepanel.add(ClearPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 230, -1, -1));

    FreeSpace.setBackground(new java.awt.Color(255, 255, 255));
    FreeSpace.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    FreeSpace.setMaximumRowCount(20);
    FreeSpace.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    FreeSpace.setSelectedIndex(-1);
    FreeSpace.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        FreeSpaceActionPerformed(evt);
      }
    });
    Freepanel.add(FreeSpace, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 380, 70));

    jPanel1.add(Freepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 160, 540, 410));

    add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 1340, 770));
  }//GEN-END:initComponents

    private void DepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DepartmentActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_DepartmentActionPerformed

    private void StageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StageActionPerformed
    // TODO add your handling code here:
    try
      {
        if(Stage.getSelectedIndex()!=-1)
          {
            DepartmentDto depart_search =
              new DepartmentDto(); //create floor dto object to use it in search
            DepartmentBao depart_bao =
              new BaoFactory().createDepartmentBao(user); //create floor bao object

            //check for selected building
            StageDto selected_stage = new StageDto();

            selected_stage.setNumber(Stage.getSelectedItem().toString()); //get selected building code

            depart_search.setStage(selected_stage); //set building to the floor search object

            //get floors from bao viewBuildinFloors method
            List<DepartmentDto> depart_list =
              depart_bao.viewStageDepartment(depart_search);

            Department.removeAllItems(); //remove all previous data in floor combobox

            //set floors code in it
            if(depart_list!=null&&!depart_list.isEmpty())
              {
                for(int i = 0; i<depart_list.size(); i++)
                  {
                    Department.addItem(depart_list.get(i).getName());

                  }

                Department.setSelectedIndex(-1);
              }
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    }//GEN-LAST:event_StageActionPerformed

    private void DayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DayActionPerformed
    // TODO add your handling code here:
    try
      {
        DepartmentDto dep = new DepartmentDto();
        StageDto stage = new StageDto();
        SlotDto slot = new SlotDto();
        ScheduleDto sch = new ScheduleDto();
        StageScheduleBao sch_bao =
          new BaoFactory().createStageScheduleBao(user);

        SlotBao slot_bao = new BaoFactory().createSlotBao(user);

        ScheduleDao sch_dao = new DaoFactory().createScheduleDao(user);
        dep.setName(Department.getSelectedItem().toString());
        stage.setNumber(Stage.getSelectedItem().toString());
        sch.setDepartment(dep);
        sch.setStage(stage);
        sch_dao.getID(sch);
        slot.setDay(Day.getSelectedItem().toString());
        if(Day.getSelectedIndex()!=-1)
          {

            List<SlotDto> slot_list = slot_bao.getSlots(slot, sch);

            System.out.println(slot_list);

            Slot.removeAllItems(); //remove all previous data in floor combobox

            //set floors code in it
            if(slot_list!=null&&!slot_list.isEmpty())
              {
                for(int i = 0; i<slot_list.size(); i++)
                  {
                    Slot.addItem(slot_list.get(i).getCode());
                  }

                Slot.setSelectedIndex(-1);

              }
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
        
        
        
    }//GEN-LAST:event_DayActionPerformed

  private void searchMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseMoved
  {//GEN-HEADEREND:event_searchMouseMoved
    // TODO add your handling code here:
    searchPanel.setBackground(Color.decode("#0051D2"));
  }//GEN-LAST:event_searchMouseMoved

  private void searchMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseExited
  {//GEN-HEADEREND:event_searchMouseExited
    // TODO add your handling code here:
    searchPanel.setBackground(Color.decode("#0081D3"));
  }//GEN-LAST:event_searchMouseExited

  private void searchActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_searchActionPerformed
  {//GEN-HEADEREND:event_searchActionPerformed
    // TODO add your handling code here:
    try
      {
        if(checkValidity())
          {
            DepartmentDto dep = new DepartmentDto();
            StageDto stage = new StageDto();
            SlotDto slot = new SlotDto();
            LocationTypeDto loc = new LocationTypeDto();
            BuildingDto build = new BuildingDto();
            ScheduleDto sch = new ScheduleDto();
            StageScheduleBao sch_bao =
              new BaoFactory().createStageScheduleBao(user);
            LocationBao loc_bao = new BaoFactory().createLocationBao(user);
            ScheduleDao sch_dao = new DaoFactory().createScheduleDao(user);


            dep.setName(Department.getSelectedItem().toString());
            stage.setNumber(Stage.getSelectedItem().toString());
            sch.setDepartment(dep);
            sch.setStage(stage);
            sch_dao.getID(sch);
            loc.setCode(LocationType.getSelectedItem().toString());
            build.setCode(Building.getSelectedItem().toString());
            slot.setCode(Slot.getSelectedItem().toString());
            slot.setDay(Day.getSelectedItem().toString());
            loc_bao.freelocationsfiltered(slot, build, loc);


            //get Locations from bao
            List<LocationDto> loc_list =
              loc_bao.freelocationsfiltered(slot, build, loc);

            FreeSpace.removeAllItems(); //remove all previous data in floor combobox

            //set Free Locations code in it
            if(loc_list!=null&&!loc_list.isEmpty())
              {
                Freepanel.setVisible(true);
                for(int i = 0; i<loc_list.size(); i++)
                  {
                    FreeSpace.addItem(loc_list.get(i).getName());
                  }

                FreeSpace.setSelectedIndex(-1);
              }
            else
              {
                JOptionPane.showMessageDialog(null,
                  "There is no space free!", "Error",
                  JOptionPane.ERROR_MESSAGE);
              }
          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    
  }//GEN-LAST:event_searchActionPerformed

  private void SetLocMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SetLocMouseMoved
  {//GEN-HEADEREND:event_SetLocMouseMoved
    // TODO add your handling code here:
    ClearPanel.setBackground(Color.decode("#0051D2"));
  }//GEN-LAST:event_SetLocMouseMoved

  private void SetLocMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SetLocMouseExited
  {//GEN-HEADEREND:event_SetLocMouseExited
    // TODO add your handling code here:
    ClearPanel.setBackground(Color.decode("#0081D3"));
  }//GEN-LAST:event_SetLocMouseExited

  private void SetLocActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SetLocActionPerformed
  {//GEN-HEADEREND:event_SetLocActionPerformed
    // TODO add your handling code here:

    try
      {
        if(FreeSpace.getSelectedIndex()!=-1)
          {

            SlotDto slot = new SlotDto(); //create slot dto objrct
            SlotBao bao =
              new BaoFactory().createSlotBao(user); // create bao object

            //get slot
            slot.setCode(Slot.getSelectedItem().toString());
            slot.setDay(Day.getSelectedItem().toString());


            //get the selected ocation
            LocationDto loc = new LocationDto();
            loc.setName(FreeSpace.getSelectedItem().toString());
            slot.setLocation(loc);

            if(bao.updateManual(slot, user))
              { //if update done
                JOptionPane.showMessageDialog(null,
                  "Slot updated Successfully", "Done", 1);
              }
            else
              {
                JOptionPane.showMessageDialog(null,
                  "This Slot Can't be updated!", "Not Found",
                  JOptionPane.ERROR_MESSAGE);
              }
          }
        else
          {
            JOptionPane.showMessageDialog(null, "Please Select Space ",
              "Invalid Input", 1);
          }

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }

  }//GEN-LAST:event_SetLocActionPerformed

  private void SlotActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SlotActionPerformed
  {//GEN-HEADEREND:event_SlotActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_SlotActionPerformed

  private void FreeSpaceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_FreeSpaceActionPerformed
  {//GEN-HEADEREND:event_FreeSpaceActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_FreeSpaceActionPerformed

  private boolean checkValidity()
  {
    try
      {
        //check for Department is not selected
        if(Department.getSelectedIndex()==-1) //floor
          {
            JOptionPane.showMessageDialog(null, "Please, Choose Department",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("floor text is empty");
          }

        //check for stage is not selected
        if(Stage.getSelectedIndex()==-1) //floor
          {
            JOptionPane.showMessageDialog(null, "Please, Choose Stage",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("floor text is empty");
          }

        //check for Day is not selected
        if(Day.getSelectedIndex()==-1) //floor
          {
            JOptionPane.showMessageDialog(null, "Please, Choose Day",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("floor text is empty");
          }

        //check for slot is not selected
        if(Slot.getSelectedIndex()==-1) //floor
          {
            JOptionPane.showMessageDialog(null, "Please, Choose Slot",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("floor text is empty");
          }

        //check for building is not selected
        if(Building.getSelectedIndex()==-1) //Building
          {
            JOptionPane.showMessageDialog(null, "Please, Choose Building",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("buiding is empty");
          }

        //check for location type is not selected
        if(LocationType.getSelectedIndex()==-1) //location type
          {
            JOptionPane.showMessageDialog(null,
              "Please, Choose location type", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("location_type text is empty");
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
    
    
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JComboBox<String> Building;
  private javax.swing.JPanel ClearPanel;
  private javax.swing.JLabel CodeLabel;
  private javax.swing.JComboBox<String> Day;
  private javax.swing.JComboBox<String> Department;
  private javax.swing.JComboBox<String> FreeSpace;
  private javax.swing.JPanel Freepanel;
  private javax.swing.JComboBox<String> LocationType;
  private javax.swing.JButton SetLoc;
  private javax.swing.JComboBox<String> Slot;
  private javax.swing.JComboBox<String> Stage;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JButton search;
  private javax.swing.JPanel searchPanel;
  // End of variables declaration//GEN-END:variables

}
