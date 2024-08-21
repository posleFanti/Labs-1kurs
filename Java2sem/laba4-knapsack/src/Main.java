// реализовать программу, класс предмет(название, вес, стоимость, соотношение цена/вес),
// ввести макс вес рюкзака (класс рюкзак)
// решить задачу тремя способами (рекурсия, динамика, жадный(любая стратегия))
// ввод: вручную пользователем, через файл
// вывод: список предметов, вес рюкзака, прибыль

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

class KnapsackSolver {
    private ArrayList<Product> objects = new ArrayList<>();
    private Backpack backpack;
    private final Product[] products;
    private int[] v, w; // v - массив стоимостей, w - массив масс
    private int[][] m;

    private void knapsackOptimalDP(int n, int W) {
        int[][] m = new int[n + 1][W + 1];
        for (int j = 0; j <= W; j++) {
            m[0][j] = 0;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= W; j++) {
                if (w[i - 1] > j)
                    m[i][j] = m[i - 1][j];
                else
                    m[i][j] = Math.max(m[i - 1][j], m[i - 1][j - w[i - 1]] + v[i - 1]);
            }
        }
        this.m = m;
    }

    private Backpack knapsackRecursive(int n, int W, Backpack canditates) {
        if (n <= 0)
            return canditates;
        else if (products[n - 1].weight > W) {
            return knapsackRecursive(n - 1, W, new Backpack(canditates));
        } else {
            if (knapsackRecursive(n - 1, W, new Backpack(canditates)).currentCost >
                    knapsackRecursive(n - 1, W - products[n - 1].weight, canditates.with(products[n - 1])).currentCost) {
                return knapsackRecursive(n - 1, W, new Backpack(canditates));
            } else {
                return knapsackRecursive(n - 1, W - products[n - 1].weight, canditates.with(products[n - 1]));
            }
        }
    }

    private Backpack knapsackGreedy(int n, int W) {
        Product[] productsSorted = products.clone();
        Backpack backpack = new Backpack(this.backpack.maxWeight);
        Arrays.sort(productsSorted, new SortByWeight().reversed());
        int i = 0;
        while (backpack.currentWeight < W) {
            if (productsSorted[i].weight + backpack.currentWeight <= W) {
                backpack.add(productsSorted[i]);
            }
            i++;
        }
        return backpack;
    }

    public Backpack findAnsGreedy(int n, int W) {
        long startTime = System.nanoTime();
        backpack = knapsackGreedy(n, W);
        long endTime = System.nanoTime();
        System.out.println("Backpack contents: ");
        backpack.printContent();
        System.out.println("Max Cost: " + backpack.currentCost);
        System.out.println("Max Weight: " + backpack.currentWeight);
        System.out.println("Time: " + (endTime - startTime) + " нс");
        return backpack;
    }

    public Backpack findAnsRec(int n, int W) {
        long startTime = System.nanoTime();
        backpack = knapsackRecursive(n, W, new Backpack(W));
        long endTime = System.nanoTime();
        int maxWeight = 0;
        int maxCost = 0;
        for (Product p : backpack.products) {
            maxWeight += p.weight;
            maxCost += p.cost;
        }
        backpack.printContent();
        System.out.println("Max Cost: " + maxCost);
        System.out.println("Max Weight: " + maxWeight);
        System.out.println("Time: " + (endTime - startTime) + " нс");
        return backpack;
    }

    private void findListOfObjects(int k, int s) {
        if (m[k][s] == 0)
            return;
        if (m[k - 1][s] == m[k][s])
            findListOfObjects(k - 1, s);
        else {
            findListOfObjects(k - 1, s - w[k - 1]);
            this.objects.add(products[k - 1]);
        }
    }

    public Backpack findAnsDP(int n, int W) {
        long startTime = System.nanoTime();
        knapsackOptimalDP(n, W);
        long endTime = System.nanoTime();
        findListOfObjects(n, W);
        int maxWeight = 0;
        for (Product i : objects) {
            backpack.add(i);
            maxWeight += i.weight;
        }
        System.out.println("Backpack contents");
        backpack.printContent();
        System.out.println("Max Cost: " + m[n][W]);
        System.out.println("Max Weight: " + maxWeight);
        System.out.println("Time: " + (endTime - startTime) + " нс");
        return backpack;
    }

    KnapsackSolver(Product[] products, Backpack backpack) {
        this.products = products;
        int[] v = new int[products.length];
        int[] w = new int[products.length];
        for (int i = 0; i < products.length; i++) {
            v[i] = products[i].cost;
            w[i] = products[i].weight;
        }
        this.v = v;
        this.w = w;
        this.backpack = backpack;
    }
}

class SortByWeight implements Comparator<Product> {
    @Override
    public int compare(Product a, Product b) {
        return Integer.compare(a.weight, b.weight);
    }
}

class Product {
    int cost;
    int weight;
    double value;

    void print() {
        System.out.println("Cost: " + cost);
        System.out.println("Weight: " + weight);
        System.out.println("Value: " + value);
    }

    void updateValue() {
        this.value = (double) cost / weight;
    }

    Product(int cost, int weight) {
        this.cost = cost;
        this.weight = weight;
        try {
            this.value = (double) cost / weight;
        } catch (ArithmeticException e) {
            this.value = 0;
        }
    }

    Product(ArrayList<Integer> buff) {
        this.cost = buff.get(0);
        this.weight = buff.get(1);
        try {
            this.value = (float) cost / weight;
        } catch (ArithmeticException e) {
            this.value = 0;
        }
    }
}

class Backpack {
    int maxWeight;
    int currentWeight = 0;
    int currentCost = 0;
    ArrayList<Product> products = new ArrayList<>();

    public void add(Product product) {
        products.add(product);
        currentWeight += product.weight;
        currentCost += product.cost;
    }

    public Backpack with(Product product) {
        Backpack backpack = new Backpack(maxWeight, products, currentWeight, currentCost);
        backpack.add(product);
        return backpack;
    }

    public void clearContents() {
        products.clear();
    }

    public void printContent() {
        for (Product p : products)
            p.print();
    }

    Backpack(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    Backpack(int maxWeight, ArrayList<Product> products, int currentWeight, int currentCost) {
        this.maxWeight = maxWeight;
        this.currentWeight = currentWeight;
        this.currentCost = currentCost;
        this.products = new ArrayList<>(products);
    }

    Backpack(Backpack backpack) {
        this.maxWeight = backpack.maxWeight;
        this.currentWeight = backpack.currentWeight;
        this.currentCost = backpack.currentCost;
        this.products = new ArrayList<>(backpack.products);
    }
}

public class Main {
    private static void printMenu() {
        System.out.println("Меню");
        System.out.println("1. Заполнение списка из файла");
        System.out.println("2. Вывести список предметов в очереди");
        System.out.println("3. Добавить предмет");
        System.out.println("4. Изменить предмет");
        System.out.println("5. Удалить предмет");
        System.out.println("6. Задать максимальный вес рюкзака");
        System.out.println("7. Решить задачу");
    }

    private static ArrayList<Product> addProductsFromFile(String file_path) {
        ArrayDeque<Integer> params = new ArrayDeque<>();
        ArrayList<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            String str;
            while ((str = br.readLine()) != null) {
                String[] strs = str.split(": ");
                params.offer(Integer.parseInt(strs[strs.length - 1]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        while (params.peek() != null) {
            ArrayList<Integer> buff = new ArrayList<>();
            int c = 0;
            for (int i = 0; i < 3; i++) {
                if (c != 0)
                    buff.add(params.poll());
                else
                    params.poll();
                c++;
            }
            products.add(new Product(buff));
        }
        return products;
    }

    private static void writeListToFile(ArrayList<Product> list, String file_path) {
        try (FileWriter fw = new FileWriter(file_path)) {
            int count = 1;
            for (Product c : list) {
                fw.write("Product: " + count + '\n');
                fw.write("Cost: " + c.cost + '\n');
                fw.write("Weight: " + c.weight + '\n');
                fw.flush();
                count++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void changeProduct(Product product) throws IllegalAccessException {
        Scanner in = new Scanner(System.in);
        List<Field> fields = Arrays.stream(Product.class.getDeclaredFields()).toList();
        for (Field f : fields) {
            if (f.getType().getName() == "double")
                continue;
            System.out.println("Параметр: " + f.getName());
            System.out.println("Текущее значение " + f.get(product));
            System.out.print("Введите новое значение: ");
            f.setInt(product, in.nextInt());
        }
        product.updateValue();
        System.out.println("Изменение прошло успешно");
    }

    public static void main(String[] args) throws IllegalAccessException {
        Scanner in = new Scanner(System.in);
        ArrayList<Product> products = new ArrayList<>();
        Backpack backpack = null;
        int ans = 0;
        do {
            printMenu();
            System.out.print("> ");
            ans = in.nextInt();
            switch (ans) {
                case 1 -> {
                    System.out.print("Введите путь к файлу: ");
                    products = addProductsFromFile(in.next());
                    System.out.println("Предметы добавлены");
                }
                case 2 -> {
                    for (int i = 0; i < products.size(); i++) {
                        System.out.println("Product # " + (i + 1));
                        products.get(i).print();
                    }
                }
                case 3 -> {
                    System.out.print("Введите стоимость: ");
                    int cost = in.nextInt();
                    System.out.print("Введите вес: ");
                    int weight = in.nextInt();
                    products.add(new Product(cost, weight));
                    System.out.println("Предмет добавлен");
                }
                case 4 -> {
                    System.out.print("Введите индекс предмета для изменения от 1 до " + products.size() + ": ");
                    changeProduct(products.get(in.nextInt() - 1));
                }
                case 5 -> {
                    System.out.print("Введите номер в списке от 1 до " + products.size() + ": ");
                    products.remove(in.nextInt() - 1);
                }
                case 6 -> {
                    System.out.print("Введите максимальный вес рюкзака: ");
                    backpack = new Backpack(in.nextInt());
                }
                case 7 -> {
                    try {
                        if (backpack == null)
                            throw new RuntimeException("Рюкзак не инициализирован!");
                        if (products.isEmpty())
                            throw new RuntimeException("Список предметов не инициализирован!");
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    KnapsackSolver solver = new KnapsackSolver(products.toArray(new Product[0]), backpack);
                    System.out.println("1. Решение методом динамического программирования");
                    System.out.println("2. Решение жадным алгоритмом (макс. вес)");
                    System.out.println("3. Решение рекурсией");
                    System.out.print("> ");
                    int localAns = in.nextInt();
                    switch (localAns) {
                        case 1 -> {
                            backpack.clearContents();
                            backpack = solver.findAnsDP(products.size(), backpack.maxWeight);
                        }
                        case 2 -> {
                            backpack.clearContents();
                            backpack = solver.findAnsGreedy(products.size(), backpack.maxWeight);
                        }
                        case 3 -> {
                            backpack.clearContents();
                            backpack = solver.findAnsRec(products.size(), backpack.maxWeight);
                        }
                        default -> System.out.println("Неверная команда!");
                    }
                }
                case 8 -> System.out.println("Выход из программы");
                default -> System.out.println("Неверная команда!");
            }
        } while (ans != 8);
    }
}