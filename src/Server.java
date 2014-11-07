
public class Server extends Thread
{
	boolean sdone;
	int VS, K, TS, N, u, sumE,sumU;
	int[] logErrors, logUpgrades;
	String s, e, fileP, estP;
	
	public Server(int VS, int K, int TS, int N) 
	{
		this.VS = VS;
		this.K = K;
		this.TS = TS;
		this.N = N;
		sdone = false;
		logErrors = new int[N];
		logUpgrades = new int[N];
		sumE = sumU = u = 0;
		s = e = "";
		fileP = "bin/salida.out";
		estP = "bin/estadisticas.txt";
	}
	
	public void ServerShutdown()
	{
		sdone = true;
	}
	
	synchronized void Update()
	{
			if(K > 0)
			{
				K--;
			}
			u++;
			notifyAll();
	}
	
	synchronized void ReceiveClient(int i1,String type, int CID, String msg, int CLE, int CV)
	{	
		switch (type)
		{
			case "T1":	//Error arrived
				
				//Write to log what goes on
				e+="<T1,"+CID+","+msg+">\n";
				
				if(logErrors[i1] <= CLE) //Check if it can still get errors or force an upgrade
				{
					logErrors[i1] = logErrors[i1] + 1;					
				}else
				{
					//Client must ask for upgrade
					JavaProy3.C[i1].RequestUpgrade();
				}

				break;
				
			case "T2":	//Version upgrade call
				
				//Write to log what goes on
				e+="<T2,"+CID+","+msg+">\n";
				
				if(VS - CV != 0)
				{
				
					//Version upgrade done. Send new version to client = CV + (VS-CV)
					JavaProy3.C[i1].UpgradeVersion(CV + (VS-CV));
					logUpgrades[i1] = logUpgrades[i1] + 1;
					
					//Write to log what goes on
					e+="<T3,"+CID+",'Se realizo una actualizacion de version'>\n";						

				}
				break;
				
		}
		notifyAll();
	}
	
	public synchronized void run()
	{
		while(!sdone)
		{
				try 
				{
					
					System.out.println("Server running...");
					wait();
					
					if (K == 0)
					{
						//Write to file 'salida.out'
						for(int i=0; i<N; i++)
						{
							s+= (i+1)+" : Numero de errores = "+logErrors[i]+" : Numero de actualizaciones = "+logUpgrades[i]+"\n";
						}
						for(int i : logErrors){sumE += i;}
						s+= "\n\nNumero total de errores = "+sumE+"\n";
						for(int i : logUpgrades){sumU += i;}
						s+= "Numero total de actualizaciones = "+sumU;
						JavaProy3.WriteFile(s,fileP);
						
						//Write to log file 'estadisticas.txt'
						JavaProy3.WriteFile(e,estP);
						
						//Shutdown server...
						JavaProy3.SU.SUShutdown();
						ServerShutdown();
						
						//Shutdown clients...
						for(int i=0; i<N; i++){JavaProy3.C[i].ClientShutdown();}
						
						System.out.println("Clients shutting down...");
						System.out.println("Server shutting down...");
					}
				} catch (InterruptedException e) 
				{
					System.out.println("Hurray java! - "+ e);
				}
		}
	}

}
