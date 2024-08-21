import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

// B4:  Напечатать слово, содержащее наибольшее количество цифр.
//      Напечатать количество слов, содержащих хотя бы два арифметических знака.
// C4:  Найти количество всех слов в строке-предложении,
//      которые обладают свойством Q. Вывести все слова исходной
//      строки-предложения в лексикографическом порядке. Записать в строку t
//      любое из слов максимальной длины строки s.
//      Удалить из строки t все символы, обладающие свойством T.
//      Q: в слове только один символ встречается более одного раза.
//      Т: символ является большой согласной латинской буквой.

public class Main
{
    private static String fillString()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите строку: ");
        return in.nextLine();
    }

    private static String fillRandomString()
    {
        Scanner in = new Scanner(System.in);
        Random rnd = new Random();
        System.out.print("Введите длину строки: ");
        int length = in.nextInt();
        int left_border = '*', right_border = 'z';
        char[] chars = new char[length];

        for (int i = 0; i < length; i++)
        {
            int randint = rnd.nextInt(right_border - left_border) + left_border;
            chars[i] = (char)randint;
        }

        String str = String.copyValueOf(chars);
        return str;
    }

    private static void taskB(String str)
    {
        String[] words = str.split("[.,:;!\\n\\t? ]");
        int max_count_nums = Integer.MIN_VALUE;
        int count_words = 0;
        String word_with_max_nums = "";
        for (var word : words)
        {
            if (word.isEmpty())
                continue;

            int count_signs = 0, count_nums = 0;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) >= '0' && word.charAt(i) <= '9')
                    count_nums++;
                if (word.charAt(i) == '+' ||
                        word.charAt(i) == '-' ||
                        word.charAt(i) == '=' ||
                        word.charAt(i) == '>' ||
                        word.charAt(i) == '<' ||
                        word.charAt(i) == '*' ||
                        word.charAt(i) == '/')
                {
                    count_signs++;
                }
            }
            if (count_signs >= 2)
                count_words++;
            if (count_nums > max_count_nums){
                max_count_nums = count_nums;
                word_with_max_nums = word;
            }
        }
        System.out.println(word_with_max_nums);
        System.out.println("Кол-во слов содерж. мин. два арифм. знака: " + count_words);
    }

    private static boolean not_T(char c, String str)
    {
        String chars = "BCDFGHJKLMNOPQRSTVWXYZ";
        return !chars.contains(Character.toString(c));
    }

    private static void taskC(String str)
    {
        String[] words = str.split("[.,:;!\\n\\t?]");
        String Q_words_str = "";

        for (var word: words)
        {
            if (word.isEmpty())
                continue;

            int[] count_chars = new int[128];
            Arrays.fill(count_chars, 0);
            for (int i = 0; i < word.length(); i++)
                count_chars[word.charAt(i)] += 1;

            int count = 0;
            for (var elem : count_chars)
            {
                if (elem > 1) // символ встречается в слове больше одного раза
                    count++;
            }
            if (count == 1) { // если один символ встретился больше одного раза
                Q_words_str += word + "%";
            }
        }

        String[] Q_words = Q_words_str.split("%");
        Arrays.sort(Q_words);
        String t = Q_words[Q_words.length - 1];
        String ans = "";

        for (int i = 0; i < t.length(); i++)
        {
            if (not_T(t.charAt(i), t))
                ans += t.charAt(i);
        }

        if (Q_words.length < 1)
            System.out.println("Подстроки со свойством Q не найдены");

        for (var word : Q_words)
            System.out.println(word);

        if (!ans.isEmpty())
            System.out.println(ans);
        else
            System.out.println("Строка t не найдена");
    }

    private static int progressiveSearch(String str, String sub)
    {
        long startTime = System.nanoTime();
        long endTime;
        boolean isFind = false;
        for (int i = 0; i < str.length() - sub.length() + 1; i++)
        {
            for (int j = 0; j < sub.length(); j++)
            {
                if (str.charAt(i+j) != sub.charAt(j)){
                    break;
                }
                if (j == sub.length() - 1) {
                    endTime = System.nanoTime();
                    System.out.print("Время выполнения: " + (endTime - startTime) + " наносекунд\n");
                    return i;
                }
            }
        }
        endTime = System.nanoTime();
        System.out.print("Время выполнения: " + (endTime - startTime) + " наносекунд\n");
        return -1;
    }

    private static int[] prefixFunc(String str)
    {
        int[] p = new int[str.length()];
        p[0] = 0;
        for (int i = 1; i <= str.length() - 1; i++)
        {
            int k = p[i - 1];
            while ((k > 0) && (str.charAt(i) != str.charAt(k)))
                k = p[k - 1];
            if (str.charAt(i) == str.charAt(k))
                k++;
            p[i] = k;
        }
        return p;
    }

    private static int KMP(String str, String sub)
    {
        long startTime = System.nanoTime();
        long endTime;
        int strlen = str.length(), sublen = sub.length();
        int[] answer = new int[strlen + sublen + 1];
        int[] prefixes = prefixFunc(sub + "#" + str);
        int count = 0;

        for (int i = 0; i <= strlen - 1; i++)
        {
            if (prefixes[sublen + i + 1] == sublen)
                answer[count++] = i - sublen;
        }

        for (var e : answer)
        {
            if (e != 0) {
                endTime = System.nanoTime();
                System.out.print("Время выполнения: " + (endTime - startTime) + " наносекунд\n");
                return e + 1;
            }
        }

        endTime = System.nanoTime();
        System.out.print("Время выполнения: " + (endTime - startTime) + " наносекунд\n");
        return -1;
    }

    private static int BoyerMoore(char[] str, char[] substr)
    {
        long startTime = System.nanoTime();
        long endTime = System.nanoTime();
        int[] shift = new int[256];
        for (int i = 0; i < shift.length; i++) // заполняем длиной substr
            shift[i] = substr.length;

        for (int i = 0; i < substr.length - 1; i++) // заполняем сдвиги
            shift[substr[i]] = substr.length - 1 - i;

        int i = 0, j = 0;
        while ((i + substr.length) <= str.length)
        {
            j = substr.length - 1;
            while (str[i+j] == substr[j])
            {
                j--;
                if (j < 0){
                    endTime = System.nanoTime();
                    System.out.print("Время выполнения: " + (endTime - startTime) + " наносекунд\n");
                    return i;
                }
            }
            i = i + shift[str[i+substr.length-1]];
        }
        endTime = System.nanoTime();
        System.out.print("Время выполнения: " + (endTime - startTime) + " наносекунд\n");
        return -1;
    }

    private static int javaSearch(String str, String sub)
    {
        long startTime = System.nanoTime();
        int index = str.indexOf(sub);
        long endTime = System.nanoTime();
        System.out.print("Время выполнения: " + (endTime - startTime) + " наносекунд\n");
        return index;
    }

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int ans = -1;
        String str = "";

        do {
            System.out.println("\nMenu");
            System.out.println("1. Инициализация строки");
            System.out.println("2. Вывод строки");
            System.out.println("3. Поиск подстроки");
            System.out.println("4. Решение задачи B");
            System.out.println("5. Решение задачи C");
            System.out.print("Введите пункт меню: ");

            ans = in.nextInt();
            switch (ans) {
                case 1 -> {
                    System.out.println("1. Ручной ввод строки");
                    System.out.println("2. Случайная генерация");
                    System.out.print("Введите пункт меню: ");
                    int local_ans = in.nextInt();
                    switch (local_ans) {
                        case 1 -> str = fillString();
                        case 2 -> str = fillRandomString();
                        default -> System.out.println("Ошибка ввода!");
                    }
                }
                case 2 -> System.out.println("Строка: " + str);
                case 3 -> {
                    System.out.print("Введите подстроку: ");
                    String sub = in.next();
                    if (sub.length() > str.length()){
                        System.out.println("Подстрока больше исходной строки!!!");
                        break;
                    }
                    System.out.println("1. Последовательный поиск");
                    System.out.print("Подстрока " + sub + " найдена по индексу: " + progressiveSearch(str, sub) + "\n");
                    System.out.println("2. Метод Кнута-Морриса-Пратта");
                    System.out.print("Подстрока " + sub + " найдена по индексу: " + KMP(str, sub) + "\n");
                    System.out.println("3. Упрощённый метод Бойера-Мура");
                    System.out.print("Подстрока " + sub + " найдена по индексу: " + BoyerMoore(str.toCharArray(), sub.toCharArray()) + "\n");
                    System.out.println("4. Встроенный метод поиска");
                    System.out.print("Подстрока " + sub + " найдена по индексу: " + javaSearch(str, sub) + "\n");
                }
                case 4 -> taskB(str);
                case 5 -> taskC(str);
                case 6 -> System.out.println("Завершение программы");
                default -> System.out.println("Ошибка ввода!");
            }
        } while (ans != 6);
    }
}