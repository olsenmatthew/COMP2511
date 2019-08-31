package lists;

import java.util.ArrayList;
import java.util.List;

public class ListExample {
    public void processList(List<String> list) {

        // Find the Yak
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).equals("Yak"))
//                System.out.println("Found a Yak at " + i);
//        }
        // List has many useful methods.
        System.out.println("Found a Yak at " + list.indexOf("Yak"));

        // Remove "pig" from the list
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).equals("pig")) {
//                list.remove(i);
//                i--;
//            }
//        }
        // Both this and the above refactoring rely on the equals() method of
        // String.
        list.remove("pig");

        // Print out all the animals except for those contains o's
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).contains("o"))
//                System.out.println("I'm scared of o's!");
//            else
//                System.out.println(list.get(i));
//        }
        // This style of for loop doesn't have a loop counter and is cleaner.
        for (String animal : list) {
            if(animal.contains("o"))
                System.out.println("I'm scared of o's!");
            else
                System.out.println(animal);
        }

        // If the animal starts with an uppercase letter swap its position with
        // the previous animal, if there is one
        for (int i = 1; i < list.size(); i++) {
            String animal = list.get(i);
            if (Character.isUpperCase(animal.charAt(0))) {
                list.set(i, list.get(i-1));
                list.set(i-1, animal);
                i++;
            }
        }
        // Because the above code moves elements within the list we can't use
        // the simpler style of for loop and thus there is little refactoring we
        // can do. It may be possible to remove the for loop entirely using the
        // Java Streams API and lambda functions, but we haven't covered them in
        // this course and it's unlikely the result would be an improvement.
        // Doing this is left as a challenge exercise
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();

        list.add("fish");
        list.add("duck");
        list.add("Cow");
        list.add("goat");
        list.add("pig");
        list.add("Yak");

        ListExample example = new ListExample();

        example.processList(list);

        System.out.println(list);
    }
}
