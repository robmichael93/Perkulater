//------------------------------------------------------------------------------
// Filename:  ContactDisplay.java
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
 * @author PerkUlateR:  Keith M. Perkins, Stephen O. Ulate, Robert J. Michael
 * @version 1.2.12 (at least)
 */

//package battlespace;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import cs3773.*;

/**
 * This class constructs the display for all contacts.
 *
 */

public class ContactDisplay extends JComponent implements MouseListener {

/**
 * Contact associated with display
 */
   protected Contact c;
/**
 * Color of contact icon
 */
   protected Color color;
/**
 * Contact update status
 */
   private boolean updated = false;
/**
 * Contact popup menu
 */
   protected JPopupMenu contactPopupMenu = new JPopupMenu();
/**
 * Popup menu item for edit
 */
   protected JMenuItem contactEdit = new JMenuItem("Edit");
/**
 * Popup menu item for delete
 */
   protected JMenuItem contactDelete = new JMenuItem("Delete");

/**
 * Creates a contact display
 * @param contact The contact associated with the display item
 */

   public ContactDisplay(Contact contact) {
      c = contact;
      setSize(10, 10);
      String classification;
      String iffCat;
      if (c.getIffCategory() == 1) { // Friendly
          color = Color.blue;
          iffCat = "Friendly";
      } else if (c.getIffCategory() == 2) { // Hostile
          color = Color.red;
          iffCat = "Hostile";
      } else if (c.getIffCategory() == 3) { // Neutral
          color = Color.white;
          iffCat = "Neutral";
      } else { // Unknown
          color = Color.yellow;
          iffCat = "Unknown";
      } // end if-else
      if (c.getClassification() == 1) { // sea
         classification = "Sea";
      } else if (c.getClassification() == 2) { // air
         classification = "Air";
      } else if (c.getClassification() == 3) { // land
         classification = "Land";
      } else {
         classification = "Unknown";
      } // end if-else
      contactPopupMenu.add(contactEdit);
      contactPopupMenu.addSeparator();
      contactPopupMenu.add(contactDelete);
      contactEdit.addActionListener(new contactEditHandler());
      contactDelete.addActionListener(new contactDeleteHandler());
      addMouseListener (this);
      setToolTipText("<html><body>" + c.getName() + "<br>" + "Type: " +
                     c.getType() + "<br>" + iffCat + " " + classification + "<br>"
                     + "IFF: " + (c.getIffCode().equals("") ? "Unknown": c.getIffCode())
                     + "</body></html>");
   }

/**
 * This method moves the ContactDisplay to reflect the location of the underlying contact
 *
 * @param t takes a transformner object.
 */

   public void sync(Transformer t) {
      Location loc = c.project(new java.util.Date());
      Point p = t.toScreen(loc);
      //center the contact within the contact display
      setLocation(p.x - 5, p.y - 5);
   }

/**
 * Returns the contact associated with this ContactDisplay
 *
 * @return Contact the contact associated with this ContactDisplay
 */
   public Contact getContact() {
      return c;
   }

/**
 * Returns the status of the contact if it has been updated or not
 *
 * @return boolean whether or not the contact has been updated
 */

   public boolean getUpdateStatus() {
       return updated;
   }

/**
 * Handler for popping up an update dialog
 *
 */

   private class contactEditHandler implements ActionListener {
/**
 * Handles the actionPerformed function
 *
 * @param e Action event
 */
      public void actionPerformed(ActionEvent e) {
          UpdateDialog ud = new UpdateDialog(c);
          ud.addWindowListener(new UpdateDialogHandler());
      } // end actionPerformed
   } // end contactEditHandler

/**
 * Handles deleting a contact from the display
 *
 */

   private class contactDeleteHandler implements ActionListener {
/**
 * Handles the actionPerformed function
 *
 * @param e Action event
 */
      public void actionPerformed(ActionEvent e) {
          setVisible(false);
      } // end actionPerformed
   } // end contactDeleteHandler

/**
 * Handles launching a popup menu for the contact
 *
 * @param me Mouse event for the popup
 */

   public void mouseReleased (MouseEvent me) {
      if (me.isPopupTrigger()) {
          contactPopupMenu.show(me.getComponent(), me.getX(), me.getY());
      } // end if
   } // end MouseReleased

/**
 * Handles checking for a double click on the contact to launch an update dialog
 *
 * @param me Mouse event for the contact
 */

   public void mouseClicked (MouseEvent me) {
      if (me.getClickCount() > 1) {
          UpdateDialog ud = new UpdateDialog(c);
          ud.addWindowListener(new UpdateDialogHandler());
      } // end if
   } // end MouseClicked
/**
 * Empty function
 *
 * @param me Mouse event
 */
   public void mousePressed(MouseEvent me) {}
/**
 * Empty function
 *
 * @param me Mouse event
 */
   public void mouseEntered(MouseEvent me) {}
/**
 * Empty function
 *
 * @param me Mouse event
 */
   public void mouseExited(MouseEvent me) {}

/**
 * Handles changing the update status of the contact after it has been updated
 *
 */

   public class UpdateDialogHandler extends WindowAdapter {
/**
 * Handles the window closed event
 *
 * @param we Window event
 */
       public void windowClosed(WindowEvent we) {
           updated = true;
       }
   }
}