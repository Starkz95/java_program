package server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {
	
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	private String driver = "org.postgresql.Driver";
	private String url = "jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk:5432/group23";
	private String username = "group23";
	private String password = "0ov2qfds3j";
	
	/**
	 * constructor
	 */
	public DBHelper() {
		try {
			//connecting to the database
			Class.forName(driver);
			ct = DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * close the connection
	 */
		public void close()
		{
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * SQL execution
		 * @param sql
		 * @param paras
		 * @return
		 */
		public boolean exeUpdate(String sql,String []paras)
		{
			boolean b = true;
			try {
				ps = ct.prepareStatement(sql);
				for(int i = 0;i<paras.length;i++)
				{
					ps.setString(i+1, paras[i]);
				}
				ps.executeUpdate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				b = false;
				e.printStackTrace();
			}
			
			return b;
		}

		/**
		 * SQL execution
		 * @param sql
		 * @param paras
		 * @return
		 */
		public ResultSet query(String sql,String []paras)
		{
			try {
				ps = ct.prepareStatement(sql);
				for(int i = 0;i<paras.length;i++)
				{
					ps.setString(i+1, paras[i]);
				}
				rs = ps.executeQuery();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return rs;
		}
		
		/**
		 * check the user when he is login
		 * @param username
		 * @param password
		 * @return
		 */
		public boolean checkUser(String username,String password)
		{
			boolean b = false;
			PreparedStatement psmt = null;
			try{
				
				String sql = "select username,password from account where username=? and password=?";
				psmt = ct.prepareStatement(sql);
				psmt.setString(1, username);
				psmt.setString(2, password);
				ResultSet rs = psmt.executeQuery();
				if(rs.next())
				{
					b=true;
				}
				rs.close();
	            psmt.close();
			}
			catch(SQLException se){
	            se.printStackTrace();
	        }catch(Exception e){
	            e.printStackTrace();
	        }finally{
	            try{
	                if(psmt!=null) psmt.close();
	            }catch(SQLException se2){
	            }
			
		}
			return b;
		}
		
		/**
		 * check the user if the username has existed when he is registering
		 * @param username
		 * @return
		 */
		public boolean checknewUser(String username)
		{
			boolean b = false;
			PreparedStatement psmt = null;
			try{
				
				String sql = "select username from account where username=?";
				psmt = ct.prepareStatement(sql);
				psmt.setString(1, username);
				ResultSet rs = psmt.executeQuery();
				if(rs.next())
				{
					b=true;
				}
				rs.close();
	            psmt.close();
			}
			catch(SQLException se){
	            se.printStackTrace();
	        }catch(Exception e){
	            e.printStackTrace();
	        }finally{
	            
	            try{
	                if(psmt!=null) psmt.close();
	            }catch(SQLException se2){
	            }
			
		}
			return b;
		}
		
		/**
		 * insert the user to the database if a user successfully registers
		 * @param username
		 * @param password
		 */
		public void insertUser(String username,String password) {
			
			
			PreparedStatement psmt = null;
			try{
				
				String sql = "insert into account (username,password) values(?, ?)";
				psmt = ct.prepareStatement(sql);
				psmt.setString(1, username);
				psmt.setString(2, password);
				psmt.execute();
				
			}
			catch(SQLException se){
	        
	            se.printStackTrace();
	        }catch(Exception e){
	            
	            e.printStackTrace();
	        }finally{
	            
	            try{
	                if(psmt!=null) psmt.close();
	            }catch(SQLException se2){
	            }
			
		}
		}
	
	

}
