import java.io.IOException;
import java.net.*;

/***
 * UDP Server Class on port 8080 by default.
 * Specify as argument a port number to set a new port
 *
 * @author Rémy UM, Osama RAIES HADJ BOUBAKER
 * @author  ENSEA RTS
 */
public class UDPServer {
    private State state;
    public int port = 8080;
    byte[] buf;
    int bufSize = 1024;

    public enum State {
        CLOSE,
        LISTENING
    }

    public void openMSG(){
        System.out.println("-------Open Server---------");
    }

    public void closeMSG(){
        System.out.println("-------Close Server---------");
    }

    private State serverState(){
        return this.state;
    }

    private void setState(State state){
        this.state = state;
    }

    private void setPort(int port){
        this.port = port;
    }

    /***
     * Receives the packet from a client
     * @param socket sokcet of the server
     * @return the client packet
     * @throws IOException
     */
    public DatagramPacket getPacket(DatagramSocket socket) throws IOException {
        buf = new byte[bufSize];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        return packet;
    }

    /***
     * Prints the client message into the console
     * @param msg
     */
    public void printMSG(DatagramPacket msg){
        String word = new String(msg.getData()).trim();
        String host = msg.getAddress().getHostAddress();
        int port = msg.getPort();
        System.out.println(word + " from " + host + " port:" + port);
    }

    /***
     * Converts a DatagramPacket into a String
     * @param msg DatagramPacket
     * @return
     */
    public String convertDatagramToString(DatagramPacket msg){
        return new String(msg.getData()).trim();
    }

    /***
     * Checks if the server receive a close message
     * @param msg
     */
    public void checkCloseMSG(String msg){
        if (msg.equals("close")){
            setState(State.CLOSE);
        }
    }

    public void launch() throws IOException {
        openMSG();
        setState(State.LISTENING);
        DatagramSocket socket = new DatagramSocket(this.port);

        while (serverState() == State.LISTENING) {
            DatagramPacket msg = getPacket(socket);
            printMSG(msg);
            String mess = convertDatagramToString(msg);
            checkCloseMSG(mess);
        }

        socket.close();
        closeMSG();
    }

    public static void main(String[] args) throws IOException {
        UDPServer server = new UDPServer();
        if (args.length != 0 ){
            server.setPort(Integer.parseInt(args[0]));
        }
        server.launch();
    }
}