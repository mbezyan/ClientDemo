package com.agical.golddigger.gui;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.view.FieldPeek;


public class FieldView extends JPanel {
	private FieldPeek fieldPeek;

	public FieldView(final Digger digger) {
		fieldPeek = createFieldPeek(digger);
		addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent e) {
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				
			}

			@Override
			public void componentResized(ComponentEvent e) {
				fieldPeek = createFieldPeek(digger);
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				
			}});
	}

	private FieldPeek createFieldPeek(Digger digger) {
		return new FieldPeek(digger, Math.round(getWidth()/(32F)), Math.round(getHeight()/32F));
	}
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void paint(Graphics g) {
		//Changs the tile set depending on the numberOfSides for the tiles
		//Might need a more efficient algorithm to do this if loading becomes too slow
		//i.e only updates the tile set once/on changes.
			if(fieldPeek.getSidesOfTiles() == 4){				
				GraphicsPeekView.changeTileSetBasedOnSides(4);
			}
			else if(fieldPeek.getSidesOfTiles() == 6){
				GraphicsPeekView.changeTileSetBasedOnSides(6);
			}
			super.paint(g);
			fieldPeek.getPeek().drawTo(new GraphicsPeekView(g, this,fieldPeek.getSidesOfTiles()));
		
	}

   
}
