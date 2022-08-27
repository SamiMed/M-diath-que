package donnees;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import vueEtControleur.TableauBordPreposer;

public class DeserialisationCatalogue {

	private ArrayList<Document> documentsArray = new ArrayList<Document>();
	private ArrayList<Livre> livreArray = new ArrayList<Livre>();
	private ArrayList<DVD> dvdArray = new ArrayList<DVD>();
	private ArrayList<Periodique> periodiqueArray = new ArrayList<Periodique>();
	private TableauBordPreposer tableauPreposer = TableauBordPreposer.getInstance();

	@SuppressWarnings({ "unchecked", "resource" })
	public void deserialise() throws IOException {

		FileInputStream fichier = new FileInputStream("catalogue.ser");
		ObjectInputStream is = new ObjectInputStream(fichier);

		try {

			documentsArray = (ArrayList<Document>) is.readObject();
			livreArray = (ArrayList<Livre>) is.readObject();
			dvdArray = (ArrayList<DVD>) is.readObject();
			periodiqueArray = (ArrayList<Periodique>) is.readObject();
			is.close();
			fichier.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return;
		}

	}

	public ArrayList<Document> getDocumentsArray() {
		return documentsArray;
	}

	public ArrayList<Livre> getLivreArray() {
		return livreArray;
	}

	public ArrayList<DVD> getDvdArray() {
		return dvdArray;
	}

	public ArrayList<Periodique> getPeriodiqueArray() {
		return periodiqueArray;
	}
}