import java.awt.event.*;
import java.util.Random;

import javax.swing.*;


public class ShiFuMi {
    static  int scoreJoueur;
    static  int scoreIA;
    static int coup;
    static int nbManche;
    public ShiFuMi () {
        this.coup=-1;
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

    void incremente(boolean estJoueurGagnant) {
        if (estJoueurGagnant) {
            scoreJoueur+=1;
        } else {
            this.scoreIA+=1;
        }
    }

    void scoreDuMoment(int joueur1, int ia) { //normalement ça marche
        if (joueur1==ia) {
            System.out.println("cas ou les deux coups se valent");
        } else {
            if (joueur1==0) {
                if (ia==1) {
                    this.incremente(false);
                } else {
                    this.incremente(true);
                }
            } else {
                if (joueur1==1) {
                    if (ia!=0) {
                        this.incremente(false);
                    } else {
                        this.incremente(true);
                    }
                } else {
                    if (joueur1==2) {
                        if (ia!=1) {
                            this.incremente((false));
                        } else {
                            this.incremente(true);
                        }
                    } else {
                        if (ia==0) {
                            this.incremente(false);
                        } else {
                            this.incremente(true);
                        }
                    }
                }
            }
        }
    }

    public static void main(String [] args) {
        var frame=new JFrame("ShiFuMi");
        frame.setSize(1000,1000);

        //affichage de texte
        var texteScore=new JLabel[4];
        for (int i=0; i<texteScore.length; i++) {
            texteScore[i]=new JLabel("");
            texteScore[i].setBounds(400, 400+200*i, 500, 200);
            frame.add(texteScore[i]);
        }

        //affichage bouton en plus
        var re=new JButton[2];
        re[0]=new JButton("REINITIALISER !");
        re[1]=new JButton("RECOMMENCER !");
        for (int i=0; i<re.length; i++) {
            re[i].setBounds(50,300+200*i,100,100);
            frame.add(re[i]);
            re[i].addActionListener (e-> {
                scoreIA=0;
                scoreJoueur=0;
                nbManche=0;
                for (int j=0; j<texteScore.length; j++) {
                    texteScore[j].setText("");
                }
            });
        }
        re[1].setVisible(false);



        var  button=new JButton[4];
        ShiFuMi shi=new ShiFuMi();
        for (int i=0; i<button.length; i++) {
            //On initialise nos boutons
            button[i]=new JButton(affichage(i));
            button[i].setBounds(50+100*i,100,95,30);
            frame.add(button[i]);

            //on les affiche en fonction de la condition


            final int i1=i;
            button[i].addActionListener(e -> {
                coup=i1;
                int coupIA=choisirCoupHasard();
                shi.scoreDuMoment(coup, coupIA);
                System.out.println("score joueur="+scoreJoueur+"||score IA"+scoreIA);
                texteScore[0].setText("Le score du joueur est de "+scoreJoueur);
                texteScore[1].setText("Le score de l'ordinateur est de "+scoreIA);
                texteScore[2].setText("L'ia a joué "+affichage(coupIA));
                nbManche+=1;

                texteScore[3].setText("On a fait "+nbManche+" manches");

                if (nbManche>=5) {
                    //on ne fait pas plus que 5 manches, dans ce cas, on cache les boutons, affiche le gagnat, propose un nouveau match
                    for (int j=0; j<button.length; j++) {
                        button[j].setVisible(false);
                        
                    }
                    re[1].setVisible(true);
                } 
            });




        }
        frame.setLayout(null);


        frame.setVisible(true);



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}