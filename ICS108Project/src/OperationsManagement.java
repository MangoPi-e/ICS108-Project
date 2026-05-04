import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;


public class OperationsManagement
{
    private ArrayList<Event> events;
    private ArrayList<Venue> venues;

    public OperationsManagement() {
        events = new ArrayList<Event>();
        venues = new ArrayList<Venue>();
    }

    public void add_venue(String name, int venueTypeIndex, int maxCapacity) {
        Venue venue = new Venue(name, venueTypeIndex, maxCapacity);
        venues.add(venue);

        System.out.println("Venue added successfully.");
    }


    public Venue select_venue(Scanner reader) {
        if (venues.isEmpty())
        {
            System.out.println("No venues available. Please add a venue first.");
            return null;
        }


        System.out.println("\n=== Available Venues ===");
        for (int i = 0; i < venues.size(); i++)
        {
            System.out.println(i + " : " + venues.get(i));
        }

        while (true) {
            try {
                System.out.print("Choose venue: ");
                int choice = Integer.parseInt(reader.nextLine());

                if (choice >= 0 && choice < venues.size())
                {
                    return venues.get(choice);
                }
                System.out.println("Invalid choice.");
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
    }

    public void add_event(String name, MyDate startDate, MyTime startTime, MyDate endDate, MyTime endTime, int eventTypeIndex, Venue venue, String departmentName, String responsiblePerson)
    {
        if (venue == null) {
            System.out.println("Event was not added because no venue was selected.");
            return;
        }

        DateTime start = new DateTime(startDate, startTime);
        DateTime end = new DateTime(endDate, endTime);

        if (!is_start_before_end(start, end)) {
            System.out.println("Event was not added. Start date/time must be before end date/time.");
            return;
        }

        if (has_venue_overlap(venue, start, end)) {
            System.out.println("Event was not added. This venue already has an overlapping event.");
            return;
        }

        Department department = new Department(departmentName, responsiblePerson);
        Event event = new Event(name, start, end, eventTypeIndex, venue, department);
        events.add(event);

        System.out.println("Event added successfully.");
    }

    private boolean is_start_before_end(DateTime start, DateTime end) {
        return start.compareTo(end) < 0;
    }
    private boolean has_venue_overlap(Venue venue, DateTime start, DateTime end) {
        for (Event event : events) {
            if (event.get_venue() == venue) {
                boolean overlaps = start.compareTo(event.get_end()) < 0 && end.compareTo(event.get_start()) > 0;
                if (overlaps) {
                    return true;
                }
            }
        }

        return false;
    }

    public void view_events() {
        if (events.isEmpty()) {
            System.out.println("No events available.");
            return;
        }

        System.out.println("\n--- Events ---");

        for (int i = 0; i < events.size(); i++) {
            System.out.println(i + " : ");
            System.out.println(events.get(i));
        }
    }

    public void view_venues() {
        if (venues.isEmpty()) {
            System.out.println("No venues available.");
            return;
        }

        System.out.println("\n--- Venues ---");

        for (int i = 0; i < venues.size(); i++) {
            System.out.println(i + " : " + venues.get(i));
        }
    }

    public void save_data(String FileName)
    {
        try {
            FileOutputStream fos = new FileOutputStream(FileName+".txt");
            PrintWriter writer = new PrintWriter(fos);

            writer.println("===Venues===");
            for (Venue v : venues)
            {
                writer.println(v.get_name() + "@@" + Venue.get_venue_type_index(v.get_type()) + "@@" + v.get_max_capacity());
            }
            writer.println("===Events===");
            for (Event e : events)
            {
                writer.println(e.get_name() + "@@" + Venue.get_event_type_index(e.get_type()) + "@@" + DateTime.DateTimeToStr(e.get_start()) + "@@" + DateTime.DateTimeToStr(e.get_end()) + "@@" + e.get_venue().get_name() + "@@" + e.get_department().get_name() + "@@" + e.get_department().get_responsible_person());
            }
            writer.close();
            fos.close();
            System.out.println("Data saved successfully to " + FileName +" !!");
        } catch (Exception e) {
            System.out.println("Failed to load data!! :\n\t"+e);
        }
    }
    public void load_data(String FileName)
    {
        try
        {
            FileInputStream fis = new FileInputStream(FileName+".txt");
            Scanner saver = new Scanner(fis);
            boolean Flag = true;//if the flag is true the code reads saved events otherwise it reads venues
            while(saver.hasNext()) {
                String line = saver.nextLine();
                if (line.equals("===Venues===")) {
                    Flag = false;
                } else if (line.equals("===Events===")) {
                    Flag = true;
                } else {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    String[] parts = line.split("@@");

                    if (!Flag)
                    {
                        String name = parts[0];
                        int typeIndex = Integer.parseInt(parts[1]);
                        int maxCapacity = Integer.parseInt(parts[2]);

                        venues.add(new Venue(name, typeIndex, maxCapacity));
                    }
                    else
                    {
                        String name = parts[0];
                        int typeIndex = Integer.parseInt(parts[1]);

                        DateTime start = DateTime.StrToDateTime(parts[2]);
                        DateTime end = DateTime.StrToDateTime(parts[3]);

                        String venueName = parts[4];
                        String departmentName = parts[5];
                        String responsiblePerson = parts[6];

                        Venue venue = Venue.find_venue_by_name(venueName, venues);
                        Department department = new Department(departmentName, responsiblePerson);

                        events.add(new Event(name, start, end, typeIndex, venue, department));
                    }

                }
            }
            saver.close();
            fis.close();
            System.out.println("Data loaded successfully.");
        }catch (IOException e){System.out.println("Failed to load data!! :\n\t"+e);}
    }
}