import java.io.IOException;
import java.net.*;


public class UDPServer {
    private State state;
    public int port = 8080;
    byte[] buf;
    int bufSize = 1024;

    public UDPServer(int port){
        this.port = port;
    }

    public State ServerState(){
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
        System.out.println("-------Open Server---------");
        state = State.LISTENING;
        DatagramSocket socket = new DatagramSocket(this.port);
        while (state == State.LISTENING) {
            buf = new byte[bufSize];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String word = printMSG(packet);
            if (word.equals("exit")){
                state = State.CLOSE;
            }
        }
        System.out.println("-------Close Server---------");
        socket.close();
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