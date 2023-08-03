import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class libSQL {
	public Object connect(String host, String database, String username, String password) {
		
		try {			
			Class.forName("com.mysql.jdbc.Driver");
			
			String c = "jdbc:mysql:"+host+"/"+database+"?user="+username+"&password="+password;
			return DriverManager.getConnection(c);
		} catch (SQLException e) {
			if(e.getErrorCode() == 0)
				System.err.println(e.getMessage());
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public Object query(Connection connect, String query){
		
		try {
			Statement statement = connect.createStatement();
			return statement.executeQuery(query);
		} catch (SQLException e) {
			return "false";
		}
	}
}
