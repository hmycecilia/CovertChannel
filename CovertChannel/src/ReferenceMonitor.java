import java.io.IOException;
import java.util.HashMap;

public class ReferenceMonitor 
{
	public static final String r = "read";
	public static final String w = "write";
	public static final String c = "create";
	public static final String d = "destroy";
	public static final String ru = "run";
	
	static HashMap<String, Integer> ref_map = new HashMap<String, Integer>();
	HashMap<String, Subject> subject_map = new HashMap<String, Subject>();
	ObjectManager object_man = new ObjectManager();

    public void RM_insert(String s, Integer level) 
    {	ref_map.put(s, level);	}
    
    
    public Integer RM_get(String s) 
    {	return ref_map.get(s);	}

	
    public void useInstruction(InstructionObject io) throws IOException 
    {
//    	System.out.println(object_man.object_map.containsKey(io.object));
//    	System.out.println(subject_map.containsKey(io.subject));
//    	System.out.println(io.command);
    	if(subject_map.containsKey(io.subject) && object_man.object_map.containsKey(io.object))
    	{
    		if(io.command.equals(r))
    		{
    			executeRead(io.subject, io.object);
    		}else if(io.command.equals(w))
    		{
    			executeWrite(io.subject, io.object, io.value);
    		}
    		else if (io.command.equals(d))
    		{
    			executeDestroy(io.subject, io.object);
    		}
    		else
    		{
//    			System.out.println("Bad Instruction");
    		}
    	}
		else if (io.command.equals(c))
		{
			executeCreate(io.subject, io.object);
		}
		else if (io.command.equals(ru))
		{
			executeRun(io.subject);
		}
    	else
    	{
//    		System.out.println("Bad instruction");
    	}
    }
	
	public void executeRead(String sub, String obj)
	{
		Subject s = subject_map.get(sub);
		Object o = object_man.object_map.get(obj);
		
//		System.out.println(s.name + " " + o.name);
//		
//		System.out.println(RM_get(sub).intValue() + " " + RM_get(obj).intValue());
		
		if(SecurityLevel.dominates(RM_get(sub).intValue(), RM_get(obj).intValue()))
		{
			object_man.read(s, o);	
		}
		else
		{
			//doesn't dominate, set the TEMP to 0 as the default
			subject_map.get(sub).TEMP = 0;
		}
//		System.out.println(s.name + " reads " + o.name);
	}
	
	public void executeWrite(String sub, String obj, int val)
	{
		Object o = object_man.object_map.get(obj);		
		
		if(SecurityLevel.can_write(RM_get(sub).intValue(), RM_get(obj).intValue()))
		{
			object_man.write(o, val);
		}
//		System.out.println(s.name + " writes value " + val + " to " + o.name);
	}
	
	public void executeCreate(String sub, String obj)
	{
		if (!object_man.object_map.containsKey(obj))
		{
			object_man.create(obj, ref_map.get(sub), ref_map);		
		}
	}
	
	public void executeDestroy(String sub, String obj)
	{
//		System.out.println(!object_man.object_map.containsKey(obj));
		if (object_man.object_map.containsKey(obj))
		{
//			System.out.println("wtffF??F?F?");
//			System.out.println(SecurityLevel.can_write(subject_map.get(sub).sl, object_man.object_map.get(obj).sl));
			if (SecurityLevel.can_write(subject_map.get(sub).sl, object_man.object_map.get(obj).sl))
			{
				Subject s = subject_map.get(sub);
				Object o = object_man.object_map.get(obj);
				object_man.destroy(s, o, ref_map);
			}
		}
	}
	
	public void executeRun(String sub) throws IOException
	{
		Subject s = subject_map.get(sub);
		object_man.run(s);
	}	
	
	
}
