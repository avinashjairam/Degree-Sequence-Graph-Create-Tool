import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class Graph extends JFrame {
	 public Graph(){
	 	add(new NewPanel());
    	
    }
	public static void main(String [] args){
		Graph frame = new Graph();
        frame.setSize(800, 600);
        frame.setTitle("Graph Creater");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class NewPanel extends JPanel implements ActionListener{
	JPanel panel1 = new JPanel(new BorderLayout());
    JPanel panel2 = new JPanel(new GridLayout(1,2));
    JPanel panel3 = new JPanel();
      
    JTextField txtdata = new JTextField(20);
    JButton enter = new JButton("Enter");
    String data="";     
	
	int [] sequence;
	int graphicCount = 0;
	boolean graphic = false;
	boolean negativeDegree = false;
	int size;


	public NewPanel(){
	 	super.setPreferredSize(new Dimension(600, 400));       
        panel2.add(txtdata);
        panel2.add(enter);
       // panel3.add(new NewPanel());
        panel1.add(panel2, BorderLayout.EAST);
        panel1.add(panel3, BorderLayout.CENTER);
        enter.addActionListener(this);
        add(panel1);
	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//g.drawLine(0,0,600,400);
	//	int x = sequence.get(0);

		g.drawString(data,400,220);
	}

	 public void actionPerformed(ActionEvent e){
        if (e.getSource() == enter) {
             data = txtdata.getText(); //perform your operation
             data = data.replace(",","");
             data = data.trim();
             initializeSequence();
             processSequence();
           // repaint();
        }
    }

    public void initializeSequence(){
    	sequence = new int[data.length()];
    	for(int i = 0; i < sequence.length;i++){
    		sequence[i] = Character.getNumericValue(data.charAt(i));
    		//System.out.println(sequence[i]);
    	}
    	System.out.print("after init");
    	printSequence();
    }

    public void processSequence(){
    	size = sequence.length;
    	while(graphic == false){
    		int front = sequence[0];
    		graphicCount =0;

    		sequence =Arrays.copyOfRange(sequence,1,sequence.length);

    		if(front > sequence.length){
    			data = "Invalid Sequence!";
    			repaint();
    			break;
    		}
    		else{
    			for(int i = 0; i < front;i++){
    				sequence[i] -=1;
    			}
    			Arrays.sort(sequence);
    			reverse();
    			printSequence();

    			for(int i = 0; i <sequence.length; i++){
    				if(sequence[i]==0){
    					graphicCount +=1;
    					System.out.println ("graphic count " + graphicCount + "length " + sequence.length);
    				}
    				if(sequence[i]==-1){
    					negativeDegree = true;
    				}
    			}

    			if(negativeDegree){
    				data= "Invalid Sequence";
    				repaint();
    				break;
    			}

    			if(graphicCount == sequence.length){
    				graphic =true;
    				data = "valid Sequence";
    				repaint();
    				break;
    			}

    			

    		}
        }

    }

    public void printSequence(){
    	for(int i = 0; i < sequence.length;i++){
    		System.out.print(sequence[i] + " ");
    	}
    	System.out.println();
    }

    public void reverse(){
    	for (int i = 0; i < sequence.length / 2; i++) {
			  int temp = sequence[i];
 			 sequence[i] = sequence[sequence.length - 1 - i];
 			 sequence[sequence.length - 1 - i] = temp;
		}
   }
  }