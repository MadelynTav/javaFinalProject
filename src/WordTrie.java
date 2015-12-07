import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.xml.bind.SchemaOutputResolver;

/**
 * Created by c4q-madelyntavarez on 12/5/15.
 */
public class WordTrie
{

    public class Node
    {
        char   letter;
        Node[] children;
        int index = 0;
        boolean endOfWord;

        public Node(char letter)
        {
            this.letter = letter;
            children = new Node[26];
            children[0] = this;
            endOfWord = false;
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

        public boolean isEndOfWord()
        {
            return endOfWord;
        }

        public void setEndOfWord(boolean endOfWord)
        {
            this.endOfWord = endOfWord;
        }

        public void addChild(Node node)
        {
            if(index < 26)
            {
                children[index] = node;
                index++;
            }
        }
    }

    Node         root;
    Node[]       current;
    Stack<Node>  nodes;
    Node         currentNode;
    List<String> words;
    String word = "";
    Node[]                     rootNodeArray;
    HashMap<Character, Node[]> map;
    int num = 0;

    public WordTrie()
    {
        root = new Node(' ');
        current = root.getChildren();
        nodes = new Stack<Node>();
        rootNodeArray = root.getChildren();
        map = new HashMap<Character, Node[]>();
        map.put(root.getLetter(), rootNodeArray);
        currentNode = root;
        words = new ArrayList<String>();
    }


    public void add(String word)
    {
        if(word.length() <= 0)
        {
            return;
        }

        Node node = new Node(word.charAt(0));
        currentNode.addChild(node);
        if(word.length() == 1)
        {
            node.setEndOfWord(true);
        }
        if(map.containsKey(word.charAt(0)))
        {
            current = map.get(word.charAt(0));
        }
        else
        {
            current = node.getChildren();
            map.put(word.charAt(0), current);
        }
        if(word.length() > 1)
        {
            add(word.substring(1));
        }
    }

    public boolean contains(String word)
    {
        current = rootNodeArray;
        return getContains(word);
    }

    private boolean getContains(String word)
    {
        for(Node node : current)
        {
            if(node == null)
            {
                return false;
            }
            if(node.getLetter() == word.charAt(0))
            {
                if(word.length() > 1)
                {
                    current = map.get(node.getLetter());
                    return contains(word.substring(1));
                }
                return true;
            }
        }
        return false;
    }

    public List<String> toList()
    {
        words = new ArrayList<String>();
        getAllWords(root);
        System.out.println(num);
        return words;
    }

    private void getAllWords(Node node)
    {
        if(node == null)
        {
            return;
        }

        for(Node child : map.get(node.getLetter()))
        {
            num++;
            if(child != null)
            {

                if(child.getLetter() == ' ')
                {
                    continue;
                }
                word += child.getLetter();

                if(child.endOfWord)
                {
                    words.add(word);
                    word = "";
                }
            }
        }
    }

    public static void main(String[] args)
    {
        WordTrie wordTrie = new WordTrie();
        //wordTrie.add("TOMORROW");
        //wordTrie.add("APPLES");
        //wordTrie.add("COFE");
        wordTrie.add("TRAVEL");
        wordTrie.add("MADELYN");//
        wordTrie.add("COFFEE");
        System.out.println(wordTrie.contains("COFFEE"));
        //        System.out.println(wordTrie.contains("MADELYN"));
        // System.out.println(wordTrie.contains("TRAVEL"));
        System.out.println(wordTrie.toList());
    }

}
