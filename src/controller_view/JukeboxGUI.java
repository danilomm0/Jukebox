// Danilo Malo-Molina and Kade Dean
package controller_view;

import java.util.Optional;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

public class JukeboxGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	LoginCreateAccountPane loginPane = new LoginCreateAccountPane();
	private BorderPane everything = new BorderPane();
	private PlayList playlist = new PlayList();
	private ListView<String> listview;
	private Label songList = new Label("Song List");
	private Label songQueue = new Label("Song Queue");
	private HBox hbox;
	private Button add = new Button("Play");
	private TableView<Song> table;
	private ObservableList<Song> observableSongs;

	@Override
	public void start(Stage primaryStage) throws Exception {
		addSongs();
		LayoutGUI();
		loadPlayListOrNot();
		loginPane.loadAccounts();
		Scene scene = new Scene(everything, 850, 650);
		add.setOnAction(event -> {
			int currentIndex = table.getSelectionModel().getSelectedIndex();
			if (loginPane.userPassesRequirements() == true) {
				loginPane.getSignedIn().addSongPlay();
				loginPane.msg.setText(loginPane.getSignedIn().getSongsPlayed() + " song(s) queued");
				playlist.queueUpNextSong(observableSongs.get(currentIndex));
				if (!playlist.songIsPlaying) {
					listview.getSelectionModel().selectFirst();
					playlist.play();
				}
			}
		});
		primaryStage.setScene(scene);
		primaryStage.setTitle("Jukebox");
		primaryStage.show();
		// an event on the closing the window
		primaryStage.setOnCloseRequest((event) -> {
			playlist.savePlayList();
			loginPane.saveAccounts();
		});
	}

	private void LayoutGUI() {
		table = new TableView<Song>();
		everything.setPadding(new Insets(10, 10, 10, 10));
		loginPane.setMaxHeight(200);
		songList.setStyle("-fx-font-size: 20; -fx-font-family: Verdana");
		songQueue.setStyle("-fx-font-size: 20; -fx-font-family: Verdana");

		hbox = new HBox(430, songList, songQueue);
		hbox.setPadding(new Insets(10));

		table.setItems(observableSongs);
		TableColumn<Song, String> titleCol = new TableColumn<Song, String>("Title");
		titleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Song, String> artistCol = new TableColumn<Song, String>("Artist");
		artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));
		TableColumn<Song, String> timeCol = new TableColumn<Song, String>("Time");
		timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
		titleCol.setMinWidth(175);
		artistCol.setMinWidth(175);
		timeCol.setMinWidth(20);
		table.getColumns().setAll(titleCol, artistCol, timeCol);
		table.setMinWidth(400);
		table.setMaxSize(400, 450);
		table.getSelectionModel().select(0);
		everything.setTop(hbox);
		everything.setLeft(table);
		everything.setCenter(add);
		everything.setBottom(loginPane);
	}

	private void addSongs() {
		observableSongs = FXCollections.observableArrayList();
		Song capture = new Song("Pokemon Capture", "Pikachu", "0:05");
		Song danse = new Song("Danse Macabre", "Kevin MacLeod", "0:37");
		Song determined = new Song("Determined Tumbao", "FreePlay Music", "0:20");
		Song longing = new Song("Longing In Their Hearts", "Michael O'Keefe/Bonnie Raitt", "4:48");
		Song loop = new Song("Loping Sting", "Kevin MacLeod", "0:05");
		Song swing = new Song("Swing Cheese", "FreePlay Music", "0:15");
		Song curtain = new Song("The Curtain Rises", "Kevin MacLeod", "0:28");
		Song fire = new Song("UntameableFire", "Pierre Langer", "4:42");
		observableSongs.add(capture);
		observableSongs.add(danse);
		observableSongs.add(determined);
		observableSongs.add(longing);
		observableSongs.add(loop);
		observableSongs.add(swing);
		observableSongs.add(curtain);
		observableSongs.add(fire);
	}

	private void loadPlayListOrNot() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Click cancel to start with an empty PlayList");
		alert.setContentText("Click OK to Load Previous Playlist");
		Optional<ButtonType> result = alert.showAndWait();
		listview = new ListView<String>(playlist.getPlayList());
		if (result.get() == ButtonType.OK) {
			playlist.loadPlayList();
			listview = new ListView<String>(playlist.getPlayList());
			if (!playlist.songIsPlaying) {
				listview.getSelectionModel().selectFirst();
				playlist.play();
			}
		}
		listview.setMaxHeight(450);
		listview.setMinWidth(300);
		everything.setRight(listview);
	}
}