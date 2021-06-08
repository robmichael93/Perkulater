//------------------------------------------------------------------------------
// Filename:  ContactPanel.java
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
import java.text.DecimalFormat;
import cs3773.*;

/**
 * The "battlespace" for the program where contacts are placed
 *
 */

public class ContactPanel extends JPanel implements MouseMotionListener, MouseListener {

/**
 * Transformer variable
 */
   Transformer t;
   /**
    * Show Gridlines Boolean, default = true
    */
   private boolean showGridlines = true;

/**
 * Creates a contact panel and associates a transformer with it
 *
 * @param trans Transformer associated with contact panel
 */

   public ContactPanel(Transformer trans) {
      super(null); //no layout manager required
      t = trans;
      setBackground(Color.black);
      addMouseMotionListener(this);
      addMouseListener(this);
   }

/**
 * Sets the gridlines flag
 *
 * @param grids Whether or not the gridlines should be displayed
 */
   public void setGridlines(boolean grids) {
      showGridlines = grids;
   }

/**
 * Displays the panel and all of the components attached to it
 *
 * @param g Graphics object required for drawing
 */
   public void paint(Graphics g) {
      //learn how large the panel currently is
      Dimension d = getSize();
      //set the viewport accordingly
      t.setViewport(0, 0, d.width, d.height);
      //get a list of child components
      Component[] kids = getComponents();
      for (int i = 0; i < kids.length; i++) {
         //should all be ContactDisplay objects if we were careful
         if (kids[i] instanceof ContactDisplay) {
            //sync up the ContactDisplay before we ask it to draw itself
            ((ContactDisplay)kids[i]).sync(t);
         }
      }
      //calls paintComponent to paint self, paintBorder and paintChildren
      //the last call asks all child objects to draw themselves
      //so we don't have to
      super.paint(g);
   }

/**
 * Performs the drawing of the panel and the gridlines if requested
 *
 * @param g Graphics object required for drawing
 */
   public void paintComponent(Graphics g) {

      //always a good thing to do in this method
      super.paintComponent(g);

      if (showGridlines == true) {

         //the following trick helps you learn what portion of the world
         //is currently being displayed in your viewport
         Dimension d = getSize();
         java.awt.geom.Point2D.Double ul = t.toWorld(0, 0);
         java.awt.geom.Point2D.Double lr = t.toWorld(d.width, d.height);

         int longStart = 0;
         int longStart2 = 0;
         int longFinish = 0;
         int latStart = 0;
         int latStart2 = 0;
         int latFinish = 0;

         if (ul.getX() >= 0) {
	    longStart = (int) Math.ceil(ul.getX());
	 } else {
	    longStart = (int) Math.floor(ul.getX());
	 } // end if-else
	 if (lr.getX() >= 0) {
	    longFinish = (int) Math.floor(lr.getX());
	 } else {
	    longFinish = (int) Math.ceil(lr.getX());
	 } // end if-else
	 if (lr.getY() >= 0) {
	    latStart = (int) Math.ceil(lr.getY());
	 } else {
	    latStart = (int) Math.floor(lr.getY());
	 } // end if-else
	 if (ul.getY() >= 0) {
	    latFinish = (int) Math.floor(ul.getY());
	 } else {
	    latFinish = (int) Math.ceil(ul.getY());
	 } // end if-else
         // store starting latitude and longitude for second pass of drawing
         longStart2 = longStart;
         latStart2 = latStart;
         int screenLong = (int) t.toScreen(longStart, ul.getY()).getX();
         int screenLat = (int) t.toScreen(ul.getX(), latStart).getY();
         g.setColor(Color.lightGray);
         while (longStart <= longFinish) {
            if (longStart % 10 != 0) {
	       g.drawLine(screenLong, 0, screenLong, d.height);
            } // end if
	    longStart++;
	    screenLong = (int) t.toScreen(longStart, ul.getY()).getX();
	 } // end while
	 while (latStart <= latFinish) {
            if (latStart % 10 != 0) {
	       g.drawLine(0, screenLat, d.width, screenLat);
            } // end if
	    latStart++;
	    screenLat = (int) t.toScreen(ul.getX(), latStart).getY();
	 } // end while
         screenLong = (int) t.toScreen(longStart2, ul.getY()).getX();
         screenLat = (int) t.toScreen(ul.getX(), latStart2).getY();
         g.setColor(Color.green);
         while (longStart2 <= longFinish) {
            if (longStart2 % 10 == 0) {
	       g.drawLine(screenLong, 0, screenLong, d.height);
               g.drawString(String.valueOf(longStart2), screenLong + 5, 12);
            } // end if
            longStart2++;
	    screenLong = (int) t.toScreen(longStart2, ul.getY()).getX();
	 } // end while
	 while (latStart2 <= latFinish) {
            if (latStart2 % 10 == 0) {
	       g.drawLine(0, screenLat, d.width, screenLat);
               g.drawString(String.valueOf(latStart2), 2, screenLat - 2);
            } // end if
	    latStart2++;
	    screenLat = (int) t.toScreen(ul.getX(), latStart2).getY();
	 } // end while
      } // end if
   } // end paintComponent
/**
 * Handler class for Status Lat/Long
 */
/** Shows the current Lat/Long based on the mouse position
 * @param e Action event
 */
        public void mouseDragged (MouseEvent e) {}
        public void mouseMoved(MouseEvent e) {
            double latit = (t.toWorld(e.getPoint())).getY();
            double longit = (t.toWorld(e.getPoint())).getX();

//Variables for scaling algorithm of Latitiude/Longitude
               int tempLat, tempLong;
               double newTempLat, newTempLong, decLat, decLong;
               DecimalFormat twoDigits = new DecimalFormat ("0.00");

 //Scaling algorithm for Latitiude
               tempLat = (int) latit;
               newTempLat = latit - tempLat;
               if (newTempLat != 0) {
                  decLat = (.595959/.999)*newTempLat;
                  if (decLat > .59) {
                     decLat = .59;
                  }
               }
               else {
                  decLat = newTempLat;
               }
            latit = tempLat + decLat;

//Scaling algorithm for Longitude
               tempLong = (int) longit;
               newTempLong = longit - tempLong;
               if (newTempLong != 0) {
                  decLong = (.595959/.999)*newTempLong;
                  if (decLong > .59) {
                     decLong = .59;
                  }
               }
               else {
                  decLong = newTempLong;
               }
            longit = tempLong + decLong;

            if (latit >= 0 && longit >= 0) {
               MainWindow.getLatStatus().setText(twoDigits.format(Math.abs(latit)) + " N");
               MainWindow.getLongStatus().setText(twoDigits.format(Math.abs(longit)) + " E");
            }
            else if (latit >= 0 && longit < 0) {
               MainWindow.getLatStatus().setText(twoDigits.format(Math.abs(latit)) + " N");
               MainWindow.getLongStatus().setText(twoDigits.format(Math.abs(longit)) + " W");
            }
            else if (latit < 0 && longit < 0) {
               MainWindow.getLatStatus().setText(twoDigits.format(Math.abs(latit)) + " S");
               MainWindow.getLongStatus().setText(twoDigits.format(Math.abs(longit)) + " W");
            }
            else if (latit < 0 && longit >= 0) {
               MainWindow.getLatStatus().setText(twoDigits.format(Math.abs(latit)) + " S");
               MainWindow.getLongStatus().setText(twoDigits.format(Math.abs(longit)) + " E");
            }
        } // end MouseMoved

        /**
         * Handles Mouse exit events
         * @param me MouseEvent
         */
        public void mouseExited(MouseEvent me) {
            MainWindow.getLatStatus().setText(null);
            MainWindow.getLongStatus().setText(null);
            MainWindow.getLatStatus().setEnabled(false);
            MainWindow.getLongStatus().setEnabled(false);
        } // end mouseExited

        /**
         * Handles Mouse enter events
         * @param me MouseEvent
         */
        public void mouseEntered(MouseEvent me) {
            MainWindow.getLatStatus().setEnabled(true);
            MainWindow.getLongStatus().setEnabled(true);
        } // end mouseEntered

        /**
         * implements null abstract methods
         * @param me MouseEvent
         */
        public void mousePressed(MouseEvent me) {}
        public void mouseReleased(MouseEvent me) {}
        public void mouseClicked(MouseEvent me) {}


}