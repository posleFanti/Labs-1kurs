import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter("test.txt", true)) {
            Scanner in = new Scanner(System.in);
            String text = in.next();
            fw.write(text);
            fw.append('\n');
            fw.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"))) {
            //...
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (FileReader fr = new FileReader("test.txt")) {
            /*int c;
            while ((c = fr.read()) != -1) {
                System.out.print((char) c);
            }*/
            char[] buff = new char[256];
            int c;
            while ((c = fr.read(buff)) > 0) {
                if (c < 256)
                    buff = Arrays.copyOf(buff, c);
                System.out.println(buff);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}