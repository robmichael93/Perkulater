/*
 * InsertContact.java
 *
 * Created on May 21, 2001, 1:41 PM
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import cs3773.*;

/**
 *
 * @author  Rob Michael
 * @version
 */
public class InsertContact extends JFrame {

    Contact contact;
    private JTextField nameInput;
    private JComboBox typeInput;
    private JComboBox iffCatInput;
    private JTextField iffInput;
    private JComboBox latHemisphereInput;
    private JTextField latDegreesInput;
    private JTextField latMinutesInput;
    private JComboBox longHemisphereInput;
    private JTextField longDegreesInput;
    private JTextField longMinutesInput;
    private JTextField headingInput;
    private JTextField speedInput;
    private JTextField altitudeInput;

    /** Creates new InsertContact */
    public InsertContact(Contact c) {
        super("Contact Insert Control");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(315,295);
        setResizable(false);

        contact = c;

        JPanel insert = new JPanel(new GridLayout(14,2));
        setContentPane(insert);

        ButtonHandler bh = new ButtonHandler();

        JLabel nameLabel = new JLabel("Name:", SwingConstants.RIGHT);
        insert.add(nameLabel);
        nameInput = new JTextField(20);
        insert.add(nameInput);
        JLabel typeLabel = new JLabel("Type:", SwingConstants.RIGHT);
        insert.add(typeLabel);
        typeInput = new JComboBox();
        typeInput.addItem("AIR");
        typeInput.addItem("SURFACE");
        typeInput.addItem("SUBSURFACE");
        typeInput.addItem("UNKNOWN");
        insert.add(typeInput);
        JLabel iffCatLabel = new JLabel("IFF Category:", SwingConstants.RIGHT);
        insert.add(iffCatLabel);
        iffCatInput = new JComboBox();
        iffCatInput.addItem("Unknown");
        iffCatInput.addItem("Friendly");
        iffCatInput.addItem("Hostile");
        iffCatInput.addItem("Neutral");
        insert.add(iffCatInput);
        JLabel iffLabel = new JLabel("IFF:", SwingConstants.RIGHT);
        insert.add(iffLabel);
        iffInput = new JTextField(10);
        insert.add(iffInput);
        JLabel latHemisphereLabel = new JLabel("Latitude Hemisphere:",
                                    SwingConstants.RIGHT);
        insert.add(latHemisphereLabel);
        latHemisphereInput = new JComboBox();
        latHemisphereInput.addItem("North");
        latHemisphereInput.addItem("South");
        insert.add(latHemisphereInput);
        JLabel latDegreesLabel = new JLabel("Latitude (degrees, XXX):",
                                 SwingConstants.RIGHT);
        insert.add(latDegreesLabel);
        latDegreesInput = new JTextField(10);
        insert.add(latDegreesInput);
        JLabel latMinutesLabel = new JLabel("Latitude (minutes, XX.X):",
                                 SwingConstants.RIGHT);
        insert.add(latMinutesLabel);
        latMinutesInput = new JTextField(10);
        insert.add(latMinutesInput);
        JLabel longHemisphereLabel = new JLabel("Longitude Hemisphere:",
                                     SwingConstants.RIGHT);
        insert.add(longHemisphereLabel);
        longHemisphereInput = new JComboBox();
        longHemisphereInput.addItem("West");
        longHemisphereInput.addItem("East");
        insert.add(longHemisphereInput);
        JLabel longDegreesLabel = new JLabel("Longitude (degrees, XXX):",
                                  SwingConstants.RIGHT);
        insert.add(longDegreesLabel);
        longDegreesInput = new JTextField(10);
        insert.add(longDegreesInput);
        JLabel longMinutesLabel = new JLabel("Longitude (minutes, XX.X):",
                                  SwingConstants.RIGHT);
        insert.add(longMinutesLabel);
        longMinutesInput = new JTextField(10);
        insert.add(longMinutesInput);
        JLabel headingLabel = new JLabel("Heading (degrees, XXX):",
                              SwingConstants.RIGHT);
        insert.add(headingLabel);
        headingInput = new JTextField(10);
        insert.add(headingInput);
        JLabel speedLabel = new JLabel("Speed (knots, XXX.X):",
                            SwingConstants.RIGHT);
        insert.add(speedLabel);
        speedInput = new JTextField(10);
        insert.add(speedInput);
        JLabel altitudeLabel = new JLabel("Altitude (meters, XXX.X):",
                               SwingConstants.RIGHT);
        insert.add(altitudeLabel);
        altitudeInput = new JTextField(10);
        insert.add(altitudeInput);
        JButton accept = new JButton("OK");
        insert.add(accept);
        accept.addActionListener(bh);
        JButton cancel = new JButton("Cancel");
        insert.add(cancel);
        cancel.addActionListener(bh);
        show();
    }

   public class ButtonHandler implements ActionListener {
       public void actionPerformed(ActionEvent e) {
           if (e.getActionCommand() == "OK") {
               int latDegrees = 0;
               int longDegrees = 0;
               double latMins = 0.0;
               double longMins = 0.0;
               double heading = 0.0;
               double speed = 0.0;
               double altitude = 0.0;
               boolean validInput = true;

               try {
                   latDegrees = Integer.parseInt(latDegreesInput.getText());
                   latMins = Double.parseDouble(latMinutesInput.getText());
                   longDegrees = Integer.parseInt(longDegreesInput.getText());
                   longMins = Double.parseDouble(longMinutesInput.getText());
                   altitude = Double.parseDouble(altitudeInput.getText());
               } catch (NumberFormatException nfe) {
                   JOptionPane.showMessageDialog(null,
                   "Invalid location.  Please re-enter.", "Invalid Number Input",
                   JOptionPane.ERROR_MESSAGE);
                   validInput = false;
                   latDegreesInput.setText(null);
                   latMinutesInput.setText(null);
                   longDegreesInput.setText(null);
                   longMinutesInput.setText(null);
                   altitudeInput.setText(null);
               } // end try-catch
               if ((String)latHemisphereInput.getSelectedItem() == "South") {
                   latDegrees *= -1;
               } // end if
               if ((String)longHemisphereInput.getSelectedItem() == "East") {
                   longDegrees *= -1;
               } // end if
               Location loc = new Location();
               try {
                   loc.setLatitude(latDegrees, latMins);
                   loc.setLongitude(longDegrees, longMins);
                   loc.setAltitude(altitude);
               } catch (InvalidLocationException ile) {
                   JOptionPane.showMessageDialog(null,
                   "Invalid location.  Please re-enter.", "Invalid Location Input",
                   JOptionPane.ERROR_MESSAGE);
                   validInput = false;
                   latDegreesInput.setText(null);
                   latMinutesInput.setText(null);
                   longDegreesInput.setText(null);
                   longMinutesInput.setText(null);
                   altitudeInput.setText(null);
               } // end try-catch
               try {
                   heading = Double.parseDouble(headingInput.getText());
                   speed = Double.parseDouble(speedInput.getText());
               } catch (NumberFormatException nfe) {
                   JOptionPane.showMessageDialog(null,
                   "Invalid location.  Please re-enter.", "Invalid Number Input",
                   JOptionPane.ERROR_MESSAGE);
                   validInput = false;
                   headingInput.setText(null);
                   speedInput.setText(null);
               } // end try-catch
               Velocity vel = new Velocity();
               try {
                   vel.setHeading(heading);
                   vel.setSpeed(speed);
               } catch (InvalidVelocityException ive) {
                   JOptionPane.showMessageDialog(null,
                   "Invalid velocity vector.  Please re-enter.", "Invalid Velocity Input",
                   JOptionPane.ERROR_MESSAGE);
                   validInput = false;
                   headingInput.setText(null);
                   speedInput.setText(null);
               } // end try-catch
               if (validInput == true) {
                   contact.setName(nameInput.getText());
                   contact.setType((String)typeInput.getSelectedItem());
                   contact.setIffCategory(iffCatInput.getSelectedIndex());
                   contact.setLocation(loc);
                   contact.setVelocity(vel);
                   dispose();
               } // end if
           } else {
               dispose();
           } // end if-else
       }
   }
}
