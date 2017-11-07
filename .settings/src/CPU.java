import java.util.Arrays;
import java.util.Random;

/**
 * @author Chris Paxton
 * 
 *         Adapted from:
 *         https://github.com/jn1772/Connect4AI/blob/master/Connect4AI.java
 *         --"Connect4 AI written in Java"
 *
 *         Chris P: This is going to need a lot of testing once it is fully
 *         adapted
 *
 */
public class CPU {
	
	
	private static final char beginner = 'B';
	private static final char intermediate = 'I';
	private static final char advanced = 'A';
	
	private static final char yellow = 'Y';
	private static final char red = 'R';
	
	private static int nextMoveLocation = -1;
	private static int maxDepth = 9;
	private static char playerColor;
	private static char cpuColor;
	private static boolean randomMove = true;
	
	private GameModel data;


	/*
	 * Constructor for CPU opponent player.
	 */
	public CPU(GameModel data) {
		this.data = data;
	}

	
	/*
	 * Returns the AI's desired column drop based on the game's level of difficulty.
	 */
	public static int getAIMove(GameModel data, char mode) {
		
		if(mode == beginner) {
			return getBeginnerCPUMove(data);
		}
		else if(mode == intermediate) {
			return getIntermediateCPUMove(data);
		}
		else if(mode == advanced) {
			return getAdvancedAIMove(data);
		}
		else {
			return getBeginnerCPUMove(data);
		}
	}
	
	
	
	
	
	
	/*
	 * Returns the desired column for the AI based on a beginner level of difficulty.
	 */
	private static int getBeginnerCPUMove(GameModel data) {
		
		char[][] board = data.getBoard();
		int column;
		int fullColumns = 0;
		
		// ensure board is not entirely full to avoid entering infinite loops
		for(int i = 0; i < 7; i++) {
			if(board[0][i] == red || board[0][i] == yellow) {
				fullColumns++;
			}
		}
		
		if(fullColumns >= 7) {
			return -1;
		}
		
		do {
			// get random # for column
			column = (int) (Math.random() * 7); // for 7 columns; represented by values 0-6
			
			// check to see if the column is not full
			if(board[0][column] != red && board[0][column] != yellow) {
				return column;
			}
		}
		while(true);	
	}
	
	
	/*
	 * Returns the desired column for the AI based on an intermediate level of difficulty.
	 */
	private static int getIntermediateCPUMove(GameModel data) {
		
		int column;
		
		if(randomMove) {
			column = getBeginnerCPUMove(data); 
		}
		else {
			column = getAdvancedAIMove(data);
		}
		
		randomMove = !randomMove;
		return column;
	}
	
	
	/*
	 * Returns the desired column for the AI based on advanced level of difficuly.
	 */
	private static int getAdvancedAIMove(GameModel data) {
		nextMoveLocation = -1;
		playerColor = data.getPlayerColor();
		cpuColor = data.getCPUColor();
		System.out.println();
		minimax(data.getBoard(), 0, cpuColor, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return nextMoveLocation;
	}

	
	/**
	 * @param board
	 * @param depth
	 * @param player
	 * @param alpha
	 * @param beta
	 * @return
	 */
	private static int minimax(char[][] board, int depth, char player, int alpha, int beta) {

		/*
		 * TODO: Chris P to check over minimax algorithm and understand enough to present to class
		 */

		char[][] vBoard = boardDeepCopy(board);

		if (beta <= alpha) {
			if (player == cpuColor)
				return Integer.MAX_VALUE;
			else
				return Integer.MIN_VALUE;
		}

		int gameResult = gameResult(board);

		if (gameResult == 1)
			return Integer.MAX_VALUE / 2;
		else if (gameResult == 2)
			return Integer.MIN_VALUE / 2;
		else if (gameResult == 0)
			return 0;

		if (depth == maxDepth)
			return evaluateBoard(board);

		int maxScore = Integer.MIN_VALUE, minScore = Integer.MAX_VALUE;

		for (int j = 0; j <= 6; ++j) {

			int currentScore = 0;

			if (!isLegalMove(vBoard, j))
				continue;

			if (player == cpuColor) {
				placeMove(vBoard, j, player);
				currentScore = minimax(vBoard, depth + 1, playerColor, alpha, beta);

				if (depth == 0) { // first recursive iteration
					System.out.println("Score for location " + j + " = " + currentScore);
					if (currentScore > maxScore)
						nextMoveLocation = j;
					if (currentScore == Integer.MAX_VALUE / 2) {
						undoMove(vBoard, j);
						break;
					}
				}

				maxScore = Math.max(currentScore, maxScore);

				alpha = Math.max(currentScore, alpha);
			} else if (player == playerColor) {
				placeMove(vBoard, j, player);
				currentScore = minimax(vBoard, depth + 1, cpuColor, alpha, beta);
				minScore = Math.min(currentScore, minScore);

				beta = Math.min(currentScore, beta);
			}
			undoMove(vBoard, j);
			if (currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE)
				break;
		}
		return player == cpuColor ? maxScore : minScore;
	}

	/**
	 * @param vBoard
	 * @param column
	 * @return
	 */
	private static boolean isLegalMove(char[][] vBoard, int column) {
		return vBoard[0][column] == 0; // if this doesnt work then change 0 to
										// '\u0000'
	}

	/**
	 * @param vBoard
	 * @param column
	 * @param player
	 * @return
	 */
	private static boolean placeMove(char[][] vBoard, int column, char player) {
		if (!isLegalMove(vBoard, column)) {
			System.out.println("Illegal move!");
			return false;
		}
		for (int i = 5; i >= 0; --i) {
			if (vBoard[i][column] == 0) {
				vBoard[i][column] = player;
				return true;
			}
		}
		return false;
	}

	/**
	 * @param board
	 * @param column
	 */
	private static void undoMove(char[][] board, int column) {
		for (int i = 0; i <= 5; ++i) {
			if (board[i][column] != 0) {
				board[i][column] = 0;
				break;
			}
		}
	}

	/**
	 * @param board
	 * @return
	 */
	private static int gameResult(char[][] board) {
		/*
		 * TODO: adjust the checks against 1 and 2 to be cpuColor and
		 * playerColor respectively.
		 * 
		 * Probably should have started here to avoid compounding errors. oops
		 */

		int aiScore = 0, humanScore = 0;

		for (int i = 5; i >= 0; --i) {
			for (int j = 0; j <= 6; ++j) {
				if (board[i][j] == 0)
					continue;

				// Checking cells to the right
				if (j <= 3) {
					for (int k = 0; k < 4; ++k) {
						if (board[i][j + k] == cpuColor)
							aiScore++;

						else if (board[i][j + k] == playerColor)
							humanScore++;

						else
							break;

					}
					if (aiScore == 4)
						return 1;

					else if (humanScore == 4)
						return 2;

					aiScore = 0;
					humanScore = 0;

				}

				// Checking cells up
				if (i >= 3) {
					for (int k = 0; k < 4; ++k) {
						if (board[i - k][j] == cpuColor)
							aiScore++;

						else if (board[i - k][j] == playerColor)
							humanScore++;

						else
							break;

					}

					if (aiScore == 4)
						return 1;

					else if (humanScore == 4)
						return 2;

					aiScore = 0;
					humanScore = 0;

				}

				// Checking diagonal up-right
				if (j <= 3 && i >= 3) {
					for (int k = 0; k < 4; ++k) {
						if (board[i - k][j + k] == cpuColor)
							aiScore++;
						else if (board[i - k][j + k] == playerColor)
							humanScore++;
						else
							break;
					}
					if (aiScore == 4)
						return 1;
					else if (humanScore == 4)
						return 2;
					aiScore = 0;
					humanScore = 0;
				}

				// Checking diagonal up-left
				if (j >= 3 && i >= 3) {
					for (int k = 0; k < 4; ++k) {
						if (board[i - k][j - k] == cpuColor)
							aiScore++;
						else if (board[i - k][j - k] == playerColor)
							humanScore++;
						else
							break;
					}
					if (aiScore == 4)
						return 1;
					else if (humanScore == 4)
						return 2;
					aiScore = 0;
					humanScore = 0;
				}
			}
		}

		for (int j = 0; j < 7; ++j) {
			// Game has not ended yet
			if (board[0][j] == 0)
				return -1;
		}
		// Game draw!
		return 0;
	}

	/**
	 * @param board
	 * @return
	 */
	private static int evaluateBoard(char[][] board) {

		int aiScore = 1;
		int score = 0;
		int blanks = 0;
		int k = 0, moreMoves = 0;
		for (int i = 5; i >= 0; --i) {
			for (int j = 0; j <= 6; ++j) {

				if (board[i][j] == 0 || board[i][j] == playerColor)
					continue;

				if (j <= 3) {
					for (k = 1; k < 4; ++k) {
						if (board[i][j + k] == 1)
							aiScore++;
						else if (board[i][j + k] == playerColor) {
							aiScore = 0;
							blanks = 0;
							break;
						} else
							blanks++;
					}

					moreMoves = 0;
					if (blanks > 0)
						for (int c = 1; c < 4; ++c) {
							int column = j + c;
							for (int m = i; m <= 5; m++) {
								if (board[m][column] == 0)
									moreMoves++;
								else
									break;
							}
						}

					if (moreMoves != 0)
						score += calculateScore(aiScore, moreMoves);
					aiScore = 1;
					blanks = 0;
				}

				if (i >= 3) {
					for (k = 1; k < 4; ++k) {
						if (board[i - k][j] == cpuColor)
							aiScore++;
						else if (board[i - k][j] == playerColor) {
							aiScore = 0;
							break;
						}
					}
					moreMoves = 0;

					if (aiScore > 0) {
						int column = j;
						for (int m = i - k + 1; m <= i - 1; m++) {
							if (board[m][column] == 0)
								moreMoves++;
							else
								break;
						}
					}
					if (moreMoves != 0)
						score += calculateScore(aiScore, moreMoves);
					aiScore = 1;
					blanks = 0;
				}

				if (j >= 3) {
					for (k = 1; k < 4; ++k) {
						if (board[i][j - k] == cpuColor)
							aiScore++;
						else if (board[i][j - k] == playerColor) {
							aiScore = 0;
							blanks = 0;
							break;
						} else
							blanks++;
					}
					moreMoves = 0;
					if (blanks > 0)
						for (int c = 1; c < 4; ++c) {
							int column = j - c;
							for (int m = i; m <= 5; m++) {
								if (board[m][column] == 0)
									moreMoves++;
								else
									break;
							}
						}

					if (moreMoves != 0)
						score += calculateScore(aiScore, moreMoves);
					aiScore = 1;
					blanks = 0;
				}

				if (j <= 3 && i >= 3) {
					for (k = 1; k < 4; ++k) {
						if (board[i - k][j + k] == cpuColor)
							aiScore++;
						else if (board[i - k][j + k] == playerColor) {
							aiScore = 0;
							blanks = 0;
							break;
						} else
							blanks++;
					}
					moreMoves = 0;
					if (blanks > 0) {
						for (int c = 1; c < 4; ++c) {
							int column = j + c, row = i - c;
							for (int m = row; m <= 5; ++m) {
								if (board[m][column] == 0)
									moreMoves++;
								else if (board[m][column] == cpuColor)
									; // dafuq
								else
									break;
							}
						}
						if (moreMoves != 0)
							score += calculateScore(aiScore, moreMoves);
						aiScore = 1;
						blanks = 0;
					}
				}

				if (i >= 3 && j >= 3) {
					for (k = 1; k < 4; ++k) {
						if (board[i - k][j - k] == cpuColor)
							aiScore++;
						else if (board[i - k][j - k] == playerColor) {
							aiScore = 0;
							blanks = 0;
							break;
						} else
							blanks++;
					}
					moreMoves = 0;
					if (blanks > 0) {
						for (int c = 1; c < 4; ++c) {
							int column = j - c, row = i - c;
							for (int m = row; m <= 5; ++m) {
								if (board[m][column] == 0)
									moreMoves++;
								else if (board[m][column] == cpuColor)
									; // dafuq
								else
									break;
							}
						}
						if (moreMoves != 0)
							score += calculateScore(aiScore, moreMoves);
						aiScore = 1;
						blanks = 0;
					}
				}
			}
		}
		return score;
	}

	/**
	 * @param aiScore
	 * @param moreMoves
	 * @return
	 */
	private static int calculateScore(int aiScore, int moreMoves) {
		int moveScore = 4 - moreMoves;
		if (aiScore == 0)
			return 0;
		else if (aiScore == 1)
			return 1 * moveScore;
		else if (aiScore == 2)
			return 10 * moveScore;
		else if (aiScore == 3)
			return 100 * moveScore;
		else
			return 1000;
	}

	/**
	 * @param board
	 * @return
	 */
	private static char[][] boardDeepCopy(char[][] board) {
		char[][] newBoard = new char[board.length][];
		for (int i = 0; i < board.length; i++) {
			newBoard[i] = Arrays.copyOf(board[i], board[i].length);
		}

		return newBoard;
	}

}
