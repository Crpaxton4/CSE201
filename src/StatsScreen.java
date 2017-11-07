import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * StatsScreen is used to display a player's Connect Four game statistics.
 * @author Chris Anderson
 * @author Korey Smith
 * @author Chris Paxton
 */
public class StatsScreen extends JPanel {
	
	private JLabel titleLabel;
	private JPanel gameOverviewPanel; // holds the gameScorePanel, win rate labels, and exit button panel
	private JPanel gameScorePanel;  // to display wins, ties and losses
	private JLabel winRateHeaderLabel;
	private JLabel winRatePercentLabel;
	private JPanel exitButtonPanel;
	private GameController gameController;
	
	
	/**
	 * Constructor for StatsScreen to show final game play win/tie/loss stats.
	 * @param gameController A reference to the GameController used to manage the logic of the Connect Four game.
	 */
	public StatsScreen(GameController gameController) {	
		this.gameController = gameController;
		createTitleLabel();
		createGameOverviewPanel();
		createGameScorePanel();
		createGameWinLabels();
		createExitButtonPanel();
		add(gameOverviewPanel, BorderLayout.CENTER);
	}
	
	
	/**
	 * Creates the title label of the StatsScreen.
	 */
	private void createTitleLabel() {
		titleLabel = new JLabel("GAME STATS");
		titleLabel.setFont(new Font("Rockwell", Font.BOLD, 100));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		this.setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, GameFrame.FRAME_HEIGHT));
		setLayout(new BorderLayout());
		setBackground(GameFrame.backgroundBlue);
		add(titleLabel, BorderLayout.NORTH);
	}
	
	
	/**
	 * Creates the GameOverviewPanel which holds all of the relevant panels to display game stats.
	 */
	private void createGameOverviewPanel() {
		gameOverviewPanel = new JPanel();
		gameOverviewPanel.setBackground(GameFrame.backgroundBlue);
		gameOverviewPanel.setLayout(new GridLayout(4, 1));
	}
	
	
	/**
	 * Creates GameScorePanel to display the player's total wins, ties and losses.
	 */
	private void createGameScorePanel() {
		gameScorePanel = new JPanel();
		gameScorePanel.setBackground(GameFrame.backgroundBlue);
		gameScorePanel.setLayout(new GridLayout());
		
		JLabel wins = new JLabel("Won: " + gameController.getWins());
		wins.setFont(new Font("Rockwell", Font.PLAIN, 50));
		wins.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel ties = new JLabel("Tied: " + gameController.getTies());
		ties.setFont(new Font("Rockwell", Font.PLAIN, 50));
		ties.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel losses = new JLabel("Lost: " + gameController.getLosses());
		losses.setFont(new Font("Rockwell", Font.PLAIN, 50));
		losses.setHorizontalAlignment(JLabel.CENTER);
		
		gameScorePanel.add(wins);
		gameScorePanel.add(ties);
		gameScorePanel.add(losses);
		gameOverviewPanel.add(gameScorePanel);
	}
	
	
	/**
	 * Creates labels to display the player's win rate.
	 */
	private void createGameWinLabels() {
		
		double winPct = gameController.getWinPercent();
		
		winRateHeaderLabel = new JLabel("WIN RATE");
		winRateHeaderLabel.setHorizontalAlignment(JLabel.CENTER);
		winRateHeaderLabel.setFont(new Font("Rockwell", Font.PLAIN, 50));
		
		winRatePercentLabel = new JLabel("" + winPct + "%");
		winRatePercentLabel.setHorizontalAlignment(JLabel.CENTER);
		winRatePercentLabel.setFont(new Font("Rockwell", Font.BOLD, 100));
		
		gameOverviewPanel.add(winRatePercentLabel);
		gameOverviewPanel.add(winRateHeaderLabel);
	}
	
	
	/**
	 * Creates panel to hold the exit button.
	 */
	private void createExitButtonPanel() {
		exitButtonPanel = new JPanel();
		exitButtonPanel.setLayout(new GridLayout(1, 7));
		
		JButton exitButton = new JButton("EXIT");
		exitButton.setIcon(new ImageIcon("exitButton.png"));
		exitButton.setRolloverIcon(new ImageIcon("exitButtonRollover.png"));
		exitButton.setBorderPainted(false);
		ActionListener exitAction = new ExitListener();
		exitButton.addActionListener(exitAction);
		
		// create empty space on the left
		for (int i = 0; i < 3; i++) {
			JPanel empty = new JPanel();
			empty.setBackground(GameFrame.backgroundBlue);
			exitButtonPanel.add(empty);
		}
		
		// add exit button
		exitButtonPanel.add(exitButton);
		
		// create empty space on the right
		for (int i = 0; i < 3; i++) {
			JPanel empty = new JPanel();
			empty.setBackground(GameFrame.backgroundBlue);
			exitButtonPanel.add(empty);
		}
		
		gameOverviewPanel.add(exitButtonPanel);
	}
	
	
	/**
	 * Inner class used to help close the frame when the exit button is clicked.
	 */
	class ExitListener implements ActionListener {
		
		/**
		 * Terminates program execution when a component using an ExitListener is clicked.
		 * @param source of the user button input.
		 */
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
} // end StatsScreen