package chessInterfacePackage;

public class Rating {
	
	//Used for positional
	//It may be helpful to look up simplified evaluation function
	static int pawnBoard[][] = {
			{ 0,  0,  0,  0,  0,  0,  0,  0},
	        {50, 50, 50, 50, 50, 50, 50, 50},
	        {10, 10, 20, 30, 30, 20, 10, 10},
	        { 5,  5, 10, 25, 25, 10,  5,  5},
	        { 0,  0,  0, 20, 20,  0,  0,  0},
	        { 5, -5,-10,  0,  0,-10, -5,  5},
	        { 5, 10, 10,-20,-20, 10, 10,  5},
	        { 0,  0,  0,  0,  0,  0,  0,  0}};
	static int rookBoard[][] = {
			{ 0,  0,  0,  0,  0,  0,  0,  0},
	        { 5, 10, 10, 10, 10, 10, 10,  5},
	        {-5,  0,  0,  0,  0,  0,  0, -5},
	        {-5,  0,  0,  0,  0,  0,  0, -5},
	        {-5,  0,  0,  0,  0,  0,  0, -5},
	        {-5,  0,  0,  0,  0,  0,  0, -5},
	        {-5,  0,  0,  0,  0,  0,  0, -5},
	        { 0,  0,  0,  5,  5,  0,  0,  0}};
	static int knightBoard[][] = {
			{-50,-40,-30,-30,-30,-30,-40,-50},
	        {-40,-20,  0,  0,  0,  0,-20,-40},
	        {-30,  0, 10, 15, 15, 10,  0,-30},
	        {-30,  5, 15, 20, 20, 15,  5,-30},
	        {-30,  0, 15, 20, 20, 15,  0,-30},
	        {-30,  5, 10, 15, 15, 10,  5,-30},
	        {-40,-20,  0,  5,  5,  0,-20,-40},
	        {-50,-40,-30,-30,-30,-30,-40,-50}};
	static int bishopBoard[][] = {
			{-20,-10,-10,-10,-10,-10,-10,-20},
	        {-10,  0,  0,  0,  0,  0,  0,-10},
	        {-10,  0,  5, 10, 10,  5,  0,-10},
	        {-10,  5,  5, 10, 10,  5,  5,-10},
	        {-10,  0, 10, 10, 10, 10,  0,-10},
	        {-10, 10, 10, 10, 10, 10, 10,-10},
	        {-10,  5,  0,  0,  0,  0,  5,-10},
	        {-20,-10,-10,-10,-10,-10,-10,-20}};
	static int queenBoard[][] = {
			{-20,-10,-10, -5, -5,-10,-10,-20},
	        {-10,  0,  0,  0,  0,  0,  0,-10},
	        {-10,  0,  5,  5,  5,  5,  0,-10},
	        { -5,  0,  5,  5,  5,  5,  0, -5},
	        {  0,  0,  5,  5,  5,  5,  0, -5},
	        {-10,  5,  5,  5,  5,  5,  0,-10},
	        {-10,  0,  5,  0,  0,  0,  0,-10},
	        {-20,-10,-10, -5, -5,-10,-10,-20}};
	static int kingMidBoard[][] = {
			{-30,-40,-40,-50,-50,-40,-40,-30},
	        {-30,-40,-40,-50,-50,-40,-40,-30},
	        {-30,-40,-40,-50,-50,-40,-40,-30},
	        {-30,-40,-40,-50,-50,-40,-40,-30},
	        {-20,-30,-30,-40,-40,-30,-30,-20},
	        {-10,-20,-20,-20,-20,-20,-20,-10},
	        { 20, 20,  0,  0,  0,  0, 20, 20},
	        { 20, 30, 10,  0,  0, 10, 30, 20}};
	static int kingEndBoard[][] = {
			{-50,-40,-30,-20,-20,-30,-40,-50},
	        {-30,-20,-10,  0,  0,-10,-20,-30},
	        {-30,-10, 20, 30, 30, 20,-10,-30},
	        {-30,-10, 30, 40, 40, 30,-10,-30},
	        {-30,-10, 30, 40, 40, 30,-10,-30},
	        {-30,-10, 20, 30, 30, 20,-10,-30},
	        {-30,-30,  0,  0,  0,  0,-30,-30},
	        {-50,-30,-30,-30,-30,-30,-30,-50}};	
	
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
		int counter = 0, material = rateMaterial(); 
		counter += rateAttack(); 
		counter += material;
		counter += rateMoveability(list, depth, material);
		counter += ratePositional(material);
		AlphaBetaChess.flipBoard();
		//Then subtract from the other side (black)
		//This takes the best moves the other side can make 
		//and then subtracts it 
		counter -= rateAttack(); 
		counter -= material; 
		counter -= rateMoveability(list, depth, material);
		counter -= ratePositional(material);
		AlphaBetaChess.flipBoard();
		//This is negative due to how the algorithm is written
		return -(counter + depth * 50); 
	}
	
	/**
	 * This is used for when we are being attacked 
	 */
	public static int rateAttack() {
		int counter = 0; 
		int tempPositionC = AlphaBetaChess.kingPositionC; 
		for(int i = 0; i < 64; i++) {
			switch(AlphaBetaChess.chessboard[i/8][i%8]) {
			//These ratings for each piece are a rule of thumb for each engine. 
				case "P": {AlphaBetaChess.kingPositionC = i; if(!AlphaBetaChess.kingSafe()) {counter -= 64;}} 
					break;
				case "R": {AlphaBetaChess.kingPositionC = i; if(!AlphaBetaChess.kingSafe()) {counter -= 500;}}  
					break;
				case "K": {AlphaBetaChess.kingPositionC = i; if(!AlphaBetaChess.kingSafe()) {counter -= 300;}} 
					break;
				case "B": {AlphaBetaChess.kingPositionC = i; if(!AlphaBetaChess.kingSafe()) {counter -= 300;}}  
					break;
				case "Q": {AlphaBetaChess.kingPositionC = i; if(!AlphaBetaChess.kingSafe()) {counter -= 900;}} 
					break;
			}
		}
		AlphaBetaChess.kingPositionC = tempPositionC; 
		if(!AlphaBetaChess.kingSafe()) {
			counter -= 200; 
		}
		return counter / 2; 
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
	
	/**
	 * Determines how flexible we are with our move, like running away, or being in a closed
	 * position. This is used for for evaluating moves such as being in check, checkmate, or stalemate
	 */
	public static int rateMoveability(int listLength, int depth, int material) {
		int counter = 0; 
		//Each possible move is already 5 points right off the back
		counter += listLength; 
		//Check to see if the king is in checkmate or stalemate meaing their are no moves possible
		if(listLength == 0) {
			//This is if checkmate
			if(!AlphaBetaChess.kingSafe()) {
				counter += -200000 * depth; 
			}
			//This is if stalemate
			else {
				counter += -150000 * depth; 
			}
		}
		
		return counter; 
	}
	
	//FIGURE OUT HOW THIS WORKS
	public static int ratePositional(int material) {
		int counter = 0; 
		
		for(int i = 0; i < 64; i++) {
			switch(AlphaBetaChess.chessboard[i/8][i%8]) {
				case "P": counter += pawnBoard[i/8][i%8];
					break; 
				case "R": counter += rookBoard[i/8][i%8];
					break;
				case "K": counter += knightBoard[i/8][i%8];
					break;
				case "B": counter += bishopBoard[i/8][i%8];
					break;
				case "Q": counter += queenBoard[i/8][i%8];
					break;
				case "A": if(material >= 1750) {
						counter += kingMidBoard[i/8][i%8];
						counter += AlphaBetaChess.possibleA(AlphaBetaChess.kingPositionC).length()*10;
					}
				else {
						counter += kingEndBoard[i/8][i%8];
						counter += AlphaBetaChess.possibleA(AlphaBetaChess.kingPositionC).length()*30;
					}
					break; 
			}
		}
		return counter; 
	}
}