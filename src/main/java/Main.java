public class Main {

    public static void main(String[] args) {
        Byte[] bytes = Util.getInstance().txtToAscii(Util.path);

        Block[] blocks = Block.getBlocks(bytes);
        prueba3(blocks);



    }
    public static void prueba(Block[] blocks){
        Block first = blocks[0];
        AES aes = new AES();
        Block substitution = aes.blockSubstitution(first);
        System.out.println("Original");
        System.out.println(first);
        System.out.println("Substitution");
        System.out.println(substitution);
        Block shift = substitution.shiftRow();
        System.out.println("Shift");
        System.out.println(shift);
        Block mix = aes.mixColumn(shift);
        System.out.println(mix);

    }
    public static void prueba2(Block[] blocks){
        Block first = blocks[0];
        AES aes = new AES();
        System.out.println("Normal");
        System.out.println(first);
        Block firstRound= aes.Round(first);
        System.out.println("First Round");
        System.out.println(firstRound);
        Block original= aes.InversedRound(firstRound);
        System.out.println("Original");
        System.out.println(original);

    }
    public static void prueba3(Block[] blocks){
        Block first = blocks[0];
        AES aes = new AES();
        System.out.println("Normal");
        System.out.println(first);
        Block encryption = aes.encrypt(first);
        System.out.println("Encryption");
        System.out.println(encryption);
        Block decryption = aes.decrypt(encryption);
        System.out.println("Decryption");
        System.out.println(decryption);

    }


}
