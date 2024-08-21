import java.util.Scanner;

// Практическая работа №2
// В4 Найти значение наибольшего элемента
//    и его порядковый номер среди всех элементов, имеющих в своем составе цифру 3.
// C4 В последовательности целых чисел найти максимальное количество чисел, идущих подряд,
//    которые обладают свойством Q, и максимальное количество чисел, идущих подряд,
//    которые не обладают свойством Q. Свойство Q задается в варианте.
//    Программа должна содержать логическую функцию, проверяющую,
//    обладает ли заданное число свойством Q.
//    Q: число является полным квадратом некоторого натурального числа.

public class Main
{
    public static boolean Check(int number)
    {
        int num = Math.abs(number);
        while (num > 0)
        {
            if (num % 10 == 3)
                return true;
            num /= 10;
        }
        return false;
    }

    public static void main(String[] args)
    {
        int num, count = 0, max_index = 1, maxim = Integer.MIN_VALUE;
        int max_Q = 0, max_not_Q = 0;
        int count_Q = 0, count_not_Q = 0;
        int i = 1;
        Scanner in = new Scanner(System.in);

        do {
            System.out.print("Enter num (0 for stop): ");
            num = in.nextInt();

            if (num == 0) // выход если 0
                break;

            // B4 code
            if (Check(num))
            {
                if(num >= maxim)
                {
                    maxim = num;
                    max_index = i;
                }
                i++;
            }

            // C4 code
            // Проверка на полный квадрат
            boolean Q = Math.sqrt(Math.abs(num)) == Math.floor(Math.sqrt(Math.abs(num)));
            if (Q)
            {
                count_not_Q = 0;
                count_Q++;
                if (count_Q > max_Q) // максимум из count_Q, max_Q
                    max_Q = count_Q;
            }
            else
            {
                count_Q = 0;
                count_not_Q++;
                if (count_not_Q > max_not_Q) // максимум из count_not_Q, max_not_Q
                    max_not_Q = count_not_Q;
            }
        } while (num != 0);

        System.out.println("-----B4 Answer-----");
        System.out.println("Maximum: " + maxim + " Index of Maximum in 3: " + max_index);

        System.out.println("-----C4 Answer-----");
        System.out.println("MaxQ: " + max_Q + " MaxNotQ: " + max_not_Q);
    }
}