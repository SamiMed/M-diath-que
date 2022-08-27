package vueEtControleur;

import java.time.LocalDate;
import java.util.ArrayList;

import donnees.DVD;
import donnees.Document;
import donnees.Livre;
import donnees.Periodique;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TableCatalogue {

	private static TableCatalogue instance = null;
	private TableView<Document> table = new TableView<Document>();
	private TableView<Livre> tablelivre = new TableView<Livre>();
	private TableView<DVD> tabledvd = new TableView<DVD>();
	private TableView<Periodique> tableper = new TableView<Periodique>();
	private FilteredList<Document> filteredocument;
	private FilteredList<Livre> filteredLivre;
	private FilteredList<DVD> filteredDvd;
	private FilteredList<Periodique> filteredPeriodique;
	private ObservableList<Document> docs;
	private ObservableList<Livre> livre;
	private ObservableList<Periodique> periodique;
	private ObservableList<DVD> dvd;
	private VBox vboxTable;
	private RadioButton rbAuteur = new RadioButton("Auteur/Réalisateur");
	private RadioButton rbMotClee = new RadioButton("Mot clé");
	private ToggleGroup group = new ToggleGroup();
	private TextField txtRecherche = new TextField();

	@SuppressWarnings("unchecked")
	public TableView<Document> afficheDocument(ArrayList<Document> arrDoc) {
		
		setObservableDocument(arrDoc);
		setFilteredocument(docs);
		table.setEditable(true);
		TableColumn<Document, String> colonneNumLivre = new TableColumn<Document, String>("Numéro document");
		TableColumn<Document, String> colonneTitre = new TableColumn<Document, String>("Titre");
		TableColumn<Document, String> colonneRealisateur = new TableColumn<Document, String>("Réalisateur");
		TableColumn<Document, LocalDate> colonneDate = new TableColumn<Document, LocalDate>("Date de parution");
		TableColumn<Document, String> colonneDisp = new TableColumn<Document, String>("État");
		TableColumn<Document, Integer> colonneNbPrets = new TableColumn<Document, Integer>("NbPrêts");
		TableColumn<Document, String> colonneEmprunteur = new TableColumn<Document, String>("Emprunteur");
		

		colonneNumLivre.setSortType(TableColumn.SortType.DESCENDING);
		colonneNumLivre.setPrefWidth(150);
		colonneTitre.setPrefWidth(575);
		colonneDate.setPrefWidth(150);
		colonneDisp.setPrefWidth(100);
		colonneRealisateur.setPrefWidth(280);
		colonneNbPrets.setPrefWidth(70);
		colonneEmprunteur.setPrefWidth(150);

		colonneNumLivre.setCellValueFactory(new PropertyValueFactory<>("noDoc"));
		colonneTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
		colonneDate.setCellValueFactory(new PropertyValueFactory<>("dateParution"));
		colonneDisp.setCellValueFactory(new PropertyValueFactory<>("disponible"));
		colonneNbPrets.setCellValueFactory(new PropertyValueFactory<>("pretActif"));
		colonneRealisateur.setCellValueFactory((CellDataFeatures<Document, String> d) -> {

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
		colonneEmprunteur.setCellValueFactory(new PropertyValueFactory<>("strEmprunt"));

		txtRecherche.textProperty().addListener(obs -> {
			String filter = txtRecherche.getText();
			if (filter == null || filter.length() == 0) {
				filteredocument.setPredicate(s -> true);
			} else {
				if (rbAuteur.isSelected()) {
					filteredocument.setPredicate(s -> s.contains(filter));

				} else {
					filteredocument.setPredicate(s -> s.containsMotCle(filter));

				}
			}
		});
		table.setPrefSize(1500, 500);
		table.getColumns().addAll(colonneNumLivre, colonneTitre, colonneDate, colonneDisp, colonneRealisateur,
				colonneNbPrets, colonneEmprunteur);
		table.setItems(filteredocument);
		return table;
	}

	@SuppressWarnings("unchecked")
	public TableView<Livre> afficheLivre(ArrayList<Livre> arrlivre) {

		setObservableLivre(arrlivre);
		setFilteredLivre(livre);
		tablelivre.setEditable(true);
		TableColumn<Livre, String> colonneNumLivre = new TableColumn<Livre, String>("Numéro document");
		TableColumn<Livre, String> colonneTitre = new TableColumn<Livre, String>("Titre");
		TableColumn<Livre, String> colonneAuteur = new TableColumn<Livre, String>("Auteur");
		TableColumn<Livre, LocalDate> colonneDate = new TableColumn<Livre, LocalDate>("Date de parution");
		TableColumn<Livre, String> colonneDisp = new TableColumn<Livre, String>("État");
		TableColumn<Livre, Integer> colonneNbPrets = new TableColumn<Livre, Integer>("NbPrêts");
		TableColumn<Livre, String> colonneEmprunteur = new TableColumn<Livre, String>("Emprunteur");

		tablelivre.getColumns().addAll(colonneNumLivre, colonneTitre, colonneAuteur, colonneDate, colonneDisp,
				colonneNbPrets, colonneEmprunteur);

		colonneNumLivre.setSortType(TableColumn.SortType.DESCENDING);
		colonneNumLivre.setPrefWidth(150);
		colonneTitre.setPrefWidth(575);
		colonneDate.setPrefWidth(150);
		colonneDisp.setPrefWidth(100);
		colonneAuteur.setPrefWidth(280);

		colonneNbPrets.setPrefWidth(100);
		colonneEmprunteur.setPrefWidth(140);
		colonneNumLivre.setCellValueFactory(new PropertyValueFactory<>("noDoc"));
		colonneTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
		colonneDate.setCellValueFactory(new PropertyValueFactory<>("dateParution"));
		colonneDisp.setCellValueFactory(new PropertyValueFactory<>("disponible"));
		colonneAuteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));

		colonneNbPrets.setCellValueFactory(new PropertyValueFactory<>("pretActif"));
		colonneEmprunteur.setCellValueFactory(new PropertyValueFactory<>("strEmprunt"));

		txtRecherche.textProperty().addListener(obs -> {
			String filter = txtRecherche.getText();
			if (filter == null || filter.length() == 0) {
				filteredLivre.setPredicate(s -> true);
			} else {
				if (rbAuteur.isSelected()) {
					filteredLivre.setPredicate(s -> s.contains(filter));

				} else {
					filteredLivre.setPredicate(s -> s.containsMotCle(filter));

				}
			}
		});
		tablelivre.setPrefSize(1500, 500);
		tablelivre.setItems(filteredLivre);
		return tablelivre;
	}

	@SuppressWarnings("unchecked")
	public TableView<DVD> afficheDVD(ArrayList<DVD> arrdvd) {

		setObservableDVD(arrdvd);
		setFilteredDvd(dvd);
		tabledvd.setEditable(true);
		TableColumn<DVD, String> colonneNumDvd = new TableColumn<DVD, String>("Numéro document");
		TableColumn<DVD, String> colonneTitre = new TableColumn<DVD, String>("Titre");
		TableColumn<DVD, String> colonneRealisateur = new TableColumn<DVD, String>("Réalisateur");
		TableColumn<DVD, LocalDate> colonneDate = new TableColumn<DVD, LocalDate>("Date de parution");
		TableColumn<DVD, Integer> colonneNbDisque = new TableColumn<DVD, Integer>("Nombres de disques");
		TableColumn<DVD, String> colonneDisp = new TableColumn<DVD, String>("État");
		TableColumn<DVD, Integer> colonneNbPrets = new TableColumn<DVD, Integer>("NbPrêts");
		TableColumn<DVD, String> colonneEmprunteur = new TableColumn<DVD, String>("Emprunteur");
		tabledvd.getColumns().addAll(colonneNumDvd, colonneTitre, colonneRealisateur, colonneDate, colonneNbDisque,
				colonneDisp, colonneNbPrets, colonneEmprunteur);

		colonneNumDvd.setSortType(TableColumn.SortType.DESCENDING);
		colonneNumDvd.setPrefWidth(150);
		colonneTitre.setPrefWidth(425);
		colonneDate.setPrefWidth(150);
		colonneDisp.setPrefWidth(100);
		colonneNbDisque.setPrefWidth(150);
		colonneRealisateur.setPrefWidth(280);

		colonneNbPrets.setPrefWidth(100);
		colonneEmprunteur.setPrefWidth(140);
		colonneNumDvd.setCellValueFactory(new PropertyValueFactory<>("noDoc"));
		colonneTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
		colonneDate.setCellValueFactory(new PropertyValueFactory<>("dateParution"));
		colonneDisp.setCellValueFactory(new PropertyValueFactory<>("disponible"));
		colonneNbDisque.setCellValueFactory(new PropertyValueFactory<>("nbDisques"));
		colonneRealisateur.setCellValueFactory(new PropertyValueFactory<>("strRealisateur"));

		colonneNbPrets.setCellValueFactory(new PropertyValueFactory<>("pretActif"));
		colonneEmprunteur.setCellValueFactory(new PropertyValueFactory<>("strEmprunt"));

		txtRecherche.textProperty().addListener(obs -> {
			String filter = txtRecherche.getText();
			if (filter == null || filter.length() == 0) {
				filteredDvd.setPredicate(s -> true);
			} else {
				if (rbAuteur.isSelected()) {
					filteredDvd.setPredicate(s -> s.contains(filter));

				} else {
					filteredDvd.setPredicate(s -> s.containsMotCle(filter));

				}
			}
		});
		tabledvd.setPrefSize(1500, 500);
		tabledvd.setItems(filteredDvd);
		return tabledvd;
	}

	@SuppressWarnings("unchecked")
	public TableView<Periodique> affichePeriodique(ArrayList<Periodique> arrper) {

		setObservablePeriodique(arrper);
		setFilteredPeriodique(periodique);
		tableper.setEditable(true);
		TableColumn<Periodique, String> colonnenoDoc = new TableColumn<Periodique, String>("Numéro document");
		TableColumn<Periodique, String> colonneTitre = new TableColumn<Periodique, String>("Titre");
		TableColumn<Periodique, LocalDate> colonneDate = new TableColumn<Periodique, LocalDate>("Date de parution");
		TableColumn<Periodique, Integer> colonenovol = new TableColumn<Periodique, Integer>("No volume");
		TableColumn<Periodique, Integer> colonenoper = new TableColumn<Periodique, Integer>("No periodique");
		TableColumn<Periodique, String> colonneDisp = new TableColumn<Periodique, String>("État");
		TableColumn<Periodique, Integer> colonneNbPrets = new TableColumn<Periodique, Integer>("NbPrêts");
		TableColumn<Periodique, String> colonneEmprunteur = new TableColumn<Periodique, String>("Emprunteur");
		tableper.getColumns().addAll(colonnenoDoc, colonneTitre, colonneDate, colonenovol, colonenoper, colonneDisp,
				colonneNbPrets, colonneEmprunteur);

		colonnenoDoc.setSortType(TableColumn.SortType.DESCENDING);
		colonnenoDoc.setPrefWidth(160);
		colonneTitre.setPrefWidth(575);
		colonneDate.setPrefWidth(150);
		colonneDisp.setPrefWidth(100);
		colonenovol.setPrefWidth(125);
		colonenoper.setPrefWidth(125);
		colonneNbPrets.setPrefWidth(100);
		colonneEmprunteur.setPrefWidth(160);

		colonnenoDoc.setCellValueFactory(new PropertyValueFactory<>("noDoc"));
		colonneTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
		colonneDate.setCellValueFactory(new PropertyValueFactory<>("dateParution"));
		colonneDisp.setCellValueFactory(new PropertyValueFactory<>("disponible"));
		colonenovol.setCellValueFactory(new PropertyValueFactory<>("noVolume"));
		colonenoper.setCellValueFactory(new PropertyValueFactory<>("noPeriodique"));

		colonneNbPrets.setCellValueFactory(new PropertyValueFactory<>("pretActif"));
		colonneEmprunteur.setCellValueFactory(new PropertyValueFactory<>("strEmprunt"));

		txtRecherche.textProperty().addListener(obs -> {
			String filter = txtRecherche.getText();
			if (filter == null || filter.length() == 0) {
				filteredPeriodique.setPredicate(s -> true);
			} else {
				filteredPeriodique.setPredicate(s -> s.containsMotCle(filter));

			}
		});
		tableper.setPrefSize(1500, 500);
		tableper.setItems(filteredPeriodique);
		return tableper;
	}

	public VBox inserTableDansTab(boolean booColonne, ArrayList<Document> documentarr, ArrayList<Livre> livrearr,
			ArrayList<DVD> dvdarr, ArrayList<Periodique> perarr) {
		
		table.getColumns().clear();
		table.setItems(null);
		tabledvd.getColumns().clear();
		tabledvd.setItems(null);
		tablelivre.getColumns().clear();
		tablelivre.setItems(null);
		tableper.getColumns().clear();
		tableper.setItems(null);
		rbAuteur.setDisable(false);
		rbMotClee.setDisable(false);

		vboxTable = new VBox();
		VBox vboxBase = new VBox();
		TabPane tabPane = new TabPane();
		HBox hboxrecherche = new HBox();
		txtRecherche.setMaxWidth(320);
		Label lblRecherche = new Label("Recherche");
		lblRecherche.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		rbAuteur.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		rbMotClee.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

		Button btnEfface = new Button("Effacer");
		btnEfface.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		btnEfface.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// TODO Auto-generated method stub
				txtRecherche.clear();
			}
		});

		btnEfface.setPrefWidth(175);

		hboxrecherche.setSpacing(20);
		hboxrecherche.setPadding(new Insets(12));
		HBox.setMargin(hboxrecherche, new Insets(10));

		rbAuteur.setToggleGroup(group);
		rbMotClee.setToggleGroup(group);
		rbAuteur.setSelected(true);

		hboxrecherche.getChildren().addAll(lblRecherche, rbAuteur, rbMotClee, txtRecherche, btnEfface);
		vboxBase.getChildren().add(hboxrecherche);
		Tab tabCatalogue = new Tab();
		tabCatalogue.setClosable(false);
		tabCatalogue.setText("Catalogue");
		tabCatalogue.setGraphic(new ImageView(new Image("icon-collection.png")));
		Group gr = new Group();
		VBox vBox = new VBox();
		afficheDocument(documentarr);
		vBox.getChildren().add(table);
		gr.getChildren().add(vBox);
		tabCatalogue.setContent(gr);
		// 2
		Tab tabLivre = new Tab();
		tabLivre.setText("Livre");
		tabLivre.setGraphic(new ImageView(new Image("icon-livre.png")));
		tabLivre.setClosable(false);
		Group gr1 = new Group();
		VBox vBox1 = new VBox();
		afficheLivre(livrearr);
		vBox1.getChildren().add(tablelivre);
		gr1.getChildren().add(vBox1);
		tabLivre.setContent(gr1);
		// 3
		Tab tabDvd = new Tab();
		tabDvd.setText("DVD");
		tabDvd.setGraphic(new ImageView(new Image("icon-dvd.png")));
		tabDvd.setClosable(false);
		Group gr2 = new Group();
		VBox vBox2 = new VBox();
		afficheDVD(dvdarr);
		vBox2.getChildren().add(tabledvd);
		gr2.getChildren().add(vBox2);
		tabDvd.setContent(gr2);
		// 4
		Group gr3 = new Group();
		Tab tabPeriodique = new Tab();
		tabPeriodique.setText("Périodique");
		VBox vbox3 = new VBox();
		affichePeriodique(perarr);
		gr3.getChildren().add(vbox3);
		vbox3.getChildren().add(tableper);
		tabPeriodique.setContent(gr3);
		tabPeriodique.setGraphic(new ImageView(new Image("icon-periodique.png")));
		tabPeriodique.setClosable(false);
		tabPane.getTabs().addAll(tabCatalogue, tabLivre, tabDvd, tabPeriodique);

		Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();

		tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if (newTab.getText().equals(tabPeriodique.getText())) {
				rbAuteur.setDisable(true);
				rbAuteur.setSelected(false);
				rbMotClee.setSelected(true);
			} else {
				rbAuteur.setDisable(false);
			}
		});

		vboxBase.getChildren().add(tabPane);
		setVboxTable(vboxBase);

		if (booColonne) {
			retirerDernierColonneDoc(table);
			retirerDernierColonneLivre(tablelivre);
			retirerDernierColonneDvd(tabledvd);
			retirerDernierColonnePer(tableper);
		}

		return vboxBase;
	}

	public TableView<Document> retirerDernierColonneDoc(TableView<Document> doc) {
		doc.getColumns().remove(6);
		return doc;
	}

	public TableView<Livre> retirerDernierColonneLivre(TableView<Livre> livre) {
		livre.getColumns().remove(6);
		return livre;
	}

	public TableView<DVD> retirerDernierColonneDvd(TableView<DVD> dvd) {
		dvd.getColumns().remove(7);
		return dvd;
	}

	public TableView<Periodique> retirerDernierColonnePer(TableView<Periodique> per) {
		per.getColumns().remove(7);
		return per;
	}

	public static TableCatalogue getInstance() {
		if (instance == null) {
			instance = new TableCatalogue();
			return instance;
		} else {
			return instance;
		}
	}

	public ObservableList<Document> getDocs() {
		return docs;
	}

	public ObservableList<Livre> getLivre() {
		return livre;
	}

	public ObservableList<Periodique> getPeriodique() {
		return periodique;
	}

	public ObservableList<DVD> getDvd() {
		return dvd;
	}

	public VBox getVboxTable() {
		return vboxTable;
	}

	public FilteredList<Document> getFilteredocument() {
		return filteredocument;
	}

	public FilteredList<Livre> getFilteredLivre() {
		return filteredLivre;
	}

	public FilteredList<DVD> getFiltereDVD() {
		return filteredDvd;
	}

	public FilteredList<Periodique> getFilteredPeriodique() {
		return filteredPeriodique;
	}

	public TableView<Document> getTable() {
		return table;
	}

	public TableView<Livre> getTablelivre() {
		return tablelivre;
	}

	public TableView<DVD> getTabledvd() {
		return tabledvd;
	}

	public TableView<Periodique> getTableper() {
		return tableper;
	}

	public void setObservableDocument(ArrayList<Document> Arraydocument) {
		docs = FXCollections.observableArrayList(Arraydocument);
	}

	public void setObservableLivre(ArrayList<Livre> ArrayLivre) {
		livre = FXCollections.observableArrayList(ArrayLivre);
	}

	public void setObservableDVD(ArrayList<DVD> ArrayDVD) {
		dvd = FXCollections.observableArrayList(ArrayDVD);
	}

	public void setObservablePeriodique(ArrayList<Periodique> ArrayPeriodique) {
		periodique = FXCollections.observableArrayList(ArrayPeriodique);
	}

	public void setFilteredocument(ObservableList<Document> document) {
		filteredocument = new FilteredList<>(document, s -> true);
	}

	public void setFilteredLivre(ObservableList<Livre> livre) {
		filteredLivre = new FilteredList<>(livre, s -> true);
	}

	public void setFilteredDvd(ObservableList<DVD> dvd) {
		filteredDvd = new FilteredList<>(dvd, s -> true);
	}

	public void setFilteredPeriodique(ObservableList<Periodique> per) {
		filteredPeriodique = new FilteredList<>(per, s -> true);
	}

	public void setTable(TableView<Document> table) {
		this.table = table;
	}

	public String setColonneEmprunteur(String strNom) {
		return strNom;
	}

	public void setVboxTable(VBox vboxTable) {
		this.vboxTable = vboxTable;
	}
}
