#include "i2c.h"
#include <Wire.h>

uint8_t i2c_readDeviceRegister(uint8_t dev7BitAddress, uint8_t reg) {
  Wire.beginTransmission(dev7BitAddress);
  Wire.write(reg);
  Wire.endTransmission();

  Wire.requestFrom(dev7BitAddress, (uint8_t) 1);
  long timeout = millis() + 1000;
  while (!Wire.available() && millis() < timeout);
  return Wire.read();
}

void i2c_writeDeviceRegister(uint8_t dev7BitAddress, uint8_t reg, uint8_t* data, uint16_t dataLen) {
  Wire.beginTransmission(dev7BitAddress);
  Wire.write(reg);
  Wire.write(data, dataLen);
  Wire.endTransmission();
}

void i2c_writeDeviceRegisterBits(uint8_t dev7BitAddress, uint8_t reg, uint8_t mask, uint8_t value) {
  uint8_t currentRegValue = i2c_readDeviceRegister(dev7BitAddress, reg);
  uint8_t maskedRegValue = currentRegValue & ~mask;

  uint8_t maskedNewValue = value & mask;
  uint8_t newRegValue = maskedRegValue | maskedNewValue;

  i2c_writeDeviceRegister(dev7BitAddress, reg, &newRegValue, 1);
}

