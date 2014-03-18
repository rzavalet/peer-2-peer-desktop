package com.distributedsystems.project;

import java.util.ArrayList;
import java.util.Set;

public class SnakeView {
    private static final int NORTH = 1;
    private static final int SOUTH = 2;
    private static final int EAST = 3;
    private static final int WEST = 4;

    
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


}
