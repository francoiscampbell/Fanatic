#include "I2cRegisterDevice.h"
#include <Wire.h>

I2cRegisterDevice::I2cRegisterDevice(uint8_t dev7BitAddress) {
  address = dev7BitAddress;
}

uint8_t I2cRegisterDevice::get7BitAddress() {
  return address;
}

uint8_t I2cRegisterDevice::readDeviceRegister(uint8_t reg) {
  Wire.beginTransmission(address);
  Wire.write(reg);
  Wire.endTransmission();

  Wire.requestFrom(address, (uint8_t) 1);
  long timeout = millis() + 1000;
  while (!Wire.available() && millis() < timeout);
  return Wire.read();
}

void I2cRegisterDevice::writeDeviceRegister(uint8_t reg, uint8_t data) {
  writeDeviceRegister(reg, &data, 1);
}

void I2cRegisterDevice::writeDeviceRegister(uint8_t reg, uint8_t* data, uint16_t dataLen) {
  Wire.beginTransmission(address);
  Wire.write(reg);
  Wire.write(data, dataLen);
  Wire.endTransmission();
  //os_printf("Wrote %x to register %x at device %x\n", data[0], reg, address);
}

