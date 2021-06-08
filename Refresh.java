//------------------------------------------------------------------------------
// Filename:  Refresh.java
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

import java.util.*;
import java.awt.*;
import javax.swing.*;
import cs3773.*;

/**
 * This class is the engine for updating/refreshing contacts.
 *
 */

public class Refresh extends java.util.TimerTask {

/**
 * Jcomponent variable
 */
    JComponent component;
    /**
     * Vector variable
     */
    Vector vector;
    /**
     * Transformer variable
     */
    Transformer trans;

    /**
     * Constructor for Refresh
     * @param c JComponent
     * @param v Vector
     * @param t Transformer
     */
    public Refresh (JComponent c, Vector v, Transformer t) {
        component = c;
        vector = v;
        trans = t;
    } // end Refresh constructor

    /**
     * Run method to actually refresh contacts
     */
    public void run() {
        Component[] kids = component.getComponents();
        int index = 0;
        for (int i = 0; i < kids.length; i++) {
           if ((kids[i] instanceof ContactDisplay)) {
               if (((ContactDisplay)kids[i]).isShowing() == false) {
                   index = vector.indexOf(((ContactDisplay)kids[i]).getContact());
                   synchronized (vector) {
                      vector.remove(index);
                      component.remove((ContactDisplay)kids[i]);
                   } // end synchronized removal of contact
               } // end if
               if (((ContactDisplay)kids[i]).getUpdateStatus() == true) {
                  synchronized (vector) {
                     // get the updated contact and add it to the vector container
                     Contact contact = ((ContactDisplay)kids[i]).getContact();
                     vector.add(contact);
                     // get the index of the old, unupdated contact and remove it
                     // from the container vector and the contact panel
                     index = vector.indexOf(((ContactDisplay)kids[i]).getContact());
                     vector.remove(index);
                     component.remove((ContactDisplay)kids[i]);
                     // reassign at display type and add it to the contact panel
                     ContactDisplay cd = assignDisplayType(contact);
                     component.add(cd);
                     cd.setLocation(trans.toScreen(contact.getLocation()));
                  } // end synchronized addition of updated contact
               } // end if
           } // end if
        } // end for
        component.repaint();
    } // end run

    /**
     * Assigns display type to contacts
     * @param c Contact
     * @return ContactDispaly of the appropriate shape by classification
     */
    public ContactDisplay assignDisplayType (Contact c) {

        if (c.getClassification() == 1) {
            return new SquareDisplay(c);
        } else if (c.getClassification() == 2) {
            return new TriangleDisplay(c);
        } else if (c.getClassification() == 3){
            return new DiamondDisplay(c);
        } else { // unknown contact
            return new CircleDisplay(c);
        } // end if-else
    } // end assignDisplayType
} // end class Refresh
