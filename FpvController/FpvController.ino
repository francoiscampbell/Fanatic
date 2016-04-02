#include "Adt7470.h"
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

#define PORT 3907
#define MAX_BUF_SIZE 256

struct Adt7470 leftFans = adt7470_new(0x2C);
struct Adt7470 rightFans = adt7470_new(0x2E);

WiFiUDP udp;

uint8_t udpRecvBuffer[MAX_BUF_SIZE] = {0};

void setup() {
  setupFans();
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

void setupWiFi() {
  WiFi.softAP("FPV");
  udp.begin(PORT);
}

bool checkObstacle() {
  return false; //TODO
}

void stop() {
  setThrottle(0);
}

void setNewPosition() {
  udp.read(udpRecvBuffer, udp.available());

  uint8_t rotation = udpRecvBuffer[0];
  uint8_t throttle = udpRecvBuffer[1];

  setRotation(rotation);
  setThrottle(throttle);
}

void setRotation(uint8_t rotation) {
  //TODO
}

void setThrottle(uint8_t throttle) {
  for (uint8_t fanNum = 1; fanNum <= 4; fanNum++) {
    Adt7470_setFanSpeed(&leftFans, fanNum, throttle);
    Adt7470_setFanSpeed(&rightFans, fanNum, throttle);
  }
}

