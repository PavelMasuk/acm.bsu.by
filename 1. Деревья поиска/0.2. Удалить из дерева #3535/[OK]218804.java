import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

class Node {
    Node l;
    Node r;
    int v;

    Node(int v) {
        this.v = v;
    }
}

public class Main {

    static Node root;

    static void Add(int v) {
        if (root == null)
            root = new Node(v);
        else Add(v, root);
    }

    static void Add(int v, Node n) {
        if (v < n.v) {
            if (n.l == null)
                n.l = new Node(v);
            else Add(v, n.l);
        } else if (v > n.v) {
            if (n.r == null)
                n.r = new Node(v);
            else Add(v, n.r);
        }
    }

    static void output(PrintWriter pw, Node n) {
        if (n == null)
            return;

        pw.println(n.v);

        output(pw, n.l);
        output(pw, n.r);
    }

    static void deleteKey(int key)
    {
        root = deleteRec(root, key);
    }

    static Node deleteRec(Node root, int key)
    {
        if (root == null)  return root;

        if (key < root.v)
            root.l = deleteRec(root.l, key);
        else if (key > root.v)
            root.r = deleteRec(root.r, key);

        else
        {
            if (root.l == null)
                return root.r;
            else if (root.r == null)
                return root.l;

            root.v = minValue(root.r);

            root.r = deleteRec(root.r, root.v);
        }

        return root;
    }

    static int minValue(Node root)
    {
        int minv = root.v;
        while (root.l != null)
        {
            minv = root.l.v;
            root = root.l;
        }
        return minv;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("input.txt"));

        String line = br.readLine();
        int k = Integer.parseInt(line);
        br.readLine();

        while ((line = br.readLine()) != null) {
            int x = Integer.parseInt(line);
            Add(x);
        }

        deleteKey(k);

        PrintWriter pw = new PrintWriter("output.txt");

        output(pw, root);

        pw.close();

    }
}
