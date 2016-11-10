package com.company;

import java.util.Scanner;

/**
 * Created by Steve Zelek on 11/3/2016.
 */
//class to hold the data points and search for angle phi
public class Simulator {

    //binary search initial bounds
    double lowerbound = 0;
    double upperbound = 90;

    double depth, height, xdistance, n1, n2;
    double phi = -1;

    public Simulator(double depth, double height, double xdistance, double n1, double n2) {
        this.depth = depth;
        this.height = height;
        this.xdistance = xdistance;
        this.n1 = n1;
        this.n2 = n2;
    }

    //incremental binary search for phi:
    //----------------------------------
    //bounds start at [0, 90]
    // 1) use the midpoint of the bounds as a test value for phi
    // 2) use trig to determine the height the laser ends up at for a given value of phi
    // 3a) if laser goes HIGHER than the plane, set current phi as the new upperbound,
    //      because the correct value must be lower
    // 3b) if laser goes LOWER than the plane, set current phi as the new lowerbound
    //      because the correct value must be higher

    //eliminates half the possible remaining angles on each trial
    //repeat til the first 2 decimal places of phi do not change*

    //*the problem only wants 2 places of precision

    public double runTrial() {
        phi = (lowerbound + upperbound) / 2;

        double trial = xdistance - depth / Math.tan(Math.toRadians(phi));
        trial *= Math.tan(Math.toRadians(90) - Math.asin(n2 / n1 * Math.cos(Math.toRadians(phi))));

        if (trial < height) {
            lowerbound = phi;
        } else if (trial > height) {
            upperbound = phi;
        }

        return phi;
    }
}

class Solution {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        double depth = scan.nextDouble();
        double height = scan.nextDouble();
        double xdist = scan.nextDouble();
        double n1 = scan.nextDouble();
        double n2 = scan.nextDouble();

        while (!(depth == 0 && height == 0 && xdist == 0 && n1 == 0 && n2 == 0)){

            Simulator s = new Simulator(depth, height, xdist, n1, n2);

            String phiResult = String.format("%.2f", s.runTrial());
            String nextPhiResult = String.format("%.2f", s.runTrial());

            //refine til the angle is not changing significantly
            while (!phiResult.equals(nextPhiResult)){
                phiResult = nextPhiResult;
                nextPhiResult = String.format("%.2f", s.runTrial());
            }

            System.out.println(phiResult);

            depth = scan.nextDouble();
            height = scan.nextDouble();
            xdist = scan.nextDouble();
            n1 = scan.nextDouble();
            n2 = scan.nextDouble();
        }


    }
}
