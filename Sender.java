/**
 * Write a description of class Sender here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sender 
{
    private java.net.Socket socket = null;
    private java.io.ObjectInputStream inputStream = null;
    private java.io.ObjectOutputStream outputStream = null;
    private String host = null;
    private int port = 23374;
    public Sender(String host, int port){
        this.host=host;
        this.port=port;
        try{
            socket = new java.net.Socket(host, port);
            System.out.println("Connected");
        }
        catch(java.net.SocketException se){
            se.printStackTrace();
            throw new RuntimeException("Not able to connect", se);
            // System.exit(0);
        }
        catch(java.io.IOException e){
            e.printStackTrace();
        }
        catch(Exception ee){
            ee.printStackTrace();
        }
    }
    
    public void sendTank(Tank a){
        try{
            outputStream = new java.io.ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(a.getGun());
            outputStream.flush();
        }
        catch(java.io.IOException e){
            e.printStackTrace();
        }
    }
    
    public void sendCrates(java.util.ArrayList<java.util.ArrayList<Boolean>> a){
        try{
            outputStream = new java.io.ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(a);
            outputStream.flush();
        }
        catch(java.io.IOException e){
            e.printStackTrace();
        }
    }
    
    public void sendPickUp(PickUp p, int x, int y){
        try{
            outputStream = new java.io.ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(p.getType());
            outputStream.writeObject(x);
            outputStream.writeObject(y);
            outputStream.flush();
        }
        catch(java.io.IOException e){
            e.printStackTrace();
        }
    }

    public void sendKeys(char keys) {
        try{
            outputStream = new java.io.ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(keys);
            outputStream.flush();
        }
        catch(java.io.IOException e){
            e.printStackTrace();
        }
    }
}
