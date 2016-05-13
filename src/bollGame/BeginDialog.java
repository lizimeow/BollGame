package bollGame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class BeginDialog extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static int SumNumber = 0;
	private JTextField jtf ;
	private JButton btn;
	private JLabel lable ;
	
	public BeginDialog (){
		this.setSize(350, 180);  
        this.setDefaultCloseOperation(3); 
        this.setLocationRelativeTo(null);  //����
        this.setAlwaysOnTop(true);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25)); 
		lable = new JLabel("����С��������"); 
		lable.setForeground(Color.black);  
		lable.setFont(new Font("����", Font.PLAIN, 14));
		this.add(lable);
		jtf= new JTextField(10);

		this.add(jtf);
		btn = new JButton("��ʼ��Ϸ");
		btn.setFont(new Font("����", Font.PLAIN, 18));
		btn.setBackground(Color.WHITE);	
		this.add(btn);
		btn.addActionListener(this);
		
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == btn){
			String str = jtf.getText();
			if (!Pattern.compile("-?\\d+").matcher(str).matches() || Integer.valueOf(str) == 0){
				JOptionPane.showMessageDialog(this, "���벻�Ϸ���");
				return;
			}
			SumNumber = Integer.parseInt(jtf.getText());
			try {
				new BallFrame();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.dispose();
		}
	}

	public static void main(String args[]) throws IOException {
		new BeginDialog();
	}
	
}
