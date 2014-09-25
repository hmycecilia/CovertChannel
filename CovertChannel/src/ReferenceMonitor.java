import java.util.HashMap;

public class ReferenceMonitor 
{
	static HashMap<String, Integer> ref_map = new HashMap<String, Integer>();
	HashMap<String, Subject> subject_map = new HashMap<String, Subject>();
	ObjectManager object_man = new ObjectManager();

    public void RM_insert(String s, Integer level) 
    {	ref_map.put(s, level);	}
    
    
    public Integer RM_get(String s) 
    {	return ref_map.get(s);	}

	
    public void useInstruction(InstructionObject io) 
    {
    	if(subject_map.containsKey(io.subject) && object_man.object_map.containsKey(io.object))
    	{
    		if(io.command.equals(Secure_System.r))
    		{
    			executeRead(io.subject, io.object);
    		}else if(io.command.equals(Secure_System.w))
    		{
    			executeWrite(io.subject, io.object, io.value);
    		}else
    		{
    			System.out.println("Bad Instruction");
    		}
    	}else
    	{
    		System.out.println("Bad instruction");
    	}
    }
	
	void executeRead(String sub, String obj)
	{
		Subject s = subject_map.get(sub);
		Object o = object_man.object_map.get(obj);
		
		if(SecurityLevel.dominates(RM_get(sub).intValue(), RM_get(obj).intValue()))
		{	
			object_man.read(s, o);	
		}else
		{
			//doesn't dominate, set the TEMP to 0 as the default
			subject_map.get(sub).TEMP = 0;
		}
		System.out.println(s.name + " reads " + o.name);
	}
	
	void executeWrite(String sub, String obj, int val)
	{
		Subject s = subject_map.get(sub);
		Object o = object_man.object_map.get(obj);		
		
		if(SecurityLevel.can_write(RM_get(sub).intValue(), RM_get(obj).intValue()))
		{
			object_man.write(o, val);
		}
		System.out.println(s.name + " writes value " + val + " to " + o.name);
	}
	
	void executeCreate(String sub, String obj)
	{
		if (!object_man.object_map.containsKey(obj))
		{
			object_man.create(obj, subject_map.get(sub).sl, ref_map);
			
		}
	}
	
	void executeDestroy(String sub, String obj)
	{
		if (!object_man.object_map.containsKey(obj))
		{
			if (SecurityLevel.can_write(subject_map.get(sub).sl, object_man.object_map.get(obj).sl))
			{
				Subject s = subject_map.get(sub);
				Object o = object_man.object_map.get(obj);
				object_man.destroy(s, o, ref_map);
			}
		}
	}
	
	void executeRun()
	{
		
	}	
	
	
}
