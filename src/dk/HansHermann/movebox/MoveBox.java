package dk.HansHermann.movebox;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class MoveBox extends JPanel implements KeyListener, MouseListener{
    private Player player = new Player(0.1, 0.1);
    private List<Minion> minions = new ArrayList<>();
    private int spawnCounter = 0;
    private Timer timer;
    private static int screenWidth = 1200;
    private static int screenHeight = 800;

    public MoveBox() {
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        addMouseListener(this);

        //game loop timer:
        timer = new Timer(5, e -> {
            player.move(getWidth(), getHeight());
            //move and spawn minions
            minions.forEach(minion -> minion.move(getWidth(), getHeight()));
            spawnCounter++;
            if (spawnCounter >=500) {
                spawnMinion();
                spawnCounter = 0;
            }
            checkCollisions();
            repaint();
        });
        timer.start();
    }

    private void checkCollisions() {
        List<Projectile> projectilesToRemove = new ArrayList<>();
        List<Minion> minionsToRemove = new ArrayList<>();

        for(Projectile projectile : player.getProjectiles()) {
            Rectangle projectileHitbox = projectile.getHitbox();

            for(Minion minion : minions) {
                Rectangle minionHitbox = minion.getHitbox(getWidth(), getHeight());

                if(projectileHitbox.intersects(minionHitbox)) {
                    //remove projectile
                    projectilesToRemove.add(projectile);
                    // check damage and apply to minion
                    minion.hit(projectile.getDamage());
                    if(minion.getHitPoints() < 1) {
                        minionsToRemove.add(minion);
                    }
                    System.out.println("hit detected");
                }
            }
        }
        player.getProjectiles().removeAll(projectilesToRemove);
        minions.removeAll(minionsToRemove);
    }

    private void spawnMinion() {
        Minion newMinion = new Minion();
        minions.add(newMinion);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g, getWidth(), getHeight());

        //draw all minions:
        for (Minion minion : minions) {
            minion.draw(g, getWidth(), getHeight());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.setDirection(e.getKeyCode());
    }

    @Override public void keyReleased(KeyEvent e){
        player.stopDirection(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        player.shoot(e.getX(), e.getY(), getWidth(), getHeight());
    }

    @Override public void keyTyped(KeyEvent e){}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flyt spilleren");
        MoveBox game = new MoveBox();
        frame.add(game);
        frame.setSize(screenWidth, screenHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
