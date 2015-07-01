import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.awt.TrayIcon;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class PlayerButton extends JButton {
	public static int editID;
	public String id;
	public static ArrayList<PlayerID> playerNames;
	public static ArrayList<Integer> ids;
	boolean isFirst = true;
	public String serverIP;
	public boolean isOnline = false;

	public static void addPlayerID(String playerName, String ID) {
		boolean added = false;
		for (int i = 0; i < playerNames.size(); i++) {
			if (playerNames.get(i).player.equals(playerName)) {
				playerNames.get(i).ids.add(ID);
				added = true;
			}
		}
		if (!added) {
			playerNames.add(new PlayerID(playerName, ID));
		}
	}

	public PlayerButton(String name, String id) {
		this.setForeground(Color.RED);
		this.setSize(91, 23);
		this.setText(name);
		this.id = id;
		// ids.add(id);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				StringSelection stringSelection = new StringSelection(serverIP);
				Clipboard clpbrd = Toolkit.getDefaultToolkit()
						.getSystemClipboard();
				clpbrd.setContents(stringSelection, null);

				if (arg0.getButton() == arg0.BUTTON3) {
					Object src = arg0.getSource();
					for (int i = 0; i < Main.buttons.size(); i++) {
						if (src == Main.buttons.get(i)) {
							System.out.println(i + " = ID");
							Main.buttons.remove(i);
							playerNames.remove(i);
							Main.repaintButtons();
							Main.writeIni();
						}
					}

				}
				if (SwingUtilities.isMiddleMouseButton(arg0)) {
					Main.frame3.setVisible(true);
					Object src = arg0.getSource();
					for (int i = 0; i < Main.buttons.size(); i++) {
						if (src == Main.buttons.get(i)) {
							editID = i;
							Main.textField.setText(Main.buttons.get(i).id);
							Main.textField_1.setText(Main.buttons.get(i)
									.getText());
						}
					}
				}
			}
		});
	}

	static void runCheckThread() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					for (int i = 0; i < playerNames.size(); i++) {
						boolean found = false;
						String serverIP = "";
						for (int m = 0; m < playerNames.get(i).ids.size(); m++) {
							try {
								// System.out.println("looping :/ : "+allIDs.get(m));
								URL url2 = new URL(
										"http://et.trackbase.net/player/"
												+ playerNames.get(i).ids.get(m));
								URLConnection conn2 = url2.openConnection();
								BufferedReader rd2 = new BufferedReader(
										new InputStreamReader(conn2
												.getInputStream()));
								String line2;

								String segments[];
								while ((line2 = rd2.readLine()) != null) {
									if (line2
											.contains("This user is currently playing on <a href=")) {
										found = true;
										// System.out.println(line2);
										segments = line2.split("/");
										serverIP = segments[2];
										//rd2.close();
									}

								}
								rd2.close();

							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						if(found){
								if (!Main.buttons.get(i).isOnline) {
									Main.icon
											.displayMessage(
													"User online",
													""
															+ Main.buttons
																	.get(i)
																	.getText()
															+ " is now online",
													TrayIcon.MessageType.INFO);
									Main.buttons.get(i).isOnline = true;
								}
								Main.buttons.get(i)
										.setForeground(
												Color.GREEN);
								Main.frame.validate();
								Main.frame.repaint();
								Main.buttons.get(i).serverIP = getServerIp(serverIP);

					} else {
						Main.buttons.get(i).isOnline = false;
						Main.buttons.get(i).setForeground(
								Color.RED);
						Main.frame.validate();
						Main.frame.repaint();
					}
						
					}
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}).start();
	}

	static String getServerIp(String id) {
		String IP = "no IP";
		try {
			URL url2 = new URL("http://et.trackbase.net/server/" + id);
			URLConnection conn2 = url2.openConnection();
			BufferedReader rd2 = new BufferedReader(new InputStreamReader(
					conn2.getInputStream()));
			String line2;

			while ((line2 = rd2.readLine()) != null) {
				// System.out.println(line2);
				// proverki za novi li4ni s1ob6teniq
				if (line2
						.contains("<tr class=\"rev2\"><td class=\"nc\">IP:</td><td class=\"nc nr\">")) {
					System.out.println(line2);
					IP = line2
							.replaceAll(
									"<tr class=\"rev2\"><td class=\"nc\">IP:</td><td class=\"nc nr\">",
									"");
					IP = IP.replaceAll("</td></tr>", "");

					rd2.close();
					break;
				}

			}
			rd2.close();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return IP;
	}
}
