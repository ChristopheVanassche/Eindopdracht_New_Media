// Clock  -> 13
// Data   -> 11

bool connectionReady = false;
bool ledState = false;
const int ledPin = 9;

int rgbBuffer[4];

const int ShiftPWM_latchPin=8;
const bool ShiftPWM_invertOutputs = false;
const bool ShiftPWM_balanceLoad = false;

#include <ShiftPWM.h>

// Function prototypes (telling the compiler these functions exist).
void printInstructions(void);

void setup(){
  pinMode(ledPin, OUTPUT);
  digitalWrite(ledPin, LOW);
  
  Serial.begin(2000000);
  setupConnection();

  // Sets the number of 8-bit registers that are used.
  ShiftPWM.SetAmountOfRegisters(3);
  //ShiftPWM.SetPinGrouping(1); //This is the default, but I added here to demonstrate how to use the funtion
  
  ShiftPWM.Start(75, 255);
}

void setupConnection() {
  while(Serial.available() <= 0) {
    Serial.print("Connect me#");
    delay(500);
  }
  printInstructions();
}

void loop()
{    
  if(Serial.available() > 0){
    if(Serial.peek() == 'l'){
      // Print information about the interrupt frequency, duration and load on your program
      ShiftPWM.PrintInterruptLoad();
    }
    else if(Serial.peek() == 'm'){
      // Print instructions again
      printInstructions();
    }
    else if (Serial.peek() == 'b') {
      blinkLed();
    }
    else{
      Serial.print(sizeof(rgbBuffer) + "kkkk#");
      /*
      fadingMode = Serial.parseInt(); // read a number from the serial port to set the mode
      Serial.print("Mode set to "); 
      Serial.print(fadingMode); 
      Serial.print(": ");
      startTime = millis();
      switch(fadingMode){
      case 0:
        Serial.println("All LED's off");
        break;
      case 1:
        Serial.println("Fade in and out one by one");
        break;
      case 2:
        Serial.println("Fade in and out all LED's");
        break;
      case 3:
        Serial.println("Fade in and out 2 LED's in parallel");
        break;
      case 4:
        Serial.println("Alternating LED's in 6 different colors");
        break;
      case 5:
        Serial.println("Hue shift all LED's");
        break;
      case 6:
        Serial.println("Setting random LED's to random color");
        break;
      case 7:
        Serial.println("Fake a VU meter");
        break;
      case 8:
        Serial.println("Display a color shifting rainbow as wide as the LED's");
        break;         
      case 9:
        Serial.println("Display a color shifting rainbow wider than the LED's");
        break;     
      default:
        Serial.println("Unknown mode!");
        break;
      }*/
    }
    while (Serial.read() >= 0){
      ; // flush remaining characters
    }
  }
  /*
  switch(fadingMode){
  case 0:
    // Turn all LED's off.
    ShiftPWM.SetAll(0);
    break;
  case 1:
    oneByOne();
    break;
  case 2:
    inOutAll();
    break;
  case 3:
    inOutTwoLeds();
    break;
  case 4:
    alternatingColors();
    break;
  case 5:
    hueShiftAll();
    break;
  case 6:
    randomColors();
    break;
  case 7:
    fakeVuMeter();
    break;
  case 8:
    rgbLedRainbow(3000,numRGBLeds);
    break;
  case 9:
    rgbLedRainbow(10000,5*numRGBLeds);    
    break;   
  default:
    Serial.println("Unknown Mode!");
    delay(1000);
    break;
  }
  */
}

void blinkLed() {
      if(ledState) {
        digitalWrite(ledPin, LOW);
        ledState = false;
        Serial.print("Ledje UIT#");
      } else {
        digitalWrite(ledPin, HIGH);
        ledState = true;
        Serial.print("Ledje AAN#");
      } 
}

void printInstructions(void){
  Serial.println("---- ShiftPWM Non-blocking fades demo ----");
  Serial.println("");
  
  Serial.println("Type 'l' to see the load of the ShiftPWM interrupt (the % of CPU time the AVR is busy with ShiftPWM)");
  Serial.println("");
  Serial.println("Type any of these numbers to set the demo to this mode:");
  Serial.println("  0. All LED's off");
  Serial.println("  1. Fade in and out one by one");
  Serial.println("  2. Fade in and out all LED's");
  Serial.println("  3. Fade in and out 2 LED's in parallel");
  Serial.println("  4. Alternating LED's in 6 different colors");
  Serial.println("  5. Hue shift all LED's");
  Serial.println("  6. Setting random LED's to random color");
  Serial.println("  7. Fake a VU meter");
  Serial.println("  8. Display a color shifting rainbow as wide as the LED's");
  Serial.println("  9. Display a color shifting rainbow wider than the LED's");  
  Serial.println("");
  Serial.println("Type 'm' to see this info again");  
  Serial.println("");
  Serial.println("----#");
}
