public class Starter
{
    public static void main(String[] args)
    {
        OperationsManagement operations = new OperationsManagement();
        MenuManager menu = new MenuManager(operations);
        menu.start();
    }
}
