import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Implements a square pit 
 * @author My Vo, Rachel Stanik, Son Vo
 *
 */
public class SquarePit extends Pit{

	/** Constructs a square pit based on given information
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param width the board's width
	 * @param height the board's height
	 * @param stones the number of stones 
	 * @param ind the position of the pit
	 */
	public SquarePit(int x, int y, int width, int height, int stones, int ind) {
		super(x, y, width, height, stones, ind);
	}

	/**
	 * Overrides paintComponent method
	 * @Override
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D.Double rectangle = new Rectangle2D.Double(x + width / 4, y + height / 4, width, height);
		g2.setColor(Color.WHITE);
		g2.fill(rectangle);
		
		double x  = rectangle.getCenterX() - 30;
		double y = rectangle.getCenterY() - 30;
		
		for(int i = 0; i < stones; i++) {
			g2.setColor(Color.BLACK);
			if(i % 2 == 0) {
				g2.setColor(Color.BLUE);
			}
			if(i % 4 == 0 && i != 0) { //format in rows
				y += 10;
				x -= 60;
			}
			if(i > 23) {
				if(i + 1 % 24 == 0) {
					x = rectangle.getCenterX() - 20;
					y = rectangle.getCenterY() - 60;
				}
				g2.setColor(Color.BLUE);
				if(i % 2 == 0) {
					g2.setColor(Color.BLACK);
				}	
			}

			g2.fill(new Ellipse2D.Double(x + (i * 15), y, 10, 10));
		}
	}
	
}
