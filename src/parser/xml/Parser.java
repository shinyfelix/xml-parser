package parser.xml;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Parser {
    private static final Pattern name=Pattern.compile("(\\p{Alpha}\\p{Alnum}*)");
    private static final Pattern text=Pattern.compile("(\"[^\"]*\")");
    private static final Pattern attribute=Pattern.compile("\\s*"+name+"\\s*=\\s*"+text+"\\s*");
    private static final Pattern nodeOpen=Pattern.compile("\\s*<\\s*("+name+")\\s*(\\s+"+attribute+")*>\\s*");
    private static final Pattern nodeClose=Pattern.compile("\\s*<\\s*/\\s*"+name+"\\s*>\\s*");
    private static final Pattern nodeOpenClose=Pattern.compile("\\s*<\\s*("+name+")\\s*(\\s+"+attribute+")*/\\s*>\\s*");
    private static final Pattern node=Pattern.compile("("+nodeOpen+")|("+nodeClose+")|("+nodeOpenClose+")");

    /**
     * Return the root element of the document. 
     * @param document The document you want to parse
     * @return An Optional containing the root element of the document, unless the format is invalid. In that case return an empty Optional.
     */
    public static Optional<Element> parse(String document){
        Matcher matcher=node.matcher(document);
        //The document structure is verified by the stack
        Stack<ModifiableElement> stack=new Stack<>();
        Element root=null;
        int i=0;
        while (matcher.find()){
            //check if the last opening bracket '<' was part of a correct node
            if (matcher.start()>i&&i!=0)return Optional.empty();
            String matched=matcher.group();
            //find out which case was true. 
            //Could be optimized by using capture groups, but since they are subject to change it is too much effort for now.
            Matcher open=nodeOpen.matcher(matched);
            Matcher close=nodeClose.matcher(matched);
            Matcher openClose=nodeOpenClose.matcher(matched);
            if (open.matches()){
                ModifiableElement x=new ModifiableElement();
                if (root==null){
                    root=x;
                }
                x.attributes.putAll(readAttributes(matched));
                x.setTempName(readName(matched));
                stack.push(x);
            }
            else if (close.matches()){
                if (stack.empty())return Optional.empty();
                ModifiableElement x=stack.pop();
                if (!readName(matched).equals(x.getName())){
                    return Optional.empty();
                }
                if (!stack.empty()){
                    stack.peek().modifiableChildren().add(x);
                }
            }
            else if (openClose.matches()){
                //we can instantly create an element because we don't have to modify it anymore.
                Element x=new Element(readAttributes(matched), Collections.emptyList(),readName(matched),"");
                if (root==null){
                    root=x;
                }
                if (!stack.empty()){
                    stack.peek().modifiableChildren().add(x);
                }
            }
            i= matcher.end();
            //find any inner text. Text outside an element is ignored.
            while (i<document.length()&&document.charAt(i)!='<'){
                //file is invalid if '>' is in any inner Text
                if (document.charAt(i)=='>')return Optional.empty();
                i++;
            }
            if (!stack.empty()){
                String s=document.substring(matcher.end(),i);
                stack.peek().setTempInnerText(stack.peek().getTempInnerText()+s);
            }
        }
        if (!stack.empty())return Optional.empty();
        //finalize the tree structure if necessary.
        return Optional.ofNullable(root).map(element -> element instanceof ModifiableElement m?m.finalizeTree():element);
    }
    private static Map<String, String> readAttributes(String node){
        Matcher matcher=attribute.matcher(node);
        Map<String,String> map=new HashMap<>();
        while (matcher.find()){
            String name=matcher.group(1);
            String text=matcher.group(2);
            map.put(name,text);
        }
        return map;
    }
    private static String readName(String node){
        Matcher matcher=name.matcher(node);
        if (matcher.find()){
            return matcher.group();
        }
        throw new IllegalStateException("If this exception is thrown something went badly wrong");
    }
}
