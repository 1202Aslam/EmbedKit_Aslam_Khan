# EmbedKit_Aslam_Khan
code written in java
Implement a circular buffer for uint8_t data with a fixed capacity of 8 bytes.
Run the following sequence in order and print the result of every operation:
1. Write the 8 bytes 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48 one at a time. After all 8 writes, confirm the buffer is full and count equals 8.
2. Attempt to write one more byte (0x99). Print that the write failed (buffer full).
3. Read 3 bytes one at a time. Print each byte read (expected: 0x41, 0x42, 0x43). Confirm count
is now 5.
4. Write 3 new bytes 0x49, 0x4A, 0x4B. These must succeed -- the 3 slots freed in step 3 are now
reused. Confirm count is back to 8.
5. Read all remaining 8 bytes one at a time and print each. Confirm buffer is empty after.
6. Attempt to read from the now-empty buffer. Print that the read failed (buffer empty).
