import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public 	class SynchronizedMapUtil<K,V> {

	  Map<K,V> dataStore;
	  int capacity;
	  static AtomicInteger readCount;
	  static AtomicInteger writeCount; 
	  static Semaphore readLock = new Semaphore(1);
	  static Semaphore writeLock = new Semaphore(1);
	  @SuppressWarnings("rawtypes")
	HashMap<K,ArrayList<Reader>> readersList;
	  
	//  public final CountDownLatch latch = new CountDownLatch(1);
	  
	  @SuppressWarnings("rawtypes")
	public SynchronizedMapUtil(int capacity) {
		  
		  dataStore = Collections.synchronizedMap(new HashMap<K,V>(capacity));
		  readCount = new AtomicInteger(0);
		  writeCount = new AtomicInteger(0);
		  readersList=new HashMap<K,ArrayList<Reader>>();
	    
	  }

	  @SuppressWarnings("rawtypes")
	synchronized void add_to_read_list(Reader<K,V> reader,K key){
		  try {
			readLock.acquire();
		
		  if(readersList.containsKey(key))
			  readersList.get(key).add(reader);
		  else{
			  @SuppressWarnings("rawtypes")
			List<Reader> list=new ArrayList<Reader>();
			  list.add((Reader<K, V>)reader);
			  readersList.put(key, (ArrayList<Reader>) list);
		  }
		  
		  } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  finally{
			  readLock.release();
		  }
			  
	  }
	  
	  public synchronized V get(K start,Reader<K,V> reader) throws InterruptedException {
	  
		  System.out.println(writeCount);
		
		//  latch.await();
		  while(!writeCount.equals(0))
			  wait();
		  readLock.acquire();
          readCount.getAndIncrement();
          readLock.release();
          
          V ret = dataStore.get(start);
          readLock.acquire();
          this.add_to_read_list(reader, start);
          readLock.release();
          readCount.getAndDecrement();
          return ret;
	    //return ;
	  }

	  synchronized void notify_readers(K key,V val){
		  List<Reader> readers;
		  
		  if(readersList.containsKey(key))
			  readers=readersList.get(key);
		  else
			  return;
		  for(int reader=0;reader<readers.size();reader++)
			  	readers.get(reader).update_key(key,val);
	  }
	  
	  public synchronized void add(K key,V Val) throws InterruptedException {
		
		
		try{
		  writeLock.acquire();
          writeCount.getAndIncrement();
          if(dataStore.containsKey(key))
        	  	dataStore.replace(key, Val);
          else
        	  dataStore.put(key, Val);
          
          notify_readers(key,Val);
          
          writeCount.getAndDecrement();
          writeLock.release();
          

		}
		catch(InterruptedException e){
			/*TODO proper interruption Handling*/
		 e.printStackTrace();
		}
		finally{
			if(!writeCount.equals(0))
				writeCount.getAndIncrement();
	          writeLock.release();
	          notifyAll();
		}
		
	  }

	
}
