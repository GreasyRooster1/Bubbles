package main;

import javax.swing.*;

import static java.lang.Math.min;

public class Main {
    public static void main(String[] args) {
        int amt = (int) min(getNumberDialog("How many bubbles (max 100)"),100);
        float speed = min(getNumberDialog("Drag ratio (max 20)"),20);
        for(int i=0;i<amt;i++) {
            makeNewBubble(speed);
        }
    }
    public static void makeNewBubble(float speed){
        ShapedWindow sw = new ShapedWindow();
        sw.dragRatio = speed;
        sw.setVisible(true);
    }
    static float getNumberDialog(String message) {
        try {
            String resultInString = JOptionPane.showInputDialog(message);
            float result = Float.parseFloat(resultInString);
            return result;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "You didn't gave a number");
        }
        return 0;
    }
}