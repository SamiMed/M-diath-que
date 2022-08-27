package vueEtControleur;

import java.io.Serializable;
import java.util.Optional;

import application.DemarrerAppli;
import donnees.Preposer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



/*Contient la logique de admin*/
public class TableauBordAdmin extends Pane {
	
	
	private static TableauBordAdmin instance = null;
	private Scene sceneAdministrateur = new Scene(this);
	private TableView<Preposer> tabViewPreposer = new TableView<Preposer>();
	private ObservableList<Preposer> obsPreposer = FXCollections.observableArrayList(Preposer.getArrPreposer());
	private Preposer prepCourant;
	private Connection connet = Connection.getInstance();
	private Stage stageAjouteprepo;
	private GridPane gpAjoutPreposer;
	private VBox vbAjoutlbl, vbAjoutOptions;
	private Button btnConfirmerPrepo, btnAnnuler;
	private boolean boolClear = false;
	
	
	public Scene creerInterfaceAdmin() {
		
		TableColumn<Preposer, String> colonneIdAdmin = new TableColumn<Preposer, String>("Numéro d'employé");
		TableColumn<Preposer, String> colonneNomAdmin = new TableColumn<Preposer, String>("Nom");
		TableColumn<Preposer, String> colonnePrenomAdmin = new TableColumn<Preposer, String>("Prénom");
		TableColumn<Preposer, String> colonneAdresseAdmin = new TableColumn<Preposer, String>("Adresse");
		TableColumn<Preposer, String> colonneNumTelAdmin = new TableColumn<Preposer, String>("Numéro de téléphone");

		
		
		colonneIdAdmin.setPrefWidth(120);
		colonneNomAdmin.setPrefWidth(150);
		colonnePrenomAdmin.setPrefWidth(120);
		colonneAdresseAdmin.setPrefWidth(170);
		colonneNumTelAdmin.setPrefWidth(130);

		
		colonneIdAdmin.setCellValueFactory(new PropertyValueFactory<>("strId"));
		colonneNomAdmin.setCellValueFactory(new PropertyValueFactory<>("strNom"));
		colonnePrenomAdmin.setCellValueFactory(new PropertyValueFactory<>("strPrenom"));
		colonneAdresseAdmin.setCellValueFactory(new PropertyValueFactory<>("strAddresse"));
		colonneNumTelAdmin.setCellValueFactory(new PropertyValueFactory<>("strNumTel"));

		
		
		tabViewPreposer.setItems(obsPreposer);
		tabViewPreposer.getColumns().addAll(colonneIdAdmin, colonneNomAdmin, colonnePrenomAdmin, colonneAdresseAdmin,colonneNumTelAdmin);

		tabViewPreposer.getSelectionModel().selectedItemProperty().addListener(ecouteurAdherent);
		VBox vbBoutons = new VBox(15);
		vbBoutons.setPadding(new Insets(15));
		Button btnAjoutPrep = new Button("Ajouter un préposé");
		Button btnSupprPrep = new Button("Supprimer un préposé");
		Button btnQuitter = new Button("Déconnexion");

		btnAjoutPrep.setMaxWidth(Double.MAX_VALUE);
		btnSupprPrep.setMaxWidth(Double.MAX_VALUE);
		btnQuitter.setMaxWidth(Double.MAX_VALUE);

		btnAjoutPrep.setOnAction(e -> {
			creerAjoutPrep();
			stageAjouteprepo.showAndWait();
		});
		btnSupprPrep.setOnAction(gestionSuppPrep);

		btnQuitter.setOnAction(e -> {
			tabViewPreposer.getColumns().clear();
			tabViewPreposer.setItems(null);
			this.getChildren().clear();
			tabViewPreposer.getSelectionModel().selectedItemProperty().removeListener(ecouteurAdherent);
			connet.getStgInterfaceAdmin().close();
			DemarrerAppli.getInstance().setStagee(connet.creerInterfaceConnexion(DemarrerAppli.getInstance().getStagee()));
			DemarrerAppli.getInstance().getStagee().show();
			
		});

		vbBoutons.getChildren().addAll(btnAjoutPrep, btnSupprPrep, new Separator(), btnQuitter);
		TitledPane tpControl = new TitledPane("Gestion préposés", vbBoutons);
		tpControl.setCollapsible(false);
		this.getChildren().addAll(new HBox(tabViewPreposer, tpControl));
		
		
	
		return sceneAdministrateur;
	}
	

	// Méthode qui retourne l'instance du singleton Admin.
	public static TableauBordAdmin getInstance() {
		if (instance == null) {
			instance = new TableauBordAdmin();
			return instance; 
		}else {	
		return instance;   
		}
	}


	
	// Écouteur de changement sur le tableView des adhérents qui récupère l'adhérent
	// sélectionné.
	ChangeListener<Preposer> ecouteurAdherent = new ChangeListener<Preposer>() {

		@Override
		public void changed(ObservableValue<? extends Preposer> observable, Preposer oldPrepose, Preposer newPrepose) {

			if (!boolClear)
			
				prepCourant = newPrepose;
		}
	};

	// Méthode qui créer et initie la fenêtre d'ajout de préposé.
	private void creerAjoutPrep() {

		stageAjouteprepo = new Stage();
		gpAjoutPreposer = new GridPane();
		vbAjoutlbl = new VBox(15);
		vbAjoutOptions = new VBox(6);

		btnConfirmerPrepo = new Button("Confirmer");
		btnConfirmerPrepo.setDefaultButton(true);

		btnAnnuler = new Button("Annuler");

		btnAnnuler.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				stageAjouteprepo.close();
			}

		});

		TextField tfNom = new TextField();
		TextField tfPrenom = new TextField();
		TextField tfAdresse = new TextField();
		TextField tfTelephone = new TextField();
		TextField tfMotdepasse = new TextField();

		HBox hbBtns = new HBox(10);

		tfNom.setPrefWidth(Double.MAX_VALUE);
		hbBtns.getChildren().addAll(btnConfirmerPrepo, btnAnnuler);
		hbBtns.setAlignment(Pos.CENTER_RIGHT);

		vbAjoutlbl.getChildren().addAll(new Label("Nom :"), new Label("Prénom :"), new Label("Adresse :"),
				new Label("Téléphone :"), new Label("Mot de passe :"));
		vbAjoutOptions.getChildren().addAll(tfNom, tfPrenom, tfAdresse, tfTelephone, tfMotdepasse, hbBtns);

		gpAjoutPreposer.setPadding(new Insets(15));
		GridPane.setMargin(vbAjoutOptions, new Insets(0, 0, 0, 40));
		gpAjoutPreposer.add(vbAjoutlbl, 0, 0, 2, 1);
		gpAjoutPreposer.add(vbAjoutOptions, 3, 0, 4, 1);

		btnConfirmerPrepo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				String strMessageErreur = "";
				boolean booValide = true;

				Alert alerteAjoutPrepose = new Alert(AlertType.ERROR);
				alerteAjoutPrepose.setHeaderText(null);
				alerteAjoutPrepose.setTitle("Erreur");

				if (tfNom.getText().trim().equals("")) {
					strMessageErreur = "Vous n'avez pas tapé le nom du préposé.";
					booValide = false;
				} else if (tfPrenom.getText().trim().equals("")) {
					strMessageErreur = "Vous n'avez pas tapé le prénom du préposé.";
					booValide = false;
				} else if (tfAdresse.getText().trim().equals("")) {
					strMessageErreur = "Vous n'avez pas tapé l'adresse du préposé.";
					booValide = false;
				} else if (tfTelephone.getText().trim().equals("")) {
					strMessageErreur = "Vous n'avez pas tapé le numéro de téléphone du préposé.";
					booValide = false;
				} else if (!tfTelephone.getText().matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
					strMessageErreur = "Le numéro de téléphone que vous avez tapé est invalide. Le format est (###)-###-####.";
					booValide = false;
				} else if (tfMotdepasse.getText().trim().equals("")) {
					strMessageErreur = "Vous n'avez pas tapé le mot de passe du préposé.";
					booValide = false;
				} else if (tfMotdepasse.getText().trim().length() < 8) {
					strMessageErreur = "Le mot de passe tapé est invalide. Le mot de passe doit avoir au moins 8 caractères.";
					booValide = false;
				}
				if (booValide) {

					Preposer.retrouverPlusPetit();
					Preposer prepose = new Preposer(tfNom.getText(), tfPrenom.getText(), tfMotdepasse.getText(), tfAdresse.getText(), tfTelephone.getText());
					Preposer.getArrPreposer().add(prepose);
					obsPreposer.add(prepose);

					stageAjouteprepo.close();

				} else {
					alerteAjoutPrepose.setContentText(strMessageErreur);
					Optional<ButtonType> retour = alerteAjoutPrepose.showAndWait();
				}
			}
		});

		Scene sceneAjoutPrepose = new Scene(gpAjoutPreposer, 500, 200);
		stageAjouteprepo.initModality(Modality.APPLICATION_MODAL);
		stageAjouteprepo.setTitle("Ajouter un préposé");
		stageAjouteprepo.setScene(sceneAjoutPrepose);
		stageAjouteprepo.getIcons().add(new Image("logoAdmin.jpg"));
		stageAjouteprepo.setResizable(false);
	}

	// Gestionnaire d'évènement qui s'occupe de la suppression des préposés.
	private EventHandler<ActionEvent> gestionSuppPrep = new EventHandler<ActionEvent>() {

		public void handle(ActionEvent e) {

			if (prepCourant == null) {

				Alert alerteSuppPrep = new Alert(AlertType.ERROR);
				alerteSuppPrep.setTitle("Erreur");
				alerteSuppPrep.setHeaderText(null);
				alerteSuppPrep.setContentText("Vous n'avez pas sélectionné un préposé à supprimer.");
				alerteSuppPrep.show();
			} else {

				
				Preposer.getArrPreposer().remove(prepCourant);
				obsPreposer.remove(prepCourant);

				boolClear = true;
				tabViewPreposer.getSelectionModel().clearSelection();
				boolClear = false;
				prepCourant = null;
			}
		}
	};
}
