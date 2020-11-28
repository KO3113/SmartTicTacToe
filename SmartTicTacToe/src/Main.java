
public class Main {

  public static void main(String[] args) {
    // Both players smart
    System.out.println("Both Smart");
    TicTacToe t = new TicTacToe();
    boolean win = false;
    while (!win) {
      win = t.smart(t.grid, 'X');
      if (win)
        break;
      win = t.smart(t.grid, 'O');
    }
    t.print();
    System.out.println();
    // -----------------------------------------

    // Smart X
    System.out.println("Smart X Stupid O");
    TicTacToe t2 = new TicTacToe();
    win = false;
    while (!win) {
      win = t2.smart(t2.grid, 'X');
      if (win)
        break;
      win = t2.stupid('O');
    }
    t2.print();
    System.out.println();
    // -----------------------------------------
    
    // Smart O
    System.out.println("Stupid X Smart O");
    TicTacToe t3 = new TicTacToe();
    win = false;
    while (!win) {
      win = t3.stupid('X');
      if (win)
        break;
      win = t3.smart(t3.grid, 'O');
    }
    t3.print();
    System.out.println();
    // -----------------------------------------

    // Both Stupid
    System.out.println("Stupid X Stupid O");
    TicTacToe t4 = new TicTacToe();
    win = false;
    while (!win) {
      win = t4.stupid('X');
      if (win)
        break;
      win = t4.stupid('O');
    }
    t4.print();
    // -----------------------------------------
  }
}
