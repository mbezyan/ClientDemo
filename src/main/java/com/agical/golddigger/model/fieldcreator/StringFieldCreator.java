package com.agical.golddigger.model.fieldcreator;

import com.agical.golddigger.model.Square;

public class StringFieldCreator extends FieldCreator {
    
    private static final int WALLS = 2;
    private final String result;
    private Square[][] squares;

    public StringFieldCreator(String result) {
        this.result = result;
    }

    public Square[][] createField() {
        return getSquares();
    }

    private Square[][] getSquares() {
        if(squares!=null) return squares;
        //
        String[] rows = (result.split("!")) [0].split("\n");
        //The travel costs grid after an !\n in the .field files
        String[] costRows = (result.split("!\n")) [1].split("\n");
        
        squares = new Square[rows.length][];
        for (int rowCount = 0; rowCount<rows.length; rowCount++) {
            String charRow = rows[rowCount];
            String charCostRow = costRows[rowCount];
            
            Square[] squareRow = new Square[charRow.length()];
            squares[rowCount] = squareRow;
            for (int i = 0; i < charRow.length(); i++) {
                char squareChar = charRow.charAt(i);
                squareRow[i] = Square.createFromChar(squareChar);
                
                char costChar = charCostRow.charAt(i);
                if (costChar !='w'){
                	int costInt = Character.getNumericValue(costChar);
                	squareRow[i].setCost(costInt);
                }
            }
        }
        return squares;
    }
    
    public int getMaxLatitude() {
        return getSquares().length-WALLS;
    }
    
    public int getMaxLongitude() {
        return getSquares()[0].length-WALLS;
    }
    
}
