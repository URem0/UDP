import java.io.IOException;
import java.net.*;


public class UDPServer {
    private State state;
    public int port = 8080;
    byte[] buf;
    int bufSize = 1024;

    public enum State {
        CLOSE,
        LISTENING
    }

    public void OpenMSG(){
        System.out.println("-------Open Server---------");
    }

    public void CloseMSG(){
        System.out.println("-------Close Server---------");
    }

    private State ServerState(){
        return this.state;
    }

    private void setState(State state){
        this.state = state;
    }

    private void setPort(int port){
        this.port = port;
    }

    public void printMSG(DatagramPacket msg){
        String word = new String(msg.getData()).trim();
        String host = msg.getAddress().getHostAddress();
        int port = msg.getPort();
        System.out.println(word + " from " + host + " port:" + port);
    }

    public void launch() throws IOException {
        setState(State.LISTENING);
        DatagramSocket socket = new DatagramSocket(this.port);
        while ( ServerState() == State.LISTENING) {
            buf = new byte[bufSize];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            printMSG(packet);
            String msg = new String(packet.getData()).trim();
            if (msg.equals("exit")){
                setState(State.CLOSE);
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        UDPServer server = new UDPServer();
        if (args.length != 0 ){
            server.setPort(Integer.parseInt(args[0]));
        }
        server.OpenMSG();
        server.launch();
        server.CloseMSG();
    }
}