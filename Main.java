package spbu.edu;

import java.util.Scanner;
import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите границы промежутка, на котором следует выбрать узлы:\n");
        Double a = in.nextDouble();
        Double b = in.nextDouble();
        System.out.print("Введите количество узлов: \n");
        int m = in.nextInt();
        Double [] X = new Double[m];//Ввод узлов
        for (int i = 0; i < m; i++){
            X[i] = a + (b-a)/(m-1) * i;
        }
        DoubleUnaryOperator f = x -> 1 - exp(-x) + pow(x,2);
        Double [] Fx = new Double[m];
        for (int i = 0; i < m; i++){
            Fx[i] = f.applyAsDouble(X[i]);
        }

        List llist = new List(m, X, Fx);
        llist.print();

        //5.1 Интерполяция
        System.out.print("Хотите найти приближенное значение в точке?" + "\n0 -Нет" + "\n1 - Да\n" );
        int newX = in.nextInt();

        while (newX == 1) {
            first:
            {
                System.out.println("Введите точку на отрезке [" + a + ", " + b + "] , в которой надо вычислить занчение полинома: ");
                Double xx = in.nextDouble();
                Double P = llist.interpolation(a,b,xx);
                Double R = abs(f.applyAsDouble(xx) - P);
                System.out.print("Значение полинома наилучшего приближения: " + P + "\nВеличина невязки: " + R);
            }
            System.out.print("Хотите найти приближенное значение в точке?" + "\n0 -Нет" + "\n1 - Да");
            newX = in.nextInt();
        }

        //5.2.1
        System.out.print("Хотите найти приближенное значение агумента?" + "\n0 -Нет" + "\n1 - Да");
        newX = in.nextInt();

        while (newX == 1) {
            first:
            {
                System.out.println("Введите точку на отрезке, в которой надо вычислить значение аргумента: ");
                Double ff = in.nextDouble();
                Double P = llist.inverse_interpolation(a,b,ff);
                Double R = abs(ff - P);
                System.out.print("Значение полинома наилучшего приближения: " + P + "\nВеличина невязки: " + R);
            }
            System.out.print("Хотите найти приближенное значение в точке?" + "\n0 -Нет" + "\n1 - Да");
            newX = in.nextInt();
        }
    }

}
