package uml_editor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.util.*;
//主視窗(按鈕、畫布)
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
    Class selectedRenameClass=null;
    
	public Canvas(Frame frame) {
		this.canvas = this;
		this.frame = frame;
        this.setOpaque(true);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBounds(250, 10, 680, 460); 
    }

	public String getmode() {
		return frame.mode;
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		 Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		for(Port[] i : associationLine) {
            int x1 = i[0].getX() + i[0].c.getX();
            int y1 = i[0].getY() + i[0].c.getY();
            int x2 = i[1].getX() + i[1].c.getX();
            int y2 = i[1].getY() + i[1].c.getY();
            drawAL(x1, y1, x2, y2, g2d, "associationLine");
			}
		for(Port[] i : generalizationLine) {
			int x1 = i[0].getX() + i[0].c.getX();
	        int y1 = i[0].getY() + i[0].c.getY();
	        int x2 = i[1].getX() + i[1].c.getX();
	        int y2 = i[1].getY() + i[1].c.getY();
	        drawAL(x1, y1, x2, y2, g2d, "generalizationLine");
		}
		for(Port[] i : compositionLine) {
			int x1 = i[0].getX() + i[0].c.getX();
	        int y1 = i[0].getY() + i[0].c.getY();
	        int x2 = i[1].getX() + i[1].c.getX();
	        int y2 = i[1].getY() + i[1].c.getY();
	        drawAL(x1, y1, x2, y2, g2d, "compositionLine");
		}
	}
	
	public void deComposite() {
		if(selectedComposite.size()==1) {
			Composite temp = selectedComposite.get(0).findGrandparent().findGrandparent();
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
	
	public void selectedclear() {//清除選中的所有物件
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
	
	public static void drawAL(int sx, int sy, int ex, int ey,
            Graphics2D g2, String type) {
        double H;
        double L;
        if (type == "compositionLine") {
            H = 10;
            L = 6;
        } else {
            H = 12;
            L = 8;
        }
        double awrad = Math.atan(L / H);
        double arraow_len = Math.sqrt(L * L + H * H);
        double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
        double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
        double x_3 = ex - arrXY_1[0];
        double y_3 = ey - arrXY_1[1];
        double x_4 = ex - arrXY_2[0];
        double y_4 = ey - arrXY_2[1];
        if (type == "associationLine") {
            g2.drawLine((int) sx, (int) sy, (int) ex, (int) ey);
            g2.drawLine((int) ex, (int) ey, (int) x_3, (int) y_3);
            g2.drawLine((int) ex, (int) ey, (int) x_4, (int) y_4);
        } else if (type == "generalizationLine") {
            GeneralPath triangle = new GeneralPath();
            triangle.moveTo(ex, ey);
            triangle.lineTo(x_3, y_3);
            triangle.lineTo(x_4, y_4);
            triangle.closePath();
            g2.draw(triangle);
            g2.drawLine((int) sx, (int) sy, (int) (x_3 + (x_4 - x_3) / 2), (int) (y_3 + (y_4 - y_3) / 2));
        } else {
            double x_5 = x_3 + (x_4 - x_3) / 2;
            double y_5 = y_3 + (y_4 - y_3) / 2;
            double x_6 = ex - (ex - x_5) * 2;
            double y_6 = ey - (ey - y_5) * 2;
            g2.drawLine((int) sx, (int) sy, (int) x_6, (int) y_6);
            g2.drawLine((int) ex, (int) ey, (int) x_3, (int) y_3);
            g2.drawLine((int) ex, (int) ey, (int) x_4, (int) y_4);
            g2.drawLine((int) x_6, (int) y_6, (int) x_3, (int) y_3);
            g2.drawLine((int) x_6, (int) y_6, (int) x_4, (int) y_4);
        }
    }

	public static double[] rotateVec(double e, double f, double ang,
            boolean isChLen, double newLen) {
        double mathstr[] = new double[2];
        double vx = e * Math.cos(ang) - f * Math.sin(ang);
        double vy = e * Math.sin(ang) + f * Math.cos(ang);
        if (isChLen) {
            double d = Math.sqrt(vx * vx + vy * vy);
            vx = vx / d * newLen;
            vy = vy / d * newLen;
            mathstr[0] = vx;
            mathstr[1] = vy;
        }
        return mathstr;
    }
	
	//---------------------------------------------------------
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(getmode()=="Select") {
			start_point = null;
			end_point = null;
			selectedRenameClass = null;
			for(Class c : class_list) {
				c.setvisible(false);
				selectedclear();
			}
		}else if(getmode()=="Class" || getmode()=="Use_case") {
			Class c = new Class(e.getX(), e.getY(), this);
			class_list.add(c);
			canvas.add(c,0);
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
			selectedRenameClass = null;
			end_point = null;
			end_point = e.getPoint();
			int min_x = Math.min(start_point.x, end_point.x);
			int min_y = Math.min(start_point.y, end_point.y);
			int max_x = Math.max(start_point.x, end_point.x);
			int max_y = Math.max(start_point.y, end_point.y);
			for(Class c : class_list) {
				if(c.getX()>min_x && c.getX()<max_x && c.getY()>min_y && c.getY()<max_y &&
						c.getX()+90>min_x && c.getX()+90<max_x && c.getY()+100>min_y && c.getY()+100<max_y) {//物件在選取的區域範圍當中
					if(c.parent==null) {//物件為非composite
						if(!selectedClass.contains(c)) 
							selectedClass.add(c);
					}else {//物件為composite
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
