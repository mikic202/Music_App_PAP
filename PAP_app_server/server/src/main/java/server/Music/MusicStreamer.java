package server.Music;

import java.util.ArrayList;
import java.util.Hashtable;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
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
    private boolean running = false;
    private boolean terminated = false;

    private int serverPort; //it is not used directly in the logic, it is just binded to this MusicPlayer
    private int songId; //it is not used directly in the logic, it is just binded to this MusicPlayer
    private boolean modifying = false;

    private int initiator;

    //user_id
    private List<Integer> listeners = new ArrayList<Integer>();
    //user_id, ip
    private Hashtable<Integer, InetAddress> ListenersIPs = new Hashtable<Integer, InetAddress>();
    //user_id, port
    private Hashtable<Integer, Integer> ListenersPorts = new Hashtable<Integer, Integer>();

    private AudioInputStream stream;
    private AudioFormat format;
    private int length;

    private InetAddress waitingIpAddress = null;
    private int waitingPort = 0;

    // port to start streaming from it
    public MusicStreamer(int port, int initiator, String path, int songId) {
        try
        {
            this.serverPort = port;
            this.initiator = initiator;
            socket = new DatagramSocket(null);
            socket.bind(new InetSocketAddress(port));
            socket.setSendBufferSize(buffer_size);

            this.stream = AudioSystem.getAudioInputStream(new File(path));
            
            this.format = stream.getFormat();
            this.length = (int)(stream.getFrameLength() * format.getFrameSize());
            this.songId = songId;

            System.out.println(port);
        }
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }

    public synchronized int getPort()
    {
        return serverPort;
    }

    public synchronized int getInitiator()
    {
        return initiator;
    }

    public synchronized boolean pauseStream(int userId)
    {
        if(userId != initiator)
        {
            return false;
        }
        running = false;
        return true;
    }

    public synchronized boolean terminateStream(int userId)
    {
        if (userId != initiator)
        {
            return false;
        }
        pauseStream(userId);
        terminated = true;
        return true;
    }

    public synchronized boolean resumeStream(int userId)
    {
        if (userId != initiator)
        {
            return false;
        }
        running = true;
        return true;
    }

    public synchronized int addListenerToCreatedStream(int userId)
    {
        if(listeners.contains(userId))
        {
            return 0;
        }

        int rtn = 0;
        if(initiateNewConnection(false))
        {
            modifying = true;
            listeners.add(userId);
            ListenersIPs.put(userId, waitingIpAddress);
            ListenersPorts.put(userId, waitingPort);
            waitingIpAddress = null;
            waitingPort = 0;
            modifying = false;
            rtn = serverPort;
        }

        return rtn;
    }
    
    public synchronized boolean removeListenerFromCreatedStream(int userId)
    {
        if(userId == initiator)
        {
            terminateStream(userId);
            return true;
        }

        modifying = true;
        if(listeners.contains(userId) == false)
        {
            return false;
        }
        listeners.remove(userId);
        ListenersIPs.remove(userId);
        ListenersPorts.remove(userId);
        modifying = false;
        return true;
    }

    public synchronized boolean checkIfPaused()
    {
        return running;
    }

    public synchronized int getLength()
    {
        return length;
    }

    public synchronized AudioFormat getFormat()
    {
        return format;
    }

    public synchronized int getPlayingSongId()
    {
        return songId;
    }

    public void run()
    {
        initiateNewConnection(true);
        socket.close();
    }

    private void startStreaming(InetAddress address, int port)
    {
        try
        {   
            byte[] buffer;
            DataInputStream in = new DataInputStream(stream);

            while ((in.read(buffer = new byte[buffer_size], 0, buffer.length)) > 0)
            {
                sendPacketToListeners(buffer);
                sleep(5);
                while(running == false)
                {
                    sleep(100);
                    if(terminated)
                    {
                        in.close();
                        return;
                    }
                }
            }
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPacketToListeners(byte[] buffer) throws Exception
    {
        DatagramPacket dgp;
        while (modifying)
        {
            sleep(5);
        }
        for (int user : listeners)
        {
            dgp = new DatagramPacket (buffer, buffer.length, ListenersIPs.get(user), ListenersPorts.get(user));
            socket.send(dgp);
        }
    }

    private boolean initiateNewConnection(boolean initiatorConnection)
    {
        try
        {
            byte[] messageBuffer = new byte[buffer_size];
            DatagramPacket packet = new DatagramPacket(messageBuffer, messageBuffer.length);
            socket.setSoTimeout(10000);
            socket.receive(packet);
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String received = new String(packet.getData(), 0, packet.getLength());

            String addressString = address.toString();
            System.out.println("Client ip: " + addressString + "Client port: " + Integer.toString(port) + "Msg: " + received);

            if (initiatorConnection)
            {
                running = true;
                listeners.add(initiator);
                ListenersIPs.put(initiator, address);
                ListenersPorts.put(initiator, port);
                if (!received.isEmpty())
                {
                    startStreaming(address, port);
                }
                return true;
            }
            waitingIpAddress = address;
            waitingPort = port;
            return true;
        }
        catch (SocketTimeoutException e)
        {
            System.out.println(e);
            return false;
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}

