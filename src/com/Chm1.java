package com;

import java.util.ArrayList;
import java.util.List;

public class Chm1 {
    /*
     * Точное решение тестового уравнения y1
     */
    public List<Double> doTestSolutionX(double a, double b, double h) {
        int n = (int) Math.round((b - a) / h);
        double x;
        List<Double> result = new ArrayList<Double>();
        for (int i = 0; i <= n; i++) {
            x = a + i * h;
            result.add(Math.cos(x) / Math.sqrt(1 + Math.pow((Math.E), 2 * x)));

        }
        return result;
    }

    /*
     * Точное решение тестового уравнения y2
     */
    public List<Double> doTestSolutionY(double a, double b, double h) {
        int n = (int) Math.round((b - a) / h);
        double x;
        List<Double> result = new ArrayList<Double>();

        for (int i = 0; i <= n; i++) {
            x = a + i * h;
            result.add(Math.sin(x)/Math.sqrt(1+Math.pow((Math.E),2*x)));
        }
        return result;
    }

    /*
     * Функция копирования списка(со всеми объектами)
     */
    private List<F> copyList(List<F> l) {
        List<F> result = new ArrayList<F>();
        for (int i = 0; i < l.size(); i++) {
            result.add(l.get(i).dublicate());
        }
        return result;
    }


    public List<F> getK2(List<F> fs, double[] k1, double h) {

        List<F> fs2 = copyList(fs);
        int i = 0;
        for (F f : fs2) {
            f.setYn(f.getYn() + h * k1[i] / 2);
            i++;
        }
        return fs2;
    }

    public List<F> getK3(List<F> fs, double[] k1, double[] k2, double h) {

        List<F> fs2 = copyList(fs);
        int i = 0;
        for (F f : fs2) {
            f.setYn(f.getYn() + h * k2[i]/2);
            i++;
        }
        return fs2;
    }

    public List<F> getK4(List<F> fs, double[] k1, double[] k2, double[] k3, double h) {

        List<F> fs2 = copyList(fs);
        int i = 0;
        for (F f : fs2) {
            f.setYn(f.getYn() + h * k3[i] );
            i++;
        }
        return fs2;
    }

    public List<List<Double>> doApproxSolution(double a, double b, double h, List<F> fs) {
        double k1[], k2[], k3[], k4[];                                // вспомогательные значения по методу Рунге-Кутты
        k1 = new double[fs.size()];
        k2 = new double[fs.size()];
        k3 = new double[fs.size()];
        k4 = new double[fs.size()];
        int n = (int) Math.round((b - a) / h);                            // число точек, в которых вычисляется значение функций из fs
        List<List<Double>> result = new ArrayList<List<Double>>();    // матрица результатов
        double tn;                                                    // текущая точка, в которой вычисляются функции fs
        for (int i = 0; i < fs.size(); i++) {
            List<Double> column = new ArrayList<Double>();            // создание нового столбца
            column.add(fs.get(i).getYn());                            // добавление начального значения
            result.add(column);
        }
        for (int i = 0; i < n; i++) {
            List<F> temp = copyList(fs);
            tn = a + i * h;

            for (int j = 0; j < fs.size(); j++) {
                F f = fs.get(j);
                k1[j] = f.exec(tn, temp);                            // вычисление k1 для каждой функции
            }

            for (int j = 0; j < fs.size(); j++) {
                F f = fs.get(j);
                k2[j] = f.exec(tn + h / 2, getK2(temp, k1, h));
            }

            for (int j = 0; j < fs.size(); j++) {
                F f = fs.get(j);
                k3[j] = f.exec(tn +  h / 2, getK3(temp, k1, k2, h));
            }

            for (int j = 0; j < fs.size(); j++) {
                F f = fs.get(j);
                k4[j] = f.exec(tn + h, getK4(temp, k1, k2, k3, h));
            }

            for (int j = 0; j < fs.size(); j++) {
                F f = fs.get(j);
                f.setYn(f.getYn() + h * (k1[j] + 2 * k2[j] + 2 * k3[j] + k4[j]) / 6);
                result.get(j).add(f.getYn());
            }
        }
        return result;
    }

    /*
     * Функция рассчета погрешности
     */
    public List<Double> difList(List<Double> list1, List<Double> list2) {
        List<Double> result = new ArrayList<Double>();
        int n = list1.size();
        for (int i = 0; i < n; i++)
            result.add(Math.abs(list1.get(i) - list2.get(i)));
        return result;
    }


}
