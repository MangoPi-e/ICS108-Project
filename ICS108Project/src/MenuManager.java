import java.util.Scanner;

public class MenuManager
{
    private Scanner reader;
    private OperationsManagement operations;

    public MenuManager(OperationsManagement operations) {
        this.operations = operations;
        this.reader = new Scanner(System.in);
    }

    public void start() {
        MainMenu mainMenu = new MainMenu(operations, reader);
        mainMenu.start();
    }
}

//Tasks base
interface Task {
    String get_name();
    void execute();
}
abstract class ConfirmableTask implements Task {
    protected OperationsManagement operations;
    protected Scanner reader;

    public ConfirmableTask(OperationsManagement operations, Scanner reader) {
        this.operations = operations;
        this.reader = reader;
    }

    @Override
    public final void execute() {
        boolean successful = run_task();

        if (successful) {
            show_summary();

            if (confirm()) {
                on_confirmed();
            } else {
                on_cancelled();
            }
        }
    }

    protected abstract boolean run_task();

    protected abstract void on_confirmed();

    protected void show_summary() {
        // Optional: override in child classes
    }

    protected void on_cancelled() {
        System.out.println("Task cancelled.");
    }

    private boolean confirm() {
        System.out.print("\nConfirm this task? (Y/n): ");
        String choice = reader.nextLine();

        return !choice.equalsIgnoreCase("n");
    }
}


//The main menu implementation
abstract class Menu {
    protected String menuName;
    protected Task[] tasks;
    protected Scanner reader;

    public Menu(String menuName, Scanner reader) {
        this.menuName = menuName;
        this.reader = reader;
    }

    public void start() {
        boolean running = true;

        while (running) {
            view();

            int choice = get_choice();

            if (choice == tasks.length) {
                running = false;
            } else {
                tasks[choice].execute();
            }
        }
    }

    protected void view() {
        System.out.println("\n==== " + menuName + " ====");

        for (int i = 0; i < tasks.length; i++) {
            System.out.println(i + " : " + tasks[i].get_name());
        }

        System.out.println(tasks.length + " : Back/Exit");
        System.out.print("Choose: ");
    }

    protected int get_choice() {
        while (true) {
            try {
                int choice = Integer.parseInt(reader.nextLine());

                if (choice >= 0 && choice <= tasks.length) {
                    return choice;
                }

                System.out.print("Invalid choice. Try again:P : ");
            } catch (Exception e) {
                System.out.print("Invalid input. Try again:P : ");
            }
        }
    }
}
class MainMenu extends Menu {
    public MainMenu(OperationsManagement operations, Scanner reader) {
        super("Main Menu", reader);

        tasks = new Task[]
        {
                new AddEventTask(operations, reader),
                new ViewEventsTask(operations),
                new AddVenueTask(operations, reader),
                new ViewVenuesTask(operations),
                new SaveDataTask(operations, reader),
                new LoadDataTask(operations, reader)
        };
    }
}


//Input boxes and some validation
abstract class InputBox<T> {
    protected Scanner reader;
    protected String label;

    public InputBox(Scanner reader, String label) {
        this.reader = reader;
        this.label = label;
    }

    public T get_input() {
        while (true) {
            try {
                T value = collect_input();

                if (validate(value)) {
                    return value;
                }

                System.out.println(get_error_message());
            } catch (Exception e) {
                System.out.println(get_error_message());
            }
        }
    }

    protected abstract T collect_input();

    protected abstract boolean validate(T value);

    protected abstract String get_error_message();
}

class NameInputBox extends InputBox<String> {
    public NameInputBox(Scanner reader, String label) {
        super(reader, label);
    }

    @Override
    protected String collect_input() {
        System.out.print(label + ": ");
        return reader.nextLine();
    }

    @Override
    protected boolean validate(String value) {
        return value != null && !value.trim().isEmpty();
    }

    @Override
    protected String get_error_message() {
        return "Invalid input. " + label + " cannot be empty :P";
    }
}

class DateInputBox extends InputBox<MyDate> {
    public DateInputBox(Scanner reader, String label) {
        super(reader, label);
    }

    @Override
    protected MyDate collect_input() {
        System.out.println("\n" + label);

        System.out.print("Year: ");
        int year = Integer.parseInt(reader.nextLine());

        System.out.print("Month: ");
        int month = Integer.parseInt(reader.nextLine());

        System.out.print("Day: ");
        int day = Integer.parseInt(reader.nextLine());

        return new MyDate(day, month, year);
    }

    @Override
    protected boolean validate(MyDate date) {
        if (date.get_year() < 0) return false;

        if (date.get_month() < 1 || date.get_month() > 12) return false;

        return date.get_day() >= 1 &&
                date.get_day() <= MyDate.max_days(date.get_year(), date.get_month());
    }

    @Override
    protected String get_error_message() {
        return "Invalid date. Please enter a valid date.";
    }
}

class TimeInputBox extends InputBox<MyTime> {
    public TimeInputBox(Scanner reader, String label) {
        super(reader, label);
    }

    @Override
    protected MyTime collect_input() {
        System.out.println("\n" + label);

        System.out.print("Hours: ");
        int hours = Integer.parseInt(reader.nextLine());

        System.out.print("Minutes: ");
        int minutes = Integer.parseInt(reader.nextLine());

        return new MyTime(hours, minutes);
    }

    @Override
    protected boolean validate(MyTime time) {
        return time.get_hours() >= 0 &&
                time.get_hours() <= 23 &&
                time.get_minutes() >= 0 &&
                time.get_minutes() <= 59;
    }

    @Override
    protected String get_error_message() {
        return "Invalid time. Hours must be 0–23 and minutes must be 0–59 !!!";
    }
}

class ChoiceInputBox extends InputBox<Integer> {
    private String[] choices;

    public ChoiceInputBox(Scanner reader, String label, String[] choices) {
        super(reader, label);
        this.choices = choices;
    }

    @Override
    protected Integer collect_input() {
        System.out.println("\n" + label);

        for (int i = 0; i < choices.length; i++) {
            System.out.println(i + " : " + choices[i]);
        }

        System.out.print("Choose: ");
        return Integer.parseInt(reader.nextLine());
    }

    @Override
    protected boolean validate(Integer value) {
        return value >= 0 && value < choices.length;
    }

    @Override
    protected String get_error_message() {
        return "Invalid choice. Please select a valid option.";
    }
}

class CapacityInputBox extends InputBox<Integer> {
    public CapacityInputBox(Scanner reader, String label) {
        super(reader, label);
    }

    @Override
    protected Integer collect_input() {
        System.out.print(label + ": ");
        return Integer.parseInt(reader.nextLine());
    }

    @Override
    protected boolean validate(Integer value) {
        return value > 0;
    }

    @Override
    protected String get_error_message() {
        return "Invalid capacity. It must be greater than 0.";
    }
}


//adding
class AddEventTask extends ConfirmableTask {
    private String name;
    private MyDate startDate;
    private MyTime startTime;
    private MyDate endDate;
    private MyTime endTime;
    private int eventTypeIndex;
    private Venue venue;
    private String departmentName;
    private String responsiblePerson;

    public AddEventTask(OperationsManagement operations, Scanner reader) {
        super(operations, reader);
    }

    @Override
    public String get_name() {
        return "Add Event";
    }

    @Override
    protected boolean run_task() {
        System.out.println("\n--- Add Event ---");

        name = new NameInputBox(reader, "Event Name").get_input();

        startDate = new DateInputBox(reader, "Start Date").get_input();
        startTime = new TimeInputBox(reader, "Start Time").get_input();

        endDate = new DateInputBox(reader, "End Date").get_input();
        endTime = new TimeInputBox(reader, "End Time").get_input();

        eventTypeIndex = new ChoiceInputBox(reader, "Event Type", DataStructures.EventTypes).get_input();
        venue = operations.select_venue(reader);

        departmentName = new NameInputBox(reader, "Department Name").get_input();
        responsiblePerson = new NameInputBox(reader, "Responsible Person").get_input();

        return true;
    }

    @Override
    protected void show_summary() {
        System.out.println("\n--- Event Summary ---");
        System.out.println("Name: " + name);
        System.out.println("Start: " + startDate + " " + startTime);
        System.out.println("End: " + endDate + " " + endTime);
        System.out.println("Type: " + DataStructures.EventTypes[eventTypeIndex]);
        System.out.println("Venue: " + venue);
        System.out.println("Department: " + departmentName);
        System.out.println("Responsible Person: " + responsiblePerson);
    }

    @Override
    protected void on_confirmed()
    {
        operations.add_event(name, startDate, startTime, endDate, endTime, eventTypeIndex, venue, departmentName, responsiblePerson);
    }
}

class AddVenueTask extends ConfirmableTask {
    private String name;
    private int venueTypeIndex;
    private int maxCapacity;

    public AddVenueTask(OperationsManagement operations, Scanner reader) {
        super(operations, reader);
    }

    @Override
    public String get_name() {
        return "Add Venue";
    }

    @Override
    protected boolean run_task() {
        System.out.println("\n--- Add Venue ---");
        name = new NameInputBox(reader, "Venue Name").get_input();
        venueTypeIndex = new ChoiceInputBox(reader, "Venue Type", DataStructures.VenueTypes).get_input();
        maxCapacity = new CapacityInputBox(reader, "Maximum Capacity").get_input();
        return true;
    }

    @Override
    protected void show_summary() {
        System.out.println("\n--- Venue Summary ---");
        System.out.println("Name: " + name);
        System.out.println("Type: " + DataStructures.VenueTypes[venueTypeIndex]);
        System.out.println("Maximum Capacity: " + maxCapacity);
    }

    @Override
    protected void on_confirmed() {
        operations.add_venue(name, venueTypeIndex, maxCapacity);
    }
}


//view data
class ViewEventsTask implements Task {
    private OperationsManagement operations;

    public ViewEventsTask(OperationsManagement operations) {
        this.operations = operations;
    }

    @Override
    public String get_name() {
        return "View Events";
    }

    @Override
    public void execute() {
        operations.view_events();
    }
}

class ViewVenuesTask implements Task {
    private OperationsManagement operations;

    public ViewVenuesTask(OperationsManagement operations) {
        this.operations = operations;
    }

    @Override
    public String get_name() {
        return "View Venues";
    }

    @Override
    public void execute() {
        operations.view_venues();
    }
}


//save and load
class SaveDataTask extends ConfirmableTask {
    String FileName;
    public SaveDataTask(OperationsManagement operations, Scanner reader) {
        super(operations, reader);
    }

    @Override
    public String get_name() {
        return "Save Data";
    }

    @Override
    protected boolean run_task()
    {
        FileName = new NameInputBox(reader, "File name").get_input();
        return true;
    }

    @Override
    protected void on_confirmed() {
        operations.save_data(FileName);
    }
}

class LoadDataTask extends ConfirmableTask {
    String FileName;
    public LoadDataTask(OperationsManagement operations, Scanner reader) {
        super(operations, reader);
    }

    @Override
    public String get_name() {
        return "Load Data";
    }

    @Override
    protected boolean run_task()
    {
        FileName = new NameInputBox(reader, "File name").get_input();
        return true;
    }

    @Override
    protected void on_confirmed() {
        operations.load_data(FileName);
    }
}