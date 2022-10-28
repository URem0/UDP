import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;


public class UDPServer {

    public int port;
    public String state;
    private byte[] buf = new byte[256];


    public UDPServer(int port){
        this.port = port;
    }

    @Override
    public String toString(){
        return this.state;
    }

    public void launch() throws IOException {
        DatagramSocket socket = new DatagramSocket(this.port);
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        String word = new String(packet.getData());

        System.out.println(word);
    }

    public static void main(String[] args) throws IOException {
        int p = 8080;
        if (args.length != 0 ){
            p = Integer.parseInt(args[0]);
        }
        UDPServer server = new UDPServer(p);
        server.launch();
    }
}