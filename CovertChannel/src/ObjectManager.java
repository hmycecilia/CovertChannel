import java.util.HashMap;


public class ObjectManager {
	public HashMap<String, Object> object_map = new HashMap<String, Object>();

	public void read(Subject s, Object o){	s.TEMP = o.current_value;	}

	public void write(Object o, int val){	o.current_value = val;	}
	
	public void create(String o, int sl)
	{
		Object obj = new Object(o, sl);
	};
	
	public void destroy(Subject s, Object o){};
	
	public void run(Subject s){};
	
	
	
}


