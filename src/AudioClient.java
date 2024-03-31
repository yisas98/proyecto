import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

public class AudioClient {
    public static void main(String[] args) {
        try {
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            Socket socket = new Socket("localhost", 5000);
            OutputStream outputStream = socket.getOutputStream();

            System.out.println("Conectado al servidor.");

            byte[] buffer = new byte[1024];
            int bytesRead;

            while (true) {
                bytesRead = microphone.read(buffer, 0, buffer.length);
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
