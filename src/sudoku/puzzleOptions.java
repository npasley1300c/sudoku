/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

/**
 *
 * @author ncp1300
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import java.io.*;
import javax.swing.Icon;
import java.util.Random;

public class puzzleOptions extends JPanel
{
    JComboBox difficulty;
    JButton newPuzzle;
    JButton clear;
   // JButton hint;
    JButton solve;
    JTextField [][] puzzleBox;
    //new stuff
    int [][] position;

    public puzzleOptions( JTextField [][] x )
    {
        position = new int[9][9];
        puzzleBox = x;
        String [] difficulties = {"Easy", "Medium", "Hard", "Evil"};
        difficulty = new JComboBox( difficulties );
        newPuzzle = new JButton( "New" );
        clear = new JButton( "Clear" );
      //  hint = new JButton( "Hint" );
        solve = new JButton( "Solve" );
        

        setLayout( new FlowLayout() );
        add( difficulty );
        add( newPuzzle );
        add( clear );
     //   add( hint );
        add( solve );

        newPuzzle.addActionListener( new newPuzzleButtonPressed() );
        clear.addActionListener( new clearButtonPressed() );
    //    hint.addActionListener( new hintButtonPressed() );
        solve.addActionListener( new solveButtonPressed() );

        setPreferredSize( new Dimension( 400, 40 ) );
    }

    private class newPuzzleButtonPressed implements ActionListener
    {
        public void actionPerformed( ActionEvent event )
        {
            String filename = new String();
            Random g = new Random();

            switch( difficulty.getSelectedIndex() )
            {
                case 0:
                    filename = "easy" + Math.abs( g.nextInt() % 5 ) + ".sudoku";
                    break;
                case 1:
                    filename = "medium" + Math.abs( g.nextInt() % 5 ) + ".sudoku";
                    break;
                case 2:
                    filename = "hard" + Math.abs( g.nextInt() % 5 ) + ".sudoku";
                    break;
                case 3:
                    filename = "evil" + Math.abs( g.nextInt() % 5 ) + ".sudoku";
                    break;
            }


            openFile("C:\\Users\\ncp1300\\Documents\\Computer Science\\Java\\cs153\\cs153\\sudoku\\src\\sudoku\\games\\" +filename );
        }
    }

    private class clearButtonPressed implements ActionListener
    {
        public void actionPerformed( ActionEvent event )
        {
            // add functionality for the clear button here
            for( int i = 0; i < 9; i++ )
            {
                for( int j = 0; j < 9; j++ )
                {
                    if( puzzleBox[i][j].isEnabled() )
                    {
                        puzzleBox[i][j].setText( "" );
                    }
                }
            }
        }
    }

 /*   private class hintButtonPressed implements ActionListener
    {
        public void actionPerformed( ActionEvent event )
        {
            // add functionality for the hint button here
            JOptionPane.showMessageDialog
            (
                puzzleOptions.this,
                hint.getText() + " button pressed"
            );

        }
    }
*/
    private class solveButtonPressed implements ActionListener
    {
      public void actionPerformed( ActionEvent event )
      {
        // Fill position array with items from GUI ////////////////////////////
        for(int i=0; i<9; i++)
	  for(int j=0;j<9;j++)
	    if(!puzzleBox [ i ][ j ].isEnabled( ))
	    {
          position[i][j] = Integer.parseInt( puzzleBox[i][j].getText() );
        }
        else
        {
          position[i][j] = 0;
        }

        // Call Sudoku solve function /////////////////////////////////////////
        placeNumber( 0, 0 );

        // Fill GUI array with items from the position array //////////////////
        for(int i=0; i<9; i++)
	      for(int j=0;j<9;j++)
	        if(puzzleBox [ i ][ j ].isEnabled( ) )
	        {
              puzzleBox[ i ][ j ].setText( Integer.toString( position[ i ][ j ] ) );
            }
      }

        public boolean isValidMove(int x,int y,int value)
	    {
		  if(value==0)
		    return true;

		  boolean bReturned=true;

		  //first check: current row
		  for(int i=0;i<9;i++)
		  {
		    if((position[x][i]==value) && (y != i))
		    {
		      bReturned=false;
		    }
		  }
		  //second check current col
		 for(int i=0;i<9;i++)
		 {
		   if((position[i][y]==value) && (x != i))
		   {
		     bReturned=false;
		   }
		 }

		//third check: 3X3 box(optional)
		int startX=x-(x%3);
		int startY=y-(y%3);

		for(int i=startX;i<startX+3;i++)
		{
		  for(int j=startY;j<startY+3;j++)
		  {
			if((position[i][j]==value) && (x != i)&&(y != j))
			{
			  bReturned=false;
			}
		  }
	    }
		  return bReturned;
	   }
	   //recursive solver
	   public boolean placeNumber(int rowIndex,int columnIndex)
	   {

	     if(columnIndex>=9)
		 {
		    columnIndex=0;
			rowIndex++;
		 }
		 int value=1;
		 boolean numberPlaced=false;

		 //stopping case
		 //finished with all rows
		 if(rowIndex>8)
		   return true;
		 if(position[rowIndex][columnIndex]!=0)
		 {
		   return placeNumber(rowIndex,columnIndex+1);
		 }
		 while(value<=9)
		 {
			while(!isValidMove(rowIndex,columnIndex,value)&&value<9)
			  value++;
		    if(!isValidMove(rowIndex,columnIndex,value))
			{
			  return false;
			}
			position[rowIndex][columnIndex]=value;
			if(placeNumber(rowIndex,columnIndex+1))
			  return true;

		    //undo last move
			position[rowIndex][columnIndex]=0;
			value++;
		 }
		  return false;
	   }

  }

    public void openFile( String filename )
    {
        try
        {
            FileInputStream input = new FileInputStream( filename );
            char x;

            for( int i = 0; i < 9; i++ )
            {
                for( int j = 0; j < 9; j++ )
                {
                    x = (char)input.read();

                  if( x == 'P' )
                  {
                    puzzleBox[i][j].setText( String.valueOf( (char)input.read() ) );
                    puzzleBox[i][j].setEnabled( false );
                  }
                  else
                  {
                    x = (char)input.read();

                    if( x == 'X' )
                    {
                      puzzleBox[i][j].setText( "" );
                      puzzleBox[i][j].setEnabled( true );
                    }
                    else
                    {
                      puzzleBox[i][j].setText( String.valueOf( x ) );
                      puzzleBox[i][j].setEnabled( true );
                    }
                  }
                }

                x = (char)input.read();
                x = (char)input.read();
            }

            input.close();
        }
        catch( IOException ioException )
        {
            JOptionPane.showMessageDialog
            (
                this,
                "Error Opening File",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

}

