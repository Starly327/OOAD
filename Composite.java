package uml_editor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import uml_editor.Class;

public class Composite extends JPanel implements MouseMotionListener, MouseListener{

	Canvas canvas;
	 ArrayList<Class> selectedClass;
	 ArrayList<Composite> selectedComposite;
	 Composite parent;
	
	public Composite(Canvas canvas, ArrayList<Class> selectedClass, ArrayList<Composite> selectedComposite) {
		this.selectedClass = new ArrayList<Class>(selectedClass);
		this.selectedComposite = new ArrayList<Composite>(selectedComposite);
		this.canvas = canvas;
		int min_x=9999,min_y=9999;
		int max_x=0,max_y=0;
		for(Class c : selectedClass) {
			if(c.getX()<min_x) min_x = c.getX();
			if(c.getY()<min_y) min_y = c.getY();
			if(c.getX()>max_x) max_x = c.getX();
			if(c.getY()>max_y) max_y = c.getY();
			c.setParent(this);
		}
		
		this.setLocation(min_x - 10, min_y - 10);
		this.setSize(max_x - min_x + 120, max_y - min_y + 130);
		for(Class c : selectedClass) c.parentDistance(min_x - 10, min_y - 10);
		
		
		setOpaque(true);
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
//-----------------------------------------------------
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(canvas.getmode()=="Select") {
			Point p = MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(p, canvas);
			this.setLocation(p);
			//canvas.moveToFront(this);
			for(Class c : selectedClass) {
				c.parentMove(p);
			}
			
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
