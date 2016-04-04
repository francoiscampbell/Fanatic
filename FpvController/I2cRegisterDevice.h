#ifndef I2cRegisterDevice_H
#define I2cRegisterDevice_H

#include <stdint.h>

class I2cRegisterDevice {
  public:
    I2cRegisterDevice(uint8_t dev7BitAddress);
    uint8_t get7BitAddress();
    uint8_t readDeviceRegister(uint8_t reg);
    void writeDeviceRegister(uint8_t reg, uint8_t data);
    void writeDeviceRegister(uint8_t reg, uint8_t* data, uint16_t dataLen);
  private:
    uint8_t address;
};

#endif

