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
package net.davidtanzer.resdroid;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.davidtanzer.resdroid.artifacts.GenActivity;

public class ResourceWalker {

	public static ResourceFileCollection readAllResources(final String folderName) {
		File folder = new File(folderName);
		if(!folder.exists()) {
			throw new RuntimeException("The folder \""+folder.getAbsolutePath()+"\" does not exist!");
		}
		if(!folder.isDirectory()) {
			throw new RuntimeException("The file system resource \""+folder.getAbsolutePath()+"\" is not a directory!");
		}

		ResourceFileCollection result = new ResourceFileCollection();
		for(File f : folder.listFiles()) {
			if(f.getName().equals("layout") && f.isDirectory()) {
				readLayout(f, result);
			}
		}
		return result;
	}

	private static void readLayout(final File folder, final ResourceFileCollection result) {
		for(File f : folder.listFiles()) {
			if(f.isDirectory()) {
				throw new RuntimeException("Folders within \"layout\" are not yet supported");
			}

			if(f.getName().endsWith(".activity.xml")) {
				GenActivity activity = new GenActivity(f.getName());
				System.out.println("[resdroid] **activity** "+f.getName()+" -> "+activity.getGeneratedActivityName());

				try {
					SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
					parser.parse(f, activity);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				result.addArtifact(activity);
				result.addArtifact(activity.getSourceFile());
			} else {
				throw new RuntimeException("Layouts that are not activities are not yet supported (make sure your file names end in \".activity.xml\")");
			}
		}
	}

}
