import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

/**
 * Implements a pit in abord
 * @author My Vo, Rachel Stanik, Son Vo
 */
public class Pit extends JPanel {
	int x;
	int y;
	int width;
	int height;
	int stones;
	int index;
	JLabel label = new JLabel();
	
	/**
	 * Constructs a pit with given information
	 * @param x the X coordinate
	 * @param y the y coordinate
	 * @param width the pit's width
	 * @param height the pit's height
	 * @param numOfStones the number of stones
	 * @param index the position of the pit
	 */
	public Pit(int x, int y, int width, int height, int numOfStones, int index) {
		setAll(x, y, width, height, numOfStones, index);
	}

	/**
	 * Sets information to a pit
	 * @param x the X coordinate
	 * @param y the y coordinate
	 * @param width the pit's width
	 * @param height the pit's height
	 * @param numOfStones the number of stones
	 * @param index the position of the pit
	 */
	public void setAll(int x, int y, int width, int height, int numOfStones, int index)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.stones = numOfStones;
		this.index = index;
		this.setPreferredSize(new Dimension(width*3/2, height*3/2));
	}
	
	/**
	 * Sets number of stones in each pit
	 * @param i the number of stones
	 */
	public void setStones(int i) {
		stones = i;
	}
	
		
	/**
	 * Overrides the painComponent method
	 * @Override
	 */
	public void paintComponent(Graphics g) {
//		System.out.println("index: " + index + "  stones: " + stones);
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double circle = new Ellipse2D.Double(x + width / 4, y + height / 4, width, height);
		g2.draw(circle);
		double x  = circle.getCenterX() - 20;
		double y = circle.getCenterY() - 30;
		
		for(int i = 0; i < stones; i++) {
			g2.setColor(Color.BLACK);
			if( i % 2 == 0) {
				g2.setColor(Color.RED);
			}
			if(i % 4 == 0 && i != 0) { //format in rows
				y += 10;
				x -= 40;
			}
			if(i > 23) {
				if(i +1 % 24 == 0) {
					x = circle.getCenterX() - 20;
					y = circle.getCenterY() - 60;
				}
				g2.setColor(Color.RED);
				if(i % 2 == 0) {
					g2.setColor(Color.BLACK);
				}	
			}
			g2.fill(new Ellipse2D.Double(x + (i * 10), y, 10, 10));
		}		
	}
	
	/**
	 * Attaches mouse listener to the pit
	 * @param listener the mouse listener
	 */
	public void attachMouseListener(MouseListener listener)
	{
		addMouseListener(listener);
	}
	
	/**
	 * Gets the position of the pit
	 * @return the position of the pit
	 */
	public int getIndex()
	{
		return index;
	}
}
