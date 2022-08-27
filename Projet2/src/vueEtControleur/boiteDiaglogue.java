package vueEtControleur;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import donnees.Adherent;
import donnees.Catalogue;
import donnees.DVD;
import donnees.Document;
import donnees.Livre;
import donnees.Periodique;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class boiteDiaglogue extends GridPane {
	private TableauBordPreposer tbl = TableauBordPreposer.getInstance();
	private Scene scene;
	private Label lblNomAdh;
	private Label lblPrenomAdh;
	private Label lblAdresseAdh;
	private Label lblTelAdh;
	private Label lblChoixDoc;
	private Label lblTitre;
	private Label lblAuteur;
	private Label lblDateParution;
	private Label lblMotcle;
	private Label lblNumVol;
	private Label lblNumPerio;
	private Label lblNbDisque;
	private Label lblRealisateur;
	private TextField txtNomAdh;
	private TextField txtPrenomAdh;
	private TextField txtAdresseAdh;
	private TextField txtTelAdh;
	private TextField txtNbDisque;
	private TextField txtRealisateur;
	private Button btnConfirmer;
	private Button btnAnnuler;
	private Button btnAjoutAdh;
	private TextField txtTitre;
	private TextField txtAuteur;
	private TextField txtDateParution;
	private TextField txtMotCle;
	private TextField txtNumVol;
	private TextField txtNumPeri;
	private String strTypeDoc[] = { "Livre", "DVD", "Périodique" };
	private ComboBox<String> comboBox = new ComboBox<String>((FXCollections.observableArrayList(strTypeDoc)));;
	private HBox HbBtn;
	private TableCatalogue tab = TableCatalogue.getInstance();
	private gestionAjoutDoc gestionAjoutDoc;
	private Catalogue cat = Catalogue.getInstance();
	private Stage stage;
	private ObservableList<Livre> livreOb;
	private ObservableList<Document> doc;
	private ObservableList<Periodique> perio;
	private ObservableList<DVD> obsDVD;
	private ObservableList<Adherent> obsListTousAdherents = FXCollections.observableArrayList(tbl.getArrTousAdherents());
	private DateTimeFormatter formatteurParution = DateTimeFormatter.ofPattern("dd-MM-uuuu");
	private TableView<Document> tabViewDocs;
	private GridPane GpAjoutDocu;
	private GridPane GpAjoutAdh;
	private gestionAjoutAdh gestiAdh;
	private GridPane GpModifierAdh;
	private gestionModfierAdh gestModifAdh;
	private Button btnModifAdh;
	private Adherent adherentCourant = tbl.getAdherent();

	public void creerInterfaceAjoutDoc() {

		stage = new Stage();
		GpAjoutDocu = new GridPane();
		stage.getIcons().add(new Image("icon-collection.png"));

		lblChoixDoc = new Label("Type de document :");
		lblTitre = new Label("Titre :");
		lblAuteur = new Label("Auteur :");
		lblDateParution = new Label("Date de parution :");
		lblMotcle = new Label("Mots clés (Séparé par des espaces) :");
		lblNumPerio = new Label("Numéro de périodique : ");
		lblNumVol = new Label("Numéro de volume :");
		lblNbDisque = new Label("Nombre de disques :");
		lblRealisateur = new Label("Réalisateur :");

		txtTitre = new TextField();
		txtAuteur = new TextField();
		txtDateParution = new TextField();
		txtMotCle = new TextField();
		txtNumPeri = new TextField();
		txtNumVol = new TextField();
		txtNbDisque = new TextField();
		txtRealisateur = new TextField();

		gestionAjoutDoc = new gestionAjoutDoc();

		btnAnnuler = new Button("Annuler");
		btnConfirmer = new Button("Confirmer");

		btnConfirmer.setOnAction(gestionAjoutDoc);
		btnAnnuler.setOnAction(e -> {

			stage.close();
		});

		comboBox.setPrefWidth(200);

		txtTitre.requestFocus();
		comboBox.setOnAction(gestionAjoutDoc);
		comboBox.getSelectionModel().selectFirst();

		scene = new Scene(gridAjoutLivre(), 600, 400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Ajouter un document");
		stage.show();

	}

	public GridPane gridAjoutLivre() {

		btnConfirmer.setDefaultButton(true);
		HbBtn = new HBox();

		GpAjoutDocu.setVgap(10);
		GpAjoutDocu.setHgap(10);

		GpAjoutDocu.add(lblChoixDoc, 1, 1);
		GpAjoutDocu.add(comboBox, 20, 1);
		GpAjoutDocu.add(lblTitre, 1, 2);
		GpAjoutDocu.add(txtTitre, 20, 2);

		GpAjoutDocu.add(lblAuteur, 1, 3);
		GpAjoutDocu.add(txtAuteur, 20, 3);

		GpAjoutDocu.add(lblDateParution, 1, 4);
		GpAjoutDocu.add(txtDateParution, 20, 4);

		GpAjoutDocu.add(lblMotcle, 1, 5);
		GpAjoutDocu.add(txtMotCle, 20, 5);

		HbBtn.setAlignment(Pos.CENTER_RIGHT);
		GridPane.setMargin(HbBtn, new Insets(51, 0, 0, 0));
		HbBtn.getChildren().addAll(btnConfirmer, btnAnnuler);
		GpAjoutDocu.add(HbBtn, 20, 15);

		txtTitre.requestFocus();
		HbBtn.setSpacing(25);

		return GpAjoutDocu;

	}

	public GridPane gridAjoutPerio() {

		btnConfirmer.setDefaultButton(true);
		HbBtn = new HBox();

		GpAjoutDocu.setVgap(10);
		GpAjoutDocu.setHgap(10);

		GpAjoutDocu.add(lblChoixDoc, 1, 1);
		GpAjoutDocu.add(comboBox, 20, 1);
		GpAjoutDocu.add(lblTitre, 1, 2);
		GpAjoutDocu.add(txtTitre, 20, 2);

		GpAjoutDocu.add(lblNumVol, 1, 3);
		GpAjoutDocu.add(txtNumVol, 20, 3);

		GpAjoutDocu.add(lblNumPerio, 1, 4);
		GpAjoutDocu.add(txtNumPeri, 20, 4);

		GpAjoutDocu.add(lblDateParution, 1, 5);
		GpAjoutDocu.add(txtDateParution, 20, 5);

		GpAjoutDocu.add(lblMotcle, 1, 6);
		GpAjoutDocu.add(txtMotCle, 20, 6);
		HbBtn.setAlignment(Pos.CENTER_RIGHT);
		GridPane.setMargin(HbBtn, new Insets(51, 0, 0, 0));
		HbBtn.getChildren().addAll(btnConfirmer, btnAnnuler);
		GpAjoutDocu.add(HbBtn, 20, 15);

		txtTitre.requestFocus();
		HbBtn.setSpacing(25);

		return GpAjoutDocu;
	}

	public GridPane gridAjoutDVD() {

		HbBtn = new HBox();
		btnConfirmer.setDefaultButton(true);
		GpAjoutDocu.setVgap(10);
		GpAjoutDocu.setHgap(10);

		GpAjoutDocu.add(lblChoixDoc, 1, 1);
		GpAjoutDocu.add(comboBox, 20, 1);
		GpAjoutDocu.add(lblTitre, 1, 2);
		GpAjoutDocu.add(txtTitre, 20, 2);

		GpAjoutDocu.add(lblNbDisque, 1, 3);
		GpAjoutDocu.add(txtNbDisque, 20, 3);

		GpAjoutDocu.add(lblRealisateur, 1, 4);
		GpAjoutDocu.add(txtRealisateur, 20, 4);

		GpAjoutDocu.add(lblDateParution, 1, 5);
		GpAjoutDocu.add(txtDateParution, 20, 5);

		GpAjoutDocu.add(lblMotcle, 1, 6);
		GpAjoutDocu.add(txtMotCle, 20, 6);
		HbBtn.setAlignment(Pos.CENTER_RIGHT);
		GridPane.setMargin(HbBtn, new Insets(51, 0, 0, 0));
		HbBtn.getChildren().addAll(btnConfirmer, btnAnnuler);
		GpAjoutDocu.add(HbBtn, 20, 15);

		txtTitre.requestFocus();
		HbBtn.setSpacing(25);

		return GpAjoutDocu;
	}

	public void creerInterfaceAjoutAdherent() {

		stage = new Stage();
		GpAjoutAdh = new GridPane();
		stage.getIcons().add(new Image("icon-collection.png"));

		lblNomAdh = new Label("Nom : ");
		lblPrenomAdh = new Label("Prénom : ");
		lblAdresseAdh = new Label("Adresse : ");
		lblTelAdh = new Label("Téléphone : ");

		txtNomAdh = new TextField();
		txtPrenomAdh = new TextField();
		txtAdresseAdh = new TextField();
		txtTelAdh = new TextField();

		HbBtn = new HBox();
		btnAjoutAdh = new Button("Confirmer");
		btnAnnuler = new Button("Annuler");

		btnAjoutAdh.setDefaultButton(true);

		gestiAdh = new gestionAjoutAdh();

		btnAnnuler.setOnAction(e -> {

			stage.close();
		});

		btnAjoutAdh.setOnAction(gestiAdh);

		GpAjoutAdh.setVgap(10);
		GpAjoutAdh.setHgap(10);

		GpAjoutAdh.add(lblNomAdh, 1, 1);
		GpAjoutAdh.add(txtNomAdh, 20, 1);
		GpAjoutAdh.add(lblPrenomAdh, 1, 2);
		GpAjoutAdh.add(txtPrenomAdh, 20, 2);

		GpAjoutAdh.add(lblAdresseAdh, 1, 3);
		GpAjoutAdh.add(txtAdresseAdh, 20, 3);

		GpAjoutAdh.add(lblTelAdh, 1, 4);
		GpAjoutAdh.add(txtTelAdh, 20, 4);

		txtNomAdh.setPrefWidth(300);
		txtPrenomAdh.setPrefWidth(300);
		txtAdresseAdh.setPrefWidth(300);
		txtTelAdh.setPrefWidth(300);

		txtNomAdh.requestFocus();
		HbBtn.setSpacing(20);
		HbBtn.setAlignment(Pos.CENTER_RIGHT);
		GridPane.setMargin(HbBtn, new Insets(10, 0, 0, 0));
		HbBtn.getChildren().addAll(btnAjoutAdh, btnAnnuler);

		GpAjoutAdh.add(HbBtn, 20, 5);

		scene = new Scene(GpAjoutAdh, 560, 200);

		stage.setScene(scene);

		stage.setResizable(false);
		stage.setTitle("Ajouter un adhérent ");
		stage.show();

	}

	public void creerInterfaceModiferAdherent(Adherent adh) {

		stage = new Stage();

		GpModifierAdh = new GridPane();
		stage.getIcons().add(new Image("imgAdhrent.png"));
		HbBtn = new HBox();
		lblAdresseAdh = new Label("Adresse : ");
		lblTelAdh = new Label("Téléphone : ");

		txtAdresseAdh = new TextField(adh.getStrAddresse());
		txtTelAdh = new TextField(adh.getStrNumTel());

		gestModifAdh = new gestionModfierAdh();
		GpModifierAdh.add(lblAdresseAdh, 1, 1);
		GpModifierAdh.add(txtAdresseAdh, 20, 1);

		GpModifierAdh.add(lblTelAdh, 1, 3);
		GpModifierAdh.add(txtTelAdh, 20, 3);

		btnModifAdh = new Button("Confirmer");
		btnAnnuler = new Button("Annuler");

		btnModifAdh.setOnAction(gestModifAdh);

		btnModifAdh.setDefaultButton(true);

		btnAnnuler.setOnAction(e -> {

			stage.close();
		});

		txtAdresseAdh.setPrefWidth(300);
		txtTelAdh.setPrefWidth(300);

		GpModifierAdh.setVgap(10);
		GpModifierAdh.setHgap(10);

		HbBtn.setSpacing(20);
		HbBtn.setAlignment(Pos.CENTER_RIGHT);
		GridPane.setMargin(HbBtn, new Insets(10, 0, 0, 0));
		HbBtn.getChildren().addAll(btnModifAdh, btnAnnuler);
		GpModifierAdh.add(HbBtn, 20, 5);
		scene = new Scene(GpModifierAdh, 560, 200);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Modifier un adhérent ");
		stage.show();

	}

	private class gestionModfierAdh implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub

			try {

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Erreur");
				String strNumTel = txtTelAdh.getText().trim();

				if (event.getSource() == btnModifAdh) {

					if (txtAdresseAdh.getText().trim().isEmpty()) {

						alert.setContentText("Veuillez inscrire une adresse");
						alert.show();

					}

					else if (txtTelAdh.getText().trim().isEmpty()) {
						alert.setContentText("Veuillez inscrire un numéro de téléphone");
						alert.show();

					}

					else if (!strNumTel.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {

						alert.setContentText(
								"Le format du numéro de téléphone est invalide. Le format est (###)-###-####");
						alert.show();
					}

					else {

						adherentCourant.setStrNumTel(txtTelAdh.getText());
						adherentCourant.setStrAddresse(txtAdresseAdh.getText());
						obsListTousAdherents.add(adherentCourant);
						tbl.setObservableAdherent(tbl.getArrTousAdherents());
						tbl.getTabViewAdherents().setItems(tbl.getObsListTousAdherents());
						tbl.getTabViewAdherents().refresh();
						clearSelections();
						stage.close();

					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	private class gestionAjoutAdh implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			String strNumTel = txtTelAdh.getText().trim();

			if (event.getSource() == btnAjoutAdh) {

				if (txtNomAdh.getText().trim().isEmpty()) {

					alert.setContentText("Veuillez inscrire un nom");
					alert.show();
				}

				else if (txtPrenomAdh.getText().trim().isEmpty()) {
					alert.setContentText("Veuillez inscrire un prénom");
					alert.show();
				}

				else if (txtAdresseAdh.getText().trim().isEmpty()) {
					alert.setContentText("Veuillez inscrire une adresse");
					alert.show();
				}

				else if (txtTelAdh.getText().trim().isEmpty()) {
					alert.setContentText("Veuillez inscrire un numéro de téléphone");
					alert.show();

				}

				else if (!strNumTel.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {

					alert.setContentText("Le format du numéro de téléphone est invalide. Le format est (###)-###-####");
					alert.show();
				}

				else {
					tbl.retrouverPlusPetit();
					Adherent adherent = new Adherent(tbl.getStrCompteur(), txtPrenomAdh.getText().trim(),
							txtNomAdh.getText().trim(), txtTelAdh.getText().trim(), txtAdresseAdh.getText().trim(),
							"0.00$", 0, 0, 0);

					tbl.ajoutAdherent(adherent);

					obsListTousAdherents.add(adherent);

					tbl.setObservableAdherent(tbl.getArrTousAdherents());

					if (tbl.getArrTousAdherents() != null) {
						for (Adherent adherentarr : tbl.getArrTousAdherents()) {
							System.out.println("ok");
						}
					}
					tbl.setObservableAdherent(tbl.getArrTousAdherents());
					tbl.getTabViewAdherents().setItems(tbl.getObsListTousAdherents());

					stage.close();

				}

			}

		}

	}

	private class gestionAjoutDoc implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			try {
				String strChoix = comboBox.getSelectionModel().getSelectedItem();
				tabViewDocs = tab.getTable();
				tab.setTable(tabViewDocs);
				String strDate = txtDateParution.getText().trim();
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Erreur");
				Boolean booTest = true;

				switch (strChoix) {
				case "Livre":

					GpAjoutDocu.getChildren().clear();
					gridAjoutLivre();

					if (event.getSource() == btnConfirmer) {

						if (txtTitre.getText().trim().equals("")) {
							txtTitre.setFocusTraversable(true);
							alert.setContentText("Veuillez inscrire un titre.");
							alert.show();
						}

						else if (txtAuteur.getText().trim().equals("")) {
							txtAuteur.setFocusTraversable(true);
							alert.setContentText("Veuillez saisir un nom d'auteur.");
							alert.show();

						} else if (txtDateParution.getText().trim().equals("")) {
							txtDateParution.setFocusTraversable(true);
							alert.setContentText("Veuillez saisir une date de parution.");
							alert.show();

						}

						else if (!strDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
							txtDateParution.setFocusTraversable(true);
							alert.setContentText(
									"La date de parution que vous avez tapé est incorrecte. Le format est  jj-mm yyyy.");
							alert.show();

						}

						else if (!verifDateAjoutDoc(strDate)) {
							txtDateParution.setFocusTraversable(true);
							alert.setContentText("La date de parution que vous avez tapé est invalide.");
							alert.show();

						}

						else if (txtMotCle.getText().trim().equals("")) {
							txtMotCle.setFocusTraversable(true);
							alert.setContentText("Veuillez inscrire un mot clé");
							alert.show();

						}

						else {

							Livre.retrouverPlusPetit();

							Livre livre = new Livre("Liv" + Livre.getStrCompteur(), txtTitre.getText(),
									LocalDate.parse(txtDateParution.getText(), formatteurParution), "Disponible",
									txtAuteur.getText(), txtMotCle.getText(), "", 0);

							cat.ajoutLivre(livre);

							doc = FXCollections.observableArrayList(cat.getLstDocuments());
							livreOb = FXCollections.observableArrayList(cat.getLstLivres());

							tab.setObservableDocument(cat.getLstDocuments());
							tab.setObservableLivre(cat.getLstLivres());

							tab.setFilteredocument(doc);
							tab.setFilteredLivre(livreOb);

							tab.getTablelivre().setItems(tab.getFilteredLivre());

							tab.getTable().setItems(tab.getFilteredocument());
							clearSelections();
							stage.close();
						}

					}

					break;

				case "Périodique":

					GpAjoutDocu.getChildren().clear();

					gridAjoutPerio();

					if (event.getSource() == btnConfirmer) {

						if (txtTitre.getText().trim().isEmpty()) {
							txtTitre.setFocusTraversable(true);
							alert.setContentText("Veuillez inscrire un titre.");
							alert.show();
							booTest = false;

						}
						if (booTest && txtNumVol.getText().trim().isEmpty()) {

							txtNumVol.setFocusTraversable(true);
							alert.show();
							alert.setContentText("Veuillez saisir un numéro de volume.");

							booTest = false;

						}

						else if (booTest && !txtNumVol.getText().trim().isEmpty()) {

							try {
								txtNumVol.setFocusTraversable(true);

								Integer.parseInt(txtNumVol.getText().trim());

							} catch (NumberFormatException e) {
								// TODO: handle exception

								alert.setContentText(
										"Le numéro de volume que vous avez tapé est invalide, seul les chiffres entiers sont acceptés.");
								alert.show();
								booTest = false;

							}

						}

						if (booTest && txtNumPeri.getText().trim().isEmpty()) {
							txtNumPeri.setFocusTraversable(true);
							alert.setContentText("Veuillez saisir un numéro périodique.");
							alert.show();
							booTest = false;

						}

						else if (booTest && !txtNumPeri.getText().trim().isEmpty()) {
							try {

								Integer.parseInt(txtNumPeri.getText().trim());

							} catch (Exception e) {
								// TODO: handle exception
								txtNumPeri.setFocusTraversable(true);
								alert.setContentText(
										"Le numéro de périodique que vous avez tapé est invalide, seul les chiffres entiers sont acceptés.");
								alert.show();
								booTest = false;

							}

						}

						if (booTest && txtDateParution.getText().trim().isEmpty()) {
							txtDateParution.setFocusTraversable(true);
							alert.setContentText("Veuillez inscrire une date de parution");
							booTest = false;
							alert.show();
						}

						else if (booTest && !strDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
							txtDateParution.setFocusTraversable(true);
							alert.setContentText(
									"La date de parution que vous avez tapé est incorrecte. Le format est  jj-mmm yyyy.");
							booTest = false;
							alert.show();

						}

						else if (booTest && !verifDateAjoutDoc(strDate)) {
							txtDateParution.setFocusTraversable(true);
							alert.setContentText("La date de parution que vous avez tapé est invalide.");
							booTest = false;
							alert.show();
						}

						else if (booTest && txtMotCle.getText().trim().isEmpty()) {
							txtMotCle.setFocusTraversable(true);
							alert.setContentText("Veuillez inscrire un mot clé");
							alert.show();
							booTest = false;
						}

						else {

							Periodique.retrouverPlusPetit();
							Periodique periodique = new Periodique("Per" + Periodique.getStrCompteur(),
									txtTitre.getText(), LocalDate.parse(txtDateParution.getText(), formatteurParution),
									"Disponible", Integer.parseInt(txtNumVol.getText()),
									Integer.parseInt(txtNumPeri.getText()), txtMotCle.getText(), "", 0);
							System.out.println("renter ici");
							cat.ajoutPerio(periodique);
							doc = FXCollections.observableArrayList(cat.getLstDocuments());
							perio = FXCollections.observableArrayList(cat.getLstPeriodiques());
							tab.setObservableDocument(cat.getLstDocuments());
							tab.setObservablePeriodique(cat.getLstPeriodiques());
							tab.setFilteredocument(doc);
							tab.setFilteredPeriodique(perio);
							tab.getTableper().setItems(tab.getFilteredPeriodique());
							tab.getTable().setItems(tab.getFilteredocument());
							clearSelections();
							stage.close();

						}
					}
					break;

				case "DVD":

					GpAjoutDocu.getChildren().clear();

					gridAjoutDVD();

					if (event.getSource() == btnConfirmer) {

						if (txtTitre.getText().trim().isEmpty()) {
							txtTitre.setFocusTraversable(true);
							alert.setContentText("Veuillez inscrire un titre.");
							alert.show();
							booTest = false;

						}
						if (booTest && txtNbDisque.getText().trim().isEmpty()) {

							txtNumVol.setFocusTraversable(true);
							alert.show();
							alert.setContentText("Veuillez inscrire un nombre de disque");

							booTest = false;

						}

						else if (booTest && !txtNbDisque.getText().trim().isEmpty()) {

							try {
								txtNbDisque.setFocusTraversable(true);

								Integer.parseInt(txtNbDisque.getText().trim());

							} catch (NumberFormatException e) {
								// TODO: handle exception

								alert.setContentText(
										"Le nombre de disque que vous avez tapé est invalide, seul les chiffres entiers sont acceptés.");
								alert.show();
								booTest = false;

							}

						}

						if (booTest && txtRealisateur.getText().trim().isEmpty()) {
							txtRealisateur.setFocusTraversable(true);
							alert.setContentText("Veuillez inscrire le nom du réalisateur");
							booTest = false;
							alert.show();

						}

						if (booTest && txtDateParution.getText().trim().isEmpty()) {
							txtDateParution.setFocusTraversable(true);
							alert.setContentText("Veuillez inscrire une date de parution");
							booTest = false;
							alert.show();
						}

						else if (booTest && !strDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
							txtDateParution.setFocusTraversable(true);
							alert.setContentText(
									"La date de parution que vous avez tapé est incorrecte. Le format est  jj-mmm yyyy.");
							booTest = false;

							alert.show();
						}

						else if (booTest && !verifDateAjoutDoc(strDate)) {
							txtDateParution.setFocusTraversable(true);
							alert.setContentText("La date de parution que vous avez tapé est invalide.");
							booTest = false;
							alert.show();
						}

						else if (booTest && txtMotCle.getText().trim().isEmpty()) {
							txtMotCle.setFocusTraversable(true);
							alert.setContentText("Veuillez inscrire un mot clé");
							alert.show();
							booTest = false;
						}

						else {

							DVD.retrouverPlusPetit();
							DVD Dvd = new DVD("DVD" + DVD.getStrCompteur(), txtTitre.getText().trim(),
									LocalDate.parse(txtDateParution.getText().trim(), formatteurParution), "Disponible",
									Integer.parseInt(txtNbDisque.getText().trim()), txtRealisateur.getText().trim(),
									txtMotCle.getText().trim(), "", 0);
							cat.ajoutDvd(Dvd);

							doc = FXCollections.observableArrayList(cat.getLstDocuments());
							obsDVD = FXCollections.observableArrayList(cat.getLstDvd());
							tab.setObservableDocument(cat.getLstDocuments());
							tab.setObservableDVD(cat.getLstDvd());
							tab.setFilteredocument(doc);
							tab.setFilteredDvd(obsDVD);
							tab.getTabledvd().setItems(tab.getFiltereDVD());
							tab.getTable().setItems(tab.getFilteredocument());
							clearSelections();
							stage.close();
						}

					}
					break;

				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	public void viderText() {

		txtTitre = new TextField("");
		txtAuteur = new TextField("");
		txtDateParution = new TextField("");
		txtMotCle = new TextField("");
		txtNumPeri = new TextField("");
		txtNumVol = new TextField("");
		txtNbDisque = new TextField("");
		txtRealisateur = new TextField("");

	}

	public boolean verifDateAjoutDoc(String strDate) {

		boolean booVerif = true;
		try {
			int intJour = Integer.parseInt(strDate.substring(0, 2));

			LocalDate dateVerif = LocalDate.parse(strDate, formatteurParution);

			booVerif = dateVerif.isBefore(LocalDate.now().plusDays(1));
			if (dateVerif.getMonthValue() == 2)
				booVerif = dateVerif.isLeapYear() ? intJour > 0 && intJour < 30 : intJour > 0 && intJour < 29;
		} catch (Exception e) {
			System.out.println(e);
			booVerif = false;
		}

		return booVerif;
	}

	private void clearSelections() {
		tab.getTable().getSelectionModel().clearSelection();
		tbl.getTabViewAdherents().getSelectionModel().clearSelection();
		tab.getTablelivre().getSelectionModel().clearSelection();
		tab.getTabledvd().getSelectionModel().clearSelection();
		tab.getTableper().getSelectionModel().clearSelection();

	}

}
