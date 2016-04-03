#include "Adt7470.h"
#include "Mcp23017.h"
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#include <Servo.h>

//Fans
struct Adt7470 leftFans = adt7470_new(0x2C);
struct Adt7470 rightFans = adt7470_new(0x2E);

//Rotation
Servo rotationServo;
#define SERVO_PIN 13

//Obstacle detection
struct Mcp23017 ioExpander = mcp23017_new(0x20);

//WiFi
WiFiUDP udp;
#define PORT 3907
#define MAX_BUF_SIZE 256

uint8_t udpRecvBuffer[MAX_BUF_SIZE] = {0};

void setup() {
  setupFans();
  setupRotation();
  setupWiFi();
}

void loop() {
  if (checkObstacle()) {
    stop();
  } else {
    if (udp.parsePacket()) {
      setNewPosition();
    }
  }
}

void setupFans() {
  stop();

  Adt7470_setLowFrequencyDrive(&leftFans);
  Adt7470_setLowFrequencyDrive(&rightFans);

  Adt7470_setDriveFrequencyToFastest(&leftFans);
  Adt7470_setDriveFrequencyToFastest(&rightFans);
}

void setupRotation() {
  rotationServo.attach(SERVO_PIN);
  rotationServo.write(90);
}


void setupWiFi() {
  WiFi.softAP("GroupH-FPV");
  udp.begin(PORT);
}


bool checkObstacle() {
  return (mcp23017_readGpio(&ioExpander, GPIO_A) & 1);
}

void stop() {
  setThrottle(0);
}

void setNewPosition() {
  if (udp.read(udpRecvBuffer, udp.available()) >= 2) {
    uint8_t rotation = udpRecvBuffer[0];
    uint8_t throttle = udpRecvBuffer[1];
  
    setRotation(rotation);
    setThrottle(throttle);
  }
}

void setRotation(uint8_t rotation) {
  rotationServo.write(rotation);
}

void setThrottle(uint8_t throttle) {
  for (uint8_t fanNum = 1; fanNum <= 4; fanNum++) {
    Adt7470_setFanSpeed(&leftFans, fanNum, throttle);
    Adt7470_setFanSpeed(&rightFans, fanNum, throttle);
  }
}

