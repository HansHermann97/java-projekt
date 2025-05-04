package dk.HansHermann.movebox;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Minion {
    private double relativeX, relativeY; //position
    private double dx, dy; //direction vector
    private double relativeSize = 0.01; // size of minion
    private double SPEED = 0.0002;
    private int startHitPoints = 5;
    private int hitPoints = 5;

    private List<Point> waypoints = new ArrayList<>();
    private int currentWaypointIndex = 0;

    public Minion(){
        relativeX = 0.0;
        relativeY = 0.0;
        //waypoints in map
        waypoints.add(new Point(10,10)); //start
        waypoints.add(new Point(10,90)); //move down
        waypoints.add(new Point(90,90)); //move right
        waypoints.add(new Point(90,10)); //move up
    }

    public void move(int windowWidth, int windowHeight) {
        if (currentWaypointIndex < waypoints.size()) {
            Point target = waypoints.get(currentWaypointIndex);

            // convert target to actual pixel coordinates
            double targetX = target.x / 100.0;
            double targetY = target.y / 100.0;

            //calculate direction vector
            double distance = Math.sqrt((targetX - relativeX) * (targetX - relativeX)
                        + (targetY - relativeY) * (targetY - relativeY));

            // Check if distance is zero to avoid division by zero
        if (distance < 0.001) {
            currentWaypointIndex++;
            relativeX = targetX; // Snap to the waypoint
            relativeY = targetY;
            return;
        }

        //normalizing direction vector, to make speed continual
        double directionX = (targetX - relativeX) / distance;
        double directionY = (targetY - relativeY) / distance;
        //updating direction vector with speed
        dx = SPEED * directionX;
        dy = SPEED * directionY;

        //move towards the target
        relativeX += dx;
        relativeY += dy;

        //check if minion has reached the current waypoint
        if  (Math.abs(relativeX - targetX) < SPEED
        && Math.abs(relativeY - targetY) < SPEED) {
            currentWaypointIndex++;
            relativeX = targetX; // snap to the waypoint
            relativeY = targetY;
            }
        } else {
            // minion has reached the end of the path
            dx = 0;
            dy = 0;
        }
    }

    public void draw(Graphics g, int windowWidth, int windowHeight) {
        g.setColor(Color.WHITE);
        g.fillRect(getActualX(windowWidth), getActualY(windowHeight), getActualSize(windowWidth), getActualSize(windowWidth));

        //draw hitpoints bar
        drawHPBar(g, windowWidth, windowHeight);
    }

    public void drawHPBar(Graphics g, int windowWidth, int windowHeight) {
        g.setColor(Color.RED);
        //calculate the width of the HP bar:
        int currentHitPointBar = 20 * hitPoints/startHitPoints;
        g.fillRect(getActualX(windowWidth)-4, getActualY(windowHeight)+20, currentHitPointBar, 4);
    }

    public Rectangle getHitbox(int windowWidth, int windowHeight) {
        return new Rectangle(getActualX(windowWidth), getActualY(windowHeight), getActualSize(windowWidth), getActualSize(windowWidth));
    }

    public double getHitPoints() {
        return hitPoints;
    }

    public void hit(int damage) {
        hitPoints -= damage;
    }

    public int getActualX(int windowWidth) {
        return (int) (relativeX * windowWidth);
    }

    public int getActualY(int windowHeight) {
        return (int) (relativeY * windowHeight);
    }

    public int getActualSize(int windowWidth) {
        return (int) (relativeSize * windowWidth);
    }
}
