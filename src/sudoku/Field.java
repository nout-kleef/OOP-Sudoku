package sudoku;

public class Field {
	private final static String IS_INITIAL_INDICATOR = "'";
	private final static String NOT_INITIAL_INDICATOR = " ";
	private int value;
	private final boolean initial;
	
	public Field() {
		this.initial = false;
		this.value = GameGrid.EMPTY_VAL;
	}
	
	public Field(int val, boolean init) {
		this.initial = init;
		this.value = val;
	}
	
	public String toString() {
		return initial ? 
				value + IS_INITIAL_INDICATOR : value + NOT_INITIAL_INDICATOR;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int val) {
		this.value = val;
	}

	public boolean isInitial() {
		return this.initial;
	}
}
