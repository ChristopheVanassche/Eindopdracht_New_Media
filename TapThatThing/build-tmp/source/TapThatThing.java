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

// Rectangles -> LED segments
Button r1;
Button r2;
Button r3;
Button r4;
Button r5;
Button r6;

// Finger
Button finger;


public void setup() {
	size(1920, 1080, P2D);
	leap = new LeapMotionP5(this);								// leap motion device
	leap.enableGesture(Type.TYPE_SCREEN_TAP);


	cp5 = new ControlP5(this);

	r1 = cp5.addButton("Led segment 1")
    	.setValue(0)
     	.setPosition(200,200)
     	.setSize(40,200)
     	.setColorBackground(color(255, 0, 0))
     	.setColorLabel(color(255, 0, 0));

     r2 = cp5.addButton("Led segment 2")
    	.setValue(0)
     	.setPosition(500,200)
     	.setSize(40,200)
     	.setColorBackground(color(255, 0, 0))
     	.setColorLabel(color(255, 0, 0));

     r3 = cp5.addButton("Led segment 3")
    	.setValue(0)
     	.setPosition(800,200)
     	.setSize(40,200)
     	.setColorBackground(color(255, 0, 0))
     	.setColorLabel(color(255, 0, 0));

     r4 = cp5.addButton("Led segment 4")
    	.setValue(0)
     	.setPosition(1100,200)
     	.setSize(40,200)
     	.setColorBackground(color(255, 0, 0))
     	.setColorLabel(color(255, 0, 0));

     r5 = cp5.addButton("Led segment 5")
    	.setValue(0)
     	.setPosition(1400,200)
     	.setSize(40,200)
     	.setColorBackground(color(255, 0, 0))
     	.setColorLabel(color(255, 0, 0));

     r6 = cp5.addButton("Led segment 6")
    	.setValue(0)
     	.setPosition(1700,200)
     	.setSize(40,200)
     	.setColorBackground(color(255, 0, 0))
     	.setColorLabel(color(255, 0, 0));

     	finger = cp5.addButton("finger")
    	.setValue(0)
     	.setPosition(width * 0.5f, width * 0.5f)
     	.setSize(10,10)
     	.setColorBackground(color(0, 255, 0));
}

public void draw() {
	background(0xffAAAAAA);


	PVector fingerPosition = leap.getTip(leap.getFinger(0));
	finger.setPosition(fingerPosition.x, fingerPosition.y);
}

public void screenTapGestureRecognized(ScreenTapGesture gesture) {
	println("screenTapGestureRecognized");
	// 3 states
	// 1 -> herkennen gesturen
	// 2 -> gesture is bezig, update
	// 3 -> gesture stopt

	if(gesture.state() == State.STATE_STOP) {
		//fillColor = color(random(255), random(255), random(255));
		r1.setColorBackground(color(random(255), random(255), random(255)));
	} else if(gesture.state() == State.STATE_UPDATE) {
		r1.setColorBackground(color(random(255), random(255), random(255)));
	} else if(gesture.state() == State.STATE_START) {

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
