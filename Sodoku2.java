package game;

import java.util.Vector;

public class Sodoku2 {
    public static void main(String args[]) {

         Table table = new Table();
//         table.setValue(1, 3, 2);
//         table.setValue(1, 4, 1);
//         table.setValue(1, 7, 4);
//         table.setValue(2, 2, 5);
//         table.setValue(2, 4, 8);
//         table.setValue(3, 1, 6);
//         table.setValue(3, 3, 8);
//         table.setValue(3, 7, 5);
//         table.setValue(3, 9, 2);
//         table.setValue(4, 2, 7);
//         table.setValue(4, 5, 4);
//         table.setValue(5, 1, 4);
//         table.setValue(5, 2, 2);
//         table.setValue(5, 8, 6);
//         table.setValue(5, 9, 3);
//         table.setValue(6, 5, 9);
//         table.setValue(6, 8, 4);
//         table.setValue(7, 1, 2);
//         table.setValue(7, 3, 3);
//         table.setValue(7, 7, 7);
//         table.setValue(7, 9, 1);
//         table.setValue(8, 3, 9);
//         table.setValue(8, 6, 6);
//         table.setValue(8, 8, 3);
//         table.setValue(9, 3, 7);
//         table.setValue(9, 6, 8);
//         table.setValue(9, 7, 6);

//         table.setValue(1, 2, 1);
//         table.setValue(1, 6, 7);
//         table.setValue(2, 3, 9);
//         table.setValue(2, 7, 5);
//         table.setValue(3, 1, 4); 
//         table.setValue(3, 2, 5);
//         table.setValue(3, 3, 7);
//         table.setValue(3, 7, 9);
//         table.setValue(3, 8, 1);
//         table.setValue(4, 1, 3);
//         table.setValue(4, 5, 6);
//         table.setValue(4, 9, 2);
//         table.setValue(5, 4, 7);
//         table.setValue(5, 6, 4);
//         table.setValue(6, 5, 9);
//         table.setValue(6, 9, 8);
//         table.setValue(7, 2, 7);
//         table.setValue(7, 3, 2);
//         table.setValue(7, 7, 3);
//         table.setValue(7, 9, 5);
//         table.setValue(8, 3, 5);
//         table.setValue(8, 6, 6);
//         table.setValue(8, 7, 2);
//         table.setValue(9, 4, 8);
//         table.setValue(9, 8, 6);

         table.setValue(1, 1, 2);
         table.setValue(1, 6, 4);
         table.setValue(1, 9, 1);
         table.setValue(2, 2, 9);
         table.setValue(2, 5, 5);
         table.setValue(3, 2, 8);
         table.setValue(3, 4, 7);
         table.setValue(3, 5, 2);
         table.setValue(3, 6, 9);
         table.setValue(3, 7, 5);
         table.setValue(4, 2, 6);
         table.setValue(4, 7, 4);
         table.setValue(4, 8, 9);
         table.setValue(5, 7, 2);
         table.setValue(6, 2, 3);
         table.setValue(6, 3, 7);
         table.setValue(6, 8, 5);
         table.setValue(7, 3, 9);
         table.setValue(7, 4, 6);
         table.setValue(7, 5, 7);
         table.setValue(7, 8, 1);
         table.setValue(8, 5, 4);
         table.setValue(8, 8, 8);
         table.setValue(9, 1, 1);
         table.setValue(9, 4, 9);
         table.setValue(9, 9, 4);


        table.print();

        int count = 1;
        while (true) {

            if (table.analze_singlePossibleValue()) {
                continue;
            }
            if (table.analyze_possibleValueOccursOnceInQuad()) {
                continue;
            }
            if (table.analyze_possibleValueOccursOnceInColumn()) {
                continue;
            }
            if (table.analyze_possibleValueOccursOnceInRow()) {
                continue;
            }
            if (table.analyze_quadRowMatchedPair()) {
                continue;
            }
            if (table.analyze_quadColumnMatchedPair()) {
                continue;
            }

            break;
        }

        table.print();
    }
}


class Cell {
    protected int row = -1;
    protected int column = -1;
    protected int value = -1;
    protected Vector<Integer> possibleValues = new Vector(9);

    public Cell(int row,
                int column)
    {
        this.row = row;
        this.column = column;
        possibleValues.add(new Integer(1));
        possibleValues.add(new Integer(2));
        possibleValues.add(new Integer(3));
        possibleValues.add(new Integer(4));
        possibleValues.add(new Integer(5));
        possibleValues.add(new Integer(6));
        possibleValues.add(new Integer(7));
        possibleValues.add(new Integer(8));
        possibleValues.add(new Integer(9));
    }

    public void setValue(int value) {
        this.value = value;
        possibleValues.clear();
    }

    public int getValue() {
        return value;
    }

    public boolean hasValue() {
        if (value == -1) {
            return false;
        }
        return true;
    }

    public Vector<Integer> getPossibleValues() {
        return possibleValues;
    }

    public void removePossibleValue(int value) {
        Integer valueInteger = new Integer(value);
        possibleValues.remove(valueInteger);
    }
}


class Table {
    protected Cell[][] cells = new Cell[10][10];

    public Table() {
        for (int row = 1; row <= 9; row++) {
            for (int column = 1; column <= 9; column++) {
                cells[row][column] = new Cell(row, column);
            }
        }
    }

    public Vector<Integer> getPossibleValues(int row, int column) {
        return cells[row][column].getPossibleValues();
    }

    public int getValue(int row, int column) {
        return cells[row][column].getValue();
    }

    public void setValueReason(int row, int column, int value, String reason) {
        System.out.println("cell[" + row + ":" + column + "]=" + value + " [" + reason + "]");
        setValue(row, column, value);
    }
    
    public void removePossibleValueReason(int row, int column, int value, String reason) {
        if (cells[row][column].getPossibleValues().contains(new Integer(value)) == false) {
            return;
        }

        System.out.println("cell[" + row + ":" + column + "] Removing Possible Value " + value + " [" + reason + "]");
        cells[row][column].removePossibleValue(value);
    }


    public void setValue(int row, int column, int value) {
        cells[row][column].setValue(value);


        for (int peerRow = 1; peerRow <= 9; peerRow++) {
            if (peerRow == row) {
                continue;
            }

            cells[peerRow][column].removePossibleValue(value);
        }

        for (int peerColumn = 1; peerColumn <= 9; peerColumn++) {
            if (peerColumn == column) {
                continue;
            }

            cells[row][peerColumn].removePossibleValue(value);
        }

        int quad = getQuad(row, column);
        for (int quadRow = getFirstRow(quad); quadRow <= getLastRow(quad); quadRow++) {
            for (int quadColumn = getFirstColumn(quad); quadColumn <= getLastColumn(quad); quadColumn++) {
                if (quadRow == row && quadColumn == column) {
                    continue;
                }
                cells[quadRow][quadColumn].removePossibleValue(value);
            }
        }
    }

    protected int getFirstRow(int quad) {
        if (quad == 1 || quad == 2 || quad == 3) {
            return 1;
        }

        if (quad == 4 || quad == 5 || quad == 6) {
            return 4;
        }

        return 7;
    }

    protected int getLastRow(int quad) {
        if (quad == 1 || quad == 2 || quad == 3) {
            return 3;
        }

        if (quad == 4 || quad == 5 || quad == 6) {
            return 6;
        }

        return 9;
    }

    protected int getFirstColumn(int quad) {
        if (quad == 1 || quad == 4 || quad == 7) {
            return 1;
        }

        if (quad == 2 || quad == 5 || quad == 8) {
            return 4;
        }

        return 7;
    }

    protected int getLastColumn(int quad) {
        if (quad == 1 || quad == 4 || quad == 7) {
            return 3;
        }

        if (quad == 2 || quad == 5 || quad == 8) {
            return 6;
        }

        return 9;

    }

    protected int getQuad(int row, int column) {
        if (row >= 1 && row <= 3) {
            if (column <= 3) {
                return 1;
            }
            if (column <= 6) {
                return 2;
            }
            return 3;
        }
        if (row >= 4 && row <= 6) {
            if (column <= 3) {
                return 4;
            }
            if (column <= 6) {
                return 5;
            }
            return 6;
        }
        if (column <= 3) {
            return 7;
        }
        if (column <= 6) {
            return 8;
        }
        return 9;
    }

    public void print() {
        for (int row = 1; row <= 9; row++) {
            for (int cellRow = 1; cellRow <= 3; cellRow++) {
                for (int column = 1; column <= 9; column++) {

                    int value = cells[row][column].getValue();
                    Vector<Integer> possibleValues = cells[row][column]
                        .getPossibleValues();

                    // System.out.println("value: " + value);
                    // System.out.println("possibleValues: " + possibleValues);

                    int cellPossibleValue = 1;
                    if (cellRow == 2) {
                        cellPossibleValue = 4;
                    }
                    if (cellRow == 3) {
                        cellPossibleValue = 7;
                    }

                    for (int cellColumn = 1; cellColumn <= 3; cellColumn++) {
                        if (possibleValues.size() > 0) {
                            if (possibleValues.contains(new Integer(
                                                                    cellPossibleValue))) {
                                System.out.print(" " + cellPossibleValue + " ");
                            } else {
                                System.out.print("   ");
                            }
                            System.out.print(" ");
                            cellPossibleValue++;
                        } else {
                            if (cellRow == 2 && cellColumn == 2) {
                                System.out.print("[" + value + "]");
                            } else {
                                System.out.print("   ");
                            }
                            System.out.print(" ");
                        }
                    }
                    if (column == 3 || column == 6) {
                        System.out.print(" # ");
                    } else {
                        System.out.print(" ' ");
                    }
                }
                System.out.println();

            }
            System.out.println();
            if (row == 3 || row == 6) {
                System.out
                    .println("======================================================================================================================================");
            } else {
                System.out
                    .println("-------------------------------------------------------------------------------------------------------------------------------------");
            }
        }
    }

    public boolean analze_singlePossibleValue()
    {
        for (int row = 1; row <= 9; row++) {
            for (int column = 1; column <= 9; column++) {
                if (cells[row][column].getPossibleValues().size() == 1) {
                    setValueReason(row, column, ((Integer) cells[row][column].getPossibleValues().get(0)).intValue(), "singlePossibleValue");
                    return true;
                }
            }
        }

        return false;
    }

    public boolean analyze_possibleValueOccursOnceInRow() {
        // Scan all rows ...
        for (int testValue = 1; testValue <= 9; testValue++) {
            for (int row = 1; row <= 9; row++) {

                // Walk across the entire row looking to see if testValue is:
                // 1) already a solved value in one of the 9 cells
                // - in this case go onto the next row
                // 2) only appears as a possible value in one of the 9 cells
                // - in which case set testValue as the value of the cell
                int columnContainingTestValue = 0;
                boolean testValueFailed = false;
                for (int column = 1; column <= 9; column++) {
                    if (cells[row][column].getValue() == testValue) {
                        testValueFailed = true;
                        break;
                    }
                    if (cells[row][column].getPossibleValues().contains(
                                                                        new Integer(testValue))) {
                        if (columnContainingTestValue > 0) {
                            // The possible value existed atleast twice
                            testValueFailed = true;
                            break;
                        }
                        columnContainingTestValue = column;
                    }
                }
                if (testValueFailed == false && columnContainingTestValue > 0) {
                    setValueReason(row, columnContainingTestValue, testValue, "possibleValueOccursOnceInRow");
                    return true;
                }
            }
        }

        return false;
    }

    public boolean analyze_possibleValueOccursOnceInColumn() {
        // Scan all columns ...
        for (int testValue = 1; testValue <= 9; testValue++) {
            for (int column = 1; column <= 9; column++) {

                // Walk down the entire column looking to see if testValue is:
                // 1) already a solved value in one of the 9 cells
                // - in this case go onto the next column
                // 2) only appears as a possible value in one of the 9 cells
                // - in which case set testValue as the value of the cell
                int rowContainingTestValue = 0;
                boolean testValueFailed = false;
                for (int row = 1; row <= 9; row++) {
                    if (cells[row][column].getValue() == testValue) {
                        testValueFailed = true;
                        break;
                    }
                    if (cells[row][column].getPossibleValues().contains(
                                                                        new Integer(testValue))) {
                        if (rowContainingTestValue > 0) {
                            // The possible value existed atleast twice
                            testValueFailed = true;
                            break;
                        }
                        rowContainingTestValue = row;
                    }
                }
                if (testValueFailed == false && rowContainingTestValue > 0) {
                    setValueReason(rowContainingTestValue, column, testValue, "possibleValueOccursOnceInColumn");
                    return true;
                }
            }
        }

        return false;
    }

    public boolean analyze_possibleValueOccursOnceInQuad()
    {
        // Scan all cells in each quad ...
        for (int quad = 1; quad <= 9; quad++) {
            for (int testValue = 1; testValue <= 9; testValue++) {
                // Walk across the entire set of cells in the quad looking to see if testValue is:
                // 1) already a solved value in one of the 9 cells
                //    - in this case go onto the next testValue (or quad)
                // 2) only appears as a possible value in one of the 9 cells
                //    - in which case set testValue as the value of the cell
                int rowContainingTestValue = 0;
                int columnContainingTestValue = 0;
                boolean testValueFailed = false;
                for (int row = getFirstRow(quad); row <= getLastRow(quad) && testValueFailed == false; row++) {
                    for (int column = getFirstColumn(quad); column <= getLastColumn(quad); column++) {
                        if (cells[row][column].getValue() == testValue) {
                            testValueFailed = true;
                            break;
                        }
                        if (cells[row][column].getPossibleValues().contains(new Integer(testValue))) {
                            if (columnContainingTestValue > 0 && rowContainingTestValue > 0) {
                                // The possible value existed atleast twice
                                testValueFailed = true;
                                break;
                            }
                            rowContainingTestValue = row;
                            columnContainingTestValue = column;
                        }
                    }
                }
                if (testValueFailed == false && rowContainingTestValue > 0 && columnContainingTestValue > 0) {
                    setValueReason(rowContainingTestValue, columnContainingTestValue, testValue, "possibleValueOccursOnceInQuad");
                    return true;
                }
            }
        }

        return false;
    }
    
    static Vector<String> quadRowMatchedPair = new Vector<String>();
    
    public boolean analyze_quadRowMatchedPair()
    {
        // Scan all cells in each quad ...
        for (int quad = 1; quad <= 9; quad++) {
            for (int row = getFirstRow(quad); row <= getLastRow(quad); row++) {

                int potentialColumn1 = 0;
                int potentialColumn2 = 0;
                int column1 = getFirstColumn(quad);
                int column2 = (column1 + 1);
                int column3 = (column2 + 1);

                if (cells[row][column1].getValue() > 0 &&
                    cells[row][column2].getPossibleValues().size() == 2 &&
                    cells[row][column3].getPossibleValues().size() == 2) {

                    potentialColumn1 = column2;
                    potentialColumn2 = column3;
                }
                
                if (cells[row][column1].getPossibleValues().size() == 2 &&
                    cells[row][column2].getValue() > 0 &&
                    cells[row][column3].getPossibleValues().size() == 2) {

                    potentialColumn1 = column1;
                    potentialColumn2 = column3;
                }
                
                if (cells[row][column1].getPossibleValues().size() == 2 &&
                    cells[row][column2].getPossibleValues().size() == 2 &&
                    cells[row][column3].getValue() > 0) {

                    potentialColumn1 = column1;
                    potentialColumn2 = column2;
                }
                
                if (potentialColumn1 == 0) {
                    continue;
                }

                Integer value1 = (Integer) cells[row][potentialColumn1].getPossibleValues().get(0);
                Integer value2 = (Integer) cells[row][potentialColumn1].getPossibleValues().get(1);

                if ((cells[row][potentialColumn2].getPossibleValues().contains(value1) &&
                     cells[row][potentialColumn2].getPossibleValues().contains(value2)) == false) {
                    continue;
                }
                
                String s = quad + " " + row;
                if (quadRowMatchedPair.contains(s)) {
                    continue;
                }

                System.out.println("cell[" + row + ":" + potentialColumn1 +"] and cell[" + row + ":" + potentialColumn2 + "]=" + value1 + "," + value2);
                quadRowMatchedPair.add(s);
                
                
                for (int columnToRemoveValueFrom = 1; columnToRemoveValueFrom <= 9; columnToRemoveValueFrom++) {
                    if (columnToRemoveValueFrom == potentialColumn1 ||
                        columnToRemoveValueFrom == potentialColumn2) {
                        continue;
                    }
                    removePossibleValueReason(row, columnToRemoveValueFrom, value1.intValue(), "quadRowMatchedPair (Same Row)");
                    removePossibleValueReason(row, columnToRemoveValueFrom, value2.intValue(), "quadRowMatchedPair (Same Row)");
                }

                for (int rowToRemoveValueFrom = getFirstRow(quad); rowToRemoveValueFrom <= getLastRow(quad); rowToRemoveValueFrom++) {
                    for (int columnToRemoveValueFrom = getFirstColumn(quad); columnToRemoveValueFrom <= getLastRow(quad); columnToRemoveValueFrom++) {
                        if (rowToRemoveValueFrom == row && columnToRemoveValueFrom == potentialColumn1 ||
                            rowToRemoveValueFrom == row && columnToRemoveValueFrom == potentialColumn2) {
                            continue;
                        }
                        removePossibleValueReason(rowToRemoveValueFrom, columnToRemoveValueFrom, value1.intValue(), "quadRowMatchedPair (Same Quad)");
                        removePossibleValueReason(rowToRemoveValueFrom, columnToRemoveValueFrom, value2.intValue(), "quadRowMatchedPair (Same Quad)");
                    }
                }

                return true;
            }
        }

        return false;
    }

    static Vector<String> quadColumnMatchedPair = new Vector<String>();
    
    public boolean analyze_quadColumnMatchedPair()
    {
        // Scan all cells in each quad ...
        for (int quad = 1; quad <= 9; quad++) {
            for (int column = getFirstColumn(quad); column <= getLastColumn(quad); column++) {

                int potentialRow1 = 0;
                int potentialRow2 = 0;
                int row1 = getFirstRow(quad);
                int row2 = (row1 + 1);
                int row3 = (row2 + 1);

                if (cells[row1][column].getValue() > 0 &&
                    cells[row2][column].getPossibleValues().size() == 2 &&
                    cells[row3][column].getPossibleValues().size() == 2) {

                    potentialRow1 = row2;
                    potentialRow2 = row3;
                }
                
                if (cells[row1][column].getPossibleValues().size() == 2 &&
                    cells[row2][column].getValue() > 0 &&
                    cells[row3][column].getPossibleValues().size() == 2) {

                    potentialRow1 = row1;
                    potentialRow2 = row3;
                }
                
                if (cells[row1][column].getPossibleValues().size() == 2 &&
                    cells[row2][column].getPossibleValues().size() == 2 &&
                    cells[row3][column].getValue() > 0) {

                    potentialRow1 = row1;
                    potentialRow2 = row2;
                }
                
                if (potentialRow1 == 0) {
                    continue;
                }

                Integer value1 = (Integer) cells[potentialRow1][column].getPossibleValues().get(0);
                Integer value2 = (Integer) cells[potentialRow1][column].getPossibleValues().get(1);

                if ((cells[potentialRow2][column].getPossibleValues().contains(value1) &&
                     cells[potentialRow2][column].getPossibleValues().contains(value2)) == false) {
                    continue;
                }
                
                String s = quad + " " + column;
                if (quadColumnMatchedPair.contains(s)) {
                    continue;
                }

                System.out.println("cell[" + potentialRow1 + ":" + column +"] and cell[" + potentialRow2 + ":" + column + "]=" + value1 + "," + value2);
                quadColumnMatchedPair.add(s);
                
                
                for (int rowToRemoveValueFrom = 1; rowToRemoveValueFrom <= 9; rowToRemoveValueFrom++) {
                    if (rowToRemoveValueFrom == potentialRow1 ||
                        rowToRemoveValueFrom == potentialRow2) {
                        continue;
                    }
                    removePossibleValueReason(column, rowToRemoveValueFrom, value1.intValue(), "quadColumnMatchedPair (Same Column)");
                    removePossibleValueReason(column, rowToRemoveValueFrom, value2.intValue(), "quadColumnMatchedPair (Same Column)");
                }

                for (int rowToRemoveValueFrom = getFirstRow(quad); rowToRemoveValueFrom <= getLastRow(quad); rowToRemoveValueFrom++) {
                    for (int columnToRemoveValueFrom = getFirstColumn(quad); columnToRemoveValueFrom <= getLastColumn(quad); columnToRemoveValueFrom++) {
                        if (columnToRemoveValueFrom == column && rowToRemoveValueFrom == potentialRow1 ||
                            columnToRemoveValueFrom == column && rowToRemoveValueFrom == potentialRow2) {
                            continue;
                        }
                        removePossibleValueReason(rowToRemoveValueFrom, columnToRemoveValueFrom, value1.intValue(), "quadColumnMatchedPair (Same Quad)");
                        removePossibleValueReason(rowToRemoveValueFrom, columnToRemoveValueFrom, value2.intValue(), "quadColumnMatchedPair (Same Quad)");
                    }
                }

                return true;
            }
        }

        return false;
    }

}
