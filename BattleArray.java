package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class BattleArray {
    //attribute
    private final char[][] battleArray;

    //constructor
    public BattleArray() {
        final char waveSymbol = '~';
        final int battleNumOfRows = 10;
        final int battleNumOfColumns = 10;

        battleArray = new char[battleNumOfRows][battleNumOfColumns];

        int i = 0;
        while (i < battleArray.length) {
            Arrays.fill(battleArray[i], waveSymbol);
            i++;
        }
    }

    //supporting methods
    public int[] parsingCoordinates(String[] coordinates) {

        final int charsShift = 64;
        int row1 = coordinates[0].charAt(0) - charsShift;
        int row2 = coordinates[1].charAt(0) - charsShift;

        StringBuilder columnPart1 = new StringBuilder();
        columnPart1.append(coordinates[0]);
        int column1 = Integer.parseInt(columnPart1.substring(1));

        StringBuilder columnPart2 = new StringBuilder();
        columnPart2.append(coordinates[1]);
        int column2 = Integer.parseInt(columnPart2.substring(1));

        return new int[]{row1, column1, row2, column2};
    }

    public boolean battleArrayModifying(int[] coordinatesArray, int shipLength, String name) {

        int rowStart;
        int rowEnd;
        int columnStart;
        int columnEnd;
        final char zeroSymbol = 'O';
        boolean isModified = false;

        //sorting coordinates ascending
        if (coordinatesArray[0] <= coordinatesArray[2]) {
            rowStart = coordinatesArray[0] - 1;
            rowEnd = coordinatesArray[2] - 1;
        } else {
            rowStart = coordinatesArray[2] - 1;
            rowEnd = coordinatesArray[0] - 1;
        }

        if (coordinatesArray[1] <= coordinatesArray[3]) {
            columnStart = coordinatesArray[1] - 1;
            columnEnd = coordinatesArray[3] - 1;
        } else {
            columnStart = coordinatesArray[3] - 1;
            columnEnd = coordinatesArray[1] - 1;
        }

        //checking of coordinates
        //1. length checking
        if (shipLength != rowEnd - rowStart + 1 && shipLength != columnEnd - columnStart + 1) {
            System.out.printf("Error! Wrong length of the %s! Try again:\n", name);
            return isModified;
        }

        //2. location checking
        if (rowStart != rowEnd && columnStart != columnEnd) {
            System.out.println("Error! Wrong ship location! Try again:");
            return isModified;
        }

        //3. close checking
        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = columnStart; j <= columnEnd; j++) {
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        if (i + m >= 0 && j + n >= 0 && i + m < battleArray[0].length && j + n < battleArray[0].length) {
                            if (battleArray[i + m][j + n] == zeroSymbol) {
                                System.out.println("Error! You placed it too close to another one. Try again:");
                                return isModified;
                            }
                        }
                    }
                }
            }
        }

        //array modifying
        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = columnStart; j <= columnEnd; j++) {
                battleArray[i][j] = zeroSymbol;
            }
        }

        return isModified = true;
    }

    //main methods
    public void battleArrayPrinting() {

        int[] intArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        char[] charArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        System.out.print("  ");
        for (int k : intArray) {
            System.out.print(k + " ");
        }
        System.out.println();

        int i = 0;
        while (i < battleArray.length) {
            System.out.print(charArray[i]+ " ");
            for (int j = 0; j < battleArray.length; j++) {
                System.out.print(battleArray[i][j] + " ");
            }
            System.out.println();
            i++;
        }
    }

    public void setupShips(Ships ship) {

        boolean isModified;
        Scanner scanner = new Scanner(System.in);

        System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getName(), ship.getShipLength());

        int shipLength = ship.getShipLength();
        String name = ship.getName();

        while (true) {
            //coordinates input
            String[] shipCoordinates = scanner.nextLine().toUpperCase().split(" ");

            //getting  coordinates
            int[] shipArray = parsingCoordinates(shipCoordinates);

            //array modifying
            isModified = battleArrayModifying(shipArray, shipLength, name);
            if (isModified) {
                battleArrayPrinting();
                break;
            }
        }
    }

    public int shotTaking() {
        Scanner scanner = new Scanner(System.in);
        final int charsShift = 64;
        final char zeroSymbol = 'O';
        final char hitSymbol = 'X';
        final char missSymbol = 'M';
        final char waveSymbol = '~';
        int sankShips = 0;

        String shotCoordinate = scanner.nextLine().toUpperCase();
        int shotRow = shotCoordinate.charAt(0) - charsShift - 1;
        int shotColumn = Integer.parseInt(shotCoordinate.substring(1)) - 1;
        int lifesCount = 0;

        //checking of coordinates
        if (shotRow < 0 || shotRow > 9 || shotColumn < 0 || shotColumn > 9) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
        }

        //logic about sank the ship to decide what message to display
        if (battleArray[shotRow][shotColumn] == zeroSymbol) {
            battleArray[shotRow][shotColumn] = hitSymbol;

            //check top
            int count = 1;
            while (shotRow - count >= 0 && battleArray[shotRow - count][shotColumn] != missSymbol
                    && battleArray[shotRow - count][shotColumn] != waveSymbol) {
                if (battleArray[shotRow - count][shotColumn] == zeroSymbol) {
                    lifesCount++;
                }
                count++;
            }

            //check bottom
            count = 1;
            while (shotRow + count < battleArray[0].length && battleArray[shotRow + count][shotColumn] != missSymbol
                    && battleArray[shotRow + count][shotColumn] != waveSymbol) {
                if (battleArray[shotRow + count][shotColumn] == zeroSymbol) {
                    lifesCount++;
                }
                count++;
            }

            //check left
            count = 1;
            while (shotColumn - count >= 0 && battleArray[shotRow][shotColumn - count] != missSymbol
                    && battleArray[shotRow][shotColumn - count] != waveSymbol) {
                if (battleArray[shotRow][shotColumn - count] == zeroSymbol) {
                    lifesCount++;
                }
                count++;
            }

            //check right
            count = 1;
            while (shotColumn + count < battleArray[0].length && battleArray[shotRow][shotColumn + count] != missSymbol
                    && battleArray[shotRow][shotColumn + count] != waveSymbol) {
                if (battleArray[shotRow][shotColumn + count] == zeroSymbol) {
                    lifesCount++;
                }
                count++;
            }

            if (lifesCount >= 1) {
                System.out.println("You hit a ship!");
            } else {
                sankShips++;
            }
        } else if (battleArray[shotRow][shotColumn] == waveSymbol) {
            battleArray[shotRow][shotColumn] = missSymbol;
            System.out.println("You missed!");
        } else if (battleArray[shotRow][shotColumn] == hitSymbol) {
            System.out.println("You hit a ship!");
        } else if (battleArray[shotRow][shotColumn] == missSymbol) {
            System.out.println("You missed!");
        }

        return sankShips;
    }

}