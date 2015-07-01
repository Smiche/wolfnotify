import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JTextPane;


public class Panel2 extends JFrame{
	public Panel2() {
		getContentPane().setLayout(null);
		
		JTextPane txtpnLeftClickTo = new JTextPane();
		txtpnLeftClickTo.setText("Get the user ID from et.trackbase.net\r\nCopy the number after /player/\r\nand seperate with \",\" for each ID\r\nNick is used for own reference\r\n\r\nLeft click to copy server IP\r\nRight click to remove\r\nMiddle mouse button to edit\r\n\r\ncreated by winrar\r\nspecial thanks to shannie & vallz");
		txtpnLeftClickTo.setBounds(10, 11, 234, 210);
		getContentPane().add(txtpnLeftClickTo);
	}
}
