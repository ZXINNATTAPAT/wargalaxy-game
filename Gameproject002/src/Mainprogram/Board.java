package Mainprogram;

import Component.Enemyship;
import Component.Player;
import Component.Shot;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;

public class Board extends JPanel {
    
    private JLabel background;

    private Dimension d;
    private List<Enemyship> aliens;
    private Player player;
    private Shot shot;
    
    private int direction = -1;
    private int deaths = 0;
    public static int scoresum = 0 ;
 
    private boolean inGame = true;
    private String explImg = "src/images/explosion.png";
   
    
    private String message = "Game Over";
    private String messages = "SCORE :";
    
    private Timer timer;
   
    
    public Board() {
        initBoard();
        gameInit();
        
    }
//######################### initBoard ###############################
    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
//        setBackground(Color.black);
        
        timer = new Timer(Commons.DELAY, new GameCycle());
        timer.start();
        
    }
   
//######################### Create Alien ###############################
    private void gameInit() {
        aliens = new ArrayList<>();
        
        //############ ALIEN 1 ######################
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                var alien = new Enemyship(Commons.ALIEN_INIT_X + 30 * j,
                        Commons.ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
                
            }
        }//############ ALIEN 2 ######################
//         for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                var alien2 = new Alien(Commons.ALIEN_INIT_X +250  * j,
//                        Commons.ALIEN_INIT_Y +15 * i);
//                aliens.add(alien2);
//            }
//        }
         //############ ALIEN 3 ######################
//          for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                var alien3 = new Alien(Commons.ALIEN_INIT_X + 250 * j,
//                        Commons.ALIEN_INIT_Y -25 * i);
//                aliens.add(alien3);
//            }
//        }//         for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                var alien2 = new Alien(Commons.ALIEN_INIT_X +250  * j,
//                        Commons.ALIEN_INIT_Y +15 * i);
//                aliens.add(alien2);
//            }
//        }
         //############ ALIEN 3 ######################
//          for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                var alien3 = new Alien(Commons.ALIEN_INIT_X + 250 * j,
//                        Commons.ALIEN_INIT_Y -25 * i);
//                aliens.add(alien3);
//            }
//        }
         
        player = new Player();
        shot = new Shot();
        

}
   
//############################# drawAliens ################################
    private void drawAliens(Graphics g) {
        for (Enemyship alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
            if (alien.isDying()) {
                alien.die(); 
            }
        }
    }

    
//########################### DRAWPLAYER ####################################
    private void drawPlayer(Graphics g) {
        
        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
        if (player.isDying()) {
            player.die();
            inGame = false;
        }
    }
    
//######################### drawShot ###################################
    private void drawShot(Graphics g) {
        if (shot.isVisible()) {
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    private void drawBombing(Graphics g) {
        for (Enemyship a : aliens) {

            Enemyship.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }
//########################## Background #############################
    private void drawBackg(Graphics g) {
        ImageIcon img = new ImageIcon("src/images/backg2.jpg");
        g.drawImage(img.getImage(), WIDTH, WIDTH, this);
    }
//#####################################################################
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    

    private void doDrawing(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);
        
        //########## USE SCORE ################
                int score = 10 ;
                score = score*deaths;
                scoresum =  score ;
        //#####################################
        
        if (inGame) {
            drawBackg(g); //background!!!!
        
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
            g.drawLine(0, Commons.GROUND,
                    Commons.BOARD_WIDTH, Commons.GROUND);
            g.setColor(Color.red);
            g.drawString(messages+(score),400 ,500);
               
        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
            gameOver(g);
        }
         
        Toolkit.getDefaultToolkit().sync();
    }
//#############################################################################
    //Game Over
    private void gameOver(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        
        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, 
                Commons.BOARD_WIDTH - 100, 50);
        
        g.setColor(Color.white);
        
        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, 
                Commons.BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);
//        g.setColor(Color.white);        
        g.setColor(Color.red);
        g.setFont(small);
        
//########################### OVER P ###############################
        g.drawString(message+"You score : "+scoresum, 
                (Commons.BOARD_WIDTH - 
                        fontMetrics.stringWidth(message)) / 2,
                Commons.BOARD_WIDTH / 2);
    }
//##################################################################    
    private void update() {
        if (deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }
        // player
        player.act();

        // shot
        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (Enemyship alien : aliens) {
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + Commons.ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + Commons.ALIEN_HEIGHT)) {

                        var ii = new ImageIcon(explImg);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }
            //###############################
            int y = shot.getY();
            y -= 22;//speed shot!!!!!!!!!!!!!!
            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
            //###############################
        }
        
        // aliens Distans 
        for (Enemyship alien : aliens) {

            int x = alien.getX();

            if (x >= Commons.BOARD_WIDTH - 
                    Commons.BORDER_RIGHT && direction != -1) {

                direction = -1;

                Iterator<Enemyship> i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Enemyship a2 = i1.next();
                    a2.setY(a2.getY() + Commons.GO_DOWN);
                }
            }

            if (x <= Commons.BORDER_LEFT && direction != 1) {

                direction = 1;

                Iterator<Enemyship> i2 = aliens.iterator();

                while (i2.hasNext()) {

                    Enemyship a = i2.next();
                    a.setY(a.getY() + Commons.GO_DOWN);
                }
            }
        }

        Iterator<Enemyship> it = aliens.iterator();

        while (it.hasNext()) {

            Enemyship alien = it.next();

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > Commons.GROUND - Commons.ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }
                alien.act(direction);
            }
        }

        // bombs
        var generator = new Random();

        for (Enemyship alien : aliens) {

            int shot = generator.nextInt(50);//normal 15
            Enemyship.Bomb bomb = alien.getBomb();

            if (shot == Commons.CHANCE && alien.isVisible() 
                    && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + Commons.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Commons.PLAYER_HEIGHT)) {

                    var ii = new ImageIcon(explImg);
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    bomb.setDestroyed(true);
                }
            }
            if (!bomb.isDestroyed()) {
                //################ speed BOMB ###################
                bomb.setY(bomb.getY() + 4);
                //hard set 4 
                //esey set 1 or 2
                if (bomb.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {
                    bomb.setDestroyed(true);
                }
            }
        }
    }

    private void doGameCycle() {
        update();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }
//############################ KEYBORD ###################################
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {//use for Actact by pressed

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

                if (inGame) {

                    if (!shot.isVisible()) {

                        shot = new Shot(x, y);
                    }
                }
            }
        }
    }
//#############################################################################
}
