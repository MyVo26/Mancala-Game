import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;

/**
 * Implements a controller class which control what to do when users click mouse
 * @author constructor to specify starting number of stones
 */
public class Controller 
{
	BasicBoard board;
	
	/**
	 * Constructs a controller class with a given board
	 * @param basicBoard the basic board
	 */
	public Controller(BasicBoard basicBoard)
	{
		board = basicBoard;
		// What to do when users click on pits
		MouseListener mouseListener = new MouseListener()
		{

			public void mouseClicked(MouseEvent event) {
				Pit pit = (Pit) event.getSource();
				int pick = pit.getIndex();
				board.getDataModel().pick_pit(pick);
				board.stateChanged(new ChangeEvent(this));
				
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
			
		};
		
		// What to do when users click on buttons
		ActionListener actionListener = new ActionListener()
		{

			public void actionPerformed(ActionEvent event) {
				JButton button = (JButton) event.getSource();
				if (button.getText().equals("Undo"))
				{
					board.getDataModel().undo();
				}
				else
				{
					board.getDataModel().endTurn();
				}
				board.stateChanged(new ChangeEvent(this));
				
			}
			
		};
		
		board.attachMouseListener(mouseListener);
		board.attachActionListener(actionListener);
	}
	
	/**
	 * Gets the board
	 * @return the board
	 */
	public BasicBoard getBoard()
	{
		return board;
	}
}
