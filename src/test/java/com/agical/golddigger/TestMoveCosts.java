package com.agical.golddigger;

import static org.junit.Assert.*;

import org.junit.Test;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.GoldField;
import com.agical.golddigger.model.Position;
import com.agical.golddigger.model.fieldcreator.FieldCreator;
import com.agical.golddigger.model.fieldcreator.ResourceFieldCreator;

public class TestMoveCosts {

	@Test
	public void moveAround() {

 
		FieldCreator resourceFieldCreator = new ResourceFieldCreator(getClass());
		GoldField goldField = new GoldField(resourceFieldCreator);
        Digger digger = new Digger("Diggers name", "secretName");
        digger.newGame(goldField);
        long startTime = System.currentTimeMillis();
		digger.move(Position.EAST);
		assertEquals(startTime + 1000, System.currentTimeMillis(), 10);
		digger.move(Position.SOUTH);
		assertEquals(startTime + 3000, System.currentTimeMillis(), 10);
		digger.move(Position.WEST);
		assertEquals(startTime + 6000, System.currentTimeMillis(), 10);
		digger.move(Position.SOUTH);
		assertEquals(startTime + 10000, System.currentTimeMillis(), 10);
		digger.move(Position.EAST);
		assertEquals(startTime + 15000, System.currentTimeMillis(), 10);
	}

}
