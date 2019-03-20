package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.*;

public class MessageThread extends Thread {
    private BufferedReader reader;
    private JTextArea textArea;
    private ClientChatUI CCUI;
    private Client client;

    /**
     * constructor
     *
     * @param reader
     * @param textArea
     * @param CCUI
     * @param client
     */
    public MessageThread(BufferedReader reader, JTextArea textArea, ClientChatUI CCUI, Client client) {
        this.reader = reader;
        this.textArea = textArea;
        this.CCUI = CCUI;
        this.client = client;

    }

    /**
     * when the server is closed
     *
     * @throws Exception
     */
    public synchronized void closeCon() {
        // remove the users
        CCUI.getUserListModel().removeAllElements();
        try {
            if (reader != null) {
                reader.close();
            }
            if (client.getWriter() != null) {
                client.getWriter().close();
            }
            if (client.getSocket() != null) {
                client.getSocket().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * receive different types of messages
     */
    public void run() {
        String message = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            message = reader.readLine();
            while (message != null) {

                System.out.println("[Client] " + message);
                String[] sp = message.split("@");

                if (sp[0].equals("CLOSE")) { //when the server is closed
                    textArea.append("The server is closed!\r\n");
                    closeCon(); //close the connection

                } else if (sp[0].equals("GroupNameToken")) {
                    JOptionPane.setDefaultLocale(Locale.ENGLISH);
                    JOptionPane.showMessageDialog(null, "Group name have been token!",
                            "", JOptionPane.ERROR_MESSAGE);
                } else if (sp[0].equals("CreatGroupSuccessful")) {
                    JOptionPane.setDefaultLocale(Locale.ENGLISH);
                    JOptionPane.showMessageDialog(null, "Creat Group Successful!",
                            "", JOptionPane.INFORMATION_MESSAGE);
                } else if (sp[0].equals("InvitedToGroup")) {

                } else if (sp[0].equals("Add")) { // add a user when he is online
                    String username = sp[1];
                    CCUI.getUserListModel().addElement(username);
                } else if (sp[0].equals("UserList")) { // the list of users
                    String[] usernames = sp[1].split("#");
                    for (String username : usernames) {
                        if (username.length() == 0)
                            continue;
                        CCUI.getUserListModel().addElement(username);
                    }
                } else if (sp[0].equals("AddGroup")) {
                    String groupName = sp[1];
                    CCUI.getGroupListModel().addElement(groupName);
                } else if (sp[0].equals("GroupList")) {// message for creating a group
                    String[] groupNames = sp[1].split("#");
                    for (String groupName : groupNames) {
                        if (groupName.length() == 0)
                            continue;
                        CCUI.getGroupListModel().addElement(groupName);
                    }
                } else if (sp[0].equals("Delete")) { // delete a user from the list when he is logout
                    String username = sp[1];
                    CCUI.getUserListModel().removeElement(username);
                    //CCUI.getTabs().removeTabAt(CCUI.getTabs().indexOfTab(username));
                    //client.addMessage("Public", "");
                    for (int i = 0; i < CCUI.getTabs().getTabCount(); i++) {
                        if (CCUI.getTabs().getTitleAt(i).equals(username)) {
                            CCUI.getTabs().remove(CCUI.getTabs().indexOfTab(username));
                        }
                    }
                    CCUI.getTabs().setSelectedIndex(CCUI.getTabs().indexOfTab("Public"));

                } else if (sp[0].equals("Public")) {// messages for public
                    String s = sp[1];// sender
                    // String r = sp[2];// receiver
                    String c = sp[3];// message
                    // textArea.append("[public]" + s + ":\r\n" + c + "\r\n\r\n");
                    client.addMessage("Public", df.format(new Date()) + "\n" + s + ":   " + c + "\r\n\r\n");
                    CCUI.getTabs().setSelectedIndex(CCUI.getTabs().indexOfTab("Public"));
                    System.out.println("[public]" + s + ": " + c + "\r\n");
                } else if (sp[0].equals("Private")) {// messages for private
                    String s = sp[1];// sender
                    String r = sp[2];// receiver
                    String c = sp[3];// message
                    // textArea.append("[private]" + s + ":\r\n" + c + "\r\n\r\n");
                    for (String str : sp) {
                        System.out.println(str);
                    }
                    System.out.println("----");
                    String s2 = s.equals(client.getName()) ? r : s;
                    client.addMessage(s2, df.format(new Date()) + "\n" + s + ":   " + c + "\r\n\r\n");
                    CCUI.getTabs().setSelectedIndex(CCUI.getTabs().indexOfTab(s2));
                    System.out.println("[Private]" + s + ": " + c + "\r\n");
                } else if (sp[0].equals("Group")) {
                    String s = sp[1];// sender
                    String r = sp[2];// receiver
                    String c = sp[3];// message
                    for (String str : sp) {
                        System.out.println(str);
                    }
                    System.out.println("----");
                    client.addMessage("[Group]" + r, df.format(new Date()) + "\n" + s + ":   " + c + "\r\n\r\n");
                    CCUI.getTabs().setSelectedIndex(CCUI.getTabs().indexOfTab("[Group]" + r));
                    System.out.println("[Group]" + s + ": " + c + "\r\n");
                } else if (sp[0].equals("Profile")) {
                    String[] profile = sp[1].split("#");
                    if (sp[1].contains("[Group]")) {
                        GroupProfileUI GPUI = new GroupProfileUI();
                        GPUI.getGroupname().setText(profile[0]);
                        for (int i = 1; i < profile.length; i++) {
                            GPUI.getGroupmembersModel().addElement(profile[i]);
                        }
                    } else {
                        UserProfileUI UPUI = new UserProfileUI();
                        UPUI.getUser().setText(profile[0]);
                        UPUI.getSex().setText(profile[1]);
                        UPUI.getAge().setText(profile[2]);
                        UPUI.getEmail().setText(profile[3]);
                        UPUI.getAddress().setText(profile[4]);
                    }
                }
//				else if(sp[0].equals("History")) {
//					HistoryUI HUI=new HistoryUI();
//					HUI.getTextArea().setText(sp[1]);
//				}
                message = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.setDefaultLocale(Locale.ENGLISH);
        JOptionPane.showMessageDialog(null, "Server is down",
                "", JOptionPane.ERROR_MESSAGE);

    }
}
