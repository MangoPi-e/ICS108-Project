/*This file includes the Menu abstract class with its children (Aka all menus)*/
import java.util.Scanner;
public abstract class Menu
{
    public static Scanner reader = new Scanner(System.in);
    String MenuName;
    Menu[] SelectionPool;

    public Menu(String Name)
    {
        this.MenuName = Name;
    }

    public int GetInput() //takes the user input and validates it
    {
        int Choice = 0;
        //input validation
        var Flag = true;
        while(Flag)
        {
            try
            {
                Choice = reader.nextInt();
                if(Choice >= SelectionPool.length)
                {
                    throw new ArrayIndexOutOfBoundsException();
                }
                else
                {
                    Flag = false;
                }
            }catch (Exception e)
            {
                System.out.println("Invalid Input!!!!");
                System.out.print("Try Again : \n||| ");
            }
        }
        return Choice;
    }

    abstract public void start(Menu Prev);//Move to next Menu/instruction after confirmation

    public boolean confirm()
    {
        System.out.println("Are you sure? : (Y/n)");//Asks the user to confirm (with Y and N) (Taking Y as default)
        String Choice = reader.nextLine();
        return !Choice.equalsIgnoreCase("n");
    }

    public void view()
    {
        System.out.println(MenuName + " : ");
        for(int i=0; i<SelectionPool.length;i++)
        {
            System.out.println(i +" : "+SelectionPool[i].MenuName);

        }
    }
}



class AddEvent extends Menu
{
    AddEvent()
    {
        super("Add Event");
    }

    @Override
    public void start(Menu Prev)
    {
        view();
        System.out.print("Enter Your choice : \n||| ");
        super.GetInput();
    }
}
