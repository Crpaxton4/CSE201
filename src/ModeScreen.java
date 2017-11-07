import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ModeScreen is used to allow the player of the Connect Four game to select their desired level of difficulty.
 * @author Chris Anderson
 * @author Korey Smith
 * @author Chris Paxton
 */
public class ModeScreen extends JPanel {

	private static final char beginner = 'B';
	private static final char intermediate = 'I';
	private static final char advanced = 'A';
	
	private GameFrame gameFrame; // use reference to switch between JPanels on GameFrame
	private GameController gameController; // use reference to record player's mode selection (beginner, intermediate, advanced)
	private JLabel instructionLabel;
	private JButton beginnerButton;
	private JButton intermediateButton;
	private JButton advancedButton;	
	
	
	/**
	 * Constructor to create a ModeScreen panel with the ability to select the desired mode of the game.
	 * @param gameController A reference to the GameController used to manage the logic of the Connect Four game.
	 * @param gameFrame A reference to the GameFrame used to display the views of the Connect Four game.
	 */
	public ModeScreen(GameController gameController, GameFrame gameFrame) {
		this.gameController = gameController; 
		this.gameFrame = gameFrame;
		this.setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, GameFrame.FRAME_HEIGHT));
		this.setBackground(GameFrame.backgroundBlue);
		this.setLayout(new GridLayout(4,1));
		createModeButtons();
	}

	
	/**
	 * Creates the buttons that allow the user to select which mode / difficulty level they'd like to play the game.
	 */
	private void createModeButtons() {
		
		instructionLabel = new JLabel();
		instructionLabel.setIcon(new ImageIcon("levelScreen.png"));
		
		beginnerButton = new JButton();
		beginnerButton.setIcon(new ImageIcon("beginnerButton.png"));
		beginnerButton.setRolloverIcon(new ImageIcon("beginnerButtonRollover.png"));
		beginnerButton.setBorderPainted(false);
		
		intermediateButton = new JButton();
		intermediateButton.setIcon(new ImageIcon("intermediateButton.png"));
		intermediateButton.setRolloverIcon(new ImageIcon("intermediateButtonRollover.png"));
		intermediateButton.setBorderPainted(false);
		
		advancedButton = new JButton();
		advancedButton.setIcon(new ImageIcon("advancedButton.png"));
		advancedButton.setRolloverIcon(new ImageIcon("advancedButtonRollover.png"));
		advancedButton.setBorderPainted(false);
		
		ActionListener buttonListener = new ModeListener();
		
		beginnerButton.addActionListener(buttonListener);
		intermediateButton.addActionListener(buttonListener);
		advancedButton.addActionListener(buttonListener);
		
		add(instructionLabel);
		add(beginnerButton);
		add(intermediateButton);
		add(advancedButton);
	}


	/**
	 * ModeListener is an inner class that is used to determine the player's desired mode.
	 */
	class ModeListener implements ActionListener {
		
		/**
		 * Determines the player's desired level of difficulty and sets the mode of the game.
		 * @param e The ActionEvent used to determine the player's desired level of difficulty.
		 */
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
	
} // end ModeScreen