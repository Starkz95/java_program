package server;


import javax.print.DocFlavor;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {

    Connection ct = null;
    PreparedStatement ps = null;
    ResultSet rs = null;


    /**
     * constructor
     */
    public DBHelper() {
        try (FileInputStream infoReader = new FileInputStream("ServerINFO.properties")) {
            String driver;
            String url;
            String username;
            String password;
            //connecting to the database
            Properties properties = new Properties();
            properties.load(infoReader);

            driver = (String) properties.getProperty("driver");
            url = (String) properties.getProperty("URL");
            username = (String) properties.getProperty("username");
            password = (String) properties.getProperty("password");

            Class.forName(driver);
            ct = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * close the connection
     */
    public void close() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (ct != null) ct.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * SQL execution
     *
     * @param sql
     * @param paras
     * @return
     */
    public boolean exeUpdate(String sql, String[] paras) {
        boolean b = true;
        try {
            ps = ct.prepareStatement(sql);
            for (int i = 0; i < paras.length; i++) {
                ps.setString(i + 1, paras[i]);
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
     *
     * @param sql
     * @param paras
     * @return
     */
    public ResultSet query(String sql, String[] paras) {
        try {
            ps = ct.prepareStatement(sql);
            for (int i = 0; i < paras.length; i++) {
                ps.setString(i + 1, paras[i]);
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
     *
     * @param username
     * @param password
     * @return
     */
    public boolean checkUser(String username, String password) {
        boolean b = false;
        PreparedStatement psmt = null;
        try {

            String sql = "select username,password from account where username=? and password=?";
            psmt = ct.prepareStatement(sql);
            psmt.setString(1, username);
            psmt.setString(2, password);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                b = true;
            }
            rs.close();
            psmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (psmt != null) psmt.close();
            } catch (SQLException se2) {
            }

        }
        return b;
    }

    /**
     * check the user if the username has existed when he is registering
     *
     * @param username
     * @return
     */
    public boolean checkNewUser(String username) {
        boolean b = false;
        PreparedStatement psmt = null;
        try {

            String sql = "select username from account where username=?";
            psmt = ct.prepareStatement(sql);
            psmt.setString(1, username);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                b = true;
            }
            rs.close();
            psmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (psmt != null) psmt.close();
            } catch (SQLException se2) {
            }

        }
        return b;
    }

    /**
     * insert the user to the database if a user successfully registers
     *
     * @param username
     * @param password
     */
    public void insertUser(String username, String password, String sex, String email, String age, String address) {


        PreparedStatement psmt = null;
        try {

            String sql = "insert into account (username,password,sex,email,age,address) values(?, ?, ?, ?, ?, ?)";
            psmt = ct.prepareStatement(sql);
            psmt.setString(1, username);
            psmt.setString(2, password);
            psmt.setString(3, sex);
            psmt.setString(4, email);
            psmt.setString(5, age);
            psmt.setString(6, address);
            psmt.execute();

        } catch (SQLException se) {

            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            try {
                if (psmt != null) psmt.close();
            } catch (SQLException se2) {
            }

        }
    }

    public boolean checkNewGroup(String groupName) {
        boolean b = false;
        PreparedStatement psmt = null;
        try {

            String sql = "select groupname from group where groupname=?";
            psmt = ct.prepareStatement(sql);
            psmt.setString(1, groupName);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                b = true;
            }
            rs.close();
            psmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (psmt != null) psmt.close();
            } catch (SQLException se2) {
            }

        }
        return b;
    }

    public void insertGroup(String groupName, String groupMembers) {

        PreparedStatement psmt = null;
        try {

            String sql = "insert into group (groupname, groupmembers) values(?, ?)";
            psmt = ct.prepareStatement(sql);
            psmt.setString(1, groupName);
            psmt.setString(2, groupMembers);
            psmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getGroupMembers(String groupName) {
        ResultSet resultSet = null;
        PreparedStatement psmt;
        try {
            String sql = "select groupmembers from group where groupname = ?";
            psmt = ct.prepareStatement(sql);
            psmt.setString(1, groupName);
            resultSet = psmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * insert messages in history table
     *
     * @param Sender
     * @param Receiver
     * @param Message
     * @param date
     */
    public void insertHistory(String Sender, String Receiver, String Message, String date) {


        PreparedStatement psmt = null;
        try {

            String sql = "insert into history (sender,receiver,message,date) values(?, ?, ?,?)";
            //String sql = "insert into QQuser (username,message) values(?, ?)";
            psmt = ct.prepareStatement(sql);
            psmt.setString(1, Sender);
            psmt.setString(2, Receiver);
            psmt.setString(3, Message);
            psmt.setString(4, date);
            psmt.execute();

        } catch (SQLException se) {

            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            try {
                if (psmt != null) psmt.close();
            } catch (SQLException se2) {
            }

        }
    }

    /**
     * select private chat history of two users
     *
     * @param Sender
     * @param Receiver
     * @return
     */
    public String getPrivateHistory(String Sender, String Receiver) {
        PreparedStatement psmt = null;
        String res = "";
        try {

            String sql = "select message from history where (sender=? and receiver=?) or (sender=? and receiver=?) order by date asc";
            psmt = ct.prepareStatement(sql);
            psmt.setString(1, Sender);
            psmt.setString(2, Receiver);
            psmt.setString(3, Receiver);
            psmt.setString(4, Sender);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                res = res + rs.getString("message") + "\n";
            }
            rs.close();
            psmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (psmt != null) psmt.close();
            } catch (SQLException se2) {
            }

        }
        return res;
    }

    /**
     * select chat history of public
     *
     * @return
     */
    public String getPublicHistory() {
        PreparedStatement psmt = null;
        String res = "";
        try {

            String sql = "select message from history where receiver=? order by date asc";
            psmt = ct.prepareStatement(sql);
            psmt.setString(1, "Public");
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                res = res + rs.getString("message") + "\n";
            }
            rs.close();
            psmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (psmt != null) psmt.close();
            } catch (SQLException se2) {
            }

        }
        return res;
    }

    /**
     * select a user's profile
     *
     * @param currentName
     * @return
     */
    public ResultSet userProfile(String currentName) {
        ResultSet result = null;
        PreparedStatement psmt = null;
        try {
            String sql = "select * from account where username=?";
            psmt = ct.prepareStatement(sql);
            psmt.setString(1, currentName);
            result = psmt.executeQuery();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }


    public ResultSet groupProfile(String groupname) {
        ResultSet result = null;
        PreparedStatement psmt = null;
        try {
            String sql = "select * from group where groupname=?";
            psmt = ct.prepareStatement(sql);
            psmt.setString(1, groupname);
            result = psmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
