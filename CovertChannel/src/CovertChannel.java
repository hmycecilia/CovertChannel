import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class CovertChannel 
{
	public static void main(String[] args) throws IOException 
	{
		for (int goku = 0; goku < 10; goku++)
		{
		//create the reference monitor to hold the subjects and objects and their security levels
        ReferenceMonitor ref_mon = new ReferenceMonitor();
        
        //variable initialization
        Path path = null;
        boolean VERBOSE = false;
        FileOutputStream out = null;
        PrintWriter log = null;
        
        //sets up the variable above based on if is verbose mode or not.
        if(args.length == 1)
        {
        	path = Paths.get(args[0]);
        	out = new FileOutputStream(args[0] + ".out");
        }
        else if(args.length == 2)
        {
        	path = Paths.get(args[1]);
        	VERBOSE = true;
        	out = new FileOutputStream(args[1] + ".out");	
        	log = new PrintWriter(new File("log"));
        }
        
        //read the entire file into this byte array
        byte [] data = Files.readAllBytes(path);
        
        //create a stream on the array
		ByteArrayInputStream is = new ByteArrayInputStream(data);
		
        Subject lyle = new Subject("lyle", SecurityLevel.low, out);
        lyle.add_print_name("Lyle");
        Subject hal = new Subject("hal", SecurityLevel.high, out);
        hal.add_print_name("Hal");
        
        ref_mon.RM_insert(lyle.name.toLowerCase(), lyle.sl);
        ref_mon.RM_insert(hal.name.toLowerCase(), hal.sl);
        
        ref_mon.subject_map.put(lyle.name.toLowerCase(), lyle);
        ref_mon.subject_map.put(hal.name.toLowerCase(), hal);
        
		InstructionObject lyle_create = new InstructionObject();
		lyle_create.parseInstructions("CREATE LYLE OBJ");
		InstructionObject lyle_write = new InstructionObject();
		lyle_write.parseInstructions("WRITE LYLE OBJ 1");
		InstructionObject lyle_read = new InstructionObject();
		lyle_read.parseInstructions("READ LYLE OBJ");
		InstructionObject lyle_destroy = new InstructionObject();
		lyle_destroy.parseInstructions("DESTROY LYLE OBJ");
		InstructionObject lyle_run = new InstructionObject();
		lyle_run.parseInstructions("RUN LYLE");
		InstructionObject hal_create = new InstructionObject();
		hal_create.parseInstructions("CREATE HAL OBJ");
		
		//name of the instruction file
        if(VERBOSE){	System.out.print("Reading from file: " + args[1] + "\n");	}
        else{	System.out.print("Reading from file: " + args[0] + "\n");	}
        
		//loop that runs and prints the status of each instruction
		
		int c;
		Stopwatch sc = new Stopwatch();
		long numbytes = 0;
		
		sc.start();
        while ((c = (byte) is.read()) != -1)
        {
        	numbytes++;
        	Integer nom = 0;
        	for (int i = 6; i >= 0; i--)
        	{
        		nom = (c & (1 << i));
        		nom >>= i;
        		if (nom == 0)
        		{
        			use_hal(hal_create, ref_mon, log, VERBOSE);
        			use_lyle(ref_mon, lyle_create, lyle_write, lyle_read, lyle_destroy, lyle_run, log, VERBOSE);
        		}
        		else
        		{
        			use_lyle(ref_mon, lyle_create, lyle_write, lyle_read, lyle_destroy, lyle_run, log, VERBOSE);
        		}
        	}
        }
        sc.stop();
		out.close();
		if (VERBOSE)
			log.close();
		else
			System.out.println(args[0] + " " + (numbytes) + " bytes " + (((numbytes * 8) / (sc.time() * 1000))) + " bits/ms");
		}
	}	

	static List<String> readSmallTextFile(String aFileName) throws IOException 
	{
		   return Files.readAllLines(Paths.get(aFileName), StandardCharsets.UTF_8);
	}
	
	private static void use_hal (InstructionObject a, ReferenceMonitor ref_mon, PrintWriter log, boolean verbose) throws IOException

	{
		ref_mon.useInstruction(a);
		if (verbose)
			log.println("CREATE HAL OBJ");
	}
	
	private static void use_lyle (ReferenceMonitor ref_mon, InstructionObject lyle_create, InstructionObject lyle_write, InstructionObject lyle_read,
			InstructionObject lyle_destroy, InstructionObject lyle_run, PrintWriter log, boolean verbose) throws IOException
	{
		ref_mon.useInstruction(lyle_create);
		ref_mon.useInstruction(lyle_write);
		ref_mon.useInstruction(lyle_read);
		ref_mon.useInstruction(lyle_destroy);
		ref_mon.useInstruction(lyle_run);
		if (verbose)
		{
			log.println("CREATE LYLE OBJ");
			log.println("WRITE LYLE OBJ 1");
			log.println("READ LYLE OBJ");
			log.println("DESTROY LYLE OBJ");
			log.println("RUN LYLE");
		}
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

class Stopwatch 
{	
    private long startTime;
    private long stopTime;

    public static final double NANOS_PER_SEC = 1000000000.0;

	/**
	 start the stop watch.
	*/
	public void start()
	{	
		System.gc();
		startTime = System.nanoTime();
	}

	/**
	 stop the stop watch.
	*/
	public void stop()
	{	
		stopTime = System.nanoTime();	
	}

	/**
	elapsed time in seconds.
	@return the time recorded on the stopwatch in seconds
	*/
	public double time()
	{	
		return (stopTime - startTime) / NANOS_PER_SEC;	
	}

	public String toString()
	{   
		return "elapsed time: " + time() + " seconds.";
	}

	/**
	elapsed time in nanoseconds.
	@return the time recorded on the stopwatch in nanoseconds
	*/
	public long timeInNanoseconds()
	{	
		return (stopTime - startTime);	
	}
}