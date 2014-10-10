import java.io.IOException;
import java.util.HashMap;


public class ObjectManager
{
	Integer a = 0;
	byte num_bits = 6;
	
	public HashMap<String, Object> object_map = new HashMap<String, Object>();

	public void read(Subject s, Object o){	s.TEMP = o.current_value;	}

	public void write(Object o, int val){	o.current_value = val;	}
	
	public void create(String o, int sl, HashMap<String, Integer> r)
	{
//		System.out.println("SL in create: " + sl);
		Object obj = new Object(o.toLowerCase(), sl);
		obj.add_print_name(o);
		object_map.put(obj.name, obj);
		r.put(obj.name, sl);
		
	}
	
	public void destroy(Subject s, Object o, HashMap<String, Integer> r)
	{
//		System.out.println("Object name in destroy: " + o.name);
		object_map.remove(o.name);
		r.remove(o.name);
	}
	
	public void run(Subject s) throws IOException
	{
		System.out.println("Temp: " + s.TEMP);
		if (s.TEMP == 1) //Lyle should add 1 to byte if his TEMP is 1 after read.
			a = (a | (1 << num_bits));
		else
			a = (a | (0 << num_bits));
		num_bits--;
		System.out.println(a);
		if (num_bits == -1)
		{
			s.filethingy.write(new byte[]{a.byteValue()});
			num_bits = 6;
			a= 0;
			System.out.println();
		}
	}
	
	
	
}


