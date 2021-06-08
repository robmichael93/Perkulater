//------------------------------------------------------------------------------
// Filename:  TriangleDisplay.java
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
import cs3773.*;

/**
 * This class constructs the display for a triangle.
 *
 */

public class TriangleDisplay extends ContactDisplay {

 /**
    * The TriangleDisplay constructor builds a graphical representation (a triangle)
    * that is used to identify an "Air" TYPE of contact when displayed.
    *
    * @param c Contact
    */

   public TriangleDisplay(Contact c) {
      super(c);
   }

   /**
    * Draws the Triangle
    * @param g Graphics
    */
   public void paintComponent(Graphics g) {
      g.setColor(color);
      int[] xPoints = {4,0,8};
      int[] yPoints = {1,7,7};
      int numberOfPoints = 3;
      g.drawPolygon (xPoints, yPoints, numberOfPoints);
   }

}