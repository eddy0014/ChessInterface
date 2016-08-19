package chessInterfacePackage;

import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
	
	static int x = 0, y = 0; 
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.YELLOW);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		g.setColor(Color.RED);
		g.fillRect(x - 10, y - 10, 20, 20);
		
		g.drawString("Eddy", x, y);
		
		Image chessPieceImage = new ImageIcon("ChessPieces.png").getImage(); 
		g.drawImage(chessPieceImage, x, y, this); 
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		x = e.getX();
		y = e.getY(); 
		repaint(); 
	}
	@Override
	public void mouseDragged(MouseEvent e) {	
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {	
	}
}
