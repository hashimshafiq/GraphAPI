import java.sql.*;
import javax.swing.*;

public class dbConnection {
	
	Connection conn = null;
	
	public static Connection dbConnector()
	{
		try{
			Class.forName("org.sqlite.JDBC");
			//Connection conn = DriverManager.getConnection("jdbc:sqlite:\\\\londonfileserver001\\common\\desktop support\\Request.sqlite");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Hashim\\Desktop\\shami\\Request.sqlite");
			return conn;
			
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	
	

		
	}
	

	

}

