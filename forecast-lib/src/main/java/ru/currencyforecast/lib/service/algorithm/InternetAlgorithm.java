package ru.currencyforecast.lib.service.algorithm;

import ru.currencyforecast.lib.domain.CurrencyData;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static ru.currencyforecast.lib.common.Constant.ALG_INTERNET_BASE;

/**
 * Алгоритм “из интернета”. Экстраполируем по данным последнего месяца.
 * <a href="https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/LinearRegression.java.html">ссылка на алгоритм</a>
 */
public class InternetAlgorithm extends Algorithm {
    public InternetAlgorithm() {
        super(ALG_INTERNET_BASE);
    }

    @Override
    protected void doForecast(String cdx, int nominal, LocalDate nextDay, LinkedList<CurrencyData> processList, List<CurrencyData> resultList, int periodDays) {
        for (int i = 0; i < periodDays; i++) {
            nextDay = nextDay.plusDays(1);
            double cost = getLinerRegressionPrice(processList);
            CurrencyData currencyData = new CurrencyData(nominal, nextDay, cost, cdx);
            processList.addFirst(currencyData);
            resultList.add(currencyData);
        }
    }

    private double getLinerRegressionPrice(List<CurrencyData> processList) {
        List<CurrencyData> lastValues = processList.subList(0, Math.min(processList.size(), ALG_INTERNET_BASE));
        return getPredict(lastValues);
    }

    private double getPredict(List<CurrencyData> processList) {
        double[] y = processList.stream()
                .mapToDouble(CurrencyData::getCurs)
                .toArray();
        double[] x = processList.stream()
                .mapToDouble(data -> data.getData().getDayOfMonth())
                .toArray();
        return new LinearRegression(x, y).predict(x[x.length - 1] + 1);
    }

    /**
     * The {@code LinearRegression} class performs a simple linear regression
     * on an set of <em>n</em> data points (<em>y<sub>i</sub></em>, <em>x<sub>i</sub></em>).
     * That is, it fits a straight line <em>y</em> = &alpha; + &beta; <em>x</em>,
     * (where <em>y</em> is the response variable, <em>x</em> is the predictor variable,
     * &alpha; is the <em>y-intercept</em>, and &beta; is the <em>slope</em>)
     * that minimizes the sum of squared residuals of the linear regression model.
     * It also computes associated statistics, including the coefficient of
     * determination <em>R</em><sup>2</sup> and the standard deviation of the
     * estimates for the slope and <em>y</em>-intercept.
     *
     * @author Robert Sedgewick
     * @author Kevin Wayne
     */
    private static class LinearRegression {
        private final double intercept;
        private final double slope;
        private final double r2;
        private final double svar0;
        private final double svar1;

        /**
         * Performs a linear regression on the data points {@code (y[i], x[i])}.
         *
         * @param x the values of the predictor variable
         * @param y the corresponding values of the response variable
         * @throws IllegalArgumentException if the lengths of the two arrays are not equal
         */
        public LinearRegression(double[] x, double[] y) {
            if (x.length != y.length) {
                throw new IllegalArgumentException("array lengths are not equal");
            }
            int n = x.length;

            // first pass
            double sumx = 0.0;
            double sumy = 0.0;
            double sumx2 = 0.0;
            for (int i = 0; i < n; i++) {
                sumx += x[i];
                sumx2 += x[i] * x[i];
                sumy += y[i];
            }
            double xbar = sumx / n;
            double ybar = sumy / n;

            // second pass: compute summary statistics
            double xxbar = 0.0;
            double yybar = 0.0;
            double xybar = 0.0;
            for (int i = 0; i < n; i++) {
                xxbar += (x[i] - xbar) * (x[i] - xbar);
                yybar += (y[i] - ybar) * (y[i] - ybar);
                xybar += (x[i] - xbar) * (y[i] - ybar);
            }
            slope = xybar / xxbar;
            intercept = ybar - slope * xbar;

            // more statistical analysis
            double rss = 0.0;      // residual sum of squares
            double ssr = 0.0;      // regression sum of squares
            for (int i = 0; i < n; i++) {
                double fit = slope * x[i] + intercept;
                rss += (fit - y[i]) * (fit - y[i]);
                ssr += (fit - ybar) * (fit - ybar);
            }

            int degreesOfFreedom = n - 2;
            r2 = ssr / yybar;
            double svar = rss / degreesOfFreedom;
            svar1 = svar / xxbar;
            svar0 = svar / n + xbar * xbar * svar1;
        }

        /**
         * Returns the standard error of the estimate for the intercept.
         *
         * @return the standard error of the estimate for the intercept
         */
        public double interceptStdErr() {
            return Math.sqrt(svar0);
        }

        /**
         * Returns the expected response {@code y} given the value of the predictor
         * variable {@code x}.
         *
         * @param x the value of the predictor variable
         * @return the expected response {@code y} given the value of the predictor
         * variable {@code x}
         */
        public double predict(double x) {
            return slope * x + intercept;
        }

        /**
         * Returns the standard error of the estimate for the slope.
         *
         * @return the standard error of the estimate for the slope
         */
        public double slopeStdErr() {
            return Math.sqrt(svar1);
        }

        /**
         * Returns a string representation of the simple linear regression model.
         *
         * @return a string representation of the simple linear regression model,
         * including the best-fit line and the coefficient of determination
         * <em>R</em><sup>2</sup>
         */
        public String toString() {
            return String.format("%.2f n + %.2f", slope(), intercept()) +
                    "  (R^2 = " + String.format("%.3f", R2()) + ")";
        }

        /**
         * Returns the slope &beta; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
         *
         * @return the slope &beta; of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>
         */
        public double slope() {
            return slope;
        }

        /**
         * Returns the <em>y</em>-intercept &alpha; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
         *
         * @return the <em>y</em>-intercept &alpha; of the best-fit line <em>y = &alpha; + &beta; x</em>
         */
        public double intercept() {
            return intercept;
        }

        /**
         * Returns the coefficient of determination <em>R</em><sup>2</sup>.
         *
         * @return the coefficient of determination <em>R</em><sup>2</sup>,
         * which is a real number between 0 and 1
         */
        public double R2() {
            return r2;
        }

    }

    /*
     *  Copyright 2002-2020, Robert Sedgewick and Kevin Wayne.
     *
     *  This file is part of algs4.jar, which accompanies the textbook
     *
     *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
     *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
     *      http://algs4.cs.princeton.edu
     *
     *
     *  algs4.jar is free software: you can redistribute it and/or modify
     *  it under the terms of the GNU General Public License as published by
     *  the Free Software Foundation, either version 3 of the License, or
     *  (at your option) any later version.
     *
     *  algs4.jar is distributed in the hope that it will be useful,
     *  but WITHOUT ANY WARRANTY; without even the implied warranty of
     *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     *  GNU General Public License for more details.
     *
     *  You should have received a copy of the GNU General Public License
     *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
     ******************************************************************************/

}
