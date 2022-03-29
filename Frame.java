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
    JMenuItem renameItem;
    JButton okButton,cancelButton;
    JTextField renameField;
    JFrame renameFrame;
    Dimension dimension;
    
	public Frame() {
		setTitle("UML_editor");
        // 獲取螢幕解析度
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
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
          renameItem = new JMenuItem("Rename");
          groupItem.addActionListener(this);
          ungroupItem.addActionListener(this);
          renameItem.addActionListener(this);
          editMenu.add(groupItem);
          editMenu.add(ungroupItem); 
          editMenu.add(renameItem);
          
          setVisible(true);
          validate();
        
	}
	
	public String getGroup() {
		return ifgroup;
	}
	
	public void portUnvisible() {//隱藏所有port
		for(Class c : canvas.class_list) c.setvisible(false);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {//按按鈕
		if(btn[0].isSelected()) {//select
			canvas.removeMouseListener(canvas);
			canvas.removeMouseMotionListener(canvas);
			canvas.addMouseListener(canvas);
			canvas.addMouseMotionListener(canvas);
			portUnvisible();
			mode = "Select";
		}else if(btn[1].isSelected()) {//association
			portUnvisible();
			mode = "Assosiationline";
		}else if(btn[2].isSelected()) {//generalization
			portUnvisible();
			mode = "Generalizationline";
		}else if(btn[3].isSelected()) {//composition
			portUnvisible();
			mode = "Compositionline";
		}else if(btn[4].isSelected()) {//class
			canvas.removeMouseListener(canvas);
			canvas.removeMouseMotionListener(canvas);
			canvas.addMouseListener(canvas);
			portUnvisible();
			mode = "Class";
		}else if(btn[5].isSelected()) {//usecase
			canvas.removeMouseListener(canvas);
			canvas.removeMouseMotionListener(canvas);
			canvas.addMouseListener(canvas);
			portUnvisible();
			mode = "Use_case";
		}else {
			canvas.removeMouseListener(canvas);
			canvas.removeMouseMotionListener(canvas);
			//portUnvisible();
			mode = "NULL";
		}
		if(e.getSource()==groupItem) canvas.addComposite();
		
		if(e.getSource()==ungroupItem) canvas.deComposite();
		
		if(e.getSource()==renameItem) {
			if(canvas.selectedRenameClass!=null) {
				renameFrame();
			}
		}
		
		if(e.getSource()==okButton) {
			canvas.selectedRenameClass.setTitle(renameField.getText());
			renameFrame.dispose();
			canvas.selectedRenameClass = null;
		}
		if(e.getSource()==cancelButton) {
			renameFrame.dispose();
			canvas.selectedRenameClass = null;
		}
			
	}

	public void renameFrame() {//改名視窗
		renameFrame = new JFrame();
		renameFrame.setTitle("Rename");
		renameFrame.setLayout(new FlowLayout());
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        renameFrame.setLocation(x, y);
        renameFrame.setResizable(false);
        
		okButton = new JButton("OK");
		okButton.setFont(new Font("MV Boli", Font.BOLD, 15));
		cancelButton = new JButton("CANCEL");
		cancelButton.setFont(new Font("MV Boli", Font.BOLD, 15));
		renameField = new JTextField();
		renameField.setPreferredSize(new Dimension(250,40));
		renameField.setFont(new Font("MV Boli", Font.BOLD, 20));
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		renameFrame.add(okButton);
		renameFrame.add(cancelButton);
		renameFrame.add(renameField);
		renameFrame.pack();
		renameFrame.setVisible(true);
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
