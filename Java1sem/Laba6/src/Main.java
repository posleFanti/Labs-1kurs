import java.io.*;
import java.util.*;

class User
{
    String username;
    String user_rights;

    void setName(String name) {
        this.username = name;
    }
    void setRights(String user_rights) {
        this.user_rights = user_rights;
    }
    void changeUser(String name, String user_rights) {
       setName(name);
       setRights(user_rights);
    }
    void printParams()
    {
        System.out.print("Имя пользователя: ");
        System.out.println(this.username);
        System.out.print("Права пользователя: ");
        System.out.println(this.user_rights);
    }

    User() {
        this.username = "Guest";
        this.user_rights = "Guest";
    }
    User(String username, String user_rights) {
        this.username = username;
        this.user_rights = user_rights;
    }
}

class Computer
{
    String processor_vendor;
    String operating_system;
    int bit_depth;
    int memory_capacity;
    User user;

    void changeProcessor(String processor_vendor) {
        this.processor_vendor = processor_vendor;
        setBitDepth();
    }

    void setBitDepth() {
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Введите разрядность: ");
                this.bit_depth = Integer.parseInt(in.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Нечисловое значение! " + e);
            }
        }
    }

    void doTask(String cmd) {
        System.out.println("Команда " + cmd + " выполняется");
    }

    void setOperatingSystem(String operating_system) {
        this.operating_system = operating_system;
    }

    void setMemoryCapacity() {
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Введите объем памяти: ");
                this.memory_capacity = Integer.parseInt(in.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Нечисловое значение! " + e);
            }
        }
    }

    void printParams()
    {
        System.out.print("Процессор: ");
        System.out.println(this.processor_vendor);
        System.out.print("ОС: ");
        System.out.println(this.operating_system);
        System.out.print("Разрядность: ");
        System.out.println(this.bit_depth);
        System.out.print("Объем памяти: ");
        System.out.println(this.memory_capacity);
        this.user.printParams();
        System.out.println("");
    }

    Computer()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите производителя процессора: ");
        this.processor_vendor = in.nextLine();
        System.out.print("Введите название ОС: ");
        this.operating_system = in.nextLine();
        this.setBitDepth();
        this.setMemoryCapacity();
        System.out.print("Введите имя пользователя: ");
        String username = in.next();
        System.out.print("Введите права пользователя: ");
        String user_rights = in.next();
        this.user = new User(username, user_rights);
    }

    Computer(ArrayList<String> params) {
        this.operating_system = params.get(0);
        this.processor_vendor = params.get(1);
        this.bit_depth = Integer.parseInt(params.get(2));
        this.memory_capacity = Integer.parseInt(params.get(3));
        this.user = new User(params.get(4), params.get(5));
    }
}

public class Main
{
    private static ArrayList<Computer> addComputersFromFile(String file_path, int number) {
        ArrayDeque<String> params = new ArrayDeque<>();
        ArrayList<Computer> computers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            String str;
            while ((str = br.readLine()) != null) {
                String[] strs = str.split(": ");
                params.offer(strs[strs.length - 1]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        while (params.peek() != null && number > 0) {
            ArrayList<String> buff = new ArrayList<>();
            int c = 0;
            for (int i = 0; i < 7; i++) {
                if (c != 0)
                    buff.add(params.poll());
                else
                    params.poll();
                c++;
            }
            computers.add(new Computer(buff));
            number--;
        }
        return computers;
    }

    private static void writeListToFile(ArrayList<Computer> list, String file_path) {
        try (FileWriter fw = new FileWriter(file_path)) {
            int count = 1;
            for (Computer c : list) {
                fw.write("PC " + count + '\n');
                fw.write("ОС: " + c.operating_system + '\n');
                fw.write("Процессор: " + c.processor_vendor + '\n');
                fw.write("Разрядность: " + c.bit_depth + '\n');
                fw.write("Объем памяти: " + c.memory_capacity + '\n');
                fw.write("Имя пользователя: " + c.user.username + '\n');
                fw.write("Права пользователя: " + c.user.user_rights + '\n');
                fw.flush();
                count++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getNumberOfLinesIn(String file_path) {
        int lines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            while (br.readLine() != null){
                lines++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lines / 7;
    }

    private static void changeParamOf(Computer computer)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("По какому параметру изменять");
        System.out.println("1. Процессор");
        System.out.println("2. ОС");
        System.out.println("3. Объем памяти");
        System.out.println("4. Пользователь");
        int ans = in.nextInt();
        switch (ans)
        {
            case 1 -> {
                System.out.print("Введите название производителя: ");
                String vendor = in.next();
                computer.changeProcessor(vendor);
                System.out.println("Объект изменен");
            }
            case 2 -> {
                System.out.print("Введите название ОС: ");
                String operating_system = in.next();
                computer.setOperatingSystem(operating_system);
                System.out.println("Объект изменен");
            }
            case 3 -> {
                computer.setMemoryCapacity();
                System.out.println("Объект изменен");
            }
            case 4 -> {
                System.out.print("Введите имя пользователя: ");
                String username = in.next();
                System.out.print("Введите права пользователя: ");
                String user_rights = in.next();
                computer.user.changeUser(username, user_rights);
                System.out.println("Объект изменен");
            }
            default -> System.out.println("Ошибка ввода");
        }
    }

    private static void findComputerBy(ArrayList<Computer> computers)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("По какому параметру искать");
        System.out.println("1. Процессор");
        System.out.println("2. ОС");
        System.out.println("3. Объем памяти");
        System.out.println("4. Пользователь (имя пользователя)");
        System.out.println("5. Пользователь (права пользователя)");
        System.out.print(">");
        int ans = in.nextInt();
        switch (ans)
        {
            case 1 -> {
                int i = 0;
                System.out.print("Введите ключ: ");
                String item = in.next();
                for (Computer computer : computers)
                {
                    if (Objects.equals(computer.processor_vendor, item)) {
                        System.out.print("PC Index: " + i + "\n");
                        computer.printParams();
                    }
                    i++;
                }
                System.out.print("\n");
            }
            case 2 -> {
                int i = 0;
                System.out.print("Введите ключ: ");
                String item = in.next();
                for (Computer computer : computers)
                {
                    if (Objects.equals(computer.operating_system, item)) {
                        System.out.print("PC Index: " + i + "\n");
                        computer.printParams();
                    }
                    i++;
                }
                System.out.print("\n");
            }
            case 3 -> {
                int i = 0;
                System.out.print("Введите ключ: ");
                int item = in.nextInt();
                for (Computer computer : computers)
                {
                    if (Objects.equals(computer.memory_capacity, item)) {
                        System.out.print("PC Index: " + i + "\n");
                        computer.printParams();
                    }
                    i++;
                }
                System.out.print("\n");
            }
            case 4 -> {
                int i = 0;
                System.out.print("Введите ключ: ");
                String item = in.next();
                for (Computer computer : computers)
                {
                    if (Objects.equals(computer.user.username, item)) {
                        System.out.print("PC Index: " + i + "\n");
                        computer.printParams();
                    }
                    i++;
                }
                System.out.print("\n");
            }
            case 5 -> {
                int i = 0;
                System.out.print("Введите ключ: ");
                String item = in.next();
                for (Computer computer : computers)
                {
                    if (Objects.equals(computer.user.user_rights, item)) {
                        System.out.print("PC Index: " + i + "\n");
                        computer.printParams();
                    }
                    i++;
                }
                System.out.print("\n");
            }
            default -> System.out.println("Ошибка ввода");
        }
    }

    public static void main(String[] args)
    {
        ArrayList<Computer> computers = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int ans = 0;
        do {
            System.out.println("Menu");
            System.out.println("1. Добавить эл-т");
            System.out.println("2. Удалить эл-т");
            System.out.println("3. Изменить эл-т");
            System.out.println("4. Поиск эл-та");
            System.out.println("5. Вывод списка");
            System.out.println("6. Вывод числа эл-тов");
            System.out.println("7. Вывод содержимого списка в файл");
            System.out.print("> ");
            ans = in.nextInt();

            switch (ans)
            {
                case 1 -> {
                    System.out.println("Пункт 1");
                    System.out.println("Куда добавить: ");
                    System.out.println("1. В конец");
                    System.out.println("2. По индексу");
                    System.out.print("> ");
                    int local_ans = in.nextInt();
                    switch (local_ans)
                    {
                        case 1 -> {
                            System.out.println("Откуда добавлять: ");
                            System.out.println("1. Из файла");
                            System.out.println("2. Вручную");
                            System.out.print("> ");
                            int mode_ans = in.nextInt();
                            switch (mode_ans) {
                                case 1 -> {
                                    System.out.print("Введите имя файла или полный путь к нему: ");
                                    String file_path = in.next();
                                    System.out.println("В файле доступно " + getNumberOfLinesIn(file_path) + " объекта");
                                    System.out.print("Сколько добавить: ");
                                    int n = in.nextInt();
                                    computers.addAll(addComputersFromFile(file_path, n));
                                }
                                case 2 -> {
                                    System.out.print("Сколько добавить: ");
                                    int n = in.nextInt();
                                    while (n > 0) {
                                        computers.add(new Computer());
                                        n--;
                                    }
                                }
                            }
                        }
                        case 2 -> {
                            System.out.print("Сколько добавить: ");
                            int n = in.nextInt();
                            System.out.print("По какому индексу: ");
                            int index = in.nextInt();
                            if (index > computers.size()) {
                                System.out.println("Индекс вне списка!!!");
                                break;
                            }
                            while (n > 0) {
                                computers.add(index, new Computer());
                                n--;
                            }
                        }
                        default -> System.out.println("Ошибка ввода");
                    }
                }
                case 2 -> {
                    System.out.println("Пункт 2");
                    System.out.print("Сколько удалить: ");
                    int n = in.nextInt();
                    System.out.println("Где удалить: ");
                    System.out.println("1. В конец");
                    System.out.println("2. По индексу");
                    System.out.print("> ");
                    int local_ans = in.nextInt();
                    switch (local_ans)
                    {
                        case 1 -> {
                            try {
                                while (n > 0) {
                                    computers.remove(computers.size() - 1);
                                    n--;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Весь список очищен " + e);
                            }
                            System.out.println("Объект(-ы) удален(-ы)");
                        }
                        case 2 -> {
                            System.out.print("По какому индексу: ");
                            int index = in.nextInt();
                            try {
                                while (n > 0) {
                                    computers.remove(index);
                                    n--;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Индекс вне списка! " + e);
                            }
                            System.out.println("Объект(-ы) удален(-ы)");
                        }
                        default -> System.out.println("Ошибка ввода");
                    }
                }
                case 3 -> {
                    System.out.println("Пункт 3");
                    System.out.print("Введите индекс по которому нужно изменить: ");
                    int index = in.nextInt();
                    if (index >= computers.size()) {
                        System.out.println("Индекс вне списка!!!");
                        break;
                    }
                    System.out.println("Какой параметр изменить");
                    changeParamOf(computers.get(index));
                }
                case 4 -> {
                    System.out.println("Пункт 4");
                    findComputerBy(computers);
                }
                case 5 -> {
                    System.out.println("Пункт 5");
                    int i = 0;
                    for (Computer computer : computers) {
                        System.out.println("PC " + (i + 1));
                        computer.printParams();
                        i++;
                    }
                }
                case 6 -> {
                    System.out.println("Пункт 6");
                    System.out.println("Длина списка: " + computers.size());
                }
                case 7 -> {
                    System.out.println("Пункт 7");
                    System.out.print("Введите название файла или полный путь: ");
                    String file_path = in.next();
                    writeListToFile(computers, file_path);
                }
                case 8 -> System.out.println("Выход из программы");
                default -> System.out.println("Ошибка ввода");
            }
        } while (ans != 8);
    }
}