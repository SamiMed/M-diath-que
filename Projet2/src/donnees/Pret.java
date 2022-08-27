package donnees;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Pret implements Serializable {

	private static final long serialVersionUID = 1L;
	private int intNumPret;
	private String strAmende;
	private LocalDate dateEmprunt;
	private LocalDate dateRetourPrevue;
	private LocalDate dateDeRetour;
	private Adherent adherent;
	private Document document;
	private String strNomAdherent;
	private int intNbJourRetards;

	public Pret(Document document, Adherent adherent) {

		this.dateEmprunt = LocalDate.now();
		this.adherent = adherent;
		this.document = document;
		this.intNumPret = setIntNumPret(adherent.getIntNbPrets() + 1);
		document.setDisponible("Emprunté");
		if (document instanceof DVD) {

			this.dateRetourPrevue = LocalDate.now().plusDays(DVD.getIntJourLimite());

		} else if (document instanceof Livre) {

			this.dateRetourPrevue = LocalDate.now().plusDays(Livre.getIntJourLimite());

		} else {

			this.dateRetourPrevue = LocalDate.now().plusDays(Periodique.getIntJourLimite());

		}
		this.strAmende = "0.00$";
	}

	public LocalDate getDateRetourPrevue() {
		return dateRetourPrevue;
	}

	public LocalDate getDateDeRetour() {
		return dateDeRetour;
	}

	public String getStrNomAdherent() {
		return strNomAdherent;
	}

	public int getIntNbJourRetards() {
		return intNbJourRetards;
	}

	public int getIntNumPret() {
		return intNumPret;
	}

	public void setStrAmende(String strAmende) {
		this.strAmende = strAmende;
	}

	// Méthode toString() redéfinit de la class Objet.
	public String toString() {

		return "Le Document " + document.getTitre() + " a été emprunté par " + adherent.getStrPrenom() + " "
				+ adherent.getStrNom() + " le " + dateEmprunt + " et retourné le " + dateDeRetour + " avec "
				+ intNbJourRetards + " jour(s) de retard.";
	}

	// Méthode qui retourne la date de retour.
	public LocalDate getDateRetour() {
		return dateDeRetour;
	}

	// Méthode qui retourne le numéro de prêt.
	public int getStrNumPret() {
		return intNumPret;
	}

	public int setIntNumPret(int intNumPret) {
		this.intNumPret = intNumPret;
		return intNumPret;
	}

	public String getStrNom() {

		return adherent.getStrNom();
	}

	public void CalculerAmendeAdherent() {

		if (dateDeRetour.isAfter(dateRetourPrevue)) {

			intNbJourRetards = (int) dateRetourPrevue.until(dateDeRetour, ChronoUnit.DAYS);
			strAmende = String.format("%.02f", intNbJourRetards * 0.50) + "$";
		}

		else {
			strAmende = "0.00$";
			intNbJourRetards = 0;
		}

	}

	// Méthode qui retourne la date de l'emprunt.
	public LocalDate getDateEmprunt() {
		return dateEmprunt;
	}

	// Méthode qui retourne la date de retour prévue.
	public LocalDate getDateRetourPrevu() {
		return dateDeRetour;
	}

	// Méthode qui retourne l'adhérent qui fait l'emprunt.
	public Adherent getAdherent() {
		return adherent;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	// Méthode qui retourne le document emprunté.
	public Document getDocument() {
		return document;
	}

	// Méthode qui retourne l'amende du prêt.
	public String getStrAmende() {
		return strAmende;
	}

	// Méthode qui modifie la date de retour du prêt.
	public void setDateRetour(LocalDate dateRetour) {
		this.dateDeRetour = dateRetour;
	}

}
