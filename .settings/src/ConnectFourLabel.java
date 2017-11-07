import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * CSE 201, Section A
 * Team 3
 * Chris Anderson, Chris Paxton, Korey Smith
 */
public class ConnectFourLabel extends JLabel {

	private boolean full;
	private char playerColor;
	
	private static final char red = 'R';
	private static final char yellow = 'Y';
	
	
	/*
	 * Constructor for a ConnectFourLabel.
	 */
	public ConnectFourLabel(GameModel data, GameController controller) {
		this.full = false;
		this.setIcon(new ImageIcon("button.png"));
	}

	
	/*
	 * Returns true if the label representing a grid position on the game board is occupied.
	 */
	public boolean isFull() {
		return full;
	}
	

	/*
	 * Returns the player color occupying a given position in the Connect Four grid.
	 */
	public char getPlayerColor() {
		return playerColor;
	}

	
	/*
	 * Fills in the ConnectFourLabel of a given color depending upon which player has made a placement.
	 */
	public void fill(char player) {
		if (player == yellow) {
			setIcon(null);
			setIcon(new ImageIcon("yellowButton.png"));
			full = true;
			playerColor = player;
		} 
		else if (player == red) {
			setIcon(null);
			setIcon(new ImageIcon("redButton.png"));
			full = true;
			playerColor = player;
		} 
		else {
			// error checking
			System.err.print("Incorect player identifier in label");
			System.exit(-1);
		}
	}
	
	
	/*
	 * Resets the ConnectFourLabel by setting to a default image of an empty ConnectFour square.
	 */
	public void resetConnectFourLabel(){
		setIcon(new ImageIcon("button.png"));
		full = false;
	}

}