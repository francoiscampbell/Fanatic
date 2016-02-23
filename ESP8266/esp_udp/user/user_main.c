#include <c_types.h>
#include <espconn.h>
#include <mem.h>
#include "driver/uart.h"
#include "osapi.h"


#pragma clang diagnostic push
#pragma ide diagnostic ignored "CannotResolve"

//void wifiConnectCb(uint8_t status) {
//    os_printf("Device connected");
//}
static void ICACHE_FLASH_ATTR network_received(void *arg, char *data, unsigned short len);

static void ICACHE_FLASH_ATTR network_udp_start(uint16_t port);

static void ICACHE_FLASH_ATTR network_received(void *arg, char *data, unsigned short len) {
    os_printf("UDP received. Len: %d. Data: ", len);
    if (len == 12) {
        uint32_t x = {
                (uint8_t) data[0]
                | (uint8_t) (data[1] & 0xFF) << 8
                | (uint8_t) (data[2] & 0xFF) << 16
                | (uint8_t) (data[3] & 0xFF) << 24
        };
        uint32_t y = {
                (uint8_t) data[4]
                | (uint8_t) (data[5] & 0xFF) << 8
                | (uint8_t) (data[6] & 0xFF) << 16
                | (uint8_t) (data[7] & 0xFF) << 24
        };
        uint32_t z = {
                (uint8_t) data[8]
                | (uint8_t) (data[9] & 0xFF) << 8
                | (uint8_t) (data[10] & 0xFF) << 16
                | (uint8_t) (data[11] & 0xFF) << 24
        };
        os_printf("x:%d y:%d z:%d\n\r", x, y, z);
    } else {
        char rotFlag = data[0];
        char rotDir = data[1];
        uint32_t rotAmt = {
                (uint8_t) data[2]
                | (uint8_t) (data[3] & 0xFF) << 8
                | (uint8_t) (data[4] & 0xFF) << 16
                | (uint8_t) (data[5] & 0xFF) << 24
        };
        uint32_t thrAmt = {
                (uint8_t) data[7]
                | (uint8_t) (data[8] & 0xFF) << 8
                | (uint8_t) (data[9] & 0xFF) << 16
                | (uint8_t) (data[10] & 0xFF) << 24
        };
        char thrFlag = data[6];
        os_printf("%c%c%d%c%d\n\r", rotFlag, rotDir, rotAmt, thrFlag, thrAmt);
    }
}

static void ICACHE_FLASH_ATTR network_udp_start(uint16_t port) {
    struct espconn *udpServer = (struct espconn *) os_zalloc(sizeof(struct espconn));
    udpServer->type = ESPCONN_UDP;
    udpServer->state = ESPCONN_NONE;
    udpServer->proto.udp = (esp_udp *) os_zalloc(sizeof(esp_udp));
    udpServer->proto.udp->local_port = port;
//udpServer->proto.udp->remote_port=3338;
    if (espconn_create(udpServer) == 0) {
        espconn_regist_recvcb(udpServer, network_received);
    }
}

void user_init(void) {
    uart_init(BIT_RATE_115200, BIT_RATE_115200);
    os_delay_us(1000000);
//    WIFI_SoftAP("", "", wifiConnectCb);

    os_printf("\r\nSystem started ...\r\n");
    network_udp_start(3907);
}


#pragma clang diagnostic pop