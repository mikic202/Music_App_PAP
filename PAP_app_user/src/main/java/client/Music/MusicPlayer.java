package client.Music;

import java.io.BufferedInputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class MusicPlayer implements Runnable
{
    private int bufferSize;

    private PipedInputStream pipedInputStream;
    private AudioFormat format;
    private boolean playing = false;
    private boolean terminated = false;
    private StreamStatusCallback streamStatusCb;

    public MusicPlayer(PipedOutputStream pipedOutput, AudioFormat fileFormat, StreamStatusCallback streamStatusCb) throws Exception
    {
        this.format = fileFormat;
        int frameSize = format.getFrameSize();
        bufferSize = frameSize*128; //amount of read bytes must be multiplication of frames/second
        this.pipedInputStream = new PipedInputStream(pipedOutput, bufferSize*1024*1024);
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
            BufferedInputStream bis = new BufferedInputStream(pipedInputStream);
            System.out.println("AAAAAAAAAAAAAAAAAA");

            while (pipedInputStream.available() < bufferSize*64)
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
                if (playing)
                {
                    if(bis.read(buf = new byte[bufferSize], 0, buf.length) > 0)
                    {
                        sourceDataLine.write(buf, 0, buf.length);
                    }
                    else
                    {
                        System.out.println("CCCCCCC");
                        checkIfStreamPaused();
                    }
                }
                else
                {
                    Thread.sleep(300);
                }
            }
            sourceDataLine.drain();
            sourceDataLine.close();
            pipedInputStream.close();
            System.out.println("All available data has been read");
        }
        catch (Exception e)
        {
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
}


