import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by c4q-madelyntavarez on 12/5/15.
 */
public class WordTrie {

     public class Node {
         char letter;
         Node[] children;
         int index = 0;

         public Node(char letter)
         {
             this.letter = letter;
             children = new Node[26];
             children[0]=this;
             index++;
         }

         public char getLetter()
         {
             return letter;
         }
         public Node[] getChildren()
         {
             return children;
         }

         public void addChild(Node node){
             if(index<26){
                 children[index]= node;
                 index++;
             }
         }
     }
    Node root;
    Node [] current;
    Stack<Node> nodes;
    Node currentNode;
    List<String> words;
    String word="";
    Node [] rootNodeArray;
    HashMap<Character,Node[]> map;

    public WordTrie(){
        root=new Node(' ');
        current= root.getChildren();
        rootNodeArray=root.getChildren();
        map = new HashMap<Character, Node[]>();
        map.put(root.getLetter(),rootNodeArray);
        currentNode=root;
    }


    public void add(String word) {
        if(word.length()<=0){ return;}
        System.out.println(word);

        for(Node node: current){
            if(node==null){
//                Node newNode= new Node(word.charAt(0));
//                current=newNode.children;
//                currentNode=newNode;
//                add(word.substring(1));
                continue;
            }

              if(node.getLetter() == word.charAt(0)){
                if(word.length()>1)
                {
                    current=node.getChildren();
                    currentNode=node;
                    add(word.substring(1));
                } else return;
            }
        }

        Node node= new Node(word.charAt(0));
        currentNode.addChild(node);

        if(map.containsKey(word.charAt(0))){
            current=map.get(word.charAt(0));
        } else {
            current=node.getChildren();
            map.put(word.charAt(0), current);
        } if(word.length()>1)
        {
            add(word.substring(1));
        }
    }

    public boolean contains(String word) {
        //make a new method that calls everything below
        current=rootNodeArray;
        return getContains(word);
    }

    private boolean getContains(String word)
    {
        System.out.println(word);

        for(Node node: current){
            if(node == null) return false;
            if(node.getLetter()==word.charAt(0)){
                if(word.length()>1){
                    current=map.get(node.getLetter());
                    return contains(word.substring(1));
                }
                return true;
            }
        }
        return false;
    }

    public List<String> toList() {
        words = new ArrayList<String>();
        currentNode=root;
        nodes=new Stack<Node>();
        nodes.push(currentNode);
        runToList();

        return words;
    }

    public void runToList(){

        while(!nodes.isEmpty())
        {
            current=map.get( nodes.pop().getLetter());

            for(Node node : current)
            {

                if(node == null)
                {
                    words.add(word);
                    break;
                }

                else
                {
                   // System.out.println("now");
                    word += node.getLetter();
                    nodes.push(node);

                }
            }
        }
    }

    public static void main(String[] args)
    {
        WordTrie wordTrie= new WordTrie();
        //wordTrie.add("TOMORROW");
        //wordTrie.add("APPLES");
        //wordTrie.add("COFFEE");
        wordTrie.add("TRAVEL");
        wordTrie.add("MADELYN");
        wordTrie.add("COFFEE");
        System.out.println(wordTrie.contains("COFFEE"));
        System.out.println(wordTrie.contains("MADELYN"));
        System.out.println(wordTrie.contains("TRAVEL"));
        System.out.println(wordTrie.toList());
    }

}
