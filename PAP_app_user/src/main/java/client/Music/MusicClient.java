package player;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import java.io.PipedOutputStream;
import java.net.SocketTimeoutException;

public class MusicClient extends Thread{

    private MusicPlayer player;
    private DatagramSocket socket;
    private InetAddress address = InetAddress.getByName("144.91.114.89");
    private boolean receive;

    private PipedOutputStream pipedOutStream;

    public MusicClient(AudioFormat fileFormat) throws Exception {
        super("MusicClient");
        this.socket = new DatagramSocket();
        pipedOutStream = new PipedOutputStream();
        this.player = new MusicPlayer(pipedOutStream, fileFormat);
/* 
        String path = "C:\\Users\\jakub\\Desktop\\pap se\\server\\file_example_WAV_10MG.wav";
        String javaPath = path.replace("\\", "/");
        AudioInputStream in =  AudioSystem.getAudioInputStream(new File(javaPath));
        format = in.getFormat();
*/
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

    public void terminateStream()
    {
        receive = false;
        synchronized(player)
        {
            player.terminatePlayer();
        }
    }

    public void resumePlaying()
    {
        synchronized(player)
        {
            player.resumePlaying();
        }
    }

    public void stopPlaying()
    {
        synchronized(player)
        {
            player.stopPlaying();
        }
    }

    public boolean isPlaying()
    {
        synchronized(player)
        {
            return player.isPlaying();
        }
    }


    private void sendMessage(String message) throws Exception {
        byte[] buf = new byte[1024];
        buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 60001);
        socket.send(packet);
    }

    private void receivePackets() throws Exception
    {
        sendMessage("hello");
        socket.setSoTimeout(500);

        boolean anyReceived = false;

        while (receive)
        {
            try{
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                anyReceived = true;

                pipedOutStream.write(packet.getData());
            }
            catch (SocketTimeoutException e)
            {
                if(anyReceived)
                {
                    System.out.println("No more packets received");
                    return;
                }
                System.out.println("timeout exception");
                sendMessage("hello");
                continue;
            }

        }
    }

    private void startPlayer() throws Exception
    {
        Thread thread = new Thread(player, "MusicPlayers");
        thread.start();
        System.out.println("receive=true");

        receive = true;
        receivePackets();
    }

}