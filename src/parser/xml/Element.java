package parser.xml;

import utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An Element of the xml document. Each element has a name.
 * An Element can have children. If it doesn't an Empty list is returned.
 * An Element can have attributes. If it doesn't an Empty Map is returned.
 * An Element can have inner Text. If it doesn't an Empty Optional is returned.
 * The parser will return the root Element which contains the entire document.
 */
public sealed class Element permits ModifiableElement{
    protected final Map<String,String> attributes;
    protected final List<Element> children;
    protected final String name;
    protected final Optional<String> innerText;
    protected Element(Map<String,String> attributes, List<Element> children,String name,String innerText){
        this.attributes=attributes;
        this.children=children;
        this.name=name;
        this.innerText= Utils.optionalOfString(innerText);

    }

    /**
     * Return the attributes of this element. Each element has a name and a value.
     * @return An unmodifiable map containing the name as the key and the value as the value of the map for each attribute.
     */
    public Map<String,String> getAttributes(){
        return Map.copyOf(attributes);
    }

    /**
     * Returns the child elements.
     * @return An unmodifiable list of child elements.
     */
    public List<Element> getChildren(){
        return List.copyOf(children);
    }

    /**
     * Return the name of the Element.
     * @return The name of the Element.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the inner text of the element.
     * @return An Optional containing the inner text of the element. If it doesn't have any inner text an empty Optional is returned.
     */
    public Optional<String> getInnerText() {
        return innerText;
    }

    @Override
    public String toString() {
        return "Element{" +
                "name='" + name + '\'' +
                ", attributes=" + attributes +
                ", children=" + children +
                ", innerText=" + innerText +
                '}';
    }
}
