/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2006-2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/
package org.opennms.features.vaadin.events;

import org.opennms.netmgt.model.OnmsSeverity;

import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 * A factory for creating Event Field objects.
 * 
 * @author <a href="mailto:agalue@opennms.org">Alejandro Galue</a> 
 */
@SuppressWarnings("serial")
public final class EventFormFieldFactory implements FormFieldFactory {

    /* (non-Javadoc)
     * @see com.vaadin.ui.FormFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
     */
    public Field createField(Item item, Object propertyId, Component uiContext) {
        if ("logmsgDest".equals(propertyId)) {
            final ComboBox dest = new ComboBox("Destination");
            dest.addItem("logndisplay");
            dest.addItem("donotpersist");
            dest.addItem("discardtraps");
            dest.setNullSelectionAllowed(false);
            dest.setRequired(true);
            return dest;
        }
        if ("logmsgContent".equals(propertyId)) {
            final TextArea content = new TextArea("Log Message");
            content.setWidth("100%");
            content.setRows(10);
            content.setRequired(true);
            return content;
        }
        if ("severity".equals(propertyId)) {
            final ComboBox severity = new ComboBox("Severity");
            for (String sev : OnmsSeverity.names()) {
                severity.addItem(sev.substring(0, 1).toUpperCase() + sev.substring(1).toLowerCase());
            }
            severity.setNullSelectionAllowed(false);
            severity.setRequired(true);
            return severity;
        }
        if ("descr".equals(propertyId)) {
            final TextArea descr = new TextArea("Description");
            descr.setWidth("100%");
            descr.setRows(10);
            descr.setRequired(true);
            return descr;
        }
        if ("maskElements".equals(propertyId)) {
            final MaskElementField field = new MaskElementField();
            field.setCaption("Mask Elements");
            return field;
        }
        if ("maskVarbinds".equals(propertyId)) {
            final MaskVarbindField field = new MaskVarbindField();
            field.setCaption("Mask Varbinds");
            return field;
        }
        if ("varbindsdecodeCollection".equals(propertyId)) {
            final VarbindsDecodeField field = new VarbindsDecodeField();
            field.setCaption("Varbind Decodes");
            return field;
        }
        if ("alarmData".equals(propertyId)) {
            final AlarmDataField field = new AlarmDataField();
            field.setCaption("Alarm Data");
            return field;
        }
        if ("uei".equals(propertyId)) {
            final TextField f = new TextField("Event UEI");
            f.setRequired(true);
            f.setWidth("100%");
            return f;
        }
        if ("eventLabel".equals(propertyId)) {
            final TextField f = new TextField("Event Label");
            f.setRequired(true);
            f.setWidth("100%");
            return f;
        }
        final Field f = DefaultFieldFactory.get().createField(item, propertyId, uiContext);
        f.setWidth("100%");
        return f;
    }
}