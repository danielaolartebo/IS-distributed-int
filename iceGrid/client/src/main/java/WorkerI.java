import java.util.function.DoubleFunction;

import com.zeroc.Ice.Current;

import Demo.HelloPrx;
import Demo.WorkerPrx;
import Demo.Worker;

public class WorkerI implements Worker{

    private final HelloPrx hello;

    public WorkerI(HelloPrx hello){

        this.hello = hello;
    }
    

    @Override
    public  void launch(Current current){

        boolean shutdwn = false;
        String task = "";
        
        while(!shutdwn){
            //task = hello.getTask();
        
            if(!task.equalsIgnoreCase("There is no more tasks")){
                String[] parts = task.split(" ");
                DoubleFunction<Double> f= (x) -> Double.parseDouble(parts[1].replace("x", String.valueOf(x)));

                double lowerBound = Double.parseDouble(parts[2]);
                double upperBound = Double.parseDouble(parts[3]);
                int numIntervals =  Integer.parseInt(parts[4]);

                switch (parts[0]) {
                    case "trapezium":
                        hello.partialResponse(integrateTrapezium(f, lowerBound, upperBound, numIntervals));
                    case "simpson":
                        hello.partialResponse(integrateSimpson(f, lowerBound, upperBound, numIntervals));
                    case "montecarlo":
                        hello.partialResponse(montecarlo(f, lowerBound, upperBound));
                    default:
                        throw new IllegalArgumentException("Unknown integration method: " + parts[0]);
                }
            }else{
                hello.shutdown();
                shutdwn = true;
            }
                
        }
        
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

    public double montecarlo(DoubleFunction<Double> f, double lowerBound, double upperBound){
        // Implement Monte Carlo method
        int numSamples = 10000; // Number of random samples
        double sum = 0;
        for (int i = 0; i < numSamples; i++) {
            double x = Math.random() * (upperBound - lowerBound) + lowerBound; // Random value within the bounds
            double y = f.apply(x); // Evaluate function at random point
            sum += y;
        }
        double resultMonteCarlo = sum / numSamples * (upperBound - lowerBound); // Approximate integral
        return resultMonteCarlo;
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
