//
// Copyright (c) ZeroC, Inc. All rights reserved.
//

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zeroc.Ice.Current;

import Demo.Hello;
import Demo.WorkerPrx;


public class HelloI implements Hello
{
    
    //private final ExecutorService threadPool
    private ExecutorService executor = Executors.newFixedThreadPool(4);
    private Map<Integer, WorkerPrx> lstConnected = new HashMap<>();
    private Queue<String> lstTask = new LinkedList<>();
    private List<Double> lstSolution = new ArrayList<>();
    private int numSubIntervals;
    private boolean access = true;
    private int idWorker = 0;

    public void integrate(String method, String f, double lowerBound, double upperBound, int numIntervals) {

        access = false;
        numSubIntervals = Integer.parseInt(getWorkers(null, "size",0));
        System.out.println(Integer.parseInt(getWorkers(null, "size",0)));

        double intervalLength = (upperBound - lowerBound) / numSubIntervals;
        
        String reply = "";

        for (int i = 0; i < numSubIntervals; i++) {
            double subLowerBound = lowerBound + i * intervalLength;
            double subUpperBound = subLowerBound + intervalLength;

            reply += taskI(method+":"+f+":"+String.valueOf(subLowerBound)+":"+String.valueOf(subUpperBound)+":"+String.valueOf(numIntervals / numSubIntervals));
        }

        System.out.println(reply);

        System.out.println(getWorkers(null, "launch",0));
        
        
    }

    private void printResults(){
        double result = 0;
        for (int i = 0; i < numSubIntervals; i++) {
            try {
                result += lstSolution.get(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(result);
    }

    
    public void partialResponse(double result, Current current) {
        new Thread(()->{
            solution(result);
        }).start();
    }


    
    public void request(WorkerPrx wk, String request, Current current) {

        
        new Thread(()->{
            wk.getConnect(getWorkers(wk, request,0)+":"+idForWorker());
        }).start();
    }


   
    public void shutdown(int id,Current current) {
        new Thread(()->{
            current.adapter.getCommunicator().shutdown();
            System.out.println(getWorkers(null, "disconnect",id));

            if(Integer.parseInt(getWorkers(null, "size",0)) == 0)
                printResults();
        }).start();
        
    }


    
    public void getTask(WorkerPrx wk, Current current) {
        new Thread(()->{
            wk.getResponse(taskI("get"));
        }).start();
        
    }

    private synchronized String getWorkers(WorkerPrx wk,String action,int id){
            
       
            if(action.equalsIgnoreCase("connect")){

                if(access){
                    lstConnected.put(idWorker,wk);
                    return "Worker connected";
                }
                else
                    return "No more access";
            }
            else if(action.equalsIgnoreCase("size")){
                return String.valueOf(lstConnected.size());
            }
            else if(action.equalsIgnoreCase("launch")){

                System.out.println("Inside Launch");
                for(Map.Entry<Integer, WorkerPrx> entry : lstConnected.entrySet()){
                    entry.getValue().launch();
                }

                return "Workers launched";

            }
            else{
                if(lstConnected.containsKey(id)){//
                    lstConnected.remove(id);
                    return "Worker disconnected";
                }
                return "A worker can be disconnected";
            }   
       
            
        
    }

    private synchronized String idForWorker(){
        String id = String.valueOf(idWorker);
        idWorker++;
        return id;

    }

    private synchronized String taskI(String action){

        System.out.println("Inside tasks");

        if(action.equalsIgnoreCase("get")){

            if(!lstTask.isEmpty())
                return lstTask.poll();
            else
                return "There is no more tasks";
            
        }
        else{
            lstTask.add(action);
            System.out.println("t");
            return "Tasks added";
        }
            
    }

    private synchronized void solution(double response){
        
        lstSolution.add(response);
    }

}