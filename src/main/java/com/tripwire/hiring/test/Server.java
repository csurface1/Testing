/*
 * Copyright (c) 2015, Tripwire, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *  o Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * -----------------------------------------------------------------------
 *
 * Written by: bobl
 *
 * =======================================================================
 */
package com.tripwire.hiring.test;


import com.google.common.annotations.VisibleForTesting;

import javax.net.ServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

  public static final int SERVER_PORT = 10000;

  public static void main(String[] args) throws Exception {
    
    (new Server()).start();
    /*
    ServerSocketFactory socketFactory = ServerSocketFactory.getDefault();
    ServerSocket serverSocket = null;
    try {
      serverSocket = socketFactory.createServerSocket(SERVER_PORT);

      listenAndRespond(serverSocket);

    } catch (IOException e) {
      System.out.printf("Socket Failure: %s", e.getMessage());
    } finally {
      if (serverSocket != null) {
        serverSocket.close();
      }
    }
    */
  }

  @VisibleForTesting
  protected static void listenAndRespond(ServerSocket serverSocket) throws IOException {
    DataHandler handler = new EchoDataHandler();
    String data = null;
    Socket clientSocket = null;
    while (true) {
      clientSocket = serverSocket.accept();
      PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
      BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      handleClientData(handler, writer, reader);
      clientSocket.close();
    }
  }

  @VisibleForTesting
  protected static void handleClientData(DataHandler handler, PrintWriter writer, BufferedReader reader) throws IOException {
    String data;
    while ((data = reader.readLine()) != null) {
      writer.println(handler.handle(data));
    }
  }

  public interface DataHandler {
    String handle(String data);
  }

  public static class EchoDataHandler implements DataHandler{
    @Override
    public String handle(String data) {
      System.out.println("Received data = " + data);
      return contigous(data);
    }
  }
  
  public void run() {
    //int SERVER_PORT = 10000;
    ServerSocketFactory socketFactory = ServerSocketFactory.getDefault();
    ServerSocket serverSocket = null;
    try {
      serverSocket = socketFactory.createServerSocket(10000);

      listenAndRespond(serverSocket);

    } catch (IOException e) {
      System.out.printf("Socket Failure: %s", e.getMessage());
    } finally {
      if (serverSocket != null) {
        serverSocket.close();
      }
    }
    serverSocket.close();
  }
  
  /*
     * Reads in a String and returns the longest chain of repeating characters 
     * for that String
     */
    public static String contigous(String a){
     
        int iCount;
        int iLongest = 0;
        int iPos = 0;
        char cTemp;
        
        //Go through each character in String a from start to finish
        for(int i = 0; i < a.length(); i++){
            
            cTemp = a.charAt(i);
            iCount = 0;           
            
            //Check character from cTemp onward until the next one isn't equal
            for(int j = i; j < a.length(); j++){
                if(a.charAt(j) != cTemp)
                    break;
                else
                    iCount++;                     
            }
            
            //If the next sequence of characters was longer then previous, update iLongest value
            if(iCount > iLongest){
                iLongest = iCount;
                iPos = i;
            }

        }
        
       //return the result of longest contigous character
       return "("+iPos+", "+iLongest+")";
    }
}
