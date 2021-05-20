package typeconverter;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import java.beans.PropertyEditorSupport;

public class PropertyEditorSupportTest {

    public static void main(String[] args) {
        PropertyEditorSupport editor = new PropertyEditorSupport();
        editor.addPropertyChangeListener(evt -> System.out.println("value changed !"));

        editor.setValue("jerry");
        editor.setValue("tom");

        System.out.println(editor.getAsText());

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        stringTrimmerEditor.setAsText(" 偏生皑皑雪，花缤落漫空。 ");
        System.out.println(stringTrimmerEditor.getValue());
    }
}
