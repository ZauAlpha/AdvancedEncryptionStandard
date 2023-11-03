public class Main {

    public static void main(String[] args) {
        Byte[] bytes = Util.getInstance().txtToAscii(Util.path);

        Block[] blocks = Block.getBlocks(bytes);
        prueba(blocks);



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


}
