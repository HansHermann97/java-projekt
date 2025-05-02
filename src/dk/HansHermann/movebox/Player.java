package dk.HansHermann.movebox;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.*;
import dk.HansHermann.movebox.Projectile;

public class Player {
    private double relativeX = 0.1; // Relative position (10% of the window width)
    private double relativeY = 0.1; // Relative position (10% of the window height)
    private double relativeSize = 0.05; // Relative size (5% of the window width)
    private double relativeSpeed = 0.01; // Relative speed (1% of the window width per frame)

    private double dx = 0;
    private double dy = 0;

    private List<Projectile> projectiles = new ArrayList<>();
    private Set<Integer> activeKeys = new HashSet<>();

    public Player(double startX, double startY) {
        this.relativeX = startX;
        this.relativeY = startY;
    }

    public void draw(Graphics g, int windowWidth, int windowHeight) {
        // Calculate actual size and position based on window dimensions
        int actualX = (int) (relativeX * windowWidth);
        int actualY = (int) (relativeY * windowHeight);
        int actualSize = (int) (relativeSize * windowWidth);

        // Draw the player
        g.setColor(Color.BLUE);
        g.fillRect(actualX, actualY, actualSize, actualSize);

        // Draw projectiles
        for (Projectile projectile : projectiles) {
            projectile.draw(g);
        }
    }

    public void move(int windowWidth, int windowHeight) {
        // Update relative position based on direction
        relativeX += dx;
        relativeY += dy;

        // Ensure the player stays within the window bounds
        relativeX = Math.max(0, Math.min(1 - relativeSize, relativeX));
        relativeY = Math.max(0, Math.min(1 - relativeSize, relativeY));

        // Move projectiles
        projectiles.removeIf(p -> p.isOutOfBounds(windowWidth, windowHeight));
        for (Projectile projectile : projectiles) {
            projectile.move();
        }
    }

    public void setDirection(int keyCode) {
        activeKeys.add(keyCode);
        updateMovement();
    }

    public void stopDirection(int keyCode) {
        activeKeys.remove(keyCode);
        updateMovement();
    }

    private void updateMovement(){
        dx = 0;
        dy = 0;

        if(activeKeys.contains(KeyEvent.VK_A)) {
            dx -= relativeSpeed; //move left
        }
        if(activeKeys.contains(KeyEvent.VK_D)) {
            dx += relativeSpeed; //move right
        }
        if(activeKeys.contains(KeyEvent.VK_W)) {
            dy -= relativeSpeed; //move up
        }
        if(activeKeys.contains(KeyEvent.VK_S)) {
            dy += relativeSpeed; //move down
        }
    }

    public void shoot(int mouseX, int mouseY, int windowWidth, int windowHeight) {
        // Convert player's relative position to actual pixel coordinates
        int actualX = (int) (relativeX * windowWidth);
        int actualY = (int) (relativeY * windowHeight);
        int actualSize = (int) (relativeSize * windowWidth);
        projectiles.add(new Projectile(actualX + actualSize / 2, actualY + actualSize / 2, mouseX, mouseY));
    }

    public double getRelativeX() {
        return relativeX;
    }
    
    public double getRelativeY() {
        return relativeY;
    }
}
