package donnees;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Periodique extends Document {

	private static final long serialVersionUID = 1L;
	private static String strCompteur;
	private int noVolume;
	private int noPeriodique;
	private String strEmprunt;
	private static int intJourLimite = 3;
	private String strMotClee;

	public Periodique(String noDoc, String titre, LocalDate dateParution, String disponible, int noVolume,
			int noPeriodique, String strMocle, String strEmprunt, int pretActif) {
		super(noDoc, titre, dateParution, disponible, strMocle, strEmprunt, pretActif);
		this.noVolume = noVolume;
		this.noPeriodique = noPeriodique;
		this.strEmprunt = strEmprunt;
		this.strMotClee = strMocle;
	}

	public boolean contains(String a) {
		return false;
	}

	public boolean containsMotCle(String a) {

		String strMotClee = getStrMotClee();
		boolean booOk = false;
		strMotClee = strMotClee.toLowerCase().trim();
		if (strMotClee.trim().contains(a) == true) {
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

	public String getStrEmprunt() {
		return strEmprunt;
	}

	public void setStrEmprunt(String strEmprunt) {
		this.strEmprunt = strEmprunt;
	}

	@Override
	public String toString() {
		return getNoDoc() + " " + getTitre() + " " + getDateParution() + " " + getDisponible() + " " + noVolume + " "
				+ noPeriodique;
	}

	public static void retrouverPlusPetit() {

		int intVieuxPetit = 1;

		ArrayList<Integer> arrNombres = new ArrayList<Integer>();

		for (Periodique l : Catalogue.getInstance().getLstPeriodiques()) {

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

	public int getNoVolume() {
		return noVolume;
	}

	public int getNoPeriodique() {
		return noPeriodique;
	}

	public static int getIntJourLimite() {
		return intJourLimite;
	}

	public static String getStrCompteur() {
		return strCompteur;
	}

}
