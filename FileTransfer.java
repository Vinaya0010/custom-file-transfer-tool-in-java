import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FileTransfer {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Send File");
        System.out.println("2. Receive File");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            sendFile(scanner); 
        } else if (choice == 2) {
            receiveFile(scanner); 
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }

    private static void sendFile(Scanner scanner) throws IOException { 
        System.out.print("Enter the hostname of the receiver: ");
        String hostname = scanner.next();
        System.out.print("Enter the port number: ");
        int port = scanner.nextInt();
        System.out.print("Enter the path to the file: ");
        String filePath = scanner.next();

        try (
                Socket socket = new Socket(hostname, port);
                OutputStream os = socket.getOutputStream();
                FileInputStream fis = new FileInputStream(filePath)
        ) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            System.out.println("File sent successfully.");
        } catch (IOException e) {
            System.err.println("Error sending file: " + e.getMessage());
        }
    }

    private static void receiveFile(Scanner scanner) throws IOException { 
        System.out.print("Enter the port number: ");
        int port = scanner.nextInt();
        System.out.print("Enter the path to save the received file: ");
        String filePath = scanner.next();

        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                FileOutputStream fos = new FileOutputStream(filePath)
        ) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            System.out.println("File received successfully.");
        } catch (IOException e) {
            System.err.println("Error receiving file: " + e.getMessage());
        }
    }
}