package parser.xml;

import java.util.*;

/**
 * A modifiable Element used internally by the parser. A modifiable Element should never be returned to the user.
 */
final class ModifiableElement extends Element{
    private String tempName="";
    private String tempInnerText="";
    ModifiableElement(){
        super(new HashMap<>(),new ArrayList<>(),"","");
    }

    /**
     * Finalizes the entire structure by calling this method on the root.
     * Every modifiable Element is turned into an Element. Which no longer allows for any modifikation.
     * @return
     */
    Element finalizeTree(){
        List<Element> newChildren=children.stream().map(c->c instanceof ModifiableElement m? m.finalizeTree():c).toList();
        var attributesCopy=getAttributes();
        children.clear();  //clear both Lists. Doesn't have to be here but should make things easier for the GC.
        attributes.clear();
        return new Element(attributesCopy,newChildren,tempName,tempInnerText);
    }
    Map<String,String> modifiableAttributes(){
        return attributes;
    }
    List<Element> modifiableChildren(){
        return children;
    }

    String getTempName() {
        return tempName;
    }

    void setTempName(String tempName) {
        this.tempName = tempName;
    }

    String getTempInnerText() {
        return tempInnerText;
    }

    void setTempInnerText(String tempInnerText) {
        this.tempInnerText = tempInnerText;
    }

    @Override
    public String getName() {
        return tempName;
    }

    @Override
    public String toString() {
        return "ModifiableElement{}";
    }
}
