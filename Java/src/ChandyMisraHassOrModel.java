import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Vinay Kumar Bolisetty
 *
 */
public class ChandyMisraHassOrModel {

	static int n = 0;

	static int[][] p;

	static boolean reply = false;

	static int[][] num;

	static int[][] wait;

	static int[][] engagingQuery;

	static List<Message> recInitMsgs = new ArrayList<>();

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the number of processor ");
		n = sc.nextInt();
		p = new int[n][n];
		num = new int[n][n];
		wait = new int[n][n];
		engagingQuery = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print("Is Processor P" + (i + 1) + " waiting for resource held by P" + (j + 1));
				p[i][j] = sc.nextInt();
			}
		}

		sc.close();

		int initiator = 0;
		Message m = new Message();
		m.setInit(initiator);
		m.setSender(initiator);
		m.setReceiver(-1);

		for (int i = 0; i < 3; i++) {
			issueMsg(m, initiator);
		}
		System.out.println("Reply process started");
		initiateReplyProcess();

	}

	private static void issueMsg(Message m, int initiator) {

		List<Message> msgs = new ArrayList<Message>();
		for (int i = 0; i < p[initiator].length; i++) {
			if (p[initiator][i] == 1) {
				Message queryMsg = new Message();
				queryMsg.setInit(m.getInit());
				queryMsg.setSender(initiator);
				queryMsg.setReceiver(i);
				msgs.add(queryMsg);
			}
		}

		if (msgs.size() > 0) {
			num[initiator][m.getInit()] = msgs.size();
			wait[initiator][m.getInit()] = 1;
		}

		for (Message msg : msgs) {
			senderAndReciever(msg, msg.getReceiver());
		}
	}

	private static void display(int arr[][]) {

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static void senderAndReciever(Message m, int currentProcess) {

		if (m.getInit() == m.getReceiver()) {
			recInitMsgs.add(m);
			return;
		}

		if (engagingQuery[m.getReceiver()][m.getInit()] == 0) {

			List<Message> msgs = new ArrayList<Message>();

			for (int i = 0; i < p[m.getReceiver()].length; i++) {
				if (p[m.getReceiver()][i] == 1) {
					Message queryMsg = new Message();
					queryMsg.setInit(m.getInit());
					queryMsg.setSender(m.getReceiver());
					queryMsg.setReceiver(i);
					engagingQuery[m.getReceiver()][m.getSender()] = 1;
					System.out.println(queryMsg.toString());
					msgs.add(queryMsg);
				}
			}

			if (msgs.size() > 0) {
				num[m.getReceiver()][m.getInit()] = msgs.size();
				wait[m.getReceiver()][m.getInit()] = 1;
			}

			for (Message msg : msgs) {
				senderAndReciever(msg, msg.getReceiver());
			}
		} else {
			System.out.println("Received a message already from you " + m);
			num[m.getReceiver()][m.getInit()] = num[m.getReceiver()][m.getInit()] + 1;
			Message newMsg = new Message();
			newMsg.setReceiver(m.getSender());
			newMsg.setSender(m.getReceiver());
			newMsg.setInit(m.getInit());
			System.out.println(newMsg);
			replyProcess(m);

		}
		return;

	}

	private static void initiateReplyProcess() {

		for (Message m : recInitMsgs) {
			Message replyMsg = new Message();
			replyMsg.setReceiver(m.getSender());
			replyMsg.setSender(m.getReceiver());
			replyMsg.setInit(m.getInit());
			System.out.println(replyMsg.toString());
			replyProcess(replyMsg);
		}
	}

	private static void replyProcess(Message m) {

		if (wait[m.getReceiver()][m.getInit()] == 1) {
			num[m.getReceiver()][m.getInit()] = num[m.getReceiver()][m.getInit()] - 1;
			if (num[m.getReceiver()][m.getInit()] == 0) {

				if (m.getInit() == m.getReceiver()) {
					System.out.println("******************Dead Lock Exist********************");
					System.exit(0);
				} else {
					List<Message> msgs = new ArrayList<Message>();
					for (int i = 0; i < engagingQuery[m.getReceiver()].length; i++) {
						if (engagingQuery[m.getReceiver()][i] == 1) {
							Message replyMsg = new Message();
							replyMsg.setInit(m.getInit());
							replyMsg.setSender(m.getReceiver());
							replyMsg.setReceiver(i);
							System.out.println(replyMsg.toString());
							msgs.add(replyMsg);
						}
					}

					for (Message m1 : msgs) {
						replyProcess(m1);
					}
				}
			}
		}
	}

}

class Message {

	private int init;

	private int sender;

	private int receiver;

	public int getInit() {
		return init;
	}

	public void setInit(int init) {
		this.init = init;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "Message [init=" + (init + 1) + ", sender=" + (sender + 1) + ", receiver=" + (receiver + 1) + "]";
	}

}
