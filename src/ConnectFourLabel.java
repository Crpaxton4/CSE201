import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * ConnectFourLabel functions as a grid position piece on a Connect Four board.
 * @author Chris Anderson
 * @author Korey Smith
 * @author Chris Paxton
 */
public class ConnectFourLabel extends JLabel {

	private boolean full;
	private char playerColor;
	
	private static final char red = 'R';
	private static final char yellow = 'Y';
	
	
	/**
	 * Constructor for a ConnectFourLabel.
	 * @param data GameModel used to play the ConnectFour game.
	 * @param controller Controller used to control the Connect Four game.
	 */
	public ConnectFourLabel(GameModel data, GameController controller) {
		this.full = false;
		this.setIcon(new ImageIcon("button.png"));
	}

	
	/**
	 * Determines if the ConnectFourLabe has a placement.
	 * @return true if the label representing a grid position on the game board is occupied.
	 */
	public boolean isFull() {
		return full;
	}
	

	/**
	 * Gets the player color occupying the ConnectFourLabel.
	 * @return the player color occupying a given position in the Connect Four grid.
	 */
	public char getPlayerColor() {
		return playerColor;
	}

	
	/**
	 * Fills in the ConnectFourLabel of a given color depending upon which player has made a placement.
	 * @param player the player's color to occupy the position on the ConnectFourLabel.
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
	
	
	/**
	 * Resets the ConnectFourLabel by setting to a default image of an empty ConnectFour square.
	 */
	public void resetConnectFourLabel(){
		setIcon(new ImageIcon("button.png"));
		full = false;
	}

} // end ConnectFourLabel