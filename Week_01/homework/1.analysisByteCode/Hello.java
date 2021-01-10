public class Hello {
    public static void main(String[] args) {
        byte b = 1;
        short s = (short) (b + 1);
        int i = s - 1;
        float f = i + 0.1f;
        long l = i * 2;
        double d = l / 0.1;
        boolean bl = true;
        char c = 'a';
        System.out.println(c % i);
        if (d > l) {
            System.out.println(d);
        }
        for (long i1 = 0; i1 < l; i1++) {
            System.out.println(i1);
        }
    }
}
