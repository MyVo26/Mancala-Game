
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Implements a basic board which contains circle pits. This class serves as a view class
 * @author My Vo, Rachel Stanik, Son Vo
 */
public class BasicBoard extends Board implements ChangeListener{
	private Pit[] pits;
	private JLabel playerTurn; // Display whose turn
	private JPanel player1Panel; // player A's panel
	private JPanel mancala1Panel; // player A's mancala
	private JPanel topPanel; // Contains button and player B's panel
	private JPanel player2Panel; // player B's panel
	private JPanel buttonPanel; // contains buttons
	private JPanel mancala2Panel; // player B's mancala
	private JPanel playerTurnPanel; // contain the playerTurn label
	private JButton undoButton;
	private JButton endButton;
	
	/**
	 * Constructs a basic board with given variables 
	 * @param width the board's width
	 * @param height the board's height
	 * @param model the model data
	 * @param arr the array of Pits
	 */
	public BasicBoard(int width, int height, DataModel model, Pit arr[]) {
		super(width, height, model); //create a model with the correct number of stones
		pits = arr;
		this.setLayout(new BorderLayout());
		
		//create top row
		player1Panel = new JPanel();
		player1Panel.setPreferredSize(new Dimension(width, height / 6));
		player1Panel.setLayout(new FlowLayout());
		player1Panel.setVisible(true);
		for(int i = 0; i < 6; i++) {
			pits[i].setAll(0, 0, width / 12, width / 12, super.getModel()[i],i);
			pits[i].setLayout(null);
			JLabel label = new JLabel("A" + (i + 1));
			pits[i].add(label);
			label.setBounds(width / 20, width / 14, width / 12, width / 12);
			player1Panel.add(pits[i]);
		}
		this.add(player1Panel,BorderLayout.SOUTH);
		
		// Create right mancala (Player A's mancala)
		mancala1Panel = new JPanel();
		mancala1Panel.setPreferredSize(new Dimension(width / 4, height / 6));
		mancala1Panel.setVisible(true);
		mancala1Panel.setLayout(new BorderLayout());
		pits[6].setAll(0, 0, width / 6, height / 4, super.getModel()[6],6);
		JLabel M1label = new JLabel("M1");		
		pits[6].add(M1label);
		M1label.setBounds(0, 0, width / 30, height / 6);
		mancala1Panel.add(pits[6]);
		this.add(mancala1Panel, BorderLayout.EAST);
		mancala1Panel.setBackground(Color.blue);
		
		//Top row of pits
		topPanel = new JPanel();
		player2Panel = new JPanel();
		player2Panel.setPreferredSize(new Dimension(width, height / 6));
		player2Panel.setLayout(new FlowLayout());
		player2Panel.setVisible(true);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		undoButton = new JButton("Undo");
		endButton = new JButton("End Turn");
		buttonPanel.add(undoButton);
		buttonPanel.add(endButton);
		for(int i = 12; i > 6; i--) {
			pits[i].setAll(0, 0, width / 12, width / 12, super.getModel()[i],i);
			pits[i].setLayout(null);
			JLabel label = new JLabel("B" + (i - 6));
			player2Panel.add(pits[i]);
			pits[i].add(label);
			label.setBounds(width / 20, -width / 30, width / 12, width / 12);
		}
		topPanel.setLayout(new BorderLayout());
		topPanel.add(player2Panel, BorderLayout.CENTER);
		topPanel.add(buttonPanel, BorderLayout.SOUTH);
		this.add(topPanel,BorderLayout.NORTH);
		
		
		// Creates a left mancala (Player B's mancala)
		mancala2Panel = new JPanel();
		mancala2Panel.setPreferredSize(new Dimension(width / 4, height / 3));
		mancala2Panel.setVisible(true);
		JLabel M2label = new JLabel("M2");
		pits[13].add(M2label);
		pits[13].setAll(0, 0, width / 6, height / 4, super.getModel()[13],13);
		mancala2Panel.add(pits[13]);
		this.add(mancala2Panel, BorderLayout.WEST);
		mancala1Panel.setVisible(true);
		pits[6].setLayout(null);
		
		// Create a panel which shows the current player's turn
		playerTurnPanel = new JPanel();
		playerTurnPanel.setLayout(null);
		playerTurnPanel.setVisible(true);
		playerTurn = new JLabel("Player A's turn");
		playerTurn.setFont(new Font("Serif", Font.PLAIN, 20));
		playerTurnPanel.add(playerTurn);
		playerTurn.setBounds(width/5,width/8,width/2,width/10);
		this.add(playerTurnPanel, BorderLayout.CENTER);
	}
	
		
	
	/**
	 * Gets the current player's turn
	 * @return the current player's turn
	 */
	public JLabel getPlayerTurn()
	{
		return playerTurn;
	}
	
	/**
	 * Gets the array of pits
	 * @return the array of pits
	 */
	public Pit[] getPit()
	{
		return pits;
	}

	/**
	 * Attaches mouseListener to all the pits
	 * @param listener the MouseListener
	 */
	public void attachMouseListener(MouseListener listener)
	{
		for (int i = 0; i < pits.length; i++)
		{
			if (i != 6 && i != 13)
			{
				pits[i].attachMouseListener(listener);
			}
		}
	}
	
	/**
	 * Attaches action listener for undo Button and endTurn Button
	 * @param listener the action listener
	 */
	public void attachActionListener(ActionListener listener)
	{
		undoButton.addActionListener(listener);
		endButton.addActionListener(listener);
	}
	
	/**
	 * Fills color for the background
	 * @param color the color
	 */
	public void fillColor(Color color)
	{
		player1Panel.setBackground(color);
		mancala1Panel.setBackground(color);
		topPanel.setBackground(color);
		player2Panel.setBackground(color);
		playerTurnPanel.setBackground(color);
		mancala2Panel.setBackground(color);
		buttonPanel.setBackground(color);
		this.getContentPane().setBackground(color);
		
	}
	
	/**
	 * Implements a stateChanged method which control what to do when players click mouse
	 */
	public void stateChanged(ChangeEvent arg0) {
		for (int i = 0; i < this.getPit().length; i++)
			pits[i].setStones(super.getModel()[i]);
		int turn = this.getDataModel().getTurn();
		String player = (turn == 0 ? "Player A's turn" : "Player B's turn");
		this.getPlayerTurn().setText(player);
		repaint();
		int winner = this.getDataModel().getWinner();
		if (winner != -1)
		{
			if (winner == 2)
				JOptionPane.showMessageDialog(this, "DRAW!!!");
			else if (winner == 0)
				JOptionPane.showMessageDialog(this, "Player A won");
			else
				JOptionPane.showMessageDialog(this, "Player B won");

		}
		int currentUndo = this.getDataModel().getNumbOfUndo();
		if (currentUndo > this.getDataModel().MAX_UNDOS)
		{
			JOptionPane.showMessageDialog(this, "You have reached maximum Undo times");
		}
	}
	
}
