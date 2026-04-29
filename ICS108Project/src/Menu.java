/*This file includes the Menu abstract class with its children (Aka all menus)*/
import java.util.InputMismatchException;
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

class NameBox extends Menu
{
    NameBox(String Name)
    {
        super(Name);
    }

    @Override
    public void start(Menu prev)
    {
    }

    public String validate() {
        //input validation
        String Name = "";
        boolean Flag = true;
        while (Flag) {
            try {
                System.out.print("\nName : ");
                Name = reader.next();
            } catch (Exception e) {
                System.out.println("Invalid Input!!!!");
                System.out.print("Try Again : \n||| ");
            }
        }
        return Name;}
}
class TimeBox extends Menu
{
    TimeBox(String Name)
    {
        super(Name);
    }

    @Override
    public void start(Menu prev)
    {

    }

    public Time validate()
    {
        //input validation
        int minutes=0, hours=0;
        boolean Flag = true;
        while(Flag)
        {
            try
            {
                System.out.print("\nHours : ");
                hours = reader.nextInt();
                if (hours > 24 || hours < 0){throw new InputMismatchException();}
            }catch (Exception e)
            {
                System.out.println("Invalid Input!!!!");
                System.out.print("Try Again : \n||| ");
            }
        }
        Flag = true;
        while(Flag)
        {
            try
            {
                System.out.print("\nMinutes : ");
                minutes = reader.nextInt();
                if (minutes > 59 || minutes < 0){throw new InputMismatchException();}
            }catch (Exception e)
            {
                System.out.println("Invalid Input!!!!");
                System.out.print("Try Again : \n||| ");
            }
        }
        return new Time(hours, minutes);
    }
}
class DateBox extends Menu
{
    DateBox(String Name)
    {
        super(Name);
    }

    @Override
    public void start(Menu prev)
    {

    }

    public MyDate validate() {
        //input validation
        int Year = 0, Month = 0, Day = 0;
        boolean Flag = true;
        while (Flag) {
            try {
                System.out.print("\nYear : ");
                Year = reader.nextInt();
                if (Year > 2026 || Year < 0) {
                    throw new InputMismatchException();
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!!!!");
                System.out.print("Try Again : \n||| ");
            }
        }
        Flag = true;
        while (Flag) {
            try {
                System.out.print("\nMonth : ");
                Month = reader.nextInt();
                if (Month > 12 || Month <= 0) {
                    throw new InputMismatchException();
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!!!!");
                System.out.print("Try Again : \n||| ");
            }
        }

        Flag = true;
        while (Flag) {
            try {
                System.out.print("\nDay : ");
                Day = reader.nextInt();
                if (Day > MyDate.MaxDays(Year, Month) || Day <= 0) {
                    throw new InputMismatchException();
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!!!!");
                System.out.print("Try Again : \n||| ");
            }
        }
        return new MyDate(Day, Month, Year);
    }
}
class DateInterval extends DateBox
{
    DateInterval(String Name)
    {
        super(Name);
    }

    @Override
    public void start(Menu prev)
    {
        boolean Flag2 = true;
        while (Flag2)
        {
            try
            {
            MyDate Start = validate();
            MyDate End = validate();
            if (Start.compareTo(End)>0){throw new Exception("Invalid Date Interval");}
            }
            catch (Exception e) {
            System.out.println("Invalid Input "+ e +"!!!!");
            System.out.print("Try Again : \n||| ");
            }
        }
    }
}
class EventTypeBox extends Menu
{
    String[] SelectionPool = DataStructures.EventTypes;
    EventTypeBox(String Name){super(Name);}
    @Override
    public void start(Menu Prev)
    {
        view();
        GetInput();
    }

}
class VenueTypeBox extends EventTypeBox
{
    String[] SelectionPool = DataStructures.VenueTypes;
    VenueTypeBox(String Name){super(Name);}
    public void start(Menu Prev)
    {
        view();
        GetInput();
    }
}
class VenuseBox extends Menu
{
    VenuseBox(String Name){super(Name);}
    @Override
    public void start(Menu Prev)
    {
        view();
        GetInput();
    }
}
class AddEvent extends Menu
{
    String Name, Type;
    MyDate StartingDate, EndDate;
    Time time;
    Venue venue;
    AddEvent()
    {
        super("Add Event");
        SelectionPool = new Menu[]{new NameBox("Event Name"), new DateInterval("Start/End Date"), new TimeBox("Time"), new EventTypeBox("Event Category")};
    }

    @Override
    public void start(Menu Prev)
    {
        view();
    }
}
