
public class InstructionObject 
{
	private static final String r = "read";
	private static final String w = "write";
	private static final String b = "BAD";
	
	public String command;
	public String subject;
	public String object;
	public int value;
	
	void parseInstructions(String a)
	{
		String delim = "[ ]+";
		String[] tokens = a.split(delim);

		//check if is a valid instruction, if is then assign to variables
		if (tokens.length == 3 || tokens.length == 4)
		{
            command = tokens[0].toLowerCase();
            
            if (!command.equals(r) && !command.equals(w))
            {
            	command = b;
            }
            
            subject = tokens[1].toLowerCase();
            object = tokens[2].toLowerCase(); 
            if(command.equals(w) && tokens.length == 4)
            {
            	try {
            	value = Integer.parseInt(tokens[3]);}
            	catch (java.lang.NumberFormatException e)
            	{
            		command = b;
            		return;
            	}
            }else if(!command.equals(r))
            {
            	command = b;
            }
            
		} else 
		{
			command = b;
		}
		
		
	}
}
