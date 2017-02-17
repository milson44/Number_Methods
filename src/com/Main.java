package com;
import java.util.*;

abstract class F
{
    private double yn; // значение функции на текущем шаге
    private String name; // название функции

    public double getYn() {
        return yn;
    }
    public void setYn(double yn) {
        this.yn = yn;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double val(String name, List<F> fs) // осуществляет поиск значения функции по ее имени
    {
        double result = 0d;
        for(F f: fs)
        {
            if(f.name.equals(name))
            {
                result = f.getYn();
                break;
            }
        }
        return result;
    }
    public abstract double exec(double t, List<F> fs); // вычисление функции
    public abstract F dublicate(); 					  // возвращает свою копию
}

/*
 * Правая часть дифференциального уравнения dx1/dt
 */
class DifX1 extends F
{
   // private final double x1;
  //  private double j; //параметры функции

    public DifX1(String name,  Double y0) {
        this.setName(name);
        //this.j = j;
        this.setYn(y0);
    }
    public double exec(double t, List<F> fs) {
        double x1 = val("x1",fs);
        double x2 = val("x2",fs);
      //  double e = 15;
        return x2- (Math.pow(x1,3)/3 - x1);
    }
    public F dublicate()
    {
        DifX1 result = new DifX1(this.getName(),  this.getYn());
        return result;
    }
}

/*
 * Правая часть дифференциального уравнения dx2/dt
 */
class DifX2 extends F {
    private double n; //параметры функции

    public DifX2(String name, double n, Double y0) {
        this.setName(name);
        this.n = n;
        this.setYn(y0);
    }

    public double exec(double t, List<F> fs) {
        double x1 = val("x1", fs);
         //double x2 = val("x2", fs);
        return -n*x1;
    }

    public F dublicate() {
        DifX2 result = new DifX2(this.getName(), n, this.getYn());
        return result;
    }
}

/*
 * Тестовое уравнение y1
 */
class fy1 extends F
{
    public fy1(String name, Double y0) {
        this.setName(name);
        this.setYn(y0);
    }
    public double exec(double t, List<F> fs) {
        double y1 = val("y1",fs);
        double y2 = val("y2",fs);
        double result = -y2+y1*(Math.pow(y1,2)+Math.pow(y2,2)-1);
        return result;
    }
    public F dublicate()
    {
        fy1 result = new fy1(this.getName(), this.getYn());
        return result;
    }
}

/*
 * Тестовое уравнение y1
 */
class fy2 extends F
{
    public fy2(String name, Double y0) {
        this.setName(name);
        this.setYn(y0);
    }
    public double exec(double t, List<F> fs) {
        double y1 = val("y1",fs);
        double y2 = val("y2",fs);
        double result = y1+y2*(Math.pow(y1,2)+Math.pow(y2,2)-1);
        return result;
    }
    public F dublicate()
    {
        fy2 result = new fy2(this.getName(), this.getYn());
        return result;
    }
}

public class Main {

    public static void main(String[] args) {
        List<Double> testMapX; // точное тестовое решение x(t) задачи Коши
        List<Double> testMapY; // точное тестовое решение y(t) задачи Коши
        List<List<Double>> results; //в этом списке хранятся значения всех вычисляемых функций по методу Рунге-Кутты
        Chm1 chm = new Chm1();
        double h = 0.01;
        testMapX = chm.doTestSolutionX(0d,5d,h);      // получение тестового решения x(t)
        testMapY = chm.doTestSolutionY(0d,5d,h);      // получение тестового решения y(t)
        F f1 = new fy1("y1", 1/Math.sqrt(2));
        F f2 = new fy2("y2", 0d);
        List<F> fs = new ArrayList<F>();         // создание списка
        fs.add(f1);
        fs.add(f2);// и занесение в этот список функций
        results = chm.doApproxSolution(0d, 5d, h, fs);  // получение результатов
      //   Тестовая задача
     /*   for(int i = 0; i < results.get(0).size(); i++){
            System.out.println(chm.difList(testMapX,results.get(0)).get(i));
        }

       System.out.println("--------------------------------------------");

        for(int i = 0; i < results.get(1).size(); i++){
            System.out.println(chm.difList(testMapY,results.get(1)).get(i));
        }

        /*System.out.println(testMapX);
        System.out.println(results.get(0));
        System.out.println(chm.difList(testMapX, results.get(0)));
        System.out.println("--------------------------------------------");
        System.out.println(testMapY);
        System.out.println(results.get(1));
        System.out.println(chm.difList(testMapY, results.get(1)));
*/

        F x1 = new DifX1("x1", 2d);
        F x2 = new DifX2("x2", 0.027, 3.4d);

        List<F> fs2 = new ArrayList<F>();
        fs2.add(x1); fs2.add(x2);
        results = chm.doApproxSolution(0d, 100d, 1, fs2);
        for(int i=0; i< results.get(0).size(); i++)
        {
            System.out.println(results.get(0).get(i));
        }
        System.out.println("--------------------------------------------");

        for(int i=0;i< results.get(1).size(); i++)
        {
            System.out.println(results.get(1).get(i));
        }


    }
}
