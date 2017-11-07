import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * CSE 201, Section A
 * Team 3
 * Chris Anderson, Chris Paxton, Korey Smith
 */
public class ModeScreen extends JPanel {

	private static final char beginner = 'B'; // beginner
	private static final char intermediate = 'I'; // intermediate
	private static final char advanced = 'A'; // advanced
	
	private GameFrame gameFrame; // use reference to switch between JPanels on GameFrame
	private GameController gameController; // use reference to record player's mode selection (beginner, intermediate, advanced)
	private JLabel instructionLabel;
	private JButton beginnerButton;
	private JButton intermediateButton;
	private JButton advancedButton;
	
	
	/*
	 * Creates a WelcomeScreen panel with game playing instructions and buttons to select player color.
	 */
	public ModeScreen(GameController gameController, GameFrame gameFrame) {
		this.gameController = gameController; 
		this.gameFrame = gameFrame;
		this.setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, GameFrame.FRAME_HEIGHT));
		this.setBackground(GameFrame.backgroundBlue);
		createModeButtons();
		
		//TODO: Make ModeScreen GUI conform to the style of the rest of the game GUI components
		
	}

	
	/*
	 * Creates the buttons that allow the user to select which mode they'd like to play the game.
	 */
	private void createModeButtons() {
		
		instructionLabel = new JLabel("Please select difficulty level:");
		
		beginnerButton = new JButton("Beginner");
		intermediateButton = new JButton("Intermediate");
		advancedButton = new JButton("Advanced");
		
		ActionListener buttonListener = new ModeListener();
		
		beginnerButton.addActionListener(buttonListener);
		intermediateButton.addActionListener(buttonListener);
		advancedButton.addActionListener(buttonListener);
		
		add(instructionLabel);
		add(beginnerButton);
		add(intermediateButton);
		add(advancedButton);
	}


	/*
	 * Inner Listener class to determine which mode / level of difficulty the player would like.
	 */
	class ModeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource().equals(beginnerButton)) {
				gameController.setMode(beginner);
			} 
			else if(e.getSource().equals(intermediateButton)) {
				gameController.setMode(intermediate);
			}
			else {
				gameController.setMode(advanced);
			}
			gameFrame.modeScreenToBoardScreen();
		}
	}
	
}