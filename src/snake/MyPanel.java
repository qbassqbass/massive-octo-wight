/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
/**
 *
 * @author qbass
 */
public class MyPanel extends JPanel implements MouseListener, KeyListener{
    private final int w = 5;
    private boolean clicked = false;
    private int noMore = 0;
    Graphics2D g2d;
    Sn sn;
    private int thingCnt = 0;
    private String string = "";
    private ArrayList<MyPoint> points = new ArrayList<MyPoint>(); // snake
    private ArrayList<MyPoint> things = new ArrayList<MyPoint>(); // points
    public MyPanel(){
//        addMouseListener(this);
        sn = new Sn();
        addKeyListener(this);
        setFocusable(true);
        setPreferredSize(new Dimension(410,410));
        this.string = "Press Enter, then arrow to start";
        sn.addThings();
        snakeKey(-1);
    }
    
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        for(int i=0;i<410/w;i++){
            for(int j=0;j<410/w;j++){
                    g2d.setPaint(Color.WHITE);
                g2d.fill(new Rectangle2D.Double(i*w,j*w,w,w));
            }
        }
        paint(g2d);
    }
    private void paint(Graphics2D g2d){
        for(int i = 0;i < points.size();i++){
            g2d.setPaint(points.get(i).getCol());
            int x = (int) points.get(i).getX(), y = (int) points.get(i).getY();
//            g2d.fill(new Rectangle2D.Double(x,y,w,w));
            g2d.fill(new RoundRectangle2D.Double(x,y,w,w,2,2));
        }
        for(int i = 0;i < things.size();i++){
            g2d.setPaint(things.get(i).getCol());
            int x = (int) things.get(i).getX(), y = (int) things.get(i).getY();
//            g2d.fill(new Rectangle2D.Double(x,y,w,w));
            g2d.fill(new RoundRectangle2D.Double(x,y,w,w,2,2));
        }
        g2d.setColor(Color.red);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2d.drawString(this.string,20,20);
    }
    
    @Override
    public void mouseExited(MouseEvent e){
    }

    @Override
    public void mouseClicked(MouseEvent e) {
////        chColor(e);
////        psSnake();
//        if(this.clicked){
//            this.clicked = false;
////            runSnake();
//            Runnable sn = new Runnable(){            
//                @Override
//                public void run(){
//                    while(!clicked){
//                        try{
//                            psSnake2();
//                            Thread.sleep(10);
//                        }catch (InterruptedException e){
//                            System.err.println(e);
//                        }
//                    }
//                }
//            };
//            Thread snakeTh = new Thread(sn);
//            snakeTh.start();
//        }else this.clicked = true;
        
    }
    
    public void runSnake(){
        while(!clicked){
            try {
                psSnake2();
                System.out.println("debug");
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void psSnake(){
        MyPoint p;
        Random gen = new Random();
        if(!points.isEmpty()){
             p = points.get(points.size()-1);
             p.setCol(Color.BLACK);
             int tmp = gen.nextInt(10)%2;
             if(gen.nextInt(10)%2 == 1/* && p.getX() > 0 && p.getY() > 0*/){
                 if(tmp == 1) points.add(new MyPoint(p.getX()-w*2,p.getY(),Color.RED));
                 else points.add(new MyPoint(p.getX(),p.getY()-w*2,Color.RED));
             }else/* if(p.getX() < 400 && p.getY() < 400)*/{
                 if(tmp == 1) points.add(new MyPoint(p.getX()+w*2,p.getY(),Color.RED));
                 else points.add(new MyPoint(p.getX(),p.getY()+w*2,Color.RED));
             }        
        }else{
            int x = gen.nextInt(400);
            int y = gen.nextInt(400);
            x /= w*2; x *= w*2;
            y /= w*2; y *= w*2;
            points.add(new MyPoint(x,y,Color.RED));
        }
        repaint();
    }
    
    private void psSnake2(){
        MyPoint p;
        MyPoint px;
        Random gen = new Random();
        if(noMore > 4) {
            points.clear();
            noMore = 0;
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(!points.isEmpty()){
             p = points.get(points.size()-1);
             p.setCol(Color.BLACK);
             int tmp = gen.nextInt(10)%2;
             if(gen.nextInt(10)%2 == 1/* && p.getX() > 0 && p.getY() > 0*/){
//                  new MyPoint(p.getX()-w*2,p.getY(),Color.BLACK);                 
                 if(tmp == 1) px = new MyPoint(p.getX()-w*2,p.getY(),Color.RED);
                 else px = new MyPoint(p.getX(),p.getY()-w*2,Color.RED);
                 if(!points.contains(px)){
                     points.add(px);
                     noMore = 0;
                 } else noMore++;
             }else/* if(p.getX() < 400 && p.getY() < 400)*/{
                 if(tmp == 1) px = new MyPoint(p.getX()+w*2,p.getY(),Color.RED);
                 else px = new MyPoint(p.getX(),p.getY()+w*2,Color.RED);
                 if(!points.contains(px)){
                     points.add(px);
                     noMore = 0;
                 } else noMore ++;
             }        
        }else{
            int x = gen.nextInt(400);
            int y = gen.nextInt(400);
            x /= w*2; x *= w*2;
            y /= w*2; y *= w*2;
            points.add(new MyPoint(x,y,Color.RED));
        }
        repaint();
    }
    
    private void snakeKey(int key){ // 0-u,1-d,2-l,3-r
        MyPoint p;
        if(!points.isEmpty()){
//            this.string = "";
             p = points.get(points.size()-1);
             p.setCol(Color.black);
             int px,py;
             px = p.getX();
             py = p.getY();
             switch(key){
                 case 0:{
                     py -= w*2;
                     break;
                 }
                 case 1:{
                     py += w*2;
                     break;
                 }
                 case 2:{
                     px -= w*2;
                     break;
                 }
                 case 3:{
                     px += w*2;
                     break;
                 }
             }
             MyPoint ptmp = new MyPoint(px,py,Color.red);
             if(points.contains(ptmp)){
                 try {
                     this.string = "You died! Your points: "+thingCnt+" Press arrow to start again...";
                     Thread.sleep(500);
                     points.clear();
                     things.clear();
                     thingCnt = 0;
                     sn.setKey(-1);
                     repaint();
                     sn.addThings();
                     sn.snLen = 5;
                     snakeKey(-1);
                     return;
                 } catch (InterruptedException ex) {
                     System.err.println(ex);
                 }
             }else{
                 if(things.contains(ptmp)){
                     things.remove(ptmp);
                     thingCnt++;
                     sn.incLen();
                     sn.addThings();
                 }
                 points.add(ptmp);
                 this.string = "Points: "+thingCnt;
             }
        }else{
            Random gen = new Random();
            int x = gen.nextInt(400);
            int y = gen.nextInt(400);
            x /= w*2; x *= w*2;
            y /= w*2; y *= w*2;
            points.add(new MyPoint(x,y,Color.RED));
        }
        
        repaint();
    }
    
    private void chColor(MouseEvent e){
        Robot robot;
        try {
            Color col;
            robot = new Robot();
            int x,y,xos,yos;
            x = e.getX();
            y = e.getY();
            xos = e.getXOnScreen();
            yos = e.getYOnScreen();
            Color color = robot.getPixelColor(xos, yos);
            System.out.println("x="+x+" y="+y+" "+color.toString());
            if(color.equals(Color.BLACK)) col = Color.WHITE;
            else col = Color.BLACK;
            x /= w;
            y /= w;
            x *= w;
            y *= w;
            points.add(new MyPoint(x,y,col));
            repaint();
        } catch (AWTException ex) {
            Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    class Sn implements Runnable{
        private int key = -1;
        private int snLen = 5;
        private int th = 0;
        @Override
        public void run(){
            while(true){
                try{                           
                    Thread.sleep(100);
                    if(key > -1)snakeKey(key);
                    else continue;
                    if(points.size() > snLen) points.remove(0);
//                    if(th == 4){ 
//                        addThings();
//                        th = 0;
//                    }else th++;
                }catch (InterruptedException e){
                    System.err.println(e);
                }
            }
        }
        public void addThings(){
            Random r = new Random();
            int x = r.nextInt(400); x /= w*2; x *= w*2;
            int y = r.nextInt(400); y /= w*2; y *= w*2;
            things.add(new MyPoint(x,y,Color.green));
        }
        public void setKey(int key){
            this.key = key;
        }
        public void incLen(){
            this.snLen++;
        }
    };
    
    @Override
    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();
        int key = -1;
        switch(keycode){
            case KeyEvent.VK_UP:{
                key = 0;
                break;
            }
            case KeyEvent.VK_DOWN:{
                key = 1;
                break;
            }
            case KeyEvent.VK_LEFT:{
                key = 2;
                break;
            }
            case KeyEvent.VK_RIGHT:{
                key = 3;
                break;
            }           
            case KeyEvent.VK_ENTER:{
                Thread snakeTh = new Thread(sn);
                snakeTh.start();
                break;
            }
            case KeyEvent.VK_HOME:{
                sn.incLen();
                return;
            }
            case KeyEvent.VK_ESCAPE:{
                System.exit(0);
            }
        }
        sn.setKey(key);
    }
    
}
