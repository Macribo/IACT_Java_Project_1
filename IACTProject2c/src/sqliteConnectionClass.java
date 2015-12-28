import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class sqliteConnectionClass {
	Connection conn = null;
	static int con = 0; // prevents multiple "Connection OK!" dialogs

	public static Connection dbConnector() {
		int conSeen = con;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:C:\\Program Files\\Products.sqlite");

			if (con == 0) {
				JOptionPane.showMessageDialog(null, "Connection OK!");
				con++;
				return conn;
			}

			return conn;

		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Cannot connect!  Please ensure the file Products.sqlite is in the correct directory: C://Program Files");
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}