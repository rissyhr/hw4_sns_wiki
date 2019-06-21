
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static ArrayList<User> userList= new ArrayList<>();
    static HashMap<String, Integer> nameToId = new HashMap<>();

    public static void main(String[] args){
        addVertexes();
        addEdges();
        int start = nameToId.get("jacob");
        int end = nameToId.get("vincent");
        if(searchRoute(start, end)) {
            System.out.println("Route Found!");
        }
    }

    public static void addVertexes(){
        try {
            File file = new File("src/nicknames.txt");
            if (!file.exists()) {
                System.out.print("File(nicknames.txt) Not Found");
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(str, "\t");
                int id = Integer.parseInt(st.nextToken());
                String name = st.nextToken();
                User user = new User(id);
                userList.add(user);
                nameToId.put(name,id);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addEdges(){
        try {
            File file = new File("src/links.txt");
            if (!file.exists()) {
                System.out.print("File(links.txt) Not Found");
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(str, "\t");
                int fromId = Integer.parseInt(st.nextToken());
                int toId = Integer.parseInt(st.nextToken());;
                userList.get(fromId).addFollowing(toId);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // BFS
    // todo: booleanではなく、ステップ数を返すように変更する
    public static boolean searchRoute(int start, int end){
        if (start == end) {
            // Success!
            return true;
        }
        Queue<Integer> queue = new ArrayDeque<>();

        userList.get(start).visited = true;
        queue.add(start);

        User user;
        while(! queue.isEmpty()){
            user = userList.get(queue.poll());

            for (int id : user.followList){
                User checkingUser = userList.get(id);
                if(checkingUser.visited == false){
                    if (id == end){
                        return true;
                    } else {
                        queue.add(id);
                    }
                }
            }
            user.visited = true;
        }
        return false;
    }
}

class User{
    int id;
    boolean visited;

    // todo: 型をIntegerからUserに変更する
    ArrayList<Integer> followList = new ArrayList<>();

    User(int id){
        this.id = id;
        visited = false;
    }

    void addFollowing(int id){
        followList.add(id);
    }
}
