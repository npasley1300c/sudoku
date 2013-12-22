/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class puzzleBoard extends JPanel
{
    JTextField [][] puzzleBox;

    public puzzleBoard( JTextField [][] x )
    {
        setLayout( new GridLayout( 9, 9 ) );
        puzzleBox = x;

        for( int i = 0; i < 9; i++ )
        {
            for( int j = 0; j < 9; j++ )
            {
                puzzleBox[i][j] = new JTextField();
                puzzleBox[i][j].setHorizontalAlignment( JTextField.CENTER );
                puzzleBox[i][j].setFont( new Font( "Dialog", Font.BOLD, 40 ) );


                if
                (
                    ( ( i < 3 || i > 5 ) && ( j < 3 || j > 5 ) ) ||
                    ( ( i > 2 && i < 6 ) && ( j > 2 && j < 6 ) )
                )
                {
                    puzzleBox[i][j].setBackground( Color.BLUE);
                }
                else
                {
                    puzzleBox[i][j].setBackground( Color.LIGHT_GRAY);
                }

                add( puzzleBox[i][j] );
            }
        }

        setPreferredSize( new Dimension( 400, 400 ) );
    }
}
