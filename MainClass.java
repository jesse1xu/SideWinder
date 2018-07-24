import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainClass extends JFrame
{
	MainClass() 
	{
		CardLayout cl = new CardLayout();
		JPanel cards =  new JPanel(cl);
		
		setTitle("SideWinder");
		setSize(737,770);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel home = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		home.setLayout(layout);
		home.setBackground(Color.BLACK);
		
		ImageIcon icon = new ImageIcon("no step on snek.jpg"); 
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		home.add(thumb,gbc);

		JLabel labelS = new JLabel(" ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		home.add(labelS ,gbc);
		
		JLabel label = new JLabel("<html><font color='white'>WELCOME TO THE GAME OF SIDEWINDER</font></html>");
		gbc.gridx = 0;
		gbc.gridy = 4;
		home.add(label,gbc);
		
		JLabel label2 = new JLabel("<html><font color='red'>RED SNAKE: WASD  </font></font> <font color='white'>|</font><font color='blue'>  BlUE SNAKE: ARROW KEYS</html>");
		gbc.gridx = 0;
		gbc.gridy = 8;
		home.add(label2,gbc);
		
		JLabel labelH = new JLabel(" ");
		gbc.gridx = 0;
		gbc.gridy = 10;
		home.add(labelH ,gbc);
		
		JButton button = new JButton("  EASY  ");
		button.setBackground(Color.WHITE);
		gbc.gridx = 0;
		gbc.gridy = 20;
		home.add(button, gbc);
		
		button.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						SnakeBoard.setDifficulty(3);
						SnakeBoard snake = new SnakeBoard();
						JPanel snake1 = new SnakeBoard();
						cards.add(snake1, "snakeboard");
						pack();
						cl.show(cards, "snakeboard");	
					}
				});
		JLabel labelE = new JLabel(" ");
		gbc.gridx = 0;
		gbc.gridy = 30;
		home.add(labelE,gbc);
		
		JButton button1 = new JButton("MEDIUM");
		button1.setBackground(Color.WHITE);
		gbc.gridx = 0;
		gbc.gridy = 40;
		home.add(button1, gbc);
		
		button1.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						SnakeBoard.setDifficulty(2);
						SnakeBoard snake = new SnakeBoard();
						JPanel snake1 = new SnakeBoard();
						cards.add(snake1, "snakeboard");
						pack();
						cl.show(cards, "snakeboard");
					}
				});
		
		JLabel labelM = new JLabel(" ");
		gbc.gridx = 0;
		gbc.gridy = 50;
		home.add(labelM ,gbc);
		
		JButton button2 = new JButton("  HARD  ");
		button2.setBackground(Color.WHITE);
		gbc.gridx = 0;
		gbc.gridy = 60;
		home.add(button2, gbc);
		
		button2.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						SnakeBoard.setDifficulty(1);
						SnakeBoard snake = new SnakeBoard();
						JPanel snake1 = new SnakeBoard();
						cards.add(snake1, "snakeboard");
						pack();
						cl.show(cards, "snakeboard");
					}
				});
		cards.add(home, "home");
		
		cl.show(cards,"home");
		setVisible(true);
		add(cards);
		
	}

	public static void main(String[] args) 
	{
		new MainClass();
	}
}
