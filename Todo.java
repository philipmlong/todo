// Todo:
//
//    -- differentiate between done and deleted tasks
//    -- move item to top at a future date

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class Todo {
    private JFrame mainFrame;
    private JPanel controlPanel;
    final DefaultListModel todoList = new DefaultListModel();
    JList todoJList = new JList(todoList);
    JScrollPane todoPane = new JScrollPane(todoJList);    
   
    public Todo(){
	prepareGUI();
    }
    public static void main(String[] args)  {
	Todo  todo = new Todo();
	todo.load();
	todo.run();
            }});
    }
    private void prepareGUI(){
	mainFrame = new JFrame();
	mainFrame.setSize(600,600);
	mainFrame.setLayout(new GridLayout(1, 2));
	/* place window in lower right corner         */
	GraphicsEnvironment ge
	    = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        mainFrame.setLocation((int) rect.getMaxX() - mainFrame.getWidth(),
			      (int) rect.getMaxY() - mainFrame.getHeight());
      
	mainFrame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent windowEvent) {
		    save();
		    System.exit(0);
		}        
	    });    

	controlPanel = new JPanel();
	controlPanel.setLayout(new BoxLayout(controlPanel,
					     BoxLayout.Y_AXIS));

	mainFrame.add(todoPane);
	mainFrame.add(controlPanel);
	/*   The following line would make the window was small as possible.
		
		mainFrame.pack(); */
	mainFrame.setVisible(true);
	mainFrame.toFront();
    }
    private void run(){                                       

	JTextField taskField = new JTextField(20);
	taskField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) { 
		    String taskString = taskField.getText();
		    todoList.add(0, taskString);
		    taskField.setText("");
		    todoJList.setSelectedIndex(0);
		}
	    });
	taskField.setMaximumSize(new Dimension(600,24));
	controlPanel.add(taskField);

	JButton removeButton = new JButton("Remove");
	removeButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) { 
		    if (todoList.getSize() > 0) {
		       int taskIndex = todoJList.getSelectedIndex();
		       todoList.remove(taskIndex);
		       save();
		       if (taskIndex  < todoList.getSize()) {
			   todoJList.setSelectedIndex(taskIndex);
		       } else if (todoList.getSize() > 0) {
			   todoJList.setSelectedIndex(todoList.getSize() - 1);
		       }
		    }
		}
	    });
	controlPanel.add(removeButton);

	JButton topButton = new JButton("Top");
	topButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if (todoList.getSize() > 0) {
		       Object task = todoJList.getSelectedValue();
		       if (task instanceof String) {
			  int taskIndex = todoJList.getSelectedIndex();
			  todoList.remove(taskIndex);
			  todoList.add(0, task);
			  todoJList.setSelectedIndex(0);
			  save();
		       }
		    }
		}
	    });
	controlPanel.add(topButton);

	JButton downButton = new JButton("Down");
	downButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if ((todoList.getSize() > 0)
			&& (todoJList.getSelectedIndex()
			    < todoList.getSize() - 1)) {
		       Object task = todoJList.getSelectedValue();
		       if (task instanceof String) {
			  int taskIndex = todoJList.getSelectedIndex();
			  todoList.remove(taskIndex);
			  todoList.add(taskIndex+1, task);
			  todoJList.setSelectedIndex(taskIndex+1);
			  save();
		       }
		    }
		}
	    });
	controlPanel.add(downButton);

	JButton upButton = new JButton("Up");
	upButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if (todoJList.getSelectedIndex() > 0) {
		       Object task = todoJList.getSelectedValue();
		       if (task instanceof String) {
			  int taskIndex = todoJList.getSelectedIndex();
			  todoList.remove(taskIndex);
			  todoList.add(taskIndex-1, task);
			  todoJList.setSelectedIndex(taskIndex-1);
			  save();
		       }
		    }
		}
	    });
	controlPanel.add(upButton);
	
	mainFrame.setVisible(true);             
    }
    // Loads items from the file and inserts them at the end of the list
    private void load() {
	try {
	  FileReader fin = new FileReader("/Users/phil/src/todo/todo.txt");
	  BufferedReader reader = new BufferedReader(fin);
	  String line = reader.readLine();
	  while (line != null) {
	      todoList.addElement(line);
	      line = reader.readLine();
	  }
	  reader.close();
	  if (todoList.getSize() > 0) {
	      todoJList.setSelectedIndex(0);
	  }
	} catch (Exception e) {
	}
    }
    private void save() {
	try {
	  FileWriter fout = new FileWriter("/Users/phil/src/todo/todo.txt");
	  BufferedWriter writer = new BufferedWriter(fout);
	  for (int i = 0;
	       i < todoList.getSize();
	       i++) {
	      writer.write((String)todoList.get(i) + "\n");
	      
	  }
	  writer.close();
	} catch (Exception e) {
	}
    }
}
