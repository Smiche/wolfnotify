import java.util.ArrayList;


public class PlayerID {
	String player;
	ArrayList<String> ids;
	public PlayerID(String playerName, String playerIDs){
		ids = new ArrayList<String>();
		this.player = playerName;
		ids.add(playerIDs);
	}
	
}
