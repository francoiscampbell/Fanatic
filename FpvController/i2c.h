#ifndef I2C_H
#define I2C_H

uint8_t i2c_readDeviceRegister(uint8_t devAddress, uint8_t register);
void i2c_writeDeviceRegister(uint8_t devAddress, uint8_t register, uint8_t* data, uint16_t dataLen);

void i2c_writeDeviceRegisterBits(uint8_t devAddress, uint8_t register, uint8_t mask, uint8_t value);

#endif
