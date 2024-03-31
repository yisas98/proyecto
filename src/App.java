import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class App {
    public static void main(String[] args) {
        try {
            // Configurar la captura de audio desde el micrófono
            AudioFormat formato = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, formato);
            TargetDataLine microfono = (TargetDataLine) AudioSystem.getLine(info);
            microfono.open(formato);
            microfono.start();

            // Configurar la reproducción de audio a través de los altavoces
            DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, formato);
            SourceDataLine altavoces = (SourceDataLine) AudioSystem.getLine(sourceInfo);
            altavoces.open(formato);
            altavoces.start();

            // Definir el tamaño del buffer para la captura y reproducción de audio
            byte[] buffer = new byte[1024];

            // Leer datos del micrófono y escribirlos en los altavoces
            while (true) {
                int bytesRead = microfono.read(buffer, 0, buffer.length);
                altavoces.write(buffer, 0, bytesRead);
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
