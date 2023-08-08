package class24.myclass24;

public class T {

    public static class R{}

    public static void main(String[] args) {
        String s = new String();
        s += "abc";
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder.append("a");
        stringBuilder2.append("a");
        String s1 = "1" + stringBuilder;
        s += 1;
        s += true;
        System.out.println(s1);
        System.out.println(s);
        R r = new R();
        System.out.println(r+s);
        System.out.println(r);
    }
}
