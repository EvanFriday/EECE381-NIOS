#include "sdcard.h"

void initsdcard(sdcard *card, alt_up_sd_card_dev **device_reference) {
	card->connected = 0;
	*device_reference = alt_up_sd_card_open_dev(SD_CARD_NAME);
}

void testsdcard(sdcard *card, alt_up_sd_card_dev *device_reference) {
	if (device_reference != NULL) {
		if ((card->connected == 0) && (alt_up_sd_card_is_Present())) {
			printf("Card connected.\n");
			if (alt_up_sd_card_is_FAT16()) {
				printf("FAT16 file system detected.\n");
			} else {
				printf("Unknown file system.\n");
			}
			card->connected = 1;
		} else if ((card->connected == 1) && (alt_up_sd_card_is_Present()
				== false)) {
			printf("Card disconnected.\n");
			card->connected = 0;
		}
	}
}

void readfile(char* name) {
	int handle;
	handle = alt_up_sd_card_fopen(name, 0);
	while (alt_up_sd_card_read(handle) != -1) {
		//alt_up_rs232_write_data(uart, alt_up_sd_card_read(handle));
	}
}

