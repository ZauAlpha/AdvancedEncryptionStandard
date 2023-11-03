public class Block {
    private Byte[][] bytes=new Byte[4][4];
    public Block(Byte[] bytes){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                this.bytes[i][j]=bytes[i*4+j];
            }
        }
    }
    public Block(Byte[][] bytes){
        this.bytes=bytes;
    }
    //A method that recives an array of bytes and returns a list of blocks
    public static Block[] getBlocks(Byte[] bytes){
        Block[] blocks=new Block[bytes.length/16];
        for(int i=0;i<blocks.length;i++){
            Byte[] blockBytes=new Byte[16];
            for(int j=0;j<16;j++){
                blockBytes[j]=bytes[i*16+j];
            }
            blocks[i]=new Block(blockBytes);
        }
        return blocks;
    }
    public Byte[] getBytes(){
        Byte[] bytes=new Byte[16];
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                bytes[i*4+j]=this.bytes[i][j];
            }
        }
        return bytes;
    }
    public Byte[][] getBytesMatrix(){
        return bytes;
    }
    //override toString method
    @Override
    public String toString(){
        String str="";
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                str+=bytes[i][j].toString()+" ";
            }
            str+="\n";
        }
        return str;
    }
    public Block shiftRow(){
        Byte[][] shiftedBytes=new Byte[4][4];
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                shiftedBytes[i][j]=bytes[i][(j+i)%4];
            }
        }
        return new Block(shiftedBytes);
    }
    public Block inverseShiftRow(){
        Byte[][] shiftedBytes=new Byte[4][4];
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                shiftedBytes[i][j]=bytes[i][(j-i+4)%4];
            }
        }
        return new Block(shiftedBytes);
    }
    //A method that returns a column of the block




}
