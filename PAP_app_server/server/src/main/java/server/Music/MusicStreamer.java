package Music;

import java.util.Hashtable;

import java.io.BufferedInputStream;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import java.util.List;



// Broadcast publisher
class MusicStreamer extends Thread{

    private int buffer_size = 4096;

    private DatagramSocket socket;
    private boolean running;
    private byte[] buffer = new byte[buffer_size];
    private int server_port;

    private String path;

    private int initiator;

    //user_id
    private List<Integer> listeners;
    //user_id, ip
    private Hashtable<Integer, InetAddress> ListenersIPs;
    //user_id, port
    private Hashtable<Integer, Integer> ListenersPorts;

    // port to start streaming from it
    public MusicStreamer(int port, int initiator) {
        try
        {
            this.server_port = port;
            this.initiator = initiator;
            socket = new DatagramSocket(null);
            socket.bind(new InetSocketAddress(port));
            socket.setSendBufferSize(buffer_size);
        }
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }

    public void terminateStream()
    {
        running = false;
    }

    public void runStream() {

        running = true;

        while (running) {
            try
            {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                String addressString = address.toString();
                int port = packet.getPort();
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Client ip: " + addressString + "Client port: " + Integer.toString(port) + "Msg: " + received);
                if (!received.isEmpty()) 
                {
                    startStreaming(address, port);
                    continue;
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }
            socket.close();
        }
    }

    public boolean getRunningState()
    {
        return running;
    }

    private void startStreaming(InetAddress address, int port)
    {
        try
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
            int read;
            while ((read = in.read(buffer)) > 0)
            {
                out.write(buffer, 0, read);
                sendPacketToListeners();
            }
            out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean stopStream(int user_id)
    {
        //todo
        return false;
    }

    private void sendPacketToListeners()
    {
        DatagramPacket dgp;

        for (int user : listeners)
        {
            dgp = new DatagramPacket (buffer, buffer.length, ListenersIPs.get(user), ListenersPorts.get(user));
            socket.send(dgp);
        }

    }

    private void addListenerToRunningStream()
    {
        //todo
    }
    
}

