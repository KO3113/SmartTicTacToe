import java.util.Random;

public class TicTacToe {

  public char[][] grid = new char[3][3];
  char playerX = 'X';
  char playerO = 'O';

  /**
   * Generate a grid full of empty symbols
   */
  public TicTacToe() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        grid[i][j] = '#';
      }
    }
  }

  /**
   * print out the tic tac toe board
   */
  public void print() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        System.out.print(grid[i][j] + "\t");
      }
      System.out.println();
    }
    System.out.println();
  }

  /**
   * checks if a move is vaild
   * @param y - horizontal coordinate
   * @param z - vertical coordinate
   * @param c - player character
   * @return true if the move is vaild
   */
  public boolean move(int y, int z, char c) {
    if (this.grid[y][z] == '#') {
      this.grid[y][z] = c;
      return true;
    }
    return false;
  }

  /**
   * Makes a move implementing minimax
   * @param board
   * @param player
   * @return if a winner was found this turn
   */
  public boolean smart(char[][] board, char player) {
    int bestRow = -1;
    int bestCol = -1;
    
    //if the player is the minimizer
    int bestVal = 1000;
    boolean playerFlag = false;
    
    //if the player is the maximizer
    if (player == playerX) {
      playerFlag = true;
      bestVal = -1000;
    }
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        // Check if cell is empty
        if (board[i][j] == '#') {
          // Make the move
          board[i][j] = player;

          int moveVal = minimax(board, !playerFlag);

          // Undo the move
          board[i][j] = '#';


          //set the best move
          if (!playerFlag) {
            if (moveVal < bestVal) {
              bestRow = i;
              bestCol = j;
              bestVal = moveVal;
            }
          } else {
            if (moveVal > bestVal) {
              bestRow = i;
              bestCol = j;
              bestVal = moveVal;
            }
          }
        }
      }
    }
    //make the best move
    if (bestRow >= 0 && bestCol >= 0)
      move(bestRow, bestCol, player);
    return winner();
  }

  /**
   * Makes a random valid move
   * @param player
   * @return if a winner was found this turn
   */
  public boolean stupid(char player) {
    boolean flag = false;
    Random rand = new Random();
    int x, y;
    //player has a 50% chance at making a smart move
    if (Math.random() < .5) {
      while (!flag) {
        x = rand.nextInt(3);
        y = rand.nextInt(3);
        flag = move(x, y, player);
      }
    } else {
      this.smart(this.grid, player);
    }
    return winner();
  }
  
/**
 * checks for victory
 * @param board
 * @return score of the board
 */
  public int evaluateBoard(char[][] board) {
    //Checking rows for victory
    for (int row = 0; row < 3; row++) {
      if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
        if (board[row][0] == playerX)
          return +1;
        else if (board[row][0] == playerO)
          return -1;
      }
    }

    // Checking columns for victory
    for (int col = 0; col < 3; col++) {
      if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
        if (board[0][col] == playerX)
          return +1;

        else if (board[0][col] == playerO)
          return -1;
      }
    }

    // Checking diagnals for victory
    if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
      if (board[0][0] == playerX)
        return +1;
      else if (board[0][0] == playerO)
        return -1;
    }

    if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
      if (board[0][2] == playerX)
        return +1;
      else if (board[0][2] == playerO)
        return -1;
    }

    return 0;
  }

  /**
   * Minimax function
   * @param board
   * @param isMax
   * @return the best score found for the board
   */
  public int minimax(char[][] board, boolean isMax) {
    int score = evaluateBoard(board);

    // return if maximizer won
    if (score == 1)
      return score;

    //return if minimizer won
    if (score == -1)
      return score;

    // return if there are no moves left (tie)
    if (!isMovesLeft(board))
      return 0;

    // maximizer's move
    if (isMax) {
      int best = -1000;

      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          //check all empty cells
          if (board[i][j] == '#') {
            
            //make a move
            board[i][j] = playerX;
            
            //find maximum value recursively
            best = Math.max(best, minimax(board, !isMax));

            //undo the move
            board[i][j] = '#';
          }
        }
      }
      return best;
    }

    //Minimizer's move
    else {
      int best = 1000;

      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          //check all empty cells
          if (board[i][j] == '#') {

            //make a move
            board[i][j] = playerO;

            //find best value recursively 
            best = Math.min(best, minimax(board, !isMax));

            //undo the move
            board[i][j] = '#';
          }
        }
      }
      return best;
    }

  }

  /**
   * checks if there are moves left on the board
   * @param board
   * @return true if there are moves left
   */
  boolean isMovesLeft(char board[][]) {
    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
        if (board[i][j] == '#')
          return true;
    return false;
  }

  /**
   * prints out the winner
   * @return
   */
  public boolean winner() {
    int score = this.evaluateBoard(this.grid);
    if (score == -1) {
      System.out.println("Player O wins");
      return true;
    } else if (score == 1) {
      System.out.println("Player X wins");
      return true;
    } else if (!isMovesLeft(this.grid)) {
      System.out.println("Tie");
      return true;
    } else {
      return false;
    }
  }
}
