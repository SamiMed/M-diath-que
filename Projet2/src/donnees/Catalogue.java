package donnees;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public final class Catalogue implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Catalogue instance = null;
	private ArrayList<Document> lstDocuments = new ArrayList<>();
	private ArrayList<Livre> lstLivres = new ArrayList<>();
	private ArrayList<Periodique> lstPeriodiques = new ArrayList<>();
	private ArrayList<DVD> lstDvd = new ArrayList<>();
	private String line;
	private StringTokenizer token;
	private LocalDate parsedDate;
	private String strNomDoc;
	private String strTitre;
	private String strDate;
	private String strMotCle;
	private String strRealisateurEtAuteur;
	private int intCompteurDoc = 0;
	private int intComteurDvd = 0;
	private int intCompteurLivres = 0;
	private int intComteurPeriodiques = 0;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
	private static DeserialisationCatalogue des = new DeserialisationCatalogue();
	private static File fichierCatalogue = new File("catalogue.ser");

	public void lire() throws IOException {

		try {
			if (!fichierCatalogue.exists()) {
				lireLivres();
				lireDvd();
				lirePeriodique();

			} else {

				des.deserialise();
				desCat();
			}
		} catch (Exception e) {
			// TODO: handle exception

		}

	}

	private void desCat() {
		lstLivres = des.getLivreArray();
		lstPeriodiques = des.getPeriodiqueArray();
		lstDvd = des.getDvdArray();
	}

	public void ajoutPerio(Periodique periodique) {
		lstPeriodiques.add(periodique);
	}

	public void ajoutLivre(Livre livre) {
		lstLivres.add(livre);
	}

	public void supprimerLivre(Livre livre) {
		lstLivres.remove(livre);
	}

	public void ajoutDvd(DVD dvd) {
		lstDvd.add(dvd);
	}

	public void lireDvd() {

		try (BufferedReader reader = new BufferedReader(new FileReader(new File("DVD.txt")))) {

			int intNbDisque;
			while ((line = reader.readLine()) != null) {

				token = new StringTokenizer(line, ",");

				while (token.hasMoreElements()) {

					strNomDoc = token.nextToken();
					strTitre = token.nextToken();
					strDate = token.nextToken();
					intNbDisque = Integer.parseInt(token.nextToken());
					strRealisateurEtAuteur = token.nextToken();
					strMotCle = token.nextToken();
					parsedDate = LocalDate.parse(strDate.trim(), formatter);
					lstDvd.add(new DVD(strNomDoc, strTitre, parsedDate, "Disponible", intNbDisque,
							strRealisateurEtAuteur, strMotCle, "", 0));
					intCompteurDoc++;
					intComteurDvd++;

				}
			}

			reader.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public int getIntCompteurDoc() {
		return intCompteurDoc;
	}

	public void lireLivres() {

		try (BufferedReader reader = new BufferedReader(new FileReader(new File("Livres.txt")))) {
			while ((line = reader.readLine()) != null) {
				token = new StringTokenizer(line, ",");
				while (token.hasMoreElements()) {
					strNomDoc = token.nextToken();
					strTitre = token.nextToken();
					strDate = token.nextToken();
					strRealisateurEtAuteur = token.nextToken();
					strMotCle = token.nextToken();
					parsedDate = LocalDate.parse(strDate.trim(), formatter);
					lstLivres.add(new Livre(strNomDoc, strTitre, parsedDate, "Disponible", strRealisateurEtAuteur,
							strMotCle, "", 0));
					intCompteurDoc++;
					intCompteurLivres++;

				}

			}

			reader.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public void lirePeriodique() {

		try (BufferedReader reader = new BufferedReader(new FileReader(new File("Periodiques.txt")))) {
			int intNoVol;
			int intNoPer;
			while ((line = reader.readLine()) != null) {
				token = new StringTokenizer(line, ",");
				while (token.hasMoreElements()) {

					strNomDoc = token.nextToken().trim();
					strTitre = token.nextToken().trim();
					strDate = token.nextToken().trim();
					intNoVol = Integer.parseInt(token.nextToken().trim());
					intNoPer = Integer.parseInt(token.nextToken().trim());
					strMotCle = token.nextToken();
					parsedDate = LocalDate.parse(strDate.trim(), formatter);
					lstPeriodiques.add(new Periodique(strNomDoc, strTitre, parsedDate, "Disponible", intNoVol, intNoPer,
							strMotCle, "", 0));

					intCompteurDoc++;
					intComteurPeriodiques++;

				}

			}

			reader.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public static void serializerCatalogue() {

		try {
			FileOutputStream fichier = new FileOutputStream(fichierCatalogue);
			ObjectOutputStream os = new ObjectOutputStream(fichier);
			os.writeObject(instance);
			os.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setLstLivres(ArrayList<Livre> lstLivres) {
		this.lstLivres = lstLivres;
	}

	public void setLstPeriodiques(ArrayList<Periodique> lstPeriodiques) {
		this.lstPeriodiques = lstPeriodiques;
	}

	public void setLstDvd(ArrayList<DVD> lstDvd) {
		this.lstDvd = lstDvd;
	}

	public static Catalogue getInstance() {

		if (instance == null) {
			instance = new Catalogue();
			return instance;

		} else {

			return instance;

		}
	}

	public ArrayList<Document> getLstDocuments() {

		ArrayList<Document> arrayTest = new ArrayList<Document>();
		arrayTest.addAll(lstLivres);
		arrayTest.addAll(lstDvd);
		arrayTest.addAll(lstPeriodiques);

		return arrayTest;
	}

	public ArrayList<Livre> getLstLivres() {
		return lstLivres;
	}

	public ArrayList<Periodique> getLstPeriodiques() {
		return lstPeriodiques;
	}

	public ArrayList<DVD> getLstDvd() {
		return lstDvd;
	}

}
