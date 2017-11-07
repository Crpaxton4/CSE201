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
 * WelcomeScreen is used to provide the user with game playing instructions and allow the user to select
 * their desired game piece for the Connect Four game.
 * @author Chris Anderson
 * @author Korey Smith
 * @author Chris Paxton
 */
public class WelcomeScreen extends JPanel implements ActionListener {
	
	private static final char yellow = 'Y';
	private static final char red = 'R';
	
	private GameFrame gameFrame; // use reference to switch between JPanels on GameFrame
	private GameController gameController; // use reference to record player's color selection
	private JButton yellowButton;
	private JButton redButton;
	
	
	/**
	 * Constructor to create a WelcomeScreen panel with game playing instructions and buttons to select player color.
	 * @param gameController A reference to the GameController used to manage the logic of the Connect Four game.
	 * @param gameFrame A reference to the GameFrame used to display the views of the Connect Four game.
	 */
	public WelcomeScreen(GameController gameController, GameFrame gameFrame) {
		
		// uses a reference to gameController to update the wins,losses, etc. 
		// and a reference to game frame to switch between panels
		this.gameController = gameController; 
		this.gameFrame = gameFrame;

		this.setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, GameFrame.FRAME_HEIGHT));
		this.setBackground(GameFrame.backgroundBlue);
		
		// add the instruction label and panel holding buttons to the WelcomeScreen
		createWelcomeLabel();
		createButtonPanel();
	}
	
	
	/**
	 * Creates a label used to display the introductory game playing instructors to the player.
	 */
	public void createWelcomeLabel() {
		JLabel welcomeLabel = new JLabel();
		welcomeLabel.setIcon(new ImageIcon("welcome.png"));
		this.add(welcomeLabel);
	}
	
	
	/**
	 * Creates a panel to hold the buttons used to allow the player to select their desired game piece color.
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
	

	/**
	 * Records player color selection and calls the Game class to change JPanel being displayed.
	 * @param e ActionEvent used to select the desired game piece color.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(yellowButton)) {
			gameController.setPlayerColor(yellow);
		} 
		else {
			gameController.setPlayerColor(red);
		}
		
		// change the view from welcome screen to mode screen
		gameFrame.welcomeScreenToModeScreen();
	}

} // end WelcomeScreen