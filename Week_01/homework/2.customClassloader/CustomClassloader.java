import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义类加载器
 *
 * @author zhao
 */
public class CustomClassloader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> hello = new CustomClassloader().findClass("Hello");
            Method method = hello.getDeclaredMethod("hello");
            method.invoke(hello.newInstance());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] loadXlass = this.loadXlass();
        if (loadXlass == null) {
            throw new ClassNotFoundException("未找到class");
        }
        return defineClass(name, loadXlass, 0, loadXlass.length);
    }

    private byte[] loadXlass() {
        File xlassFile = new File("./Hello.xlass");
        try (FileInputStream fis = new FileInputStream(xlassFile)) {
            if (xlassFile.length() > Integer.MAX_VALUE) {
                throw new RuntimeException("文件过大无法加载");
            }
            byte[] fileByte = new byte[(int) xlassFile.length()];
            int read = fis.read(fileByte);
            if (read < 0) {
                throw new RuntimeException("文件读取失败");
            }
            for (int i = 0; i < fileByte.length; i++) {
                fileByte[i] = (byte) (255 - fileByte[i]);
            }
            return fileByte;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}