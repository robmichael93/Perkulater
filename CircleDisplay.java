//------------------------------------------------------------------------------
// Filename:  CircleDisplay.java
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
 * This class constructs the display for a circle.
 *
 */

public class CircleDisplay extends ContactDisplay {

  /**
    * The CircleDisplay constructor builds a graphical representation (a circle)
    * that is used to identify an "Unknown" TYPE of contact when displayed.
    *@param c Contact
    */

   public CircleDisplay(Contact c) {
      super(c);
   }

   /**
    * Draws Circle
    * @param g Graphics
    */
   public void paintComponent(Graphics g) {
      g.setColor(color);
      g.drawOval(0, 0, 9, 9);
   }

}