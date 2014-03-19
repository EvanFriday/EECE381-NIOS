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

void readsendfile(char* name) {
	int handle;
	char temp_sdread = 1;
	handle = alt_up_sd_card_fopen(name, 0);
	while (temp_sdread != -1) {
		temp_sdread = alt_up_sd_card_read(handle);
		alt_up_rs232_write_data(uart, temp_sdread);
		while (alt_up_rs232_get_available_space_in_write_FIFO(uart) < 10)
			;
	}
}

