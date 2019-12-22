
package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.CourseBao;
import com.fym.lta.BAO.DepartmentBao;
import com.fym.lta.BAO.LocationTypeBao;
import com.fym.lta.BAO.RoleScreenBao;
import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.RoleScreenDao;
import com.fym.lta.DTO.CourseDto;
import com.fym.lta.DTO.DepartmentDto;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.UserDto;
import com.fym.lta.Excel.CourseExcel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

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
 * @author Mustafa Zalat
 */
public class CoursePanel extends javax.swing.JPanel
{
  @SuppressWarnings({ "compatibility:-4093812132744169411",
      "oracle.jdeveloper.java.serialversionuid-stale" })
  private static final long serialVersionUID = 1L;

  /**Definitions*/
  //define user dto as "user"
  private static UserDto user;
  //define course dro as "course"
  private static CourseDto course;
  //define building bao as "course_bao"
  private static CourseBao course_bao;
  

  /**constructor to create new Coursepanel
   * @param user object */
  public CoursePanel(UserDto user)
  {
    try
      {
        //set taken user object to the private one
        CoursePanel.user = user; 

        //set new course bao object
        course_bao = new BaoFactory().createCourseBao(user);
      
        //set panel components
        initComponents();
      
        defaultdata(); //set default data for edit panel

        //view all existing course
        setTableModel(course_bao.listAll());

        //use user access method to check user validity on this screen
        userAccess();

      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }


  /**set course table model
   * @param course dto list */
  private void setTableModel(List<CourseDto> courses)
  {
    
    int size = 0; //for table row size

    //if given list not empty or null
    if(courses!=null&&!courses.isEmpty())
      size = courses.size(); //set table size to this list size

    /*this (2D) array of objects for table model
       *the first D for rows, second for columns */
    Object[][] coursesArr = new Object[size][9];
    
    for(int i = 0; i<size; i++) //for each row
      {
        /**set course attributes to this array element*/
        coursesArr[i][0] = courses.get(i).getId();  
        coursesArr[i][1] = courses.get(i).getCode(); 
        coursesArr[i][2] = courses.get(i).getName();
        coursesArr[i][3] = courses.get(i).getTerm();
        coursesArr[i][4] = courses.get(i).getLec_time();
        coursesArr[i][5] = courses.get(i).getSec_time();
        coursesArr[i][6] = courses.get(i).getPlt_lecture().getCode();
        coursesArr[i][7] = courses.get(i).getPlt_section().getCode();

        //for departments
        String departments = ""; //buffer string
      
        //view departments in table as format "d1, d2,..."
        if(courses.get(i).getDeparts()!=null&&
          !courses.get(i).getDeparts().isEmpty())
          {
            for(int j = 0; j<courses.get(i).getDeparts().size(); j++)
              {

                if(j==courses.get(i).getDeparts().size()-1) //for last one
                  departments += courses.get(i).getDeparts().get(j).getCode();
                else
                  departments += courses.get(i).getDeparts().get(j).getCode()+", ";
              }
          }

        coursesArr[i][8] = departments; //set them in table

      }

    //set table model using this array
    courseTable.setModel(new javax.swing.table.DefaultTableModel(coursesArr,
        new String[] { "Id", "Code", "Name","Term", "lec time","Sec time", "PLT lec",
        "PLT sec", "Department" }));


    //change table header color
    courseTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
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

    //label under tabel show courses count viewed in table
    no_of_rows.setText("Table result: "+
      Integer.toString(courseTable.getRowCount())+"  courses");
  }


  /**This method use to check user validity on this screen
   * if user has full access or viewonly access*/
  private void userAccess()
  {

    //set user screen name to "Course" to make check on it
    user.setScreen_name("Course");

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


  /** to set default data for edit space panel
   */
  private void defaultdata()
  {

    
    IdText.setEnabled(true); //enable id text box
    Save.setText("Insert");  //set save button text to "insert"
    
    //set default text for text boxes
    IdText.setText("Enter course ID");
    CodeText.setText("Enter course code");
    no_of_hours_sec.setText("Enter number of hours");
    no_of_hours_lec.setText("Enter number of hours");
    NameText.setText("Enter course name");

    //clear department combobox 
    departCombo.removeAllItems();

    //create location type bao
    LocationTypeBao locType_Bao =
      new BaoFactory().createLocationTypeBao(user); //create location type bao object
   
   //create location type list
    List<LocationTypeDto> loc_list =
      locType_Bao.viewAll(); //get all location types


    //Set all existing location type to plt_lec combobox
    try
      {

        plt_lecBox.removeAllItems(); //remove all existing data in  plt_lec combobox

        //set location type codes to the plt_lec combobox
        if(loc_list!=null&&!loc_list.isEmpty())
          {
            for(int i = 0; i<loc_list.size(); i++)
              {
                plt_lecBox.addItem(loc_list.get(i).getCode());
              }
            plt_lecBox.setSelectedIndex(-1); //select nothing in this combo

          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }


    //Set all existing location type to plt_sec combobox
    try
      {
        plt_secBox.removeAllItems(); //remove all existing data in  plt_sec combobox

        //set location type codes to the plt_sec combobox
        if(loc_list!=null&&!loc_list.isEmpty())
          {
            for(int i = 0; i<loc_list.size(); i++)
              {
                plt_secBox.addItem(loc_list.get(i).getCode());
              }
            plt_secBox.setSelectedIndex(-1); //select nothing in this combo

          }
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
   
   //select nothing in term combobox
    TermCombo.setSelectedIndex(-1);

  }

  /**update an existing course*/
  private void update()
  {

    if(checkValidity())
      {
        try
          {

            course = new CourseDto(); //set new course dto to "course"

            //set attributes values for course object
            course.setId(Integer.parseInt(IdText.getText()));
            course.setCode(CodeText.getText());
            course.setName(NameText.getText());
            course.setLec_time(Float.parseFloat(no_of_hours_lec.getText()));
            course.setSec_time(Float.parseFloat(no_of_hours_sec.getText()));
            course.setTerm(TermCombo.getSelectedItem().toString());

            //for PLT
            //lecture
            course.setPlt_lecture(new LocationTypeDto());
            course.getPlt_lecture().setCode(plt_lecBox.getItemAt(plt_lecBox.getSelectedIndex()));
            //section
            course.setPlt_section(new LocationTypeDto());
            course.getPlt_section().setCode(plt_secBox.getItemAt(plt_secBox.getSelectedIndex()));


            // check and pick up which departments have been selected
            DepartmentDto depart = new DepartmentDto();
            List<DepartmentDto> departments =
              new ArrayList<DepartmentDto>();

            for(int i = 0; i<departCombo.getItemCount(); i++)
              {
                depart = new DepartmentDto();
                depart.setCode(departCombo.getItemAt(i));
                departments.add(depart);
                depart = null;
              }

            course.setDeparts(departments); //set them to course object

            //try update this course
            if(course_bao.update(course, user))
              {
                //if update done show message to inform user that
                JOptionPane.showMessageDialog(null,
                  "Course has updated successfully!", "Done", 1);

                //refresh table
                setTableModel(course_bao.listAll());
                courseTable.repaint();
              }

            else //if update failed
              {
                //show message to tell user this course does'nt exist
                JOptionPane.showMessageDialog(null, "Course doesn't exist!",
                  "Not Found", JOptionPane.ERROR_MESSAGE);
              }

          }

        catch(Exception e)
          {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
              "Some Thing went wrong!\nPlease check your entered data. ",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);

          }
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
        /**check for empty entered data*/
        //for default id text
        if(IdText.getText().equalsIgnoreCase("Enter course ID"))
          {
            //ask user enter id
            JOptionPane.showMessageDialog(null, "Please, enter Course id",
              "Invaid Input", JOptionPane.ERROR_MESSAGE);
            
            //thow this exception 
            throw new IllegalArgumentException("id text is empry");
          }
        //for default code text
        else if(CodeText.getText().equalsIgnoreCase("Enter course code"))

          {
            //ask user enter code
            JOptionPane.showMessageDialog(null, "Please, enter Course code",
              "Invaid Input", JOptionPane.ERROR_MESSAGE);
            
            //throw this exception 
            throw new IllegalArgumentException("code text is empty");
          }
        //for default name text
        else if(NameText.getText().equalsIgnoreCase("Enter course name"))
          {
            //ask user enter name
            JOptionPane.showMessageDialog(null, "Please, enter Course name",
              "Invaid Input", JOptionPane.ERROR_MESSAGE);
            //throw empty name exception
            throw new IllegalArgumentException("name text is empty");

          }
        //for default number of hours  text
        else if(no_of_hours_lec.getText().equalsIgnoreCase("Enter number of hours")||
                no_of_hours_sec.getText().equalsIgnoreCase("Enter number of hours"))
          {
            //ask user enter no. of hours
            JOptionPane.showMessageDialog(null,
              "Please enter, number of hours", "Invaid Input",
              JOptionPane.ERROR_MESSAGE);
            
            //throw this exception
            throw new IllegalArgumentException("no_of_hourstext is empty");
          }
        //check for plt_lec is not selected
        else if(plt_lecBox.getSelectedIndex()==-1)
          {
            //ask user select it
            JOptionPane.showMessageDialog(null,
              "Please, Choose Preferred location type for lecture ",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            
            //throw this exception
            throw new IllegalArgumentException("Plt_lec is not selected");
          }
        //check for plt_sec is not selected
        else if(plt_secBox.getSelectedIndex()==-1)
          {
            //ask user select it
            JOptionPane.showMessageDialog(null,
              "Please, Choose Preferred location type for section ",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            
            //throw this exception
            throw new IllegalArgumentException("Plt_sec is not selected");
          }
        //check for department is not selected
        else if(departCombo.getItemCount()==0)
          {
            //ask user select department/s for this course
            JOptionPane.showMessageDialog(null,
              "Please, select at least one department ", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            
            //throw this exception
            throw new IllegalArgumentException("no selected department");
          }


        /**  Check validity of id */
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
            //ask user to enter number
            JOptionPane.showMessageDialog(null,
              "Please enter number for location ID", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            
            return false; //data is invalid
          }


        /** check validity of code */

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

            //code contain only letters/numbers and "_","."," ","-"
            if(!Character.isLetterOrDigit(CodeText.getText().charAt(i))&&
              CodeText.getText().charAt(i)!='_'&&
              CodeText.getText().charAt(i)!='-'&&
              CodeText.getText().charAt(i)!='.'&&
              CodeText.getText().charAt(i)!=' ')
              {
                //show message to tell user  that
                JOptionPane.showMessageDialog(null,
                  "code can't contain stranger symbols",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);

                //throw this exception
                throw new IllegalArgumentException("code can't contain stranger symbols");

              }
          }


        /** check validity of name */

        //name can't start with number or symbol
        if(!Character.isAlphabetic(NameText.getText().charAt(0)))
          {
           
            //show message to tell user  that
            JOptionPane.showMessageDialog(null,
              "Invalid Name format\n\n(Name can't start with number or symbol)",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
           
            //throw this exception
            throw new IllegalArgumentException("Name can't start with number or symbol");

          }

        //check for all chars
        for(int i = 1; i<NameText.getText().length(); i++)
          {

            //code contain only letters/numbers and "_","."," ","-"
            if(!Character.isLetterOrDigit(NameText.getText().charAt(i))&&
              NameText.getText().charAt(i)!='_'&&
              NameText.getText().charAt(i)!=' '&&
              NameText.getText().charAt(i)!='.'&&
              NameText.getText().charAt(i)!='-'
            )
              {
                JOptionPane.showMessageDialog(null,
                  "Name can't contain stranger symbols",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Name can't contain stranger symbols");

              }
          }


        /** Check validity of number of hours */
        try
          {
            float hours = Float.parseFloat(no_of_hours_lec.getText());
            //Check for the entered number of hours is a positive float number
            if(hours<1)
              {
                //ask user enter positive number 
                JOptionPane.showMessageDialog(null,
                  "Invalid number of hours \n\n(number of hours is only Positive Number)",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
               
                //throw this exception
                throw new IllegalArgumentException("number of hours is only Positive Number");
              }
          }
        catch(java.lang.NumberFormatException e) //if it is not a number
          {
            e.printStackTrace();
            
            //ask user to enter number
            JOptionPane.showMessageDialog(null,
              "Please, enter number for number of hours", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            
            return false; //data is invalid
          }

        try
          {
            float hours = Float.parseFloat(no_of_hours_sec.getText());
            //Check for the entered number of hours is a positive float number
            if(hours<1)
              {
                //ask user enter positive number
                JOptionPane.showMessageDialog(null,
                  "Invalid number of hours \n\n(number of hours is only Positive Number)",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);

                //throw this exception
                throw new IllegalArgumentException("number of hours is only Positive Number");
              }
          }
        catch(java.lang.NumberFormatException e) //if it is not a number
          {
            e.printStackTrace();

            //ask user to enter number
            JOptionPane.showMessageDialog(null,
              "Please, enter number for number of hours", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);

            return false; //data is invalid
          }

     
        return true; //all data correct

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
          "Some Thing went wrong!\n\nPlease check your entered data. ",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
        return false; //data is invaild
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
    courseTable = new javax.swing.JTable();
    SearchText = new javax.swing.JTextField();
    no_of_rows = new java.awt.Label();
    EditPanel = new javax.swing.JPanel();
    id = new javax.swing.JLabel();
    no_of_hours_lecture = new javax.swing.JLabel();
    CodeText = new javax.swing.JTextField();
    IdText = new javax.swing.JTextField();
    no_of_hours_lec = new javax.swing.JTextField();
    code = new javax.swing.JLabel();
    name = new javax.swing.JLabel();
    NameText = new javax.swing.JTextField();
    plt = new javax.swing.JLabel();
    plt_lecBox = new javax.swing.JComboBox<>();
    Plt_lec_label = new javax.swing.JLabel();
    plt_sec = new javax.swing.JLabel();
    plt_secBox = new javax.swing.JComboBox<>();
    jSeparator1 = new javax.swing.JSeparator();
    departCombo = new javax.swing.JComboBox<>();
    plt_sec1 = new javax.swing.JLabel();
    jSeparator2 = new javax.swing.JSeparator();
    departmentSectionImage = new javax.swing.JLabel();
    SavePanel = new javax.swing.JPanel();
    Save = new javax.swing.JButton();
    DeletePanel = new javax.swing.JPanel();
    Delete = new javax.swing.JButton();
    ClearPanel = new javax.swing.JPanel();
    clear = new javax.swing.JButton();
    ChangePanel = new javax.swing.JPanel();
    ChangeDepartment = new javax.swing.JButton();
    no_of_hours = new javax.swing.JLabel();
    no_of_hours_sec = new javax.swing.JTextField();
    no_of_hours_section = new javax.swing.JLabel();
    term = new javax.swing.JLabel();
    TermCombo = new javax.swing.JComboBox<>();
    titleLabel = new javax.swing.JLabel();
    jSeparator3 = new javax.swing.JSeparator();
    RefreshPanel = new javax.swing.JPanel();
    Refresh = new javax.swing.JButton();
    searchPanel = new javax.swing.JPanel();
    search = new javax.swing.JButton();
    ExportButton = new javax.swing.JButton();
    ImportButton = new javax.swing.JButton();

    setBackground(new java.awt.Color(245, 245, 245));
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    courseTable.setAutoCreateRowSorter(true);
    courseTable.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    courseTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {

      },
      new String []
      {
        "ID", "code", "name", "lecture time", "plt section", "plt lecture", "department"
      }
    )
    {
      Class[] types = new Class []
      {
        java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Float.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
      };

      public Class getColumnClass(int columnIndex)
      {
        return types [columnIndex];
      }
    });
    courseTable.setFillsViewportHeight(true);
    courseTable.setRowHeight(25);
    courseTable.setRowMargin(2);
    courseTable.setSelectionBackground(new java.awt.Color(0, 153, 204));
    courseTable.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        courseTableMouseClicked(evt);
      }
    });
    courseTable.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyPressed(java.awt.event.KeyEvent evt)
      {
        courseTableKeyPressed(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt)
      {
        courseTableKeyReleased(evt);
      }
    });
    jScrollPane1.setViewportView(courseTable);
    if (courseTable.getColumnModel().getColumnCount() > 0)
    {
      courseTable.getColumnModel().getColumn(0).setHeaderValue("ID");
      courseTable.getColumnModel().getColumn(1).setHeaderValue("code");
      courseTable.getColumnModel().getColumn(2).setHeaderValue("name");
      courseTable.getColumnModel().getColumn(3).setHeaderValue("lecture time");
      courseTable.getColumnModel().getColumn(4).setHeaderValue("plt section");
      courseTable.getColumnModel().getColumn(5).setHeaderValue("plt lecture");
      courseTable.getColumnModel().getColumn(6).setHeaderValue("department");
    }

    add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 760, 750));

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
    add(SearchText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 670, 50));

    no_of_rows.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
    no_of_rows.setForeground(new java.awt.Color(135, 135, 135));
    no_of_rows.setText("no of rows");
    add(no_of_rows, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 860, 180, 20));

    EditPanel.setBackground(new java.awt.Color(245, 245, 245));
    EditPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    id.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    id.setForeground(new java.awt.Color(0, 51, 204));
    id.setText("ID");
    EditPanel.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 50, 50));

    no_of_hours_lecture.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    no_of_hours_lecture.setForeground(new java.awt.Color(0, 51, 204));
    no_of_hours_lecture.setText("Lecture");
    EditPanel.add(no_of_hours_lecture, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, -1, 20));

    CodeText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    CodeText.setText("Enter course code");
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
    EditPanel.add(CodeText, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 510, 50));

    IdText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    IdText.setText("Enter course ID");
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
    EditPanel.add(IdText, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 510, 50));

    no_of_hours_lec.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    no_of_hours_lec.setText("Enter number of hours");
    no_of_hours_lec.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    no_of_hours_lec.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        no_of_hours_lecFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        no_of_hours_lecFocusLost(evt);
      }
    });
    no_of_hours_lec.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        no_of_hours_lecActionPerformed(evt);
      }
    });
    EditPanel.add(no_of_hours_lec, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 440, 190, 50));

    code.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    code.setForeground(new java.awt.Color(0, 51, 204));
    code.setText("Code");
    EditPanel.add(code, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 61, 60));

    name.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    name.setForeground(new java.awt.Color(0, 51, 204));
    name.setText("Name");
    EditPanel.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 48, 60));

    NameText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    NameText.setText("Enter course name");
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
    EditPanel.add(NameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 510, 50));

    plt.setBackground(new java.awt.Color(231, 78, 123));
    plt.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    plt.setForeground(new java.awt.Color(231, 78, 123));
    plt.setText("Preferred location type:");
    EditPanel.add(plt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, -1, 20));

    plt_lecBox.setBackground(new java.awt.Color(255, 255, 255));
    plt_lecBox.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    EditPanel.add(plt_lecBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 530, 180, 50));

    Plt_lec_label.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    Plt_lec_label.setForeground(new java.awt.Color(0, 51, 204));
    Plt_lec_label.setText("Lecture");
    EditPanel.add(Plt_lec_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, -1, -1));

    plt_sec.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    plt_sec.setForeground(new java.awt.Color(0, 51, 204));
    plt_sec.setText("Section");
    EditPanel.add(plt_sec, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 540, -1, -1));

    plt_secBox.setBackground(new java.awt.Color(255, 255, 255));
    plt_secBox.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    EditPanel.add(plt_secBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 530, 200, 50));
    EditPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 393, 600, 20));

    departCombo.setBackground(new java.awt.Color(255, 255, 255));
    departCombo.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    departCombo.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        departComboActionPerformed(evt);
      }
    });
    EditPanel.add(departCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 620, 261, 50));

    plt_sec1.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    plt_sec1.setForeground(new java.awt.Color(0, 51, 204));
    plt_sec1.setText("Department");
    EditPanel.add(plt_sec1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, -1, -1));
    EditPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 600, 20));

    departmentSectionImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_org_unit_128px.png"))); // NOI18N
    EditPanel.add(departmentSectionImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, -1, -1));

    SavePanel.setBackground(new java.awt.Color(0, 129, 211));

    Save.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    Save.setForeground(new java.awt.Color(255, 255, 255));
    Save.setText("Insert");
    Save.setBorderPainted(false);
    Save.setContentAreaFilled(false);
    Save.setFocusPainted(false);
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
      .addComponent(Save, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(SavePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 710, -1, -1));

    DeletePanel.setBackground(new java.awt.Color(0, 129, 211));

    Delete.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    Delete.setForeground(new java.awt.Color(255, 255, 255));
    Delete.setText("Delete ");
    Delete.setBorderPainted(false);
    Delete.setContentAreaFilled(false);
    Delete.setFocusPainted(false);
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
      .addComponent(Delete, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
    );
    DeletePanelLayout.setVerticalGroup(
      DeletePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Delete, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(DeletePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 710, -1, 50));

    ClearPanel.setBackground(new java.awt.Color(0, 129, 211));

    clear.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    clear.setForeground(new java.awt.Color(255, 255, 255));
    clear.setText("Clear");
    clear.setBorderPainted(false);
    clear.setContentAreaFilled(false);
    clear.setFocusPainted(false);
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
      .addComponent(clear, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(ClearPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 700, 100, -1));

    ChangePanel.setBackground(new java.awt.Color(0, 129, 211));

    ChangeDepartment.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    ChangeDepartment.setForeground(new java.awt.Color(255, 255, 255));
    ChangeDepartment.setText("Change");
    ChangeDepartment.setBorderPainted(false);
    ChangeDepartment.setContentAreaFilled(false);
    ChangeDepartment.setFocusPainted(false);
    ChangeDepartment.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        ChangeDepartmentMouseMoved(evt);
      }
    });
    ChangeDepartment.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        ChangeDepartmentMouseExited(evt);
      }
    });
    ChangeDepartment.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        ChangeDepartmentActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout ChangePanelLayout = new javax.swing.GroupLayout(ChangePanel);
    ChangePanel.setLayout(ChangePanelLayout);
    ChangePanelLayout.setHorizontalGroup(
      ChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(ChangeDepartment, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
    );
    ChangePanelLayout.setVerticalGroup(
      ChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(ChangeDepartment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    EditPanel.add(ChangePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 620, -1, 50));

    no_of_hours.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    no_of_hours.setForeground(new java.awt.Color(231, 78, 123));
    no_of_hours.setText("Number of hours:");
    EditPanel.add(no_of_hours, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, -1, 20));

    no_of_hours_sec.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    no_of_hours_sec.setText("Enter number of hours");
    no_of_hours_sec.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    no_of_hours_sec.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        no_of_hours_secFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        no_of_hours_secFocusLost(evt);
      }
    });
    no_of_hours_sec.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        no_of_hours_secActionPerformed(evt);
      }
    });
    EditPanel.add(no_of_hours_sec, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 440, 210, 50));

    no_of_hours_section.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    no_of_hours_section.setForeground(new java.awt.Color(0, 51, 204));
    no_of_hours_section.setText("Section");
    EditPanel.add(no_of_hours_section, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 450, -1, 20));

    term.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    term.setForeground(new java.awt.Color(0, 51, 204));
    term.setText("Term");
    EditPanel.add(term, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 48, 60));

    TermCombo.setBackground(new java.awt.Color(255, 255, 255));
    TermCombo.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    TermCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First", "Second" }));
    EditPanel.add(TermCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 510, 50));

    add(EditPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 100, 650, 780));

    titleLabel.setBackground(new java.awt.Color(231, 78, 123));
    titleLabel.setFont(new java.awt.Font("VIP Rawy Regular", 0, 36)); // NOI18N
    titleLabel.setForeground(new java.awt.Color(231, 78, 123));
    titleLabel.setText("Course");
    add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, 240, 80));
    add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 90, 610, 40));

    RefreshPanel.setBackground(new java.awt.Color(0, 129, 211));

    Refresh.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    Refresh.setForeground(new java.awt.Color(255, 255, 255));
    Refresh.setText("Refresh ");
    Refresh.setBorderPainted(false);
    Refresh.setContentAreaFilled(false);
    Refresh.setFocusPainted(false);
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
      .addComponent(Refresh, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
    );
    RefreshPanelLayout.setVerticalGroup(
      RefreshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(Refresh, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
    );

    add(RefreshPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 870, 130, 50));

    searchPanel.setBackground(new java.awt.Color(0, 129, 211));

    search.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    search.setForeground(new java.awt.Color(255, 255, 255));
    search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_search_26px.png"))); // NOI18N
    search.setBorderPainted(false);
    search.setContentAreaFilled(false);
    search.setFocusPainted(false);
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
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanelLayout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    add(searchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 40, 80, 50));

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
  }//GEN-END:initComponents


    private void CodeTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodeTextFocusGained
   
    //if found text is the default text
    if(CodeText.getText().equalsIgnoreCase("Enter course code"))
      {
        CodeText.setText(""); //remove it
      }

    //change border color
    CodeText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_CodeTextFocusGained

    private void CodeTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodeTextFocusLost
    
    //if there is no text
    if(CodeText.getText().trim().equalsIgnoreCase(""))
      {
        //set the default one
        CodeText.setText("Enter course code");
      }

    //reset border color
    CodeText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));
    
    }//GEN-LAST:event_CodeTextFocusLost

    private void CodeTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CodeTextActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_CodeTextActionPerformed

    private void IdTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusGained
    
    //if found text is the default text
    if(IdText.getText().equalsIgnoreCase("Enter Course Id"))
      {
        IdText.setText("");  //remove it
      }

    //change border color
    IdText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_IdTextFocusGained

    private void IdTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusLost
   
    //if there is no text
    if(IdText.getText().trim().equalsIgnoreCase(""))
      {
        //set the default one
        IdText.setText("Enter Course Id");
      }
    
    //reset border color
    IdText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_IdTextFocusLost

    private void IdTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdTextActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_IdTextActionPerformed

    private void no_of_hours_lecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_no_of_hours_lecFocusGained
    
    //if found text is the default text
    if(no_of_hours_lec.getText().equalsIgnoreCase("Enter number of hours"))
      {
        no_of_hours_lec.setText(""); //remove it
      }

    //change border color
    no_of_hours_lec.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_no_of_hours_lecFocusGained

    private void no_of_hours_lecFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_no_of_hours_lecFocusLost
   
    //if there is no text
    if(no_of_hours_lec.getText().trim().equalsIgnoreCase(""))
      {
        //set the default one
        no_of_hours_lec.setText("Enter number of hours");
      }

    //reset border color
    no_of_hours_lec.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_no_of_hours_lecFocusLost

    private void SearchTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusGained
   
    //if found text is the default text
    if(SearchText.getText().equalsIgnoreCase("Enter text to search"))
      {
        SearchText.setText("");  //remove it
      }
    
    //change border color
    SearchText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_SearchTextFocusGained

    private void SearchTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusLost
   
    //if there is no text
    if(SearchText.getText().trim().equalsIgnoreCase(""))
      {
        //set the default one
        SearchText.setText("Enter text to search");
      }

    //reset border color
    SearchText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_SearchTextFocusLost

    private void SearchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchTextActionPerformed
    try
      {

        course = new CourseDto(); //set new course dto to "course"

        // Check if the Search field is empty
        if(SearchText.getText().equalsIgnoreCase(""))
          {
            //Show message tell user that Search Field is empty
            JOptionPane.showMessageDialog(null, "Search field is empty",
              "Invalid Input!", JOptionPane.ERROR_MESSAGE);
            //throw exception
            throw new IllegalArgumentException("Search field is empty");
          }
      
        course.setSearch(SearchText.getText()); //get search text
      
        //get search result in course dto list using search bao method
        List<CourseDto> result = course_bao.searchFor(course);

        //if there is any result
        if(result!=null&&!result.isEmpty())
          {
            //set result set to the table
            setTableModel(result);
            courseTable.repaint();
          }

       //else show message to tell user that there is no result
        else
          JOptionPane.showMessageDialog(null,
            "There is no search result for: "+SearchText.getText(),
            "Invalid search", 1);

      }

    catch(Exception e)
      {
        //print catched exception
        e.printStackTrace();

      }
    }//GEN-LAST:event_SearchTextActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed

    try
      {

        course = new CourseDto(); //set new course dto to "course"

        // Check if the Search field is empty
        if(SearchText.getText().equalsIgnoreCase("Enter text to search"))
          {
            //Show message tell user that Search Field is empty
            JOptionPane.showMessageDialog(null, "Search field is empty",
              "Invalid Input!", JOptionPane.ERROR_MESSAGE);
            //throw exception
            throw new IllegalArgumentException("Search field is empty");
          }
      
        course.setSearch(SearchText.getText()); //get search text

        //get search result in course dto list using search bao method
        List<CourseDto> result = course_bao.searchFor(course);

        //if there is any result
        if(result!=null&&!result.isEmpty())
          {
            //set result set to the table
            setTableModel(result);
            courseTable.repaint();
          }

        //else show message to tell user that there is no result
        else
          JOptionPane.showMessageDialog(null,
            "There is no search result for: "+SearchText.getText(),
            "Invalid search", 1);
      }

    catch(Exception e)
      {
        //print catched exception
        e.printStackTrace();

      }
    }//GEN-LAST:event_searchActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
    
    //if the set text update
    if(Save.getText().equalsIgnoreCase("Update"))
      update(); //use update method

    else // try insert this course
      {
        if(checkValidity())  //check if entered data is valid
          {
            try
              {

                course = new CourseDto(); //set new course dto to "course"

                //set attributes values for cousre object
                course.setId(Integer.parseInt(IdText.getText()));
                course.setCode(CodeText.getText());
                course.setName(NameText.getText());
                course.setLec_time(Float.parseFloat(no_of_hours_lec.getText()));
                course.setSec_time(Float.parseFloat(no_of_hours_sec.getText()));
                course.setTerm(TermCombo.getSelectedItem().toString());

                //for PLT
                //lecture
                course.setPlt_lecture(new LocationTypeDto());
                course.getPlt_lecture().setCode(plt_lecBox.getItemAt(plt_lecBox.getSelectedIndex()));
                //section
                course.setPlt_section(new LocationTypeDto());
                course.getPlt_section().setCode(plt_secBox.getItemAt(plt_secBox.getSelectedIndex()));


                // check and pick up which departments have been selected
                DepartmentDto depart = new DepartmentDto();
                List<DepartmentDto> departments =
                  new ArrayList<DepartmentDto>();

                for(int i = 0; i<departCombo.getItemCount(); i++)
                  {
                    depart = new DepartmentDto();
                    depart.setCode(departCombo.getItemAt(i));
                    departments.add(depart);
                    depart = null;
                  }

                //set them to course object
                course.setDeparts(departments);

                if(course_bao.add(course, user)) //try insert it
                  {
                    //if it success show message to tell user that
                    JOptionPane.showMessageDialog(null,
                      "Course has inserted successfully!", "Done", 1);

                    //refresh table
                    setTableModel(course_bao.listAll());
                    courseTable.repaint();

                    //set save button text to "Update"
                    Save.setText("Update");
                    
                    //set id text disable (id can't be edited after insert)
                    IdText.setEnabled(false);

                  }
                else // if it failed it mean this course is already exit
                  {

                    /*so show message to ask user if he want
                    * to update this course or not*/
                    int reply =
                      JOptionPane.showConfirmDialog(null,
                        "This Course is already exist!\n\n"+
                        "Do you want update it?", "Warning",
                        JOptionPane.YES_NO_OPTION);

                    //update object if user choose yes
                    if(reply==JOptionPane.YES_OPTION)
                      {
                        update(); //use update method
                        
                        //set save button text to "Update"
                        Save.setText("Update");
                       
                        IdText.setEnabled(false); //set id text disable
                      }
                  }
              }

            catch(Exception e)
              {
               
                e.printStackTrace();
                
                //if there is any unexpected exception
                JOptionPane.showMessageDialog(null,
                  "Some Thing went wrong!\n\nPlease check your entered data. ",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
              }
          }
      }

    }//GEN-LAST:event_SaveActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
    try
      {

        //Show confirm message
        int reply =
          JOptionPane.showConfirmDialog(null,
            "Are you sure to delete this course?\n"+
            "All things depend on it will be deleted!", "Warning",
            JOptionPane.YES_NO_OPTION);

        //delete object if user choose yes
        if(reply==JOptionPane.YES_OPTION)
          {
            course = new CourseDto(); //set new course to course object

            course.setCode(CodeText.getText()); //get course code
            course.setId(Integer.parseInt(IdText.getText()));  //get course id

            //go to business layer
            boolean flag = course_bao.delete(course);

            if(flag==true)  //if deletion is success 
              {
                //show message ti=o inform user that
                JOptionPane.showMessageDialog(null,
                  "Course has deleted successfully!", "Done", 1);
                
                //refresh table
                setTableModel(course_bao.listAll());
                courseTable.repaint();
                
                IdText.setEnabled(true);  //set id text enable
                Save.setText("Insert");    //set save button text to "Insert"
              }
            else
              //if deletion failed show this message
              JOptionPane.showMessageDialog(null,
                "Can't delete this course.\n\n"+
                "It may assign to slots, Please delete them first.",
                "Error", JOptionPane.ERROR_MESSAGE);
          }
      }
    catch(Exception e)
      {
        
        e.printStackTrace(); //print exception
        
        //if there is any unexpected exception
        JOptionPane.showMessageDialog(null,
          "Some Thing went wrong!\nPlease check your entered data. ",
          "Invalid Input", 1);
      }
    }//GEN-LAST:event_DeleteActionPerformed

    private void NameTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameTextFocusGained
   
    //if found text is the default text
    if(NameText.getText().equalsIgnoreCase("Enter course Name"))
      {
        NameText.setText("");  //remove it
      }

    NameText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_NameTextFocusGained

    private void NameTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameTextFocusLost
    // TODO add your handling code here:
    if(NameText.getText().trim().equalsIgnoreCase(""))
      {
        NameText.setText("Enter course Name");
      }

    //change border color
    NameText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_NameTextFocusLost

    private void NameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameTextActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_NameTextActionPerformed

    private void no_of_hours_lecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_no_of_hours_lecActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_no_of_hours_lecActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
      
    defaultdata(); //set default data to edit space

    // view all data again
    List<CourseDto> courses = course_bao.listAll();
    setTableModel(courses);
        
    }//GEN-LAST:event_RefreshActionPerformed

    private void courseTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_courseTableKeyPressed
    
    //get number of selected row in table
    int i = courseTable.getSelectedRow();

    //Because "courseTable.getSelectedRow()" give the previous selected row
    if(evt.getExtendedKeyCode()==KeyEvent.VK_UP)
      i--; //for up key decrement
    else if(evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
      i++; // for down key increment


    /**move up and down in table to get data into edit space*/
    try
      {

        IdText.setEnabled(false); //set id text disable
        Save.setText("Update"); //set save button text "Update"

        /** set the attributes of selected row to text boxes*/
      
        IdText.setText(courseTable.getValueAt(i, 0).toString()); //get id
        CodeText.setText(courseTable.getValueAt(i, 1).toString()); //get code 
        NameText.setText(courseTable.getValueAt(i, 2).toString()); //get name
        no_of_hours_lec.setText(courseTable.getValueAt(i,
          4).toString()); //get no. of hours for lecture
        no_of_hours_sec.setText(courseTable.getValueAt(i,
          5).toString()); //get no. of hours for section

       
        String term = courseTable.getValueAt(i,
          3).toString(); //get term
        //set term in its combobox
        if(term.equalsIgnoreCase("First"))
           TermCombo.setSelectedIndex(0);
        else
          TermCombo.setSelectedIndex(1);
      
        //get plt lecture from table
        String plt_lec = courseTable.getValueAt(i, 6).toString();
        for(int j = 0; j<plt_lecBox.getItemCount(); j++)
          {
           //select it in plt_lecBox
            if(plt_lecBox.getItemAt(j).equalsIgnoreCase(plt_lec))
              plt_lecBox.setSelectedIndex(j);
          }

        //get plt section from table
        String plt_sec = courseTable.getValueAt(i, 7).toString();
        for(int j = 0; j<plt_secBox.getItemCount(); j++)
          {
            //select it in plt_secBox
            if(plt_secBox.getItemAt(j).equalsIgnoreCase(plt_sec))
              plt_secBox.setSelectedIndex(j);
          }

        //get departments from table
        String departs = courseTable.getValueAt(i, 8).toString();
        String[] departsCombo = null;

        //remove all existing items in departments combobox
        departCombo.removeAllItems();
      
        if(departs.contains(",")) //if there are more than one department
          {

            //split departs text to get each department code
            departsCombo = departs.split(", ");          

            if(departsCombo!=null&&departsCombo.length!=0) //for non empty department
              {
                //add existing departments into department combobox
                for(int j = 0; j<departsCombo[j].length(); j++)
                  {
                    departCombo.addItem(departsCombo[j]);
                  }
              }
          }
        // for one department
        else
          {
            departCombo.addItem(departs); //add it to combo
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

    /**  delete selected object when press delete */
    if(evt.getExtendedKeyCode()==KeyEvent.VK_DELETE)
      {

        try
          {

            //For one selected row in table
            if(courseTable.getSelectedRowCount()==1)
              { //Show confirm message
                int reply =
                  JOptionPane.showConfirmDialog(null,
                    "Are you sure to delete this course?\n"+
                    "All things depend on it will be deleted!", "Warning",
                    JOptionPane.YES_NO_OPTION);

                //delete object if user choose yes
                if(reply==JOptionPane.YES_OPTION)
                  {
                     course = new CourseDto(); //new dto course object

                    //get selected Course id from table
                    int id =
                      Integer.parseInt(courseTable.getValueAt(courseTable.getSelectedRow(),
                          0).toString());
                    course.setId(id); //set it to Course object
                    course.setCode(courseTable.getValueAt(courseTable.getSelectedRow(),
                      1).toString()); //get code
                    
                    //delete it using bao delete method
                    if(course_bao.delete(course)) 
                      {
                        //if it deleted sucessfilly show message to tell user that
                        JOptionPane.showMessageDialog(null,
                          "Location Deleted Successfully");
                        
                        //refresh table
                        setTableModel(course_bao.listAll());
                        courseTable.repaint();
                        
                        IdText.setEnabled(true); //set id text field enable
                        Save.setText("Insert");  // set save button text insert

                      }

                    else
                      //if bao method return false (Course not be deleted)
                      JOptionPane.showMessageDialog(null,
                        "Can't delete object", "Error",
                        JOptionPane.ERROR_MESSAGE);
                  }

              }
            else if(courseTable.getSelectedRowCount()==0)
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
        
    }//GEN-LAST:event_courseTableKeyPressed

    private void courseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_courseTableMouseClicked
    
    try
      {

        //get number of selected row in table
        int i = courseTable.getSelectedRow();

        IdText.setEnabled(false); //set id text disable
        Save.setText("Update"); //set save button text "Update"

        /** set the attributes of selected row to text boxes*/

        IdText.setText(courseTable.getValueAt(i, 0).toString()); //get id
        CodeText.setText(courseTable.getValueAt(i,
          1).toString()); //get code
        NameText.setText(courseTable.getValueAt(i,
          2).toString()); //get name
        no_of_hours_lec.setText(courseTable.getValueAt(i,
          4).toString()); //get no. of hours for lecture
        no_of_hours_sec.setText(courseTable.getValueAt(i,
          5).toString()); //get no. of hours for section


        String term = courseTable.getValueAt(i, 3).toString(); //get term
        //set term in its combobox
        if(term.equalsIgnoreCase("First"))
          TermCombo.setSelectedIndex(0);
        else
          TermCombo.setSelectedIndex(1);

        //get plt lecture from table
        String plt_lec = courseTable.getValueAt(i, 6).toString();
        for(int j = 0; j<plt_lecBox.getItemCount(); j++)
          {
            //select it in plt_lecBox
            if(plt_lecBox.getItemAt(j).equalsIgnoreCase(plt_lec))
              plt_lecBox.setSelectedIndex(j);
          }

        //get plt section from table
        String plt_sec = courseTable.getValueAt(i, 7).toString();
        for(int j = 0; j<plt_secBox.getItemCount(); j++)
          {
            //select it in plt_secBox
            if(plt_secBox.getItemAt(j).equalsIgnoreCase(plt_sec))
              plt_secBox.setSelectedIndex(j);
          }

        //get departments from table
        String departs = courseTable.getValueAt(i, 8).toString();
        String[] departsCombo = null;

        //remove all existing items in departments combobox
        departCombo.removeAllItems();

        if(departs.contains(",")) //if there are more than one department
          {

            //split departs text to get each department code
            departsCombo = departs.split(", ");

            if(departsCombo!=null&&
              departsCombo.length!=0) //for non empty department
              {
                //add existing departments into department combobox
                for(int j = 0; j<departsCombo[j].length(); j++)
                  {
                    departCombo.addItem(departsCombo[j]);
                  }
              }
          }
        // for one department
        else
          {
            departCombo.addItem(departs); //add it to combo
          }

      }
    catch(Exception e)
      {
        e.printStackTrace();

      }
            
    }//GEN-LAST:event_courseTableMouseClicked

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
       
    defaultdata(); //set default data to edit space 
        
    }//GEN-LAST:event_clearActionPerformed

    private void courseTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_courseTableKeyReleased
    // TODO add your handling code here:
    }//GEN-LAST:event_courseTableKeyReleased

  private void ChangeDepartmentActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ChangeDepartmentActionPerformed
  {//GEN-HEADEREND:event_ChangeDepartmentActionPerformed

  
    JFrame frame = new JFrame(); // Create new frame
    frame.setBounds(100, 100, 450, 300); //set frame bounds
    frame.setResizable(false);  //set frame resizable false
    frame.setAlwaysOnTop(true); //set it in top
    frame.getContentPane().setLayout(null); //set its layout null
    
    JPanel panel = new JPanel(); //create new panel
    panel.setBackground(Color.decode("#F5F5F5")); //set its background
    panel.setLayout(null);  //set its layout null
    
    //Declare new button named "add" 
    JButton add = new JButton("add");
    add.setBackground(Color.decode("#0081D3")); //set background color
    add.setForeground(Color.white); //set font color
    add.setBounds(95, 270, 85, 40); // set its bounds
    add.setFont(new java.awt.Font("VIP Rawy Regular", 0,
        16)); // set font

    //Declare new buttom named "save"
    JButton save = new JButton("save");
    save.setBackground(Color.decode("#0081D3"));   //set background color
    save.setForeground(Color.white); //set font color
    save.setBounds(200, 270, 85, 40); // set its bounds
    save.setFont(new java.awt.Font("VIP Rawy Regular", 0, 16)); // set font
    
    //Declare new buttom named "remove" 
    JButton remove = new JButton("remove");
    remove.setBackground(Color.decode("#0081D3")); //set background color
    remove.setForeground(Color.white); //set font color
    remove.setBounds(310, 270, 85, 40); // set its bounds
    remove.setFont(new java.awt.Font("VIP Rawy Regular", 0, 16)); // set font

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
    labelList1.setText("Available departments:");
    labelList1.setSize(170, 19);
    labelList1.setLocation(48, 20);
    labelList1.setFont(new java.awt.Font("VIP Rawy Regular", 0,
        16)); 
    labelList1.setForeground(Color.decode("#0081D3"));
    panel.add(labelList1); //add it to the panel

    //set labels for list2, set location,size,text,font,font color
    JLabel labelList2 = new JLabel();
    labelList2.setText("Selected departments:");
    labelList2.setSize(170, 19);
    labelList2.setLocation(265, 20);
    labelList2.setFont(new java.awt.Font("VIP Rawy Regular", 0,
        16)); 
    labelList2.setForeground(Color.decode("#0081D3"));
    panel.add(labelList2); //add it to the panel

    //get all existing departments from database
    DepartmentBao departBao = new BaoFactory().createDepartmentBao(user);
    List<DepartmentDto> departs = departBao.viewAll();

    // set all existing Departments' code to the first list
    for(int i = 0; i<departs.size() && departs!=null ; i++)
      {
        model_list1.addElement(departs.get(i).getCode());
      }

    /*get existing departments' code from combobox (if exist)
    * add them to second list
    * remove them from first list
    * */
    for(int i = 0; i<departCombo.getItemCount(); i++)
      {

        model_list2.addElement(departCombo.getItemAt(i));
        model_list1.removeElement(departCombo.getItemAt(i));

      }

    /* move all selected element to the secode list and remove it from first list
       when user click on add button */
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

    /* remove all selected element from the secode list and add it to first list
       when user click on remove button */
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
        departCombo.removeAllItems();

        //Save selected building into building combobox
        for(int i = 0; i<model_list2.size(); i++)
          {
            departCombo.addItem(model_list2.get(i).toString());
          }

        //close frame
        frame.dispose();
      }
    });


    //add buttons into the panel and set the frame size/title
    panel.add(add);
    panel.add(save);
    panel.add(remove);
    frame.setContentPane(panel); //add this panel to the frame
    frame.setLocationRelativeTo(null); //to make frame in screen center
    frame.setTitle("Choose course departments");
    frame.setSize(490, 400);

    //veiw the frame when user click on the change buttom
    frame.setVisible(true);
    
  }//GEN-LAST:event_ChangeDepartmentActionPerformed

  private void searchMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseMoved
  {//GEN-HEADEREND:event_searchMouseMoved
    
    // change search panel color 
    searchPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_searchMouseMoved

  private void RefreshMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseMoved
  {//GEN-HEADEREND:event_RefreshMouseMoved
   
    // change refresh panel color
    RefreshPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_RefreshMouseMoved

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

  private void ChangeDepartmentMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ChangeDepartmentMouseMoved
  {//GEN-HEADEREND:event_ChangeDepartmentMouseMoved
    
    // change change panel color
    ChangePanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_ChangeDepartmentMouseMoved

  private void RefreshMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_RefreshMouseExited
  {//GEN-HEADEREND:event_RefreshMouseExited
    
    // reset refresh panel color
    RefreshPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_RefreshMouseExited

  private void SaveMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseExited
  {//GEN-HEADEREND:event_SaveMouseExited
    
    // reset save panel color
    SavePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_SaveMouseExited

  private void DeleteMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteMouseExited
  {//GEN-HEADEREND:event_DeleteMouseExited
    
    // reset delete panel color
    DeletePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_DeleteMouseExited

  private void clearMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearMouseExited
  {//GEN-HEADEREND:event_clearMouseExited
    
    // reset clear panel color
    ClearPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_clearMouseExited

  private void ChangeDepartmentMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ChangeDepartmentMouseExited
  {//GEN-HEADEREND:event_ChangeDepartmentMouseExited
    
    // reset change panel color
    ChangePanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_ChangeDepartmentMouseExited

  private void searchMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseExited
  { //GEN-HEADEREND:event_searchMouseExited//GEN-LAST:event_searchMouseExited

    // reset search panel color
    searchPanel.setBackground(Color.decode("#0081D3"));

  }
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
    
    //define course excel object
    CourseExcel excel = new CourseExcel(user);
    List<CourseDto> courses = null; //to set export courses on it


    try
      {
        //get all courses in table
        for(int i = 0; i<courseTable.getRowCount(); i++)
          {
            if(courses==null)
              courses = new ArrayList<CourseDto>();

            //get each course attributes
            course = new CourseDto();
            course.setId(Integer.parseInt(courseTable.getValueAt(i,
              0).toString()));
            course.setCode(courseTable.getValueAt(i, 1).toString());
            course.setName(courseTable.getValueAt(i, 2).toString());
            course.setTerm(courseTable.getValueAt(i, 3).toString());
            course.setLec_time(Float.parseFloat(courseTable.getValueAt(i,
              4).toString()));
            course.setSec_time(Float.parseFloat(courseTable.getValueAt(i,
              5).toString()));
          
            //for plt_lecture
            course.setPlt_lecture(new LocationTypeDto());
            course.getPlt_lecture().setCode(courseTable.getValueAt(i,
              6).toString());

            //for plt_section
            course.setPlt_section(new LocationTypeDto());
            course.getPlt_section().setCode(courseTable.getValueAt(i,
              7).toString());

            //get departments text from table
            String[] departs =
              courseTable.getValueAt(i, 8).toString().trim().split(",");
            List<DepartmentDto> departments = null; //department list
            DepartmentDto department = null; //create department dto
          
            for(int j = 0; j<departs.length; j++)
              {
                if(departments==null)
                  departments = new ArrayList<>();
              
                //set new department and set its code
                department = new DepartmentDto();
                department.setCode(departs[j]);
                //add it to the list
                departments.add(department);
              
                department = null; //clear department object
              }

            course.setDeparts(departments); //add list to course object

            courses.add(course); //add this course to course list

            course = null; //clear course object

          }

        //create new file chooser
        JFileChooser fileChooser = new JFileChooser();
        //set current directory
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //set selection mode to directory only
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);


        //set filename to "Courses" as a default name
        String filename = "Courses.xlsx";

        //if this data result from search
        if(!SearchText.getText().equalsIgnoreCase("Enter text to search"))
          {
            //get the search text and change filename to inculde it
            filename = SearchText.getText()+"-coures.xlsx";
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
                excel.write(filename, filePath, courses); //write excel file

                //if it success show this message
                JOptionPane.showMessageDialog(null,
                  "Courses data export ended successfully!", "Done", 1);
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

    //define CourseExcel object
    CourseExcel read = new CourseExcel(user);

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
        List<CourseDto> import_courses = read.read_data(filePath);
        
        //if there is any result
        if(import_courses!=null)
        {

          //insert reading courses to database
          for (int i=0;  i<import_courses.size() ;i++)
          {
            course_bao.add(import_courses.get(i), user);
          }

          // show result set in table
          setTableModel(import_courses);
          courseTable.repaint();

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

  private void no_of_hours_secActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_no_of_hours_secActionPerformed
  {//GEN-HEADEREND:event_no_of_hours_secActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_no_of_hours_secActionPerformed

  private void departComboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_departComboActionPerformed
  {//GEN-HEADEREND:event_departComboActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_departComboActionPerformed

  private void no_of_hours_secFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_no_of_hours_secFocusGained
  {//GEN-HEADEREND:event_no_of_hours_secFocusGained
   
    //if found text is the default text
    if(no_of_hours_sec.getText().equalsIgnoreCase("Enter number of hours"))
      {
        no_of_hours_sec.setText(""); //remove it
      }

    //change border color
    no_of_hours_sec.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

  }//GEN-LAST:event_no_of_hours_secFocusGained

  private void no_of_hours_secFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_no_of_hours_secFocusLost
  {//GEN-HEADEREND:event_no_of_hours_secFocusLost
    
  //if there is no text
      if(no_of_hours_sec.getText().trim().equalsIgnoreCase(""))
        {
          //set the default one
          no_of_hours_sec.setText("Enter number of hours");
        }

      //reset border color
      no_of_hours_sec.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));
      
  }//GEN-LAST:event_no_of_hours_secFocusLost

  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton ChangeDepartment;
  private javax.swing.JPanel ChangePanel;
  private javax.swing.JPanel ClearPanel;
  private javax.swing.JTextField CodeText;
  private javax.swing.JButton Delete;
  private javax.swing.JPanel DeletePanel;
  private javax.swing.JPanel EditPanel;
  private javax.swing.JButton ExportButton;
  private javax.swing.JTextField IdText;
  private javax.swing.JButton ImportButton;
  private javax.swing.JTextField NameText;
  private javax.swing.JLabel Plt_lec_label;
  private javax.swing.JButton Refresh;
  private javax.swing.JPanel RefreshPanel;
  private javax.swing.JButton Save;
  private javax.swing.JPanel SavePanel;
  private javax.swing.JTextField SearchText;
  private javax.swing.JComboBox<String> TermCombo;
  private javax.swing.JButton clear;
  private javax.swing.JLabel code;
  private javax.swing.JTable courseTable;
  private javax.swing.JComboBox<String> departCombo;
  private javax.swing.JLabel departmentSectionImage;
  private javax.swing.JLabel id;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JSeparator jSeparator3;
  private javax.swing.JLabel name;
  private javax.swing.JLabel no_of_hours;
  private javax.swing.JTextField no_of_hours_lec;
  private javax.swing.JLabel no_of_hours_lecture;
  private javax.swing.JTextField no_of_hours_sec;
  private javax.swing.JLabel no_of_hours_section;
  private java.awt.Label no_of_rows;
  private javax.swing.JLabel plt;
  private javax.swing.JComboBox<String> plt_lecBox;
  private javax.swing.JLabel plt_sec;
  private javax.swing.JLabel plt_sec1;
  private javax.swing.JComboBox<String> plt_secBox;
  private javax.swing.JButton search;
  private javax.swing.JPanel searchPanel;
  private javax.swing.JLabel term;
  private javax.swing.JLabel titleLabel;
  // End of variables declaration//GEN-END:variables

}
