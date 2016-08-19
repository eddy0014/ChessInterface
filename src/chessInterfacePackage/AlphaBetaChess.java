package chessInterfacePackage;

import javax.swing.*;

public class AlphaBetaChess {

	static String chessboard[][] = {
			{"r","k","b","q","a","b","k","r"},          
			{"p","p","p","p","p","p","p","p"},      //These are black
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{"P","P","P","P","P","P","P","P"},      //These are white
			{"R","K","B","Q","A","B","K","R"}
	};

	//Variables used to monitor the position of the king using a solid number
	static int kingPositionC, kingPositionL; 
	
	public static void main(String[] args) {
		/*JFrame frame = new JFrame("Title goes here!"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setSize(500, 500); 
		UserInterface ui = new UserInterface(); 
		frame.add(ui); 
		frame.setVisible(true);*/
	}

	/**
	 * This will return a huge list of moves that the computer will be able to try
	 * from all the pieces
	 */
	public static String possibleMoves() {
		String list = ""; 
		for(int i = 0; i < 64; i++) {
			switch(chessboard[i/8][i%8]) {
				case "P": list += possibleP(i); 
				break;
				case "R": list += possibleR(i);
				break;
				case "K": list += possibleK(i);
				break;
				case "B": list += possibleB(i);
				break;
				case "Q": list += possibleQ(i);
				break;
				case "A": list += possibleA(i);
				break;
			}
		}
		return list; 
	}
	
	/**
	 * These next possible methods will return a list of the possible 
	 * moves for that certain piece which are to be added to the huge 
	 * list 
	 */
	public static String possibleP(int i) {
		String list = ""; 
		return list; 
	}
	
	public static String possibleR(int i) {
		String list = ""; 
		return list; 
	}
	
	public static String possibleK(int i) {
		String list = ""; 
		return list; 
	}
	
	public static String possibleB(int i) {
		String list = ""; 
		return list; 
	}
	
	public static String possibleQ(int i) {
		String list = ""; 
		return list; 
	}
	
	public static String possibleA(int i) {
		String list = "", oldPiece; 
		int r = i/8, c = i%8; 
		for(int j = 0; j < 9; j++) {
			if(j != 4) {
				try {
					if(Character.isLowerCase(chessboard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessboard[r-1+j/3][c-1+j%3])) {
						oldPiece = chessboard[r-1+j/3][c-1+j%3]; 
						chessboard[r][c] = " "; 
						chessboard[r-1+j/3][c-1+j%3] = "A"; 
						int kingTemp = kingPositionC; 
						kingPositionC = i + (j/3) * 8 + j%3 - 9; 
						if(kingSafe()) {
							list = list + r + c + (r - 1 + j/3) + (c - 1 + j%3) + oldPiece; 
						}
						//Move the king back since we are just checking for moves right now
						chessboard[r][c] = "A"; 
						chessboard[r-1+j/3][c-1+j%3] = oldPiece; 
						kingPositionC = kingTemp; 
					}
				} catch(Exception e) {
					
				}
			}
		}
		return list; //Will need to add castling later 
		//Make sure to take time to fully understand all the math that is going on in here
	}
	
	public static boolean kingSafe() {
		return true; 
	}
}
