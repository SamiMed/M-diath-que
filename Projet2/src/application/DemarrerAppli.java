package application;

import java.io.File;


import donnees.Adherent;
import donnees.Catalogue;
import donnees.DeserialisationCatalogue;
import donnees.Preposer;
import donnees.SerialisationCatalogue;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import vueEtControleur.Connection;
import vueEtControleur.TableauBordPreposer;


public class DemarrerAppli extends Application  {

	/*
	 
	  Membres de l'équipe :  -Sami Medelci
	  						 -Isabelle Machado
	  
	  
	Information de connection
	  
	  
	  Compte Administrateur :
	  
	  Nom d'utilisateur : Admin
	  Mot de passe : Password1 
	
	
	
	 */
	

	private static DemarrerAppli instance = null;

	public Stage stagee = new Stage();
	
	public static DemarrerAppli getInstance() {
		
		if (instance == null) {
			instance = new DemarrerAppli();
			return instance; 
		}else {	
		return instance;   
		}
		
	}

	public void setStagee(Stage stagee) {
		this.stagee = stagee;
	}

	public Stage getStagee() {
		return stagee;
	}

	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		stagee = primaryStage;
	
		TableauBordPreposer tbl = TableauBordPreposer.getInstance();
		primaryStage.getIcons().add(new Image("icon-collection.png"));
		DeserialisationCatalogue desrialiser = new DeserialisationCatalogue();
		
		File ad = new File("adherents.ser");
		File pr = new File("preposes.ser");
		
		
		if (ad.exists()) {
			Adherent.deserializerAdherent(tbl.getArrTousAdherents());
		}
		
		if (pr.exists()) {
			Preposer.deserializerPreposes();
		}
		
		Connection connect = Connection.getInstance();
		Catalogue catalog = Catalogue.getInstance();
	
		try {
			catalog.lire();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		
		stagee =  connect.creerInterfaceConnexion(stagee);
		stagee.show();
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
		    @Override
		    public void run()
		    {
				Catalogue cat = Catalogue.getInstance();
		    	SerialisationCatalogue ser = new SerialisationCatalogue();
		    	Adherent.serializerAdherents(tbl.getArrTousAdherents());
				ser.serialise(cat.getLstDocuments(), cat.getLstLivres(), cat.getLstDvd(), cat.getLstPeriodiques());
				Preposer.serializerPreposes();
	
		    }	
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}	
}