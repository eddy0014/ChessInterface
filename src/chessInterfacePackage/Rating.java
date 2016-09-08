package chessInterfacePackage;

public class Rating {
	/**
	 * @param list
	 * Check to see if there are no moves due to checkmate or stalemate
	 * @param depth
	 * A deeper move has a higher rating than a move on the surface. With every
	 * depth, multiply by 50 when returning the rating. 
	 */
	public static int rating(int list, int depth) {
		//All this is temporary used for testing
		/*
		System.out.print("What is the score?: "); 
		Scanner sc = new Scanner(System.in); 
		return sc.nextInt(); */
		
		//Rate every possible move that can be made
		int counter = 0; 
		counter += rateAttack(); 
		counter += rateMaterial();
		counter += rateMoveability();
		counter += ratePositional();
		AlphaBetaChess.flipBoard();
		//Then subtract from the other side (black)
		//This takes the best moves the other side can make 
		//and then subtracts it 
		counter -= rateAttack(); 
		counter -= rateMaterial();
		counter -= rateMoveability();
		counter -= ratePositional();
		AlphaBetaChess.flipBoard();
		//This is negative due to how the algorithm is written
		return -(counter + depth * 50); 
	}
	
	public static int rateAttack() {
		return 0; 
	}
	
	public static int rateMaterial() {
		int counter = 0, bishopCounter = 0; 
		
		for(int i = 0; i < 64; i++) {
			switch(AlphaBetaChess.chessboard[i/8][i%8]) {
			//These ratings for each piece are a rule of thumb for each engine. 
				case "P": counter += 100; 
					break;
				case "R": counter += 500; 
					break;
				case "K": counter += 300; 
					break;
				case "B": bishopCounter += 1; 
					break;
				case "Q": counter += 900; 
					break;
			}
		}
		//This is done because having two bishops means having more power
		//compared to just one. If one bishop is lost, then half of the
		//positions on the board for a bishop are lost
		if(bishopCounter >= 2) {
			counter += 300 * bishopCounter; 
		}
		else {
			//CHANGE this code since it can be written differently
			if(bishopCounter == 1) {
				counter += 250; 
			}
		}
		return counter; 
	}
	
	public static int rateMoveability() {
		return 0; 
	}
	
	public static int ratePositional() {
		return 0; 
	}
}
