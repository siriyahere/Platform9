import java.util.HashMap;
import java.util.Map;

public class Reader<K,V> implements Runnable {

	  private SynchronizedMapUtil<K,V> map;

	  Integer start;
	  Integer end;
	  int simulationCount;
	  Map<K,V> data; 
	  public Reader(SynchronizedMapUtil<K,V> map, int count,Integer start,Integer end) {
	    this.map = map;
	    data = new HashMap<K,V>(count);
	    this.start=start;
	    this.end=end;
	  }

	  public void update_key(K key,V val){
		  	data.replace(key, val);
		  	return;
	  }
	  public void run() {
	    try {
	    	while(!start.equals(end)){
	    		System.out.println("Read begins"+start);
	    		V v=map.get((K)start, this);
	    		data.put((K)start, v);
	    		System.out.println("Read end"+start);
	    		start++;
	    		
	      }
	    } catch (InterruptedException exception) {
	    }
	  }

	}
