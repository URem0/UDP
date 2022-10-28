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

    public String printMSG(DatagramPacket msg){
        String word = new String(msg.getData()).trim();
        InetAddress address = msg.getAddress();
        int port = msg.getPort();
        System.out.println(word + " from " + address + " port:" + port);
        return word;
    }

    public void launch() throws IOException {
        DatagramSocket socket = new DatagramSocket(this.port);
        while (this.state.equals("listening")) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String word = printMSG(packet);
            if (word.equals("exit")){
                this.state = "close";
            }
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