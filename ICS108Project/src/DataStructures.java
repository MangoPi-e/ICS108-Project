import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.TreeMap;

public class DataStructures
{
    public static String[] EventTypes = {"Sports", "Social", "Religious", "Academic"};
    public static String[] VenueTypes = {"Sports area", "Lecture hall", "Conference hall", "Public space"};
}

class Event
{//initiating the class properties
    String Name, Type;
    MyDate StartingDate, EndDate;
    Time time;
    Venue venue;
    public Event(String name, MyDate startingDate, MyDate endDate, Time time, int TypeIndex, Venue venue)
    {
        this.Name = name;
        this.StartingDate = startingDate;
        this.EndDate = endDate;
        this.time = time;
        this.Type = DataStructures.EventTypes[TypeIndex];
        this.venue = venue;
    }
}

class Venue
{
    String Name, Type;
    ArrayList<MyDate> Dates = new ArrayList<MyDate>();
    Venue(String Name, int TypeIndex)
    {
        this.Name = Name;
        this.Type = DataStructures.VenueTypes[TypeIndex];
    }
}

class MyDate implements Comparable<MyDate>
{
    int day, month, year, noDate;
    public MyDate(int day, int month, int year)
    {
        this.day = day;
        this.month = month;
        this.year = year;
        this.noDate = day + (month*100) + (year*10000);
    }

    static int  MaxDays(int year, int month)
        {
            int[] DaysPerMonth = {31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if (month == 2)
            {
                if (year%400 == 0){return 29;}
                if (year%100 == 0){return 28;}
                if (year%4 == 0){return 29;}
                return 28;
            }
            else{return DaysPerMonth[month-1];}
        }

    public int getDay() {return day;}
    public void setDay(int day) {this.day = day;this.noDate = day + (month*100) + (year*10000);}

    public int getMonth() {return month;}
    public void setMonth(int month) {this.month = month;this.noDate = day + (month*100) + (year*10000);}

    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;this.noDate = day + (month*100) + (year*10000);}

    @Override
    public String toString()
    {
        return day + "/" + month + "/" + year;
    }
    @Override
    public int compareTo(MyDate other)
    {
        return this.noDate - other.noDate;
    }
}

class Time
{
    int hours, minutes;

    public Time(int hours, int minutes)
    {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {return hours;}
    public void setHours(int hours) {this.hours = hours;}

    public int getMinutes() {return minutes;}
    public void setMinutes(int minutes) {this.minutes = minutes;}

    @Override
    public String toString()
    {
        return hours + ":" + minutes;
    }
}