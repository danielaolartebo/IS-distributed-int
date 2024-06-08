//
// Copyright (c) ZeroC, Inc. All rights reserved.
//

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Properties;
import com.zeroc.Ice.Util;

public class Server
{
    public static void main(String[] args)
    {
        int status = 0;
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        //
        // Try with resources block - communicator is automatically destroyed
        // at the end of this try block
        //
        try(Communicator communicator = Util.initialize(args, extraArgs))
        {
            //
            // Install shutdown hook to (also) destroy communicator during JVM shutdown.
            // This ensures the communicator gets destroyed when the user interrupts the application with Ctrl-C.
            //
            Runtime.getRuntime().addShutdownHook(new Thread(() -> communicator.destroy()));

            if(!extraArgs.isEmpty())
            {
                System.err.println("too many arguments");
                status = 1;
            }
            else
            {
                ObjectAdapter adapter = communicator.createObjectAdapter("Hello");
                Properties properties = communicator.getProperties();
                Identity id = Util.stringToIdentity(properties.getProperty("Identity"));
                adapter.add(new HelloI(properties.getProperty("Ice.ProgramName")), id);
                adapter.activate();

                communicator.waitForShutdown();
            }
        }

        System.exit(status);
    }
}