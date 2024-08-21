import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main
{
    private static boolean checkArrSize(int arr_size)
    {
        if (arr_size == 0)
        {
            System.out.println("Массив не задан или его размер равен нулю\n");
            return true;
        }
        return false;
    }

    private static boolean checkNum(int num)
    {
        while (num > 0)
        {
            if (num % 10 == 0)
                return false;
            num /= 10;
        }
        return true;
    }

    private static int[] fillArrayByHand()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите размер: ");
        int arr_size = in.nextInt();
        int[] arr = new int[arr_size];

        for (int i = 0; i < arr_size; i++)
        {
            System.out.print("Введите " + i + "-й элемент: ");
            arr[i] = in.nextInt();
        }

        return arr;
    }

    private static int[] fillArrayByRandom()
    {
        int left_border = -100, right_border = 100;
        Scanner in = new Scanner(System.in);
        System.out.print("Введите размер: ");
        int arr_size = in.nextInt();
        int[] arr = new int[arr_size];

        System.out.print("Введите границы: ");
        if (in.hasNextInt())
            left_border = in.nextInt();
        if (in.hasNextInt())
            right_border = in.nextInt();

        if (left_border > right_border)
        {
            int buff = left_border;
            left_border = right_border;
            right_border = buff;
        }

        Random rnd = new Random();

        for (int i = 0; i < arr_size; i++)
            arr[i] = rnd.nextInt(right_border - left_border) + left_border;

        return arr;
    }

    private static void printArray(int[] arr)
    {
        System.out.print("Массив = [" + arr[0]);
        for (int i = 1; i < arr.length; i++)
        {
            System.out.print(", " + arr[i]);
        }
        System.out.print("]\n");
    }

    private static void changeElementOfArray(int[] arr)
    {
        int index = 0;
        Scanner in = new Scanner(System.in);

        do {
            System.out.print("Введите индекс элемента " + "(от 0 до " + (arr.length - 1) + ") для изменения: ");
            index = in.nextInt();
            if (index >= arr.length)
                System.out.println("Индекс вне заданного массива!");
        } while (index >= arr.length);

        System.out.print("Введите новое значение: ");
        arr[index] = in.nextInt();
        System.out.println(index + "-й элемент изменен\n");
    }

    private static int taskB(int[] arr)
    {
        int M = 0, count = 0;
        Scanner in = new Scanner(System.in);
        System.out.print("Введите число M: ");
        M = in.nextInt();

        for (int i = 0; i < arr.length - 1; i++)
        {
            if (arr[i] != arr[i + 1] && arr[i] < M)
                count++;
        }

        if (arr[arr.length - 1] != arr[arr.length - 2] && arr[arr.length - 1] < M)
            count++;

        return count;
    }

    private static int taskC(int[] arr)
    {
        Scanner in = new Scanner(System.in);

        int count = 0;
        for (int i = 0; i < arr.length - 1; i++)
        {
            if (arr[i] != arr[i + 1] && checkNum(arr[i]))
                count++;
        }

        if (arr[arr.length - 1] != arr[arr.length - 2] && checkNum(arr[arr.length - 1]))
            count++;

        return count;
    }

    private static int[] bubbleSort(int[] arr)
    {
        int[] sorted_arr = arr.clone();
        long startTime = System.currentTimeMillis();
        boolean swap;
        int buff = 0;

        for (int i = 0; i < sorted_arr.length; i++)
        {
            swap = false;
            for (int j = 1; j < sorted_arr.length - i; j++)
            {
                if (sorted_arr[j - 1] > sorted_arr[j])
                {
                    buff = sorted_arr[j];
                    sorted_arr[j] = sorted_arr[j - 1];
                    sorted_arr[j - 1] = buff;
                    swap = true;
                }
            }
            if (!swap)
                break;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (endTime - startTime) + " миллисекунд");
        return sorted_arr;
    }

    private static int[] shellSort(int[] arr)
    {
        int[] sorted_arr = arr.clone();
        long startTime = System.currentTimeMillis();
        int j = 0;
        int step = sorted_arr.length / 2;
        while (step > 0)
        {
            for (int i = 0; i < (sorted_arr.length - step); i++)
            {
                j = i;
                while ((j >= 0) && sorted_arr[j] > sorted_arr[j + step])
                {
                    int buff = sorted_arr[j];
                    sorted_arr[j] = sorted_arr[j + step];
                    sorted_arr[j + step] = buff;
                    j -= step;
                }
            }
            step /= 2;
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Время выполнения: " + (endTime - startTime) + " миллисекунд");
        return sorted_arr;
    }

    private static void quickSort(int[] arr, int left, int right)
    {
        if (arr.length == 0)
            return;
        if (left >= right)
            return;
        int pivot = arr[left + (right - left) / 2];
        int i = left, j = right;

        while (i <= j)
        {
            while (arr[i] < pivot)
                i++;
            while (arr[j] > pivot)
                j--;
            if (i <= j)
            {
                int buff = arr[i];
                arr[i] = arr[j];
                arr[j] = buff;
                i++;
                j--;
            }
        }

        if (left < j)
            quickSort(arr, left, j);
        if (right > i)
            quickSort(arr, i, right);
    }

    private static int[] javaSort(int[] arr)
    {
        int [] sorted_arr = arr.clone();
        long startTime = System.currentTimeMillis();
        Arrays.sort(sorted_arr);
        long endTime = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (endTime - startTime) + " миллисекунд");
        return sorted_arr;
    }

    public static void main(String[] args)
    {
        int ans = 0, local_ans = 0;
        long startTime, endTime;
        int[] array = new int[0];
        Scanner in = new Scanner(System.in);

        do
        {
            System.out.println("Menu");
            System.out.println("1. Задать массив");
            System.out.println("2. Вывести массив на экран");
            System.out.println("3. Изменить элемент массива");
            System.out.println("4. Выполнить задачу"); // найти все (>0)элементов и их кол-во, если сумма цифр больше 10
            System.out.println("5. Выполнить сортировку массива");
            System.out.println("6. Выход");
            System.out.print("Выберите пункт меню: ");

            ans = in.nextInt();
            switch (ans)
            {
                case 1:
                    System.out.println("\nПункт 1");
                    System.out.println("1.1 Fill by hand");
                    System.out.println("1.2 Fill by random");
                    System.out.println("1.3 Go back");
                    System.out.print("Выберите пункт меню: ");
                    local_ans = in.nextInt();
                    switch (local_ans)
                    {
                        case 1:
                            array = fillArrayByHand();
                            System.out.println("Массив задан вручную\n");
                            break;
                        case 2:
                            array = fillArrayByRandom();
                            System.out.println("Массив сгенерирован случайными значениями\n");
                            break;
                        case 3:
                            System.out.println("Возвращаемся обратно\n");
                            break;
                        default:
                            System.out.println("Ошибка ввода\n");
                            break;
                    }
                    break;
                case 2:
                    if (checkArrSize(array.length))
                        break;
                    System.out.println("\nПункт 2");
                    printArray(array);
                    break;
                case 3:
                    System.out.println("\nПункт 3");
                    if (checkArrSize(array.length))
                        break;
                    changeElementOfArray(array);
                    break;
                case 4:
                    System.out.println("\nПункт 4");
                    if (checkArrSize(array.length))
                        break;
                    System.out.println("1. Решить задачу B");
                    System.out.println("2. Решить задачу C");
                    System.out.print("Выберите задачу для решения: ");
                    local_ans = in.nextInt();
                    switch (local_ans)
                    {
                        case 1:
                            System.out.println("Решение задачи B: " + taskB(array));
                            break;
                        case 2:
                            System.out.println("Решение задачи C: " + taskC(array));
                            break;
                    }
                    break;
                case 5:
                    System.out.println("\nПункт 5");
                    if (checkArrSize(array.length))
                        break;
                    System.out.print("Сортировка пузырьком: ");
                    bubbleSort(array);
                    System.out.print("Сортировка Шелла: ");
                    shellSort(array);
                    System.out.print("Встроенная сортировка Java Arrays.sort(): ");
                    javaSort(array);
                    System.out.print("Быстрая сортировка: ");
                    startTime = System.currentTimeMillis();
                    quickSort(array, 0, array.length - 1);
                    endTime = System.currentTimeMillis();
                    System.out.println("Время выполнения: " + (endTime - startTime) + " миллисекунд");
                    break;
                case 6:
                    System.out.println("Завершение программы");
                    break;
                default:
                    System.out.println("\nОшибка ввода\n");
                    break;
            }
        } while (ans != 6);
    }
}