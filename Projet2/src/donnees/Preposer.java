package donnees;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Preposer implements Serializable {

	private static final long serialVersionUID = 1L;
	private static String strNumIdentification;
	private String strId;
	private String strNom;
	private String strPrenom;
	private String strMotDePasse;
	private String strAddresse;
	private String strNumTel;
	private static ArrayList<Preposer> arrPreposer = new ArrayList<Preposer>();
	
	public Preposer(String strNom, String strPrenom, String strMotDePasse, String strAdresse,String strNumTel) {
	
		this.strId = "P000" + strNumIdentification;
		this.strNom = strNom;
		this.strPrenom = strPrenom;
		this.strMotDePasse = strMotDePasse;
		this.strAddresse = strAdresse;
		this.strNumTel = strNumTel;
	}
	
	
	public static void retrouverPlusPetit() {

		int intVieuxPetit = 1;

		ArrayList<Integer> arrNombres = new ArrayList<Integer>();

		for(Preposer p : arrPreposer) {
            
			arrNombres.add(Integer.parseInt(p.getStrId().substring(1)));
		}

		if(!arrNombres.isEmpty()) {
			Collections.sort(arrNombres);

			for(int i : arrNombres) {

				if(intVieuxPetit < i)
					break;

				intVieuxPetit++;
			}
		}

		strNumIdentification = Integer.toString(intVieuxPetit);
	}
	
	public String getStrId() {
		return strId;
	}


	public void setStrId(String strId) {
		this.strId = strId;
	}


	public static void setStrNumIdentification(String strNumIdentification) {
		Preposer.strNumIdentification = strNumIdentification;
	}


	public String getStrNumIdentification() {
		return strNumIdentification;
	}


	public String getStrNom() {
		return strNom;
	}


	public String getStrPrenom() {
		return strPrenom;
	}


	public String getStrMotDePasse() {
		return strMotDePasse;
	}


	public String getStrAddresse() {
		return strAddresse;
	}
     

	public String getStrNumTel() {
		return strNumTel;
	}


	public static ArrayList<Preposer> getArrPreposer() {
		return arrPreposer;
	}

	public static void serializerPreposes() {

		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("preposes.ser"));

			for(Preposer p : arrPreposer) {
				os.writeObject(p);
			}

			os.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public static void deserializerPreposes() {

		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("preposes.ser"));
			Preposer prep;
			while((prep = (Preposer)is.readObject()) != null){
				arrPreposer.add(prep);
			}

			is.close();

		} catch (ClassNotFoundException e) {

		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
	}
	

	public void setStrNom(String strNom) {
		this.strNom = strNom;
	}


	public void setStrPrenom(String strPrenom) {
		this.strPrenom = strPrenom;
	}


	public void setStrMotDePasse(String strMotDePasse) {
		this.strMotDePasse = strMotDePasse;
	}


	public void setStrAddresse(String strAddresse) {
		this.strAddresse = strAddresse;
	}


	public void setStrNumTel(String strNumTel) {
		this.strNumTel = strNumTel;
	}
	
	
	
}
