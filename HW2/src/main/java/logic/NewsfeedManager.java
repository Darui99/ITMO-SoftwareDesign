package logic;

import java.io.IOException;

public class NewsfeedManager {
    private final NewsfeedClient client;

    public NewsfeedManager(final NewsfeedClient client) {
        this.client = client;
    }

    public int[] calculatePostsFrequency(final String hashtag, final int hours) throws IOException {
        int[] res = new int[hours];
        long curTime = System.currentTimeMillis() / 1000L;
        long hourInSecs = 60L * 60L;

        for (int i = 0; i < hours; i++) {
            res[i] = client.getPostsCount(hashtag,
                    curTime - (long) (i + 1) * hourInSecs + 1,
                    curTime - (long) i * hourInSecs);
        }

        return res;
    }
}
