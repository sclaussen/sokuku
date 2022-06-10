import java.util.Iterator;
import java.util.Iterator;
import java.util.Vector;


public class Sodoku2 {
    public static void main(String args[]) {
        Table table = new Table();
        table.setValue(1, 3, 2);
        table.setValue(1, 4, 1);
        table.setValue(1, 7, 4);

        table.setValue(2, 2, 5);
        table.setValue(2, 4, 8);

        table.setValue(3, 1, 6);
        table.setValue(3, 3, 8);
        table.setValue(3, 7, 5);
        table.setValue(3, 9, 2);

        table.setValue(4, 2, 7);
        table.setValue(4, 5, 4);

        table.setValue(5, 1, 4);
        table.setValue(5, 2, 2);
        table.setValue(5, 8, 6);
        table.setValue(5, 9, 3);

        table.setValue(6, 5, 9);
        table.setValue(6, 8, 4);

        table.setValue(7, 1, 2);
        table.setValue(7, 3, 3);
        table.setValue(7, 7, 7);
        table.setValue(7, 9, 1);

        table.setValue(9, 3, 7);
        table.setValue(9, 6, 8);
        table.setValue(9, 7, 6);

        table.print();
    }
}


class Table {
    protected Cell cells[10][10];

    public Table() {
        for (int row = 1; row <= 9; row++) {
            for (int column = 1; column <= 9; column++) {
                cells[row][column] = new Cell();
            }
        }
    }
    
    public Vector<Integer> getPossibleValues(int row, int column) {
        return cells[row][column].getPossibleValues(row, column);
    }

    public int getValue(int row, int column) {
        return cells[row][column].getValue();
    }


    public void setValue(int row, int column, int value) {
        cells[row][column].setValue(value);

        for (int peerRow == 1; peerRow <= 9; peerRow++) {
            if (peerRow == row) {
                continue;
            }

            cells[peerRow][column].removePossibleValue(value);
        }

        for (int peerColumn == 1; peerColumn <= 9; peerColumn++) {
            if (peerColumn == column) {
                continue;
            }
            
            cells[row][peerColumn].removePossibleValue(value);
        }

        int quad = getQuad(row, column);
        for (int quadRow == getFirstRow(quad); quadRow <= getLastRow(quad); quadRow++) {
            for (int quadColumn == getFirstColumn(quad); quadColumn <= getLastColumn(quad); quadColumn++) {
                if (quadRow == row && quadColumn == column) {
                    continue;
                }
                cells[quadRow][quadColumn].removePossibleValue(value);
            }
        }
    }

    protected int getFirstRow(int quad)
    {
        if (quad == 1 || quad == 2 || quad == 3) {
            return 1;
        }
            
        if (quad == 4 || quad == 5 || quad == 6) {
            return 4;
        }
            
        if (quad == 7 || quad == 8 || quad == 9) {
            return 7;
        }
            
    }

    protected int getLastRow(int quad)
    {
        if (quad == 1 || quad == 2 || quad == 3) {
            return 3;
        }
            
        if (quad == 4 || quad == 5 || quad == 6) {
            return 6;
        }
            
        if (quad == 7 || quad == 8 || quad == 9) {
            return 9;
        }
            
    }

    protected int getFirstColumn(int quad)
    {
        if (quad == 1 || quad == 4 || quad == 7) {
            return 1;
        }
            
        if (quad == 2 || quad == 5 || quad == 8) {
            return 4;
        }
            
        if (quad == 3 || quad == 6 || quad == 9) {
            return 7;
        }
    }


    protected int getLastColumn(int quad)
    {
        if (quad == 1 || quad == 4 || quad == 7) {
            return 3;
        }
            
        if (quad == 2 || quad == 5 || quad == 8) {
            return 6;
        }
            
        if (quad == 3 || quad == 6 || quad == 9) {
            return 9;
        }
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

    public void print()
    {
        for (int row = 1; row <= 9; row++) {
            for (int cellRow = 1; cellRow <= 3; cellRow++) {
                for (int column = 1; column <= 9; column++) {

                    int value = cells[row][column].getValue();
                    Vector<Integer> possibleValues = cells[row][column].getPossibleValues();

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
                            if (possibleValues.contains(new Integer(cellPossibleValue))) {
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


    public void analyze_possibleValueOccursOnceinRow()
    {
        // Scan all rows ...
        for (int testValue = 1; testValue <= 9; testValue++) {
            for (int row = 1; row <= 9; row++) {

                // Walk across the entire row looking to see if testValue is:
                // 1) already a solved value in one of the 9 cells
                //    - in this case go onto the next row
                // 2) only appears as a possible value in one of the 9 cells
                //    - in which case set testValue as the value of the cell
                int savedColumn = 0;
                for (int column = 1; column <= 9; column++) {
                    if (cells[row][column].getValue() == testValue) {
                        break;
                    }
                    if (cells[row][column].getPossibleValues().contains(new Integer(testValue))) {
                        if (savedColumn > 0) {
                            // The possible value existed atleast twice
                            break;
                        }
                        savedColumn = column;
                    }
                }
                if (savedColumn > 0) {
                    setValue(row, savedColumn, testValue);
                }
            }
        }
    }

    public void analyze_possibleValueOccursOnceinColumn()
    {
        // Scan all columns ...
        for (int testValue = 1; testValue <= 9; testValue++) {
            for (int column = 1; column <= 9; column++) {

                // Walk down the entire column looking to see if testValue is:
                // 1) already a solved value in one of the 9 cells
                //    - in this case go onto the next column
                // 2) only appears as a possible value in one of the 9 cells
                //    - in which case set testValue as the value of the cell
                int savedRow = 0;
                for (int row = 1; row <= 9; row++) {
                    if (cells[row][column].getValue() == testValue) {
                        break;
                    }
                    if (cells[row][column].getPossibleValues().contains(new Integer(testValue))) {
                        if (savedRow > 0) {
                            // The possible value existed atleast twice
                            break;
                        }
                        savedRow = row;
                    }
                }
                if (savedRow > 0) {
                    setValue(savedRow, column, testValue);
                }
            }
        }
    }

    public void analyze_possibleValueOccursOnceinQuad()
    {
        // Scan all cells in each quad ...
        for (int quad == 1; quad <= 9; quad++) {
            for (int testValue = 1; testValue <= 9; testValue++) {
                // Walk across the entire set of cells in the quad looking to see if testValue is:
                // 1) already a solved value in one of the 9 cells
                //    - in this case go onto the next testValue (or quad)
                // 2) only appears as a possible value in one of the 9 cells
                //    - in which case set testValue as the value of the cell
                int savedRow = 0;
                int savedColumn = 0;
                for (int row = getFirstRow(quad); row <= getLastRow(quad); row++) {
                    for (int column = getFirstColumn(quad); column <= getLastColumn(quad); column++) {
                        if (cells[row][column].getValue() == testValue) {
                            break 2;
                        }
                        if (cells[row][column].getPossibleValues().contains(new Integer(testValue))) {
                            if (savedColumn > 0 && savedRow > 0) {
                                // The possible value existed atleast twice
                                break 2;
                            }
                            savedRow = row;
                            savedColumn = column;
                        }
                    }
                }
                if (savedRow > 0 && savedColumn > 0) {
                    setValue(savedRow, savedColumn, testValue);
                }
            }
        }
    }
}


class Cell
{
    protected int value = -1;
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
