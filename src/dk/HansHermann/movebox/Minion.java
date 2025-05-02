package dk.HansHermann.movebox;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Minion {
    private double relativeX, relativeY; //position
    private double dx, dy; //direction vector
    private double relativeSize = 0.01; // size of minion
    private double SPEED = 0.01;
    private double hitPoints = 1;

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
        if (distance == 0) {
            currentWaypointIndex++;
            relativeX = targetX; // Snap to the waypoint
            relativeY = targetY;
            System.out.println("Reached waypoint " + currentWaypointIndex);
            return;
        }

        dx = SPEED * (targetX - relativeX) / distance;
        dy = SPEED * (targetY - relativeY) / distance;

        //move towards the target
        relativeX += dx;
        relativeY += dy;

        System.out.println("Moving to waypoint " + currentWaypointIndex +
                        ": relativeX=" + relativeX + ", relativeY=" + relativeY);

        //check if minion has reached the current waypoint
        if  (Math.abs(relativeX - targetX) < SPEED
        && Math.abs(relativeY - targetY) < SPEED) {
            currentWaypointIndex++;
            relativeX = targetX; // snap to the waypoint
            relativeY = targetY;
            System.out.println("Reached waypoint " + currentWaypointIndex);
            }
        } else {
            // minion has reached the end of the path
            dx = 0;
            dy = 0;
            System.out.println("Reached the end of the path");
        }
    }

    public void draw(Graphics g, int windowWidth, int windowHeight) {
        int actualX = (int) (relativeX * windowWidth);
        int actualY = (int) (relativeY * windowHeight);
        int actualSize = (int) (relativeSize * windowWidth);

        g.setColor(Color.RED);
        g.fillRect(actualX, actualY, actualSize, actualSize);
    }
}
