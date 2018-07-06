/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import StringWithID.StringWithID;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author C
 */
public class Server {

    public static void main(String args[]) throws Exception {

	System.out.println("Подключение к базе запущено");
	//буфер отправки
	byte[] sendData = new byte[1024];
	//буфер получения
	byte[] receiveData = new byte[1024];

	DatagramSocket serverSocket = new DatagramSocket(9002);

	while (true) {

	    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

	    serverSocket.receive(receivePacket);

	    //десериализуем
	    StringWithID swid = StringWithID.deserialize(receivePacket.getData());

	    System.out.println("Получено: " + swid.toString() + " от Клиент #" + swid.toInt());
	    InetAddress IPAddress = receivePacket.getAddress();
	    int port = receivePacket.getPort();

	    //обработка
	    StringWithID NewSWID = new StringWithID(swid.toInt(), swid.toString().toUpperCase());

	    sendData = StringWithID.serialize(NewSWID);
	    DatagramPacket sendPacket
		    = new DatagramPacket(sendData, sendData.length, IPAddress, port);
	    serverSocket.send(sendPacket);
	}
    }
}
