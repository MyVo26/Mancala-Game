import java.util.ArrayList;

/**
 * Implements DataModel class for the board
 * @author My Vo, Rachel Stanik, Son Vo
 */
public class DataModel 
{
	private int[] holes;	// The pits
	private int[] temp_saved; // Store all the information for undo
	private int current_undo; // the number of undo (maximum 3 times) 
	private int current_player; // the current player
	private int last_pick; // the last pick
	private int previous_pick; // this is the position of pit when users end turn
	private int last_position; // this is the position of pit when users click mouse but not end turn 
	private boolean isLastPitMancala; // decide is the last pit mancala or not
	private boolean additionalTurn; // decide is there additional turn or not
	private int winner; 
	private static final int MAX_STONES = 4;
	private static final int HOLES = 14;
	public static final int MAX_UNDOS = 3;
	private static final int MANCALA_IND1 = 6;
	private static final int MANCALA_IND2 = 13;
	
	/**
	 * Constructs DataModel class
	 * @param stones the number of stones in each pit
	 */
	public DataModel(int stones)
	{
		additionalTurn = false;
		holes = new int[HOLES];
		temp_saved = new int[HOLES];
		current_undo = 0;
		current_player = 0;
		last_pick = -1;
		previous_pick = -1;
		isLastPitMancala = false;
		winner = -1;
		last_position = -1;
		initial(stones);
	}
	
	/**
	 * Distributes the stones into each pits, except mancalas
	 * @param stones the number of stones
	 * Post-condition: the number of stones cannot exceed 4 (MAXIMUM_STONES)
	 */
	public void initial(int stones)
	{
		int stones_in_pit = Math.min(stones, MAX_STONES);
		for (int i = 0; i < HOLES; i++ )
		{
			if (i == MANCALA_IND1 || i == MANCALA_IND2)
			{
				holes[i] = 0;
			}
			else
			{
				holes[i] = stones_in_pit;
			}
		}
	}
	
	/**
	 * Implements pick pit method
	 * @param i the index of the pit
	 */
	public void pick_pit(int i)
	{
		if (isValidPick(i) == false)
			return;
		save();
		int stones = holes[i];
		holes[i] = 0;	// Removes all the stones in the pit
		last_pick = i;	// save the last pick
		i++;
		distribution(i, stones); // Distribute stones
		checkLastPitMancala(); // Check if the last pit is mancala
		checkLastPitEmpty(); // Check if the last pit is empty
	}
	
	/**
	 * Checks whose turn based on the given index
	 * @param i the index
	 * @return the player's turn
	 */
	public int decideWhoseTurn(int i)
	{
		if (i < 0)
			return i; // If negative index was passed in, then return that negative value
					  // Negative index is only for the initial, when players have not picked pit
		return (i <= MANCALA_IND1 ? 0 : 1);
	}
	
	/**
	 * Checks if the player choose the valid pit or not
	 * @param i the index of the pit
	 * @return true if valid and false if not
	 */
	public boolean isValidPick(int i)
	{
		
		if (holes[i] == 0)
		{
			return false;
		}
		int previous_player_turn = decideWhoseTurn(previous_pick);
		int current_pick_side = decideWhoseTurn(i);
		int last_pick_side = decideWhoseTurn(last_pick);
		if (i == MANCALA_IND1 || i == MANCALA_IND2)
		{
			// If the player clicks on mancalas, nothing happens
			return false;
		}
		if (isLastPitMancala == true && current_pick_side != previous_player_turn)
		{
			return false;
		}
		if (isLastPitMancala == false && current_pick_side == previous_player_turn)
		{
			return false;
		}
		if (isLastPitMancala == true && current_pick_side != last_pick_side)
		{
			return false;
		}
		if (isLastPitMancala == false && current_pick_side == last_pick_side)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Distributes the stones counter-clockwise from the given index
	 * @param fromIndex the given index
	 * @param stones the number of stones need to be distributed
	 */
	public void distribution(int fromIndex, int stones)
	{
		int passing_mancala = 0;
		while (stones > 0)
		{
			if (fromIndex == MANCALA_IND1 || fromIndex == MANCALA_IND2)
			{
				passing_mancala++;
				if (passing_mancala % 2 == 0)
				{
					fromIndex++;
					if (fromIndex > 13)
						fromIndex = fromIndex %14;
					continue; // stones will not be put into the opponent's mancala
				}
			}
			holes[fromIndex]++;
			stones--;
			fromIndex = (fromIndex + 1) % 14;
		}
		last_position = fromIndex-1; // Decrement fromIndex to get the last distributed position 
		
		if (last_position == -1)
		{
			last_position = MANCALA_IND2;
		}
	}
	
	
	/**
	 * Checks if the last distributed pit is empty and get all the stones from 
	 *   opposite pit and put it into the current player's mancala
	 */
	public void checkLastPitEmpty()
	{
		if (last_position == MANCALA_IND1 || last_position == MANCALA_IND2)
		{
			// Do nothing when the last pit is mancala
			// we have a different method to handle this case
			return;
		}
		if (decideWhoseTurn(last_position) == current_player && holes[last_position] == 1)
		{
			// The last pit is empty if the number of stones in it = 1 after distribution
			int opposite_index = 12 - last_position;
			if (holes[opposite_index] == 0)
			{
				return;
			}
			if (current_player == 0)
			{
				
				holes[MANCALA_IND1] += holes[opposite_index] + 1; 
			}
			else if (current_player == 1)
			{
				holes[MANCALA_IND2] += holes[opposite_index] + 1;
			}
			holes[opposite_index] = 0;
			holes[last_position] = 0;
		}
	}
	
	/**
	 * Checks if the last distributed hole is a mancala of the current player. 
	 */
	public void checkLastPitMancala()
	{
		if (last_position == MANCALA_IND1 && current_player == 0)
		{
			isLastPitMancala = true;
		}
		else if (last_position == MANCALA_IND2 && current_player == 1)
		{
			isLastPitMancala = true;
		}
		else
		{
			isLastPitMancala = false;
		}
	}
	
	public boolean isChanged()
	{
		for (int i = 0; i < holes.length; i++)
		{
			if (holes[i] != temp_saved[i])
			{
				return true;
			}
		}
		return false;
	}
	public void endTurn()
	{
		if (isChanged() == false)
		{
			return;
		}
		// If the last pit is not a mancala, then increment player's turn
		additionalTurn = isLastPitMancala;
		if (isLastPitMancala == false)
		{
			
			current_player = (current_player+1)%2;
			
		}
		this.current_undo = 0; // reset the number of undo
		previous_pick = last_pick;
		if (isEnd() == true) // check if the game is end
		{
			collectRemaining(); 
			decideWhoWin();
		}
	}
	
	/**
	 * Decides who will win the game
	 */
	public void decideWhoWin()
	{
		if (holes[MANCALA_IND1] == holes[MANCALA_IND2])
		{
			winner = 2; // winner == 2 means draw
		}
		else if (holes[MANCALA_IND1] > holes[MANCALA_IND2])
		{
			winner = 0;
		}
		else
		{
			winner = 1;
		}
	}
	
	/**
	 * Collects all the remaining stones when all the pits in one side are empty
	 */
	public void collectRemaining()
	{
		int index = 0;
		int mancala = MANCALA_IND1;
		if (holes[index] == 0)
		{
			index = MANCALA_IND1+1;
			mancala = MANCALA_IND2;
		}
		for (; index < mancala; index++)
		{
			int stones = holes[index];
			holes[index] = 0;
			holes[mancala] += stones;
		}
	}
	
	/**
	 * Implements undo function
	 * @return false when play reachs maximum undo, else true
	 */
	public boolean undo()
	{
		if (previous_pick == last_pick)
		{
			// Do nothing when player have not picked hole but click the undo button
			return true;
		}
		current_undo++;
		if (current_undo > MAX_UNDOS)
		{
			// Do nothing when reach maximum undo
			return false;
		}
		isLastPitMancala = additionalTurn;
		last_pick = previous_pick;
		for (int i = 0; i < HOLES; i++)
			holes[i] = temp_saved[i];
		return true;
	}
	
	/**
	 * Checks if the array is empty or not based on from and to index
	 * @param arr the array
	 * @param from_index  the start index
	 * @param to_index the end index
	 * @return true if the portion of the array is empty
	 */
	public boolean IsEmptyArray(int arr[], int from_index, int to_index)
	{
		while (from_index < to_index)
		{
			if (arr[from_index] != 0)
			{
				return false;
			}
			from_index++;
		}
		return true;
	}
	
	/**
	 * Checks if the game is end
	 * @return true if end, false if not
	 */
	public boolean isEnd()
	{
		if (IsEmptyArray(holes, 0, MANCALA_IND1) == true)
		{
			return true;
		}
		if (IsEmptyArray(holes, MANCALA_IND1+1, MANCALA_IND2) == true)
		{
			return true;
		}
		return false;
	}
	
	
	
	
	/**
	 * Gets the data
	 * @return the data
	 */
	public int[] getData()
	{
		return holes;
	}

	/**
	 * Gets the winner
	 * @return the winner
	 */
	public int getWinner()
	{
		return winner;
	}
	
	/**
	 * Saves the data after each pick so that we can undo
	 */
	
	public int getTurn()
	{
		return current_player;
	}
	public void save()
	{
		for (int i = 0; i < HOLES; i++)
		{
			temp_saved[i] = holes[i];
		}
	}
	
	/**
	 * Gets the number of undo
	 * @return the number of undo
	 */
	public int getNumbOfUndo()
	{
		return current_undo;
	}
}
