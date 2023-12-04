package day1;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile1 = new File("./src/day1/example.txt");
        //File inputFile2 = new File("./src/day1/example2.txt");
        File inputFile2 = new File("./src/day1/input.txt");
        Scanner scanner = new Scanner(inputFile1).useDelimiter("\r\n");
        List<String> input1 = new ArrayList<>();
        while (scanner.hasNext()) {
            input1.add(scanner.nextLine());
        }
        int result1 = solve1(input1);
        System.out.println("Result 1:" + result1);

        scanner = new Scanner(inputFile2).useDelimiter("\r\n");
        List<String> input2 = new ArrayList<>();
        while (scanner.hasNext()) {
            input2.add(scanner.nextLine());
        }
        scanner.close();

        int result2 = solve2(input2);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(List<String> input) {
        List<Integer> list = new LinkedList<>();
        input.forEach(l -> {
            Character first = l.chars().mapToObj(c -> (char) c).filter(Character::isDigit).toList().getFirst();
            Character last = l.chars().mapToObj(c -> (char) c).filter(Character::isDigit).toList().getLast();
            list.add(Integer.parseInt(first.toString() + last.toString()));
        });
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    private static int solve2(List<String> input) {
        List<Integer> list = new LinkedList<>();
        input.forEach(l -> {
            int first = -1;
            int last = -1;
            int length = l.length();
            for (int i = 0; i < length; i++) {
                Optional<Integer> digit = startsWithInt(l);
                if (Character.isDigit(l.charAt(0))) {
                    if (first == -1) {
                        first = Integer.parseInt(String.valueOf(l.charAt(0)));
                    }
                    last = Integer.parseInt(String.valueOf(l.charAt(0)));
                }
                if (digit.isPresent()) {
                    if (first == -1) {
                        first = digit.get();
                    }
                    last = digit.get();
                }
                l = l.substring(1);
            }
            System.out.println(first + " " + last);
            list.add(Integer.parseInt(first + String.valueOf(last)));
        });
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    private static Optional<Integer> startsWithInt(String s) {
        if (s.startsWith("one")) {
            return Optional.of(1);
        }
        if (s.startsWith("two")) {
            return Optional.of(2);
        }
        if (s.startsWith("three")) {
            return Optional.of(3);
        }
        if (s.startsWith("four")) {
            return Optional.of(4);
        }
        if (s.startsWith("five")) {
            return Optional.of(5);
        }
        if (s.startsWith("six")) {
            return Optional.of(6);
        }
        if (s.startsWith("seven")) {
            return Optional.of(7);
        }
        if (s.startsWith("eight")) {
            return Optional.of(8);
        }
        if (s.startsWith("nine")) {
            return Optional.of(9);
        }
        return Optional.empty();
    }


}