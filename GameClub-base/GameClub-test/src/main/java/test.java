public class test {
    public static void main(String[] args) {
        String a = new String("1"); //创建新对象
        String b = new String("1");

        String c = "2";
        String d = "2"; //常量池中获取相同对象
        System.out.println( a==b); //false
        System.out.println( a.equals(b));  //true

        System.out.println( c==d); //true
        System.out.println( c.equals(d)); //true
    }
}
