package client.Music;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.sound.sampled.AudioFormat;

import java.io.PipedOutputStream;
import java.net.SocketTimeoutException;
import client.Music.MusicPlayer;
import client.Music.MusicPlayer.StreamStatusCallback;

public class MusicClient implements Runnable
{
    public static final int PACKET_SIZE = 512;

    public static final String SERVER_ADDRESS = "144.91.114.89";

    private MusicPlayer player;
    private DatagramSocket socket;
    private InetAddress address;
    private int serverUdpPort;
    private boolean receive;
    private boolean connectionEstablished = false;
    private boolean playerPaused = false;
    private boolean active = false;

    private PipedOutputStream pipedOutStream;

    public MusicClient(AudioFormat fileFormat, int serverUdpPort, boolean startNow, StreamStatusCallback streamStatusCb)
    {
        try
        {
            socket = new DatagramSocket();
            pipedOutStream = new PipedOutputStream();
            player = new MusicPlayer(pipedOutStream, fileFormat, streamStatusCb);
            this.serverUdpPort = serverUdpPort;
            System.out.println(serverUdpPort);
            address = InetAddress.getByName(SERVER_ADDRESS);
            if(!startNow)
            {
                stopPlaying();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public synchronized void terminateReceiving()
    {
        receive = false;
        player.terminatePlayer();
    }

    public synchronized void resumePlaying()
    {
        playerPaused = false;
        player.resumePlaying();
    }

    public  synchronized void stopPlaying()
    {
        playerPaused = true;
        player.stopPlaying();
    }

    public synchronized boolean isPlaying()
    {
        return player.isPlaying();
    }

    public synchronized boolean isActive()
    {
        return active;
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
            byte[] buf = new byte[PACKET_SIZE];
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
        active = true;
        try
        {
            sendMessage("hello");
            socket.setSoTimeout(500);
            int terminationCount = 0;
            while (receive)
            {
                if(playerPaused)
                {
                    Thread.sleep(300);
                }
                try
                {
                    byte[] buf = new byte[PACKET_SIZE];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    connectionEstablished = true;
                    terminationCount = 0;
                    pipedOutStream.write(packet.getData());
                    sendMessage(".");
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
                    continue;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        active = false;
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