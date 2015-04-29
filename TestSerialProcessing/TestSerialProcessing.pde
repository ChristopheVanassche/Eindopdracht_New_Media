import processing.serial.*;
import controlP5.*;

ControlP5 cp5;
 
Serial serialPort;  // Seriële poort
String val;         // Waarde seriële poort
boolean connected = false;
 
void setup() {
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

  // Maak connectie -> 115200 = vies snel
  serialPort = new Serial(this, portName, 115200);

  // Lees totdat '#' wordt gezien
  serialPort.bufferUntil('#'); 
}

void draw() {
  // Programma logica
}

public void controlEvent(ControlEvent theEvent) {
  if(theEvent.controller().id() == 1) {
    serialPort.write("r");
    serialPort.write(5);
    serialPort.write(12);
    serialPort.write(256);
    serialPort.write(2);
  }
}

// Handel seriële communicatie af
void serialEvent(Serial myPort) {
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