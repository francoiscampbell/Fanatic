#include "Adt7470.h"

struct Adt7470 adt7470_new(uint8_t dev7BitAddress) {
  struct Adt7470 device = {.dev7BitAddress = dev7BitAddress};
  return device;
}

void Adt7470_setLowFrequencyDrive(struct Adt7470* device) {
  i2c_writeDeviceRegisterBits(device->dev7BitAddress, CONFIG_1, 0b01000000, 0b01000000);
}

void Adt7470_setDriveFrequencyToFastest(struct Adt7470* device) {
  i2c_writeDeviceRegisterBits(device->dev7BitAddress, CONFIG_2 , 0b01110000, 0b01110000);
}

void Adt7470_setFanSpeed(struct Adt7470* device, uint8_t fanNum, uint8_t speed) {
  if (fanNum > 0 && fanNum < 4) {
    uint8_t fanOffset = fanNum - 1;
    i2c_writeDeviceRegister(device->dev7BitAddress, PWM_1 + fanOffset, &speed, 1);
  }
}

