//------------------------------------------------------------------------------
// Filename:  ContactInfo.java
// Date:      25 May 2001
// Course:    CS3773-1
// Project#:  2
// Compiler:  JBuilder 4.0
//------------------------------------------------------------------------------

/**
 * Title:        CS3773 Project # 2
 * Description:  Battlefield Situational Display Project
 * Copyright:    Copyright (c) 2001
 * Company:      NPS, U.S. Army, U.S. Navy
 * @author Keith M. Perkins, Stephen O. Ulate, Robert J. Michael
 * @version 1.2.12 (at least)
 */

//package battlespace;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;
import java.sql.Time;
import cs3773.*;

public class ContactInfo extends JFrame {

   Contact c;
   ButtonGroup ContactIFF = new ButtonGroup();
   ButtonGroup ContactTyp = new ButtonGroup();
   JRadioButton unknown = new JRadioButton();
   JRadioButton friendly = new JRadioButton();
   JRadioButton neutral = new JRadioButton();
   JRadioButton hostile = new JRadioButton();
   JRadioButton air = new JRadioButton();
   JRadioButton sea = new JRadioButton();
   JRadioButton land = new JRadioButton();
   JRadioButton unknownType = new JRadioButton();
   JButton OK = new JButton();
   ButtonGroup CloseWindow = new ButtonGroup();
   JButton cancel = new JButton();
   JLabel ctcIFF = new JLabel();
   JLabel ctcType = new JLabel();
   JPanel contract_ID = new JPanel();
   JPanel contactType = new JPanel();
   JPanel contactInformation = new JPanel();
   JLabel ctcInfo = new JLabel();
   JPanel selectorBox = new JPanel();
   JLabel cse = new JLabel();
   JLabel spd = new JLabel();
   JLabel alt = new JLabel();
   JTextField ctcSpeed = new JTextField();
   JTextField ctcAltitude = new JTextField();
   JTextField ctcCourse = new JTextField();
   JLabel ctcName = new JLabel();
   JTextField ctcNameField = new JTextField();
   JPanel jPanel1 = new JPanel();
   JLabel ctcPosit = new JLabel();
   JLabel tim = new JLabel();
   JLabel ctcLat = new JLabel();
   JLabel ctcLong = new JLabel();
   JTextField ctcTimeHr = new JTextField();
   JTextField ctcTimeMin = new JTextField();
   JLabel jLabel1 = new JLabel();
   JLabel jLabel2 = new JLabel();
   JTextField ctcTimeSec = new JTextField();
   JLabel jLabel3 = new JLabel();
   JTextField ctcDegreeLatitude = new JTextField();
   JTextField ctcDegreeLongitude = new JTextField();
   JLabel ctcDegLat = new JLabel();
   JLabel ctcDegLong = new JLabel();
   JTextField ctcMinuteLatitude = new JTextField();
   JTextField ctcMinuteLongitude = new JTextField();
   JLabel ctcMinLat = new JLabel();
   JLabel ctcMinLong = new JLabel();
   BorderLayout borderLayout1 = new BorderLayout();
   FlowLayout flowLayout1 = new FlowLayout();
   JTextPane jTextPane2 = new JTextPane();
   JTextField iffCodeInput = new JTextField();
   JLabel iffCode = new JLabel();
   JTextField typeInput = new JTextField();
   JLabel typeCtc = new JLabel();
   JComboBox latComboBox = new JComboBox();
   JComboBox longComboBox = new JComboBox();
   FlowLayout flowLayout3 = new FlowLayout();
   JTextPane jTextPane1 = new JTextPane();
   GridBagLayout gridBagLayout2 = new GridBagLayout();

   //####### - Variables for the new VEHICLE / CONTACT - #######
double contactLatitude = 0;
int contactDegLatitude = 0;
double contactMinLatitude = 0;
double contactLongitude = 0;
int contactDegLongitude = 0;
double contactMinLongitude = 0;
double contactAltitude;
double V1;//Course
double V2;//Speed
String T1 = new String("");//for the contact type
String C1 = new String("UNKNOWN");//for the classification
String N1 = new String("");//for the contact name
String I1 = new String("UNKNOWN");//for the contact IFF category
String IC = new String("");//for the contact IFF code
java.util.Date newContactTime = new java.util.Date();
GridBagLayout gridBagLayout1 = new GridBagLayout();
private boolean validInput = true;


//####### -begin- Constructor for ContactInfo Class (new VEHICLE / CONTACT) -begin- #######
/**
 * Creates a new contact input dialog
 * @param contact The contact to be created from this dialog
 */
   public ContactInfo (Contact contact) {
      super("Enter New Contact ");
      setSize (450, 350);
      setLocation(225,225);
      JPanel p = new JPanel();
      c = contact;

      setContentPane(p);
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
      show();
   }

   private void jbInit() throws Exception {

//####### - begin CONTACT IFF button group - #######
      this.getContentPane().setLayout(borderLayout1);

      unknown.setPreferredSize(new Dimension(77, 40));
      unknown.setMinimumSize(new Dimension(77, 25));
      unknown.setSelected(true);
      unknown.setText("Unknown");
      unknown.setBackground(Color.yellow);
      unknown.setMaximumSize(new Dimension(77, 55));
      unknown.setBounds(new Rectangle(11, 56, 77, 40));
      unknown.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            unknown_actionPerformed(e);
         }
      });
      friendly.setPreferredSize(new Dimension(77, 40));
      friendly.setMinimumSize(new Dimension(77, 25));
      friendly.setMaximumSize(new Dimension(77, 45));
      friendly.setBounds(new Rectangle(11, 99, 77, 40));
      friendly.setText("Friendly");
      friendly.setBackground(Color.blue);
      friendly.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            friendly_actionPerformed(e);
         }
      });
      neutral.setPreferredSize(new Dimension(77, 40));
      neutral.setMinimumSize(new Dimension(77, 25));
      neutral.setText("Neutral");
      neutral.setBackground(Color.white);
      neutral.setMaximumSize(new Dimension(77, 55));
      neutral.setBounds(new Rectangle(11, 142, 77, 40));
      neutral.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
           neutral_actionPerformed(e);
         }
      });
      hostile.setPreferredSize(new Dimension(77, 40));
      hostile.setMinimumSize(new Dimension(77, 25));
      hostile.setText("Hostile");
      hostile.setBackground(Color.red);
      hostile.setMaximumSize(new Dimension(64, 55));
      hostile.setBounds(new Rectangle(10, 186, 77, 40));
      hostile.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            hostile_actionPerformed(e);
         }
      });
//####### - begin CONTACT TYPE button group - #######
      air.setPreferredSize(new Dimension(40, 25));
      air.setText("Air");
      air.setHorizontalAlignment(SwingConstants.CENTER);
      air.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            air_actionPerformed(e);
         }
      });
      sea.setPreferredSize(new Dimension(70, 25));
      sea.setMinimumSize(new Dimension(88, 25));
      sea.setText("Sea");
      sea.setHorizontalAlignment(SwingConstants.TRAILING);
      sea.setMaximumSize(new Dimension(88, 25));
      sea.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            sea_actionPerformed(e);
         }
      });
      land.setPreferredSize(new Dimension(78, 25));
      land.setText("Land");
      land.setHorizontalAlignment(SwingConstants.CENTER);
      land.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            land_actionPerformed(e);
         }
      });
      unknownType.setText("Unknown");
      unknownType.setSelected(true);
      unknownType.setHorizontalAlignment(SwingConstants.TRAILING);
      unknownType.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            unknownType_actionPerformed(e);
         }
      });

//####### - end CONTACT TYPE button group - #######
//############################################################################
//####### - begin - SELECTOR button group ActionListeners - begin - #######

      OK.setNextFocusableComponent(cancel);
      OK.setText("OK");
      OK.addKeyListener(new java.awt.event.KeyAdapter() {
         public void keyTyped(KeyEvent e) {
            OK_keyTyped(e);
         }
      });
      OK.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed (ActionEvent e) {
            OK_actionPerformed(e);
         }
      });
      cancel.setText("Cancel");
      cancel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            cancel_actionPerformed(e);
         }
      });
//####### - end - SELECTOR button group ActionListeners - end - #######
//############################################################################
//############################################################################

      ctcIFF.setBackground(Color.white);
      ctcIFF.setBorder(BorderFactory.createLineBorder(Color.black));
      ctcIFF.setMaximumSize(new Dimension(70, 30));
      ctcIFF.setPreferredSize(new Dimension(70, 30));
      ctcIFF.setDisplayedMnemonic('0');
      ctcIFF.setHorizontalAlignment(SwingConstants.CENTER);
      ctcIFF.setText("Contact IFF");
      ctcIFF.setBounds(new Rectangle(14, 7, 70, 30));
      ctcType.setBorder(BorderFactory.createLineBorder(Color.black));
      ctcType.setText("Contact Class");
      contactType.setLayout(flowLayout1);
      contactType.setBorder(BorderFactory.createEtchedBorder());
      contactType.setPreferredSize(new Dimension(366, 35));
      contract_ID.setBorder(BorderFactory.createEtchedBorder());
      contract_ID.setNextFocusableComponent(ctcPosit);
      contract_ID.setPreferredSize(new Dimension(100, 100));
      contract_ID.setLayout(null);
      contactInformation.setBorder(BorderFactory.createEtchedBorder());
      contactInformation.setMinimumSize(new Dimension(100, 225));
      contactInformation.setNextFocusableComponent(cancel);
      contactInformation.setPreferredSize(new Dimension(140, 200));
      contactInformation.setLayout(gridBagLayout1);
      ctcInfo.setBorder(BorderFactory.createLineBorder(Color.black));
      ctcInfo.setHorizontalAlignment(SwingConstants.CENTER);
      ctcInfo.setText("Contact Information");
      cse.setText("Course:");
      spd.setText("Speed:");
      alt.setText("Altitude:");
      ctcName.setHorizontalAlignment(SwingConstants.CENTER);
      ctcName.setText("Contact Name:");
      ctcNameField.setNextFocusableComponent(OK);
      ctcNameField.setHorizontalAlignment(SwingConstants.CENTER);

      ctcPosit.setBorder(BorderFactory.createLineBorder(Color.black));
      ctcPosit.setHorizontalAlignment(SwingConstants.CENTER);
      ctcPosit.setText("Contact Time & Position");
      jPanel1.setBorder(BorderFactory.createEtchedBorder());
      jPanel1.setMinimumSize(new Dimension(100, 150));
      jPanel1.setNextFocusableComponent(contactInformation);
      jPanel1.setPreferredSize(new Dimension(100, 200));
      jPanel1.setLayout(gridBagLayout2);
      tim.setHorizontalAlignment(SwingConstants.LEFT);
      tim.setText("Time:");
      ctcLat.setHorizontalAlignment(SwingConstants.LEFT);
      ctcLat.setText("Latitude:");
      ctcLong.setHorizontalAlignment(SwingConstants.LEFT);
      ctcLong.setText("Longitude:");
      jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
      jLabel1.setText("hr.");
      jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
      jLabel2.setText("min.");
      jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
      jLabel3.setText("sec");
      ctcDegLat.setText("deg.");
      ctcDegLong.setText("deg");
      ctcMinLat.setText("min");
      ctcMinLong.setText("min");
      selectorBox.setPreferredSize(new Dimension(139, 35));
      ctcTimeMin.setMinimumSize(new Dimension(3, 21));
      ctcTimeMin.setPreferredSize(new Dimension(3, 21));
      ctcTimeMin.setHorizontalAlignment(SwingConstants.CENTER);
      ctcTimeHr.setHorizontalAlignment(SwingConstants.CENTER);
      jTextPane2.setPreferredSize(new Dimension(200, 30));
      jTextPane2.setBorder(BorderFactory.createEtchedBorder());
      jTextPane2.setEditable(false);
      jTextPane2.setForeground(Color.red);
      jTextPane2.setFont(new java.awt.Font("SansSerif", 3, 8));
      jTextPane2.setText("Time: DEFAULT ENTRY =  Current Time");
      jTextPane2.setBackground(Color.lightGray);
      jTextPane2.setMinimumSize(new Dimension(6, 30));
      jTextPane2.setLayout(flowLayout3);
      this.setResizable(false);
      iffCode.setText("IFF Code:");
      typeCtc.setText("Type:");
      jTextPane1.setSelectedTextColor(Color.lightGray);
      jTextPane1.setBackground(Color.lightGray);
      jTextPane1.setBounds(new Rectangle(12, 41, 75, 12));
      longComboBox.addItem ("East");
      longComboBox.addItem("West");
      latComboBox.addItem ("North");
      latComboBox.addItem("South");
    ctcTimeSec.setNextFocusableComponent(latComboBox);
      ctcTimeSec.setHorizontalAlignment(SwingConstants.CENTER);
      ctcDegreeLatitude.setHorizontalAlignment(SwingConstants.CENTER);
      ctcMinuteLatitude.setHorizontalAlignment(SwingConstants.CENTER);
      ctcMinuteLongitude.setHorizontalAlignment(SwingConstants.CENTER);
      ctcDegreeLongitude.setHorizontalAlignment(SwingConstants.CENTER);
      typeInput.setHorizontalAlignment(SwingConstants.CENTER);
      iffCodeInput.setHorizontalAlignment(SwingConstants.CENTER);
      ctcAltitude.setHorizontalAlignment(SwingConstants.CENTER);
      ctcSpeed.setHorizontalAlignment(SwingConstants.CENTER);
      ctcCourse.setHorizontalAlignment(SwingConstants.CENTER);
      this.getContentPane().add(selectorBox, BorderLayout.SOUTH);
      selectorBox.add(OK, null);
      selectorBox.add(cancel, null);
      this.getContentPane().add(contract_ID, BorderLayout.WEST);
      contract_ID.add(neutral, null);
      contract_ID.add(hostile, null);
      contract_ID.add(friendly, null);
      contract_ID.add(unknown, null);
      contract_ID.add(jTextPane1, null);
      contract_ID.add(ctcIFF, null);
      this.getContentPane().add(contactType, BorderLayout.NORTH);
      contactType.add(ctcType, null);
      contactType.add(sea, null);
      contactType.add(land, null);
      contactType.add(air, null);
      contactType.add(unknownType, null);
      this.getContentPane().add(contactInformation, BorderLayout.EAST);
      contactInformation.add(ctcInfo, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 4, 0, 11), 12, 0));
      contactInformation.add(ctcCourse, new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 16, 0, 0), 50, 0));
      contactInformation.add(cse, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 11, 0, 0), 0, 0));
      contactInformation.add(spd, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(12, 12, 0, 0), 0, 0));
      contactInformation.add(ctcSpeed, new GridBagConstraints(2, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(9, 16, 0, 0), 50, 0));
    contactInformation.add(ctcNameField, new GridBagConstraints(0, 7, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 4, 4, 0), 128, 2));
    contactInformation.add(iffCode, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(11, 13, 0, 0), 0, 0));
    contactInformation.add(ctcAltitude, new GridBagConstraints(2, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(13, 17, 0, 0), 49, 0));
    contactInformation.add(alt, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(16, 13, 0, 0), 0, 0));
    contactInformation.add(iffCodeInput, new GridBagConstraints(2, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 16, 0, 0), 50, 0));
    contactInformation.add(typeInput, new GridBagConstraints(1, 5, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 0, 0, 0), 79, 0));
    contactInformation.add(typeCtc, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 14, 0, 12), 0, 0));
    contactInformation.add(ctcName, new GridBagConstraints(0, 6, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(7, 10, 0, 8), 36, -5));
      this.getContentPane().add(jPanel1, BorderLayout.CENTER);
      jPanel1.add(ctcMinLong, new GridBagConstraints(4, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(20, 0, 3, 0), 4, 1));
      jPanel1.add(ctcMinuteLongitude, new GridBagConstraints(3, 8, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(17, 0, 3, 0), 36, 0));
      jPanel1.add(ctcDegLong, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(21, 0, 3, 0), 0, 0));
      jPanel1.add(ctcDegreeLongitude, new GridBagConstraints(0, 8, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(17, 28, 3, 0), 29, 0));
    jPanel1.add(ctcTimeHr, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 18, 0, 8), 31, -2));
    jPanel1.add(ctcDegreeLatitude, new GridBagConstraints(0, 6, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(11, 25, 0, 0), 32, 0));
    jPanel1.add(ctcDegLat, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(16, 0, 0, 0), 0, 0));
    jPanel1.add(ctcMinuteLatitude, new GridBagConstraints(3, 6, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(11, 0, 0, 0), 36, 0));
    jPanel1.add(ctcLong, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(25, 3, 0, 0), 0, -3));
    jPanel1.add(ctcLat, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 3, 0, 11), 0, -2));
    jPanel1.add(ctcMinLat, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(14, 0, 0, 0), 5, 2));
    jPanel1.add(jTextPane2, new GridBagConstraints(0, 4, 5, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 3, 0, 0), -52, -17));
    jPanel1.add(latComboBox, new GridBagConstraints(1, 5, 4, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8, 0, 0, 0), -29, 0));
    jPanel1.add(ctcTimeMin, new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 27, 0));
    jPanel1.add(ctcTimeSec, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 0), 28, 0));
    jPanel1.add(tim, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 12, 0, 0), 48, -4));
    jPanel1.add(ctcPosit, new GridBagConstraints(0, 0, 5, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 12, 0, 0), 5, 0));
    jPanel1.add(longComboBox, new GridBagConstraints(2, 7, 3, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(19, 0, 0, 0), -53, 0));
    jPanel1.add(jLabel1, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 18, 0, 21), 8, 0));
    jPanel1.add(jLabel2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel3, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 9, 0, 0), 11, 0));
      ContactIFF.add(unknown);
      ContactIFF.add(friendly);
      ContactIFF.add(neutral);
      ContactIFF.add(hostile);

      ContactTyp.add(air);
      ContactTyp.add(sea);
      ContactTyp.add(land);
      ContactTyp.add(unknownType);

      CloseWindow.add(OK);
      CloseWindow.add(cancel);
   }

//############################################################################
//####### BEGIN - Mouse Listener functions for the CONTACT TYPE group #######
//############################################################################
   void air_actionPerformed(ActionEvent e) {
      AbstractButton ab = (AbstractButton) e.getSource();
      String typeOfContact = ab.getText().toUpperCase();
      C1 = new String(typeOfContact);
   }
   void sea_actionPerformed(ActionEvent e) {
      AbstractButton ab = (AbstractButton) e.getSource();
      String typeOfContact = ab.getText().toUpperCase();
      C1 = new String(typeOfContact);
   }
   void land_actionPerformed(ActionEvent e) {
      AbstractButton ab = (AbstractButton) e.getSource();
      String typeOfContact = ab.getText().toUpperCase();
      C1 = new String(typeOfContact);
   }
   void unknownType_actionPerformed(ActionEvent e) {
      AbstractButton ab = (AbstractButton) e.getSource();
      String typeOfContact = ab.getText().toUpperCase();
      C1 = new String(typeOfContact);
   }
//############################################################################
//########## END  - Mouse Listener functions for the CONTACT TYPE group ##########
//############################################################################
//####### BEGIN - Mouse Listener functions for the CONTACT IFF group #######
//############################################################################

   void unknown_actionPerformed(ActionEvent e) {
      AbstractButton ab = (AbstractButton) e.getSource();
      String Iffzero = ab.getText().toUpperCase();
      I1 = new String(Iffzero);
   }

   void friendly_actionPerformed(ActionEvent e) {
      AbstractButton ab = (AbstractButton) e.getSource();
      String Iffzero = ab.getText().toUpperCase();
      I1 = new String(Iffzero);
   }

   void neutral_actionPerformed(ActionEvent e) {
      AbstractButton ab = (AbstractButton) e.getSource();
      String Iffzero = ab.getText().toUpperCase();
      I1 = new String(Iffzero);
   }

   void hostile_actionPerformed(ActionEvent e) {
      AbstractButton ab = (AbstractButton) e.getSource();
      String Iffzero = ab.getText().toUpperCase();
      I1 = new String(Iffzero);
   }

//############################################################################
//########## END  - Mouse Listener functions for the CONTACT TYPE group ##########
//############################################################################
//#######  begin - Mouse Listener functions for the CANCEL button ########

   void cancel_actionPerformed(ActionEvent e) {
      dispose();
   }

//########## END  - Mouse Listener functions for the CANCEL button ##########
//############################################################################
//################# BEGIN - Mouse Listener functions for the OK button ########

    void OK_actionPerformed(ActionEvent e) {
      iuContact();
    }

    void OK_keyTyped(KeyEvent e) {
      iuContact();
    }
//*This function performs the error checking and actual entry of contacts*/
public void iuContact () {
   validInput = true;
      int answer = JOptionPane.showConfirmDialog(null,
      "Are you sure you want to insert this Contact?", "Insert Contact",
      JOptionPane.YES_NO_CANCEL_OPTION,
      JOptionPane.QUESTION_MESSAGE);
      if (answer == JOptionPane.CANCEL_OPTION){
         dispose();
      }
      if (answer == JOptionPane.NO_OPTION){
         dispose();
      }
      if (answer == JOptionPane.OK_OPTION){

      //####### - Sets the TIME the contact was spotted - ######## -
      String newContactHour = ctcTimeHr.getText();
      String newContactMinute = ctcTimeMin.getText();
      String newContactSecond = ctcTimeSec.getText();

      try{
         int newCtcHour = Integer.parseInt(newContactHour);
            if ( newCtcHour >= 0 && newCtcHour <= 12){
               newContactTime.setHours(newCtcHour);
            } else {
               validInput = false;
            }
            int newCtcMinute = Integer.parseInt(newContactMinute);
            if ( newCtcMinute >= 0 && newCtcMinute <= 60){
               newContactTime.setMinutes(newCtcMinute);
            } else {
               validInput = false;
            }

            int newCtcSecond = Integer.parseInt(newContactSecond);
            if ( newCtcSecond >= 0 && newCtcSecond <= 60){
               newContactTime.setSeconds(newCtcSecond);
            } else {
               validInput = false;
            }
            if (!validInput){
               int cte = JOptionPane.showConfirmDialog(null,
               "Invalid Time entered! Please re-enter.", "Invalid Time",
               JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
               ctcTimeHr.setText(null);
               ctcTimeMin.setText(null);
               ctcTimeSec.setText(null);
            }
         }
            catch (Exception eTime){
               if (!newContactHour.equals("") && !newContactMinute.equals("") &&
                   !newContactSecond.equals("")) {
                   int cte = JOptionPane.showConfirmDialog(null,
                   "Invalid Time entered! Please re-enter.", "Invalid Time",
                   JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                   validInput = false;
                   ctcTimeHr.setText(null);
                   ctcTimeMin.setText(null);
                   ctcTimeSec.setText(null);
               }
               //newContactTime = new java.util.Date();
            }
     //####### - Sets the LATITUDE of the contact - ######## - GOOD/WORKS
     if (validInput) {
        try{
         String newDegreeLat = ctcDegreeLatitude.getText();
         int tempCtcDegLat = Integer.parseInt(newDegreeLat);
            if (tempCtcDegLat < 0) {
               int ndl = JOptionPane.showConfirmDialog(null,
                  "Invalid Degrees Latitude entered! Please re-enter.", "Invalid Entry",
                  JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                  validInput = false;
                  ctcDegreeLatitude.setText(null);
            }
         String newMinuteLat = ctcMinuteLatitude.getText();
         double tempCtcMinLat = Integer.parseInt(newMinuteLat);
         if (tempCtcMinLat < 0) {
            int nml = JOptionPane.showConfirmDialog(null,
                   "Invalid Minutes Latitude entered! Please re-enter.", "Invalid Entry",
                   JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                   validInput = false;
                   ctcMinuteLatitude.setText(null);
         }
         String latitudeComboEntry = (String) latComboBox.getSelectedItem();
         if (latitudeComboEntry == "South") {
            tempCtcDegLat = -1 * tempCtcDegLat;
         }
         if ( tempCtcDegLat >= -90 && tempCtcDegLat <= 90){
            contactDegLatitude = tempCtcDegLat;
         }
         else {
            int jopDegLat = JOptionPane.showConfirmDialog(null,
                  "Invalid degrees entered for Latitude. Please re-enter",
                  "Error in Latitude degrees entry",
                  JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            validInput = false;
            ctcDegreeLatitude.setText(null);
         }
         tempCtcMinLat = Double.parseDouble(newMinuteLat);
               if ( tempCtcMinLat >= 0 && tempCtcMinLat <= 60){
                   if (contactDegLatitude >= 0) {
                       contactMinLatitude = tempCtcMinLat;
                   } else {
                       contactMinLatitude = tempCtcMinLat * -1.0;
                   } // end if-else
               }
               else {
                  int jopMinLat = JOptionPane.showConfirmDialog(null,
                  "Invalid minutes entered for Latitude. Please re-enter.",
                  "Error in Latitude minutes entry",
                  JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                  validInput = false;
                  ctcMinuteLatitude.setText(null);
               }
   //####### - Sets the LONGITUDE of the contact - ######## - GOOD/WORKS
         String newDegreeLong = ctcDegreeLongitude.getText();
         String newMinuteLong = ctcMinuteLongitude.getText();
         int tempCtcDegLong = Integer.parseInt(newDegreeLong);
         String longitudeComboEntry = (String) longComboBox.getSelectedItem();
           if (longitudeComboEntry == "West") {
             tempCtcDegLong = -1 * tempCtcDegLong;
             }
            if ( tempCtcDegLong >= -180 && tempCtcDegLong <= 180){
               contactDegLongitude = tempCtcDegLong;
            }
            else {
               contactDegLongitude = 0;
                int jopDegLong = JOptionPane.showConfirmDialog(null,
               "Invalid degrees entered for Longitude.  Please re-enter.",
               "Error in Longitude degrees entry",
               JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
             }
         double tempCtcMinLong = Double.parseDouble(newMinuteLong);
               if ( tempCtcMinLong >= 0 && tempCtcMinLong <= 60){
                   if (contactDegLongitude >= 0) {
                       contactMinLongitude = tempCtcMinLong;
                   } else {
                       contactMinLongitude = tempCtcMinLong * -1.0;
                   } // end if-else
               }
               else {
                  int jopMinLong = JOptionPane.showConfirmDialog(null,
                  "Invalid minutes entered for Longitude!!  Please re-enter.",
                  "Error in Longitude minutes entry",
                  JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                  validInput = false;
                  ctcMinuteLongitude.setText(null);
               }

        }//ends try block
        catch (Exception LLE) {
           int lle = JOptionPane.showConfirmDialog(null,
           "Invalid Latitude and/or Longitude entered!  Please re-enter.",
           "Invalid Lat/Long",
           JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
           validInput = false;
           ctcDegreeLongitude.setText(null);
           ctcMinuteLongitude.setText(null);
           ctcDegreeLatitude.setText(null);
           ctcMinuteLatitude.setText(null);
       }//ends CATCH block
     } // end if
      //####### - Sets the COURSE of the contact - ######## - GOOD/WORKS
      if (validInput) {
         try{
           String newCourse = ctcCourse.getText();
           double tempV1 = Double.parseDouble(newCourse);
           if ( tempV1 >= 0 && tempV1 <= 360){
               V1 = tempV1;
           } else {
               int jopv1 = JOptionPane.showConfirmDialog(null,
               "Invalid Course entered!!  Please re-enter.",
               "Error in Course entry",
               JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
               validInput = false;
               ctcCourse.setText(null);
           }//end else
         }//end try
         catch (Exception CE) {
           int ce = JOptionPane.showConfirmDialog(null,
           "Invalid Course entered!  Please re-enter.",
           "Invalid Course",
           JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
           validInput = false;
           ctcCourse.setText(null);
         }//end catch
      }//end if "validInput"
     //####### - Sets the SPEED of the contact - ######## - GOOD/WORKS
     if (validInput) {
        try {
            String newSpeed = ctcSpeed.getText();
            V2 = Double.parseDouble(newSpeed);
        } catch (Exception SE) {
           int se = JOptionPane.showConfirmDialog(null,
           "Invalid Speed entered!  Contact speed set to 0 mph.",
           "Invalid Speed",
           JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
           validInput = false;
           ctcSpeed.setText(null);
        }
     }
     //####### - Sets the ALTITUDE of the contact - ######## - GOOD/WORKS
     if (validInput) {
        try {
            String newAltitude = ctcAltitude.getText();
            contactAltitude = Double.parseDouble(newAltitude);
        }//end try
        catch (Exception AE) {
            int ae = JOptionPane.showConfirmDialog(null,
            "Invalid Altitude entered!  Please re-enter.",
            "Invalid Altitude",
            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            validInput = false;
            contactAltitude = 0;
        }//end catch
     }//end if validInput
      //####### - Sets the NAME of the contact - ######## - GOOD/WORKS
      if (validInput) {
         String newName = ctcNameField.getText().toUpperCase();
         N1 = new String(newName);
         c.setName(N1);

         //####### - Sets the IFF of the contact - ######## - GOOD/WORKS
         String newIFF = iffCodeInput.getText();
         IC = new String(newIFF);
         c.setIffCode(IC);

         //####### - Sets the NAME of the contact - ######## - GOOD/WORKS
         String newType = typeInput.getText();
         T1 = new String(newType);
         c.setType(T1);

   //####### - Closes the CONTACT INFO window - ######## - GOOD/WORKS

         if (I1.equals("UNKNOWN")) {
             c.setIffCategory(0);
            } else if (I1.equals("FRIENDLY")) {
                c.setIffCategory(1);
            } else if (I1.equals("HOSTILE")) {
                c.setIffCategory(2);
            } else {
                c.setIffCategory(3);
            } // end if-else
         c.setLastReport(newContactTime);
         Location loc = new Location();
         Velocity vel = new Velocity();
         try {
         loc.setLatitude(contactDegLatitude,contactMinLatitude);
         loc.setLongitude(contactDegLongitude,contactMinLongitude);
         loc.setAltitude(contactAltitude);
         c.setLocation(loc);
         vel.setHeading(V1);
         vel.setSpeed(V2);
         c.setVelocity(vel);
         } catch (Exception exception) {
         }
         if (C1.equals("AIR")) {
             c.setClassification(2);
            } else if (C1.equals("SEA")) {
                c.setClassification(1);
            } else if (C1.equals("LAND")) {
                c.setClassification(3);
            } else {
                c.setClassification(0);
            } // end if-else
         dispose();
      } // end if for validInput
//      dispose();
   }//ends the IF for "OK_Option statement"
}/////////////ends the 'iuContact' function for the OK button
}//ends jbInit
