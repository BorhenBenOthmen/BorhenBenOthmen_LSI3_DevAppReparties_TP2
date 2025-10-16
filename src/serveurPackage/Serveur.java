package serveurPackage;

import java.io.*;
import java.net.*;

public class Serveur {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Le serveur attend la connexion d'un client...");

            Socket socket = serverSocket.accept();
            System.out.println("Un client est connecté.");

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            PrintWriter out = new PrintWriter(os, true);

            String message = in.readLine();
            System.out.println("Opération reçue : " + message);

            String[] parties = message.trim().split(" ");

            if (parties.length != 3) {
                out.println("Erreur : format invalide. Utilisez : nombre1 opérateur nombre2");
                System.out.println("Format invalide reçu !");
                socket.close();
                serverSocket.close();
                return;
            }

            String op1Str = parties[0];
            String operateur = parties[1];
            String op2Str = parties[2];

            if (!estNombre(op1Str) || !estNombre(op2Str)) {
                out.println("Erreur : les opérandes doivent être des nombres !");
                System.out.println("Erreur : opérande invalide.");
                socket.close();
                serverSocket.close();
                return;
            }

            float op1 = Float.parseFloat(op1Str);
            float op2 = Float.parseFloat(op2Str);
            float resultat = 0;
            String err = "";

            if (!(operateur.equals("+") || operateur.equals("-") ||
                    operateur.equals("*") || operateur.equals("/"))) {
                out.println("Erreur : opérateur invalide !");
                System.out.println("Erreur : opérateur invalide reçu !");
                socket.close();
                serverSocket.close();
                return;
            }

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
                out.println("Résultat : " + resultat);
                System.out.println("Résultat envoyé au client : " + resultat);
            } else {
                out.println(err);
                System.out.println(err);
            }

            in.close();
            out.close();
            socket.close();
            serverSocket.close();
            System.out.println("Connexion fermée côté serveur.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean estNombre(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}