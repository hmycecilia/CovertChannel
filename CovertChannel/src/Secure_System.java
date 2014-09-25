import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Secure_System 
{
	
	public static final String r = "read";
	public static final String w = "write";
	

	public static void main(String[] args) throws IOException
	{

		
		//add the subjects to the system
        Subject lyle = new Subject("lyle", SecurityLevel.low);
        lyle.add_print_name("Lyle");
        Subject hal = new Subject("hal", SecurityLevel.high);
        hal.add_print_name("Hal");
        
        ref_mon.RM_insert(lyle.name.toLowerCase(), lyle.sl);
        ref_mon.RM_insert(hal.name.toLowerCase(), hal.sl);
        
        ref_mon.subject_map.put(lyle.name.toLowerCase(), lyle);
        ref_mon.subject_map.put(hal.name.toLowerCase(), hal);
		
		//add the objects to the system
        Object lobj = new Object("lobj", SecurityLevel.low);
        lobj.add_print_name("LObj");
		Object hobj = new Object("hobj", SecurityLevel.high);
		hobj.add_print_name("HObj");
		
		ref_mon.RM_insert(lobj.name.toLowerCase(), lobj.sl);
		ref_mon.RM_insert(hobj.name.toLowerCase(), hobj.sl);
		
		ref_mon.object_man.object_map.put(lobj.name.toLowerCase(), lobj);
		ref_mon.object_man.object_map.put(hobj.name.toLowerCase(), hobj);
        
        

		
	}


}





