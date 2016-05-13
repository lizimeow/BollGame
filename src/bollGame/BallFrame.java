package bollGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class BallFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
    private ArrayList<Ball> list = new ArrayList<Ball>();
    private Random rand = new Random();
    private JPanel centerPanel;
    private Timer timer;
    private TimeCount timecount;
    private JLabel lable;
    public static int mouseX,mouseY; 
    private boolean gameOver = false;
    private boolean lastOver = false;
    private int response;
    double distX, distY, distance;
    private int count;

    public void setGameOver(boolean go) {
    	this.gameOver = go;
    }
	 
	public BallFrame() throws IOException
	{
		super();
		init();
	}
	
	private void init() {
		this.setTitle("KEEP LIFE");  
        this.setSize(710, 650);  
        this.setDefaultCloseOperation(3);  
        this.setLocationRelativeTo(null);  //����
        this.setAlwaysOnTop(true);
        this.setLayout(new BorderLayout());  
        this.getContentPane().setBackground(Color.WHITE);  
        lable = new JLabel("ʱ��:");  
        lable.setForeground(Color.black);  
        this.add(lable,BorderLayout.SOUTH);  
        this.count = 0;
        
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        this.add(centerPanel,BorderLayout.CENTER);
        this.setVisible(true);
       
        BufferedImage bf = new BufferedImage(40, 40, BufferedImage.TYPE_INT_BGR); 
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(bf, new Point(0, 0), "A");	
        centerPanel.setCursor(cursor);
        createBall(BeginDialog.SumNumber);
        start();
       
        addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent event){
				mouseX = event.getX();
				mouseY = event.getY();
			}
			
			public void mouseDragged(MouseEvent event){
				mouseX = event.getX();
				mouseY = event.getY();
			}
		});
        
         
	}
	  public void createBall(int sum){
	        for(int i=0;i<sum;i++){
	            addBall();
	        }
	  }
	  
	  public void addBall(){
		  Ball ball = new Ball(new Color(rand.nextInt(255), rand.nextInt(255),  
                  rand.nextInt(255)), rand.nextInt(650), rand.nextInt(600), 50,  
                  rand.nextInt(4) + 1, rand.nextInt(4) + 1, this);
          ball.start();
          list.add(ball);
	  }
	 
	  //��дpaint
	  public void paint(Graphics g) {  
	        // ���ø����paint����  
		   if(!gameOver){
			   super.paint(g);  
		       for (int i = 0; i < list.size(); i++) {  
		            // ��ball�л�ȡ��ɫ������  
		            g.setColor(list.get(i).getcolor());  
		            // ����С��  
		            g.fillOval(list.get(i).getX(),list.get(i).getY(), list.get(i).getRadiu(),list.get(i).getRadiu()); 
		       }
		      
		       lable.setText("���Ѽ�֣�"+timecount.showTime());
		       collision();  
		       // ������ײ����  
	        }
		       
	    }
	  
	  //��ʱ�߳�����
	 public void start(){
		 timecount = new TimeCount();
		 timer = new Timer();
		 timer.schedule(new TimerTask() {
			//private boolean lastGameover;
			public void run() {	
				if (!gameOver){
					count++;
					timecount.update();
					repaint();
					if(count%200==0){
						addBall();
					}
				}
				if (gameOver != lastOver){
					isAgain();
				}
				lastOver = gameOver;
			}
		}, 0 , 50);
	 }
	 
	 public void isAgain(){
		response=JOptionPane.showConfirmDialog(this, "�������"+timecount.showTime()+"��,�Ƿ����¿�ʼ��Ϸ��","GAMEOVER",JOptionPane.OK_CANCEL_OPTION);
		//new BeginDialog();
   	    if (response == 0) {   
            if (list.size() != 0) {  
                // �ֽ�ԭ���Ķ���Ӷ������Ƴ�  
                list.removeAll(list); 
                gameOver = true;
                new BeginDialog();
            } 
   	    } else if (response == -1 || response == 1) { 
       	// �������رգ����̶߳���Ӷ������Ƴ�  
            list.removeAll(list);  
        } else{
        	System.exit(0);
        }
	 }
 
	// ��ײ����  
    private void collision() {  
        // �������飬�洢��С���ľ���  
        double[][] dis = new double[list.size()][list.size()];  
        for (int i = 0; i < list.size(); i++) {  
            for (int j = 0; j < list.size(); j++) {  
                // ��������С���ľ���  
                dis[i][j] = Math.sqrt(Math.pow(list.get(i).getX() - list.get(j).getX(),  
                        2) + Math.pow(list.get(i).getY() - list.get(j).getY(), 2));  
            }  
        }  
        for (int i = 0; i < list.size(); i++) {  
            for (int j = i + 1; j < list.size(); j++) {  
                if (dis[i][j] < (list.get(i).getRadiu() + list.get(j).getRadiu()) / 2) {  
                    int t;  
                    // ����С��x������ٶ�  
                    t = list.get(i).getVx();  
                    list.get(i).setVx(list.get(j).getVx());  
                    list.get(j).setVx(t);  
                    // ����С��y������ٶ�  
                    t = list.get(i).getVy();  
                    list.get(i).setVy(list.get(j).getVy());  
                    list.get(j).setVy(t);  
                    // ȷ����ײ��ڶ���С���λ��  
                    int x2 = list.get(j).getX() - list.get(i).getX(), y2 = list.get(j)  
                            .getY() - list.get(i).getY();  
                    list.get(j).setX(list.get(i).getX() + x2);  
                    list.get(j).setY(list.get(j).getY() + y2);  
                } else {  
                }  
            }  
        }  
    }  
}
