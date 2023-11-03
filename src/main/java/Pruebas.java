import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;


public class Pruebas {
    public static void main(String[] args) {
       /* char[][] matrizPrueba = {{'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}};
        //notación [fila][columna]
        System.out.println(matrizPrueba[0][2]);

        */

        int[] plaintext = {0x87, 0xf2, 0x4d, 0x97, 0xec, 0x6e, 0x4c, 0x90, 0x4a, 0xc3, 0x46, 0xe7, 0x86, 0xd8, 0x95, 0xa6};
        Byte[] bytes = new Byte[16];
        for (int i = 0; i < 16; i++) {
            bytes[i] = new Byte(plaintext[i]);
        }
        Block block = new Block(bytes);
        Byte[][] bytesMatrix = block.getBytesMatrix();
        System.out.println("Original");
        for (int i = 0; i < 4; i++) {
            for(int j=0;j<4;j++){
                System.out.print(bytesMatrix[i][j]+" ");
            }
            System.out.println();
        }
        AES aes = new AES();
        Block mixColumn = aes.mixColumn(block);
        System.out.println("Mix Column");
        bytesMatrix = mixColumn.getBytesMatrix();
        for (int i = 0; i < 4; i++) {
            for(int j=0;j<4;j++){
                System.out.print(bytesMatrix[i][j]+" ");
            }
            System.out.println();
        }

    }
}