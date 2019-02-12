import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Implements the main class of the project
 * @author My Vo, Rachel Stanik, Son Vo
 */
public class MancalaTester {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	private static String choice = ""; // basic board or square board
	private static int stones = 0; // the number of stones in each pit
	private static JFrame mainFrame;
	public static void main(String[] args) {
		mainFrame = new JFrame();
		mainFrame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		mainFrame.pack();
		
		mainFrame.setLayout(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		JButton squareChoice = new JButton("Square Board");
		JButton basicChoice = new JButton("Basic Board");
		squareChoice.setFont(new Font("Serif",Font.PLAIN,30));
		basicChoice.setFont(new Font("Serif",Font.PLAIN,30));
		final JTextField textField = new JTextField("4");
		JLabel label = new JLabel("Stones in each pit (Maximum 4)");
		label.setFont(new Font("Serif",Font.PLAIN,15));
		ActionListener listener = new ActionListener()
		{

			public void actionPerformed(ActionEvent event) {
				JButton temp = (JButton) event.getSource();
				choice = temp.getText();
				stones = Integer.valueOf(textField.getText());
				mainFrame.dispose();
				createBoard();
			}
			
		};
		squareChoice.addActionListener(listener);
		basicChoice.addActionListener(listener);
		mainFrame.add(squareChoice);
		mainFrame.add(basicChoice);
		mainFrame.add(label);
		mainFrame.add(textField);
		squareChoice.setBounds(WIDTH/20, WIDTH/4, WIDTH/3, WIDTH/20);
		basicChoice.setBounds(WIDTH/2, WIDTH/4, WIDTH/3, WIDTH/20);
		label.setBounds(WIDTH/4, WIDTH/2, WIDTH/2, WIDTH/20);
		textField.setBounds(WIDTH/4+WIDTH/3, WIDTH/2, WIDTH/10, WIDTH/20);
		
	}
	
	/**
	 * Creates a board based on MVC pattern
	 */
	public static void createBoard()
	{
		DataModel model = new DataModel(stones);
		BasicBoard board;
		if (choice.equals("Square Board"))
		{
			SquarePit pits[] = new SquarePit[14];
			for (int i = 0; i < 14; i++)
			{
				pits[i] = new SquarePit(0,0,0,0,0,0);
			}
			board = new SquareBoard(800, 800, model, pits);
		}
		else
		{
			Pit pits[] = new Pit[14];
			for (int i = 0; i < 14; i++)
			{
				pits[i] = new Pit(0,0,0,0,0,0);
			}
			board = new BasicBoard(800, 800, model, pits);
		}
		board.pack();
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setVisible(true);
		Controller control = new Controller(board);
	}
}
