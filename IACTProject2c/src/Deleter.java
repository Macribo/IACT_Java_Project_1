import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
//seperate class for delete functionality.

public class Deleter extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	ProductTable dTable = new ProductTable();
	ProductInfo inf = new ProductInfo();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Deleter frame = new Deleter();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	Connection connection = null;

	public Deleter() {
		connection = sqliteConnectionClass.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDeleteARecord = new JLabel("Delete? ");
		lblDeleteARecord.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteARecord.setVerticalAlignment(SwingConstants.TOP);
		lblDeleteARecord.setBounds(137, 24, 106, 33);
		contentPane.add(lblDeleteARecord);

		JLabel lblEnterTheProduct = new JLabel(
				" Enter the product ID (as Integer)");
		lblEnterTheProduct.setBounds(71, 48, 251, 42);
		contentPane.add(lblEnterTheProduct);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "DELETE FROM Products WHERE ProductID = '"
							+ textField.getText() + "' ";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
				

					JOptionPane.showMessageDialog(null, "Deleted!");

					pst.close();
					inf.refreshTable();
					
/*					^don't know why this doesn't refresh.
 * String bQuery = "SELECT EID,Name,Surname,Age from EmployeeInfoTable";
					PreparedStatement bPst = connection.prepareStatement(bQuery);
					ResultSet rs = bPst.executeQuery();
					dTable.table.setModel(DbUtils.resultSetToTableModel(rs));
					*/
					
					inf.fillComboBox();
					xit();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		btnDelete.setBounds(71, 191, 117, 25);
		contentPane.add(btnDelete);

		textField = new JTextField();
		textField.setBounds(182, 115, 47, 19);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				xit();
			}
		});
		btnCancel.setBounds(269, 191, 117, 25);
		contentPane.add(btnCancel);
	}

	public void xit() {
		super.dispose();
	}
}
