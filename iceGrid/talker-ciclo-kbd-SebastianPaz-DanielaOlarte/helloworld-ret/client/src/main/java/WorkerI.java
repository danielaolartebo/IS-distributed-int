import java.util.function.DoubleFunction;
import java.util.function.Function;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

import Demo.HelloPrx;
import Demo.Worker;
import Demo.WorkerPrx;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class WorkerI implements Worker {

    private HelloPrx hello;
    private WorkerPrx wk;
    private int id;
    private com.zeroc.Ice.Communicator communicator;

    public void launch(Current current) {

        System.out.println("launch received");
        wgetTask();
    }

    public void wgetTask() {
        hello.getTask(wk);
    }

    public void getResponse(String task, Current current) {

        if (task.equalsIgnoreCase("exit")) {
            shutdown();
            System.out.println("Cliente desconectado");
        } else if (!task.equalsIgnoreCase("There is no more tasks")) {
            System.out.println("2ndo if");
            String[] parts = task.split(":");
            Function<Double, Double> f = parseFunction(parts[1]);
            f = transformFunction(f);
            double lowerBound = Double.parseDouble(parts[2]);
            double upperBound = Double.parseDouble(parts[3]);
            int numIntervals = Integer.parseInt(parts[4]);
            String method = parts[0];

            if (method.equalsIgnoreCase("simpson")) {
                hello.partialResponse(integrateSimpson(f, lowerBound, upperBound, numIntervals));
            } else if (method.equalsIgnoreCase("trapezium")) {
                hello.partialResponse(integrateTrapezium(f, lowerBound, upperBound, numIntervals));
            } else if (method.equalsIgnoreCase("puntomedio")) {
                hello.partialResponse(integratePuntoMedio(f, lowerBound, upperBound, numIntervals));
            } else {
                System.out.println("no encontro a ningun metodo");
            }
        }

    }

    public void getConnect(String connection, Current current) {
        System.out.println(connection);
        id = Integer.parseInt(connection.split(":")[1]);
    }

    private Function<Double, Double> parseFunction(String expression) {
        return (x) -> {
            Expression e = new ExpressionBuilder(expression)
                    .variables("x")
                    .build()
                    .setVariable("x", x);
            return e.evaluate();
        };
    }

    public static Function<Double, Double> transformFunction(Function<Double, Double> f) {
        return (t) -> {
            double x = Math.tan(t);
            return f.apply(x) * (1 / Math.cos(t) / Math.cos(t));
        };
    }

    private double integrateSimpson(Function<Double, Double> f, double a, double b, int n) {
        if (n % 2 != 0) {
            throw new IllegalArgumentException("El número de intervalos n debe ser par.");
        }

        double h = (b - a) / n;
        double sum = f.apply(a) + f.apply(b);

        for (int i = 1; i < n; i += 2) {
            sum += 4 * f.apply(a + i * h);
        }

        for (int i = 2; i < n - 1; i += 2) {
            sum += 2 * f.apply(a + i * h);
        }

        return (h / 3) * sum;
    }

    private double integrateTrapezium(Function<Double, Double> f, double a, double b, int n) {
        double h = (b - a) / n;
        double sum = (f.apply(a) + f.apply(b)) / 2.0;

        for (int i = 1; i < n; i++) {
            sum += f.apply(a + i * h);
        }

        return h * sum;
    }

    private double integratePuntoMedio(Function<Double, Double> f, double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.0;

        for (int i = 0; i < n; i++) {
            double mid = a + (i + 0.5) * h;
            sum += f.apply(mid);
        }

        return h * sum;
    }

    public void setHelloProxy(HelloPrx hello2) {
        hello = hello2;
    }

    public void setWorkerProxy(WorkerPrx wk) {
        this.wk = wk;
    }

    public void setCommunicator(com.zeroc.Ice.Communicator communicator) {
        this.communicator = communicator;
    }

    public void shutdown() {
        try {
            if (wk != null) {
                communicator.shutdown();
                System.out.println("Cliente desconectado");
            }
        } catch (Exception e) {
            System.err.println("Error al desconectar el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
