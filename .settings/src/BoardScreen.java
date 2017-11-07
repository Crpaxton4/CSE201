import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * CSE 201, Section A
 * Team 3
 * Chris Anderson, Chris Paxton, Korey Smith
 */
public class BoardScreen extends JPanel implements ActionListener {
	
	
	
	private static final int rows = 6;
	private static final int columns = 7;	
	
	private GameController controller; // use reference to implement game logic and control the board
	private GameFrame gameFrame; // use reference to switch between JPanels on GameFrame
	private JPanel boardPanel;
	private JPanel buttonPanel;
	private JPanel basePanel;
	private JPanel topPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JButton exitButton;
	private JButton helpButton;
	private Icon buttonIcon;
	private Icon disabledButtonIcon; // for when column is full
	private ConnectFourLabel space[][] = new ConnectFourLabel[6][7]; // references to each grid position
	private JButton[] columnButtons = new JButton[7]; // references to each drop button
	private JLabel winLabel;
	private JLabel lossLabel;
	private JLabel tieLabel;
	

	/*
	 * Constructor to create a panel representing a ConnectFour game board.
	 */
	public BoardScreen(GameFrame gameFrame, GameController controller) {
		this.gameFrame = gameFrame;
		this.controller = controller;
		this.setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, GameFrame.FRAME_HEIGHT));
		this.setBackground(GameFrame.backgroundBlue);
		createBoardAreaPanel();
	}
	
	
	/*
	 * Creates a visual representation of the Connect Four game board.
	 */
	private void createBoardAreaPanel() {
		createBoardPanel();
		createBasePanel();
		createTopPanel();
		createLeftPanel();
		createRightPanel();
		setLayout(new BorderLayout());
		setBackground(GameFrame.backgroundBlue);
		add(boardPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
		add(basePanel, BorderLayout.SOUTH);
		add(topPanel, BorderLayout.NORTH);
		add(leftPanel, BorderLayout.WEST);
	}
	
	
	/*
	 * Creates a Connect Four board, with buttons on top to drop pieces and a 6x7 grid.
	 */
	private void createBoardPanel() {
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(7, 7)); // include top row for drop buttons
		boardPanel.setBackground(GameFrame.backgroundBlue);
		buttonIcon = new ImageIcon("selectButton.png");
		disabledButtonIcon = new ImageIcon("selectButtonGray.png");

		// to set buttons in the top row of the game board for dropping game pieces
		for (int i = 0; i < columns; i++) {
			JButton selectButton = new JButton(Integer.toString(i));
			selectButton.setBackground(GameFrame.backgroundBlue);
			selectButton.setIcon(buttonIcon);
			selectButton.setDisabledIcon(disabledButtonIcon);
			selectButton.setBorderPainted(false);
			selectButton.setRolloverEnabled(true);
			selectButton.setRolloverIcon(new ImageIcon("selectButtonRollover.png"));
			selectButton.addActionListener(this);
			boardPanel.add(selectButton);
			columnButtons[i] = selectButton;
		}
		
		// to create 6x7 Connect Four board
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				space[i][j] = new ConnectFourLabel(controller.getGameData(), controller);
				boardPanel.add(space[i][j]);
			}
		}
	}
	
	
	/*
	 * Creates the panel which contains the exit and help buttons.
	 */
	private void createButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setBackground(GameFrame.backgroundBlue);
		buttonPanel.setLayout(new FlowLayout());
		Color buttonColor = new Color(255, 255, 0);

		exitButton = new JButton("EXIT");
		exitButton.setBackground(buttonColor);
		exitButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		exitButton.addActionListener(this);
		buttonPanel.add(exitButton);

		helpButton = new JButton("HELP");
		helpButton.setBackground(buttonColor);
		helpButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		helpButton.addActionListener(this);
		buttonPanel.add(helpButton);
		add(buttonPanel, BorderLayout.CENTER);
	}

	
	/*
	 * Creates panel to the right of the game board used to keep track of game statistics.
	 */
	private void createRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4, 1));
		rightPanel.setBackground(GameFrame.backgroundBlue);
		createButtonPanel();
				
		JLabel gameStats = new JLabel();
		gameStats.setText("Game Stats");
		gameStats.setFont(new Font("SansSerif", Font.BOLD, 25));
		gameStats.setHorizontalAlignment(JLabel.CENTER);
		
		winLabel = new JLabel();
		lossLabel = new JLabel();
		tieLabel = new JLabel();
		winLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lossLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		tieLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		winLabel.setText("Wins: " + controller.getWins());
		lossLabel.setText("Losses: " + controller.getLosses());
		tieLabel.setText("Ties: " + controller.getTies());
		
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new GridLayout(4,1));
		scorePanel.add(gameStats);
		scorePanel.add(winLabel);
		scorePanel.add(lossLabel);
		scorePanel.add(tieLabel);
		
		rightPanel.add(buttonPanel);
		
		// provide space for score tally to go in bottom right of screen
		for (int i = 0; i < 2; i++) {
			JPanel empty = new JPanel();
			empty.setBackground(GameFrame.backgroundBlue);
			rightPanel.add(empty);
		}
		rightPanel.add(scorePanel);
	}
	
	
	/*
	 * Creates base panel.
	 */
	private void createBasePanel() {
		basePanel = new JPanel();
		basePanel.setBackground(GameFrame.backgroundBlue);
		add(basePanel, BorderLayout.CENTER);
	}

	
	/*
	 * Creates top panel.
	 */
	private void createTopPanel() {
		topPanel = new JPanel();
		topPanel.setBackground(GameFrame.backgroundBlue);
		add(topPanel, BorderLayout.CENTER);
	}

	
	/*
	 * Creates left panel.
	 */
	private void createLeftPanel() {
		leftPanel = new JPanel();
		leftPanel.setBackground(GameFrame.backgroundBlue);
		add(leftPanel, BorderLayout.CENTER);
	}
	
		
	/*
	 * Determines appropriate course of action when a button is clicked (e.g., exit, display instructions, or drop piece).
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(exitButton) || ((JButton)e.getSource()).getText().equals("EXIT")) {
			gameFrame.boardScreenToStatsScreen(); // switch JPanel being displayed on the GameFrame
		}
		else if (e.getSource().equals(helpButton) || ((JButton)e.getSource()).getText().equals("EXIT")) {
			helpAction(e);
		} 
		else {
			boardAction(e);
		}
	}
	
	
	/*
	 * Resets the GUI by re-enabling all of the drop buttons and resetting all grid positions to empty.
	 */
	public void resetGUIBoard(){
		
		//System.out.println("Resetting board...");
		
		// enable any previously disabled buttons
		for(int i = 0; i < columns; i++) {
			columnButtons[i].setEnabled(true);
		}
		
		// iterate through the game board and reset the labels that represent piece placements
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				space[i][j].resetConnectFourLabel();
			}
		}
		
		GameFrame.invalidateFrame();
		controller.resetBoard();
		GameFrame.validateFrame();
	}
	
	
	/*
	 * Displays a dialogue box with help instructions.
	 */
	private void helpAction(ActionEvent e){
		String message = "The objective of the game is to get four of your colored checker"
				+ "pieces in a row either horizontally, vertically, or diagonally."
				+ " \nClick on a button above the game board to drop one of your pieces.";
		JOptionPane.showMessageDialog(null, message);
	}
	
	
	/*
	 * Makes player's game piece placement as well as the CPU player's corresponding opposing placement.
	 * The human player always gets the first move of the game.
	 */
	private void boardAction(ActionEvent e) {
		
		// get player's desired column drop position and validate move
		JButton srcButton = (JButton) e.getSource();  
		int column = Integer.parseInt(srcButton.getText());
		
		//int row = getNextEmptyRow(column);
		int row = controller.getNextEmptyRow(column);
		
		if(controller.columnIsFull(column)) {
			return;
		}
		
		// drop piece in column and update game board if move is valid
		space[row][column].fill(controller.getPlayerColor());
		controller.placeMove(column, controller.getPlayerColor());
		boolean playerWin = controller.checkWin(controller.getGameData().getBoard(), controller.getPlayerColor());
		//System.out.println("Player win = " + playerWin);
		//controller.printBoard();
		
		// check for player win
		if(playerWin) {
			playerWinAction();
			return;
		}
		
		// check to see if column is full after player placement
		if(controller.columnIsFull(column)) {
			columnButtons[column].setEnabled(false);
		}
		
		// get AI's desired column drop position based on selected mode
		int cpuColumn = CPU.getAIMove(controller.getGameData(), controller.getMode());
		int cpuRow = controller.getNextEmptyRow(cpuColumn);
		
		// drop piece in column and update game board
		space[cpuRow][cpuColumn].fill(controller.getCPUColor());
		controller.placeMove(cpuColumn, controller.getCPUColor());
		boolean cpuWin = controller.checkWin(controller.getGameData().getBoard(), controller.getCPUColor());
		//System.out.println("CPU win = " + cpuWin);
		//controller.printBoard();
		
		// check for AI winning move
		if(cpuWin) {
			cpuWinAction();
			return;
		}
		
		// check to see if there is a tie
		if(controller.boardIsFull()) {
			tieAction();
			return;
		}
		
		// check to see if the column is full after AI move
		if(controller.columnIsFull(cpuColumn)) {
			columnButtons[cpuColumn].setEnabled(false);
		}	
	}
	
	
	/*
	 * Displays a dialogue box indicating the player has won, increments player wins, and resets the game board.
	 */
	private void playerWinAction() {
		String message = "You win!\n";
		JOptionPane.showMessageDialog(null, message);
		controller.incrementWins();
		resetGUIBoard();
		winLabel.setText("Wins: " + controller.getWins());
	}
	
	
	/*
	 * Displays a dialogue box indicating the CPU player has won, increments player losses, and resets the game board.
	 */
	private void cpuWinAction() {
		String message = "Computer wins!\n";
		JOptionPane.showMessageDialog(null, message);
		controller.incrementLosses();
		resetGUIBoard();
		lossLabel.setText("Losses: " + controller.getLosses());
	}
	
	
	/*
	 * Displays a dialogue box indicating a tie, increments ties, and resets the game board.
	 */
	private void tieAction() {
		String message = "Tie!\n";
		JOptionPane.showMessageDialog(null, message);
		controller.incrementTies();
		resetGUIBoard();
		lossLabel.setText("Ties: " + controller.getTies());
	}	

}