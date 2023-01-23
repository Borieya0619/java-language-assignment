package test_project;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class BallThreadAssig_20191251 extends Frame implements ActionListener {
	private Canvas canvas;
	public BallThreadAssig_20191251() {
		canvas = new Canvas();
		add("Center", canvas);
		Panel p = new Panel();
		Button s = new Button("Start");
		Button c = new Button("Close");
		p.add(s); p.add(c);
		s.addActionListener(this);
		c.addActionListener(this);
		add("South", p);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="Start") {
			Ball b = new Ball(canvas);
			b.start();
		}
		if(e.getActionCommand()=="Close")
			System.exit(0);
	}
	
	public static void main(String args[]) {
		Frame f = new BallThreadAssig_20191251();
		f.setSize(400, 300);
		WindowDestroyer listener = new WindowDestroyer();
		f.addWindowListener(listener);
		f.setVisible(true);
	}
}


class Ball extends Thread{
	private Canvas box;
	private static final int XSIZE = 16; 
	private static final int YSIZE = 16;
	private ArrayList<Integer> x = new ArrayList<>();//{200, 200, 200, 200, 200};
	private ArrayList<Integer> y = new ArrayList<>(); //{150, 150, 150, 150, 150};
	private ArrayList<Integer> dx = new ArrayList<>(); //{-2, 2, -2, -1, 2};
	private ArrayList<Integer> dy = new ArrayList<>(); //{-2, -2, 2, 2, 2};
	private ArrayList<Integer> collapsedJustNow = new ArrayList<>();
	private ArrayList<Integer> bornJustNow = new ArrayList<>();
	private Dimension df;
	private int first = 0;
	//private int ballX[]; //ball들의 x좌표를 저장할 배열
	//private int ballY[]; //ball들의 x좌표를 저장할 배열
	private ArrayList<Integer> ballSize = new ArrayList<>(); //ball들의 크기를 저장할 배열
	private int count=5; //만들어진 ball의 개수
	private int newcount;
	public Ball (Canvas c) {
		box = c;
		df = box.getSize();
	}
	
	public void draw() { 
		
			for(int i=0; i<count; i++) {
				x.add(i, df.width/2);
				y.add(i, df.height/2);
				ballSize.add(i, XSIZE);
				collapsedJustNow.add(i, 0);
				bornJustNow.add(i,0);
			}
	
		dx.add(-4); dx.add(2); dx.add(-6); dx.add(-2); dx.add(2);	 //이거 추가하기
		dy.add(-2); dy.add(-2); dy.add(2); dy.add(6); dy.add(4); 	
		
		for(int i=0; i<count; i++) {
		Graphics g = box.getGraphics();
		
		g.fillOval(x.get(i), y.get(i), XSIZE, YSIZE); g.dispose();
		}
		
		
	}
	
	public void move() {
		count = x.size(); int i=0;
		while(i<count) {
		Graphics g = box.getGraphics();
		g.setXORMode(box.getBackground());
		//System.out.println("좌표들은 "+x[i]+", " +y[i]);
		
		//이전 위치에서 공지우기. 만일 직전에 충돌한 경우, 원래 사이즈가 2였다면 지워버리고 다시 그리지 않는다. 2가 아니라면 이전 크기 만큼을 지우고 새 크기로 다시 그린다. 
		if(collapsedJustNow.get(i)==1&&ballSize.get(i)>1) {g.fillOval(x.get(i),  y.get(i), ballSize.get(i)*2, ballSize.get(i)*2); collapsedJustNow.set(i, 0);}
		else if(collapsedJustNow.get(i)==1&&ballSize.get(i)==1) {g.fillOval(x.get(i),  y.get(i), ballSize.get(i)*2, ballSize.get(i)*2); 
		x.remove(i); y.remove(i); dx.remove(i); dy.remove(i); ballSize.remove(i); collapsedJustNow.remove(i); bornJustNow.remove(i);
		count--;
		continue;}
		else if(bornJustNow.get(i)!=1) {g.fillOval(x.get(i),  y.get(i), ballSize.get(i), ballSize.get(i)); }
		else bornJustNow.set(i, 0);
		
		
		x.set(i, x.get(i)+dx.get(i)); y.set(i, y.get(i)+dy.get(i)); 
		
		Dimension d = box.getSize();
		if(x.get(i)<0) {x.set(i,  0); dx.set(i, -dx.get(i));}//x[i]=0; dx[i] = -dx[i];}
		if(x.get(i)+ballSize.get(i)>=d.width) {x.set(i, d.width-ballSize.get(i)); dx.set(i, -dx.get(i));}//x[i] = d.width-ballSize[i]; dx[i] = -dx[i];}
		if(y.get(i)<0){y.set(i,  0); dy.set(i, -dy.get(i));}//y[i]=0; dy[i] = -dy[i];}
		if(y.get(i)+ballSize.get(i)>=d.height) {y.set(i, d.height-ballSize.get(i)); dy.set(i, -dy.get(i));}//y[i] = d.height-ballSize[i]; dy[i] = -dy[i];}
		
		
		g.fillOval(x.get(i), y.get(i), ballSize.get(i), ballSize.get(i)); //System.out.println(ballSize.get(i));
		//움직였는데 부딪히는 공이 있다면, 즉 공들 사이의 거리가 각각의 반지름을 더한 값이라면 공들을 전부 지우고 각각 dx=-dx, dy=-dy 방향으로 절반 크기의 공 두 개씩 그려준다. 
		//만일 갱신한 (newX, newY)와 (ballX[j], ballY[j]) 사이 거리가 둘 의 반지름을 더한 값이라면 둘의 충돌을 의미한다. 따라서 이때 공을 다시 지우고, 부딪힌 공도 다시 지워준다. 
		//그 다음, 둘의 절반 크기인 두 개의 공들을 서로의 dx dy의 +-절반이며 크기가 절반인 애들을 그려준다.
		//새 공들은 각 배열들에 추가한다. 이때 4개가 아닌 두 개씩만 추가하면 될 것...
		if(first >= 4) {
		for(int j=0; j<count; j++) {
			if(j!=i) {
				int ceniX = (x.get(i)+ballSize.get(i)/2); int ceniY =  (y.get(i)+ballSize.get(i)/2); //i번째 공의 중심좌표 
				int cenjX = (x.get(j)+ballSize.get(j)/2); int cenjY = (y.get(j)+ballSize.get(j)/2); //j번째 공의 중심좌표
				double dis = Math.sqrt(Math.pow(ceniX-cenjX, 2) + Math.pow(ceniY-cenjY, 2)); // 두 원의 중심 사이의 거리
				double sumOfR = ballSize.get(i)/2+ballSize.get(j)/2; //i번째 공의 반지름+j번째 공의 반지름
				if (dis<=sumOfR){ //공의 중심들끼리의 거리가 반지름의 합보다 작거나 같다면 겹치거나 충돌함을 의미!
					
					ballSize.set(i,  ballSize.get(i)/2); ballSize.set(j,  ballSize.get(j)/2); 
					collapsedJustNow.set(i, 1); collapsedJustNow.set(j, 1); 
					
					int temp = dx.get(i); dx.set(i, dx.get(j)); dx.set(j,  temp); //dx[i] = dx.get(j); dx[j] = temp;
					int temp2 = dy.get(i); dy.set(i, dy.get(j)); dy.set(j,  temp2);//dy[i] = dy.get(j); dy[j] = temp2;
					
					if(ballSize.get(i)!=1) {//자식의 사이즈가 1이 아닌 경우에만 자식공들 추가
					//절반 크기 공 하나씩 추가해서 그리기
					x.add(x.get(i)+ballSize.get(i)+8); y.add(y.get(i));
					dx.add(-dx.get(i)); dy.add(dy.get(i));
					ballSize.add(ballSize.get(i));
					collapsedJustNow.add(0); 
					bornJustNow.add(1); 
					first = 0;
					}
					if(ballSize.get(j)!=1) {
						x.add(x.get(j)+ballSize.get(j)+8); y.add(y.get(j));
						dx.add(-dx.get(i)); dy.add(-dy.get(i));
						ballSize.add(ballSize.get(j));
						collapsedJustNow.add(0); 
						bornJustNow.add(1);
						first = 0;
					}
					//first = 0;
				}
			}
		} 
		}
		i++;
		//충돌이 없다면 넘어간다.
		g.dispose();
		}
		first += 1;
	}
	
	public static void attack() {}
	
	public void run() {
		draw();
		for(int i=0; ; i++) {
			move();
			
			try {Thread.sleep(50);}
			catch(InterruptedException e) {}
		}
	}
}

