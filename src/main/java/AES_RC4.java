import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.Scanner;

public class AES_RC4 {
    public static String plaintext1 = "src/main/resources/Map.json";
    public static  String plaintext2= "src/main/resources/MapMOD.json";
    public static Byte[][] AESkey2 ={{new Byte(0x56), new Byte(0x10), new Byte(0x08), new Byte(0x23)},
            {new Byte(0x7e), new Byte(0xae), new Byte(0xf7), new Byte(0xcf)},
            {new Byte(0x15), new Byte(0xd2), new Byte(0x15), new Byte(0x4f)},
            {new Byte(0x16), new Byte(0xa6), new Byte(0x88), new Byte(0x3c)}} ;
    public static Byte[][] AESkey3 = {{new Byte(0x68), new Byte(0x28), new Byte(0xab), new Byte(0x09)},
            {new Byte(0x26), new Byte(0xae), new Byte(0xf7), new Byte(0xcf)},
            {new Byte(0x01), new Byte(0xd2), new Byte(0x15), new Byte(0x4f)},
            {new Byte(0x10), new Byte(0xa6), new Byte(0x88), new Byte(0x3c)}} ;
    public static Byte[][] AESkey4 = {{new Byte(0x2b), new Byte(0x28), new Byte(0xab), new Byte(0x25)},
            {new Byte(0x7e), new Byte(0xae), new Byte(0xf7), new Byte(0x01)},
            {new Byte(0x15), new Byte(0xd2), new Byte(0x15), new Byte(0x09)},
            {new Byte(0x16), new Byte(0xa6), new Byte(0x88), new Byte(0x28)}}   ;
    public static void main(String[] args) {
        //PERMITIR CAMBIAR DE LLAVE (true=permite ingresar nueva llave y comparar los dos plaintext)
        boolean changeKey=true;
        boolean changePlaintext=true;
        //RUTA DEL ARCHIVO


        try {
            Scanner scanner = new Scanner(System.in);
            int choice=0;

            Path filePath2 = Paths.get(plaintext2);
            Path filePath = Paths.get(plaintext1);
            while (true) {
                System.out.println("1. AES ONLY");
                System.out.println("2. RC4 ONLY");
                System.out.println("3. PROPOSED (AES-RC4)");
                System.out.println("4. Exit");
                System.out.print("Select an option: ");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            System.out.println("Has seleccionado la Opción 1.");
                            System.out.println("Key exchange");
                            Byte[] bytes = Util.getInstance().txtToAscii(plaintext1);
                            Block[] blocks = Block.getBlocks(bytes);
                            AES aes = new AES();
                            String cipher1 = aes.encryptText(blocks);
                            System.out.println("CIPHERTEXT SIZE: " + cipher1.length());
                            AES.setKey(AESkey2);
                            String cipher2 = aes.encryptText(blocks);
                            System.out.println("1st KEY CIPHERTEXT SIZE: " + cipher2.length());
                            calculateAE(cipher1.getBytes(), cipher2.getBytes(),cipher2.length()*8);
                            AES.setKey(AESkey3);
                            String cipher3 = aes.encryptText(blocks);
                            System.out.println("2nd KEY CIPHERTEXT SIZE: " + cipher3.length());
                            calculateAE(cipher1.getBytes(), cipher3.getBytes(),cipher3.length()*8);
                            AES.setKey(AESkey4);
                            String cipher4 = aes.encryptText(blocks);
                            System.out.println("3rd KEY CIPHERTEXT SIZE: " + cipher4.length());
                            calculateAE(cipher1.getBytes(), cipher4.getBytes(),cipher4.length()*8);
                            System.out.println("Plaintext exchange");
                            Byte[] bytes2 = Util.getInstance().txtToAscii(plaintext2);
                            Block[] blocks2 = Block.getBlocks(bytes2);
                            String cipher5 = aes.encryptText(blocks2);
                            System.out.println("MODIFIED PLAINTEXT CIPHERTEXT SIZE: " + cipher5.length());
                            calculateAE(cipher1.getBytes(), cipher5.getBytes(),cipher5.length()*8);


                            break;
                        case 2:
                            //Convertir los archivos en arreglo de bytes e imprimir su tamaño
                            byte[] data = Files.readAllBytes(filePath);
                            System.out.println("INITIAL DOC SIZE: "+data.length);
                            byte[] ciphertext1 =performRC4(data,"TempusFugit");

                            //+++++++++++++REALIZAR CAMBIO DE LLAVE
                            if(changeKey)
                            {
                                System.out.print("Enter modified Key: ");
                                String modKey = scanner.next();
                                byte[] ciphertext2= performRC4(data,modKey);
                                //calculate Avalanche Effect
                                calculateAE(ciphertext1, ciphertext2,data.length*8);
                            }
                            //+++++++++++REALIZAR CAMBIO DE PLAINTEXT
                            if(changePlaintext)
                            {
                                //Convertir los archivos en arreglo de bytes e imprimir su tamaño
                                byte[] data2 = Files.readAllBytes(filePath2);
                                System.out.println("INITIAL DOC SIZE: "+data2.length);
                                byte[] ciphertextP2 =performRC4(data2,"TempusFugit");
                                //calculate Avalanche Effect
                                calculateAE(ciphertext1, ciphertextP2,data.length*8);
                            }
                            break;
                        case 3:
                            //+++++++++AES SECTION
                            System.out.println("Has seleccionado la Opción 1.");
                            System.out.println("Key exchange");
                            Byte[] bytes_rc4 = Util.getInstance().txtToAscii(plaintext1);
                            Block[] blocks_rc4 = Block.getBlocks(bytes_rc4);
                            AES aes_rc4 = new AES();
                            String cipher1_rc4 = aes_rc4.encryptText(blocks_rc4);
                            System.out.println("CIPHERTEXT SIZE: " + cipher1_rc4.length());
                            AES.setKey(AESkey2);
                            String cipher2_rc4 = aes_rc4.encryptText(blocks_rc4);
                            System.out.println("1st KEY CIPHERTEXT SIZE: " + cipher2_rc4.length());


                            //+++++++++RC4 SECTION

                            byte[] ciphertextAES = cipher1_rc4.getBytes();
                            byte[] ciphertextRC4= performRC4(ciphertextAES,"ZempusFugip");


                                //++++++++++AES KEY

                                //++++++++++RC4 KEY

                                String modKeyRC4 = "TempusFugit";

                                byte[] ciphertextRC4_2= performRC4(cipher2_rc4.getBytes(),modKeyRC4);

                                //calculate Avalanche Effect
                                calculateAE(ciphertextRC4, ciphertextRC4_2,cipher2_rc4.getBytes().length*8);

                            //+++++++++++REALIZAR CAMBIO DE PLAINTEXT
                            if(changePlaintext){
                                //+++++++++AES SECTION


                                //+++++++++RC4 SECTION
                                /*
                                byte[] ciphertextRC4_2 =performRC4(AESciphertext,"TempusFugit");

                                //+++calculate Avalanche Effect
                                calculateAE(ciphertextRC4, ciphertextRC4_2,AESciphertext.length*8);
                                 */
                            }
                            break;
                        case 4:
                            System.out.println("Exit");
                            scanner.close();
                            System.exit(0);
                            break;

                        default:
                            System.out.println("Try again");
                    }
                } else {
                    System.out.println("Try again with a number");
                    scanner.next(); // Limpia la entrada incorrecta.
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void calculateAE(byte[] arr1, byte[] arr2, int totalBits) {
        int n=Math.max(arr1.length, arr2.length);
        int min = Math.min(arr1.length, arr2.length);
        int differences = 0;
        for (int i = 0; i < min; i++) {
            byte b1 = arr1[i];
            byte b2 = arr2[i];
            for (int j = 0; j < 8; j++) {
                //Compare bits in each byte position
                if ((b1 & (1 << j)) != (b2 & (1 << j))) {
                    differences++;
                }
            }
        }
        double AE = Math.abs((double) (differences)/ totalBits ) * 100.0;  // Cambiar la fórmula de similitud.
        DecimalFormat df = new DecimalFormat("#.###");
        System.out.println("Avalanche effect: " + df.format(AE) + " %");
    }

    public static byte[] performRC4(byte[] data, String key) throws UnsupportedEncodingException {
        // Convierte el arreglo de bytes en una cadena
        String dataString = new String(data, "UTF-8");

        // Cifra la cadena con el método Crypt
        StringBuffer input = new StringBuffer(dataString);
        StringBuffer k = new StringBuffer(key);
        System.out.println("Key for RC4: "+key);
        //RC4 ENCRYPTION
        Crypt(input, k);
        // Convierte la cadena cifrada de nuevo en un arreglo de bytes para tomar el tamaño de este
        byte[] encryptedData = input.toString().getBytes("UTF-8");
        // Imprime el arreglo de bytes cifrado
        System.out.println("CIPHERTEXT SIZE: " + encryptedData.length);

        // RC4 DECRYPT
        Crypt(input, k);
        byte[]decryptedData=input.toString().getBytes("UTF-8");
        // Imprime el arreglo de bytes cifrado
        System.out.println("PLAINTEXT SIZE (DECRYPTED): " + decryptedData.length);
        return encryptedData;
    }

    static void Crypt(StringBuffer inp,StringBuffer key)
    {
        int Sbox[];
        int Sbox2[];
        Sbox=new int[257];
        Sbox2=new int[257];
        int i, j, t, x;
        String OurUnSecuredKey = "StardustCrusaders";
        char temp , k;
        i = 0;
        j = 0;
        k = 0;
        t = 0;
        x = 0;
        temp = 0;

        //initialize sbox i
        for( i = 0; i < 256; i++)
        {
            Sbox[i] = i;
        }

        j = 0;
        if(key.length() >0)
        {
            for(i = 0; i < 256 ; i++)
            {
                if(j == key.length() )
                    j = 0;

                Sbox2[i] = key.charAt(j++);
            }
        }
        else
        {
            for(i = 0; i < 256 ; i++)
            {
                if(j == OurUnSecuredKey.length() +1)
                    j = 0;

                Sbox2[i] = OurUnSecuredKey.charAt(j++);
            }
        }

        j = 0 ;
        for(i = 0; i < 256; i++)
        {
            j = (j + Sbox[i] + Sbox2[i]) % 256;
            temp = (char)Sbox[i];
            Sbox[i] = Sbox[j];
            Sbox[j] = temp;
        }
        i = j = 0;
        for(x = 0; x < inp.length() ; x++)
        {
            //increment i
            i = (i + 1) % 256;
            //increment j
            j = (j + Sbox[i]) % 256;

            //Scramble SBox #1 further so encryption routine will
            //will repeat itself at great interval
            temp = (char)Sbox[i];
            Sbox[i] = Sbox[j] ;
            Sbox[j] = temp;

            //Get ready to create pseudo random byte for encryption key
            t = ( Sbox[i] + Sbox[j]) % 256 ;

            //get the random byte
            k = (char)Sbox[t];

            //xor with the data and done
            inp.setCharAt(x, (char)(inp.charAt(x) ^ k));
        }
    }
}

