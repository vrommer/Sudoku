package com.rommer.vadim.sudoku;
import java.awt.Color;
/*
 *
 * @author vadim rommer
 */
public class SudokuCell {
    Color c;    // color of cell
    private int _value; //  value in cell
    private boolean _isValid;   // indicates if this cell can be modified.
    // in sudoku, a valid value is a value that wasn't conflicting during
    // insertiong time.
    private boolean[] _validNumbers;    // this arrays work principle is similar
    // to direct hashing. if number n is valid, than _validNumbers[n] = true
    private int _validLeft; // the number of valid numbers left
    private boolean _conflicts; // cell conflicted during insertion
    
    /**
     * Sudoku cell constructor. Creates a new Sudoku Cell.
     */
    public SudokuCell() {
        c = Color.WHITE;
        _value = 0; // difult value
        _isValid = false;   // all new cells begin as valid
        _validNumbers = new boolean [10];   // all numbers are valid. 0 ignored
        // init _validNumbers. see explanation above
        for (int i = 0; i < 10; i++)    
            _validNumbers[i] = true;
        _validLeft = 9; 
        _conflicts = false;
    }
    
    /**
     * Get value in cell.
     * @return int value in cell.
     */
    public int getValue() {
        return _value;
    }
    
    /**
     * Set value to cell. 
     * @param x the new value to set in calling cell.
     */
    public void setValue (int x) {
        _value = x;
    }
    
    /**
     * Check to see if the cell is modifiable.
     * @return true if cell is valid, false otherwise.
     */
    public boolean isValid() {
        return _isValid;
    }
    
    /**
     * Change the modify setting of the calling cell to b.
     * @param b is the required modify setting.
     */
    public void setValid(boolean b){
        _isValid = b;
    }
    
    /**
     * Retrieve a boolean array with true for each valid number for cell, and
     * false for every invalid number. A valid number is a number that would
     * not conflict if inserted into cell.
     * @return boolean array _validNumbers.
     */
    public boolean [] getValidNumbers(){
        return _validNumbers;
    }
    
    /**
     * Change valid setting for number n to boolean b.
     * @param num number to change valid setting.
     * @param b new valid setting.
     */
    public void setValidNumber(int num, boolean b){
        _validNumbers[num] = b;
    }
    
    /**
     * Return the number of valid numbers that may be inserted to this cell.
     * @return the number of valid numbers (numbers that won't conflict) for cell.
     */
    public int getValidLeft(){
        return _validLeft;
    }
    
    /**
     * See if this cell conflicts.
     * @return true if conflicts, false otherwise.
     */
    public boolean conflicts(){
        return _conflicts;
    }
    
    /**
     * Change the conflicts setting of the calling cell to boolean b.
     * @param b the new conflicts setting for cell.
     */
    public void setConflict(boolean b){
        _conflicts = b;
    }
    
    public void increaseValid(){
        if (_validLeft < 9)
            _validLeft++;
    }
    
    /**
     * Decrease the number of valid numbers left for this cell, change the valid
     * setting of num in from valid to invalid.
     * @param num is the new invalid number in cell.
     */
    public void decreaseValid(int num) {
        if ( (_validLeft > 0)&&(_validNumbers[num]) ){
            _validLeft--;
            _validNumbers[num] = false;
        }
    }
    
    /**
     * Reset all cell settings.
     */
    public void reset() {
        for (int i = 0; i < 10; i++)
            _validNumbers[i] = true;
        _validLeft = 9;
        _value = 0;
    }
    
    /**
     * Retrieve a String representation of cell properties.
     * @return String representation of cell properties.
     */
    @Override
    public String toString() {
        return String.format(" %d ", _value);
    }
}
