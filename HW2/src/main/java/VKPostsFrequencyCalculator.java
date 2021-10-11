import logic.NewsfeedClient;
import logic.NewsfeedManager;

public class VKPostsFrequencyCalculator {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("args is null");
            return;
        }
        if (args.length != 2) {
            System.out.println("number of arguments is incorrect");
            return;
        }

        try {
            String hashtag = args[0];
            if (hashtag.isEmpty()) {
                System.out.println("hashtag is empty");
                return;
            }
            int hours = Integer.parseUnsignedInt(args[1]);
            if (hours == 0 || hours > 24) {
                System.out.println("hours' value is incorrect");
                return;
            }

            int[] frequency = new NewsfeedManager(new NewsfeedClient()).calculatePostsFrequency("#" + hashtag, hours);
            for (int i = 0; i < hours; i++) {
                System.out.println("hour: " + (i + 1) + " | posts: " + frequency[i]);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
