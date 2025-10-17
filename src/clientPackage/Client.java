package clientPackage;

import java.io.*;
import java.net.*;
import java.util.*;
import objetPackage.Operation;

public class Client {
    public static void main(String[] args) {
        System.out.println("Je suis un client pas encore connecté…");

        try {
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Je suis un client connecté.");

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Scanner sc = new Scanner(System.in);

            System.out.print("Entrez le premier opérande : ");
            String op1 = sc.nextLine();

            System.out.print("Entrez l’opérateur (+, -, *, /) : ");
            String operateur = sc.nextLine();

            System.out.print("Entrez le deuxième opérande : ");
            String op2 = sc.nextLine();

            // Création et envoi de l’objet Operation
            Operation op = new Operation(op1, operateur, op2);
            out.writeObject(op);
            System.out.println("Objet Operation envoyé au serveur : " + op);

            // Lecture du résultat
            String reponse = (String) in.readObject();
            System.out.println(">>> Résultat reçu du serveur : " + reponse);

            in.close();
            out.close();
            socket.close();
            sc.close();
            System.out.println("Connexion fermée côté client.");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
