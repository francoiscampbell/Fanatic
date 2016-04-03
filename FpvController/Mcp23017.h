#ifndef MCP23017_H
#define MCP23017_H

#include <stdint.h>

#define GPIO_A 0x12
#define GPIO_B 0x13

struct Mcp23017 {
  uint8_t dev7BitAddress;
};

struct Mcp23017 mcp23017_new(uint8_t dev7BitAddress);

uint8_t Mcp23017_readGpio(struct Mcp23017* device, uint8_t gpio);
void Mcp23017_writeGpio(struct Mcp23017* device, uint8_t gpio, uint8_t value);

#endif
