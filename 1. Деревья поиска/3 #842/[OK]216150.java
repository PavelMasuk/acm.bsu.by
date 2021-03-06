import java.io.*;
import java.util.*;

public class AiSD1 {
    public static void main(String[] args) throws FileNotFoundException {
        Tree tree = new Tree();
        
        
        Scanner sc = new Scanner(new File("in.txt"));
        while(sc.hasNext()){
            String token = sc.next();
            tree.add(Integer.parseInt(token));
        }
        
        tree.remove(tree.getSpecAverage());
        
        tree.print(new PrintStream(new File("out.txt")));
    }
    
}

class Tree {
    Node head;
    
    public void print(PrintStream ps){
        if(head != null)
            get(ps, head);
    }
    
    private void get(PrintStream ps, Node node){
        ps.println(node.value);
        if(node.left != null)
            get(ps, node.left);
        if(node.right != null)
            get(ps, node.right);
    }
    
    private ArrayList<Node> searchForSpec(Node node){
        ArrayList<Node> result = new ArrayList();
        
        if(node.left != null)
            result.addAll(searchForSpec(node.left));
        if(node.right != null)
            result.addAll(searchForSpec(node.right));
        
       /* int res = 0;
        if(node.left != null)
            res = (node.left.l + 1);
        if(node.right != null)
            res -= (node.right.l + 1);*/
        node.l = 0;
        if(node.left == null)
            node.l = 0;
        else{
            node.l = node.left.l + node.left.r + 1;
        }
        if(node.right == null)
            node.r = 0;
        else{
            node.r = node.right.r + node.right.l + 1;;
        }
        
        if(Math.abs(node.l - node.r) == 1)
            result.add(node);
        
        return result;
    }
    
    public int getSpecAverage(){
//        calculateL(head);
        ArrayList<Node> list = searchForSpec(head);
        
        if(list.isEmpty())
            return -1;
        
        if(list.size() % 2 == 0)
            return -1;
       
        Collections.sort(list, new Comparator<Node>(){
            @Override
            public int compare(Node t, Node t1) {
                return Integer.compare(t.value, t1.value);
            }
        });
        
        int index = list.size() / 2;
        return list.get(index).value;
    }
    
    public void add(int value){
        if(head == null){
            head = new Node(value);
        }
        else{
            Node node = head;
            Node last = null;
            while(node != null){
                last = node;
                if(value < last.value){
                    node = node.left;
                }
                else if(value > last.value){
                    node = node.right;
                }
                else{
                    return;
                }
            }

            if(value < last.value){
                last.left = new Node(value);
                last.left.parent = last;
            } else {
                last.right = new Node(value);
                last.right.parent = last;
            }
        }
    }
    
    private Node search(int value){
        Node node = head;
        Node last = null;
        while(node != null){
            last = node;
            if(value == node.value){
                return node;
            } 
            if(value < node.value){
                node = node.left;
            }
            else{
                node = node.right;
            }
        }
        return null;
    }
    
    public void remove(int value){
        Node last = search(value);
        
        if(last == null)
            return;
        
        if(last.value == value){
            if(last.right == last.left){
                if(last.parent == null){
                    head = null;
                } else if(last.parent.left == last){
                    last.parent.left = null;
                } else {
                    last.parent.right = null;
                }
            } else if(last.right == null || last.left == null){
                if(last.right != null){
                    if(last.parent == null){
                        head = last.right;
                        last.right.parent = null;
                    } else
                    if(last.parent.right == last){
                        last.parent.right = last.right;
                        last.right.parent = last.parent;
                    } else {
                        last.parent.left = last.right;
                        last.right.parent = last.parent;
                    }
                } else {
                    if(last.parent == null){
                        head = last.left;
                        last.left.parent = null;
                    } else
                    if(last.parent.right == last){
                        last.parent.right = last.left;
                        last.left.parent = last.parent;
                    } else {
                        last.parent.left = last.left;
                        last.left.parent = last.parent;
                    }
                }
            } else {
                
                
                Node node = last.right;
                while(node.left != null)
                    node = node.left;
                int key = node.value;
                remove(key);
                last.value = key;
            }
        }
    }
    
/*    public void calculateL(Node node){
        node.l = 0;
        
        if(node.left != null){
            if(node.left.l == -1)
                calculateL(node.left);
            node.l += node.left.l + 1;
        }
        
        if(node.right != null){
            if(node.right.l == -1)
                calculateL(node.right);
            node.l += node.right.l + 1;
        }
    }*/
}

class Node {
    Node left;
    Node right;
    Node parent;
    int value;
    int l;
    int r;
    
    Node(int value){
        this.value = value;
        this.l = -1;
        this.r = 0;
    }
}