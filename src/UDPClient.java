import java.io.Console;
import java.io.IOException;
import java.net.*;
/***
 * UDP Client Class connected to localhost, port 8080 by default.
 * Specify as arguments address port to connect to a UDP server on address:port
 *
 * @author Rémy UM, Osama RAIES HADJ BOUBAKER
 * @author  ENSEA RTS
 */
public class UDPClient {
    private static final int portCLIENT = 5490 ;
    private int portDST = 8080;
    private State state;
    private DatagramSocket socket;
    private InetAddress addressDST;
    private String dst = "localhost";

    public enum State {
        CLOSE,
        RUNNING
    }

    public void openMSG(){
        System.out.println("-------Open Client---------");
    }

    public void closeMSG(){
        System.out.println("-------Close Client---------");
    }

    private void setState(State state){
        this.state = state;
    }

    private State clientState(){
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

    /***
     * Reads Client message from the console
     * @return msg the client's msg
     */
    public String clientMSG(){
        System.out.println("Enter your message: ");
        Console c = System.console();
        String msg = c.readLine();
        return msg;
    }

    /***
     * Checks if the client wants to close connection
     * @param msg
     */
    public void checkCloseMSG(String msg){
        if (msg.equals("close")){
            setState(State.CLOSE);
        }
    }

    /***
     * Sends message through the socket to the server on addressDST:portDST
     * @param msg
     * @param socket
     * @param addressDST
     * @param portDST
     * @throws IOException
     */
    public void sendMSG(String msg, DatagramSocket socket, InetAddress addressDST, int portDST) throws IOException {
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, addressDST, portDST);
        socket.send(packet);
    }

    public void launch() throws Exception {
        openMSG();
        setState(State.RUNNING);

        while (clientState() == State.RUNNING) {
            String msg = clientMSG();
            sendMSG(msg, socket, addressDST, portDST);
            checkCloseMSG(msg);
        }

        socket.close();
        closeMSG();
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