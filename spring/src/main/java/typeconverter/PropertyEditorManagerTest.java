package typeconverter;

import model.User;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/9/5
 */
public class PropertyEditorManagerTest {

    public static void main(String[] args) {
        PropertyEditorManager.setEditorSearchPath(new String[]{"model"});
        PropertyEditor userPropertyEditor = PropertyEditorManager.findEditor(User.class);
        System.out.println(userPropertyEditor);
    }
}
