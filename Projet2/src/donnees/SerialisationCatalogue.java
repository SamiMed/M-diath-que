package donnees;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SerialisationCatalogue {

	public void serialise(ArrayList<Document> doc, ArrayList<Livre> livre, ArrayList<DVD> dvd, ArrayList<Periodique> per) {

		try {

			FileOutputStream fichier = new FileOutputStream("catalogue.ser");
			ObjectOutputStream os = new ObjectOutputStream(fichier);
			
			os.writeObject(doc);
			os.writeObject(livre);
			os.writeObject(dvd);
			os.writeObject(per);

			os.close();
			fichier.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
