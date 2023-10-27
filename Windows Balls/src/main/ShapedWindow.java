package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.*;

public class ShapedWindow extends JFrame {
    public JLabel label;
    public JLabel bubble;
    public float x = (float) (random()*1000);
    public float y = (float) (random()*500);
    public float xvel =0;
    public float yvel = 0;
    public int size = 200;
    public boolean drag = false;
    public float pmx = 0;
    public float pmy = 0;
    public float dragRatio = 1;
    public ShapedWindow() {
        super("main.ShapedWindow");
        setupPhysics();
        setupFrame();
        setupEvents();
    }
    void setupFrame(){
        setLayout(new GridBagLayout());
        setUndecorated(true);
        setSize(size,size);
        setAlwaysOnTop(true);
        setBackground(new Color(0,0,0,0));


        Image img = new ImageIcon("G:/My Drive/1st Programming/Projects/Java/Games/Windows Balls/bubble.png").getImage().getScaledInstance(size, size,Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(img);
        bubble = new JLabel(icon);
        bubble.setBounds(0,0,size,size);
        add(bubble);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    void setupEvents(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        addComponentListener(new ComponentAdapter() {
            // Give the window an elliptical shape.
            // If the window is resized, the shape is recalculated here.
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new Ellipse2D.Double(0,0,getWidth(),getHeight()));
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                x = (int) (p.getX());
                y = (int) (p.getY());
                xvel=((x-pmx)/4)*dragRatio;
                yvel=((y-pmy)/4)*dragRatio;
                drag = true;
            }
        });
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable yourRunnable = new Runnable() {
            @Override
            public void run() {
                Point p = MouseInfo.getPointerInfo().getLocation();
                if(x+xvel<=0){
                    xvel*=-1;
                }
                if(x+xvel>=screenSize.width-size){
                    xvel*=-1;
                }
                if(y+yvel<=0){
                    yvel*=-1;
                }
                if(y+yvel>=screenSize.height-size){
                    yvel*=-1;
                }
                yvel+=0.3f;
                x+=xvel;
                y+=yvel;
                setLocation((int) x, (int) y);
                pmx = (float) p.getX();
                pmy = (float) p.getY();
            }
        };
        int initialDelay = 0;
        int delay = 5;
        scheduler.scheduleWithFixedDelay(yourRunnable, initialDelay, delay, TimeUnit.MILLISECONDS);
    }
    void setupPhysics(){
        float dir = new Random().nextFloat()*6.28f;
        xvel = (float) (cos(dir)*3);
        yvel = (float) (sin(dir)*3);
    }
}
