package typeconverter;

import model.User;

import java.beans.PropertyEditorSupport;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/9/5
 */
public class StringToUserPropertyEditor extends PropertyEditorSupport {
    // 例如: 1-jerry-8

    @Override
    public String getAsText() {
        User user = (User) getValue();
        return user.getId() + "-" + user.getName() + "-" + user.getAge();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] strArray = text.split("-");
        User user = new User();
        user.setId(Long.valueOf(strArray[0]));
        user.setName(strArray[1]);
        user.setAge(Integer.valueOf(strArray[2]));
        setValue(user);
    }

    public static void main(String[] args) {
        String text = "1-jerry-8";
        StringToUserPropertyEditor editor = new StringToUserPropertyEditor();
        editor.setAsText(text);
        User user = (User) editor.getValue();
        System.out.println(user);
    }
}
