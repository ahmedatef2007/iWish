
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IWishServerApp {

    private ServerSocket server;
    private Vector<ClientHandler> clientsVector;

    public IWishServerApp() {
        clientsVector = new Vector<>();

        try {
            server = new ServerSocket(5005);
            while (true) {
                Socket clientSocket = server.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientsVector.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (server != null) {
                    server.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new IWishServerApp();
    }

    private class ClientHandler extends Thread {

        private Socket clientSocket;
        private DataInputStream dis;
        private PrintStream ps;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;

            try {
                dis = new DataInputStream(clientSocket.getInputStream());
                ps = new PrintStream(clientSocket.getOutputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String str = dis.readLine();

                    if (str == null || str.equals("close")) {
                        closeClient();
                        break;
                    } else if (str.equals("loginRequest")) {
                        boolean auth = new db.DataAccessLayer().login(dis.readLine(), dis.readLine());
                        if (auth) {
                            sendMessage("succeed");
                        } else {
                            sendMessage("failed");
                        }
                    }
                }
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            } finally {
                closeClient();
            }
        }

        private void sendMessage(String message) {
            clientsVector.get(clientsVector.indexOf(this)).ps.println(message);
            ps.println(message);
        }

        private void closeClient() {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (ps != null) {
                    ps.close();
                }
                clientsVector.remove(this);
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
