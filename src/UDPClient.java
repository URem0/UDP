import java.io.Console;
import java.net.*;

public class UDPClient {
    private static int portCLIENT = 5490 ;
    private int portDST;
    private String state = "open";
    private DatagramSocket socket;
    private InetAddress addressDST;


    public UDPClient(String dst, int portDST) throws Exception {
        this.portDST = portDST;
        this.socket = new DatagramSocket(portCLIENT);
        this.addressDST = InetAddress.getByName(dst);
    }

    public void launch() throws Exception {
        while (state.equals("open")) {
            System.out.println("Enter your message: ");
            Console c = System.console();
            String msg = c.readLine();

            if (msg.equals("exit")){
                state = "close";
            }

            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), addressDST ,portDST);
            socket.send(packet);
        }
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        int p = 8080;
        String dst = "localhost";

        if (args.length != 0 ){
            dst = args[0];
            p = Integer.parseInt(args[1]);
        }

        UDPClient client = new UDPClient(dst, p);
        client.launch();
    }
}