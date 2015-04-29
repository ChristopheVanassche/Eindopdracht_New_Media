import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TestSerialProcessing extends PApplet {




ControlP5 cp5;
 
Serial serialPort;  // Seri\u00eble poort
String val;         // Waarde seri\u00eble poort
boolean connected = false;
 
public void setup() {
  cp5 = new ControlP5(this);
  // (1)
  // create some controllers
  cp5.addButton("Test!")
     .setValue(10)
     .setPosition(20,20)
     .setSize(100,30)
     .setId(1);


  size(200, 200);

  // Hier zou dropdown moeten komen om juiste poort te selecteren
  String portName = Serial.list()[1];

  // Maak connectie -> 2000000 = vies snel
  serialPort = new Serial(this, portName, 2000000);

  // Lees totdat '#' wordt gezien
  serialPort.bufferUntil('#'); 
}

public void draw() {
  // Programma logica
}

public void controlEvent(ControlEvent theEvent) {
  if(theEvent.controller().id() == 1) {
    serialPort.write("255");
    serialPort.write("25f5");
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
        connected = true;
        delay(100);
      }
    } else {
      // Connectie klaar
      println(val);
    }
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TestSerialProcessing" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
