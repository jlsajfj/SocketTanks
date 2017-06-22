public class Reciever extends Thread
{
    private java.net.ServerSocket serverSocket = null;
    private java.net.Socket socket = null;
    private java.io.ObjectInputStream inStream = null;
    private MainWorld w;
    private int port = 23374;
    public Reciever(MainWorld w, int port){
        this.w = w;
        this.port = port;
        try {
            this.serverSocket = new java.net.ServerSocket(this.port);
            serverSocket.setReuseAddress(true);
            System.out.println("SERVER RUNNING ON PORT " + this.port);
        } catch (java.io.IOException e) {
            throw new RuntimeException("Not able to open the server", e);
        }
    }

    public int getPort(){
        return port;
    }

    public String getIP(){
        if(serverSocket!=null){
            try{
                return serverSocket.getInetAddress().getLocalHost().toString();
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        return "";
    }

    public void run(){
        try{
            socket = serverSocket.accept();
            w.connect();
        }
        catch(java.io.IOException e){
            e.printStackTrace();
        }
        try {
            //inStream = new java.io.ObjectInputStream(socket.getInputStream());

            //java.util.ArrayList<java.util.ArrayList<Boolean>> crates = (java.util.ArrayList<java.util.ArrayList<Boolean>>) inStream.readObject(); for(int i = 0; i < MainWorld.WIDTH/Crate.SIZE; i++){ for(int j = 0; j < MainWorld.HEIGHT/Crate.SIZE; j++){ if(j==2||j==3) if(i==0||i==1||i==7||i==8) continue; if(crates.get(i).get(j)^w.crates.get(MainWorld.WIDTH/Crate.SIZE-i-1).get(MainWorld.HEIGHT/Crate.SIZE-j-1)) w.addObject(new Crate(), Crate.SIZE/2+i*Crate.SIZE, Crate.SIZE/2+j*Crate.SIZE); } }

            inStream = new java.io.ObjectInputStream(socket.getInputStream());
            Tank tank = new Tank(false, (Integer) inStream.readObject());
            w.tanks[1]=tank;
            w.tanks[1].fsetRotation(180);
            w.addObject(w.tanks[1], MainWorld.WIDTH-110, MainWorld.HEIGHT/2);
        }
        catch(java.net.SocketException se){
            System.exit(0);
        }
        catch(java.io.IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException cn){
            cn.printStackTrace();
        }
        while(true){
            try {
                inStream = new java.io.ObjectInputStream(socket.getInputStream());
                Object o = inStream.readObject();
                if(o instanceof Integer){
                    PickUp p = new PickUp((Integer) o);
                    p.shift();
                    int x = (Integer) inStream.readObject();
                    int y = (Integer) inStream.readObject();
                    w.addObject(p,MainWorld.WIDTH-x,MainWorld.HEIGHT-y);
                }
                else{
                    char keys = (Character) o;
                    w.tanks[1].sendKeys(keys);
                }
            }
            catch(java.net.SocketException se){
                System.exit(0);
            }
            catch(java.io.IOException e){
                e.printStackTrace();
            }
            catch(ClassNotFoundException cn){
                cn.printStackTrace();
            }
        }
    }
}
