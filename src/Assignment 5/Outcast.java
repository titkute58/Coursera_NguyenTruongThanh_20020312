import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;

    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        String outCast = nouns[0];
        int d = distance(nouns[0], nouns);
        for (int i = 1; i < nouns.length; i++) {
            int tmp = distance(nouns[i], nouns);
            if (d < tmp) {
                d = tmp;
                outCast = nouns[i];
            }
        }
        return outCast;
    }


    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordNet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

    private int distance(String s, String[] str) {
        int d = 0;
        for (String n : str) {
            d += wordNet.distance(s, n);
        }
        return d;
    }
}