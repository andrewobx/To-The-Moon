//Andrew Obiano and Michelle Luu
//ICS111

import java.awt.Color;
import java.util.*;
import java.awt.event.KeyEvent;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EZ.initialize(400, 800); // creates our window
		EZ.setBackgroundColor(Color.BLACK); // sets the background to black
		EZImage bg1 = EZ.addImage("bg.png", 200, 400); // background image
		EZImage bg2 = EZ.addImage("bg.png", 200, -400); // copy
		EZImage bg3 = EZ.addImage("bg.png", 200, -1200); // copy
		EZImage moon = EZ.addImage("moon.png", 200, 50); // moon image
		moon.scaleBy(0.5); // scale the image smaller
		EZRectangle groundBlock = EZ.addRectangle(200, 800, 80, 60, Color.lightGray, true); // creates and places our starting block
		ArrayList<Block> stack = new ArrayList<Block>(); // the arraylist called stack that will hold all of our blocks
		EZImage title  = EZ.addImage("title.png", 200, 400); // title picture
		EZSound music = EZ.addSound("Milky_Way.wav"); // background music
		EZSound sfx1 = EZ.addSound("1.wav"); // sound effect for stack
		EZSound sfx2 = EZ.addSound("2.wav"); //sound effect for gameover
		music.loop(); // loop the background music
		
		int gameState = 1; // keeps track of game state
		final int gameStart = 1; // starting state
		final int gameRunning = 2; // game-is-running state
		final int gameOver = 3; // game over state
		
		int score = 0; // keep track of score/blocks stacked
		int highscore = 0; // keep track of highscore
		int combo = 0; // keep track of perfect stacks in a row
		
		// game texts
		EZText text = EZ.addText(200, 50, "0", Color.BLACK, 50); // score text
		EZText high = EZ.addText(200, 200, "High Score: " + highscore, Color.WHITE, 50); // highscore text
		EZText restart = EZ.addText(200, 250, "Press SPACEBAR to restart", Color.WHITE, 20); // restarting text
		EZText PRESS = EZ.addText(200, 425, "PRESS IT!!!", Color.WHITE, 50); // "instructions" for game
		
		ArrayList<EZImage> spacebar = new ArrayList<EZImage>(); // arraylist for sprite pictures AO
		spacebar.add(EZ.addImage("space.png", 0, 0));
		spacebar.add(EZ.addImage("space2.png", 0, 0));
		Sprite sb = new Sprite(200, 500, spacebar); // sprite animation for spacebar AO
		
		while (true) {

			switch (gameState) { // main game switch AO

			case gameStart: // ML
				// hide everything except the starting picture
				title.show();
				bg1.hide();
				bg2.hide();
				bg3.hide();
				moon.hide();
				text.hide();
				PRESS.hide();
				high.hide(); // hide the highscore text
				restart.hide(); // hide the restart text
				if (EZInteraction.wasKeyPressed(KeyEvent.VK_ENTER)) { // if enter was pressed
					gameState = gameRunning; // game starts running
					title.hide();
					text.show();
				}
				break;
 
			case gameOver: // ML
				high.show(); // show the highscore
				restart.show(); // show the restart text
				if (score > highscore){ // if the current score is higher than the current highscore
					highscore = score; // update the highscore
					high.setMsg("High Score: " + highscore); // update the highscore text
				}
			if (EZInteraction.wasKeyPressed(KeyEvent.VK_SPACE)) { // if the space bar was pressed
					for (int i = 0; i < stack.size(); i++){
						stack.get(i).removeBlocks(); // remove all of the EZ block objects in the stack
						stack.get(i).resetWidth(); // reset the width of the block
						stack.get(i).resetPosition(); // reset the starting position of the block
						stack.get(i).resetSpeed(); // reset the speed of the block
						groundBlock.translateTo(200, 800);
					}
					stack.clear(); // empty the array list for the stack
					score = 0; // reset the score to 0
					text.setMsg("" + score); // update the score text
					gameState = gameStart; // bring the game to the starting stage again
				}
				break;

			case gameRunning:
				
				// keep the background repeating AO
				bg1.show();
				bg2.show();
				bg3.show();
				moon.show();
				moon.rotateBy(0.1);
				bg1.translateBy(0, 0.5);
				bg2.translateBy(0, 0.5);
				bg3.translateBy(0, 0.5);
				if (bg1.getYCenter() > 1100)
					bg1.translateTo(200, -500);
				if (bg2.getYCenter() > 1100)
					bg2.translateTo(200, -500);
				if (bg3.getYCenter() > 1100)
					bg3.translateTo(200, -500);
				
				if (stack.size() < 1){ // ML
					Block firstBlock = new Block(); // create the first block
					stack.add(0, firstBlock); // add the first block to the stack
				}
				
				
				if(stack.get(stack.size()-1).getY() < 455) { // if the top block goes higher than the point 455 AO and ML
					for(int i=0;i<stack.size();i++){
						groundBlock.translateBy(0, 30); // move the ground block down one height
						stack.get(i).translateBy(0, 30); // move the stack down one block height
						stack.get(stack.size()-1).setY(455); // "pulls out" of if statement so stack only moves down once
					}
				}
				
				for (int i =0; i < stack.size(); i++) { // loop through our stack
					stack.get(i).processStates(); // checks if each block should be moving or not

				}
					
					// if the FIRST block is in the area of the GROUND block and is stopped AO
					if (stack.get(0).isFixed() && stack.size() < 2
							&& stack.get(0).isInBounds(groundBlock.getXCenter())) {
						Block nextBlock = new Block(); // create the next block
						stack.add(nextBlock); // add it to the stack
						stack.get(stack.size() - 1).nextLevel(); // start it off 30 pixels higher ("on top" of the other block)
						score++; // increase the score
						text.setMsg("" + score); // and update the score text
						sfx1.play();
						if (score == 1){
							stack.get(stack.size() - 1).newDir(); // switches block translation direction on next block
				   		}
						// if the block is closer towards the middle of the ground block (the user has room for error)
						if (stack.get(0).perfectStack(groundBlock.getXCenter())){
							combo++; // increase combo counter
							// the block will be placed in the center of the ground block
						} else { // and if the first block isn't directly over the ground block
							combo = 0; // reset the combo
							stack.get(stack.size()-1).speedUp(); // the translation motion speeds up
							stack.get(stack.size() - 1).shrink(); // and the next block size shrinks
						}
					}
					// if the first block is stopped out of bounds of the ground block
					if (stack.get(0).isFixed() && stack.size() < 2
							&& !stack.get(0).isInBounds(groundBlock.getXCenter())) {
						sfx2.play(); // game over sound effect
						gameState = gameOver; // end game
					}
					if (!stack.get(0).isFixed()){ // show and play the spacebar sprite for the first block AO
						PRESS.show();
						sb.go();
					} else {
						sb.hide();
						PRESS.hide();
					}

					
					
					
					
					
					// For the most part, the rest is repeated code but for the CURRENT block in regards to the PREVIOUS block ML
					// Commented lines are new code
					if (stack.get(stack.size() - 1).isFixed() && stack.size() >= 2
							&& stack.get(stack.size() - 1).isInBounds(stack.get(stack.size() - 2).getX())) {
						Block nextBlock = new Block();
						stack.add(nextBlock);
						stack.get(stack.size() - 1).nextLevel();
						score++;
						text.setMsg("" + score);
						sfx1.play();
						if (score % 2 == 0){
						} else stack.get(stack.size() - 1).newDir();
						
						if (stack.get(stack.size() - 2).perfectStack(stack.get(stack.size() - 3).getX())){
							combo++;
						} else {
							combo = 0;
							stack.get(stack.size()-1).speedUp();
							stack.get(stack.size() - 1).shrink();
						}
						if (combo >= 2){ // if they get a combo of 3 or more
							stack.get(stack.size() - 2).resetWidth(); // reset the block width
						}
					} 
					if (stack.get(stack.size() - 1).isFixed() && stack.size() >= 2
							&& !stack.get(stack.size() - 1).isInBounds(stack.get(stack.size() - 2).getX())) {
						sfx2.play();
						gameState = gameOver;
					}
				

				EZ.refreshScreen();
				break;
			}
		}
	}
}
