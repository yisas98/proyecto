import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

public class AudioServer {
    public static void main(String[] args) {
        try {
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);
            speakers.open(format);
            speakers.start();

            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Servidor escuchando en el puerto 5000...");

            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado.");

            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                speakers.write(buffer, 0, bytesRead);
            }

            socket.close();
            serverSocket.close();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}