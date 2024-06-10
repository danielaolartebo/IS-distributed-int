module Demo
{
   
    interface Worker
    {
        void launch();
    }
    
    interface Hello
    {
        void partialResponse(double result);
        string request(Worker* wk, string request);
        string getTask();
        void shutdown();
    }
}