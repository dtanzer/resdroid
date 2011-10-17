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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.davidtanzer.resdroid.Generate;
import net.davidtanzer.resdroid.generator.ActivitySourceGenerator;

public class ActivitySourceFile implements Artifact {
	private final ActivitySourceGenerator generator = new ActivitySourceGenerator();

	public ActivitySourceFile(final String generatedActivityName, final String layoutName, final List<Widget> widgets) {
		generator.setFileName(generatedActivityName+".java");

		addPackageName();
		addImports(widgets);
		addActivity(generatedActivityName, layoutName, widgets);
	}

	private void addActivity(final String generatedActivityName, final String layoutName, final List<Widget> widgets) {
		generator.add("public class "+generatedActivityName+" extends Activity {\n");

		addWidgetFields(widgets);

		generator.add("@Override\n");
		generator.add("public void onCreate(Bundle savedInstanceState) {\n");
		generator.add("super.onCreate(savedInstanceState);\n");
		generator.add("setContentView(R.layout."+layoutName+");\n");

		addWidgets(widgets);

		generator.add("}\n");

		generator.add("}\n");
	}

	private void addWidgetFields(final List<Widget> widgets) {
		for(Widget w : widgets) {
			generator.add("protected "+w.widgetClassName+" "+w.widgetVariableName+";\n");
		}
	}

	private void addWidgets(final List<Widget> widgets) {
		for(Widget w : widgets) {
			generator.add(w.widgetVariableName+" = ("+w.widgetClassName+") findViewById(R.id."+w.widgetId+");");
		}
	}

	private void addImports(final List<Widget> widgets) {
		Set<String> importNames = new HashSet<String>();

		for(Widget w : widgets) {
			importNames.add(w.widgetImportName);
		}

		generator.add("import android.app.Activity;\n");
		generator.add("import android.os.Bundle;\n");
		generator.add("import "+Generate.basePackageName()+".R;\n");

		for(String importName : importNames) {
			generator.add("import "+importName+";\n");
		}
	}

	private void addPackageName() {
		generator.add("package "+Generate.basePackageName()+".generated;\n");
	}

	@Override
	public ArtifactGenerator generator() {
		return generator;
	}

}
