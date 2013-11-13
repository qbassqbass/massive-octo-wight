/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.EventQueue;

/**
 *
 * @author qbass
 */
public class SnakeTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EventQueue.invokeLater(new Runnable(){
            
            @Override
            public void run(){
                new MyFrame();
            }
        });
    }
}
