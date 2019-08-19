package com.rommer.vadim.sudoku;
import javax.swing.JFrame;
import com.rommer.vadim.sudoku.SudokuFrame;
/**
 *
 * @author vrommer
 */
public class Main {
    
    public static void main(String[] args) {

        SudokuFrame frame = new SudokuFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }

    
}
