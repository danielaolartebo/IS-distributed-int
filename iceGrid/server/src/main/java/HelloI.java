import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.zeroc.Ice.Current;
import java.util.function.DoubleFunction;
import Demo.Hello;

public class HelloI implements Hello
{
    private final ExecutorService threadPool;
    private final int numberOfWorkers;
    private final IntegrationMethod integrationMethod;

    public HelloI(int numberOfWorkers, IntegrationMethod integrationMethod) {
        this.numberOfWorkers = numberOfWorkers;
        this.threadPool = Executors.newFixedThreadPool(numberOfWorkers);
        this.integrationMethod = integrationMethod;
    }

    public double integrate(String method, DoubleFunction<Double> f, double lowerBound, double upperBound, int numIntervals) {
        int numSubIntervals = numberOfWorkers;
        double intervalLength = (upperBound - lowerBound) / numSubIntervals;
        Future<Double>[] results = new Future[numSubIntervals];

        for (int i = 0; i < numSubIntervals; i++) {
            double subLowerBound = lowerBound + i * intervalLength;
            double subUpperBound = subLowerBound + intervalLength;
            results[i] = threadPool.submit(() -> integrateSubInterval(method, f, subLowerBound, subUpperBound, numIntervals / numSubIntervals));
        }

        double result = 0;
        for (int i = 0; i < numSubIntervals; i++) {
            try {
                result += results[i].get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private double integrateSubInterval(String method, DoubleFunction<Double> f, double lowerBound, double upperBound, int numIntervals) {
        switch (method.toLowerCase()) {
            case "trapezium":
                return integrationMethod.integrateTrapezium(f, lowerBound, upperBound, numIntervals);
            case "simpson":
                return integrationMethod.integrateSimpson(f, lowerBound, upperBound, numIntervals);
            case "montecarlo":
                return integrationMethod.montecarlo(f, lowerBound, upperBound);
            default:
                throw new IllegalArgumentException("Unknown integration method: " + method);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        HelloI hello = new HelloI(4, client);
        DoubleFunction<Double> function = x -> x * x; // funcion de ejemplooo
        double result = hello.integrate("simpson", function, 0, 1, 100);
        System.out.println("Integral result: " + result);
    }

}