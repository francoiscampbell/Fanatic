#include "Adt7470.h"
#include "Mcp23017.h"
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#include <Servo.h>
#include <Wire.h>

//Fans
Adt7470 leftFans(0x2C);
Adt7470 rightFans(0x2E);

//Rotation
Servo rotationServo;
#define SERVO_PIN 13

//Obstacle detection
#define OBSTACLE_FRONT 14
#define OBSTACLE_BOTTOM 12
//Mcp23017 ioExpander(0x20);

//WiFi
#define SSID "GroupH-FPV"
WiFiUDP udp;
#define PORT 3907
#define MAX_BUF_SIZE 256
uint8_t udpRecvBuffer[MAX_BUF_SIZE] = {0};

bool obstacle = false;
uint8_t savedThrottle = 0;

void setup() {
  Serial.begin(115200);
  Serial.setDebugOutput(true);
  os_printf("\nInit-FPV\n");

  Wire.begin();
  
  setupFans();
  setupRotation();
  setupWiFi();
}

void loop() {
  bool oldObstacle = obstacle;
  obstacle = checkObstacle();
  if (obstacle) {
    if (!oldObstacle) {
      stop();
    }
  } else {
    setThrottle(savedThrottle);
  }
  if (udp.parsePacket()) {
    setNewPosition();
  }
}

void setupFans() {
  stop();
}

void setupRotation() {
  rotationServo.attach(SERVO_PIN);
  rotationServo.write(90);
}


void setupWiFi() {
  //WiFi.softAP(SSID);
  //WiFi.mode(WIFI_AP);
  udp.begin(PORT);
  os_printf("WiFi active at %s\n", SSID);
}


bool checkObstacle() {
  return !digitalRead(OBSTACLE_FRONT) || digitalRead(OBSTACLE_BOTTOM);
}

void stop() {
  setThrottle(0);
  //os_printf("Stopped fans\n");
}

void setNewPosition() {
  if (udp.read(udpRecvBuffer, udp.available()) >= 2) {
    uint8_t rotation = udpRecvBuffer[0];
    uint8_t throttle = udpRecvBuffer[1];
  
    setRotation(rotation);
    setThrottle(throttle);
    savedThrottle = throttle;
  }
}

void setRotation(uint8_t rotation) {
  rotationServo.write(rotation);
  os_printf("Set servo to %d\n", rotation);
}

void setThrottle(uint8_t throttle) {
  os_printf("Set throttle to %d\n", throttle);
  for (uint8_t fanNum = 1; fanNum <= 4; fanNum++) {
    leftFans.setFanSpeed(fanNum, throttle);
    rightFans.setFanSpeed(fanNum, throttle);
  }
}

