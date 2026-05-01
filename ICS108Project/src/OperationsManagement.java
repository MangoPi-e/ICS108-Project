import java.util.ArrayList;
import java.util.Scanner;

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


        System.out.println("\n--- Available Venues ---");
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
}