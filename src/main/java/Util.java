import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Util {
    private static Util instance;
    static String path = "src/main/resources/plaintext.txt";


    private Util() {

    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }
    public String asciiToText(int[] asciiText) {
        // Convierte el texto a ASCII
        char[] chars = new char[asciiText.length];
        for (int i = 0; i < asciiText.length; i++) {
            chars[i] = (char) asciiText[i];
        }

        return new String(chars);
    }
    public Byte[] txtToAscii(String path) {
        // Abre el archivo
        FileReader fr = null;
        String text;
        try {
            fr = new FileReader(path);
            // Lee el texto del archivo
            BufferedReader br = new BufferedReader(fr);
            String line;
            text = "";
            while ((line = br.readLine()) != null) {
                text += line;
            }

            // Cierra el archivo
            br.close();
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ;

        // Convierte el texto a ASCII
        char[] chars = text.toCharArray();
        Byte[] asciiText = new Byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            asciiText[i] = new Byte((int) chars[i]);
        }

        return asciiText;
    }
}
