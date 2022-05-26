//Name: Ted Tran
import java.util.*;

public class Mancala{
   
   private int[] board;
  
   public Mancala(){
       
   }
   
   //initializes the board with the correct values
   private void initialize(){
      board = new int[14]; //create board
      for (int i = 0; i < 14; i++) { 
         if (i != 13 && i !=6) {
            board[i] = 4; //4 stones each
         }
      }
   }
   
   //sets the values on the board 
   //true indicates player side
   //false indicates computer side
   //may assume correct input since it is a private method
   private void set(boolean playerComputer,int index,int num){
      if (playerComputer == false) {
         board[index-1] = num; //sets index to num
      } else {
         board[index + 6] = num; //set index to num
      }
   }
   
   //picks up stones from the indicated index and distributes them 
   //returns same boolean if the play lands in the players own mancala
   //or the hole is empty
   //true indicates player side
   //false indicates computer side
   //may assume valid input since it is a private method
   private boolean pick(boolean playerComputer,int index){
      if (playerComputer == false ) { //computer
         int lastPlayNum = board[index]; //will be lastplay # of marbles
         int lastPlay = index; //will be lastplay # of index
         index--; 
         int marbles = board[index];
         int extra = 0;
         for (int i = 1; i <= marbles; i++) { //#marbles to pass tokens
            if ((index+i+extra) % board.length == 13) { //if play was on other mancala then skip and add one
               extra++; //add 1 to extra
            }
            lastPlay = (index+i+extra) % board.length; //set lastPlay index
            lastPlayNum = board[(index+i+extra) % board.length]; //set lastPlay marbles
            board[(index+i+extra) % board.length]++; //add 1
         }
         if (lastPlayNum == 0) { //empty hole
            board[6] += board[Math.abs(12 - lastPlay)]; //add other side's marbles to computer
            board[Math.abs(12 - lastPlay)] = 0; //sets opposite side to 0
         }
         board[index] = 0; //sets original index marbles to 0
         if (lastPlay == 6) {
            return true; //computer's turn
         } else {
            return false; //player's turn  
         }
      } else { //player
         int lastPlayNum = board[index]; //will be lastplay # of marbles
         int lastPlay = index;//will be lastplay # of index
         index += 6;
         int marbles = board[index];
         int extra = 0;
         for (int i = 1; i <= marbles; i++) { //#marbles to pass tokens
            if ((index+i+extra) % board.length == 6) { //if play was on other mancala then skip and add one
               extra++; //add 1 to extra
            }
            lastPlayNum = board[(index+i+extra) % board.length]; //set lastPlay index
            lastPlay = (index+i+extra) % board.length; //set lastPlay marbles
            board[(index+i+extra)% board.length]++; //add 1
         }
         if (lastPlayNum == 0) { //empty hole
            board[13] += board[Math.abs(12 - lastPlay)]; //add other side's marbles to player
            board[Math.abs(12 - lastPlay)] = 0; //sets opposite side to 0
         }
         board[index] = 0; //sets original index marbles to 0
         if (lastPlay == 13) {
            return true; //player's turn
         } else {
            return false; //computer's turn  
         }
      }
   }
   
   //Prints out the board. Prints the computer board on top and the player board on the bottom with the stores
   // on each end.
   public String toString(){
      String ans = " ";
      for (int x = 5; x >= 0; x--) { //first 6 holes
         ans += board[x] + " ";
      }
      ans += "\n" + board[6] + "           " + board[13] + "\n "; //mancalas
      for (int x = 7; x < 13; x++) { //last 6 holes
         ans += board[x] + " ";
      }
      return ans; //return whole board
   }
   
   private boolean gameOver(){
      int count = 0;
      int acount = 0;
      for (int i = 0; i < 6; i++) { //check if computer's side is empty
         if (board[i] == 0) {
            count++;
         }
      }
      for (int i = 7; i < 13; i++) { //checks if player's side is empty
         if (board[i] == 0) {
            acount++;
         }
      }
      if (count == 6) { 
         for (int i = 7; i < 13; i++) { //adds all of stones to other side
            board[6] += board[i];
            board[i] = 0;
         }
         System.out.println(this);
         if (board[6] > board[13]) { //if computer's stones is more than players
            System.out.println("Computer wins");
         } else if (board[6] < board[13]) { //if player's stones is more than computers
            System.out.println("Player wins");
         } else { //otherwise tie
            System.out.println("Tie");
         }
         return true;
      }
      if (acount == 6) {
         for (int i = 0; i < 6; i++) { //adds all of stones to other side
            board[13] += board[i];
            board[i] = 0;
         }
         System.out.println(this);
         if (board[6] > board[13]) { //if computer's stones is more than players
            System.out.println("Computer wins");
         } else if (board[6] < board[13]) { //if player's stones is more than computers
            System.out.println("Player wins");
         } else { //otherwise tie
            System.out.println("Tie");
         }
         return true;
      }
      return false; //default false
   }
   
   
   
//Initializes the board with 4 stones at each space except at 6 and 13 which should be empty
   public void play(){
      this.initialize();
      System.out.println(this); //print board
      Scanner obj = new Scanner(System.in);
      String input = "";
      int i = 0;
      boolean playerTurnNext;
      boolean computerTurnNext;
      while(this.gameOver() == false) { //repeat until game is over
         
         boolean check = false;
         while(check == false) { //repeat until input valid
            System.out.println("Enter number input:");
            i = obj.nextInt(); //get input
            if (i >= 1 && i <= 6 && board[i+6] != 0) { //check if input is between 1 and 6
               check = true;
            } else {
               System.out.println("try again from 1-6");
            }
         }
         check = false;
         
         playerTurnNext = this.pick(true,i); //player picks
         System.out.println(this); //print board
         while (playerTurnNext == true) {
           
            while(check == false) { //repeat until input valid
               System.out.println("Enter number input:");
               i = obj.nextInt(); //get input
               
               if (i >= 1 && i <= 6 && board[i+6] != 0) { //check if input is between 1 and 6
                  check = true;
               } else {
                  System.out.println("try again from 1-6");
               }
            }
            if (this.gameOver() == true) { //check if game is over
                  return;
               }
            playerTurnNext = this.pick(true,i); //player picks
            System.out.println(this); //print board
            
            
         }
         check = false;
         int compInput = (int)(Math.random()*6)+1; //generate number from 1-6
         while (compInput == 0) {
            if (this.gameOver() == true) {
               return; //end
            }
            compInput = (int)(Math.random()*6)+1;
         }
         computerTurnNext = this.pick(false, compInput); //computer randomly picks
         System.out.println(this); //print board
         
         while (computerTurnNext == true) { //give turns
            compInput = (int)(Math.random()*6)+1; //generate nubmer from 1-6
            while (compInput == 0) {
               if (this.gameOver() == true) {
                  return; //end
               }
               compInput = (int)(Math.random()*6)+1; //generate number from 1-6
            }
            computerTurnNext = this.pick(false, compInput); //computer randomly picks
            System.out.println(this); //print board
            if (this.gameOver() == true) { //check if game is over
                  return; //end
               }
            
         }
      }
   }   
                                 
   public static void main(String[] args){
      
      Mancala mygame = new Mancala();
      mygame.initialize();
      System.out.println("Player 6");
      mygame.pick(true,6);
      System.out.println(mygame);
      System.out.println("Computer 6");
      mygame.pick(false,6);
      System.out.println(mygame);
      System.out.println("Player 1");
      mygame.pick(true,1);
      System.out.println(mygame);
      System.out.println("Computer 2");
      System.out.println(mygame.pick(false,2));
      System.out.println(mygame);
      System.out.println("Computer 5");
      mygame.pick(false,5);
      System.out.println(mygame);
      System.out.println("Player 6");
      System.out.println(mygame.pick(true,6));
      System.out.println(mygame);
      System.out.println("Computer 6 and being set to 8");
      mygame.set(false,6,8);
      System.out.println(mygame+"\n");
      mygame.pick(false,6);
      System.out.println(mygame);
      System.out.println("Computer 1");
      mygame.pick(false,1);
      System.out.println(mygame);
      System.out.println("Computer 2");
      mygame.pick(false,2);
      System.out.println(mygame);
      System.out.println("Computer 3");
      mygame.pick(false,3);
      System.out.println(mygame);
      System.out.println("Computer 4");
      mygame.pick(false,4);
      System.out.println("Game Over expected false "+mygame.gameOver());
      System.out.println(mygame);
      System.out.println("Computer 5");
      mygame.pick(false,5);
      System.out.println(mygame);
      System.out.println("Computer 6");
      mygame.pick(false,6);
      System.out.println(mygame);
      System.out.println("Game Over expected true "+mygame.gameOver());
      mygame.play();
      
   }
}
