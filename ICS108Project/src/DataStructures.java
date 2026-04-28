public class DataStructures
{
}

class Event
{//initiating the class properties
    String Name;
    Date StartingDate, EndDate;
    Time time;
    Venue venue;
    public Event(String name, Date startingDate, Date endDate, Time time, Venue venue)
    {
        this.Name = name;
        this.StartingDate = startingDate;
        this.EndDate = endDate;
        this.time = time;
        this.venue = venue;
    }
}

class Venue
{
    String Name, Type;
    Venue(String Name, String Type)
    {
        this.Name = Name;
        this.Type = Type;
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

    static int  validateDays(int year, int month)
        {
            if (month == 2)
            {
                if (year%400 == 0){return 29;}
                if (year%100 == 0){return 28;}
                if (year%4 == 0){return 29;}
                return 28;
            }
            else if(month%2 == 0)
            {return }
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