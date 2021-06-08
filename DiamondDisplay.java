//------------------------------------------------------------------------------
// Filename:  DiamondDisplay.java
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
import cs3773.*;

/**
 * This class constructs the display for a diamond.
 *
 */

public class DiamondDisplay extends ContactDisplay {


 /**
    * The DiamondDisplay constructor builds a graphical representation (a diamond)
    * that is used to identify the a "Submarine" TYPE of contact when displayed.
    *@param c Contact
    */

   public DiamondDisplay(Contact c) {
      super(c);
   }

   /**
    * Draws the Diamond
    * @param g Graphics
    */
   public void paintComponent(Graphics g) {
      g.setColor(color);
      int[] xPoints = {5,1,5,9};
      int[] yPoints = {1,5,9,5};
      int numberOfPoints = 4;
      g.drawPolygon (xPoints, yPoints, numberOfPoints);
   }

}
