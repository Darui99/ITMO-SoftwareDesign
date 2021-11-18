package stat;

import domain.Fraction;

import java.util.Map;

public interface EventsStatistic {
    void incEvent(String name);

    Fraction getEventStatisticByName(String name);

    Map<String, Fraction> getAllEventStatistic();

    void printStatistic();
}
