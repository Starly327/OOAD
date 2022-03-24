package uml_editor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
//¥Dµøµ¡(«ö¶s¡Bµe¥¬)
public class Canvas extends JLayeredPane implements MouseListener, MouseMotionListener{
	JLayeredPane canvas;
    Frame frame;
    
    ArrayList<Port[]> associationLine = new ArrayList<>();
    ArrayList<Port[]> generalizationLine = new ArrayList<>();
    ArrayList<Port[]> compositionLine = new ArrayList<>();
    ArrayList<Class> class_list = new ArrayList<>();
    ArrayList<Composite> composite_list = new ArrayList<>();
    ArrayList<Class> selectedClass = new ArrayList<>();
    ArrayList<Composite> selectedComposite = new ArrayList<>();
    Port start_port;
    Class end_class, start_class;
    Point start_point,end_point;
    
	public Canvas(Frame frame) {
		this.canvas = this;
		this.frame = frame;
        this.setOpaque(true);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBounds(250, 10, 680, 460);
        
        
    }

	public String getmode() {
		return frame.getmode();
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		 //Graphics2D g2d = (Graphics2D) g.create();
		g.setColor(Color.black);
		for(Port[] i : associationLine) {
            int x1 = i[0].getX() + i[0].c.getX();
            int y1 = i[0].getY() + i[0].c.getY();
            int x2 = i[1].getX() + i[1].c.getX();
            int y2 = i[1].getY() + i[1].c.getY();
            g.drawLine(x1, y1, x2, y2);
			}
		for(Port[] i : generalizationLine) {
			int x1 = i[0].getX() + i[0].c.getX();
	        int y1 = i[0].getY() + i[0].c.getY();
	        int x2 = i[1].getX() + i[1].c.getX();
	        int y2 = i[1].getY() + i[1].c.getY();
	        g.drawLine(x1, y1, x2, y2);
		}
		for(Port[] i : compositionLine) {
			int x1 = i[0].getX() + i[0].c.getX();
	        int y1 = i[0].getY() + i[0].c.getY();
	        int x2 = i[1].getX() + i[1].c.getX();
	        int y2 = i[1].getY() + i[1].c.getY();
	        g.drawLine(x1, y1, x2, y2);
		}
	}
	
	public void classRename() {
		
	}
	
	public void deComposite() {
		if(selectedComposite.size()==1) {
			Composite temp = selectedComposite.get(0);
			while(temp.findParent()!=null) temp = temp.findParent();
			for(Class c : temp.selectedClass) c.setParent(null);
			
			for(Composite com : temp.selectedComposite) com.setParent(null);
			this.remove(temp);
			selectedComposite.clear();
			repaint();
		}
	}
	
	public void addComposite() {
		if(selectedClass.size() + selectedComposite.size()>1) {
			Composite composite = new Composite(this, selectedClass, selectedComposite);
			this.add(composite);
			composite_list.add(composite);
			selectedComposite.clear();
			selectedClass.clear();
			repaint();
		}
	}
	
	public void selectedclear() {
		if(selectedClass!=null) selectedClass.clear();
		if(selectedComposite!=null) selectedComposite.clear();
	}
	

	public void addline(Port p1, Port p2) {
		Port[] temp = {p1, p2};
		if(getmode()=="Assosiationline") {
			associationLine.add(temp);
		}else if(getmode()=="Generalizationline") {
			generalizationLine.add(temp);
		}else if(getmode()=="Compositionline") {
			compositionLine.add(temp);
		}
		repaint();
	}
	
	//---------------------------------------------------------
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(getmode()=="Select") {
			start_point = null;
			end_point = null;
			for(Class c : class_list) {
				c.setvisible(false);
				selectedclear();
			}
		}else if(getmode()=="Class" || getmode()=="Use_case") {
			Class c = new Class(e.getX(), e.getY(), this);
			class_list.add(c);
			canvas.add(c);
		}
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(getmode()=="Select") {
			start_point = null;
			start_point = e.getPoint();
		}
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(getmode()=="Select") { 
			selectedclear();
			end_point = null;
			end_point = e.getPoint();
			int min_x = Math.min(start_point.x, end_point.x);
			int min_y = Math.min(start_point.y, end_point.y);
			int max_x = Math.max(start_point.x, end_point.x);
			int max_y = Math.max(start_point.y, end_point.y);
			for(Class c : class_list) {
				if(c.getX()>min_x && c.getX()<max_x && c.getY()>min_y && c.getY()<max_y &&
						c.getX()+90>min_x && c.getX()+90<max_x && c.getY()+100>min_y && c.getY()+100<max_y) {
					if(c.findParent()==null) {
						if(!selectedClass.contains(c)) 
							selectedClass.add(c);
					}else {
						Composite temp = c.findGrandparent();
						if(!selectedComposite.contains(temp)) selectedComposite.add(temp);
					}
					c.setvisible(true);
				}else
					c.setvisible(false);
			}
			
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
//---------------------------------------------------------
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
