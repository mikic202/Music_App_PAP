package player;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class MusicPlayer implements Runnable{

    private int bufferSize;
    private int sufficientBytesToPlay;

    private PipedInputStream pipedInputStream;
    private AudioFormat format;
    private boolean playing;
    private boolean terminated;

    public MusicPlayer(PipedOutputStream pipedOutput, AudioFormat fileFormat) throws Exception
    {
        this.format = fileFormat;
        int frameSize = format.getFrameSize();
        bufferSize = frameSize*256; //amount of read bytes must be multiplication of frames/second
        this.pipedInputStream = new PipedInputStream(pipedOutput, bufferSize*4);
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
                java.lang.Thread.sleep(100);
            }
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(format);
            sourceDataLine.start();

            byte[] buf = new byte[bufferSize];


            while (pipedInputStream.read(buf = new byte[bufferSize], 0, buf.length) > 0)
            {
                sourceDataLine.write(buf, 0, buf.length);

                while (playing == false)
                {
                    if (terminated)
                    {
                        sourceDataLine.drain();
                        sourceDataLine.close();
                        pipedInputStream.close();
                        return;
                    }
                    java.lang.Thread.sleep(300);
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

    public void stopPlaying()
    {
        playing = false;
    }

    public void resumePlaying()
    {
        playing = true;
    }

    public boolean isPlaying()
    {
        return playing;
    }

    public void terminatePlayer()
    {
        playing = false;
        terminated = true;
    }
}
