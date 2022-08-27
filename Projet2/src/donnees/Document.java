package donnees;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Document implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String  noDoc;
	private String  titre;
	private LocalDate  dateParution;
	private String disponible;
	private int pretActif;
	private String strEmprunt;
	private Pret pretCourant;
	private String strMotClee;
	
	public Document(String noDoc, String titre, LocalDate dateParution, String disponible, String strMotCle, String strEmprunt, int pretActif) {
		this.noDoc = noDoc;
		this.titre = titre;
		this.dateParution = dateParution;
		this.disponible = disponible;
		this.strEmprunt = strEmprunt;
		this.pretActif = pretActif;
		this.strMotClee = strMotCle;
	}

	
	public Pret getPretCourant() {
		return pretCourant;
	}
	
	public void setPretCourant(Pret pretCourant) {
		this.pretCourant = pretCourant;
	}


	public String getStrEmprunt() {
		return strEmprunt;
	}


	public void setStrEmprunt(String strEmprunt) {
		this.strEmprunt = strEmprunt;
	}

	public int getPretActif() {
		return pretActif;
	}

	public void setRetourPret() {
		
		if(pretActif >= 1) {
			pretActif--;
		}else {
			pretActif = 0;
		}		
		}
	

	public void setNouvPret() {
		pretActif ++;
	}

	public void setDateParution(LocalDate dateParution) {
		this.dateParution = dateParution;
	}
	public String getNoDoc() {
		return noDoc;
	}

	public String getTitre() {
		return titre;
	}

	public LocalDate getDateParution() {
		return dateParution;
	}

	public String getDisponible() {
		return disponible;
	}

	public void setDisponible(String disponible) {
		this.disponible = disponible;
	}
	
	public String getStrMotClee() {
		return strMotClee;
	}

	public void setStrMotClee(String strMotClee) {
		this.strMotClee = strMotClee;
	}

	public boolean containsMotCle(String a) {
		
		String strMotClee = getStrMotClee();
		boolean booOk = false;
		strMotClee = strMotClee.trim().toLowerCase();
			if(strMotClee.contains(a) == true) {
				booOk = true;
			}

		return booOk;
			
		}
	
	public boolean contains(String a) {
		
		String strAuteur = getTitre();
		boolean booOk = false;
		strAuteur = strAuteur.toLowerCase();
			if(strAuteur.contains(a) == true) {
				booOk = true;
			}

		return booOk;
			
		}
}