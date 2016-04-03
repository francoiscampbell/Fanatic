#ifndef MCP23017_H
#define MCP23017_H

#include "I2cRegisterDevice.h"

#define GPIO_A 0x12
#define GPIO_B 0x13

class Mcp23017 : I2cRegisterDevice {
  public:
    Mcp23017(uint8_t dev7BitAddress);
    uint8_t readGpio(uint8_t gpio);
    void writeGpio(uint8_t gpio, uint8_t value); 
};

#endif
