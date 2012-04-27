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
    
    // MMB
    // Tile type enum
    private enum TileTypes {Square, Hexagon, Triangle}
    
    private TileTypes TileType = TileTypes.Hexagon; 
    
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
        
        Integer line_of_sight_length = 1;
        
        result = constructDiggerView(digger, line_of_sight_length);
        
        return result;
    }
    
    // **MMB
    // a new function that returns the view as a String called by getDiggerView
    // the length of the line-of-sight can be passed to this function
    // along with the digger object
    
    public String constructDiggerView(Digger digger, Integer length) {
    	
    
    	
    	
    	String view = "";
    	
    	
    	
		int arraySizeLength = squares.length;
    	int arraySizeWidth = squares[0].length;
		String[][] visibleTiles;
		
    	visibleTiles = new String[arraySizeLength][arraySizeWidth];
    	Position position = digger.getPosition();
    	int digger_lat = position.getLatitude();
    	int digger_long = position.getLongitude();
    	visibleTiles[position.getLatitude()][position.getLongitude()] = "True";
    	
    	int k = 0;
    	while (k < length){
    		for (int i = -(k); i < (k + 1); i++){
    			for (int j = -(k); j < (k + 1); j++){
    				
    				position = new Position(digger_lat + i, digger_long + j);
    				
    				if ((position.getLatitude() >= 0) && (position.getLatitude() < visibleTiles.length)) {
            			
           			 	if (position.getLongitude() >= 0 && (position.getLongitude() < visibleTiles[0].length)) {
	        				if (visibleTiles[position.getLatitude()][position.getLongitude()] == "True"){
	        					

	        					markAdjacentTiles(position, visibleTiles);

	        					visibleTiles[position.getLatitude()][position.getLongitude()] = "Checked";
	        					
	        				}
           			 	}
    				}
    			}
    			
    		}
    		k++;
    	}
    	
    	for (int i = 0; i < visibleTiles.length; i++){
    		for (int j = 0; j < visibleTiles[0].length; j++){
    			
    			if (visibleTiles[i][j] == "True" || visibleTiles[i][j] == "Checked"){
    				Square square = squares[i][j];
        			square.viewed();
        			view += square;
    			} else if (visibleTiles[i][j] == "Blank") {
    				
        			view += " ";
    			}
    		}
    		view += '\n';
    	}
    		
    	
    	
    	    	
    	
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
    public void markAdjacentTiles(Position position, String[][] sightedArray){
    	int deltaLat;
    	int deltaLong;

    	for(deltaLat = -1; deltaLat <= 1; deltaLat++) {
        	for(deltaLong = -1; deltaLong <= 1; deltaLong++) {
        		if (position.getLatitude() + deltaLat >= 0 && position.getLatitude() + deltaLat < sightedArray.length) {
        			
        			 if (position.getLongitude() + deltaLong >= 0 && position.getLongitude() + deltaLong < sightedArray[0].length) {
        		
		        		if (TileType == TileTypes.Hexagon) {
		        			if (!(deltaLat == -1 && deltaLong == -1) && !(deltaLat == -1 && deltaLong == 1)){
		        				sightedArray[position.getLatitude() + deltaLat][position.getLongitude() + deltaLong] = "True";
		        			} else {
		        				sightedArray[position.getLatitude() + deltaLat][position.getLongitude() + deltaLong] = "Blank";
		        				
		        			}
		        		} else if (TileType == TileTypes.Triangle) {
		        			if ((position.getLongitude() % 2) == 1) {
		        				if ((!(deltaLat == 1 && deltaLong == 1)) || (!(deltaLat == 1 && deltaLong == -1))) {
		        					sightedArray[position.getLatitude() + deltaLat][position.getLongitude() + deltaLong] = "True";
		        				}
		        			} else if ((position.getLongitude() % 2) == 0) {
		        				if ((!(deltaLat == -1 && deltaLong == -1)) || (!(deltaLat == -1 && deltaLong == 1))) {
		        					sightedArray[position.getLatitude() + deltaLat][position.getLongitude() + deltaLong] = "True";
		        				}
		        				
		        			}
		        			
		        			
		        		} else {

		        			
		        			sightedArray[position.getLatitude() + deltaLat][position.getLongitude() + deltaLong] = "True";

		        			
		        		}
        			 }
        			 
        		}
        		
        			
        	}
    	}
    }

}
