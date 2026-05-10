package task_May_2026;

public class Task01 
{
	private static final int BUFFER_SIZE = 8;
	private static final int BUFFER_MASK = BUFFER_SIZE - 1; 			// for array last index

	private final byte[] buffer = new byte[BUFFER_SIZE];
//	private int head = 0; 												// Write index
	private int tail = 0; 												// Read index
	private int count = 0; 												// Number of elements

	// for add buffer entries
	public boolean write(byte data) 
	{
		if (isFull()) 
		{
			return false;
		}
		
		int myIndex = count ;
		buffer[myIndex] = data;
		myIndex = (myIndex + 1) & BUFFER_MASK; 							// Bitwise conditions		
		count++;
		return true ;
	}

	// for read and remove buffer entries,
	public int read(int uptoRange) 
	{
		if (isEmpty()) 
		{
			return -1;
		}
		int range = uptoRange;
		int data = buffer[tail] & 0xFF;
		
		for (int j = 0; j < range; j++) 
		{
			count-- ;
			int indexRemove = 0;
			System.out.printf("[Read]     0x%02X:   ", buffer[indexRemove]);
			System.out.println("(count="+ count +")");
			for (int i = 0; i<BUFFER_SIZE; i++) 
			{
				if (i == BUFFER_MASK) 
				{
					buffer[i] = 0;
				} 
				else 
				{
					buffer[i] = buffer[i + 1];
				}
			}
		}
		return data;	
	}
	
	public int getCount() 
	{
		return count;
	}

	public boolean isFull() 
	{
		return count == BUFFER_SIZE;
	}

	public boolean isEmpty() 
	{
		return count == 0;
	}
	
	// for retrieve entries and testing purpose...
	public void retrieve()
	{
		System.out.println("*************************************");
		for(int i=0; i<8; i++ )
		{
			System.out.print(buffer[i]+", ");
		}
		System.out.println("");
		System.out.println("*************************************");
	}

	public static void main(String[] args) 
	{
		Task01 cb = new Task01();

		// 1. Write 8 bytes (0x41 to 0x48)

		System.out.println("--- Step 1: Writing 8 bytes ---");
		
		byte[] input = { 65, 66, 67, 68, 69, 70, 71, 72 };
		for (byte b : input) 
		{
			boolean success = cb.write(b);
			boolean sizeFull = cb.isFull();
			System.out.printf("[Write] 0x%02X:   -> %s", b, success ? " Ok ": " Fail ");
			System.out.print("(count= " + cb.getCount()+")");
			System.out.printf(" %s", sizeFull ? "FUll " : " ");
			System.out.println(" ");
			
		}
		
		//  cb.retrieve();			// for testing
		
		// 2. Attempt to write one more (0x99)

			System.out.println("\n--- Step 2: Overflow Test ---");
			boolean overFlowWrite = cb.write((byte) 0x99);
			System.out.println("Writing 0x99:   -> " + (overFlowWrite ? "Success" : "Fail (Buffer Full)"));
		
		// 3. Read 3 bytes

			System.out.println("\n--- Step 3: Reading 3 bytes ---");
			cb.read(3);
		//  cb.retrieve();			// for testing purpose 
		
		// 4. Write 3 new bytes (0x49, 0x4A, 0x4B)
		
			System.out.println("\n--- Step 4: Writing 3 new bytes (Wrap-around test) ---");
			
			byte[] newInput = {73,74,75};
			for (byte b : newInput)
			{
				boolean success = cb.write(b);
				boolean sizeFull = cb.isFull();
				System.out.printf("[Write] 0x%02X:   -> %s", b, success ? " Ok ": " Fail ");
				System.out.print("(count= " + cb.getCount()+")");
				System.out.printf(" %s", sizeFull ? "FUll " : " ");
				System.out.println(" ");
			}
		//  cb.retrieve();			// for testing purpose
			
			// 5. Read 8 bytes

				System.out.println("\n--- Step 5: Reading 8 bytes ---");
				cb.read(8);
			//  cb.retrieve();			// for testing purpose
						
			// 6. Attempt to read from empty buffer
						
				System.out.println("\n--- Step 6: Underflow Test ---");
				int emptyRead = cb.read(8);
				
				if (emptyRead == -1)
				{	
					System.out.println("[Read] (empty)  -> FAIL (buffer empty");
				}
	}
}
