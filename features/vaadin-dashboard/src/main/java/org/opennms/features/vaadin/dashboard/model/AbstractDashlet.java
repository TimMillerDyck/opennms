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
package org.opennms.features.vaadin.dashboard.model;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class AbstractDashlet implements Dashlet {
    private String m_name;
    /**
     * The {@link DashletSpec} to be used
     */
    private DashletSpec m_dashletSpec;

    public AbstractDashlet(String name, DashletSpec dashletSpec) {
        m_name = name;
        m_dashletSpec = dashletSpec;
    }

    @Override
    public final String getName() {
        return m_name;
    }

    public final void setName(String name) {
        m_name = name;
    }

    @Override
    public final DashletSpec getDashletSpec() {
        return m_dashletSpec;
    }

    public final void setDashletSpec(DashletSpec dashletSpec) {
        m_dashletSpec = dashletSpec;
    }

    private final void updateWallboard() {
    }

    private final void updateDashboard() {
    }

    @Override
    public boolean isBoosted() {
        return false;
    }

    @Override
    public DashletComponent getWallboardComponent() {
        return new AbstractDashletComponent() {
            private Label label = new Label(m_name + " wallboard view");

            public void refresh() {
            }

            public Component getComponent() {
                return label;
            }
        };
    }

    @Override
    public DashletComponent getDashboardComponent() {
        return new AbstractDashletComponent() {
            private Label label = new Label(m_name + " dashboard view");

            public void refresh() {
            }

            public Component getComponent() {
                return label;
            }
        };
    }
}
