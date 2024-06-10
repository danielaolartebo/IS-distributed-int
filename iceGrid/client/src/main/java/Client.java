//
// Copyright (c) ZeroC, Inc. All rights reserved.
//

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
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Properties;
import com.zeroc.Ice.Util;
import com.zeroc.IceGrid.QueryPrx;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import Demo.Hello;
import Demo.HelloPrx;
import Demo.WorkerPrx;

public class Client 
{

    private  HelloPrx hello;
    public void main(String[] args)
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

    private  int run(Communicator communicator)
    {
        QueryPrx query = QueryPrx.checkedCast(communicator.stringToProxy("DemoIceGrid/Query"));
        hello = HelloPrx.checkedCast(query.findObjectByType("::Demo::Hello"));

        if(hello == null)
        {
            System.err.println("couldn't find a `::Demo::Hello' object");
            return 1;
        }

        
        com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Worker");
        com.zeroc.Ice.Object object = new WorkerI(hello);
        com.zeroc.Ice.ObjectPrx objPrx= adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("worker"));
        adapter.activate();
        WorkerPrx workerPrx = Demo.WorkerPrx.uncheckedCast(objPrx);

        hello.request(workerPrx, "connect");

        communicator.waitForShutdown();

        return 0;
    }

}