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
package net.davidtanzer.resdroid.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileGenerator {

	public static void generate(final String directoryName, final String fileName, final List<Outputable> output) {
		File directory = new File(directoryName);
		if(!directory.exists()) {
			directory.mkdirs();
		}
		if(!directory.isDirectory()) {
			throw new RuntimeException("Target \""+directory.getAbsolutePath()+"\" exists but is not a directory");
		}

		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(directory, fileName));

			for(Outputable o : output) {
				writer.append(o.getOutput());
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not write file", e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
			}
		}
	}

}
