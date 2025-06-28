import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose Exercise: ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1 -> ex1();
            case 2 -> ex2();
        }
    }
    public static void ex1(){
        Scanner sc = new Scanner(System.in);
        List<List<Character>> edgeList = new LinkedList<>();
        while (true){
            System.out.println("Enter Edge");
            System.out.print("Enter Source('/' to exit): ");
            char source = sc.nextLine().charAt(0);
            if (source == '/'){
                break;
            }
            System.out.print("Enter Target: ");
            char target = sc.nextLine().charAt(0);
            edgeList.add(new LinkedList<>(List.of(source,target)));
        }
        System.out.print("Enter Destination: ");
        char destination = sc.nextLine().charAt(0);
        System.out.println("Number of routes: " + numberOfRoutesToVertex(edgeList,destination));
    }
    //List complied of edges only(For example: [[a,b],[a,c],[d,a],[d,b],[b,a]])
    private static int numberOfRoutesToVertex(List<List<Character>> edgeList, char destination){
        HashMap<Character, Integer> numRoutes = new HashMap<>();
        Stack<Character> topologicalOrder = new Stack<>();
        HashMap<Character,List<Character>> adjLists = new HashMap<>();
        for (List<Character> edge: edgeList) {
            if (!adjLists.containsKey(edge.get(0))) {
                adjLists.put(edge.get(0),new ArrayList<>());
            }
            adjLists.get(edge.get(0)).add(edge.get(1));
        }
        for (List<Character> edge: edgeList) {
            numRoutes.put(edge.get(0), 1);
            numRoutes.put(edge.get(1), 1);
        }
        HashSet<Character> visited = new HashSet<>();
        for (char vertex: numRoutes.keySet()) {
            if (!visited.contains(vertex)) {
                dfsVisit(adjLists,topologicalOrder,visited,vertex);
            }
        }
        while (!topologicalOrder.isEmpty()) {
            Character c = topologicalOrder.pop();
            for (Character adj: adjLists.getOrDefault(c,new LinkedList<>())) {
                numRoutes.put(adj, numRoutes.get(c) + numRoutes.get(adj));
            }
        }
        return numRoutes.get(destination);
    }
    private static void dfsVisit(HashMap<Character,List<Character>> adjLists,
                                 Stack<Character> topologicalOrder,
                                 HashSet<Character> visited,
                                 char curr){
        visited.add(curr);
        for (Character vertex: adjLists.getOrDefault(curr,new LinkedList<>())) {
            dfsVisit(adjLists,topologicalOrder,visited,vertex);
        }
        topologicalOrder.push(curr);
    }
    public static void ex2(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of cells: ");
        int n = sc.nextInt();
        int[] combinations = new int[n];
        combinations[0] = 2;
        for (int i = 1; i < n; i++) {
            combinations[i] = (int) (combinations[i-1] + Math.pow(3,i));
        }
        System.out.println("Number of combinations: " + combinations[n-1]);
    }
}