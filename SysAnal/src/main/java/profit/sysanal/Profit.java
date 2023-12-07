package profit.sysanal;

public class Profit {

    //lambda - інтенсивність потоку заготівок
    //n - кількість верстатів
    //s- кількість накопичувачів
    //t – середній обробки однієї заготівки на верстаті
    // k - стандартна ємність накопичувача
    // d – прибуток від обробки однієї заготівки на верстаті

    public static int factorial(int num) {
        if (num == 0 || num == 1) {
            return 1;
        } else {
            return num * factorial(num - 1);
        }
    }
   private double Po(double lambda, int n, int s, double t, int k ){
       double result = 1;
       for (int i = 0; i < n; i++) {
           double result1 = (Math.pow(lambda * t, i)/factorial(i));
           double result2 = (Math.pow(lambda * t, n + 1)/(n * factorial(n)));
           double result3 = (1 - Math.pow((lambda*t)/n, k*s))/ (1-(lambda*t)/n);
           result += result1 + result2 * result3;
       }
       return 1/result;
    }

    private double Pmn(double lambda, int n, int s, double t, int k){
        double result = Math.pow(lambda * t, n + k * s)/(Math.pow(n, k * s)* factorial(n));
        return  result * Po(lambda, n, s, t, k);
    }

    private double D(double lambda, int n, int s, double t, int k, double d){
        return lambda * (1 - Pmn(lambda, n, s, t, k)) * d;
    }

    public double calcProfit(double lambda, int n, int s, double t, int k, double d, double vVers, double vNako){
        double profit = D(lambda, n, s, t, k, d) - (vVers * n + vNako * s);
        if (Double.isInfinite(profit) || Double.isNaN(profit)) {
            return 0;
        }
        return profit;
    }
}
