package day2;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static day2.Main.CubeColor.RED;

public class Main {

    //12 red cubes, 13 green cubes, and 14 blue cubes
    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day2/input.txt");
        //File inputFile = new File("./src/day2/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<Game> games = new LinkedList<>();
        String currentLine;
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            String[] game = currentLine.split(": ");
            games.add(new Game(Integer.parseInt(game[0].split(" ")[1]),
                    Arrays.stream(game[1].split("; ")).map(Handful::new).toList()));
        }
        scanner.close();
        int result1 = solve1(games);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(games);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(List<Game> input) {
        return input.stream().filter(game -> game.handfuls.stream()
                        .allMatch(h -> h.red.count <= 12 && h.green.count <= 13 && h.blue.count <= 14))
                .mapToInt(game -> game.id).sum();
    }

    private static int solve2(List<Game> input) {
        return input.stream().mapToInt(game -> game.minRed * game.minGreen * game.minBlue).sum();
    }

    static class Game {
        int id;
        List<Handful> handfuls;

        int minRed = 0;
        int minGreen = 0;
        int minBlue = 0;

        public Game(int game, List<Handful> handfuls) {
            this.id = game;
            this.handfuls = handfuls;
            handfuls.forEach(h -> {
                if (h.red.count > minRed) {
                    this.minRed = h.red.count;
                }
                if (h.green.count > minGreen) {
                    this.minGreen = h.green.count;
                }
                if (h.blue.count > minBlue) {
                    this.minBlue = h.blue.count;
                }
            });
        }
    }

    static class Handful {
        public CubeSet red;
        public CubeSet green;
        public CubeSet blue;

        public Handful(String s) {
            this(Arrays.stream(s.split(", ")).map(CubeSet::new).toList());
        }

        public Handful(List<CubeSet> cubeSets) {
            cubeSets.forEach(s -> {
                switch (s.color) {
                    case RED -> this.red = s;
                    case GREEN -> this.green = s;
                    case BLUE -> this.blue = s;
                }
            });
            if (this.red == null) {
                this.red = new CubeSet("0 red");
            }
            if (this.green == null) {
                this.green = new CubeSet("0 green");
            }
            if (this.blue == null) {
                this.blue = new CubeSet("0 blue");
            }

        }

        public Handful(CubeSet set1, CubeSet set2, CubeSet set3) {
            switch (set1.color) {
                case RED -> this.red = set1;
                case GREEN -> this.green = set1;
                case BLUE -> this.blue = set1;
            }
            switch (set2.color) {
                case RED -> this.red = set2;
                case GREEN -> this.green = set2;
                case BLUE -> this.blue = set2;
            }
            switch (set3.color) {
                case RED -> this.red = set3;
                case GREEN -> this.green = set3;
                case BLUE -> this.blue = set3;
            }
        }
    }

    static class CubeSet {
        public int count;
        public CubeColor color;

        public CubeSet(String s) {
            this.count = Integer.parseInt(s.split(" ")[0]);
            String colorString = s.split(" ")[1];
            if (colorString.equals("red")) {
                this.color = RED;
            } else if (colorString.equals("green")) {
                this.color = CubeColor.GREEN;
            } else if (colorString.equals("blue")) {
                this.color = CubeColor.BLUE;
            }
        }
    }

    enum CubeColor {
        RED,
        GREEN,
        BLUE
    }

}