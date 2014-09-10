import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TextAnalyze {
    private static ArrayList<String> LetterList = new ArrayList<String>();
    private static HashMap<String,Integer> LetterCount = new HashMap<String,Integer>();

    private static ArrayList<String> WordList = new ArrayList<String>();
    private static HashMap<String,Integer> WordCount = new HashMap<String,Integer>();
    public static void main(String[] args)
    {
        String text = readFile("input.txt", Charset.defaultCharset());
        text = text.replace("\n","").replace("\r", " ");
        for(char c:text.toCharArray())
        {
            if(c!=' ')
            LetterList.add(""+c);
        }
        for(String s:LetterList)
        {
            if(LetterCount.containsKey(s))
            {
                LetterCount.put(s,LetterCount.get(s)+1);
            }
            else
            {
                LetterCount.put(s,1);
            }
        }
        String word = "";
        for(char c:text.toCharArray())
        {
            if(c == ' '|| c== '.'||c==',')
            {
                if(word.length()>0)
                    WordList.add(word);
                word = "";
            }
            else
            {
                word = word + c;
            }
        }
        if(word.length()>0)
            WordList.add(word);
        for(String s:WordList)
        {
            if(WordCount.containsKey(s))
            {
                WordCount.put(s,WordCount.get(s)+1);
            }
            else
            {
                WordCount.put(s,1);
            }
        }
        //System.out.println(LetterCount);
        HashMap<String,Integer> sortedWords = SortMap(WordCount);
        ArrayList<String> words = new ArrayList<String>(sortedWords.keySet());
        for(String s : words)
        {
            System.out.println(sortedWords.get(s)+"-"+s);
        }
    }
    public static String readFile(String path, Charset encoding)
    {
        try
        {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        }
        catch(IOException ie)
        {

        }
        return "fu";
    }

    public static <T> LinkedHashMap<T,Integer> SortMap(HashMap<T,Integer> input)
    {
        ArrayList<T> keys = new ArrayList<T>(input.keySet());
        ArrayList<Integer> values = new ArrayList<Integer>(input.values());
        outer:
        for(int i = 0; i < values.size();i++)
        {
            for(int j = 0; j < i;j++)
            {
                if(values.get(i)>values.get(j))
                {
                    T key = keys.get(i);
                    Integer value = values.get(i);
                    keys.remove(i);
                    values.remove(i);
                    keys.add(j,key);
                    values.add(j,value);
                    continue outer;
                }
            }
        }
        LinkedHashMap<T,Integer> output = new LinkedHashMap<T,Integer>();
        for(int i = 0; i < values.size();i++)
        {
            output.put(keys.get(i),values.get(i));
        }

        return output;
    }
}
