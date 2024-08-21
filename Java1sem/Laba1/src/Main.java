import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        // Практическая работа №1 В4: кол-во значений функции, имеющих в младшем разряде
        // целой части цифру, большую 3 (каждый шаг вывод x, y)

        Scanner in = new Scanner(System.in);
        double x = 0, y = 0, L = 0, R = 0, H = 1;
        System.out.print("Enter Left and Right borders (by space): ");

        // ввод границ
        if (in.hasNextDouble())
            L = in.nextDouble();
        if (in.hasNextDouble())
            R = in.nextDouble();

        if (L > R)
        {
            double t = L;
            L = R;
            R = t;
        }

        // ввод шага
        do {
            System.out.print("Enter step value (!=0): ");
            H = in.nextDouble();
            if (H == 0)
                System.out.println("Step value cant be == 0!");
        } while (H == 0); // если шаг равен нулю, то заново спрашиваем пользователя

        int count = 0;
        for (x = L; x <= R; x += Math.abs(H))
        {
            y = x * Math.exp(x) + 2 * Math.sin(x) - Math.sqrt(Math.abs(Math.pow(x, 3) - Math.pow(x, 2)));
            System.out.println("x = " + x + "\t" + "y = " + y);
            if (Math.abs(Math.floor(y)) % 10 > 3)
                count++;
        }

        System.out.println("Answer: " + count);
    }
}
