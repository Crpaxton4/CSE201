import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * BoardScreen is used to provide a visual representation of a Connect Four game board.
 * @author Chris Anderson
 * @author Korey Smith
 * @author Chris Paxton
 */
public class BoardScreen extends JPanel implements ActionListener {
	
	private static final int rows = 6;
	private static final int columns = 7;	
	private static final int beginner = 'B';
	private static final int intermediate = 'I';
	
	private GameController controller; // use reference to controller to implement game logic and control the board
	private GameFrame gameFrame; // use reference to switch between panels on the GameFrame
	private JPanel boardPanel;
	private JPanel buttonPanel;
	private JPanel levelPanel;
	private JPanel colorPanel;
	private JPanel basePanel;
	private JPanel topPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JButton exitButton;
	private JButton helpButton;
	private JButton resetButton;
	private JButton aboutButton;
	private Icon buttonIcon;
	private Icon disabledButtonIcon; // for when column is full
	private ConnectFourLabel space[][] = new ConnectFourLabel[6][7]; // references to each grid position
	private JButton[] columnButtons = new JButton[7]; // references to each drop button
	private JLabel winLabel;
	private JLabel lossLabel;
	private JLabel tieLabel;
	

	/**
	 * Constructor to create a panel representing a ConnectFour game board.
	 * @param gameController A reference to the GameController used to manage the logic of the Connect Four game.
	 * @param gameFrame A reference to the GameFrame used to display the views of the Connect Four game.
	 */
	public BoardScreen(GameFrame gameFrame, GameController controller) {
		this.gameFrame = gameFrame;
		this.controller = controller;
		this.setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, GameFrame.FRAME_HEIGHT));
		this.setBackground(GameFrame.backgroundBlue);
		createBoardScreen();
	}
	
	
	/**
	 * Creates a visual representation of the Connect Four game board.
	 */
	private void createBoardScreen() {
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
	
	
	/**
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
				space[i][j] = new ConnectFourLabel(controller.getGameModel(), controller);
				boardPanel.add(space[i][j]);
			}
		}
	}
	
	
	/**
	 * Creates the panel which contains the exit and help buttons.
	 */
	private void createButtonPanel() {
		buttonPanel = new JPanel();
		
		buttonPanel.setBackground(GameFrame.backgroundBlue);
		buttonPanel.setLayout(new GridLayout(4, 1));
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
		
		resetButton = new JButton("RESET");
		resetButton.setBackground(buttonColor);
		resetButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		resetButton.addActionListener(this);		
		buttonPanel.add(resetButton);
		
		aboutButton = new JButton("ABOUT");
		aboutButton.setBackground(buttonColor);
		aboutButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		aboutButton.addActionListener(this);
		buttonPanel.add(aboutButton);

		add(buttonPanel, BorderLayout.CENTER);
	}
	
	
	/**
	 * Creates the panel which contains the player's color.
	 */
	private void createColorPanel() {
		colorPanel = new JPanel();
		colorPanel.setLayout(new GridLayout(2,1));
		colorPanel.setBackground(GameFrame.backgroundBlue);
		JLabel color = new JLabel("Your Color:");
		color.setHorizontalAlignment(JLabel.CENTER);
		color.setBackground(GameFrame.backgroundBlue);
		color.setFont(new Font("SansSerif", Font.BOLD, 20));
		colorPanel.add(color);
		
		if (controller.getPlayerColor()=='R') {
			JLabel red = new JLabel();
			red.setIcon(new ImageIcon("redButtonIcon.png"));
			red.setBackground(GameFrame.backgroundBlue);
			colorPanel.add(red);
		}
		else {
			JLabel yellow = new JLabel();
			yellow.setIcon(new ImageIcon("chooseYellowButton.png"));
			yellow.setBackground(GameFrame.backgroundBlue);
			colorPanel.add(yellow);
		}
		
		add(colorPanel, BorderLayout.CENTER);
	}
	
	
	/**
	 * Creates the panel which contains the current level.
	 */
	private void createLevelPanel() {
		levelPanel = new JPanel();	
		levelPanel.setLayout(new GridLayout(2,1));
		levelPanel.setBackground(GameFrame.backgroundBlue);
		JLabel level = new JLabel();
		level.setBackground(GameFrame.backgroundBlue);
		level.setText("Current Level:");
		level.setFont(new Font("SansSerif", Font.BOLD, 20));
		level.setHorizontalAlignment(JLabel.CENTER);
		levelPanel.add(level);
		
		if (controller.getMode()==beginner) {
			JLabel beginner = new JLabel();
			beginner.setBackground(GameFrame.backgroundBlue);
			beginner.setText("Beginner");
			beginner.setFont((new Font("SansSerif", Font.PLAIN, 18)));
			beginner.setHorizontalAlignment(JLabel.CENTER);
			beginner.setVerticalAlignment(JLabel.TOP);
			levelPanel.add(beginner);
		}
		else if (controller.getMode()==intermediate) {
			JLabel intermediate = new JLabel();
			intermediate.setBackground(GameFrame.backgroundBlue);
			intermediate.setText("Intermediate");
			intermediate.setFont((new Font("SansSerif", Font.PLAIN, 18)));
			intermediate.setHorizontalAlignment(JLabel.CENTER);
			intermediate.setVerticalAlignment(JLabel.TOP);
			levelPanel.add(intermediate);
		}
		else {
			JLabel advanced = new JLabel();
			advanced.setBackground(GameFrame.backgroundBlue);
			advanced.setText("Advanced");
			advanced.setFont((new Font("SansSerif", Font.PLAIN, 18)));
			advanced.setHorizontalAlignment(JLabel.CENTER);
			advanced.setVerticalAlignment(JLabel.TOP);
			levelPanel.add(advanced);
		}
		
		add(levelPanel, BorderLayout.CENTER);
	}
	
	
	/**
	 * Creates panel to the right of the game board used to keep track of game statistics.
	 */
	private void createRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4, 1));
		rightPanel.setBackground(GameFrame.backgroundBlue);
		createButtonPanel();
		createColorPanel();
		createLevelPanel();
				
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
		scorePanel.setBorder(BorderFactory.createLineBorder(Color.blue,3));
		
		rightPanel.add(buttonPanel);		
		rightPanel.add(levelPanel);
		rightPanel.add(colorPanel);
		rightPanel.add(scorePanel);
	}
	
	
	/**
	 * Creates base panel used as a colored border around the bottom of the Connect Four grid.
	 */
	private void createBasePanel() {
		basePanel = new JPanel();
		basePanel.setBackground(GameFrame.backgroundBlue);
		add(basePanel, BorderLayout.CENTER);
	}

	
	/**
	 * Creates top panel used as a colored border around the top of the Connect Four grid.
	 */
	private void createTopPanel() {
		topPanel = new JPanel();
		topPanel.setBackground(GameFrame.backgroundBlue);
		add(topPanel, BorderLayout.CENTER);
	}

	
	/**
	 * Creates left panel used as a colored border around the left of the Connect Four grid..
	 */
	private void createLeftPanel() {
		leftPanel = new JPanel();
		leftPanel.setBackground(GameFrame.backgroundBlue);
		add(leftPanel, BorderLayout.CENTER);
	}
	
	
	/**
	 * Resets the Connect Four game board by re-enabling all of the drop buttons and resetting all grid positions to empty.
	 */
	public void resetGUIBoard(){
		
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
		
	
	/**
	 * Determines appropriate course of action when a button is clicked (e.g., exit, display instructions, or drop piece).
	 * @param e ActionEvent source of the user button input.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(exitButton) || ((JButton)e.getSource()).getText().equals("EXIT")) {
			exitAction(e);
		}
		else if (e.getSource().equals(helpButton) || ((JButton)e.getSource()).getText().equals("HELP")) {
			helpAction(e);
		}
		else if(e.getSource().equals(resetButton) || ((JButton)e.getSource()).getText().equals("RESET")) {
			resetAction(e);
		}
		else if(e.getSource().equals(aboutButton) || ((JButton)e.getSource()).getText().equals("ABOUT")) {
			aboutAction(e);
		} 
		else {
			boardAction(e);
		}
	}
	
	
	/**
	 * Displays a dialogue box asking player to confirm their intention to exit.
	 * @param e source of the user button input.
	 */
	private void exitAction(ActionEvent e) {
		
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);

		if(confirmation == JOptionPane.YES_OPTION) {
			gameFrame.boardScreenToStatsScreen();
		}
	}
	
	
	/**
	 * Displays a dialogue box with game playing help instructions.
	 * @param source of the user button input.
	 */
	private void helpAction(ActionEvent e){
		String message = "The objective of Connect Four is to get four of your colored checker pieces in a row \n"
				+ "either horizontally, vertically, or diagonally. To drop your checker game piece in a column, \n"
				+ "click on a yellow triangular button above the game board corresponding to the column you'd \n"
				+ "like to drop your piece in.  When the column is full, the triangular arrow will appear gray \n"
				+ "and the button will be disabled, which will prevent you from dropping your game piece in a full column.\n"
				+ "When you are finished playing the game, click the exit button to view your game session statistics.";
		
		JOptionPane.showMessageDialog(null, message);
	}
	
	
	/**
	 * Displays a dialogue box asking player to confirm their intention to reset the board.
	 * @param source of the user button input.
	 */
	private void resetAction(ActionEvent e) {
		
		// dialog box asking player if they want to reset the board; if yes, reset board and increment losses
		String message = "Are you sure you want to reset the board?  If you choose to reset the board, the computer opponent will win the game.";
		
		int confirmation = JOptionPane.showConfirmDialog(null, message, "Reset", JOptionPane.YES_NO_OPTION);

		if(confirmation == JOptionPane.YES_OPTION) {
			controller.incrementLosses();
			lossLabel.setText("Losses: " + controller.getLosses());
			resetGUIBoard();
		}		
	}

	
	/**
	 * Displays information pertaining to the version and authors of the game in a dialog box.
	 * @param source of the user button input.
	 */
	private void aboutAction(ActionEvent e) {
		String message = "You are playing software version 1.0 of Connect Four.\n\nDeveloper contact information:\n\n"
				+ "Chris Anderson; ander231@miamioh.edu\nKorey Smith; smithk31@miamioh.edu\nChris Paxton; paxtoncr@miamioh.edu";
		JOptionPane.showMessageDialog(null, message);
		
	}
	
	
	/**
	 * Makes player's game piece placement as well as the CPU player's corresponding opposing placement.
	 * @param source of the user button input.
	 */
	private void boardAction(ActionEvent e) {
		
		// get player's desired column drop position and validate move
		JButton srcButton = (JButton) e.getSource();  
		int column = Integer.parseInt(srcButton.getText());
		int row = controller.getNextEmptyRow(column);
		
		if(controller.columnIsFull(column)) {
			return;
		}
		
		// drop piece in column and update game board if move is valid
		space[row][column].fill(controller.getPlayerColor());
		controller.placeMove(column, controller.getPlayerColor());
		boolean playerWin = controller.checkWin(controller.getGameModel().getBoard(), controller.getPlayerColor());
		
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
		CPU cpu = new CPU(controller.getGameModel(), controller.getMode());
		
		//int cpuColumn = CPU.getAIMove(controller.getGameModel(), controller.getMode());
		int cpuColumn = cpu.getAIMove();
		int cpuRow = controller.getNextEmptyRow(cpuColumn);
		
		// drop piece in column and update game board
		space[cpuRow][cpuColumn].fill(controller.getCPUColor());
		controller.placeMove(cpuColumn, controller.getCPUColor());
		boolean cpuWin = controller.checkWin(controller.getGameModel().getBoard(), controller.getCPUColor());
		
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
	
	
	/**
	 * Displays a dialogue box indicating the player has won, increments player wins, and resets the game board.
	 */
	private void playerWinAction() {
		String message = "You win!\n";
		JOptionPane.showMessageDialog(null, message);
		controller.incrementWins();
		resetGUIBoard();
		winLabel.setText("Wins: " + controller.getWins());
	}
	
	
	/**
	 * Displays a dialogue box indicating the CPU player has won, increments player losses, and resets the game board.
	 */
	private void cpuWinAction() {
		String message = "Computer wins!\n";
		JOptionPane.showMessageDialog(null, message);
		controller.incrementLosses();
		resetGUIBoard();
		lossLabel.setText("Losses: " + controller.getLosses());
	}
	
	
	/**
	 * Displays a dialogue box indicating a tie, increments ties, and resets the game board.
	 */
	private void tieAction() {
		String message = "Tie!\n";
		JOptionPane.showMessageDialog(null, message);
		controller.incrementTies();
		resetGUIBoard();
		tieLabel.setText("Ties: " + controller.getTies());
	}	
	
} // end BoardScreen