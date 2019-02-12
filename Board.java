import javax.swing.*;
import java.awt.*;

/**
 * Implements the board class which is the parrent of Basic Board and Square Board
 * This class serves as a view class
 * @author My Vo, Rachel Stanik, Son Vo
 */
public abstract class Board extends JFrame {
	private int boardWidth;
	private int boardHeight;
	private DataModel model; //holds reference to the model class
	
	/**
	 * Constructs a board which the given information 
	 * @param width the board's width
	 * @param height the board's height
	 * @param model the DataModel
	 */
	public Board(int width, int height ,DataModel model) {
		this.model  = model;
		boardWidth = width;
		boardHeight = height;
		this.setResizable(false);
	}
	
	/**
	 * Gets the board's width
	 * @return the board's width
	 */
	public int getWidth() {
		return boardWidth;
	}
	
	/**
	 * Gets the board's height
	 * @return the board's height
	 */
	public int getHeight() {
		return boardHeight;
	}
	
	/**
	 * Gets the array of pits
	 * @return the array of pits
	 */
	public int[] getModel(){
		return model.getData();
	}
	
	/**
	 * Gets the DataModel
	 * @return the DataModel
	 */
	public DataModel getDataModel()
	{
		return model;
	}
	
}
