import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

// Перемножение цепочки матриц
public class Main {
    private static int[][] matrixMultiply(int[][] A, int[][] B) {
        if (A[0].length != B.length)
            throw new InputMismatchException("Матрицы несовместны!");
        int[][] C = new int[A.length][B[0].length];
        int count = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                C[i][j] = 0;
                for (int k = 0; k < A[0].length; k++) {
                    C[i][j] = C[i][j] + A[i][k] * B[k][j];
                    count++;
                }
            }
        }
        System.out.println("Кол-во скалярных умножений: " + count);
        return C;
    }

    private static void printOrder(List<List<Integer>> s, int i, int j) {
        if (i == j) {
            System.out.print("A" + i);
        } else {
            System.out.print("(");
            printOrder(s, i, s.get(i).get(j));
            printOrder(s, s.get(i).get(j) + 1, j);
            System.out.print(")");
        }
    }

    public static void main(String[] args) {
        int[] p = {10, 100, 5, 50, 150};
        int n = p.length - 1;

        // инициализация списков m и s
        List<List<Integer>> m = new ArrayList<>();
        List<List<Integer>> s = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            m.add(new ArrayList<>());
            s.add(new ArrayList<>());
            for (int a = 0; a < n; a++) {
                m.get(i).add(0);
                s.get(i).add(0);
            }
        }

        // итерационный алгоритм
        int j;
        for (int l = 1; l < n; l++) {
            for (int i = 0; i < n - l; i++) {
                j = i + l;
                m.get(i).set(j, Integer.MAX_VALUE);
                for (int k = i; k < j; k++) {
                    int q = m.get(i).get(k) + m.get(k + 1).get(j) + p[i] * p[k + 1] * p[j + 1];
                    if (q < m.get(i).get(j)) {
                        m.get(i).set(j, q);
                        s.get(i).set(j, k);
                    }
                }
            }
        }

        System.out.println(m.get(0).get(n-1));
        printOrder(s, 0, n - 1);
    }
}