import java.util.Random;
import javax.swing.JOptionPane;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Vadim Rommer 312763436
 */
public class SudokuGameplay {
    private boolean _started;   //  indicates if the game was started
    private SudokuCell [][] _board; // array of cells
    
    /**
     * Create a new empty Sudoku board. A new board begins as not started.
     */
    public SudokuGameplay () {
        _started = false;
        _board = new SudokuCell [9][9]; // create 81 new sudoku cells
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                _board[i][j] = new SudokuCell();
    }
    
    /**
     * Check if the board is started.
     * @return true of the board is started, false otherwise.
     */
    public boolean getStarted() {
        return _started;
    }
 
    /**
     * Get the game board for this sudoku.
     * @return SudokuCell [][].
     */
    public SudokuCell[][] getBoard(){
        return _board;
    }
    
    /**
     * Generate a random solution for sudoku, fill board with solution.
     */
    public void fillSudokuBoard(){
        fillSudokuBoard(0, 0);  // the first cell.
    }
    
    /**
     * Start game.
     * @param level 
     */
    public void start(int level){
        int num, i, j;
        Random rand;
        
        if (!_started){
            clear();    // clear sudoku.
            fillSudokuBoard();  // generate solution
            _started = true;    // set started to true.
            rand = new Random();    
            num = 81 - level;   
            // remove 'num' numbers from random cells.
            while (num > 0){    
                i = rand.nextInt(9);
                j = rand.nextInt(9);
                if (!_board[i][j].isValid()){
                    insertNext(0, i, j);
                    _board[i][j].setValid(true);
                    num--;
                }   // end if
            }   // end while
        }
        else    // can't start started game.
            JOptionPane.showMessageDialog(null, "The game is"
                                    + " already started.");
    }
    
    /**
     * Clear the sudoku board. Remove all numbers from cells, make cells 
     * edditable.
     */
    public void clear(){
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++){
                _board[i][j].setValue(0);
                _board[i][j].setValid(false);
            }
        _started = false;
    }
    
    /**
     * Clear the board, and then start it. 
     * @param level is the level of the new game.
     */
    public void restart(int level){
        clear();
        start(level);
    }
    
    // fill the booard with numbers.
    private void fillSudokuBoard(int i, int j) {
        if ( (i == 8)&&(j == 8) ){  // if last cell
            int var = findValid(i, j);
            if (var != 0)   // found valid
                insertNext(var, i, j);
            else {  // if didn't find valid value
                // reset cell, backtrack
                _board[i][j].reset();
                fillSudokuBoard (i-1, j);
            }   // end else
        }   // end if
        else if ( (i == 8) ){   // if last cell in a row
            int var = findValid(i, j);
            if (var != 0){   // found valid
                insertNext(var, i, j);
                fillSudokuBoard(0, j+1);
            }
            else {  // if didn't find valid value
                _board[i][j].reset();
                fillSudokuBoard (i-1, j);
            }   // end else
        }   // end else if
        else {  // if i<8 and j<8 or only i<8
            int var = findValid(i, j);
            if (var != 0){
                insertNext(var, i, j);
                fillSudokuBoard(i+1, j);
            }
            else {   // if didn't find valid value
                _board[i][j].reset();
                if ( (i == 0)&&(j == 0) ){
                    fillSudokuBoard(i, j);
                }
                else if( i == 0 ){
                    fillSudokuBoard(8, j-1);
                }
                else {
                    fillSudokuBoard(i-1, j);
                }   // end else               
            }   //end else
        }   // end else
    }   // end fillSudokuBoard method
    
    // find a valid number, return it. if no valid value found, return 0.
    private int findValid (int i, int j){
        Random rand = new Random();
        int value;
        value = rand.nextInt(10);
        if (value == 0) // make sure value isn't 0
            value++;
        // if value isn't suitable 
        while ( (!_board[i][j].getValidNumbers()[value])||(!validate(value, i, j)) ) {
            // if there are no more numbers to choose from
            if (_board[i][j].getValidLeft() == 0)
                return 0;
            // if value already failed
            else if (!_board[i][j].getValidNumbers()[value]){
                value = rand.nextInt(10);   // get next value
                if (value == 0)
                    value++;
            // if value fails for the first time
            }
            else {
                _board[i][j].decreaseValid(value);  // remove from options
                value = rand.nextInt(10);   // get next value
                if (value == 0)
                    value++;
            }   // end else                
        }   // end while
       return value;            
    }

    /**
     * Insert a number into sudoku board without validating.
     * @param next the number to insert.
     * @param i row.
     * @param j column.
     */
    public void insertNext(int next, int i, int j){
        _board[i][j].setValue(next);
    }   // end insertNext
    
    /**
     * Insert next number into cell [i][j] on Sudoku board. This method will 
     * insert a new number, then check if the number conflicts. If so, it will
     * indicate that the number conflicts. The method will execute only if 
     * the game is started, and the cell is a valid cell.
     * @param num is the next number to be inserted.
     * @param i points to the next row.
     * @param j is the next column.
     */
    public void insert (int num, int i, int j) {
        SudokuCell sudokuCell;  // pointer to required cell
        
        sudokuCell = _board[i][j];
        if ( (sudokuCell.isValid()) && (_started) ){
            if (!(validate(num, i, j)))
                _board[i][j].setConflict(true);
            else
                _board[i][j].setConflict(false);

                _board[i][j].setValue(num);    
        }
    }
    
    // validate input number and cell.
    private boolean validate (int num, int i, int j) 
            throws IllegalArgumentException {
        int row, col, mod, value1, value2;
        
        if ( (num > 9 ) || ( num < 1) )
            throw new IllegalArgumentException();
        // make sure rows and collumns don't contain 'num'

        for (int k = 0; k < 9; k++){
            value1 = _board[i][k].getValue();
            value2 = _board[k][j].getValue();
            if ( ((value1 == num)||(value2 == num)) )
                return false;
        }
        // find out which square cell [i][j] is part of  
        mod = i%3;
        if (mod == 1)
            row  = i - 1;
        else if (mod == 2)
            row = i - 2;
        else
            row = i;
        mod = j%3;
        if (mod == 1)
            col = j - 1;
        else if (mod == 2)
            col = j - 2;
        else
            col = j;
        // make sure 'num' isn't in square
        for (int k = row; k < row + 3; k++){
                for (int h = col; h < col + 3; h++)
                    if (_board[k][h].getValue() == num)
                        return false;
            
        }
        return true;
    }
    
    /**
     * Check the result for the sudoku. Check to see if sudoku is finished, 
     * and if there is a conflicting cell. If there are cells that conflict,
     * the method will point out only the first it finds. The first referenced 
     * cell is (0,0).
     * Return String representing the result, and user options. 
     * User may choose to cancel and continue, 
     * start again with same board or no do anything. if user chooses 'no',
     * the game is ended, and cannot be continued or restarted.
     * @return String with result and options.
     */
    public String checkResult(){
        String s = "";
        boolean notFinished = false;
        boolean foundConflicts = false;
        if (_started){
            lock();
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++){
                    if (_board[i][j].isValid()&&_board[i][j].conflicts()&&
                            (!foundConflicts)){
                        foundConflicts = true;
                        s += String.format("Cell "
                                    + "[%d,%d] conflicts.\n", i, j);
                    }
                    if (_board[i][j].getValue() == 0 && (!notFinished)){
                        notFinished = true;
                        s += "You didn't finish your game."
                            + "\nWould you like to restart this sudoku?\n";
                    }
                    if (foundConflicts && notFinished) {    // leave loop
                        i = 9; j = 9;
                    }
                }
            if (notFinished || foundConflicts) return s;
            return "Congratulations! You won!\nPlay the level again? :)"; 
        }
        return "Press \"start\" or \"yes\" to start the game";
    }
    
    // make board not edditable.
    private void lock(){
        if (_started){
            _started = !_started;
        }
    }
    
    /**
     * Make board edditable.
     */
    public void unlock(){
        if (!_started){
            _started = !_started;
        }
    }
    
    /**
     * Clear all the numbers inserted by user. Retry to fill the same board from
     * scratch.
     */
    public void retry(){
        unlock();   // make sure board is not locked
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (_board[i][j].isValid()){
                    insertNext(0, i, j);
                    _board[i][j].setConflict(false);
                }
    }
    
    /**
     * Return the numbers in all the cells in the board.
     * @return String representation of the calling board.
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                if ( j == 8 )
                    s += _board[i][j].toString() + "\n";
                else
                    s += _board[i][j].toString();
            }   // end for
        }   // end for
        return s;
    }   // end toString
}
