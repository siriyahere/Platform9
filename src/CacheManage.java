
public class CacheManage {

		  public static void main(String[] args) {
			 SynchronizedMapUtil util = new SynchronizedMapUtil(100);
		    
		    Runnable run1 = new Writer(util, 50,1,5);
		    Runnable run2 = new Reader(util, 50,1,2);
		    Runnable run3 = new Reader(util, 50,3,5);

		    Thread thread1 = new Thread(run1);
		    Thread thread2 = new Thread(run2);
		    Thread thread3 = new Thread(run3);

		    thread1.start();
		
		    thread2.start();
		    thread3.start();
		  }
		}

		

		
	