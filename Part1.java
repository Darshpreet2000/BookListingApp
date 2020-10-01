package DataTypes;

public class Part1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        //Part1
		byte b=10;
        short sh=20;
        int in=30;
        long ln=40;
        
        sh=b;
        //sh=in;
        //sh=ln;
        in=b;
        in=sh;
        //in=ln;
        ln=b;
        ln=sh;
        ln=in;
        
        //Part2
        b=10;
        b=127;
        b=(byte)128;
        System.out.println(b);
        
        //Part3
        in=1000000000;
        //in=10000000000;
        ln=10000000000L;
        System.out.println(ln);
        
        //Part4
        sh=32000;
        
        //Part5
        float f=5.5f;
        System.out.println(f);
        
        double db=6.5;
        
        //Part6
        in=(int)f;
        System.out.println(in);
        
        f=in;
        System.out.println(f);
        
        //Part7
        boolean bit=true;
        //bit=1;
        //bit=0;
        if(bit){
        	System.out.println("Hello");
        }else{
        	System.out.println("bye");
        }
        if(in>0){
        	System.out.println("greater");
        }else {
        	System.out.println("lesser or equal");
        }
        
	}

}
