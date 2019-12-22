
package com.fym.lta.UI;

import com.fym.lta.BAO.BaoFactory;
import com.fym.lta.BAO.LocationTypeBao;
import com.fym.lta.DAO.DaoFactory;
import com.fym.lta.DAO.RoleScreenDao;
import com.fym.lta.DTO.LocationTypeDto;
import com.fym.lta.DTO.UserDto;
import com.fym.lta.Excel.LocationTypeExcel;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


/**
 *
 * @author Mai-AbdEltwab
 */

public class LocationTypePanel extends javax.swing.JPanel
{
  @SuppressWarnings({ "compatibility:7383884149032212571",
      "oracle.jdeveloper.java.serialversionuid-stale" })
  private static final long serialVersionUID = 1L;

  private static UserDto user = null;


  /** Creates new form LocationTypePanel */
  public LocationTypePanel(UserDto user_data)
  {

    user = user_data; //set user
    initComponents();

    /* UIManager.put("OptionPane.background", Color.white);
    UIManager.put("Panel.background", Color.white);
    UIManager.put("OptionPane.buttonAreaBorder", Color.white);
    UIManager.put("OptionPane.buttonFont", Font.decode("Trebuchet MS"));
    UIManager.put("OptionPane.font", Font.decode("Trebuchet MS")); */

   
    
    LocationTypeBao bao =
      new BaoFactory().createLocationTypeBao(user); //create LT bao object

    viewonly(user); //if user have view only access

    List<LocationTypeDto> types =
      new ArrayList<LocationTypeDto>(); //List of location types

    types =
      bao.viewAll(); //get all existing types from database through bao

    //view result data set in location type table
    setTableModel(types);
    
  }


  /**For view only user access */
  private void viewonly(UserDto u)
  {

    u.setScreen_name("Locaton type");

    RoleScreenDao dao = new DaoFactory().createRoleScreenDao(user);
    if(dao.viewonly(u))
      {
        EditPanel.setVisible(false);
        System.out.println(" \n done panel");
      }
  }

  /**Set Location type tabel model*/
  private void setTableModel(List<LocationTypeDto> types)
  {

    int s = 0; //for table size with default size

    //if list parameter is not null
    if(types!=null&&!types.isEmpty())
      {
        s = types.size(); //set table size as list size
      }


    //view data in table
    Object[][] TypeArr = new Object[s][4];
    for(int i = 0; i<s; i++)
      {
        TypeArr[i][0] = types.get(i).getId();
        TypeArr[i][1] = types.get(i).getCode();
        TypeArr[i][3] = types.get(i).getColor();
        TypeArr[i][2] = types.get(i).getDescription();
      }

    LTTable.setModel(new javax.swing.table.DefaultTableModel(TypeArr,
        new String[] { "Id", "Code", "Description", "Color" }));


    //change header color
    LTTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
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

    //table background
    // LTTable.setBackground(Color.decode("#E4E4E4"));

    //Set color to color cell in location type table
    LTTable.getColumnModel().getColumn(3).setCellRenderer(new TableCellRenderer()
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
          types.get(row).getColor(); //get location type color
        colorStr =
          "#".concat(colorStr); //convert it to color code "#ffffff"

        System.out.println(colorStr);
        comp.setBackground(Color.decode(colorStr.trim())); //set cell color
        return comp;
      }
    });

    //label under tabel show types count viewed in table
    no_of_rows.setText("Table result: "+
      Integer.toString(LTTable.getRowCount())+"  location types");
  }


  /**check Validity of entered data
   * @return: True if data is entered correctly (as expected)
   * False when one or more of them entered uncorrectly
   * */
  private boolean checkValidity()
  {
    try
      {

        //check for empty entered data
        if(IdText.getText().equalsIgnoreCase("Enter Location type ID"))
          {
            JOptionPane.showMessageDialog(null,
              "Please, enter location type id", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("id text is empry");
          }

        else if(CodeText.getText().equalsIgnoreCase("Enter Location type code"))

          {
            JOptionPane.showMessageDialog(null,
              "Please, enter location type code", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("code text is empty");
          }

        else if(ColorText.getText().equalsIgnoreCase("Enter Location type color (Hex)"))
          {
            JOptionPane.showMessageDialog(null,
              "Please, enter location type color", "Invalid Input",
              JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("color text is empty");
          }

        //Check for the entered id is a positive number
        int id = Integer.parseInt(IdText.getText());
        if(id<1)
          {
            JOptionPane.showMessageDialog(null,
              "Invalid Id \n\n(ID is only Positive Numbers)",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("ID is only Positive Numbers");
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


        //check validity of input color (must be only 6 hexadecimal code)
        String color = ColorText.getText();
        Character[] hexa =
        {
          '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
          'd', 'e', 'f'
        };
        for(int i = 0; i<color.length(); i++)
          {
            int j = 0;
            for(j = 0; j<hexa.length; j++)
              {
                if(color.charAt(i)==hexa[j])
                  break;
              }

            if((hexa[j]=='f'&&color.charAt(i)!='f')||color.length()>6)
              {
                JOptionPane.showMessageDialog(null,
                  "Invalid color code\n\n"+"(Enter hexa code)",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Invaild color code");
              }
          }

        return true; //all data correct

      }
    catch(java.lang.NumberFormatException e2) //occur when id isn't a number
      {
        e2.printStackTrace();
        JOptionPane.showMessageDialog(null, "Please enter number for ID",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);

      }

    //occur when color is out of bounds (hexa chars)
    catch(java.lang.ArrayIndexOutOfBoundsException e3)
      {

        e3.printStackTrace();
        JOptionPane.showMessageDialog(null, "Invalid color code",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
        return false;

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

    return false;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  private void initComponents()//GEN-BEGIN:initComponents
  {

    SearchText = new javax.swing.JTextField();
    jScrollPane1 = new javax.swing.JScrollPane();
    LTTable = new javax.swing.JTable();
    EditPanel = new javax.swing.JPanel();
    IdText = new javax.swing.JTextField();
    CodeText = new javax.swing.JTextField();
    ColorText = new javax.swing.JTextField();
    SavePanel = new javax.swing.JPanel();
    Save = new javax.swing.JButton();
    ColorPanel = new javax.swing.JPanel();
    ChangeColor = new javax.swing.JButton();
    ClearPanel = new javax.swing.JPanel();
    clear = new javax.swing.JButton();
    DeletePanel = new javax.swing.JPanel();
    Delete = new javax.swing.JButton();
    DesText = new javax.swing.JTextField();
    jLabel1 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    no_of_rows = new java.awt.Label();
    jScrollPane2 = new javax.swing.JScrollPane();
    jScrollPane3 = new javax.swing.JScrollPane();
    searchPanel = new javax.swing.JPanel();
    search = new javax.swing.JButton();
    RefreshPanel = new javax.swing.JPanel();
    Refresh = new javax.swing.JButton();
    jSeparator1 = new javax.swing.JSeparator();
    jLabel6 = new javax.swing.JLabel();
    ExportButton = new javax.swing.JButton();
    ImportButton = new javax.swing.JButton();

    setBackground(new java.awt.Color(245, 245, 245));
    setFont(search.getFont());
    setName(""); // NOI18N
    setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    SearchText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    SearchText.setText("What do you want to search for?");
    SearchText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    SearchText.setDoubleBuffered(true);
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
    add(SearchText, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 630, 50));

    jScrollPane1.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        jScrollPane1FocusGained(evt);
      }
    });

    LTTable.setAutoCreateRowSorter(true);
    LTTable.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    LTTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {

      },
      new String []
      {
        "Id", "Code", "Color", "Description"
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
        return false;
      }
    });
    LTTable.setToolTipText("");
    LTTable.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
    LTTable.setDoubleBuffered(true);
    LTTable.setFillsViewportHeight(true);
    LTTable.setRowHeight(25);
    LTTable.setSelectionBackground(new java.awt.Color(0, 153, 204));
    LTTable.setSurrendersFocusOnKeystroke(true);
    LTTable.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        LTTableFocusGained(evt);
      }
    });
    LTTable.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        LTTableMouseClicked(evt);
      }
    });
    LTTable.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyPressed(java.awt.event.KeyEvent evt)
      {
        LTTableKeyPressed(evt);
      }
    });
    jScrollPane1.setViewportView(LTTable);
    LTTable.getAccessibleContext().setAccessibleName("");

    add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 720, 760));

    EditPanel.setBackground(new java.awt.Color(245, 245, 245));
    EditPanel.setForeground(new java.awt.Color(255, 255, 255));
    EditPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    IdText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    IdText.setHorizontalAlignment(javax.swing.JTextField.LEFT);
    IdText.setText("Enter Location type ID");
    IdText.setToolTipText("");
    IdText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    IdText.setDisabledTextColor(new java.awt.Color(102, 102, 102));
    IdText.setDoubleBuffered(true);
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
    IdText.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        IdTextMouseClicked(evt);
      }
    });
    IdText.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        IdTextActionPerformed(evt);
      }
    });
    EditPanel.add(IdText, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 570, 70));

    CodeText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    CodeText.setHorizontalAlignment(javax.swing.JTextField.LEFT);
    CodeText.setText("Enter Location type code");
    CodeText.setToolTipText("");
    CodeText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    CodeText.setDoubleBuffered(true);
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
    EditPanel.add(CodeText, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 570, 70));

    ColorText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    ColorText.setHorizontalAlignment(javax.swing.JTextField.LEFT);
    ColorText.setText("Enter Location type color (Hex)");
    ColorText.setToolTipText("");
    ColorText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    ColorText.setDoubleBuffered(true);
    ColorText.addFocusListener(new java.awt.event.FocusAdapter()
    {
      public void focusGained(java.awt.event.FocusEvent evt)
      {
        ColorTextFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt)
      {
        ColorTextFocusLost(evt);
      }
    });
    ColorText.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        ColorTextActionPerformed(evt);
      }
    });
    EditPanel.add(ColorText, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 440, 70));

    SavePanel.setBackground(new java.awt.Color(0, 129, 211));
    SavePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    Save.setBackground(new java.awt.Color(0, 51, 153));
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
      public void mouseDragged(java.awt.event.MouseEvent evt)
      {
        SaveMouseDragged(evt);
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

    EditPanel.add(SavePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 630, 100, 50));

    ColorPanel.setBackground(new java.awt.Color(0, 129, 211));
    ColorPanel.setFocusCycleRoot(true);
    ColorPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    ChangeColor.setBackground(new java.awt.Color(0, 51, 153));
    ChangeColor.setFont(new java.awt.Font("VIP Rawy Regular", 0, 20)); // NOI18N
    ChangeColor.setForeground(new java.awt.Color(255, 255, 255));
    ChangeColor.setText("Change");
    ChangeColor.setBorderPainted(false);
    ChangeColor.setContentAreaFilled(false);
    ChangeColor.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        ChangeColorMouseMoved(evt);
      }
    });
    ChangeColor.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseExited(java.awt.event.MouseEvent evt)
      {
        ChangeColorMouseExited(evt);
      }
    });
    ChangeColor.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        ChangeColorActionPerformed(evt);
      }
    });
    ColorPanel.add(ChangeColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 50));

    EditPanel.add(ColorPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 420, 100, 50));

    ClearPanel.setBackground(new java.awt.Color(0, 129, 211));
    ClearPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    clear.setBackground(new java.awt.Color(0, 129, 211));
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

    EditPanel.add(ClearPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 630, 100, 50));

    DeletePanel.setBackground(new java.awt.Color(0, 129, 211));
    DeletePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    Delete.setBackground(new java.awt.Color(0, 51, 153));
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

    EditPanel.add(DeletePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 630, 100, 50));

    DesText.setFont(new java.awt.Font("AGA Cordoba V3 قرطبة", 0, 18)); // NOI18N
    DesText.setText("Enter Location type Description");
    DesText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
    DesText.setDoubleBuffered(true);
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
    EditPanel.add(DesText, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 570, 70));

    jLabel1.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(0, 51, 204));
    jLabel1.setText(" Description");
    EditPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 110, 30));

    jLabel3.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(0, 51, 204));
    jLabel3.setText(" ID");
    EditPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 70, -1));

    jLabel4.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    jLabel4.setForeground(new java.awt.Color(0, 51, 204));
    jLabel4.setText(" Code");
    EditPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 70, 30));

    jLabel5.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(0, 51, 204));
    jLabel5.setText(" Color");
    EditPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 70, 30));

    jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_geo_fence_128px.png"))); // NOI18N
    EditPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 140, 130));

    add(EditPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 150, 640, 770));

    no_of_rows.setBackground(new java.awt.Color(245, 245, 245));
    no_of_rows.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
    no_of_rows.setForeground(new java.awt.Color(135, 135, 135));
    no_of_rows.setText("no of rows");
    add(no_of_rows, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 870, 200, 20));
    add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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

    search.setBackground(new java.awt.Color(0, 129, 211));
    search.setFont(new java.awt.Font("VIP Rawy Regular", 0, 18)); // NOI18N
    search.setForeground(new java.awt.Color(255, 255, 255));
    search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fym/lta/Images/icons8_search_26px.png"))); // NOI18N
    search.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
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
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        searchMouseClicked(evt);
      }
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
    searchPanel.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 50));

    add(searchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 40, 80, 50));

    RefreshPanel.setBackground(new java.awt.Color(0, 129, 211));
    RefreshPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    Refresh.setBackground(new java.awt.Color(51, 51, 51));
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

    add(RefreshPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 880, 130, 50));
    add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 120, 600, 20));

    jLabel6.setBackground(new java.awt.Color(231, 78, 123));
    jLabel6.setFont(new java.awt.Font("VIP Rawy Regular", 0, 36)); // NOI18N
    jLabel6.setForeground(new java.awt.Color(231, 78, 123));
    jLabel6.setText("Location Type");
    add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 60, 240, 60));

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
    add(ExportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 870, 80, 70));

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
    add(ImportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 870, 80, 70));
  }//GEN-END:initComponents

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed

    RefreshPanel.setBackground(Color.decode("#007BFF"));

    // for text boxes
    //enable text boxes again
    IdText.setEnabled(true);
    Save.setText("Save");

    //set default text for text boxes
    IdText.setText("Enter Location type ID");
    CodeText.setText("Enter Location type code");
    ColorText.setText("Enter Location type color (Hex)");
    DesText.setText("Enter Location type Description");

    LocationTypeBao bao = new BaoFactory().createLocationTypeBao(user);
    // view all data again
    List<LocationTypeDto> types = bao.viewAll();
    setTableModel(types);
    }//GEN-LAST:event_RefreshActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed

    searchPanel.setBackground(Color.decode("#007BFF"));


    try
      {

        LocationTypeDto dto = new LocationTypeDto();
        LocationTypeBao bao = new BaoFactory().createLocationTypeBao(user);

        // Check if the Search field is empty
        if(SearchText.getText().equalsIgnoreCase("What do you want to search for?"))
          {
            //Show message tell user that Search Field is empty
            JOptionPane.showMessageDialog(null, "Search field is empty",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
            //throw exception
            throw new IllegalArgumentException("Search field is empty");
          }


        //take search text int dto object
        dto.setSearch(SearchText.getText());

        //Pick up result set from database through search bao method
        List<LocationTypeDto> result = new ArrayList<LocationTypeDto>();
        result = bao.searchFor(dto);

        //if search results are found, Show result set in table
        if(result!=null&&!result.isEmpty())
          {
            setTableModel(result);
            LTTable.repaint();
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
        // TODO: Add catch code
        e.printStackTrace();

      }

    }//GEN-LAST:event_searchActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
    // TODO add your handling code here:

    ClearPanel.setBackground(Color.decode("#007BFF"));

    //enable text boxes again
    IdText.setEnabled(true);
    Save.setText("Save");

    //set default text for text boxes
    IdText.setText("Enter Location type ID");
    CodeText.setText("Enter Location type code");
    ColorText.setText("Enter Location type color (Hex)");
    DesText.setText("Enter Location type Description");

    }//GEN-LAST:event_clearActionPerformed

    private void ColorTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColorTextActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_ColorTextActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed

    SavePanel.setBackground(Color.decode("#007BFF"));

    if(Save.getText().equalsIgnoreCase("Update"))
      update();

    else
      {

        try
          {

            LocationTypeDto dto =
              new LocationTypeDto(); //create location type dto object
            LocationTypeBao bao =
              new BaoFactory().createLocationTypeBao(user); //create location type bao object

            if(checkValidity()) //check data validation
              //if true
              {

                //set attributes values for location type object
                dto.setId(Integer.parseInt(IdText.getText())); //get id
                dto.setCode(CodeText.getText()); //get code
                dto.setColor(ColorText.getText()); //get color

                //if description text field is empty set description to null
                if(DesText.getText().equalsIgnoreCase("Enter Location type Description"))
                  dto.setDescription("");
                else
                  dto.setDescription(DesText.getText()); //get entered description

                boolean flag;
                flag = bao.add(dto, user); //try add it

                //if location inserted successfully
                if(flag==true)
                  {

                    //show message tell user that
                    JOptionPane.showMessageDialog(null,
                      "Location type has inserted successfully!", "Done",
                      1);

                    // refresh tabel
                    setTableModel(bao.viewAll());
                    LTTable.repaint();

                    IdText.setEnabled(false); // make id uneditable
                    Save.setText("Update");

                  }
                else
                  {

                    int reply =
                      JOptionPane.showConfirmDialog(null,
                        "This location type is already exist!\n\n"+
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
            // TODO: Add catch code
            e.printStackTrace();
          }
      }

    }//GEN-LAST:event_SaveActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed


    DeletePanel.setBackground(Color.decode("#007BFF"));

    //Show confirm message
    int reply =
      JOptionPane.showConfirmDialog(null,
      "Are you sure to delete this Location type?\n"+
      "All things depend on it will be deleted!", "Warning",
      JOptionPane.YES_NO_OPTION);


    //delete object if user choose yes
    if(reply==JOptionPane.YES_OPTION)
      {

        try
          {
            LocationTypeDto dto = new LocationTypeDto();
            LocationTypeBao bao =
              new BaoFactory().createLocationTypeBao(user);

            dto.setId(Integer.parseInt(IdText.getText()));

            //go to business layer
            boolean flag = bao.delete(dto);

            /*if object has deleted successfully
             * show message to tell user this
             * repait the table
             * make id text box enabled
             * */
            if(flag==true)
              {
                JOptionPane.showMessageDialog(null,
                  "Location Type has deleted successfully!", "Done", 1);

                //refresh table
                setTableModel(bao.viewAll());
                LTTable.repaint();
                IdText.setEnabled(true); //set id tect enabled again
                Save.setText("Save");

              }

            //else show message to tell user that this object does't exist in database
            else
              JOptionPane.showMessageDialog(null,
                "Failed delete type dependents \nOr, This type may doesn't exist!",
                "Not Found", JOptionPane.ERROR_MESSAGE);
          }
        catch(Exception e)
          {
            // TODO: Add catch code
            e.printStackTrace();
            //for non expected error
            JOptionPane.showMessageDialog(null,
              "Some Thing went wrong!\nPlease check your entered data. ",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);

          }
      }
    }//GEN-LAST:event_DeleteActionPerformed

    private void ChangeColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangeColorActionPerformed

    ColorPanel.setBackground(Color.decode("#007BFF"));

    // define new color chooser
    JColorChooser Change = new JColorChooser();

    // HIDE ALL DEFAULT PANELS AND SHOW RGB AND SWATCHES PANELS ONLY
    AbstractColorChooserPanel[] panels = Change.getChooserPanels();
    for(AbstractColorChooserPanel p : panels)
      {
        String displayName = p.getDisplayName();
        switch(displayName)
          {
            case "HSV":
              Change.removeChooserPanel(p);
              break;
            case "HSL":
              Change.removeChooserPanel(p);
              break;
            case "CMYK":
              Change.removeChooserPanel(p);
              break;
            case "Swatches":
              Change.removeChooserPanel(p);
          }
      }

    //Declare new buttom named "save" and set its bounds
    JButton SaveColor = new JButton("Save");
    SaveColor.setBounds(50, 100, 60, 30);

    // Create new frame and panel
    JFrame chooseColorFrame = new JFrame();
    JPanel panel = new JPanel();

    /*Close the choose color frame
         * pickup the cooched color to the color tect
         * when click on save*/

    SaveColor.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        //Pick up the RGB color choosed by user
        String getcolor = Integer.toHexString(Change.getColor().getRGB());
        char[] setcolor =
        {
          '1', '2', '3', '4', '5', '6'
        };
        for(int i = 2; i<8; i++)
          setcolor[i-2] = getcolor.charAt(i);
        String s = new String(setcolor);
        ColorText.setText(s);
        chooseColorFrame.dispose();
      }
    });

    // add the color chooser and sace buttom on the panel
    panel.setLayout(new FlowLayout());
    panel.add(Change);
    panel.add(SaveColor);

    //add the panel into the frame and set the frame size/title
    chooseColorFrame.add(panel);
    chooseColorFrame.setTitle("Choose Location type color");
    chooseColorFrame.setSize(680, 450);
    chooseColorFrame.setResizable(false);

    //veiw the frame when user click on the change buttom
    chooseColorFrame.setResizable(false);
    chooseColorFrame.setLocationRelativeTo(null); //to make frame in screen center
    chooseColorFrame.setAlwaysOnTop(true);
    chooseColorFrame.setVisible(true); //view the screen
    }//GEN-LAST:event_ChangeColorActionPerformed

    private void IdTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdTextActionPerformed
     
    }//GEN-LAST:event_IdTextActionPerformed

    private void IdTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IdTextMouseClicked
      
    }//GEN-LAST:event_IdTextMouseClicked

    private void IdTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusGained
    if(IdText.getText().equalsIgnoreCase("Enter Location type ID"))
      IdText.setText("");

    IdText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_IdTextFocusGained

    private void IdTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IdTextFocusLost
    if(IdText.getText().trim().equalsIgnoreCase(""))
      IdText.setText("Enter Location type ID");

    IdText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_IdTextFocusLost

    private void CodeTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodeTextFocusGained
    if(CodeText.getText().equalsIgnoreCase("Enter Location type code"))
      CodeText.setText("");

    CodeText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_CodeTextFocusGained

    private void CodeTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CodeTextFocusLost
    if(CodeText.getText().trim().equalsIgnoreCase(""))
      CodeText.setText("Enter Location type code");

    CodeText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_CodeTextFocusLost

    private void ColorTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ColorTextFocusLost
    if(ColorText.getText().equalsIgnoreCase(""))
      ColorText.setText("Enter Location type color (Hex)");

    ColorText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

    }//GEN-LAST:event_ColorTextFocusLost

    private void ColorTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ColorTextFocusGained
    if(ColorText.getText().equalsIgnoreCase("Enter Location type color (Hex)"))
      ColorText.setText("");

    ColorText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    }//GEN-LAST:event_ColorTextFocusGained

    private void SearchTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusGained
    if(SearchText.getText().equalsIgnoreCase("What do you want to search for?"))
      SearchText.setText("");

    SearchText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

    
    }//GEN-LAST:event_SearchTextFocusGained

    private void SearchTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchTextFocusLost
    if(SearchText.getText().equalsIgnoreCase(""))
      SearchText.setText("What do you want to search for?");

    SearchText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));
    }//GEN-LAST:event_SearchTextFocusLost

    private void jScrollPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane1FocusGained
        
    }//GEN-LAST:event_jScrollPane1FocusGained

    private void LTTableFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_LTTableFocusGained
        
        
    }//GEN-LAST:event_LTTableFocusGained

    private void LTTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LTTableMouseClicked
    int i = LTTable.getSelectedRow();
    //unable to edit id for selected item
    IdText.setEnabled(false);
    Save.setText("Update");
    //get values from table to text boxes
    IdText.setText(LTTable.getValueAt(i, 0).toString());
    CodeText.setText(LTTable.getValueAt(i, 1).toString());
    ColorText.setText(LTTable.getValueAt(i, 3).toString());
    DesText.setText(LTTable.getValueAt(i, 2).toString());
    }//GEN-LAST:event_LTTableMouseClicked

    private void LTTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LTTableKeyPressed
       
    int i = LTTable.getSelectedRow();


    //Because "LTTable.getSelectedRow()" doesn't give the correct selected row
    if(evt.getExtendedKeyCode()==KeyEvent.VK_UP)
      i--; //for up key decrement
    else if(evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)
      i++; // for down key increment
    try
      {
        //move up and down in table to get data into edit space
        if(evt.getExtendedKeyCode()==KeyEvent.VK_UP||
          evt.getExtendedKeyCode()==KeyEvent.VK_DOWN)


          { //unable to edit id for selected item
            IdText.setEnabled(false);
            Save.setText("Update");
            // set the attributes of selected row to text boxes
            IdText.setText(LTTable.getValueAt(i, 0).toString());
            CodeText.setText(LTTable.getValueAt(i, 1).toString());
            ColorText.setText(LTTable.getValueAt(i, 3).toString());
            DesText.setText(LTTable.getValueAt(i, 2).toString());
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
            if(LTTable.getSelectedRowCount()==1)
              {


                //Show confirm message
                int reply =
                  JOptionPane.showConfirmDialog(null,
                    "Are you sure to delete this Location type?\n"+
                    "All things depend on it will be deleted!", "Warning",
                    JOptionPane.YES_NO_OPTION);


                //delete object if user choose yes
                if(reply==JOptionPane.YES_OPTION)
                  {
                    LocationTypeDto location =
                      new LocationTypeDto(); //create dto location type object
                    LocationTypeBao bao =
                      new BaoFactory().createLocationTypeBao(user); //create object location type bao

                    //get selected location type id from table
                    int s =
                      Integer.parseInt(LTTable.getValueAt(LTTable.getSelectedRow(),
                          0).toString());
                    location.setId(s); //set it to location type object

                    //delete it using bao delete method
                    if(bao.delete(location)) //if it deleted sucessfilly show message to tell user that
                      {
                        JOptionPane.showMessageDialog(null,
                          "Location Type Deleted Successfully");
                        setTableModel(bao.viewAll());
                        LTTable.repaint();
                        Save.setText("Save");

                      }

                    else
                      //if bao method return false (location type not be deleted)
                      JOptionPane.showMessageDialog(null,
                        "Failed delete this type dependents.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                  }

              }
            else if(LTTable.getSelectedRowCount()==0)
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
        
        
    }//GEN-LAST:event_LTTableKeyPressed

    private void SearchTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchTextKeyPressed
    if(evt.getExtendedKeyCode()==KeyEvent.VK_ENTER)
      {

        try
          {

            LocationTypeDto dto = new LocationTypeDto();
            LocationTypeBao bao =
              new BaoFactory().createLocationTypeBao(user);

            // Check if the Search field is empty
            if(SearchText.getText().equalsIgnoreCase(""))
              {
                //Show message tell user that Search Field is empty
                JOptionPane.showMessageDialog(null, "Search field is empty",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
                //throw exception
                throw new IllegalArgumentException("Search field is empty");
              }


            //take search text int dto object
            dto.setSearch(SearchText.getText());

            //Pick up result set from database through search bao method
            List<LocationTypeDto> result = new ArrayList<LocationTypeDto>();
            result = bao.searchFor(dto);

            //if search results are found, Show result set in table
            if(result!=null&&!result.isEmpty())
              {
                setTableModel(result);
                LTTable.repaint();
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
            // TODO: Add catch code
            e.printStackTrace();

          }
      }
        
    }//GEN-LAST:event_SearchTextKeyPressed

  private void SearchTextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SearchTextActionPerformed
  {//GEN-HEADEREND:event_SearchTextActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_SearchTextActionPerformed

  private void DesTextFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_DesTextFocusGained
  {//GEN-HEADEREND:event_DesTextFocusGained
    // TODO add your handling code here:
    if(DesText.getText().equalsIgnoreCase("Enter Location type Description"))
      DesText.setText("");

    DesText.setBorder(new LineBorder(Color.decode("#0081D3"), 2));

  }//GEN-LAST:event_DesTextFocusGained

  private void DesTextFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_DesTextFocusLost
  {//GEN-HEADEREND:event_DesTextFocusLost
    // TODO add your handling code here:
    if(DesText.getText().equalsIgnoreCase(""))
      DesText.setText("Enter Location type Description");

    DesText.setBorder(new LineBorder(Color.decode("#CCCCCC"), 2));

  }//GEN-LAST:event_DesTextFocusLost

  private void searchPanelMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchPanelMouseMoved
  {//GEN-HEADEREND:event_searchPanelMouseMoved
    // TODO add your handling code here:
    
  }//GEN-LAST:event_searchPanelMouseMoved

  private void searchPanelMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchPanelMouseExited
  {//GEN-HEADEREND:event_searchPanelMouseExited
    // TODO add your handling code here:
   
  }//GEN-LAST:event_searchPanelMouseExited

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

  private void searchMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchMouseClicked
  {//GEN-HEADEREND:event_searchMouseClicked
    // TODO add your handling code here:
    

  }//GEN-LAST:event_searchMouseClicked

  private void ChangeColorMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ChangeColorMouseMoved
  {//GEN-HEADEREND:event_ChangeColorMouseMoved
    // TODO add your handling code here:
    ColorPanel.setBackground(Color.decode("#0051D2"));

  }//GEN-LAST:event_ChangeColorMouseMoved

  private void SaveMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SaveMouseDragged
  {//GEN-HEADEREND:event_SaveMouseDragged
    // TODO add your handling code here:
  }//GEN-LAST:event_SaveMouseDragged

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

  private void ChangeColorMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ChangeColorMouseExited
  {//GEN-HEADEREND:event_ChangeColorMouseExited
    // TODO add your handling code here:
    ColorPanel.setBackground(Color.decode("#0081D3"));

  }//GEN-LAST:event_ChangeColorMouseExited

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
    LocationTypeExcel excel = new LocationTypeExcel(user);
    List<LocationTypeDto> types = null;
    LocationTypeDto type = null;

    try
    {
      //get all buildings in table

      for(int i=0; i<LTTable.getRowCount() ; i++)
      {
        if(types==null)
        types = new ArrayList<>();

        type = new LocationTypeDto();
        
        type.setId(Integer.parseInt(LTTable.getValueAt(i, 0).toString()));
        type.setCode(LTTable.getValueAt(i, 1).toString());
        type.setDescription(LTTable.getValueAt(i, 2).toString());
        type.setColor(LTTable.getValueAt(i, 3).toString());

        types.add(type);

        type=null;

      }

      //create new file chooser
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      fileChooser.setAcceptAllFileFilterUsed(false);

      //set file name
      String filename = "Location_types.xlsx";

      if(!SearchText.getText().equalsIgnoreCase("What do you want to search for?"))
      filename = SearchText.getText()+ "-Location_types.xlsx";
      //show file dialog
      int result = fileChooser.showSaveDialog(null);

      //user selected file
      if(result==JFileChooser.APPROVE_OPTION)
      {
        //get path
        String filePath =
        fileChooser.getSelectedFile().getAbsolutePath();

        excel.write(filename, filePath, types);

        //write excel file
        try
        {

          JOptionPane.showMessageDialog(null,
            "Location types data export ended successfully!", "Done", 1);
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

    LocationTypeExcel read = new LocationTypeExcel(user);
    LocationTypeBao bao = new BaoFactory().createLocationTypeBao(user);
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

        List<LocationTypeDto> import_locations_type = read.read_data(filePath);

        if(import_locations_type!=null)
        {

          //insert reading building to database
          for (int i=0;  i<import_locations_type.size() ;i++)
          {
            bao.add(import_locations_type.get(i), user);
          }

          // show result set in table
          setTableModel(import_locations_type);
          LTTable.repaint();

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


  //update an existing location type
  private void update()
  {

    // TODO add your handling code here:

    try
      {
        if(checkValidity()) //check data validation

          {
            LocationTypeDto dto =
              new LocationTypeDto(); //create location type dto object
            LocationTypeBao bao =
              new BaoFactory().createLocationTypeBao(user); //create bao object


            //set location type dto object attributes
            dto.setId(Integer.parseInt(IdText.getText())); //get id
            dto.setCode(CodeText.getText()); // get code
            dto.setColor(ColorText.getText()); // get color

            //if description text field is empty set description to null
            if(DesText.getText().equalsIgnoreCase("Enter Location type Description"))
              dto.setDescription("");
            else
              dto.setDescription(DesText.getText()); //get entered description


            //go to business layer
            boolean flag = bao.update(dto, user);

            /*if object has updated successfully
           * show message to tell user this
           * repait the table
           * */
            if(flag==true)
              {
                JOptionPane.showMessageDialog(null,
                  "Location Type has updated successfully!", "Done", 1);
                setTableModel(bao.viewAll());
                LTTable.repaint();
              }

            //else show message to tell user that this object does't exist in database
            else
              JOptionPane.showMessageDialog(null,
                "Location Type doesn't exist!", "Not Found",
                JOptionPane.ERROR_MESSAGE);

          }
      }
    catch(Exception e)
      {
        // TODO: Add catch code
        e.printStackTrace();
      }

  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton ChangeColor;
  private javax.swing.JPanel ClearPanel;
  private javax.swing.JTextField CodeText;
  private javax.swing.JPanel ColorPanel;
  private javax.swing.JTextField ColorText;
  private javax.swing.JButton Delete;
  private javax.swing.JPanel DeletePanel;
  private javax.swing.JTextField DesText;
  private javax.swing.JPanel EditPanel;
  private javax.swing.JButton ExportButton;
  private javax.swing.JTextField IdText;
  private javax.swing.JButton ImportButton;
  private javax.swing.JTable LTTable;
  private javax.swing.JButton Refresh;
  private javax.swing.JPanel RefreshPanel;
  private javax.swing.JButton Save;
  private javax.swing.JPanel SavePanel;
  private javax.swing.JTextField SearchText;
  private javax.swing.JButton clear;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JSeparator jSeparator1;
  private java.awt.Label no_of_rows;
  private javax.swing.JButton search;
  private javax.swing.JPanel searchPanel;
  // End of variables declaration//GEN-END:variables

}


