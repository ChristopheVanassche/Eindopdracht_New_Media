import com.onformative.leap.*;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.*;	// state en type
import controlP5.*;

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

int curTime;
int wait = 5000;

//Score en levens bijhouden
int score = 0;
int lifes = 5;


void setup() {
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

	r2 = cp5.addButton("Led segment 2")
	.setValue(0)
	.setPosition(500,200)
	.setSize(100,200)
	.setColorBackground(color(255, 0, 0))

	r3 = cp5.addButton("Led segment 3")
	.setValue(0)
	.setPosition(800,200)
	.setSize(100,200)
	.setColorBackground(color(255, 0, 0))

	r4 = cp5.addButton("Led segment 4")
	.setValue(0)
	.setPosition(1100,200)
	.setSize(100,200)
	.setColorBackground(color(255, 0, 0))

	r5 = cp5.addButton("Led segment 5")
	.setValue(0)
	.setPosition(1400,200)
	.setSize(100,200)
	.setColorBackground(color(255, 0, 0))

	r6 = cp5.addButton("Led segment 6")
	.setValue(0)
	.setPosition(1700,200)
	.setSize(100,200)
	.setColorBackground(color(255, 0, 0))

	finger = cp5.addButton("finger")
	.setValue(0)
	.setPosition(width * 0.5, width * 0.5)
	.setSize(20,20)
	.setColorBackground(color(0, 0, 0));
}

void draw() {
	background(#AAAAAA);
	PVector fingerPosition = leap.getTip(leap.getFinger(0));
	finger.setPosition(fingerPosition.x, fingerPosition.y);

	// Score en levens bijhouden, moet in draw omdat die hertekend wordt.
	fill(0);
	textFont(createFont("Verdana", 40));
	text("Lifes: " + lifes, 100, 100);

	fill(0);
	textFont(createFont("Verdana", 40));
	text("Score: " + score, 1400, 100);
}

void pickARandomLed(){
	if(millis() - curTime >= wait){
		int ran = int(random(1, 7));
    	println(ran); //if it is, do something
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

void resetAllLeds(){
	r1.setColorBackground(color(255, 0, 0));
	r2.setColorBackground(color(255, 0, 0));
	r3.setColorBackground(color(255, 0, 0));
	r4.setColorBackground(color(255, 0, 0));
	r5.setColorBackground(color(255, 0, 0));
	r6.setColorBackground(color(255, 0, 0));
}

void checkTappedButton(int ran){
		println("Er werd een button ingedrukt");
	}
	else{
		println("Er werd NIETS ingedrukt");
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

	if(gesture.state() == State.STATE_STOP) {
		pickARandomLed();
	} else if(gesture.state() == State.STATE_UPDATE) {
		pickARandomLed();
	} else if(gesture.state() == State.STATE_START) {
		
	}
}
