package server;

import java.util.ArrayList;
import java.util.Hashtable;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.io.DataInputStream;
import java.io.File;

import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;



// Broadcast publisher
class MusicStreamer extends Thread{

    private int buffer_size = 1024;

    private DatagramSocket socket;
    private boolean running;
    private byte[] receiving_buffer = new byte[buffer_size];
    private int server_port;
    private boolean adding = false;

    private String path = "/home/pap/music/file_example_WAV_10MG.wav";

    private int initiator;

    //user_id
    private List<Integer> listeners = new ArrayList<Integer>();
    //user_id, ip
    private Hashtable<Integer, InetAddress> ListenersIPs = new Hashtable<Integer, InetAddress>();
    //user_id, port
    private Hashtable<Integer, Integer> ListenersPorts = new Hashtable<Integer, Integer>();

    private AudioFormat format;
    private int length;

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

    public void run() 
    {
        try
        {
            DatagramPacket packet = new DatagramPacket(receiving_buffer, receiving_buffer.length);
            socket.receive(packet);
            InetAddress address = packet.getAddress();
            String addressString = address.toString();
            int port = packet.getPort();
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Client ip: " + addressString + "Client port: " + Integer.toString(port) + "Msg: " + received);
            listeners.add(11);
            ListenersIPs.put(11, address);
            ListenersPorts.put(11, port);
            if (!received.isEmpty()) 
            {
                startStreaming(address, port);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        socket.close();
    }
    

    public boolean getRunningState()
    {
        return running;
    }

    public int getLength()
    {
        return length;
    }


    public AudioFormat getFormat()
    {
        return format;
    }

    private void startStreaming(InetAddress address, int port)
    {
        try
        {

            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(path));
            
            format = stream.getFormat();
            length = (int)(stream.getFrameLength() * format.getFrameSize());
            

            byte[] buffer;
            DataInputStream in = new DataInputStream(stream);


            while ((in.read(buffer = new byte[buffer_size], 0, buffer.length)) > 0)
            {
                sendPacketToListeners(buffer);
                sleep(5);
            }
            in.close();
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

    public void addListenerToRunningStream(int userId, InetAddress ipAddress, int port)
    {
        adding = true;
        listeners.add(userId);
        ListenersIPs.put(userId, ipAddress);
        ListenersPorts.put(userId, port);
        adding = false;
    }

    private void sendPacketToListeners(byte[] buffer) throws Exception
    {
        DatagramPacket dgp;
        while (adding)
        {
            sleep(5);
        }
        for (int user : listeners)
        {
            dgp = new DatagramPacket (buffer, buffer.length, ListenersIPs.get(user), ListenersPorts.get(user));
            socket.send(dgp);
        }
    }

}

