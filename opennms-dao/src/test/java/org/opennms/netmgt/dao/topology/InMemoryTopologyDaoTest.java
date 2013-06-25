/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2012 The OpenNMS Group, Inc.
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

package org.opennms.netmgt.dao.topology;

import static org.junit.Assert.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.opennms.core.test.MockLogAppender;
import org.opennms.netmgt.dao.TopologyDao;
import org.opennms.netmgt.dao.topology.TopologyDaoInMemoryImpl;
import org.opennms.netmgt.model.topology.BridgeDot1dTpFdbLink;
import org.opennms.netmgt.model.topology.BridgeElementIdentifier;
import org.opennms.netmgt.model.topology.BridgeEndPoint;
import org.opennms.netmgt.model.topology.Element;
import org.opennms.netmgt.model.topology.EndPoint;
import org.opennms.netmgt.model.topology.InetElementIdentifier;
import org.opennms.netmgt.model.topology.LldpElementIdentifier;
import org.opennms.netmgt.model.topology.LldpEndPoint;
import org.opennms.netmgt.model.topology.MacAddrElementIdentifier;
import org.opennms.netmgt.model.topology.MacAddrEndPoint;
import org.opennms.netmgt.model.topology.NodeElementIdentifier;
import org.opennms.netmgt.model.topology.LldpElementIdentifier.LldpChassisIdSubType;
import org.opennms.netmgt.model.topology.LldpEndPoint.LldpPortIdSubType;

//@RunWith(OpenNMSJUnit4ClassRunner.class)
//@ContextConfiguration(locations= {
//})
//@JUnitConfigurationEnvironment
public class InMemoryTopologyDaoTest {
    
	TopologyDao m_topologyDao;
    @Before
    public void setUp() throws Exception {
        Properties p = new Properties();
//        p.setProperty("log4j.logger.org.hibernate.SQL", "WARN");
//        p.setProperty("log4j.logger.org.opennms.mock.snmp", "WARN");
//       p.setProperty("log4j.logger.org.opennms.core.test.snmp", "WARN");
        p.setProperty("log4j.logger.org.opennms.netmgt", "DEBUG");
//        p.setProperty("log4j.logger.org.springframework","WARN");
//        p.setProperty("log4j.logger.com.mchange.v2.resourcepool", "WARN");
        MockLogAppender.setupLogging(p);
        
        m_topologyDao = new TopologyDaoInMemoryImpl();
     }

	@Test
	public void testSaveOrUpDateLldp() {
		Element elementAF = new Element();
		elementAF.addElementIdentifier(new LldpElementIdentifier("0016c8bd4d80", "switch3", LldpChassisIdSubType.LLDP_CHASSISID_SUBTYPE_MACADDRESS,111));
		elementAF.addElementIdentifier(new NodeElementIdentifier(111));
		LldpEndPoint endPointA1A = new LldpEndPoint("Ge0/1", LldpPortIdSubType.LLDP_PORTID_SUBTYPE_INTERFACENAME,111);
		LldpEndPoint endPointA1B = new LldpEndPoint("Ge0/2", LldpPortIdSubType.LLDP_PORTID_SUBTYPE_INTERFACENAME,111);
		LldpEndPoint endPointA1C = new LldpEndPoint("Ge0/3", LldpPortIdSubType.LLDP_PORTID_SUBTYPE_INTERFACENAME,111);
		LldpEndPoint endPointA1D = new LldpEndPoint("Ge0/4", LldpPortIdSubType.LLDP_PORTID_SUBTYPE_INTERFACENAME,111);
		elementAF.addEndPoint(endPointA1A);
		elementAF.addEndPoint(endPointA1B);
		elementAF.addEndPoint(endPointA1C);
		elementAF.addEndPoint(endPointA1D);
		
		m_topologyDao.saveOrUpdate(endPointA1A);
		m_topologyDao.saveOrUpdate(endPointA1B);
		m_topologyDao.saveOrUpdate(endPointA1C);
		m_topologyDao.saveOrUpdate(endPointA1D);
		
		assertEquals(1, m_topologyDao.getTopology().size());
		
		
		Element elementA = new Element();
		elementA.addElementIdentifier(new LldpElementIdentifier("0016c8bd4d80", "switch3", LldpChassisIdSubType.LLDP_CHASSISID_SUBTYPE_MACADDRESS,101));
		LldpEndPoint endPointA1 = new LldpEndPoint("Ge0/1", LldpPortIdSubType.LLDP_PORTID_SUBTYPE_INTERFACENAME,101);
		elementA.addEndPoint(endPointA1);
		
		
	}
    
	@Test
	public void testSaveOrUpDateBridge() throws UnknownHostException {
		
		Integer nodeA  = 10;
        Integer portA1 = 1;
        Integer portA2 = 2;

        Integer nodeB = 20;
        InetAddress ip1 = InetAddress.getByName("10.10.10.1");
        InetAddress ip2 = InetAddress.getByName("10.10.10.2");

        assertEquals(0, m_topologyDao.getTopology().size());

        Element host1 = new Element();
        host1.addElementIdentifier(new MacAddrElementIdentifier("000daaaa0001", nodeB));
        host1.addElementIdentifier(new InetElementIdentifier(ip1, nodeB));
        MacAddrEndPoint mac1 = new MacAddrEndPoint("000daaaa0001", nodeB);
        mac1.setIpAddr(ip1);
        host1.addEndPoint(mac1);

        m_topologyDao.saveOrUpdate(mac1);
        assertEquals(1, m_topologyDao.getTopology().size());

        Element host2 = new Element();
        host2.addElementIdentifier(new MacAddrElementIdentifier("000daaaa0002", nodeB));
        host2.addElementIdentifier(new InetElementIdentifier(ip2, nodeB));
        MacAddrEndPoint mac2 = new MacAddrEndPoint("000daaaa0002", nodeB);
        mac2.setIpAddr(ip2);
        host2.addEndPoint(mac2);

        m_topologyDao.saveOrUpdate(mac2);
        assertEquals(2, m_topologyDao.getTopology().size());

        Element bridge = new Element();
        bridge.addElementIdentifier(new BridgeElementIdentifier("000a00000010", nodeA));
        bridge.addElementIdentifier(new NodeElementIdentifier(nodeA));
        
        BridgeEndPoint bridgeport1 = new BridgeEndPoint(portA1, nodeA);
        bridge.addEndPoint(bridgeport1);
        assertEquals(null, m_topologyDao.get(bridgeport1));

        Element rhost1 = new Element();
        rhost1.addElementIdentifier(new MacAddrElementIdentifier("000daaaa0001", nodeA));
        MacAddrEndPoint rmac1 = new MacAddrEndPoint("000daaaa0001", nodeA);
        rhost1.addEndPoint(rmac1);

        BridgeDot1dTpFdbLink link1 = new BridgeDot1dTpFdbLink(bridgeport1, rmac1, nodeA);
        
        m_topologyDao.saveOrUpdate(link1);

        assertEquals(3, m_topologyDao.getTopology().size());

        EndPoint dbbridgeport1 = m_topologyDao.get(bridgeport1);
        assertEquals(bridge, dbbridgeport1.getElement());
        assertEquals(1, dbbridgeport1.getElement().getEndpoints().size());
        assertEquals(bridgeport1, dbbridgeport1);
        assertEquals(mac1, dbbridgeport1.getLink().getB());

        Element dbelement = m_topologyDao.get(new InetElementIdentifier(ip1, -1));
        assertEquals(true, dbelement.hasElementIdentifier(new MacAddrElementIdentifier("000daaaa0001", nodeA)));
        assertEquals(1, dbelement.getEndpoints().size());

        assertEquals(null, m_topologyDao.get(new MacAddrElementIdentifier("000a00000010", nodeA)));
        
        EndPoint dbmac = m_topologyDao.get(mac2);
        assertEquals(2, dbmac.getElement().getElementIdentifiers().size());

        Element rbridge = new Element();
        rbridge.addElementIdentifier(new BridgeElementIdentifier("000a00000010", nodeA));
        rbridge.addElementIdentifier(new NodeElementIdentifier(nodeA));
        
        BridgeEndPoint bridgeport2 = new BridgeEndPoint(portA2, nodeA);
        
        Element rhost2 = new Element();
        rhost2.addElementIdentifier(new MacAddrElementIdentifier("000daaaa0002", nodeA));
        MacAddrEndPoint rmac2 = new MacAddrEndPoint("000daaaa0002", nodeA);
        rhost2.addEndPoint(rmac2);
        
        BridgeDot1dTpFdbLink link2 = new BridgeDot1dTpFdbLink(bridgeport2, rmac2, nodeA);
        
        m_topologyDao.saveOrUpdate(link2);
        
        assertEquals(3, m_topologyDao.getTopology().size());

        EndPoint dbbridgeport2 = m_topologyDao.get(bridgeport2);
        assertEquals(bridge, dbbridgeport2.getElement());
        assertEquals(2, dbbridgeport2.getElement().getEndpoints().size());
        assertEquals(bridgeport2, dbbridgeport2);
        assertEquals(mac2, dbbridgeport2.getLink().getB());

        m_topologyDao.delete(new BridgeDot1dTpFdbLink(bridgeport1, rmac1, nodeA));
        assertEquals(null, m_topologyDao.get(mac1).getLink());
        assertEquals(null, m_topologyDao.get(bridgeport1).getLink());

        m_topologyDao.delete(host1);
        assertEquals(2, m_topologyDao.getTopology().size());
        assertEquals(null, m_topologyDao.get(new InetElementIdentifier(ip1, nodeA)));
        assertEquals(null, m_topologyDao.get(new MacAddrElementIdentifier("000daaaa0001", nodeA)));

        m_topologyDao.delete(bridgeport1);
        assertEquals(2, m_topologyDao.getTopology().size());
        assertEquals(null, m_topologyDao.get(bridgeport1));
        
        m_topologyDao.delete(new InetElementIdentifier(ip2, nodeB));
        assertEquals(null, m_topologyDao.get(new InetElementIdentifier(ip2, nodeB)));

        m_topologyDao.saveOrUpdate(link1);
        assertEquals(3, m_topologyDao.getTopology().size());

        m_topologyDao.saveOrUpdate(link2);
        assertEquals(3, m_topologyDao.getTopology().size());
        
	}


}
