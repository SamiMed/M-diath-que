package donnees;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import vueEtControleur.Connection;
import vueEtControleur.TableauBordPreposer;

public class Adherent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String strNumAdh;
	private String strNumTel;
	private String strPrenom;
	private String strNom;
	private String strAddresse;
	private String strAmende;

	private int intNbPrets;
	private static TableauBordPreposer tbl = TableauBordPreposer.getInstance();
	private static Connection conect = Connection.getInstance();

	private int intNbEmprunt;
	private int intNbLivre;
	private int intNbPerio;
	private int intNbDvd;
	private double dblAmende;

	private ArrayList<Pret> arrPrets = new ArrayList<Pret>();

	public Adherent(String strNumIncription, String strPrenom, String strNom, String strNumTel, String strAdresse,
			String strAmende, int intNbLivre, int intNbPerio, int intNbDvd) {

		this.strNumAdh = "A" + strNumIncription;
		this.strPrenom = strPrenom;
		this.strNom = strNom;
		this.strNumTel = strNumTel;
		this.strAddresse = strAdresse;
		this.strAmende = strAmende;
		this.intNbDvd = intNbDvd;
		this.intNbPerio = intNbPerio;
		this.intNbLivre = intNbLivre;

	}

	public int getIntNbLivre() {
		return intNbLivre;
	}

	public int getIntNbPerio() {
		return intNbPerio;
	}

	public int getIntNbDvd() {
		return intNbDvd;
	}

	public boolean testerNombrePret(Adherent adherent, Document doc) {
		boolean booRetour = false;

		if (doc instanceof Livre) {

			if (intNbLivre < 3) {
				booRetour = true;
			}
		} else if (doc instanceof DVD) {

			if (intNbDvd < 2) {
				booRetour = true;
			}
		} else {

			if (intNbPerio < 1) {
				booRetour = true;
			}
		}

		return booRetour;
	}

	@SuppressWarnings("unchecked")
	public static void deserializerAdherent(ArrayList<Adherent> arrTousAdherents) {

		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("adherents.ser"));
			arrTousAdherents = (ArrayList<Adherent>) is.readObject();
			tbl.setArrTousAdherents(arrTousAdherents);
			conect.setAdherents(arrTousAdherents);
			is.close();
		

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void serializerAdherents(ArrayList<Adherent> arrTousAdherents) {

		try {
			
			FileOutputStream fichier1 = new FileOutputStream("adherents.ser");

			ObjectOutputStream os = new ObjectOutputStream(fichier1);
			os.writeObject(arrTousAdherents);

			os.close();
			fichier1.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ajouterFraisAdherent(String strAjouterAmende) {

		if (strAjouterAmende != "0.00$") {

			double dblAmendeAjouter = Double.parseDouble(strAjouterAmende.substring(0, strAjouterAmende.indexOf('$')));
			double dblAmendeOriginal = Double.parseDouble(strAmende.substring(0, strAmende.indexOf('$')));
			strAmende = String.format("%.02f", dblAmendeAjouter + dblAmendeOriginal);
			strAmende += "$";
		}

	}

	public int getIntNbPrets() {
		intNbPrets = arrPrets.size();
		return intNbPrets;
	}

	public ArrayList<Pret> getArrPrets() {
		return arrPrets;
	}

	public void setArrPrets(Pret pret) {
		arrPrets.add(pret);
	}

	public void setStrNumAdh(String strNumAdh) {
		this.strNumAdh = strNumAdh;
	}

	public void setStrNumTel(String strNumTel) {
		this.strNumTel = strNumTel;
	}

	public void setStrPrenom(String strPrenom) {
		this.strPrenom = strPrenom;
	}

	public void setStrNom(String strNom) {
		this.strNom = strNom;
	}

	public void setStrAddresse(String strAddresse) {
		this.strAddresse = strAddresse;
	}

	public void setStrAmende(String strAmende) {
		this.strAmende = strAmende;
	}

	public void setIntNbEmprunt(int intNbEmprunt) {
		this.intNbEmprunt = intNbEmprunt;
	}

	public void setIntNbLivreRetour() {

		if (intNbLivre >= 1) {
			intNbLivre--;
		} else {
			intNbLivre = 0;
		}
	}

	public void setIntNbLivreEmprunt() {
		intNbLivre++;
	}

	public void setIntNbDvdRetour() {

		if (intNbLivre >= 1) {
			intNbLivre--;
		} else {
			intNbLivre = 0;
		}
	}

	public void setIntNbDvdEmprunt() {
		intNbLivre++;
	}

	public void setIntNbPerioRetour() {

		if (intNbLivre >= 1) {
			intNbLivre--;
		} else {
			intNbLivre = 0;
		}
	}

	public void setIntNbPerioEmprunt() {
		intNbLivre++;
	}

	public void setDblAmende(double dblAmende) {
		this.dblAmende = dblAmende;
	}

	public String getStrNumTel() {
		return strNumTel;
	}

	public String getStrPrenom() {
		return strPrenom;
	}

	public String getStrNom() {
		return strNom;
	}

	public String getStrAddresse() {
		return strAddresse;
	}

	public String getStrAmende() {
		return strAmende;
	}

	public int getIntNbEmprunt() {
		intNbEmprunt = intNbLivre + intNbDvd + intNbPerio;
		return intNbEmprunt;
	}

	public double getDblAmende() {
		return dblAmende;
	}

	public String getStrNumAdh() {
		return strNumAdh;
	}

	public String toString() {

		return strNumAdh + strNom + strPrenom + strNumTel + strAddresse + strAmende;

	}
}
