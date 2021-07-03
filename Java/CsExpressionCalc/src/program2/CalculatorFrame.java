package program2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;


@SuppressWarnings("serial")
class CalculatorFrame extends JFrame implements ActionListener  {
	JTextField jtfInfix = new JTextField(21); // for infix 
	JTextField jtfPostfix = new JTextField();  // for postix
	JTextField result = new JTextField("0");   // for result

	JButton[][] calcButton = new JButton[4][5];

	JPanel calcPanel = new JPanel();	
	JPanel topPanel = new JPanel();    


	public CalculatorFrame(){
		String[][] buttonText = 
				new String[][]{{"7","8","9","/","C"},{"4","5","6","*","B"},
			{"1","2","3","-","R"},{"0","(",")","+","="}};

			this.setTitle("CSC130 Calculator");
			this.setLayout(new BorderLayout(2,1));

			jtfInfix.setHorizontalAlignment(JTextField.RIGHT);
			jtfPostfix.setHorizontalAlignment(JTextField.RIGHT);
			result.setHorizontalAlignment(JTextField.RIGHT);
			jtfPostfix.setEnabled(false);
			result.setEnabled(false);
			//jtfInfix.setEditable(false); // hide text caret

			// set the font size to 34 for the text fields
			Font textFieldFont=new Font(jtfPostfix.getFont().getName(),jtfPostfix.getFont().getStyle(),24);
			jtfInfix.setFont(textFieldFont);
			jtfPostfix.setFont(textFieldFont);
			result.setFont(textFieldFont);

			topPanel.setLayout(new GridLayout(3,1));				
			topPanel.add(jtfInfix);		
			topPanel.add(jtfPostfix);
			topPanel.add(result);

			calcPanel.setLayout(new GridLayout(4,5,3,3));

			for (int i = 0; i < 4; i++) {
				for(int j = 0; j < 5; j++) {
					calcButton[i][j]= new JButton("" + buttonText[i][j]);
					calcButton[i][j].setForeground(Color.blue);
					calcButton[i][j].setFont(new Font("sansserif",Font.BOLD,42));
					calcButton[i][j].addActionListener(this);
					calcButton[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
					calcPanel.add(calcButton[i][j]);
				}
			}
			this.add(topPanel,BorderLayout.NORTH);
			this.add(calcPanel,BorderLayout.CENTER);
	}
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {				
				if(e.getSource() == calcButton[i][j]){
					// clear
					if(i==0 && j == 4){
						jtfInfix.setText(null);
						jtfPostfix.setText(null);
						result.setText("0");
					}
					// backspace
					else if(i==1 && j == 4){
						if(jtfInfix.getDocument().getLength()>0)
							try {
								jtfInfix.setText(jtfInfix.getText(0, jtfInfix.getDocument().getLength()-1));
							} catch (BadLocationException e1) {
								e1.printStackTrace();
							}

					}
					// number or operator
					else if(j<4){
						jtfInfix.setText(jtfInfix.getText()
								+ calcButton[i][j].getText());
					}
					// = button pressed
					else if(i==3&&j==4){
						// erase contents of the postfix textfield
						jtfPostfix.setText(null);  
						// update the postfix textfield with the String returned  
						jtfPostfix.setText(infixToPostfix());
						// update the result textfield with the result of the computation
						result.setText("" + calculate());
					}
				}
			}
		}
	}

	public String infixToPostfix(){		
		LinkedStack <String>l1 = new LinkedStack<String>();
		String expression = jtfInfix.getText();
		String delims = "*/-+() ";
		String token;
		String result = new String();
		StringTokenizer strToken = new StringTokenizer(expression, delims, true);
		while(strToken.hasMoreTokens()){
			token = strToken.nextToken();
			if (isInteger(token))
				result += token + " ";
			else if(token.equals("("))
				l1.push(token);
			else if(token.equals(")")) {
				while(!l1.peek().equals("(")) {
					result += l1.pop() + " ";
				}
				if(l1.peek().equals("("))
					l1.pop();
			}
			else if (prec(token) == 1) {
				if (l1.isEmpty())
					l1.push(token);
				else if(prec(l1.peek()) > 1) {
					result += l1.pop() + " ";
					l1.push(token);
				}
				else
					l1.push(token);
			}
			else if (prec(token) == 2) {
				if (l1.isEmpty())
					l1.push(token);
				else if(prec(l1.peek()) < 2) {
					l1.push(token);
				}
				else
					l1.push(token);
			}			
		}
		while(!l1.isEmpty()) {
			result += l1.pop() + " ";
		}
		return result;
	}

	public String calculate() {
		LinkedStack <Integer>l1 = new LinkedStack<Integer>();
		String expression = infixToPostfix();
		String delims = "*/-+ ";
		String token;
		String result = new String();
		int left, right;
		StringTokenizer strToken = new StringTokenizer(expression, delims, true);
		while(strToken.hasMoreTokens()){
			token = strToken.nextToken();
			if(isInteger(token))
				l1.push(Integer.parseInt(token));
			else if(prec(token) > 0) {
				right = l1.pop();
				left = l1.pop();
				if(token.equals("+"))
					l1.push(left+right);
				else if(token.equals("-"))
					l1.push(left-right);
				else if(token.equals("/"))
					l1.push(left/right);
				else if(token.equals("*"))
					l1.push(left*right);
			}
		}
		return result + l1.pop();
	}

	static boolean isInteger( String input ) {
		try
		{
			Integer.parseInt( input );
			return true;
		}
		catch( RuntimeException Exception )
		{
			return false;
		}
	}

	static int prec(String x) {
		int num = 0;
		if (x.equals("+") || x.equals("-"))
			num = 1;
		if (x.equals("/") || x.equals("*"))
			num = 2;
		return num;
	}

	static final int MAX_WIDTH = 398, MAX_HEIGHT = 440;

	public static void main(String arg[]){
		JFrame frame = new CalculatorFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(MAX_WIDTH,MAX_HEIGHT);	
		frame.setBackground(Color.white);		
		frame.setResizable(false);				
		frame.setVisible(true);
	}
}