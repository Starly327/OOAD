package uml_editor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
//Class物件
public class Class extends JPanel implements MouseMotionListener, MouseListener{

	Point corner;
	Canvas canvas;
	Port[] port;
	Boolean select = false;
	Composite parent;
	Point parentDistance;
	Point movePoint;
	JLabel title;
	String mode;
	
	public Class(int x, int y, Canvas canvas) {
		this.canvas = canvas;
		corner = new Point(x,y);
		parentDistance = new Point();
		title = new JLabel();
		
		this.setOpaque(true);
        this.setBackground(Color.white);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setLayout(null);
        
        mode = new String();
        mode = canvas.getmode();
        port = new Port[4];
        for(int i=0; i<port.length; i++) {
        	port[i] = new Port(this);
        	this.add(port[i]);
        }
        if(mode=="Class") {
        	this.setBounds(corner.x, corner.y, 100, 110);
        	port[0].setLocation(45,0);//上
	    	port[1].setLocation(45,100);//下
	    	port[2].setLocation(0,50);//左
	    	port[3].setLocation(90,50);//右
        }
        if(mode=="Use_case") {
        	this.setBounds(corner.x, corner.y, 140, 100);
        	//setBorder(BorderFactory.createLineBorder(Color.BLACK));
        	port[0].setLocation(65,0);//上
	    	port[1].setLocation(65,90);//下
	    	port[2].setLocation(0,45);//左
	    	port[3].setLocation(130,45);//右
        }
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(mode=="Class") {
			g.drawLine(10, 10, 90, 10);
			g.drawLine(10, 10, 10, 100);
			g.drawLine(10, 100, 90, 100);
			g.drawLine(90, 10, 90, 100);
			g.drawLine(10, 40, 90, 40);
			g.drawLine(10, 70, 90, 70);
		}else if(mode=="Use_case") {
			g.drawOval(10, 10, 120, 80);
	    }
	}
	
	public void setTitle(String newtitle) {//物件命名
        title.setText(newtitle);
        title.setFont(new Font("MV Boli", Font.BOLD, 15));
        title.setForeground(Color.BLACK);
        title.setLocation(20, 0);
        title.setSize(100, 50);
        this.add(title);
        repaint();
	}
	
	public Port check_distance(){//計算line與port的距離
		Point p = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(p, this);
		//HashMap<Port, Double> map = new HashMap<>();
		double temp=9999;
		Port temp_port = new Port();
		for(Port port : port) {
			double distance = Math.sqrt(Math.pow(p.x-port.getX(),2) +
						Math.pow(p.y-port.getY(), 2));
			if(distance<temp) {
				temp = distance;
				temp_port = port;
			}
		}
		return temp_port;
	}
	
	public void setvisible(Boolean b) {//port隱藏與否
		this.select = b;
		for(int i=0; i<port.length; i++) {
			port[i].setVisible(b);
		}
	}
	
	public void setParent(Composite parent) {
		this.parent = parent;
	}
	
	public Composite findGrandparent() {//找最大的composite
		Composite temp = parent;
		while(temp.parent!=null) temp = temp.parent;
		return temp;
	}
	
	public void parentMove(Point p) {
		this.setLocation(p.x + parentDistance.x, p.y + parentDistance.y);
		canvas.moveToFront(this);
		canvas.repaint();
	}
	
	public void parentDistance(int x, int y) {//class與parent的距離
		parentDistance.x = this.getX() - x;
		parentDistance.y = this.getY()- y;
	}
	
	//-----------------------------------------------------
	@Override
	public void mouseDragged(MouseEvent e) {
		
		if(canvas.getmode()=="Select") {
			if(parent!=null) {
				parent.mouseDragged(e);
			}else {
				Point p = MouseInfo.getPointerInfo().getLocation();
				SwingUtilities.convertPointFromScreen(p, canvas);
				this.setLocation((int) (p.getX() - movePoint.getX()),
	                    (int) (p.getY() - movePoint.getY()));
				
			}
			canvas.moveToFront(this);
			canvas.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		
	}
	//-----------------------------------------------
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(canvas.getmode()=="Select") {
			canvas.selectedRenameClass = this;
			if(parent!=null) {
				canvas.selectedComposite.clear();
				canvas.selectedComposite.add(parent);
				Composite temp = findGrandparent();
				temp.movePoint = MouseInfo.getPointerInfo().getLocation();
	            SwingUtilities.convertPointFromScreen(temp.movePoint, temp);
			}else {
				canvas.selectedComposite.clear();
				canvas.selectedClass.clear();
				canvas.selectedClass.add(this);
				
			}
			movePoint = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(movePoint, this);
			ArrayList<Class> class_list = canvas.class_list;
			for(Class c : class_list) c.setvisible(false);
			this.setvisible(true);
			
		}else if(canvas.getmode()=="Assosiationline" || canvas.getmode()=="Generalizationline" ||
				canvas.getmode()=="Compositionline") {
			canvas.start_port = check_distance();
			canvas.start_class = this;
		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(canvas.getmode()=="Assosiationline" || canvas.getmode()=="Generalizationline" ||
				canvas.getmode()=="Compositionline")
			if(canvas.start_port!=null && canvas.end_class!=null && canvas.start_class!=canvas.end_class) {
				
				Port end_port = canvas.end_class.check_distance();
				canvas.addline(canvas.start_port, end_port);
				canvas.start_port = null;
			}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(canvas.getmode()=="Select") {
			canvas.selectedRenameClass = this;
			if(parent!=null) {
				canvas.selectedComposite.clear();
				canvas.selectedComposite.add(this.parent);
			}else {
				canvas.selectedComposite.clear();
				canvas.selectedClass.clear();
				canvas.selectedClass.add(this);	
			}
		}
		if(canvas.getmode()=="Class" || canvas.getmode()=="Use_case") {
			Point p = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(p, canvas);
			Class c = new Class(p.x, p.y, canvas);
			canvas.class_list.add(c);
			canvas.add(c,0);
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(canvas.getmode()=="Assosiationline" || canvas.getmode()=="Generalizationline" ||
				canvas.getmode()=="Compositionline") {
			//setvisible(true);
			if(canvas.start_port!=null){
				canvas.end_class = this;
			}
		}
	}



	@Override
	public void mouseExited(MouseEvent e) {
		if(canvas.getmode()=="Assosiationline" || canvas.getmode()=="Generalizationline" ||
				canvas.getmode()=="Compositionline") {
			canvas.end_class = null;
			//setvisible(false);
		}
		
	}
	
}
