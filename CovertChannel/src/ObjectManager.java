import java.util.HashMap;


public class ObjectManager {
	public HashMap<String, Object> object_map = new HashMap<String, Object>();

	public void read(Subject s, Object o){	s.TEMP = o.current_value;	}

	public void write(Object o, int val){	o.current_value = val;	}
	
	public void create(String o, int sl, HashMap<String, Integer> r)
	{
		Object obj = new Object(o.toLowerCase(), sl);
		obj.print_name = o;
		object_map.put(obj.name, obj);
		r.put(obj.name, sl);
		
	}
	
	public void destroy(Subject s, Object o, HashMap<String, Integer> r)
	{
		object_map.remove(o.name);
		r.remove(o.name);
	}
	
	public void run(Subject s)
	{
		//do the bit fiddling
	}
	
	
	
}


