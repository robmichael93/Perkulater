//------------------------------------------------------------------------------
// Filename:  Vehicle.java
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
import java.io.*;
import java.util.*;
import javax.swing.*;
import cs3773.*;

/**
 * This class maintains the Vehicle ( or Contact) information.
 * @see <A HREF="www.cs.nps.navy.mil/%7Ecseagle/classes/cs3773/doc/">Contact</A>
 *
 */
 public class Vehicle implements Contact{

   /**
    * This Vehicle default constructor intializes each instance variable with
    * Monterey California Location (latitude: 36.36, longitude: 121.54, altitude: 0) and
    * Velocity (heading: 90, Speed: 100) and type: Tank/Destroyer, Name: PerkUlateR,
    * IFF: Foe.
    */

   public Vehicle(){
      try {
         loc=new Location();
         vel = new Velocity();
         loc.setLatitude(36.36);
         loc.setLongitude(121.54);
         loc.setAltitude(0);
         vel.setHeading(90);
         vel.setSpeed(vel.fromMPH(100));
         setType("Tank/Destroyer");
         setName("PerkUlateR");
         setIffCategory(2);
         setLastReport(new Date());
      }
      catch (Exception Ex2){
      }
   }//end constructor

   /**
    * Vehicle constructor
    * @param LatDeg the Degrees of the latitude
    * @param LatMin the Minutes of the latitude
    * @param LongDeg the Degrees of the longitude
    * @param LongMin the Minutes of the longitude
    * @param alt the altitude
    * @param hdg the heading
    * @param spd the speed in Meters/Second
    * @param T1 the type of vehicle / contact
    * @param N1 the name of the vehicle / contact
    * @param I1 the IFF (identification friendly or foe)
    * @param D1 a time object reflecting the time the vehicle / contact was ID'ed
    */
   public Vehicle(int LatDeg, double LatMin, int LongDeg, double LongMin,
          double alt, double hdg, double spd, String T1, String N1, int I1, Date D1){
   try {
      try {//(int latDegrees, double latMin, int lonDegrees, double lonMin, double alt)
         loc=new Location();
         loc.setLatitude(LatDeg, LatMin);
         loc.setLongitude(LongDeg, LongMin);
         loc.setAltitude(alt);
      }//end try

      catch (InvalidLocationException ILE) {
         loc.setLatitude(36, 36.0);
         loc.setLongitude(121, 54.0);
         loc.setAltitude(0);
         JOptionPane.showMessageDialog(null,
         "Invalid Location, vehicle location will be set to Monterey.", "Invalid Location",
         JOptionPane.ERROR_MESSAGE);
      }//end catch

      try {
         vel = new Velocity();
         vel.setHeading(hdg);
         vel.setSpeed(vel.fromMPH(spd));
      }//end try

      catch (InvalidVelocityException IVE) {
         vel.setHeading(90);
         JOptionPane.showMessageDialog(null,
         "Invalid Heading, vehicle heading will be set to East.", "Invalid Heading",
         JOptionPane.ERROR_MESSAGE);
      }//end catch
   }//end big try
   catch (Exception ex){
   }
      setType(T1);
      setName(N1);
      setIffCategory(I1);
      setLastReport(D1);
   }//end Vehicle Constructor

   double timeInterval;
   String type,name,iff;
   Location loc;
   Velocity vel;
   java.util.Date dat,currentTime,oldTime,time;
   MotionManager move = new MyMotionManager();
   int cat;
   int classification;

   /**
    * Project the Vehicle position using
    * <A HREF="www.cs.nps.navy.mil/%7Ecseagle/classes/cs3773/doc/cs3773/MotionManager.html">
    * MotionManager</A>'s translate method.
    * Also updates the Vehicle's date object with date and time the vehicle last
    * reported movement.
    * @see <A HREF="www.cs.nps.navy.mil/%7Ecseagle/classes/cs3773/doc/cs3773/MotionManager.html">MotionManager</A>
    * @see <A HREF="java.sun.com/j2se/1.3/docs/api/java/util/Date.html">Date</A>
    * @param p0 the currenttime
    * @return Location the projected location
    */
   public Location project(Date p0){
      currentTime=p0;
      oldTime=getLastReport();
      Location temp = new Location();
      try {
         temp.setLatitude(loc.getLatitude());
         temp.setLongitude(loc.getLongitude());
         temp.setAltitude(loc.getAltitude());
      }
      catch (InvalidLocationException e) {
         /*Had to catch, but should never be thrown. Only
         valid Location data is used */
      }
      timeInterval= (currentTime.getTime() - oldTime.getTime())/1000;
      move.translate(temp,timeInterval,vel);
      return temp;
   }//end project

   /**
    * Gets the Type of Vehicle
    * @return a <code>String</code> specifying the Vehicle Type
    */

   public String getType(){
      return type;
   }//end getType

   /**
    * Sets the type of Vehicle
    * @param p0 the Vehicle type
    */

   public void setType(String p0){
      type=p0;
   }//end setType

   /**
    * Gets the name of Vehicle
    * @return a <code>String</code> specifying the Vehicle name
    */

   public String getName(){
      return name;
   }//end getName

   /**
    * Sets the name of Vehicle
    * @param p0 the Vehicle name
    */

   public void setName(String p0){
      name=p0;
   }//end setName

   /**
    * Gets the iff code of Vehicle
    * @return a <code>String</code> specifying the Vehicle iff code
    */

   public String getIffCode(){
      return iff;
   }//end getIffCode

   /**
    * Sets the iff code of Vehicle
    * @param p0 the Vehicle iff code
    */

   public void setIffCode(String p0){
      iff = p0;
   }//end setIffCode

      /**
    * Gets the iff category of Vehicle
    * @return a <code>Integer</code> specifying the Vehicle iff category
    */

   public int getIffCategory(){
      return cat;
   }//end getIffCategory

   /**
    * Sets the iff category of Vehicle
    * @param p0 the Vehicle iff category
    */

   public void setIffCategory(int p0){
      cat=p0;
   }//end setIffCategory

      /**
    * Gets the classification of Vehicle
    * @return a <code>Integer</code> specifying the Vehicle classification
    */

   public int getClassification(){
      return classification;
   }//end getClassification

   /**
    * Sets the classification of Vehicle
    * @param p0 the Vehicle iff category
    */

   public void setClassification(int cl){
      classification = cl;
   }//end setClassification

   /**
    * Gets the location of Vehicle
    * @return a <code>Location</code> specifying the Vehicle location
    */
   public Location getLocation(){
      return loc;
   }//end getLocation

   /**
    * Sets the location of Vehicle
    * @param p0 the Vehicle location
    */
   public void setLocation(Location p0){
      loc=p0;
   }//end setLocation

   /**
    * Gets the velocity of Vehicle
    * @return a <code>Velocity</code> specifying the Vehicle velocity
    */
   public Velocity getVelocity(){
      return vel;
   }//end getVelocity

   /**
    * Sets the velocity of Vehicle
    * @param p0 the Vehicle velocity
    */
   public void setVelocity(Velocity p0){
      vel=p0;
   }//end setVelocity

   /**
    * Gets the current Date and time
    * @return a <code>Date</code> specifying the current date and time
    */
   public Date getLastReport(){
      return time;
   }//end getLastReport

   /**
    * Sets the time the Vehicle was last located
    * @param p0 the Vehicle velocity
    */
   public void setLastReport(Date p0){
      time=p0;
   }//end setLastReport

}//end vehicle