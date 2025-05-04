package dk.HansHermann.movebox;

import java.awt.*;

public class Projectile {
    private double x, y; //position
    private double dx, dy; //direction vector
    private int SIZE = 4; // size of projectile
    private double SPEED = 5;
    private int damage;

    public Projectile(double startX, double startY, double targetX, double targetY, int damage) {
        this.x = startX;
        this.y = startY;
        this.damage = damage;

    //calculate direction vector
    double distance = Math.sqrt((targetX - startX) * (targetX - startX) + (targetY - startY) * (targetY - startY));
    this.dx = SPEED * (targetX - startX) / distance;
    this.dy = SPEED * (targetY - startY) / distance;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) {
        //drawhitbox (debugging)
        //drawHitBox(g);
        g.setColor(Color.YELLOW);
        g.fillOval((int)x, (int) y, SIZE, SIZE);
    }

    public Rectangle getHitbox() {
        int actualX = (int) (x);
        int actualY = (int) (y);
        int hitboxSize = SIZE + 8;
        return new Rectangle(actualX - hitboxSize / 2, actualY - hitboxSize / 2, hitboxSize, hitboxSize);
    }

    public int getDamage() {
        return damage;
    }

    public boolean isOutOfBounds(int width, int height) {
        return x < 0 || x > width || y < 0 || y> height;
    }

    public void drawHitBox(Graphics g) {
        g.setColor(Color.GREEN);
        int actualX = (int) (x);
        int actualY = (int) (y);
        int hitboxSize = SIZE + 8;
        g.fillRect(actualX - hitboxSize / 2, actualY - hitboxSize / 2, hitboxSize, hitboxSize);
    }
}
