import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * CSE 201, Section A
 * Team 3
 * Chris Anderson, Chris Paxton, Korey Smith
 */
public class WelcomeScreen extends JPanel implements ActionListener {
	
	private static final char yellow = 'Y';
	private static final char red = 'R';
	
	private GameFrame gameFrame; // use reference to switch between JPanels on GameFrame
	private GameController gameController; // use reference to record player's color selection
	private JButton yellowButton;
	private JButton redButton;
	
	
	/*
	 * Creates a WelcomeScreen panel with game playing instructions and buttons to select player color.
	 */
	public WelcomeScreen(GameController gameController, GameFrame gameFrame) {
		
		// uses a reference to gameController to update the wins,losses, etc. and a reference to frame to switch between panels
		this.gameController = gameController; 
		this.gameFrame = gameFrame;

		// set WelcomeScreen panel size and color properties
		this.setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, GameFrame.FRAME_HEIGHT));
		this.setBackground(GameFrame.backgroundBlue);
		
		// add the instruction label and panel holding buttons to the WelcomeScreen
		createWelcomeLabel();
		createButtonPanel();
	}
	
	
	/*
	 * Creates a JLabel and adds an ImageIcon containing the instructions to play the ConnectFour game.
	 */
	public void createWelcomeLabel() {
		JLabel welcomeLabel = new JLabel();
		welcomeLabel.setIcon(new ImageIcon("welcome.png"));
		this.add(welcomeLabel);
	}
	
	
	/*
	 * Creates a JPanel holding two JButtons which allow the player to select their desired color.
	 */
	private void createButtonPanel() {

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(GameFrame.backgroundBlue);
		yellowButton = new JButton();
		redButton = new JButton();
		
		// create and add necessary components to the YELLOW piece button
		yellowButton.setIcon(new ImageIcon("chooseYellowButton.png"));
		yellowButton.setBackground(GameFrame.backgroundBlue);
		yellowButton.setBorderPainted(false);
		yellowButton.setRolloverEnabled(true);
		yellowButton.setRolloverIcon(new ImageIcon("chooseYellowbuttonRollover.png"));
		yellowButton.addActionListener(this);

		// create and add necessary components to the RED piece button
		redButton.setIcon(new ImageIcon("chooseRedButton.png"));
		redButton.setBackground(GameFrame.backgroundBlue);
		redButton.setBorderPainted(false);
		redButton.setRolloverEnabled(true);
		redButton.setRolloverIcon(new ImageIcon("chooseRedbuttonRollover.png"));
		redButton.addActionListener(this);

		// use grid layout to allow buttons to be centered on the panel?
		buttonPanel.setLayout(new GridLayout(1, 6));
		
		// create empty space on the left
		for (int i = 0; i < 2; i++) {
			JPanel empty = new JPanel();
			empty.setBackground(GameFrame.backgroundBlue);
			buttonPanel.add(empty);
		}
		
		// add yellow and red buttons in the center of the grid
		buttonPanel.add(yellowButton);
		buttonPanel.add(redButton);
		
		// create empty space on the right
		for (int i = 0; i < 2; i++) {
			JPanel empty = new JPanel();
			empty.setBackground(GameFrame.backgroundBlue);
			buttonPanel.add(empty);
		}

		this.add(buttonPanel, BorderLayout.CENTER);
	}
	

	/*
	 * Records player color selection and calls the Game class to change JPanel being displayed.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(yellowButton)) {
			gameController.setPlayerColor(yellow);
		} 
		else {
			gameController.setPlayerColor(red);
		}
		
		// calling GameFrame class method to show the view of the Connect Four game board
		
		// consider going to mode selection screen next?
		//gameFrame.welcomeScreenToBoardScreen();
		gameFrame.welcomeScreenToModeScreen();
	}

} // end WelcomeScreens