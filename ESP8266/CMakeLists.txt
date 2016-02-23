cmake_minimum_required(VERSION 3.3)
project(esp_udp)

set(SDK_BASE /Volumes/esp-open-sdk/sdk)

aux_source_directory(user SOURCE_FILES)
aux_source_directory(${SDK_BASE}/driver SOURCE_FILES)

include_directories(user)
include_directories(${SDK_BASE}/include)
include_directories(${SDK_BASE}/driver/include)

add_executable(dummy ${SOURCE_FILES})
