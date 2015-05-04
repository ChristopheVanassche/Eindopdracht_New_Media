import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import com.onformative.leap.*; 
import com.leapmotion.leap.*; 
import com.leapmotion.leap.Gesture.*; 
import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TapThatThing extends PApplet {



	// state en type


// Libs
ControlP5 cp5;
LeapMotionP5 leap;
PVector fingerPosition;

// Rectangles -> LED segments
Button r1;
Button r2;
Button r3;
Button r4;
Button r5;
Button r6;

// Finger
Button finger;

// Random nummer elke x aantal milliseconden (1s = 1000ms)
int curTime;
int wait = 5000;
int ran = 0;

//Score en levens bijhouden
int score = 0;
int lifes = 5;


public void setup() {
	size(1920, 1080, P2D);
	leap = new LeapMotionP5(this);						// leap motion device
	leap.enableGesture(Type.TYPE_SCREEN_TAP);

	curTime = millis(); //store the current curTime


	cp5 = new ControlP5(this);

	r1 = cp5.addButton("Led segment 1")
	.setValue(0)
	.setPosition(200,200)
	.setSize(100,200)
	.setColorBackground(color(255, 0, 0))
	.setColorLabel(color(255, 0, 0));
	//.isPressed(false);

	r2 = cp5.addButton("Led segment 2")
	.setValue(0)
	.setPosition(500,200)
	.setSize(100,200)
	.setColorBackground(color(255, 0, 0))
	.setColorLabel(color(255, 0, 0));
	//.isPressed(false);

	r3 = cp5.addButton("Led segment 3")
	.setValue(0)
	.setPosition(800,200)
	.setSize(100,200)
	.setColorBackground(color(255, 0, 0))
	.setColorLabel(color(255, 0, 0));
	//.isPressed(false);

	r4 = cp5.addButton("Led segment 4")
	.setValue(0)
	.setPosition(1100,200)
	.setSize(100,200)
	.setColorBackground(color(255, 0, 0))
	.setColorLabel(color(255, 0, 0));
	//.isPressed(false);

	r5 = cp5.addButton("Led segment 5")
	.setValue(0)
	.setPosition(1400,200)
	.setSize(100,200)
	.setColorBackground(color(255, 0, 0))
	.setColorLabel(color(255, 0, 0));
	//.isPressed(false);

	r6 = cp5.addButton("Led segment 6")
	.setValue(0)
	.setPosition(1700,200)
	.setSize(100,200)
	.setColorBackground(color(255, 0, 0))
	.setColorLabel(color(255, 0, 0));
	//.isPressed(false);

	finger = cp5.addButton("finger")
	.setValue(0)
	.setPosition(width * 0.5f, width * 0.5f)
	.setSize(20,20)
	.setColorBackground(color(0, 0, 0));

	// Font globaal instellen
	textFont(createFont("Verdana", 40));
	fill(0);
}

public void draw() {
	background(0xffAAAAAA);
	fingerPosition = leap.getTip(leap.getFinger(0));
	finger.setPosition(fingerPosition.x, fingerPosition.y);

	// Score en levens bijhouden, moet in draw omdat die hertekend wordt.
	//fill(0);
	//textFont(createFont("Verdana", 40));
	text("Lifes: " + lifes, 100, 100);

	//fill(0);
	//textFont(createFont("Verdana", 40));
	text("Score: " + score, 1400, 100);
	//delay(10);
}

public void pickARandomLed(){
	if(millis() - curTime >= wait){
		ran = PApplet.parseInt(random(1, 7));
    	//println(ran); //if it is, do something
    	curTime = millis();

    	switch(ran) {
    		case 1: 
    		//println("case1");  // Does not execute
    		resetAllLeds();
    		r1.setColorBackground(color(0, 255, 0));
    		checkTappedButton(ran);    
    		break;

    		case 2: 
		    //println("case2");  // Does not execute
		    resetAllLeds();
		    r2.setColorBackground(color(0, 255, 0));
		    checkTappedButton(ran);    	
		    break;

		    case 3:
		    //println("case3");  // Does not execute
		    resetAllLeds();
		    r3.setColorBackground(color(0, 255, 0));
		    checkTappedButton(ran);      
		    break;

		    case 4: 
		    //println("case4");  // Does not execute
		    resetAllLeds();
		    r4.setColorBackground(color(0, 255, 0));
		    checkTappedButton(ran);    	
		    break;

		    case 5:
		    //println("case5");  // Does not execute
		    resetAllLeds();
		    r5.setColorBackground(color(0, 255, 0));
		    checkTappedButton(ran);     
		    break;

		    case 6: 
		    //println("case6");  // Does not execute
		    resetAllLeds();
		    r6.setColorBackground(color(0, 255, 0));
		    checkTappedButton(ran);  	
		    break;
		    default :
		    	ran = 3;
		    break;	
		}
	}
}

public void resetAllLeds(){
	r1.setColorBackground(color(255, 0, 0));
	r2.setColorBackground(color(255, 0, 0));
	r3.setColorBackground(color(255, 0, 0));
	r4.setColorBackground(color(255, 0, 0));
	r5.setColorBackground(color(255, 0, 0));
	r6.setColorBackground(color(255, 0, 0));
}

public void checkTappedButton(int ran){
	/*if(r1.isPressed == true || r2.isPressed == true || r3.isPressed == true || r4.isPressed == true || r5.isPressed == true || r6.isPressed == true){		
		println("Er werd een button ingedrukt");
	}
	else{
		println("Er werd NIETS ingedrukt");
	}*/
	switch(ran) {
    		case 1: 
    		    pickARandomLed();
    		    score++;
    		break;

    		case 2: 
		      	pickARandomLed();
    		    score++;
		    break;

		    case 3:
		       	pickARandomLed();
    		    score++;
		    break;

		    case 4: 
		       	pickARandomLed();
    		    score++;
		    break;

		    case 5:
		        pickARandomLed();
    		    score++; 
		    break;

		    case 6: 
		    	pickARandomLed();
    		    score++;
		    break;
		    default :
		    	lifes--;
		    break;	
		}
}

public void screenTapGestureRecognized(ScreenTapGesture gesture) {
	println("screenTapGestureRecognized");
	// 3 states
	// 1 -> herkennen gesturen
	// 2 -> gesture is bezig, update
	// 3 -> gesture stopt

	float currY = finger.getPosition().y;
	if(currY >= 200 && currY <= 400) {

		float currX = finger.getPosition().x;
		if(gesture.state() == State.STATE_STOP) {
			//testje(currX);
			pickARandomLed();
		} else if(gesture.state() == State.STATE_UPDATE) {
			//testje(currX);
			pickARandomLed();
		} else if(gesture.state() == State.STATE_START) {
			
		}
	}
}

public void testje(float x) {
	if(x >= 200 && x <= 300) {
		println("eerste");
	} else if(x >= 500 && x <= 600) {
		println("tweede");
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TapThatThing" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
