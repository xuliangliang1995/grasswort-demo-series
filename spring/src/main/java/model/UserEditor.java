package model;

import typeconverter.StringToUserPropertyEditor;

import java.beans.PropertyEditorSupport;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/9/5
 */
public class UserEditor extends PropertyEditorSupport {

    private final StringToUserPropertyEditor stringToUserPropertyEditor;

    public UserEditor() {
        this.stringToUserPropertyEditor = new StringToUserPropertyEditor();
    }

    @Override
    public String getAsText() {
        return stringToUserPropertyEditor.getAsText();
    }


    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        stringToUserPropertyEditor.setAsText(text);
    }
}
