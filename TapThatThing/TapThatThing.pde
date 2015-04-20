import java.util.*;
import controlP5.*;
import com.onformative.leap.*;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.*;

LeapMotionP5 leap;
color fillColor = #FFFFFF;
int score = 0;
int lifes = 3;
int curTime;
int wait = 1000;

void setup(){
	size(1200, 800);
	noFill();

	curTime = millis();//store the current curTime

	leap = new LeapMotionP5(this);
	leap.enableGesture(Type.TYPE_SCREEN_TAP);

	drawButtons();
}

void draw(){
	background(fillColor);

	fill(0);
	textFont(createFont("Verdana", 40));
	text("Lifes: " + lifes, 100, 100);

	textFont(createFont("Verdana", 40));
	text("Score: " + score, 900, 100);

	//drawButtons();
}

void drawButtons(){

	// rectangles tekenen ipv controlP5 buttons (cP5 kan niet op geklikt worden met leapmotion??)
	fill(#000000);
	rect(200, 300, 60, 200);
	rect(350, 300, 60, 200);
	rect(500, 300, 60, 200);
	rect(650, 300, 60, 200);
	rect(800, 300, 60, 200);
	rect(950, 300, 60, 200);

	//pickRandomRectangle();
}

void pickRandomRectangle(){

	if(millis() - curTime >= wait){
    int ran = int(random(0, 6));
    //println(ran);//if it is, do something

    switch(ran) {
  	case 1: 
    println("case1");  // Does not execute
    fill(#FF0000);
    rect(200, 300, 60, 200);
    fill(#000000);
    rect(350, 300, 60, 200);
	rect(500, 300, 60, 200);
	rect(650, 300, 60, 200);
	rect(800, 300, 60, 200);
	rect(950, 300, 60, 200);
    break;

    case 2: 
    println("case2");  // Does not execute
    fill(#FF0000);
    rect(350, 300, 60, 200);
    fill(#000000);
    rect(200, 300, 60, 200);
    rect(500, 300, 60, 200);
	rect(650, 300, 60, 200);
	rect(800, 300, 60, 200);
	rect(950, 300, 60, 200);
    break;

    case 3: 
    println("case3");  // Does not execute
    fill(#FF0000);
    rect(500, 300, 60, 200);
    fill(#000000);
    rect(200, 300, 60, 200);
	rect(350, 300, 60, 200);
    rect(650, 300, 60, 200);
	rect(800, 300, 60, 200);
	rect(950, 300, 60, 200);
    break;

    case 4: 
    println("case4");  // Does not execute
    fill(#FF0000);
    rect(650, 300, 60, 200);
    fill(#000000);
    rect(200, 300, 60, 200);
	rect(350, 300, 60, 200);
	rect(500, 300, 60, 200);
	rect(800, 300, 60, 200);
	rect(950, 300, 60, 200);
    break;

    case 5: 
    println("case5");  // Does not execute
    fill(#FF0000);
    rect(800, 300, 60, 200);
    fill(#000000);
    rect(200, 300, 60, 200);
	rect(350, 300, 60, 200);
	rect(500, 300, 60, 200);
	rect(650, 300, 60, 200);
	rect(950, 300, 60, 200);
    break;

    case 6: 
    println("case6");  // Does not execute
    fill(#FF0000);
    rect(950, 300, 60, 200);
    fill(#000000);
    rect(200, 300, 60, 200);
	rect(350, 300, 60, 200);
	rect(500, 300, 60, 200);
	rect(650, 300, 60, 200);
	rect(800, 300, 60, 200);
    break;
}
    curTime = millis();//also update the stored time   
  }
}

public void screenTapGestureRecognized(ScreenTapGesture gesture){
	// START, UPDATE, STOP
	if(gesture.state() == State.STATE_STOP){
		fillColor = color(random(255), random(255), random(255));
		score++;
	}
	else if(gesture.state() == State.STATE_UPDATE){
		//fillColor = color(random(255), random(255), random(255));
	}
	else if(gesture.state() == State.STATE_START){

	}
}	