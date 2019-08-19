package com.rommer.vadim.sudoku;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * @author vadim rommer 312763436
 */
public class SudokuJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public SudokuGameplay play;
    private Font font;
    public static final int GRAD = 70;  // height and width of a square cell
    private static final int _HEIGHT = GRAD * 3;    // height of 3 cells
    private static final int _WIDTH = GRAD * 3;     // width of 3 cells
    
    /**
     * Sudoku game panel constructor. Generates a new sudoku game.
     */
    public SudokuJPanel () {
        play = new SudokuGameplay();
        // play.start(28);
        font = new Font ("Comic Sans MS", Font.ITALIC, 32);
    }
    
    @Override
    public void paintComponent ( Graphics g ){
        int x, y;
        SudokuCell [][] matrix;
        super.paintComponent(g);
        Graphics2D g2d = ( Graphics2D ) g;
        
        // make a drawing of the board.
        g2d.setColor(new Color(170,240,247));
        g2d.fill (new Rectangle2D.Double(0,0,_HEIGHT,_WIDTH));
        g2d.fill (new Rectangle2D.Double(GRAD*3,GRAD*3,_HEIGHT,_WIDTH));
        g2d.fill (new Rectangle2D.Double(GRAD*6,0,_HEIGHT,_WIDTH));
        g2d.fill (new Rectangle2D.Double(GRAD * 6,GRAD * 6,_HEIGHT,_WIDTH));
        g2d.fill (new Rectangle2D.Double(0,GRAD * 6,_HEIGHT,_WIDTH));
        
        g2d.setColor(new Color(190,250,253));
        g2d.fill (new Rectangle2D.Double(0, GRAD * 3, _HEIGHT,_WIDTH));
        g2d.fill (new Rectangle2D.Double(GRAD * 3, 0, _HEIGHT,_WIDTH));
        g2d.fill (new Rectangle2D.Double(GRAD * 3,GRAD * 6,_HEIGHT,_WIDTH));
        g2d.fill (new Rectangle2D.Double(GRAD * 6,GRAD * 3,_HEIGHT,_WIDTH));

        g2d.setPaint(Color.BLACK);
        for (int i = 0; i<=630; i = i+70) {
            g2d.draw(new Line2D.Double(i,0,i,630));
            g2d.draw(new Line2D.Double(0,i,630,i));
        }   // end for  
        
        g2d.setFont(font);
        matrix = play.getBoard();
        Color color = new Color(4,125,119);
        g2d.setColor(new Color (4,125,119));
        for (int i = 0; i < 9; i++){
            for (int j = 0; j<9; j++){
                x = Coordinator.getXCoor(GRAD, i);
                y = Coordinator.getYCoor(GRAD, j);
                // if there is a valid sudoku number in cell (i, j)
                if ( (matrix[i][j].getValue() > 0)&&(matrix[i][j].getValue()) < 10){
                    // if the cell may not be eddited by user
                    if (!matrix[i][j].isValid())
                            g2d.setColor(Color.BLACK);  // choose black color
                        g2d.drawString(String.format("%d", matrix[i][j].getValue()), x, y);
                }
                    g2d.setColor(color);    // restore to default color
            }   // end inner for
        }   // end outer for
        if (!play.getStarted()){    // if game is not started
            g2d.setFont(new Font("Comic Sans MS", Font.TRUETYPE_FONT, 64));
            g2d.setColor(Color.ORANGE);
            g2d.drawString("Press Start!", _WIDTH - GRAD, _HEIGHT*3/2);
        }
    }   // end paintComponent
    
    public SudokuGameplay getGame(){
        return play;
    }
}
