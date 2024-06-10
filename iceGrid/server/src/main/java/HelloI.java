//
// Copyright (c) ZeroC, Inc. All rights reserved.
//

import com.zeroc.Ice.Current;

import Demo.Hello;
import Demo.WorkerPrx;

public class HelloI implements Hello
{
    public HelloI(String name)
    {
        _name = name;
    }

    
    private final String _name;


    @Override
    public void partialResponse(double result, Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'partialResponse'");
    }


    @Override
    public String request(WorkerPrx wk, String request, Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'request'");
    }


    @Override
    public void shutdown(Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shutdown'");
    }


    @Override
    public String getTask(Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTask'");
    }

}