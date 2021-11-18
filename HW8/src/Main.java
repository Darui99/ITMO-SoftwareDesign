import stat.EventsStatistic;
import stat.EventsStatisticImpl;

public class Main {
    public static void main(String[] args) {
        EventsStatistic stat = new EventsStatisticImpl();

        for (int i = 0; i < 1; i++)
            stat.incEvent("a");
        for (int i = 0; i < 2; i++)
            stat.incEvent("b");
        for (int i = 0; i < 3; i++)
            stat.incEvent("c");
        for (int i = 0; i < 4; i++)
            stat.incEvent("d");

        stat.printStatistic();
    }
}
