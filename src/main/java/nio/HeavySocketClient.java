
package nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HeavySocketClient {
	private static ExecutorService  tp=Executors.newCachedThreadPool();
	public static class EchoClient implements Runnable{
		public void run(){
	        Socket client = null;
	        PrintWriter writer = null;
	        BufferedReader reader = null;
	        try {
	            client = new Socket();
	            client.connect(new InetSocketAddress("localhost", 8000));
	            writer = new PrintWriter(client.getOutputStream(), true);

				System.out.println("请输入四则混合运算表达式，以分号结尾");
				Scanner scanner = new Scanner(System.in);

				String line = scanner.nextLine();
				writer.print(line);
				writer.println();
				writer.flush();

				reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				System.out.println("[from server]计算结果为： " + reader.readLine());

				run();

			} catch (UnknownHostException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
					if (writer != null)
					    writer.close();
					if (reader != null)
					    reader.close();
					if (client != null)
					    client.close();
				} catch (IOException e) {
				}
	        }
		}
	}
    public static void main(String[] args) throws IOException {
    	EchoClient ec=new EchoClient();
		tp.execute(ec);
    }
}
