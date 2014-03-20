#include <stdio.h>
#include "altera_up_avalon_rs232.h"
#include "sdcard.h"
#include <string.h>
#include <system.h>

int main() {
	int i;
	unsigned char data;
	unsigned char parity;
	//unsigned char message[] = "EECE381 is so much fun";
	sdcard card;

	//initializing sdcard
	alt_up_sd_card_dev *device_reference = NULL;
	initsdcard(&card, &device_reference);
	testsdcard(&card, device_reference);

	printf("UART Initialization\n");
	alt_up_rs232_dev* uart = alt_up_rs232_open_dev(RS232_0_NAME);
	printf("Clearing read buffer to start\n");
	while (alt_up_rs232_get_used_space_in_read_FIFO(uart)) {
		alt_up_rs232_read_data(uart, &data, &parity);
	}

	printf("Sending the message to the Middleman\n");
	readsendfile("nodes.xml", uart);

	printf("\n");
	printf("Message Echo Complete\n");
	return 0;
}

/*
 int readFile(char *, void *);
 void convertToBit(void *, int, void *);

 int main() {
 int i;
 unsigned char data;
 unsigned char parity;
 char file_to_read[];
 unsigned char * buffer;
 unsigned long fileLen;

 //Read in JPEG file
 fileLen = readFile(file_to_read, buffer);
 unsigned char bits[fileLen + 1];
 //Convert into bits.
 convertToBits(buffer, *fileLen, *bits);

 printf("Initializing Serial");
 alt_up_rs232_dev* uart = alt_up_rs232_open_dev(RS232_0_NAME);

 printf("Clearing read buffer to start\n");
 while (alt_up_rs232_get_used_space_in_read_FIFO(uart)) {
 alt_up_rs232_read_data(uart, &data, &parity);
 }

 printf("Sending the message to the Middleman\n");
 alt_up_rs232_write_data(uart, fileLen);
 // Now send the actual message to the Middleman
 for (i = 0; i < fileLen; i++) {
 alt_up_rs232_write_data(uart, bits[i]);
 }

 printf("Waiting for data to come back from the Middleman\n");
 while (alt_up_rs232_get_used_space_in_read_FIFO(uart) == 0)
 ;
 // First byte is the number of characters in our message
 alt_up_rs232_read_data(uart, &data, &parity);
 int num_to_receive = (int) data;

 printf("About to receive %d characters:\n", num_to_receive);
 for (i = 0; i < num_to_receive; i++) {
 while (alt_up_rs232_get_used_space_in_read_FIFO(uart) == 0)
 ;
 alt_up_rs232_read_data(uart, &data, &parity);
 printf("%c", data);
 }
 printf("\n");
 printf("Message Echo Complete\n");
 free(buffer);
 return 0;
 }

 //read in a file to buffer, and return length of file
 int readFile(char * file_to_read, void * buffer) {
 FILE file = fopen(file_location, rb);
 fseek(file, 0, SEEK_END);
 int fileLen = ftell(file);
 fseek(file, 0, SEEK_SET);
 buffer = (char *) malloc(&fileLen + 1);
 fread(buffer, &fileLen, 1, file_to_read);
 fclose(file);
 return fileLen;
 }

 //pass in a buffer to read from, the length of contents, and a char array to store in
 void convertToBit(void * buffer, int length, void * bits) {
 int c = 0;
 int size = length * 8;
 unsigned char mask = 1;
 unsigned char byte;
 int i = 0;
 for (c = 0; c < length; c++) {
 byte = ((char *) &buffer)[c];
 for (i = 0; i < 8; i++) {
 bits[i] = (byte >> i) & mask;
 }
 }

 }
 */
