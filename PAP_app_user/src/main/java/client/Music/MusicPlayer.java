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
    private boolean playing;
    private boolean terminated;
    private StreamStatusCallback streamStatusCb;

    public MusicPlayer(PipedOutputStream pipedOutput, AudioFormat fileFormat, StreamStatusCallback streamStatusCb) throws Exception
    {
        this.format = fileFormat;
        int frameSize = format.getFrameSize();
        bufferSize = frameSize*256; //amount of read bytes must be multiplication of frames/second
        this.pipedInputStream = new PipedInputStream(pipedOutput, bufferSize*4);
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
            while (pipedInputStream.available() < bufferSize*2)
            {
                Thread.sleep(100);
            }
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(format);
            sourceDataLine.start();

            byte[] buf = new byte[bufferSize];

            BufferedInputStream bis = new BufferedInputStream(pipedInputStream);
            
            boolean checkedIfStopped = false;

            while (true)
            {
                while (playing == false)
                {
                    if (terminated)
                    {
                        sourceDataLine.drain();
                        sourceDataLine.close();
                        pipedInputStream.close();
                        return;
                    }
                    Thread.sleep(300);
                }
                if(bis.read(buf = new byte[bufferSize], 0, buf.length) > 0)
                {
                    sourceDataLine.write(buf, 0, buf.length);
                    checkedIfStopped = false;
                }
                else
                {
                    if(!checkedIfStopped)
                    {
                        checkIfStreamPaused();
                        checkedIfStopped = true;
                    }
                    else
                    {
                        break;
                    }
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


