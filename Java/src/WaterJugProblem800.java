import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WaterJugProblem {

	private static List<String> visitedStates = new ArrayList<String>();
	static Queue<Node> q = new LinkedList<>();

	static class Node {
		String data;
		Node previous;

		public Node(String data, Node parent) {
			this.data = data;
			this.previous = parent;
		}
	};

	/**
	 * @param state
	 * @return
	 */
	private static List<Node> createAdjacentStates(Node parent) {
		String state = parent.data;
		List<Node> adjacentStates = new ArrayList<Node>();
		int firstJug = state.charAt(0) - '0';
		int secondJug = state.charAt(1) - '0';
		int thirdJug = state.charAt(2) - '0';
		if (firstJug != 0) {
			if (5 - secondJug <= firstJug) {
				firstJug = firstJug - (5 - secondJug);
				secondJug = 5;

			} else {
				secondJug = secondJug + firstJug;
				firstJug = 0;
			}
			String newState = new StringBuffer().append(firstJug).append(secondJug).append(thirdJug).toString();
			//Check in the queue as well
			if (visitedStates.contains(newState)) {
			} else {
				visitedStates.add(newState);
				adjacentStates.add(new Node(newState,parent));
			}

			firstJug = state.charAt(0) - '0';
			secondJug = state.charAt(1) - '0';
			thirdJug = state.charAt(2) - '0';

			if (3 - thirdJug <= firstJug) {
				firstJug = firstJug - (3 - thirdJug);
				thirdJug = 3;
			} else {
				thirdJug = thirdJug + firstJug;
				firstJug = 0;
			}

			String newState1 = new StringBuffer().append(firstJug).append(secondJug).append(thirdJug).toString();
			if (visitedStates.contains(newState1)) {
			} else {
				visitedStates.add(newState1);
				adjacentStates.add(new Node(newState1,parent));
			}
		}
		firstJug = state.charAt(0) - '0';
		secondJug = state.charAt(1) - '0';
		thirdJug = state.charAt(2) - '0';

		if (secondJug != 0) {
			if (8 - firstJug <= secondJug) {
				secondJug = secondJug - (8 - firstJug);
				firstJug = 8;
			} else {
				firstJug = firstJug + secondJug;
				secondJug = 0;
			}

			String newState = new StringBuffer().append(firstJug).append(secondJug).append(thirdJug).toString();
			if (visitedStates.contains(newState)) {
			} else {
				visitedStates.add(newState);
				adjacentStates.add(new Node(newState,parent));
			}

			firstJug = state.charAt(0) - '0';
			secondJug = state.charAt(1) - '0';
			thirdJug = state.charAt(2) - '0';

			if (3 - thirdJug <= secondJug) {
				secondJug = secondJug - (3 - thirdJug);
				thirdJug = 3;
			} else {
				thirdJug = secondJug + thirdJug;
				secondJug = 0;
			}
			String newState1 = new StringBuffer().append(firstJug).append(secondJug).append(thirdJug).toString();
			if (visitedStates.contains(newState1)) {
			} else {
				visitedStates.add(newState1);
				adjacentStates.add(new Node(newState1,parent));
			}
		}
		firstJug = state.charAt(0) - '0';
		secondJug = state.charAt(1) - '0';
		thirdJug = state.charAt(2) - '0';

		if (thirdJug != 0) {
			if (8 - firstJug <= thirdJug) {
				thirdJug = thirdJug - (8 - firstJug);
				firstJug = 8;
			} else {
				firstJug = firstJug + thirdJug;
				thirdJug = 0;
			}
			String newState = new StringBuffer().append(firstJug).append(secondJug).append(thirdJug).toString();
			if (visitedStates.contains(newState)) {
			} else {
				visitedStates.add(newState);
				adjacentStates.add(new Node(newState,parent));
			}
			firstJug = state.charAt(0) - '0';
			secondJug = state.charAt(1) - '0';
			thirdJug = state.charAt(2) - '0';
			if (5 - secondJug <= thirdJug) {
				thirdJug = thirdJug - (5 - secondJug);
				secondJug = 5;
			} else {
				secondJug = secondJug + thirdJug;
				thirdJug = 0;
			}
			String newState1 = new StringBuffer().append(firstJug).append(secondJug).append(thirdJug).toString();
			if (visitedStates.contains(newState1)) {
			} else {
				visitedStates.add(newState1);
				adjacentStates.add(new Node(newState1,parent));
			}
		}
		for (Node adjState : adjacentStates) {
			if (adjState.data.equals("143") || adjState.data.equals("440") || adjState.data.equals("413")) {
				while(adjState.previous != null) {
					System.out.println(adjState.data +" --->");
					adjState = adjState.previous;
				}
				System.out.println(adjState.data);
				System.exit(0);
			}
		}

		return adjacentStates;
	}

	public static void main(String args[]) {
		visitedStates.add("800");
		q.add(new Node("800", null));
		callRecursive(visitedStates);
	}

	private static void callRecursive(List<String> state) {
		while (q.size() > 0) {
			Node removedele = q.remove();
			for (Node adState : createAdjacentStates(removedele)) {
				q.add(adState);
			}
		}
	}

}
