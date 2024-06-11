import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import com.zeroc.Ice.Object;

import Demo.WorkerPrx;
import Demo.HelloPrx;

public class Client
{
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.client",extraArgs))
        {
            //com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -p 10000");
            HelloPrx hello = Demo.HelloPrx
                    .checkedCast(communicator.propertyToProxy("Hello.Proxy"));
                    
            if(hello == null)
            {
                throw new Error("Invalid proxy");
            }
            

            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Worker");
            WorkerI wk = new WorkerI();
            com.zeroc.Ice.Object object = wk;
            com.zeroc.Ice.ObjectPrx objPrx= adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("worker"));
            adapter.activate();
            WorkerPrx workerPrx = WorkerPrx.uncheckedCast(objPrx);
            wk.setHelloProxy(hello);
            wk.setWorkerProxy(workerPrx);

            System.out.println(workerPrx);

            hello.request(workerPrx, "connect");
            communicator.waitForShutdown();
        }
    }

}