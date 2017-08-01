int IS_SHOOTING_PIN = 7;
int IS_BLUE_ALLIANCE = 12;
int IS_RED_ALLIANCE = 8;

int RED_PIN = 3;
int GREEN_PIN = 5;
int BLUE_PIN = 6;

int counter = 0;
int rgbArrCounter = 0;
bool increasing = true;

float sine = 0;
float cosine = 0;

float rgb [3] = {255,0,0};
float allianceColor [3] = {0, 255, 0};

// The sine's period will be 2pi times this number
float PERIOD_FACTOR = 0.1;

void setup() {
    pinMode(IS_SHOOTING_PIN, INPUT_PULLUP);
    pinMode(IS_BLUE_ALLIANCE, INPUT_PULLUP);
    pinMode(IS_RED_ALLIANCE, INPUT_PULLUP);
    pinMode(RED_PIN, OUTPUT);
    pinMode(BLUE_PIN, OUTPUT);
    pinMode(GREEN_PIN, OUTPUT);
    Serial.begin(9600);
}

void loop() {
    counter++;
    if (digitalRead(IS_BLUE_ALLIANCE) == HIGH) {
      allianceColor[0] = 0;
      allianceColor[1] = 0;
      allianceColor[2] = 255;
    } else if (digitalRead(IS_RED_ALLIANCE) == HIGH) {
      allianceColor[0] = 255;
      allianceColor[1] = 0;
      allianceColor[2] = 0;
    }
    if (digitalRead(IS_BLUE_ALLIANCE) == HIGH || digitalRead(IS_RED_ALLIANCE) == HIGH) {
        if (digitalRead(IS_SHOOTING_PIN) == HIGH) {
          sine = 0.5*(sin(counter/ (100 * PERIOD_FACTOR)))+0.5;

          setLights(sine * allianceColor[0], sine * allianceColor[1], sine * allianceColor[2], 10);
        } else {
          setLights(allianceColor[0], allianceColor[1], allianceColor[2], 10);
        }
    } else {
        if (counter % 255 != 0) {
            if (increasing) {
                rgb[(rgbArrCounter + 1) % 3]++;
            } else {
                rgb[rgbArrCounter]--;
            }
        } else {
            if (!increasing) {
                rgbArrCounter++;
                rgbArrCounter %= 3;
            }
            increasing = !increasing;
        }
        setLights(rgb[0], rgb[1], rgb[2], 5);
    }
}

void setLights(float r, float g, float b, float timeout) {
    analogWrite(RED_PIN, r);
    analogWrite(GREEN_PIN, g);
    analogWrite(BLUE_PIN, b);

    Serial.println(r);
    Serial.println(g);
    Serial.println(b);

    delay(timeout);
}

