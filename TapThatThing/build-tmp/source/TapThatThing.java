import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
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

Serial serialPort;  // Seri\u00eble poort
String val;         // Waarde seri\u00eble poort
boolean connected = false;

// Rectangles -> LED segments
Button [] s = new Button[6];		// segments

// Finger
Button finger;

// Random nummer elke x aantal milliseconden (1s = 1000ms)
int curTime;
float wait = 5000;
int ran = 0;

//Score en levens bijhouden + background
int score = 0;
int lifes = 5;
boolean inTime = false;
PImage bg;

int buttonStartPosY = 580;

boolean started = false;
boolean gameOver = false;

// No windows
public void init() {
  frame.removeNotify();
  frame.setUndecorated(true);
  frame.addNotify();
  super.init();
}


public void setup() {
	size(1920, 1080, P2D);
	bg = loadImage("TapThatThingMonsters.png");
	
	leap = new LeapMotionP5(this);						// leap motion device
	leap.enableGesture(Type.TYPE_SCREEN_TAP);

	curTime = millis(); //store the current curTime


	cp5 = new ControlP5(this);

	int i = 0;
	for(; i < s.length; i++) {
		s[i] = cp5.addButton("s" + i)			// segment 1
		.setValue(0)
		.setPosition(235 + (i * 270), buttonStartPosY)
		.setSize(100,200)
		.setColorBackground(color(255, 255, 255, 130))
		.setVisible(false)
		.setLabelVisible(false);
	}


	finger = cp5.addButton("f")
	.setValue(0)
	.setSize(20,20)
	.setPosition(100, 100)
	.setColorBackground(color(0, 0, 0))
	.setLabelVisible(false);

	// Font globaal instellen
	textFont(createFont("Verdana", 40));
	fill(color(255, 255, 255));
	stroke(color(255, 255, 255));

	  // Hier zou dropdown moeten komen om juiste poort te selecteren
	  String portName = Serial.list()[1];

	  // Maak connectie -> 115200 = vies snel
	  serialPort = new Serial(this, portName, 115200);

	  // Lees totdat '#' wordt gezien
	  serialPort.bufferUntil('#'); 
}

public void draw() {
	fingerPosition = leap.getTip(leap.getFinger(0));
	finger.setPosition(fingerPosition.x, fingerPosition.y);

	if(started) {
		background(bg);
		if(millis() - curTime >= wait){
			if(!inTime) {
				lifes--;
				checkGameOver();
			}
			pickARandomLed();
		}
		inTime = false;

		text("Score: " + score, 1604, 100);
		text("Lifes: " + lifes, 100, 100);
	} else {
		background(0xff454545);
		text("Tap to start!", 820, 522);
		if(gameOver) {
			text("GAME OVER", 820, 562);
			text("Score: " + score, 820, 602);
		}
	}
}

public void pickARandomLed(){
	resetLed();
	sedLEDRGB(ran, 255, 255, 255);
	ran = PApplet.parseInt(random(0, s.length));
	curTime = millis();
	s[ran].setColorBackground(color(0, 255, 0, 130));
	sedLEDRGB(ran, 0, 255, 0);
	wait = random(1800, 4500);
}

public void resetLed(){
	s[ran].setColorBackground(color(255, 255, 255, 130));
}

public void screenTapGestureRecognized(ScreenTapGesture gesture) {
	//println("screenTapGestureRecognized");
	// 3 states
	// 1 -> herkennen gesturen
	// 2 -> gesture is bezig, update
	// 3 -> gesture stopt

	if(!started) {
		started = true;
		gameOver = false;
		score = 0;
		int i = 0;
		for(; i < s.length; i++) {
			s[i].setVisible(true);
			ledsWit();
		}
		pickARandomLed();
	}

	float currY = finger.getPosition().y;
	if(currY >= buttonStartPosY && currY <= (buttonStartPosY + 200)) {
		float currX = finger.getPosition().x;
		if(gesture.state() == State.STATE_STOP) {
			checkWin(currX);
		} else if(gesture.state() == State.STATE_UPDATE) {
			checkWin(currX);			
		}
	}
}

public void ledsWit() {
	serialPort.write("w");
}

public void sedLEDRGB(int l, int r, int g, int b) {
	serialPort.write("r");
    serialPort.write(l);
    serialPort.write(r);
    serialPort.write(g);
    serialPort.write(b);
}


public void checkWin(float x) {
	int clicked = -1;

	if(x >= 235 && x <= 335) {
		clicked = 0;
		//println("eerste button aangeklikt.");
		//s[0].setColorBackground(color(255, 255, 255));
	} else if(x >= 505 && x <= 605) {
		clicked = 1;
		//println("tweede button aangeklikt.");
		//s[1].setColorBackground(color(255, 255, 255));
	} else if(x >= 775 && x <= 875){
		clicked = 2;
		//println("derde button aangeklikt.");
		//s[2].setColorBackground(color(255, 255, 255));
	} else if(x >= 1045 && x <= 1145){
		clicked = 3;
		//println("vierde button aangeklikt.");
		//s[3].setColorBackground(color(255, 255, 255));
	} else if(x >= 1315 && x <= 1415){
		clicked = 4;
		//println("vijfde button aangeklikt.");
		//s[4].setColorBackground(color(255, 255, 255));
	} else if(x >= 1585 && x <= 1685){
		clicked = 5;
		//println("zesde button aangeklikt.");
		//s[5].setColorBackground(color(255, 255, 255));
	}

	if(clicked == ran) {
		s[ran].setColorBackground(color(0, 0, 255));
		sedLEDRGB(ran, 0, 0, 255);
		delay(150);
		s[ran].setColorBackground(color(255, 255, 255));
		sedLEDRGB(ran, 255, 255, 255);
		delay(150);
		s[ran].setColorBackground(color(0, 0, 255));
		sedLEDRGB(ran, 0, 0, 255);
		score++;
		inTime = true;
		pickARandomLed();
	} else if(clicked >= 0) {
		s[clicked].setColorBackground(color(255, 0, 0));
		sedLEDRGB(clicked, 255, 0, 0);
		delay(150);
		s[clicked].setColorBackground(color(255, 255, 255));
		sedLEDRGB(clicked, 255, 255, 255);
		lifes--;
		inTime = true;
		pickARandomLed();
	}
	checkGameOver();
}

public void checkGameOver() {
	if(lifes < 0) {
		started = false;
		gameOver = true;
		lifes = 5;
		int i = 0;
		for(; i < s.length; i++) {
			s[i].setVisible(false);
			ledsWit();
		}
	}
}

// Handel seri\u00eble communicatie af
public void serialEvent(Serial myPort) {
  val = serialPort.readStringUntil('#');


  if (val != null) {
    val = trim(val.replaceAll("#", ""));

    // Wachten op connectie...
    if(connected == false) {
      if(val.equals("Connect me")) {
        serialPort.clear();
        serialPort.write("Connected!");
        println("Connected!");
        connected = true;
        delay(100);
      }
    } else {
      // Connectie klaar
      println(val);
    }
  }
}
//done


public void mouseClicked() {
	started = true;
			int i = 0;
		for(; i < s.length; i++) {
			s[i].setVisible(true);
			ledsWit();
		}
		pickARandomLed();
		gameOver = false;
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
