package cly;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class Make_Frame {
	List<Byte> Tran = new ArrayList<Byte>();
	transferm k = new transferm();
	public List<Byte> gettcp(int Sport, int Dport, int num, int confrim,int offset,int window,int check,int enpointer)
	{
		Tran.removeAll(Tran);
		byte[] Sp = new byte[2];//Դ�˿�
		byte[] Dp = new byte[2];//Ŀ�Ķ˿�
		byte[] nu = new byte[4];//���
		byte[] co = new byte[4];//ȷ�Ϻ�
		byte[] off = new byte[2];//����ƫ��
		byte[] win = new byte[2];//14,15 ����
		byte[] che = new byte[2];//16,17 �����
		byte[] enp = new byte[2];//18,19ͷ
		Sp=k.intToByte2(Sport);//0,20
		Dp=k.intToByte2(Dport);
		nu=k.intToByte4(num);
		co=k.intToByte4(confrim);
		off=k.intToByte2(offset);
		win=k.intToByte2(window);
		che=k.intToByte2(check);
		enp=k.intToByte2(enpointer);
		for(int i=0;i<Sp.length;i++)
		{
			Tran.add(Sp[i]);
		}
		for(int i=0;i<Dp.length;i++)
		{
			Tran.add(Dp[i]);
		}
		for(int i=0;i<nu.length;i++)
		{
			Tran.add(nu[i]);
		}
		for(int i=0;i<co.length;i++)
		{
			Tran.add(co[i]);
		}
		for(int i=0;i<off.length;i++)
		{
			Tran.add(off[i]);
		}
		for(int i=0;i<win.length;i++)
		{
			Tran.add(win[i]);
		}
		for(int i=0;i<che.length;i++)
		{
			Tran.add(che[i]);
		}
		for(int i=0;i<enp.length;i++)
		{
			Tran.add(enp[i]);
		}
		return Tran;
	}

}
