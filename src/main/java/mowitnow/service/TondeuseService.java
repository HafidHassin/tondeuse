package mowitnow.service;

import mowitnow.dto.Pelouse;
import mowitnow.dto.Tondeuse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Classe permettant de lire le fichier et exécuter les instructions des tondeuses
 */
public class TondeuseService {

    private final static Logger logger = LogManager.getLogger(TondeuseService.class);

    /**
     * Méthode qui permet de charger/lire la propriété du chemin de fichier
     * et d'appeler la méthode de lecture du fichier
     */
    public static boolean positionnerTondeuse() {
        var properties = new Properties();
        try (InputStream input = TondeuseService.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                logger.error("Le fichier application.properties n'a pas été trouvé");
                return false;
            }
            // Charger la propriété depuis le fichier
            properties.load(input);
            var cheminFichier = properties.getProperty("chemin.fichier");
            lireFichier(cheminFichier);
            return true;
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        return false;
    }

    /**
     * Méthode qui permet de lire le fichier et exécuter les instructions
     * @param cheminFichier chemin du fichier
     */
    private static void lireFichier(String cheminFichier){
        try (var fileInputStream = new FileInputStream(cheminFichier);
             var inputStreamReader = new InputStreamReader(fileInputStream);
             var bufferedReader = new BufferedReader(inputStreamReader)) {

            executerInstructions(bufferedReader);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void executerInstructions(BufferedReader bufferedReader) throws IOException {
        // Lire la première ligne qui correspond aux coordonnées du coin supérieur droit de la pelouse
        var positionSupPelouse = bufferedReader.readLine();
        Pelouse pelouse = new Pelouse();
        if (positionSupPelouse != null) {
            var positions = positionSupPelouse.split(" ");
            if(positions.length == 2){
                pelouse.setCoinSuperieurDroitX(Integer.parseInt(positions[0]));
                pelouse.setCoinSuperieurDroitY(Integer.parseInt(positions[1]));
            }
        }

        String positionInitiale, instructions;
        List<Tondeuse> listeTondeuse = new ArrayList<>();

        // Lire le reste des lignes deux par deux
        while ((positionInitiale = bufferedReader.readLine()) != null) {
            var tondeuse = new Tondeuse();
            var positionInitialeArray = positionInitiale.split(" ");

            if(positionInitialeArray.length == 3){
                tondeuse.setPositionX(Integer.parseInt(positionInitialeArray[0]));
                tondeuse.setPositionY(Integer.parseInt(positionInitialeArray[1]));
                tondeuse.setOrientation(positionInitialeArray[2].charAt(0));
                tondeuse.setPelouse(pelouse);
            }

            // Lecture de la ligne des instructions
            instructions = bufferedReader.readLine();
            if (instructions != null) {
                for (int i = 0; i < instructions.toCharArray().length; i++){
                    changerOrientation(tondeuse, instructions.charAt(i));
                }
            }
            listeTondeuse.add(tondeuse);
        }

        // Affichage du résultat
        listeTondeuse.forEach(System.out::println);
    }

    /**
     * Méthode qui permet de gérer les instructions 'D', 'G' et 'A'
     * @param tondeuse tondeuse
     * @param instruction instruction
     */
    public static void changerOrientation(Tondeuse tondeuse, char instruction){
        if(tondeuse != null){
            switch (instruction){
                case 'D':
                    pivoterADroite(tondeuse);
                    break;
                case 'G':
                    pivoterAGauche(tondeuse);
                    break;
                case 'A':
                    avancerTondeuse(tondeuse);
                    break;
            }
        }
    }

    /**
     * Méthode qui permet de gérer l'instruction 'D' pour pivoter la tondeuse à droite de 90 degré
     * @param tondeuse tondeuse
     */
    private static void pivoterADroite(Tondeuse tondeuse){
        switch (tondeuse.getOrientation()){
            case 'N':
                tondeuse.setOrientation('E');
                break;
            case 'E':
                tondeuse.setOrientation('S');
                break;
            case 'W':
                tondeuse.setOrientation('N');
                break;
            case 'S':
                tondeuse.setOrientation('W');
                break;
        }
    }

    /**
     * Méthode qui permet de gérer l'instruction 'G' pour pivoter la tondeuse à gauche de 90 degré
     * @param tondeuse tondeuse
     */
    private static void pivoterAGauche(Tondeuse tondeuse){
        switch (tondeuse.getOrientation()){
            case 'N':
                tondeuse.setOrientation('W');
                break;
            case 'E':
                tondeuse.setOrientation('N');
                break;
            case 'W':
                tondeuse.setOrientation('S');
                break;
            case 'S':
                tondeuse.setOrientation('E');
                break;
        }
    }

    /**
     * Méthode qui permet de gérer l'instruction 'A' pour avancer d'une case
     * @param tondeuse tondeuse
     */
    private static void avancerTondeuse(Tondeuse tondeuse){
        switch (tondeuse.getOrientation()){
            case 'N':
                if(tondeuse.getPositionY() < tondeuse.getPelouse().getCoinSuperieurDroitY()){
                    tondeuse.setPositionY(tondeuse.getPositionY() + 1);
                }
                break;
            case 'E':
                if(tondeuse.getPositionX() < tondeuse.getPelouse().getCoinSuperieurDroitX()){
                    tondeuse.setPositionX(tondeuse.getPositionX() + 1);
                }
                break;
            case 'W':
                if((tondeuse.getPositionX() > 0)){
                    tondeuse.setPositionX(tondeuse.getPositionX() - 1);
                }
                break;
            case 'S':
                if((tondeuse.getPositionY() > 0)){
                    tondeuse.setPositionY(tondeuse.getPositionY() - 1);
                }
                break;
        }
    }
}