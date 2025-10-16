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
            String[] parties = message.split(" ");
        
            if(parties[1]!="+" && parties[1]!="-" && parties[1]!="*" && parties[1]!="/" );
            System.out.println("l'operateur est invalide");
            float op1 = Float.parseFloat(parties[0]);
            float op2 = Float.parseFloat(parties[2]);
            float resultat =0 ;
            String err ="";
            switch (parties[1]) {
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
                	
                    err= "on ne peut pas divisé par '0'";
                    break;
                } else {
                    resultat = op1 / op2;
                }
        }
            if(err == "") {
            System.out.println("Résultat calculé : " + resultat);
            out.println(resultat);
            System.out.println("Résultat envoyé au client.");
            }
            else 
            	out.println("erreur :"+err);
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
            System.out.println("Connexion fermée côté serveur.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
