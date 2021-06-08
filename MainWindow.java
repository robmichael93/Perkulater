//------------------------------------------------------------------------------
// Filename:  MainWindow.java
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
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.text.DecimalFormat;
import javax.net.ssl.*;
//import com.sun.net.ssl.*;
import java.security.*;
import java.security.cert.*;
import cs3773.*;

/**
 * This is the main window for the project
 *
 */

public class MainWindow extends JFrame {

/** Transformer for screen-to-world and world-to-screen transformations
 */
    protected Transformer t = new Transformer(700,400);

/** Status Bar display panel
 */
   protected JPanel main = new JPanel(new BorderLayout());
//    main.setBackground(Color.white);
/** Status Bar display panel
 */
    protected JPanel statusBar = new JPanel(new GridLayout(1,4));
/** "Battlespace" display panel
 */
    protected ContactPanel cp;
/** Vehicle for contact inputs
 */
    protected Vehicle v;
/** Container for vehicles (contacts)
 */
    protected Vector contacts = new Vector(10,5);
/** Scaling factor for refreshing the screen at appropriate time periods
 */
    private static double scalingFactor = 1.0;
/** Flag for if the state of the contact vector on the screen have changed
 */
    private boolean changeDisplay = false;
/** Flag for a valid file
 */
    private boolean isFile = false;
/** Name of file for input or output
 */
    private String fileName;

/** File New menu item
 */
    protected JMenuItem fileNew = new JMenuItem("New");
/** File Open menu item
 */
    protected JMenuItem fileOpen = new JMenuItem("Open");
/** File Close menu item
 */
    protected JMenuItem fileClose = new JMenuItem("Close");
/** File Save menu item
 */
    protected JMenuItem fileSave = new JMenuItem("Save");
/** File Exit menu item
 */
    protected JMenuItem fileExit = new JMenuItem("Exit");
/** Insert Contact menu item
 */
    protected JMenuItem insertContact = new JMenuItem("Contact");
/** Insert File menu item
 */
    protected JMenuItem insertFile = new JMenuItem("File");
/** Zoom In menu item
 */
    protected JMenu viewZoomIn = new JMenu("Zoom In");
/** Zoom In by a factor of 2
 */
    protected JMenuItem viewZoomIn2 = new JMenuItem("Zoom In 2:1");
/** Zoom In by a factor of 3
 */
    protected JMenuItem viewZoomIn3 = new JMenuItem("Zoom In 3:1");
/** Zoom In by a factor of 4
 */
    protected JMenuItem viewZoomIn4 = new JMenuItem("Zoom In 4:1");
/** Zoom Out menu item
 */
    protected JMenu viewZoomOut = new JMenu("Zoom Out");
/** Zoom out by a factor of 2
 */
    protected JMenuItem viewZoomOut2 = new JMenuItem("Zoom Out 1:2");
/** Zoom out by a factor of 3
 */
    protected JMenuItem viewZoomOut3 = new JMenuItem("Zoom Out 1:3");
/** Zoom out by a factor of 4
 */
    protected JMenuItem viewZoomOut4 = new JMenuItem("Zoom Out 1:4");
/** View Recenter menu item
 */
    protected JMenuItem viewRecenter = new JMenuItem("Recenter");
/** View Gridlines menu item
 */
    protected JCheckBoxMenuItem viewShowGridlines =
                                new JCheckBoxMenuItem("Show Gridlines", true);
/** Network Connect menu item
 */
    protected JMenu networkConnect = new JMenu("Connect");
/** Network Connect Secure menu item
 */
    protected JMenuItem connectSecure = new JMenuItem("Secure");
/** Network Connect Standard menu item
 */
    protected JMenuItem connectStandard = new JMenuItem("Standard");
/** Network Connect Text menu item
 */
    protected JMenuItem connectText = new JMenuItem("Text");
/** Network Disconnect menu item
 */
    protected JMenu networkDisconnect = new JMenu("Disconnect");
/** Help about menu item
 */
    protected JMenuItem helpAbout = new JMenuItem("About");
/** Popup menu for the "battlespace" panel
 */
    protected JPopupMenu panelPopupMenu = new JPopupMenu();
/** Popup Zoom In menu item
 */
    protected JMenu popViewZoomIn = new JMenu("Zoom In");
/** Zoom in by a factor of 2
 */
    protected JMenuItem popViewZoomIn2 = new JMenuItem("Zoom In 2:1");
/** Zoom in by a factor of 3
 */
    protected JMenuItem popViewZoomIn3 = new JMenuItem("Zoom In 3:1");
/** Zoom in by a factor of 4
 */
    protected JMenuItem popViewZoomIn4 = new JMenuItem("Zoom In 4:1");
/** Popup Zoom Out menu item
 */
    protected JMenu popViewZoomOut = new JMenu("Zoom Out");
/** Zoom out by a factor of 2
 */
    protected JMenuItem popViewZoomOut2 = new JMenuItem("Zoom Out 1:2");
/** Zoom out by a factor of 3
 */
    protected JMenuItem popViewZoomOut3 = new JMenuItem("Zoom Out 1:3");
/** Zoom out by a factor of 4
 */
    protected JMenuItem popViewZoomOut4 = new JMenuItem("Zoom Out 1:4");
/** Popup Recenter menu item
 */
    protected JMenuItem popViewRecenter = new JMenuItem("Recenter");
/** Popup Show Lat/Long menu
 */
    protected JMenuItem popViewLatLong = new JMenuItem("Lat/Long");
/** Popup Show Gridlines menu
 */
    protected JCheckBoxMenuItem popViewShowGridlines =
                                new JCheckBoxMenuItem("Show Gridlines", true);
/** Point selected for recentering
 */
    protected Point recenterPoint = null;
/**
 *  URL for contact class
 */
    private String contactClassURL = new String("http://131.120.9.148/classes/");
/**
 *  JFileChooser for selecting file to open
 */
    JFileChooser openFileChooser = new JFileChooser();
/**
 *  JFileChooser for selecting file to save to
 */
    JFileChooser saveFileChooser = new JFileChooser();
/**
 *  JFileChooser for selecting file to insert from
 */
    JFileChooser fileInsertChooser = new JFileChooser();
/**
 *  Vector containing server connections
 */
    private Vector connections = new Vector(5,1);
/**
 *  URL for the PerkUlateR Icon
 */
    private URL percolatorPath = ClassLoader.getSystemResource("percolator.gif");
/**
 *  PerkUlateR Icon
 */
    private Icon percolator = new ImageIcon(percolatorPath);
/**
 *  Label for Latitude Display
 */
    private JLabel latLabel = new JLabel("Latitude:      " );
/**
 *  Label for Latitude Status
 */
    private static JLabel latStatus = new JLabel();
/**
 *  Label for Longitude Display
 */
    private JLabel longLabel = new JLabel("Longitude:      " );
/**
 *  Label for Longitude Status
 */
    private static JLabel longStatus = new JLabel();
/**
 *  Label for spacer Display
 */
    private JLabel spacer = new JLabel();

    /** Creates new MainWindow
     *
     */
    public MainWindow() {

        super("Perkulater Battlespace Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,400);
        setLocation(200,200);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        fileMenu.add(fileNew);
        fileMenu.add(fileOpen);
        fileMenu.add(fileClose);
        fileMenu.add(fileSave);
        fileMenu.add(fileExit);
        fileMenu.setMnemonic('f');

        fileNew.addActionListener(new NewHandler());
        fileNew.setMnemonic('n');
        fileOpen.addActionListener(new OpenHandler());
        fileOpen.setMnemonic('o');
        fileClose.addActionListener(new CloseHandler());
        fileClose.setMnemonic('c');
        fileSave.addActionListener(new SaveHandler());
        fileSave.setMnemonic('s');
        fileExit.addActionListener(new ExitHandler());
        fileExit.setMnemonic('x');

        JMenu insertMenu = new JMenu("Insert");
        menuBar.add(insertMenu);
        insertMenu.add(insertContact);
        insertMenu.add(insertFile);
        insertMenu.setMnemonic('i');

        insertContact.addActionListener(new ContactHandler());
        insertContact.setMnemonic('c');
        insertFile.addActionListener(new FileHandler());
        insertFile.setMnemonic('l');

        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);
        viewMenu.add(viewZoomIn);
        viewZoomIn.add(viewZoomIn2);
        viewZoomIn.add(viewZoomIn3);
        viewZoomIn.add(viewZoomIn4);
        viewZoomIn.setMnemonic('z');
        viewMenu.add(viewZoomOut);
        viewZoomOut.add(viewZoomOut2);
        viewZoomOut.add(viewZoomOut3);
        viewZoomOut.add(viewZoomOut4);
        viewZoomOut.setMnemonic('o');
        viewMenu.add(viewRecenter);
        viewMenu.addSeparator();
        viewMenu.add(viewShowGridlines);
        viewMenu.setMnemonic('v');

        viewZoomIn2.addActionListener(new ZoomInHandler());
        viewZoomIn2.setMnemonic('2');
        viewZoomIn3.addActionListener(new ZoomInHandler());
        viewZoomIn3.setMnemonic('3');
        viewZoomIn4.addActionListener(new ZoomInHandler());
        viewZoomIn4.setMnemonic('4');
        viewZoomOut2.addActionListener(new ZoomOutHandler());
        viewZoomOut2.setMnemonic('2');
        viewZoomOut3.addActionListener(new ZoomOutHandler());
        viewZoomOut3.setMnemonic('3');
        viewZoomOut4.addActionListener(new ZoomOutHandler());
        viewZoomOut4.setMnemonic('4');
        viewRecenter.addActionListener(new RecenterHandler());
        viewRecenter.setMnemonic('r');
        viewShowGridlines.addActionListener(new GridlinesHandler());
        viewShowGridlines.setMnemonic('s');

        JMenu networkMenu = new JMenu("Network");
        menuBar.add(networkMenu);
        networkMenu.add(networkConnect);
        networkConnect.add(connectSecure);
        networkConnect.add(connectStandard);
        networkConnect.add(connectText);
        networkConnect.setMnemonic('c');
        networkMenu.add(networkDisconnect);
        networkDisconnect.setMnemonic('d');
        networkMenu.setMnemonic('n');

        connectSecure.addActionListener(new ConnectSecureHandler());
        connectSecure.setMnemonic('s');
        connectStandard.addActionListener(new ConnectStandardHandler());
        connectStandard.setMnemonic('d');
        connectText.addActionListener(new ConnectTextHandler());
        connectText.setMnemonic('t');

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        helpMenu.add(helpAbout);
        helpMenu.setMnemonic('h');

        helpAbout.addActionListener(new AboutHandler());
        helpAbout.setMnemonic('a');

        t.setWindow(-125,25,-90,45);
        cp = new ContactPanel(t);
        cp.add(panelPopupMenu);
        cp.addMouseListener(new PanelMouseHandler());
        panelPopupMenu.add(popViewZoomIn);
        popViewZoomIn.add(popViewZoomIn2);
        popViewZoomIn.add(popViewZoomIn3);
        popViewZoomIn.add(popViewZoomIn4);
        panelPopupMenu.add(popViewZoomOut);
        popViewZoomOut.add(popViewZoomOut2);
        popViewZoomOut.add(popViewZoomOut3);
        popViewZoomOut.add(popViewZoomOut4);
        panelPopupMenu.add(popViewRecenter);
        panelPopupMenu.add(popViewLatLong);
        panelPopupMenu.addSeparator();
        panelPopupMenu.add(popViewShowGridlines);

        popViewZoomIn2.addActionListener(new ZoomInHandler());
        popViewZoomIn3.addActionListener(new ZoomInHandler());
        popViewZoomIn4.addActionListener(new ZoomInHandler());
        popViewZoomOut2.addActionListener(new ZoomOutHandler());
        popViewZoomOut3.addActionListener(new ZoomOutHandler());
        popViewZoomOut4.addActionListener(new ZoomOutHandler());
        popViewRecenter.addActionListener(new PanelRecenterHandler());
        popViewLatLong.addActionListener(new ShowLatLongHandler());
        popViewShowGridlines.addActionListener(new PanelGridlinesHandler());

        statusBar.add(latLabel);
        statusBar.add(latStatus);
        statusBar.add(longLabel);
        statusBar.add(longStatus);

        latLabel.setHorizontalAlignment(11);
        longLabel.setHorizontalAlignment(11);
        statusBar.setBackground(Color.yellow);

        main.add(cp);
        main.add(statusBar, BorderLayout.SOUTH);

        setContentPane(main);

        addWindowListener(
           new WindowAdapter () {
              public void windowClosing (WindowEvent we) {
					  if(changeDisplay == true){
						  int answer = JOptionPane.showConfirmDialog(null,
							  "Would you like to save your dispay?", "Exit Program",
							  JOptionPane.YES_NO_CANCEL_OPTION,
							  JOptionPane.QUESTION_MESSAGE);
						  if(answer == JOptionPane.OK_OPTION){
							  save();
						  }//if saveIt == Y
					  }// if changeDisplay == true
					  System.exit(0);
              } // end windowClosing
           } // end WindowAdapter
        );
    }

/**
 * Handler class for File New
 */
    public class NewHandler implements ActionListener {
/** Clears the screen for a new session
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
            int answer = JOptionPane.showConfirmDialog(null,
                  "Are you Sure you want Delete all Contacts?", "New File Confirm",
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.OK_OPTION){
               vecDelete();
               isFile = false;
               changeDisplay = true;
            } // end if
        } // end actionPerformed
    } // end NewHandler

/**
 * Handler class for File Open
 */
    public class OpenHandler implements ActionListener {
/** Opens a file of contacts
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
            int returnVal = openFileChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = openFileChooser.getSelectedFile();
                fileName = file.getAbsolutePath();
                if (file.exists() == true) {
                    vecDelete();
                    vecRead(fileName);
                    isFile = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                    "File not found.", "File Not Found",
                    JOptionPane.ERROR_MESSAGE);
                }//end if-else
            } else {
                isFile = false;
            } // end if-else
        } // end actionPerformed
    } // end OpenHandler

/**
 * Handler class for File Close
 */
    public class CloseHandler implements ActionListener {
/** Closes the file associated with the current session and asks the user to
 * save the file if it has changed since it was opened or created
 *
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
       //Close Program
             if(changeDisplay == true){
                 int answer=JOptionPane.showConfirmDialog(null,
                    "Would you like to save your dispay?", "Close File",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

                 if(answer == JOptionPane.OK_OPTION){
                    save();
                 }//if answer OK
              }//if changeDisplay true

              int answer = JOptionPane.showConfirmDialog(null,
                      "Are you Sure you want Clear the Display?", "Close File Confirm",
                      JOptionPane.YES_NO_OPTION,
                      JOptionPane.QUESTION_MESSAGE);
              if (answer == JOptionPane.OK_OPTION){
		  vecDelete();
                  changeDisplay = true;
                  isFile = false;
              }//end if answer = OK
        } // end actionPerformed
    } // end CloseHandler

/**
 * Handler class for File Save
 */
    public class SaveHandler implements ActionListener {
/** Saves the current file.  If the file does not exist, the user is prompted for
 * the file name
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
           save();
        } // end actionPerformed
    } // end SaveHandler

/** Saves the current file.  If the file does not exist, the user is prompted for
 * the file name
 */
    private void save() {
        if (isFile == false) {
           saveFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
           saveFileChooser.setMultiSelectionEnabled(false);
           int answer = saveFileChooser.showSaveDialog(null);
           if (answer == JFileChooser.CANCEL_OPTION){
              return;
           } // end if
           File file = saveFileChooser.getSelectedFile();
           fileName = file.getAbsolutePath();
           if (fileName == null || fileName.equals("")) {
              JOptionPane.showMessageDialog(null,
              "Invalid File Name", "Invalid File Name",
              JOptionPane.ERROR_MESSAGE);
           } else {
              vecWrite(fileName);
              changeDisplay = false;
              isFile = true;
           } // end if-else
       } else if (changeDisplay) {
           vecWrite(fileName);
           changeDisplay = false;
       } // end if-else
    } // end save
/**
 * Handler class for File Exit
 */
    public class ExitHandler implements ActionListener {
/** Exits the program.  If the vector of contacts has changed at all, the user
 * is asked to save the file.
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
           exit();
        } // end actionPerformed
    } // end ExitHandler

/** Exits the program.  If the vector of contacts has changed at all, the user
 * is asked to save the file.
 */
    private void exit() {
       if(changeDisplay == true){
           int answer = JOptionPane.showConfirmDialog(null,
              "Would you like to save your dispay?", "Exit Program",
              JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE);
           if(answer == JOptionPane.OK_OPTION){
              save();
           }//if saveIt == Y
       }// if changeDisplay == true
       int answer = JOptionPane.showConfirmDialog(null,
           "Are you Sure you want to Exit?", "Exit Program Confirm",
           JOptionPane.YES_NO_OPTION,
           JOptionPane.QUESTION_MESSAGE);
       if (answer == JOptionPane.OK_OPTION){
           System.exit(0);
       } // end if
    } // end exit
/**
 * Handler class for Inserting Contacts
 */
    public class ContactHandler implements ActionListener {
/** Launches a dialog for inserting contacts
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
            v = new Vehicle();
            ContactInfo ci = new ContactInfo(v);
            ci.addWindowListener(new InsertContactHandler());
        }
    }

/**
 * Handler class for post-contat insertion
 */
    public class InsertContactHandler extends WindowAdapter {
/** Creates a new icon for the contact and adds it to the contact container
 * and the screen
 * @param we Window event
 */
        public void windowClosed(WindowEvent we) {
            if (v.getType() != "Tank/Destroyer") {
               synchronized (contacts) {
                  contacts.add(v);
                  ContactDisplay cd = assignDisplayType(v);
                  cp.add(cd);
                  cd.setLocation(t.toScreen(v.getLocation()));
                  cp.repaint();
						changeDisplay = true;
               } // end synchronized addition of contact
            } else {
                v = null;
                System.gc();
            } // end if-else
        } // end windowClosed
    } // end InsertContactHandler

/**
 * Handler class for Insert File
 */
    public class FileHandler implements ActionListener {
/** Appends a file of contacts to the current container.  If any duplicates are
 * found, they are essentially ignored.
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
            int returnVal = fileInsertChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File fileAppend = fileInsertChooser.getSelectedFile();
                fileName = fileAppend.getAbsolutePath();
                if(fileAppend.exists() == true){
                   vecRead(fileName);
                   changeDisplay = true;
                } else {
                   JOptionPane.showMessageDialog(null,
                   "File Not Found.", "File Not Found",
                   JOptionPane.ERROR_MESSAGE);
                }//end if-else
           } //end if
        } // end actionPerformed
    } // end FileHandler

/**
 * Handler class for Zoom In
 */
    public class ZoomInHandler implements ActionListener {
/** Zooms in the screen based on the factor the user chose
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
            double zoomInFactor = 1.0;
            if (e.getActionCommand() == "Zoom In 2:1") {
                zoomInFactor = 2.0;
            } else if (e.getActionCommand() == "Zoom In 3:1") {
                zoomInFactor = 3.0;
            } else {
                zoomInFactor = 4.0;
            } // end if-else
            Dimension d = cp.getSize();
            java.awt.geom.Point2D.Double ul = t.toWorld(0, 0);
            java.awt.geom.Point2D.Double lr = t.toWorld(d.width, d.height);
            double ulX = ul.getX();
            double ulY = ul.getY();
            double lrX = lr.getX();
            double lrY = lr.getY();
            double width = Math.abs(ulX - lrX);
            double height = Math.abs(ulY - lrY);
            double centerX = (ulX + lrX) / 2.0;
            double centerY = (ulY + lrY) / 2.0;
            double minX = centerX - width / (2.0 * zoomInFactor);
            double minY = centerY - height / (2.0 * zoomInFactor);
            double maxX = centerX + width / (2.0 * zoomInFactor);
            double maxY = centerY + height / (2.0 * zoomInFactor);
            t.setWindow(minX, minY, maxX, maxY);
            calculateScalingFactor();
            cp.repaint();
        }
    }

/**
 * Handler class for Zoom Out
 */
    public class ZoomOutHandler implements ActionListener {
/** Zooms out the screen based on the user chosen factor
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
            double zoomOutFactor = 1.0;
            if (e.getActionCommand() == "Zoom Out 1:2") {
                zoomOutFactor = 2.0;
            } else if (e.getActionCommand() == "Zoom Out 1:3") {
                zoomOutFactor = 3.0;
            } else {
                zoomOutFactor = 4.0;
            } // end if-else
            Dimension d = cp.getSize();
            java.awt.geom.Point2D.Double ul = t.toWorld(0, 0);
            java.awt.geom.Point2D.Double lr = t.toWorld(d.width, d.height);
            double ulX = ul.getX();
            double ulY = ul.getY();
            double lrX = lr.getX();
            double lrY = lr.getY();
            double width = Math.abs(ulX - lrX);
            double height = Math.abs(ulY - lrY);
            double centerX = (ulX + lrX) / 2.0;
            double centerY = (ulY + lrY) / 2.0;
            double minX = centerX - (width / 2.0) * zoomOutFactor;
            double minY = centerY - (height / 2.0) * zoomOutFactor;
            double maxX = centerX + (height / 2.0) * zoomOutFactor;
            double maxY = centerY + (height / 2.0) * zoomOutFactor;
            // check to see if recentering would draw over the North pole and
            // if so, shift world view so 90 degrees North is the upper limit
            // and the height of the world view remains the same; the same applies
            // to the South pole
            if (maxY > 90 && minY >= -90) {
               maxY = 90;
               minY = 90 - height * zoomOutFactor;
            } else if (minY < -90 && maxY <= 90) {
               minY = -90;
               maxY = -90 + height * zoomOutFactor;
            } else if (maxY > 90 && minY < -90) { // both minY and maxY exceed the limits
               maxY = 90;
               minY = -90;
            } // end if-else
            // check to see if the world window would stradle the +/- 180 degree
	    // meridian; if so, don't project the world past it and maintain the
            // width of the screen
            if (minX < -180 && maxX <= 180) {
               minX = -180;
               maxX = -180 + width * zoomOutFactor;
            } else if (maxX > 180 && minX >= -180) {
               minX = 180 - width * zoomOutFactor;
               maxX = 180;
            } else if (minX < -180 && maxX > 180) {
					minX = -180;
					maxX = 180;
            } // end if-else
            t.setWindow(minX, minY, maxX, maxY);
            calculateScalingFactor();
            cp.repaint();
        }
    }

/**
 * Handler class for View Recenter
 */
    public class RecenterHandler implements ActionListener {
/** Launches a recentering dialog
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
            Dimension d = cp.getSize();
            RecenterDialog rd = new RecenterDialog(t, cp, d.width, d.height);
            rd.addWindowListener(new RecenterDialogHandler());
        }
    }

/**
 * Handler class for post-recenter dialog operations
 */
    public class RecenterDialogHandler extends WindowAdapter {
/** Redraws the screen after a recenter
 * @param we Window event
 */
        public void windowClosed(WindowEvent we) {
            cp.repaint();
        }
    }


/**
 * Handler class for View Show Gridlines
 */
    public class GridlinesHandler implements ActionListener {
/** Shows the gridlines if the user checks the box
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
            cp.setGridlines(viewShowGridlines.getState());
            cp.repaint();
            popViewShowGridlines.setState(viewShowGridlines.getState());
        }
    }
/**
 * Handler class for Network Connect Secure
 */
    public class ConnectSecureHandler implements ActionListener {
     /** Creates new Secure Connection
      * @param e Action event
      */
        public void actionPerformed(ActionEvent e) {
            SecureConnection sc = new SecureConnection();
            connections.addElement(sc);
            sc.start();
        } // end actionPerformed
    } // end ConnectSecureHandler

/**
 * Thread for secure connection
 */
    public class SecureConnection extends Thread {
        /**
         * Name of Host
         */
        private String hostName;
        /**
         * Internet Address
         */
        private InetAddress server;
        /**
         * Secure Socket Factory
         */
        private SSLSocketFactory socketCreator;
        /**
         * Secure Socket
         */
        private SSLSocket secure;
        /**
         * Socket Factory Maker
         */
        private SSLContext ctx;
        /**
         * Key Store
         */
        private KeyStore ks;
        /**
         * Key Store Path
         */
        private InputStream keystorePath = ClassLoader.getSystemResourceAsStream("mykeystore");
        /**
         * Trust Store
         */
        private KeyStore ts;
        /**
         * Trust Store Path
         */
        private InputStream truststorePath = ClassLoader.getSystemResourceAsStream("mytruststore");
        /**
         * Key Store Password
         */
        private char[] keypass;
        /**
         * Trust Store Password
         */
        private char[] trustpass;
        /**
         * Trust Manager Factory
         */
        private TrustManagerFactory tmf;
        /**
         * Key Manager Factory
         */
        private KeyManagerFactory kmf;
        /**
         * Variable to get Input stream
         */
        private InputStream input;
        /**
         * Variable for Contact Input Stream
         */
        private ContactInputStream contactInput;
        /**
         * Connection status variable
         */
        private boolean isConnected = false;
        /**
         * Type of connection status variable
         */
        boolean goodConnection = true;
        /**
         * Contact Variable
         */
        Contact c;
        /**
         * Vehicle Variable
         */
        Vehicle v;

        /**
         * Run Method to create secure connection thread
         */
        public void run() {
            hostName = JOptionPane.showInputDialog(null,
            "Enter IP address or host name to connect to");
            if (hostName != null) {
					try {
						server = InetAddress.getByName(hostName);
						java.security.Security.addProvider(
						new com.sun.net.ssl.internal.ssl.Provider());
						// create a SSLContext: a factory for a SSLSocketFactory
						// that will be able to create sockets that can authenticate
						ctx = SSLContext.getInstance("TLS");
						// get a KeyStore for the keystore
					   ks = KeyStore.getInstance("JKS");
					// get password
					PasswordDemo k = new PasswordDemo(null, "Keystore");
					k.show();
					keypass = k.getPass().toCharArray();
					// load in the correct keystore with password
					ks.load(new FileInputStream("mykeystore"), keypass);

					// get a KeyManagerFactory for X509 certificates
					kmf = KeyManagerFactory.getInstance("SunX509", "SunJSSE");
					// initialize the KeyManagerFactory with the keystore and password
					kmf.init(ks, keypass);
					// do the same things for the truststore
					ts = KeyStore.getInstance("JKS");
					PasswordDemo t = new PasswordDemo(null, "Trust Store");
					t.show();
					trustpass = t.getPass().toCharArray();
					ts.load(new FileInputStream("mytruststore"), trustpass);

					tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
					tmf.init(ts);
					// now initialize the SSLContext with the right keys from
					// the keystore and the truststore
					// now the SSLContext can create the proper SSLSocketFactory
					ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
					socketCreator = ctx.getSocketFactory();
					// get a SSL socket on port 54321
					secure = (SSLSocket) socketCreator.createSocket(server, 54321);
					// get an input stream from the socket and wrap a
					// contact input stream around it
					input = secure.getInputStream();
					contactInput = new ContactInputStream(input, contactClassURL);
					} catch (UnknownHostException uhe) {
						goodConnection = false;
						JOptionPane.showMessageDialog(null,
						"Host not found at that address.", "Host not found",
                  JOptionPane.ERROR_MESSAGE);
					} catch (KeyManagementException kme) {
                   goodConnection = false;
						 JOptionPane.showMessageDialog(null,
						 "Key Management Exception:\n" + kme, "Key Management Exception",
						 JOptionPane.ERROR_MESSAGE);
               } catch (KeyStoreException kse) {
                   goodConnection = false;
						 JOptionPane.showMessageDialog(null,
						 "Key Store Exception:\n" + kse, "Key Store Exception",
						 JOptionPane.ERROR_MESSAGE);
               } catch (UnrecoverableKeyException uke) {
                   goodConnection = false;
						 JOptionPane.showMessageDialog(null,
						 "Unrecoverable Key Exception:\n" + uke, "Unrecoverable Key Exception",
						 JOptionPane.ERROR_MESSAGE);
               } catch (CertificateException ce) {
                   goodConnection = false;
						 JOptionPane.showMessageDialog(null,
						 "Certificate Exception:\n" + ce, "Certificate Exception",
						 JOptionPane.ERROR_MESSAGE);
               } catch (NoSuchProviderException nspe) {
                   goodConnection = false;
						 JOptionPane.showMessageDialog(null,
						 "No Such Provider Exception:\n" + nspe, "No Such Provider Exception",
						 JOptionPane.ERROR_MESSAGE);
               } catch (NoSuchAlgorithmException nsae) {
                   goodConnection = false;
						 JOptionPane.showMessageDialog(null,
						 "Invalid Algorithm:\n" + nsae, "No Such Algorithm Exception",
						 JOptionPane.ERROR_MESSAGE);
               } catch (IOException ioe) {
                   JOptionPane.showMessageDialog(null,
                   "IO Exception during connection to host:\n" + ioe + "\nPlease try again.",
                   "IO Exception", JOptionPane.ERROR_MESSAGE);
                   goodConnection = false;
               } // end try-catch
               if (goodConnection) {
                   setName("Secure: " + server.getHostName());
                   isConnected = true;
                   JMenuItem disconnectSecure = new JMenuItem("Secure: " + server.getHostName());
                   networkDisconnect.add(disconnectSecure);
                   disconnectSecure.addActionListener(new DisconnectHandler());
                   while (isConnected) {
                      v = new Vehicle();
                      try {
                         c = (Contact) contactInput.readObject();
                         v.setName(c.getName());
                         v.setType(c.getType());
                         v.setClassification(c.getClassification());
                         v.setIffCategory(c.getIffCategory());
                         v.setIffCode(c.getIffCode());
                         v.setLocation(c.getLocation());
                         v.setVelocity(c.getVelocity());
                         v.setLastReport(c.getLastReport());
                         vecAdd(v);
                      }//end try
                       catch (ClassNotFoundException cnfe){
                          JOptionPane.showMessageDialog(null,
                          "Class not found:\n" + cnfe, "Class Not Found Exception",
                          JOptionPane.ERROR_MESSAGE);
                       }//end catch
                       catch (ClassCastException cce) {
                          JOptionPane.showMessageDialog(null,
                          "Class Cast Exception:\n" + cce, "Class Cast Exception",
                          JOptionPane.ERROR_MESSAGE);
                       } // end catch
                       catch (OptionalDataException ode) {
                          JOptionPane.showMessageDialog(null,
                          "Optional Data Exception:\n" + ode, "Optional Data Exception",
                          JOptionPane.ERROR_MESSAGE);
                       } // end catch
                       catch (SocketException se) { // connection lost
                          reconnect();
                       } // end catch
                       catch (EOFException eofe) {
                          reconnect();
                       } // end catch
                       catch (IOException ioe) {
                          JOptionPane.showMessageDialog(null,
                          "IO Exception:\n" + ioe, "IO Exception",
                          JOptionPane.ERROR_MESSAGE);
                       } // end catch
                   }//end while
              } // end if
               try {
                   contactInput.close();
                   secure.close();
               } catch (Exception e) {
               } // end try-catch
           } // end if
       } // end run

       /**
        * Sets the connection status
        * @param status boolean
        */
        public void setConnectionStatus (boolean status) {
            isConnected = status;
        } // end setConnectionStatus
        /**
         * Sets the isConnected variable to false
         */
        private void reconnect () {
           isConnected = false;
           // close the Socket and the ContactInputStream
           try {
              contactInput.close();
              secure.close();
           } catch (IOException ioe) {
           } // end try-catch
           // make 3 attempts to reconnect to the server
           for (int i = 0; i < 3; i++) {
              if (isConnected == false) {
                 try {
                    secure = (SSLSocket) socketCreator.createSocket(hostName, 54321);
                    input = secure.getInputStream();
                    contactInput = new ContactInputStream(input, contactClassURL);
                    isConnected = true;
                 } catch (IOException ioe) {
                 } // end try-catch
              } // end if
           } // end for
           if (isConnected == false) {
              Component[] menuItems = networkDisconnect.getMenuComponents();
              int index = 0;
              for (int i = 0; i < menuItems.length; i++) {
                 if (((JMenuItem)menuItems[i]).getActionCommand().equals(getName())) {
                    index = i;
                 } // end if
              } // end for
              networkDisconnect.remove(index);
              JOptionPane.showMessageDialog(null,
                 "Communications with the Secure Server: " +
                 server.getHostName() + " lost.", "Communications Link Lost",
                 JOptionPane.ERROR_MESSAGE);
            } // end if
        } // end reconnect
    } // end SecureConnection

    /**
     * Receives password from keyboard
     */
    public class PasswordDemo extends JDialog {
    /**
     * Where the password is typed.
     */
    protected JPasswordField pass;
    /**
     * The OK button.
     */
    protected JButton okButton;
    /**
     * The cancel button.
     */
    protected JButton cancelButton;

    /**
     * The label for the field in which the password is typed.
     */
    protected JLabel passLabel;


    /**
     * Set the password that appears as the default
     * An empty string will be used if this in not specified
     * before the dialog is displayed.
     *
     * @param pass default password to be displayed.
     */
    public void setPass(String pass){
        this.pass.setText(pass);
    }

    /**
     * Set the label on the ok button.
     * "OK" is the default.
     *
     * @param ok label for the ok button.
     */
    public void setOKText(String ok){
        this.okButton.setText(ok);
    }

    /**
     * Set the label on the cancel button.
     * "Cancel" is the default.
     *
     * @param cancel label for the cancel button.
     */
    public void setCancelText(String cancel){
        this.cancelButton.setText(cancel);
    }



    /**
     * Set the label for the field in which the password is entered.
     * "Password: " is the default.
     *
     * @param pass label for the password field.
     */
    public void setPassLabel(String pass){
        this.passLabel.setText(pass);
    }


    /**
     * Get the password that was entered into the dialog before
     * the dialog was closed.
     *
     * @return the password from the password field.
     */
    public String getPass(){
        return new String(pass.getPassword());
    }

    /**
     * Did the user use the OK button or an equivelant action
     * to close the dialog box?
     * Pressing enter in the password field may be the same as
     * 'OK' but closing the dialog and pressing the cancel button
     * are not.
     *
     * @return true if the the user hit OK, false if the user cancelled.
     */
    public boolean okPressed(){
        return pressed_OK;
    }

    /**
     * update this variable when the user makes an action
     */
    private boolean pressed_OK = false;

    /**
     * Create this dialog with the given parent and title.
     *
     * @param parent window from which this dialog is launched
     * @param title the title for the dialog box window
     */
    public PasswordDemo(Frame parent, String title) {
        super(parent, title, true);
        setLocationRelativeTo(parent);
        // super calls dialogInit, so we don't need to do it again.
    }

    /**
     * Called by constructors to initialize the dialog.
     */
    protected void dialogInit(){


        pass = new JPasswordField("", 20);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        passLabel = new JLabel("Password: ");

        super.dialogInit();

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Object source = e.getSource();
                pressed_OK = (source == pass || source == okButton);
                PasswordDemo.this.hide();
            }
        };

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.insets.top = 5;
        c.insets.bottom = 5;
        JPanel pane = new JPanel(gridbag);
        pane.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
        JLabel label;

        c.anchor = GridBagConstraints.EAST;
        c.gridy = 1;
        gridbag.setConstraints(passLabel, c);
        pane.add(passLabel);

        gridbag.setConstraints(pass, c);
        pass.addActionListener(actionListener);
        pane.add(pass);

        c.gridy = 2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.CENTER;
        JPanel panel = new JPanel();
        okButton.addActionListener(actionListener);
        panel.add(okButton);
        cancelButton.addActionListener(actionListener);
        panel.add(cancelButton);
        gridbag.setConstraints(panel, c);
        pane.add(panel);

        getContentPane().add(pane);

        pack();
    }
	}

/**
 * Handler class for Network Connect Standard
 */
    public class ConnectStandardHandler implements ActionListener {
     /** Creates new Standard Connection
      * @param e Action event
      */
        public void actionPerformed(ActionEvent e) {
            StandardConnection sc = new StandardConnection();
            connections.addElement(sc);
            sc.start();
        } // end actionPerformed
    } // end ConnectStandardHandler
/**
 * Thread for standard connection
 */
    public class StandardConnection extends Thread {
        /**
         * Name of Host
         */
        private String hostName;
        /**
         * Internet Address
         */
        private InetAddress server;
        /**
         * Standard Socket
         */
        private Socket standard;
        /**
         * Variable to get Input stream
         */
        private InputStream input;
        /**
         * Variable for Contact Input Stream
         */
        private ContactInputStream contactInput;
        /**
         * Connection status variable
         */
        private boolean isConnected = false;
        /**
         * Type of connection status variable
         */
        private boolean goodConnection = true;
        /**
         * Contact Variable
         */
        private Contact c;
        /**
         * Vehicle Variable
         */
        private Vehicle v;

        /**
         * Run Method to create standard connection thread
         */
        public void run() {
            hostName = JOptionPane.showInputDialog(null,
            "Enter IP address or host name to connect to");
            if (hostName != null) {
                try {
                    server = InetAddress.getByName(hostName);
                    standard = new Socket(server, 12345);
                    input = standard.getInputStream();
                    contactInput = new ContactInputStream(input, contactClassURL);
                } catch (UnknownHostException uhe) {
                    JOptionPane.showMessageDialog(null,
                    "Host not found at that address.", "Host not found",
                    JOptionPane.ERROR_MESSAGE);
						  goodConnection = false;
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(null,
                    "IO Exception during connection to host.\nPlease try again.",
                    "IO Exception", JOptionPane.ERROR_MESSAGE);
                    goodConnection = false;
                } // end try-catch
                if (goodConnection) {
                    setName("Standard: " + server.getHostName());
                    isConnected = true;
                    JMenuItem disconnectStandard = new JMenuItem("Standard: " + server.getHostName());
                    networkDisconnect.add(disconnectStandard);
                    disconnectStandard.addActionListener(new DisconnectHandler());
                    while (isConnected) {
                       v = new Vehicle();
                       try {
                          c = (Contact)contactInput.readObject();
                          v.setName(c.getName());
                          v.setType(c.getType());
                          v.setClassification(c.getClassification());
                          v.setIffCategory(c.getIffCategory());
                          v.setIffCode(c.getIffCode());
                          v.setLocation(c.getLocation());
                          v.setVelocity(c.getVelocity());
                          v.setLastReport(c.getLastReport());
                          vecAdd(v);
                       }//end try
                       catch (ClassNotFoundException cnfe){
                          JOptionPane.showMessageDialog(null,
                          "Class not found:\n" + cnfe, "Class Not Found Exception",
                          JOptionPane.ERROR_MESSAGE);
                       }//end catch
                       catch (ClassCastException cce) {
                          JOptionPane.showMessageDialog(null,
                          "Class Cast Exception:\n" + cce, "Class Cast Exception",
                          JOptionPane.ERROR_MESSAGE);
                       } // end catch
                       catch (OptionalDataException ode) {
                          JOptionPane.showMessageDialog(null,
                          "Optional Data Exception:\n" + ode, "Optional Data Exception",
                          JOptionPane.ERROR_MESSAGE);
                       } // end catch
                       catch (SocketException se) { // connection lost
                          reconnect();
                       } // end catch
                       catch (EOFException eofe) {
                          reconnect();
                       } // end catch
                       catch (IOException ioe) {
                          JOptionPane.showMessageDialog(null,
                          "IO Exception:\n" + ioe, "IO Exception",
                          JOptionPane.ERROR_MESSAGE);
                       } // end catch
                    }//end while
               } // end if
                try {
                    contactInput.close();
                    standard.close();
                } catch (Exception e) {
                } // end try-catch
            } // end if
        } // end run

        /**
         * Sets the connection status
         * @param status boolean
         */
        public void setConnectionStatus (boolean status) {
            isConnected = status;
        } // end setConnectionStatus

        /**
         * Sets the isConnected variable to false
         */
        private void reconnect() {
           isConnected = false;
           // close the Socket and the ContactInputStream
           try {
              contactInput.close();
              standard.close();
           } catch (IOException ioe) {
           } // end try-catch
           // make 3 attempts to reconnect to the server
           for (int i = 0; i < 3; i++) {
              if (isConnected == false) {
                 try {
                    standard = new Socket(server, 12345);
                    input = standard.getInputStream();
                    contactInput = new ContactInputStream(input, contactClassURL);
                    isConnected = true;
                 } catch (IOException ioe) {
                 } // end try-catch
              } // end if
           } // end for
           if (isConnected == false) {
              Component[] menuItems = networkDisconnect.getMenuComponents();
              int index = 0;
              for (int i = 0; i < menuItems.length; i++) {
                 if (((JMenuItem)menuItems[i]).getActionCommand().equals(getName())) {
                    index = i;
                 } // end if
              } // end for
              networkDisconnect.remove(index);
              JOptionPane.showMessageDialog(null,
                 "Communications with the Standard Server: " +
                 server.getHostName() + " lost.", "Communications Link Lost",
                 JOptionPane.ERROR_MESSAGE);
            } // end if
        } // end reconnect
    } // end StandardConnection

/**
 * Handler class for Network Connect Text
 */
    public class ConnectTextHandler implements ActionListener {
         /** Creates new Text Connection
          * @param e Action event
          */
        public void actionPerformed(ActionEvent e) {
            TextConnection tc = new TextConnection();
            connections.addElement(tc);
            tc.start();
        } // end actionPerformed
    } // end ConnectTextHandler
/**
 * Thread for text connection
 */
    public class TextConnection extends Thread {
        /**
         * Name of Host
         */
        private String hostName;
        /**
         * Internet Address
         */
        private InetAddress server;
        /**
         * Text Socket
         */
        private Socket text;
        /**
         * Variable to get Input stream
         */
        private InputStream input;
        /**
         * Variable for Contact Input Stream
         */
        private BufferedReader contactInput;
        /**
         * Connection status variable
         */
        private boolean isConnected = false;
        /**
         * Type of connection status variable
         */
        private boolean goodConnection = true;
        /**
         * Vehicle Variable
         */
        private Vehicle v;
        /**
         * Contact input line
         */
        private String readLine;
        /**
         * Key string
         */
        private String key;
        /**
         * Value string
         */
        private String value;
        /**
         * Variable to determine where to start parsing text
         */
        private int start;
        /**
         * Variable to determine where to end parsing text
         */
        private int end;
        /**
         * Location string
         */
        private String location;
        /**
         * Index string
         */
        private int colonIndex;
        /**
         * Hemisphere variable
         */
        private int hemisphere;
        /**
         * Degree variable
         */
        private String degrees;
        /**
         * Minutes variable
         */
        private String minutes;
        /**
         * Degrees Latitude variable
         */
        private int latDegs;
        /**
         * Minutes Latitude variable
         */
        private double latMins;
        /**
         * Degrees Longitude variable
         */
        private int longDegs;
        /**
         * Minutes Longitude variable
         */
        private double longMins;
        /**
         * Altitude variable, default = 0
         */
        private int altitude = 0;
        /**
         * Location variable, default = 0
         */
        private Location loc;
        /**
         * Heading variable, default = 0
         */
        private int heading = 0;
        /**
         * Speed variable, default = 0
         */
        private double speed = 0;
        /**
         * Velocity variable
         */
        private Velocity vel;
        /**
         * Classification Variable, default unknown
         */
        private String classification = new String("UNKNOWN");
        /**
         * IFF Category variable, default unknown
         */
        private String iffCategory = new String("UNKNOWN");
        /**
         * report time variable
         */
        private java.util.Date reportTime = new java.util.Date();
        /**
         * Time variable
         */
        private long time;
        /**
         * Data storage variable
         */
        private Hashtable storedData = new Hashtable();
        /**
         * Input validity indicator
         */
        private boolean validInput = true;

        /**
         * Run Method to create text connection thread
         */
        public void run() {
            hostName = JOptionPane.showInputDialog(null,
            "Enter IP address or host name to connect to");
            if (hostName != null) {
                try {
                    server = InetAddress.getByName(hostName);
                    text = new Socket(server, 9876);
                    input = text.getInputStream();
						  contactInput = new BufferedReader(new InputStreamReader(input));
                } catch (UnknownHostException uhe) {
                    JOptionPane.showMessageDialog(null,
                    "Host not found at that address.", "Host not found",
                    JOptionPane.ERROR_MESSAGE);
						  goodConnection = false;
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(null,
                    "IO Exception during connection to host.\nPlease try again.",
                    "IO Exception", JOptionPane.ERROR_MESSAGE);
                    goodConnection = false;
                } // end try-catch
                if (goodConnection) {
                    setName("Text: " + server.getHostName());
                    isConnected = true;
                    JMenuItem disconnectText = new JMenuItem("Text: " + server.getHostName());
                    networkDisconnect.add(disconnectText);
                    disconnectText.addActionListener(new DisconnectHandler());
                    while (isConnected) {
                        try {
                            // consume all input until the CONTACT tag is reached
                            readLine = contactInput.readLine();
                            while (!readLine.equals("<CONTACT>")) {
                                readLine = contactInput.readLine();
                            } // end while
                            // read in the next line
                            readLine = contactInput.readLine();
                            // process all info until the closing CONTACT tag
                            validInput = true; // reinitialize flag
                            while (validInput && !readLine.equals("</CONTACT>")) {
                                start = readLine.indexOf("<");
                                end = readLine.indexOf(">");
                                key = readLine.substring(start, end + 1);
                                validInput = validTag(key);
                                value = readLine.substring(end + 1);
                                storedData.put(key, value);
                                readLine = contactInput.readLine();
                            } // end while
                            if (validInput) { // don't process unless tags are valid
                                v = new Vehicle();
                                // create a contact from that data
                                v.setName((String)storedData.get("<NAME>"));
                                // check for a TYPE tag
                                if (storedData.containsKey("<TYPE>")) {
                                    v.setType((String)storedData.get("<TYPE>"));
                                } else { // set default value for TYPE
                                    v.setType("");
                                } // end if-else
                                location = (String) storedData.get("<LOCATION>");
                                // parse location data
                                // set multiplier for hemisphere
                                if (location.charAt(0) == 'N') {
                                    hemisphere = 1;
                                } else {
                                    hemisphere = -1;
                                } // end if
                                colonIndex = location.indexOf(':');
                                // get latitude degrees
                                degrees = location.substring(1,colonIndex);
                                try {
                                    latDegs = hemisphere * Integer.parseInt(degrees);
                                    end = location.indexOf(' ');
                                    // get latitude minutes
                                    minutes = location.substring(colonIndex + 1, end);
                                    latMins = hemisphere * Double.parseDouble(minutes);
                                    // find start of longitude
                                    start = location.indexOf('W');
                                    if (start == -1) {  // hemisphere must be east
                                        start = location.indexOf('E');
                                    } // end if
                                    // set multiplier for hemisphere
                                    if (location.charAt(start) == 'E') {
                                        hemisphere = 1;
                                    } else {
                                        hemisphere = -1;
                                    } // end if
                                    colonIndex = location.lastIndexOf(":");
                                    // get longitude degrees
                                    degrees = location.substring(start + 1,colonIndex);
                                    longDegs = hemisphere * Integer.parseInt(degrees);
                                    // get longitude minutes
                                    minutes = location.substring(colonIndex + 1);
                                    longMins = hemisphere * Double.parseDouble(minutes);
                                    // check for an ALTITUDE tag
                                    if (storedData.containsKey("<ALTITUDE>")) {
                                        altitude = Integer.parseInt((String)storedData.get("<ALTITUDE>"));
                                    } // end if
                                    loc = new Location(latDegs, latMins, longDegs, longMins, altitude);
                                    v.setLocation(loc);
                                    if (storedData.containsKey("<CLASS>")) {
                                        classification = (String) storedData.get("<CLASS>");
                                        if (classification.equals("SEA")) {
                                            v.setClassification(1);
                                        } else if (classification.equals("AIR")) {
                                            v.setClassification(2);
                                        } else if (classification.equals("LAND")) {
                                            v.setClassification(3);
                                        } else {
                                            v.setClassification(0);
                                        } // end if-else
                                    } else {
                                        v.setClassification(0);
                                    } // end if-else
                                    if (storedData.containsKey("<IFFCAT>")) {
                                        iffCategory = (String) storedData.get("<IFFCAT>");
                                        if (iffCategory.equals("FRIENDLY")) {
                                            v.setIffCategory(1);
                                        } else if (iffCategory.equals("HOSTILE")) {
                                            v.setIffCategory(2);
                                        } else if (iffCategory.equals("NEUTRAL")) {
                                            v.setIffCategory(3);
                                        } else {
                                            v.setIffCategory(0);
                                        } // end if-else
                                    } else {
                                        v.setIffCategory(0);
                                    } // end if-else
                                    if (storedData.containsKey("<IFF>")) {
                                        v.setIffCode((String)storedData.get("<IFF>"));
                                    } else {
                                        v.setIffCode("");
                                    } // end if-else
                                    // parse date information
                                    time = java.util.Date.parse((String)storedData.get("<REPORTED>"));
                                    reportTime.setTime(time);
                                    v.setLastReport(reportTime);
                                    if (storedData.containsKey("<HEADING>")) {
                                        heading = Integer.parseInt((String)storedData.get("<HEADING>"));
                                    } // end if
                                    if (storedData.containsKey("<SPEED>")) {
                                        speed = Double.parseDouble((String)storedData.get("<SPEED>"));
                                    } // end if
                                    vel = new Velocity((double)heading, speed);
                                    v.setVelocity(vel);
                                // stop processing if input data is invalid
                                } catch (NumberFormatException nfe) {
                                    validInput = false;
                                } catch (InvalidLocationException ile) {
                                    validInput = false;
                                } catch (InvalidVelocityException ive) {
                                    validInput = false;
                                } // end try-catch
                            } // end if
                        } // end try
                        catch (SocketException se) { // connection lost
                           reconnect();
                        }
                        catch (IOException ioe) {
									JOptionPane.showMessageDialog(null,
									"IO Exception:\n" + ioe, "IO Exception",
									JOptionPane.ERROR_MESSAGE);
                           validInput = false;
                        } // end try-catch
                        if (validInput) {
                            vecAdd(v);
                        } // end if
                    }//end while
               } // end if
                try {
                    contactInput.close();
                    text.close();
                } catch (Exception e) {
                } // end try-catch
            } // end if
        } // end run

        /**
         * Checks for a valid Tag
         * @param tag String
         * @return which valid tag that is found
         */
        private boolean validTag(String tag) {
            return (tag.equals("<NAME>") || tag.equals("<TYPE>") ||
                    tag.equals("<LOCATION>") || tag.equals("<ALTITUDE>") ||
                    tag.equals("<CLASS>") || tag.equals("<IFFCAT>") ||
                    tag.equals("<IFF>") || tag.equals("<REPORTED>") ||
                    tag.equals("<HEADING>") || tag.equals("<SPEED>"));
        } // end validTag

        /**
         * Sets connection status
         * @param status boolean
         */
        public void setConnectionStatus (boolean status) {
            isConnected = status;
        } // end setConnectionStatus

        /**
         * sets validInput and isConnected variables to false
         */
        private void reconnect() {
           validInput = false;
           isConnected = false;
           // close the Socket and the ContactInputStream
           try {
              contactInput.close();
              text.close();
           } catch (IOException ioe) {
           } // end try-catch
           // make 3 attempts to reconnect to the server
           for (int i = 0; i < 3; i++) {
              if (isConnected == false) {
                 try {
                    text = new Socket(server, 9876);
                    input = text.getInputStream();
                    contactInput =  new BufferedReader(new InputStreamReader(input));
                    isConnected = true;
                 } catch (IOException ioe) {
                 } // end try-catch
              } // end if
           } // end for
           if (isConnected == false) {
              Component[] menuItems = networkDisconnect.getMenuComponents();
              int index = 0;
              for (int i = 0; i < menuItems.length; i++) {
                 if (((JMenuItem)menuItems[i]).getActionCommand().equals(getName())) {
                    index = i;
                 } // end if
              } // end for
              networkDisconnect.remove(index);
              JOptionPane.showMessageDialog(null,
                 "Communications with the Text Server: " +
                 server.getHostName() + " lost.", "Communications Link Lost",
                 JOptionPane.ERROR_MESSAGE);
            } // end if
        }

    } // end TextConnection

    /**
     * Handles disconnecting the socket
     */
    public class DisconnectHandler implements ActionListener {
    /**
     * Disconnects the socket
     * @param e ActionEvent
     */
        public void actionPerformed(ActionEvent e) {
            int answer = JOptionPane.showConfirmDialog(null,
                 "Are you Sure you want to Disconnect?", "Disconnect Confirm",
                 JOptionPane.YES_NO_OPTION,
                 JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.OK_OPTION){
                Thread connection = getThread(e.getActionCommand());
                try {
                    if (connection instanceof SecureConnection) {
                        ((SecureConnection)connection).setConnectionStatus(false);
                        ((SecureConnection)connection).join();
                    } else if (connection instanceof StandardConnection) {
                        ((StandardConnection)connection).setConnectionStatus(false);
                        ((StandardConnection)connection).join();
                    } else { // must be a TextConnection
                        ((TextConnection)connection).setConnectionStatus(false);
                        ((TextConnection)connection).join();
                    } // end if-else
                } catch (InterruptedException ie) {
                } // end try-catch
                JMenuItem remove = ((JMenuItem)e.getSource());
                remove.removeActionListener(this);
                networkDisconnect.remove(remove);
                remove = null; // set the JMenuItem up for garbage collection
					 connections.remove(connection);
					 connection = null; // set the Thread up for garbage collection
            } // end if
        } // end actionPerformed
    } // end DisconnectHandler

    /**
     * Gets all the threads from the vector
     * @param current String
     * @return connection thread
     */
    public Thread getThread(String current) {
        Thread connection;
        for (int i = 0; i < connections.size(); i++) {
            connection = (Thread) connections.elementAt(i);
            if (current.compareToIgnoreCase(connection.getName()) == 0) {
                return connection;
            } // end if
        } // end for
        return null;
    } // end getThreadIndex

/**
 * Handler class for Help About
 */
    public class AboutHandler implements ActionListener {
/** The authors of this program
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
           JOptionPane.showMessageDialog(null,
           "Programmed by:  {PerkUlateR} keith PERKins, steve ULATE, Rob michael",
           "Help About Perkulater Battlespace Manager",JOptionPane.PLAIN_MESSAGE,
           percolator);
        }
    }

/**
 * Handler class for Popup recentering
 */
    public class PanelRecenterHandler implements ActionListener {
/** Recenters the screen based on the mouse position
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
            double centerX = (t.toWorld(recenterPoint)).getX();
            double centerY = (t.toWorld(recenterPoint)).getY();
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
            // and the height of the world view remains the same; the same applies
            // to the South pole
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
            // check to see if the world window would stradle the +/- 180 degree
				// meridian; if so, don't project the world past it and maintain the
				// width of the screen
            if (minX < -180 && maxX <= 180) {
               minX = -180;
               maxX = -180 + worldWidth;
            } else if (maxX > 180 && minX >= -180) {
               minX = 180 - worldWidth;
               maxX = 180;
            } else if (minX < -180 && maxX > 180) {
					minX = -180;
					maxX = 180;
            } // end if-else
            t.setWindow(minX, minY, maxX, maxY);
            cp.repaint();
        }
    }

/**
 * Handler class for Popup Lat/Long
 */
  public class ShowLatLongHandler implements ActionListener {
/** Shows the current Lat/Long based on the mouse position
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
            double latit = (t.toWorld(recenterPoint)).getY();
            double longit =(t.toWorld(recenterPoint)).getX();

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

            latit = tempLat + decLat;
            longit = tempLong + decLong;

            if (latit >= 0 && longit >= 0) {
            JOptionPane.showMessageDialog (null,"Latitude: " + " " + twoDigits.format(Math.abs(latit)) +
            " " + "N " + "\n" + "Longitude: " + " " + twoDigits.format(Math.abs(longit)) + " " + "E",
            "Latitude/Longitude", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (latit >= 0 && longit < 0) {
            JOptionPane.showMessageDialog (null,"Latitude: " + " " + twoDigits.format(Math.abs(latit)) +
            " " + "N " + "\n" + "Longitude: " + " " + twoDigits.format(Math.abs(longit)) + " " +  "W",
            "Latitude/Longitude", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (latit < 0 && longit < 0) {
            JOptionPane.showMessageDialog (null,"Latitude: " + " " + twoDigits.format(Math.abs(latit)) +
            " " + "S" + "\n" + "Longitude: " + " " + twoDigits.format(Math.abs(longit)) + " " + "W",
            "Latitude/Longitude", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (latit < 0 && longit >= 0) {
            JOptionPane.showMessageDialog (null,"Latitude: " + " " + twoDigits.format(Math.abs(latit)) +
            " " + "S" + "\n" + "Longitude: " + " " + twoDigits.format(Math.abs(longit)) + " " + "E",
            "Latitude/Longitude", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Gets Latitude status
     * @return Latitude status
     */
    public static JLabel getLatStatus() {
      return latStatus;
    }
    /**
     * Gets Longitude status
     * @return Longitude status
     */
    public static JLabel getLongStatus() {
      return longStatus;
    }
/**
 * Handler class for Popup gridline selection
 */
    public class PanelGridlinesHandler implements ActionListener {
/** Shows the gridlines based on user selection
 * @param e Action event
 */
        public void actionPerformed(ActionEvent e) {
            cp.setGridlines(popViewShowGridlines.getState());
            cp.repaint();
            viewShowGridlines.setState(popViewShowGridlines.getState());
        }
    }

/**
 * Handler class for mouse events on the panel
 */
   public class PanelMouseHandler extends MouseAdapter {
/** Brings up a context menu for right clicks
 * @param me Mouse event
 */
       public void mouseReleased (MouseEvent me) {
          if (me.isPopupTrigger()) {
              recenterPoint = me.getPoint();
              panelPopupMenu.show(me.getComponent(), me.getX(), me.getY());
          } // end if
       } // end MouseReleased
   } // end PanelMouseHandler

/**
 * Assigns a display icon to a contact based on its type (surface, air, etc.)
 * @param v Vehicle (contact) being assigned an icon
 * @return ContactDisplay An icon object for the contact
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


/**
 * Determines a "scaling factor" for screen refreshing.  A 100 square nm area
 * has a factor of one.  Larger areas have a smaller scaling factor and vice
 * versa.  Timing of screen refreshes will be 1 second divided by the scaling
 * factor.
 */
    public void calculateScalingFactor() {

       Dimension d = cp.getSize();
       java.awt.geom.Point2D.Double ul = t.toWorld(0, 0);
       java.awt.geom.Point2D.Double lr = t.toWorld(d.width, d.height);
       double ulX = ul.getX();
       double ulY = ul.getY();
       double lrX = lr.getX();
       double lrY = lr.getY();
       double worldWidth = (lrX - ulX);
       double worldHeight = (ulY - lrY);
       scalingFactor = Math.abs(100.0 / (worldWidth * worldHeight));
    } // end calculateScalingFactor

/**
 * Adds a contact to the container and displays it on the screen
 * @param tempVehicle Vehicle to be added
 */
   public void vecAdd(Contact contact){
      if (contacts.size() > 0) {
         int target = -1;
         int compare = -1;
         int index = 0;
         boolean finished = false;
         Contact tempContact = null;
         do {
            tempContact = (Contact) contacts.elementAt(index);
            compare = contact.getName().compareToIgnoreCase(tempContact.getName());
            if(compare == 0){
               target = index;
               finished = true;
            }//end if
            index++;
         } while (finished == false && index < contacts.size());
         if (target > -1){
            // remove the old unupdated contact from the container vector
            synchronized (contacts) {
               contacts.removeElementAt(target);
               // find the corresponding contact display panel and remove it from
               // the contact panel
               Component[] kids = cp.getComponents();
               // reset the finished flag and counter
               finished = false;
               index = 0;
               // find the contact to be updated and remove it from the contact panel
               do {
                  if ((kids[index] instanceof ContactDisplay)) {
                     if ((((ContactDisplay)kids[index]).getContact()).getName().compareTo(tempContact.getName()) == 0) {
                         cp.remove((ContactDisplay)kids[index]);
                         cp.repaint();
                         finished = true;
                     } // end if
                  } // end if
                  index++;
               } while (finished == false && index < kids.length);
            } // end synchronized method on contacts
         }//end if
      } // end if
      // add in the new or updated contact to the container vector
      synchronized (contacts) {
         contacts.addElement(contact);
         // assign the updated display type
         ContactDisplay cd = assignDisplayType(contact);
         // add the contact display to the contact panel
         cp.add(cd);
         cd.setLocation(t.toScreen(contact.getLocation()));
         cp.repaint();
         changeDisplay = true;
      } // end synchronized method on contacts
   }//end add

/**
 * Deletes all contacts from the container and the screen
 */
   public void vecDelete() {
      if (contacts.isEmpty() == false){
         synchronized (contacts) {
            contacts.removeAllElements();
            cp.removeAll();
         } // end synchronized
      }//end if
   }//end vecDelete

/**
 * Writes the container vector of contacts to file
 * @param fileName The name of the file being written to
 */
   public void vecWrite(String fileName){
      try {
         FileOutputStream fileOut;
         fileOut = new FileOutputStream(fileName);
         ObjectOutputStream objectOut;
         objectOut = new ObjectOutputStream(fileOut);
         for (int i = 0; i < contacts.size(); i++){
            objectOut.writeObject(contacts.elementAt(i));
            objectOut.flush();
         }//end for
         objectOut.close();
      }//end try
      catch(FileNotFoundException FNFE){
         JOptionPane.showMessageDialog(null,
         "File not found.", "File Not Found",
         JOptionPane.ERROR_MESSAGE);
      }//end catch
      catch (IOException IOE){
            JOptionPane.showMessageDialog(null,
            "IO Exception during file write:\n" + IOE, "IO Exception",
            JOptionPane.ERROR_MESSAGE);
      }//end catch
   }//end vecWrite

/**
 * Reads in contact objects from a file
 * @param fileName The name of file being read from
 */
   public void vecRead(String fileName){
      try {
         FileInputStream fileIn;
         fileIn = new FileInputStream(fileName);
         ObjectInputStream objectIn;
         objectIn = new ContactInputStream(fileIn, contactClassURL);
         Contact c;
         Vehicle v;

         try {
            while (true) {
               v = new Vehicle();
               c = (Contact) objectIn.readObject();
               v.setName(c.getName());
               v.setType(c.getType());
               v.setClassification(c.getClassification());
               v.setIffCategory(c.getIffCategory());
               v.setIffCode(c.getIffCode());
               v.setLocation(c.getLocation());
               v.setVelocity(c.getVelocity());
               v.setLastReport(c.getLastReport());
               vecAdd(v);
            }//end while
         }//end try
         catch(FileNotFoundException FNFE){
            JOptionPane.showMessageDialog(null,
            "File not found.", "File Not Found",
            JOptionPane.ERROR_MESSAGE);
         }//end catch
         catch (EOFException eofe) {
            try {
               objectIn.close();
            } catch (IOException ioe) {
            } // end try-catch
         } // end catch
         catch (IOException IOE){
         }//end catch
         catch (ClassNotFoundException CNFE){
            JOptionPane.showMessageDialog(null,
            "Class not found:\n" + CNFE, "Class Not Found",
            JOptionPane.ERROR_MESSAGE);
         }//end catch
      }//end try
      catch(FileNotFoundException FNFE){
         JOptionPane.showMessageDialog(null,
         "File not found.", "File Not Found",
         JOptionPane.ERROR_MESSAGE);
      }//end catch
      catch(StreamCorruptedException SCE){
         JOptionPane.showMessageDialog(null,
         "Stream corrupted exception:\n" + SCE, "Stream Corrupted",
         JOptionPane.ERROR_MESSAGE);
      }//end catch
      catch(IOException IOE){
         JOptionPane.showMessageDialog(null,
         "IO Exception:\n" + IOE, "IO Exception",
         JOptionPane.ERROR_MESSAGE);
      }//end catch
   }//end vecRead

    /** The main "driver" of the program.  It schedules the periodic updates
     * of the program state.
    * @param args the command line arguments
    */

    public static void main (String args[]) {
        MainWindow mw = new MainWindow();
        mw.setVisible(true);
        mw.calculateScalingFactor();
        java.util.Timer t = new java.util.Timer(true);
        t.scheduleAtFixedRate(new Refresh(mw.cp, mw.contacts, mw.t), 0,
                             (long) (1000.0/mw.scalingFactor));
    }//ends main

}//ends MainWindow
