import java.io.*;

import com.zeroc.Ice.Object;

public class Server {
    public static void main(String[] args) throws IOException {
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "config.server",
                extraArgs)) {
            if (!extraArgs.isEmpty()) {
                System.err.println("too many arguments");
                for (String v : extraArgs) {
                    System.out.println(v);
                }
            }
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Hello");
            HelloI hi = new HelloI();
            com.zeroc.Ice.Object object = hi;
            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("hello"));
            adapter.activate();
            boolean contador = true;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while (contador) {
                System.out.println("Write the method you want to use (or type 'exit' to quit):");
                String method = br.readLine();
                if (method.equalsIgnoreCase("exit")) {
                    contador = false;
                    hi.shutdownAllClients();
                    break;
                }

                System.out.println("Write the function you want to process:");
                String f = br.readLine();

                System.out.println("Write the upper bound:");
                double upper = Double.parseDouble(br.readLine());

                System.out.println("Write the lower bound:");
                double lower = Double.parseDouble(br.readLine());

                System.out.println("Write the number of intervals:");
                int numIntervals = Integer.parseInt(br.readLine());

                if (method.equalsIgnoreCase("simpson") || method.equalsIgnoreCase("trapezium")
                        || method.equalsIgnoreCase("puntomedio")) {
                    hi.integrate(method, f, lower, upper, numIntervals);
                } else {
                    System.out.println("Type a valid option: Simpson - Trapezium - Puntomedio");
                }
            }

            communicator.waitForShutdown();
        }
    }

}