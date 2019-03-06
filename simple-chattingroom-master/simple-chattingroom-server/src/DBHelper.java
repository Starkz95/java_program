

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {
	
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/qqdb?useUnicode=true&characterEncoding=utf-8";
	private String username = "root";
	private String password = "root";
	
	public DBHelper() {
		try {
			//��������
			Class.forName(driver);
			ct = DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//�ر���Դ
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
		
		//�Ѷ����ݿ����ɾ��
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

		//�����ݿ�Ĳ�ѯ
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
		
		public boolean checkUser(String username,String password)
		{
			boolean b = false;
			PreparedStatement psmt = null;
			try{
				
				//��֯sql���Ͳ����б�
				String sql = "select username,password from QQuser where username=? and password=?";
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
	            // ���� JDBC ����
	            se.printStackTrace();
	        }catch(Exception e){
	            // ���� Class.forName ����
	            e.printStackTrace();
	        }finally{
	            // �ر���Դ
	            try{
	                if(psmt!=null) psmt.close();
	            }catch(SQLException se2){
	            }// ʲô������
			
		}
			return b;
		}
		public boolean checknewUser(String username)
		{
			boolean b = false;
			PreparedStatement psmt = null;
			try{
				
				//��֯sql���Ͳ����б�
				String sql = "select username from QQuser where username=?";
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
	            // ���� JDBC ����
	            se.printStackTrace();
	        }catch(Exception e){
	            // ���� Class.forName ����
	            e.printStackTrace();
	        }finally{
	            // �ر���Դ
	            try{
	                if(psmt!=null) psmt.close();
	            }catch(SQLException se2){
	            }// ʲô������
			
		}
			return b;
		}
		public void insertUser(String username,String password) {
			
			
			PreparedStatement psmt = null;
			try{
				
				//��֯sql���Ͳ����б�
				String sql = "insert into QQuser (username,password) values(?, ?)";
				psmt = ct.prepareStatement(sql);
				psmt.setString(1, username);
				psmt.setString(2, password);
				psmt.execute();
				
			}
			catch(SQLException se){
	            // ���� JDBC ����
	            se.printStackTrace();
	        }catch(Exception e){
	            // ���� Class.forName ����
	            e.printStackTrace();
	        }finally{
	            // �ر���Դ
	            try{
	                if(psmt!=null) psmt.close();
	            }catch(SQLException se2){
	            }// ʲô������
			
		}
		}
	
	

}
