package com.distributedsystems.project;

import java.util.ArrayList;
import java.util.Random;

/**
 * This public class simulates the SnakeView in the Snake game. It contains hardcoded 
 * values for the snake coordinates as well as for the apples and other 
 * game properties
 */

public class SnakeView {
    private static final int NORTH = 1;

    private static final Random RNG = new Random();
    private static final boolean debug = true;
    
    private ArrayList<Coordinate> mSnakeTrail = new ArrayList<Coordinate>();
    private ArrayList<Coordinate> mAppleList = new ArrayList<Coordinate>();
    private int mNextDirection = NORTH;
    private long mScore = 0;
    private long mMoveDelay = 600;
    private int width = 600;
    private int height = 900;
    
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
	
    public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void setmNextDirection(int mNextDirection) {
		this.mNextDirection = mNextDirection;
	}
	public void setmScore(long mScore) {
		this.mScore = mScore;
	}
	public void setmMoveDelay(long mMoveDelay) {
		this.mMoveDelay = mMoveDelay;
	}
	private void addRandomApple() {
        Coordinate newCoord = null;
        boolean found = false;
        while (!found) {
            // Choose a new location for our apple
            int newX = 1 + RNG.nextInt(10 - 2);
            int newY = 1 + RNG.nextInt(10 - 2);
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
    
    
    /** 
     * During creation, we pass in hardcoded coordinates
     * to facilitate the debugging of the application.
     */
	public void initSnakeView() {
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
	
	public void resetView(int width, int height) {
    	
    	if (this.width > width) {
    		this.width = width;
    	}
    	if (this.height > height) {
    		this.height = height;
    	}
    	
    	Debug.print("New Width: " + this.width, debug);
    	Debug.print("New Height: " + this.height, debug);
    }
	
	public void printGame() {
		System.out.println("Printing Snake");
		for (Coordinate currentCoord : mSnakeTrail) {
			System.out.println(currentCoord);
		}
		
		System.out.println("Printing Apples");
		for (Coordinate currentCoord : mAppleList) {
			System.out.println(currentCoord);
		}
		
		System.out.println("Direction: " + mNextDirection);
		System.out.println("Delay: " + mMoveDelay);
		System.out.println("Score: " + mScore);
		
		System.out.println("Widht: " + width);
		System.out.println("Height: " + height);
	}
	
}
