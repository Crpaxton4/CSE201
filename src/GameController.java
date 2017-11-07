import java.math.BigDecimal;

/**
 * The GameController is used to implement the core logic of the Connect Four game.
 * @author Chris Anderson
 * @author Korey Smith
 * @author Chris Paxton
 */
public class GameController {
	
	private static final int rows = 6;
	private static final int columns = 7;
	private static final char yellow = 'Y';
	private static final char red = 'R';
	
	private GameModel gameModel;	

	
	/**
	 * Constructor for the GameController.
	 */
	public GameController() {
		this.gameModel = new GameModel();
	}

	
	/**
	 * Sets the mode / level of game difficulty selected by the player.
	 * @param mode Player-selected mode.
	 */
	public void setMode(char mode) {
		gameModel.setMode(mode);
	}
	
	
	/**
	 * Gets the mode / level of game difficulty selected by the player.
	 * @return Player-selected mode.
	 */
	public char getMode() {
		return gameModel.getMode();
	}
	
	
	/**
	 * Gets the GameModel. 
	 * @return a reference to the GameModel associated with the session of Connect Four.
	 */
	public GameModel getGameModel() {
		return gameModel;
	}
	
	
	/**
	 * Gets the game board.
	 * @return a 2D character array representing the Connect Four gameboard.
	 */
	public char[][] getGameBoard() {
		return gameModel.getBoard();
	}
	
	
	/**
	 * Sets the player's game piece color.
	 * @param c The color of the player's game piece.
	 */
	public void setPlayerColor(char c) {
		gameModel.setPlayerColor(c);
	}

	
	/**
	 * Gets the player's game piece color.
	 * @return the player's game piece color.
	 */
	public char getPlayerColor() {
		return gameModel.getPlayerColor();
	}

	
	/**
	 * Gets the CPU's game piece color.
	 * @return the CPU's game piece color.
	 */
	public char getCPUColor() {
		return gameModel.getCPUColor();
	}

	
	/**
	 * Increments player's cumulative win count for the game session.
	 */
	public void incrementWins() {
		gameModel.incrementWins();
	}

	
	/**
	 * Increments player's cumulative loss count for the game session.
	 */
	public void incrementLosses() {
		gameModel.incrementLosses();
	}

	
	/**
	 * Increments player's cumulative tie count for the game session.
	 */
	public void incrementTies() {
		gameModel.incrementTies();
	}
	
	
	/**
	 * Gets the number of player wins in the game session.
	 * @return the number of player wins in the game session.
	 */
	public int getWins(){
		return gameModel.getWins();
	}
	
	
	/**
	 * Gets the number of player losses in the game session.
	 * @return the number of player losses in the game session.
	 */
	public int getLosses() {
		return gameModel.getLosses();
	}
	
	
	/**
	 * Gets the number of player ties in the game session.
	 * @return the number of player ties in the game session.
	 */
	public int getTies() {
		return gameModel.getTies();
	}
	
	
	/**
	 * Gets the player's win rate.
	 * @return the player's win rate.
	 */
	public double getWinPercent() {
		
		// calculate win rate and ensure it isn't NaN (division by 0 when user exits before playing first game)
		double totalGames = getWins() + getLosses() + getTies();
		double calculatedWinRate = getWins() / totalGames;
		double actualWinPercent = (Double.isNaN(calculatedWinRate)) ? 0 : (calculatedWinRate * 100); // to check against division by 0
		
		// convert win rate percentage to 1 digit decimal
		BigDecimal winPct2DForm = new BigDecimal(actualWinPercent);
		winPct2DForm = winPct2DForm.setScale(1, BigDecimal.ROUND_HALF_UP);
		
		return winPct2DForm.doubleValue();
	}
	
	
	/**
	 * Resets the game board to an empty Connect Four grid.
	 */
	public void resetBoard(){
		gameModel.resetBoard();
	}
	
	
	/**
	 * Gets the next empty row in a given column.
	 * @param column the column to check for an empty row.
	 * @return the next empty row in a given column position on the Connect Four grid, and -1 if the column is full.
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
	
	
	/**
	 * Returns true of the given column in the gameboard is full, and false if not.
	 */
	public boolean columnIsFull(int column) {
		// the column is full if the top piece of the column has a red or yellow piece
		char[][] board = getGameBoard();
		return board[0][column] == yellow || board[0][column] == red; 
	}
	
	
	/**
	 * Checks to see if the Connect Four game board is full.
	 * @return true if the board is full, and false if not.
	 */
	public boolean boardIsFull() {
		
		for(int i = 0; i < columns; i++) {
			if(!columnIsFull(i)) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Makes a move for a given player in a given column.
	 * @param column the desired column drop position.
	 * @param player the color of the player to make a move.
	 */
	public void placeMove(int column, char player) {
		gameModel.makeMove(column, player);
	}

	
	/**
	 * Checks to determine if there is a winning piece combination on the game board for a player of a given color.
	 * @param board a 2D character array representing a Connect Four game board.
	 * @param color the color of the game piece to check for a win.
	 * @return true if there is a winning combination on the game board.
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
	
} // end GameController