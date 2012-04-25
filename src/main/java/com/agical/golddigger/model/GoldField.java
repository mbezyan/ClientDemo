package com.agical.golddigger.model;

import com.agical.golddigger.model.event.GolddiggerNotifier;
import com.agical.golddigger.model.fieldcreator.EmptyFieldCreator;
import com.agical.golddigger.model.fieldcreator.FieldCreator;
import com.agical.jambda.Option;
import com.agical.jambda.Functions.Fn1;



public class GoldField {
    private Square[][] squares;

    private GolddiggerNotifier golddiggerNotifier;

    private int maxLatitude;

    private int maxLongitude;
    
    public void setGolddiggerNotifier(GolddiggerNotifier golddiggerNotifier) {
        this.golddiggerNotifier = golddiggerNotifier;
    }

    public GoldField(int maxLatitude, int maxLongitude) {
        this(new EmptyFieldCreator(maxLatitude, maxLongitude));
    }
    @Override
    public String toString() {
        return Square.getField(squares);
    }
    public GoldField(FieldCreator fieldCreator) {
        squares = fieldCreator.createField();
        maxLatitude = fieldCreator.getMaxLatitude();
        maxLongitude = fieldCreator.getMaxLongitude();
    }

    public int getMaxLatitude() {
        return maxLatitude;
    }

    public int getMaxLongitude() {
        return maxLongitude;
    }

    public String getDiggerView(Digger digger) {
        String result = "";
        
        Integer length = 1;
        
        result = constructDiggerView(digger, length);
        
        return result;
    }
    
    // **MMB
    // a new function that returns the view as a String called by getDiggerView
    // the length of the line-of-sight can be passed to this function
    // along with the digger object
    
    public String constructDiggerView(Digger digger, Integer length) {
    	
    	// create an empty array x with the same dimensions as the squares array
    	// make the cell where the digger is located "true" in array x
    	
    	// while (k < line_of_sight_length){
    			
    			// go through all the cells in array x
    			// for (int i = -(m); i < (m + 1); i++){
    			//		for (int j = -(m); j < (m + 1); j++){
    			//			if (x[diggerLat + i][diggerLong + j] == true){
    			//				Position position = new Position(i, j);	
    			// 				call adjacency function, give it array x
    			
    			// increment k
    			// change the original cells that was "true" to "checked"
    	
    	// }
    	
    	// go through array x 
    	// print every cell in squares where the corresponding cell in x is true or checked
    	
    	
    	String view = "";
    	
    	// for all the different types of tiles, the digger should ideally 
    	// be in the centre. This cannot happen if the digger is close to a
    	// wall on one side
    	
    	// construct view for square tiles
    	for(int deltaLat=length*(-1);deltaLat<length+1;deltaLat++) {
    		for(int deltaLong=length*(-1);deltaLong<length+1;deltaLong++) {	
    			Position position = digger.getPosition();
    			Square square = squares[position.getLatitude()+deltaLat][position.getLongitude()+deltaLong];
    			square.viewed();
    			view += square;
    		}
    	}
    		view += '\n';
    	    	
    	
    	// construct view for hexagon tiles
    	
    		int arraySizeLength = squares.length;
        	int arraySizeWidth = squares[0].length;
    		String[][] visibleTiles;
        	visibleTiles = new String[arraySizeLength][arraySizeWidth];
    		
    		
    		
    	
    	// construct view for triangle tiles
    	    	
    	
    	return view;
    	
    }
    
    public String getField(Digger digger) {
        String result = "";
        for(int lat=1;lat<=getMaxLatitude();lat++) {
            for(int lon=1;lon<=getMaxLongitude();lon++) {
                Position position = digger.getPosition();
                result += squares[lat][lon];
            }
            result += "\n";
        }
        return result;
    }
    public String fieldAt(int lat, int lon) {
        return squares[lat][lon]+"";
    }
    private Square getSquare(int latitude, int longitude) {
        return squares[latitude][longitude];
    }

    public Square getSquare(Position newPosition) {
        return getSquare(newPosition.getLatitude(), newPosition.getLongitude());
    }

    public boolean isTreadable(Position position) {
        int longitude = position.getLongitude();
        int latitude = position.getLatitude();
        return latitude>=1&&longitude>=1&&latitude<=getMaxLatitude()&&longitude<=getMaxLongitude()&&getSquare(position).isTreadable();
    }
    
    public Square[][] getSquares() {
		return squares;
	}

    public boolean hasGold() {
        for(int lat=1;lat<=getMaxLatitude();lat++) {
            for(int lon=1;lon<=getMaxLongitude();lon++) {
                if(!squares[lat][lon].isEmpty()) return true;
            }
        }
        return false;
    }
    
    //Calculates view of surrounding adjacent hexagon tiles of length 1
    public String [][] calculateAdjacency(Position position, String[][] sightedArray){
    	int deltaLat;
    	int deltaLong;

    	for(deltaLat = -1; deltaLat <= 1; deltaLat++) {
        	for(deltaLong = -1; deltaLong <= 1; deltaLong++) {
        		if (!(deltaLat == -1 && deltaLong == -1) || !(deltaLat == -1 && deltaLong == 1)){
        			sightedArray[position.getLatitude() + deltaLat][position.getLongitude() + deltaLong] = "True";
        		}
        	}
    	}
    }

}
