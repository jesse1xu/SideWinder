public class Snake 
{
	// Stores the parts (joints) of snake on the grid
	private int[] x = new int[SnakeBoard.getAllDots()];
	private int[] y = new int[SnakeBoard.getAllDots()];

	// Direction the snake is moving
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean movingUp = false;
	private boolean movingDown = false;
	
	//	Number of joints on our snake
	private int joints = 0;

	/**
	 * Gets the xCoordinate of the selected snake joint
	 * @param index
	 * @return
	 */
	public int getSnakeX(int index) 
	{
		return x[index];
	}

	/**
	 * Gets the yCoordinate of the selected snake joint
	 * @param index
	 * @return
	 */
	public int getSnakeY(int index) 
	{
		return y[index];
	}

	/**
	 * Sets the xCoordinate of the selected snake joint
	 * @param xCor
	 */
	public void setSnakeX(int xCor) 
	{
		x[0] = xCor;
	}
	
	/**
	 * Sets the yCoordinate of the selected snake joint
	 * @param yCor
	 */
	public void setSnakeY(int yCor) 
	{
		y[0] = yCor;
	}
	
	public void reset()
	{
		x= new int[SnakeBoard.getAllDots()];
		y= new int[SnakeBoard.getAllDots()];
	}

	/**
	 * Determines if the snake is moving left
	 * @return
	 */
	public boolean isMovingLeft() 
	{
		return movingLeft;
	}

	/**
	 * Sets the snake's direction to left
	 * @param movingLeft
	 */
	public void setMovingLeft(boolean movingLeft) 
	{
		this.movingLeft = movingLeft;
	}

	/**
	 * Determines if the snake is moving right
	 * @return
	 */
	public boolean isMovingRight() 
	{
		return movingRight;
	}

	/**
	 * Sets the snake's direction to right
	 * @param movingRight
	 */
	public void setMovingRight(boolean movingRight) 
	{
		this.movingRight = movingRight;
	}

	/**
	 * Determines if the snake is moving up
	 * @return
	 */
	public boolean isMovingUp() {
		return movingUp;
	}

	/**
	 * Sets the snake's direction to up
	 * @param movingUp
	 */
	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}

	/**
	 * Determines if the snake is moving down
	 * @return
	 */
	public boolean isMovingDown() 
	{
		return movingDown;
	}

	/**
	 * Sets the snake's direction to down
	 * @param movingDown
	 */
	public void setMovingDown(boolean movingDown) 
	{
		this.movingDown = movingDown;
	}
	
	/**
	 * Returns the number of joints
	 * @return
	 */
	public int getJoints() 
	{
		return joints;
	}

	/**
	 * Sets the number of joints to a specified number
	 * @param j
	 */
	public void setJoints(int j) 
	{
		joints = j;
	}

	/**
	 * Snake performs its actions
	 */
	public void move() 
	{
		//	Creates infinite trail
		joints+=1;
		
		for (int i = joints; i > 0; i--) 
		{
			// Moves the joints of the snake 'up the chain'
			x[i] = x[(i - 1)];
			y[i] = y[(i - 1)];
		}
		
		// Moves snake to the left
		if (movingLeft) 
		{
			x[0] -= SnakeBoard.getDotSize();
		}
		
		// To the right
		if (movingRight) 
		{
			x[0] += SnakeBoard.getDotSize();
		}
		
		// Down
		if (movingDown) 
		{
			y[0] += SnakeBoard.getDotSize();
		}
		
		// Up
		if (movingUp) 
		{
			y[0] -= SnakeBoard.getDotSize();
		}

	}
}