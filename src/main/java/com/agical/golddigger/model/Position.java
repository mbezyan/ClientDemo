/**
 * 
 */
package com.agical.golddigger.model;

import com.agical.jambda.Functions;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;

public class Position {
	private static final int[] _SOUTH = new int[] { 1, 0, 1, 0 };
	private static final int[] _NORTH = new int[] { -1, 0, -1, 0 }; // [lat,long]
	private static final int[] _EAST = new int[] { 0, 1, 0, 1 };
	private static final int[] _WEST = new int[] { 0, -1, 0, -1 };
	
	private static final int[] _NORTH_EAST = new int[] { 0, 1, -1, 1 }; // ODD[lat,long],EVEN[lat,long]
	private static final int[] _SOUTH_EAST = new int[] { 1, 1, 0, 1 };
	private static final int[] _NORTH_WEST = new int[] { 0, -1, -1, -1 };
	private static final int[] _SOUTH_WEST = new int[] { 1, -1, 0, -1 };

    public static Functions.Fn2<Position,GoldField, Square> square = new Fn2<Position,GoldField, Square>() {
        public Square apply(Position position, GoldField goldField) {
            return goldField.getSquare(position);
        }
    };
    private static Functions.Fn2<Position,int[],Position> move = new Fn2<Position, int[], Position>() {
    	public Position apply(Position position, int[] internalMove) {
			if (isEven(position.longitude)) {
				return new Position(position.latitude + internalMove[2],
						position.longitude + internalMove[3]);

			} else {
				return new Position(position.latitude + internalMove[0],
						position.longitude + internalMove[1]);
			}

		}
    };
    public static final Fn1<Position,Position> NORTH = move.rightCurry(Position._NORTH);
    public static final Fn1<Position,Position> EAST = move.rightCurry(Position._EAST);
    public static final Fn1<Position,Position> SOUTH = move.rightCurry(Position._SOUTH);
    public static final Fn1<Position,Position> WEST  = move.rightCurry(Position._WEST);
    
    public static final Fn1<Position, Position> NORTH_EAST = move.rightCurry(Position._NORTH_EAST);
	public static final Fn1<Position, Position> SOUTH_EAST = move.rightCurry(Position._SOUTH_EAST);
	public static final Fn1<Position, Position> NORTH_WEST = move.rightCurry(Position._NORTH_WEST);
	public static final Fn1<Position, Position> SOUTH_WEST = move.rightCurry(Position._SOUTH_WEST);
    
    private final int latitude;
    private final int longitude;
    
    public Position(int latitude, int longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String toString() {
        return "Position[lat=" + latitude + ", long=" + longitude + "]";
    }
    
    protected static boolean isEven(int n) {
		if (n % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}
    
    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Position ) {
            Position position = (Position) obj;
            return position.latitude==latitude && position.longitude==longitude;
        }
        return false;
    }
    
    
}