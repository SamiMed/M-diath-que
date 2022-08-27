package donnees;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Livre extends Document {

	private String auteur;
	private static final long serialVersionUID = 1L;
	private String motsCles;
	private static String strCompteur;
	private static int intJourLimite = 14;
	private String strEmprunt;
	private String strMotClee;

	public Livre(String noDoc, String titre, LocalDate dateParution, String disponible, String auteur, String strMotCle,
			String strEmprunt, int pretActif) {

		super(noDoc, titre, dateParution, disponible, strMotCle, strEmprunt, pretActif);
		this.auteur = auteur;
		this.motsCles = strMotCle;
		this.strEmprunt = strEmprunt;
		this.strMotClee = strMotCle;
	}

	public boolean contains(String a) {

		String strAuteur = getAuteur();
		boolean booOk = false;
		strAuteur = strAuteur.toLowerCase();
		if (strAuteur.contains(a) == true) {
			booOk = true;
		}

		return booOk;

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

	public String getStrMotClee() {
		return strMotClee;
	}

	public void setStrMotClee(String strMotClee) {
		this.strMotClee = strMotClee;
	}

	public String getMotClee() {
		return motsCles;
	}

	public String getAuteur() {
		return auteur;
	}

	public String getStrEmprunt() {
		return strEmprunt;
	}

	public void setStrEmprunt(String strEmprunt) {
		this.strEmprunt = strEmprunt;
	}

	public static int getIntJourLimite() {
		return intJourLimite;
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

}
