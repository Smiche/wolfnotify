import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;








import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;



public class Main extends JFrame {
	//promenlivi	
	public static boolean newMessages = false;
	public static boolean newPosts = false;
	public static Main frame;
	public static String cookie;
	public static TrayIcon icon;
	public static JTextField textField;
	public static JTextField textField_1;
	static BufferedReader ini;
	static String read;
	static JTabbedPane pane;
	public static JPanel panel;
	public static ArrayList<PlayerButton> buttons;
	JPanel panel2;
	public static JFrame frame2;
	JButton btnAdd;
	static JTextField textField2,textField2_1;
	static JButton btnNew;
	static JButton btnExit;
	static JFrame frame3;
	static JButton leaveEdit;
	static JPanel panel3;
	//constructor na panela
	public Main() {
		PlayerButton.playerNames = new ArrayList<PlayerID>();
		setResizable(false);
		panel.setLayout(null);
		
		frame3 = new JFrame("Edit user");
		panel3 = new JPanel();
		leaveEdit = new JButton("Edit");
		leaveEdit.setBounds(85, 198, 89, 23);
		panel3.setLayout(null);
		
		leaveEdit.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				buttons.set(PlayerButton.editID,new PlayerButton(textField_1.getText(),textField.getText()));
				PlayerButton.playerNames.get(PlayerButton.editID).player = textField_1.getText();
				String[] ids = textField.getText().split(",");
				for(String seg:ids){
					PlayerButton.addPlayerID(textField_1.getText(), seg);
				}
				System.out.println("edited");
				repaintButtons();
				frame3.setVisible(false);
				writeIni();
			}
		});
		panel3.add(leaveEdit);
		
		frame2 = new JFrame("Add new user");
		panel2 = new JPanel();
		btnNew = new JButton("New");				
				panel2.setLayout(null);
				
				JLabel lblUserId = new JLabel("User ID:");
				lblUserId.setBounds(10, 11, 234, 35);
				
				JLabel lblUserId2 = new JLabel("User ID:");
				lblUserId2.setBounds(10, 11, 234, 35);
				
				panel2.add(lblUserId2);
				panel3.add(lblUserId);
				
				textField = new JTextField();
				textField.setBounds(10, 57, 136, 20);
				
				textField2 = new JTextField();
				textField2.setBounds(10, 57, 136, 20);
				
				
				panel2.add(textField2);
				panel3.add(textField);
				textField.setColumns(10);
				
				
				JLabel lblUserNick = new JLabel("User nick:");
				lblUserNick.setBounds(10, 88, 97, 14);
				
				JLabel lblUserNick2 = new JLabel("User nick:");
				lblUserNick2.setBounds(10, 88, 97, 14);
				
				panel2.add(lblUserNick2);
				panel3.add(lblUserNick);
				
				textField_1 = new JTextField();
				textField_1.setBounds(10, 113, 136, 20);
				
				textField2_1 = new JTextField();
				textField2_1.setBounds(10, 113, 136, 20);
				
				panel2.add(textField2_1);
				panel3.add(textField_1);
				textField_1.setColumns(10);
				btnAdd = new JButton("Add");
				btnAdd.setBounds(85, 198, 89, 23);				
				panel2.add(btnAdd);
				frame2.add(panel2);
				frame3.add(panel3);
				frame2.setBounds(200,200,270,270);			
				frame3.setBounds(200,200,270,270);
				btnAdd.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						PlayerButton newButton = new PlayerButton(textField2_1.getText(), textField2.getText());
						if(buttons.size()<6){
						newButton.setBounds(10,15+(30*buttons.size()),91,23);
						} else {
							newButton.setBounds(145,15+(30*(buttons.size()-5)),91,23);
						}
						
						String segments[] = textField2.getText().split(",");
						for(String seg1:segments){
							
							PlayerButton.addPlayerID(textField2_1.getText(), seg1);
						}
						buttons.add(newButton);
						repaintButtons();
						textField2_1.setText("");
						textField2.setText("");
						frame2.setVisible(false);
						writeIni();
					}
				});
		btnNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame2.setVisible(true);
			}
		});

		btnNew.setBounds(10, 208, 91, 23);
		panel.add(btnNew);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(163, 208, 91, 23);		
		btnExit.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0){
				System.exit(-1);
			}
		});
		panel.add(btnExit);
	}
	public static void repaintButtons(){
		panel.removeAll();
		panel.add(btnNew);
		panel.add(btnExit);
		for(int i =0;i<buttons.size();i++){
			if(i<6){
			buttons.get(i).setLocation(10,15+(30*i));
			} else {
				buttons.get(i).setLocation(131,15+(30*(i-6)));
			}
			panel.add(buttons.get(i));
		}
		panel.validate();
		panel.repaint();
	}
	public static void readIni() {
		try {
			ini = new BufferedReader(new FileReader("saved.ini"));
			read = ini.readLine();	
			String line1,line2;
			String[] seg1 = null;
			String[] seg2 = null;
			if(read!=null){
				seg1 = read.split(";");
			}
			read = ini.readLine();
			if(read!=null){
				seg2 = read.split(",");
			}
			if(seg1!=null)
			for(int i =0;i<seg1.length;i++){
				
				PlayerButton newButton = new PlayerButton(seg2[i], seg1[i]);
				String segments[] = seg1[i].split(",");
				for(String segm:segments){
					
				PlayerButton.addPlayerID(seg2[i], segm);
				}
				if(i<6){
				newButton.setBounds(10,15+(30*i),91,23);
				}else{
					newButton.setBounds(131,15+(30*(i-6)),91,23);
				}
				buttons.add(newButton);
				frame.repaintButtons();
				frame2.setVisible(false);
			}
			
		} catch (IOException e) {

		}
	}
	public static void writeIni(){
			try {
				BufferedWriter outi = new BufferedWriter(new FileWriter(
						"saved.ini"));
				String a = "";
				String b = "";
				for(int i =0;i<buttons.size();i++){
					a+=";"+buttons.get(i).id;
					b+=","+buttons.get(i).getText();
				}
				a = a.substring(1, a.length());
				b = b.substring(1, b.length());
				outi.write(a+"\n"+b);
				outi.flush();
				outi.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
	}
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
    	UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
    	panel = new JPanel();
    	frame = new Main();
    	JPanel readmePanel = new JPanel();
    	JLabel readme = new JLabel();
    	
    	JTextPane txtpnLeftClickTo = new JTextPane();
		txtpnLeftClickTo.setText("Get the user ID from et.trackbase.net\r\nCopy the number after /player/\r\nand seperate with \",\" for each ID\r\nNick is used for own reference\r\n\r\nLeft click to copy server IP\r\nRight click to remove\r\nMiddle mouse button to edit\r\n\r\ncreated by winrar\r\nspecial thanks to shannie & vallz");
		txtpnLeftClickTo.setBounds(10, 11, 234, 210);
		txtpnLeftClickTo.setEditable(false);
		
		readmePanel.setLayout(null);
		readmePanel.add(txtpnLeftClickTo);
		
    	icon = new TrayIcon(getIconGreen(),
       		 
                "WolfetNotify", createPopupMenu());
    	pane = new JTabbedPane();
    	ImageIcon icon2 = frame.createImageIcon("resources/wet_icon.gif","Wolfet");
    	ImageIcon icon3 = frame.createImageIcon("resources/readme.png","Wolfet");
    	pane.addTab("Users",icon2,panel,"List of added users");
    	pane.addTab("Readme",icon3,readmePanel,"Information on how to use");
    	icon.addMouseListener(new MouseAdapter(){
    	    public void mouseClicked(MouseEvent e) {
    	    	frame.setVisible(true);
    	    }
    	});
    	buttons = new ArrayList();
    	
    	  try {
    			SystemTray.getSystemTray().add(icon);
    		} catch (AWTException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	frame.add(pane);
    	frame.setBounds(200,200,270,310);
    	frame.setTitle("WolfetNotifier");
    	frame.setResizable(false);
    	frame.setVisible(true);
    	PlayerButton.ids = new ArrayList<Integer>();
    	readIni();
    	PlayerButton.runCheckThread();
    }
    
    protected ImageIcon createImageIcon(String path,
            String description) {
    	java.net.URL imgURL = getClass().getResource(path);
    	if (imgURL != null) {
    		return new ImageIcon(imgURL, description);
    	} else {
    		System.err.println("Couldn't find file: " + path);
    		return null;
    	}
    }
    private static PopupMenu createPopupMenu() throws
    
    HeadlessException {

    	PopupMenu menu = new PopupMenu();

    	MenuItem exit = new MenuItem("Exit");

    	exit.addActionListener(new ActionListener() {

    		public void actionPerformed(ActionEvent e) {

    			System.exit(0);

    		}

    	});

    	menu.add(exit);

    	return menu;
    	
    }
    
    
    //metodi za "hva6tane" na ikonkata za taskbara
    private static Image getIconGreen() throws HeadlessException {
    	Image img = null;
    	try {
    		img = ImageIO.read(Main.class.getResource("/resources/wet_icon.gif"));
    	} catch (IOException e2) {
    		e2.printStackTrace();
    	}
    	
    	return img;
    	
    	
    }
    private static Image getIconOrange() throws HeadlessException {
    	Image img = null;
    	try {
    		img = ImageIO.read(Main.class.getResource("/resources/wet_icon.gif"));
    	} catch (IOException e2) {
    		e2.printStackTrace();
    	}
    	
    	return img;
    	
    	
    }
    private static Image getIconRed() throws HeadlessException {
    	Image img = null;
    	try {
    		img = ImageIO.read(Main.class.getResource("/resources/wet_icon.gif"));
    	} catch (IOException e2) {
    		e2.printStackTrace();
    	}
    	
    	return img;
    	
    } 
}