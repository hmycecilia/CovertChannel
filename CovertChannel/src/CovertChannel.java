import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;


public class CovertChannel 
{
	public static void main(String[] args) throws IOException 
	{
		List<String> instructions = null;
		
        ReferenceMonitor ref_mon = new ReferenceMonitor();
                
        Path path = null;
        boolean VERBOSE = false;
        
        if(args.length == 1)
        {
        	path = Paths.get(args[0]);
        	instructions = readSmallTextFile(args[0]);
        }
        else if(args.length == 2)
        {
        	path = Paths.get(args[1]);
        	instructions = readSmallTextFile(args[1]);
        	VERBOSE = true;
        }
        
        byte [] data = Files.readAllBytes(path);
        
        FileOutputStream out = null;
        if(VERBOSE){	out = new FileOutputStream(args[1] + ".out");	}
        else{	out = new FileOutputStream(args[0] + ".out");	}        	

		ByteArrayInputStream is = new ByteArrayInputStream(data);
		
		byte c;
		
        while ((c = (byte) is.read()) != -1)
        {
        	System.out.println(c);
        }
        
		//name of the instruction file
        if(VERBOSE){	System.out.print("Reading from file: " + args[1] + "\n");	}
        else{	System.out.print("Reading from file: " + args[0] + "\n");	}
				
        
        
		//loop that runs and prints the status of each instruction
		Iterator<String> it = instructions.iterator();
		while(it.hasNext())
		{
			System.out.println();
			InstructionObject trogdor = new InstructionObject();
			trogdor.parseInstructions(it.next());
			ref_mon.useInstruction(trogdor);
			//print_instruction_state(hobj, lobj, hal, lyle);
		}

		out.close();
	}
	
	private static void lyle_inst()
	{
//		  CREATE LYLE OBJ
//		  WRITE LYLE OBJ 1
//		  READ LYLE OBJ
//		  DESTROY LYLE OBJ
//		  RUN LYLE
	}
	

	
	private static byte[] read (File file) throws IOException
	{
		byte [] buffer = new byte[(int) file.length()];
		InputStream i = null;
		try
		{
			i = new FileInputStream(file);
			if (i.read(buffer) == -1)
				throw new IOException("Eof reached trying to read.");
		}
		finally
		{
			if (i != null)
				i.close();
		}
		return buffer;
	}
	
	private static void print_instruction_state(Object high, Object low, Subject h, Subject l) 
	{
       System.out.println("The current state is: ");
       System.out.println("	" + low.print_name + " has value: " + low.current_value);
       System.out.println("	" + high.print_name + " has value: " + high.current_value);
       System.out.println("	" + l.print_name + " has recently read: " + l.TEMP);
       System.out.println("	" + h.print_name + " has recently read: " + h.TEMP);
	}

	static List<String> readSmallTextFile(String aFileName) throws IOException 
	{
		   return Files.readAllLines(Paths.get(aFileName), StandardCharsets.UTF_8);
	}
	
	
	
}


class Subject 
{
	String name;
	String print_name;
	int TEMP;
	int sl;
	FileOutputStream filethingy;
	
	//create a subject. TEMP is set to 0 on default
	Subject(String s, int sl, FileOutputStream f)
	{
		name = s;
		TEMP = 0;
		this.sl = sl;
		filethingy = f;
	}
	
	void add_print_name(String s)
	{
		print_name = s;
	}
}

class Object 
{
	String name;
	String print_name;
	int current_value;
	int sl;
	
	//create an object. current_value is set to 0 on default
	Object(String s, int sl)
	{
		name = s;
		current_value = 0;
		this.sl = sl;
	}
	
	void add_print_name(String s)
	{
		print_name = s;
	}
	
}

class SecurityLevel{
    protected static final int low = 0;
    protected static final int high = 1;

    public static boolean dominates(int subject_level, int object_Level){
        if (subject_level >= object_Level){		return true;		}
        else{	return false;	}
    }

    public static boolean can_write(int subject_level, int object_Level){
        if (subject_level <= object_Level){		return true;		}
        else{	return false;	}
    }
}