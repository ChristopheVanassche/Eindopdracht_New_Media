// Clock  -> 13
// Data   -> 11

bool connectionReady = false;
char inChar;
int rgb[4];
int rgbIndex = 0;

const int leds = 6;
const int ShiftPWM_latchPin = 8;
const bool ShiftPWM_invertOutputs = false;
const bool ShiftPWM_balanceLoad = false;

#include <ShiftPWM.h>

void setup() {
  Serial.begin(115200);
  setupConnection();

  // Sets the number of 8-bit registers that are used.
  ShiftPWM.SetAmountOfRegisters(3);
  //ShiftPWM.SetPinGrouping(1); //This is the default, but I added here to demonstrate how to use the funtion

  ShiftPWM.Start(75, 255);
}

void setupConnection() {
  while (Serial.available() <= 0) {
    Serial.print("Connect me#");
    delay(500);
  }
  printInstructions();
}

void loop() {
  if (Serial.available() > 0) {
    inChar = Serial.read();
    if (Serial.peek() == 'l') {
      ShiftPWM.PrintInterruptLoad();
    } else if (inChar == 'm') {
      printInstructions();
    } else if (inChar == 'r') {
      setRGB();
    } else if (inChar == 'f') {
      randomColors();
      Serial.println("Fun Fun!#");
    } else if (inChar == 'w') {
      ShiftPWM.SetAllHSV(297, 0, 30);
      Serial.println("Alle LEDs wit.#");
    }
  }
}

void setRGB() {
  while (rgbIndex < 4) {
    if (Serial.available() > 0) {
      int val = Serial.read();
      if (rgbIndex == 0 && (val > 5 || val < 0)) {
        Serial.print("Ongelidge LED keuze: ");
        Serial.print(val);
        Serial.println("#");
        rgbIndex = 0;
        break;
      }
      rgb[rgbIndex] = val;
      rgbIndex++;
    }
  }

  // Klaar -> resetten en debug info
  rgbIndex = 0;
  Serial.print(rgb[0]);  Serial.print(", ");
  Serial.print(rgb[1]);  Serial.print(", ");
  Serial.print(rgb[2]);  Serial.print(", ");
  Serial.print(rgb[3]);
  ShiftPWM.SetRGB(rgb[0], rgb[1], rgb[2], rgb[3]);
  Serial.println("#");
}

void randomColors() { // Update random LED to random color. Funky!
  unsigned long updateDelay = 100;
  static unsigned long previousUpdateTime;
  if (millis() - previousUpdateTime > updateDelay) {
    previousUpdateTime = millis();
    //ShiftPWM.SetHSV(random(leds), random(360), 255, 255);
    ShiftPWM.SetAllHSV(random(360), 255, 255);
  }
}

void printInstructions() {
  Serial.println("----=Tap That Thing Arduino Backend=---");
  Serial.println("-> Commands");
  Serial.println("\t l = ShiftPWM interrupt load");
  Serial.println("\t m = Dit menu weergeven");
  Serial.println("\t r = RGB LED kleur instellen:");
  Serial.println("\t\t Geef eerst LED nummber (0, 1, 2, ...)");
  Serial.println("\t\t Schrijft RGB waarden een voor een. Foutieve waarden worden auto verbeterd.");
  Serial.println("\t f Fun mode");
  Serial.println("\t w Alle LEDs wit");
  Serial.println("----#");
}
