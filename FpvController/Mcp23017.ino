#include "Mcp23017.h"

struct Mcp23017 mcp23017_new(uint8_t dev7BitAddress) {
  struct Mcp23017 device = {.dev7BitAddress = dev7BitAddress};
  return device;
}

uint8_t mcp23017_readGpio(struct Mcp23017* device, uint8_t gpio) {
  if (gpio == GPIO_A || gpio == GPIO_B) {
    return i2c_readDeviceRegister(device->dev7BitAddress, gpio);
  }
}

void mcp23017_writeGpio(struct Mcp23017* device, uint8_t gpio, uint8_t value) {
  if (gpio == GPIO_A || gpio == GPIO_B) {
    i2c_writeDeviceRegister(device->dev7BitAddress, gpio, &value, 1);
  }
}

