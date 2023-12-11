/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AIPractical;

import java.util.*;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class Prac1a {
    public static void main(String[] args) {
        //Map<String, Set<String>> graph1 = new HashMap<>();
        Map<String, Set<String>> graph1 = new HashMap<>();
        graph1.put("A", new HashSet<>(Arrays.asList("B", "C")));
        graph1.put("B", new HashSet<>(Arrays.asList("A", "D", "E")));
        graph1.put("C", new HashSet<>(Arrays.asList("A", "F")));
        graph1.put("D", new HashSet<>(Collections.singletonList("B")));
        graph1.put("E", new HashSet<>(Arrays.asList("B", "F")));
        graph1.put("F", new HashSet<>(Arrays.asList("C", "E")));

        List<String> visited = new ArrayList<>();
        //dfs(graph1, "A", visited);
        System.out.println(bfs(graph1, "A"));
    }

    private static void dfs(Map<String, Set<String>> graph1, String node, List<String> visited){
        if(!visited.contains(node)){
            visited.add(node);
            for(String s: graph1.get(node)){
                dfs(graph1, s, visited);
            }
        }
    }
    
    private static List<String> bfs(Map<String, Set<String>> graph1, String node){
        List<String> visited = new ArrayList<>();
        List<String> result = new ArrayList<>();
        Queue<String> q = new LinkedList<>();
        q.add(node);
        visited.add(node);
        
        while(!q.isEmpty()){
            String cur = q.poll();
            result.add(cur);
            
            for(String s: graph1.get(cur)){
                if(!visited.contains(s)){
                    q.add(s);
                    visited.add(s);
                }
            }
        }   
        return result;
    }
}


// Prac 2

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AIPractical;

/**
 *
 * @author DELL
 */
public class Prac2 {
    
    public static void main(String[] args){
        int n = 4;
        boolean[][] board = new boolean[n][n];
        //System.out.println("No of solutions: " + nQueens(board, 0));

        int numberOfDisks = 3;
        towerOfHanoi(numberOfDisks, 'A', 'C', 'B');
    }
    
    static int nQueens(boolean[][] board, int row){

        if(row == board.length){
            display(board);
            System.out.println();
            return 1;
        }
        int count = 0;
        for (int col = 0; col < board.length; col++) {
            if(isSafe(board, row, col)){
                board[row][col] = true;
                count += nQueens(board,row+1);
                board[row][col] = false;
            }
        }
        return count;
    }

    private static boolean isSafe(boolean[][] board, int row, int col) {
        // to check if a box is safe to place a queen or not, we need to check in three directions, up, rightDiagonal
        // left diagonal as we are placing queens row-wise from top so we don't need to check below

        // to check vertically upwards
        for (int i = 0; i < row; i++) {
            if(board[i][col]){
                return false;
            }
        }

        // to check left diagonal
        int leftMax = Math.min(row, col); // min of row, col is the no. of boxes remaining in left diagonal
        for (int i = 1; i <= leftMax; i++) {
            if(board[row-i][col-i]){
                return false;
            }
        }

         //to check right diagonal
        int rightMax = Math.min(row, board.length-col-1);
        for (int i = 1; i <= rightMax; i++) {
            if(board[row-i][col+i]){
                return false;
            }
        }

       

        return true;
    }

    private static void display(boolean[][] board) {
        for(boolean[] row : board){
            for(boolean col : row){
                if(col){
                    System.out.print("Q ");
                }else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }
    
    public static void towerOfHanoi(int n, char source, char destination, char auxiliary) {
        if (n == 1) {
            System.out.println("Move disk 1 from " + source + " to " + destination);
            return;
        }

        // Move (n-1) disks from source to auxiliary peg
        towerOfHanoi(n - 1, source, auxiliary, destination);

        // Move the nth disk from source to destination peg
        System.out.println("Move disk " + n + " from " + source + " to " + destination);

        // Move the (n-1) disks from auxiliary peg to destination peg
        towerOfHanoi(n - 1, auxiliary, destination, source);
    }
}


// Tic Tac Toe

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AIPractical;

import java.util.*;

/**
 *
 * @author DELL
 */
public class Prac5 {
    static char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    static int player = 1;

    public static void main(String[] args) {
        int gameResult=0;
        char mark = 'X';

        Scanner scanner = new Scanner(System.in);

        System.out.println("Tic-Tac-Toe Game");
        System.out.println("Player 1 [X] --- Player 2 [O]\n");
        System.out.println("Please Wait...");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        do {
            displayBoard();

            if (player % 2 != 0) {
                System.out.println("Player 1's chance");
                mark = 'X';
            } else {
                System.out.println("Player 2's chance");
                mark = 'O';
            }

            int choice = scanner.nextInt();

            if (checkPosition(choice)) {
                board[choice] = mark;
                player++;
                gameResult = checkWin();
            } else {
                System.out.println("Invalid move. Position already taken.");
                continue;
            }

        } while (gameResult == 0);

        displayBoard();

        if (gameResult == -1) {
            System.out.println("Game Draw");
        } else if (gameResult == 1) {
            player--;
            if (player % 2 != 0) {
                System.out.println("Player 1 Won");
            } else {
                System.out.println("Player 2 Won");
            }
        }

        scanner.close();
    }

    static void displayBoard() {
        System.out.println(" " + board[1] + " | " + board[2] + " | " + board[3] + " ");
        System.out.println("___|___|___");
        System.out.println(" " + board[4] + " | " + board[5] + " | " + board[6] + " ");
        System.out.println("___|___|___");
        System.out.println(" " + board[7] + " | " + board[8] + " | " + board[9] + " ");
        System.out.println(" | | ");
    }

    static boolean checkPosition(int x) {
        return board[x] == ' ';
    }

    static int checkWin() {
    // Check rows, columns, and diagonals for a win
    for (int i = 1; i <= 7; i += 3) {
        if (board[i] == board[i + 1] && board[i + 1] == board[i + 2] && board[i] != ' ') {
            return 1;  // Win
        }
    }

    for (int i = 1; i <= 3; i++) {
        if (board[i] == board[i + 3] && board[i + 3] == board[i + 6] && board[i] != ' ') {
            return 1;  // Win
        }
    }

    if ((board[1] == board[5] && board[5] == board[9]) || (board[3] == board[5] && board[5] == board[7]) && board[5] != ' ') {
        return 1;  // Win
    }

    // Check for a draw
    for (int i = 1; i <= 9; i++) {
        if (board[i] == ' ') {
            return 0;  // Game still running
        }
    }

    return -1;  // Draw
}

}



/* Missionaries and Cannibals problem.

Problem Statement:
There are three missionaries and three cannibals on one side of a river. They need to cross to the other side of the river using a boat that 
can carry at most two people. The boat cannot cross the river by itself and must have at least one person on board to operate it.

The constraints are as follows:

If missionaries are outnumbered by cannibals on either side of the river, the cannibals will eat the missionaries, resulting in an unsafe state.

The boat can only carry a maximum of two people at a time.

All people on the boat must be either missionaries or cannibals; it cannot be a mix of both.

The boat must always have at least one person on board to operate it.

    */
