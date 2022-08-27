package donnees;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class DVD extends Document {

	private static String strCompteur;
	private int nbDisques;
	private String strRealisateur;
	private static final long serialVersionUID = 1L;
	private String strEmprunt;
	private String strNbPret;
	private static int intJourLimite = 7;
	private int pretActif;
	private String strMotClee;

	public DVD(String noDoc, String titre, LocalDate dateParution, String disponible, int nbDisques,
			String strRealisateur, String strMoCle, String strEmprunt, int pretActif) {

		super(noDoc, titre, dateParution, disponible, strMoCle, strEmprunt, pretActif);
		this.nbDisques = nbDisques;
		this.strRealisateur = strRealisateur.trim();
		this.strRealisateur = strRealisateur;
		this.strEmprunt = strEmprunt;
		this.strMotClee = strMoCle;
	}

	public boolean containsMotCle(String a) {

		String strMotClee = getStrMotClee();
		boolean booOk = false;
		strMotClee = strMotClee.toLowerCase();
		if (strMotClee.contains(a) == true) {
			booOk = true;
		}

		return booOk;

	}

	public boolean contains(String a) {

		String strAuteur = getStrRealisateur();
		boolean booOk = false;
		strAuteur = strAuteur.toLowerCase();
		if (strAuteur.contains(a) == true) {
			booOk = true;
		}

		return booOk;

	}

	public static int getIntJourLimite() {
		return intJourLimite;
	}

	public String getStrMotClee() {
		return strMotClee;
	}

	public void setStrMotClee(String strMotClee) {
		this.strMotClee = strMotClee;
	}

	public String getStrEmprunt() {
		return strEmprunt;
	}

	public void setStrEmprunt(String strEmprunt) {
		this.strEmprunt = strEmprunt;
	}

	public static void retrouverPlusPetit() {

		int intVieuxPetit = 1;

		ArrayList<Integer> arrNombres = new ArrayList<Integer>();

		for (Livre l : Catalogue.getInstance().getLstLivres()) {
			arrNombres.add(Integer.parseInt(l.getNoDoc().substring(3)));
		}

		Collections.sort(arrNombres);

		for (int i : arrNombres) {
			if (intVieuxPetit < i)
				break;

			intVieuxPetit++;
		}

		strCompteur = Integer.toString(intVieuxPetit);
	}

	public static String getStrCompteur() {
		return strCompteur;
	}

	@Override
	public String toString() {
		return getNoDoc() + " " + getTitre() + " " + getDateParution() + " " + getDisponible() + " " + getNbDisques()
				+ " " + getStrRealisateur();

	}

	public int getNbDisques() {
		return nbDisques;
	}

	public String getStrRealisateur() {
		return strRealisateur;
	}
}
