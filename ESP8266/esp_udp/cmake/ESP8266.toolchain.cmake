INCLUDE(CMakeForceCompiler)

set(CMAKE_SYSTEM_NAME ESP8266)
set(CMAKE_SYSTEM_VERSION 1)

set(ESP_OPEN_SDK_ROOT /Volumes/esp-open-sdk)
set(ESP_ESPRESSIF_SDK ${ESP_OPEN_SDK_ROOT}/sdk)

set(SOURCE_FILES user/user_main.c)
include_directories(user)

add_executable(esp ${SOURCE_FILES})
add_custom_target(esp-make)

# specify the cross compiler
cmake_force_c_compiler(${ESP_OPEN_SDK_ROOT}/xtensa-lx106-elf/bin/xtensa-lx106-elf-gcc lx106-elf-gcc)
cmake_force_cxx_compiler(${ESP_OPEN_SDK_ROOT}/xtensa-lx106-elf/bin/xtensa-lx106-elf-g++ lx106-elf-g++)

include_directories(${ESP_ESPRESSIF_SDK}/include)
include_directories(${ESP_ESPRESSIF_SDK}/include/driver)

#link_directories(${SDK_BASE}/lib)
file(GLOB ESP8266_LIBS "${ESP_ESPRESSIF_SDK}/lib/*.a")
target_link_libraries(esp ${ESP8266_LIBS})

# search for programs in the build host directories
set(CMAKE_FIND_ROOT_PATH_MODE_PROGRAM NEVER)
# for libraries and headers in the target directories
set(CMAKE_FIND_ROOT_PATH_MODE_LIBRARY ONLY)
set(CMAKE_FIND_ROOT_PATH_MODE_INCLUDE ONLY)

set(CMAKE_C_FLAGS "-Os -g -std=gnu99 -Wpointer-arith -Wno-implicit-function-declaration -Wundef -pipe -D__ets__ -DICACHE_FLASH -fno-inline-functions -ffunction-sections -nostdlib -mlongcalls -mtext-section-literals -falign-functions=4 -fdata-sections")
set(CMAKE_CXX_FLAGS "-Os -g -D__ets__ -DICACHE_FLASH -mlongcalls -mtext-section-literals -fno-exceptions -fno-rtti -falign-functions=4 -std=c++11 -MMD -ffunction-sections -fdata-sections")
set(CMAKE_EXE_LINKER_FLAGS "-nostdlib -Wl,--no-check-sections -Wl,-static -Wl,--gc-sections")

set(CMAKE_C_LINK_EXECUTABLE "<CMAKE_C_COMPILER> <FLAGS> <CMAKE_C_LINK_FLAGS> <LINK_FLAGS> -o <TARGET> -Wl,--start-group <OBJECTS> <LINK_LIBRARIES> -Wl,--end-group")
set(CMAKE_CXX_LINK_EXECUTABLE "<CMAKE_CXX_COMPILER> <FLAGS> <CMAKE_CXX_LINK_FLAGS> <LINK_FLAGS> -o <TARGET> -Wl,--start-group <OBJECTS> <LINK_LIBRARIES> -Wl,--end-group")