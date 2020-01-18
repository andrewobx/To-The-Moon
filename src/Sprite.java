//Andrew Obiano and Michelle Luu
//ICS111


//********** Code from Nurit

import java.util.ArrayList;

public class Sprite {
	static final int TIME_STEP = 10;
	ArrayList<EZImage> sequence;
	int index;
	int counter;
	
	
	Sprite(int x, int y, ArrayList<EZImage> seq){
		sequence = seq;
		index = 0;
		for (int i=0; i< sequence.size();i++){
			sequence.get(i).translateTo(x, y);
			sequence.get(i).hide();
		}
	}
	
	void go() {
		counter++;
		if (counter == TIME_STEP) {
			counter = 0;
			int next_index = (index+1) % sequence.size();
			sequence.get(index).hide();
			sequence.get(next_index).show();
			index = next_index;
		}
	}
	
	void translateTo(int posx, int posy){ // translate the sprite AO
		for (int i = 0; i < sequence.size(); i++){
			sequence.get(i).translateTo(posx, posy);
		}
	}
	
	void translateBy(){ // move the sprite by how much AO
		for (int i = 0; i < sequence.size(); i++){
			sequence.get(i).translateBy(0, 30);
		}
	}
	
	void hide(){ // hide the sprite AO
		for (int i=0; i< sequence.size();i++){
			sequence.get(i).hide();
		}
	}
	
}
