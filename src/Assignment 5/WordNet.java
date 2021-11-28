import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.DirectedCycle;
import java.util.HashMap;

public class WordNet {

    private SAP sap;
    private HashMap<String, SET<Integer>> nouns;
    private HashMap<Integer, SET<String>> synonyms;
    private Digraph dg;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        nouns = new HashMap<>();
        synonyms = new HashMap<>();
        In in1 = new In(synsets);
        String[] s1 = in1.readAllLines();
        for (String line : s1) {
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            SET<String> synset = new SET<String>();
            for (String noun : fields[1].split(" ")) {
                synset.add(noun);

                SET<Integer> knownIds = this.nouns.get(noun);
                if (knownIds == null) {
                    knownIds = new SET<Integer>();
                    this.nouns.put(noun, knownIds);
                }
                knownIds.add(id);
            }
            this.synonyms.put(id, synset);
        }
        in1.close();
        dg = new Digraph(this.synonyms.keySet().size());
        In in2 = new In(hypernyms);
        String[] s2 = in2.readAllLines();
        for (String line : s2) {
            String[] fields = line.split(",");
            int synsetId = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int id = Integer.parseInt(fields[i]);
                dg.addEdge(synsetId, id);
            }
        }
        in2.close();
        this.sap = new SAP(this.dg);
        DirectedCycle  cycle = new DirectedCycle(dg);
        if (cycle.hasCycle()) {
            throw new IllegalArgumentException();
        }
        int roots = 0;
        for (int i = 0; i < this.dg.V(); i++) {
            if (this.dg.outdegree(i) == 0) {
                roots++;
            }
        }
        if (roots != 1) {
            throw new IllegalArgumentException();
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        if (nouns.containsKey(word)) return true;
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        SET<Integer> idA = nouns.get(nounA);
        SET<Integer> idB = nouns.get(nounB);

        return this.sap.length(idA,idB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        SET<Integer> idA = nouns.get(nounA);
        SET<Integer> idB = nouns.get(nounB);
        int ancestor = this.sap.ancestor(idA, idB);
        return toString(synonyms.get(ancestor)); //String Value of?
    }


    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private String toString(SET<String> set) {
        String ans = "";
        for (String s : set) {
            ans += " " + s;
        }

        return ans;
    }
}