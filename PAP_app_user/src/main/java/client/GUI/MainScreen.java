/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package client.GUI;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import client.Chat.Chat;
import client.GUI.guiListeners.MusicEventListener;
import client.GUI.guiListeners.SongListSelectionListener;
import client.GUI.guiListeners.SwitchConversationFromMainWindwoListener;
import client.Music.MusicManager;
import client.ServerConnectionConstants.ChatMessagesConstants;
import client.ServerConnectionConstants.ServerInformation;
import client.ServerConnector.ServerConnector;
import client.GUI.guiWorkers.SongTimeUpdater;

public class MainScreen extends javax.swing.JFrame {

	/**
	 * Creates new form MainScreen
	 */
	boolean dark = true;
	boolean light = false;
	People people;
	Music music;
	Account account;
	private ServerConnector serverConnector;
	MusicEventListener musicEventListenerInstance;
	SongListSelectionListener songListListenerInstance;
	SongTimeUpdater songTimeUpdaterInstance;
	MusicManager musicManagerInstance;
	public StringBuilder path = new StringBuilder();

	private Chat chat;

	public MainScreen() {
		JSONObject userInfo = new JSONObject();
		userInfo.put(ChatMessagesConstants.USER_ID.value(), 1);
		userInfo.put(ChatMessagesConstants.USERNAME.value(), "mikic202");
		userInfo.put(ChatMessagesConstants.EMAIL.value(), "mikolaj.chomanski@gmail.com");
		userInfo.put("profile_picture", "0");
		try {
			serverConnector = new ServerConnector(
					new Socket(ServerInformation.SERVER_IP.value(), ServerInformation.MAIN_PORT.intValue()));
		} catch (Exception e) {
			System.out.println(e);
		}
		chat = new Chat(userInfo, -1, serverConnector);
		FlatDarkLaf.setup();

		musicManagerInstance = new MusicManager(serverConnector,
				userInfo.getInt(ChatMessagesConstants.USER_ID.value()));
		this.musicEventListenerInstance = new MusicEventListener(userInfo.getInt(ChatMessagesConstants.USER_ID.value()),
				musicManagerInstance);
		chatsList.addListSelectionListener(new SwitchConversationFromMainWindwoListener(chat, new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				return null;
			}
		}));

		this.songListListenerInstance = new SongListSelectionListener();
		songTimeUpdaterInstance = new SongTimeUpdater(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				updateSongTime();
				return null;
			}
		});
		songTimeUpdaterInstance.execute();

		new Thread(new Runnable() {
			@Override
			public void run() {
				updateSongTime();
			}
		}).start();

		initComponents();
		this.peopleButton.setIcon(new ImageIcon("src/main/java/client/GUI/GuiResources/PeoplePAP.png"));
		this.musicButton.setIcon(new ImageIcon("src/main/java/client/GUI/GuiResources/MusicPAP.png"));
		this.accountButton.setIcon(new ImageIcon("src/main/java/client/GUI/GuiResources/AccountSettingsPAP.png"));
		char[] userPassword = { '1', '2', '3', '4', '5', '6', '7' };
		people = new People(this, chat, userPassword);
		music = new Music(this, serverConnector, chat);
		account = new Account(this, serverConnector, chat);
	}

	public MainScreen(ServerConnector serverConnector, JSONObject userInfo, char[] userPassword) {
		chat = new Chat(userInfo, -1, serverConnector);
		this.serverConnector = serverConnector;
		FlatDarkLaf.setup();
		musicManagerInstance = new MusicManager(serverConnector,
				userInfo.getInt(ChatMessagesConstants.USER_ID.value()));
		this.musicEventListenerInstance = new MusicEventListener(userInfo.getInt(ChatMessagesConstants.USER_ID.value()),
				musicManagerInstance);

		this.songListListenerInstance = new SongListSelectionListener();

		musicManagerInstance = new MusicManager(serverConnector,
				userInfo.getInt(ChatMessagesConstants.USER_ID.value()));
		this.musicEventListenerInstance = new MusicEventListener(userInfo.getInt(ChatMessagesConstants.USER_ID.value()),
				musicManagerInstance);
		this.songListListenerInstance = new SongListSelectionListener();
		initComponents();

		chatsList.addListSelectionListener(new SwitchConversationFromMainWindwoListener(chat, new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				return null;
			}
		}));
		songTimeUpdaterInstance = new SongTimeUpdater(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				updateSongTime();
				return null;
			}
		});
		songTimeUpdaterInstance.execute();

		new Thread(new Runnable() {
			@Override
			public void run() {
				updateSongTime();
			}
		}).start();

		this.peopleButton.setIcon(new ImageIcon("src/main/java/client/GUI/GuiResources/PeoplePAP.png"));
		this.musicButton.setIcon(new ImageIcon("src/main/java/client/GUI/GuiResources/MusicPAP.png"));
		this.accountButton.setIcon(new ImageIcon("src/main/java/client/GUI/GuiResources/AccountSettingsPAP.png"));
		people = new People(this, chat, userPassword);
		music = new Music(this, serverConnector, chat);
		account = new Account(this, serverConnector, chat);
	}

	public void updateSongTime() {
		ArrayList<Integer> currentTime = musicManagerInstance.getCurrentTime();
		ArrayList<Integer> totalTime = musicManagerInstance.getTotalTime();
		int percentage = musicManagerInstance.getPercentageOfSongPlayed();
		timeSlashLabel.setText((String.format(("%d: %d"), totalTime.get(1), totalTime.get(0))));
		currentTimeLabel.setText((String.format(("%d: %d"), currentTime.get(1), currentTime.get(0))));
		jProgressBar1.setValue(percentage);
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
		muteButton = new javax.swing.JToggleButton();
		volumeButton = new javax.swing.JToggleButton();
		gainSlider = new javax.swing.JSlider();
		peopleButton = new javax.swing.JButton();
		musicButton = new javax.swing.JButton();
		themeButton = new javax.swing.JRadioButton();
		accountButton = new javax.swing.JButton();
		loadButton = new javax.swing.JButton();
		mainScreenButton = new javax.swing.JButton();
		jProgressBar1 = new javax.swing.JProgressBar();
		leaveStreamButton1 = new javax.swing.JButton();
		chatsContainer = new javax.swing.JScrollPane();
		chatsList = new javax.swing.JList<>();
		joinStreamButton = new javax.swing.JButton();
		stopStreamButton = new javax.swing.JButton();
		startStreamButton = new javax.swing.JButton();
		currentSongTitleLabel1 = new javax.swing.JLabel();
		chatsLabel = new javax.swing.JLabel();
		currentSessionLabel = new javax.swing.JLabel();
		currentSongLabel = new javax.swing.JLabel();
		chatsContainer1 = new javax.swing.JScrollPane();
		chooseSongList = new javax.swing.JList<>();
		currentSongLabel1 = new javax.swing.JLabel();
		menu = new javax.swing.JMenuBar();
		chooseMusic = new javax.swing.JMenu();

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
		playButton.addActionListener(musicEventListenerInstance);

		pauseLabel.setText("Pause");
		pauseLabel.setToolTipText("");
		pauseLabel.addActionListener(musicEventListenerInstance);

		muteButton.setText("Mute");

		volumeButton.setText("Volume");
		volumeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				volumeButtonActionPerformed(evt);
			}
		});

		gainSlider.setOrientation(javax.swing.JSlider.VERTICAL);

		peopleButton.setIcon(new javax.swing.ImageIcon("src/main/java/client/GUI/GuiResources/PeoplePAP.png")); // NOI18N
		peopleButton.setText("jButton1");
		peopleButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				peopleButtonActionPerformed(evt);
			}
		});

		musicButton.setIcon(new javax.swing.ImageIcon("src/main/java/client/GUI/GuiResources/MusicPAP.png")); // NOI18N
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

		accountButton
				.setIcon(new javax.swing.ImageIcon("src/main/java/client/GUI/GuiResources/AccountSettingsPAP.png")); // NOI18N
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

		mainScreenButton.setIcon(new javax.swing.ImageIcon("src/main/java/client/GUI/GuiResources/MainScreenPAP.png")); // NOI18N
		mainScreenButton.setText("jButton1");
		mainScreenButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mainScreenButtonActionPerformed(evt);
			}
		});

		leaveStreamButton1.setText("Leave stream");
		leaveStreamButton1.addActionListener(musicEventListenerInstance);

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
		chatsContainer.setViewportView(chatsList);

		joinStreamButton.setText("Join stream");
		joinStreamButton.addActionListener(musicEventListenerInstance);

		stopStreamButton.setText("Stop stream");
		stopStreamButton.addActionListener(musicEventListenerInstance);

		startStreamButton.setText("Start stream");
		startStreamButton.addActionListener(musicEventListenerInstance);

		currentSongTitleLabel1.setText("jLabel3");

		chatsLabel.setText("Chats:");

		currentSessionLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
		currentSessionLabel.setText("Current session:");

		currentSongLabel.setText("Current song:");

		// TODO song list shoul be updated after uploading/removing files
		MusicManager.updateUserSongsList();
		var songNameSet = MusicManager.getUserSongsData().keySet();

		String[] songNames = new String[songNameSet.size()];
		i = 0;
		for (String name : songNameSet) {
			songNames[i++] = name;
		}

		chooseSongList.setModel(new javax.swing.AbstractListModel<String>() {
			String[] strings = songNames;

			public int getSize() {
				return strings.length;
			}

			public String getElementAt(int i) {
				return strings[i];
			}
		});
		chooseSongList.addListSelectionListener(songListListenerInstance);
		chatsContainer1.setViewportView(chooseSongList);

		currentSongLabel1.setText("Songs:");

		javax.swing.GroupLayout mainScreenLayout = new javax.swing.GroupLayout(mainScreen);
		mainScreen.setLayout(mainScreenLayout);
		mainScreenLayout.setHorizontalGroup(mainScreenLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(mainScreenLayout.createSequentialGroup().addGroup(mainScreenLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(mainScreenLayout.createSequentialGroup()
								.addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 426,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(mainScreenLayout.createSequentialGroup().addContainerGap().addGroup(mainScreenLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(mainScreenLayout.createSequentialGroup().addComponent(previousListens)
										.addGap(6, 6, 6))
								.addComponent(previousListensLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(mainScreenLayout.createSequentialGroup().addGroup(mainScreenLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(listeningNowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 420,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(currentSongNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 405,
												javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(0, 0, Short.MAX_VALUE)))))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(mainScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(mainScreenLayout.createSequentialGroup().addGroup(mainScreenLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(currentSongTitleLabel, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
												mainScreenLayout.createSequentialGroup().addGroup(mainScreenLayout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(loadButton,
																javax.swing.GroupLayout.PREFERRED_SIZE, 155,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(volumeButton,
																javax.swing.GroupLayout.PREFERRED_SIZE, 155,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(mainScreenLayout.createSequentialGroup()
																.addGap(67, 67, 67).addComponent(gainSlider,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addGroup(mainScreenLayout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING,
																		false)
																.addGroup(mainScreenLayout.createSequentialGroup()
																		.addGroup(mainScreenLayout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																				.addGroup(mainScreenLayout
																						.createSequentialGroup()
																						.addGroup(mainScreenLayout
																								.createParallelGroup(
																										javax.swing.GroupLayout.Alignment.LEADING)
																								.addComponent(
																										currentSessionLabel)
																								.addComponent(
																										chatsLabel))
																						.addGap(148, 148, 148))
																				.addGroup(mainScreenLayout
																						.createSequentialGroup()
																						.addGap(140, 140, 140)
																						.addComponent(
																								currentSongLabel)
																						.addGap(0, 0, Short.MAX_VALUE))
																				.addGroup(
																						javax.swing.GroupLayout.Alignment.TRAILING,
																						mainScreenLayout
																								.createSequentialGroup()
																								.addComponent(
																										chatsContainer,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										110,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addGroup(
																										mainScreenLayout
																												.createParallelGroup(
																														javax.swing.GroupLayout.Alignment.LEADING)
																												.addGroup(
																														mainScreenLayout
																																.createSequentialGroup()
																																.addPreferredGap(
																																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																		Short.MAX_VALUE)
																																.addGroup(
																																		mainScreenLayout
																																				.createParallelGroup(
																																						javax.swing.GroupLayout.Alignment.LEADING)
																																				.addComponent(
																																						joinStreamButton,
																																						javax.swing.GroupLayout.Alignment.TRAILING,
																																						javax.swing.GroupLayout.PREFERRED_SIZE,
																																						123,
																																						javax.swing.GroupLayout.PREFERRED_SIZE)
																																				.addComponent(
																																						stopStreamButton,
																																						javax.swing.GroupLayout.Alignment.TRAILING,
																																						javax.swing.GroupLayout.PREFERRED_SIZE,
																																						123,
																																						javax.swing.GroupLayout.PREFERRED_SIZE)
																																				.addComponent(
																																						startStreamButton,
																																						javax.swing.GroupLayout.Alignment.TRAILING,
																																						javax.swing.GroupLayout.PREFERRED_SIZE,
																																						123,
																																						javax.swing.GroupLayout.PREFERRED_SIZE)
																																				.addComponent(
																																						leaveStreamButton1,
																																						javax.swing.GroupLayout.Alignment.TRAILING,
																																						javax.swing.GroupLayout.PREFERRED_SIZE,
																																						123,
																																						javax.swing.GroupLayout.PREFERRED_SIZE)))
																												.addGroup(
																														mainScreenLayout
																																.createSequentialGroup()
																																.addGap(30,
																																		30,
																																		30)
																																.addComponent(
																																		currentSongTitleLabel1,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		117,
																																		javax.swing.GroupLayout.PREFERRED_SIZE)
																																.addGap(0,
																																		0,
																																		Short.MAX_VALUE)))))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGroup(mainScreenLayout
																				.createParallelGroup(
																						javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(chatsContainer1,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						110,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(currentSongLabel1)))
																.addGroup(mainScreenLayout.createSequentialGroup()
																		.addComponent(playButton,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				155,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(64, 64, 64)
																		.addComponent(pauseLabel,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				156,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(48, 48, 48).addComponent(muteButton,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				141,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
								.addGroup(mainScreenLayout.createSequentialGroup()
										.addComponent(musicPictureContainer, javax.swing.GroupLayout.PREFERRED_SIZE,
												409, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(mainScreenLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(mainScreenLayout.createSequentialGroup()
														.addGroup(mainScreenLayout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(mainScreenLayout.createSequentialGroup()
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
																.addGroup(mainScreenLayout.createSequentialGroup()
																		.addGroup(mainScreenLayout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(mainScreenLayout
																						.createSequentialGroup()
																						.addComponent(genreLabel)
																						.addGap(110, 110, 110))
																				.addGroup(
																						javax.swing.GroupLayout.Alignment.TRAILING,
																						mainScreenLayout
																								.createSequentialGroup()
																								.addComponent(
																										hereGenreLabel,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										189,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
																		.addGroup(mainScreenLayout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(viewsLabel)
																				.addComponent(currentViewsLabel))
																		.addGap(0, 0, Short.MAX_VALUE)))
														.addGap(25, 25, 25))
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainScreenLayout
														.createSequentialGroup()
														.addComponent(producerAuthorLabel,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addGap(14, 14, 14))
												.addGroup(mainScreenLayout.createSequentialGroup()
														.addComponent(authorLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED))
												.addGroup(mainScreenLayout.createSequentialGroup()
														.addComponent(uploadedByLabel,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED))
												.addGroup(mainScreenLayout.createSequentialGroup()
														.addComponent(nicknameAndDateLabel,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED))
												.addGroup(mainScreenLayout.createSequentialGroup()
														.addComponent(currentTotalTimeLabel,
																javax.swing.GroupLayout.DEFAULT_SIZE, 381,
																Short.MAX_VALUE)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
						.addGroup(mainScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(mainScreenLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(peopleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(musicButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(accountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(themeButton))
								.addComponent(mainScreenButton, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.PREFERRED_SIZE, 77,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		mainScreenLayout.setVerticalGroup(mainScreenLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(mainScreenLayout.createSequentialGroup().addContainerGap().addGroup(mainScreenLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(mainScreenLayout.createSequentialGroup()
								.addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(listeningNowLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(currentSongNameLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(previousListensLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(previousListens).addContainerGap())
						.addGroup(mainScreenLayout.createSequentialGroup().addGroup(mainScreenLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(mainScreenLayout.createSequentialGroup().addGroup(mainScreenLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(producerAuthorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(peopleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
												javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(mainScreenLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(authorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(musicButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(mainScreenLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(uploadedByLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
														39, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(accountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(mainScreenLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(nicknameAndDateLabel,
														javax.swing.GroupLayout.PREFERRED_SIZE, 39,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(mainScreenButton, javax.swing.GroupLayout.PREFERRED_SIZE,
														35, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(mainScreenLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(genreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(viewsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(mainScreenLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(hereGenreLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
														39, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(currentViewsLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
														39, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(7, 7, 7)
										.addComponent(currentTotalTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(mainScreenLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(currentTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
														39, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(totalTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
														39, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(timeSlashLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
														39, javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addComponent(musicPictureContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 359,
										javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(mainScreenLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addGroup(mainScreenLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
												.addComponent(themeButton))
										.addGroup(mainScreenLayout.createSequentialGroup()
												.addComponent(currentSongTitleLabel)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 18,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(mainScreenLayout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(pauseLabel).addComponent(loadButton)
														.addComponent(playButton).addComponent(muteButton))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(mainScreenLayout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(mainScreenLayout.createSequentialGroup()
																.addComponent(volumeButton)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(gainSlider,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 0,
																		Short.MAX_VALUE))
														.addGroup(mainScreenLayout.createSequentialGroup()
																.addGroup(mainScreenLayout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(currentSessionLabel)
																		.addComponent(currentSongLabel)
																		.addComponent(currentSongLabel1))
																.addGap(4, 4, 4)
																.addGroup(mainScreenLayout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(mainScreenLayout
																				.createSequentialGroup()
																				.addComponent(chatsLabel)
																				.addPreferredGap(
																						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(chatsContainer,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						0, Short.MAX_VALUE))
																		.addGroup(mainScreenLayout
																				.createSequentialGroup()
																				.addComponent(currentSongTitleLabel1,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addPreferredGap(
																						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(startStreamButton)
																				.addPreferredGap(
																						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(stopStreamButton)
																				.addPreferredGap(
																						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(joinStreamButton)
																				.addPreferredGap(
																						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(leaveStreamButton1))
																		.addComponent(chatsContainer1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				157, Short.MAX_VALUE))))
												.addContainerGap()))))));

		chooseMusic.setText("Choose music");
		chooseMusic.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				chooseMusicMouseClicked(evt);
			}
		});
		menu.add(chooseMusic);

		setJMenuBar(menu);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(mainScreen,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(mainScreen,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}// </editor-fold>

	private void volumeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// if (filePlaying == true) {
		// FloatControl gainControl = (FloatControl)
		// clip.getControl(FloatControl.Type.MASTER_GAIN);
		// gainControl.setValue(-25.0f);
		// }
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
		people.goBackToLatestConversation();
		this.setContentPane(people);
		this.repaint();
		this.revalidate();
	}

	private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// if (filePlaying == true) {
		// this.clip.start();
		// }
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
		// if (filePlaying == true) {
		// this.loadMusic();
		// }
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
		// if (filePlaying == true) {
		// this.clip.stop();
		// }
	}

	private void chooseMusicMouseClicked(java.awt.event.MouseEvent evt) {
		MusicChooser musicChooserNew = new MusicChooser(path);
		musicChooserNew.setVisible(true);
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
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
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
	private javax.swing.JButton accountButton;
	private javax.swing.JLabel authorLabel;
	private javax.swing.JScrollPane chatsContainer;
	private javax.swing.JScrollPane chatsContainer1;
	private javax.swing.JLabel chatsLabel;
	private javax.swing.JList<String> chatsList;
	private javax.swing.JList<String> chooseSongList;
	private javax.swing.JMenu chooseMusic;
	private javax.swing.JLabel currentSessionLabel;
	private javax.swing.JLabel currentSongLabel;
	private javax.swing.JLabel currentSongLabel1;
	private javax.swing.JLabel currentSongNameLabel;
	private javax.swing.JLabel currentSongTitleLabel;
	private javax.swing.JLabel currentSongTitleLabel1;
	private javax.swing.JLabel currentTimeLabel;
	private javax.swing.JLabel currentTotalTimeLabel;
	private javax.swing.JLabel currentViewsLabel;
	private javax.swing.JSlider gainSlider;
	private javax.swing.JLabel genreLabel;
	private javax.swing.JLabel hereGenreLabel;
	private javax.swing.JEditorPane jEditorPane1;
	private javax.swing.JProgressBar jProgressBar1;
	private javax.swing.JScrollPane jScrollPane5;
	private javax.swing.JButton joinStreamButton;
	private javax.swing.JButton leaveStreamButton1;
	private javax.swing.JLabel listeningNowLabel;
	private javax.swing.JButton loadButton;
	private javax.swing.JLabel logoLabel;
	public javax.swing.JPanel mainScreen;
	private javax.swing.JButton mainScreenButton;
	public javax.swing.JMenuBar menu;
	private javax.swing.JButton musicButton;
	private javax.swing.JEditorPane musicPicture;
	private javax.swing.JScrollPane musicPictureContainer;
	private javax.swing.JToggleButton muteButton;
	private javax.swing.JLabel nicknameAndDateLabel;
	private javax.swing.JButton pauseLabel;
	public javax.swing.JButton peopleButton;
	private javax.swing.JButton playButton;
	private javax.swing.JScrollPane previousListens;
	private javax.swing.JLabel previousListensLabel;
	private javax.swing.JList<String> previousListensList;
	private javax.swing.JLabel producerAuthorLabel;
	private javax.swing.JButton startStreamButton;
	private javax.swing.JButton stopStreamButton;
	public javax.swing.JRadioButton themeButton;
	private javax.swing.JLabel timeSlashLabel;
	private javax.swing.JLabel totalTimeLabel;
	private javax.swing.JLabel uploadedByLabel;
	private javax.swing.JLabel viewsLabel;
	private javax.swing.JToggleButton volumeButton;
	// End of variables declaration
}
