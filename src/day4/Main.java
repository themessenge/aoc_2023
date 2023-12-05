package day4;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<Card> input = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day4/input.txt");
        //File inputFile = new File("./src/day4/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        String currentLine;
        int i = 1;
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            input.add(new Card(currentLine.split(": ")[1], i));
            i++;
        }
        scanner.close();
        int result1 = solve1();
        System.out.println("Result 1:" + result1);
        int result2 = solve2(input);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1() {
        return (int) input.stream().mapToDouble(card -> {
            List<Integer> yourWinning = new LinkedList<>();
            card.yours.stream().filter(y -> card.winning.contains(y)).forEach(yourWinning::add);
            if (yourWinning.isEmpty()) {
                return 0;
            } else {
                return Math.pow(2, yourWinning.size() - 1);
            }
        }).sum();
    }

    public static List<Card> allCards = new LinkedList<>();

    private static int solve2(List<Card> currentInput) {
        allCards.addAll(input);
        loop(currentInput);
        return allCards.size();
    }

    private static void loop(List<Card> currentInput) {
        for (Card card : currentInput) {
            List<Integer> yourWinning = new LinkedList<>();
            card.yours.stream().filter(y -> card.winning.contains(y)).forEach(yourWinning::add);
            if (!yourWinning.isEmpty()) {
                List<Card> newList = new LinkedList<>();
                for (int j = 0; j < yourWinning.size(); j++) {
                    newList.add(input.get(card.cardNumber + j));
                }
                allCards.addAll(newList);
                loop(newList);
            }
        }
    }


    public static class Card {
        public List<Integer> winning = new LinkedList<>();
        public List<Integer> yours = new LinkedList<>();
        public int cardNumber;

        public Card(String numbers, int cardNumber) {
            Arrays.stream(numbers.split(" \\| ")[0].split(" ")).filter(w -> !w.isBlank()).forEach(w -> winning.add(Integer.parseInt(w.trim())));
            Arrays.stream(numbers.split(" \\| ")[1].split(" ")).filter(w -> !w.isBlank()).forEach(w -> yours.add(Integer.parseInt(w.trim())));
            this.cardNumber = cardNumber;
        }
    }

}