package chessInterfacePackage;

import javax.swing.*;

public class AlphaBetaChess {

	static String chessboard[][] = {
			{"r","k","b","q","a","b","k","r"},          
			{"p","p","p","p","p","p","p","p"},      //These are black
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{"R"," "," "," "," "," "," "," "},
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
		System.out.println(possibleMoves());
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
		String list = "", oldPiece; 
		int r = i/8, c = i%8; 
		int temp = 1; 
		for(int j = -1; j <= 1; j +=2) {
			try {
				while(" ".equals(chessboard[r][c + temp * j])) {
					oldPiece = chessboard[r][c + temp * j]; 
					chessboard[r][c] = " "; 
					chessboard[r][c + temp * j] = "R"; 
					if(kingSafe()) {
						list = list + r + c + r + (c + temp * j) + oldPiece; 
					}
					chessboard[r][c] = "R"; 
					chessboard[r][c + temp * j] = oldPiece; 
					temp++; 
				}
				if(Character.isLowerCase(chessboard[r][c + temp * j].charAt(0))) {
					oldPiece = chessboard[r][c + temp * j]; 
					chessboard[r][c] = " "; 
					chessboard[r][c + temp * j] = "R"; 
					if(kingSafe()) {
						list = list + r + c + r + (c + temp * j) + oldPiece; 
					}
					chessboard[r][c] = "R"; 
					chessboard[r][c + temp * j] = oldPiece; 
				}
			} catch (Exception e) {
				
			}
			temp = 1; 
			try {
				while(" ".equals(chessboard[r + temp * j][c])) {
					oldPiece = chessboard[r + temp * j][c]; 
					chessboard[r][c] = " "; 
					chessboard[r + temp * j][c] = "R"; 
					if(kingSafe()) {
						list = list + r + c + (r + temp * j) + c + oldPiece; 
					}
					chessboard[r][c] = "R"; 
					chessboard[r + temp * j][c] = oldPiece; 
					temp++; 
				}
				if(Character.isLowerCase(chessboard[r + temp * j][c].charAt(0))) {
					oldPiece = chessboard[r + temp * j][c]; 
					chessboard[r][c] = " "; 
					chessboard[r + temp * j][c] = "R"; 
					if(kingSafe()) {
						list = list + r + c + (r + temp * j) + c + oldPiece; 
					}
					chessboard[r][c] = "R"; 
					chessboard[r + temp * j][c] = oldPiece; 
				}
			} catch (Exception e) {
				
			}
			temp = 1; 
		}
		return list; 
	}
	
	public static String possibleK(int i) {
		String list = ""; 
		return list; 
	}
	
	public static String possibleB(int i) {
		String list = "", oldPiece; 
		int r = i/8, c = i%8; 
		int temp = 1; 
		for(int j = -1; j <= 1; j+=2) {
			for(int k = -1; k <= 1; k+=2) {
				try {
					while(" ".equals(chessboard[r + temp * j][c + temp * k])) {
						oldPiece = chessboard[r + temp * j][c + temp * k]; 
						chessboard[r][c] = " "; 
						chessboard[r + temp * j][c + temp * k] = "B"; 
						if(kingSafe()) {
							list = list + r + c + (r + temp * j) + (c + temp * k) + oldPiece; 
						}
						chessboard[r][c] = "B"; 
						chessboard[r + temp * j][c + temp * k] = oldPiece; 
						temp++; 
					}
					if(Character.isLowerCase(chessboard[r + temp * j][c + temp * k].charAt(0))) {
						oldPiece = chessboard[r + temp * j][c + temp * k]; 
						chessboard[r][c] = " "; 
						chessboard[r + temp * j][c + temp * k] = "B"; 
						if(kingSafe()) {
							list = list + r + c + (r + temp * j) + (c + temp * k) + oldPiece; 
						}
						chessboard[r][c] = "B"; 
						chessboard[r + temp * j][c + temp * k] = oldPiece; 
					}
				} catch (Exception e) {
					
				}
				temp = 1; 
			}
		}
		return list;  
	}
	
	public static String possibleQ(int i) {
		String list = "", oldPiece; 
		int r = i/8, c = i%8; 
		int temp = 1; 
		for(int j = -1; j <= 1; j++) {
			for(int k = -1; k <= 1; k++) {
				if(j != 0 || k != 0) {
					try {
						while(" ".equals(chessboard[r + temp * j][c + temp * k])) {
							oldPiece = chessboard[r + temp * j][c + temp * k]; 
							chessboard[r][c] = " "; 
							chessboard[r + temp * j][c + temp * k] = "Q"; 
							if(kingSafe()) {
								list = list + r + c + (r + temp * j) + (c + temp * k) + oldPiece; 
							}
							chessboard[r][c] = "Q"; 
							chessboard[r + temp * j][c + temp * k] = oldPiece; 
							temp++; 
						}
						if(Character.isLowerCase(chessboard[r + temp * j][c + temp * k].charAt(0))) {
							oldPiece = chessboard[r + temp * j][c + temp * k]; 
							chessboard[r][c] = " "; 
							chessboard[r + temp * j][c + temp * k] = "Q"; 
							if(kingSafe()) {
								list = list + r + c + (r + temp * j) + (c + temp * k) + oldPiece; 
							}
							chessboard[r][c] = "Q"; 
							chessboard[r + temp * j][c + temp * k] = oldPiece; 
						}
					} catch (Exception e) {
						
					}
					temp = 1; 
				}
			}
		}
		return list; 
	}
	
	public static String possibleA(int i) {
		String list = "", oldPiece; 
		int r = i/8, c = i%8; 
		//This loop will just loop around the positions that the king can make around him
		for(int j = 0; j < 9; j++) {
			if(j != 4) {
				try {
					//Check to see if position being evaluated is a piece that can be captured or if it's a blank space
					//meaning we could move there. 
					if(Character.isLowerCase(chessboard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessboard[r-1+j/3][c-1+j%3])) {
						//Whatever is in that position, set that as oldPiece
						oldPiece = chessboard[r-1+j/3][c-1+j%3]; 
						//The current position of the king, set that as a blank space
						chessboard[r][c] = " "; 
						//Set the king in the new position that is being evaluated
						chessboard[r-1+j/3][c-1+j%3] = "A"; 
						int kingTemp = kingPositionC; 
						//Set kingPositionC as the new position of the king, but just as a single int 
						kingPositionC = i + (j/3) * 8 + j%3 - 9; 
						//If this position is safe then add this move to the list of moves for the king 
						if(kingSafe()) {
							list = list + r + c + (r - 1 + j/3) + (c - 1 + j%3) + oldPiece; 
						}
						//Move the king back to its original spot since we are just checking for moves right now
						//Set the other positions back as well
						chessboard[r][c] = "A"; 
						chessboard[r-1+j/3][c-1+j%3] = oldPiece; 
						kingPositionC = kingTemp; 
					}
				} catch(Exception e) {
					
				}
			}
		}
		return list; //Will need to add castling later 
	}
	
	public static boolean kingSafe() {
		return true; 
	}
}
