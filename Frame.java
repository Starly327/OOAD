package uml_editor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Frame extends JFrame implements ActionListener, MouseListener, MouseMotionListener{
	String st[] = {"Select","Associationline","Generalizationline","Compositionline","Class","Usecase"};
    JToggleButton[] btn = new JToggleButton[st.length];
    NoneSelectedButtonGroup group = new NoneSelectedButtonGroup();
    Canvas canvas;
    String mode;
    String ifgroup;
    JMenuItem groupItem;
    JMenuItem ungroupItem;
    JMenuItem changeName;
    
	public Frame() {
		setTitle("UML_editor");
        // 獲取螢幕解析度
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        // 設定視窗大小佔螢幕四分之一
        setSize(dimension.width / 2, dimension.height / 2);
        //設定視窗顯示在螢幕畫面中間位置
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//設定關閉可以關掉程式
        ImageIcon image = new ImageIcon("logo.jpg");
        setIconImage(image.getImage());
        setLayout(null);
        
        for(int i = 0; i < st.length; i++) {
            btn[i] = new JToggleButton(st[i]);
            btn[i].setFont(new Font("MV Boli", Font.BOLD, 20));
            group.add(btn[i]);
            btn[i].setBounds(25, 100+i*50, 220, 50);
            btn[i].addActionListener(this);
            add(btn[i]);
          }
          canvas = new Canvas(this);
          add(canvas);
          JMenuBar menuBar = new JMenuBar();
          
          JMenu fileMenu = new JMenu("File");
          JMenu editMenu = new JMenu("Edit");
          menuBar.add(fileMenu);
          menuBar.add(editMenu);
          setJMenuBar(menuBar);
          groupItem = new JMenuItem("Group");
          ungroupItem = new JMenuItem("UnGroup");
          changeName = new JMenuItem("Rename");
          groupItem.addActionListener(this);
          ungroupItem.addActionListener(this);
          changeName.addActionListener(this);
          editMenu.add(groupItem);
          editMenu.add(ungroupItem); 
          editMenu.add(changeName);
          
          setVisible(true);
          validate();
        
	}

	public String getmode() {
		return mode;
	}
	
	public String getGroup() {
		return ifgroup;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {//按按鈕
		if(btn[0].isSelected()) {//select
			canvas.removeMouseListener(this);
			canvas.removeMouseMotionListener(this);
			canvas.addMouseListener(this);
			canvas.addMouseMotionListener(this);
			mode = "Select";
		}else if(btn[1].isSelected()) {//association
			canvas.removeMouseListener(this);
			canvas.removeMouseMotionListener(this);
			canvas.addMouseListener(this);
			canvas.addMouseMotionListener(this);
			mode = "Assosiationline";
		}else if(btn[2].isSelected()) {//generalization
			canvas.removeMouseListener(this);
			canvas.removeMouseMotionListener(this);
			canvas.addMouseListener(this);
			canvas.addMouseMotionListener(this);
			mode = "Generalizationline";
		}else if(btn[3].isSelected()) {//composition
			canvas.removeMouseListener(this);
			canvas.removeMouseMotionListener(this);
			canvas.addMouseListener(this);
			canvas.addMouseMotionListener(this);
			mode = "Compositionline";
		}else if(btn[4].isSelected()) {//class
			canvas.removeMouseListener(canvas);
			canvas.removeMouseMotionListener(canvas);
			canvas.addMouseListener(canvas);
			mode = "Class";
		}else if(btn[5].isSelected()) {//usecase
			canvas.removeMouseListener(this);
			canvas.removeMouseMotionListener(this);
			canvas.addMouseListener(this);
			mode = "Use_case";
		}else {
			canvas.removeMouseListener(this);
			canvas.removeMouseMotionListener(this);
			mode = "NULL";
		}
		if(e.getSource()==groupItem) {
			canvas.addComposite();
		}
		if(e.getSource()==ungroupItem) {
			
		}
		
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
//------------------------------------------------
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
