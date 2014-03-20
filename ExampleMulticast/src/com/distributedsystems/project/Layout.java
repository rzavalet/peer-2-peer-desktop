package com.distributedsystems.project;

import java.util.ArrayList;
import java.util.List;

public class Layout {

	public List<Coordinate> snake;
	public List<Coordinate> apples;
	
    public long mScore;
    public long mMoveDelay;
    public int mNextDirection;
    
    public int width;
    public int height;
    
    public Layout() {
    	snake = new ArrayList<Coordinate>();
    	apples = new ArrayList<Coordinate>();
    	mScore = -1;
    	mMoveDelay = -1;
    	mNextDirection = -1;
    	width = -1;
    	height = -1;
    }
}
