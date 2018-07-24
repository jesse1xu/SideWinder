import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SnakeBoard extends JPanel implements ActionListener 
{
	// Height and width of the window
	private final static int BOARDWIDTH = 720;
	private final static int BOARDHEIGHT = 720 ;
	
	// Pixel size of snake and joints
	private final static int PIXELSIZE = 10;
	
	// The total amount of pixels of the game
	
	private final static int TOTALPIXELS = (BOARDWIDTH * BOARDHEIGHT) / (PIXELSIZE * PIXELSIZE);
	
	// Check to see if the game just started
	private boolean startGame = true;
	
	// Check to see if the game is running for both snakes
	private boolean inGame1 = true;
	private boolean inGame2 = true;
	
	//	Timer
	private Timer timer;
	
	//	Used to set game speed, the lower the #, the faster the snake travels
	private static int speed = 50;
	
	//	Instantiates snakes 
	//	Snake = Red
	//	Snake2 = Blue
	private Snake snake = new Snake();
	private Snake snake2 = new Snake();
	
	// Variable that stores if game is over or not
	private boolean gameOver = false;
	
	// Scores of both snakes
	private int snakeOneScore = 0;
	private int snakeTwoScore = 0;
	
	public SnakeBoard() 
	{
		InputMap im = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
	    ActionMap am = getActionMap();

	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightArrow");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpArrow");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DownArrow");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "D");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "A");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "W");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "S");

	    am.put("RightArrow", new ArrowAction("RightArrow"));
	    am.put("LeftArrow", new ArrowAction("LeftArrow"));
	    am.put("UpArrow", new ArrowAction("UpArrow"));
	    am.put("DownArrow", new ArrowAction("DownArrow"));
	    am.put("D", new ArrowAction("D"));
	    am.put("A", new ArrowAction("A"));
	    am.put("W", new ArrowAction("W"));
	    am.put("S", new ArrowAction("S"));
	    
	    setBackground(Color.BLACK);
	    setFocusable(true);
	
	    setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT));
	    
	    initializeGame();
	}
	
	static void setDifficulty(int d)
	{
		if (d==1)
		{
			speed = 20;
		}
		else if (d==2)
		{
			speed = 35;
		}
		else
		{
			speed = 50;
		}
	}
	
	/**
	 * Paints components to screen
	 */
	@Override
	protected void paintComponent(Graphics g) 
	{
	    super.paintComponent(g);
	    
	    if (startGame)
	    {    	
	    	try 
	    	{
	    		drawStart(g);
				Thread.sleep(1000);
			} 
	    	catch (InterruptedException e) 
	    	{
				e.printStackTrace();
			}
	    }
	    else
	    {
	    	draw(g);
	    }
	}
	
	/**
	 * Draws snake
	 * Uses repaint method
	 * @param g
	 * @throws InterruptedException 
	 */
	void drawStart(Graphics g) 
	{
		
		startGame(g);
		startGame = false;
	}
	
	void draw(Graphics g) 
	{
	    // Only draw if the game is running / the snake is alive
	    if (inGame1 == true && inGame2 == true) 
	    {
	        // Draw snake 1.
	        for (int i = 0; i < snake.getJoints(); i++) 
	        {
	            // Snake's head
	            if (i == 0) 
	            {
	                g.setColor(Color.WHITE);
	                g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i), PIXELSIZE, PIXELSIZE);
	            // Body of snake
	            } 
	            else 
	            {
	            	g.setColor(Color.RED);
	                g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i), PIXELSIZE, PIXELSIZE);
	            }
	        }
	        
	        // Draw snake 2.
	        for (int i = 0; i < snake2.getJoints(); i++) 
	        {
	            // Snake's head
	            if (i == 0) 
	            {
	                g.setColor(Color.WHITE);
	                g.fillRect(snake2.getSnakeX(i), snake2.getSnakeY(i), PIXELSIZE, PIXELSIZE);
	            // Body of snake
	            } 
	            else {
	            	g.setColor(Color.BLUE);
	                g.fillRect(snake2.getSnakeX(i), snake2.getSnakeY(i), PIXELSIZE, PIXELSIZE);
	            }
	        }
	        
	        
	
	        // Syncs graphics
	        Toolkit.getDefaultToolkit().sync();
	    } 
	    //	Ends game if both die at same time
	    else if (!inGame1 && !inGame2)
	    {
	        endGame(g); 
	    }
	    //	Ends game if Red dies first
	    else if (!inGame1)
	    {
	        endGame1(g); 
	    }
	    //	Ends game if Blue dies first
	    else if (!inGame2)
	    {
	    	endGame2(g);
	    }
	}
	
	/**
	 * Creates the two snakes, starts game
	 */
	void initializeGame() 
	{	
		snake.reset();
		snake2.reset();
		
		// Set game running
		inGame1 = true;
		inGame2 = true;
		
	    // set snake1 initial size
		snake.setJoints(1); 
		// set snake2 initial size
		snake2.setJoints(1); 
	
	    // Create snake1 body
	    for (int i = 0; i < snake.getJoints(); i++) 
	    {
	        snake.setSnakeX((BOARDWIDTH / 2) - 20);
	        snake.setSnakeY(BOARDHEIGHT / 2);
	    }
	    
	     // Create snake2 body
	    for (int i = 0; i < snake2.getJoints(); i++) 
	    {
	        snake2.setSnakeX((BOARDWIDTH / 2) + 20);
	        snake2.setSnakeY(BOARDHEIGHT / 2);
	    }
	    
	    // Set snakes moving up
	    snake.setMovingUp(true);
	    snake.setMovingDown(false);
	    snake.setMovingLeft(false);
	    snake.setMovingRight(false);
	    
	    snake2.setMovingUp(true);
	    snake2.setMovingDown(false);
	    snake2.setMovingLeft(false);
	    snake2.setMovingRight(false);
	   
	    // set the timer to record game's speed, animate
	    timer = new Timer(speed, this);
	    
	    // Allows time before starting
	    timer.setInitialDelay(2000);
	    
	    timer.start();
	    
	}
	
	/**
	 * Check collisions between snake and itself and edges of game board
	 */
	void checkCollisions() 
	{
	
	    // If the snake1 hits its own joints
	    for (int i = snake.getJoints(); i > 0; i--) 
	    {
	        if ((snake.getSnakeX(0) == snake.getSnakeX(i) && (snake.getSnakeY(0) == snake.getSnakeY(i)))) 
	        {
	            inGame1 = false; 
	            System.out.println("snake 1 hit own joints");
	        }
	    }
	    
	    // If the snake2 hits its own joints
	    for (int i = snake2.getJoints(); i > 0; i--) 
	    {
	        if ((snake2.getSnakeX(0) == snake2.getSnakeX(i) && (snake2.getSnakeY(0) == snake2.getSnakeY(i)))) 
	        {
	            inGame2 = false; 
	            System.out.println("snake 2 hit own joints");
	        }
	    }
	    
	    // If the snake1 hits snake2 joints
	    for (int i = snake.getJoints(); i > 0; i--) 
	    {
	    	
	        if (
	        		(snake.getSnakeX(0) == snake2.getSnakeX(i) && snake.getSnakeY(0) == snake2.getSnakeY(i)) 
	        		|| 
	        		(snake.getSnakeX(0) == snake2.getSnakeX(0) && snake.getSnakeY(0) == snake2.getSnakeY(0))
	        		)
	        {
	            inGame1 = false; 
	            System.out.println("snake 1 hit snake 2 joints");
	        }
	    }
	    
	    // If the snake2 hits snake1 joints
	    for (int i = snake2.getJoints(); i > 0; i--) 
	    {
	    	if (
	    			(snake2.getSnakeX(0) == snake.getSnakeX(i) && snake2.getSnakeY(0) == snake.getSnakeY(i))
	    			|| 
	    			(snake2.getSnakeX(0) == snake.getSnakeX(0) && snake2.getSnakeY(0) == snake.getSnakeY(0))
	    			)
	    	{
	            inGame2 = false; 
	            System.out.println("Snake 2 hit snake 1 joints");
	        }
	    }
	
	    // If the snake1 hits board edges
	    if (snake.getSnakeY(0) >= BOARDHEIGHT || snake.getSnakeY(0)<0 
	    		|| snake.getSnakeX(0) >= BOARDWIDTH || snake.getSnakeX(0) < 0) 
	    {
	        inGame1 = false;
	        System.out.println("snake 1 hit own board edges");
	    }
	    
	    // If the snake2 hits board edges
	    if (snake2.getSnakeY(0) >= BOARDHEIGHT || snake2.getSnakeY(0)<0 
	    		|| snake2.getSnakeX(0) >= BOARDWIDTH || snake2.getSnakeX(0)<0) 
	    {
	        inGame2 = false;
	        System.out.println("snake 2 hit own board edges");
	    }
	
	    
	    // If the game ends, end timer
	    if (!inGame1 || !inGame2) 
	    {
	        timer.stop();
	    }
	}
	
	/**
	 * Starts off the game with messages
	 * @param g 
	 * @throws InterruptedException 
	 */
	void startGame(Graphics g) 
	{
		// Start off messages
		String message = "GET READY...";
		
		Font font = new Font("Times New Roman", Font.BOLD, 40);
		FontMetrics metrics = getFontMetrics(font);
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		
		g.drawString(message, (BOARDWIDTH - metrics.stringWidth(message)) / 2, BOARDHEIGHT / 2);
	}
	
	/**
	 * If both die at same time
	 * @param g
	 */
	void endGame(Graphics g) 
	{
		String message3 = "";
		String message5 = "";
	    // Create a message telling the player the game is over
		String message = "TIE!";
		String message2 = "Press the enter key to replay";
		message3 += snakeOneScore;
		String message4 = "-";
		message5 += snakeTwoScore;
		
		// Creates font
		Font font = new Font("Times New Roman", Font.BOLD, 40);
		Font font2 = new Font("Times New Roman", Font.BOLD,12);
		Font font3 = new Font("Times New Roman", Font.BOLD, 40);
		Font font4 = new Font("Times New Roman", Font.BOLD, 40);
		Font font5 = new Font("Times New Roman", Font.BOLD, 40);
		FontMetrics metrics = getFontMetrics(font);
		FontMetrics metrics2 = getFontMetrics(font2);
		FontMetrics metrics3 = getFontMetrics(font3);
		FontMetrics metrics4 = getFontMetrics(font4);
		FontMetrics metrics5 = getFontMetrics(font5);
		
		// Sets the color and creates font
		g.setColor(Color.MAGENTA);
		
		g.setFont(font);
		g.drawString(message, (BOARDWIDTH - metrics.stringWidth(message)) / 2, (BOARDHEIGHT / 2));
		
		g.setFont(font2);
		g.drawString(message2, (BOARDWIDTH - metrics2.stringWidth(message2)) / 2, (BOARDHEIGHT / 2)+30);
		
		g.setColor(Color.RED);
		g.setFont(font3);
		g.drawString(message3, ((BOARDWIDTH - metrics3.stringWidth(message3)) / 2)-15, (BOARDHEIGHT / 2)+80);
		
		g.setColor(Color.WHITE);
		g.setFont(font4);
		g.drawString(message4, ((BOARDWIDTH - metrics4.stringWidth(message4)) / 2), (BOARDHEIGHT / 2)+80);
		
		g.setColor(Color.BLUE);
		g.setFont(font5);
		g.drawString(message5, ((BOARDWIDTH - metrics5.stringWidth(message5)) / 2)+15, (BOARDHEIGHT / 2)+80);
		
		gameOver = true;
		InputMap im = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
	    ActionMap am = getActionMap();
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "Enter");
	    am.put("Enter", new ArrowAction("Enter"));
	}
	
	
	/**
	 * If Red dies first
	 * @param g
	 */
	void endGame1(Graphics g) 
	{	
		snakeTwoScore++;
		String message3 = "";
		String message5 = "";
	    // Create a message telling the player the game is over
		String message = "BLUE WINS!";
		String message2 = "Press the enter key to replay";
		message3 += snakeOneScore;
		String message4 = "-";
		message5 += snakeTwoScore;
		
		// Creates font
		Font font = new Font("Times New Roman", Font.BOLD, 40);
		Font font2 = new Font("Times New Roman", Font.BOLD,12);
		Font font3 = new Font("Times New Roman", Font.BOLD, 40);
		Font font4 = new Font("Times New Roman", Font.BOLD, 40);
		Font font5 = new Font("Times New Roman", Font.BOLD, 40);
		FontMetrics metrics = getFontMetrics(font);
		FontMetrics metrics2 = getFontMetrics(font2);
		FontMetrics metrics3 = getFontMetrics(font3);
		FontMetrics metrics4 = getFontMetrics(font4);
		FontMetrics metrics5 = getFontMetrics(font5);
		
		// Sets the color and creates font
		g.setColor(Color.BLUE);
		
		g.setFont(font);
		g.drawString(message, (BOARDWIDTH - metrics.stringWidth(message)) / 2, (BOARDHEIGHT / 2));
		
		g.setFont(font2);
		g.drawString(message2, (BOARDWIDTH - metrics2.stringWidth(message2)) / 2, (BOARDHEIGHT / 2)+30);
		
		g.setColor(Color.RED);
		g.setFont(font3);
		g.drawString(message3, ((BOARDWIDTH - metrics3.stringWidth(message3)) / 2)-15, (BOARDHEIGHT / 2)+80);
		
		g.setColor(Color.WHITE);
		g.setFont(font4);
		g.drawString(message4, ((BOARDWIDTH - metrics4.stringWidth(message4)) / 2), (BOARDHEIGHT / 2)+80);
		
		g.setColor(Color.BLUE);
		g.setFont(font5);
		g.drawString(message5, ((BOARDWIDTH - metrics5.stringWidth(message5)) / 2)+15, (BOARDHEIGHT / 2)+80);
		
	    
	    gameOver = true;
	    InputMap im = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
	    ActionMap am = getActionMap();
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "Enter");
	    am.put("Enter", new ArrowAction("Enter"));
	}
	
	/**
	 * If Blue dies first
	 * @param g
	 */
	void endGame2(Graphics g) 
	{
		snakeOneScore++;
		String message3 = "";
		String message5 = "";
	    // Create a message telling the player the game is over
		String message = "RED WINS!";
		String message2 = "Press the enter key to replay";
		message3 += snakeOneScore;
		String message4 = "-";
		message5 += snakeTwoScore;
		
		// Creates font
		Font font = new Font("Times New Roman", Font.BOLD, 40);
		Font font2 = new Font("Times New Roman", Font.BOLD,12);
		Font font3 = new Font("Times New Roman", Font.BOLD, 40);
		Font font4 = new Font("Times New Roman", Font.BOLD, 40);
		Font font5 = new Font("Times New Roman", Font.BOLD, 40);
		FontMetrics metrics = getFontMetrics(font);
		FontMetrics metrics2 = getFontMetrics(font2);
		FontMetrics metrics3 = getFontMetrics(font3);
		FontMetrics metrics4 = getFontMetrics(font4);
		FontMetrics metrics5 = getFontMetrics(font5);
		
		// Sets the color and creates font
		g.setColor(Color.RED);
		
		g.setFont(font);
		g.drawString(message, (BOARDWIDTH - metrics.stringWidth(message)) / 2, (BOARDHEIGHT / 2));
		
		g.setFont(font2);
		g.drawString(message2, (BOARDWIDTH - metrics2.stringWidth(message2)) / 2, (BOARDHEIGHT / 2)+30);
		
		g.setColor(Color.RED);
		g.setFont(font3);
		g.drawString(message3, ((BOARDWIDTH - metrics3.stringWidth(message3)) / 2)-15, (BOARDHEIGHT / 2)+80);
		
		g.setColor(Color.WHITE);
		g.setFont(font4);
		g.drawString(message4, ((BOARDWIDTH - metrics4.stringWidth(message4)) / 2), (BOARDHEIGHT / 2)+80);
		
		g.setColor(Color.BLUE);
		g.setFont(font5);
		g.drawString(message5, ((BOARDWIDTH - metrics5.stringWidth(message5)) / 2)+15, (BOARDHEIGHT / 2)+80);
    	
    	gameOver = true;
    	InputMap im = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
	    ActionMap am = getActionMap();
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "Enter");
	    am.put("Enter", new ArrowAction("Enter"));
	}

	/**
	 * Runs constantly if game is running
	 * Checks for collisions and reanimates
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (inGame1 == true && inGame2 == true) 
		{
			checkCollisions();
			snake.move();
			snake2.move();
        
			repaint();
    	}
	}

	public class ArrowAction extends AbstractAction 
	{
	    private String cmd;

	    public ArrowAction(String cmd) 
	    {
	        this.cmd = cmd;
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) 
	    {
	    	//	Directs movement for snake1
	        if (cmd.equalsIgnoreCase("A") && (!snake.isMovingRight())) 
	        {
	        	snake.setMovingLeft(true);
	            snake.setMovingUp(false);
	            snake.setMovingDown(false);
	        } 
	        else if (cmd.equalsIgnoreCase("D") && (!snake.isMovingLeft())) 
	        {
	        	snake.setMovingRight(true);
	            snake.setMovingUp(false);
	            snake.setMovingDown(false);
	        } 
	        else if (cmd.equalsIgnoreCase("W") && (!snake.isMovingDown())) 
	        {
	        	snake.setMovingUp(true);
	            snake.setMovingRight(false);
	            snake.setMovingLeft(false);
	        } 
	        else if (cmd.equalsIgnoreCase("S") && (!snake.isMovingUp())) 
	        {
	        	snake.setMovingDown(true);
	            snake.setMovingRight(false);
	            snake.setMovingLeft(false);
	        } 
	        
	        //	Directs movement for snake2
	        else if (cmd.equalsIgnoreCase("LeftArrow") && (!snake2.isMovingRight())) 
	        {
	            snake2.setMovingLeft(true);
	            snake2.setMovingUp(false);
	            snake2.setMovingDown(false);
	        } 
	        else if (cmd.equalsIgnoreCase("RightArrow") && (!snake2.isMovingLeft())) 
	        {
	        	snake2.setMovingRight(true);
	            snake2.setMovingUp(false);
	            snake2.setMovingDown(false);
	        } 
	        else if (cmd.equalsIgnoreCase("UpArrow") && (!snake2.isMovingDown())) 
	        {
	        	snake2.setMovingUp(true);
	            snake2.setMovingRight(false);
	            snake2.setMovingLeft(false);
	        } 
	        else if (cmd.equalsIgnoreCase("DownArrow") && (!snake2.isMovingUp())) 
	        {
	        	snake2.setMovingDown(true);
	            snake2.setMovingRight(false);
	            snake2.setMovingLeft(false);
	        }
	        else if (cmd.equalsIgnoreCase("Enter") && (gameOver))
	        {
	        	startGame = true;
	        	repaint();
	        	revalidate();
	        	initializeGame();
	        
	        }
	    }
	}

	/**
	* Returns total amount of pixels
	* @return
 	*/
	public static int getAllDots() 
	{
		return TOTALPIXELS;
	}
	
	/**
	 * Returns size of joints
	 * @return
	 */
	public static int getDotSize() 
	{
		return PIXELSIZE;
	}
}