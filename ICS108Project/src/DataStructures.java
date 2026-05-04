import java.util.ArrayList;

public class DataStructures {
    public static final String[] EventTypes = {
            "Sports",
            "Social",
            "Religious",
            "Academic"
    };
    public static final String[] VenueTypes = {
            "Sports Area",
            "Lecture Hall",
            "Conference Hall",
            "Public Space"
    };
}


class Event {
    private String name;
    private String type;
    private DateTime start;
    private DateTime end;
    private Venue venue;
    private Department department;

    public Event(String name, DateTime start, DateTime end, int typeIndex, Venue venue, Department department)
    {
        this.name = name;
        this.start = start;
        this.end = end;
        this.type = DataStructures.EventTypes[typeIndex];
        this.venue = venue;
        this.department = department;
    }


    public String get_name() {return name;}
    public String get_type() {return type;}
    public DateTime get_start() {return start;}
    public DateTime get_end() {return end;}
    public Venue get_venue() {return venue;}
    public Department get_department() {return department;}

    public void set_name(String name) {this.name = name;}
    public void set_type(int typeIndex) {this.type = DataStructures.EventTypes[typeIndex];}
    public void set_start(DateTime start) {this.start = start;}
    public void set_end(DateTime end) {this.end = end;}
    public void set_venue(Venue venue) {this.venue = venue;}
    public void set_department(Department department) {this.department = department;}


    @Override
    public String toString() {
        return "Event: " + name + "\nType: " + type +
                "\nStart: " + start + "\nEnd: " + end +
                "\nVenue: " + venue + "\nDepartment: " + department +
                "\n----------------------";
    }
}


class MyDate implements Comparable<MyDate> {
    private int day, month, year, value;

    public MyDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.value = year * 10000 + month * 100 + day;//easy to use value for comparing datas
    }

    public static int max_days(int year, int month) {
        int[] days = {31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};//array of days per-month excluding February

        if (month == 2) {/*year test for February to check whether it's 28 or 29 days*/
            if (year % 400 == 0) return 29;
            if (year % 100 == 0) return 28;
            if (year % 4 == 0) return 29;
            return 28;
        }

        return days[month - 1];
    }



    public int get_day() { return day; }
    public int get_month() { return month; }
    public int get_year() { return year; }

    //Both setters update the corresponding parameter and the value
    public void set_day(int day) {this.day = day;this.value = year * 10000 + month * 100 + day;}
    public void set_month(int month) {this.month = month;this.value = year * 10000 + month * 100 + day;}
    public void set_year(int year) {this.year = year;this.value = year * 10000 + month * 100 + day;}



    @Override
    public int compareTo(MyDate other) {
        if (year != other.year) return Integer.compare(year, other.year);
        if (month != other.month) return Integer.compare(month, other.month);
        return Integer.compare(day, other.day);
    }

    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }
}

class MyTime implements Comparable<MyTime> {
    private int hours, minutes;
    private int value;//easy to use value for time

    public MyTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
        this.value = hours * 100 + minutes;
    }



    public int get_hours() {return hours;}
    public int get_minutes() {return minutes;}

    //Both setters update the corresponding parameter and the value
    public void set_hours(int hours) {this.hours = hours;    this.value = hours * 100 + minutes;}
    public void set_minutes(int minutes) {this.minutes = minutes;    this.value = hours * 100 + minutes;}



    @Override
    public int compareTo(MyTime other) {
        return Integer.compare(this.value, other.value);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d"/*this for formating the text such that both hours and minutes have two slots*/, hours, minutes);
    }
}

class DateTime implements Comparable<DateTime> {//to make comparing start and end more convenient
    private MyDate date;
    private MyTime time;

    public DateTime(MyDate date, MyTime time) {
        this.date = date;
        this.time = time;
    }


    public MyDate getDate() {return date;}
    public MyTime getTime() {return time;}

    public void setDate(MyDate date) {this.date = date;}
    public void setTime(MyTime time) {this.time = time;}

    public static String DateTimeToStr(DateTime dateTime)//Str here is short for string
    {
        return dateTime.getDate().get_day() + "/" + dateTime.getDate().get_month() + "/" + dateTime.getDate().get_year() + "-" + dateTime.getTime().get_hours() + ":" + dateTime.getTime().get_minutes();
    }

    public static DateTime StrToDateTime(String text)//Also here Str is short for string :P
    {
        String[] mainParts = text.split("-");
        String[] dateParts = mainParts[0].split("/");
        String[] timeParts = mainParts[1].split(":");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);

        return new DateTime(new MyDate(day, month, year), new MyTime(hours, minutes));
    }

    @Override
    public int compareTo(DateTime other) {
        int dateComparison = this.date.compareTo(other.date);

        if (dateComparison != 0) {
            return dateComparison;
        }

        return this.time.compareTo(other.time);
    }

    @Override
    public String toString() {
        return date + " " + time;
    }
}

class Department {//Just in case so we don't lose points
    private String name;
    private String responsiblePerson;

    public Department(String name, String responsiblePerson) {
        this.name = name;
        this.responsiblePerson = responsiblePerson;
    }



    public String get_name() {return name;}
    public String get_responsible_person() {return responsiblePerson;}

    public void set_name(String name) {this.name = name;}
    public void set_responsible_person(String responsiblePerson) {this.responsiblePerson = responsiblePerson;}



    @Override
    public String toString() {
        return name + " - Responsible: " + responsiblePerson;
    }
}

class Venue {
    private String name;
    private String type;
    private int maxCapacity;

    public Venue(String name, int typeIndex, int maxCapacity) {
        this.name = name;
        this.type = DataStructures.VenueTypes[typeIndex];
        this.maxCapacity = maxCapacity;
    }


    public String get_name() {
        return name;
    }
    public String get_type() {
        return type;
    }
    public int get_max_capacity() {
        return maxCapacity;
    }

    public void set_name(String name) {
        this.name = name;
    }
    public void set_type(int typeIndex) {
        this.type = DataStructures.VenueTypes[typeIndex];
    }
    public void set_max_capacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public static Venue find_venue_by_name(String name, ArrayList<Venue> venues)
    {
        for (Venue v : venues)
        {
            if (v.get_name().equalsIgnoreCase(name))
            {
                return v;
            }
        }
        return null;
    }

    public static int get_venue_type_index(String type) {
        for (int i = 0; i < DataStructures.VenueTypes.length; i++)
        {
            if (DataStructures.VenueTypes[i].equalsIgnoreCase(type))
            {
                return i;
            }
        }
        return 0;
    }

    public static int get_event_type_index(String type)
    {
        for (int i = 0; i < DataStructures.EventTypes.length; i++)
        {
            if (DataStructures.EventTypes[i].equalsIgnoreCase(type))
            {
                return i;
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        return name + " (" + type + "), Capacity: " + maxCapacity;
    }
}