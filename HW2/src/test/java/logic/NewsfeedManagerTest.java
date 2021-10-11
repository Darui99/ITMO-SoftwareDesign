package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static logic.Common.generateTestSample;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class NewsfeedManagerTest {
    private NewsfeedManager manager;

    @Mock
    private NewsfeedClient client;

    @BeforeEach
    private void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        manager = new NewsfeedManager(client);

        when(client.getPostsCount(anyString(), anyLong(), anyLong())).thenAnswer(
                invocation -> {
                    String hashtag = invocation.getArgument(0, String.class);
                    Long startTime = invocation.getArgument(1, Long.class);
                    Long endTime = invocation.getArgument(2, Long.class);

                    return (int) generateTestSample().stream()
                            .filter(p -> p[0].equals(hashtag) && startTime <= Long.parseLong(p[1]) && Long.parseLong(p[1]) <= endTime)
                            .count();
                }
        );
    }

    private void checkBoxedArray(int[] actual, List<Integer> expected) {
        assertEquals(Arrays.stream(actual).boxed().collect(Collectors.toList()), expected);
    }

    @Test
    public void test1_1Hour() throws IOException {
        checkBoxedArray(manager.calculatePostsFrequency("#autumn", 1), List.of(1));
    }

    @Test
    public void test2_4Hours() throws IOException {
        checkBoxedArray(manager.calculatePostsFrequency("#spring", 4), List.of(1, 0, 1, 0));
    }

    @Test
    public void test3_24Hours() throws IOException {
        checkBoxedArray(manager.calculatePostsFrequency("#autumn", 24),
                List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
    }

    @Test
    public void test4_zeroMatching() throws IOException {
        checkBoxedArray(manager.calculatePostsFrequency("#winter", 7),
                List.of(0, 0, 0, 0, 0, 0, 0));
    }

}