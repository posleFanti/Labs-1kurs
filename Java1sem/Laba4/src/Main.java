import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

// Практическая работа №4 Методы поиска и модификация двумерных массивов
// B4. Требуется определить двумерный массив целых чисел a (например, размера 30),
//     заполнить его случайными числами (в диапазоне от A до B) или
//     ввести его элементы с клавиатуры.
//     Задан прямоугольный массив целых чисел. Определить, все ли строки
//     состоят только из разных чисел, и напечатать строки, имеющие в своем составе одинаковые числа.

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

    private static int[][] fillIntMatrixByHand()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите кол-во строчек: ");
        int rows = in.nextInt();
        System.out.print("Введите кол-во столбцов: ");
        int columns = in.nextInt();
        int[][] arr = new int[rows][columns];

        for (int row = 0; row < arr.length; row++)
        {
            for (int col = 0; col < arr[row].length; col++)
            {
                System.out.print("Введите элемент с координатами " + row + " " + col + ": ");
                arr[row][col] = in.nextInt();
            }
        }

        return arr;
    }

    private static char[][] fillCharMatrixByHand()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите кол-во строчек: ");
        int rows = in.nextInt();
        System.out.print("Введите кол-во столбцов: ");
        int columns = in.nextInt();
        char[][] arr = new char[rows][columns];

        for (int row = 0; row < arr.length; row++)
        {
            for (int col = 0; col < arr[row].length; col++)
            {
                System.out.print("Введите элемент с координатами " + row + " " + col + ": ");
                arr[row][col] = in.next().charAt(0);
            }
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

    private static int[][] fillMatrixByRandom()
    {
        int left_border = -100, right_border = 100;
        Scanner in = new Scanner(System.in);
        System.out.print("Введите количество строк: ");
        int rows = in.nextInt();
        System.out.print("Введите количество столбцов: ");
        int cols = in.nextInt();
        int[][] arr = new int[rows][cols];

        System.out.print("Введите границы: ");
        if (in.hasNextInt())
            left_border = in.nextInt();
        if (in.hasNextInt())
            right_border = in.nextInt();

        for (int i = 0; i < rows; i++)
            arr[i] = fillRow(arr[i], cols, left_border, right_border);

        return arr;
    }

    private static int[] fillRow(int[] row, int size, int left_border, int right_border)
    {
        Random rnd = new Random();
        for (int i = 0; i < size; i++)
            row[i] = rnd.nextInt(right_border - left_border) + left_border;
        return row;
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

    private static void printMatrix(int[][] arr)
    {
        System.out.println("Матрица = ");
        for (var row : arr)
        {
            for (var elem : row)
                System.out.print(elem + " ");
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private static void printMatrix(char[][] arr)
    {
        System.out.println("Матрица = ");
        for (var row : arr)
        {
            for (var elem : row)
                System.out.print(elem + " ");
            System.out.print("\n");
        }
        System.out.print("\n");
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

//    private static boolean wasNotInArr(int[] arr, int element)
//    {
//        for (int i = 0; i < arr.length; i++)
//        {
//            if (arr[i] == element)
//                return false;
//        }
//        return true;
//    }

    private static int taskB_laba3(int[] arr)
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

    private static int taskC_laba3(int[] arr)
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

    private static void taskB(int[][] arr)
    {
        boolean isAllRowsDiff = true;
        for (int i = 0; i < arr.length; i++)
        {
            int[] sorted_row = shellSort(arr[i]);
            for (int j = 1; j < sorted_row.length - 1; j++)
            {
                if (    sorted_row[j - 1] == sorted_row[j] ||
                        sorted_row[j + 1] == sorted_row[j] ||
                        sorted_row[j - 1] == sorted_row[j + 1])
                {
                    isAllRowsDiff = false;
                    printArray(arr[i]);
                    break;
                }
            }
        }
        System.out.println("Все строки содержат различные числа: " + isAllRowsDiff + "\n");
    }

    private static boolean arr_i_j_on_cross(char[][]arr, int i, int j)
    {
        return false;
    }

    private static void taskC(char[][] arr)
    {
        int count1 = 0, count2 = 0;
        for (int i = 0; i < arr.length; i++)
        {
            int wordlen = 0;
            for (int j = 0; j < arr[i].length; j++)
            {
                if (arr[i][j] == ' ' && wordlen > 1)
                {
                    count1++;
                    continue;
                }

                if (arr_i_j_on_cross(arr, i, j))
                    System.out.print(arr[i][j] + " ");

                wordlen++;
            }
            if (wordlen > 1)
                count1++;
        }

        for (int i = 0; i < arr.length; i++)
        {
            int wordlen = 0;
            for (int j = 0; j < arr[i].length; j++)
            {
                if (arr[j][i] == ' ' && wordlen > 1)
                {
                    count2++;
                    continue;
                }

                if (arr_i_j_on_cross(arr, i, j))
                    System.out.print(arr[j][i] + " ");

                wordlen++;
            }
            if (wordlen > 1)
                count2++;
        }

        System.out.println("Общее кол-во слов: " + (count1 + count2));
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
        //long startTime = System.currentTimeMillis();
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
        //long endTime = System.currentTimeMillis();

        //System.out.println("Время выполнения: " + (endTime - startTime) + " миллисекунд");
        return sorted_arr;
    }

    private static int[][] shellSortMatrix(int[][] arr)
    {
        int[][] sorted_arr = arr.clone();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < sorted_arr.length; i++)
            sorted_arr[i] = shellSort(sorted_arr[i]);

        /*int j = 0;
        int step = arr.length / 2;
        while (step > 0)
        {
            for (int i = 0; i < (arr.length - step); i++)
            {
                j = i;
                while ((j >= 0) && arr[j][0] > arr[j + step][0])
                {
                    int[] buff = arr[j];
                    arr[j] = arr[j + step];
                    arr[j + step] = buff;
                    j -= step;
                }
            }
            step /= 2;
        }*/

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

    private static int[][] quickSortMatrix(int[][] arr)
    {
        int[][] sorted_arr = arr.clone();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < sorted_arr.length; i++)
            quickSort(sorted_arr[i], 0, sorted_arr[i].length - 1);
        long endTime = System.currentTimeMillis();

        System.out.println("Время выполнения: " + (endTime - startTime) + " миллисекунд");
        return sorted_arr;
    }

    private static int[] javaSort(int[] arr)
    {
        int [] sorted_arr = arr.clone();
        //long startTime = System.currentTimeMillis();
        Arrays.sort(sorted_arr);
        //long endTime = System.currentTimeMillis();
        //System.out.println("Время выполнения: " + (endTime - startTime) + " миллисекунд");
        return sorted_arr;
    }

    private static int[][] javaSortMatrix(int[][] arr)
    {
        int[][] sorted_arr = arr.clone();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < sorted_arr.length; i++)
            sorted_arr[i] = javaSort(sorted_arr[i]);
        long endTime = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (endTime - startTime) + " миллисекунд");
        return sorted_arr;
    }

    private static void findOtherIndexes(int[] arr, int key_pos, int key)
    {
        if (key_pos < 0) {
            System.out.print("Элемент " + key + " не найден");
            return;
        }

        System.out.print("Элемент " + key + " найден на позициях: " + key_pos + " ");
        int j = key_pos - 1;
        while (arr[j] == key && j >= 0)
        {
            System.out.print(j + " ");
            j--;
        }
        j = key_pos + 1;
        while (arr[j] == key && j < arr.length)
        {
            System.out.print(j + " ");
            j++;
        }
    }

    private static void progressiveSearch(int[] arr, int key)
    {
        long startTime = System.nanoTime();
        int key_pos = -1;
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] == key)
                key_pos = i;
        }
        long endTime = System.nanoTime();

        findOtherIndexes(arr, key_pos, key);
        System.out.print("\nВремя выполнения: " + (endTime - startTime) + " наносекунд\n");
    }

    private static void binarySearchIter(int[] arr, int key, int left, int right)
    {
        long startTime = System.nanoTime();
        int key_pos = -1;
        while (left <= right)
        {
            int mid = left + (right - left) / 2;
            if (arr[mid] < key)
                left = mid + 1;
            else if (arr[mid] > key)
                right = mid - 1;
            else {
                key_pos = mid;
                break;
            }
        }
        long endTime = System.nanoTime();

        findOtherIndexes(arr, key_pos, key);
        System.out.print("\nВремя выполнения: " + (endTime - startTime) + " наносекунд\n");
    }

    private static void fibonacciSearch(int[] arr, int key, int n)
    {
        long startTime = System.nanoTime();
        int fibMin1 = 1;
        int fibMin2 = 0;
        int fib = fibMin1 + fibMin2;
        int key_pos = -1;
        while (fib < n)
        {
            fibMin2 = fibMin1;
            fibMin1 = fib;
            fib = fibMin1 + fibMin2;
        }

        int offset = -1;
        while (fib > 1)
        {
            int i = Math.min(offset + fibMin2, n - 1);
            if (arr[i] < key){
                fib = fibMin1;
                fibMin1 = fibMin2;
                fibMin2 = fib - fibMin1;
                offset = i;
            } else if (arr[i] > key) {
                fib = fibMin2;
                fibMin1 = fibMin1 - fibMin2;
                fibMin2 = fib - fibMin1;
            } else {
                key_pos = i;
                break;
            }
        }

        if (fibMin1 == 1 && arr[n - 1] == key)
            key_pos = n - 1;
        long endTime = System.nanoTime();

        findOtherIndexes(arr, key_pos, key);
        System.out.print("\nВремя выполнения: " + (endTime - startTime) + " наносекунд\n");
    }

    private static int baseInterpolationSearch(int[] arr, int key, int left, int right)
    {
        int mid = -1;
        while (arr[left] < key && key < arr[right])
        {
            mid = left + (key - arr[left]) * (right - left) / (arr[right] - arr[left]);
            if (arr[mid] < key)
                left = mid + 1;
            else if (arr[mid] > key)
                right = mid - 1;
            else
                return mid;
        }

        if (arr[left] == key)
            return left;
        else if (arr[right] == key)
            return right;
        else
            return -1;
    }

    private static void interpolationSearch(int[] arr, int key, int left, int right)
    {
        long startTime = System.nanoTime();
        int key_pos = -1;
        try {
            key_pos = baseInterpolationSearch(arr, key, left, right);
        }
        catch (StackOverflowError e) {
            System.out.println("StackOverflowError");
            return;
        }
        long endTime = System.nanoTime();

        if (key != -1)
            findOtherIndexes(arr, key_pos, key);

        System.out.print("\nВремя выполнения: " + (endTime - startTime) + " наносекунд\n");
    }

    private static void javaBinSearch(int[] arr, int key)
    {
        long startTime = System.nanoTime();
        int key_pos = Arrays.binarySearch(arr, key);
        long endTime = System.nanoTime();
        findOtherIndexes(arr, key_pos, key);
        System.out.print("\nВремя выполнения: " + (endTime - startTime) + " наносекунд\n");
    }

    public static void main(String[] args)
    {
        int ans = 0;
        int local_ans = 0;
        int index = 0;
        int result;
        long startTime, endTime;
        int[] array = new int[0];
        int[][] matrix = new int[0][0];
        char[][] matrixC = new char[0][0];
        Scanner in = new Scanner(System.in);

        do
        {
            System.out.println("Menu");
            System.out.println("1. Задать матрицу");
            System.out.println("2. Вывести матрицу на экран");
            System.out.println("3. Изменить элемент матрицы");
            System.out.println("4. Выполнить задачу"); // найти все (>0)элементов и их кол-во, если сумма цифр больше 10
            System.out.println("5. Выполнить сортировку матрицы по строкам");
            System.out.println("6. Выполнить поиск элемента в строке массиве");
            System.out.println("7. Выход");
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
                            System.out.print("Какую матрицу создать? (int - 1; char - 2): ");
                            int local_ans2 = in.nextInt();
                            switch (local_ans2)
                            {
                                case 1:
                                    matrix = fillIntMatrixByHand();
                                    break;
                                case 2:
                                    matrixC = fillCharMatrixByHand();
                                    break;
                            }
                            System.out.println("Матрица задана вручную\n");
                            break;
                        case 2:
                            matrix = fillMatrixByRandom();
                            System.out.println("Матрица сгенерирована случайными значениями\n");
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
                    System.out.println("\nПункт 2");
                    printMatrix(matrix);
                    break;
                case 3:
                    System.out.println("\nПункт 3");
                    if (checkArrSize(matrixC.length))
                        break;
                    changeElementOfArray(array);
                    break;
                case 4:
                    System.out.println("\nПункт 4");
                    if (checkArrSize(matrix.length))
                        break;
                    System.out.println("1. Решить задачу B");
                    System.out.println("2. Решить задачу C");
                    System.out.print("Выберите задачу для решения: ");
                    local_ans = in.nextInt();
                    switch (local_ans)
                    {
                        case 1:
                            System.out.println("Решение задачи B: ");
                            taskB(matrix);
                            break;
                        case 2:
                            System.out.println("Решение задачи C: ");
                            taskC(matrixC);
                            break;
                    }
                    break;
                case 5:
                    System.out.println("\nПункт 5");
                    if (checkArrSize(matrix.length))
                        break;
                    //System.out.print("Сортировка пузырьком: ");
                    //bubbleSort(array);
                    System.out.print("Сортировка Шелла: ");
                    shellSortMatrix(matrix);
                    System.out.print("Встроенная сортировка Java Arrays.sort(): ");
                    javaSortMatrix(matrix);
                    System.out.print("Быстрая сортировка: ");
                    quickSortMatrix(matrix);
                    break;
                case 6:
                    System.out.println("\nПункт 6");
                    System.out.print("Введите какой элемент хотите найти: ");
                    int key = in.nextInt();
                    System.out.print("Введите номер строки (от 1 до " + matrix.length + ") в которой нужно найти эл-т: ");
                    int row = in.nextInt() - 1;
                    System.out.print("Последовательный поиск: ");
                    progressiveSearch(matrix[row], key);
                    System.out.print("Бинарный поиск: ");
                    binarySearchIter(matrix[row], key, 0, matrix[row].length - 1);
                    System.out.print("Фибоначчиев поиск: ");
                    fibonacciSearch(matrix[row], key, matrix[row].length);
                    System.out.print("Интерполяционный поиск: ");
                    interpolationSearch(matrix[row], key, 0, matrix[row].length - 1);
                    System.out.print("Встроенный поиск: ");
                    javaBinSearch(matrix[row], key);
                    break;
                case 7:
                    System.out.println("Завершение программы");
                    break;
                default:
                    System.out.println("\nОшибка ввода\n");
                    break;
            }
        } while (ans != 7);
    }
}