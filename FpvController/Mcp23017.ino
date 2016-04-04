#include "Mcp23017.h"

Mcp23017::Mcp23017(uint8_t dev7BitAddress) : I2cRegisterDevice(dev7BitAddress) {}

uint8_t Mcp23017::readGpio(uint8_t gpio) {
  if (gpio == GPIO_A || gpio == GPIO_B) {
    return readDeviceRegister(gpio);
  }
}

void Mcp23017::writeGpio(uint8_t gpio, uint8_t value) {
  if (gpio == GPIO_A || gpio == GPIO_B) {
    writeDeviceRegister(gpio, value);
  }
}

