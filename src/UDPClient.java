import java.io.Console;
import java.io.Reader;

public class UDPClient {
    public int port;
    final byte[] buf = new byte[1024];

    public void message(){
        System.out.println("Enter your name: ");
        Console c = System.console();

        String n=c.readLine();
        System.out.println("Welcome "+n);
    }



    public static void main(String[] args){
        UDPClient client = new UDPClient();
        client.message();

    }
}
