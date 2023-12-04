package day3;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day3/input.txt");
        //File inputFile = new File("./src/day3/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<String> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            temps.add(scanner.nextLine());
        }
        scanner.close();

        Char[][] input = new Char[temps.size()][temps.get(0).length()];
        char[][] inputChar = new char[temps.size()][temps.get(0).length()];
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.get(0).length(); j++) {
                input[i][j] = new Char(temps.get(i).charAt(j));
                inputChar[i][j] = temps.get(i).charAt(j);
            }
        }

        int result1 = solve1(input);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(inputChar);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(Char[][] input) {
        List<Number> numbers = new LinkedList<>();
        // first pass, mark characters
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (hasSurroundingSymbol(i, j, input)) {
                    input[i][j].adjacentToSymbol = true;
                }

            }
        }
        //second pass, create and mark numbers
        for (Char[] chars : input) {
            Number currNumber = new Number();
            for (int j = 0; j < input[0].length; j++) {
                if (Character.isDigit(chars[j].character)) {
                    currNumber.addChar(chars[j]);
                } else {
                    if (!currNumber.chars.isEmpty()) {
                        numbers.add(currNumber);
                    }
                    currNumber = new Number();
                }
            }
            if (!currNumber.chars.isEmpty()) {
                numbers.add(currNumber);
            }
        }
        return numbers.stream().filter(n -> n.adjacentToSymbol).mapToInt(Number::getInt).sum();
    }

    private static int solve2(char[][] input) {
        int sum = 0;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (input[i][j] == '*') {
                    Map<Map.Entry<Integer, Integer>, Integer> map = new HashMap<>();
                    sum += getParts(input, i, j, map);
                }
            }
        }

        return sum;
    }


    public static boolean hasSurroundingSymbol(int i, int j, Char[][] currInputs) {
        boolean isSymbol = false;
        isSymbol |= isSymbol(i - 1, j - 1, currInputs);
        isSymbol |= isSymbol(i - 1, j, currInputs);
        isSymbol |= isSymbol(i - 1, j + 1, currInputs);
        isSymbol |= isSymbol(i, j - 1, currInputs);
        isSymbol |= isSymbol(i, j + 1, currInputs);
        isSymbol |= isSymbol(i + 1, j - 1, currInputs);
        isSymbol |= isSymbol(i + 1, j, currInputs);
        isSymbol |= isSymbol(i + 1, j + 1, currInputs);
        return isSymbol;
    }


    public static boolean isSymbol(int i, int j, Char[][] currInputs) {
        if (i < 0 || i >= currInputs.length || j < 0 || j >= currInputs[0].length) {
            return false;
        }
        return !Character.isDigit(currInputs[i][j].character) && currInputs[i][j].character != '.';
    }

    private static final int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    private static int getParts(char[][] arr, int i, int j, Map<Map.Entry<Integer, Integer>, Integer> map) {
        for (int[] direction : directions) {
            Endpoints endpoints = getEndpoints(arr, i, j, direction);
            if (endpoints == null) continue;
            String strNum = String.copyValueOf(arr[endpoints.row()], endpoints.start(), endpoints.end() - endpoints.start() + 1);
            map.merge(Map.entry(endpoints.row(), endpoints.start()), Integer.parseInt(strNum), Math::max);
        }
        return map.size() == 2 ? map.values().stream().reduce(1, Math::multiplyExact) : 0;
    }

    private static Endpoints getEndpoints(char[][] arr, int i, int j, int[] direction) {
        int row = i + direction[0];
        int col = j + direction[1];

        if (row < 0 || col < 0 || row >= arr.length || col >= arr[0].length || !Character.isDigit(arr[row][col]))
            return null;

        try {
            while (Character.isDigit(arr[row][col - 1])) col--;
        } catch (Exception ignored) { }
        int start = col;

        try {
            while (Character.isDigit(arr[row][col + 1])) col++;
        } catch (Exception ignored) {}
        int end = col;

        return new Endpoints(row, start, end);
    }

    private record Endpoints(int row, int start, int end) {
    }

    static class Number {
        private final List<Char> chars;

        boolean adjacentToSymbol;

        Number() {
            this.chars = new LinkedList<>();
            this.adjacentToSymbol = false;
        }

        public int getInt() {
            return Integer.parseInt(chars.stream().map(c -> c.character.toString()).reduce("", String::concat));
        }

        public void addChar(Char c) {
            chars.add(c);
            adjacentToSymbol |= c.adjacentToSymbol;
        }
    }


    public static class Char {
        Character character;
        boolean adjacentToSymbol = false;

        Char(Character number) {
            this.character = number;
        }
    }

}