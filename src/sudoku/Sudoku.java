/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
 *
 * @author ncp1300
 */
public class Sudoku extends JFrame{

    puzzleBoard pb;
    puzzleOptions po;
    Container c;

    JMenuBar mb;
    JMenu file;
    JMenuItem save;
    JMenuItem open;
    JMenuItem exit;
    JMenu help;
    JMenuItem about;

    JTextField [][] puzzleBox;
    
    public Sudoku(){
     super( "Sudoku" );

        puzzleBox = new JTextField[9][9];

        // Setup JPanels
        pb = new puzzleBoard( puzzleBox );
        po = new puzzleOptions( puzzleBox );
        c = getContentPane();
        c.setLayout( new BoxLayout( c, BoxLayout.Y_AXIS ) );
        c.add( po );
        c.add( pb );

        // Setup JMenuBar
        mb = new JMenuBar();
        setJMenuBar( mb );

        file = new JMenu( "File" );
        help = new JMenu( "Help" );
        mb.add( file );
        mb.add( help );

        save = new JMenuItem( "Save" );
        open = new JMenuItem( "Open" );
        exit = new JMenuItem( "Exit" );
        file.add( save );
        file.add( open );
        file.add( exit );

        about = new JMenuItem( "About" );
        help.add( about );

        save.addActionListener( new saveSelected() );
        open.addActionListener( new openSelected() );
        exit.addActionListener( new exitSelected() );
        about.addActionListener( new aboutSelected() );

        pack();
        setVisible( true );
        setResizable( false );
   }
private class saveSelected implements ActionListener
    {
        public void actionPerformed( ActionEvent event )
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );

            int result = fileChooser.showSaveDialog( c );

            if( result == JFileChooser.CANCEL_OPTION )
            {
                return;
            }

            File filename = fileChooser.getSelectedFile();

            if( filename == null || filename.getName().equals( "" ) )
            {
                JOptionPane.showMessageDialog
                (
                    c,
                    "Invalid File Name",
                    "Invalid File Name",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            else
            {
                try
                {
                    FileOutputStream output = new FileOutputStream( filename );

                    for( int i = 0; i < 9; i++ )
                    {
                        for( int j = 0; j < 9; j++ )
                        {
                            if( puzzleBox[i][j].isEnabled() )
                            {
                                if( puzzleBox[i][j].getText().length() == 0 )
                                {
                                    output.write( (int)'U' );
                                    output.write( (int)'X' );
                                }
                                else
                                {
                                    output.write( (int)'U' );
                                    output.write( (int)(puzzleBox[i][j].getText().charAt(0)) );
                                }
                            }
                            else
                            {
                                output.write( (int)'P' );
                                output.write( (int)(puzzleBox[i][j].getText().charAt(0)) );
                            }
                        }

                        output.write( (int)'\r' );
                        output.write( (int)'\n' );
                    }

                    output.close();
                }
                catch( IOException ioException )
                {
                    JOptionPane.showMessageDialog
                    (
                        c,
                        "Error Opening File",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    private class openSelected implements ActionListener
    {
        public void actionPerformed( ActionEvent event )
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );

            int result = fileChooser.showOpenDialog( c );

            if( result == JFileChooser.CANCEL_OPTION )
            {
                return;
            }

            File filename = fileChooser.getSelectedFile();

            if( filename == null || filename.getName().equals( "" ) )
            {
                JOptionPane.showMessageDialog
                (
                    c,
                    "Invalid File Name",
                    "Invalid File Name",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            else
            {
                po.openFile( filename.getPath() );
            }
        }
    }

    private class exitSelected implements ActionListener
    {
        public void actionPerformed( ActionEvent event )
        {
            System.exit( 0 );
        }
    }

    private class aboutSelected implements ActionListener
    {
        public void actionPerformed( ActionEvent event )
        {
            JOptionPane.showMessageDialog
            (
                Sudoku.this, "Program: Sudoku Solver\nAuthor: Nieamiah Pasley\nEmail: nieamiah@gmail.com"
            );
        }
    }

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater
        (
            new Runnable()
            {
                public void run()
                {
                    Sudoku s = new Sudoku();
                    s.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                }
            }
        );
    }

}

    

