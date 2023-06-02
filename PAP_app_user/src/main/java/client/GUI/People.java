/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package client.GUI;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONObject;

import net.miginfocom.swing.MigLayout;

import client.Chat.Chat;
import client.GUI.guiListeners.AddUsersListener;
import client.GUI.guiListeners.ChangeConversationNameListener;
import client.GUI.guiListeners.ChatContentsUpdater;
import client.GUI.guiListeners.CreateGroupListener;
import client.GUI.guiListeners.JoinGroupUsingCodeListener;
import client.GUI.guiListeners.RemoveUserListener;
import client.GUI.guiListeners.SendMessageListener;
import client.GUI.guiListeners.SendPhotoListener;
import client.GUI.guiListeners.SwitchConversationListener;
import client.GUI.guiWorkers.ChatWorker;
import client.ServerConnectionConstants.ChatMessagesConstants;

public class People extends javax.swing.JPanel {

        boolean light = false;
        boolean dark = true;
        MainScreen mainScreenWindow;

        private ChatWorker chatWorker;
        private Chat chat;
        StringBuilder choosenImagePath;

        public People(MainScreen mainScreenParam, Chat chat, char[] userPassword) {
                choosenImagePath = new StringBuilder();
                this.chat = chat;
                mainScreenWindow = mainScreenParam;
                initComponents();
                this.membersList.setEditable(true);
                this.textArea.setText("");
                this.membersList.setLineWrap(true);
                this.textArea.setColumns(45);
                this.textArea.setLineWrap(true);
                this.membersList.setWrapStyleWord(true);
                this.textArea.setWrapStyleWord(true);
                this.chatPanel.setLayout(new MigLayout("fillx"));

                chatWorker = new ChatWorker(chat, userPassword, new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                                updateChatUi();
                                return null;
                        }
                }, chatPanel);
                chatWorker.execute();

                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                updateChatUi();
                                ChatContentsUpdater.updateChat(chat.getCurrentMessages(), chat, chatPanel);
                        }
                }).start();
        }

        public void Theme() {
                if (light == true & dark == false) {
                        FlatLightLaf.setup();
                        SwingUtilities.updateComponentTreeUI(this);
                } else if (light == false & dark == true) {
                        FlatDarkLaf.setup();
                        SwingUtilities.updateComponentTreeUI(this);
                }
        }

        public void updateChatUi() {
                pathFileLabel.setText(choosenImagePath
                                .substring(choosenImagePath.lastIndexOf("\\") + 1, choosenImagePath.length()));
                var usersInConv = chat.getUsersInCurrentConversation();
                String[] usersList = new String[usersInConv.size()];
                int i = 0;
                for (int userId : usersInConv.keySet()) {
                        usersList[i] = usersInConv.get(userId).getString(ChatMessagesConstants.USERNAME.value());
                        i++;
                }
                membersInConvList.removeAll();
                membersInConvList.setModel(new javax.swing.AbstractListModel<String>() {
                        public int getSize() {
                                return usersList.length;
                        }

                        public String getElementAt(int i) {
                                return usersList[i];
                        }
                });
                peopleListRemove.removeAll();
                peopleListRemove.setModel(new javax.swing.AbstractListModel<String>() {
                        public int getSize() {
                                return usersList.length;
                        }

                        public String getElementAt(int i) {
                                return usersList[i];
                        }
                });

                changeChatNameLabel.setText("<html>Current conversation code:" + chat.getConversationCode()
                                + "<br> Change current conversation name:</html>");

                // System.out.println(345);

                var convNamesSet = chat.getConversationsNamesToIds().keySet();

                chatsList.removeAll();

                String[] convNames = new String[convNamesSet.size()];
                i = 0;
                for (String name : convNamesSet) {
                        convNames[i++] = name;
                }
                chatsList.setModel(new javax.swing.AbstractListModel<String>() {

                        public int getSize() {
                                return convNames.length;
                        }

                        public String getElementAt(int i) {
                                return convNames[i];
                        }
                });
                // System.out.println(234);
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">
        private void initComponents() {

                logo = new javax.swing.JLabel();
                createGroupLabel = new javax.swing.JLabel();
                groupNameLabel = new javax.swing.JLabel();
                groupName = new javax.swing.JTextField();
                membersLabel = new javax.swing.JLabel();
                membersContainer = new javax.swing.JScrollPane();
                membersList = new javax.swing.JTextArea();
                joinToGroupLabel = new javax.swing.JLabel();
                groupCodeLabel2 = new javax.swing.JLabel();
                groupCode2 = new javax.swing.JTextField();
                joinButton = new javax.swing.JButton();
                currentConversationLable = new javax.swing.JLabel();
                userOrGroupNameLabel = new javax.swing.JLabel();
                chatsContainer = new javax.swing.JScrollPane();
                chatsList = new javax.swing.JList<>();
                textContainer = new javax.swing.JScrollPane();
                textArea = new javax.swing.JTextArea();
                sendButton = new javax.swing.JButton();
                addPersonToGroupLabel = new javax.swing.JLabel();
                createButton = new javax.swing.JButton();
                peopleButton = new javax.swing.JButton();
                musicButton = new javax.swing.JButton();
                accountButton = new javax.swing.JButton();
                mainScreenButton = new javax.swing.JButton();
                chatContainer = new javax.swing.JScrollPane();
                chatPanel = new javax.swing.JPanel();
                photoButton = new javax.swing.JButton();
                peopleContainer = new javax.swing.JScrollPane();
                peopleToAddList = new javax.swing.JTextArea();
                addButton = new javax.swing.JButton();
                removePersonFromGroupLabel = new javax.swing.JLabel();
                peopleListRemove = new javax.swing.JList<>();
                removeButton = new javax.swing.JButton();
                changeChatNameLabel = new javax.swing.JLabel();
                changeChatName = new javax.swing.JTextField();
                changeButton = new javax.swing.JButton();
                membersInConvLabel = new javax.swing.JLabel();
                membersInConContainer = new javax.swing.JScrollPane();
                membersInConvList = new javax.swing.JList<>();
                sendPhotoButton = new javax.swing.JButton();
                chosenFileLabel = new javax.swing.JLabel();
                pathFileLabel = new javax.swing.JLabel();

                setPreferredSize(new java.awt.Dimension(1280, 712));

                logo.setFont(new java.awt.Font("Segoe UI Semilight", 3, 48)); // NOI18N
                logo.setText("ConnectedByMusic");

                createGroupLabel.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
                createGroupLabel.setText("Create Conversation:");

                groupNameLabel.setText("Conversation name:");

                groupName.setText("");

                membersLabel.setText("Members (example: friend1;friend2;friend3...):");

                membersList.setColumns(20);
                membersList.setRows(5);
                membersContainer.setViewportView(membersList);

                joinToGroupLabel.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
                joinToGroupLabel.setText("Join to conversation:");

                groupCodeLabel2.setText("Conversation code:");

                groupCode2.setText("");

                joinButton.setText("Join");
                joinButton.addActionListener(new JoinGroupUsingCodeListener(chat, groupCode2, new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                                updateChatUi();
                                System.out.println("updated");
                                return null;
                        }
                }));

                currentConversationLable.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
                currentConversationLable.setText("Conversations:");

                userOrGroupNameLabel.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
                userOrGroupNameLabel.setText("");

                var convNamesSet = chat.getConversationsNamesToIds().keySet();

                String[] convNames = new String[convNamesSet.size()];
                int i = 0;
                for (String name : convNamesSet) {
                        convNames[i++] = name;
                }
                chatsList.setModel(new javax.swing.AbstractListModel<String>() {

                        public int getSize() {
                                return convNames.length;
                        }

                        public String getElementAt(int i) {
                                return convNames[i];
                        }
                });

                chatsList.addListSelectionListener(
                                new SwitchConversationListener(chat, chatPanel,
                                                new Callable<Void>() {
                                                        @Override
                                                        public Void call() throws Exception {
                                                                updateChatUi();
                                                                return null;
                                                        }
                                                }));
                chatsContainer.setViewportView(chatsList);

                textArea.setColumns(20);
                textArea.setRows(5);
                textContainer.setViewportView(textArea);

                sendButton.setText("Send");
                sendButton.addActionListener(new SendMessageListener(chat, textArea, chatPanel));

                addPersonToGroupLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
                addPersonToGroupLabel.setText("Add person to current conversation:");

                createButton.setText("Create");
                createButton.addActionListener(new CreateGroupListener(chat, groupName, membersList, chatsList));

                peopleButton.setText("jButton1");
                peopleButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                peopleButtonActionPerformed(evt);
                        }
                });

                musicButton.setText("jButton1");
                musicButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                musicButtonActionPerformed(evt);
                        }
                });

                accountButton.setText("jButton1");
                accountButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                accountButtonActionPerformed(evt);
                        }
                });

                mainScreenButton.setText("jButton1");
                mainScreenButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                mainScreenButtonActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout chatPanelLayout = new javax.swing.GroupLayout(chatPanel);
                chatPanel.setLayout(chatPanelLayout);
                chatPanelLayout.setHorizontalGroup(
                                chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 644, Short.MAX_VALUE));
                chatPanelLayout.setVerticalGroup(
                                chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 739, Short.MAX_VALUE));

                chatContainer.setViewportView(chatPanel);

                photoButton.setText("Add photo");
                photoButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                photoButtonActionPerformed(evt);
                        }
                });

                peopleContainer.setViewportView(peopleToAddList);

                addButton.setText("Add");
                addButton.addActionListener(new AddUsersListener(chat, peopleToAddList));

                removePersonFromGroupLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
                removePersonFromGroupLabel.setText("Remove person from current conversation:");

                peopleListRemove.setModel(new javax.swing.AbstractListModel<String>() {
                        String[] strings = {};

                        public int getSize() {
                                return strings.length;
                        }

                        public String getElementAt(int i) {
                                return strings[i];
                        }
                });

                removeButton.setText("Remove");
                removeButton.addActionListener(new RemoveUserListener(chat, membersInConvList, new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                                updateChatUi();
                                return null;
                        }
                }));

                changeChatNameLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
                changeChatNameLabel.setText(
                                "<html>Current conversation code: <br> Change current conversation name:</html>");

                changeChatName.setText("jTextField1");

                changeButton.setText("Chnage");
                changeButton.addActionListener(
                                new ChangeConversationNameListener(changeChatName, chat, new Callable<Void>() {
                                        @Override
                                        public Void call() throws Exception {
                                                updateChatUi();
                                                return null;
                                        }
                                }));

                membersInConvLabel.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
                membersInConvLabel.setText("Members:");

                membersInConvList.setModel(new javax.swing.AbstractListModel<String>() {
                        String[] strings = {};

                        public int getSize() {
                                return strings.length;
                        }

                        public String getElementAt(int i) {
                                return strings[i];
                        }
                });
                membersInConContainer.setViewportView(membersInConvList);

                sendPhotoButton.setText("Send photo");
                sendPhotoButton.addActionListener(new SendPhotoListener(chat, choosenImagePath, pathFileLabel));

                chosenFileLabel.setText("Chosen file:");

                pathFileLabel.setText("(path)");

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(logo,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                false)
                                                                                                                .addComponent(groupNameLabel,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(groupName))
                                                                                                .addGap(8, 8, 8))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                .addComponent(membersContainer,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(createButton,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addGap(10, 10, 10))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(addPersonToGroupLabel,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGap(195, 195, 195))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(createGroupLabel,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                                                .addComponent(
                                                                                                                                                                                removePersonFromGroupLabel,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                .addGap(63, 63, 63)
                                                                                                                                                                .addComponent(addButton,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                Short.MAX_VALUE))
                                                                                                                                                .addComponent(peopleListRemove,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(peopleContainer)
                                                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                .addGroup(layout
                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                .addComponent(
                                                                                                                                                                                                                changeChatNameLabel,
                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                .addGap(129, 129,
                                                                                                                                                                                                                129))
                                                                                                                                                                                .addGroup(layout
                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                .addComponent(
                                                                                                                                                                                                                changeChatName,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                315,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                Short.MAX_VALUE)))
                                                                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                .addComponent(changeButton,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                .addComponent(removeButton,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                Short.MAX_VALUE))))
                                                                                                                                .addGap(7, 7, 7)))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                .addComponent(joinButton,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                layout.createSequentialGroup()
                                                                                                                                                .addComponent(membersLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                399,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                .addComponent(groupCodeLabel2)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(groupCode2)))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(joinToGroupLabel,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                397,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)))
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(currentConversationLable,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                125, Short.MAX_VALUE)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(membersInConvLabel)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                layout.createSequentialGroup()
                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addComponent(membersInConContainer))
                                                                                                                                .addComponent(chatsContainer))
                                                                                                                .addGap(6, 6, 6)))
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(userOrGroupNameLabel,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGap(117, 117, 117))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                layout.createSequentialGroup()
                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                .addComponent(chatContainer,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                653,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addComponent(textContainer)
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addComponent(photoButton)
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addComponent(sendButton,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                62,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                                .addComponent(chosenFileLabel)
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addComponent(pathFileLabel,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addGap(45, 45, 45)
                                                                                                                                                .addComponent(sendPhotoButton)
                                                                                                                                                .addGap(25, 25, 25)))
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(peopleButton,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                77,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(musicButton,
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                77,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addComponent(accountButton,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                77,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(mainScreenButton,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                77,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap()));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(logo)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(addPersonToGroupLabel,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                layout.createSequentialGroup()
                                                                                                                .addContainerGap()
                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                .addComponent(currentConversationLable)
                                                                                                                                .addComponent(userOrGroupNameLabel))))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                .addComponent(peopleContainer,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                25,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                                                .addComponent(addButton,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                31,
                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                .addGap(18, 18, 18))
                                                                                                                                                .addGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                layout.createSequentialGroup()
                                                                                                                                                                                .addGap(16, 16, 16)
                                                                                                                                                                                .addComponent(
                                                                                                                                                                                                removePersonFromGroupLabel,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                                                                                                                .addComponent(peopleListRemove,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                29,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                                                .addComponent(removeButton,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                32,
                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                .addGap(63, 63, 63))
                                                                                                                                                .addGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                layout.createSequentialGroup()
                                                                                                                                                                                .addGap(21, 21, 21)
                                                                                                                                                                                .addComponent(
                                                                                                                                                                                                changeChatNameLabel,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                .addGroup(layout
                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                                                                .addComponent(
                                                                                                                                                                                                                changeChatName,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                .addComponent(
                                                                                                                                                                                                                changeButton))
                                                                                                                                                                                .addGap(17, 17, 17)))
                                                                                                                                .addComponent(createGroupLabel)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(groupNameLabel)
                                                                                                                                                .addComponent(groupName,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)))
                                                                                                                .addComponent(chatsContainer))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(membersLabel)
                                                                                                                .addComponent(membersInConvLabel))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                .addComponent(membersContainer,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                152,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(createButton)
                                                                                                                                .addGap(24, 24, 24)
                                                                                                                                .addComponent(joinToGroupLabel)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(groupCodeLabel2)
                                                                                                                                                .addComponent(groupCode2,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(joinButton,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                20,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(9, 9, 9))
                                                                                                                .addComponent(membersInConContainer)))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(chatContainer,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                0,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                layout.createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(sendButton)
                                                                                                                                                .addComponent(photoButton))
                                                                                                                .addComponent(textContainer,
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                23,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                false)
                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                .addComponent(pathFileLabel)
                                                                                                                                .addComponent(chosenFileLabel))
                                                                                                                .addComponent(sendPhotoButton,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                0,
                                                                                                                                Short.MAX_VALUE))))
                                                                .addGap(10, 10, 10))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addGap(12, 12, 12)
                                                                .addComponent(peopleButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                35,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(11, 11, 11)
                                                                .addComponent(musicButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                35,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(accountButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                35,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(mainScreenButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                35,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
        }// </editor-fold>

        private void joinButtonActionPerformed(java.awt.event.ActionEvent evt) {

        }

        private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
                String message = this.textArea.getText().trim();
                LeftChatPanel leftChatPanel = new LeftChatPanel();
                if (!message.equals("")) {
                        leftChatPanel.chatText.setText(message);
                        leftChatPanel.chatText.setForeground(Color.black);
                        // this.chatPanel.add(leftChatPanel.avatarChat);
                        this.chatPanel.add(leftChatPanel.chatBlock, "wrap");
                        this.chatPanel.repaint();
                        this.chatPanel.revalidate();
                        this.textArea.setText("");

                }
        }

        private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO add your handling code here:
        }

        private void peopleButtonActionPerformed(java.awt.event.ActionEvent evt) {

        }

        private void musicButtonActionPerformed(java.awt.event.ActionEvent evt) {
                mainScreenWindow.setContentPane(mainScreenWindow.music);
                mainScreenWindow.repaint();
                mainScreenWindow.revalidate();
        }

        private void accountButtonActionPerformed(java.awt.event.ActionEvent evt) {
                mainScreenWindow.setContentPane(mainScreenWindow.account);
                mainScreenWindow.repaint();
                mainScreenWindow.revalidate();
        }

        private void mainScreenButtonActionPerformed(java.awt.event.ActionEvent evt) {
                mainScreenWindow.setContentPane(mainScreenWindow.mainScreen);
                mainScreenWindow.setVisible(true);
                mainScreenWindow.repaint();
                mainScreenWindow.revalidate();
        }

        private void photoButtonActionPerformed(java.awt.event.ActionEvent evt) {
                AvatarChooser photoChooser = new AvatarChooser();
                photoChooser.setVisible(true);
        }

        private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO add your handling code here:
        }

        private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO add your handling code here:
        }

        private void changeButtonActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO add your handling code here:
        }

        private void sendPhotoButtonActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO add your handling code here:
        }

        // Variables declaration - do not modify
        private javax.swing.JButton accountButton;
        private javax.swing.JButton addButton;
        private javax.swing.JLabel addPersonToGroupLabel;
        private javax.swing.JButton changeButton;
        private javax.swing.JTextField changeChatName;
        private javax.swing.JLabel changeChatNameLabel;
        private javax.swing.JScrollPane chatContainer;
        private javax.swing.JPanel chatPanel;
        private javax.swing.JScrollPane chatsContainer;
        private javax.swing.JLabel currentConversationLable;
        private javax.swing.JList<String> chatsList;
        private javax.swing.JLabel chosenFileLabel;
        private javax.swing.JButton createButton;
        private javax.swing.JLabel createGroupLabel;
        private javax.swing.JTextField groupCode2;
        private javax.swing.JLabel groupCodeLabel2;
        private javax.swing.JTextField groupName;
        private javax.swing.JLabel groupNameLabel;
        private javax.swing.JButton joinButton;
        private javax.swing.JLabel joinToGroupLabel;
        private javax.swing.JLabel logo;
        private javax.swing.JButton mainScreenButton;
        private javax.swing.JScrollPane membersContainer;
        private javax.swing.JScrollPane membersInConContainer;
        private javax.swing.JLabel membersInConvLabel;
        private javax.swing.JList<String> membersInConvList;
        private javax.swing.JLabel membersLabel;
        private javax.swing.JTextArea membersList;
        private javax.swing.JButton musicButton;
        private javax.swing.JLabel pathFileLabel;
        private javax.swing.JButton peopleButton;
        private javax.swing.JScrollPane peopleContainer;
        private javax.swing.JTextArea peopleToAddList;
        private javax.swing.JList<String> peopleListRemove;
        private javax.swing.JButton photoButton;
        private javax.swing.JButton removeButton;
        private javax.swing.JLabel removePersonFromGroupLabel;
        private javax.swing.JButton sendButton;
        private javax.swing.JButton sendPhotoButton;
        private javax.swing.JTextArea textArea;
        private javax.swing.JScrollPane textContainer;
        private javax.swing.JLabel userOrGroupNameLabel;
        // End of variables declaration
}
