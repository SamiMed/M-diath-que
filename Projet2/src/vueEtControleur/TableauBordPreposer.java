package vueEtControleur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javax.sound.midi.SysexMessage;

import application.DemarrerAppli;
import donnees.Adherent;
import donnees.Catalogue;
import donnees.DVD;
import donnees.Document;
import donnees.Livre;
import donnees.Periodique;
import donnees.Pret;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableauBordPreposer extends BorderPane {

	private TableCatalogue tbl = TableCatalogue.getInstance();
	private static TableauBordPreposer instance = null;
	private VBox vueGestionAdherent;
	private panneauControle panneauControle;
	private Document Doc;
	private Adherent adherent;
	private Button btnDeconnexion;
	private Button btnAjouterAdherent;
	private Button btnSupprimerAdh;
	private Button btnModifAdh;
	private Button btnAjouterDoc;
	private Button btnPayerAmende;
	private Button btnRetirerDoc;
	private Button btnInscPret;
	private Button btnInscRetou;
	private Button btnConfirmer;
	private Button btnAnnuler;
	private TableView<Document> tabViewDocs;
	private TableView<Livre> tabViewLivres;
	private TableView<DVD> tabViewDVD;
	private TableView<Periodique> tabViewPeri;
	private ObservableList<Document> obsDoc;
	private ObservableList<Livre> obsLivre;
	private ObservableList<Periodique> obsPeriodique;
	private ObservableList<DVD> obsDvd;
	private Boolean booClear = false;
	private TableView<Adherent> tabViewAdherents = new TableView<Adherent>(); // Table de vue des Adhérents.
	private ObservableList<Adherent> obsListTousAdherents;
	private Catalogue catalog = Catalogue.getInstance();
	private ArrayList<Livre> arrli = catalog.getLstLivres();
	private ArrayList<DVD> arrDvd = catalog.getLstDvd();
	private ArrayList<Periodique> arrPerio = catalog.getLstPeriodiques();
	private String strCompteur;
	private ArrayList<Adherent> arrTousAdherents = new ArrayList<Adherent>();
	private Scene sceneMenuPreposer = new Scene(this);
	private Stage stageAjoutPretAdh;
	private Pret pret;

	public Scene CreerInterfacePreposer() {
		// TODO Auto-generated constructor stub
		
		this.getChildren().clear();
		setArrTousAdherents(getArrTousAdherents());
		setObservableAdherent(getArrTousAdherents());
		vueGestionAdherent = creerGestionAdherent();
		tabViewDocs = tbl.getTable();
		tabViewDVD = tbl.getTabledvd();
		tabViewLivres = tbl.getTablelivre();
		tabViewPeri = tbl.getTableper();
		this.setLeft(tbl.inserTableDansTab(false, catalog.getLstDocuments(), catalog.getLstLivres(),
				catalog.getLstDvd(), catalog.getLstPeriodiques()));

		tabViewDocs.getSelectionModel().selectedItemProperty().addListener(getGestionChangement());
		tabViewLivres.getSelectionModel().selectedItemProperty().addListener(getGestionChangement());
		tabViewDVD.getSelectionModel().selectedItemProperty().addListener(getGestionChangement());
		tabViewPeri.getSelectionModel().selectedItemProperty().addListener(getGestionChangement());
		tabViewAdherents.getSelectionModel().selectedItemProperty().addListener(gestionAdherent);

		obsLivre = FXCollections.observableArrayList(arrli);
		obsPeriodique = FXCollections.observableArrayList(arrPerio);
		obsDvd = FXCollections.observableArrayList(arrDvd);

		panneauControle = new panneauControle();

		btnDeconnexion = new Button("Déconnexion");
		btnDeconnexion.setOnAction(panneauControle);

		// gestion adhérent
		btnAjouterAdherent = new Button("Ajouter adhérent");
		btnAjouterAdherent.setOnAction(panneauControle);

		btnSupprimerAdh = new Button("Supprimer adhérent");
		btnSupprimerAdh.setOnAction(panneauControle);

		btnModifAdh = new Button("Modifier adhérent");
		btnModifAdh.setOnAction(panneauControle);

		// catalogue
		btnAjouterDoc = new Button("Ajouter un document");
		btnAjouterDoc.setOnAction(panneauControle);

		// prêt
		btnRetirerDoc = new Button("Retirer un document");
		btnRetirerDoc.setOnAction(panneauControle);

		btnPayerAmende = new Button("Payer une amende");
		btnPayerAmende.setOnAction(gestionAmende);

		btnInscPret = new Button("Inscrire un prêt");
		btnInscRetou = new Button("Inscrire un retour");

		Accordion accordion = new Accordion();
		VBox VBoxMenu = new VBox(5);
		VBox vBoxCatalogue = new VBox(8);

		vBoxCatalogue.setPadding(new Insets(10));
		HBox.setHgrow(btnAjouterDoc, Priority.ALWAYS);
		HBox.setHgrow(btnRetirerDoc, Priority.ALWAYS);

		VBox vBoxAdherents = new VBox(8);
		vBoxAdherents.setPadding(new Insets(10));
		btnAjouterAdherent.setMaxWidth(Double.MAX_VALUE);
		btnModifAdh.setMaxWidth(Double.MAX_VALUE);
		btnSupprimerAdh.setMaxWidth(Double.MAX_VALUE);
		btnPayerAmende.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(btnAjouterAdherent, Priority.ALWAYS);
		HBox.setHgrow(btnModifAdh, Priority.ALWAYS);
		HBox.setHgrow(btnSupprimerAdh, Priority.ALWAYS);
		HBox.setHgrow(btnPayerAmende, Priority.ALWAYS);

		VBox vBoxPrets = new VBox(8);
		vBoxPrets.setPadding(new Insets(10));
		btnInscPret.setMaxWidth(Double.MAX_VALUE);
		btnInscRetou.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(btnInscPret, Priority.ALWAYS);

		btnInscPret.setOnAction(gestionEmprunts);

		btnInscRetou.setOnAction(gestionRetour);

		vBoxCatalogue.getChildren().addAll(btnAjouterDoc, btnRetirerDoc);
		vBoxAdherents.getChildren().addAll(btnAjouterAdherent, btnModifAdh, btnSupprimerAdh, btnPayerAmende);
		vBoxPrets.getChildren().addAll(btnInscPret, btnInscRetou);

		TitledPane paneCatalogue = new TitledPane("Gestion catalogue", vBoxCatalogue);
		TitledPane panePret = new TitledPane("Gestion prêt", vBoxPrets);
		TitledPane panAdherent = new TitledPane("Gestion adhérent", vBoxAdherents);

		accordion.getPanes().addAll(paneCatalogue, panePret, panAdherent);
		accordion.setExpandedPane(paneCatalogue);
		VBox.setMargin(btnDeconnexion, new Insets(0, 10, 0, 10));
		btnDeconnexion.setMaxWidth(btnAjouterAdherent.getMaxWidth());
		VBoxMenu.getChildren().addAll(accordion, btnDeconnexion);

		panAdherent.setOnMouseClicked(gerrerVue);
		paneCatalogue.setOnMouseClicked(gerrerVue);
		panePret.setOnMouseClicked(gerrerVue);
		this.setRight(VBoxMenu);
		return sceneMenuPreposer;
	}

	public void setArrTousAdherents(ArrayList<Adherent> arrTousAdherents) {
		this.arrTousAdherents = arrTousAdherents;

	}

	public void creerInterfaceInscrirePret() {

		stageAjoutPretAdh = new Stage();
		VBox vBoxAjoutPret = new VBox();
		btnAnnuler = new Button("Annuler");
		btnConfirmer = new Button("Confirmer");
		btnConfirmer.setDefaultButton(true);
		btnAnnuler.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				stageAjoutPretAdh.close();
				tabViewDocs.getSelectionModel().clearSelection();
			}
		});

		HBox hBoxBtnBas = new HBox();
		hBoxBtnBas.getChildren().addAll(btnConfirmer, btnAnnuler);
		hBoxBtnBas.setSpacing(10);
		hBoxBtnBas.setPadding(new Insets(10));

		vBoxAjoutPret.getChildren().addAll(vueGestionAdherent, hBoxBtnBas);
		hBoxBtnBas.setAlignment(Pos.TOP_RIGHT);

		btnConfirmer.setOnAction(e -> {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);

			if (adherent == null) {

				alert.setContentText("Vous devez sélectionner l'adhérent qui veut emprunter le document.");
				alert.show();
			}

			else if (!adherent.getStrAmende().equals("0.00$")) {

				alert.setContentText("L'adhérent ne peut pas emprunter de documents puisqu'il a des frais à payer.");
				alert.show();

			}

			else if (!adherent.testerNombrePret(adherent, Doc)) {
				alert.setContentText("L'adhérent  a atteint la limite d'emprunt pour ce type de document.");
				alert.show();
			}

			else {
				pret = new Pret(Doc, adherent);
				String strEmprunteur = (adherent.getStrPrenom() + " " + adherent.getStrNom());
				Doc.setNouvPret();
				Doc.setStrEmprunt(strEmprunteur);
				adherent.setArrPrets(pret);
				mettreAJourTables();
				if (Doc instanceof Livre) {
					adherent.setIntNbLivreEmprunt();
					;
				} else if (Doc instanceof DVD) {
					adherent.setIntNbDvdEmprunt();
				} else {
					adherent.setIntNbPerioEmprunt();
				}

				Doc = null;
				adherent = null;
				tabViewDocs.getSelectionModel().clearSelection();
				tabViewDVD.getSelectionModel().clearSelection();
				tabViewLivres.getSelectionModel().clearSelection();
				tabViewPeri.getSelectionModel().clearSelection();
				stageAjoutPretAdh.close();
			}
		});

		Scene sceneAjoutPretAdh = new Scene(vBoxAjoutPret, 972, 440);
		stageAjoutPretAdh.setTitle("Effectuer un prêt");
		stageAjoutPretAdh.setScene(sceneAjoutPretAdh);
		stageAjoutPretAdh.getIcons().add(new Image("icon-collection.png"));
		stageAjoutPretAdh.setResizable(false);
		clearSelections();
		tabViewAdherents.getSelectionModel().clearSelection();
		stageAjoutPretAdh.showAndWait();
	}

	private void mettreAJourTables() {

		tabViewDocs.refresh();
		tabViewLivres.refresh();
		tabViewDVD.refresh();
		tabViewPeri.refresh();
		tabViewAdherents.refresh();

	}

	private EventHandler<ActionEvent> gestionAmende = e -> {

		Alert alert = null;

		if (adherent == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Vous devez sélectionner l'adhérent pour qui payer le solde.");
			Optional<ButtonType> retour = alert.showAndWait();
		} else {

			if (adherent.getStrAmende().equals("0.00$")) {

				alert = new Alert(AlertType.WARNING);
				alert.setHeaderText(null);
				alert.setTitle("Avertissement");
				alert.setContentText("Le solde de l'adhérent ne peut être payé, car il est de 0.00 $.");
				Optional<ButtonType> retour = alert.showAndWait();
				adherent = null;
			} else {

				alert = new Alert(AlertType.WARNING);
				alert.setHeaderText(null);
				alert.setTitle("Avertissement");
				alert.setContentText(
						"Le solde de l'adhérent est payé, ancien solde de " + adherent.getStrAmende() + " .");
				Optional<ButtonType> retour = alert.showAndWait();

				adherent.setStrAmende("0.00$");
				for (Pret pret : adherent.getArrPrets()) {
					pret.setStrAmende("0.00$");
				}
				clearSelections();
				mettreAJourTables();
				adherent = null;
			}
		}

	};

	private EventHandler<ActionEvent> gestionEmprunts = e -> {

		Alert alert = null;

		if (Doc == null) {

			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Vous devez sélectionner le document à emprunter");
			alert.showAndWait();

		} else if (!Doc.getDisponible().equals("Disponible")) {

			alert = new Alert(AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setTitle("Avertissement");
			alert.setContentText(
					"Malhereusement, le document que vous avez sélectionné n'est pas disponible pour le momment.");
			alert.showAndWait();
		}
		else {

			clearSelections();
			creerInterfaceInscrirePret();
		}
	};

	private EventHandler<ActionEvent> gestionRetour = e -> {

		Alert alert = null;

		if (Doc == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Vous devez sélectionner le document à retourner.");
			alert.show();
		}

		else if (Doc.getDisponible().equals("Disponible")) {
			alert = new Alert(AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Le document sélectionné n'est pas emprunté");
			alert.show();

		} else {
			Pret pretActuel = null;
			for (Adherent adherent : getArrTousAdherents()) {
				for (Pret adh : adherent.getArrPrets()) {
					
					if (adh.getDocument().getNoDoc().trim().equals(tabViewDocs.getSelectionModel().selectedItemProperty().get().getNoDoc().trim())) {
						Doc.setPretCourant(adh);
						pretActuel = Doc.getPretCourant();
					}
				}
			}

			if (pretActuel != null) {

				Adherent adhPretActuel = pretActuel.getAdherent();
				pretActuel.setDateRetour(LocalDate.now());
				if (Doc instanceof Livre) {
					adhPretActuel.setIntNbLivreRetour();
				} else if (Doc instanceof DVD) {
					adhPretActuel.setIntNbDvdRetour();
				} else {
					adhPretActuel.setIntNbPerioRetour();
				}
				Doc.setDisponible("Disponible");
				adhPretActuel.ajouterFraisAdherent("0.50$");
				pretActuel.setStrAmende("0.50$");
				Doc.setStrEmprunt("");
				Doc.setPretCourant(null);
				clearSelections();
				mettreAJourTables();
				Doc = null;
				adhPretActuel = null;
			}
		}

	};

	private Boolean clearSelections() {

		if (booClear) {

			tabViewDocs.getSelectionModel().clearSelection();
			tabViewLivres.getSelectionModel().clearSelection();
			tabViewDVD.getSelectionModel().clearSelection();
			tabViewPeri.getSelectionModel().clearSelection();
			tabViewAdherents.getSelectionModel().clearSelection();

		}

		return booClear;
	}

	private EventHandler<MouseEvent> gerrerVue = e -> {

		clearSelections();
		if (((TitledPane) e.getSource()).getText().equals("Gestion adhérent")) {

			this.setLeft(vueGestionAdherent);
			tabViewAdherents.getSelectionModel().clearSelection();

		} else {
			clearSelections();
			this.setLeft(tbl.getVboxTable());

		}

		if (((TitledPane) e.getSource()).getText().equals("Gestion adhérent")) {
			this.setLeft(vueGestionAdherent);

		} else {

			this.setLeft(tbl.getVboxTable());

		}
		clearSelections();

	};

	@SuppressWarnings("unchecked")
	private VBox creerGestionAdherent() {

		VBox vbAdherents = new VBox();
		obsListTousAdherents = getObsListTousAdherents();
		TableColumn<Adherent, String> colNumAdherent = new TableColumn<Adherent, String>("Numéro d'adhérent");
		TableColumn<Adherent, String> colNomAdherent = new TableColumn<Adherent, String>("Nom");
		TableColumn<Adherent, String> colPrenomAdherent = new TableColumn<Adherent, String>("Prénom");
		TableColumn<Adherent, String> colAdresseAdherent = new TableColumn<Adherent, String>("Adresse");
		TableColumn<Adherent, String> colTelAdherent = new TableColumn<Adherent, String>("Numéro de téléphone");
		TableColumn<Adherent, Integer> colNbPretAdherent = new TableColumn<Adherent, Integer>("Nombre de prêts actifs");
		TableColumn<Adherent, String> colSoldeAdherent = new TableColumn<Adherent, String>("Solde dû");

		colNumAdherent.setPrefWidth(140);
		colNomAdherent.setPrefWidth(150);
		colPrenomAdherent.setPrefWidth(120);
		colAdresseAdherent.setPrefWidth(150);
		colTelAdherent.setPrefWidth(170);
		colNbPretAdherent.setPrefWidth(150);
		colSoldeAdherent.setPrefWidth(100);

		colNumAdherent.setCellValueFactory(new PropertyValueFactory<>("strNumAdh"));
		colNomAdherent.setCellValueFactory(new PropertyValueFactory<>("strNom"));
		colPrenomAdherent.setCellValueFactory(new PropertyValueFactory<>("strPrenom"));
		colAdresseAdherent.setCellValueFactory(new PropertyValueFactory<>("strAddresse"));
		colTelAdherent.setCellValueFactory(new PropertyValueFactory<>("strNumTel"));
		colNbPretAdherent.setCellValueFactory(new PropertyValueFactory<>("intNbPrets"));
		colSoldeAdherent.setCellValueFactory(new PropertyValueFactory<>("strAmende"));

		tabViewAdherents.setPrefSize(1510, 1000);
		tabViewAdherents.setItems(obsListTousAdherents);
		tabViewAdherents.getColumns().addAll(colNumAdherent, colNomAdherent, colPrenomAdherent, colAdresseAdherent,
				colTelAdherent, colNbPretAdherent, colSoldeAdherent);

		vbAdherents.getChildren().add(tabViewAdherents);
		tabViewAdherents.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		return vbAdherents;
	}

	public class panneauControle implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub

			if (event.getSource() == btnAjouterDoc) {

				new boiteDiaglogue().creerInterfaceAjoutDoc();
			}

			else if (event.getSource() == btnRetirerDoc) {

				if (Doc == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Erreur");
					alert.setContentText("Vous devez sélectionner un document à supprimer");
					alert.showAndWait();
				}

				else {

					clearSelections();

					if (Doc instanceof Livre) {

						obsLivre = FXCollections.observableArrayList(catalog.getLstLivres());
						obsLivre.remove(Doc);
						catalog.getLstLivres().remove(Doc);
						obsDoc = FXCollections.observableArrayList(catalog.getLstDocuments());
						tbl.setFilteredocument(obsDoc);
						tbl.setFilteredLivre(obsLivre);
						tabViewDocs.setItems(tbl.getFilteredocument());
						tabViewLivres.setItems(tbl.getFilteredLivre());
					}

					else if (Doc instanceof DVD) {

						obsDvd = FXCollections.observableArrayList(catalog.getLstDvd());
						obsDvd.remove(Doc);
						catalog.getLstDvd().remove(Doc);
						obsDoc = FXCollections.observableArrayList(catalog.getLstDocuments());
						tbl.setFilteredocument(obsDoc);
						tbl.setFilteredDvd(obsDvd);
						tabViewDocs.setItems(tbl.getFilteredocument());
						tabViewDVD.setItems(tbl.getFiltereDVD());

					} else {

						obsPeriodique = FXCollections.observableArrayList(catalog.getLstPeriodiques());
						obsPeriodique.remove(Doc);
						catalog.getLstPeriodiques().remove(Doc);
						obsDoc = FXCollections.observableArrayList(catalog.getLstDocuments());
						tbl.setFilteredocument(obsDoc);
						tbl.setFilteredPeriodique(obsPeriodique);
						tabViewDocs.setItems(tbl.getFilteredocument());
						tabViewPeri.setItems(tbl.getFilteredPeriodique());
					}

					Doc = null;

					obsDoc = FXCollections.observableArrayList(catalog.getLstDocuments());
					tbl.setFilteredocument(obsDoc);
					tabViewDocs.setItems(tbl.getFilteredocument());
					catalog.getLstDocuments().remove(Doc);

				}

			}

			else if (event.getSource() == btnAjouterAdherent) {

				clearSelections();

				new boiteDiaglogue().creerInterfaceAjoutAdherent();

			}

			else if (event.getSource() == btnSupprimerAdh) {

				if (adherent == null) {

					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Erreur");
					alert.setContentText("Vous devez sélectionner un adhérent à supprimer");
					alert.showAndWait();

				}

				else {

					clearSelections();

					arrTousAdherents.remove(adherent);
					setObservableAdherent(getArrTousAdherents());
					tabViewAdherents.setItems(obsListTousAdherents);

					clearSelections();

				}
			}

			else if (event.getSource() == btnModifAdh) {

				if (adherent == null) {

					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Erreur");
					alert.setContentText("Vous devez sélectionner l'adhérent à modifier.");
					alert.showAndWait();

				}

				else {

					new boiteDiaglogue().creerInterfaceModiferAdherent((adherent));

				}

			}

			else if (event.getSource() == btnDeconnexion) {

				try {
					tabViewDocs.setItems(null);
					tabViewDVD.setItems(null);
					tabViewLivres.setItems(null);
					tabViewPeri.setItems(null);
					tabViewAdherents.setItems(null);
					tabViewAdherents.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

					tabViewDocs.getColumns().clear();
					tabViewDVD.getColumns().clear();
					tabViewLivres.getColumns().clear();
					tabViewPeri.getColumns().clear();
					tabViewAdherents.getColumns().clear();

					tabViewDocs.getSelectionModel().selectedItemProperty().removeListener(getGestionChangement());
					tabViewLivres.getSelectionModel().selectedItemProperty().removeListener(getGestionChangement());
					tabViewDVD.getSelectionModel().selectedItemProperty().removeListener(getGestionChangement());
					tabViewPeri.getSelectionModel().selectedItemProperty().removeListener(getGestionChangement());
					tabViewAdherents.getSelectionModel().selectedItemProperty().removeListener(gestionAdherent);
					
					tabViewDocs.getSelectionModel().clearSelection();
					tabViewLivres.getSelectionModel().clearSelection();
					tabViewDVD.getSelectionModel().clearSelection();
					tabViewPeri.getSelectionModel().clearSelection();
					tabViewAdherents.getSelectionModel().clearSelection();
				
					
					Connection.getInstance().getStgTableauBordPreposer().close();
					DemarrerAppli.getInstance().setStagee(Connection.getInstance().creerInterfaceConnexion(DemarrerAppli.getInstance().getStagee()));
					DemarrerAppli.getInstance().getStagee().show();

				} catch (Exception e) {
					// TODO Auto-generated catch block

				}
			}

		}

	}

	public Adherent getAdherent() {
		return adherent;
	}

	public String getStrCompteur() {
		return strCompteur;
	}

	public static TableauBordPreposer getInstance() {

		if (instance == null) {
			instance = new TableauBordPreposer();
			return instance;

		} else {
			return instance;
		}

	}

	public void retrouverPlusPetit() {

		int intVieuxPetit = 1;

		ArrayList<Integer> arrNombres = new ArrayList<Integer>();

		for (Adherent a : arrTousAdherents) {

			arrNombres.add(Integer.parseInt(a.getStrNumAdh().substring(1)));
		}

		if (!arrNombres.isEmpty()) {
			Collections.sort(arrNombres);

			for (int i : arrNombres) {

				if (intVieuxPetit < i)
					break;

				intVieuxPetit++;
			}
		}

		strCompteur = Integer.toString(intVieuxPetit);
	}

	public ArrayList<Adherent> getArrTousAdherents() {
		return arrTousAdherents;
	}

	public void ajoutAdherent(Adherent adherent) {
		arrTousAdherents.add(adherent);
	}

	public ObservableList<Adherent> getObsListTousAdherents() {
		return obsListTousAdherents;
	}

	public void setObservableAdherent(ArrayList<Adherent> ArrayAdherent) {
		obsListTousAdherents = FXCollections.observableArrayList(ArrayAdherent);
	}

	public TableView<Adherent> getTabViewAdherents() {
		return tabViewAdherents;
	}

	public void setTabViewAdherents(TableView<Adherent> tabViewAdherents) {

		this.tabViewAdherents = tabViewAdherents;

	}

	public ChangeListener<Document> getGestionChangement() {
		return gestionChangement;
	}

	public void setGestionChangement(ChangeListener<Document> gestionChangement) {
		this.gestionChangement = gestionChangement;
	}

	 ChangeListener<Document> gestionChangement = new ChangeListener<Document>() {

		@Override
		public void changed(ObservableValue<? extends Document> observable, Document oldDoc, Document newDoc) {

			if (!booClear)
				clearSelections();
			Doc = newDoc;

		}
	};

	// Évènement qui écoute si y a quelque chose qui se produit dans la table
	// adhérent
	ChangeListener<Adherent> gestionAdherent = new ChangeListener<Adherent>() {
		@Override
		public void changed(ObservableValue<? extends Adherent> observable, Adherent oldAdherent, Adherent newAdh) {

			if (!booClear)
				clearSelections();
			adherent = newAdh;
		}
	};

}
