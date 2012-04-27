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
    
    private int numberOfSides = 4;
    
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
//------------------4 Sided---------------------------------------------
        String result = "";
        for(int deltaLat=-1;deltaLat<2;deltaLat++) {
            for(int deltaLong=-1;deltaLong<2;deltaLong++) {
                Position position = digger.getPosition();
                Square square = squares[position.getLatitude()+deltaLat][position.getLongitude()+deltaLong];
                square.viewed();
                result += square;
            }
            result += '\n';
        }
        
//-----------6 Sided---------------------------------------------------
        if(numberOfSides == 6){
        	result = getSixSidedView(digger, result);
        }
        return result;
    }

    private String getSixSidedView(Digger digger,String result) {
		boolean isEvenColumn = digger.getPosition().getLongitude() % 2 == 0;
		//Chops off the bottom left and right tiles as they are not seen by
		//6 sided view.
		if(isEvenColumn){
			result = result.substring(0,7) + result.substring(9,12);
		}
		//Chops off the top left and right tiles as they are not seen by
		//6 sided view.
		else {
			result = result.substring(1,2) + result.substring(3,12);
		}
		// format into the pattern:
//	      x   
//	   x     x
//	      c   
//	   x     x
//	      x   
//	where x are adjacent tiles and c is where the digger is at the moment
		result = result.replaceAll("\n", "");
		if (isEvenColumn) {
			result = "   " + result.charAt(1) + "   " + '\n'
					+ result.charAt(0)	+ "     " + result.charAt(2) + '\n'
					+ "   " + result.charAt(4) + "   " + '\n'
					+ result.charAt(3)	+ "     " + result.charAt(5) + '\n'
					+ "   " + result.charAt(6) + "   " + '\n';

		} else {
			result = "   " + result.charAt(0) + "   " + '\n' 
					+ result.charAt(1) + "     " + result.charAt(3) + '\n'
					+ "   " + result.charAt(2) + "   " + '\n'
					+ result.charAt(4) + "     " + result.charAt(6) + '\n'
					+ "   " + result.charAt(5) + "   " + '\n';
		}
		return result;
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
    
    public void setNumberOfSides(int numberOfSides){
    	this.numberOfSides = numberOfSides;
    }

}
