import java.util.HashMap;
import java.util.Map;

public class Writer<K, V> implements Runnable {
	private SynchronizedMapUtil<K,V> map;

	Integer start;
	Integer end;
	int simulationCount;
	Map<K,V> data; 
	public Writer(SynchronizedMapUtil<K,V> map, int count,Integer start,Integer end) {
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
				V val=(V)((Integer)(start+10));
				map.add((K)start, val);
				start++;
				System.out.println("Write for key"+start);

			}
		} catch (InterruptedException exception) {
		}
	}


}