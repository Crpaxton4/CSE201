/**
 * CSE 201, Section A
 * Team 3
 * Chris Anderson, Chris Paxton, Korey Smith
 */
public class GameController {
	
	private static final int rows = 6;
	private static final int columns = 7;
	private static final char yellow = 'Y';
	private static final char red = 'R';
	
	private GameModel masterDataLog;	

	
	/*
	 * Constructor for the GameController.
	 */
	public GameController() {
		this.masterDataLog = new GameModel();
	}

	
	/*
	 * Sets the mode / level of game difficulty selected by the player.
	 */
	public void setMode(char mode) {
		masterDataLog.setMode(mode);
	}
	
	
	/*
	 * Gets the mode / level of game difficulty selected by the player.
	 */
	public char getMode() {
		return masterDataLog.getMode();
	}
	
	
	/*
	 * Returns a reference to the GameDataLog associated with the session of Connect Four.
	 */
	public GameModel getGameData() {
		return masterDataLog;
	}
	
	
	/*
	 * Returns a 2D character array representing the Connect Four gameboard.
	 */
	public char[][] getGameBoard() {
		return masterDataLog.getBoard();
	}
	
	
	/*
	 * Sets the player's game piece color.
	 */
	public void setPlayerColor(char c) {
		masterDataLog.setPlayerColor(c);
	}

	
	/*
	 * Gets the player's game piece color.
	 */
	public char getPlayerColor() {
		return masterDataLog.getPlayerColor();
	}

	
	/*
	 * Gets the CPU's game piece color.
	 */
	public char getCPUColor() {
		return masterDataLog.getCPUColor();
	}

	
	/*
	 * Increments player's cumulative win count for the game session.
	 */
	public void incrementWins() {
		masterDataLog.incrementWins();
	}

	
	/*
	 * Increments player's cumulative loss count for the game session.
	 */
	public void incrementLosses() {
		masterDataLog.incrementLosses();
	}

	
	/*
	 * Increments player's cumulative tie count for the game session.
	 */
	public void incrementTies() {
		masterDataLog.incrementTies();
	}
	
	
	/*
	 * Returns the number of player wins in the game session.
	 */
	public int getWins(){
		return masterDataLog.getWins();
	}
	
	
	/*
	 * Returns the number of player losses in the game session.
	 */
	public int getLosses() {
		return masterDataLog.getLosses();
	}
	
	
	/*
	 * Returns the number of player ties in the game session.
	 */
	public int getTies() {
		return masterDataLog.getTies();
	}
	
	
	/*
	 * Resets the game board to an empty Connect Four grid.
	 */
	public void resetBoard(){
		masterDataLog.resetBoard();
	}
	
	
	/*
	 * Returns the next empty row in a given column position on the Connect Four grid, and -1
	 * if the column is full.
	 */
	public int getNextEmptyRow(int column) {
		
		char[][] board = getGameBoard();
		
		for(int i = 5; i >= 0; i--) {
			if(board[i][column] != yellow && board[i][column] != red) {
				return i;
			}
		}
		return -1;
	}
	
	
	/*
	 * Returns true of the given column in the gameboard is full, and false if not.
	 */
	public boolean columnIsFull(int column) {
		// the column is full if the top piece of the column has a red or yellow piece
		char[][] board = getGameBoard();
		return board[0][column] == yellow || board[0][column] == red; 
	}
	
	
	/*
	 * Returns true if the board is full, and false if not.
	 */
	public boolean boardIsFull() {
		
		for(int i = 0; i < rows; i++) {
			if(!columnIsFull(i)) {
				return false;
			}
		}
		return false;
	}
	
	
	/*
	 * Makes a move for a given player in a given column.
	 */
	public void placeMove(int column, char player) {
		masterDataLog.makeMove(column, player);
	}

	
	/*
	 * Returns true if there is a winning combination on the game board.
	 */
	public boolean checkWin(char[][] board, char color) {
		// check for a horizontal win
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns-3; j++) {
				if(board[i][j] == color && board[i][j+1] == color && board[i][j+2] == color && board[i][j+3] == color) {
					return true;
				}
			}
		}
		
		// check for a vertical win
		for(int i = 0; i < rows - 3; i++) {
			for(int j = 0; j < columns; j++) {
				if(board[i][j] == color && board[i+1][j] == color && board[i+2][j] == color && board[i+3][j] == color) {
					return true;
				}
			}
		}
		
		// check for ascending diagonal win
		for (int i = 3; i < rows; i++) {
			for (int j = 0; j < columns - 3; j++) {
				if (board[i][j] == color && board[i-1][j+1] == color && board[i-2][j+2] == color && board[i-3][j+3] == color) {
					return true;
				}
			}
		}

		// check for descending diagonal win
		for (int i = 3; i < rows; i++) {
			for (int j = 3; j < columns; j++) {
				if (board[i][j] == color && board[i-1][j-1] == color && board[i-2][j-2] == color && board[i-3][j-3] == color) {
					return true;
				}
			}
		}
		// no win has been found if code execution gets here
		return false;
	}
	
	
	/*
	 * Prints the game board.
	 * 
	 * Note: For testing purposes; delete after testing.
	 * 
	 */
	public void printBoard() {
		
		char[][] board = getGameBoard();
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				
				if(board[i][j] == red) {
					System.out.print('R');
					System.out.print(' ');
				}
				else if(board[i][j] == yellow) {
					System.out.print('Y');
					System.out.print(' ');
				}
				else {
					System.out.print('X');
					System.out.print(' ');
				}
				
			}
			System.out.println();
		}
	}
	
}