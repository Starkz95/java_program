package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ClientThread extends Thread {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String userName;
    private ArrayList<ClientThread> clients;
    private ServerUI SUI;
    private DBHelper dbHelper;

    /**
     * constructor
     *
     * @param socket
     * @param clients
     * @param SUI
     */
    public ClientThread(Socket socket, ArrayList<ClientThread> clients, ServerUI SUI) {
        this.socket = socket;
        this.clients = clients;
        this.SUI = SUI;
        this.dbHelper = new DBHelper();
        try {

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());

            String inf = reader.readLine();

            String[] sp = inf.split("@");
            if (sp.length != 2 || !sp[0].equals("Logon")) {
                return;
            }
            userName = sp[1];
            // send message to clients
            writer.println("Public@Server@ALL@" + userName + " successfully connecting to the server!");
            writer.flush();

            // send message to client how many users are online
            if (clients.size() > 0) {
                String allUsers = "";
                for (int i = clients.size() - 1; i >= 0; i--) {
                    allUsers += (clients.get(i).getUserName() + "#");
                }
                writer.println("UserList@" + allUsers);
                writer.flush();
            }

            // Send the message that who is online to all online users
            for (int i = clients.size() - 1; i >= 0; i--) {
                // clients.get(i).getWriter().println("Add@" + clients.get(i).getUserName());
                clients.get(i).getWriter().println("Add@" + userName);
                clients.get(i).getWriter().flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * receive different types of messages from client
     */
    public void run() {
        String message = null;
        while (true) {
            try {
                message = reader.readLine();
                String[] s = message.split("@");
                if (s[0].equals("Logout"))// when the user is logout
                {
                    SUI.getContentArea().append(this.getUserName() + " offline!\r\n");
                    reader.close();
                    writer.close();
                    socket.close();

                    // Send the message that who is offline to all online users
                    for (int i = clients.size() - 1; i >= 0; i--) {
                        clients.get(i).getWriter().println("Delete@" + userName);
                        clients.get(i).getWriter().flush();
                    }

                    SUI.getListModel().removeElement(userName);// refresh the userlist

                    // stop the thread for this user
                    for (int i = clients.size() - 1; i >= 0; i--) {
                        if (clients.get(i).getUserName().equals(userName)) {
                            ClientThread temp = clients.get(i);
                            clients.remove(i);
                            temp.stop();
                            return;
                        }
                    }
                } else if (s[0].equals("Profile")) {
                    ResultSet rs;
                    String profile;
                    if (s[1].contains("[Group]")) {
                        rs = dbHelper.groupProfile(s[1].substring(7));
                        if (rs.next()) {
                            String groupname = rs.getString("groupname");
                            String groupmembers = rs.getString("groupmembers");
                            profile = "Profile@" + "[Group]" + groupname + "#" + groupmembers;
                            getWriter().println(profile);
                        }
                    } else {
                        rs = dbHelper.userProfile(s[1]);
                        if (rs.next()) {
                            String user = rs.getString("username");
                            String sex = rs.getString("sex");
                            String age = rs.getString("age");
                            String email = rs.getString("email");
                            String address = rs.getString("address");
                            profile = "Profile@" + user + "#" + sex + "#" + age + "#" + email + "#" + address;
                            getWriter().println(profile);
                        }
                    }
                    getWriter().flush();

                } else if (s[0].equals("CreatGroup")) {
                    boolean CreatGroup = dbHelper.checkNewGroup(s[1]);
                    if (!CreatGroup) {
                        String groupName = s[1];
                        String groupMemberString = s[2] + userName + "#";
                        String groupMem = groupMemberString;
                        ArrayList<String> groupMembers = new ArrayList<>();
                        dbHelper.insertGroup(groupName, groupMemberString);

                        while (groupMemberString.contains("#")) {
                            groupMembers.add(groupMemberString.substring(0, groupMemberString.indexOf("#")));
                            groupMemberString = groupMemberString.substring(groupMemberString.indexOf("#") + 1);
                        }

                        getWriter().println("CreatGroupSuccessful@");
                        getWriter().flush();
                        for (String groupMember :
                                groupMembers) {
                            for (int i = clients.size() - 1; i >= 0; i--) {
                                if (clients.get(i).getUserName().equals(groupMember)) {
                                    clients.get(i).getWriter().println("AddGroup@" + groupName);
                                    clients.get(i).getWriter().flush();
                                    clients.get(i).getWriter().println("InvitedToGroup@" + groupName + "@" + groupMem);
                                    clients.get(i).getWriter().flush();
                                }
                            }
                        }

                    } else {
                        getWriter().println("GroupNameToken@");
                        getWriter().flush();
                    }
                }
//				else if(s[0].equals("History")) {
//					if(s.length==2) {
//						String history=new DBHelper().getPublicHistory();
//						for (int i = clients.size() - 1; i >= 0; i--) {
//							if (clients.get(i).getUserName().equals(userName)) {
//								clients.get(i).getWriter().println("History@"+history);
//								clients.get(i).getWriter().flush();
//							}
//						}
//					}
//					else {
//						String history=new DBHelper().getPrivateHistory(s[1], s[2]);
//						for (int i = clients.size() - 1; i >= 0; i--) {
//							if (clients.get(i).getUserName().equals(userName)) {
//								clients.get(i).getWriter().println("History@"+history);
//								clients.get(i).getWriter().flush();
//							}
//						}
//					}
//				}
                else {
                    dispatcherMessage(message);
                }
            } catch (IOException e) {
                // e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * send the public messages to all online users, and send the private messages to corresponding users.
     *
     * @param message
     */
    public void dispatcherMessage(String message) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] sp = message.split("@");
        if (sp.length != 4) {
            return;
        }
        if (sp[0].equals("Public")) {// send the public messages

            String s = sp[1];// sender
            // String r = sp[2];// receiver
            String c = sp[3];// message
            dbHelper.insertHistory(s, "Public", df.format(new Date()) + "\n" + s + ":   " + c + "\r\n", df.format(new Date()));
            SUI.getContentArea().append("[Public]" + s + ": " + c + "\r\n");
            for (int i = clients.size() - 1; i >= 0; i--) {
                clients.get(i).getWriter().println(message);
                clients.get(i).getWriter().flush();
            }
        } else if (sp[0].equals("Private")) { // send the private messages
            String s = sp[1];// sender
            String r = sp[2];// receiver
            String c = sp[3];// message
            dbHelper.insertHistory(s, r, df.format(new Date()) + "\n" + s + ":   " + c + "\r\n", df.format(new Date()));
            SUI.getContentArea().append("[Private]" + s + " => " + r + ": " + c + "\r\n");
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i).getUserName().equals(r) || clients.get(i).getUserName().equals(s)) {
                    clients.get(i).getWriter().println(message);
                    clients.get(i).getWriter().flush();
                }
            }
        } else if (sp[0].equals("Group")) { // send the group messages
            String s = sp[1];// sender
            String r = sp[2];// receiver
            String c = sp[3];// message
            dbHelper.insertHistory(s, "[Group]" + r, df.format(new Date()) + "\n" + s + ":   " + c + "\r\n", df.format(new Date()));
            SUI.getContentArea().append("[Group]" + s + " => " + r + ": " + c + "\r\n");
            ResultSet resultSet = dbHelper.getGroupMembers(r);
            try {
                if (resultSet.next()) {
                    String[] groupMembers = resultSet.getString("groupmembers").split("#");
                    for (String groupMember :
                            groupMembers) {
                        for (int i = clients.size() - 1; i >= 0; i--) {
                            if (clients.get(i).getUserName().equals(groupMember)) {
                                clients.get(i).getWriter().println(message);
                                clients.get(i).getWriter().flush();
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public String getUserName() {
        return userName;
    }


}
