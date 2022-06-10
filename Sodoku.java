import java.util.Iterator;
import java.util.Iterator;
import java.util.Vector;

public class Sodoku {
    public static void main(String args[]) {
        Table table = new Table();
        table.setValue(1, 1, 3, 2);
        table.setValue(1, 2, 2, 5);
        table.setValue(1, 3, 1, 6);
        table.setValue(1, 3, 3, 8);
        table.setValue(2, 1, 1, 1);
        table.setValue(2, 2, 1, 8);
        table.setValue(3, 1, 1, 4);
        table.setValue(3, 3, 1, 5);
        table.setValue(3, 3, 3, 2);
        table.setValue(4, 1, 2, 7);
        table.setValue(4, 2, 1, 4);
        table.setValue(4, 2, 2, 2);
        table.setValue(5, 1, 2, 4);
        table.setValue(5, 3, 2, 9);
        table.setValue(6, 2, 2, 6);
        table.setValue(6, 2, 3, 3);
        table.setValue(6, 3, 2, 4);
        table.setValue(7, 1, 1, 2);
        table.setValue(7, 1, 3, 3);
        table.setValue(7, 2, 3, 9);
        table.setValue(7, 3, 3, 7);
        table.setValue(8, 2, 3, 6);
        table.setValue(8, 3, 3, 8);
        table.setValue(9, 1, 1, 7);
        table.setValue(9, 1, 3, 1);
        table.setValue(9, 2, 2, 3);
        table.setValue(9, 3, 1, 6);
        System.out.println();
        table.print();

        System.out.println("Analysis");
        table.analyze();
    }
}

class Table {
    public void analyze() {
        // For each possible value, scan the entire row, and if that value is
        // only in one
        // of the possible lists for a cell it must be the value for that cell.
        for (int testValue = 1; testValue <= 9; testValue++) {
            boolean found = false;
            int savedRow = 0;
            int savedColumn = 0;
            while (true) {
                for (int row = 1; row <= 9; row++) {
                    for (int column = 1; column <= 9; column++) {
                        if (getPossibleValues(row, column).contains(
                                                                    new Integer(testValue))) {
                            if (found == true) {
                                break;
                            }
                            savedRow = row;
                            savedColumn = column;
                        }
                    }
                }

                if (savedRow > 0 && savedColumn > 0) {
                    setValue(savedRow, savedColumn, testValue);
                }
                break;
            }
        }

        // For each possible value, scan the entire column, and if that value is
        // only in one
        // of the possible lists for a cell it must be the value for that cell.
        for (int testValue = 1; testValue <= 9; testValue++) {
            boolean found = false;
            int savedRow = 0;
            int savedColumn = 0;
            while (true) {
                for (int column = 1; column <= 9; column++) {
                    for (int row = 1; row <= 9; row++) {
                        if (getPossibleValues(row, column).contains(
                                                                    new Integer(testValue))) {
                            if (found == true) {
                                break;
                            }
                            savedRow = row;
                            savedColumn = column;
                        }
                    }
                }

                if (savedRow > 0 && savedColumn > 0) {
                    setValue(savedRow, savedColumn, testValue);
                }
                break;
            }
        }

        for (int testQuad = 1; testQuad <= 9; testQuad++) {
            Quad quad = quads[testQuad];

            for (int testValue = 1; testValue <= 9; testValue++) {
                boolean found = false;
                int savedRow = 0;
                int savedColumn = 0;
                while (true) {
                    for (int row = 1; row <= 3; row++) {
                        for (int column = 1; column <= 3; column++) {
                            if (quad.getPossibleValues(row, column).contains(
                                                                             new Integer(testValue))) {
                                if (found == true) {
                                    break;
                                }
                                savedRow = row;
                                savedColumn = column;
                            }
                        }
                    }

                    if (savedRow > 0 && savedColumn > 0) {
                        setValue(savedRow, savedColumn, testValue);
                    }
                    break;
                }
            }
        }
    }

    protected Quad[] quads = new Quad[10];

    public Table() {
        for (int i = 1; i <= 9; i++) {
            quads[i] = new Quad();
        }
    }

    public Vector<Integer> getPossibleValues(int row, int column) {
        int quadRow = (row - 1) % 3 + 1;
        int quadColumn = (column - 1) % 3 + 1;
        return quads[getQuad(row, column)].getPossibleValues(quadRow,
                                                             quadColumn);
    }

    public int getValue(int row, int column) {
        int quadRow = (row - 1) % 3 + 1;
        int quadColumn = (column - 1) % 3 + 1;
        return quads[getQuad(row, column)].getValue(quadRow, quadColumn);
    }

    public void setValue(int row, int column, int value) {
        int quadRow = (row - 1) % 3 + 1;
        int quadColumn = (column - 1) % 3 + 1;
        setValue(getQuad(row, column), quadRow, quadColumn, value);
    }

    public void setValue(int quad, int row, int column, int value) {
        quads[quad].setValue(row, column, value);

        quads[quad].removePossibleValue(value);

        Vector<Integer> rowPeerQuads = new Vector();
        Vector<Integer> columnPeerQuads = new Vector();
        switch (quad) {
        case 1:
            rowPeerQuads.add(new Integer(2));
            rowPeerQuads.add(new Integer(3));
            columnPeerQuads.add(new Integer(4));
            columnPeerQuads.add(new Integer(7));
            break;
        case 2:
            rowPeerQuads.add(new Integer(1));
            rowPeerQuads.add(new Integer(3));
            columnPeerQuads.add(new Integer(5));
            columnPeerQuads.add(new Integer(8));
            break;

        case 3:
            rowPeerQuads.add(new Integer(1));
            rowPeerQuads.add(new Integer(2));
            columnPeerQuads.add(new Integer(6));
            columnPeerQuads.add(new Integer(9));
            break;

        case 4:
            columnPeerQuads.add(new Integer(1));
            rowPeerQuads.add(new Integer(5));
            rowPeerQuads.add(new Integer(6));
            columnPeerQuads.add(new Integer(7));
            break;

        case 5:
            columnPeerQuads.add(new Integer(2));
            rowPeerQuads.add(new Integer(4));
            rowPeerQuads.add(new Integer(6));
            columnPeerQuads.add(new Integer(8));
            break;

        case 6:
            columnPeerQuads.add(new Integer(3));
            rowPeerQuads.add(new Integer(4));
            rowPeerQuads.add(new Integer(5));
            columnPeerQuads.add(new Integer(9));
            break;

        case 7:
            columnPeerQuads.add(new Integer(1));
            columnPeerQuads.add(new Integer(4));
            rowPeerQuads.add(new Integer(8));
            rowPeerQuads.add(new Integer(9));
            break;

        case 8:
            columnPeerQuads.add(new Integer(2));
            columnPeerQuads.add(new Integer(5));
            rowPeerQuads.add(new Integer(7));
            rowPeerQuads.add(new Integer(9));
            break;

        case 9:
            columnPeerQuads.add(new Integer(3));
            columnPeerQuads.add(new Integer(6));
            rowPeerQuads.add(new Integer(7));
            rowPeerQuads.add(new Integer(8));
            break;
        }

        Iterator rowPeerIterator = rowPeerQuads.iterator();
        while (rowPeerIterator.hasNext()) {
            int quadRowPeer = ((Integer) rowPeerIterator.next()).intValue();
            quads[quadRowPeer].removePossibleRowValue(row, value);
        }

        Iterator columnPeerIterator = rowPeerQuads.iterator();
        while (columnPeerIterator.hasNext()) {
            int quadColumnPeer = ((Integer) columnPeerIterator.next())
                .intValue();
            quads[quadColumnPeer].removePossibleColumnValue(column, value);
        }
    }

    public void print() {

        for (int row = 1; row <= 9; row++) {
            // System.out.println("Row: " + row);
            for (int cellRow = 1; cellRow <= 3; cellRow++) {
                // System.out.println("Cell row: " + cellRow);
                for (int column = 1; column <= 9; column++) {

                    int quadRow = (row - 1) % 3 + 1;
                    int quadColumn = (column - 1) % 3 + 1;
                    int value = quads[getQuad(row, column)].getValue(quadRow,
                                                                     quadColumn);
                    Vector<Integer> possibleValues = quads[getQuad(row, column)]
                        .getPossibleValues(quadRow, quadColumn);
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
                            // System.out.println("Testing for: " +
                            // cellPossibleValue);
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

    public void printQuad(int quad) {
        quads[quad].print();
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
}

class Quad {
    protected Cell[][] cells = new Cell[4][4];

    public Quad() {
        for (int row = 1; row <= 3; row++) {
            for (int column = 1; column <= 3; column++) {
                cells[row][column] = new Cell();
            }
        }
    }

    public void print() {
        for (int row = 1; row <= 3; row++) {
            for (int column = 1; column <= 3; column++) {
                System.out.print(cells[row][column].getValue() + " ");

            }
            System.out.println();
        }
    }

    public void setValue(int row, int column, int value) {
        System.out.println("Cell (" + row + ":" + column + "): " + value);
        cells[row][column].setValue(value);
    }

    public int getValue(int row, int column) {
        return cells[row][column].getValue();
    }

    public Vector getPossibleValues(int row, int column) {
        return cells[row][column].getPossibleValues();
    }

    public void removePossibleValue(int value) {
        for (int row = 1; row <= 3; row++) {
            for (int column = 1; column <= 3; column++) {
                cells[row][column].removePossibleValue(value);
            }
        }
    }

    public void removePossibleRowValue(int row, int value) {
        for (int column = 1; column <= 3; column++) {
            cells[row][column].removePossibleValue(value);
        }
    }

    public void removePossibleColumnValue(int column, int value) {
        for (int row = 1; row <= 3; row++) {
            cells[row][column].removePossibleValue(value);
        }
    }
}

class Cell {
    protected int value = 0;
    protected Vector<Integer> possibleValues = new Vector(9);

    public Cell() {
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

    public Vector<Integer> getPossibleValues() {
        return possibleValues;
    }

    public void removePossibleValue(int value) {
        Integer valueInteger = new Integer(value);
        possibleValues.remove(valueInteger);

        if (possibleValues.size() == 1) {
            value = -1;
        }
    }
}
