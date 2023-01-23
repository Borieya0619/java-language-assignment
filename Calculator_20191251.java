package test_project;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class Calculator_20191251 extends Frame implements ActionListener{
	private Frame f;
	private JTextField ans;
	private String result;
	Stack<Character> opStack = new Stack<>();
	Stack<Double> numStack = new Stack<>();
	int sixOp = 0;
	int logv=0; int lnv=0; int root=0; int per = 0; int factor = 0; int power = 0;
	Color sky = new Color(51, 153, 255);
	Color grey = new Color(160, 160, 160);
	Color grey2 = new Color(224, 224, 224);
	public Calculator_20191251() {
		f = new Frame("Calculator");
		f.setBackground(Color.WHITE);
		f.setBounds(100, 100, 412, 598);
		ans = new JTextField("0"); //진행 상황을 보여줄 text field
		ans.setFont(new Font("Tahoma", Font.PLAIN, 80));
		ans.setBounds(12, 30, 300, 80);                                               
		ans.setHorizontalAlignment(JTextField.RIGHT);
		f.add(ans, BorderLayout.NORTH);
		
		Panel buttonP = new Panel();
		buttonP.setLayout(new GridLayout(5,5,10,10));
		buttonP.setBackground(Color.WHITE);
		buttonP.setBounds(12, 150, 300, 40);
		
		String buttonName[]= {"!", "(", ")", "%", "AC", "Ln", "7", "8",
				"9", "÷", "log", "4", "5", "6", "x", "√", "1", "2", "3", "-", "^", "0", ".", "=", "+"};
		
		Button buttons[] = new Button[buttonName.length];
		
		for(int i=0; i<buttonName.length; i++) {
			buttons[i] = new Button(buttonName[i]);
			buttons[i].setFont(new Font("Tahoma", Font.PLAIN, 30));
			if(buttonName[i].equals("=")) buttons[i].setBackground(sky);
			else if(buttonName[i].equals("0")||buttonName[i].equals("1")||buttonName[i].equals("2")||buttonName[i].equals("3")||
					buttonName[i].equals("4")||buttonName[i].equals("5")||buttonName[i].equals("6")||buttonName[i].equals("7")||
					buttonName[i].equals("8")||buttonName[i].equals("9")) buttons[i].setBackground(grey2);
			else
				buttons[i].setBackground(grey);
			buttons[i].addActionListener(this);
			buttonP.add(buttons[i]);
		}
		
		f.add(buttonP);
		
		
		
		//종료 버튼 생성 및 frame 띄우기
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
		      dispose();
		      System.exit(0);
			}		
		});
		
		f.setVisible(true);

	}
	
	public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		String clicked_button = e.getActionCommand(); //눌려진 버튼 이름
		if(clicked_button.equals("AC")) ans.setText("0"); //AC키 눌리면 text 0으로 초기화
		else if(clicked_button.equals("=")) {
			if(sixOp==1) {//단일 연산만 수행하는 3가지 연산들
			System.out.println("여기");
			if(root==1) {
				int operand = Integer.parseInt(ans.getText().substring(1)); //System.out.println(operand);
				double val = Math.sqrt(operand);
				ans.setText(""+val);
				root=0;
			}
			else if(logv==1) {//System.out.println(ans.getText());
				int operand = Integer.parseInt(ans.getText().substring(3)); //System.out.println(operand);
				double val = Math.log(operand)/Math.log(10);
				ans.setText(""+val);
				logv=0;
			}
			else if(lnv==1) {
				int operand = Integer.parseInt(ans.getText().substring(2)); //System.out.println(operand);
				double val = Math.log(operand);
				ans.setText(""+val);
				lnv=0;
			}
			else if(power==1) {
			int ind = ans.getText().indexOf('^'); //System.out.println(ind);
		    double operand1 = Double.parseDouble(ans.getText().substring(0,ind));
			double operand2 = Double.parseDouble(ans.getText().substring(ind+1));
			double val = Math.pow(operand1, operand2);
			ans.setText(""+val);
			power=0;
			}
			else if(factor==1) {//System.out.println(ans.getText());
			int ind = ans.getText().indexOf('!');
			int operand = Integer.parseInt(ans.getText().substring(0,ind)); //System.out.println(operand);
			double val =1;
			for(int i=1; i<=operand; i++) val*=i;
			ans.setText(""+val);
			factor=0;
			}
			else if(per==1) {//System.out.println(ans.getText());
			int ind = ans.getText().indexOf('%');
			double operand = Double.parseDouble(ans.getText().substring(0,ind)); //System.out.println(operand);
			double val = (double)operand/100;
			ans.setText(""+val);
			per=0;
			}
			sixOp = 0;
			}
			
			else{//사칙연산인 경우
			double d = calculator(ans.getText());
			ans.setText(""+d);
			}}
		else if(clicked_button.equals("√")) {
			ans.setText(clicked_button);
			root = 1;
			sixOp=1;
		}
		else if(clicked_button.equals("log")) {
			ans.setText(clicked_button);
			logv = 1;
			sixOp=1;
		}
		else if(clicked_button.equals("Ln")) {
			ans.setText(clicked_button);
			lnv = 1;
			sixOp=1;
		}
		else if(clicked_button.equals("%")) {
			ans.setText(ans.getText()+clicked_button);
			per = 1;
			sixOp=1;
		}
		else if(clicked_button.equals("+")||clicked_button.equals("-")||clicked_button.equals("x")||clicked_button.equals("÷")){
			if(ans.getText().substring(ans.getText().length()-1).equals(")")||ans.getText().substring(ans.getText().length()-1).equals("!")||ans.getText().substring(ans.getText().length()-1).equals("0")||ans.getText().substring(ans.getText().length()-1).equals("1")||ans.getText().substring(ans.getText().length()-1).equals("2")||ans.getText().substring(ans.getText().length()-1).equals("3")||ans.getText().substring(ans.getText().length()-1).equals("4")||
					ans.getText().substring(ans.getText().length()-1).equals("5")||ans.getText().substring(ans.getText().length()-1).equals("6")||ans.getText().substring(ans.getText().length()-1).equals("7")||ans.getText().substring(ans.getText().length()-1).equals("8")||ans.getText().substring(ans.getText().length()-1).equals("9"))
			{ans.setText(ans.getText()+clicked_button);} //앞에 숫자가 올 때 연산자 붙이기
			else
				{ans.setText(ans.getText().substring(0, ans.getText().length()-1)+clicked_button); }//앞에 연산자가 올 때 그 연산자 지우고 입력 받은 연산자 넣기		
		}
		else if(clicked_button.equals("^")) {//^연산자의 경우 앞에 다른 연산자가 오면 ^을 대신 넣고, ^이나 숫자가 오면 그대로 추가한다.
			if(ans.getText().substring(ans.getText().length()-1).equals("+")||ans.getText().substring(ans.getText().length()-1).equals("-")||ans.getText().substring(ans.getText().length()-1).equals("x")||ans.getText().substring(ans.getText().length()-1).equals("÷")||ans.getText().substring(ans.getText().length()-1).equals("%")||ans.getText().substring(ans.getText().length()-1).equals("^"))
				ans.setText(ans.getText().substring(0, ans.getText().length()-1)+clicked_button);
			else
				ans.setText(ans.getText()+clicked_button);
			power = 1;
			sixOp=1;
		}
		else if(clicked_button.equals("!")) {//!연산자의 경우 앞에 다른 연산자가 오면 !을 대신 넣고, !이나 숫자가 오면 그대로 추가한다.
			if(ans.getText().substring(ans.getText().length()-1).equals("+")||ans.getText().substring(ans.getText().length()-1).equals("-")||ans.getText().substring(ans.getText().length()-1).equals("x")||ans.getText().substring(ans.getText().length()-1).equals("÷")||ans.getText().substring(ans.getText().length()-1).equals("%")||ans.getText().substring(ans.getText().length()-1).equals("^"))
				ans.setText(ans.getText().substring(0, ans.getText().length()-1)+clicked_button);
			else
				ans.setText(ans.getText()+clicked_button);
			factor = 1;
			sixOp=1;
		}
		else{//숫자 버튼 누른 경우
			if(ans.getText().equals("0")) ans.setText(clicked_button);
			else ans.setText(ans.getText()+clicked_button);
		}
	}
	
	public int getPriority(char token) {//연산자 우선순위를 return하는 함수.
		int rv=-1;
		switch(token) {
		case 'x': rv = 9; break;
		case '÷': rv = 9; break;
		case '+': rv = 8; break;
		case '-': rv = 8; break;
		}
		return rv;
	}
	
	public String infixToPostfix(String text) {
		String postfix = "";
		int set = 0;
		for(int i=0; i<text.length(); i++) {
			char token = text.charAt(i);
			if(Character.isDigit(token)||token=='.') {//숫자인 경우 바로 postfix문자열에 추가
				postfix+=token;
				set = 1;
			}
			else if(token=='(') {
				if(set==1) postfix+=" "; //이 코드는 숫자들 연속해서 postfix에 들어올 때 구분해주기 위함. 숫자 뒤에 공백을 추가
				set = 0;
				opStack.push(token);
			}
			else if(token==')') {
				if(set==1) postfix+=" ";
				set = 0;
				while(!opStack.empty()&&opStack.peek()!='(') {
					postfix+=opStack.peek();
					opStack.pop();
				}
				opStack.pop();
			}
			else {
				if(set==1) postfix+=" ";
				set = 0;
				while(!opStack.empty()&&(getPriority(token)<=getPriority(opStack.peek()))) {
					postfix+=opStack.peek();
					opStack.pop();
				}
				opStack.push(token);
			}
		}
		while(!opStack.empty()) {
			if(set==1) postfix+=" ";
			set = 0;
			postfix+=opStack.peek();
			opStack.pop();
		}
		//System.out.println(postfix);
		return postfix;
		}
	public double calculator(String text) {
		String postfix = infixToPostfix(text);
		int i =0; double op1; double op2; String op = "";
		char cur=' ';
		while(i<postfix.length()) { //postfix문자열을 앞에서부터 훑으면서
			cur = postfix.charAt(i);
			if(cur==' ') {i++; continue;}
			else if(Character.isDigit(cur)) {//숫자면 stack에 넣기 
				//공백이나 기호 나올 때까지 수를 모은다.
				while(true) {
					op+=cur;
					i++;
					cur = postfix.charAt(i);
					if(cur==' ') break;
				}
				//stack에 숫자 추가
				double ins = Double.parseDouble(op); //System.out.println(ins);
				numStack.push(ins);
				op=""; continue;
			}
			else {//숫자나 공백이 아닌, 연산자라면
				op2 = numStack.pop(); //stack에 넣었던 피연사자 꺼내기 
				//System.out.println("op2는 "+op2);
			    op1 = numStack.pop();
			    //System.out.println("op1은 "+op1);
				switch(cur) {
				case '+': numStack.push(op1+op2); break;
				case '-': numStack.push(op1-op2); break;
				case 'x': numStack.push(op1*op2); break;
				case '÷': numStack.push(op1/op2); break;
				}
				
			}
			i++;
		}
		//System.out.println("답은 :"+numStack.peek());
		return numStack.peek();
	}
	public static void main(String args[]) {
		Calculator_20191251 cal = new Calculator_20191251();
	}
	
}
