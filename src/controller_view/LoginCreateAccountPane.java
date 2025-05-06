// Danilo Malo-Molina and Kade Dean

package controller_view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.*;

public class LoginCreateAccountPane extends BorderPane {

	private VBox mainWrapper = new VBox();

	private GridPane loginWrapper = new GridPane();

	private Label nameLabel = new Label("Username:");
	private Label password = new Label("Password:");
	public Label msg = new Label("Login or Create Account");
	private TextField nameInputField = new TextField("");
	private PasswordField passwordInputField = new PasswordField();
	private Button loginButton = new Button("Login");
	private Button logoutButton = new Button("Logout");
	private Button createAccntButton = new Button("Create new Account");
	private ArrayList<JukeboxAccount> accounts = new ArrayList<>();
	private JukeboxAccount loggedInAccount;

	public LoginCreateAccountPane() {
		loadAccounts();
		if (accounts.size() == 0) {
			accounts.add(new JukeboxAccount("Chris", "1"));
			accounts.add(new JukeboxAccount("Devon", "22"));
			accounts.add(new JukeboxAccount("River", "333"));
			accounts.add(new JukeboxAccount("Ryan", "4444"));
		}

		loginWrapper.add(nameLabel, 0, 0);
		loginWrapper.add(nameInputField, 1, 0);
		loginWrapper.add(password, 0, 1);
		loginWrapper.add(passwordInputField, 1, 1);
		loginWrapper.add(loginButton, 2, 0);
		loginWrapper.add(logoutButton, 2, 1);
		loginWrapper.add(createAccntButton, 1, 2);
		loginWrapper.setVgap(10);
		loginWrapper.setHgap(20);
		loginWrapper.setAlignment(Pos.CENTER);
		loginWrapper.setAlignment(Pos.CENTER);

		mainWrapper.getChildren().addAll(msg, loginWrapper);
		mainWrapper.setAlignment(Pos.CENTER);
		mainWrapper.setAlignment(Pos.CENTER);
		mainWrapper.setSpacing(10);
		mainWrapper.minHeight(250);
		this.setTop(mainWrapper);

		// login button handler
		this.loginButton.setOnAction(event -> {
			String name = nameInputField.getText();
			String pass = passwordInputField.getText();
			nameInputField.setText("");
			passwordInputField.setText("");
			// handles null input
			if (name == "" || pass == "") {
				msg.setText("Invalid Username or password");
				return;
			}
			for (JukeboxAccount accnt : accounts) {
				if (accnt.getUserId().equals(name)) {
					if (accnt.attemptLogin(pass)) {
						msg.setText("Successfully logged in");
						loggedInAccount = accnt;
						return;
					}
					msg.setText("Wrong Password");
					return;
				}
			}
			msg.setText("There is no account with that username");
		});

		// create account button handler
		this.createAccntButton.setOnAction(event -> {
			String name = nameInputField.getText();
			String pass = passwordInputField.getText();
			if (name == "" || pass == "") {
				msg.setText("Invalid Username or password");
				return;
			}
			for (JukeboxAccount accnt : accounts) {
				if (accnt.getUserId().equals(name)) {
					msg.setText("An account with this username already exists");
					return;
				}
			}
			loggedInAccount = new JukeboxAccount(name, pass);
			accounts.add(loggedInAccount);
			msg.setText("Account created!");
		});

		// logout button handler
		this.logoutButton.setOnAction(event -> {
			if (loggedInAccount == null) {
				msg.setText("No Account is logged in!");
				return;
			}
			msg.setText("Logged Out!");
			loggedInAccount = null;
		});
	}

	/**
	 * Checks to see if user can play song before allowing a song to be played
	 * 
	 * @return true if user can play song, false otherwise
	 */
	public boolean userPassesRequirements() {
		if (loggedInAccount == null) {
			msg.setText("Login First");
			return false;
		}
		if (!loggedInAccount.canPlaySong()) {
			msg.setText("You've used all your songs for today!");
			return false;
		}
		return true;
	}

	/**
	 * Returns the account that is singed in. Returns null if there is no account
	 * currently logged in
	 * 
	 * @return JukeboxAccount, null
	 */
	public JukeboxAccount getSignedIn() {
		return loggedInAccount;
	}

	public void saveAccounts() {
		String fileName = "accounts.ser";
		try {
			FileOutputStream bytesToDisk = new FileOutputStream(fileName);
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			// outFile understands the writeObject message.
			outFile.writeObject(accounts);
			outFile.close(); // Always close the output file!
		} catch (IOException ioe) {
			System.out.println("Write failed");
		}
//		
//		try {
//			FileOutputStream bytesToDisk = new FileOutputStream("accounts.ser");
//			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
//			outFile.writeObject(accounts);
//			outFile.close();
//		} catch (Exception e) {
//			System.out.println("Write failed");
//		}
	}

	public void loadAccounts() {
		try {
			FileInputStream rawBytes = new FileInputStream("accounts.ser");
			ObjectInputStream inFile = new ObjectInputStream(rawBytes);
			// Read the entire object from the file on disk. Casts required
			ArrayList<JukeboxAccount> temp = (ArrayList<JukeboxAccount>) inFile.readObject();
			for (JukeboxAccount item : temp) {
				accounts.add(item);
			}
			// Should close input files also
			inFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		try {
//			FileInputStream bytesFromDisk = new FileInputStream("accounts.ser");
//			ObjectInputStream inFile = new ObjectInputStream(bytesFromDisk);
//			accounts = (ArrayList<JukeboxAccount>) inFile.readObject();
//			inFile.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}