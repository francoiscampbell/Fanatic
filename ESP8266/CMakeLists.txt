cmake_minimum_required(VERSION 3.3)
project(esp_udp)

#message("setting toolchain")
#set(CMAKE_TOOLCHAIN_FILE cmake/ESP8266.toolchain.cmake)
set(SDK_BASE /Volumes/esp-open-sdk/sdk)

aux_source_directory(user SOURCE_FILES)
aux_source_directory(${SDK_BASE}/driver SOURCE_FILES)

#find_library(ESP_ESPCONN espconn)
include_directories(user)
include_directories(${SDK_BASE}/include)
include_directories(${SDK_BASE}/driver/include)

add_executable(dummy ${SOURCE_FILES})

#set(CMAKE_C_COMPILER /Volumes/esp-open-sdk/xtensa-lx106-elf/bin/xtensa-lx106-elf-gcc CACHE FILEPATH CMAKE_C_COMPILER)
#set(CMAKE_CXX_COMPILER /Volumes/esp-open-sdk/xtensa-lx106-elf/bin/xtensa-lx106-elf-g++ CACHE FILEPATH CMAKE_CXX_COMPILER)