import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;


import java.util.Arrays;

public class Byte {
    //Recuerda que el bit menos significativo es bit 0 y el más significativo es bit 7 (de izquierda a derecha)
    boolean[] binary = new boolean[8];

    public Byte(int num) {
        for (int i = 0; i < 8; i++) {
            binary[i] = ((num >> i) & 1) == 1;
        }
    }

    public Byte(double[] num) {
        int lenght = num.length;
        if (lenght > 8) {
            throw new RuntimeException("El número es demasiado grande");
        }
        for (int i = 0; i < lenght; i++) {
            if (num[i] != 0 && num[i] != 1) {
                throw new RuntimeException("El número no es binario");
            }
            binary[i] = num[i] == 1;
        }
        if(lenght<8){
            for(int i=lenght;i<8;i++){
                binary[i]=false;
            }
        }
    }


    public Byte XOR(Byte b) {
        Byte result = new Byte(0);
        for (int i = 0; i < 8; i++) {
            result.binary[i] = binary[i] ^ b.binary[i];
        }
        return result;
    }

    public int getDecimal() {
        int num = 0;
        for (int i = 0; i < 8; i++) {
            if (binary[i]) {
                num += Math.pow(2, i);
            }
        }
        return num;
    }

    public PolynomialFunction getPolynomial() {
        double[] coefficients = new double[8];
        for (int i = 0; i < 8; i++) {
            if (binary[i]) {
                coefficients[i] = 1;
            }
        }
        return new PolynomialFunction(coefficients);
    }

    public char getChar() {
        return (char) getDecimal();
    }

    @Override
    public String toString() {
        return getDecimal() + "";
    }
    public static Byte AESmultiplyBytes(Byte multiplying, Byte multiplier) {
        PolynomialFunction multiplicand = multiplying.getPolynomial();
        PolynomialFunction multiplierPolynomial = multiplier.getPolynomial();
        PolynomialFunction product = multiplicand.multiply(multiplierPolynomial);
        double[] bits = product.getCoefficients();
        for (int i = 0; i < bits.length; i++) {
            bits[i] = bits[i]%2;

        }
        product = new PolynomialFunction(bits);
        if (product.degree() >= 8) {
            product = simplify(product);
        }
         bits = product.getCoefficients();
        for (int i = 0; i < bits.length; i++) {
            bits[i] = bits[i]%2;

        }
        Byte bait= new Byte(bits);
        return new Byte(bits);
    }
    private static PolynomialFunction simplify(PolynomialFunction polynomial){
        double[] coefficients=polynomial.getCoefficients();
        PolynomialFunction temp = AES.irreduciblePolynomial;
        for(int i = 0 ; i < polynomial.degree()-7;i++){
            if(coefficients[i+8]==0){
                temp=temp.multiply(new PolynomialFunction(new double[]{0,1}));
                continue;
            }
            coefficients[i+8]=0;
            PolynomialFunction aux = new PolynomialFunction(coefficients);
            aux=aux.add(temp);
            coefficients=aux.getCoefficients();
            temp=temp.multiply(new PolynomialFunction(new double[]{0,1}));
        }
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i] = coefficients[i]%2;

        }
        return new PolynomialFunction(coefficients);
    }
    public String getHexadecimal(){
        return(intToHex(getDecimal()));
    }
    private  String intToHex(int numero) {

        // Crear un arreglo de caracteres para almacenar la cadena hexadecimal
        char[] hexadecimal = new char[8];

        // Iterar sobre el número entero, dividiéndolo por 16 en cada iteración
        for (int i = 7; i >= 0; i--) {

            // Obtener el residuo de la división
            int residuo = numero % 16;

            // Convertir el residuo a un carácter hexadecimal
            char caracter = (char) (residuo >= 10 ? residuo - 10 + 'a' : residuo + '0');

            // Almacenar el carácter en el arreglo hexadecimal
            hexadecimal[i] = caracter;

            // Dividir el número entero entre 16
            numero /= 16;
        }

        // Invertir el orden del arreglo hexadecimal
        char[] hexadecimalInvertido = new char[8];
        for (int i = 0; i < 8; i++) {
            hexadecimalInvertido[i] = hexadecimal[7 - i];
        }

        // Devolver la cadena hexadecimal
        return new String(hexadecimalInvertido);
    }

}
