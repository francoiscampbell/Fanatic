#ifndef ADT7470_H
#define ADT7470_H

#include "I2cRegisterDevice.h"

#define CONFIG_1 0x40
#define CONFIG_2 0x74
#define PWM_1 0x32
  
class Adt7470 : I2cRegisterDevice {
  public:
    Adt7470(uint8_t dev7BitAddress);
    void setLowFrequencyDrive();
    void setDriveFrequencyToFastest(); 
    void setFanSpeed(uint8_t fanNum, uint8_t speed);
};

#endif
