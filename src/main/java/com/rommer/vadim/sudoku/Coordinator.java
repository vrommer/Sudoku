/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rommer.vadim.sudoku;
public class Coordinator {
    
    /**
     * Find the x value of the cell on a 2d graphic board using a coordinate 
     * on the board, and the width of a cell.
     * @param cellWidth the width of the cell on the board.
     * @param coord the coordinate on the board.
     * @return the x value in (x,y) of the cell.
     */
    public static int findX (int cellWidth, int coord) {
        return coord/cellWidth;
    }
    
    /**
     * Find the y value of the cell on a 2d graphic board using a coordinate 
     * on the board, and the height of a cell.
     * @param cellHeight the height of the cell on the board.
     * @param coord the coordinate on the board.
     * @return the y value of (x,y) cell.
     */
    public static int findY (int cellHeight, int coord) {
        return coord/cellHeight;
    }
    
    /**
     * Find the coordinate on a board which corresponds to the x value of cell.
     * @param cellWidth the width of a cell.
     * @param x the required x parameter of a cell.
     * @return int representing a coordinate.
     */
    public static int getXCoor (int cellWidth, int x){
        return ((cellWidth * x) + (cellWidth/2) - 7);
    }
    
    /**
     * Find the coordinate on a board which corresponds to the y value of cell.
     * @param cellHeight the height of a cell.
     * @param y the required y parameter of a cell.
     * @return int representing a coordinate.
     */    
    public static int getYCoor (int cellWidth, int y){
        return ((cellWidth * y) + (cellWidth/2) + 9);
    }
}
