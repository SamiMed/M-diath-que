package vueEtControleur;

import java.util.ArrayList;

import application.DemarrerAppli;
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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TableauBordAdherent extends HBox {

	private Catalogue catalog = Catalogue.getInstance();
	private TableCatalogue tbl = TableCatalogue.getInstance();
	private static TableauBordAdherent instance;
	private TableView<Document> tabViewDocs;
	private TableView<Livre> tabViewLivres;
	private TableView<DVD> tabViewDVD;
	private TableView<Periodique> tabViewPeri;
	private ObservableList<Livre> obsLivre;
	private ObservableList<Periodique> obsPeriodique;
	private ObservableList<DVD> obsDvd;
	private ArrayList<Livre> arrli = catalog.getLstLivres();
	private ArrayList<DVD> arrDvd = catalog.getLstDvd();
	private ArrayList<Periodique> arrPerio = catalog.getLstPeriodiques();
	private Scene sceneMenuPreposer = new Scene(this);
	private Adherent adherent;
	private Button btnDeconnexion;
	private RadioButton rbNomPrenom, rbTelephone;
	private TextField tfNom, tfPrenom, tfTelephone;

	public Scene CreerInterfaceadherant() {
		// TODO Auto-generated constructor stub
		this.getChildren().clear();
		ToggleGroup tgModeId = new ToggleGroup();
		BorderPane borderCatalogue = new BorderPane();
		tabViewDocs = tbl.getTable();
		tabViewDVD = tbl.getTabledvd();
		tabViewLivres = tbl.getTablelivre();
		tabViewPeri = tbl.getTableper();

		borderCatalogue.setLeft(tbl.inserTableDansTab(true, catalog.getLstDocuments(), catalog.getLstLivres(),
				catalog.getLstDvd(), catalog.getLstPeriodiques()));

		TableauBordPreposer tablBordPrepo = TableauBordPreposer.getInstance();
		tabViewDocs.getSelectionModel().selectedItemProperty().addListener(tablBordPrepo.gestionChangement);
		tabViewLivres.getSelectionModel().selectedItemProperty().addListener(tablBordPrepo.gestionChangement);
		tabViewDVD.getSelectionModel().selectedItemProperty().addListener(tablBordPrepo.gestionChangement);
		tabViewPeri.getSelectionModel().selectedItemProperty().addListener(tablBordPrepo.gestionChangement);
		obsLivre = FXCollections.observableArrayList(arrli);
		obsPeriodique = FXCollections.observableArrayList(arrPerio);
		obsDvd = FXCollections.observableArrayList(arrDvd);

		GridPane gridConne = new GridPane();

		BorderStrokeStyle bssTry = new BorderStrokeStyle(StrokeType.OUTSIDE, StrokeLineJoin.ROUND, null, 5, 5, null);
		gridConne.setBorder(new Border(
				new BorderStroke(Color.BLACK, bssTry, new CornerRadii(5), new BorderWidths(2), new Insets(4))));
		gridConne.setPadding(new Insets(10));
		Label lblNomTel = new Label("Nom :");
		Label lblPrenom = new Label("Prénom :");

		gridConne.add(new Label("Identification par"), 0, 1, 1, 1);

		rbNomPrenom = new RadioButton("Nom et Prénom");
		rbNomPrenom.setPadding(new Insets(0, 0, 0, 0));
		rbTelephone = new RadioButton("Téléphone");

		btnDeconnexion = new Button("Déconnexion");
		btnDeconnexion.setOnAction(deconect);
		Button btnQuitter = new Button("Quitter");
		btnQuitter.setOnAction(deconect);

		tfTelephone = new TextField();
		tfPrenom = new TextField();
		tfNom = new TextField();

		Button btnDossier = new Button("Consulter mon dossier");

		btnDossier.setOnAction(gestionConnection);
		gridConne.add(rbNomPrenom, 1, 0);
		gridConne.add(rbTelephone, 1, 1);
		gridConne.add(lblNomTel, 0, 2);
		gridConne.add(btnDossier, 0, 4, 2, 1);
		gridConne.add(btnQuitter, 0, 5, 2, 2);
		btnQuitter.setPrefWidth(100);
		gridConne.setHgap(40);
		gridConne.setVgap(20);
		gridConne.setGridLinesVisible(false);
		GridPane.setHalignment(btnDossier, HPos.CENTER);
		GridPane.setHalignment(btnQuitter, HPos.CENTER);

		rbNomPrenom.setOnAction(event -> {
			gridConne.getChildren().remove(tfTelephone);
			tfTelephone.clear();

			tfNom.setPrefWidth(125);
			tfNom.setMaxWidth(125);
			tfPrenom.setPrefWidth(125);
			tfPrenom.setMaxWidth(125);

			lblNomTel.setText("Nom :");

			gridConne.add(lblPrenom, 0, 3);
			gridConne.add(tfNom, 1, 2);
			gridConne.add(tfPrenom, 1, 3);

			adherent = null;
			tfNom.requestFocus();

		});

		rbTelephone.setOnAction(event -> {

			tfNom.clear();
			tfPrenom.clear();
			lblNomTel.setText("Téléphone");

			tfTelephone.setPrefWidth(125);
			tfTelephone.setMaxWidth(125);
			gridConne.getChildren().removeAll(tfNom, lblPrenom, tfPrenom);
			gridConne.add(tfTelephone, 1, 2);
			adherent = null;
			tfTelephone.requestFocus();
		});

		rbNomPrenom.fire();
		tgModeId.getToggles().addAll(rbNomPrenom, rbTelephone);

		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(borderCatalogue, gridConne);
		return sceneMenuPreposer;

	}

	private EventHandler<ActionEvent> deconect = e -> {

		try {
			tfNom.clear();
			tfPrenom.clear();
			tfTelephone.clear();
			Connection.getInstance().getStgTableauBordAdherent().close();

			DemarrerAppli.getInstance().setStagee(
					Connection.getInstance().creerInterfaceConnexion(DemarrerAppli.getInstance().getStagee()));
			DemarrerAppli.getInstance().getStagee().show();

		} catch (Exception e2) {
			// TODO: handle exception
		}

	};

	EventHandler<ActionEvent> gestionConnection = new EventHandler<ActionEvent>() {

		private Object adherentarr;

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			Adherent aderents = null;

			if (((Button) event.getSource()).getText().contentEquals("Consulter mon dossier")) {
				String strMessage = "";
				if (Connection.getInstance().ValiderAdherant(tfNom.getText().trim().toString(),
						tfPrenom.getText().trim().toString(), tfTelephone.getText().trim().toString(), rbTelephone,
						rbNomPrenom)) {
					tfNom.clear();
					tfPrenom.clear();
					tfTelephone.clear();

					Connection.getInstance().getStgTableauBordAdherent().close();

					if (!Connection.getInstance().getStgInterfaceAdherentDossier().getModality()
							.equals(Modality.APPLICATION_MODAL)) {

						Connection.getInstance().getStgInterfaceAdherentDossier()
								.initModality(Modality.APPLICATION_MODAL);
					}

					Connection.getInstance().getStgInterfaceAdherentDossier().setScene(null);
					aderents = TableauBordPreposer.getInstance().getArrTousAdherents()
							.get(Connection.getInstance().getIntIndexAdherent());
					Connection.getInstance().getStgInterfaceAdherentDossier()
							.setScene(InterfaceAdherent.getInstance().interfaceDossierAdherent(aderents));
					Connection.getInstance().getStgInterfaceAdherentDossier().show();
				}

			}

		}
	};

	public static TableauBordAdherent getInstance() {

		if (instance == null) {
			instance = new TableauBordAdherent();
			return instance;

		} else {
			return instance;
		}

	}

}
