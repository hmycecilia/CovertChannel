
public class InstructionObject 
{
//	private static final String r = "read";
//	private static final String w = "write";
	private static final String b = "BAD";
//	public static final String c = "create";
//	public static final String d = "destroy";
//	public static final String run = "run";
	
	public String command;
	public String subject;
	public String object;
	public int value;
	
	void parseInstructions(String a)
	{
		String delim = "[ ]+";
		String[] tokens = a.split(delim);

		if(tokens.length == 2)
		{
			command = tokens[0].toLowerCase();
			subject = tokens[1].toLowerCase(); 
		}
		else if(tokens.length == 3)
		{
			command = tokens[0].toLowerCase();
			subject = tokens[1].toLowerCase();
			object = tokens[2].toLowerCase(); 
		}
		else
		{
			command = tokens[0].toLowerCase();
			subject = tokens[1].toLowerCase();
			object = tokens[2].toLowerCase(); 
			try {
            	value = Integer.parseInt(tokens[3]);}
            	catch (java.lang.NumberFormatException e)
            	{
            		command = b;
            		return;
            	}
		}
		
		System.out.println("Instruction: " + command + " " + subject + " " + object + " " + value);
	}
}
