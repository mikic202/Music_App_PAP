package client.Music;

import java.io.BufferedInputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.InputStream;
import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class MusicPlayer implements Runnable
{
    private int bufferSize;

    private PipedInputStream pipedInputStream;
    private AudioFormat format;
    private boolean playing = false;
    private boolean terminated = false;
    private StreamStatusCallback streamStatusCb;
    private int currentMinute = 0;
    private int currentSecond = 0;
    private int totalSeconds = 0;
    private int totalMinutes = 0;
    private int currentPacketCount = 0;


    public MusicPlayer(PipedOutputStream pipedOutput, AudioFormat fileFormat, StreamStatusCallback streamStatusCb, int lengthInBytes) throws Exception
    {
        this.format = fileFormat;
        int size = (int) (lengthInBytes/(format.getSampleRate()*format.getChannels()*(format.getSampleSizeInBits()/8)));

        totalSeconds = size % 60;
        totalMinutes = size / 60;

        int frameSize = format.getFrameSize();
        bufferSize = frameSize*128; //amount of read bytes must be multiplication of frames/second
        this.pipedInputStream = new PipedInputStream(pipedOutput, bufferSize*1024*128);
        this.streamStatusCb = streamStatusCb;
    }

    public void run()
    {
        this.playing = true;
        startPlayer();
    }

    public void startPlayer()
    {
        try
        {
            System.out.println("AAAAAAAAAAAAAAAAAA");

            while (pipedInputStream.available() < bufferSize*1024)
            {
                Thread.sleep(100);
            }
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(format);
            sourceDataLine.start();

            byte[] buf = new byte[bufferSize];
            System.out.println("BBBBBBB");

            while (terminated == false)
            {
                System.out.println("in loop");
                if (playing)
                {
                    if(pipedInputStream.available() > (bufferSize - 1))
                    {
                        if(pipedInputStream.read(buf = new byte[bufferSize], 0, buf.length) > 0)
                        {
                            System.out.println(buf);
                            this.currentPacketCount = byteArrayToInt(buf);
                            byte[] strippedBuf = Arrays.copyOfRange(buf, 0, buf.length - 4);
                            sourceDataLine.write(strippedBuf, 0, strippedBuf.length);
                            updateTime();
                        }
                    }
                    else
                    {
                        System.out.println("start check if paused");
                        checkIfStreamPaused();
                        System.out.println("check if paused");
                        Thread.sleep(500);
                    }
                }
                else
                {
                    System.out.println("skipping");
                    int toSkip = pipedInputStream.available() - bufferSize*32;
                    if(toSkip < 0)
                    {
                        toSkip = 0;
                    }
                    pipedInputStream.skip(toSkip);
                    Thread.sleep(1000);
                }
            }
            System.out.println("outside loop");
            playing = false;
            sourceDataLine.drain();
            sourceDataLine.close();
            pipedInputStream.close();
            System.out.println("All available data has been read");
        }
        catch (Exception e)
        {
            System.out.println("Exception");
            e.printStackTrace();
        }
    }

    public synchronized void stopPlaying()
    {
        playing = false;
    }

    public synchronized void resumePlaying()
    {
        playing = true;
    }

    public synchronized boolean isPlaying()
    {
        return playing;
    }

    public synchronized void terminatePlayer()
    {
        playing = false;
        terminated = true;
    }

    private void checkIfStreamPaused()
    {
        streamStatusCb.checkIfPaused();
    }

    interface StreamStatusCallback
    {
        void checkIfPaused();
    }

    private synchronized void updateTime()
    {

        int absSecond = (int) ((bufferSize - 4)*currentPacketCount/(format.getSampleRate()*format.getChannels()*(format.getSampleSizeInBits()/8)));

        if(absSecond >= 60 * (currentMinute + 1))
        {
            currentMinute += 1;
        }

        currentSecond = absSecond - (60*currentMinute);


        System.out.println(String.format(("%d:%d ||||| Total %d:%d"), currentMinute, currentSecond, totalMinutes, totalSeconds));
    }

    public static final int byteArrayToInt(byte[] bytes) {
        byte[] byteArrayInt = new byte[4];
        byteArrayInt[0] = bytes[bytes.length - 4];
        byteArrayInt[1] = bytes[bytes.length - 3];
        byteArrayInt[2] = bytes[bytes.length - 2];
        byteArrayInt[3] = bytes[bytes.length - 1];
        return ByteBuffer.wrap(byteArrayInt).getInt();
    }

    public synchronized ArrayList<Integer> getCurrentTime()
    {
        ArrayList<Integer> time = new ArrayList<Integer>();
        time.add(currentSecond);
        time.add(currentMinute);
        return time;
    }

    public synchronized ArrayList<Integer> getTotalTime()
    {
        ArrayList<Integer> time = new ArrayList<Integer>();
        time.add(totalSeconds);
        time.add(totalMinutes);
        return time;
    }

    public synchronized int getPercentageOfSongPlayed()
    {
        if(totalMinutes == 0 || totalSeconds == 0)
        {
            return 0;
        }
        return (int) (totalMinutes * 60 + totalSeconds)/(currentMinute*60 + currentSecond);
    }
}


