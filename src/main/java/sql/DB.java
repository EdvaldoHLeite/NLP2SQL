package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
   static final String DB_URL = "jdbc:postgresql://localhost:5432/nlp";
   static final String USER = "nlp";
   static final String PASS = "123456";
			
	public static void updateDatabase(String sql) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			// abrindo uma conexao
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();	
			
			stmt.execute(sql);
		} catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){ 
			e.printStackTrace();
		}
	}
}
