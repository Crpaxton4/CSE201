import java.awt.Color;
import javax.swing.JFrame;

/**
 * GameFrame is used to hold the panels for playing Connect Four.
 * @author Chris Anderson
 * @author Korey Smith
 * @author Chris Paxton
 */
public class GameFrame extends JFrame {

	public static final int FRAME_WIDTH = 850;
	public static final int FRAME_HEIGHT = 650;
	public static final Color backgroundBlue = new Color(176, 241, 252);

	private GameController gameController;
	private WelcomeScreen welcomeScreen;
	private ModeScreen modeScreen;
	private BoardScreen boardScreen;
	private StatsScreen statsScreen;
	
	private static GameFrame gameFrame;
	
	
	/**
	 * Constructor for the Connect Four GameFrame.
	 */
	public GameFrame() {
		super("Connect 4");
		gameController = new GameController();
		welcomeScreen = new WelcomeScreen(gameController, this); // start out displaying the welcome screen
		add(welcomeScreen);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	
	
	/**
	 * Runs the Connect Four game and displays to the player the WelcomeScreen.
	 */
	public static void main(String[] args) {
		gameFrame = new GameFrame();
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true);
		gameFrame.setResizable(false);
		gameFrame.pack();
	}
	
	
	/**
	 * Invalidates the GameFrame.
	 */
	public static void invalidateFrame(){
		gameFrame.invalidate();
	}
	
	
	/**
	 * Validates the GameFrame.
	 */
	public static void validateFrame(){
		gameFrame.validate();
	}
	
	
	/**
	 * Changes the view on the GameFrame from the WelcomeScreen to the ModeScreen.
	 */
	public void welcomeScreenToModeScreen() {
		modeScreen = new ModeScreen(gameController, this);
		getContentPane().remove(welcomeScreen);
		add(modeScreen);
		getContentPane().invalidate();
		getContentPane().validate();
	}
	
	
	/**
	 * Changes the view on the GameFrame from the ModeScreen to the BoardScreen.
	 */
	public void modeScreenToBoardScreen() {
		boardScreen = new BoardScreen(this, gameController);
		getContentPane().remove(modeScreen);
		add(boardScreen);
		getContentPane().invalidate();
		getContentPane().validate();
	}	
	
	
	/**
	 * Changes the view on the GameFrame from the BoardScreen to the StatsScreen.
	 */
	public void boardScreenToStatsScreen() {
		statsScreen = new StatsScreen(gameController);
		getContentPane().remove(boardScreen);
		add(statsScreen);
		getContentPane().invalidate();
		getContentPane().validate();
	}

} // end GameFrame