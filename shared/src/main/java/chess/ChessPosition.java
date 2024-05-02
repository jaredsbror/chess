package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private int row;
    private int column;

    public ChessPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
    }

    /** Set the column */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return column;
    }

    /** Set the column */
    public void setColumn(int column) {
        this.column = column;
    }

    //
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    //
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    //
    @Override
    public String toString() {
        return super.toString();
    }
}
