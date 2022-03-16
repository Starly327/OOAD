package uml_editor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
//Class����
public class Class extends JPanel implements MouseMotionListener, MouseListener{

	Point corner;
	Canvas canvas;
	Port[] port = new Port[4];
	Boolean select = false;
	Composite parent;
	Point parentDistance = new Point();
	
	public Class(int x, int y, Canvas canvas) {
		corner = new Point(x,y);
		this.setOpaque(true);
		this.setBounds(corner.x, corner.y, 100, 110);
        this.setBackground(Color.white);
        //this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        //this.setLayout(new BorderLayout());
        this.canvas = canvas;
        this.setLayout(null);
        
        JLabel label = new JLabel();
        label.setText("hello");
        label.setFont(new Font("MV Boli", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        label.setLocation(0, 0);
        this.add(label);
        
     
        for(int i=0; i<port.length; i++) {
        	port[i] = new Port(this);
        	this.add(port[i]);
        }
        port[0].setLocation(45,0);//�W
        port[1].setLocation(45,100);//�U
        port[2].setLocation(0,50);//��
        port[3].setLocation(90,50);//�k
        
        validate();
        
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawLine(10, 10, 90, 10);
		g.drawLine(10, 10, 10, 100);
		g.drawLine(10, 100, 90, 100);
		g.drawLine(90, 10, 90, 100);
		g.drawLine(10, 40, 90, 40);
		g.drawLine(10, 70, 90, 70);
		}
	
	public Port check_distance(){//�p��line�Pport���Z��
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
	
	public void setvisible(Boolean b) {
		this.select = b;
		for(int i=0; i<port.length; i++) {
			port[i].setVisible(b);
		}
	}
	
	public Boolean ifselect() {
		return select;
	}
	
	public void setParent(Composite parent) {
		this.parent = parent;
	}
	
	public void parentMove(Point p) {
		this.setLocation(p.x + parentDistance.x, p.y + parentDistance.y);
		canvas.moveToFront(this);
		canvas.repaint();
	}
	
	public void parentDistance(int x, int y) {//class�Pparent���Z��
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
				this.setLocation(p);
				
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
			
			ArrayList<Class> class_list = canvas.getarray();
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
		// TODO Auto-generated method stub
		
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(canvas.getmode()=="Assosiationline" || canvas.getmode()=="Generalizationline" ||
				canvas.getmode()=="Compositionline") {
			setvisible(true);
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
			setvisible(false);
		}
		
	}
	
}