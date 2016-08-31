package chessInterfacePackage;

import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
	
	static int mouseX, mouseY, newMouseX, newMouseY; 
	static int squareSize = 32; 
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.YELLOW);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		for(int i = 0; i < 64; i += 2) {
			//Set the lighter color first
			g.setColor(new Color(255, 200, 100)); 
			g.fillRect((i%8 + (i/8) % 2) * squareSize, (i/8) * squareSize, squareSize, squareSize);
			//Set the darker color next
			g.setColor(new Color(150, 50, 30));
			g.fillRect(((i + 1) % 8 - ((i + 1) / 8) % 2) * squareSize, ((i + 1) / 8) * squareSize, squareSize, squareSize);
		}
		Image chessPieceImage = new ImageIcon("ChessPieces.png").getImage(); 
		//g.drawImage(chessPieceImage, x, y, this);
		for(int i = 0; i < 64; i++) {
			int j = -1, k = -1; 
			switch(AlphaBetaChess.chessboard[i/8][i%8]) {
				case "P": j = 5; k = 0; 
					break;
				case "p": j = 5; k = 1; 
					break;
				case "R": j = 2; k = 0; 
					break;
				case "r": j = 2; k = 1; 
					break;
				case "K": j = 4; k = 0; 
					break;
				case "k": j = 4; k = 1; 
					break;
				case "B": j = 3; k = 0; 
					break;
				case "b": j = 3; k = 1; 
					break;
				case "Q": j = 1; k = 0; 
					break;
				case "q": j = 1; k = 1; 
					break;
				case "A": j = 0; k = 0; 
					break;
				case "a": j = 0; k = 1; 
					break;
			}
			if(j != -1 && k != -1) {
				g.drawImage(chessPieceImage, (i%8) * squareSize, (i/8) * squareSize, (i%8 + 1) * squareSize, 
						(i/8 + 1) * squareSize, j * 64, k * 64, (j + 1) * 64, (k + 1) * 64, this);
			}
		}
		
		/*g.setColor(Color.RED);
		g.fillRect(x - 10, y - 10, 20, 20);
		
		g.drawString("Eddy", x, y);
		
		Image chessPieceImage = new ImageIcon("ChessPieces.png").getImage(); 
		g.drawImage(chessPieceImage, x, y, this); */
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	}
	//This method is for when the mouse is just pressed 
	@Override
	public void mousePressed(MouseEvent e) {
		//Check to see if the press is within the board in order to move on
		if(e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
			//This is the position of the piece before it is moved
			mouseX = e.getX(); 
			mouseY = e.getY(); 
			repaint(); 
		}
	}
	//This method is for when the mouse button is released
	@Override
	public void mouseReleased(MouseEvent e) {
		//Check to see if the release is within the board in order for the move to count
		if(e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
			//This is the position of the piece after it is moved
			newMouseX = e.getX(); 
			newMouseY = e.getY(); 
			if(e.getButton() == MouseEvent.BUTTON1) {
				String dragMove; 
				//Check to see if it's in the format for pawn promotion
				if(newMouseY/squareSize == 0 && mouseY/squareSize == 1 && "P".equals(AlphaBetaChess.chessboard[mouseY/squareSize][mouseX/squareSize])) {
					//Pawn promotion move
					//Right now, this is assuming that the player picks a Queen as the new piece from promotion
					dragMove = "" + mouseX/squareSize + newMouseX/squareSize + AlphaBetaChess.chessboard[newMouseY/squareSize][newMouseX/squareSize] + "QP";
				}
				else {
					//Regular move 
					dragMove = "" + mouseY/squareSize + mouseX/squareSize + newMouseY/squareSize + newMouseX/squareSize + 
							AlphaBetaChess.chessboard[newMouseY/squareSize][newMouseX/squareSize]; 
				}
				//Check to see if the move made is in the compiled list of possible moves
				String userPossibilities = AlphaBetaChess.possibleMoves(); 
				if(userPossibilities.replaceAll(dragMove, "").length() < userPossibilities.length()) {
					//If the move is valid
					AlphaBetaChess.makeMove(dragMove);
				}
			}
			repaint(); 
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) { 
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
