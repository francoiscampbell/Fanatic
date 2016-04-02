#ifndef ADT7470_H
#define ADT7470_H

#include <stdint.h>

#define CONFIG_1 0x40
#define CONFIG_2 0x74
#define PWM_1 0x32

struct Adt7470 {
  uint8_t dev7BitAddress;
};

struct Adt7470 Adt7470_new(uint8_t dev7BitAddress);

void Adt7470_setLowFrequencyDrive(struct Adt7470* device);
void Adt7470_setDriveFrequencyToFastest(struct Adt7470* device);

void Adt7470_setFanSpeed(struct Adt7470* device, uint8_t fanNum, uint8_t speed);

#endif
