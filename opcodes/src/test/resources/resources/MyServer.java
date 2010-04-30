import java.io.*;
import java.net.*;

public class MyServer{
  private int port;
  private ServerSocket server;

  public MyServer(int defaultPort){
    try{
      if(defaultPort > 0){
        port = defaultPort;
        server = new ServerSocket(port);
      }
      else{
        server = new ServerSocket();
      }
    } catch(IOException e){
    }
  }
}
