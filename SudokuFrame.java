import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Vadim Rommer 312763436
 */



public class SudokuFrame extends JFrame {
    private JTextField _textField;  // text field for level
    private JTextField _comment;    // filed to put in some comments
    private SudokuJPanel _panel;    // punnel for sudoku game
    private JPanel _buttonPanel;    // panel for buttons    
    private JRadioButton [] _radioButtons;  // panel for level buttons
    private ButtonGroup _buttonGroup;   // group for levels
    private JButton [] _buttons;    // option buttons
    private String[] _messages; // comments to present through the game
    private Icon[] _options;    // icons for option buttons
    private Icon[] _levels; // icons for level buttons
    private Icon onFocus;
    private Icon notChoosen;    // icon for level that isn't choosen
    private boolean _optionFileNotFound;
    private boolean _levelFileNotFound;
    private String [] altOptions;
    private String [] altLevels;
    
    /**
     * Create a new SudokuFame to show all components and play sudoku game. 
     */
    public SudokuFrame() {
        
        super ("Good Luck!");       
        
        ButtonListener buttonHandler = new ButtonListener();
        String level;
        int butlen = 5;
        int radiolen = 3;
        _buttonPanel = new JPanel();
        _options = new Icon[butlen];
        _levels = new Icon[radiolen];
        _radioButtons = new JRadioButton[butlen];
        _buttons = new JButton [butlen];
        _panel = new SudokuJPanel();
        _buttonGroup = new ButtonGroup();
        _messages = new String[]{"Great!!","Good!!",
            "Nice!","Excellent!!","Way to go!!"};
        _comment = new JTextField("");
        
        _comment.setEditable(false);
        _comment.setFont(new Font("SansSarif",Font.BOLD, 14));
        
        String [] toolTips = new String[] {"Start", "Restart", "Clear", "Retry",
            "Result"};  // tooltips for option buttons
        String [] levels = new String[] {"Easy", "Medium", "Hard"}; // labels
        // for levels
        altOptions = new String[]{"Start","Restart","Clear","Retry","Result"};
        altLevels = new String[]{"Easy","Medium","Hard"};
        
        try {
            _options[0] = new ImageIcon( getClass().getResource( "start.png" ) );
            _options[1] = new ImageIcon( getClass().getResource( "restart.png" ) );
            _options[2] = new ImageIcon( getClass().getResource( "clear.png" ) );
            _options[3] = new ImageIcon( getClass().getResource( "retry.png" ) );
            _options[4] = new ImageIcon( getClass().getResource( "result.png" ) );
            _optionFileNotFound = false;
        } catch (Exception e){
            _optionFileNotFound = true;
        } try {
            _levels[0] = new ImageIcon( getClass().getResource( "easy.png" ) );
            _levels[1] = new ImageIcon( getClass().getResource( "medium.png" ) );
            _levels[2] = new ImageIcon( getClass().getResource( "hard.png" ) );
            notChoosen = new ImageIcon( getClass().getResource( "not_choosen.png") );
            onFocus = new ImageIcon( getClass().getResource( "on_focus.png") );
            _levelFileNotFound = false;
        } catch (Exception e){
            _levelFileNotFound = true;
        }
        
        JLabel options = new JLabel("Game Options");
        level = "Easy";
        JLabel label = new JLabel("Game Level");
        _textField = new JTextField(level);
        _textField.setFont(new Font("SansSarif",Font.BOLD, 14));
        _buttonPanel.add(options);
        options.setForeground(new Color(0, 107, 113));

        for (int i = 0; i < butlen; i++){   // init options
            if (_optionFileNotFound)
                _buttons[i] = new JButton( altOptions[i] );
            else
                _buttons[i] = new JButton( _options[i] );   // create a new button
            _buttons[i].setToolTipText(toolTips[i]); 
            _buttonPanel.add(_buttons[i]);  // add button to panel
            _buttons[i].setBackground(new Color(0, 155, 154));  
            _buttons[i].addActionListener(buttonHandler);
        }
        _buttonPanel.add(_comment); // add text field for comment to panel
        _buttonPanel.add(label);    // add a label to panel
        label.setForeground(new Color(0, 107, 113));
        for (int i = 0; i < radiolen; i++){ // init level radio buttons
            if (_levelFileNotFound)
                _radioButtons[i] = new JRadioButton( altLevels[i] );
            else 
                _radioButtons[i] = new JRadioButton( levels[i], notChoosen);
            _radioButtons[i].setRolloverIcon(onFocus);
            _buttonGroup.add(_radioButtons[i]);
            _buttonPanel.add(_radioButtons[i]);
            _radioButtons[i].setForeground(new Color(0, 107, 113));
            _radioButtons[i].addActionListener(buttonHandler);
        }
        _buttonPanel.add(_textField);   // show text field with current level
        _textField.setEditable(false);
        add( _buttonPanel,BorderLayout.EAST);   // add buttons to frame
               
        _buttonPanel.setLayout( new GridLayout(_buttons.length +
                _radioButtons.length + 2,1,0,5));
        add( _panel, BorderLayout.CENTER);
        setSize(730, 669);
        setResizable(false);
        
        MouseHandler handler = new MouseHandler();
        _panel.addMouseListener( handler );
    }   // end SudokuFrame constructor
    
    public SudokuJPanel getPanel() {
        return _panel;
    }

    // private inner class for handling buttons
    private class ButtonListener implements ActionListener, ItemListener {
        private int EASY;
        private int MEDIUM;
        private int HARD;
        private int level;
        private String label;
        private JButton source1;
        private JRadioButton source2;
        private String result;
        
        public ButtonListener () {
            // possible game levels, number of filled squares for each level
            EASY = 36;  
            MEDIUM = 31;
            HARD = 28;
            level = 1;
            label = new String();
            source1 = new JButton();    // handle JButtons
            source2 = new JRadioButton();   // handle JRadioButtons
            result = "";
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm;
            if ( (e.getSource().getClass() == (JButton.class)) ){
                source1 = (JButton) e.getSource();
                if (source1 == _buttons[0]){    // start
                    switch(level){
                        case 2:
                            _panel.play.start(MEDIUM);
                            break;
                        case 3:
                            _panel.play.start(HARD);
                            break;
                        case 1:
                            _panel.play.start(EASY);
                            break;
                    }   // end switch
                }
                else if (source1 == _buttons[1]){   // restart
                    _comment.setText("");
                    switch(level){
                        case 2:
                            _panel.play.restart(MEDIUM);
                            break;
                        case 3:
                            _panel.play.restart(HARD);
                            break;
                        case 1:
                            _panel.play.restart(EASY);
                            break;
                    }   // end switch
                }
                else if (source1 == _buttons[2]){   // clear
                    _panel.play.clear();
                    _comment.setText("");
                }
                else if (source1 == _buttons[3]){   // retry 
                    if (_panel.play.getStarted())
                        _panel.play.retry();
                }
                else{   // result
                    if (_panel.play.getStarted()){
                        result = _panel.play.checkResult();
                        confirm = JOptionPane.showConfirmDialog(rootPane, 
                                result);
                        if (confirm == 0){
                            _panel.play.retry();                           
                        }
                        else if (confirm == 2)
                            _panel.play.unlock();
                        else 
                            _panel.play.clear();
                    }
                }
                _panel.repaint();
            }   // end if
            else if ( (e.getSource().getClass() == (JRadioButton.class)) ){
                source2 = (JRadioButton) e.getSource();
                label = source2.getLabel(); // read the label of button.
                    switch(label){
                    case "Easy":
                        _radioButtons[1].setIcon(notChoosen);
                        _radioButtons[2].setIcon(notChoosen);
                        _radioButtons[0].setIcon(_levels[0]);
                        _textField.setText("Easy");
                        level = 1;
                        break;
                    case "Medium":
                        _radioButtons[0].setIcon(notChoosen);
                        _radioButtons[2].setIcon(notChoosen);
                        _radioButtons[1].setIcon(_levels[1]);
                        _textField.setText("Medium");
                        level = 2;
                        break;
                    case "Hard":
                        _radioButtons[0].setIcon(notChoosen);
                        _radioButtons[1].setIcon(notChoosen);
                        _radioButtons[2].setIcon(_levels[2]);
                        _textField.setText("Hard");
                        level = 3;
                        break;
                }   // end switch   
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            source2 = (JRadioButton) e.getSource();
            _textField.setText(source1.getLabel());
        }
    }
    
    private class MouseHandler implements MouseListener {
        
        @Override
        public void mouseClicked (MouseEvent event) {
            if (_panel.play.getStarted()){
                Random rand;
                int num, x, y;
                String s;
                
                rand = new Random();
                num = 0;
                x = Coordinator.findX(SudokuJPanel.GRAD,event.getX());
                y = Coordinator.findY(SudokuJPanel.GRAD,event.getY());
                if (_panel.play.getBoard()[x][y].isValid()){
                    s = JOptionPane.showInputDialog(rootPane, "What is your next"
                            + " number?", "Put Your Number", JOptionPane.QUESTION_MESSAGE);
                    try {
                        num = Integer.parseInt(s);
                        _panel.play.insert(num, x, y);
                        _comment.setText(_messages[rand.nextInt(_messages.length)]);
                        _panel.repaint();
                    }
                    catch (NumberFormatException e) {
                        // JOptionPane.showMessageDialog(rootPane, e, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (IllegalArgumentException e){
                        if ( num > 9|| num < 1 )
                            JOptionPane.showMessageDialog(rootPane, e + "\n" + s + " "
                                + "is illegal number in soduko","Error", JOptionPane.INFORMATION_MESSAGE);
                    }   // end catch  
                }
                /*
                JOptionPane.showMessageDialog(null, "Mouse clicked at"
                        + " [" + Coordinator.findX(SudokuJPanel.GRAD,event.getX())
                        + "," + Coordinator.findY(SudokuJPanel.GRAD,event.getY()) + "]");
                        * */
            }   
        }   // end method mouseClicked

        @Override
        public void mousePressed(MouseEvent e) {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }   // end class MouseHandler
}   // end Class SudokuFrame
