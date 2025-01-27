import java.awt.event.*;
import java.util.Random;
//problème reinitialiser/recommencer
import javax.swing.*;


public class ShiFuMi {
    static  int scoreJoueur1;
    static int scoreJoueur2;
    static  int scoreIA;
    static int coupJoueur1;
    static int coupJoueur2;
    static int nbManche;
    static boolean tourJoueur1 = true;

    boolean aJouerJoueur2;
    public ShiFuMi () {
        this.coupJoueur1=-1;
        this.coupJoueur2=-1;
        this.scoreIA=0;
        this.scoreIA=0;
        this.nbManche=0;
    }


    static String affichage(int coup) {
        if (coup==0) {
            return ("feuille");
        } else {
            if (coup==1)  {
                return ("ciseaux");
            } else {
                if (coup==2) {
                   return ("pierre");
                } else {
                    if (coup==3) {
                        return ("puit");
                    } else {
                        return ("erreur sur le coup");
                    }
                }
            }
        }
    }


    static int choisirCoupHasard() {
        Random random=new Random();
        int var=random.nextInt(0, 5);
        return var;
    }

    void incremente(boolean estJoueurGagnant, boolean estDeuxJoueurs) {
        if (estJoueurGagnant) {
            scoreJoueur1+=1;
        } else {
            if (estDeuxJoueurs) {
                scoreJoueur2+=1;
            } else {
                scoreIA+=1;
            }
            
        }
    }

    void scoreDuMoment(int joueur1, int ia, boolean estDeuxJoueurs) { //normalement ça marche
        if (joueur1==ia) {
            System.out.println("cas ou les deux coups se valent");
        } else {
            if (joueur1==0) {
                if (ia==1) {
                    this.incremente(false, estDeuxJoueurs);
                } else {
                    this.incremente(true, estDeuxJoueurs);
                }
            } else {
                if (joueur1==1) {
                    if (ia!=0) {
                        this.incremente(false, estDeuxJoueurs);
                    } else {
                        this.incremente(true, estDeuxJoueurs);
                    }
                } else {
                    if (joueur1==2) {
                        if (ia!=1) {
                            this.incremente(false, estDeuxJoueurs);
                        } else {
                            this.incremente(true, estDeuxJoueurs);
                        }
                    } else {
                        if (ia==0) {
                            this.incremente(false, estDeuxJoueurs);
                        } else {
                            this.incremente(true, estDeuxJoueurs);
                        }
                    }
                }
            }
        }
    }

    public static void main(String [] args) { //il faudrait recréer un autre truc pour pouvoir continuer à joueur après mais bon
        ShiFuMi shi=new ShiFuMi();
        boolean estDeuxJoueurs=true;


        //Fenêtre
        JFrame [] frame;
        if (estDeuxJoueurs) {
            frame=new JFrame[2];
        } else {
            frame=new JFrame[1];
        }
        for (int i = 0; i < frame.length; i++) {
            frame[i] = new JFrame("ShiFuMi - Joueur " + (i + 1));
            frame[i].setSize(600, 400);  // Taille réduite pour éviter qu'elles ne soient trop grandes
            frame[i].setLayout(null);
            frame[i].setVisible(true);
            frame[i].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        


        //Affichage txt
        JLabel [][] texteScore;
        if (estDeuxJoueurs) {
           texteScore=new JLabel[2][4];
        } else {
            texteScore=new JLabel[1][4];
        }
        
        for (int i=0; i<texteScore.length; i++) {
            for (int j=0; j<texteScore[i].length; j++) {
                texteScore[i][j]=new JLabel("");
                texteScore[i][j].setBounds(400, 400+50*j, 500, 50);
                frame[i].add(texteScore[i][j]);
            }

        }

        //affichage bouton en plus, A REVOIR, mon ethique en travail doit absolument revenir sur les rails !
        var re=new JButton[2];
        re[0]=new JButton("REINITIALISER !");
        re[1]=new JButton("RECOMMENCER !");
        for (int i=0; i<re.length; i++) {
            re[i].setBounds(50,300+100*i,100,100);
            frame[i].add(re[i]);
            re[i].addActionListener (e-> {
                scoreIA=0;
                scoreJoueur1=0;
                scoreJoueur2=0;
                nbManche=0;
                for (int j=0; j<texteScore.length; j++) {
                    for (int u=0; u<texteScore[j].length; u++) {
                        texteScore[j][u].setText("");
                    }
                }
    
            });
        }
        re[1].setVisible(false);



        JButton button [][];
        if (estDeuxJoueurs) {
            button=new JButton[2][4]; 
        } else {
            button=new JButton[1][4];
        }
        for (int i=0; i<button.length; i++) {
            for (int j=0; j<button[i].length; j++) {
                button[i][j]=new JButton(affichage(j));
                button[i][j].setBounds(50+100*j,100,95,30);
                frame[i].add(button[i][j]);

                final int i1=j;
                button[i][j].addActionListener(e -> {
                    if (tourJoueur1) {
                        coupJoueur1 = i1;
                        tourJoueur1 = false;  // On passe au joueur 2
                    } else {
                        coupJoueur2 = i1;
                        tourJoueur1 = true;  // Retour au joueur 1
                        shi.scoreDuMoment(coupJoueur1, coupJoueur2, estDeuxJoueurs);  // Évaluation du round
                        for (int y=0; y<texteScore.length; y++) {
                            actualiserAffichage(texteScore[y], coupJoueur2, estDeuxJoueurs);

                        }
                    }
                });
            }

        }


        if (estDeuxJoueurs) {
            for (int i=0; i<button.length; i++) {
                BoutonJeuChoix(button[i], shi, texteScore[i], re, estDeuxJoueurs);

            }
        } 
       

    }



    static void actualiserAffichage(JLabel [] texteScore, int coupIA, boolean estDeuxJoueurs) {
        System.out.println("score joueur="+scoreJoueur1+"||score IA"+scoreIA);
        texteScore[0].setText("Le score du joueur est de "+scoreJoueur1);
        if (estDeuxJoueurs) {
            texteScore[1].setText("Le score du joueur 2 est de "+scoreIA);
        } else {
            texteScore[1].setText("Le score de l'ordinateur est de "+scoreIA);
        }
        texteScore[2].setText("L'adversaire a joué "+affichage(coupIA));

        nbManche+=1;

        texteScore[3].setText("On a fait "+nbManche+" manches");
    }

    static void BoutonJeuChoix(JButton [] button, ShiFuMi shi, JLabel [] texteScore, JButton [] re, boolean estDeuxJoueurs)  {
        for (int i=0; i<button.length; i++) {
            //On initialise nos boutons
        
            button[i]=new JButton(affichage(i));
            

            //on les affiche en fonction de la condition

            
        }
    }





}