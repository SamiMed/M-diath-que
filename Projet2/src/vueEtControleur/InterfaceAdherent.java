package vueEtControleur;

import java.time.LocalDate;
import java.util.ArrayList;

import application.DemarrerAppli;
import donnees.Adherent;
import donnees.DVD;
import donnees.Document;
import donnees.Livre;
import donnees.Pret;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class InterfaceAdherent extends BorderPane {

	private Connection connet = Connection.getInstance();
	private static InterfaceAdherent instance = null;
	private Scene sceneInterfaceAdherent = new Scene(this);
	private VBox vboxTableaux = new VBox();
	private TableView<Document> tableDoc = new TableView<Document>();	
	private TableView<Pret> tablePret = new TableView<Pret>();
	private ObservableList<Pret> obsPret;
	private ArrayList<Document> arrDoc;
	private ArrayList<Pret> arrPret;
	private Button btnQuitter;

	public static InterfaceAdherent getInstance() {
		if (instance == null) {
			instance = new InterfaceAdherent();
			return instance;
		} else {
			return instance;
		}
	}

	@SuppressWarnings("unchecked")
	public Scene interfaceDossierAdherent(Adherent aderent) {

		btnQuitter = new Button("Quitter");
		VBox vBoxDroite = new VBox();
		btnQuitter.setPrefSize(140, 20);
		btnQuitter.setMaxSize(140, 20);
		
		vBoxDroite.getChildren().addAll(btnQuitter);
		vBoxDroite.setSpacing(10);
		vBoxDroite.setPrefSize(170, 400);
		vBoxDroite.setMaxSize(170, 400);
		vBoxDroite.setAlignment(Pos.TOP_CENTER);
		
	
		this.setRight(vBoxDroite);
		btnQuitter.setOnAction((ActionEvent event) -> {

			try {
				tableDoc.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
				tablePret.getSelectionModel().selectedItemProperty().removeListener(gestionChangement);
				tablePret.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
				tableDoc.setItems(null);
				this.getChildren().clear();
				vboxTableaux.getChildren().clear();
				tableDoc.getColumns().clear();
				tablePret.getColumns().clear();

				tableDoc.getSelectionModel().clearSelection();
				connet.getStgInterfaceAdherentDossier().close();
				DemarrerAppli.getInstance().setStagee(connet.creerInterfaceConnexion(DemarrerAppli.getInstance().getStagee()));
				DemarrerAppli.getInstance().getStagee().show();

			} catch (Exception e) {
				// TODO Auto-generated catch block

			}
		});

		arrPret = aderent.getArrPrets();
		obsPret = FXCollections.observableArrayList(arrPret);
		arrDoc = new ArrayList<Document>();

		for (Pret prets : arrPret) {
			arrDoc.add(prets.getDocument());

		}

		vboxTableaux.setMinWidth(500);

		tableDoc.setEditable(true);
		TableColumn<Document, String> colonnenoDoc = new TableColumn<Document, String>("Numéro document");
		TableColumn<Document, String> colonneTitre = new TableColumn<Document, String>("Titre");
		TableColumn<Document, String> colonneAuteur = new TableColumn<Document, String>("Auteur/réalisateur");
		TableColumn<Document, LocalDate> colonneDate = new TableColumn<Document, LocalDate>("Date");
		tableDoc.getColumns().addAll(colonnenoDoc, colonneTitre, colonneAuteur, colonneDate);
		colonnenoDoc.setSortType(TableColumn.SortType.DESCENDING);
		colonnenoDoc.setPrefWidth(120);
		colonneTitre.setPrefWidth(475);
		colonneAuteur.setPrefWidth(300);
		colonneDate.setPrefWidth(100);
		colonnenoDoc.setCellValueFactory(new PropertyValueFactory<>("noDoc"));
		colonneTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
		colonneAuteur.setCellValueFactory((CellDataFeatures<Document, String> d) -> {

			String auteur = "";
			if (Livre.class.isInstance(d.getValue())) {
				Livre livre = (Livre) d.getValue();
				auteur = livre.getAuteur();
			} else if (DVD.class.isInstance(d.getValue())) {
				DVD dvd = (DVD) d.getValue();

				auteur = dvd.getStrRealisateur();
			}
			return new ReadOnlyObjectWrapper<String>(auteur);
		});

		colonneDate.setCellValueFactory(new PropertyValueFactory<>("dateParution"));
		tableDoc.setPrefSize(1050, 200);

		TableColumn<Pret, Integer> colonneNumEmprunt = new TableColumn<Pret, Integer>("Numero d'emprunt");
		TableColumn<Pret, LocalDate> colonneDateEmprun = new TableColumn<Pret, LocalDate>("Date d'emprunt");
		TableColumn<Pret, LocalDate> colonneDateRetourPrevue = new TableColumn<Pret, LocalDate>("Date de retour prévue");
		TableColumn<Pret, LocalDate> colonneDateRetour = new TableColumn<Pret, LocalDate>("Date de retour");
		TableColumn<Pret, String> colonneAmende = new TableColumn<Pret, String>("Amende");

		tablePret.getColumns().addAll(colonneNumEmprunt, colonneDateEmprun, colonneDateRetourPrevue, colonneDateRetour,
				colonneAmende);
		colonneNumEmprunt.setSortType(TableColumn.SortType.DESCENDING);
		colonneDateEmprun.setPrefWidth(200);
		colonneDateRetourPrevue.setPrefWidth(200);
		colonneDateRetour.setPrefWidth(200);
		colonneAmende.setPrefWidth(250);

		colonneNumEmprunt.setCellValueFactory(new PropertyValueFactory<>("intNumPret"));
		colonneDateEmprun.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
		colonneDateRetourPrevue.setCellValueFactory(new PropertyValueFactory<>("dateRetourPrevue"));
		colonneDateRetour.setCellValueFactory(new PropertyValueFactory<>("dateDeRetour"));
		colonneAmende.setCellValueFactory(new PropertyValueFactory<>("strAmende"));

		tablePret.setPrefSize(1050, 500);
		tablePret.setItems(obsPret);
		vboxTableaux.getChildren().addAll(tableDoc, tablePret);
		this.setLeft(vboxTableaux);
		vboxTableaux.setSpacing(50);
		tablePret.getSelectionModel().selectedItemProperty().addListener(gestionChangement);

		tableDoc.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tablePret.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		return sceneInterfaceAdherent;
	}

	ChangeListener<Pret> gestionChangement = new ChangeListener<Pret>() {

		@Override
		public void changed(ObservableValue<? extends Pret> observable, Pret oldPret, Pret newPret) {

			if (newPret.getDocument() != null) {
				tableDoc.setItems(FXCollections.observableArrayList(newPret.getDocument()));
			}
		}
	};

}
