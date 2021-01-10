public class Hello {
    public static void main(String[] args) {
        byte b = 110;
        System.out.println(b);
        short s = (short) (b + 256);
        System.out.println(s);
        int i = s - 23;
        System.out.println(i);
        float f = i + 0.1f;
        System.out.println(f);
        long l = i * 15;
        System.out.println(l);
        double d = l / 0.1;
        System.out.println(d);
        boolean bl = true;
        System.out.println(bl);
        char c = 'a';
        System.out.println(c);
        System.out.println(c % i);
        if (d > l) {
            System.out.println(d);
        }
        for (long i1 = 0; i1 < l; i1++) {
            System.out.println(i1);
        }
    }
}
