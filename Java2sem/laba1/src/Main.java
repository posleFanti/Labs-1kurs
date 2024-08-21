import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.Stack;

/* 4. Написать программу, моделирующую железнодорожный сортировочный узел.
    Исходный файл содержит информацию об имеющихся вагонах двух типов, при этом
    количество вагонов обоих типов одинаково. Последовательность элементов файла не
    упорядочена, в каждом элементе файла: тип вагона и идентификационный номер вагона.
    Используя стек (“тупик”), за один просмотр исходного файла сформировать новый файл
    (“состав вагонов”), в котором типы вагонов чередуются. */

class Wagon
{
    int id;
    int type;

    void setId(int id) {
        this.id = id;
    }
    void setType(int type) {
        this.type = type;
    }

    Wagon(int id, int type) {
        setId(id);
        setType(type);
    }

    Wagon()
    {
        setId(-1);
        setType(-1);
    }
}

public class Main
{
    private static ArrayDeque<Wagon> create_queue()
    {
        /* Создание изначальной очереди из
           введенной строки (e.g 1001 - очередь из вагонов 1->0->0->1) */
        Scanner in = new Scanner(System.in);
        ArrayDeque<Wagon> input_queue = new ArrayDeque<>();
        System.out.print("Enter input queue by type (1 and 0): ");
        String user_input = in.nextLine();
        for (int i = user_input.length() - 1; i >= 0; i--) {
            input_queue.offer(new Wagon(user_input.length() - i, user_input.charAt(i) - 48));
        }
        return input_queue;
    }
    private static void print_input_queue(ArrayDeque<Wagon> input) {
        ArrayDeque<Wagon> queue = new ArrayDeque<>(input);
        System.out.println("Input (first is first): ");
        while (queue.peek() != null) {
            Wagon wagon = queue.poll();
            System.out.println("ID: " + wagon.id + " Type: " + wagon.type);
        }
    }

    public static void main(String[] args)
    {
        ArrayDeque<Wagon> input_queue = create_queue();
        Stack<Wagon> buffer = new Stack<>();
        Stack<Wagon> output = new Stack<>();
        buffer.push(new Wagon());
        output.push(new Wagon());
        print_input_queue(input_queue);

        Wagon current_wagon, input_wagon, buffer_wagon;
        while (input_queue.peek() != null) {
            current_wagon = output.peek();
            input_wagon = input_queue.peek();
            buffer_wagon = buffer.peek();
            System.out.println( "Вагон в тупике ID: " + buffer_wagon.id + " Type: " + buffer_wagon.type +
                                " текущий вагон ID: " + current_wagon.id + " Type: " + current_wagon.type);
            /* Очистка тупика */
            while (buffer_wagon.type != current_wagon.type && buffer_wagon.type != -1) {
                System.out.println( "Добавляем вагон ID: " + buffer_wagon.id +
                                    " Type: " + buffer_wagon.type + " из тупика в выходную очередь");
                output.push(buffer.pop());
                current_wagon = output.peek();
            }
            System.out.println("Вагон во входной очереди ID: " + input_wagon.id + " Type: " + input_wagon.type +
                               " текущий вагон ID: " + current_wagon.id + " Type: " + current_wagon.type);
            /* Выбор вагона, если тип иной то присоединяем к выходному
               составу иначе убираем в тупик */
            if (input_wagon.type != current_wagon.type) {
                System.out.println( "Добавляем вагон ID: " + input_wagon.id +
                                    " Type: " + input_wagon.type + " в выходную очередь");
                output.push(input_queue.poll());
            } else {
                System.out.println( "Добавляем вагон ID: " + input_wagon.id +
                                    " Type: " + input_wagon.type + " в тупик");
                buffer.push(input_queue.poll());
            }
        }

        System.out.println("Output (first is last): ");
        while (output.peek().type != -1) {
            Wagon wagon = output.pop();
            System.out.println("ID: " + wagon.id + " Type: " + wagon.type);
        }
    }
}