//
// Copyright (c) ZeroC, Inc. All rights reserved.
//

import com.zeroc.Ice.Current;

import Demo.Hello;

public class HelloI implements Hello
{
    public HelloI(String name)
    {
        _name = name;
    }

    @Override
    public void processRequest(Current current)
    {
        System.out.println(_name + " says Hello World!");
    }

    @Override
    public void shutdown(Current current)
    {
        System.out.println(_name + " shutting down...");
        current.adapter.getCommunicator().shutdown();
    }

    private final String _name;
}