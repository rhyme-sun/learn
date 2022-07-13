package learn.spring.ioc.selfeditor;

import java.beans.PropertyEditorSupport;

/**
 * AddressPropertyEditor.
 */
public class AddressPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] s = text.split("_");
        Address address = new Address();
        address.setProvince(s[0]);
        address.setCity(s[1]);
        address.setCounty(s[2]);
        this.setValue(address);
    }
}
