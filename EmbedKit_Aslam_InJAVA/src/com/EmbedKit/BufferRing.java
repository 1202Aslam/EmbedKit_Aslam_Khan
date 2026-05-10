package com.EmbedKit;

public class BufferRing 
{
    private static final int BufferSIZE = 8;
    private static final int BufferFace = BufferSIZE - 1;

    private final byte[] bufferJAVA = new byte[BufferSIZE];

    private int head = 0;       // Write index
    private int tail = 0;       // Read index
    private int count = 0;      // Number of elements

    // for add bufferJAVA entries
    public boolean write(byte data)
    {
        if (isFull())
        {
            return false;
        }

        bufferJAVA[head] = data;
        head = (head + 1) & BufferFace;					// move head forward with wrap-around
        count++;

        return true;
    }

    // for read and remove bufferJAVA entries
    public int read(int uptoRange)
    {
        if (isEmpty())
        {
            return -1;
        }

       // int range = Math.min(uptoRange, count);
        int range = uptoRange;
        int data = bufferJAVA[tail] & 0xFF;

        for (int j = 0; j < range; j++)
        {
            System.out.printf("[Read]     0x%02X:   ", bufferJAVA[tail]);
         
            bufferJAVA[tail] = 0;					// read and remove entry
            count--;
            
            System.out.println("(count=" + count + ")");

            tail = (tail + 1) & BufferFace;			// move tail forward
        }

        return data;
    }

    public int getCount()
    {
        return count;
    }

    public boolean isFull()
    {
        return count == BufferSIZE;
    }

    public boolean isEmpty()
    {
        return count == 0;
    }
    
 
    public static void main(String[] args)
    {
        BufferRing BRing = new BufferRing();

        // 1. Write the 8 bytes 

        System.out.println("1. Write the 8 bytes .....");

        byte[] input = {65, 66, 67, 68, 69, 70, 71, 72};

        for (byte b : input)
        {
            boolean success = BRing.write(b);

            boolean sizeFull = BRing.isFull();

            System.out.printf("[Write] 0x%02X:   -> %s", b, success ? " Ok " : " Fail ");

            System.out.print("(count= " + BRing.getCount() + ")");

            System.out.printf(" %s", sizeFull ? "Full " : " ");

            System.out.println(" ");
        }


        // 2. Attempt to write one more byte (0x99).

        System.out.println("2- Attempt to write one more byte (0x99).");

        boolean overFlowWrite = BRing.write((byte) 0x99);

        System.out.println( "Writing 0x99:   -> " +(overFlowWrite ? "Success" : "Fail (Buffer Full)"));

        // 3. Read 3 bytes

        System.out.println("3- Read 3 bytes one at a time.");

        BRing.read(3);


        // 4. Writing 3 new bytes

        System.out.println("4- Writing 3 new bytes");

        byte[] newInput = {73, 74, 75};

        for (byte b : newInput)
        {
            boolean success = BRing.write(b);

            boolean sizeFull = BRing.isFull();

            System.out.printf("[Write] 0x%02X:   -> %s", b, success ? " Ok " : " Fail ");

            System.out.print("(count= " + BRing.getCount() + ")");

            System.out.printf(" %s", sizeFull ? "Full " : " ");

            System.out.println(" ");
        }


        // 5. Reading all 8 bytes

        System.out.println("5- Reading all 8 bytes");

        BRing.read(8);

        // 6. Read attempt on Buffer

        System.out.println("6- Read attempt on Buffer");

        int emptyRead = BRing.read(8);

        if (emptyRead == -1)
        {
            System.out.println(
                    "[Read] (empty)  -> FAIL (bufferJAVA empty)");
        }
    }
}