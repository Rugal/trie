package ga.rugal.trie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;

/**
 *
 * @author Brian, Rugal Bernstein
 */
public class TrieViewer extends JFrame
{

    private final ViewableTrie trie;

    public TrieViewer()
    {
        trie = new ViewableTrie(false);
    }

    public void go() throws Exception
    {
        JTree jTree = new JTree(trie.getTreeModel());
        getContentPane().add(new JScrollPane(jTree));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Trie Visualizer");
        setBounds(0, 0, 500, 600); //necessary to display with OpenJDK 7 on
        //Linux
        pack();
        setVisible(true);
    }

    public void addToDictionary(File f) throws IOException, FileNotFoundException
    {
        addToDictionary(new FileInputStream(f));
    }

    /**
     * Adds the words in file f to the dictionary, where word is
     * a series of characters surrounded by whitespace.
     *
     * @param f A file containing words.
     *
     * @throws java.io.IOException           When unable to read from file
     * @throws java.io.FileNotFoundException When no file found
     */
    public void addToDictionary(InputStream f) throws IOException, FileNotFoundException
    {
        long t = System.currentTimeMillis();
        final int bufSize = 1000;
        int read;
        int numWords = 0;
        try (InputStreamReader fr = new InputStreamReader(f))
        {
            char[] buf = new char[bufSize];
            while ((read = fr.read(buf)) != -1)
            {
                // TODO modify this split regex to actually be useful
                String[] words = new String(buf, 0, read).split("\\W");
                for (String s : words)
                {
                    trie.insert(s);
                    numWords++;
                }
            }
        }
        System.out.println("Read from file and inserted " + numWords + " words into trie in "
            + (System.currentTimeMillis() - t) / 1000.0 + " seconds.");
    }
}
