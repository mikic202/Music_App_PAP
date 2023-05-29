/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package client.GUI;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import client.Chat.Chat;
import client.ServerConnector.ServerConnector;

import javax.sound.sampled.*;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.FloatControl;

public class MainScreen extends javax.swing.JFrame {

        boolean dark = true;
        boolean light = false;
        MusicChooser musicChooser;
        File musicFile;
        AudioInputStream audioStream;
        Clip clip;
        People people;
        Music music;
        Account account;
        private ServerConnector serverConnector;

        private Chat chat;

        private void loadMusic() {
                musicFile = new File(musicChooser.selectedPath);
                try {
                        audioStream = AudioSystem.getAudioInputStream(musicFile);
                } catch (UnsupportedAudioFileException | IOException ex) {
                        Logger.getLogger(MainScreenCode.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                        clip = AudioSystem.getClip();
                } catch (LineUnavailableException ex) {
                        Logger.getLogger(MainScreenCode.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                        this.clip.open(this.audioStream);
                } catch (LineUnavailableException | IOException ex) {
                        Logger.getLogger(MainScreenCode.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

        public MainScreen() {
                JSONObject userInfo = new JSONObject();
                userInfo.put("user_id", 1);
                userInfo.put("username", "mikic202");
                userInfo.put("email", "mikolaj.chomanski@gmail.com");
                userInfo.put("profile_picture", "0");
                try {
                        serverConnector = new ServerConnector(new Socket("localhost",
                                        8000));
                } catch (Exception e) {
                        System.out.println(e);
                }
                chat = new Chat(userInfo, -1, serverConnector);
                FlatDarkLaf.setup();
                initComponents();
                this.peopleButton.setIcon(new ImageIcon(
                                "C:\\Users\\Adam\\Documents\\NetBeansProjects\\NewGUI\\src\\main\\java\\com\\mycompany\\newgui\\PeoplePAP.png"));
                this.musicButton.setIcon(new ImageIcon(
                                "C:\\Users\\Adam\\Documents\\NetBeansProjects\\NewGUI\\src\\main\\java\\com\\mycompany\\newgui\\MusicPAP.png"));
                this.accountButton.setIcon(new ImageIcon(
                                "C:\\Users\\Adam\\Documents\\NetBeansProjects\\NewGUI\\src\\main\\java\\com\\mycompany\\newgui\\AccountSettingsPAP.png"));
                // AudioFormat format = audioStream.getFormat();
                // long frames = audioStream.getFrameLength();
                // duration = (frames+0.0) / format.getFrameRate();
                // Math.round(duration);
                // jLabel21.setText(String.valueOf(duration));
                // this.MainScreenCode.setLayout(new FlowLayout());
                char[] userPassword = { '1', '2', '3', '4', '5', '6', '7' };
                people = new People(this, chat, userPassword);
                music = new Music(this);
                account = new Account(this, serverConnector, chat);
        }

        public MainScreen(ServerConnector serverConnector, JSONObject userInfo, char[] userPassword) {
                chat = new Chat(userInfo, -1, serverConnector);
                this.serverConnector = serverConnector;
                FlatDarkLaf.setup();
                initComponents();
                this.peopleButton.setIcon(new ImageIcon(
                                "C:\\Users\\Adam\\Documents\\NetBeansProjects\\NewGUI\\src\\main\\java\\com\\mycompany\\newgui\\PeoplePAP.png"));
                this.musicButton.setIcon(new ImageIcon(
                                "C:\\Users\\Adam\\Documents\\NetBeansProjects\\NewGUI\\src\\main\\java\\com\\mycompany\\newgui\\MusicPAP.png"));
                this.accountButton.setIcon(new ImageIcon(
                                "C:\\Users\\Adam\\Documents\\NetBeansProjects\\NewGUI\\src\\main\\java\\com\\mycompany\\newgui\\AccountSettingsPAP.png"));
                // AudioFormat format = audioStream.getFormat();
                // long frames = audioStream.getFrameLength();
                // duration = (frames+0.0) / format.getFrameRate();
                // Math.round(duration);
                // jLabel21.setText(String.valueOf(duration));
                // this.MainScreenCode.setLayout(new FlowLayout());
                people = new People(this, chat, userPassword);
                music = new Music(this);
                account = new Account(this, serverConnector, chat);
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">
        private void initComponents() {

                jScrollPane5 = new javax.swing.JScrollPane();
                jEditorPane1 = new javax.swing.JEditorPane();
                mainScreen = new javax.swing.JPanel();
                logoLabel = new javax.swing.JLabel();
                listeningNowLabel = new javax.swing.JLabel();
                currentSongNameLabel = new javax.swing.JLabel();
                previousListensLabel = new javax.swing.JLabel();
                previousListens = new javax.swing.JScrollPane();
                previousListensList = new javax.swing.JList<>();
                musicPictureContainer = new javax.swing.JScrollPane();
                musicPicture = new javax.swing.JEditorPane();
                currentSongTitleLabel = new javax.swing.JLabel();
                producerAuthorLabel = new javax.swing.JLabel();
                authorLabel = new javax.swing.JLabel();
                uploadedByLabel = new javax.swing.JLabel();
                nicknameAndDateLabel = new javax.swing.JLabel();
                genreLabel = new javax.swing.JLabel();
                viewsLabel = new javax.swing.JLabel();
                hereGenreLabel = new javax.swing.JLabel();
                currentViewsLabel = new javax.swing.JLabel();
                currentTotalTimeLabel = new javax.swing.JLabel();
                currentTimeLabel = new javax.swing.JLabel();
                totalTimeLabel = new javax.swing.JLabel();
                timeSlashLabel = new javax.swing.JLabel();
                playButton = new javax.swing.JButton();
                pauseLabel = new javax.swing.JButton();
                loopButton = new javax.swing.JToggleButton();
                randomButton = new javax.swing.JButton();
                timeSongSlider = new javax.swing.JSlider();
                volumeButton = new javax.swing.JToggleButton();
                panningButton = new javax.swing.JToggleButton();
                equalizerButton = new javax.swing.JToggleButton();
                reverbButton = new javax.swing.JToggleButton();
                gainSlider = new javax.swing.JSlider();
                leftSlider = new javax.swing.JSlider();
                rightSlider = new javax.swing.JSlider();
                Slider32Hz = new javax.swing.JSlider();
                Slider64Hz = new javax.swing.JSlider();
                Slider125Hz = new javax.swing.JSlider();
                Slider250Hz = new javax.swing.JSlider();
                Slider500Hz = new javax.swing.JSlider();
                Slider1kHz = new javax.swing.JSlider();
                Slider2kHz = new javax.swing.JSlider();
                Slider4kHz = new javax.swing.JSlider();
                Slider8kHz = new javax.swing.JSlider();
                Slider16kHz = new javax.swing.JSlider();
                Label16kHz = new javax.swing.JLabel();
                Label8kHz = new javax.swing.JLabel();
                Label4kHz = new javax.swing.JLabel();
                Label2kHz = new javax.swing.JLabel();
                Label1kHz = new javax.swing.JLabel();
                Label500Hz = new javax.swing.JLabel();
                Label250Hz = new javax.swing.JLabel();
                Label125Hz = new javax.swing.JLabel();
                Label64Hz = new javax.swing.JLabel();
                Label32Hz = new javax.swing.JLabel();
                rightLabel = new javax.swing.JLabel();
                leftLabel = new javax.swing.JLabel();
                reverbListContainer = new javax.swing.JScrollPane();
                reverbList = new javax.swing.JList<>();
                gainLabel = new javax.swing.JLabel();
                peopleButton = new javax.swing.JButton();
                musicButton = new javax.swing.JButton();
                themeButton = new javax.swing.JRadioButton();
                accountButton = new javax.swing.JButton();
                loadButton = new javax.swing.JButton();
                mainScreenButton = new javax.swing.JButton();
                menu = new javax.swing.JMenuBar();
                chooseMusic = new javax.swing.JMenu();
                changeAvatar = new javax.swing.JMenu();

                jScrollPane5.setViewportView(jEditorPane1);

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                logoLabel.setFont(new java.awt.Font("Segoe UI Semilight", 3, 48)); // NOI18N
                logoLabel.setText("ConnectedByMusic");
                logoLabel.setMinimumSize(new java.awt.Dimension(0, 0));

                listeningNowLabel.setText("Listening now:");

                currentSongNameLabel.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
                currentSongNameLabel.setText("Current song name");

                previousListensLabel.setText("Previous listens:");

                previousListensList.setModel(new javax.swing.AbstractListModel<String>() {
                        String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

                        public int getSize() {
                                return strings.length;
                        }

                        public String getElementAt(int i) {
                                return strings[i];
                        }
                });
                previousListens.setViewportView(previousListensList);

                musicPictureContainer.setViewportView(musicPicture);

                currentSongTitleLabel.setFont(new java.awt.Font("Serif", 3, 48)); // NOI18N
                currentSongTitleLabel.setText("Current Song Title");

                producerAuthorLabel.setFont(new java.awt.Font("Verdana", 3, 24)); // NOI18N
                producerAuthorLabel.setText("Producer/Author:");

                authorLabel.setFont(new java.awt.Font("Verdana", 3, 18)); // NOI18N
                authorLabel.setText("Paste author here");

                uploadedByLabel.setFont(new java.awt.Font("Verdana", 3, 24)); // NOI18N
                uploadedByLabel.setText("Uploaded by:");

                nicknameAndDateLabel.setFont(new java.awt.Font("Verdana", 3, 18)); // NOI18N
                nicknameAndDateLabel.setText("here nickname and date");

                genreLabel.setFont(new java.awt.Font("Verdana", 3, 24)); // NOI18N
                genreLabel.setText("Genre:");

                viewsLabel.setFont(new java.awt.Font("Verdana", 3, 24)); // NOI18N
                viewsLabel.setText("Views:");

                hereGenreLabel.setFont(new java.awt.Font("Verdana", 3, 18)); // NOI18N
                hereGenreLabel.setText("here genre");

                currentViewsLabel.setFont(new java.awt.Font("Verdana", 3, 18)); // NOI18N
                currentViewsLabel.setText("views");

                currentTotalTimeLabel.setFont(new java.awt.Font("Verdana", 3, 24)); // NOI18N
                currentTotalTimeLabel.setText("Current Time / Total Time");

                currentTimeLabel.setFont(new java.awt.Font("Verdana", 3, 24)); // NOI18N
                currentTimeLabel.setText("3:48");

                totalTimeLabel.setFont(new java.awt.Font("Verdana", 3, 24)); // NOI18N
                totalTimeLabel.setText("/");

                timeSlashLabel.setFont(new java.awt.Font("Verdana", 3, 24)); // NOI18N
                timeSlashLabel.setText("3:48");

                playButton.setText("Play");
                playButton.setToolTipText("");
                playButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                playButtonActionPerformed(evt);
                        }
                });

                pauseLabel.setText("Pause");
                pauseLabel.setToolTipText("");
                pauseLabel.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                pauseLabelActionPerformed(evt);
                        }
                });

                loopButton.setText("Loop");

                randomButton.setText("Random");
                randomButton.setToolTipText("");

                timeSongSlider.setValue(0);

                volumeButton.setText("Volume");
                volumeButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                volumeButtonActionPerformed(evt);
                        }
                });

                panningButton.setText("Panning");
                panningButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                panningButtonActionPerformed(evt);
                        }
                });

                equalizerButton.setText("Equalizer");
                equalizerButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                equalizerButtonActionPerformed(evt);
                        }
                });

                reverbButton.setText("Reverb");
                reverbButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                reverbButtonActionPerformed(evt);
                        }
                });

                gainSlider.setOrientation(javax.swing.JSlider.VERTICAL);

                leftSlider.setOrientation(javax.swing.JSlider.VERTICAL);

                rightSlider.setOrientation(javax.swing.JSlider.VERTICAL);

                Slider32Hz.setOrientation(javax.swing.JSlider.VERTICAL);

                Slider64Hz.setOrientation(javax.swing.JSlider.VERTICAL);

                Slider125Hz.setOrientation(javax.swing.JSlider.VERTICAL);

                Slider250Hz.setOrientation(javax.swing.JSlider.VERTICAL);

                Slider500Hz.setOrientation(javax.swing.JSlider.VERTICAL);

                Slider1kHz.setOrientation(javax.swing.JSlider.VERTICAL);

                Slider2kHz.setOrientation(javax.swing.JSlider.VERTICAL);

                Slider4kHz.setOrientation(javax.swing.JSlider.VERTICAL);

                Slider8kHz.setOrientation(javax.swing.JSlider.VERTICAL);

                Slider16kHz.setOrientation(javax.swing.JSlider.VERTICAL);

                Label16kHz.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
                Label16kHz.setText("16kHz");

                Label8kHz.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
                Label8kHz.setText("8kHz");

                Label4kHz.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
                Label4kHz.setText("4kHz");

                Label2kHz.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
                Label2kHz.setText("2kHz");

                Label1kHz.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
                Label1kHz.setText("1kHz");

                Label500Hz.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
                Label500Hz.setText("500Hz");

                Label250Hz.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
                Label250Hz.setText("250Hz");

                Label125Hz.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
                Label125Hz.setText("125Hz");

                Label64Hz.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
                Label64Hz.setText("64Hz\n");

                Label32Hz.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
                Label32Hz.setText("32Hz");

                rightLabel.setText("Right");

                leftLabel.setText("Left");

                reverbList.setModel(new javax.swing.AbstractListModel<String>() {
                        String[] strings = { "Cavern", "Dungeon", "Garage", "Acoustinc Lab", "Closet" };

                        public int getSize() {
                                return strings.length;
                        }

                        public String getElementAt(int i) {
                                return strings[i];
                        }
                });
                reverbListContainer.setViewportView(reverbList);

                gainLabel.setText("Gain");

                peopleButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Adam\\Desktop\\PeoplePAP.png")); // NOI18N
                peopleButton.setText("jButton1");
                peopleButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                peopleButtonActionPerformed(evt);
                        }
                });

                musicButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Adam\\Desktop\\MusicPAP.png")); // NOI18N
                musicButton.setText("jButton1");
                musicButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                musicButtonActionPerformed(evt);
                        }
                });

                themeButton.setText("LightMode");
                themeButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                themeButtonActionPerformed(evt);
                        }
                });

                accountButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Adam\\Desktop\\AccountSettingsPAP.png")); // NOI18N
                accountButton.setText("jButton1");
                accountButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                accountButtonActionPerformed(evt);
                        }
                });

                loadButton.setText("Load");
                loadButton.setToolTipText("");
                loadButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                loadButtonActionPerformed(evt);
                        }
                });

                mainScreenButton.setText("jButton1");
                mainScreenButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                mainScreenButtonActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout mainScreenLayout = new javax.swing.GroupLayout(mainScreen);
                mainScreen.setLayout(mainScreenLayout);
                mainScreenLayout.setHorizontalGroup(
                                mainScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(mainScreenLayout.createSequentialGroup()
                                                                .addGroup(mainScreenLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(mainScreenLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(logoLabel,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                426,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(0, 6, Short.MAX_VALUE))
                                                                                .addGroup(mainScreenLayout
                                                                                                .createSequentialGroup()
                                                                                                .addContainerGap()
                                                                                                .addGroup(mainScreenLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(previousListens)
                                                                                                                                .addGap(6, 6, 6))
                                                                                                                .addComponent(previousListensLabel,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(listeningNowLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                420,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(currentSongNameLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                405,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(mainScreenLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addGroup(mainScreenLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(mainScreenLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(currentSongTitleLabel,
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                mainScreenLayout.createSequentialGroup()
                                                                                                                                                .addComponent(loadButton,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                155,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addComponent(playButton,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                155,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addComponent(pauseLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                156,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addComponent(loopButton,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                141,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addComponent(randomButton,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE))
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                mainScreenLayout.createSequentialGroup()
                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                .addComponent(volumeButton,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                155,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                .addComponent(panningButton,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                155,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                .addComponent(equalizerButton,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                Short.MAX_VALUE))
                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                .addGap(68, 68, 68)
                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                                .addComponent(gainSlider,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                .addComponent(gainLabel,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                37,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                                .addComponent(leftSlider,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                .addComponent(rightSlider,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                28,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                                .addComponent(leftLabel)
                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                .addComponent(rightLabel)))
                                                                                                                                                                                .addGap(60, 60, 60)
                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                                .addComponent(Slider32Hz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                .addComponent(Slider64Hz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                                .addComponent(Label32Hz)
                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                .addComponent(Label64Hz)))
                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                                                                false)
                                                                                                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                                                mainScreenLayout.createSequentialGroup()
                                                                                                                                                                                                                                .addComponent(Label125Hz)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                                                .addComponent(Label250Hz))
                                                                                                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                                                mainScreenLayout.createSequentialGroup()
                                                                                                                                                                                                                                .addComponent(Slider125Hz,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                .addComponent(Slider250Hz,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                                                                false)
                                                                                                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                                                mainScreenLayout.createSequentialGroup()
                                                                                                                                                                                                                                .addComponent(Slider500Hz,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                .addComponent(Slider1kHz,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                .addComponent(Slider2kHz,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                .addComponent(Slider4kHz,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                .addComponent(Slider8kHz,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                .addComponent(Slider16kHz,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                                                mainScreenLayout.createSequentialGroup()
                                                                                                                                                                                                                                .addComponent(Label500Hz,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                                26,
                                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                .addComponent(Label1kHz)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                .addComponent(Label2kHz)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                .addComponent(Label4kHz)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                .addComponent(Label8kHz)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                .addComponent(Label16kHz)))))
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                .addComponent(reverbButton,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                .addComponent(reverbListContainer)))
                                                                                                                .addComponent(timeSongSlider,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                                                .addGroup(mainScreenLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(musicPictureContainer,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                409,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(mainScreenLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addComponent(currentTimeLabel,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                161,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                                                .addComponent(totalTimeLabel,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                20,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                .addComponent(timeSlashLabel,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                Short.MAX_VALUE))
                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                .addComponent(genreLabel)
                                                                                                                                                                                                .addGap(110, 110,
                                                                                                                                                                                                                110))
                                                                                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                                mainScreenLayout.createSequentialGroup()
                                                                                                                                                                                                                .addComponent(hereGenreLabel,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                189,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                .addComponent(viewsLabel)
                                                                                                                                                                                .addComponent(currentViewsLabel))
                                                                                                                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                                                                                                .addGap(25, 25, 25))
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                mainScreenLayout.createSequentialGroup()
                                                                                                                                                .addComponent(producerAuthorLabel,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addGap(14, 14, 14))
                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(authorLabel,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(uploadedByLabel,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(nicknameAndDateLabel,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(currentTotalTimeLabel,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                375,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                                                                .addGroup(mainScreenLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(mainScreenLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                .addComponent(peopleButton,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                77,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(musicButton,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                77,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(accountButton,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                77,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(themeButton))
                                                                                .addComponent(mainScreenButton,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                77,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap()));
                mainScreenLayout.setVerticalGroup(
                                mainScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(mainScreenLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(mainScreenLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(mainScreenLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(logoLabel,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(listeningNowLabel)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(currentSongNameLabel)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(previousListensLabel)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(previousListens)
                                                                                                .addContainerGap())
                                                                                .addGroup(mainScreenLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(mainScreenLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(producerAuthorLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                39,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(peopleButton,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                35,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(authorLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                39,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(musicButton,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                35,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(uploadedByLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                39,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(accountButton,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                35,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(nicknameAndDateLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                39,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(mainScreenButton,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                35,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(genreLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                39,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(viewsLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                39,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(hereGenreLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                39,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(currentViewsLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                39,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addGap(7, 7, 7)
                                                                                                                                .addComponent(currentTotalTimeLabel,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                39,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(currentTimeLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                39,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(totalTimeLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                39,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(timeSlashLabel,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                39,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                                .addComponent(musicPictureContainer,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                359,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(mainScreenLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(currentSongTitleLabel)
                                                                                                                                .addGap(4, 4, 4)
                                                                                                                                .addComponent(timeSongSlider,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(randomButton,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                23,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                                .addComponent(pauseLabel)
                                                                                                                                                                .addComponent(loadButton)
                                                                                                                                                                .addComponent(playButton)
                                                                                                                                                                .addComponent(loopButton)))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(volumeButton)
                                                                                                                                                .addComponent(panningButton)
                                                                                                                                                .addComponent(equalizerButton)
                                                                                                                                                .addComponent(reverbButton))
                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addGap(6, 6, 6)
                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                .addComponent(gainSlider,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                0,
                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                .addGap(22, 22, 22))
                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                                                .addComponent(Slider16kHz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                0,
                                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                                .addComponent(Slider8kHz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                0,
                                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                                .addComponent(Slider4kHz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                0,
                                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                                .addComponent(Slider2kHz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                0,
                                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                                .addComponent(Slider1kHz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                0,
                                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                                .addComponent(Slider500Hz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                0,
                                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                                .addComponent(Slider250Hz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                0,
                                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                                .addComponent(Slider125Hz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                0,
                                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                                .addComponent(Slider64Hz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                0,
                                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                                .addComponent(Slider32Hz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                0,
                                                                                                                                                                                                                                Short.MAX_VALUE))
                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                                                .addComponent(Label125Hz,
                                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                11,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                                                                                                .addComponent(Label16kHz)
                                                                                                                                                                                                                                .addComponent(Label8kHz)
                                                                                                                                                                                                                                .addComponent(Label4kHz)
                                                                                                                                                                                                                                .addComponent(Label2kHz)
                                                                                                                                                                                                                                .addComponent(Label1kHz)
                                                                                                                                                                                                                                .addComponent(Label500Hz)
                                                                                                                                                                                                                                .addComponent(Label250Hz)
                                                                                                                                                                                                                                .addComponent(Label64Hz)
                                                                                                                                                                                                                                .addComponent(Label32Hz)))
                                                                                                                                                                                                .addGap(3, 3, 3))
                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                                                .addComponent(leftSlider,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                0,
                                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                                .addComponent(rightSlider,
                                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                                0,
                                                                                                                                                                                                                                Short.MAX_VALUE))
                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                                                                                .addComponent(rightLabel)
                                                                                                                                                                                                                .addComponent(leftLabel)
                                                                                                                                                                                                                .addComponent(gainLabel))
                                                                                                                                                                                                .addContainerGap())))
                                                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                .addComponent(reverbListContainer,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                151,
                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                .addContainerGap())))
                                                                                                                .addGroup(mainScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                                                                .addComponent(themeButton)))))));

                chooseMusic.setText("Choose music");
                chooseMusic.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                chooseMusicMouseClicked(evt);
                        }
                });
                menu.add(chooseMusic);

                changeAvatar.setText("");
                changeAvatar.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
                        public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                                changeAvatarMenuKeyPressed(evt);
                        }

                        public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
                        }

                        public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
                        }
                });
                changeAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                changeAvatarMouseClicked(evt);
                        }
                });
                menu.add(changeAvatar);

                setJMenuBar(menu);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(mainScreen, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(mainScreen, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

                pack();
        }// </editor-fold>

        private void volumeButtonActionPerformed(java.awt.event.ActionEvent evt) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-25.0f);
        }

        private void panningButtonActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO add your handling code here:
        }

        private void equalizerButtonActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO add your handling code here:
        }

        private void reverbButtonActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO add your handling code here:
        }

        private void peopleButtonActionPerformed(java.awt.event.ActionEvent evt) {
                if (dark == false & light == true) {
                        people.dark = false;
                        people.light = true;
                        people.Theme();
                } else {
                        people.dark = true;
                        people.light = false;
                        people.Theme();
                }
                this.setContentPane(people);
                this.repaint();
                this.revalidate();
        }

        private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {

                this.clip.start();
        }

        private void themeButtonActionPerformed(java.awt.event.ActionEvent evt) {
                if (light == true & dark == false) {
                        dark = true;
                        light = false;
                        FlatDarkLaf.setup();
                        SwingUtilities.updateComponentTreeUI(mainScreen);
                } else if (light == false & dark == true) {
                        dark = false;
                        light = true;
                        FlatLightLaf.setup();
                        SwingUtilities.updateComponentTreeUI(mainScreen);
                }
        }

        private void accountButtonActionPerformed(java.awt.event.ActionEvent evt) {
                if (dark == false & light == true) {
                        account.dark = false;
                        account.light = true;
                        account.Theme();
                } else {
                        account.dark = true;
                        account.light = false;
                        account.Theme();
                }
                this.setContentPane(account);
                this.repaint();
                this.revalidate();
        }

        private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {
                this.loadMusic();
        }

        private void mainScreenButtonActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO add your handling code here:
        }

        private void musicButtonActionPerformed(java.awt.event.ActionEvent evt) {
                if (dark == false & light == true) {
                        music.dark = false;
                        music.light = true;
                        music.Theme();
                } else {
                        music.dark = true;
                        music.light = false;
                        music.Theme();
                }
                this.setContentPane(music);
                this.repaint();
                this.revalidate();
        }

        private void pauseLabelActionPerformed(java.awt.event.ActionEvent evt) {
                this.clip.stop();
        }

        private void chooseMusicMouseClicked(java.awt.event.MouseEvent evt) {
                MusicChooser musicChooserNew = new MusicChooser();
                musicChooserNew.setVisible(true);
                this.musicChooser = musicChooserNew;
        }

        private void changeAvatarMenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                //
        }

        private void changeAvatarMouseClicked(java.awt.event.MouseEvent evt) {
                AvatarChooser avatarChooser = new AvatarChooser();
                avatarChooser.setVisible(true);
        }

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
                // (optional) ">
                /*
                 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
                 * look and feel.
                 * For details see
                 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
                 */
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                                        .getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ClassNotFoundException ex) {
                        java.util.logging.Logger.getLogger(MainScreen.class.getName())
                                        .log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(MainScreen.class.getName())
                                        .log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(MainScreen.class.getName())
                                        .log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(MainScreen.class.getName())
                                        .log(java.util.logging.Level.SEVERE, null, ex);
                }
                // </editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new MainScreen().setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify
        private javax.swing.JLabel Label125Hz;
        private javax.swing.JLabel Label16kHz;
        private javax.swing.JLabel Label1kHz;
        private javax.swing.JLabel Label250Hz;
        private javax.swing.JLabel Label2kHz;
        private javax.swing.JLabel Label32Hz;
        private javax.swing.JLabel Label4kHz;
        private javax.swing.JLabel Label500Hz;
        private javax.swing.JLabel Label64Hz;
        private javax.swing.JLabel Label8kHz;
        private javax.swing.JSlider Slider125Hz;
        private javax.swing.JSlider Slider16kHz;
        private javax.swing.JSlider Slider1kHz;
        private javax.swing.JSlider Slider250Hz;
        private javax.swing.JSlider Slider2kHz;
        private javax.swing.JSlider Slider32Hz;
        private javax.swing.JSlider Slider4kHz;
        private javax.swing.JSlider Slider500Hz;
        private javax.swing.JSlider Slider64Hz;
        private javax.swing.JSlider Slider8kHz;
        private javax.swing.JButton accountButton;
        private javax.swing.JLabel authorLabel;
        private javax.swing.JMenu changeAvatar;
        private javax.swing.JMenu chooseMusic;
        private javax.swing.JLabel currentSongNameLabel;
        private javax.swing.JLabel currentSongTitleLabel;
        private javax.swing.JLabel currentTimeLabel;
        private javax.swing.JLabel currentTotalTimeLabel;
        private javax.swing.JLabel currentViewsLabel;
        private javax.swing.JToggleButton equalizerButton;
        private javax.swing.JLabel gainLabel;
        private javax.swing.JSlider gainSlider;
        private javax.swing.JLabel genreLabel;
        private javax.swing.JLabel hereGenreLabel;
        private javax.swing.JEditorPane jEditorPane1;
        private javax.swing.JScrollPane jScrollPane5;
        private javax.swing.JLabel leftLabel;
        private javax.swing.JSlider leftSlider;
        private javax.swing.JLabel listeningNowLabel;
        private javax.swing.JButton loadButton;
        private javax.swing.JLabel logoLabel;
        private javax.swing.JToggleButton loopButton;
        public javax.swing.JPanel mainScreen;
        private javax.swing.JButton mainScreenButton;
        public javax.swing.JMenuBar menu;
        private javax.swing.JButton musicButton;
        private javax.swing.JEditorPane musicPicture;
        private javax.swing.JScrollPane musicPictureContainer;
        private javax.swing.JLabel nicknameAndDateLabel;
        private javax.swing.JToggleButton panningButton;
        private javax.swing.JButton pauseLabel;
        public javax.swing.JButton peopleButton;
        private javax.swing.JButton playButton;
        private javax.swing.JScrollPane previousListens;
        private javax.swing.JLabel previousListensLabel;
        private javax.swing.JList<String> previousListensList;
        private javax.swing.JLabel producerAuthorLabel;
        private javax.swing.JButton randomButton;
        private javax.swing.JToggleButton reverbButton;
        private javax.swing.JList<String> reverbList;
        private javax.swing.JScrollPane reverbListContainer;
        private javax.swing.JLabel rightLabel;
        private javax.swing.JSlider rightSlider;
        public javax.swing.JRadioButton themeButton;
        private javax.swing.JLabel timeSlashLabel;
        private javax.swing.JSlider timeSongSlider;
        private javax.swing.JLabel totalTimeLabel;
        private javax.swing.JLabel uploadedByLabel;
        private javax.swing.JLabel viewsLabel;
        private javax.swing.JToggleButton volumeButton;
        // End of variables declaration
}
