import java.io.Console;
import java.net.*;

public class UDPClient {
    private static int portCLIENT = 5490 ;
    private int portDST = 8080;
    private State state;
    private DatagramSocket socket;
    private InetAddress addressDST;
    private String dst = "localhost";

    public enum State {
        CLOSE,
        RUNNING
    }

    private void setState(State state){
        this.state = state;
    }

    private State ClientState(){
        return this.state;
    }

    private void setDST(String dst){
        this.dst = dst;
    }

    private void setPortDST(int portDST){
        this.portDST = portDST;
    }

    public UDPClient() throws Exception {
        this.socket = new DatagramSocket(portCLIENT);
        this.addressDST = InetAddress.getByName(this.dst);
    }

    public void launch() throws Exception {
        setState(State.RUNNING);
        System.out.println("CUT ");
        while (ClientState() == State.RUNNING) {
            System.out.println("Enter your message: ");
            Console c = System.console();
            String msg = c.readLine();

            if (msg.equals("exit")){
                setState(State.CLOSE);
            }

            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, addressDST, portDST);
            socket.send(packet);
        }
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        UDPClient client = new UDPClient();

        if (args.length != 0 ){
            client.setDST(args[0]);
            client.setPortDST(Integer.parseInt(args[1]));
        }

        client.launch();
    }
}