package client.Music;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.sound.sampled.AudioFormat;

import java.io.PipedOutputStream;
import java.net.SocketTimeoutException;

public class MusicClient extends Thread
{
    public static final String SERVER_ADDRESS = "144.91.114.89";

    private MusicPlayer player;
    private DatagramSocket socket;
    private InetAddress address = InetAddress.getByName(SERVER_ADDRESS);
    private int serverUdpPort;
    private boolean receive;
    boolean connectionEstablished = false;

    private PipedOutputStream pipedOutStream;

    public MusicClient(AudioFormat fileFormat, int serverUdpPort) throws Exception {
        super("MusicClient");
        this.socket = new DatagramSocket();
        pipedOutStream = new PipedOutputStream();
        this.player = new MusicPlayer(pipedOutStream, fileFormat);
        this.serverUdpPort = serverUdpPort;
    }

    public void terminateReceiving()
    {
        receive = false;
        player.terminatePlayer();
    }

    public void resumePlaying()
    {
        player.resumePlaying();
    }

    public void stopPlaying()
    {
        player.stopPlaying();
    }

    public boolean isPlaying()
    {
        return player.isPlaying();
    }

    public void run()
    {
        try
        {
            startPlayer();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message)
    {
        try
        {
            byte[] buf = new byte[1024];
            buf = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, serverUdpPort);
            socket.send(packet);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void receivePackets()
    {
        try
        {
            sendMessage("hello");
            socket.setSoTimeout(500);
            int terminationCount = 0;
            while (receive)
            {
                try
                {
                    byte[] buf = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    connectionEstablished = true;

                    pipedOutStream.write(packet.getData());
                }
                catch (SocketTimeoutException e)
                {
                    if (!connectionEstablished)
                    {
                        // retry sending introducing packet
                        sendMessage("hello");
                    }
                    else
                    {
                        terminationCount += 1;
                        if (terminationCount > 20)
                        {
                            terminateReceiving();
                            socket.close();
                        }
                    }
                    // if any packet has been received before, then it means that the stream is probably paused or terminated
                    // @TODO it should be handled properly depending on the stream state (paused/terminated)
                    // now it just pressumes that it is terminated if is waiting more that 20s
                    continue;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void startPlayer()
    {
        Thread thread = new Thread(player, "MusicPlayers");
        thread.start();
        System.out.println("receive=true");

        receive = true;
        receivePackets();
    }
}