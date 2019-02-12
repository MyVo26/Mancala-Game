import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Implements a square board which contains square pits and square mancalas
 * @author My Vo, Rachel Stanik, Son Vo
 */
public class SquareBoard extends BasicBoard{
private Color backColor = Color.GREEN;
	
	/**
	 * Constructs a board based on given information
	 * @param width the board's width
	 * @param height the board's height
	 * @param model the model class
	 * @param arr the pits array
	 */
	public SquareBoard(int width, int height, DataModel model, SquarePit arr[]) {
		super(width, height, model, arr); 
		super.fillColor(backColor);
	}	
	
}
