/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package client.GUI;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;

import client.GUI.guiListeners.SongListSelectionListener;
import client.Music.MusicManager;

/**
 *
 * @author Adam
 */
public class Music extends javax.swing.JPanel {

	MainScreen mainScreenWindow;
	boolean light = false;
	boolean dark = true;

	public Music(MainScreen mainScreenParam) {
		mainScreenWindow = mainScreenParam;
		initComponents();
		this.peopleButton.setIcon(new ImageIcon("src/main/java/client/GUI/PeoplePAP.png"));
		this.musicButton.setIcon(new ImageIcon("src/main/java/client/GUI/MusicPAP.png"));
		this.accountButton.setIcon(new ImageIcon("src/main/java/client/GUI/AccountSettingsPAP.png"));
		mainScreenButton.setIcon(new ImageIcon("src/main/java/client/GUI/MainScreenPAP.png"));
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

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();
		logoLabel = new javax.swing.JLabel();
		addSongLabel = new javax.swing.JLabel();
		titleLabel = new javax.swing.JLabel();
		title = new javax.swing.JTextField();
		producerLabel = new javax.swing.JLabel();
		uploadedLabel = new javax.swing.JLabel();
		genreLabel = new javax.swing.JLabel();
		uploaded = new javax.swing.JTextField();
		producer = new javax.swing.JTextField();
		genre = new javax.swing.JTextField();
		uploadButton = new javax.swing.JButton();
		removeSongLabel = new javax.swing.JLabel();
		songNameLabel = new javax.swing.JLabel();
		songName = new javax.swing.JTextField();
		rewriteLabel = new javax.swing.JLabel();
		rewriteCodeLabel = new javax.swing.JLabel();
		rewrite = new javax.swing.JTextField();
		disclaimerLabel = new javax.swing.JLabel();
		removeButton = new javax.swing.JButton();
		uploadedByYouLabel = new javax.swing.JLabel();
		uploadedByYouContainer = new javax.swing.JScrollPane();
		uploadedByYouList = new javax.swing.JList<>();
		favouritesLabel = new javax.swing.JLabel();
		favouritesContainer = new javax.swing.JScrollPane();
		favouritesList = new javax.swing.JList<>();
		mostPopularContainer = new javax.swing.JScrollPane();
		mostPopularList = new javax.swing.JList<>();
		mostPopularLabel = new javax.swing.JLabel();
		previousListensContainer = new javax.swing.JScrollPane();
		previousListensList = new javax.swing.JList<>();
		previousListensLabel = new javax.swing.JLabel();
		chooseSongLabel = new javax.swing.JLabel();
		chooseSongContainer = new javax.swing.JScrollPane();
		chooseSongList = new javax.swing.JList<>();
		viewsLabel = new javax.swing.JLabel();
		views = new javax.swing.JLabel();
		uploadedOnLabel = new javax.swing.JLabel();
		uploadedOn = new javax.swing.JLabel();
		uploadedByLabel = new javax.swing.JLabel();
		uploadedBy = new javax.swing.JLabel();
		genreLabel2 = new javax.swing.JLabel();
		genre2 = new javax.swing.JLabel();
		popularityLabel = new javax.swing.JLabel();
		popularity = new javax.swing.JLabel();
		lastPlayedLabel = new javax.swing.JLabel();
		lastPlayed = new javax.swing.JLabel();
		musicButton = new javax.swing.JButton();
		accountButton = new javax.swing.JButton();
		mainScreenButton = new javax.swing.JButton();
		peopleButton = new javax.swing.JButton();
		loadedMusicFileLabel = new javax.swing.JLabel();
		loadedMusicFile = new javax.swing.JLabel();

		jMenu1.setText("File");
		jMenuBar1.add(jMenu1);

		jMenu2.setText("Edit");
		jMenuBar1.add(jMenu2);

		logoLabel.setFont(new java.awt.Font("Segoe UI Semilight", 3, 48)); // NOI18N
		logoLabel.setText("ConnectedByMusic");

		addSongLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
		addSongLabel.setText("Add song:");

		titleLabel.setText("Title:");

		title.setText("jTextField1");

		producerLabel.setText("Producer:");

		uploadedLabel.setText("Uploaded by:");

		genreLabel.setText("Genre:");

		uploaded.setText("jTextField1");

		producer.setText("jTextField1");

		genre.setText("jTextField1");

		uploadButton.setText("Upload");
		uploadButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				uploadButtonActionPerformed(evt);
			}
		});

		removeSongLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
		removeSongLabel.setText("Remove song:");

		songNameLabel.setText("Song name:");

		songName.setText("jTextField1");

		rewriteLabel.setText("Rewrite:");

		rewriteCodeLabel.setText("jLabel3");

		rewrite.setText("jTextField1");

		disclaimerLabel.setText("Disclaimer: You can only remove songs that have been uploaded by you!");

		removeButton.setText("Remove");
		removeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeButtonActionPerformed(evt);
			}
		});

		uploadedByYouLabel.setText("Uploaded by you:");

		uploadedByYouList.setModel(new javax.swing.AbstractListModel<String>() {
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

			public int getSize() {
				return strings.length;
			}

			public String getElementAt(int i) {
				return strings[i];
			}
		});
		uploadedByYouContainer.setViewportView(uploadedByYouList);

		favouritesLabel.setText("Favourites:");

		favouritesList.setModel(new javax.swing.AbstractListModel<String>() {
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

			public int getSize() {
				return strings.length;
			}

			public String getElementAt(int i) {
				return strings[i];
			}
		});
		favouritesContainer.setViewportView(favouritesList);

		mostPopularList.setModel(new javax.swing.AbstractListModel<String>() {
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

			public int getSize() {
				return strings.length;
			}

			public String getElementAt(int i) {
				return strings[i];
			}
		});
		mostPopularContainer.setViewportView(mostPopularList);

		mostPopularLabel.setText("Most popular:");

		previousListensList.setModel(new javax.swing.AbstractListModel<String>() {
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

			public int getSize() {
				return strings.length;
			}

			public String getElementAt(int i) {
				return strings[i];
			}
		});
		previousListensContainer.setViewportView(previousListensList);

		previousListensLabel.setText("Previously listened:");

		chooseSongLabel.setText("Choose song:");

		// TODO song list shoul be updated after uploading/removing files
		MusicManager.updateUserSongsList();
		var songNameSet = MusicManager.getUserSongsData().keySet();

		String[] songNames = new String[songNameSet.size()];
		int i = 0;
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

		chooseSongList.addListSelectionListener(mainScreenWindow.songListListenerInstance);

		chooseSongContainer.setViewportView(chooseSongList);

		viewsLabel.setText("Views:");

		views.setText("jLabel10");

		uploadedOnLabel.setText("Uploaded on:");

		uploadedOn.setText("jLabel10");

		uploadedByLabel.setText("Uploaded by:");

		uploadedBy.setText("jLabel11");

		genreLabel2.setText("Genre:");

		genre2.setText("jLabel14");

		popularityLabel.setText("Popularity:");

		popularity.setText("jLabel15");

		lastPlayedLabel.setText("Last played:");

		lastPlayed.setText("jLabel16");

		musicButton.setIcon(new javax.swing.ImageIcon("src/main/java/client/GUI/MusicPAP.png")); // NOI18N
		musicButton.setText("jButton1");
		musicButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				musicButtonActionPerformed(evt);
			}
		});

		accountButton.setIcon(new javax.swing.ImageIcon("src/main/java/client/GUI/AccountSettingsPAP.png")); // NOI18N
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

		peopleButton.setIcon(new javax.swing.ImageIcon("src/main/java/client/GUI/PeoplePAP.png")); // NOI18N
		peopleButton.setText("jButton1");
		peopleButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				peopleButtonActionPerformed(evt);
			}
		});

		loadedMusicFileLabel.setText("Loaded music file:");

		loadedMusicFile.setText("jLabel28");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addComponent(logoLabel).addGap(0, 0, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGroup(layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(uploadedLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(producerLabel, javax.swing.GroupLayout.Alignment.LEADING,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(genreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 96,
												javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(6, 6, 6)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(uploaded, javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(producer, javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(title).addComponent(genre)))
								.addGroup(layout.createSequentialGroup().addComponent(loadedMusicFileLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(loadedMusicFile, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(layout.createSequentialGroup().addComponent(rewriteLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(rewriteCodeLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(songName).addComponent(rewrite)
								.addGroup(layout.createSequentialGroup()
										.addComponent(disclaimerLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 143,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(addSongLabel)
								.addGroup(layout.createSequentialGroup().addGap(459, 459, 459).addComponent(
										uploadButton, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
								.addComponent(removeSongLabel).addComponent(songNameLabel,
										javax.swing.GroupLayout.PREFERRED_SIZE, 103,
										javax.swing.GroupLayout.PREFERRED_SIZE))))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup()
						.addComponent(chooseSongContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 185,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
								.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(viewsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(uploadedOnLabel).addComponent(uploadedByLabel))
								.addGap(43, 43, 43)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addComponent(uploadedBy, javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(uploadedOn, javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.PREFERRED_SIZE, 199,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addComponent(views, javax.swing.GroupLayout.PREFERRED_SIZE, 156,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(genreLabel2).addComponent(popularityLabel)
												.addComponent(lastPlayedLabel))
										.addGap(53, 53, 53)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(popularity, javax.swing.GroupLayout.PREFERRED_SIZE, 152,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(lastPlayed, javax.swing.GroupLayout.PREFERRED_SIZE, 152,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(genre2, javax.swing.GroupLayout.PREFERRED_SIZE, 177,
														javax.swing.GroupLayout.PREFERRED_SIZE)))))
						.addComponent(chooseSongLabel)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(uploadedByYouLabel).addComponent(uploadedByYouContainer,
												javax.swing.GroupLayout.PREFERRED_SIZE, 140,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(favouritesLabel).addComponent(favouritesContainer,
												javax.swing.GroupLayout.PREFERRED_SIZE, 140,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(mostPopularLabel).addComponent(mostPopularContainer,
												javax.swing.GroupLayout.PREFERRED_SIZE, 140,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(previousListensLabel).addComponent(previousListensContainer,
												javax.swing.GroupLayout.PREFERRED_SIZE, 141,
												javax.swing.GroupLayout.PREFERRED_SIZE))))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(musicButton, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(accountButton, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(mainScreenButton, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(peopleButton, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(addSongLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(loadedMusicFileLabel).addComponent(loadedMusicFile))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(titleLabel).addComponent(title,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(producerLabel).addComponent(producer,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(uploadedLabel).addComponent(uploaded,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(genreLabel)
												.addComponent(genre, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(9, 9, 9).addComponent(uploadButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(removeSongLabel).addGap(7, 7, 7)
										.addComponent(songNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(songName, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(rewriteLabel).addComponent(rewriteCodeLabel))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(rewrite, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(disclaimerLabel).addComponent(removeButton))
										.addGap(0, 296, Short.MAX_VALUE))
								.addGroup(layout.createSequentialGroup().addGap(12, 12, 12)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup().addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addGroup(layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(uploadedByYouLabel)
																.addComponent(favouritesLabel)
																.addComponent(mostPopularLabel))
														.addComponent(
																previousListensLabel,
																javax.swing.GroupLayout.Alignment.TRAILING))
														.addGroup(layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(layout.createSequentialGroup().addGap(7, 7, 7)
																		.addComponent(uploadedByYouContainer,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				385, Short.MAX_VALUE))
																.addGroup(layout.createSequentialGroup()
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(mostPopularContainer)
																				.addComponent(
																						favouritesContainer)
																				.addComponent(
																						previousListensContainer,
																						javax.swing.GroupLayout.Alignment.TRAILING))))
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(chooseSongLabel)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(layout.createSequentialGroup().addGroup(layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(views,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(viewsLabel,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(uploadedOnLabel,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						uploadedOn,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(uploadedByLabel,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						uploadedBy,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(genreLabel2,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(genre2,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(popularityLabel,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						popularity,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(lastPlayedLabel,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(lastPlayed,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																		.addGap(29, 29, 29))
																.addComponent(
																		chooseSongContainer,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 155,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGroup(layout.createSequentialGroup()
														.addComponent(peopleButton,
																javax.swing.GroupLayout.PREFERRED_SIZE, 35,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(musicButton,
																javax.swing.GroupLayout.PREFERRED_SIZE, 35,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(12, 12, 12)
														.addComponent(accountButton,
																javax.swing.GroupLayout.PREFERRED_SIZE, 35,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(mainScreenButton,
																javax.swing.GroupLayout.PREFERRED_SIZE, 35,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(0, 515, Short.MAX_VALUE)))))
						.addContainerGap()));
	}// </editor-fold>

	private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {

	}

	private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {

	}

	private void musicButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
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

	private void peopleButtonActionPerformed(java.awt.event.ActionEvent evt) {
		mainScreenWindow.setContentPane(mainScreenWindow.people);
		mainScreenWindow.repaint();
		mainScreenWindow.revalidate();
	}

	// Variables declaration - do not modify
	private javax.swing.JButton accountButton;
	private javax.swing.JLabel addSongLabel;
	private javax.swing.JScrollPane chooseSongContainer;
	private javax.swing.JLabel chooseSongLabel;
	private javax.swing.JList<String> chooseSongList;
	private javax.swing.JLabel disclaimerLabel;
	private javax.swing.JScrollPane favouritesContainer;
	private javax.swing.JLabel favouritesLabel;
	private javax.swing.JList<String> favouritesList;
	private javax.swing.JTextField genre;
	private javax.swing.JLabel genre2;
	private javax.swing.JLabel genreLabel;
	private javax.swing.JLabel genreLabel2;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JLabel lastPlayed;
	private javax.swing.JLabel lastPlayedLabel;
	private javax.swing.JLabel loadedMusicFile;
	private javax.swing.JLabel loadedMusicFileLabel;
	private javax.swing.JLabel logoLabel;
	private javax.swing.JButton mainScreenButton;
	private javax.swing.JScrollPane mostPopularContainer;
	private javax.swing.JLabel mostPopularLabel;
	private javax.swing.JList<String> mostPopularList;
	private javax.swing.JButton musicButton;
	private javax.swing.JButton peopleButton;
	private javax.swing.JLabel popularity;
	private javax.swing.JLabel popularityLabel;
	private javax.swing.JScrollPane previousListensContainer;
	private javax.swing.JLabel previousListensLabel;
	private javax.swing.JList<String> previousListensList;
	private javax.swing.JTextField producer;
	private javax.swing.JLabel producerLabel;
	private javax.swing.JButton removeButton;
	private javax.swing.JLabel removeSongLabel;
	private javax.swing.JTextField rewrite;
	private javax.swing.JLabel rewriteCodeLabel;
	private javax.swing.JLabel rewriteLabel;
	private javax.swing.JTextField songName;
	private javax.swing.JLabel songNameLabel;
	private javax.swing.JTextField title;
	private javax.swing.JLabel titleLabel;
	private javax.swing.JButton uploadButton;
	private javax.swing.JTextField uploaded;
	private javax.swing.JLabel uploadedBy;
	private javax.swing.JLabel uploadedByLabel;
	private javax.swing.JScrollPane uploadedByYouContainer;
	private javax.swing.JLabel uploadedByYouLabel;
	private javax.swing.JList<String> uploadedByYouList;
	private javax.swing.JLabel uploadedLabel;
	private javax.swing.JLabel uploadedOn;
	private javax.swing.JLabel uploadedOnLabel;
	private javax.swing.JLabel views;
	private javax.swing.JLabel viewsLabel;
	// End of variables declaration
}
