/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author qbass
 */
public class MyFrame extends JFrame{
    public MyFrame(){
        super("rysowanie");
        JPanel panel = new MyPanel();
        
        add(panel);
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
