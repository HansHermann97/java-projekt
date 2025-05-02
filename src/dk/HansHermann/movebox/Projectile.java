package dk.HansHermann.movebox;

import java.awt.*;

public class Projectile {
    private double x, y; //position
    private double dx, dy; //direction vector
    private int SIZE = 2; // size of projectile
    private double SPEED = 23;

    public Projectile(double startX, double startY, double targetX, double targetY) {
        this.x = startX;
        this.y = startY;

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
        g.setColor(Color.YELLOW);
        g.fillOval((int)x, (int) y, SIZE, SIZE);
    }

    public boolean isOutOfBounds(int width, int height) {
        return x < 0 || x > width || y < 0 || y> height;
    }
}
