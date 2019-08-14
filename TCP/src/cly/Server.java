package cly;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import java.awt.Button;
import java.awt.TextField;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
public class Server extends JFrame{
	Button button_stop = new Button("\u505C\u6B62\u670D\u52A1\u5668");
	Button button_start = new Button("\u542F\u52A8\u670D\u52A1\u5668");
	
	TextField duibaoField = new TextField();
	TextField yanchiField_2 = new TextField();
	TextField textField_port = new TextField();
	TextField cuobaoField = new TextField();
	
	Label label = new Label("\u4E22\u5305\u7387(%)");
	Label label_1 = new Label("\u670D\u52A1\u5668\u4FE1\u606F");
	Label label_3 = new Label("\u9519\u5305\u7387(%)");
	Label label_4 = new Label("\u5EF6\u8FDF(ms)");
	Label label_2 = new Label("\u7AEF\u53E3\u53F7");
	
	TextArea textArea = new TextArea();
	
	Random ra =new Random();
	
    boolean started = false;
    ServerSocket ss = null;
    List<ClientThread> clients = new ArrayList<ClientThread>(); //����ͻ����߳���
    int port,yanchi,cuobao,diubao;
    boolean bConnected = true;
    private ServerThread c;
	public Server() {
        setSize(682,480);
        setLocation(0,0);
        textArea.setBackground(Color.WHITE);
        textArea.setEditable(false);
		getContentPane().setLayout(null);

		duibaoField.setBounds(421, 36, 81, 23);
		getContentPane().add(duibaoField);
		label.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		label.setBounds(342, 36, 87, 23);
		getContentPane().add(label);
		
		textArea.setBounds(10, 94, 644, 331);
		getContentPane().add(textArea);
		
		textField_port.setText("8888");
		cuobaoField.setText("0");
		yanchiField_2.setText("0");
		duibaoField.setText("0");
		label_1.setFont(new Font("Calibri", Font.PLAIN, 20));
		
		label_1.setBounds(10, 65, 160, 23);
		getContentPane().add(label_1);
		button_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					StartS();
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		button_start.setBounds(545, 10, 76, 23);
		
		getContentPane().add(button_start);
		label_2.setFont(new Font("Calibri", Font.PLAIN, 16));
		label_2.setBounds(153, 10, 49, 23);
		
		getContentPane().add(label_2);
		textField_port.setBounds(217, 10, 81, 23);
		
		getContentPane().add(textField_port);

		button_stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                    if (!started) {  
                        appendStr("��������δ����������ֹͣ������\n");  
                        return;  
                    }  
                     closeServer();  
                     started = false;
                     bConnected = false;
                     appendStr("�������ѹر�\n");
                        
                }  
		});
		button_stop.setBounds(545, 49, 76, 23);
		getContentPane().add(button_stop);
		label_3.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		label_3.setBounds(342, 65, 76, 23);
		getContentPane().add(label_3);
		
		cuobaoField.setBounds(421, 65, 81, 23);
		getContentPane().add(cuobaoField);
		label_4.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		label_4.setBounds(342, 10, 76, 23);
		getContentPane().add(label_4);
		
		yanchiField_2.setBounds(421, 10, 81, 23);
		getContentPane().add(yanchiField_2);
		
      	 
        addWindowListener(new WindowAdapter() { //��Ӧ�رմ����¼�
            public void windowClosing(WindowEvent e) {
                try {
					disconnect();
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
                System.exit(0);
            }
        });
	}
	//�رմ��ڼ���������
	void disconnect() throws IOException
    {
        try {  
            if (c != null)  
            	c.stop();;// ֹͣ�������߳�  
  
            for (int i = clients.size() - 1; i >= 0; i--) {  
                // �ͷ���Դ  
                clients.get(i).stop();// ֹͣ����Ϊ�ͻ��˷�����߳�  
                clients.get(i).dis.close();  
                clients.get(i).dos.close();  
                clients.get(i).socket.close();  
                clients.remove(i);  
            }  
            if (c != null) {  
                ss.close();// �رշ�����������  
            }  
            started = false;  
        } catch (IOException e) {  
            e.printStackTrace();  
            started = true;  
        } 
	
    }
	//��������������������
	protected void StartS() throws IOException {
        if (started) {  
            appendStr( "�������Ѵ�������״̬����Ҫ�ظ�����������\n");  
            return;  
        } 
        cuobao = Integer.parseInt(cuobaoField.getText());
        diubao = Integer.parseInt(duibaoField.getText());
        yanchi = Integer.parseInt(yanchiField_2.getText());
		port = Integer.parseInt(textField_port.getText());
		serverStart(port); //����������
		appendStr("������������!\n");
		appendStr("�˿ں�:  "+textField_port.getText()+"   ");
		appendStr("�ӳ�ms:  "+yanchiField_2.getText()+"    ");
		appendStr("������%:  "+duibaoField.getText()+"    ");
		appendStr("�����%:  "+cuobaoField.getText()+"\n\n");
	}
	//�رշ���������������
	protected void closeServer() {
        try {  
            if (c != null)  
            	c.stop();;// ֹͣ�������߳�  
  
            for (int i = clients.size() - 1; i >= 0; i--) {  
                // �ͷ���Դ  
                clients.get(i).stop();// ֹͣ����Ϊ�ͻ��˷�����߳�  
                clients.get(i).dis.close();  
                clients.get(i).dos.close();  
                clients.get(i).socket.close();  
                clients.remove(i);  
            }  
            if (c != null) {  
                ss.close();// �رշ�����������  
            }  
            started = false;  
        } catch (IOException e) {  
            e.printStackTrace();  
            started = true;  
        } 
		
	}
	//������������������
	void serverStart(int port2) throws IOException {
        ss = new ServerSocket(port2); //��port2�Ŷ˿ڴ�������socket 
        c = new ServerThread (ss);  
        c.start();
        //new Thread(c).start(); //�����߳�
        started = true;
	}
	//������
	public static void main(String[] args) {
    	Server Frame = new Server();
    	Frame.show();
    }
	//�ı���ʾ
    public void appendStr(String str)
    {
    	textArea.append(str);
    }
    //�������߳�
    class ServerThread extends Thread { //�����ͻ����߳̽���
    	private ServerSocket serverSocket; 
        public ServerThread (ServerSocket ss) {
        	serverSocket = ss;
        }

        public void run() {
                while (true) {
                	Socket socket;
					try {
						socket = serverSocket.accept();
						appendStr("�ͻ��˽���ɹ�\n");
		                 ClientThread client = new ClientThread(socket);  
		                 client.start();// �����Դ˿ͻ��˷�����߳�  
		                 clients.add(client);    
					} catch (IOException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}   
            }
    }
}
    //�ͻ����߳�,���ڷ���ͻ���ͨ��
    class ClientThread extends Thread {  
        private Socket socket;  
        DataInputStream dis = null;
        DataOutputStream dos = null;
        ClientThread(Socket socket)
        {
        	this.socket = socket;
        	try {
        		dis = new DataInputStream(socket.getInputStream());//���ͻ�����
        		dos = new DataOutputStream(socket.getOutputStream());//���ջ�����
        		} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
        	void send(String str) {
        		try {
        			dos.writeUTF(str);//���յ������ݻش����ͻ�
        		} catch (SocketException e) {
        			appendStr("�Է��˳���\n");
        			
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
       		public void run()
       		{
       		try {
       			while(bConnected)
       			{
       				String str;
       					str = dis.readUTF();//���տͻ����͵�����
	      				for (int i = 0; i < clients.size(); i++) {
	      					ClientThread c = clients.get(i);
	      					if(c.equals(this)==false)
	      						{
	      							try {
										ClientThread.sleep(yanchi);//�û������ӳ�
									} catch (InterruptedException e) {
										// TODO �Զ����ɵ� catch ��
										e.printStackTrace();
									}
	      							if(Math.sqrt((100-cuobao)/100)*100>=(ra.nextInt(100)+1))//�����ģ��
	      							{
	      								if(Math.sqrt((100-diubao)/100)*100>=(ra.nextInt(100)+1))//��������ģ��
	      								{
	      									c.send(str);
	      								}
	      								else
	      								{}
	      							}
	      							else
	      							{
	      								c.send(str);
	      								c.send(str);//���������ͬ����֡
	      							}
	      							
	      						}
	                    }
       			
	} 
       		}catch (EOFException e) {
		                appendStr("�͑����˳���\n");
		                clients.removeAll(clients);
		            } catch (IOException e) {
		                e.printStackTrace();
		            }finally {
		                if (dis != null)
		                    if (socket != null)
		                        try {
		                            dis.close();
		                            socket.close();
		                            dos.close();
		                        } catch (IOException e) {
		                            e.printStackTrace();
		                        }

       			}
       		}
      }
  }  