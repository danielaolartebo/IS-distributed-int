import java.io.*;

import com.zeroc.Ice.Object;


public class Server
{
    public static void main(String[] args) throws IOException
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.server",extraArgs))
        {
            if(!extraArgs.isEmpty())
            {
                System.err.println("too many arguments");
                for(String v:extraArgs){
                    System.out.println(v);
                }
            }
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Hello");
            HelloI hi = new HelloI();
            com.zeroc.Ice.Object object = hi;
            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("hello"));
            adapter.activate();
            

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Write the method what you want to use");
            String method = br.readLine();

            System.out.println("Write the function what you want to process");
            String f = br.readLine();

            System.out.println("Write the upper bound");
            double upper = Double.parseDouble(br.readLine());

            System.out.println("Write the lower bound");
            double lower= Double.parseDouble(br.readLine());

            System.out.println("Write the intervals number");
            int numIntervals= Integer.parseInt(br.readLine());

            hi.integrate(method, f, lower, upper, numIntervals);

            communicator.waitForShutdown();
        }
    }

}