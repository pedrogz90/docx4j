/*
 *  Copyright 2007-2008, Plutext Pty Ltd.
 *   
 *  This file is part of docx4j.

    docx4j is licensed under the Apache License, Version 2.0 (the "License"); 
    you may not use this file except in compliance with the License. 

    You may obtain a copy of the License at 

        http://www.apache.org/licenses/LICENSE-2.0 

    Unless required by applicable law or agreed to in writing, software 
    distributed under the License is distributed on an "AS IS" BASIS, 
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
    See the License for the specific language governing permissions and 
    limitations under the License.

 */

package org.docx4j.openpackaging.parts.PresentationML;

import java.util.Random;

import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.dml.CTColorMapping;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.relationships.Relationship;
import org.pptx4j.pml.CTCommonSlideData;
import org.pptx4j.pml.CTSlideLayoutIdList;
import org.pptx4j.pml.CTSlideLayoutIdListEntry;
import org.pptx4j.pml.ObjectFactory;
import org.pptx4j.pml.Sld;
import org.pptx4j.pml.SldMaster;



public final class SlideMasterPart extends JaxbPmlPart<SldMaster> {
	
	public SlideMasterPart(PartName partName) throws InvalidFormatException {
		super(partName);
		init();
	}

	public SlideMasterPart() throws InvalidFormatException {
		super(new PartName("/ppt/slideMasters/slideMaster1.xml"));
		init();
	}
	
	public void init() {		
		// Used if this Part is added to [Content_Types].xml 
		setContentType(new  org.docx4j.openpackaging.contenttype.ContentType( 
				org.docx4j.openpackaging.contenttype.ContentTypes.PRESENTATIONML_SLIDE_MASTER));

		// Used when this Part is added to a rels 
		setRelationshipType(Namespaces.PRESENTATIONML_SLIDE_MASTER);
		
	}
	
	
	
	public static SldMaster createSldMaster() throws JAXBException {

		ObjectFactory factory = Context.getpmlObjectFactory(); 
		SldMaster sldMaster = factory.createSldMaster();
		sldMaster.setCSld( 
				(CTCommonSlideData)XmlUtils.unmarshalString(COMMON_SLIDE_DATA, Context.jcPML) );
		sldMaster.setClrMap(
				(CTColorMapping)XmlUtils.unmarshalString(COLOR_MAPPING, Context.jcPML, CTColorMapping.class ) );
		
		CTSlideLayoutIdList slideLayoutIdList = factory.createCTSlideLayoutIdList();
		sldMaster.setSldLayoutIdLst(slideLayoutIdList);
		
		return sldMaster;		
	}
		
	public CTSlideLayoutIdListEntry addSlideLayoutIdListEntry(SlideLayoutPart slideLayoutPart) 
		throws InvalidFormatException {
		
		Relationship rel = this.addTargetPart(slideLayoutPart);
		
		CTSlideLayoutIdListEntry entry = Context.getpmlObjectFactory().createCTSlideLayoutIdListEntry();
		
		entry.setId( new Long(this.getSlideLayoutOrMasterId()) );
		entry.setRid(rel.getId());
		
		this.jaxbElement.getSldLayoutIdLst().getSldLayoutId().add(entry);
		
		return entry;
	}
	
	

}