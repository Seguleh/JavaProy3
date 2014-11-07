public class Cliente extends Thread
{
	
	int ID, V, TP, TN, LE, NV, i1;
	boolean cdone;
	String errorMsg;
	
	public Cliente(int i1, int ID, int V, int TP, int TN, int LE) 
	{
		this.i1 = i1;
		this.ID = ID;
		this.V = V;
		this.TP = TP;
		this.TN = TN;
		this.LE = LE;
		cdone = false;
	}
	
	public void ClientShutdown()
	{
		cdone=true;
	}
	
	public void SendError()
	{
		switch(1 + (int)(Math.random()*((3-1) + 1))) //Random msg selection
		{
			case 1:
				errorMsg = "'La pagina no pudo ser cargada'";
				break;
			case 2:
				errorMsg = "'No hay conexion'";
				break;
			case 3:
				errorMsg = "'El navegador no responde'";
				break;
		}
		JavaProy3.S.ReceiveClient(i1,"T1",ID,errorMsg, LE, V);
	}
	
	public void RequestUpgrade()
	{
		JavaProy3.S.ReceiveClient(i1,"T2",ID,"'Solicito una actualizacion de version'", LE, V);
	}
	
	public void UpgradeVersion(int NV)
	{
		V = NV;
	}
	
	public synchronized void run()
	{
		 while(!cdone)
		 {
			 try {
				System.out.println("Client "+ID+" running...");
				Thread.sleep(TN*1000); //Surfing TN seconds
				SendError();//Send random error
				Thread.sleep(Math.abs(TP-TN)*1000); //Ask upgrade after TP-TN seconds, it takes into consideration the surf time.
				RequestUpgrade();//Ask for upgrade
			} catch (Exception e) {
				System.out.println("Hurray java!"+e);
			}
		 }
	}

}
