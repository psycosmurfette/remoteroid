package org.secmem.remoteroid.service;

import java.util.ArrayList;

import org.secmem.remoteroid.IRemoteroid;
import org.secmem.remoteroid.data.RDSmsMessage;
import org.secmem.remoteroid.intent.RemoteroidIntent;
import org.secmem.remoteroid.receiver.SmsReceiver;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.SmsManager;

/**
 * A base service to communicate with PC.
 * @author Taeho Kim
 *
 */
public class RemoteroidService extends Service {
	
	private IBinder mBinder = new IRemoteroid.Stub() {
		
		@Override
		public void onNotificationCatched(String notificationText, long when)
				throws RemoteException {
			// TODO Notification hooked and notification data has been delivered. Now this data should be sent to PC.
		}

		@Override
		public void onReceiveCall(String displayedName, String number, long when)
				throws RemoteException {
			// TODO Call received.
			
		}

		@Override
		public void onReceiveSMS(String displayedName, String number,
				String body, long when) throws RemoteException {
			// TODO SMS Received.
			
		}

		@Override
		public void onSMSSent(String displayedName, String phoneNumber,
				long when) throws RemoteException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getConnectionStatus() throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void connect(String ipAddress, String password)
				throws RemoteException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void disconnect() throws RemoteException {
			// TODO Auto-generated method stub
			
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(intent.getExtras()!=null){
			ArrayList<RDSmsMessage> list = intent.getParcelableArrayListExtra(SmsReceiver.EXTRA_MSGS);
			
			for(RDSmsMessage msg : list){
				System.out.println(msg.toString());
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}


	/**
	 * Place a phone call.
	 * @param phoneNumber a Phone number
	 */
	private void callPhone(String phoneNumber){
		startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+phoneNumber)));
	}
	
	/**
	 * Send a SMS.
	 * @param phoneNumber a Phone number
	 * @param body SMS body text
	 */
	private void sendSMS(String phoneNumber, String body){
		if(!phoneNumber.matches("^\\d+$"))
			throw new NumberFormatException("Invalid phone number format.");
		SmsManager mng = SmsManager.getDefault();
		PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent(RemoteroidIntent.ACTION_SMS_SENT), 0);
		
		mng.sendTextMessage(phoneNumber, null, body, sentIntent, null);
	}
	
	private void lockNow(){
		
	}

}