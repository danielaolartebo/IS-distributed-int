//
// Copyright (c) ZeroC, Inc. All rights reserved.
//

import java.beans.Expression;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.function.DoubleFunction;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.LocalException;
import com.zeroc.Ice.NotRegisteredException;
import com.zeroc.Ice.Util;
import com.zeroc.IceGrid.QueryPrx;
import Demo.HelloPrx;

public class Client implements IntegrationMethod
{

    public static void main(String[] args)
    {
        int status = 0;
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        //
        // Try with resources block - communicator is automatically destroyed
        // at the end of this try block
        //
        try(Communicator communicator = Util.initialize(args, "config.client", extraArgs))
        {

            if(!extraArgs.isEmpty())
            {
                System.err.println("too many arguments");
                status = 1;
            }
            else
            {
                status = run(communicator);
            }
        }

        System.exit(status);
    }

    private static int run(Communicator communicator)
    {
        //
        // First we try to connect to the object with the `hello'
        // identity. If it's not registered with the registry, we
        // search for an object with the ::Demo::Hello type.
        //
        HelloPrx hello = null;
        QueryPrx query = QueryPrx.checkedCast(communicator.stringToProxy("DemoIceGrid/Query"));
        hello = HelloPrx.checkedCast(query.findObjectByType("::Demo::Hello"));
        // try
        // {
        //     hello = HelloPrx.checkedCast(communicator.stringToProxy("hello"));
        // }
        // catch(NotRegisteredException ex)

        // }
        if(hello == null)
        {
            System.err.println("couldn't find a `::Demo::Hello' object");
            return 1;
        }
            
            
        
            hello = HelloPrx.checkedCast(query.findObjectByType("::Demo::Hello"));
        
       

        return 0;
    }

    public static double integrateTrapezium(DoubleFunction<Double> f, double lowerBound, double upperBound, int numIntervals) {
        double sum = 0;
        double h = (upperBound - lowerBound) / numIntervals;

        for (int i = 0; i < numIntervals; i++) {
            double x0 = lowerBound + i * h;
            double x1 = lowerBound + (i + 1) * h;
            double y0 = f.apply(x0);
            double y1 = f.apply(x1);
            double yMid = f.apply(x0 + (h / 2));

            sum += (h / 2) * (y0 + 2 * yMid + y1);
        }

        return sum;
    }

    public void montecarlo(DoubleFunction<Double> f, double lowerBound, double upperBound){
        // Implement Monte Carlo method
        int numSamples = 10000; // Number of random samples
        double sum = 0;
        for (int i = 0; i < numSamples; i++) {
            double x = Math.random() * (upperBound - lowerBound) + lowerBound; // Random value within the bounds
            double y = f.apply(x); // Evaluate function at random point
            sum += y;
        }
        double resultMonteCarlo = sum / numSamples * (upperBound - lowerBound); // Approximate integral
        System.out.println("Monte Carlo method result: " + resultMonteCarlo);
    }


    public static double integrateSimpson(DoubleFunction<Double> f, double lowerBound, double upperBound, int numIntervals) {
        double sum = 0;
        double h = (upperBound - lowerBound) / numIntervals;

        // Evaluate function at endpoints and odd/even midpoints of intervals
        for (int i = 0; i < numIntervals; i++) {
            double x0 = lowerBound + i * h;
            double x1 = lowerBound + (i + 1) * h;
            double y0 = f.apply(x0);
            double y1 = f.apply(x1);

            if (i % 2 == 0) { // Even interval
                sum += h * (y0 + 2 * f.apply(x0 + (h / 2)) + y1);
            } else { // Odd interval
                sum += h * (4 * f.apply(x0 + (h / 2)) + y0 + y1);
            }
        }
        return sum / 6; // Divide by 6 to get the actual integral value
    }
}