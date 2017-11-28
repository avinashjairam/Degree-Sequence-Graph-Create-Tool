import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class Graph4 extends JFrame {
  NewPanel panel = new NewPanel();

	 public Graph4(){
	 	add(panel);

    addMouseListener(new MouseAdapter() { 
          public void mousePressed(MouseEvent e) { 
            panel.processClicks(e.getX(),e.getY());
          } 
    });
 
      addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
              panel.processDrag(e.getX(),e.getY());           
           }
     });
  }

	public static void main(String [] args){
		Graph4 frame = new Graph4();
        frame.setSize(1200, 1000);
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
  char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	int [][] matrix;

	int [] xCoordinates;
	int [] yCoordinates;
	int graphicCount = 0;

  ArrayList<Vertex> vertex;
  ArrayList<Edge>edges;

  ArrayList<ArrayList<Vertex>> subVertex = new ArrayList<ArrayList<Vertex>>();


	boolean graphic = false;
	boolean negativeDegree = false;
	boolean invalid = false;
  boolean reset = false;
  boolean attempt = false;
	int size;
    int endX=300, endY=400, x=300,y=300,z=200;
    Color randomColor = new Color(220, 220, 220);


	public NewPanel(){
    super.setPreferredSize(new Dimension(600, 400));     
    vertex = new ArrayList<Vertex>();  
    edges = new ArrayList<Edge>();
        panel2.add(txtdata);
        panel2.add(enter);
        panel1.add(panel2, BorderLayout.EAST);
        panel1.add(panel3, BorderLayout.CENTER);
        enter.addActionListener(this);
  
        add(panel1);
	}

	protected void paintComponent(Graphics g){
    

		super.paintComponent(g);
		
		// int endX=300, endY=400, x=300,y=300,z=200;

    System.out.println("Graphic is " + graphic);

    if(reset){
             g.setColor(UIManager.getColor ( "Panel.background" ));
             g.fillRect(0,0,1200,1000);
             reset=false;
              g.setColor(randomColor);

          
     }

  	
  		if(graphic){
                    g.setColor(new Color(0,0,0));

         for(int i=0; i <edges.size();i++){
          for(int j=0; j< edges.get(i).getNeighborsSize();j++){
            g.drawLine(edges.get(i).getX(),edges.get(i).getY()+10,edges.get(i).neighbors.get(j).getXCoordinates(),edges.get(i).neighbors.get(j).getYCoordinates()+10);
            System.out.println(edges.get(i).getName() + " to " + edges.get(i).neighbors.get(j).getName());
          }
       }
      
  			for ( int i = 0; i<size; i++ ){
     			endX = x + (int)(z*Math.cos( (2*Math.PI/size)*i ));
      			endY = y - (int)(z*Math.sin( (2*Math.PI/size)*i ));  // Note "-"
      			xCoordinates[i] = endX +10;
      			yCoordinates[i] = endY +10;
      			//System.out.println("xCoordinates " + endX + "yCoordinates " + endY);
            g.setColor(randomColor);
		        g.fillOval(endX,endY,20,20);
            System.out.println(endX + " " + endY + " " + alphabet[i]);
            g.setColor(new Color(0,0,0));
            g.drawString(Character.toString(alphabet[i]),endX+5,endY+15);
            vertex.add(new Vertex(alphabet[i],originalSequence[i], endX+10,endY+10));

           
       

    		}

     
    	
    	}

    
    	if(invalid){
        g.setColor(new Color(0,0,0));

    		g.drawString(data,400,300);
    	}

    

		//g.drawString(data,400,220);
		
	}

  public void processClicks(int x, int y){
    System.out.println("x = " + x + "y =" + y);

    for(int i = 0; i < xCoordinates.length;i++){
      if((Math.abs(xCoordinates[i] - x) < 50) && (Math.abs(yCoordinates[i] - y) < 50) ){
        System.out.println(alphabet[i]);
      }
    }
  }

  public void processDrag(int x, int y){
    char v ='4';

    for(int i = 0; i < xCoordinates.length;i++){
      if((Math.abs(xCoordinates[i] - x) < 50) && (Math.abs(yCoordinates[i] - y) < 50) ){
        v = (alphabet[i]);
      }

      for(int j = 0; j < edges.size();j++){
        if(edges.get(j).getName()==v){
          edges.get(j).setX(x);
          edges.get(j).setY(y);
          endX=x;
          endY=y;
          repaint();
        }
      }
    }
  }

	 public void actionPerformed(ActionEvent e){
        if (e.getSource() == enter) {
              if(attempt){
                reset=true;

                data="";     
  
               sequence=null;
                originalSequence=null;
               matrix =null;

               xCoordinates =null;
              yCoordinates =null;
              graphicCount = 0;

             vertex = new ArrayList<Vertex>();  
            edges = new ArrayList<Edge>();

            subVertex = new ArrayList<ArrayList<Vertex>>();
             graphic = false;
             negativeDegree = false;
            invalid = false;
               size=0;
                 data = txtdata.getText(); //perform your operation
             data = data.replace(",","");
             data = data.trim();
             initializeSequence();
             processSequence();
              //  this.dispose();
               // new Graph3();
                repaint();
              }else{
                 data = txtdata.getText(); //perform your operation
                  data = data.replace(",","");
                  data = data.trim();
                  initializeSequence();
                   processSequence();


              }

            
         //    findEdges();
                // System.out.println(vertex.size());

            

           // repaint();
        }
    }

    public void initializeSequence(){
      int endX=300, endY=400, x=300,y=300,z=200;

    	sequence = new int[data.length()];
    	originalSequence = new int[data.length()];
    	xCoordinates = new int[data.length()];
    	yCoordinates = new int[data.length()];

    	for(int i = 0; i < sequence.length;i++){
    		sequence[i] = Character.getNumericValue(data.charAt(i));
    		originalSequence[i] = Character.getNumericValue(data.charAt(i));

        endX = x + (int)(z*Math.cos( (2*Math.PI/sequence.length)*i ));
        endY = y - (int)(z*Math.sin( (2*Math.PI/sequence.length)*i ));  // Note "-"
        vertex.add(new Vertex(alphabet[i], originalSequence[i], endX+10,endY+10));


    	}

       
    }

    public void processSequence(){
     Collections.sort(vertex, (v1, v2) -> v2.getDegree() - v1.getDegree());
     // findEdges();
     int count =0;
    	size = sequence.length;
      attempt = true;
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
          ArrayList<Vertex> sub = new ArrayList<Vertex>();
    			for(int i = 0; i < front;i++){
    				sequence[i] -=1;
            sub.add(new Vertex(alphabet[i], sequence[i],0,0));
    			}

          subVertex.add(sub);
          Collections.sort(subVertex.get(count), (v1, v2) -> v2.getDegree() - v1.getDegree());    

          count++;
    			Arrays.sort(sequence);
    			reverse();
    			//printSequence();

    			for(int i = 0; i <sequence.length; i++){
    				if(sequence[i]==0){
    					graphicCount +=1;
    				//	System.out.println ("graphic count " + graphicCount + "length " + sequence.length);
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
    				//printMatrix();
    				findEdges();
    			   printEdges();
    		//		System.out.println("num of edges " + countEdges(0));
    				repaint();

            for(ArrayList<Vertex> v : subVertex){
              for(int i =0; i<v.size();i++){
                System.out.print(v.get(i).getName() + " " + v.get(i).getDegree() + " ");
              }
              System.out.println();
            }
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

  public void findEdges(){
    while(!vertex.isEmpty()){
      Vertex front = vertex.get(0);

      vertex.remove(0);

      System.out.println("front is " + front.getName());

      ArrayList<Vertex>copy = new ArrayList<Vertex>();

      for(int i = 0; i<front.getDegree();i++){
        copy.add(new Vertex(vertex.get(i).getName(),vertex.get(i).getDegree(), vertex.get(i).getXCoordinates(),vertex.get(i).getYCoordinates()));
      }

    //  map.put(front.getName(), new ArrayList<Vertex>(vertex.subList(0,front.getDegree())));
      Edge edge = new Edge(front, copy);
      edges.add(edge);
    
      //System.out.println("vertex size is " + vertex.size());

      edge.printList();

       System.out.println("Vertex is (Before decrement) ");
      for(int i =0 ;i <vertex.size();i++){
        System.out.println(vertex.get(i).getName() + " deg. " + vertex.get(i).getDegree() + " " );
      }
      System.out.println();

       System.out.println("Vertex is (During Decrement ");

       for(int i = 0; i <front.getDegree();i++){
          System.out.println(vertex.get(i).getName() + " deg. " + vertex.get(i).getDegree() + " " );

        // if(i == vertex.size()){
        //    System.out.println("i =" + i + " vertex size" + vertex.size());
        //      vertex.get(i-1).decrement();

        //      // if(vertex.get(i-1).getDegree() == 0){
        //      //    vertex.remove(i-1);
        //      //  }
        //      removeZero();


        //      break;
        // }
        System.out.print(vertex.get(i).getName());
            vertex.get(i).decrement();

              if(vertex.get(i).getDegree() == 0){
              //  vertex.remove(i);
               // removeZero();
              }
        
      }
//      Collections.shuffle(vertex);

      Collections.sort(vertex, (v1, v2) -> v2.getDegree() - v1.getDegree());    

      System.out.print("Vertex is (After Decrement) ");
      for(int i =0 ;i <vertex.size();i++){
        System.out.println(vertex.get(i).getName() + " deg. " + vertex.get(i).getDegree() + " " );
      }
      System.out.println();
    }
  

 }

 public void removeZero(){
  for(int i =0;i<vertex.size();i++){
        //  System.out.println("Vertex" + vertex.get(i).getName() + " " + vertex.get(i).getDegree());

    if(vertex.get(i).getDegree()==0){
      System.out.println(" Removing Vertex " + vertex.get(i).getName() + " with deg. " + vertex.get(i).getDegree());
      vertex.remove(i);
    }
  }
 }

 public void printEdges(){
  for(int i =0; i <edges.size();i++){
    System.out.print("EDGES " +edges.get(i).getName()+ " ");
    edges.get(i).printList();
  }
 }







}

class Vertex{
  char name;
  int degree;
  int xCoordinates;
  int yCoordinates;

  public Vertex(char name, int degree, int xCoordinates, int yCoordinates){
    this.name = name;
    this.degree = degree;
    this.xCoordinates = xCoordinates;
    this.yCoordinates = yCoordinates;
  }

  public void setName(char name){
    this.name=name;
  }

  public void setDegree(int degree){
    this.degree=degree;
  }

  public void setXCoordinates(int xCoordinates){
    this.xCoordinates=xCoordinates;
  }

  public void setYCoordinates(int yCoordinates){
    this.yCoordinates=yCoordinates;
  }

  public void decrement(){
    this.degree--;
  }

  public char getName(){
    return this.name;
  }

  public int getDegree(){
    return this.degree;
  }

  public int getXCoordinates(){
    return this.xCoordinates;
  }

  public int getYCoordinates(){
    return this.yCoordinates;
  }

}

class Edge{
  public Vertex vertex;
  public ArrayList<Vertex>neighbors = new ArrayList<Vertex>();

  public Edge(Vertex v, ArrayList<Vertex> neighbors){
    this.vertex = v;
    this.neighbors = neighbors;
  }

  public void printList(){
    for(int i =0; i<neighbors.size();i++){
      System.out.print(neighbors.get(i).getDegree() + " ");
    }

    System.out.println();
  }

  public void setX(int x){
    vertex.setXCoordinates(x);
  }

  public void setY(int y){
    vertex.setYCoordinates(y);
  }

  public char getName(){
    return vertex.getName();
  }

  public int getDegree(){
    return vertex.getDegree();
  }

  public int getX(){
    return vertex.getXCoordinates();
  }

  public int getY(){
    return vertex.getYCoordinates();
  }

  public int getNeighborsSize(){
    return neighbors.size();
  }

}