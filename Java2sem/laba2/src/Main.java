// подсчет в префиксной
// подсчет в постфиксной
// перевод из инфиксной в префиксную

import java.util.*;

public class Main {
    private static boolean isOperator(String item) {
        HashSet<String> operators = new HashSet<>();
        operators.add("*");
        operators.add("/");
        operators.add("+");
        operators.add("-");
        operators.add("exp");
        return operators.contains(item);
    }

    private static int precedence(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "exp" -> 3;
            default -> -1;
        };
    }

    private static int getType(String operand) {
        HashMap<String, Integer> types = new HashMap<>();
        types.put("exp", 1);
        types.put("*", 2);
        types.put("/", 2);
        types.put("+", 2);
        types.put("-", 2);
        return types.get(operand);
    }

    private static String reverse(String str) {
        StringBuilder out = new StringBuilder();
        char[] chars = str.toCharArray();

        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == ')') {
                out.append('(');
            } else if (chars[i] == '(') {
                out.append(')');
            } else if (chars[i] != ' ') {
                StringBuilder item = new StringBuilder();
                while (i >= 0 && chars[i] != ' ' && chars[i] != '(') {
                    item.append(chars[i]);
                    i--;
                }
                i++;
                out.append(item.reverse());
            } else {
                out.append(chars[i]);
            }
        }

        return out.toString();
    }

    private static String infixToPostfix(String infix) {
        Stack<String> opstack = new Stack<>();
        StringBuilder out = new StringBuilder();
        char[] tokens = infix.toCharArray();

        for (int i = 0; i < infix.length(); i++) {
            if (tokens[i] == ' ') {
                continue;
            } else if (tokens[i] == '(') {
                opstack.push(Character.toString(tokens[i]));
            } else if (tokens[i] == ')') {
                while (!opstack.isEmpty() && !opstack.peek().equals("(")) {
                    out.append(opstack.pop() + " ");
                }
                opstack.pop();
            } else {
                String item = "";
                while (i < tokens.length && tokens[i] != ')' && tokens[i] != ' ') {
                    item += tokens[i];
                    i++;
                }
                i--;
                if (isOperator(item)) {
                    while (!opstack.isEmpty() && precedence(opstack.peek()) >= precedence(item))
                        out.append(opstack.pop() + " ");
                    opstack.push(item);
                } else {
                    out.append(item + " ");
                }
            }
        }

        while (!opstack.isEmpty()) {
            out.append(opstack.pop() + " ");
        }
        out.deleteCharAt(out.length() - 1);
        return out.toString();
    }

    private static String infixToPrefix(String infix) {
        String postfix = infixToPostfix(infix);
        return postfixToPrefix(postfix);
    }

    private static String postfixToPrefix(String postfix) {
        Stack<String> opstack = new Stack<>();
        StringBuilder out = new StringBuilder();
        char[] tokens = postfix.toCharArray();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;
            String item = "";
            while (i < tokens.length && tokens[i] != ' ') {
                item += tokens[i];
                i++;
            }
            i--;
            if (isOperator(item)) {
                String temp = "";
                int type = getType(item);
                temp += item + " ";
                List<String> list = new ArrayList<>();
                for (int j = 0; j < type; j++) {
                    list.add(0, opstack.pop());
                }
                for (String it : list) {
                    temp += it + " ";
                }
                opstack.push(temp);
            } else {
                opstack.push(item);
            }
        }

        while (!opstack.isEmpty()) {
            out.append(opstack.pop());
        }

        return out.toString();
    }

    private static int infixCalc(String exp) {
        ArrayDeque<Integer> operand_stack = new ArrayDeque<>();
        ArrayDeque<String> operator_stack = new ArrayDeque<>();
        char[] tokens = exp.toCharArray();

        for (int i = 0; i < tokens.length; i++) {
            if (Character.isDigit(tokens[i])) {
                int num = 0;
                while (i < tokens.length && Character.isDigit(tokens[i])) {
                    num = num * 10 + (tokens[i] - '0');
                    i++;
                }
                i--;
                operand_stack.push(num);
            } else if (tokens[i] == '(') {
                operator_stack.push(Character.toString(tokens[i]));
            } else if (tokens[i] == ')') {
                while (operator_stack.peek().charAt(0) != '(') {
                    int ans = calc(operator_stack.pop(), operand_stack, 0);
                    operand_stack.push(ans);
                }
                operator_stack.pop();
            } else if (tokens[i] == ' ') {
                continue;
            } else {
                String operator = "";
                while (i < tokens.length && !Character.isDigit(tokens[i]) && tokens[i] != ' ') {
                    operator += tokens[i];
                    i++;
                }
                i--;
                while (!operator_stack.isEmpty() && precedence(operator) <= precedence(operator_stack.peek())) {
                    int ans = calc(operator_stack.pop(), operand_stack, 0);
                    operand_stack.push(ans);
                }
                operator_stack.push(operator);
            }
        }

        while (!operator_stack.isEmpty()) {
            operand_stack.push(calc(operator_stack.pop(), operand_stack, 0));
        }
        return operand_stack.pop();
    }

    private static int prefixCalc(String prefix) {
        ArrayDeque<Integer> opstack = new ArrayDeque<>();
        String[] items = reverse(prefix).split(" ");
        for (String item : items) {
            if (item.isEmpty())
                continue;
            if (isOperator(item)) {
                opstack.push(calc(item, opstack, 1));
            } else {
                opstack.push(Integer.parseInt(item));
            }
        }
        return opstack.pop();
    }

    private static int postfixCalc(String postfix) {
        ArrayDeque<Integer> opstack = new ArrayDeque<>();
        String[] items = postfix.split(" ");
        for (String item : items) {
            if (item.isEmpty())
                continue;
            if (isOperator(item)) {
                opstack.push(calc(item, opstack, 0));
            } else {
                opstack.push(Integer.parseInt(item));
            }
        }
        return opstack.pop();
    }

    private static int calc(String operator, ArrayDeque<Integer> opstack, int r) {
        ArrayList<Integer> oplist = new ArrayList<>();
        int type = getType(operator);
        if (r == 0) {
            for (int i = 0; i < type; i++) {
                oplist.add(0, opstack.pop());
            }
        } else {
            for (int i = 0; i < type; i++) {
                oplist.add(opstack.pop());
            }
        }
        switch (operator) {
            case "*" -> {
                return oplist.get(0) * oplist.get(1);
            }
            case "/" -> {
                return oplist.get(0) / oplist.get(1);
            }
            case "+" -> {
                return oplist.get(0) + oplist.get(1);
            }
            case "-" -> {
                return oplist.get(0) - oplist.get(1);
            }
            case "exp" -> {
                return (int) Math.exp(oplist.get(0));
            }
            default -> System.out.println("Неверный оператор");
        }
        return 0;
    }

    private static String getExpression() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите выражение в инфиксной форме: ");
        return in.nextLine();
    }

    private static void printMenu() {
        System.out.println("Меню");
        System.out.println("1. Ввести выражение");
        System.out.println("2. Посчитать выражение в инфиксной форме");
        System.out.println("3. Посчитать выражение в постфиксной форме");
        System.out.println("4. Посчитать выражение в префиксной форме");
        System.out.println("5. Выход");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String infix = "";
        int ans = 0;
        do {
            printMenu();
            System.out.print("> ");
            if (in.hasNextInt()) {
                ans = in.nextInt();
            }
            switch (ans) {
                case 1 -> infix = getExpression();
                case 2 -> {
                    System.out.println("Выражение: \t" + infix);
                    System.out.println("Результат: \t" + infixCalc(infix));
                }
                case 3 -> {
                    String postfix = infixToPostfix(infix);
                    System.out.println("Выражение: \t" + postfix);
                    System.out.println("Результат: \t" + postfixCalc(postfix));
                }
                case 4 -> {
                    String prefix = infixToPrefix(infix);
                    System.out.println("Выражение: \t" + prefix);
                    System.out.println("Результат: \t" + prefixCalc(prefix));
                }
                case 5 -> System.out.println("Выход из программы");
                default -> System.out.println("Неверная команда!");
            }
        } while (ans != 5);
    }
}