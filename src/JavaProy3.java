import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;

public class JavaProy3 {
	
	static int VS, K, TS, N, ID, V, TP, TN, LE;
	static Server S;
	static Cliente[] C;
	static ServerUpgrade SU;
		
	public static void ReadFile()
	{
			
		BufferedReader br = null;
		StringTokenizer Token;
		int i;
		
		try{

			br= new BufferedReader(new FileReader("bin/entrada.in"));

			Token= new StringTokenizer(br.readLine(), " ");
			VS=Integer.parseInt(Token.nextToken());
			K=Integer.parseInt(Token.nextToken());
			TS=Integer.parseInt(Token.nextToken());
			N=Integer.parseInt(Token.nextToken());
			
			S = new Server(VS, K, TS, N);
			S.start();
			
			C= new Cliente[N];			
			
			for(i=0;i<N;i++)
			{
				
				Token= new StringTokenizer(br.readLine(), " ");
				ID=Integer.parseInt(Token.nextToken());
				V=Integer.parseInt(Token.nextToken());
				TP=Integer.parseInt(Token.nextToken());
				TN=Integer.parseInt(Token.nextToken());
				LE=Integer.parseInt(Token.nextToken());
				
				System.out.println("Cliente: " + ID);
				System.out.println(ID +" "+ V +" "+ TP +" "+ TN +" "+ LE);
				C[i]= new Cliente(i,ID,V,TP,TN,LE);
				C[i].start();
			}
			
			SU = new ServerUpgrade(TS);
			SU.start();
			
		}catch (Exception e){
			System.out.println("Hurray java! - "+ e);
		}
	
	}
	
	public static void WriteFile(String s, String filePath)
	{
		
		//Check if file exists, if not then create one
		//Delete if it exists
		File filec = new File(filePath);
		
		try {
			
			if (filec.exists()) 
			{
				filec.delete();
				filec.createNewFile();
			}else
			{
				filec.createNewFile();
			}
			
		} catch (IOException e) {
			System.out.println("Hurray java! - "+ e);
		}
		Path file = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.WRITE))
        {
                writer.write(s, 0, s.length());
        } catch (Exception e) {
        	System.out.println("Hurray java! - "+ e);
        }
	}
		
	public static void main(String[] args) {
		
		ReadFile();
		
	}
}
