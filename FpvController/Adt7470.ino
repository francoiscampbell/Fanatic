#include "Adt7470.h"

Adt7470::Adt7470(uint8_t dev7BitAddress) : I2cRegisterDevice(dev7BitAddress) {}

void Adt7470::setLowFrequencyDrive() {
  writeDeviceRegisterBits(CONFIG_1, 0b01000000, 0b01000000);
}

void Adt7470::setDriveFrequencyToFastest() {
  writeDeviceRegisterBits(CONFIG_2 , 0b01110000, 0b01110000);
}

void Adt7470::setFanSpeed(uint8_t fanNum, uint8_t speed) {
  if (fanNum > 0 && fanNum < 4) {
    uint8_t fanOffset = fanNum - 1;
    writeDeviceRegister(PWM_1 + fanOffset, &speed, 1);
  }
}

