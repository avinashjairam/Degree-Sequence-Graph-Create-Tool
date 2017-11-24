import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class Graph3 extends JFrame {
	 public Graph3(){
	 	add(new NewPanel());
    	
    }
	public static void main(String [] args){
		Graph3 frame = new Graph3();
        frame.setSize(800, 600);
        frame.setTitle("Graphic Sequence");
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
	int [] originalSequence;
	int [][] matrix;

	int [] xCoordinates;
	int [] yCoordinates;
	int graphicCount = 0;
	boolean graphic = false;
	boolean negativeDegree = false;
	boolean invalid = false;
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
		
		int endX=300, endY=200, x=300,y=200,z=100;
  	
  		if(graphic){
  			for ( int i = 0; i<size; i++ ){
     			endX = x + (int)(z*Math.cos( (2*Math.PI/size)*i ));
      			endY = y - (int)(z*Math.sin( (2*Math.PI/size)*i ));  // Note "-"
      			xCoordinates[i] = endX +10;
      			yCoordinates[i] = endY +10;
      			System.out.println("xCoordinates " + endX + "yCoordinates " + endY);
		        g.fillOval(endX,endY,20,20);
		      //  g.drawString(Integer.toString(i),endX,endY);
    		}

    		// for(int i =0; i < size; i++){
    		// 	 g.drawString(Integer.toString(originalSequence[i]),xCoordinates[i]-10, yCoordinates[i]-10);

    		// 	for(int j = 0; j<size;j++){
    		// 		if(i == j){
    		// 			continue;
    		// 		}
    		// 		if(matrix[i][j]==1){
    		// 			g.drawLine(xCoordinates[i],yCoordinates[i],xCoordinates[j],yCoordinates[j]);

    		// 		}
    		// 	}
    		// }
    		//g.drawLine(xCoordinates[1],yCoordinates[1],xCoordinates[4],yCoordinates[4]);

    	}

    	if(invalid){
    		g.drawString(data,400,300);
    	}

    

		//g.drawString(data,400,220);
		
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
    	originalSequence = new int[data.length()];
    	xCoordinates = new int[data.length()];
    	yCoordinates = new int[data.length()];

    	for(int i = 0; i < sequence.length;i++){
    		sequence[i] = Character.getNumericValue(data.charAt(i));
    		originalSequence[i] = Character.getNumericValue(data.charAt(i));
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
    			invalid = true;
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
    				invalid = true;
    				repaint();
    				break;
    			}

    			if(graphicCount == sequence.length){
    				graphic =true;
    				data = "valid Sequence";
    				adjacencyMatrix();
    				//printMatrix();
    				findEdges();
    				printMatrix();
    				System.out.println("num of edges " + countEdges(0));
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

   public void adjacencyMatrix(){
   		matrix = new int [size][size];

   		for(int i =0; i<size;i++){
   			for(int j = 0; j<size;j++){
   				//if((i==j) || (j == 0 && i ==2)){
   					matrix[i][j]=0;
   				/*}
   				else{
   					matrix[i][j] = 1;
   				}*/
   			}
   		}
   }


   public void printMatrix(){
   	for(int i =0; i<size;i++){
   			for(int j = 0; j<size;j++){
   				System.out.print(matrix[i][j] + " ");
   			}
   			System.out.println();
   		}

   }

   public int countEdges(int col){
   	int count = 0;
   	for(int i = 0; i < size; i++){
   		if(matrix[i][col]==1){
   			count++;
   		}
   	}

   	return count;
   }

  public void findEdges(){
  	int checks = 0;
   	for(int i = 0; i < size; i++){
   		checks =0;
   		for(int j = 0; j <size; j++){
   			if(i==j){
   				continue;
   			}
   			else{
   				if(countEdges(j) < originalSequence[j]){
   					System.out.println("i =" +i + " j =" +j + "countEdges " + countEdges(j) + " originalSequence " +originalSequence[j]);
   					if(checks < originalSequence[i]){
   						matrix[i][j]=1;
   						checks++;
   					}
   				}
   			}
   		} 
   }
 }

}