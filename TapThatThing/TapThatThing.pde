import com.onformative.leap.*;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.*;	// state en type
import controlP5.*;

// Libs
ControlP5 cp5;
LeapMotionP5 leap;

// Rectangles -> LED segments
Button r1;

// Finger
Button finger;


void setup() {
	size(1800, 700, P2D);
	leap = new LeapMotionP5(this);								// leap motion device
	leap.enableGesture(Type.TYPE_SCREEN_TAP);


	cp5 = new ControlP5(this);

	r1 = cp5.addButton("Led segment 1")
    	.setValue(0)
     	.setPosition(500,500)
     	.setSize(200,19)
     	.setColorBackground(color(255, 0, 0))
     	.setColorLabel(color(255, 0, 0));
     ;


	finger = cp5.addButton("finger")
    	.setValue(0)
     	.setPosition(width * 0.5, width * 0.5)
     	.setSize(10,10)
     	.setColorBackground(color(0, 255, 0))
     ;
}

void draw() {
	background(#000000);


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
