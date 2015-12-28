
/*
From the IACT Project Brief:
"Create a Java project with a GUI Interface Similar to the following..."
"...The Database should contain a Table called tblProducts with the following fields:
ProductID: number
ProductName: varchar2(30)
Price: number
QtyInStock: number
Decsription: varchar2(50)
The Database should also use a Sequence called ProductSequence to keep track of the ProductID.
The program should load the database contents into an ArrayList of Product Objects..."

*/


import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


//main login page.  Tries to connect to db after password verified.
public class MainLogin {//verify password
	static boolean authenticate(String uName, String pWord) {
		if (uName.equalsIgnoreCase("student")
				&& pWord.equalsIgnoreCase("password")) {
			return true;
		}
		return false;
	}

	private JFrame frame;
	int counter = 0;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainLogin window = new MainLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JPasswordField passwordField;
	private JTextField userNameTextField;

	public MainLogin() {
		initialize();

	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(218, 86, 114, 15);
		frame.getContentPane().add(lblUserName);

		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBounds(218, 158, 70, 15);
		frame.getContentPane().add(lblNewLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(302, 158, 114, 19);
		frame.getContentPane().add(passwordField);

		userNameTextField = new JTextField();
		userNameTextField.setBounds(302, 86, 114, 19);
		frame.getContentPane().add(userNameTextField);
		userNameTextField.setColumns(10);

		JButton btnConnect = new JButton("Connect");

		final JLabel lblSecurityImg = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("img/si.png"))
				.getImage();
		lblSecurityImg.setIcon(new ImageIcon(img));
		lblSecurityImg.setBounds(12, 37, 206, 171);

		frame.getContentPane().add(lblSecurityImg);

		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = passwordField.getText();
				String userName = userNameTextField.getText();
				if (authenticate(userName, password)) {
					Image img = new ImageIcon(this.getClass().getResource(
							"img/si2.png")).getImage();
					lblSecurityImg.setIcon(new ImageIcon(img));
					JOptionPane.showMessageDialog(null,
							"username & password correct!");
					frame.dispose();
					ProductInfo emplInfo = new ProductInfo();
					emplInfo.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(null,
							"Incorrect password/username");
				}

			}
		});
		btnConnect.setBounds(299, 209, 117, 25);
		frame.getContentPane().add(btnConnect);

	}
}
