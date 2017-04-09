package spbu.edu;

import java.util.Scanner;
import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 * Created by karl-crl on 09.04.17.
 */
public class List {
    private final int m;
    private final Double [] X;
    private final Double [] Fx;

    public List(int m, Double[] X, Double[] Fx) {
        this.m = m;
        this.X = X;
        this.Fx = Fx;
    }

    void print(){
        //Таблица узлов
        System.out.println("Таюлица узлов для функции f(x) = 1 - exp(-x) + x^2");
        System.out.println("╔" + X[0] + "╦" + Fx[0] + "╗");
        for (int i = 1; i < m - 1; i++){
            System.out.println("╠" + X[i] + "║" + Fx[i] + "╣");
        }
        System.out.println("" + X[m-1] + "╩" + Fx[m-1] + "╝");
    }

    void interpolation(Double a, Double b, DoubleUnaryOperator f){
        Scanner in = new Scanner(System.in);
        System.out.print("Хотите найти приближенное значение в точке?" + "\n0 -Нет" + "\n1 - Да\n" );
        int newX = in.nextInt();

        while (newX == 1){
            first:{
                System.out.println("Введите точку на отрезке [" + a + ", " + b + "] , в которой надо вычислить занчение полинома: ");
                Double xx = in.nextDouble();
                if (xx < a){
                    System.out.println("Выход за границы отрезка");
                    break first;
                }
                if (xx > b){
                    System.out.println("Выход за границы отрезка");
                    break first;
                }

                //Степень Р_n
                Double P = Fx[0];
                System.out.print("Введите степень полинома наилучшего приближения (не выше " + (m - 1) + "): ");
                int n = in.nextInt();
                if (n >  m - 1){
                    System.out.println("Ошибка в степени полинома");
                    break first;
                }

                Double h = (b-a)/(m-1);
                //System.out.print("\n");
                if (xx < (a +  h) ) {
                    Double t = (xx - a)/h;

                    for (int i = 1; i <= n ; i++ ){
                        P += fin_dif(i, 0, Fx, X)*mult(i, t)/factorial(i);
                    }


                }else if (xx > (b - h ) ){
                    Double t = (xx - b)/h;
                    P = Fx[m-1];

                    for (int i = 1; i <= n ; i++ ){
                        P += fin_dif(i, m-1-i, Fx, X)*mult(i, t)/factorial(i);
                    }
                }else {
                    Double z = X[0];
                    int i0 = 0;
                    for (int i = 1; i < m; i++){
                        if (comparator(X[i],z,xx)){
                            z = X[i];
                            P = Fx[i];
                            i0 = i;
                        }
                    }
                    Double t = (xx - z)/h;

                    for (int i = 1; i <= n ; i++ ){
                        int k = (int) ((i+1)/2);
                        P += fin_dif(i,i0 - k,Fx,X) * (multG(i,t))/factorial(i);
                    }
                }

                Double R = abs(f.applyAsDouble(xx) - P);
                System.out.print("Значение полинома наилучшего приближения: " + P + "\nВеличина невязки: " + R);
            }
            System.out.print("Хотите найти приближенное значение в точке?" + "\n0 -Нет" + "\n1 - Да" );
            newX = in.nextInt();
        }
    }

    static boolean comparator (Double x1, double x2, double a){
        if ( (abs(x1 - a)) < (abs(x2 - a)) ){
            return true;
        }
        else {
            return false;
        }
    }

    static double fin_dif (int l, int i, Double [] Fx, Double [] X){  //i - номер f_i
        Double f;
        if (l == 1){
            f = (Fx[i+1] - Fx[i]);
        }
        else{
            f = (fin_dif(l-1, i + 1, Fx, X) - fin_dif(l-1, i, Fx, X));
        }
        return f;
    }

    static double mult (int l, Double t){
        Double m = 1.0;
        for (int i = 0; i < l; i++){
            m = m*(t-i);
        }
        return m;
    }

    static double multG (int l, Double t){
        Double m = 1.0;
        int g = 0;
        for (int i = 0; i < l; i++){
            g = (int) ((i+1)/2);
            m = m*(t + pow(-1,i)*g);
        }
        return m;
    }

    static int factorial (int x) {
        int fact=1;
        for (int i=2; i<=x;i++) // на 1 умножать смысла нет, начинаем с 2. Умножаем, пока i<= числу, для которого
            fact*=i;            // вычисляется факториал. fact*=i это сокращение для fact=fact*i
        return fact;        // возвращаем методу значение факториала, который мы только что вычислили
    }

}
