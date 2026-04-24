public class Main{
    public static void main(String[] args)
    {
        MenuManager.CurrentMenu = new Menu("Start Menu");
        MenuManager.ViewMenu();
    }
}


abstract class MenuManager
{
    public static Menu CurrentMenu;
    public static void ViewMenu()
    {
        System.out.println(CurrentMenu.MenuName + " : ");
        if (CurrentMenu.SelectionPool != null)
        {
        for(int i=0; i<CurrentMenu.SelectionPool.length; i++)
        {
            System.out.println(i + " :\n\t" + CurrentMenu.SelectionPool[i]);
        }
        }
    }
}

abstract class Menu
{
    String MenuName;
    Menu[] SelectionPool;

    public Menu(String Name)
    {
        this.MenuName = Name;
    }
}






class Event
{//initiating the class properties
    String Name;
    Date StartingDate, EndDate;
    Time time;
//constructor
    public Event(String name, Date startingDate, Date endDate, Time time)
    {
        this.Name = name;
        this.StartingDate = startingDate;
        this.EndDate = endDate;
        this.time = time;
    }
}


class Date
{
    int day, month, year;
    public Date(int day, int month, int year)
    {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {return day;}
    public void setDay(int day) {this.day = day;}

    public int getMonth() {return month;}
    public void setMonth(int month) {this.month = month;}

    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;}

    @Override
    public String toString()
    {
        return day + "/" + month + "/" + year;
    }
}

class Time
{
    int hours, minutes, seconds;

    public Time(int hours, int minutes, int seconds)
    {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getHours() {return hours;}
    public void setHours(int hours) {this.hours = hours;}

    public int getMinutes() {return minutes;}
    public void setMinutes(int minutes) {this.minutes = minutes;}

    public int getSeconds() {return seconds;}
    public void setSeconds(int seconds) {this.seconds = seconds;}

    @Override
    public String toString()
    {
        return hours + ":" + minutes + ":" + seconds;
    }
}

class Venue
{
}