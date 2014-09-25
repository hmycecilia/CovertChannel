import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;


public class CovertChannel 
{
	public static void main(String[] args) throws IOException 
	{
		List<String> instructions = readSmallTextFile(args[0]);
		
        ReferenceMonitor ref_mon = new ReferenceMonitor();
        
        
        
        
		//name of the instruction file
		System.out.print("Reading from file: " + args[0] + "\n");
				
		//loop that runs and prints the status of each instruction
		Iterator<String> it = instructions.iterator();
		while(it.hasNext())
		{
			System.out.println();
			InstructionObject trogdor = new InstructionObject();
			trogdor.parseInstructions(it.next());
			ref_mon.useInstruction(trogdor);
			print_instruction_state(hobj, lobj, hal, lyle);
		}	
        
        
        
	}
	
	
	
	
	private static void print_instruction_state(Object high, Object low, Subject h, Subject l) 
	{
       System.out.println("The current state is: ");
       System.out.println("	" + low.print_name + " has value: " + low.current_value);
       System.out.println("	" + high.print_name + " has value: " + high.current_value);
       System.out.println("	" + l.print_name + " has recently read: " + l.TEMP);
       System.out.println("	" + h.print_name + " has recently read: " + h.TEMP);
	}

	static List<String> readSmallTextFile(String aFileName) throws IOException {
		   return Files.readAllLines(Paths.get(aFileName), StandardCharsets.UTF_8);
	}
	
	
	
}


class Subject 
{
	String name;
	String print_name;
	int TEMP;
	int sl;
	
	//create a subject. TEMP is set to 0 on default
	Subject(String s, int sl)
	{
		name = s;
		TEMP = 0;
		this.sl = sl;
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