package serveurPackage;

import java.io.*;
import java.net.*;
import objetPackage.Operation;

public class Serveur {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Le serveur attend la connexion d'un client...");

            Socket socket = serverSocket.accept();
            System.out.println("Un client est connecté.");

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // Lecture de l’objet Operation
            Operation op = (Operation) in.readObject();
            System.out.println("Objet Operation reçu : " + op);

            String op1Str = op.getOperande1();
            String operateur = op.getOperateur();
            String op2Str = op.getOperande2();

            String reponse;

            // Vérification des opérandes
            if (!estNumerique(op1Str) || !estNumerique(op2Str)) {
                reponse = "Erreur : les opérandes doivent être des nombres !";
                System.out.println("Erreur : opérande invalide.");
            }
            // Vérification de l’opérateur
            else if (!(operateur.equals("+") || operateur.equals("-") ||
                    operateur.equals("*") || operateur.equals("/"))) {
                reponse = "Erreur : opérateur invalide !";
                System.out.println("Erreur : opérateur invalide reçu !");
            }
            else {
                float op1 = Float.parseFloat(op1Str);
                float op2 = Float.parseFloat(op2Str);
                float resultat = 0;
                String err = "";

                switch (operateur) {
                    case "+":
                        resultat = op1 + op2;
                        break;
                    case "-":
                        resultat = op1 - op2;
                        break;
                    case "*":
                        resultat = op1 * op2;
                        break;
                    case "/":
                        if (op2 == 0) {
                            err = "Erreur : division par zéro impossible.";
                        } else {
                            resultat = op1 / op2;
                        }
                        break;
                }

                if (err.equals("")) {
                    reponse = "Résultat : " + resultat;
                    System.out.println("Résultat envoyé au client : " + resultat);
                } else {
                    reponse = err;
                    System.out.println(err);
                }
            }

            // Envoi du résultat
            out.writeObject(reponse);

            // Fermeture
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
            System.out.println("Connexion fermée côté serveur.");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // --- Vérifie si une chaîne est un nombre valide (entier ou flottant) ---
    public static boolean estNumerique(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
