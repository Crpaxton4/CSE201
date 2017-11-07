import java.util.Arrays;
import java.util.Random;

/**
 * 
 * Takes a 2d array of characters and a game mode character from GameDatalog and
 * returns the column [0-6] that the CPU will take on the board.
 * 
 * @author Chris Paxton
 *
 */
public class CPU {
	/**
	 * A helper class to make the use of the minimax algorithm streamline
	 * 
	 * @author Chris Paxton
	 *
	 */
	private class Board {
		private char[][] b;
		private int rows;
		private int columns;

		/**
		 * Creates a new board object using a 2d array of chars
		 * 
		 * @param b
		 *            2d array of chars that is the board
		 */
		public Board(char[][] b) {
			this.b = b;
			this.rows = b.length;
			this.columns = b[0].length;
		}

		/**
		 * Copy constructor
		 * 
		 * @param board
		 *            Board object to copy
		 */
		public Board(Board board) {
			char[][] newBoard = new char[board.rows][board.columns];
			for (int i = 0; i < board.rows; i++) {
				newBoard[i] = Arrays.copyOf(board.b[i], board.columns);
			}

			this.rows = newBoard.length;
			this.columns = newBoard[0].length;
			this.b = newBoard;
		}

		/**
		 * Places a token of the specified player in the first available space
		 * in the provided column
		 * 
		 * @param column
		 *            column of move
		 * @param player
		 *            players token color
		 * @return true if move was made and false otherwise
		 */
		public boolean placeMove(int column, char player) {
			if (!isLegalMove(column)) {
				return false;
			}

			for (int i = rows - 1; i >= 0; i--) {

				if (b[i][column] == 0) {
					b[i][column] = player;
					return true;
				}
			}

			return false;
		}

		/**
		 * w Checks tthe board to determine if then specified player has a
		 * sequence of four or not
		 * 
		 * @param player
		 *            player color to check for win
		 * @return true if player has a sequence of 4 and false otherwise
		 */
		public boolean detectWin(char player) {
			// check for a horizontal win
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns - 3; j++) {
					if (b[i][j] == player && b[i][j + 1] == player && b[i][j + 2] == player && b[i][j + 3] == player) {
						return true;
					}
				}
			}

			// check for a vertical win
			for (int i = 0; i < rows - 3; i++) {
				for (int j = 0; j < columns; j++) {
					if (b[i][j] == player && b[i + 1][j] == player && b[i + 2][j] == player && b[i + 3][j] == player) {
						return true;
					}
				}
			}

			// check for ascending diagonal win
			for (int i = 3; i < rows; i++) {
				for (int j = 0; j < columns - 3; j++) {
					if (b[i][j] == player && b[i - 1][j + 1] == player && b[i - 2][j + 2] == player
							&& b[i - 3][j + 3] == player) {
						return true;
					}
				}
			}

			// check for descending diagonal win
			for (int i = 3; i < rows; i++) {
				for (int j = 3; j < columns; j++) {
					if (b[i][j] == player && b[i - 1][j - 1] == player && b[i - 2][j - 2] == player
							&& b[i - 3][j - 3] == player) {
						return true;
					}
				}
			}
			// no win has been found if code execution gets here
			return false;
		}

		/**
		 * Check to determine if there are any available spaces left in the
		 * board
		 * 
		 * @return true if spaces are available and false if board is full
		 */
		public boolean boardIsFull() {
			for (int i = 0; i < columns; i++) {
				if (b[0][i] == 0) {
					return false;
				}
			}

			return true;
		}

		/**
		 * Makes sure there is room for a move in the spacified column
		 * 
		 * @param column
		 *            column to ckeck for space in
		 * @return true if there is at least one available space in the
		 *         specified column
		 */
		public boolean isLegalMove(int column) {
			return b[0][column] == 0;
		}

		/**
		 * Determines the favorability of the current board state (node) and
		 * returnes the score of the board's state
		 * 
		 * @return board favorability score
		 */
		public int nodeScore() {
			// Evaluates the favorableness of the current board for the CPU by
			// checking the number of sequences and weighing then accordingly
			// //System.out.println(cpuColor);
			int boardFavorabilityScore = 0;

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					if (b[i][j] == 0 || b[i][j] == playerColor) {
						// skip space if CPU does not occupy
						continue;

					} else if (b[i][j] == cpuColor) {

						boardFavorabilityScore += horizontalSequenceScore(i, j);
						boardFavorabilityScore += verticalSequenceScore(i, j);
						boardFavorabilityScore += leftDiagonalSequanceScore(i, j);
						boardFavorabilityScore += rightDiagonalSequenceScore(i, j);
					}

				}
			}
			return boardFavorabilityScore;

		}

		/**
		 * Checks the board for sequences (CPU tokens in a row) vertically in
		 * every space and returns the score
		 * 
		 * @param i
		 *            space row
		 * @param j
		 *            space column
		 * @return score of vertical sequence favorability
		 */
		private int verticalSequenceScore(int i, int j) {
			// check vertical sequence of 4 from space (i, j)

			int numCpuInSequence = 0;
			int remainingSpacesinSequence = 0;

			if (i < 3) {

				for (int k = 0; k < 4; k++) {
					if (b[i + k][j] == cpuColor) {
						numCpuInSequence++;

					} else if (b[i + k][j] == 0) {
						remainingSpacesinSequence++;

					} else {
						numCpuInSequence = 0;
						remainingSpacesinSequence = 0;
						break;

					}
				}
			} else {

				for (int k = 0; k < 4; k++) {
					if (b[i - k][j] == cpuColor) {
						numCpuInSequence++;

					} else if (b[i - k][j] == 0) {
						remainingSpacesinSequence++;

					} else {
						numCpuInSequence = 0;
						remainingSpacesinSequence = 0;
						break;

					}
				}
			}

			if (numCpuInSequence > 0) {
				return sequenceWeight(numCpuInSequence, remainingSpacesinSequence);

			}
			return 0;
		}

		/**
		 * Checks the board for sequences (CPU tokens in a row) horizontally in
		 * every space and returns the score
		 * 
		 * @param i
		 *            space row
		 * @param j
		 *            space column
		 * @return score of horizontal sequence favorability
		 */
		private int horizontalSequenceScore(int i, int j) {
			// check horizontal sequence of 4 from space (i, j)

			int numCpuInSequence = 0;
			int remainingSpacesinSequence = 0;

			if (j > 3) {
				for (int k = 0; k < 4; k++) {
					if (b[i][j - k] == cpuColor) {
						numCpuInSequence++;

					} else if (b[i][j - k] == 0) {
						remainingSpacesinSequence++;

					} else {
						numCpuInSequence = 0;
						remainingSpacesinSequence = 0;
						break;

					}
				}
			} else {
				for (int k = 0; k < 4; k++) {
					if (b[i][j + k] == cpuColor) {
						numCpuInSequence++;

					} else if (b[i][j + k] == 0) {
						remainingSpacesinSequence++;

					} else {
						numCpuInSequence = 0;
						remainingSpacesinSequence = 0;
						break;

					}
				}
			}

			if (numCpuInSequence > 0) {
				return sequenceWeight(numCpuInSequence, remainingSpacesinSequence);

			}

			return 0;
		}

		/**
		 * Checks the board for sequences (CPU tokens in a row) diagonally to
		 * the left in every space and returns the score
		 * 
		 * @param i
		 *            space row
		 * @param j
		 *            space column
		 * @return score of left diagonal sequence favorability
		 */
		private int leftDiagonalSequanceScore(int i, int j) {
			// check left diagonal sequence of 4 from space (i, j)

			int numCpuInSequence = 0;
			int remainingSpacesinSequence = 0;

			if (j > 3) {
				if (i > 3) {
					for (int k = 0; k < 4 && (j > 2 && i < 3); k++) {
						if (b[i + k][j - k] == cpuColor) {
							numCpuInSequence++;

						} else if (b[i - k][j - k] == 0) {
							remainingSpacesinSequence++;

						} else {
							numCpuInSequence = 0;
							remainingSpacesinSequence = 0;
							break;

						}
					}

				} else {
					for (int k = 0; k < 4 && (j > 2 && i < 3); k++) {
						if (b[i + k][j - k] == cpuColor) {
							numCpuInSequence++;

						} else if (b[i + k][j - k] == 0) {
							remainingSpacesinSequence++;

						} else {
							numCpuInSequence = 0;
							remainingSpacesinSequence = 0;
							break;

						}
					}
				}
			} else {
				return 0;
			}

			if (numCpuInSequence > 0) {
				return sequenceWeight(numCpuInSequence, remainingSpacesinSequence);
			}
			return 0;
		}

		/**
		 * Checks the board for sequences (CPU tokens in a row) diagonally to
		 * the right in every space and returns the score
		 * 
		 * @param i
		 *            space row
		 * @param j
		 *            space column
		 * @return score of right diegonal sequence favorability
		 */
		private int rightDiagonalSequenceScore(int i, int j) {
			// check right diagonal sequence
			int numCpuInSequence = 0;
			int remainingSpacesinSequence = 0;

			if (j < 3) {
				if (i > 2) {
					for (int k = 0; k < 4; k++) {
						if (b[i - k][j + k] == cpuColor) {
							numCpuInSequence++;

						} else if (b[i - k][j + k] == 0) {
							remainingSpacesinSequence++;

						} else {
							numCpuInSequence = 0;
							remainingSpacesinSequence = 0;
							break;

						}
					}
				} else {
					for (int k = 0; k < 4; k++) {
						if (b[i + k][j + k] == cpuColor) {
							numCpuInSequence++;

						} else if (b[i + k][j + k] == 0) {
							remainingSpacesinSequence++;

						} else {
							numCpuInSequence = 0;
							remainingSpacesinSequence = 0;
							break;

						}
					}
				}
			}

			if (numCpuInSequence > 0) {
				return sequenceWeight(numCpuInSequence, remainingSpacesinSequence);
			}
			return 0;
		}

		/**
		 * @param cpuSpaces
		 *            number of spaces that the cpu occupies in a given run of
		 *            four spaces
		 * @param emptySpaces
		 *            number of available spaces in a given run of four spaces
		 * @return score of sequence
		 */
		private int sequenceWeight(int cpuSpaces, int emptySpaces) {
			// gives a score to a sequence based on the number of spaces the cpu
			// occupies. using double instead of int because there wont be
			// decimals and casting
			// reduces readability a bit

			return (int) (emptySpaces * Math.pow(10, cpuSpaces)) + movesRemaining();
		}

		/**
		 * Number of moves remaining in the game
		 * 
		 * @return count of empty spaces in the board
		 */
		private int movesRemaining() {
			int count = 0;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					if (b[i][j] == 0) {
						count++;
					}
				}
			}
			// System.out.println(count);
			return count;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					System.out.print(b[i][j] + " ");
				}
				System.out.println();
			}

			return "";
		}

	}

	private int searchDepth = 8;
	private char cpuColor;
	private char playerColor;
	private int nextMoveColumn = -1;
	private final int MAX_VALUE = Integer.MAX_VALUE;
	private final int MIN_VALUE = Integer.MIN_VALUE;
	private GameModel data;
	private char mode;
	private final char beginner = 'B';
	private final char intermediate = 'I';
	private final char advanced = 'A';
	private static final char yellow = 'Y';
	private static final char red = 'R';

	/**
	 * Creates a new CPU for the current game (GameModel data) and a character
	 * specifying the difficulty
	 * 
	 * @param data
	 * @param mode
	 */
	public CPU(GameModel data, char mode) {
		this.data = data;
		this.mode = mode;
		this.cpuColor = data.getCPUColor();
		this.playerColor = data.getPlayerColor();
	}

	/**
	 * Based on the difficulty and the respective algorithm, return the column
	 * that the CPU will place a move in the current game
	 * 
	 * @return column index of next CPU move
	 */
	public int getAIMove() {

		if (mode == beginner) {
			return getBeginnerCPUMove();

		} else if (mode == intermediate) {
			return getIntermediateCPUMove();

		} else if (mode == advanced) {
			return getAdvancedAIMove();

		}
		// use a beginner mode by default
		else {
			return getBeginnerCPUMove();
		}
	}

	/**
	 * If the game mode is Intermediate, return a Beginner move 30% of the time
	 * and an advances move 70% of the time
	 * 
	 * @return column index of next CPU move
	 */
	private int getIntermediateCPUMove() {
		int column;
		Random rand = new Random();
		float chance = rand.nextFloat();

		if (chance < 0.3) {
			column = getBeginnerCPUMove();
			System.out.println("Beginer Move");
		} else {
			column = getAdvancedAIMove();
			System.out.println("Advanced Move");
		}
		return column;
	}

	/**
	 * If the game mode is Advanced, using the minimax algorithm determine the
	 * most favorable move for the CPU based on the current board
	 * 
	 * @return column index of next CPU move
	 */
	private int getAdvancedAIMove() {
		Board b = new Board(data.getBoard());
		minimax(b, searchDepth, MIN_VALUE, MAX_VALUE, true);
		return nextMoveColumn;
	}

	/**
	 * If the game mode is Beginner, return a random column index as the CPU's
	 * move
	 * 
	 * @return column index of next CPU move
	 */
	private int getBeginnerCPUMove() {
		char[][] board = data.getBoard();
		int column;
		int fullColumns = 0;

		// ensure board is not entirely full to avoid entering infinite loops
		for (int i = 0; i < 7; i++) {
			if (board[0][i] == red || board[0][i] == yellow) {
				fullColumns++;
			}
		}

		if (fullColumns >= 7) {
			return -1;
		}

		do {
			// get random # for column
			column = (int) (Math.random() * 7); // for 7 columns; represented by
												// values 0-6

			// check to see if the column is not full
			if (board[0][column] != red && board[0][column] != yellow) {
				return column;
			}
		} while (true);
	}

	/**
	 * Using the Minimax algorithm, recursively traverse all possible future
	 * game states for the number of moves (spacified by the depth) and find the
	 * column that will be most favorable for the CPU to make its next moves.
	 * Alpha-beta pruning improves the preformance of the minimax algorithm by
	 * reducing the number of nodes that need to be traversed
	 * 
	 * @param b
	 *            Board node that is being analyzed
	 * @param depth
	 *            depth of recursive tree search
	 * @param alpha
	 *            min score that the CPU has for all possible future moves
	 * @param beta
	 *            max score that the CPU has for all possible future moves moves
	 * @param maximizingPlayer
	 * @return
	 */
	private int minimax(Board b, int depth, int alpha, int beta, boolean maximizingPlayer) {
		if (depth == 0) {
			return b.nodeScore();

		} else if (b.detectWin((maximizingPlayer) ? cpuColor : playerColor)) {
			return (maximizingPlayer) ? MAX_VALUE / 2 : MIN_VALUE / 2;

		} else if (b.boardIsFull()) {
			return 0;
		}

		int result;
		int bestMoveScore = Integer.MIN_VALUE;

		if (maximizingPlayer) { // maximizing player is the cpu
			result = MIN_VALUE;

			for (int i = 0; i < b.columns; i++) {
				Board child = new Board(b);
				if (child.placeMove(i, cpuColor)) {
					result = Integer.max(result, minimax(child, depth - 1, alpha, beta, false));

					if (depth == searchDepth) {
						System.out.println("Score for location " + i + " = " + result);
						if (result > bestMoveScore) {
							bestMoveScore = result;
							nextMoveColumn = i;
						}
					}

					alpha = Integer.max(alpha, result);

					if (beta < alpha) {
						// //System.out.println("cpu pruned");
						break;
					}
				} else {
					continue;
				}
			}
		} else {
			// //System.out.println("player");
			result = MAX_VALUE;

			for (int i = 0; i < b.columns; i++) {
				Board child = new Board(b);
				if (child.placeMove(i, playerColor)) {
					result = Integer.min(result, minimax(child, depth - 1, alpha, beta, true));
					beta = Integer.min(beta, result);

					if (beta <= alpha) {
						// //System.out.println("player pruned");
						break;
					}
				} else {
					continue;
				}
			}
		}

		return result;

	}
}
