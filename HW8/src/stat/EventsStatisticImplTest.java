package stat;

import clock.SetableClock;
import domain.Fraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EventsStatisticImplTest {
    private Instant start;
    private SetableClock clock;
    private EventsStatistic stat;

    @BeforeEach
    private void setUp() {
        start = Instant.now();
        clock = new SetableClock(start);
        stat = new EventsStatisticImpl(clock);
    }

    @Test
    public void test1_simple_check() {
        stat.incEvent("a");
        for (int i = 0; i < 3; i++) {
            clock.setNow(start.plusSeconds(i * 10));
            stat.incEvent("c");
        }
        assertEquals(stat.getEventStatisticByName("a"), new Fraction(1, 60));
        assertEquals(stat.getEventStatisticByName("c"), new Fraction(3, 60));
        assertEquals(stat.getAllEventStatistic(), Map.of("a", new Fraction(1, 60), "c", new Fraction(3, 60)));
    }

    @Test
    public void test2_one_per_hour() {
        for (int i = 0; i < 3; i++) {
            clock.setNow(start.plus(i, ChronoUnit.HOURS));
            stat.incEvent("a");
            assertEquals(stat.getEventStatisticByName("a"), new Fraction(1, 60));
            assertEquals(stat.getAllEventStatistic(), Map.of("a", new Fraction(1, 60)));
        }
    }

    @Test
    public void test3_K_in_Kth_hour() {
        for (int i = 0; i < 5; i++) {
            clock.setNow(start.plus(i, ChronoUnit.HOURS));
            for (int j = 0; j <= i; j++) {
                stat.incEvent("a");
            }
            assertEquals(stat.getEventStatisticByName("a"), new Fraction(i + 1, 60));
            assertEquals(stat.getAllEventStatistic(), Map.of("a", new Fraction(i + 1, 60)));
        }
    }

    @Test
    public void test4_manual_border() {
        clock.setNow(Instant.parse("2021-11-15T09:29:59Z"));
        stat.incEvent("a");
        assertEquals(stat.getEventStatisticByName("a"), new Fraction(1, 60));
        assertEquals(stat.getAllEventStatistic(), Map.of("a", new Fraction(1, 60)));

        clock.setNow(Instant.parse("2021-11-15T09:30:00Z"));
        stat.incEvent("a");
        assertEquals(stat.getEventStatisticByName("a"), new Fraction(1, 30));
        assertEquals(stat.getAllEventStatistic(), Map.of("a", new Fraction(1, 30)));

        clock.setNow(Instant.parse("2021-11-15T09:30:01Z"));
        stat.incEvent("a");
        assertEquals(stat.getEventStatisticByName("a"), new Fraction(1, 20));
        assertEquals(stat.getAllEventStatistic(), Map.of("a", new Fraction(1, 20)));

        clock.setNow(Instant.parse("2021-11-15T10:29:58Z"));
        assertEquals(stat.getEventStatisticByName("a"), new Fraction(1, 20));
        assertEquals(stat.getAllEventStatistic(), Map.of("a", new Fraction(1, 20)));

        clock.setNow(Instant.parse("2021-11-15T10:29:59Z"));
        assertEquals(stat.getEventStatisticByName("a"), new Fraction(1, 30));
        assertEquals(stat.getAllEventStatistic(), Map.of("a", new Fraction(1, 30)));

        clock.setNow(Instant.parse("2021-11-15T10:30:00Z"));
        assertEquals(stat.getEventStatisticByName("a"), new Fraction(1, 60));
        assertEquals(stat.getAllEventStatistic(), Map.of("a", new Fraction(1, 60)));

        clock.setNow(Instant.parse("2021-11-15T10:30:01Z"));
        assertEquals(stat.getEventStatisticByName("a"), new Fraction(0, 60));
        assertEquals(stat.getAllEventStatistic(), Map.of("a", new Fraction(0, 60)));
    }
}