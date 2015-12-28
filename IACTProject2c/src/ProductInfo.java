import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

public class ProductInfo extends JFrame {

	public static String toTitleCase(String givenString) {
		String[] arr = givenString.split(" ");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < arr.length; i++) {
			sb.append(Character.toUpperCase(arr[i].charAt(0)))
					.append(arr[i].substring(1)).append(" ");
		}
		return sb.toString().trim();
	}

	ProductTable dTable = new ProductTable();
	private JPanel contentPane;
	private JComboBox productSelector;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductInfo frame = new ProductInfo();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		});
	}

	Connection connection = null;
	private JTextField pIDTextField;
	private JTextField qISTextField;
	private JTextField prodNameTextField;
	private JTextField priceTextField;
	int hOrS = 0;// setsup one button to toggle table visiblity
	private JTextField descriptionTextField;
	private JTextField searchTextField;

	public void refreshTable() {

		try {
			String query = "SELECT ProductID,ProductName,ProductPrice,QuantityInStock,ProductDescription from Products";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			dTable.table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fillComboBox() {

		try {
			productSelector.removeAllItems();
			String query = "SELECT * FROM Products";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				productSelector.addItem(rs.getString("ProductName"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public ProductInfo() {

		connection = sqliteConnectionClass.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 507, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblnd = new JLabel("Data Access Window");
		lblnd.setFont(new Font("Dialog", Font.BOLD, 15));
		lblnd.setBounds(161, 12, 220, 15);
		contentPane.add(lblnd);

		JButton btnHideShowData = new JButton("Hide/Show Data");
		btnHideShowData
				.setToolTipText("Click here to hide or show Database entries.");
		btnHideShowData.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (dTable.isVisible()) {
					dTable.setVisible(false);

				} else {
					try {
						dTable.setVisible(true);
						String query = "select * from Products";
						PreparedStatement pst = connection
								.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						dTable.table.setModel(DbUtils.resultSetToTableModel(rs));
					}

					catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		});
		btnHideShowData.setBounds(318, 400, 157, 25);
		contentPane.add(btnHideShowData);

		JLabel lblProdID = new JLabel("Product ID");
		lblProdID.setBounds(84, 102, 133, 15);
		contentPane.add(lblProdID);

		JLabel lblProdName = new JLabel("Product Name");
		lblProdName.setBounds(84, 129, 133, 15);
		contentPane.add(lblProdName);

		JLabel lblProdPrice = new JLabel("Product Price");
		lblProdPrice.setBounds(84, 158, 133, 15);
		contentPane.add(lblProdPrice);

		JLabel lblQtyIS = new JLabel("Quantity In Stock");
		lblQtyIS.setBounds(84, 186, 133, 15);
		contentPane.add(lblQtyIS);

		pIDTextField = new JTextField();
		pIDTextField.setBounds(246, 98, 60, 19);
		contentPane.add(pIDTextField);
		pIDTextField.setColumns(10);

		qISTextField = new JTextField();
		qISTextField.setBounds(246, 184, 60, 19);
		contentPane.add(qISTextField);
		qISTextField.setColumns(10);

		prodNameTextField = new JTextField();
		prodNameTextField.setBounds(246, 127, 114, 19);
		contentPane.add(prodNameTextField);
		prodNameTextField.setColumns(10);

		priceTextField = new JTextField();
		priceTextField.setBounds(246, 156, 60, 19);
		contentPane.add(priceTextField);
		priceTextField.setColumns(10);

		JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Click SAVE to commit the fields to the Database.");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String prodN = prodNameTextField.getText();
				toTitleCase(prodN);
				try {
					String query = "Insert into Products(ProductID,ProductName,ProductPrice,QuantityInStock,ProductDescription) VALUES (?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, pIDTextField.getText());
					pst.setString(2, prodN);
					pst.setString(3, priceTextField.getText());
					pst.setString(4, qISTextField.getText());
					pst.setString(5, descriptionTextField.getText());

					pst.execute();

					JOptionPane.showMessageDialog(null, "Data Saved");
					pst.close();
					refreshTable();
					fillComboBox();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		btnSave.setBounds(72, 400, 117, 25);
		contentPane.add(btnSave);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setToolTipText("Click here to update an existing entry.");
		btnUpdate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				;

				try {

					String prodN = prodNameTextField.getText();
					toTitleCase(prodN);

					String query = "UPDATE Products set ProductID = '"
							+ pIDTextField.getText() + "' ,ProductName ='"
							+ prodN + "',ProductPrice = '"
							+ priceTextField.getText()
							+ "' ,QuantityInStock = '" + qISTextField.getText()
							+ "' ,ProductDescription = '"

							+ descriptionTextField.getText()
							+ "' WHERE ProductID = '" + pIDTextField.getText()
							+ "'";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Data Updated");
					pst.close();
					refreshTable();
					fillComboBox();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		btnUpdate.setBounds(201, 400, 105, 25);
		contentPane.add(btnUpdate);

		JButton btnDelete = new JButton("Delete");

		btnDelete
				.setToolTipText("Click here to delete currently displayed entry.");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Deleter del = new Deleter();
				del.setVisible(true);

			}
		});
		btnDelete.setBackground(Color.PINK);
		btnDelete.setBounds(346, 351, 117, 25);
		contentPane.add(btnDelete);

		productSelector = new JComboBox();
		productSelector.setBackground(new Color(152, 251, 152));
		productSelector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					String query = "Select * from Products Where ProductName = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, (String) productSelector.getSelectedItem());
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						// get values from seleted item

						pIDTextField.setText(rs.getString("ProductID"));

						prodNameTextField.setText(rs.getString("ProductName"));

						priceTextField.setText(rs.getString("ProductPrice"));

						qISTextField.setText(rs.getString("QuantityInStock"));

						descriptionTextField.setText(rs
								.getString("ProductDescription"));

					}

					pst.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		productSelector.setBounds(171, 39, 133, 25);
		contentPane.add(productSelector);

		JLabel lblProductDescription = new JLabel("Product Description");
		lblProductDescription.setBounds(84, 215, 157, 15);
		contentPane.add(lblProductDescription);

		descriptionTextField = new JTextField();
		descriptionTextField.setBounds(246, 213, 229, 19);
		contentPane.add(descriptionTextField);
		descriptionTextField.setColumns(10);

		JButton btnCreateNew = new JButton("Create New");
		btnCreateNew
				.setToolTipText("Click here to clear fields for new entry.");
		btnCreateNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					pIDTextField.setText("");
					prodNameTextField.setText("");
					priceTextField.setText("");
					qISTextField.setText("");
					descriptionTextField.setText("");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnCreateNew.setBounds(72, 351, 117, 25);
		contentPane.add(btnCreateNew);

		Label outerFind = new Label("Find A Product");
		outerFind.setFont(new Font("Dialog", Font.BOLD, 12));
		outerFind.setBounds(84, 284, 105, 21);
		contentPane.add(outerFind);

		Panel panel = new Panel();
		panel.setBounds(75, 294, 400, 45);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel innerFind = new JLabel("Search for Product");
		innerFind.setBounds(0, 17, 147, 15);
		panel.add(innerFind);

		searchTextField = new JTextField();
		searchTextField
				.setToolTipText("enter search term here.('Show Data' to view results)");
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				String prodN = searchTextField.getText();
				toTitleCase(prodN);

				try {
					String query = "SELECT * FROM Products where ProductName = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, prodN);
					ResultSet rs = pst.executeQuery();

					dTable.table.setModel(DbUtils.resultSetToTableModel(rs));

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		searchTextField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
			}
		});
		searchTextField.setColumns(10);
		searchTextField.setBounds(156, 15, 114, 20);
		panel.add(searchTextField);

		/*
		 * JButton button = new JButton("Search"); button.addActionListener(new
		 * ActionListener() { public void actionPerformed(ActionEvent arg0) {
		 * 
		 * //if (searchTextField.getText()!=null) { String search =
		 * searchTextField.getText(); toTitleCase(search);
		 * 
		 * try { String query = "select * from Products WHERE ProductName =?";
		 * PreparedStatement pst = connection.prepareStatement(query);
		 * pst.setString(1, search); ResultSet rs = pst.executeQuery(); //
		 * refreshTable(); pst.close(); } catch (Exception e) {
		 * e.printStackTrace(); } //}else{JOptionPane.showMessageDialog(null,
		 * "No text-input found. Please try again.");}
		 * 
		 * refreshTable(); dTable.setVisible(true); } }); button.setBounds(300,
		 * 12, 85, 25); panel.add(button);
		 */
		refreshTable();
		fillComboBox();
	}
}
