//------------------------------------------------------------------------------
// Filename:  RecenterDialog.java
// Date:      25 May 2001
// Course:    CS3773-1
// Project#:  3
// Compiler:  JBuilder 4.0
//------------------------------------------------------------------------------

/**
 * Title:        CS3773 Project # 3
 * Description:  Battlefield Situational Display Project
 * Copyright:    Copyright (c) 2001
 * Company:      NPS, U.S. Army, U.S. Navy
 * @author PerkUlateR: Keith M. Perkins, Stephen O. Ulate, Robert J. Michael
 * @version 1.2.12 (at least)
 */

//package battlespace;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import cs3773.*;

/**
 * A dialog box for recentering the display.
 *
 *
 */

public class RecenterDialog extends JFrame {

/**
 * Transformer variable
 */
    private Transformer t;
    /**
     * Contact panel
     */
    private ContactPanel cp;
    /**
     * Maximum X for screen
     */
    private int screenMaxX;
    /**
     * Maximum Y for screen
     */
    private int screenMaxY;
    /**
     * Input field for Degrees Latitude
     */
    private JTextField latDegreesInput;
    /**
     * Input field for Minutes Latitude
     */
    private JTextField latMinutesInput;
    /**
     * Input field for Degrees Longitude
     */
    private JTextField longDegreesInput;
    /**
     * Input field for Minutes Longitude
     */
    private JTextField longMinutesInput;
    /**
     * Input box for Hemisphere Latitude
     */
    private JComboBox latHemisphereInput;
    /**
     * Input box for Hemisphere Longitude
     */
    private JComboBox longHemisphereInput;

    /**
     * Creates new RecenterDialog
     * @param trans Transformer
     * @param panel ContactPanel
     * @param maxX int
     * @param maxY int
     */
    public RecenterDialog(Transformer trans, ContactPanel panel, int maxX, int maxY) {
        super("Recenter");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(350,150);
        setLocation(0,300);
        setResizable(false);

        t = trans;
        cp = panel;
        screenMaxX = maxX;
        screenMaxY = maxY;

        JPanel main = new JPanel(new GridLayout(7,2));
        setContentPane(main);

        ButtonHandler bh = new ButtonHandler();

        JLabel latHemisphereLabel = new JLabel("Latitude Hemisphere:",
                                    SwingConstants.RIGHT);
        main.add(latHemisphereLabel);
        latHemisphereInput = new JComboBox();
        latHemisphereInput.addItem("North");
        latHemisphereInput.addItem("South");
        main.add(latHemisphereInput);
        JLabel latDegreesLabel = new JLabel("Latitude (degrees, XXX):",
                                 SwingConstants.RIGHT);
        main.add(latDegreesLabel);
        latDegreesInput = new JTextField(10);
        main.add(latDegreesInput);
        JLabel latMinutesLabel = new JLabel("Latitude (minutes, XX.X):",
                                 SwingConstants.RIGHT);
        main.add(latMinutesLabel);
        latMinutesInput = new JTextField(10);
        main.add(latMinutesInput);
        JLabel longHemisphereLabel = new JLabel("Longitude Hemisphere:",
                                     SwingConstants.RIGHT);
        main.add(longHemisphereLabel);
        longHemisphereInput = new JComboBox();
        longHemisphereInput.addItem("West");
        longHemisphereInput.addItem("East");
        main.add(longHemisphereInput);
        JLabel longDegreesLabel = new JLabel("Longitude (degrees, XXX):",
                                  SwingConstants.RIGHT);
        main.add(longDegreesLabel);
        longDegreesInput = new JTextField(10);
        main.add(longDegreesInput);
        JLabel longMinutesLabel = new JLabel("Longitude (minutes, XX.X):",
                                  SwingConstants.RIGHT);
        main.add(longMinutesLabel);
        longMinutesInput = new JTextField(10);
        main.add(longMinutesInput);
        JButton accept = new JButton("OK");
        accept.addActionListener(bh);
        main.add(accept);
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(bh);
        main.add(cancel);
        show();
    }


/**
 * Button handler for OK and Cancel
 *
 *
 */

    public class ButtonHandler implements ActionListener {
    /**
     * Handles the actions depending on the buttons pressed
     * @param e ActionEvent
     */
        public void actionPerformed(ActionEvent e) {
            int latDegrees = 0;
            int longDegrees = 0;
            double latMins = 0.0;
            double longMins = 0.0;
            boolean validInput = true;

            if (e.getActionCommand() == "OK") {
                try {
                    latDegrees = Integer.parseInt(latDegreesInput.getText());
                    latMins = Double.parseDouble(latMinutesInput.getText());
                    longDegrees = Integer.parseInt(longDegreesInput.getText());
                    longMins = Double.parseDouble(longMinutesInput.getText());
						  if (latDegrees < 0 || latMins < 0 || longDegrees < 0 ||
							   longMins < 0) {
									JOptionPane.showMessageDialog(null,
									"Invalid number.  Only positive numbers allowed.",
									"Invalid number.", JOptionPane.ERROR_MESSAGE);
									validInput = false;
									latDegreesInput.setText(null);
									latMinutesInput.setText(null);
									longDegreesInput.setText(null);
									longMinutesInput.setText(null);
						  } // end if
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null,
                    "Invalid location.  Please re-enter.", "Invalid Number Input",
                    JOptionPane.ERROR_MESSAGE);
                    validInput = false;
                    latDegreesInput.setText(null);
                    latMinutesInput.setText(null);
                    longDegreesInput.setText(null);
                    longMinutesInput.setText(null);
                } // end try-catch
                if ((String)latHemisphereInput.getSelectedItem() == "South") {
                    latDegrees *= -1;
                } // end if
                if ((String)longHemisphereInput.getSelectedItem() == "West") {
                    longDegrees *= -1;
                } // end if
                Location recenter = new Location();
                try {
                    recenter.setLatitude(latDegrees, latMins);
                    recenter.setLongitude(longDegrees, longMins);
                } catch (InvalidLocationException ile) {
                    JOptionPane.showMessageDialog(null,
                    "Invalid location.  Please re-enter.", "Invalid Location Input",
                    JOptionPane.ERROR_MESSAGE);
                    validInput = false;
                    latDegreesInput.setText(null);
                    latMinutesInput.setText(null);
                    longDegreesInput.setText(null);
                    longMinutesInput.setText(null);
                } // end try-catch
                if (validInput == true) {
                    double centerX = recenter.getLongitude();
                    double centerY = recenter.getLatitude();
                    Dimension d = cp.getSize();
                    java.awt.geom.Point2D.Double ul = t.toWorld(0, 0);
                    java.awt.geom.Point2D.Double lr = t.toWorld(d.width, d.height);
                    double ulX = ul.getX();
                    double ulY = ul.getY();
                    double lrX = lr.getX();
                    double lrY = lr.getY();
                    double worldWidth = Math.abs(lrX - ulX);
                    double worldHeight = Math.abs(ulY - lrY);
                    double minX = centerX - worldWidth / 2.0;
                    double minY = centerY - worldHeight / 2.0;
                    double maxX = centerX + worldWidth / 2.0;
                    double maxY = centerY + worldHeight / 2.0;
                    // check to see if recentering would draw over the North pole and
                    // if so, shift world view so 90 degrees North is the upper limit
                    // and the height of the world view remains the same
                    if (maxY > 90 && minY >= -90) {
                       maxY = 90;
                       minY = 90 - worldHeight;
                    } else if (minY < -90 && maxY <= 90) {
                       minY = -90;
                       maxY = -90 + worldHeight;
                    } else if (maxY > 90 && minY < -90) { // both maxY and minY exceed the limits
                       maxY = 90;
                       minY = -90;
                    } // end if-else
                    // if the recentering stradles the +/- 180 degree longitude line
                    // recalculate the correct coordinates
                    if (minX < -180) {
                       minX = 180 - (worldWidth / 2.0) + Math.abs(-180 - centerX);
                       maxX = -180 + (worldWidth / 2.0) + Math.abs(-180 - centerX);
                    } // end if
                    if (maxX > 180) {
                       minX = 180 - (worldWidth / 2.0) - Math.abs(180 - centerX);
                       maxX = -180 + (worldWidth / 2.0) - Math.abs(180 - centerX);
                    } // end if
                    t.setWindow(minX, minY, maxX, maxY);
                    dispose();
                } // end if
            } else {
                dispose();
            } // end if-else
        } // end actionPerformed
    } // end ButtonHandler
} // end RecenterDialog
