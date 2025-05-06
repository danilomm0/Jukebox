// Danilo Malo-Molina and Kade Dean

package controller_view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.*;

public class MusicPane extends BorderPane {


	private JukeboxAccount loggedInAccount;

	public MusicPane() {

		GridPane songButtonWrapper = new GridPane();
		Button songOneButton = new Button("Select Song 1");
		Button songTwoButton = new Button("Select Song 2");
		songButtonWrapper.add(songOneButton, 0, 0);
		songButtonWrapper.add(songTwoButton, 1, 0);
		songButtonWrapper.setVgap(10);
		songButtonWrapper.setHgap(20);
		songButtonWrapper.setAlignment(Pos.CENTER);
		songButtonWrapper.setAlignment(Pos.CENTER);
		// Song button handlers
		songOneButton.setOnAction(event -> {
			//if (userPassesRequirements()) {
				// TODO: Add song functionality
				/**
				 * Add it here
				 */
				//msg.setText("Song Played! Plays Left: " + (loggedInAccount.getSongsPlayed() - 3));
			//}

		});
		songTwoButton.setOnAction(event -> {
			//if (userPassesRequirements()) {
				// TODO: Add song functionality
				/**
				 * Add it here
				 */
				//msg.setText("Song Played! Plays Left: " + (loggedInAccount.getSongsPlayed() - 3));
			//}
		});
	}
}