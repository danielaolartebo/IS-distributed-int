module Demo
{
   
    interface Worker
    {
        void launch();
        void getResponse(string task);
        void getConnect(string connection);

    }
    
    interface Hello
    {
        void partialResponse(double result);
        void request(Worker* wk, string request);
        void getTask(Worker* wk);
        void shutdown(int id);
    }
}