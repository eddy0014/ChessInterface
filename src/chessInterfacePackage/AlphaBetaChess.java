package chessInterfacePackage;

import javax.swing.*;
import java.util.*; 

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
	static int globalDepth = 4; 
	
	public static void main(String[] args) {
		
		//Scan each spot to find the position of both Kings
		while(!"A".equals(chessboard[kingPositionC/8][kingPositionC%8])) {
			kingPositionC++; 
		}
		while(!"a".equals(chessboard[kingPositionL/8][kingPositionL%8])) {
			kingPositionL++; 
		}
		
		/*JFrame frame = new JFrame("Title goes here!"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setSize(500, 500); 
		UserInterface ui = new UserInterface(); 
		frame.add(ui); 
		frame.setVisible(true);*/
		System.out.println(possibleMoves());
		
		//This next line was used for testing alphaBeta() 
		//System.out.println(alphaBeta(globalDepth, 1000000, -1000000, "", 0)); 
		
		makeMove(alphaBeta(globalDepth, 1000000, -1000000, "", 0)); 
		/*
		makeMove("6050 "); 
		for(int i = 0; i < 8; i++) {
			System.out.println(Arrays.toString(chessboard[i])); 
		}
		undoMove("6050 "); */
		for(int i = 0; i < 8; i++) {
			System.out.println(Arrays.toString(chessboard[i])); 
		}
	}
	
	public static String alphaBeta(int depth, int beta, int alpha, String move, int player) {
		//Return in the format of x1,y1,x2,y2,captured piece,score 
		
	    //This next line is temporary that is used for testing if it works
		//String list = "1"; 
		
		String list = possibleMoves(); 
		//If the search hits the end of the line
		if(depth == 0 || list.length() == 0) {
			return move + (rating()* (player * 2 - 1));  //* (player * 2 - 1) remove if testing
		}
	   
		//Temporary used for testing alphaBeta() 
		/*
		list = ""; 
		System.out.print("How many moves are there?: "); 
		Scanner sc = new Scanner(System.in); 
		int temp = sc.nextInt(); 
		for(int i = 0; i < temp; i++) {
			list += "1111b"; 
		}*/
	    //End of temporary
		
		//Sort the moves from best to worst later
		player = 1 - player; //Player is either a 1 or 0 
		for(int i = 0; i < list.length(); i+=5) {
			makeMove(list.substring(i, i + 5));  
			flipBoard(); 
			String returnString = alphaBeta(depth - 1, beta, alpha, list.substring(i, i + 5), player); 
			int value = Integer.valueOf(returnString.substring(5)); 
			flipBoard(); 
			undoMove(list.substring(i, i + 5)); 
			if(player == 0) {
				if(value <= beta) {
					beta = value; 
					if(depth == globalDepth) {
						move = returnString.substring(0, 5); 
					}
				}
			}
			else {
				if(value > alpha) {
					alpha = value; 
					if(depth == globalDepth) {
						move = returnString.substring(0, 5); 
					}
				}
			}
			if(alpha >= beta) {
				if(player == 0) {
					return move + beta; 
				}
				else {
					return move + alpha; 
				}
			}
		}
		if(player == 0) {
			return move + beta; 
		}
		else {
			return move + alpha; 
		}
	}
	
	public static void flipBoard() {
		String temp; 
		for(int i = 0; i < 32; i++) {
			int r = i/8, c = i%8; 
			if(Character.isUpperCase(chessboard[r][c].charAt(0))) {
				temp = chessboard[r][c].toLowerCase(); 
			}
			else {
				temp = chessboard[r][c].toUpperCase(); 
			}
			if(Character.isUpperCase(chessboard[7 - r][7 - c].charAt(0))) {
				chessboard[r][c] = chessboard[7 - r][7 - c].toLowerCase(); 
			}
			else {
				chessboard[r][c] = chessboard[7 - r][7 - c].toUpperCase();
			}
			chessboard[7 - r][7 - c] = temp; 
		}
		int kingTemp = kingPositionC; 
		kingPositionC = 63 - kingPositionL; 
		kingPositionL = 63 - kingTemp; 
	}
	
	public static void makeMove(String move) {
		if(move.charAt(4) != 'P') {
			chessboard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = 
					chessboard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
			chessboard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " "; 
			//Deals with the flip of the board
			if("A".equals(chessboard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
				kingPositionC = 8 * Character.getNumericValue(move.charAt(2)) + Character.getNumericValue(move.charAt(3));
			}
		}
		//If pawn promotion
		else {
			chessboard[1][Character.getNumericValue(move.charAt(0))] = " "; 
			chessboard[0][Character.getNumericValue(move.charAt(1))] = String.valueOf(move.charAt(3)); 
		}
	}
	
	public static void undoMove(String move) {
		if(move.charAt(4) != 'P') {
			chessboard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = 
					chessboard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
			chessboard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] =
					String.valueOf(move.charAt(4)); 
			//Deals with the flip of the board
			if("A".equals(chessboard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])) {
				kingPositionC = 8 * Character.getNumericValue(move.charAt(0)) + Character.getNumericValue(move.charAt(1));
			}
		}
		//If pawn promotion
		else {
			chessboard[1][Character.getNumericValue(move.charAt(0))] = "P"; 
			chessboard[0][Character.getNumericValue(move.charAt(1))] = String.valueOf(move.charAt(2)); 
		}
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
		String list = "", oldPiece; 
		int r = i/8, c = i%8; 
		for(int j = -1; j <= 1; j +=2) {
			//Look for possible captures
			try {
				if(Character.isLowerCase(chessboard[r - 1][c + j].charAt(0)) && i >= 16) {
					oldPiece = chessboard[r - 1][c + j]; 
					chessboard[r][c] = " "; 
					chessboard[r - 1][c + j] = "P"; 
					if(kingSafe()) {
						list = list + r + c + (r - 1) + (c + j) + oldPiece; 
					}
					chessboard[r][c] = "P"; 
					chessboard[r - 1][c + j] = oldPiece; 
				}
			} catch(Exception e) {
				
			}
			//Promotion and capture
			try {
				if(Character.isLowerCase(chessboard[r - 1][c + j].charAt(0)) && i < 16) {
					String[] temp = {"Q", "R", "B", "K"}; 
					for(int k = 0; k < 4; k++) {
						oldPiece = chessboard[r - 1][c + j]; 
						chessboard[r][c] = " "; 
						chessboard[r - 1][c + j] = temp[k];  
						if(kingSafe()) {
							//Column1, column2, captured piece, new piece, P
							list = list + c + (c + j) + oldPiece + temp[k] + "P";  
						}
						chessboard[r][c] = "P"; 
						chessboard[r - 1][c + j] = oldPiece; 
					}
				}
			} catch(Exception e) {
				
			}
		}
		//Move one up
		try { 
			if(" ".equals(chessboard[r - 1][c]) && i >= 16) {
				oldPiece = chessboard[r - 1][c]; 
				chessboard[r][c] = " "; 
				chessboard[r - 1][c] = "P"; 
				if(kingSafe()) {
					list = list + r + c + (r - 1) + c + oldPiece; 
				}
				chessboard[r][c] = "P"; 
				chessboard[r - 1][c] = oldPiece; 
			}
		} catch(Exception e) {
			
		}
		//Promotion and no capture 
		try { 
			if(" ".equals(chessboard[r - 1][c]) && i < 16) {
				String[] temp = {"Q", "R", "B", "K"}; 
				for(int k = 0; k < 4; k++) {
					oldPiece = chessboard[r - 1][c]; 
					chessboard[r][c] = " "; 
					chessboard[r - 1][c] = temp[k];  
					if(kingSafe()) {
						//Column1, column2, captured piece, new piece, P
						list = list + c + c + oldPiece + temp[k] + "P";  
					}
					chessboard[r][c] = "P"; 
					chessboard[r - 1][c] = oldPiece; 
				}
			}
		} catch(Exception e) {
			
		}
		//Move two up
		try { 
			if(" ".equals(chessboard[r - 1][c]) && " ".equals(chessboard[r - 2][c]) && i >= 48) {
				oldPiece = chessboard[r - 2][c]; 
				chessboard[r][c] = " "; 
				chessboard[r - 2][c] = "P"; 
				if(kingSafe()) {
					list = list + r + c + (r - 2) + c + oldPiece; 
				}
				chessboard[r][c] = "P"; 
				chessboard[r - 2][c] = oldPiece; 
			}
		} catch(Exception e) {

		}
		
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
		String list = "", oldPiece; 
		int r = i/8, c = i%8; 
		for(int j = -1; j <= 1; j+=2) {
			for(int k = -1; k <= 1; k+=2) {
				try {
					if(Character.isLowerCase(chessboard[r + j][c + k * 2].charAt(0)) || " ".equals(chessboard[r + j][c + k * 2])) {
						oldPiece = chessboard[r + j][c + k * 2]; 
						chessboard[r][c] = " "; 
						chessboard[r + j][c + k * 2] = "K"; 
						if(kingSafe()) {
							list = list + r + c + (r + j) + (c + k * 2) + oldPiece; 
						}
						chessboard[r][c] = "K"; 
						chessboard[r + j][c + k * 2] = oldPiece; 
					}
				} catch (Exception e) {

				}
				try {
					if(Character.isLowerCase(chessboard[r + j * 2][c + k].charAt(0)) || " ".equals(chessboard[r + j * 2][c + k])) {
						oldPiece = chessboard[r + j * 2][c + k]; 
						chessboard[r][c] = " "; 
						chessboard[r + j * 2][c + k] = "K"; 
						if(kingSafe()) {
							list = list + r + c + (r + j * 2) + (c + k) + oldPiece; 
						}
						chessboard[r][c] = "K"; 
						chessboard[r + j * 2][c + k] = oldPiece; 
					}
				} catch (Exception e) {

				}
			}
		}
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
	
	public static int rating() {
		//All this is temporary used for testing
		/*
		System.out.print("What is the score?: "); 
		Scanner sc = new Scanner(System.in); 
		return sc.nextInt(); */
		return 0; 
	}
	
	public static boolean kingSafe() {
		//Bishop or Queen
		//Picked these two since these are the ones to most likely pose the greatest danger to the King, meaning it
		//would end the search quicker. 
		int temp = 1; 
		for(int i = -1; i <= 1; i+=2) {
			for(int j = -1; j <= 1; j+=2) {
				try {
					while(" ".equals(chessboard[kingPositionC/8 + temp * i][kingPositionC%8 + temp * j])) {
							temp++;
						}
						if("b".equals(chessboard[kingPositionC/8 + temp * i][kingPositionC%8 + temp * j]) ||
								"q".equals(chessboard[kingPositionC/8 + temp * i][kingPositionC%8 + temp * j])) {
							return false;  
					}
				} catch (Exception e) {
						
					}
				temp = 1; 
			}
		}
		
		//Rook or Queen
		for(int i = -1; i <= 1; i+=2) {
				try {
					while(" ".equals(chessboard[kingPositionC/8][kingPositionC%8 + temp * i])) {
							temp++;
						}
						if("r".equals(chessboard[kingPositionC/8][kingPositionC%8 + temp * i]) ||
								"q".equals(chessboard[kingPositionC/8][kingPositionC%8 + temp * i])) {
							return false;  
					}
				} catch (Exception e) {
						
					}
				temp = 1; 
				try {
					while(" ".equals(chessboard[kingPositionC/8 + temp * i][kingPositionC%8])) {
							temp++;
						}
						if("r".equals(chessboard[kingPositionC/8 + temp * i][kingPositionC%8]) ||
								"q".equals(chessboard[kingPositionC/8 + temp * i][kingPositionC%8])) {
							return false;  
					}
				} catch (Exception e) {
						
					}
				temp = 1; 
		}
		
		//Knight 
		for(int i = -1; i <= 1; i+=2) {
			for(int j = -1; j <= 1; j+=2) {
				try {
						if("k".equals(chessboard[kingPositionC/8 + i][kingPositionC%8 + j * 2])) {
							return false;  
					}
				} catch (Exception e) {
						//If the knight is on the edge of the board, this catch statement will prevent any errors from
						//happening when searching outside the board and just move on 
					}
				try {
					//This is in its own try-catch and not with the other one because if the first search causes an error,
					//then it will just skip the second and go straight to catch that error
					if("k".equals(chessboard[kingPositionC/8 + i * 2][kingPositionC%8 + j])) {
						return false;  
				}
			} catch (Exception e) {
					
				}
			}
		}
		
		//Pawn
		//Don't bother checking if the King is alongside or behind the initial enemy pawn line, since no pawn would be able to 
		//look back and attack
		if(kingPositionC >= 16) {
			try {
				if("p".equals(chessboard[kingPositionC/8 - 1][kingPositionC%8 - 1])) {
					return false;  
				}
			} catch (Exception e) {

			}
			try {
				if("p".equals(chessboard[kingPositionC/8 - 1][kingPositionC%8 + 1])) {
					return false;  
				}
			} catch (Exception e) {

			}
			//King
			//This is placed at the end of the search since it is the least likely scenario to happen.
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					if(i != 0 || j != 0) {
						try {
							if("a".equals(chessboard[kingPositionC/8 + i][kingPositionC%8 + j])) {
								return false;  
							}
						} catch (Exception e) {
							
						}
					}
				}
			}
		}
		
		return true; 
	}
}
