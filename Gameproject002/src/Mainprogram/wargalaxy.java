package Mainprogram;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class wargalaxy extends JFrame  {
    
    public wargalaxy() {
        initUI();
    }

    private void initUI() {
        add(new Board());

        setTitle("SPACE WAR!!!!!");
        setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        //USE FOR TEST BACKGROUND 
//        JLabel background;      
//        ImageIcon img = new ImageIcon("src/images/backg.jpg");
//        
//        background = new JLabel("",img,JLabel.CENTER);
//        add(background);
 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var ex = new wargalaxy();
            ex.setVisible(true);
        });
    }
}
