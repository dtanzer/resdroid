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

import java.util.ArrayList;
import java.util.List;

import net.davidtanzer.resdroid.artifacts.ArtifactGenerator;

public class ActivityLayoutGenerator implements ArtifactGenerator {
	private final List<Outputable> output = new ArrayList<Outputable>();
	private String fileName;

	@Override
	public void generateArtifact() {
		FileGenerator.generate("res/layout", fileName, output);
	}

	public void setOutputFileName(final String fileName) {
		this.fileName = fileName;
	}

	public void add(final String string) {
		output.add(new StringOutput(string));
	}

}
