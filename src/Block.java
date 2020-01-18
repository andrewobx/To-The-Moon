//Andrew Obiano and Michelle Luu
//ICS111

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Block { //AO and ML
	// class member variables
	EZRectangle blockPicture; // class type
	int x = 40; // x position of the block
	static int y = 755; // y position of the block
	static int width = 80; // width of the block (not final because it will change later)
	static final int min_width = 5; // minimum width the block can be
	static int speed = 3; // speed the block is moving (also not final because it will change)
	static int speed_index = 10;
	int blockState = 0; // index to keep track of block state
	static final int FIXED = 0; // state for stopping the block
	static final int MOVING = 1; // state for moving the block
	static final int HEIGHT = 30; // height of block is never changed
	
	public Block(){ // constructor
		blockPicture = EZ.addRectangle(x, y, width, HEIGHT, Color.lightGray, true); // initialize the block
		blockState = MOVING; // block starts off moving
	}
	
	void positionBlock(int posx, int posy){ // member function to move the block ML
		blockPicture.translateTo(posx, posy); // move the block using EZ's translateTo 
	}
	
	void newDir(){ // member function used to change where the block starts from ML
		x = 360;
		positionBlock(x,y);
	}
	
	void processStates(){ // checks the block's state ML
		switch (blockState){
		
		case MOVING: // ML
			x += speed; // start the block off moving toward the right
			positionBlock(x, y); // continuously move the block
			if (x < 40){ // if the block goes off the screen on the left
				speed = -speed; // switch direction to the right
			} else if (x > 360){ // if the block goes off the screen on the right
				speed = -speed; // switch direction to the left
			} else if (EZInteraction.wasKeyPressed(KeyEvent.VK_SPACE)){ // if the space bar was pressed
				blockState = FIXED;  // the block changes to the FIXED state and stops
			}
			break;
		case FIXED: // ML
			// block stops moving
		}
	}
	
	boolean isFixed(){ // checks if block is FIXED or not AO
		if (blockState == FIXED){ // if it's fixed
			return true; // the function returns true
		} else return false; // returns false otherwise
	}
	
	int nextLevel(){ // moves the selected block upward AO
		if (y >= 455){ // only if it's lower than the point 455
			y -= HEIGHT; // subtracts 30 pixels from the y position member variable
			positionBlock(x,y);
		}
		return y;
	}
	
	int getX(){ // gets the X coordinate of the block's center ML
		return blockPicture.getXCenter();
	}
	
	int getY(){ // gets the Y coordinate of the block's center ML
		return blockPicture.getYCenter();
	}
	
	boolean isInBounds(int posx){ // checks if a block is within a block size boundary
		if (posx > x-width && posx < x+width) return true; // true within bounds
		return false; // false if not
	}

	boolean perfectStack(int posx){ // checks if a block is near the center of a point AO
		if (posx > x-width/5 && posx < x+width/5){
			x = posx;
			positionBlock(posx, y+HEIGHT); // then moves it to that point
			return true;
		}
		return false;
	}
	
	int speedUp(){ // speeds up the block translation speed AO
		if (speed < speed_index && speed > 0){ // only if it's less than the speed index AO
			speed += 1;
		} else if (speed > -speed_index && speed < 0){ // same thing but when it's traveling in the negative x direction AO
			speed -= 1;
		}
		return speed;
	}
	
	int shrink(){ // decreases the block's width AO
		if (width > min_width){ // decreases the width only if it's bigger than its smallest size
			width -= 5;
		} return width;
	}
	
	void removeBlocks(){ // removes the block picture AO
		EZ.removeEZElement(blockPicture);
	}
	
	int resetWidth(){ // reset's the block's width back to 80 pixels wide AO
		width = 80;
		return width;
	}
	
	int resetPosition(){ // reset's the block's height position to 755 (the bottom) AO
		y = 755;
		positionBlock(x,y);
		return y;
	}
	
	int resetSpeed(){ // reset's the block's speed to 1 AO
		speed = 3;
		return speed;
	}
	
	void translateBy(int posx, int posy){ // move the block down by a block height AO
		blockPicture.translateBy(posx, posy);
	}
	
	void setY(int posy){ // function to set y to whatever input AO
		y = posy;
	}
}
