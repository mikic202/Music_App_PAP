package server.Music;

import java.util.ArrayList;
import java.util.Hashtable;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.time.Duration;
import java.time.Instant;
import java.io.DataInputStream;
import java.io.File;

import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

// Broadcast publisher
class MusicStreamer extends Thread {

    private int PACKET_SIZE = 512;

    private DatagramSocket socket;
    private boolean running = false;
    private boolean terminated = false;

    private int serverPort; // it is not used directly in the logic, it is just binded to this MusicPlayer
    private int songId; // it is not used directly in the logic, it is just binded to this MusicPlayer

    private int initiator;

    // user_id
    private List<Integer> listeners = new ArrayList<Integer>();
    // user_id, ip
    private Hashtable<Integer, InetAddress> ListenersIPs = new Hashtable<Integer, InetAddress>();
    // user_id, port
    private Hashtable<Integer, Integer> ListenersPorts = new Hashtable<Integer, Integer>();

    private AudioInputStream stream;
    private AudioFormat format;
    private int length;

    private InetAddress waitingIpAddress = null;
    private int waitingPort = 0;

    // port to start streaming from it
    public MusicStreamer(int port, int initiator, String path, int songId) {
        try {
            this.serverPort = port;
            this.initiator = initiator;
            socket = new DatagramSocket(null);
            socket.bind(new InetSocketAddress(port));
            socket.setSendBufferSize(PACKET_SIZE);

            this.stream = AudioSystem.getAudioInputStream(new File(path));

            this.format = stream.getFormat();
            this.length = (int) (stream.getFrameLength() * format.getFrameSize());
            this.songId = songId;

            System.out.println(port);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public synchronized int getPort() {
        return serverPort;
    }

    public synchronized int getInitiator() {
        return initiator;
    }

    public synchronized boolean pauseStream(int userId) {
        if (userId != initiator) {
            return false;
        }
        running = false;
        return true;
    }

    public synchronized boolean terminateStream(int userId) {
        if (userId != initiator) {
            return false;
        }
        pauseStream(userId);
        terminated = true;
        return true;
    }

    public synchronized boolean resumeStream(int userId) {
        if (userId != initiator) {
            return false;
        }
        running = true;
        return true;
    }

    public synchronized int addListenerToCreatedStream(int userId) {
        if (listeners.contains(userId)) {
            return 0;
        }

        int rtn = 0;
        if (initiateNewConnection(false)) {
            listeners.add(userId);
            ListenersIPs.put(userId, waitingIpAddress);
            ListenersPorts.put(userId, waitingPort);
            waitingIpAddress = null;
            waitingPort = 0;
            rtn = serverPort;
        }

        return rtn;
    }

    public synchronized boolean removeListenerFromCreatedStream(int userId) {
        if (userId == initiator) {
            terminateStream(userId);
            return true;
        }
        if (listeners.contains(userId) == false) {
            return false;
        }
        listeners.remove(userId);
        ListenersIPs.remove(userId);
        ListenersPorts.remove(userId);
        return true;
    }

    public synchronized boolean checkIfPaused() {
        return running;
    }

    public synchronized int getLength() {
        return length;
    }

    public synchronized AudioFormat getFormat() {
        return format;
    }

    public synchronized int getPlayingSongId() {
        return songId;
    }

    public synchronized boolean checkIfSupportedEncoding()
    {
        int sampleSize = format.getSampleSizeInBits();
        String encodingStr = format.getEncoding().toString();
        if(sampleSize == 24 || (!encodingStr.equals("PCM_SIGNED") && !encodingStr.equals("PCM_UNSIGNED")))
        {
            System.out.println(String.format(("Unsupported encoding! sampleSize: %d, str: " + encodingStr), sampleSize));
            return false;
        }
        return true;
    }

    public void run() {
        initiateNewConnection(true);
        socket.close();
        running = false;
        terminated = true;
    }

    private void startStreaming(InetAddress address, int port) {
        try {
            byte[] buffer;
            DataInputStream in = new DataInputStream(stream);
            int count = 0;

            while ((in.read(buffer = new byte[PACKET_SIZE], 0, buffer.length)) > 0) {
                Instant start = Instant.now();
                System.out.println(count);
                count += 1;
                sendPacketToListeners(buffer);
                Instant finish = Instant.now();
                long time = Duration.between(start, finish).toNanos();
                if (time < 3000000){
                    time = 3000000 - time;
                    try
                    {
                        Thread.sleep(time/1000000);
                    }
                    catch (InterruptedException e){}
                }
                while (running == false) {
                    Thread.sleep(100);
                    if (terminated) {
                        in.close();
                        return;
                    }
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPacketToListeners(byte[] buffer) throws Exception {
        DatagramPacket dgp;

        for (int user : listeners) {
            dgp = new DatagramPacket(buffer, buffer.length, ListenersIPs.get(user), ListenersPorts.get(user));
            socket.send(dgp);
        }
    }

    private boolean initiateNewConnection(boolean initiatorConnection) {
        try {
            byte[] messageBuffer = new byte[PACKET_SIZE];
            DatagramPacket packet = new DatagramPacket(messageBuffer, messageBuffer.length);
            socket.setSoTimeout(10000);
            socket.receive(packet);
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String received = new String(packet.getData(), 0, packet.getLength());

            String addressString = address.toString();
            System.out.println(
                    "Client ip: " + addressString + "Client port: " + Integer.toString(port) + "Msg: " + received);

            if (initiatorConnection) {
                running = true;
                listeners.add(initiator);
                ListenersIPs.put(initiator, address);
                ListenersPorts.put(initiator, port);
                if (!received.isEmpty()) {
                    startStreaming(address, port);
                }
                return true;
            }
            waitingIpAddress = address;
            waitingPort = port;
            return true;
        } catch (SocketTimeoutException e) {
            System.out.println(e);
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
