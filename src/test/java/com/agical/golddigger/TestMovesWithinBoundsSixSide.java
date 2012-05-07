package com.agical.golddigger;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.GoldField;
import com.agical.golddigger.model.Position;
import com.agical.jambda.Functions.Fn1;


public class TestMovesWithinBoundsSixSide {

    private GoldField goldField;
    private Digger digger;

    @Before
    public void initateGolddigger() throws Exception {
        goldField = new GoldField(4,4,6);
        digger = new Digger("Diggers name", "secretName");
        digger.newGame(goldField);
        digger.move(Position.SOUTH);
        digger.move(Position.NORTH_EAST);
        digger.move(Position.SOUTH_EAST);
        assertEquals( "   .   \n.     .\n   .   \n.     .\n   .   \n", digger.getView());
    // grid looks like:
    //   w      w           
    //w     .      .
    //   b      . 
    //w     .      .     
    //   .      .
    //    
    }
    
    @Test
    public void canMoveInAllDirections() throws Exception {
        moveAndAssert(Position.NORTH, "   w   \n.     .\n   .   \n.     .\n   .   \n");
        moveAndAssert(Position.SOUTH_EAST, "   .   \n.     w\n   .   \n.     w\n   .   \n");
        moveAndAssert(Position.SOUTH, "   .   \n.     w\n   .   \n.     w\n   .   \n");
        moveAndAssert(Position.SOUTH, "   .   \n.     w\n   .   \n.     w\n   w   \n");
        moveAndAssert(Position.SOUTH_WEST, "   .   \n.     .\n   .   \nw     w\n   w   \n");
        moveAndAssert(Position.NORTH_WEST, "   .   \n.     .\n   .   \n.     .\n   w   \n");
        moveAndAssert(Position.NORTH_EAST, "   .   \n.     .\n   .   \n.     .\n   .   \n");
    }

    @Test
    public void wontMoveOutsideNorthBounds() throws Exception {
        moveAndAssert(Position.NORTH, "   w   \n.     .\n   .   \n.     .\n   .   \n");
        moveAndAssert(Position.NORTH, "   w   \n.     .\n   .   \n.     .\n   .   \n");
    }
    @Test
    public void wontMoveOutsideSouthBounds() throws Exception {
        moveAndAssert(Position.SOUTH, "   .   \n.     .\n   .   \n.     .\n   .   \n");
        moveAndAssert(Position.SOUTH, "   .   \n.     .\n   .   \nw     w\n   w   \n");
        moveAndAssert(Position.SOUTH, "   .   \n.     .\n   .   \nw     w\n   w   \n");
    }
    @Test
    public void wontMoveOutsideNorthEastBounds() throws Exception {
        moveAndAssert(Position.NORTH_EAST, "   .   \n.     w\n   .   \n.     w\n   .   \n");
        moveAndAssert(Position.NORTH_EAST, "   .   \n.     w\n   .   \n.     w\n   .   \n");
    }
    @Test
    public void wontMoveOutsideSouthWestBounds() throws Exception {
        moveAndAssert(Position.SOUTH_WEST, "   .   \n.     .\n   .   \n.     .\n   .   \n");
        moveAndAssert(Position.SOUTH_WEST, "   .   \nw     .\n   .   \nw     .\n   .   \n");
        moveAndAssert(Position.SOUTH_WEST, "   .   \nw     .\n   .   \nw     .\n   .   \n");
    }
    @Test
    public void wontMoveOutsideSouthEastBounds() throws Exception {
        moveAndAssert(Position.SOUTH_EAST, "   .   \n.     w\n   .   \n.     w\n   .   \n");
        moveAndAssert(Position.SOUTH_EAST, "   .   \n.     w\n   .   \n.     w\n   .   \n");
    }
    @Test
    public void wontMoveOutsideNorthWestBounds() throws Exception {
        moveAndAssert(Position.NORTH_WEST, "   .   \nb     .\n   .   \n.     .\n   .   \n");
        moveAndAssert(Position.NORTH_WEST, "   w   \nw     .\n   b   \nw     .\n   .   \n");
        moveAndAssert(Position.NORTH_WEST, "   w   \nw     .\n   b   \nw     .\n   .   \n");
    }
    @Test(expected=RuntimeException.class)
    public void wontMoveOutside() throws Exception {
        moveAndAssert(Position.SOUTH_WEST, "   .   \n.     .\n   .   \n.     .\n   .   \n");
        moveAndAssert(Position.SOUTH_WEST, "   .   \nw     .\n   .   \nw     .\n   .   \n");
        moveAndAssert(Position.SOUTH, "   .   \nw     .\n   .   \nw     w\n   w   \n");
        moveAndAssert(Position.SOUTH, "   .   \nw     .\n   .   \nw     w\n   w   \n");
        digger.setPosition(new Position(5,1));
    }
    
    @Test
    public void fullField() throws Exception {
    	
        assertEquals( "b...\n....\n....\n....\n", goldField.getField(digger));
    }
    
    private void moveAndAssert(Fn1<Position,Position> move, String expected) {
        digger.move(move);
        assertEquals( expected, digger.getView());
    }
    
}
