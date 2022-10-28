import java.io.IOException;
import java.net.*;


public class UDPServer {

    public int port;
    public String state;
    final byte[] buf = new byte[1024];

    public UDPServer(int port){
        this.port = port;
    }

    @Override
    public String toString(){
        return this.state;
    }

    public void launch() throws IOException {
        DatagramSocket socket = new DatagramSocket(this.port);

        while (this.state.equals("listening")) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String word = new String(packet.getData()).trim();
            InetAddress address = packet.getAddress();
            int port = packet.getPort();

            if (word.equals("exit")){
                this.state = "close";
            }
            System.out.println(word + " from " + address + " port :" + port);
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        int p = 8080;
        if (args.length != 0 ){
            p = Integer.parseInt(args[0]);
        }
        UDPServer server = new UDPServer(p);
        server.state = "listening";
        server.launch();
    }
}