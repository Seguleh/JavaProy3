
public class ServerUpgrade extends Thread {

	boolean sudone;
	int TS;
	
	public ServerUpgrade(int TS) 
	{
		this.TS = TS;
		sudone = false;
	}
	
	public void SUShutdown()
	{
		sudone=true;
	}

	public synchronized void run()
	{
		while(!sudone)
		{
			try {
				Thread.sleep(TS*1000);
				JavaProy3.S.Update();
			} catch (InterruptedException e) {
				System.out.println("Hurray java! - "+ e);
			}
		}
	}
}
