/**
 * CSE 201, Section A
 * Team 3
 * Chris Anderson, Chris Paxton, Korey Smith
 */
public class GameModel {

	private static final char red = 'R';
	private static final char yellow = 'Y';	
	
	private int wins;
	private int losses;
	private int ties;
	private char mode;
	private char playerColor; // 'Y' for yellow, 'R' for red
	private char cpuColor; // 'Y' for yellow, 'R' for red
	private char[][] gameBoard = new char[6][7]; // representing player placements

	
	/*
	 * Constructor for GameDataLog.  Sets all wins, ties and losses to 0.
	 */
	public GameModel() {
		wins = losses = ties = 0;
	}

	
	/*
	 * Sets the color of the human player and the CPU player.
	 */
	public void setPlayerColor(char c) {
		
		// make sure that the colors of the player can only be 'R' or 'Y'
		if (c != yellow && c != red) {
			this.playerColor = yellow;
			this.cpuColor = red;
		} else {
			this.playerColor = c;

			if (this.playerColor == yellow) {
				this.cpuColor = red;
			} else {
				this.cpuColor = yellow;
			}
		}
		//System.out.print("Player Color: " + c); // to delete; for debugging purposes
	}
	
	
	/*
	 * Sets the mode / level of difficulty of the game.
	 */
	public void setMode(char mode) {
		this.mode = mode;
	}
	
	
	/*
	 * Gets the mode / level of difficulty of the game.
	 */
	public char getMode() {
		return this.mode;
	}
	
	
	/*
	 * Resets the game board by creating a new, empty 2D array.
	 */
	public void resetBoard() {
		gameBoard = new char[6][7];
	}

	
	/*
	 * Returns the piece color of the player.
	 */
	public char getPlayerColor() {
		return playerColor;
	}
	
	
	/*
	 * Returns the piece color of the CPU player.
	 */
	public char  getCPUColor(){
		return cpuColor;
	}
	
	
	/*
	 * Returns the number of times the player has won.
	 */
	public int getWins(){
		return wins;
	}

	
	/*
	 * Returns the number of times the player has lost.
	 */
	public int getLosses() {
		return losses;
	}
	
	
	/*
	 * Returns the number of times the player has tied the CPU opponent.
	 */
	public int getTies() {
		return ties;
	}
	
	
	/*
	 * Increments the number of player wins.
	 */
	public void incrementWins() {
		this.wins++;
	}

	
	/*
	 * Increments the number of player losses.
	 */
	public void incrementLosses() {
		this.losses++;
	}

	
	/*
	 * Increments the number of player ties.
	 */
	public void incrementTies() {
		this.ties++;
	}

	
	/*
	 * Returns the game board as represented by a 2D array.
	 */
	public char[][] getBoard(){
		return gameBoard;
	}
	
	
	/*
	 * Determines if the given move in a column drop is legal.
	 */
	private static boolean isLegalMove(char[][] vBoard, int column) {
		return vBoard[0][column] == 0;
	}

	
	/*
	 * Drops a piece in a column.  Returns true if the piece was successfully placed, and false if not.
	 */
	public boolean makeMove(int column, char player){
		
		if (!isLegalMove(gameBoard, column)) {
			System.out.println("Illegal move!");
			return false;
		}
		
		// place piece in the first open row in the column
		for (int i = 5; i >= 0; --i) {
			if (gameBoard[i][column] == 0) {
				gameBoard[i][column] = player;
				return true;
			}
		}
		return false;
	}

}
