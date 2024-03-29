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

public class Generate {
	private static String basePackageName;

	public static void main(final String[] args) {
		try {
			System.out.println("[resdroid] Generating android artifacts from source folder \"./resdroid\"...");

			if(args.length != 1) {
				throw new RuntimeException("Usage: Generate [base.package.name]");
			}
			basePackageName = args[0];

			ResourceFileCollection resources = ResourceWalker.readAllResources("resdroid");
			resources.generateArtifacts();
		} catch(Exception e) {
			System.err.println("[resdroid] ERROR: "+e.getMessage());
		}
	}

	public static String basePackageName() {
		return basePackageName;
	}
}
