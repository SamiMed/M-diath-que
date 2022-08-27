package vueEtControleur;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.function.IntConsumer;

import donnees.Adherent;
import donnees.Preposer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Connection extends BorderPane {

	private static Connection instance;
	private Stage stgInterfaceAdherentDossier = new Stage();
	private Stage stgInterfaceAdmin = new Stage();
	private Stage stgTableauBordAdherent = new Stage();
	private Stage stgTableauBordPreposer = new Stage();
	private Scene scene = new Scene(this, 550, 400);
	private GridPane gridAdherent;
	private Button btnConnecPrepo;
	private Button btnConneAdh;
	private Button btnViewCatalog;
	private RadioButton rdNomPrenom;
	private RadioButton rdNumTel;
	private PasswordField txtPwdPrepo;
	private TextField txtNomUtlPrepo;
	private TextField txtNumTel;
	private TextField txtNomAdh;
	private TextField txtPrenomAdh;
	private int intIndexAdherent;
	private ArrayList<Adherent> adherents = new ArrayList<Adherent>();
	private gestionIdentification gestion;

	public Stage creerInterfaceConnexion(Stage stage) {

		// TODO Auto-generated method stub

		/* Interface Pour le compte preposer */
		GridPane gridPrepo = new GridPane();
		stage.getIcons().add(new Image("icon-collection.png"));
		Text txtTitrePrincip = new Text("Connection préposé");
		txtTitrePrincip.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));

		txtNomUtlPrepo = new TextField();
		txtNomUtlPrepo.setPromptText("Numéro d'employé");
		txtNomUtlPrepo.setFocusTraversable(false);

		txtPwdPrepo = new PasswordField();
		txtPwdPrepo.setPromptText("Mot de passe");
		txtPwdPrepo.setFocusTraversable(false);

		btnConnecPrepo = new Button("Connexion");
		btnConnecPrepo.setFont(Font.font("tahoma", FontWeight.NORMAL, 20));
		btnConnecPrepo.setFocusTraversable(false);
		btnConnecPrepo.setPrefWidth(400);
		btnConnecPrepo.setOnAction(null);

		Image image = new Image("logoAdmin1.jpg");
		ImageView ImgView = new ImageView(image);

		gridPrepo.add(txtTitrePrincip, 0, 0, 4, 1);
		gridPrepo.add(ImgView, 0, 1, 2, 2);
		gridPrepo.add(txtNomUtlPrepo, 1, 1, 2, 2);
		gridPrepo.add(txtPwdPrepo, 1, 2, 2, 2);
		gridPrepo.add(btnConnecPrepo, 0, 3, 4, 1);
		GridPane.setHalignment(txtTitrePrincip, HPos.CENTER);
		GridPane.setHalignment(ImgView, HPos.CENTER);
		GridPane.setHalignment(txtNomUtlPrepo, HPos.CENTER);
		GridPane.setHalignment(txtPwdPrepo, HPos.CENTER);
		GridPane.setHalignment(btnConnecPrepo, HPos.CENTER);
		GridPane.setValignment(txtTitrePrincip, VPos.CENTER);
		GridPane.setValignment(ImgView, VPos.CENTER);
		GridPane.setValignment(txtNomUtlPrepo, VPos.CENTER);
		GridPane.setValignment(txtPwdPrepo, VPos.CENTER);
		GridPane.setValignment(btnConnecPrepo, VPos.CENTER);

		gridPrepo.setGridLinesVisible(false);

		this.setCenter(gridPrepo);
		BorderPane.setAlignment(gridPrepo, Pos.CENTER);
		gridPrepo.setVgap(20);
		gridPrepo.setHgap(100);
		GridPane.setHgrow(gridPrepo, Priority.ALWAYS);
		GridPane.setVgrow(gridPrepo, Priority.ALWAYS);
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setFillWidth(true);
		RowConstraints rowConstraints = new RowConstraints();
		rowConstraints.setFillHeight(true);
		rowConstraints.setVgrow(Priority.ALWAYS);
		columnConstraints.setHgrow(Priority.ALWAYS);
		gridPrepo.getColumnConstraints().add(columnConstraints);
		gridPrepo.getRowConstraints().add(rowConstraints);

		/* Interface pour le compte adhérent */

		gridAdherent = new GridPane();

		ToggleGroup group = new ToggleGroup();
		Text txtTitreAdh = new Text("Connection adhérent");
		txtTitreAdh.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		BorderPane.setAlignment(txtTitreAdh, Pos.TOP_CENTER);
		txtNomAdh = new TextField();
		txtPrenomAdh = new TextField();
		rdNomPrenom = new RadioButton("Nom et prénom");
		rdNumTel = new RadioButton("Téléphone");
		rdNomPrenom.setToggleGroup(group);
		rdNumTel.setToggleGroup(group);
		rdNomPrenom.setSelected(true);

		gestion = new gestionIdentification();
		rdNomPrenom.setOnAction(gestion);
		rdNumTel.setOnAction(gestion);

		txtPrenomAdh.setPromptText("Prénom");
		txtNomAdh.setPromptText("Nom");
		txtNumTel = new TextField();
		txtNumTel.setPromptText("Numéro de téléphone");
		txtNumTel.setVisible(false);
		btnConneAdh = new Button("Connexion");
		btnConneAdh.setFont(Font.font("tahoma", FontWeight.NORMAL, 20));
		btnConneAdh.setFocusTraversable(false);
		btnConneAdh.setPrefWidth(400);
		btnViewCatalog = new Button("Catalogue");
		btnViewCatalog.setFont(Font.font("tahoma", FontWeight.NORMAL, 20));
		btnViewCatalog.setFocusTraversable(false);
		btnViewCatalog.setPrefWidth(400);

		Image imageAdh = new Image("imgAdhrent1.png");
		ImageView ImgViewAdh = new ImageView(imageAdh);
		gridAdherent.add(txtTitreAdh, 0, 0, 4, 1);
		gridAdherent.add(ImgViewAdh, 0, 1, 2, 3);
		gridAdherent.add(rdNomPrenom, 2, 1, 1, 1);
		gridAdherent.add(rdNumTel, 3, 1, 1, 1);
		gridAdherent.add(txtPrenomAdh, 2, 2, 2, 1);
		gridAdherent.add(txtNomAdh, 2, 3, 2, 1);

		gridAdherent.add(txtNumTel, 2, 2, 2, 2);

		gridAdherent.add(btnViewCatalog, 0, 4, 2, 1);

		gridAdherent.add(btnConneAdh, 2, 4, 2, 1);

		GridPane.setHalignment(txtTitreAdh, HPos.CENTER);
		GridPane.setHalignment(ImgViewAdh, HPos.CENTER);
		GridPane.setHalignment(rdNomPrenom, HPos.CENTER);
		GridPane.setHalignment(rdNumTel, HPos.CENTER);
		GridPane.setHalignment(txtPrenomAdh, HPos.CENTER);
		GridPane.setHalignment(txtNomAdh, HPos.CENTER);
		GridPane.setHalignment(txtNumTel, HPos.CENTER);
		GridPane.setHalignment(btnConneAdh, HPos.CENTER);
		GridPane.setHalignment(btnViewCatalog, HPos.CENTER);

		GridPane.setValignment(txtTitreAdh, VPos.CENTER);
		GridPane.setValignment(ImgViewAdh, VPos.CENTER);
		GridPane.setValignment(rdNumTel, VPos.CENTER);
		GridPane.setValignment(rdNomPrenom, VPos.CENTER);
		GridPane.setValignment(txtPrenomAdh, VPos.CENTER);
		GridPane.setValignment(txtNomAdh, VPos.CENTER);
		GridPane.setValignment(txtNumTel, VPos.CENTER);
		GridPane.setValignment(btnConneAdh, VPos.CENTER);
		GridPane.setValignment(btnViewCatalog, VPos.CENTER);

		GridPane.setHgrow(gridAdherent, Priority.ALWAYS);
		GridPane.setVgrow(gridAdherent, Priority.ALWAYS);
		ColumnConstraints columnConstraints1 = new ColumnConstraints();
		columnConstraints1.setFillWidth(true);
		RowConstraints rowConstraints1 = new RowConstraints();
		rowConstraints1.setFillHeight(true);
		rowConstraints1.setVgrow(Priority.ALWAYS);
		columnConstraints1.setHgrow(Priority.ALWAYS);
		gridAdherent.getColumnConstraints().add(columnConstraints);
		gridAdherent.getRowConstraints().add(rowConstraints);
		this.setCenter(gridAdherent);
		gridAdherent.setVgap(20);
		gridAdherent.setHgap(30);
		BorderPane.setMargin(gridAdherent, new Insets(300, 200, 50, 100));

		btnViewCatalog.setOnAction((ActionEvent event) -> {

			try {

				stage.close();
				if (!getStgTableauBordAdherent().getModality().equals(Modality.APPLICATION_MODAL)) {

					getStgTableauBordAdherent().initModality(Modality.APPLICATION_MODAL);
				}
				getStgTableauBordAdherent().setResizable(false);
				getStgTableauBordAdherent().setTitle("Consultation du catalogue");
				getStgTableauBordAdherent().getIcons().add(new Image("icon-collection.png"));

				getStgTableauBordAdherent().setScene(TableauBordAdherent.getInstance().CreerInterfaceadherant());
				getStgTableauBordAdherent().show();

			} catch (Exception e) {
				// TODO Auto-generated catch block

			}
		});

		btnConneAdh.setOnAction((ActionEvent event) -> {

			try {
				if (ValiderAdherant(txtNomAdh.getText().trim().toString(), txtPrenomAdh.getText().trim().toString(),
						txtNumTel.getText().trim().toString(), rdNumTel, rdNomPrenom) == true) {

					if (!getStgInterfaceAdherentDossier().getModality().equals(Modality.APPLICATION_MODAL)) {

						getStgInterfaceAdherentDossier().initModality(Modality.APPLICATION_MODAL);
					}

					stage.close();

					Adherent aderent = TableauBordPreposer.getInstance().getArrTousAdherents().get(intIndexAdherent);
					getStgInterfaceAdherentDossier()
							.setScene(InterfaceAdherent.getInstance().interfaceDossierAdherent(aderent));
					getStgInterfaceAdherentDossier().getIcons().add(new Image("icon-collection.png"));
					stgInterfaceAdherentDossier.setTitle("Dossier adhérent");
					getStgInterfaceAdherentDossier().show();

				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		});

		Accordion accordion = new Accordion();
		accordion.setMaxSize(500, 400);
		VBox vBoxMenu = new VBox(accordion);
		TitledPane paneAdherent = new TitledPane("Connection adhérent", gridAdherent);
		TitledPane panePreposer = new TitledPane("Connection préposé", gridPrepo);

		accordion.getPanes().add(paneAdherent);
		accordion.getPanes().add(panePreposer);
		accordion.setExpandedPane(paneAdherent);
		this.setCenter(accordion);
		this.getChildren().add(vBoxMenu);
		accordion.setPadding(new Insets(25, 25, 25, 25));
		stage.setResizable(true);

		stage.setTitle("Connexion");
		stage.setScene(scene);

		btnConnecPrepo.setOnAction((ActionEvent event) -> {

			boolean booExiste = false;
			Alert alerteErreur = new Alert(AlertType.ERROR);
			alerteErreur.setTitle("Erreur");
			alerteErreur.setHeaderText(null);

			if (txtNomUtlPrepo.getText().trim().isEmpty()) {

				alerteErreur.setContentText("Veuillez inscrire le nom d'utilisateur");
				alerteErreur.show();
			}

			else if (txtPwdPrepo.getText().trim().isEmpty()) {

				alerteErreur.setContentText("Veuillez inscrire le mot de passe");
				alerteErreur.show();
			}

			else {

				if (txtNomUtlPrepo.getText().trim().equals("Admin")
						&& txtPwdPrepo.getText().trim().equals("Password1")) {

					booExiste = true;
					txtNomUtlPrepo.clear();
					txtPwdPrepo.clear();
					stage.close();

					if (!getStgInterfaceAdmin().getModality().equals(Modality.APPLICATION_MODAL)) {

						getStgInterfaceAdmin().initModality(Modality.APPLICATION_MODAL);
					}

					getStgInterfaceAdmin().setScene(TableauBordAdmin.getInstance().creerInterfaceAdmin());

					getStgInterfaceAdmin().setTitle("Médiathèque");
					getStgInterfaceAdmin().getIcons().add(new Image("icon-collection.png"));
					getStgInterfaceAdmin().centerOnScreen();
					getStgInterfaceAdmin().show();

				} else {

					for (int i = 0; i < Preposer.getArrPreposer().size(); i++) {

						if (txtNomUtlPrepo.getText().trim().equals(Preposer.getArrPreposer().get(i).getStrId().trim())
								&& txtPwdPrepo.getText().trim()
										.equals(Preposer.getArrPreposer().get(i).getStrMotDePasse().trim())) {

							booExiste = true;
							txtNomUtlPrepo.clear();
							txtPwdPrepo.clear();
							stage.close();

							if (!getStgTableauBordPreposer().getModality().equals(Modality.APPLICATION_MODAL)) {

								getStgTableauBordPreposer().initModality(Modality.APPLICATION_MODAL);
							}

							getStgTableauBordPreposer()
									.setScene(TableauBordPreposer.getInstance().CreerInterfacePreposer());

							getStgTableauBordPreposer().setTitle("Panneau du préposé");
							getStgTableauBordPreposer().setResizable(false);

							getStgTableauBordPreposer().getIcons().add(new Image("icon-collection.png"));
							getStgTableauBordPreposer().show();

							break;
						}

					}

				}

				if (!booExiste) {

					txtNomUtlPrepo.clear();
					txtPwdPrepo.clear();

					alerteErreur.setContentText("Cet  utilisateur n'existe pas");
					alerteErreur.show();
				}
			}

		});

		return stage;
	}

	public class gestionIdentification implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			try {

				Alert alert = new Alert(AlertType.ERROR);

				alert.setTitle("Erreur");
				alert.setHeaderText(null);

				if (event.getSource() == rdNomPrenom) {
					txtPrenomAdh.setVisible(true);
					txtNomAdh.setVisible(true);
					txtNumTel.setVisible(false);

				}

				else if (event.getSource() == rdNumTel) {

					txtPrenomAdh.setVisible(false);
					txtNomAdh.setVisible(false);
					txtNumTel.setVisible(true);
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public boolean ValiderAdherant(String strNom, String strPrenom, String strTel, RadioButton rbTel,
			RadioButton rbNomPren) {
		/* Permet de valider les information d'un adhérent */

		boolean blnValideTot = false;
		boolean blnValideNom = false;
		boolean blnValidePrenom = false;
		boolean blnValideTel = false;
		try {
			Alert alert = new Alert(AlertType.ERROR);
			String strMessage = "";
			alert.setTitle("Erreur");
			alert.setHeaderText(null);

			if (rbTel.isSelected()) {

				for (Adherent adherentarr : adherents) {
					if (strTel.trim().equals(adherentarr.getStrNumTel().trim())) {
						blnValideTel = true;
						intIndexAdherent = adherents.indexOf(adherentarr);
						break;

					} else {
						blnValideTel = false;
					}

				}

				if (blnValideTel == true) {

					blnValideTot = true;
				} else if (blnValideTel == false) {
					blnValideTot = false;
					if (strTel.equalsIgnoreCase("")) {
						strMessage = "Veuillez inscrire un numéro de téléphone";

						alert.show();
					} else {

						strMessage = "Ce numéro de téléphone n'existe pas dans notre base de donnée";
						alert.show();
					}
				}
			} else if (rbNomPren.isSelected()) {

				for (Adherent adherentarr : adherents) {
					if (strNom.trim().equals(adherentarr.getStrNom().trim())) {
						blnValideNom = true;
					} else {
						blnValideNom = false;
					}
					if (strPrenom.trim().equals(adherentarr.getStrPrenom().trim())) {
						blnValidePrenom = true;
					} else {
						blnValidePrenom = false;
					}

					if (blnValideNom == true || blnValidePrenom == true) {
						intIndexAdherent = adherents.indexOf(adherentarr);

						break;
					}

				}

				if (blnValideNom == true && blnValidePrenom == true) {

					blnValideTot = true;
				} else if (blnValidePrenom == false && blnValideNom == false) {
					blnValideTot = false;
					if (strPrenom.equalsIgnoreCase("") && strNom.equalsIgnoreCase("")) {
						strMessage = "Veuillez inscrire un prénom et un nom";

						alert.show();
					} else {

						strMessage = "Ce prénom et ce nom n'existe pas dans notre base de donnée";
						alert.show();
					}

				} else if (blnValideNom == false) {
					blnValideTot = false;
					if (strNom.equalsIgnoreCase("")) {
						strMessage = "Veuillez inscrire un nom";

						alert.show();
					} else {
						strMessage = "Ce nom n'existe pas dans notre base de donnée";
						alert.show();

					}

				} else if (blnValidePrenom == false) {
					blnValideTot = false;
					if (strPrenom.equalsIgnoreCase("")) {
						strMessage = "Veuillez inscrire un prénom";

						alert.show();
					} else {

						strMessage = "Ce prénom n'existe pas dans notre base de donnée";
						alert.show();
					}

				}

			}

			alert.setContentText(strMessage);

		} catch (Exception e) {

		}

		return blnValideTot;
	}

	public static Connection getInstance() {

		if (instance == null) {
			instance = new Connection();
			return instance;

		} else {
			return instance;
		}

	}

	public Stage getStgInterfaceAdherentDossier() {
		return stgInterfaceAdherentDossier;
	}

	public Stage getStgTableauBordPreposer() {
		return stgTableauBordPreposer;
	}

	public int getIntIndexAdherent() {
		return intIndexAdherent;
	}

	public Stage getStgTableauBordAdherent() {
		return stgTableauBordAdherent;
	}

	public void setStgTableauBordAdherent(Stage stgTableauBordAdherent) {
		this.stgTableauBordAdherent = stgTableauBordAdherent;
	}

	public Stage getStgInterfaceAdmin() {
		return stgInterfaceAdmin;
	}

	public void setStgInterfaceAdmin(Stage stgInterfaceAdmin) {
		this.stgInterfaceAdmin = stgInterfaceAdmin;
	}

	public GridPane getGridAdherent() {
		return gridAdherent;
	}

	public ArrayList<Adherent> getAdherents() {
		return adherents;
	}

	public void setAdherents(ArrayList<Adherent> adherents) {
		this.adherents = adherents;
	}

	public void setStgInterfaceAdherentDossier(Stage stgInterfaceAdherentDossier) {

		this.stgInterfaceAdherentDossier = stgInterfaceAdherentDossier;
	}
}
