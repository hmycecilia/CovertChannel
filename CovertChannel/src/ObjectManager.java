import java.util.HashMap;


public class ObjectManager {
	public HashMap<String, Object> object_map = new HashMap<String, Object>();

	public void read(Subject s, Object o){	s.TEMP = o.current_value;	}

	public void write(Object o, int val){	o.current_value = val;	}
}


