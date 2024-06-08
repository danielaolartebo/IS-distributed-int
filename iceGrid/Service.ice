module Demo
{
    interface Hello
    {
        idempotent void processRequest();
        void shutdown();
    }
    interface Worker
    {
        void getTask();
    }
}