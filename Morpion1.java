import java.io.IOException;
import java.util.Random;

public class Morpion1 {
    
    public static int demandeNombre(Channel<String> canal, String str1, int joueur) throws  ClassNotFoundException, IOException{ 
        canal.send("Quelle est la valeur de " + str1 + " choisie par le joueur " + joueur + " ? (0,1,2)");
        
        int x = 0;
        boolean valide = false;
    
        while (!valide) {
        
            String str = canal.getNext(); // On redemande une nouvelle entrée à chaque itération

            while (str.equals("Erreur : Veuillez entrer un nombre entier valide")||str.equals("")) {
                str = canal.getNext();
            }

            try {
                x = Integer.parseInt(str);
                if (x>=0&&x<=2) {
                    valide = true; // Sortie de la boucle si conversion réussie
                }

            } catch (NumberFormatException e) {
                //canal.send("Erreur : Veuillez entrer un nombre entier valide.");
            }
        }
    
        return x;
    }


    


 public static void main(String [] args) throws  ClassNotFoundException, IOException { //faire ensuite en sorte de pouvoir aussi jouer depuis le terminal
    var cellSize = 50;
    var m = new Model();
    Random random=new Random();
    int var=random.nextInt(100);
    String str ="mathieu"+var ;
    System.out.println("Le nom des channels sera pour le joueur1 :"+str+"j1");
    System.out.println("Le nom des channels sera pour le joueur2 : "+str+"j2");
    Channel<String> [] tab=new Channel[2];
    tab[0] = new Channel<String>(str+"j1");
    tab[1] = new Channel<String>(str+"j2");
    int x;
    int y;
   // var channelJoueur2 = new Channel<String>(str);
    boolean valide=false;
    while (!m.isFinished()) {
        valide=false;
        x=-1;
        y=-1;
        while (!valide) {
            if (m.getPlayer()==1) {
                x=demandeNombre(tab[m.getPlayer()-1], "x", m.getPlayer());
                //on regarde uniquement s'il s'agit bien d'u
                y=demandeNombre(tab[m.getPlayer()-1], "y", m.getPlayer());
                } 
            if (m.play(x, y)) {
                valide=true;
            }

        }
        /*ici on fait la demande */
        m.affichage();
    }

    for (int i=0; i<tab.length; i++) {
        tab[i].send("Fin de jeu, bien joué à vous !");
    }


}
}