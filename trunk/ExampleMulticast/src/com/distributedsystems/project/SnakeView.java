package com.distributedsystems.project;

import java.util.ArrayList;
import java.util.Random;

public class SnakeView {
    private static final int NORTH = 1;
    private static final int SOUTH = 2;
    private static final int EAST = 3;
    private static final int WEST = 4;

    private static final Random RNG = new Random();

    private ArrayList<Coordinate> mSnakeTrail = new ArrayList<Coordinate>();
    private ArrayList<Coordinate> mAppleList = new ArrayList<Coordinate>();
    private int mNextDirection = NORTH;
    private long mScore = 0;
    private long mMoveDelay = 600;
    
	public ArrayList<Coordinate> getmSnakeTrail() {
		return mSnakeTrail;
	}
	public ArrayList<Coordinate> getmAppleList() {
		return mAppleList;
	}
	public int getmNextDirection() {
		return mNextDirection;
	}
	public long getmScore() {
		return mScore;
	}
	public long getmMoveDelay() {
		return mMoveDelay;
	}
	
    private void addRandomApple() {
        Coordinate newCoord = null;
        boolean found = false;
        while (!found) {
            // Choose a new location for our apple
            int newX = 1 + RNG.nextInt(50 - 2);
            int newY = 1 + RNG.nextInt(50 - 2);
            newCoord = new Coordinate(newX, newY);

            // Make sure it's not already under the snake
            boolean collision = false;
            int snakelength = mSnakeTrail.size();
            for (int index = 0; index < snakelength; index++) {
                if (mSnakeTrail.get(index).equals(newCoord)) {
                    collision = true;
                }
            }
            // if we're here and there's been no collision, then we have
            // a good location for an apple. Otherwise, we'll circle back
            // and try again
            found = !collision;
        }
        if (newCoord == null) {
            System.out.println("Somehow ended up with a null newCoord!");
        }
        mAppleList.add(newCoord);
    }
    
	public SnakeView() {
        mSnakeTrail.add(new Coordinate(7, 7));
        mSnakeTrail.add(new Coordinate(6, 7));
        mSnakeTrail.add(new Coordinate(5, 7));
        mSnakeTrail.add(new Coordinate(4, 7));
        mSnakeTrail.add(new Coordinate(3, 7));
        mSnakeTrail.add(new Coordinate(2, 7));
        mNextDirection = NORTH;

        // Two apples to start with
        addRandomApple();
        addRandomApple();

        mMoveDelay = 600;
        mScore = 0;

	}
	
}
