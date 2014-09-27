import java.util.HashMap;


public class ObjectManager 
{
	int a;
	int num_bits = 0;
	
	public HashMap<String, Object> object_map = new HashMap<String, Object>();

	public void read(Subject s, Object o){	s.TEMP = o.current_value;	}

	public void write(Object o, int val){	o.current_value = val;	}
	
	public void create(String o, int sl, HashMap<String, Integer> r)
	{
		Object obj = new Object(o.toLowerCase(), sl);
		obj.add_print_name(o);
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
		if (s.TEMP == 1) //Lyle should add 1 to byte if his TEMP is 1 after read.
			a = a | (1 << num_bits);
		else
			a = a | (0 << num_bits);
		num_bits++;
		if (num_bits > 7)
		{
			System.out.println(a);
			a = 0;
		}
	}
	
	
	
}


