/*
   Copyright 2011 David Tanzer

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package net.davidtanzer.resdroid.artifacts;

import java.util.ArrayList;
import java.util.List;

import net.davidtanzer.resdroid.generator.ActivityLayoutGenerator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GenActivity extends DefaultHandler implements Artifact {

	private final String prefix;
	private final String generatedActivityName;
	private String currentElementName;
	private ActivitySourceFile sourceFile;
	private final List<Widget> widgets = new ArrayList<Widget>();
	private final ActivityLayoutGenerator generator = new ActivityLayoutGenerator();
	private final String layoutName;

	public GenActivity(final String fileName) {
		String shortName = fileName.substring(0, fileName.lastIndexOf(".activity.xml"));
		layoutName = shortName+"_activity";
		generator.setOutputFileName(layoutName + ".xml"); //TODO add package prefix if necessary!
		prefix = shortName+"_";
		generatedActivityName = toJavaName(shortName, false)+"ActivityBase";
	}

	private String toJavaName(final String toParse, final boolean firstLower) {
		String[] parts = toParse.split("[_\\- ]");
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for(String part : parts) {
			String firstLetter = part.substring(0, 1);
			if(first && !firstLower) {
				firstLetter = firstLetter.toUpperCase();
			}
			result.append(firstLetter);
			result.append(part.substring(1));
			first = false;
		}
		return result.toString();
	}

	public String getPrefix() {
		return prefix;
	}
	public String getGeneratedActivityName() {
		return generatedActivityName;
	}

	@Override
	public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
		currentElementName = qName;
		generator.add("<"+qName);

		for(int i=0; i<attributes.getLength(); i++) {
			String name = attributes.getQName(i);
			String value = attributes.getValue(i);

			generator.add(" "+name+"=\"");
			if(value.startsWith("@+id")) {
				String parsedId = value.substring("@+id/".length());
				generator.add("@+id/"+prefix+parsedId);

				widgets.add(new Widget(Widget.imports.get(currentElementName), Widget.classes.get(currentElementName),
						toJavaName(parsedId, true), prefix+parsedId));
			} else {
				generator.add(value);
			}
			generator.add("\"");
		}

		generator.add(">");
	}

	@Override
	public void endElement(final String uri, final String localName, final String qName) throws SAXException {
		generator.add("</"+qName+">");
	}

	@Override
	public void characters(final char[] ch, final int start, final int length) throws SAXException {
		String s = new String(ch, start, length);
		generator.add(s);
	}

	public ActivitySourceFile getSourceFile() {
		if(sourceFile == null) {
			createSourceFile();
		}
		return sourceFile;
	}

	private void createSourceFile() {
		sourceFile = new ActivitySourceFile(generatedActivityName, layoutName, widgets);
	}

	@Override
	public ArtifactGenerator generator() {
		return generator;
	}
}
